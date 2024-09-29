package z3roco01.nucmatic.item

import net.minecraft.item.Item
import net.minecraft.item.Item.Settings
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import z3roco01.nucmatic.Nucmatic

/**
 * registration for all the items in this mod
 * @since 27/09/2024
 */
object NucmaticItems {
    // all the items in the mod
    val STEM_CELL = StemCellItem()
    val RAW_URANIUM = Item(Settings())
    // uranium fuel is enrich in the isotope uranium-235
    val NU_FUEL  = RadioactiveItem(0.01f, 1, (20..40), Settings()) // natural uranium fuel, used in some reactors
    val LEU_FUEL = RadioactiveItem(0.02f, 1, (20..80), Settings()) // low enriched uranium fuel, used in reactors
    val HEU_FUEL = RadioactiveItem(0.04f, 2, (20..80), Settings()) // high enriched uranium fuel, used in weapons

    /**
     * called to register all the items
     */
    fun register() {
        register("stem_cell", STEM_CELL)
        register("raw_uranium", RAW_URANIUM)
        register("nu_fuel", NU_FUEL)
        register("leu_fuel", LEU_FUEL)
        register("heu_fuel", HEU_FUEL)
    }

    /**
     * helper method for registration
     * @param path the path for the id of the item
     * @param item the item
     */
    private fun register(path: String, item: Item) = Registry.register(Registries.ITEM, Nucmatic.mkId(path), item)
}