package z3roco01.nucmatic.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.registry.RegistryWrapper
import z3roco01.nucmatic.block.NucmaticBlocks
import z3roco01.nucmatic.item.NucmaticItems
import java.util.concurrent.CompletableFuture

/**
 * data generator for the english translation file
 * @since 04/10/2024
 */
class NucmaticEnglishLanguageProvider(fabricDataOutput: FabricDataOutput, registryLookup: CompletableFuture<RegistryWrapper.WrapperLookup>):
    FabricLanguageProvider(fabricDataOutput, "en_us", registryLookup) {
    override fun generateTranslations(registryLookup: RegistryWrapper.WrapperLookup, translationBuilder: TranslationBuilder) {
        // items
        translationBuilder.add(NucmaticItems.STEM_CELL,         "Stem Cell")
        translationBuilder.add(NucmaticItems.RAW_URANIUM,       "Raw Uranium")
        translationBuilder.add(NucmaticItems.NU_FUEL,           "NU Fuel")
        translationBuilder.add(NucmaticItems.LEU_FUEL,          "LEU Fuel")
        translationBuilder.add(NucmaticItems.HEU_FUEL,          "HEU Fuel")
        translationBuilder.add(NucmaticItems.HAZMAT_HELMET,     "Hazmat Helmet")
        translationBuilder.add(NucmaticItems.HAZMAT_CHESTPLATE, "Hazmat Chestplate")
        translationBuilder.add(NucmaticItems.HAZMAT_LEGGINGS,   "Hazmat Leggings")
        translationBuilder.add(NucmaticItems.HAZMAT_BOOTS,      "Hazmat Boots")

        // blocks
        translationBuilder.add(NucmaticBlocks.URANIUM_ORE,           "Uranium Ore")
        translationBuilder.add(NucmaticBlocks.DEEPSLATE_URANIUM_ORE, "Deepslate Uranium Ore")
        translationBuilder.add(NucmaticBlocks.NUCLEAR_GENERATOR,     "Nuclear Generator")

        // misc
        translationBuilder.add("effect.nucmatic.radiation_poisoning", "Radiation Poisoning")
        translationBuilder.add("itemGroup.nucmatic", "Nucmatic")
    }
}