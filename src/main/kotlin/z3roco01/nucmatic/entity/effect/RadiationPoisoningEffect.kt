package z3roco01.nucmatic.entity.effect

import com.mojang.authlib.minecraft.client.MinecraftClient
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory
import net.minecraft.registry.RegistryKeys
import net.minecraft.server.MinecraftServer
import net.minecraft.world.World
import sun.font.AttributeValues
import z3roco01.nucmatic.Nucmatic
import z3roco01.nucmatic.entity.damage.NucmaticDamageTypes

/**
 * The status effect that gets applied when the player has radiation poisoning
 * @since 2024/09/26
 */
class RadiationPoisoningEffect: StatusEffect(StatusEffectCategory.HARMFUL, 0x2CFA1F) {
    var healthRemoveAmount = 0
    var tickCounter = 0
    var RADIATION_POISONING_ATTRIBUTE_MODIFIER =
        EntityAttributeModifier(Nucmatic.mkId("radiation_poisoning"), -1.0, EntityAttributeModifier.Operation.ADD_VALUE)

    // is called every tick, checks if the effect can be applied
    // set to true so it will always be applicable
    // public static final RegistryKey<DamageType> TATER_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of("fabric-docs-reference", "tater"));
    override fun canApplyUpdateEffect(duration: Int, amplifier: Int) = true

    // called to update the effect, gets called every tick
    // decreases max health by 1 * amplifier per tick
    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int): Boolean {
        tickCounter++
        // get max health
        val maxHealth = entity.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH)
        // if there is health to remove, remove it
        if(maxHealth > 1 && tickCounter % 20 == 0) {
            entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)!!.removeModifier(RADIATION_POISONING_ATTRIBUTE_MODIFIER)
            healthRemoveAmount += 1*amplifier
            Nucmatic.LOGGER.info(healthRemoveAmount.toString())
            RADIATION_POISONING_ATTRIBUTE_MODIFIER = EntityAttributeModifier(Nucmatic.mkId("radiation_poisoning"),
                healthRemoveAmount * -1.0, EntityAttributeModifier.Operation.ADD_VALUE)
            entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)!!.addPersistentModifier(RADIATION_POISONING_ATTRIBUTE_MODIFIER)
            tickCounter = 0
        }

        return true
    }
}