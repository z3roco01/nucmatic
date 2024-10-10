package z3roco01.pragmatica.screen

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.math.BlockPos
import z3roco01.nucmatic.block.entity.EnergyContainer

abstract class EnergyScreenHandler(type: ScreenHandlerType<*>, syncId: Int, playerInv: PlayerInventory, val data: EnergyContainerScreenData): ScreenHandler(type, syncId) {
    val blockEntity: EnergyContainer = playerInv.player.world.getBlockEntity(data.pos) as EnergyContainer

    // handles the qucik moving ( shift + click ) of items into this blocks inventory
    override fun quickMove(player: PlayerEntity, slot: Int): ItemStack {
        var itemStack = ItemStack.EMPTY
        val slot2 = slots[slot]
        if (slot2.hasStack()) {
            val itemStack2 = slot2.stack
            itemStack = itemStack2.copy()
            if (if (slot < 27) !this.insertItem(itemStack2, 27, slots.size, true) else !this.insertItem(
                    itemStack2,
                    0,
                    27,
                    false
                )
            ) {
                return ItemStack.EMPTY
            }
            if (itemStack2.isEmpty) {
                slot2.stack = ItemStack.EMPTY
            } else {
                slot2.markDirty()
            }
        }
        return itemStack!!
    }

    // returns if a player can open this inventory
    override fun canUse(player: PlayerEntity) = true

    /**
     * holds the data passed to this screen handler from the block
     * @param amount the amount of stored energy
     * @param capacity the capacity of the block
     * @param pos the position of the block
     */
    data class EnergyContainerScreenData(val amount: Long, val capacity: Long, val pos: BlockPos) {
        companion object {
            // the codec for this data, used when its sent in a packet
            val PACKET_CODEC: PacketCodec<RegistryByteBuf, EnergyContainerScreenData> = PacketCodec.tuple(
                PacketCodecs.VAR_LONG, EnergyContainerScreenData::amount,
                PacketCodecs.VAR_LONG, EnergyContainerScreenData::capacity,
                BlockPos.PACKET_CODEC, EnergyContainerScreenData::pos,
            ) { amount: Long, capacity: Long, pos: BlockPos -> EnergyContainerScreenData(amount, capacity, pos) }
        }
    }
}