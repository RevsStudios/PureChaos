package cf.revstudios.purechaos.items.extended;

import cf.revstudios.purechaos.client.entity.render.MeganiumBattleAxeItemRender;
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
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.function.Supplier;

public class MeganiumBattleAxeItem extends AxeItem implements IAnimatable, ICATieredItem {
	private final AnimationFactory factory = new AnimationFactory(this);
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

	public MeganiumBattleAxeItem(PCItemTier pTier, Supplier<IntValue> configDmg, float pAttackSpeedModifier, double reach, Properties pProperties, Supplier<EnchantmentData[]> enchantments) {
		super(pTier, pTier.getAttackDamageMod(), pAttackSpeedModifier, pProperties.setISTER(() -> MeganiumBattleAxeItemRender::new));
		this.configDmg = configDmg;
		this.attackSpeed = pAttackSpeedModifier;
		this.reach = reach;
		this.enchantments = enchantments;
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

	public void registerControllers(AnimationData data) {
	}

	public AnimationFactory getFactory() {
		return this.factory;
	}
}
