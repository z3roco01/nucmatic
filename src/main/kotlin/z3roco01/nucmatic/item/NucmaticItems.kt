package z3roco01.nucmatic.item

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ArmorItem
import net.minecraft.item.Item
import net.minecraft.item.Item.Settings
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.text.Text
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
    val NU_FUEL  = NuclearFuelItem(64000, 50, 0.0005f, 1, (20..40)) // natural uranium fuel, used in some reactors
    val LEU_FUEL = NuclearFuelItem(128000, 100, 0.001f,  1, (20..80)) // low enriched uranium fuel, used in most reactors
    val HEU_FUEL = NuclearFuelItem(256000, 300, 0.005f,  2, (20..80)) // high enriched uranium fuel, used mostly in weapons
    // hazmat armour items
    val HAZMAT_HELMET     = EffectArmourItem(NucmaticArmourMaterials.HAZMAT_ARMOR, ArmorItem.Type.HELMET, Settings()
        .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15)), StatusEffects.SLOWNESS)
    val HAZMAT_CHESTPLATE = EffectArmourItem(NucmaticArmourMaterials.HAZMAT_ARMOR, ArmorItem.Type.CHESTPLATE, Settings()
        .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(15)), StatusEffects.SLOWNESS)
    val HAZMAT_LEGGINGS   = EffectArmourItem(NucmaticArmourMaterials.HAZMAT_ARMOR, ArmorItem.Type.LEGGINGS, Settings()
        .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(15)), StatusEffects.SLOWNESS)
    val HAZMAT_BOOTS      = EffectArmourItem(NucmaticArmourMaterials.HAZMAT_ARMOR, ArmorItem.Type.BOOTS, Settings()
        .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(15)), StatusEffects.SLOWNESS)

    // create a registry key for the item group
    val NUCMATIC_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Nucmatic.mkId("item_group"))
    // make the actual item group
    val NUCMATIC_ITEM_GROUP = FabricItemGroup.builder()
        .icon{ ItemStack(HEU_FUEL) }
        .displayName(Text.translatable("itemGroup.nucmatic"))
        .build()
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

        // register the item group
        Registry.register(Registries.ITEM_GROUP, NUCMATIC_ITEM_GROUP_KEY, NUCMATIC_ITEM_GROUP)
        // add the items to the group, will be in the specified order
        ItemGroupEvents.modifyEntriesEvent(NUCMATIC_ITEM_GROUP_KEY).register{itemGroup ->
            itemGroup.add(RAW_URANIUM)
            itemGroup.add(NU_FUEL)
            itemGroup.add(LEU_FUEL)
            itemGroup.add(HEU_FUEL)
            itemGroup.add(STEM_CELL)
            itemGroup.add(HAZMAT_HELMET)
            itemGroup.add(HAZMAT_CHESTPLATE)
            itemGroup.add(HAZMAT_LEGGINGS)
            itemGroup.add(HAZMAT_BOOTS)
        }
    }

    /**
     * helper method for registration
     * @param path the path for the id of the item
     * @param item the item
     */
    private fun register(path: String, item: Item) = Registry.register(Registries.ITEM, Nucmatic.mkId(path), item)
}