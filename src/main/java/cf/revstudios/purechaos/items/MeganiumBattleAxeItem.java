package cf.revstudios.purechaos.items;

import cf.revstudios.purechaos.enums.PCItemTier;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.chaosawakens.api.IAutoEnchantable;
import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.util.EnumUtils;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.Lazy;

import java.util.UUID;

public class MeganiumBattleAxeItem extends AxeItem implements IAutoEnchantable, IVanishable, IUtilityHelper {
	public static final UUID REACH_MODIFIER = UUID.fromString("4B3447EE-EC65-11EC-8EA0-0242AC120002");
	public static final UUID KB_MODIFIER = UUID.fromString("516858D0-EC65-11EC-8EA0-0242AC120002");
	public static int attackDamage;
	public static int attackSpeed;
	public static double attackReach;
	public static double attackKnockback;
	private final EnchantmentData[] enchantments;
	public static Lazy<? extends Multimap<Attribute, AttributeModifier>> LAZY = Lazy.of(() -> {
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
		if (ForgeMod.REACH_DISTANCE.isPresent()) {
			builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(REACH_MODIFIER, "Weapon modifier", attackReach, AttributeModifier.Operation.ADDITION));
		}

		builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(KB_MODIFIER, "Weapon modifier", attackKnockback, AttributeModifier.Operation.ADDITION));
		Multimap<Attribute, AttributeModifier> map = builder.build();
		return map;
	});

	public MeganiumBattleAxeItem(PCItemTier tierIn, int attackDamageIn, float attackSpeedIn, double attackReachIn, double attackKnockbackIn, Item.Properties builderIn, EnchantmentData[] enchantments) {
		super(tierIn, (float)attackDamage, attackSpeedIn, builderIn);
		attackDamage = (int)((float)attackDamageIn + tierIn.getAttackDamageBonus());
		attackSpeed = (int)attackSpeedIn;
		attackReach = attackReachIn;
		attackKnockback = attackKnockbackIn;
		this.enchantments = enchantments;
	}

	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
		return slot == EquipmentSlotType.MAINHAND ? LAZY.get() : super.getAttributeModifiers(slot, stack);
	}

	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
		double reach = entity.getAttributeValue(ForgeMod.REACH_DISTANCE.get());
		double reachSqr = reach * reach;
		World world = entity.level;
		Vector3d viewVec = entity.getViewVector(1.0F);
		Vector3d eyeVec = entity.getEyePosition(1.0F);
		Vector3d targetVec = eyeVec.add(viewVec.x * reach, viewVec.y * reach, viewVec.z * reach);
		AxisAlignedBB bb = entity.getBoundingBox().expandTowards(viewVec.scale(reach)).inflate(4.0, 4.0, 4.0);
		EntityRayTraceResult result = ProjectileHelper.getEntityHitResult(world, entity, eyeVec, targetVec, bb, EntityPredicates.NO_CREATIVE_OR_SPECTATOR);
		if (result != null && result.getEntity() instanceof LivingEntity) {
			LivingEntity target = (LivingEntity)result.getEntity();
			double distanceToTargetSqr = entity.distanceToSqr(target);
			boolean resultBool = (result != null ? target : null) != null;
			if (resultBool && entity instanceof PlayerEntity && reachSqr >= distanceToTargetSqr) {
				target.hurt(DamageSource.playerAttack((PlayerEntity)entity), (float)attackDamage);
				this.hurtEnemy(stack, target, entity);
			}

			return super.onEntitySwing(stack, entity);
		} else {
			return false;
		}
	}

	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.allowdedIn(group)) {
			ItemStack stack = new ItemStack(this);
			if (CACommonConfig.COMMON.enableAutoEnchanting.get()) {
				EnchantmentData[] var4 = this.enchantments;
				int var5 = var4.length;

				for(int var6 = 0; var6 < var5; ++var6) {
					EnchantmentData enchant = var4[var6];
					stack.enchant(enchant.enchantment, enchant.level);
				}
			}

			items.add(stack);
		}

	}

	public boolean isFoil(ItemStack stack) {
		return CACommonConfig.COMMON.enableAutoEnchanting.get() && super.isFoil(stack) || super.isFoil(stack);
	}

	public EnchantmentData[] enchantments() {
		return this.enchantments;
	}
}
