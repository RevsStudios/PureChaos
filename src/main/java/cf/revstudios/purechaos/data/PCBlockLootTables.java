package cf.revstudios.purechaos.data;

import cf.revstudios.purechaos.registry.PCBlocks;
import cf.revstudios.purechaos.registry.PCItems;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.functions.SetCount;
import net.minecraftforge.fml.RegistryObject;

public class PCBlockLootTables extends BlockLootTables {
	@Override
	protected void addTables() {
		add(PCBlocks.GALACTITE_ORE.get(), (ore) -> createSilkTouchDispatchTable(ore, applyExplosionDecay(ore, ItemLootEntry.lootTableItem(PCItems.GALACTITE_DUST.get()).apply(SetCount.setCount(RandomValueRange.between(1.0F, 3.0F))))));
		dropSelf(PCBlocks.GALACTITE_DUST_BLOCK.get());
		dropSelf(PCBlocks.GALACTITE_BLOCK.get());
		dropSelf(PCBlocks.MEGANIUM_ORE.get());
		dropSelf(PCBlocks.MEGANIUM_BLOCK.get());
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return PCBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
	}
}
