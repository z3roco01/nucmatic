package z3roco01.nucmatic.item

import net.minecraft.item.ArmorItem
import net.minecraft.item.ArmorMaterial
import net.minecraft.item.Items
import net.minecraft.recipe.Ingredient
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import z3roco01.nucmatic.Nucmatic
import java.util.function.Supplier

/**
 * registration for all the armour materials
 * @since 02/10/2024
 */
object NucmaticArmourMaterials {
    val  HAZMAT_ARMOR= register("hazmat", mapOf(
            // map of defense points for each supported item type
            ArmorItem.Type.HELMET to 3,
            ArmorItem.Type.CHESTPLATE to 5,
            ArmorItem.Type.LEGGINGS to 4,
            ArmorItem.Type.BOOTS to 2,
        ),
            //the enchantability of this armour type
            13,
            // sound that plays when equipping, uses the sound for chainmail
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,
            // supplier for the repair ingredient
            { Ingredient.ofItems(Items.AIR) },
            // toughness ( damage absorption ) of the armour
            0.2f,
            // knockback resistance of this armour
            0.4f,
            // is the armour dyeable
            false)

    /**
     * helper method for registration of armour materials
     * @param id the path of the id for the material
     * @param defensePoints a map for each armour piece type to how many defense points it will have
     * @param enchantability how enchantable any piece of this armour will be
     * @param equipSound the sound that will play when you equip a piece of this armour
     * @param repairIngredient a supplier for the repair item of this armour
     * @param toughness how much damage will be absorbed by a piece of this armour
     * @param kbResistance how much knockback will be nullified once a player is hit with this armour on
     * @param dyeable if this armour type is dyable or not ( like leather )
     */
    private fun register(id: String, defensePoints: Map<ArmorItem.Type, Int>, enchantability: Int,
                         equipSound: RegistryEntry<SoundEvent>, repairIngredient: Supplier<Ingredient>,
                         toughness: Float, kbResistance: Float, dyeable: Boolean): RegistryEntry<ArmorMaterial> {
        // get supported texture layers for this armour
        val layers = listOf(
            ArmorMaterial.Layer(Nucmatic.mkId(id), "", dyeable)
        )

        // create the armor material with the supplied values
        var material = ArmorMaterial(defensePoints, enchantability, equipSound, repairIngredient, layers, toughness, kbResistance)
        // then register that material
        material = Registry.register(Registries.ARMOR_MATERIAL, Nucmatic.mkId(id), material)

        // return a registry entry of the material since that is what we need
        return RegistryEntry.of(material)
    }
}