package z3roco01.nucmatic

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

/**
 * the common entry point ran on both the client and server
 * @since 2024/09/26
 */
object Nucmatic : ModInitializer {
	val MOD_ID = "nucmatic"
    val LOGGER = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {


		LOGGER.info("init finished !!")
	}
}