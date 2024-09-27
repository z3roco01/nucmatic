package z3roco01.nucmatic.entity.effect

import net.minecraft.entity.effect.StatusEffect
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import z3roco01.nucmatic.Nucmatic

class NucmaticEffects {
    companion object {
        /**
         * registers all the effects in the mod
         */
        fun register() {
            register("radiation_poisoning", RadiationPoisoningEffect())
        }

        /**
         * helper function to make it a bit easier
         */
        private fun register(id: String, effect: StatusEffect) = Registry.register(Registries.STATUS_EFFECT,
            Nucmatic.mkId(id), effect)
    }
}