package z3roco01.nucmatic.entity.damage

import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import z3roco01.nucmatic.Nucmatic

/**
 * holds all the damage types for this mod
 */
class NucmaticDamageTypes {
    companion object {
        val RADIATION_POISONING_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE,
            Nucmatic.mkId("radiation_poisoning"))
    }
}