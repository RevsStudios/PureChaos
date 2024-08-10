package cf.revstudios.purechaos.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class PCServerConfig {
	public static final ForgeConfigSpec SERVER_SPEC;
	public static final Server SERVER;

	static {
		final Pair<Server, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(Server::new);
		SERVER_SPEC = serverSpecPair.getRight();
		SERVER = serverSpecPair.getLeft();
	}

	public static class Server {
		public final ForgeConfigSpec.IntValue meganiumSwordDamage;
		public final ForgeConfigSpec.IntValue meganiumAxeDamage;
		public final ForgeConfigSpec.IntValue meganiumPickaxeDamage;
		public final ForgeConfigSpec.IntValue meganiumShovelDamage;
		public final ForgeConfigSpec.IntValue meganiumHoeDamage;
		public final ForgeConfigSpec.IntValue meganiumBattleAxeDamage;
		public final ForgeConfigSpec.DoubleValue meganiumBowArrowBaseDamage;
		public final ForgeConfigSpec.DoubleValue meganiumBowArrowDamageMultiplier;

		Server(ForgeConfigSpec.Builder builder) {
			builder.push("Attack Damage");
			builder.push("Meganium Weapons/Tools");
			this.meganiumSwordDamage = builder.defineInRange("Damage of the Meganium Sword", 60, 0, Integer.MAX_VALUE);
			this.meganiumAxeDamage = builder.defineInRange("Damage of the Meganium Axe", 62, 0, Integer.MAX_VALUE);
			this.meganiumPickaxeDamage = builder.defineInRange("Damage of the Meganium Pickaxe", 58, 0, Integer.MAX_VALUE);
			this.meganiumShovelDamage = builder.defineInRange("Damage of the Meganium Shovel", 59, 0, Integer.MAX_VALUE);
			this.meganiumHoeDamage = builder.defineInRange("Damage of the Meganium Hoe", 1, 0, Integer.MAX_VALUE);
			this.meganiumBattleAxeDamage = builder.defineInRange("Damage of the Meganium Battle Axe", 111, 0, Integer.MAX_VALUE);
			this.meganiumBowArrowBaseDamage = builder.comment("How much damage the Meganium bow will add up to the base arrow damage").defineInRange("Additional Damage of the Meganium Bow Arrows", 7.5, 0.0, 2.147483647E9);
			this.meganiumBowArrowDamageMultiplier = builder.defineInRange("Damage Multiplier of the Meganium Bow's Power Enchantment", 0.75, 0.0, 2.147483647E9);
			builder.pop();
			builder.pop();
		}
	}
}
