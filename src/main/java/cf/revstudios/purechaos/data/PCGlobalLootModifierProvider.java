package cf.revstudios.purechaos.data;

import cf.revstudios.purechaos.items.MeganiumPickaxeItem;
import cf.revstudios.purechaos.registry.PCGlobalLootModifiers;
import cf.revstudios.purechaos.registry.PCItems;
import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class PCGlobalLootModifierProvider extends GlobalLootModifierProvider {
	public PCGlobalLootModifierProvider(DataGenerator generator) {
		super(generator, ChaosAwakens.MODID);
	}

	@Override
	public String getName() {
		return ChaosAwakens.MODNAME + " Global Loot Modifiers";
	}

	@Override
	protected void start() {
		add("meganium_pickaxe_smelting", PCGlobalLootModifiers.MEGANIUM_PICKAXE_SMELTING.get(),
				new MeganiumPickaxeItem.MeganiumAutoSmeltingModifier(new ILootCondition[] {
						MatchTool.toolMatches(ItemPredicate.Builder.item().of(PCItems.MEGANIUM_PICKAXE.get())).build()
				}));
	}
}
