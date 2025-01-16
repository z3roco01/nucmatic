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
        Nucmatic.LOGGER.info("")
        // the axis that the front and back walls are built on
        val frontBackAxis= findDirection(world, pos)
        // axis that the left and right are on
        val leftRightAxis = if(frontBackAxis == Axis.X) Axis.Z else Axis.X

        // measures the lengths beside the reactor like this
        // X---XRX---X
        val frontWallExtends = findWallExtends(world, pos, frontBackAxis, true)
        // measures one of the side walls ( reactors must be square )
        val secondExtends = findWallExtends(world, pos.add(frontBackAxis.vector.multiply(frontWallExtends.positive)), leftRightAxis, false)
        // measures the height ( reactors must be the same height all around )
        val height = findWallExtends(world, pos, Axis.Y, false)

        var valid = true

        frontWallExtends.positive++

        Nucmatic.LOGGER.info(checkWall(world, pos.subtract(frontBackAxis.vector.multiply(frontWallExtends.negative)),
            frontWallExtends, frontBackAxis, height.positive).toString())
    }

    /**
     * method that checks if a wall is made of reactor blocks
     * @param world the [World] this is being run in
     * @param pos the [BlockPos] to start checking from
     * @param extend the [Extend] holding the wall size
     * @param axis the [Axis] this wall is on
     * @param height the height of the wall
     * @return true if the wall is all reactor blocks, false otherwise
     */
    private fun checkWall(world: World, pos: BlockPos, extend: Extend, axis: Axis, height: Int): Boolean {
        // variable holding if the wall is all reactors
        var valid = false

        // loop over each y value
        for(y in 0..<height) {
            // and each block in that y
            for(v in 0..<extend.total()) {
                // if its on the x use the v as x
                if(axis == Axis.X)
                    valid = isInAddedPos(world, pos, v, y, 0, NucmaticBlockTags.REACTOR_CASING)
                else if (axis == Axis.Z) // and if its on z use the v as z
                    valid = isInAddedPos(world, pos, 0, y, v, NucmaticBlockTags.REACTOR_CASING)
                else // should not happen, but if it does make the wall invalid
                    valid = false
            }
        }

        return valid
    }

    /**
     * method that finds how much a wall extends out on an axis from the starting position
     * @param world the [World] this is running in
     * @param pos the starting [BlockPos]
     * @param axis the [Axis] to traverse
     * @param subtract should 1 be subtracted from each element ( if we are starting in the middle
     * @return an [Extend] holding how many blocks it extends
     */
    private fun findWallExtends(world: World, pos: BlockPos, axis: Axis, subtract: Boolean): Extend {
        // should start from a block that has the reactor casing tag or it wont work
        var state = world.getBlockState(pos)
        var curPos = pos

        // option for -1 since it does count the starting block
        var extend = if(subtract) Extend(-1, -1, axis) else Extend(axis)

        // check in the positive direction for matching blocks
        while(state.isIn(NucmaticBlockTags.REACTOR_CASING)) {
            extend.positive++
            curPos = curPos.add(axis.vector)
            state = world.getBlockState(curPos)
        }

        // reset the loop vars
        state = world.getBlockState(pos)
        curPos = pos

        // now check in the negative direction
        while(state.isIn(NucmaticBlockTags.REACTOR_CASING)) {
            extend.negative++
            curPos = curPos.subtract(axis.vector)
            state = world.getBlockState(curPos)
        }

        return extend
    }

    /**
     * finds which direction the reactor is built in, since it could be built any way
     * @param world [World] that this is running in
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

    /**
     * a class to hold an extend, used in multiblock detection
     * @param negative how many blocks does it extend in the negative
     * @param positive how many block does it extend in the positive
     * @param axis a [Axis], what axis are we extending along
     */
    private class Extend(var negative: Int, var positive: Int, var axis: Axis) {
        constructor(axis: Axis) : this(0, 0, axis)
        constructor(): this(0, 0, Axis.NONE)

        /**
         * sums up [positive] and [negative] then returns it
         * @return returns [positive] plus [negative]
         */
        fun total() = positive + negative

        override fun toString() = "$axis-$negative $axis$positive"
    }
}