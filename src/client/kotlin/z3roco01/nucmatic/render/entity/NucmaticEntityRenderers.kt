package z3roco01.nucmatic.render.entity

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import z3roco01.nucmatic.entity.NucmaticEntities

/**
 * handles the registration of all the entity renderers
 * @since 22/01/2025
 */
object NucmaticEntityRenderers {
    /**
     * registers all the entity renderers
     */
    fun register() {
        EntityRendererRegistry.register(NucmaticEntities.NUCLEAR_TNT_ENTITY) { context ->
            NuclearTntEntityRenderer(context)
        }

    }
}