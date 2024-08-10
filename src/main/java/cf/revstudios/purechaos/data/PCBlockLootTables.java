package cf.revstudios.purechaos.data;

import cf.revstudios.purechaos.registry.PCBlocks;
import cf.revstudios.purechaos.registry.PCItems;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.fml.RegistryObject;

public class PCBlockLootTables extends BlockLootTables {
	@Override
	protected void addTables() {
		add(PCBlocks.GALACTITE_DUST_BLOCK.get(), (ore) -> randomDropping(PCItems.GALACTITE_DUST.get(), 1, 4));
		dropSelf(PCBlocks.GALACTITE_BLOCK.get());
		dropSelf(PCBlocks.MEGANIUM_ORE.get());
		dropSelf(PCBlocks.MEGANIUM_BLOCK.get());
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return PCBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
	}

	private LootTable.Builder randomDropping(IItemProvider item, float random1, float random2) {
		return LootTable.lootTable().withPool(
				applyExplosionCondition(item, LootPool.lootPool()
						.setRolls(RandomValueRange.between(random1, random2)))
						.add(ItemLootEntry.lootTableItem(item)));
	}
}
