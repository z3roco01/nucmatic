package z3roco01.nucmatic.block

import com.mojang.serialization.MapCodec
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import z3roco01.nucmatic.block.entity.EnergyContainer
import z3roco01.nucmatic.block.entity.NucmaticBlockEntityTypes
import z3roco01.nucmatic.block.entity.ReactorControllerBlockEntity

class ReactorControllerBlock: BlockWithEntity(Settings.create()) {
    // creates the block entity, which handles all ticking, etc logic
    override fun createBlockEntity(pos: BlockPos, state: BlockState) = ReactorControllerBlockEntity(pos, state)

    // returns a codec for this block
    override fun getCodec(): MapCodec<out BlockWithEntity> = createCodec{ReactorControllerBlock()}

    // getRenderType returns INVISIBLE im BlockWithEntity, so it needs to be overriden
    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    // returns the ticker for this block entity, so it can run code on each game tick
    override fun <T : BlockEntity?> getTicker(world: World, state: BlockState, type: BlockEntityType<T>)
            = validateTicker(type, NucmaticBlockEntityTypes.REACTOR_CONTROLLER_TYPE, EnergyContainer::staticTick)

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hit: BlockHitResult):
            ActionResult {
        val entity = world.getBlockEntity(pos)

        if(entity is ReactorControllerBlockEntity)
            entity.check(world, pos)

        return ActionResult.PASS
    }
}