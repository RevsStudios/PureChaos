package cf.revstudios.purechaos.data;

import cf.revstudios.purechaos.registry.PCBlocks;
import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class PCBlockStateProvider extends BlockStateProvider {
    public PCBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlock(PCBlocks.GALACTITE_ORE.get());
        this.simpleBlock(PCBlocks.MEGANIUM_ORE.get());
        this.simpleBlock(PCBlocks.MEGANIUM_BLOCK.get());
    }
}
