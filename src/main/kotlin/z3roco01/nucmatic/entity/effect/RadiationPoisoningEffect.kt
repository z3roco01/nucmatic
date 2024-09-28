package z3roco01.nucmatic.entity.effect

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory
import z3roco01.nucmatic.Nucmatic

/**
 * The status effect that gets applied when the player has radiation poisoning
 * @since 26/09/2024
 */
class RadiationPoisoningEffect: StatusEffect(StatusEffectCategory.HARMFUL, 0x2CFA1F) {
    companion object {
        // id used in the attribute modifier
        val RADIATION_POISONING_MODIFIER_ID = Nucmatic.mkId("radiation_poisoning")
    }

    // is called every tick on client and server, if true then it continues to applyUpdateEffect
    // returns true every 20 ticks which is each second
    override fun canApplyUpdateEffect(duration: Int, amplifier: Int) = duration % 20 == 0

    // called to update the effect, gets called every tick
    // decreases max health by 1 * amplifier per tick
    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int): Boolean {
        // dont run the on the client
        if(entity.world.isClient) return true
        println("as")

        // get max health
        val maxHealth = entity.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH)
        // if there is health to remove, remove it
        if(maxHealth > 1) {
            // if the entity has radiation poisoning caused health loss
            if(entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)!!.hasModifier(
                    RADIATION_POISONING_MODIFIER_ID)) {
                // get the value of the modifier
                val value = entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)!!.getModifier(
                    RADIATION_POISONING_MODIFIER_ID)!!.value

                // then remove the modifier
                removeModifier(entity)

                // and readd the modifier with the decreased health amount
                applyModifier(entity, value - amplifier)
            }else {
                // if the player does not yet have health removed from radiation poisoning
                // then remove amplifier hearts
                applyModifier(entity, amplifier * -1.0)
            }
        }

        return true
    }

    /**
     * removes the attribute modifier
     * @param entity the entity the modifier is on
     */
    private fun removeModifier(entity: LivingEntity) {
        entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)!!.removeModifier(
            RADIATION_POISONING_MODIFIER_ID)
    }

    /**
     * applies the modifier with the specified value
     * @param entity the entity this applies to
     * @param value the value for the modifier
     */
    private fun applyModifier(entity: LivingEntity, value: Double) {
        entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)!!.addPersistentModifier(
            EntityAttributeModifier(RADIATION_POISONING_MODIFIER_ID,
                value, EntityAttributeModifier.Operation.ADD_VALUE))

    }
}