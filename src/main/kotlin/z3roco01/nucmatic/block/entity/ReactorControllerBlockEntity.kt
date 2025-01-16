package z3roco01.nucmatic.block.entity

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3i
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
    }

    /**
     * testing method, runs the multiblock structure checking code
     */
    fun check(world: World, pos: BlockPos) {
        // the axis that the front wall is built on
        val firstAxis= findDirection(world, pos)
        val otherAxis = if(firstAxis == Axis.X) Axis.Z else Axis.X
        val extends = findWallExtends(world, pos, firstAxis, true)
        val secondExtends = findWallExtends(world, pos.add(firstAxis.vector.multiply(extends[0])), otherAxis, false)
        val height = findWallExtends(world, pos, Axis.Y, false)

    }

    /**
     * method that finds how much a wall extends out on an axis from the starting position
     * @param world the [World] this is running in
     * @param pos the starting [BlockPos]
     * @param axis the [Axis] to traverse
     * @param subtract should 1 be subtracted from each element ( if we are starting in the middle
     * @return an int array, the first element is the positive axis, the second is the negative
     */
    private fun findWallExtends(world: World, pos: BlockPos, axis: Axis, subtract: Boolean): IntArray {
        // should start from a block that has the reactor casing tag or it wont work
        var state = world.getBlockState(pos)
        var curPos = pos

        // first element is in the positive, second in the negative
        // -1 since it does count the reactor block
        var extends = if(subtract) intArrayOf(-1, -1) else intArrayOf(0, 0)

        while(state.isIn(NucmaticBlockTags.REACTOR_CASING)) {
            extends[0]++
            curPos = curPos.add(axis.vector)
            state = world.getBlockState(curPos)
        }

        state = world.getBlockState(pos)
        curPos = pos

        while(state.isIn(NucmaticBlockTags.REACTOR_CASING)) {
            extends[1]++
            curPos = curPos.subtract(axis.vector)
            state = world.getBlockState(curPos)
        }

        Nucmatic.LOGGER.info("")
        Nucmatic.LOGGER.info(extends[0].toString())
        Nucmatic.LOGGER.info(extends[1].toString())

        return extends
    }

    /**
     * finds which direction the reactor is built in, since it could be built any way
     * @param world [World] that this is runing in
     * @param pos the [BlockPos] that this is starting from
     * @return the [Axis] the front wall is on, or [Axis.NONE] if it is not built
     */
    private fun findDirection(world: World, pos: BlockPos): Axis {
        if(isInAddedPos(world, pos, 1, 0, 0, NucmaticBlockTags.REACTOR_CASING) &&
            isInAddedPos(world, pos, -1, 0, 0, NucmaticBlockTags.REACTOR_CASING))
            // if true the front wall is built on the x axis
            return Axis.X
        else if(isInAddedPos(world, pos, 0, 0, 1, NucmaticBlockTags.REACTOR_CASING) &&
            isInAddedPos(world, pos, 0, 0, -1, NucmaticBlockTags.REACTOR_CASING))
            // if true the front wall is built on the z axis
            return Axis.Z

        return Axis.NONE
    }

    /**
     * first runs [getBlockStateAdd] with [pos] and [x] [y] [z], then returns if it is in [tag]
     * @param world the [World] this is run in
     * @param pos the [BlockPos] we are adding to
     * @param x the value to be added to the x component
     * @param y the value to be added to the y component
     * @param z the value to be added to the z component
     * @param tag the tag we are checking against
     */
    private fun isInAddedPos(world: World, pos: BlockPos, x: Int, y: Int, z: Int, tag: TagKey<Block>) =
        getBlockStateAdd(world, pos, x, y, z).isIn(tag)

    /**
     * adds the supplied ints to the pos, then gets the block state at that position
     * @param world the [World] this is run in
     * @param pos the [BlockPos] we are adding to
     * @param x the value to be added to the x component
     * @param y the value to be added to the y component
     * @param z the value to be added to the z component
     * @return the [BlockState] at the newly added position
     */
    private fun getBlockStateAdd(world: World, pos: BlockPos, x: Int, y: Int, z: Int) =
        world.getBlockState(pos.add(x, y, z))

    /**
     * a class used for returns, used to denote an axis in world
     * @param vector the vector for this axis to be used in math
     */
    private enum class Axis(val vector: Vec3i) {
        X(Vec3i(1, 0, 0)),
        Y(Vec3i(0, 1, 0)),
        Z(Vec3i(0, 0, 1)),
        NONE(Vec3i.ZERO)
    }
}