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

    private var healthRemoveAmount = 0

    // is called every tick on client and server, if true then it continues to applyUpdateEffect
    // returns true every 20 ticks which is each second
    override fun canApplyUpdateEffect(duration: Int, amplifier: Int) = duration % 20 == 0

    // called to update the effect, gets called every tick
    // decreases max health by 1 * amplifier per tick
    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int): Boolean {
        // dont run the on the client
        if(entity.world.isClient) return true

        // if the entity does not have the modifier
        // ( they wont if the player just ate a stem cell or smth else removed it )
       if(!entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)!!.hasModifier(RADIATION_POISONING_MODIFIER_ID)) {
           // then reset the amount of health to remove
           // if we didnt itd remove too much health
           healthRemoveAmount = 0
        }

        // get max health
        val maxHealth = entity.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH)
        // if there is health to remove, remove it
        if(maxHealth > 1) {
            // remove previous modifier
            entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)!!.removeModifier(
                RADIATION_POISONING_MODIFIER_ID)
            // increase health remove amount
            healthRemoveAmount += 1*amplifier
            // create apply the modiifer
            entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)!!.addPersistentModifier(
                EntityAttributeModifier(RADIATION_POISONING_MODIFIER_ID,
                    healthRemoveAmount * -1.0, EntityAttributeModifier.Operation.ADD_VALUE))
        }

        return true
    }
}