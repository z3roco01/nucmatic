package z3roco01.nucmatic.datagen.providers

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.server.recipe.RecipeExporter
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.registry.RegistryWrapper
import z3roco01.nucmatic.block.NucmaticBlocks
import z3roco01.nucmatic.item.NucmaticItems
import java.util.concurrent.CompletableFuture

/**
 * this class handles the generation of recipes for items
 * @since 14/01/2024
 */
class NucmaticRecipeProvider(output: FabricDataOutput, registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>)
    : FabricRecipeProvider(output, registriesFuture){
    override fun generate(exporter: RecipeExporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, NucmaticBlocks.NU_FUEL_ROD)
            .pattern("f")
            .pattern("f")
            .pattern("f")
            .input('f', NucmaticItems.NU_FUEL)
            .criterion(hasItem(NucmaticItems.NU_FUEL), conditionsFromItem(NucmaticItems.NU_FUEL))
            .offerTo(exporter)
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, NucmaticBlocks.LEU_FUEL_ROD)
            .pattern("f")
            .pattern("f")
            .pattern("f")
            .input('f', NucmaticItems.LEU_FUEL)
            .criterion(hasItem(NucmaticItems.LEU_FUEL), conditionsFromItem(NucmaticItems.LEU_FUEL))
            .offerTo(exporter)
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, NucmaticBlocks.HEU_FUEL_ROD)
            .pattern("f")
            .pattern("f")
            .pattern("f")
            .input('f', NucmaticItems.HEU_FUEL)
            .criterion(hasItem(NucmaticItems.HEU_FUEL), conditionsFromItem(NucmaticItems.HEU_FUEL))
            .offerTo(exporter)
    }
}