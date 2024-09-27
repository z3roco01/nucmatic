package z3roco01.nucmatic.effect

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier
import z3roco01.nucmatic.Nucmatic

/**
 * The status effect that gets applied when the player has radiation poisoning
 * @since 2024/09/26
 */
class RadiationPoisoningEffect: StatusEffect(StatusEffectCategory.HARMFUL, 0x2CFA1F) {
    // is called every tick, checks if the effect can be applied
    // set to true so it will always be applicable
    // public static final RegistryKey<DamageType> TATER_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of("fabric-docs-reference", "tater"));
    override fun canApplyUpdateEffect(duration: Int, amplifier: Int) = true

    // called to update the effect, gets called every tick
    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int): Boolean {
        entity.damage(DamageSource(
            entity.registryManager.get(RegistryKeys.DAMAGE_TYPE)
                .entryOf(RADIATION_POISONING_DAMAGE_TYPE)
        ), 1f)

        return true
    }

    companion object {
        val RADIATION_POISONING_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE,
            Identifier.of(Nucmatic.MOD_ID, "radiation_poisoning"))
    }
}