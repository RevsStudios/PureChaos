package cf.revstudios.purechaos.registry;

import cf.revstudios.purechaos.PureChaos;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.projectile.UltimateFishingBobberEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class PCEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, PureChaos.MODID);

    // Projectiles
    public static final RegistryObject<EntityType<UltimateFishingBobberEntity>> ULTIMATE_FISHING_BOBBER = ENTITY_TYPES.register("ultimate_fishing_bobber",
            () -> EntityType.Builder.<UltimateFishingBobberEntity>createNothing(EntityClassification.MISC).noSave().noSummon().setShouldReceiveVelocityUpdates(true)
                    .sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(5)
                    .setCustomClientFactory(UltimateFishingBobberEntity::new)
                    .build(new ResourceLocation(ChaosAwakens.MODID, "ultimate_fishing_bobber").toString()));

    private static final List<EntityType<?>> ALL = new ArrayList<>();

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> evt) {
        evt.getRegistry().registerAll(ALL.toArray(new EntityType<?>[0]));
    }
}
