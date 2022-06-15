package cf.revstudios.purechaos.items;

import cf.revstudios.purechaos.config.PCConfig;
import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.entity.projectile.UltimateArrowEntity;
import io.github.chaosawakens.common.items.UltimateBowItem;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class MeganiumBowItem extends UltimateBowItem {
	public MeganiumBowItem(Properties builderIn, EnchantmentData[] enchantments) {
		super(builderIn, enchantments);
	}

	public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity)entityLiving;
			if (ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, this.getUseDuration(stack) - timeLeft, true) < 0) {
				return;
			}

			if (!worldIn.isClientSide) {
				AbstractArrowEntity arrowEntity = new UltimateArrowEntity(worldIn, entityLiving);
				arrowEntity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, 3.0F, 0.0F);
				arrowEntity.setCritArrow(true);
				arrowEntity.setSecondsOnFire(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0 ? 250 : 75);
				int powerLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
				if (!(Boolean) CAConfig.COMMON.enableAutoEnchanting.get()) {
					arrowEntity.setBaseDamage(PCConfig.COMMON.meganiumBowArrowBaseDamage.get() + (double)powerLevel * PCConfig.COMMON.meganiumBowArrowDamageMultiplier.get() + 2.0D);
				} else {
					arrowEntity.setBaseDamage(PCConfig.COMMON.meganiumBowArrowBaseDamage.get() + 3.0D);
				}

				int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
				arrowEntity.setKnockback(!(Boolean)CAConfig.COMMON.enableAutoEnchanting.get() ? k + 1 : 1);
				if (!playerentity.isCreative()) {
					stack.hurtAndBreak(1, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
				}

				worldIn.addFreshEntity(arrowEntity);
				worldIn.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
				playerentity.awardStat(Stats.ITEM_USED.get(this));
			}
		}

	}
}
