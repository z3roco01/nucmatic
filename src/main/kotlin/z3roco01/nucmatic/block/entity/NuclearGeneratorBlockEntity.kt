package z3roco01.nucmatic.block.entity

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import z3roco01.nucmatic.Nucmatic
import z3roco01.nucmatic.screen.NuclearGeneratorScreenHandler
import z3roco01.pragmatica.screen.EnergyScreenHandler

/**
 * the block entity for the nuclear generator
 * @since 07/10/2024
 */
class NuclearGeneratorBlockEntity(pos: BlockPos, state: BlockState):
    EnergyContainer(NucmaticBlockEntityTypes.NUCLEAR_GENERATOR_TYPE, pos, state), ExtendedScreenHandlerFactory<EnergyScreenHandler.EnergyContainerScreenData> {
    override fun getEnergyCapacity() = 32000L
    override fun getMaxIns() = 200L
    override fun getMaxExt() = 200L

    // create the data that will be passed to the screen handler
    override fun getScreenOpeningData(player: ServerPlayerEntity) = EnergyScreenHandler.EnergyContainerScreenData(this.getEnergy(), this.getEnergyCapacity(), pos)
    // returns the display name for the screen
    override fun getDisplayName() = Text.translatable(cachedState.block.translationKey)
    // creates the screen handler
    override fun createMenu(syncId: Int, playerInventory: PlayerInventory, player: PlayerEntity): ScreenHandler? {
        Nucmatic.LOGGER.info("poooo")
        return NuclearGeneratorScreenHandler(syncId, playerInventory, getScreenOpeningData(player as ServerPlayerEntity))
    }
}