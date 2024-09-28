package z3roco01.nucmatic

import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory
import z3roco01.nucmatic.entity.effect.NucmaticEffects
import z3roco01.nucmatic.item.NucmaticItems

/**
 * the common entry point ran on both the client and server
 * @since 26/09/2024
 */
object Nucmatic : ModInitializer {
	val MOD_ID = "nucmatic"
    val LOGGER = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		// register all the things
		NucmaticEffects.register()
		NucmaticItems.register()

		LOGGER.info("init finished !!")
	}

	/**
	 * makes an [Identifier] in the [MOD_ID] namespace
	 * @param name the name of the id
	 * @return the fully made [Identifier]
	 */
	fun mkId(name: String) = Identifier.of(MOD_ID, name)
}