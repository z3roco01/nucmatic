package z3roco01.nucmatic.datagen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import z3roco01.nucmatic.datagen.providers.NucmaticEnglishLanguageProvider
import z3roco01.nucmatic.datagen.providers.NucmaticModelProvider
import z3roco01.nucmatic.datagen.providers.NucmaticRecipeProvider

/**
 * entry point for the datagen
 * @since 26/09/2024
 */
object NucmaticDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		val pack = fabricDataGenerator.createPack()
		
		pack.addProvider(::NucmaticModelProvider)
		pack.addProvider(::NucmaticEnglishLanguageProvider)
		pack.addProvider(::NucmaticRecipeProvider)
	}
}