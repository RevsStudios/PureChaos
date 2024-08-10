package cf.revstudios.purechaos.worldgen;

import cf.revstudios.purechaos.config.PCCommonConfig;
import cf.revstudios.purechaos.registry.PCConfiguredFeatures;
import io.github.chaosawakens.common.registry.CABiomes;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class BiomeLoadEventSubscriber {
	public static void onBiomeLoadingEvent(final BiomeLoadingEvent event) {
		StructureHandler.addFeatures(event);
	}

	private static class StructureHandler {
		public static void addFeatures(BiomeLoadingEvent event) {
			BiomeGenerationSettingsBuilder gen = event.getGeneration();

			RegistryKey<Biome> biome = RegistryKey.create(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(event.getName(), "Who registered null name biome, naming criticism!"));

			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)) {
				if (PCCommonConfig.COMMON.enableOreGen.get())
					if (PCCommonConfig.COMMON.enableOverworldOreGen.get())
						addOverworldOres(gen);
			}

			if (BiomeDictionary.hasType(biome, CABiomes.Type.MINING_PARADISE)) {
				if (PCCommonConfig.COMMON.enableOreGen.get())
					if (PCCommonConfig.COMMON.enableMiningParadiseOreGen.get())
						addMiningParadiseOres(gen);
			}
		}
		
		private static void addOverworldOres(BiomeGenerationSettingsBuilder gen) {
			if (PCCommonConfig.COMMON.enableOreMeganiumGen.get()) gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, PCConfiguredFeatures.ORE_MEGANIUM);
			if (PCCommonConfig.COMMON.enableDustGalactiteGen.get()) gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, PCConfiguredFeatures.DUST_GALACTITE);
		}

		private static void addMiningParadiseOres(BiomeGenerationSettingsBuilder gen) {
			if (PCCommonConfig.COMMON.enableOreMeganiumGen.get()) gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, PCConfiguredFeatures.MINING_ORE_MEGANIUM);
			if (PCCommonConfig.COMMON.enableDustGalactiteGen.get()) gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, PCConfiguredFeatures.MINING_DUST_GALACTITE);
		}
	}
}
