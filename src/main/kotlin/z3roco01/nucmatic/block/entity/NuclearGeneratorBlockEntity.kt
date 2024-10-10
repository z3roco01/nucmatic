package z3roco01.nucmatic.block.entity

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos

/**
 * the block entity for the nuclear generator
 * @since 07/10/2024
 */
class NuclearGeneratorBlockEntity(pos: BlockPos, state: BlockState):
    EnergyContainer(NucmaticBlockEntityTypes.NUCLEAR_GENERATOR_TYPE, pos, state) {
    override fun getEnergyCapacity() = 32000L

    override fun getMaxIns() = 2000L

    override fun getMaxExt() = 2000L
}