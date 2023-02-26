package cf.revstudios.purechaos.items;

import com.google.gson.JsonObject;
import io.github.chaosawakens.common.items.UltimatePickaxeItem;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MeganiumPickaxeItem extends UltimatePickaxeItem {
	public MeganiumPickaxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, EnchantmentData[] enchantments) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn, enchantments);
	}

	public boolean isFireResistant() {
		return true;
	}

	public float getXpRepairRatio(ItemStack stack) {
		return 20.0F;
	}

	public static class MeganiumAutoSmeltingModifier extends LootModifier {
		public MeganiumAutoSmeltingModifier(ILootCondition[] conditionsIn) {
			super(conditionsIn);
		}

		protected final ItemStack getSmeltedOutput(LootContext context, ItemStack stack) {
			return context.getLevel() != null ? (ItemStack)context.getLevel().getRecipeManager().getRecipeFor(IRecipeType.SMELTING, new Inventory(new ItemStack[]{stack}), context.getLevel()).map(AbstractCookingRecipe::getResultItem).filter((itemStack) -> {
				return !itemStack.isEmpty();
			}).map((itemStack) -> {
				return copyStackWithSize(itemStack, stack.getCount());
			}).orElse(stack) : stack;
		}

		@Nonnull
		public static ItemStack copyStackWithSize(@Nonnull ItemStack itemStack, int size) {
			if (size == 0) {
				return ItemStack.EMPTY;
			} else {
				ItemStack copy = itemStack.copy();
				copy.setCount(size);
				return copy;
			}
		}

		@Nonnull
		protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
			ArrayList<ItemStack> stackArrayList = new ArrayList();
			generatedLoot.forEach((stack) -> {
				stackArrayList.add(this.getSmeltedOutput(context, stack));
			});
			return stackArrayList;
		}

		public static class Serializer extends GlobalLootModifierSerializer<MeganiumAutoSmeltingModifier> {
			public Serializer() {
			}

			public MeganiumAutoSmeltingModifier read(ResourceLocation name, JsonObject json, ILootCondition[] conditionsIn) {
				return new MeganiumAutoSmeltingModifier(conditionsIn);
			}

			public JsonObject write(MeganiumAutoSmeltingModifier instance) {
				return this.makeConditions(instance.conditions);
			}
		}
	}
}
