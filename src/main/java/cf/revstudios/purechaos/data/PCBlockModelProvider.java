package cf.revstudios.purechaos.data;

import cf.revstudios.purechaos.PureChaos;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class PCBlockModelProvider extends BlockModelProvider {
	public PCBlockModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, modid, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		this.cubeAll("galactite_ore", pureChaosRL("galactite_ore"));
		this.cubeAll("galactite_dust_block", pureChaosRL("galactite_dust_block"));
		this.cubeAll("galactite_block", pureChaosRL("galactite_block"));
		this.cubeAll("meganium_ore", pureChaosRL("meganium_ore"));
		this.cubeAll("meganium_block", pureChaosRL("meganium_block"));
	}

	private ResourceLocation pureChaosRL(String texture) {
		return new ResourceLocation(PureChaos.MODID, BLOCK_FOLDER + "/" + texture);
	}
}
