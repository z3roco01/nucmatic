package z3roco01.nucmatic.item

import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.item.ArmorItem
import net.minecraft.item.ArmorMaterial
import net.minecraft.item.ItemStack
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.world.World

/**
 * armor item that applies an the effect when worn
 * @since 02/10/2024
 * @param material the [ArmorMaterial] of it
 * @param type the [Type] of this armour
 * @param settings the [Settings] for the item version
 * @param effect the effect that will apply
 */
class EffectArmourItem(material: RegistryEntry<ArmorMaterial>, type: Type, settings: Settings,
                       val effect: RegistryEntry<StatusEffect>): ArmorItem(material, type, settings) {
    // runs on every tick when in an inventory
    override fun inventoryTick(stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean) {
        // checks if it is it the slot of its armour type, and if the entity is a LivingEntity
        // you cannot apply status effect if it is not a LivingEntity
        if(slot == type.equipmentSlot.entitySlotId && entity is LivingEntity)
            // we can subtract one from hasArmour's return since it will always be at least 1 since
            // the armour piece this code in running on counts
            entity.addStatusEffect(StatusEffectInstance(effect, 1, hasArmour(entity)-1)) // apply the status effect to the entity

    }

    /**
     * checks how many slots have this armour material
     * @param entity the entity to check
     * @return between 0 and 4, 1 for each piece of this armour that is worn
     */
    private fun hasArmour(entity: LivingEntity): Int {
        // return value
        var count = 0
        // loop over ever armour piece
        for(armour in entity.armorItems) {
            // get the item from the ItemStack
            val item = armour.item
            // if the item is an EffectArmourItem and the material is the same
            if(item is EffectArmourItem && item.material == material)
                count++
        }

        return count
    }
}