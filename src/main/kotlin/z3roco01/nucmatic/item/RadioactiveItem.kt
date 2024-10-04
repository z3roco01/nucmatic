package z3roco01.nucmatic.item

import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.world.World
import z3roco01.nucmatic.entity.effect.NucmaticEffects
import z3roco01.nucmatic.entity.effect.RadiationPoisoningEffect

/**
 * class for all radioactive items, will give [RadiationPoisoningEffect] with a chance% chance
 * @since 28/09/2024
 * @param chance the decimal percent chance of getting [RadiationPoisoningEffect] per tick
 * @param amplifier the amplifier used when [RadiationPoisoningEffect] is applied
 * @param settings the item settings for this item
 */
class RadioactiveItem(val chance: Float, val amplifier: Int, val duration: IntRange, settings: Settings): Item(settings) {
    override fun inventoryTick(stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean) {
        // dont run on client or if its not a LivingEntity
        if(world.isClient || entity !is LivingEntity) return

        // do not give the player radiation poisoning if they are in creative or spectator
        if(entity is PlayerEntity && (entity.isCreative || entity.isSpectator)) return

        // roll a random float between 0 and 1, if it is equal to or smaller than the chance, then the roll succeeded
        // but only do logic if the entity doesnt also already have radiation poisoning
        if(world.random.nextFloat() <= chance &&
            !entity.hasStatusEffect(Registries.STATUS_EFFECT.getEntry(NucmaticEffects.RADIATION_POISONING_EFFECT))) {
            // apply the effect with the amplifier supplied and a random number from the range
            entity.addStatusEffect(StatusEffectInstance(
                Registries.STATUS_EFFECT.getEntry(NucmaticEffects.RADIATION_POISONING_EFFECT),
                duration.random(), amplifier))
        }
    }
}