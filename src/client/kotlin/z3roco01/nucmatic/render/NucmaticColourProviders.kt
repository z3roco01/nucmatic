package z3roco01.nucmatic.render

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import z3roco01.nucmatic.Util
import z3roco01.nucmatic.item.NucmaticItems

/**
 * registers all the colour providers with [net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry]
 * @since 07/01/2025
 */
object NucmaticColourProviders {
    fun register() {
        //
        ColorProviderRegistry.ITEM.register({stack, tintIndex ->
            val damage = stack.damage
            0xFFFFFFF
        }, NucmaticItems.NU_FUEL, NucmaticItems.LEU_FUEL, NucmaticItems.HEU_FUEL)
    }
}