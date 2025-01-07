package z3roco01.nucmatic.block.entity

import net.fabricmc.fabric.api.networking.v1.PlayerLookup
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.RegistryWrapper
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import team.reborn.energy.api.EnergyStorage
import team.reborn.energy.api.EnergyStorageUtil
import team.reborn.energy.api.base.SimpleSidedEnergyContainer
import z3roco01.nucmatic.Nucmatic
import z3roco01.nucmatic.network.SyncEnergyPayload
import kotlin.math.max
import kotlin.math.min

/**
 * class for every block entity which stores energy, handles most of the annoying stuff
 * @since 07/10/2024
 */
abstract class EnergyContainer(type: BlockEntityType<*>, pos: BlockPos, state: BlockState):
    BlockEntity(type, pos, state), BlockEntityTicker<EnergyContainer> {
    // map that has an entry for each block side, determines if energy can be extracted/inserted into it
    val sideIOMap = mutableMapOf(
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

        override fun getCapacity() = getEnergyCapacity()

        override fun getMaxInsert(side: Direction?): Long {
            // if energy can be inserted, then return the insert rate
            if(side == null || canInsertSide(side))
                return getMaxIns()
            // else return 0 since it cannot insert
            return 0
        }

        override fun getMaxExtract(side: Direction?): Long {
            // if energy can be extracted, then return the extract rate
            if(side == null || canExtractSide(side))
                return getMaxExt()
            // else return 0 since it cannot extract
            return 0
        }
    }

    /**
     * implemented by all child classes
     * @return how much energy this block can store
     */
    abstract fun getEnergyCapacity(): Long

    /**
     * implemented by all child classes, returns the insert rate
     * @return the insert rate
     */
    abstract protected fun getMaxIns(): Long

    /**
     * implemented by all child classes, returns the extract rate
     * @return the extract rate
     */
    abstract protected fun getMaxExt(): Long

    // Syncs the energy amount to the client
    fun syncEnergy() {
        if(world != null && !world!!.isClient){
            for(player in PlayerLookup.tracking(world as ServerWorld, getPos()))
                ServerPlayNetworking.send(player, SyncEnergyPayload(getEnergy(), getPos()))
        }
    }

    // push power to all blocks accepting that are beside this block
    override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: EnergyContainer) {
        // do not run on the client or if it has no energy
        if(world.isClient || getEnergy() <= 0) return

        Nucmatic.LOGGER.info("gdsifji")

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
    protected fun canExtractSide(side: Direction): Boolean {
        // get the permission for that side
        val sidePerms = sideIOMap.get(side)
        return (sidePerms == IOPermission.EXTRACT || sidePerms == IOPermission.INS_EXT)
    }

    /**
     * checks if insertion can be done on a side
     * @param side the [Direction] that energy is inserted from
     * @return true if insertion can happen
     */
    protected fun canInsertSide(side: Direction): Boolean {
        // get the permission for that side
        val sidePerms = sideIOMap.get(side)
        return (sidePerms == IOPermission.INSERT || sidePerms == IOPermission.INS_EXT)
    }

    /**
     * sets the energy stored to the supplied amount, will not exceed the capacity
     * @param amount the amount that it is being set it
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

    /**
     * increments the stored energy by the supplied amount, will not exceed the capacity
     * @param amount the amount that the energy will be incremented by
     */
    fun incrementEnergy(amount: Long) {
        // get the energy and add the amount, also keep it in check with the capacity
        setEnergy(getEnergy() + amount)
    }

    /**
     * increments the stored energy by the supplied amount, will not exceed the capacity
     * @param amount the amount that the energy will be incremented by
     */
    fun incrementEnergy(amount: Int) {
        incrementEnergy(amount.toLong())
    }

    /**
     * decrements the energy by the supplied amount, will not let it go bellow 0
     * @param amount the amount that it is being decremented by
     */
    fun decrementEnergy(amount: Long) = setEnergy(max(getEnergy() - amount, 0))

    /**
     * decrements the energy by the supplied amount, will not let it go bellow 0
     * @param amount the amount that it is being decremented by
     */
    fun decrementEnergy(amount: Int) {
        decrementEnergy(amount)
    }

    // reads the blocks energy amount from the nbt
    override fun readNbt(nbt: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
        super.readNbt(nbt, registryLookup)
        // extract all information abt energy from the nbt
        val nbtData = nbt.get(ENERGY_DATA_KEY) as NbtCompound
        // set the stored energy
        setEnergy(nbtData.getLong(AMOUNT_ENERGY_KEY))
        syncEnergy()
    }

    // writes the stored energy amount into the blocks nbt
    override fun writeNbt(nbt: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
        super.writeNbt(nbt, registryLookup)
        // store all information abt energy in the nbt
        val nbtData = NbtCompound()
        // store the energy
        nbtData.putLong(AMOUNT_ENERGY_KEY, energyStorage.amount)
        nbt.put(ENERGY_DATA_KEY, nbtData)
    }

    companion object {
        // keys for storage in nbt
        val ENERGY_DATA_KEY = "pragmatica:energy"
        val AMOUNT_ENERGY_KEY = "energy"
    }

    /**
     * the enum for energy permissions
     */
    enum class IOPermission {
        NONE, // used when insertion and extraction cannot happen on a side
        INSERT, // used when only insertion can happen
        EXTRACT, // used when only extraction can happen
        INS_EXT // used when both insertion and extraction can happen
    }
}