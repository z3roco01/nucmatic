package z3roco01.nucmatic.screen

import net.minecraft.client.gui.screen.ingame.HandledScreens

/**
 * handles the registration of the client sided screens
 * @since 15/10/2024
 */
object NucmaticScreens {
    /**
     * registers all the screens
     */
    fun register() {
        HandledScreens.register(NucmaticScreenHandlerTypes.NUCLEAR_GENERATOR_TYPE, ::NuclearGeneratorScreen)
    }
}