package z3roco01.nucmatic.block

import com.mojang.serialization.MapCodec
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.screen.ScreenHandler
import net.minecraft.util.ActionResult
import net.minecraft.util.ItemScatterer
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import z3roco01.nucmatic.block.entity.EnergyContainer
import z3roco01.nucmatic.block.entity.NuclearGeneratorBlockEntity
import z3roco01.nucmatic.block.entity.NucmaticBlockEntityTypes

/**
 * class holding the logic for the nuclear generator block
 * @since 07/10/2024
 */
class NuclearGeneratorBlock: BlockWithEntity(Settings.create()) {
    // creates the block entity, which handles all ticking, etc logic
    override fun createBlockEntity(pos: BlockPos, state: BlockState) = NuclearGeneratorBlockEntity(pos, state)

    // returns a codec for this block
    override fun getCodec(): MapCodec<out BlockWithEntity> = createCodec{NuclearGeneratorBlock()}

    // getRenderType returns INVISIBLE im BlockWithEntity, so it needs to be overriden
    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        // if this block has been replaced with a different type of block
        if (state.block != newState.block) {
            // get the block entity
            val blockEntity = world.getBlockEntity(pos)
            // ensure the block entity is the correct type
            if (blockEntity is NuclearGeneratorBlockEntity) {
                // scatter the items on the ground
                ItemScatterer.spawn(world, pos, blockEntity)
                // and update comparators
                world.updateComparators(pos, this)
            }
            super.onStateReplaced(state, world, pos, newState, moved)
        }
    }

    override fun <T : BlockEntity?> getTicker(world: World, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>? {
        return validateTicker(type, NucmaticBlockEntityTypes.NUCLEAR_GENERATOR_TYPE, EnergyContainer::staticTick)
    }

    // called when the player right clicks on this block, will open the screen
    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hit: BlockHitResult): ActionResult {
        // do not run on the client
        if(world.isClient) return ActionResult.PASS

        val blockEntity = world.getBlockEntity(pos)

        // get the factory for the screen handler from the block entity
        if(blockEntity !is ExtendedScreenHandlerFactory<*>) return ActionResult.PASS

        val screenHandlerFactory = (blockEntity as ExtendedScreenHandlerFactory<*>)
        player.openHandledScreen(screenHandlerFactory)

        // the action succeeded so return that
        return ActionResult.SUCCESS
    }

    override fun hasComparatorOutput(state: BlockState) = true

    // calculate the comparator output with a helper function
    override fun getComparatorOutput(state: BlockState, world: World, pos: BlockPos) =
        ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos))
}