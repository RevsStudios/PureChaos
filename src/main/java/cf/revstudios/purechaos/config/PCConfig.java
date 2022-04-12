package cf.revstudios.purechaos.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class PCConfig {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
    }

    public static class Common {
        public final ForgeConfigSpec.ConfigValue<Integer> meganiumSwordDamage;
        public final ForgeConfigSpec.ConfigValue<Integer> meganiumAxeDamage;
        public final ForgeConfigSpec.ConfigValue<Integer> meganiumPickaxeDamage;
        public final ForgeConfigSpec.ConfigValue<Integer> meganiumShovelDamage;
        public final ForgeConfigSpec.ConfigValue<Integer> meganiumHoeDamage;
        public final ForgeConfigSpec.ConfigValue<Integer> meganiumBattleAxeDamage;
        public final ForgeConfigSpec.ConfigValue<Double> meganiumBowArrowBaseDamage;
        public final ForgeConfigSpec.ConfigValue<Double> meganiumBowArrowDamageMultiplier;

        public final ForgeConfigSpec.ConfigValue<Boolean> enableOreGen;
        public final ForgeConfigSpec.ConfigValue<Boolean> enableOreMeganiumGen;
        public final ForgeConfigSpec.ConfigValue<Boolean> enableOreGalactiteGen;

        Common(ForgeConfigSpec.Builder builder) {
            builder.push("Attack Damage");
            builder.push("Meganium Weapons/Tools");
            meganiumSwordDamage = builder.define("Damage of the Meganium Sword", 60);
            meganiumAxeDamage = builder.define("Damage of the Meganium Axe", 62);
            meganiumPickaxeDamage = builder.define("Damage of the Meganium Pickaxe", 58);
            meganiumShovelDamage = builder.define("Damage of the Meganium Shovel", 59);
            meganiumHoeDamage = builder.define("Damage of the Meganium Hoe", 1);
            meganiumBattleAxeDamage = builder.define("Damage of the Meganium Battle Axe", 80);
            meganiumBowArrowBaseDamage = builder.comment("How much damage the Meganium bow will add up to the base arrow damage").define("Additional Damage of the Meganium Bow Arrows", 7.5D);
            meganiumBowArrowDamageMultiplier = builder.define("Damage Multiplier of the Meganium Bow's Power Enchantment", 0.75D);
            builder.pop();
            builder.pop();
            builder.push("World Generation");
            enableOreGen = builder.define("Enable ore generation", true);
            builder.push("Specific Ore Spawning");
            enableOreMeganiumGen = builder.define("Meganium ore generation", true);
            enableOreGalactiteGen = builder.define("Galactite ore generation", true);
            builder.pop();
            builder.pop();
        }
    }
}
