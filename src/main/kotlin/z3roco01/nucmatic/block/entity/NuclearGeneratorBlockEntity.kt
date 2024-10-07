package z3roco01.nucmatic.block.entity

import net.fabricmc.fabric.api.networking.v1.PlayerLookup
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.RegistryWrapper
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import team.reborn.energy.api.EnergyStorage
import team.reborn.energy.api.EnergyStorageUtil
import team.reborn.energy.api.base.SimpleSidedEnergyContainer
import kotlin.math.max
import kotlin.math.min

/**
 * the block entity for the nuclear generator
 * @since 07/10/2024
 */
class NuclearGeneratorBlockEntity(pos: BlockPos, state: BlockState):
    BlockEntity(NucmaticBlockEntityTypes.NUCLEAR_GENERATOR_TYPE, pos, state), BlockEntityTicker<NuclearGeneratorBlockEntity> {
    // map that has an entry for each block side, determines if energy can be extracted/inserted into it
    val sideIOMap = mapOf(
        Direction.DOWN  to IOPermission.INSERT,
        Direction.UP    to IOPermission.INSERT,
        Direction.NORTH to IOPermission.INSERT,
        Direction.SOUTH to IOPermission.INSERT,
        Direction.WEST  to IOPermission.INSERT,
        Direction.EAST  to IOPermission.INSERT)

    val ENERGY_CAPACITY = 32000L

    // energy storage variable, from the team reborn library, handles most of the energy stuff
    val energyStorage = object: SimpleSidedEnergyContainer() {
        override fun onFinalCommit() {
            markDirty()

            syncEnergy()
        }

        override fun getCapacity() = ENERGY_CAPACITY

        override fun getMaxInsert(side: Direction?): Long {
            // if energy can be inserted, then return the insert rate
            if(side == null || canInsertSide(side))
                return 4000L
            // else return 0 since it cannot insert
            return 0
        }

        override fun getMaxExtract(side: Direction?): Long {
            // if energy can be extracted, then return the extract rate
            if(side == null || canExtractSide(side))
                return 4000L
            // else return 0 since it cannot extract
            return 0
        }
    }

    // Syncs the energy amount to the client
    fun syncEnergy() {
        /*if(world != null && !world!!.isClient){
            for(player in PlayerLookup.tracking(world as ServerWorld, getPos()))
                ServerPlayNetworking.send(player, SyncEnergyContainerPayload(getEnergy(), getPos()))
        }*/
    }

    // push power to all blocks accepting that are beside this block
    override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: NuclearGeneratorBlockEntity) {
        // do not run on the client or if it has no energy
        if(world.isClient || getEnergy() <= 0) return

        // loop over each side
        for(side in Direction.entries) {
            // push energy to anything that is accepting energy beside it
            // this api is push based not pull so energy should never be pulled
            EnergyStorageUtil.move(
                energyStorage.getSideStorage(side),
                EnergyStorage.SIDED.find(world, pos.offset(side), side.opposite),
                Long.MAX_VALUE,
                null
            )
        }
    }

    /**
     * checks if extraction can be done on a side
     * @param side the [Direction] that energy is taken from
     * @return true if extraction can happen
     */
    private fun canExtractSide(side: Direction): Boolean {
        // get the permission for that side
        val sidePerms = sideIOMap.get(side)
        return (sidePerms == IOPermission.EXTRACT || sidePerms == IOPermission.INS_EXT)
    }

    /**
     * checks if insertion can be done on a side
     * @param side the [Direction] that energy is inserted from
     * @return true if insertion can happen
     */
    private fun canInsertSide(side: Direction): Boolean {
        // get the permission for that side
        val sidePerms = sideIOMap.get(side)
        return (sidePerms == IOPermission.INSERT || sidePerms == IOPermission.INS_EXT)
    }

    /**
     * sets the energy stored to the supplied amount
     * @param amount the amount that it is being set it ( it will not go above capacity )
     */
    fun setEnergy(amount: Long) {
        // set the amount ( make sure it never goes above the capacity ), and mark the block entity as dirty
        energyStorage.amount = min(amount, energyStorage.capacity)
        // mark this block as dirty, making the data sync when it can
        markDirty()
    }

    /**
     * gets the amount of energy stored
     * @return how much energy is being stored
     */
    fun getEnergy() = energyStorage.amount

    fun incrementEnergy(amount: Long) {
        // get the energy and add the amount, also keep it in check with the capacity
        setEnergy(getEnergy() + amount)
    }

    /**
     * decrements the energy by the supplied amount, will not let it go bellow 0
     * @param amount the amount that it is being decremented by
     */
    fun decrementEnergy(amount: Long) = setEnergy(max(getEnergy() - amount, 0))

    override fun readNbt(nbt: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
        super.readNbt(nbt, registryLookup)
        // extract all information abt energy from the nbt
        val nbtData = nbt.get(ENERGY_DATA_KEY) as NbtCompound
        this.energyStorage.amount = min(nbtData.getLong(AMOUNT_ENERGY_KEY), ENERGY_CAPACITY) // make sure the amount of energy doesnt exceed the max
        syncEnergy()
    }

    override fun writeNbt(nbt: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
        super.writeNbt(nbt, registryLookup)
        // store all information abt energy in the nbt
        val nbtData = NbtCompound()
        nbtData.putLong(AMOUNT_ENERGY_KEY, energyStorage.amount)
        nbt.put(ENERGY_DATA_KEY, nbtData)
    }

    companion object {
        // keys for storage in nbt
        val ENERGY_DATA_KEY = "pragmatica:energy"
        val AMOUNT_ENERGY_KEY = "energy"
    }

    /**
     * an enum for energy permissions
     */
    enum class IOPermission {
        NONE, // used when insertion and extraction cannot happen on a side
        INSERT, // used when only insertion can happen
        EXTRACT, // used when only extraction can happen
        INS_EXT // used when both insertion and extraction can happen
    }
}