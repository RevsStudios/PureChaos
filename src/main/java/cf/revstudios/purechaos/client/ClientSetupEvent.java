package cf.revstudios.purechaos.client;

import cf.revstudios.purechaos.registry.PCItems;
import io.github.chaosawakens.client.entity.render.*;
import io.github.chaosawakens.common.entity.EntEntity;
import io.github.chaosawakens.common.items.UltimateFishingRodItem;
import io.github.chaosawakens.common.registry.*;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientSetupEvent {
    public static void register() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetupEvent::onFMLClientSetupEvent);
    }

    public static void onFMLClientSetupEvent(FMLClientSetupEvent event) {
        ItemModelsProperties.register(PCItems.MEGANIUM_BOW.get(), new ResourceLocation("pull"),
                (stack, world, living) -> {
                    if (living == null) {
                        return 0.0F;
                    } else {
                        return living.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - living.getUseItemRemainingTicks()) / 8.0F;
                    }
                });
        ItemModelsProperties.register(PCItems.MEGANIUM_BOW.get(), new ResourceLocation("pulling"),
                (stack, world, living) -> living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F);
        ItemModelsProperties.register(PCItems.MEGANIUM_FISHING_ROD.get(), new ResourceLocation("cast"), (p_239422_0_, p_239422_1_, p_239422_2_) -> {
            if (p_239422_2_ == null) {
                return 0.0F;
            } else {
                boolean flag = p_239422_2_.getMainHandItem() == p_239422_0_;
                boolean flag1 = p_239422_2_.getOffhandItem() == p_239422_0_;
                if (p_239422_2_.getMainHandItem().getItem() instanceof UltimateFishingRodItem) {
                    flag1 = false;
                }

                return (flag || flag1) && p_239422_2_ instanceof PlayerEntity && ((PlayerEntity) p_239422_2_).fishing != null ? 1.0F : 0.0F;
            }
        });
    }
}
