package z3roco01.nucmatic.block.entity

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3i
import net.minecraft.world.World
import z3roco01.nucmatic.Nucmatic
import z3roco01.nucmatic.block.entity.ReactorControllerBlockEntity.Axis
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
        for(i in net.minecraft.util.math.Direction.entries) {
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
        val sideWallAxis = if(frontBackAxis == Axis.X) Axis.Z else Axis.X

        // measures the lengths beside the reactor like this
        // X---XRX---X
        val frontWallExtends = findWallExtends(world, pos, frontBackAxis, true)
        // measures one of the side walls ( reactors must be square )
        val sideWallExtends = findWallExtends(world, pos.add(frontBackAxis.vector.multiply(frontWallExtends.positive)), sideWallAxis, false)
        // measures the height ( reactors must be the same height all around )
        val height = findWallExtends(world, pos, Axis.Y, false)

        var valid = true
        // subtracts half of the front walls length, on the correct axis so that we start from one side
        val frontWallStart = pos.subtract(frontBackAxis.vector.multiply(frontWallExtends.negative))

        // since it doesnt count the reactor we need to add one ( would add 2 if we counted the reactor in the counting method )
        frontWallExtends.positive++
        // check the front wall
        valid = checkWall(world, frontWallStart, frontWallExtends.total(), height.positive, frontBackAxis,
        Direction.POS)
        // check the wall closer to the negative side of the front wall
        valid = valid && checkWall(world, frontWallStart, height.positive, sideWallExtends)
        // check the other sidewall, which is spaced one front wall apart from the other wall
        valid = valid && checkWall(world, frontWallStart.add(frontBackAxis.vector.multiply(frontWallExtends.total()-1)),
            sideWallExtends.max(), height.positive, sideWallAxis, sideWallExtends.maxDir())
        // finally check the back wall, which is behind the front wall by one side wall-1
        valid = valid && checkWall(world, frontWallStart.subtract(sideWallAxis.vector.multiply(sideWallExtends.max()-1)),
            frontWallExtends.total(), height.positive, frontBackAxis, Direction.POS)

        Nucmatic.LOGGER.info(valid.toString())

        // the start of the floor/roof first goes down by a block, then in on both axis in the right direction
        val floorStart = frontWallStart.add(0, -1, 0).add(sideWallAxis.vector
            .multiply(sideWallExtends.maxDir().mul)).add(frontBackAxis.vector.multiply(Direction.POS.mul))

        // check the floor
        valid = valid && checkFloor(world, floorStart, frontWallExtends.total(), sideWallExtends.max(),
            frontBackAxis, Direction.POS, sideWallExtends.maxDir())
        Nucmatic.LOGGER.info(valid.toString())
        Nucmatic.LOGGER.info(sideWallAxis.vector.multiply(sideWallExtends.maxDir().mul).toString())
        Nucmatic.LOGGER.info(floorStart.toString())
    }

    /**
     * simpler version of [checkWall], doesnt take in direction, height or axis, and instead takes in an [Extend]
     */
    private fun checkWall(world: World, pos: BlockPos, height: Int, extend: Extend) =
        checkWall(world, pos, extend.max(), height, extend.axis, extend.maxDir())

    /**
     * method that checks if a wall is made of reactor blocks
     * @param world the [World] this is being run in
     * @param pos the [BlockPos] to start checking from
     * @param length the length of the wall to check
     * @param axis the [Axis] this wall is on
     * @param height the height of the wall
     * @param direction the [Direction] the wall is going in
     * @return true if the wall is all reactor blocks, false otherwise
     */
    private fun checkWall(world: World, pos: BlockPos, length: Int, height: Int, axis: Axis,
                          direction: Direction): Boolean {
        // variable holding if the wall is all reactors
        var valid = false
        val xRange = direction.createRange(0, length)

        // loop over each y value
        for(y in 0..<height) {
            // and each block in that y
            for(v in xRange) {
                // if its on the x use the v as x
                valid = if(axis == Axis.X)
                    isInAddedPos(world, pos, v, y, 0, NucmaticBlockTags.REACTOR_CASING)
                else if (axis == Axis.Z) // and if its on z use the v as z
                    isInAddedPos(world, pos, 0, y, v, NucmaticBlockTags.REACTOR_CASING)
                else // should not happen, but if it does make the wall invalid
                    false

                // return early if valid is false, as to not waste resources
                if(valid == false) return valid
            }
        }

        return valid
    }

    /**
     * checks a floor ( or roof ) for being made only of reactor blocks
     * @param world the [World] this is being run in
     * @param pos the [BlockPos] to start from
     * @param length the length of the floor
     * @param width the width of the floor
     * @param lengthAxis the [Axis] the length is on, the width is put on the other axis ( either x or y )
     * @param lengthDir the [Direction] the length goes in
     * @param widthDir the [Direction] the width goes in
     *
     * @return returns true if it is made correctly, otherwise returns false
     */
    private fun checkFloor(world: World, pos: BlockPos, length: Int, width: Int, lengthAxis: Axis,
                           lengthDir: Direction, widthDir: Direction): Boolean {
        // assume valid till a improper block is found
        var valid = true
        // create the ranges to loop over for length and width
        val lenRange = lengthDir.createRange(0, length)
        val widRange = widthDir.createRange(0, width)

        for(len in lenRange) {
            for(wid in widRange) {

                valid = if(lengthAxis == Axis.X) {
                    Nucmatic.LOGGER.info(pos.add(len, 0, wid).toString())
                    isInAddedPos(world, pos, len, 0, wid, NucmaticBlockTags.REACTOR_CASING)
                }else if(lengthAxis == Axis.Z) {
                    Nucmatic.LOGGER.info(pos.add(wid, 0, len).toString())
                    isInAddedPos(world, pos, wid, 0, len, NucmaticBlockTags.REACTOR_CASING)
                }else // should not happen, if it does then make it invalid
                    false

                // if valid is false, return early
                if(valid == false) return valid
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
     * a class used for passing positions, stores if it is in the negative or positive
     * @param mul used to multiply number so it will be negative or positive
     */
    private enum class Direction(val mul: Int) {
        POS(1),
        NEG(-1);

        /**
         * creates an [IntRange] for 2 values that starts at the proper number to go in its direction
         * @param min the min value of the range
         * @param max the max value of the range
         */
        fun createRange(min: Int, max: Int) = if(this == Direction.POS) (min..<max)
        else (-(max-1)..min)
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

        /**
         * returns the largest of positive and negative
         * @return either positive or negative, whichever is larger
         */
        fun max() = kotlin.math.max(positive, negative)

        /**
         * returns which direction the [max] value is in
         * @return the [Direction] of the value returned from max
         */
        fun maxDir(): Direction {
            if(max() == positive) return Direction.POS
            else return Direction.NEG
        }

        override fun toString() = "$axis-$negative $axis$positive"
    }
}