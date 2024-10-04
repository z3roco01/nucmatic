package z3roco01.nucmatic.entity.effect

import net.minecraft.entity.effect.StatusEffect
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import z3roco01.nucmatic.Nucmatic

/**
 * registration for all the effects in the mod
 * @since 26/09/2024
 */
object NucmaticEffects {
    val RADIATION_POISONING_EFFECT = RadiationPoisoningEffect()

    fun register() {
        register("radiation_poisoning", RADIATION_POISONING_EFFECT)
    }

    /**
     * helper function to make it a bit easier
     */
    private fun register(path: String, effect: StatusEffect) = Registry.register(Registries.STATUS_EFFECT,
        Nucmatic.mkId(path), effect)
}