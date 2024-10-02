package z3roco01.nucmatic.item

import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ArmorItem
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
    // uranium fuel is enriched in the isotope uranium-235
    val NU_FUEL  = RadioactiveItem(0.001f, 1, (20..40), Settings()) // natural uranium fuel, used in some reactors
    val LEU_FUEL = RadioactiveItem(0.003f, 1, (20..80), Settings()) // low enriched uranium fuel, used in reactors
    val HEU_FUEL = RadioactiveItem(0.009f, 2, (20..80), Settings()) // high enriched uranium fuel, used in weapons
    // hazmat armour items
    val HAZMAT_HELMET     = EffectArmourItem(NucmaticArmourMaterials.HAZMAT_ARMOR, ArmorItem.Type.HELMET, Settings()
        .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15)), StatusEffectInstance(StatusEffects.SLOWNESS))
    val HAZMAT_CHESTPLATE = ArmorItem(NucmaticArmourMaterials.HAZMAT_ARMOR, ArmorItem.Type.CHESTPLATE, Settings()
        .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(15)))
    val HAZMAT_LEGGINGS   = ArmorItem(NucmaticArmourMaterials.HAZMAT_ARMOR, ArmorItem.Type.LEGGINGS, Settings()
        .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(15)))
    val HAZMAT_BOOTS      = ArmorItem(NucmaticArmourMaterials.HAZMAT_ARMOR, ArmorItem.Type.BOOTS, Settings()
        .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(15)))

    /**
     * called to register all the items
     */
    fun register() {
        register("stem_cell",   STEM_CELL)
        register("raw_uranium", RAW_URANIUM)
        register("nu_fuel",     NU_FUEL)
        register("leu_fuel",    LEU_FUEL)
        register("heu_fuel",    HEU_FUEL)
        register("hazmat_helmet", HAZMAT_HELMET)
        register("hazmat_chestplate", HAZMAT_CHESTPLATE)
        register("hazmat_leggings", HAZMAT_LEGGINGS)
        register("hazmat_boots", HAZMAT_BOOTS)
    }

    /**
     * helper method for registration
     * @param path the path for the id of the item
     * @param item the item
     */
    private fun register(path: String, item: Item) = Registry.register(Registries.ITEM, Nucmatic.mkId(path), item)
}