package z3roco01.nucmatic.block.entity

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.BlockState
import net.minecraft.component.DataComponentTypes
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.RegistryWrapper
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import z3roco01.nucmatic.Nucmatic
import z3roco01.nucmatic.block.BasicInventory
import z3roco01.nucmatic.item.NuclearFuelItem
import z3roco01.nucmatic.screen.NuclearGeneratorScreenHandler
import z3roco01.pragmatica.screen.EnergyScreenHandler

/**
 * the block entity for the nuclear generator
 * @since 07/10/2024
 */
class NuclearGeneratorBlockEntity(pos: BlockPos, state: BlockState):
    EnergyContainer(NucmaticBlockEntityTypes.NUCLEAR_GENERATOR_TYPE, pos, state), ExtendedScreenHandlerFactory<EnergyScreenHandler.EnergyContainerScreenData>, BasicInventory{
    override fun getEnergyCapacity() = 32000L
    override fun getMaxIns() = 200L
    override fun getMaxExt() = 200L

    // item held by this block, used to store the burning fuel
    private val items = DefaultedList.ofSize(1, ItemStack.EMPTY)

    init {
        // loop over every direction ...
        for(i in Direction.entries) {
            // and set its io permission to extract only
            sideIOMap[i] = IOPermission.EXTRACT
        }
    }

    // create the data that will be passed to the screen handler
    override fun getScreenOpeningData(player: ServerPlayerEntity) = EnergyScreenHandler.EnergyContainerScreenData(this.getEnergy(), this.getEnergyCapacity(), pos)
    // returns the display name for the screen
    override fun getDisplayName() = Text.translatable(cachedState.block.translationKey)
    // creates the screen handler
    override fun createMenu(syncId: Int, playerInventory: PlayerInventory, player: PlayerEntity): ScreenHandler {
        return NuclearGeneratorScreenHandler(syncId, playerInventory, getScreenOpeningData(player as ServerPlayerEntity), this)
    }

    override fun getItems() = items

    // code ran on every tick, will generate energy if it is possible
    override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: EnergyContainer) {
        super.tick(world, pos, state, blockEntity)

        if(world.isClient) return

        Nucmatic.LOGGER.info("asdasdasd")
        // if we have a nuclear fuel in the inventory
        if(items[0].item is NuclearFuelItem) {
            var fuel = items[0]
            // then check that it still has energy left and that this block has space for energy
            if(fuel.get(DataComponentTypes.DAMAGE)!! < fuel.maxDamage && getEnergy() < getEnergyCapacity()) {
                var burnRate = (fuel.item as NuclearFuelItem).burnRate
                // damage the fuel stack ( burn energy ) by the specified rate
                fuel.damage += burnRate
                // add the specified amount of burnt energy to the stored energy
                incrementEnergy(burnRate)
            }
        }

    }

    // overriden to read the inventory's nbt
    override fun readNbt(nbt: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
        super.readNbt(nbt, registryLookup)
        Inventories.readNbt(nbt, items, registryLookup)
    }

    // overriden to write the inventory's nbt
    override fun writeNbt(nbt: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
        Inventories.writeNbt(nbt, items, registryLookup)
        super.writeNbt(nbt, registryLookup)
    }
}