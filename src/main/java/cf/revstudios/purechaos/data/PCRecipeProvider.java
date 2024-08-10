package cf.revstudios.purechaos.data;

import cf.revstudios.purechaos.PureChaos;
import cf.revstudios.purechaos.registry.PCBlocks;
import cf.revstudios.purechaos.registry.PCItems;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;

import java.util.function.Consumer;

public class PCRecipeProvider extends RecipeProvider {
	public PCRecipeProvider(DataGenerator gen) {
		super(gen);
	}

	private static void smelting(Consumer<IFinishedRecipe> consumer, IItemProvider pInput, IItemProvider pOutput, Float experience, int cookingTime) {
		CookingRecipeBuilder.smelting(Ingredient.of(pInput), pOutput, experience, cookingTime).unlockedBy("has_" + pInput.asItem(), has(pInput)).save(consumer, "purechaos:" + pOutput + "_from_smelting");
	}

	private static void blasting(Consumer<IFinishedRecipe> consumer, IItemProvider pInput, IItemProvider pOutput, Float experience, int cookingTime) {
		CookingRecipeBuilder.blasting(Ingredient.of(pInput), pOutput, experience, cookingTime).unlockedBy("has_" + pInput.asItem(), has(pInput)).save(consumer, "purechaos:" + pOutput + "_from_blasting");
	}

	private static void smeltingNamed(Consumer<IFinishedRecipe> consumer, IItemProvider pInput, IItemProvider pOutput, Float experience, int cookingTime, String name) {
		CookingRecipeBuilder.smelting(Ingredient.of(pInput), pOutput, experience, cookingTime).unlockedBy("has_" + pInput.asItem(), has(pInput)).save(consumer, name);
	}

	private static void blastingNamed(Consumer<IFinishedRecipe> consumer, IItemProvider pInput, IItemProvider pOutput, Float experience, int cookingTime, String name) {
		CookingRecipeBuilder.blasting(Ingredient.of(pInput), pOutput, experience, cookingTime).unlockedBy("has_" + pInput.asItem(), has(pInput)).save(consumer, name);
	}

	public String getName() {
		return PureChaos.MODNAME + ": Recipes";
	}

	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(PCItems.GALACTITE_ROD.get(), 2).define('#', PCItems.GALACTITE.get()).pattern("#").pattern("#").unlockedBy("has_" + PCItems.GALACTITE.get(), has(PCItems.GALACTITE.get())).save(consumer);

		ShapelessRecipeBuilder.shapeless(PCItems.MEGANIUM_INGOT.get()).requires(PCItems.MEGANIUM_CHUNK.get(), 2).requires(Items.IRON_INGOT).unlockedBy("has_" + PCItems.MEGANIUM_CHUNK.get(), has(PCItems.MEGANIUM_CHUNK.get())).save(consumer);

		ShapedRecipeBuilder.shaped(PCBlocks.MEGANIUM_BLOCK.get()).define('#', PCItems.MEGANIUM_INGOT.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_" + PCItems.MEGANIUM_INGOT.get(), has(PCItems.MEGANIUM_INGOT.get())).save(consumer);
		ShapelessRecipeBuilder.shapeless(PCItems.MEGANIUM_INGOT.get(), 9).requires(PCBlocks.MEGANIUM_BLOCK.get()).unlockedBy("has_" + PCBlocks.MEGANIUM_BLOCK.get().asItem(), has(PCBlocks.MEGANIUM_BLOCK.get())).save(consumer, "purechaos:" + PCBlocks.MEGANIUM_BLOCK.get().asItem() + "_from_block");
		ShapedRecipeBuilder.shaped(PCBlocks.GALACTITE_DUST_BLOCK.get()).define('#', PCItems.GALACTITE_DUST.get()).pattern("##").pattern("##").unlockedBy("has_" + PCItems.GALACTITE_DUST.get(), has(PCItems.GALACTITE_DUST.get())).save(consumer);
		ShapelessRecipeBuilder.shapeless(PCItems.GALACTITE_DUST.get(), 4).requires(PCBlocks.GALACTITE_DUST_BLOCK.get()).unlockedBy("has_" + PCBlocks.GALACTITE_DUST_BLOCK.get().asItem(), has(PCBlocks.GALACTITE_DUST_BLOCK.get())).save(consumer, "purechaos:" + PCBlocks.GALACTITE_DUST_BLOCK.get().asItem() + "_from_block");
		ShapedRecipeBuilder.shaped(PCBlocks.GALACTITE_BLOCK.get()).define('#', PCItems.GALACTITE.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_" + PCItems.GALACTITE.get(), has(PCItems.GALACTITE.get())).save(consumer);
		ShapelessRecipeBuilder.shapeless(PCItems.GALACTITE.get(), 9).requires(PCBlocks.GALACTITE_BLOCK.get()).unlockedBy("has_" + PCBlocks.GALACTITE_BLOCK.get().asItem(), has(PCBlocks.GALACTITE_BLOCK.get())).save(consumer, "purechaos:" + PCBlocks.GALACTITE_BLOCK.get().asItem() + "_from_block");

		ShapedRecipeBuilder.shaped(PCItems.MEGANIUM_SWORD.get()).define('#', PCItems.MEGANIUM_INGOT.get()).define('$', CAItems.ULTIMATE_SWORD.get()).define('%', PCItems.GALACTITE_ROD.get()).pattern("#").pattern("$").pattern("%").unlockedBy("has_" + PCItems.MEGANIUM_INGOT.get(), has(PCItems.MEGANIUM_INGOT.get())).unlockedBy("has_" + CAItems.ULTIMATE_SWORD.get(), has(CAItems.ULTIMATE_SWORD.get())).unlockedBy("has_" + PCItems.GALACTITE_ROD.get(), has(PCItems.GALACTITE_ROD.get())).save(consumer);
		ShapedRecipeBuilder.shaped(PCItems.MEGANIUM_PICKAXE.get()).define('#', PCItems.MEGANIUM_INGOT.get()).define('$', CAItems.ULTIMATE_PICKAXE.get()).define('%', PCItems.GALACTITE_ROD.get()).pattern("###").pattern(" $ ").pattern(" % ").unlockedBy("has_" + PCItems.MEGANIUM_INGOT.get(), has(PCItems.MEGANIUM_INGOT.get())).unlockedBy("has_" + CAItems.ULTIMATE_PICKAXE.get(), has(CAItems.ULTIMATE_PICKAXE.get())).unlockedBy("has_" + PCItems.GALACTITE_ROD.get(), has(PCItems.GALACTITE_ROD.get())).save(consumer);
		ShapedRecipeBuilder.shaped(PCItems.MEGANIUM_AXE.get()).define('#', PCItems.MEGANIUM_INGOT.get()).define('$', CAItems.ULTIMATE_AXE.get()).define('%', PCItems.GALACTITE_ROD.get()).pattern("##").pattern("#$").pattern(" %").unlockedBy("has_" + PCItems.MEGANIUM_INGOT.get(), has(PCItems.MEGANIUM_INGOT.get())).unlockedBy("has_" + CAItems.ULTIMATE_AXE.get(), has(CAItems.ULTIMATE_AXE.get())).unlockedBy("has_" + PCItems.GALACTITE_ROD.get(), has(PCItems.GALACTITE_ROD.get())).save(consumer);
		ShapedRecipeBuilder.shaped(PCItems.MEGANIUM_SHOVEL.get()).define('#', PCItems.MEGANIUM_INGOT.get()).define('$', CAItems.ULTIMATE_SHOVEL.get()).define('%', PCItems.GALACTITE_ROD.get()).pattern("#").pattern("$").pattern("%").unlockedBy("has_" + PCItems.MEGANIUM_INGOT.get(), has(PCItems.MEGANIUM_INGOT.get())).unlockedBy("has_" + CAItems.ULTIMATE_SHOVEL.get(), has(CAItems.ULTIMATE_SHOVEL.get())).unlockedBy("has_" + PCItems.GALACTITE_ROD.get(), has(PCItems.GALACTITE_ROD.get())).save(consumer);
		ShapedRecipeBuilder.shaped(PCItems.MEGANIUM_HOE.get()).define('#', PCItems.MEGANIUM_INGOT.get()).define('$', CAItems.ULTIMATE_HOE.get()).define('%', PCItems.GALACTITE_ROD.get()).pattern("##").pattern(" $").pattern(" %").unlockedBy("has_" + PCItems.MEGANIUM_INGOT.get(), has(PCItems.MEGANIUM_INGOT.get())).unlockedBy("has_" + CAItems.ULTIMATE_HOE.get(), has(CAItems.ULTIMATE_HOE.get())).unlockedBy("has_" + PCItems.GALACTITE_ROD.get(), has(PCItems.GALACTITE_ROD.get())).save(consumer);
		ShapedRecipeBuilder.shaped(PCItems.MEGANIUM_BATTLE_AXE.get()).define('#', PCItems.MEGANIUM_INGOT.get()).define('$', CAItems.BATTLE_AXE.get()).define('%', PCItems.GALACTITE_ROD.get()).pattern("#$#").pattern("#%#").pattern(" % ").unlockedBy("has_" + PCItems.MEGANIUM_INGOT.get(), has(PCItems.MEGANIUM_INGOT.get())).unlockedBy("has_" + CAItems.BATTLE_AXE.get(), has(CAItems.BATTLE_AXE.get())).unlockedBy("has_" + PCItems.GALACTITE_ROD.get(), has(PCItems.GALACTITE_ROD.get())).save(consumer);
		ShapedRecipeBuilder.shaped(PCItems.MEGANIUM_BOW.get()).define('#', PCItems.MEGANIUM_INGOT.get()).define('$', CAItems.ULTIMATE_BOW.get()).define('%', PCItems.GALACTITE.get()).define('^', Items.STRING).pattern(" #^").pattern("%$^").pattern(" #^").unlockedBy("has_" + PCItems.MEGANIUM_INGOT.get(), has(PCItems.MEGANIUM_INGOT.get())).unlockedBy("has_" + CAItems.ULTIMATE_BOW.get(), has(CAItems.ULTIMATE_BOW.get())).unlockedBy("has_" + PCItems.GALACTITE_ROD.get(), has(PCItems.GALACTITE_ROD.get())).save(consumer);
		ShapedRecipeBuilder.shaped(PCItems.MEGANIUM_FISHING_ROD.get()).define('#', PCItems.MEGANIUM_INGOT.get()).define('$', CAItems.ULTIMATE_FISHING_ROD.get()).define('%', PCItems.GALACTITE_ROD.get()).define('^', Items.STRING).pattern("  #").pattern(" $^").pattern("% ^").unlockedBy("has_" + PCItems.MEGANIUM_INGOT.get(), has(PCItems.MEGANIUM_INGOT.get())).unlockedBy("has_" + CAItems.ULTIMATE_FISHING_ROD.get(), has(CAItems.ULTIMATE_FISHING_ROD.get())).unlockedBy("has_" + PCItems.GALACTITE_ROD.get(), has(PCItems.GALACTITE_ROD.get())).save(consumer);
		ShapedRecipeBuilder.shaped(PCItems.MEGANIUM_HELMET.get()).define('#', PCItems.MEGANIUM_INGOT.get()).define('$', CAItems.ULTIMATE_HELMET.get()).pattern("###").pattern("#$#").unlockedBy("has_" + PCItems.MEGANIUM_INGOT.get(), has(PCItems.MEGANIUM_INGOT.get())).unlockedBy("has_" + CAItems.ULTIMATE_HELMET.get(), has(CAItems.ULTIMATE_HELMET.get())).save(consumer);
		ShapedRecipeBuilder.shaped(PCItems.MEGANIUM_CHESTPLATE.get()).define('#', PCItems.MEGANIUM_INGOT.get()).define('$', CAItems.ULTIMATE_CHESTPLATE.get()).pattern("#$#").pattern("###").pattern("###").unlockedBy("has_" + PCItems.MEGANIUM_INGOT.get(), has(PCItems.MEGANIUM_INGOT.get())).unlockedBy("has_" + CAItems.ULTIMATE_CHESTPLATE.get(), has(CAItems.ULTIMATE_CHESTPLATE.get())).save(consumer);
		ShapedRecipeBuilder.shaped(PCItems.MEGANIUM_LEGGINGS.get()).define('#', PCItems.MEGANIUM_INGOT.get()).define('$', CAItems.ULTIMATE_LEGGINGS.get()).pattern("###").pattern("#$#").pattern("# #").unlockedBy("has_" + PCItems.MEGANIUM_INGOT.get(), has(PCItems.MEGANIUM_INGOT.get())).unlockedBy("has_" + CAItems.ULTIMATE_LEGGINGS.get(), has(CAItems.ULTIMATE_LEGGINGS.get())).save(consumer);
		ShapedRecipeBuilder.shaped(PCItems.MEGANIUM_BOOTS.get()).define('#', PCItems.MEGANIUM_INGOT.get()).define('$', CAItems.ULTIMATE_BOOTS.get()).pattern("#$#").pattern("# #").unlockedBy("has_" + PCItems.MEGANIUM_INGOT.get(), has(PCItems.MEGANIUM_INGOT.get())).unlockedBy("has_" + CAItems.ULTIMATE_BOOTS.get(), has(CAItems.ULTIMATE_BOOTS.get())).save(consumer);

		smeltingNamed(consumer, PCItems.GALACTITE_DUST.get(), PCItems.GALACTITE.get(), 0.1F, 200, "purechaos:" + PCItems.GALACTITE.get() + "_from_smelting_dust");
		blastingNamed(consumer, PCItems.GALACTITE_DUST.get(), PCItems.GALACTITE.get(), 0.1F, 100, "purechaos:" + PCItems.GALACTITE.get() + "_from_blasting_dust");
		smelting(consumer, PCBlocks.MEGANIUM_ORE.get(), PCItems.MEGANIUM_CHUNK.get(), 0.1F, 200);
		blasting(consumer, PCBlocks.MEGANIUM_ORE.get(), PCItems.MEGANIUM_CHUNK.get(), 0.1F, 100);
	}
}
