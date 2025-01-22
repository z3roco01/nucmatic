package z3roco01.nucmatic.entity

import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import z3roco01.nucmatic.Nucmatic

/**
 * holds all the entity types
 * @since 01/22/2025
 */
object NucmaticEntities {
    // create the entity type
    val NUCLEAR_TNT_ENTITY = EntityType.Builder.create(::NuclearTntEntity, SpawnGroup.MISC).dimensions(1f, 1f).build()

    /**
     * register all the entity types
     */
    fun register() {
        Registry.register(Registries.ENTITY_TYPE, Nucmatic.mkId("nuclear_tnt"), NUCLEAR_TNT_ENTITY)
    }
}