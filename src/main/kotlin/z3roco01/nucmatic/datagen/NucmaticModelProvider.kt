package z3roco01.nucmatic.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.Models
import z3roco01.nucmatic.block.NucmaticBlocks
import z3roco01.nucmatic.item.NucmaticItems

/**
 * data generator for most block + all item models
 * @since 04/10/2024
 */
class NucmaticModelProvider(dataOutput: FabricDataOutput): FabricModelProvider(dataOutput) {
    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(NucmaticBlocks.URANIUM_ORE)
        blockStateModelGenerator.registerSimpleCubeAll(NucmaticBlocks.DEEPSLATE_URANIUM_ORE)
        blockStateModelGenerator.registerSimpleCubeAll(NucmaticBlocks.REACTOR_CASING)
        blockStateModelGenerator.registerSimpleCubeAll(NucmaticBlocks.REACTOR_GLASS)
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {
        itemModelGenerator.register(NucmaticItems.STEM_CELL,         Models.GENERATED)
        itemModelGenerator.register(NucmaticItems.RAW_URANIUM,       Models.GENERATED)
        itemModelGenerator.register(NucmaticItems.NU_FUEL,           Models.GENERATED)
        itemModelGenerator.register(NucmaticItems.LEU_FUEL,          Models.GENERATED)
        itemModelGenerator.register(NucmaticItems.HEU_FUEL,          Models.GENERATED)
        itemModelGenerator.register(NucmaticItems.HAZMAT_HELMET,     Models.GENERATED)
        itemModelGenerator.register(NucmaticItems.HAZMAT_CHESTPLATE, Models.GENERATED)
        itemModelGenerator.register(NucmaticItems.HAZMAT_LEGGINGS,   Models.GENERATED)
        itemModelGenerator.register(NucmaticItems.HAZMAT_BOOTS,      Models.GENERATED)
    }
}