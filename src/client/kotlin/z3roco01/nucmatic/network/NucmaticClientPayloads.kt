package z3roco01.nucmatic.network

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import z3roco01.nucmatic.block.entity.EnergyContainer

/**
 * handles the registration of sever to client receivers
 * @since 10/10/2024
 */
object NucmaticClientPayloads {
    fun register() {
        // register receiver for energy sync packet
        ClientPlayNetworking.registerGlobalReceiver(SyncEnergyPayload.ID) { payload, context ->
            // get the stored energy from the packet
            val energy = payload.amount
            // get the position of the block
            val pos = payload.pos
            // get the world from the context
            val world = context.client().world

            // if there is world then return ( it should be there )
            if (world == null) return@registerGlobalReceiver

            // get the block entity at the position
            val blockEntity = world.getBlockEntity(pos)

            // if the block entity is an EnergyContainer ( should always be )
            if (blockEntity is EnergyContainer)
                blockEntity.energyStorage.amount = energy // then set the stored energy ( it should be between 0 and max)
        }
    }
}