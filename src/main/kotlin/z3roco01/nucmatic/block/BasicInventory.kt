package z3roco01.nucmatic.block

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList

/**
 * a basic inventory class to be used by blocks that hold items<br>
 * based off of this code https://wiki.fabricmc.net/tutorial:inventory#implementing_inventory
 * @since 06/01/2024
 */
interface BasicInventory: Inventory {
    /**
     * returns a defauled list containing the items in this inventory
     */
    fun getItems(): DefaultedList<ItemStack>

    /**
     * returns the inventory size gotten from [getItems]
     */
    override fun size() = getItems().size

    /**
     * gets the stack in the specified slot
     * @param slot the 0 indexed slot number
     */
    override fun getStack(slot: Int) = getItems()[slot]

    /**
     * completely empties a slot
     * @param slot the 0 indexed slot number
     */
    override fun removeStack(slot: Int): ItemStack = Inventories.removeStack(getItems(), slot)

    /**
     * removes the specified amount from the specified slot
     * @param slot the 0 indexed slot number
     * @param amount the amount to be removed, if there is less it will just be empty
     */
    override fun removeStack(slot: Int, amount: Int): ItemStack {
        // calls a helper function to removed x items from a slot
        val removedStack = Inventories.splitStack(getItems(), slot, amount)

        // if the slot is not empty, resync inv
        if(!removedStack.isEmpty)
            markDirty()

        return removedStack
    }

    /**
     * sets the specified slot to the passed stack
     * @param slot the 0 indexed slot number to be set
     * @param stack the new [ItemStack] to set the slot to
     */
    override fun setStack(slot: Int, stack: ItemStack) {
        getItems()[slot] = stack

        // if the stack count is too big for the stack...
        if(stack.count > stack.maxCount)
            // set it to the max count
            stack.count = stack.maxCount
    }

    /**
     * returns false if the inventory is not empty, else returns true
     */
    override fun isEmpty(): Boolean {
        // loop over every item
        for(i in 0..<size()){
            // if a stack is not empty...
            if(!getStack(i).isEmpty)
                return false
        }
        // no empty stacks have been found so return empty
        return true
    }

    /**
     * completely empty the inventory
     */
    override fun clear() {
        getItems().clear()
    }

    override fun canPlayerUse(player: PlayerEntity) = true
}