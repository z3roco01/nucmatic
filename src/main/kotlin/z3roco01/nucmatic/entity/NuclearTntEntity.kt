package z3roco01.nucmatic.entity

import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.MovementType
import net.minecraft.entity.TntEntity
import net.minecraft.particle.ParticleTypes
import net.minecraft.world.World
import z3roco01.nucmatic.mixin.TntEntityAccessor
import kotlin.math.cos
import kotlin.math.sin

/**
 * the entity that is spawned when a [z3roco01.nucmatic.block.NuclearTntBlock] is lit
 * @since 22/01/2025
 */
class NuclearTntEntity(entityType: EntityType<out NuclearTntEntity>, world: World): TntEntity(entityType, world) {
    // constructor called in the block, accepts the world, position and living entity the ignited the block
    constructor(world: World, x: Double, y: Double, z: Double, igniter: LivingEntity?):
            this(NucmaticEntities.NUCLEAR_TNT_ENTITY, world) {
        // set the pos to the passed pos
        this.setPos(x, y, z)

        // makes the tnt jump slightly when spawned
        val d = world.random.nextDouble() * 6.2831854820251465
        this.setVelocity(-sin(d) * 0.02, 0.20000000298023224, -cos(d) * 0.02)

        // set the fuse to 160 ticks ( 8 seconds, double the normal tnt )
        this.fuse = 160

        // set the previous position to the starting position
        this.prevX = x
        this.prevY = y
        this.prevZ = z

        // set the causingEntity to the passed entity
        (this as TntEntity as TntEntityAccessor).setCausingEntity(igniter)
    }

    override fun tick() {
        // tick all the physics processes
        this.tickPortalTeleportation()
        this.applyGravity()
        this.move(MovementType.SELF, this.velocity)

        // set velocity for next tick
        this.velocity = this.velocity.multiply(0.98)
        if(this.isOnGround) {
            this.velocity = this.velocity.multiply(0.7, -0.5, 0.7)
        }

        // decrement the fuse by 1, since it has been one more tick
        val i = this.fuse - 1
        this.fuse = i

        // if the fuse is out, then explode it
        if(i <= 0) {
            // destroy the entity, since it is not needed
            this.discard()

            // if we are not on the client, then explode
            if(!this.world.isClient)
                this.explode()
        }else { // if there is still fuse
            // update if it is inside water
            this.updateWaterState()

            // and spawn smoke particles at the top if we are not running on the client
            if(this.world.isClient)
                this.world.addParticle(ParticleTypes.SMOKE, this.x, this.y + 0.5, this.z, 0.0, 0.0, 0.0)
        }
    }

    private fun explode() {

    }
}