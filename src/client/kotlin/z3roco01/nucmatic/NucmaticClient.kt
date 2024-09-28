package z3roco01.nucmatic

import net.fabricmc.api.ClientModInitializer
import org.slf4j.LoggerFactory

/**
 * The client side entry point for this mod
 * @since 26/09/2024
 */
object NucmaticClient : ClientModInitializer {
	// client version of the logger, just for better debugging
	val LOGGER = LoggerFactory.getLogger(Nucmatic.MOD_ID + "_client")

	override fun onInitializeClient() {


		LOGGER.info("client init finished !!")
	}
}