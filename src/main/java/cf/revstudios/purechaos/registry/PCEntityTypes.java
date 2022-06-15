package cf.revstudios.purechaos.registry;

import cf.revstudios.purechaos.PureChaos;
import cf.revstudios.purechaos.entity.projectile.MeganiumFishingBobberEntity;
import io.github.chaosawakens.ChaosAwakens;
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
	public static final RegistryObject<EntityType<MeganiumFishingBobberEntity>> MEGANIUM_FISHING_BOBBER = ENTITY_TYPES.register("meganium_fishing_bobber",
			() -> EntityType.Builder.<MeganiumFishingBobberEntity>createNothing(EntityClassification.MISC).noSave().noSummon().setShouldReceiveVelocityUpdates(true)
					.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(5)
					.setCustomClientFactory(MeganiumFishingBobberEntity::new)
					.build(new ResourceLocation(ChaosAwakens.MODID, "meganium_fishing_bobber").toString()));

	private static final List<EntityType<?>> ALL = new ArrayList<>();

	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityType<?>> evt) {
		evt.getRegistry().registerAll(ALL.toArray(new EntityType<?>[0]));
	}
}
