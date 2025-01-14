package z3roco01.nucmatic.block.entity

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import z3roco01.nucmatic.Nucmatic
import z3roco01.nucmatic.tag.NucmaticBlockTags

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

        val asd = findDirection(world, pos)
        if(asd != Axis.NONE)
            Nucmatic.LOGGER.info(asd.toString())
    }

    /**
     * finds which direction the reactor is built in, since it could be built any way
     * @param world [World] that this is runing in
     * @param pos the [BlockPos] that this is starting from
     * @return the [Axis] the front wall is on, or [Axis.NONE] if it is not built
     */
    private fun findDirection(world: World, pos: BlockPos): Axis {
        val xPlus = isInAddedPos(world, pos, 1, 0, 0, NucmaticBlockTags.REACTOR_CASING)
        val xMinus = isInAddedPos(world, pos, -1, 0, 0, NucmaticBlockTags.REACTOR_CASING)
        val zPlus = isInAddedPos(world, pos, 0, 0, 1, NucmaticBlockTags.REACTOR_CASING)
        val zMinus = isInAddedPos(world, pos, 0, 0, -1, NucmaticBlockTags.REACTOR_CASING)

        if(xPlus != null && xMinus != null && xPlus && xMinus)
            // if true the front wall is built on the x axis
            return Axis.X_AXIS
        else if(zPlus != null && zMinus != null && zPlus && zMinus)
            // if true the front wall is built on the z axis
            return Axis.Z_AXIS

        return Axis.NONE
    }

    /**
     * first runs [getBlockEntAdd] with [pos] and [x] [y] [z], then returns if it is in [tag]
     * @param world the [World] this is run in
     * @param pos the [BlockPos] we are adding to
     * @param x the value to be added to the x component
     * @param y the value to be added to the y component
     * @param z the value to be added to the z component
     * @param tag the tag we are checking against
     */
    private fun isInAddedPos(world: World, pos: BlockPos, x: Int, y: Int, z: Int, tag: TagKey<Block>) =
        getBlockEntAdd(world, pos, x, y, z)?.cachedState?.isIn(tag)

    /**
     * adds the supplied ints to the pos, then gets the block entity at that position
     * @param world the [World] this is run in
     * @param pos the [BlockPos] we are adding to
     * @param x the value to be added to the x component
     * @param y the value to be added to the y component
     * @param z the value to be added to the z component
     */
    private fun getBlockEntAdd(world: World, pos: BlockPos, x: Int, y: Int, z: Int) =
        world.getBlockEntity(pos.add(x, y, z))

    /**
     * a class used for returns, used to denote an axis in world
     */
    private enum class Axis {
        X_AXIS,
        Y_AXIS,
        Z_AXIS,
        NONE
    }
}