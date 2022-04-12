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
        dropSelf(PCBlocks.MEGANIUM_ORE.get());
        dropSelf(PCBlocks.MEGANIUM_BLOCK.get());
        this.add(PCBlocks.GALACTITE_ORE.get(), (p_218464_0_) -> createSilkTouchDispatchTable(p_218464_0_, applyExplosionDecay(p_218464_0_, ItemLootEntry.lootTableItem(PCItems.GALACTITE_DUST.get()).apply(SetCount.setCount(RandomValueRange.between(1.0F, 3.0F))))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return PCBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
