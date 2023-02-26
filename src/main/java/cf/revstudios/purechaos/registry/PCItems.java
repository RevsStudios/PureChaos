package cf.revstudios.purechaos.registry;

import cf.revstudios.purechaos.PureChaos;
import cf.revstudios.purechaos.client.entity.render.MeganiumBattleAxeItemRender;
import cf.revstudios.purechaos.config.PCConfig;
import cf.revstudios.purechaos.enums.PCItemTier;
import cf.revstudios.purechaos.enums.PCArmorMaterial;
import cf.revstudios.purechaos.items.MeganiumAxeItem;
import cf.revstudios.purechaos.items.extended.MeganiumBattleAxeItem;
import cf.revstudios.purechaos.items.MeganiumBowItem;
import cf.revstudios.purechaos.items.MeganiumFishingRodItem;
import cf.revstudios.purechaos.items.MeganiumHoeItem;
import cf.revstudios.purechaos.items.MeganiumPickaxeItem;
import cf.revstudios.purechaos.items.MeganiumShovelItem;
import io.github.chaosawakens.common.items.*;
import io.github.chaosawakens.common.registry.CAItemGroups;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = PureChaos.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PCItems {
	// RARITIES
	public static final Rarity RARITY_MEGANIUM = Rarity.create(PureChaos.MODID + ":meganium", TextFormatting.DARK_RED);
	public static final Rarity RARITY_GALACTITE = Rarity.create(PureChaos.MODID + ":galactite", TextFormatting.DARK_PURPLE);

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PureChaos.MODID);

	public static final RegistryObject<Item> MEGANIUM_CHUNK = ITEMS.register("meganium_chunk", () -> new Item(new Item.Properties().rarity(RARITY_MEGANIUM).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> MEGANIUM_INGOT = ITEMS.register("meganium_ingot", () -> new Item(new Item.Properties().rarity(RARITY_MEGANIUM).tab(CAItemGroups.ITEMS)));

	public static final RegistryObject<Item> GALACTITE_DUST = ITEMS.register("galactite_dust", () -> new Item(new Item.Properties().rarity(RARITY_GALACTITE).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> GALACTITE = ITEMS.register("galactite", () -> new Item(new Item.Properties().rarity(RARITY_GALACTITE).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> GALACTITE_ROD = ITEMS.register("galactite_rod", () -> new Item(new Item.Properties().rarity(RARITY_GALACTITE).tab(CAItemGroups.ITEMS)));

	public static final RegistryObject<EnchantedSwordItem> MEGANIUM_SWORD = ITEMS.register("meganium_sword", () -> new EnchantedSwordItem(PCItemTier.TOOL_MEGANIUM, PCConfig.COMMON.meganiumSwordDamage.get() - 47, -2.4F, new Item.Properties().rarity(RARITY_MEGANIUM).tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.KNOCKBACK, 2), new EnchantmentData(Enchantments.MOB_LOOTING, 3), new EnchantmentData(Enchantments.UNBREAKING, 3), new EnchantmentData(Enchantments.FIRE_ASPECT, 2)}));
	public static final RegistryObject<MeganiumShovelItem> MEGANIUM_SHOVEL = ITEMS.register("meganium_shovel", () -> new MeganiumShovelItem(PCItemTier.TOOL_MEGANIUM, PCConfig.COMMON.meganiumShovelDamage.get() - 47, -3F, new Item.Properties().rarity(RARITY_MEGANIUM).tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.BLOCK_EFFICIENCY, 5), new EnchantmentData(Enchantments.UNBREAKING, 3)}));
	public static final RegistryObject<MeganiumPickaxeItem> MEGANIUM_PICKAXE = ITEMS.register("meganium_pickaxe", () -> new MeganiumPickaxeItem(PCItemTier.TOOL_MEGANIUM, PCConfig.COMMON.meganiumPickaxeDamage.get() - 47, -2.8F, new Item.Properties().rarity(RARITY_MEGANIUM).tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.BLOCK_EFFICIENCY, 5), new EnchantmentData(Enchantments.BLOCK_FORTUNE, 3), new EnchantmentData(Enchantments.UNBREAKING, 3)}));
	public static final RegistryObject<MeganiumAxeItem> MEGANIUM_AXE = ITEMS.register("meganium_axe", () -> new MeganiumAxeItem(PCItemTier.TOOL_MEGANIUM, PCConfig.COMMON.meganiumAxeDamage.get() - 47, -3F, new Item.Properties().rarity(RARITY_MEGANIUM).tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.BLOCK_EFFICIENCY, 5), new EnchantmentData(Enchantments.UNBREAKING, 3)}));
	public static final RegistryObject<MeganiumHoeItem> MEGANIUM_HOE = ITEMS.register("meganium_hoe", () -> new MeganiumHoeItem(PCItemTier.TOOL_MEGANIUM, PCConfig.COMMON.meganiumHoeDamage.get() - 47, 0F, new Item.Properties().rarity(RARITY_MEGANIUM).tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.BLOCK_EFFICIENCY, 5), new EnchantmentData(Enchantments.BLOCK_FORTUNE, 3), new EnchantmentData(Enchantments.UNBREAKING, 3)}));
	public static final RegistryObject<MeganiumBowItem> MEGANIUM_BOW = ITEMS.register("meganium_bow", () -> new MeganiumBowItem(new Item.Properties().rarity(RARITY_MEGANIUM).tab(CAItemGroups.EQUIPMENT).stacksTo(1).durability(1024),
			new EnchantmentData[]{new EnchantmentData(Enchantments.POWER_ARROWS, 5), new EnchantmentData(Enchantments.FLAMING_ARROWS, 1), new EnchantmentData(Enchantments.PUNCH_ARROWS, 2), new EnchantmentData(Enchantments.INFINITY_ARROWS, 1)}));
	public static final RegistryObject<MeganiumFishingRodItem> MEGANIUM_FISHING_ROD = ITEMS.register("meganium_fishing_rod", () -> new MeganiumFishingRodItem(new Item.Properties().rarity(RARITY_MEGANIUM).tab(CAItemGroups.EQUIPMENT).stacksTo(1).durability(1024),
			new EnchantmentData[]{new EnchantmentData(Enchantments.UNBREAKING, 3)}));
	public static final RegistryObject<MeganiumBattleAxeItem> MEGANIUM_BATTLE_AXE = ITEMS.register("meganium_battle_axe", () -> new MeganiumBattleAxeItem(PCItemTier.TOOL_MEGANIUM, PCConfig.COMMON.meganiumBattleAxeDamage.get() - 47, -3.35F, 3.0D, 0, new Item.Properties().rarity(RARITY_MEGANIUM).tab(CAItemGroups.EQUIPMENT).setISTER(() -> MeganiumBattleAxeItemRender::new),
			new EnchantmentData[]{new EnchantmentData(Enchantments.MOB_LOOTING, 3), new EnchantmentData(Enchantments.UNBREAKING, 3)}));

	public static final RegistryObject<EnchantedArmorItem> MEGANIUM_HELMET = ITEMS.register("meganium_helmet", () -> new EnchantedArmorItem(PCArmorMaterial.MEGANIUM, EquipmentSlotType.HEAD, new Item.Properties().rarity(RARITY_MEGANIUM).tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.ALL_DAMAGE_PROTECTION, 4), new EnchantmentData(Enchantments.UNBREAKING, 3), new EnchantmentData(Enchantments.RESPIRATION, 3), new EnchantmentData(Enchantments.AQUA_AFFINITY, 1)}));
	public static final RegistryObject<EnchantedArmorItem> MEGANIUM_CHESTPLATE = ITEMS.register("meganium_chestplate", () -> new EnchantedArmorItem(PCArmorMaterial.MEGANIUM, EquipmentSlotType.CHEST, new Item.Properties().rarity(RARITY_MEGANIUM).tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.ALL_DAMAGE_PROTECTION, 4), new EnchantmentData(Enchantments.UNBREAKING, 3)}));
	public static final RegistryObject<EnchantedArmorItem> MEGANIUM_LEGGINGS = ITEMS.register("meganium_leggings", () -> new EnchantedArmorItem(PCArmorMaterial.MEGANIUM, EquipmentSlotType.LEGS, new Item.Properties().rarity(RARITY_MEGANIUM).tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.ALL_DAMAGE_PROTECTION, 4), new EnchantmentData(Enchantments.UNBREAKING, 3)}));
	public static final RegistryObject<EnchantedArmorItem> MEGANIUM_BOOTS = ITEMS.register("meganium_boots", () -> new EnchantedArmorItem(PCArmorMaterial.MEGANIUM, EquipmentSlotType.FEET, new Item.Properties().rarity(RARITY_MEGANIUM).tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.ALL_DAMAGE_PROTECTION, 4), new EnchantmentData(Enchantments.UNBREAKING, 3), new EnchantmentData(Enchantments.FALL_PROTECTION, 4)}));
}
