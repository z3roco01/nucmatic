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
        // colour provider for the fuel items, makes the fuel darker the more depleted it is
        ColorProviderRegistry.ITEM.register({stack, tintIndex ->
            val damage = stack.damage
            // formula that takes the damage, maps it to the range of numbers in a byte, then subtracts that from the ubyte max
            // so fuel with no damage will be at full brightness, hafl brightness at half damage, and no brightness at full damage
            val tint = (UByte.MAX_VALUE - Util.map(damage, (0..stack.maxDamage), (0..UByte.MAX_VALUE.toInt())).toUByte()).toLong()
            // need to go unit to int bc it wont let you do the full 4 bytes
            (0xFF000000 or (tint) or (tint shl 8) or (tint shl 16)).toUInt().toInt()
        }, NucmaticItems.NU_FUEL, NucmaticItems.LEU_FUEL, NucmaticItems.HEU_FUEL)
    }
}