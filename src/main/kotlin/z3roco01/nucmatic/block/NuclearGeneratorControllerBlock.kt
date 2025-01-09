package z3roco01.nucmatic.block

import com.mojang.serialization.MapCodec
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.util.math.BlockPos
import z3roco01.nucmatic.block.entity.NuclearGeneratorControllerBlockEntity

class NuclearGeneratorControllerBlock: BlockWithEntity(Settings.create()) {
    // creates the block entity, which handles all ticking, etc logic
    override fun createBlockEntity(pos: BlockPos, state: BlockState) = NuclearGeneratorControllerBlockEntity(pos, state)

    // returns a codec for this block
    override fun getCodec(): MapCodec<out BlockWithEntity> = createCodec{NuclearGeneratorBlock()}

    // getRenderType returns INVISIBLE im BlockWithEntity, so it needs to be overriden
    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL
}