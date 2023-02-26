package cf.revstudios.purechaos.client.entity.render;

import cf.revstudios.purechaos.client.entity.model.MeganiumBattleAxeItemModel;
import cf.revstudios.purechaos.items.extended.MeganiumBattleAxeItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class MeganiumBattleAxeItemRender extends GeoItemRenderer<MeganiumBattleAxeItem> {
	public MeganiumBattleAxeItemRender() {
		super(new MeganiumBattleAxeItemModel());
	}
}
