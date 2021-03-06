package cf.revstudios.purechaos.data;

import cf.revstudios.purechaos.PureChaos;
import cf.revstudios.purechaos.registry.PCBlocks;
import cf.revstudios.purechaos.registry.PCItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Collection;

public class PCItemModelGenerator extends ItemModelProvider {
	public PCItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, PureChaos.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		generate(PCItems.ITEMS.getEntries());
		generateBlockItems(PCBlocks.ITEM_BLOCKS.getEntries());
	}

	@Nonnull
	@Override
	public String getName() {
		return PureChaos.MODNAME + " Item models";
	}

	private void generate(final Collection<RegistryObject<Item>> items) {
		final ModelFile parentGenerated = getExistingFile(mcLoc("item/generated"));
		final ModelFile.ExistingModelFile parentHandheld = getExistingFile(mcLoc("item/handheld"));

		for (RegistryObject<Item> item : items) {
			String name = item.getId().getPath();

			if (name.startsWith("enchanted"))
				name = name.substring(name.indexOf("_") + 1);

			/*
			 *  Skip elements that have no texture at assets/chaosadditions/textures/item
			 *  or already have an existing model at assets/chaosadditions/models/item
			 */
			if (!existingFileHelper.exists(new ResourceLocation(PureChaos.MODID, "item/" + name), TEXTURE) || existingFileHelper.exists(new ResourceLocation(PureChaos.MODID, "item/" + name), MODEL))
				continue;

			PureChaos.LOGGER.info(item.getId());

			getBuilder(item.getId().getPath()).parent(item.get().getMaxDamage(ItemStack.EMPTY) > 0 && !(item.get() instanceof ArmorItem) ? parentHandheld : parentGenerated).texture("layer0", ItemModelProvider.ITEM_FOLDER + "/" + name);
		}
	}

	private void generateBlockItems(final Collection<RegistryObject<Item>> itemBlocks) {
		for (RegistryObject<Item> item : itemBlocks) {
			String name = item.getId().getPath();

			/*
			 *  Skip elements that have no block model inside of assets/chaosadditions/models/block
			 *  or already have an existing item model at assets/chaosadditions/models/item
			 */

			if (!existingFileHelper.exists(new ResourceLocation(PureChaos.MODID, "block/" + name), MODEL) || existingFileHelper.exists(new ResourceLocation(PureChaos.MODID, "item/" + name), MODEL))
				continue;

			PureChaos.LOGGER.info(item.getId());

			withExistingParent(name, new ResourceLocation(PureChaos.MODID, "block/" + name));

		}
	}
}
