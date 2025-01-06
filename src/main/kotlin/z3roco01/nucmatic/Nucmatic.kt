package z3roco01.nucmatic

import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory
import z3roco01.nucmatic.block.NucmaticBlocks
import z3roco01.nucmatic.block.entity.NucmaticBlockEntityTypes
import z3roco01.nucmatic.entity.effect.NucmaticEffects
import z3roco01.nucmatic.item.NucmaticItems
import z3roco01.nucmatic.network.NucmaticServerPayloads
import z3roco01.nucmatic.screen.NucmaticScreenHandlerTypes

/**
 * the common entry point ran on both the client and server
 * @since 26/09/2024
 */
object Nucmatic : ModInitializer {
	const val MOD_ID = "nucmatic"
    val LOGGER = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		// register all the things
		NucmaticScreenHandlerTypes.register()
		NucmaticBlocks.register()
		NucmaticBlockEntityTypes.register()
		NucmaticEffects.register()
		NucmaticItems.register()
		NucmaticServerPayloads.register()

		LOGGER.info("init finished !!")
	}

	/**
	 * makes an [Identifier] in the [MOD_ID] namespace
	 * @param path the path of the id
	 * @return the fully made [Identifier]
	 */
	fun mkId(path: String) = Identifier.of(MOD_ID, path)
}