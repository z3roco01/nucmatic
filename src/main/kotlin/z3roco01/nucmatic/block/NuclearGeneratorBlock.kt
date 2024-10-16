package z3roco01.nucmatic.block

import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import z3roco01.nucmatic.block.entity.NuclearGeneratorBlockEntity

/**
 * class holding the logic for the nuclear generator block
 * @since 07/10/2024
 */
class NuclearGeneratorBlock: Block(Settings.create()), BlockEntityProvider {
    // creates the block entity, which handles all ticking, etc logic
    override fun createBlockEntity(pos: BlockPos, state: BlockState) = NuclearGeneratorBlockEntity(pos, state)

    // called when the player right clicks on this block, will open the screen
    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hit: BlockHitResult): ActionResult? {
        // do not run on the client
        if(world.isClient) return ActionResult.PASS

        // get the factory for the screen handler from the block entity
        val screenHandlerFactory = state.createScreenHandlerFactory(world, pos)

        if (screenHandlerFactory != null) {
            // if it has a screen handler factory ( it should ) then open the screen for the player
            player.openHandledScreen(screenHandlerFactory)
        }

        // the action succeeded so return that
        return ActionResult.SUCCESS
    }
}