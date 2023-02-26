package cf.revstudios.purechaos.client.entity.model;

import cf.revstudios.purechaos.PureChaos;
import cf.revstudios.purechaos.items.extended.MeganiumBattleAxeItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MeganiumBattleAxeItemModel extends AnimatedGeoModel<MeganiumBattleAxeItem> {
	@Override
	public ResourceLocation getModelLocation(MeganiumBattleAxeItem object) {
		return new ResourceLocation(PureChaos.MODID, "geo/meganium_battle_axe.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(MeganiumBattleAxeItem object) {
		return new ResourceLocation(PureChaos.MODID, "textures/item/meganium_battle_axe_model.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(MeganiumBattleAxeItem animatable) {
		return new ResourceLocation(PureChaos.MODID, "animations/dummy.animation.json");
	}
}
