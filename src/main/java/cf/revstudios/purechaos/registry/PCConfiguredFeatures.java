package cf.revstudios.purechaos.registry;

import io.github.chaosawakens.api.FeatureWrapper;
import io.github.chaosawakens.common.events.CommonSetupEvent;
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
    public static final ConfiguredFeature<?, ?> ORE_MEGANIUM = register("ore_meganium", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.MEGANIUM_ORE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(1, 9))).squared().count(3));
    public static final ConfiguredFeature<?, ?> ORE_GALACTITE = register("ore_galactite", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, States.GALACTITE_ORE, 3)).decorated(Placement.DEPTH_AVERAGE.configured(new DepthAverageConfig(1, 9))).squared().count(3));

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        CommonSetupEvent.configFeatures.add(new FeatureWrapper(key, configuredFeature));
        return configuredFeature;
    }

    public static final class States {
        private static final BlockState MEGANIUM_ORE = PCBlocks.MEGANIUM_ORE.get().defaultBlockState();
        private static final BlockState GALACTITE_ORE = PCBlocks.GALACTITE_ORE.get().defaultBlockState();
    }
}
