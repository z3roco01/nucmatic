package z3roco01.nucmatic.block.entity

import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
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

    /**
     * called to register all the block entity types
     */
    fun register() {
        register("nuclear_generator", NUCLEAR_GENERATOR_TYPE)
    }

    /**
     * helper method for registering a block entity type
     * @param path the path of the id of the type
     * @param type the [BlockEntityType] being registers
     */
    private fun register(path: String, type: BlockEntityType<*>) = Registry.register(Registries.BLOCK_ENTITY_TYPE,
        Nucmatic.mkId(path), type)
}