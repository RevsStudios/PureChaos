package cf.revstudios.purechaos.items;

import cf.revstudios.purechaos.entity.projectile.MeganiumFishingBobberEntity;
import io.github.chaosawakens.api.item.IAutoEnchantable;
import io.github.chaosawakens.common.registry.CADimensions;
import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class MeganiumFishingRodItem extends FishingRodItem implements IAutoEnchantable {
	private final Supplier<EnchantmentData[]> enchantments;

	public MeganiumFishingRodItem(Properties builder, Supplier<EnchantmentData[]> enchantments) {
		super(builder);
		this.enchantments = enchantments;
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.allowdedIn(group)) {
			ItemStack stack = new ItemStack(this);
			if (CAConfigManager.MAIN_COMMON.enableAutoEnchanting.get())
				for (EnchantmentData enchant : enchantments.get()) {
					stack.enchant(enchant.enchantment, enchant.level);
				}
			items.add(stack);
		}
	}

	@Override
	public void onCraftedBy(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		if (CAConfigManager.MAIN_COMMON.enableAutoEnchanting.get())
			for (EnchantmentData enchant : enchantments.get()) {
				stack.enchant(enchant.enchantment, enchant.level);
			}
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return CAConfigManager.MAIN_COMMON.enableAutoEnchanting.get() && super.isFoil(stack) || super.isFoil(stack);
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack curHeldStack = playerIn.getItemInHand(handIn);
		int lureMod;
		if (playerIn.fishing != null) {
			if (!worldIn.isClientSide) {
				lureMod = playerIn.fishing.retrieve(curHeldStack);
				curHeldStack.hurtAndBreak(lureMod, playerIn, (player) -> player.broadcastBreakEvent(handIn));
			}

			worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.FISHING_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		} else {
			worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.FISHING_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
			if (!worldIn.isClientSide) {
				lureMod = EnchantmentHelper.getFishingSpeedBonus(curHeldStack) + 90;
				int luckMod = EnchantmentHelper.getFishingLuckBonus(curHeldStack) + 75;
				if(worldIn.dimension() == CADimensions.MINING_PARADISE) {
					lureMod = EnchantmentHelper.getFishingSpeedBonus(curHeldStack) + 30;
					luckMod = EnchantmentHelper.getFishingLuckBonus(curHeldStack) + 60;
				}
				worldIn.addFreshEntity(new MeganiumFishingBobberEntity(playerIn, worldIn, luckMod, lureMod));
			}

			playerIn.awardStat(Stats.ITEM_USED.get(this));
		}

		return ActionResult.sidedSuccess(curHeldStack, worldIn.isClientSide());
	}

	public EnchantmentData[] getEnchantments() {
		return this.enchantments.get();
	}
}
