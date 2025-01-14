package z3roco01.nucmatic.tag

import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import z3roco01.nucmatic.Nucmatic

/**
 * this class contains all the block tags from this mod
 * @since 14/01/2025
 */
object NucmaticBlockTags {
    // this tag contains every block that counts as a reactor casing block
    val REACTOR_CASING = TagKey.of(RegistryKeys.BLOCK, Nucmatic.mkId("reactor_casing"))
    // this tag contains every block that counts as a fuel rod
    val FUEL_RODS = TagKey.of(RegistryKeys.BLOCK, Nucmatic.mkId("fuel_rods"))
}