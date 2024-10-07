package z3roco01.nucmatic.block

import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import z3roco01.nucmatic.block.entity.NuclearGeneratorBlockEntity

/**
 * class holding the logic for the nuclear generator block
 * @since 07/10/2024
 */
class NuclearGeneratorBlock: Block(Settings.create()), BlockEntityProvider {
    // creates the block entity, which handles all ticking, etc logic
    override fun createBlockEntity(pos: BlockPos, state: BlockState) = NuclearGeneratorBlockEntity(pos, state)
}