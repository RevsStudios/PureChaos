package cf.revstudios.purechaos.data;

import cf.revstudios.purechaos.PureChaos;
import cf.revstudios.purechaos.registry.PCItems;
import io.github.chaosawakens.common.loot_modifiers.UltimateAutoSmeltModifier;
import io.github.chaosawakens.common.registry.CALootModifiers;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class PCGlobalLootModifierProvider extends GlobalLootModifierProvider {
	public PCGlobalLootModifierProvider(DataGenerator generator) {
		super(generator, PureChaos.MODID);
	}

	@Override
	public String getName() {
		return PureChaos.MODNAME + " Global Loot Modifiers";
	}

	protected void start() {
		this.add("ultimate_pickaxe_smelting", CALootModifiers.ULTIMATE_PICKAXE_SMELTING.get(), new UltimateAutoSmeltModifier(new ILootCondition[]{MatchTool.toolMatches(ItemPredicate.Builder.item().of(PCItems.MEGANIUM_PICKAXE.get())).build()}));
	}
}
