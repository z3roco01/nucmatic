package z3roco01.nucmatic.block

import net.minecraft.block.AbstractBlock.Settings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import z3roco01.nucmatic.Nucmatic
import z3roco01.nucmatic.block.entity.NuclearGeneratorControllerBlock

/**
 * contains all the block for this mod
 * @since 28/09/2024
 */
object NucmaticBlocks {
    // all the blocks in the mod
    val URANIUM_ORE                  = Block(Settings.copy(Blocks.IRON_ORE))
    val DEEPSLATE_URANIUM_ORE        = Block(Settings.copy(Blocks.IRON_ORE))
    val GENERATOR_CASING             = Block(Settings.create().strength(5f, 6f))
    val GENERATOR_GLASS              = Block(Settings.copy(Blocks.GLASS).strength(4.5f, 5.5f))
    val NUCLEAR_GENERATOR_CONTROLLER = NuclearGeneratorControllerBlock()
    val NUCLEAR_GENERATOR            = NuclearGeneratorBlock()

    /**
     * registers all the blocks for this mod
     */
    fun register() {
        register("uranium_ore", URANIUM_ORE)
        register("deepslate_uranium_ore", DEEPSLATE_URANIUM_ORE)
        register("generator_casing", GENERATOR_CASING)
        register("generator_glass", GENERATOR_GLASS)
        register("generator_controller", NUCLEAR_GENERATOR_CONTROLLER)
        register("nuclear_generator", NUCLEAR_GENERATOR)
    }

    /**
     * registers the block and an item for the block
     * @param path the path for id of the block
     * @param block the block
     */
    private fun register(path: String, block: Block) {
        Registry.register(Registries.BLOCK, Nucmatic.mkId(path), block)
        Registry.register(Registries.ITEM, Nucmatic.mkId(path), BlockItem(block, Item.Settings()))
    }
}