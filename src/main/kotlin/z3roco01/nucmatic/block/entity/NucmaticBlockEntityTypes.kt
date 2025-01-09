package z3roco01.nucmatic.block.entity

import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import team.reborn.energy.api.EnergyStorage
import z3roco01.nucmatic.Nucmatic
import z3roco01.nucmatic.block.NucmaticBlocks

/**
 * handles the registration of block entity types
 * @since 07/10/2024
 */
object NucmaticBlockEntityTypes {
    // all the block entity types
    val NUCLEAR_GENERATOR_TYPE = BlockEntityType.Builder.create(::NuclearGeneratorBlockEntity,
        NucmaticBlocks.NUCLEAR_GENERATOR).build()
    val NUCLEAR_GENERATOR_CONTROLLER_TYPE = BlockEntityType.Builder.create(::NuclearGeneratorControllerBlockEntity,
        NucmaticBlocks.NUCLEAR_GENERATOR_CONTROLLER).build()

    /**
     * called to register all the block entity types
     */
    fun register() {
        registerEnergyContainer("nuclear_generator", NUCLEAR_GENERATOR_TYPE)
        registerEnergyContainer("generator_controller", NUCLEAR_GENERATOR_CONTROLLER_TYPE)
    }

    /**
     * registers an energy container block entity, registers both the type and the entity as an energy storing block
     * @param path the path of the id
     * @param type the [BlockEntityType] of the block
     */
    private fun registerEnergyContainer(path: String, type: BlockEntityType<out EnergyContainer>) {
        register(path, type)
        EnergyStorage.SIDED.registerForBlockEntity({blockEntity, direction -> blockEntity.energyStorage.getSideStorage(direction)}, type)
    }

    /**
     * helper method for registering a block entity type
     * @param path the path of the id of the type
     * @param type the [BlockEntityType] being registers
     */
    private fun register(path: String, type: BlockEntityType<*>) = Registry.register(Registries.BLOCK_ENTITY_TYPE,
        Nucmatic.mkId(path), type)
}