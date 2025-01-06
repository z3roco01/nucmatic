package z3roco01.nucmatic.screen

import net.minecraft.entity.player.PlayerInventory
import net.minecraft.util.Identifier
import z3roco01.nucmatic.Nucmatic
import z3roco01.pragmatica.screen.EnergyScreenHandler

class NuclearGeneratorScreenHandler(syncId: Int, playerInv: PlayerInventory, data: EnergyContainerScreenData): EnergyScreenHandler(
    NucmaticScreenHandlerTypes.NUCLEAR_GENERATOR_TYPE, syncId, playerInv, data) {

    init {
        // add the players inventory and hotbar slots
        addPlayerInventory(playerInv)
    }

}