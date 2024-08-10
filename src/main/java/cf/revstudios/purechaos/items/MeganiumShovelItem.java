package cf.revstudios.purechaos.items;

import cf.revstudios.purechaos.enums.PCItemTier;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.chaosawakens.api.item.IAutoEnchantable;
import io.github.chaosawakens.api.item.ICATieredItem;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.Lazy;

import java.util.function.Supplier;

public class MeganiumShovelItem extends ShovelItem implements IAutoEnchantable, ICATieredItem {
	private final Supplier<EnchantmentData[]> enchantments;
	private Supplier<IntValue> configDmg;
	private float attackSpeed;
	private double reach;
	private Lazy<? extends Multimap<Attribute, AttributeModifier>> attributeModMapLazy = Lazy.of(() -> {
		ImmutableMultimap.Builder<Attribute, AttributeModifier> attrModMapBuilder = ImmutableMultimap.builder();

		attrModMapBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", getActualAttackDamage().get().get() - 1, AttributeModifier.Operation.ADDITION));
		attrModMapBuilder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", getAttackSpeed(), AttributeModifier.Operation.ADDITION));
		if (ForgeMod.REACH_DISTANCE.isPresent()) attrModMapBuilder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ICATieredItem.getReachUUIDMod(), "Weapon modifier", getReach(), AttributeModifier.Operation.ADDITION));

		return attrModMapBuilder.build();
	});

	public MeganiumShovelItem(PCItemTier pTier, Supplier<IntValue> configDmg, float pAttackSpeedModifier, Properties pProperties, Supplier<EnchantmentData[]> enchantments) {
		super(pTier, pTier.getAttackDamageMod(), pAttackSpeedModifier, pProperties);
		this.configDmg = configDmg;
		this.attackSpeed = pAttackSpeedModifier;
		this.reach = 0;
		this.enchantments = enchantments;
	}

	public ActionResultType useOn(ItemUseContext ctx) {
		World curWorld = ctx.getLevel();
		BlockPos eventPos = ctx.getClickedPos();
		BlockState clickedPos = curWorld.getBlockState(eventPos);
		PlayerEntity curPlayer = ctx.getPlayer();
		if (ctx.getClickedFace() == Direction.DOWN) {
			return ActionResultType.PASS;
		} else {
			if (!curWorld.isClientSide && curPlayer != null) {
				ctx.getItemInHand().hurtAndBreak(1, curPlayer, (curTool) -> {
					curTool.broadcastBreakEvent(ctx.getHand());
				});
			}

			for(int x = -1; x < 2; ++x) {
				for(int y = -1; y < 2; ++y) {
					for(int z = -1; z < 2; ++z) {
						BlockPos targetPos = new BlockPos(eventPos.getX() + x, eventPos.getY() + y, eventPos.getZ() + z);
						BlockState modifiedState = clickedPos.getToolModifiedState(curWorld, targetPos, curPlayer, ctx.getItemInHand(), ToolType.SHOVEL);
						BlockState targetState = null;
						if (curWorld.isEmptyBlock(targetPos.above()) && curWorld.getBlockState(targetPos).is(clickedPos.getBlock())) {
							if (modifiedState != null) {
								curWorld.playSound(curPlayer, targetPos, SoundEvents.SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
								targetState = modifiedState;
							} else if (clickedPos.getBlock() instanceof CampfireBlock && clickedPos.getValue(CampfireBlock.LIT)) {
								if (!curWorld.isClientSide()) {
									curWorld.levelEvent(null, 1009, targetPos, 0);
								}

								CampfireBlock.dowse(curWorld, targetPos, clickedPos);
								targetState = clickedPos.setValue(CampfireBlock.LIT, Boolean.FALSE);
							}

							if (targetState != null && !curWorld.isClientSide) {
								curWorld.setBlock(targetPos, targetState, 11);
							}
						}
					}
				}
			}

			return ActionResultType.sidedSuccess(curWorld.isClientSide);
		}
	}

	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.allowdedIn(group)) {
			ItemStack swordStack = new ItemStack(this);

			if (CAConfigManager.MAIN_COMMON.enableAutoEnchanting.get()) {
				for (EnchantmentData curEnch : enchantments.get()) {
					swordStack.enchant(curEnch.enchantment, curEnch.level);
				}
			}

			items.add(swordStack);
		}
	}

	public void onCraftedBy(ItemStack itemStack, World world, PlayerEntity playerEntity) {
		if (CAConfigManager.MAIN_COMMON.enableAutoEnchanting.get()) {
			for (EnchantmentData curEnch : enchantments.get()) {
				if (curEnch.level == 0) itemStack.enchant(curEnch.enchantment, curEnch.level);
			}
		}
	}

	public boolean isFoil(ItemStack stack) {
		return CAConfigManager.MAIN_COMMON.enableAutoEnchanting.get() && super.isFoil(stack) || super.isFoil(stack);
	}

	public EnchantmentData[] getEnchantments() {
		return this.enchantments.get();
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
		return slot.equals(EquipmentSlotType.MAINHAND) ? attributeModMapLazy.get() : super.getAttributeModifiers(slot, stack);
	}

	@Override
	public Supplier<IntValue> getActualAttackDamage() {
		return configDmg;
	}

	@Override
	public void setAttackDamage(Supplier<IntValue> attackDamage) {
		this.configDmg = attackDamage;
	}

	@Override
	public float getAttackSpeed() {
		return attackSpeed;
	}

	@Override
	public void setAttackSpeed(float attackSpeed) {
		this.attackSpeed = attackSpeed - 2.4F;
	}

	@Override
	public double getReach() {
		return reach;
	}

	@Override
	public void setReach(double reach) {
		this.reach = reach;
	}

	@Override
	public double getAttackKnockback() {
		return 0;
	}

	@Override
	public void setAttackKnockback(double attackKnockback) {

	}

	@Override
	public void setAttributeModifiers(Lazy<? extends Multimap<Attribute, AttributeModifier>> attributeModMapLazy) {
		this.attributeModMapLazy = attributeModMapLazy;
	}

	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
		EntityUtil.applyReachModifierToEntity(entity, stack, (float) this.getActualAttackDamage().get().get());
		return super.onEntitySwing(stack, entity);
	}
}
