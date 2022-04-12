package cf.revstudios.purechaos.worldgen;

import cf.revstudios.purechaos.config.PCConfig;
import cf.revstudios.purechaos.registry.PCConfiguredFeatures;
import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.registry.CABiomes;
import io.github.chaosawakens.common.registry.CAConfiguredFeatures;
import io.github.chaosawakens.common.registry.CAConfiguredStructures;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class BiomeLoadEventSubscriber {
	public static void onBiomeLoadingEvent(final BiomeLoadingEvent event) {
		StructureHandler.addfeatures(event);
	}

	private static class StructureHandler {
		public static void addfeatures(BiomeLoadingEvent event) {
			BiomeGenerationSettingsBuilder gen = event.getGeneration();

			RegistryKey<Biome> biome = RegistryKey.create(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(event.getName(), "Who registered null name biome, naming criticism!"));
			final String location = biome.location().toString();


			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)) {
				if (PCConfig.COMMON.enableOreGen.get())
					addOverworldOres(gen);
			}
		}
		
		private static void addOverworldOres(BiomeGenerationSettingsBuilder gen) {
			if (PCConfig.COMMON.enableOreMeganiumGen.get())
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, PCConfiguredFeatures.ORE_MEGANIUM);
			if (PCConfig.COMMON.enableOreGalactiteGen.get())
				gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, PCConfiguredFeatures.ORE_GALACTITE);
		}
	}
}
