package cf.revstudios.purechaos.registry;

import io.github.chaosawakens.api.wrapper.FeatureWrapper;
import io.github.chaosawakens.common.events.CACommonSetupEvents;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;

public class PCConfiguredFeatures {
	// ORES
	// GENERIC
	public static final ConfiguredFeature<?, ?> ORE_MEGANIUM = register("ore_meganium", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.MEGANIUM_ORE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(1, 40))).squared().count(1));
	public static final ConfiguredFeature<?, ?> DUST_GALACTITE = register("dust_galactite", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.GALACTITE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(1, 40))).squared().count(1));

	public static final ConfiguredFeature<?, ?> MINING_ORE_MEGANIUM = register("mining_ore_meganium", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.MEGANIUM_ORE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(1, 20))).squared().count(1));
	public static final ConfiguredFeature<?, ?> MINING_DUST_GALACTITE = register("mining_dust_galactite", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.GALACTITE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(1, 20))).squared().count(1));

	private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
		CACommonSetupEvents.CONFIG_FEATURES.add(new FeatureWrapper(key, configuredFeature));
		return configuredFeature;
	}

	public static final class States {
		private static final BlockState MEGANIUM_ORE = PCBlocks.MEGANIUM_ORE.get().defaultBlockState();
		private static final BlockState GALACTITE = PCBlocks.GALACTITE_DUST_BLOCK.get().defaultBlockState();
	}
}
