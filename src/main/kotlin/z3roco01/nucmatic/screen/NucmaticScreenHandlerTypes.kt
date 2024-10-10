package z3roco01.nucmatic.screen

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.screen.ScreenHandlerType
import z3roco01.nucmatic.Nucmatic
import z3roco01.pragmatica.screen.EnergyScreenHandler

/**
 * handles the registration of the screen handler types
 * @since 10/10/2024
 */
object NucmaticScreenHandlerTypes {
    val NUCLEAR_GENERATOR_TYPE = ExtendedScreenHandlerType(::NuclearGeneratorScreenHandler, EnergyScreenHandler.EnergyContainerScreenData.PACKET_CODEC)

    /**
     * registers all the types
     */
    fun register() {

    }

    /**
     * helper fun for registration
     */
    private fun register(path: String, type: ScreenHandlerType<*>) = Registry.register(Registries.SCREEN_HANDLER,
        Nucmatic.mkId(path))
}