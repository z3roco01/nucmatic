package z3roco01.nucmatic.render.entity

import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.TntEntityRenderer
import net.minecraft.client.render.entity.model.EntityModelLayer
import net.minecraft.entity.TntEntity
import z3roco01.nucmatic.Nucmatic

/**
 * handles the rendering of [z3roco01.nucmatic.entity.NuclearTntEntity], mostly from [TntEntityRenderer]
 * @since 22/01/2025
 */
class NuclearTntEntityRenderer(context: EntityRendererFactory.Context): TntEntityRenderer(context) {
    override fun getTexture(tntEntity: TntEntity) = Nucmatic.mkId("textures/entity/nuclear_tnt/nuclear_tnt.png")

    companion object {
        // the render layer for the entity
        val RENDER_LAYER = EntityModelLayer(Nucmatic.mkId("nuclear_tnt"), "main")
    }
}