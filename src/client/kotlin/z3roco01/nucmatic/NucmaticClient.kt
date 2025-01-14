package z3roco01.nucmatic

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.client.render.RenderLayer
import org.slf4j.LoggerFactory
import z3roco01.nucmatic.block.NucmaticBlocks
import z3roco01.nucmatic.network.NucmaticClientPayloads
import z3roco01.nucmatic.render.NucmaticColourProviders
import z3roco01.nucmatic.screen.NucmaticScreens

/**
 * The client side entry point for this mod
 * @since 26/09/2024
 */
object NucmaticClient : ClientModInitializer {
	// client version of the logger, just for better debugging
	val LOGGER = LoggerFactory.getLogger(Nucmatic.MOD_ID + "_client")

	override fun onInitializeClient() {
		NucmaticClientPayloads.register()
		NucmaticScreens.register()
		NucmaticColourProviders.register()

		BlockRenderLayerMap.INSTANCE.putBlock(NucmaticBlocks.REACTOR_GLASS, RenderLayer.getTranslucent())

		LOGGER.info("client init finished !!")
	}
}