package z3roco01.nucmatic.block

import net.minecraft.block.AbstractBlock.Settings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import z3roco01.nucmatic.Nucmatic

/**
 * contains all the block for this mod
 * @since 28/09/2024
 */
object NucmaticBlocks {
    // all the blocks in the mod
    val URANIUM_ORE           = Block(Settings.copy(Blocks.IRON_ORE))
    val DEEPSLATE_URANIUM_ORE = Block(Settings.copy(Blocks.IRON_ORE))
    val REACTOR_CASING        = Block(Settings.create().strength(5f, 6f))
    val REACTOR_GLASS         = Block(Settings.copy(Blocks.GLASS).strength(4.5f, 5.5f))
    val REACTOR_CONTROLLER    = ReactorControllerBlock()
    val NUCLEAR_GENERATOR     = NuclearGeneratorBlock()
    val NU_FUEL_ROD           = Block(Settings.create())
    val LEU_FUEL_ROD          = Block(Settings.create())
    val HEU_FUEL_ROD          = Block(Settings.create())

    /**
     * registers all the blocks for this mod
     */
    fun register() {
        register("uranium_ore",           URANIUM_ORE)
        register("deepslate_uranium_ore", DEEPSLATE_URANIUM_ORE)
        register("reactor_casing",        REACTOR_CASING)
        register("reactor_glass",         REACTOR_GLASS)
        register("reactor_controller",    REACTOR_CONTROLLER)
        register("nuclear_generator",     NUCLEAR_GENERATOR)
        register("nu_fuel_rod",           NU_FUEL_ROD)
        register("leu_fuel_rod",          LEU_FUEL_ROD)
        register("heu_fuel_rod",          HEU_FUEL_ROD)
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