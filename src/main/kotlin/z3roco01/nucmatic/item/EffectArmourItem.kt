package z3roco01.nucmatic.item

import net.minecraft.entity.Entity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.item.ArmorItem
import net.minecraft.item.ArmorMaterial
import net.minecraft.item.ItemStack
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.world.World
import z3roco01.nucmatic.Nucmatic

/**
 * armor item that applies an the effect when worn
 * @since 02/10/2024
 * @param material the [ArmorMaterial] of it
 * @param type the [Type] of this armour
 * @param settings the [Settings] for the item version
 * @param effect the effect that will apply
 */
class EffectArmourItem(material: RegistryEntry<ArmorMaterial>, type: Type, settings: Settings,
                       val effect: StatusEffectInstance): ArmorItem(material, type, settings) {
    override fun inventoryTick(stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean) {
        if(slot == type.equipmentSlot.entitySlotId)
            Nucmatic.LOGGER.info("pppppp")
    }
}