package z3roco01.nucmatic.block.entity

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

/**
 * the block entity for the nuclear generator controller
 * @since 07/10/2024
 */
class ReactorControllerBlockEntity(pos: BlockPos, state: BlockState):
    EnergyContainer(NucmaticBlockEntityTypes.REACTOR_CONTROLLER_TYPE, pos, state) {
    override fun getEnergyCapacity() = 256000L

    // sets the max insert value for energy to 0, you cannot insert into this
    override fun getMaxIns() = 0L

    // sets the max extract value for the stored energy
    override fun getMaxExt() = 1000L

    init {
        // loop over every direction ...
        for(i in Direction.entries) {
            // and set its io permission to extract only
            sideIOMap[i] = IOPermission.EXTRACT
        }
    }

    // runs code every tick
    override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: EnergyContainer) {
        super.tick(world, pos, state, blockEntity)

        // dont run this logic on the client
        if(world.isClient) return

    }
}