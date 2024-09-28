package z3roco01.nucmatic.item

import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import z3roco01.nucmatic.Nucmatic

/**
 * registration for all the items in this mod
 * @since 27/09/2024
 */
class NucmaticItems {
    companion object {
        // all the items in the mod
        val STEM_CELL = StemCellItem()

        /**
         * called to register all the items
         */
        fun register() {
            register("stem_cell", STEM_CELL)
        }

        /**
         * helper method for registration
         * @param id the id of the item
         * @param item the item
         */
        private fun register(id: String, item: Item) = Registry.register(Registries.ITEM, Nucmatic.mkId(id), item)
    }
}