package z3roco01.nucmatic.network

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry

/**
 * handles the registrations of all server to client payloads
 * @since 10/10/2024
 */
object NucmaticServerPayloads {
    /**
     * registers all the c2s payloads
     */
    fun register() {
        PayloadTypeRegistry.playS2C().register(SyncEnergyPayload.ID, SyncEnergyPayload.PACKET_CODEC)
    }
}