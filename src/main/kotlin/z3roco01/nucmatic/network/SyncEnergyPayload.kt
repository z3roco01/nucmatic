package z3roco01.nucmatic.network

import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.network.packet.CustomPayload
import net.minecraft.network.packet.CustomPayload.Id
import net.minecraft.util.math.BlockPos
import z3roco01.nucmatic.Nucmatic

/**
 * a payload, sent from server to client when the energy of a block needs to be synced
 * @since 07/10/2024
 */
data class SyncEnergyPayload(val amount: Long, val pos: BlockPos) : CustomPayload {
    companion object {
        // id used for this payload
        val ID = Id<SyncEnergyPayload>(Nucmatic.mkId("sync_energy"))
        // codec for the payload, used to get the data from it
        val PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_LONG, SyncEnergyPayload::amount,
            BlockPos.PACKET_CODEC, SyncEnergyPayload::pos,
            ::SyncEnergyPayload)
    }

    // required override, returns the payloads id
    override fun getId(): Id<out CustomPayload> = ID
}