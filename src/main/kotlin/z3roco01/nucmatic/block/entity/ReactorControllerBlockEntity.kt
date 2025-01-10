package z3roco01.nucmatic.block.entity

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos

/**
 * the block entity for the nuclear generator controller
 * @since 07/10/2024
 */
class ReactorControllerBlockEntity(pos: BlockPos, state: BlockState):
    EnergyContainer(NucmaticBlockEntityTypes.REACTOR_CONTROLLER_TYPE, pos, state) {
    override fun getEnergyCapacity() = 1024000L

    override fun getMaxIns() = 4000L

    override fun getMaxExt() = 1000L
}