package cf.revstudios.purechaos.items;

import cf.revstudios.purechaos.enums.PCItemTier;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.chaosawakens.api.item.IAutoEnchantable;
import io.github.chaosawakens.api.item.ICATieredItem;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.block.BlockState;
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
import net.minecraftforge.event.ForgeEventFactory;

import java.util.function.Supplier;

public class MeganiumHoeItem extends HoeItem implements IAutoEnchantable, ICATieredItem {
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

	public MeganiumHoeItem(PCItemTier pTier, Supplier<IntValue> configDmg, float pAttackSpeedModifier, Properties pProperties, Supplier<EnchantmentData[]> enchantments) {
		super(pTier, pTier.getAttackDamageMod(), pAttackSpeedModifier, pProperties);
		this.configDmg = configDmg;
		this.attackSpeed = pAttackSpeedModifier;
		this.reach = 0;
		this.enchantments = enchantments;
	}

	public ActionResultType useOn(ItemUseContext context) {
		World curWorld = context.getLevel();
		BlockPos eventPos = context.getClickedPos();
		int hoeUseHook = ForgeEventFactory.onHoeUse(context);
		if (hoeUseHook != 0) {
			return hoeUseHook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;
		} else {
			if (context.getClickedFace() != Direction.DOWN && curWorld.isEmptyBlock(eventPos.above())) {
				BlockState modifiedState = curWorld.getBlockState(eventPos).getToolModifiedState(curWorld, eventPos, context.getPlayer(), context.getItemInHand(), ToolType.HOE);
				if (modifiedState != null) {
					PlayerEntity curPlayer = context.getPlayer();
					curWorld.playSound(curPlayer, eventPos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
					if (!curWorld.isClientSide && curPlayer != null) {
						context.getItemInHand().hurtAndBreak(1, curPlayer, (player) -> {
							player.broadcastBreakEvent(context.getHand());
						});
					}

					for(int x = -1; x < 2; ++x) {
						for(int y = -1; y < 2; ++y) {
							for(int z = -1; z < 2; ++z) {
								BlockPos targetPos = new BlockPos(eventPos.getX() + x, eventPos.getY() + y, eventPos.getZ() + z);
								if (curWorld.isEmptyBlock(targetPos.above())) {
									modifiedState = curWorld.getBlockState(targetPos).getToolModifiedState(curWorld, targetPos, context.getPlayer(), context.getItemInHand(), ToolType.HOE);
									if (modifiedState != null && !curWorld.isClientSide) {
										curWorld.setBlock(targetPos, modifiedState, 11);
									}
								}
							}
						}
					}

					return ActionResultType.sidedSuccess(curWorld.isClientSide);
				}
			}

			return ActionResultType.PASS;
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
