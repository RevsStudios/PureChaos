package cf.revstudios.purechaos.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class PCCommonConfig {
	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = commonSpecPair.getRight();
		COMMON = commonSpecPair.getLeft();
	}

	public static class Common {
		public final ForgeConfigSpec.BooleanValue enableOreGen;
		public final ForgeConfigSpec.BooleanValue enableOverworldOreGen;
		public final ForgeConfigSpec.BooleanValue enableMiningParadiseOreGen;
		public final ForgeConfigSpec.BooleanValue enableOreMeganiumGen;
		public final ForgeConfigSpec.BooleanValue enableDustGalactiteGen;

		Common(ForgeConfigSpec.Builder builder) {
			builder.push("World Generation");
			this.enableOreGen = builder.define("Enable ore generation", true);
			this.enableOverworldOreGen = builder.define("Enable Overworld ore generation", true);
			this.enableMiningParadiseOreGen = builder.define("Enable Mining Paradise ore generation", true);
			builder.push("Specific Ore Spawning");
			this.enableOreMeganiumGen = builder.define("Meganium ore generation", true);
			this.enableDustGalactiteGen = builder.define("Galactite dust generation", true);
			builder.pop();
			builder.pop();
		}
	}
}
