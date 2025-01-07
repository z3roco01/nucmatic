package z3roco01.nucmatic.screen

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot
import z3roco01.nucmatic.item.NuclearFuelItem
import z3roco01.pragmatica.screen.EnergyScreenHandler

/**
 * the class for the screen handler for the nuclear generator
 * @param syncId the id for syncing, passed to [EnergyScreenHandler]
 * @param playerInv the players inventory, passed to [EnergyScreenHandler]
 * @param data the [EnergyContainerScreenData], pass to [EnergyScreenHandler]
 * @param inventory the blocks inventory, passed in the block entity
 */
class NuclearGeneratorScreenHandler(syncId: Int, playerInv: PlayerInventory, data: EnergyContainerScreenData, val inventory: Inventory): EnergyScreenHandler(
    NucmaticScreenHandlerTypes.NUCLEAR_GENERATOR_TYPE, syncId, playerInv, data) {
    // alternative constructor, used by minecraft in some circumstances, is not passed an inventory but instead creates a SimpleInventory
    constructor(syncId: Int, playerInv: PlayerInventory, data: EnergyContainerScreenData): this(syncId, playerInv, data, SimpleInventory(1))

    init {
        checkSize(inventory, 1)
        // run any logic that the inventory may have on open
        inventory.onOpen(playerInv.player)

        // add the players inventory and hotbar slots
        addPlayerInventory(playerInv)

        // add a slot for the fuel
        this.addSlot(NuclearFuelSlot(inventory, 0, 80, 30))
    }

    // can this specific player use this screen
    override fun canUse(player: PlayerEntity) = this.inventory.canPlayerUse(player)

    // overriden so any custom on close logic for the inventory can run
    override fun onClosed(player: PlayerEntity) {
        inventory.onClose(player)
        super.onClosed(player)
    }

    /**
     * this class is for the slot that contains the nuclear fuel.<br>
     * it can only accept items that inherit [z3roco01.nucmatic.item.NuclearFuelItem]
     * @param inventory an [Inventory] passed to the [Slot] constructor
     * @param index an int passed to the [Slot] constructor
     * @param x an int passed to the [Slot] constructor
     * @param y an int passed to the [Slot] constructor
     */
    class NuclearFuelSlot(inventory: Inventory, index: Int, x: Int, y: Int):
        Slot(inventory, index, x, y) {
        override fun canInsert(stack: ItemStack) = (stack.item is NuclearFuelItem)
    }
}