package cf.revstudios.purechaos;

import cf.revstudios.purechaos.client.ClientSetupEvent;
import cf.revstudios.purechaos.config.PCConfig;
import cf.revstudios.purechaos.data.*;
import cf.revstudios.purechaos.registry.PCBlocks;
import cf.revstudios.purechaos.registry.PCEntityTypes;
import cf.revstudios.purechaos.registry.PCItems;
import cf.revstudios.purechaos.worldgen.BiomeLoadEventSubscriber;
import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.forgespi.language.IModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

import java.util.Optional;

@Mod(PureChaos.MODID)
public class PureChaos {
	public static final String MODID = "purechaos";
	public static final String MODNAME = "Pure Chaos";
	public static final Logger LOGGER = LogManager.getLogger();
	public static ArtifactVersion VERSION = null;

	public PureChaos() {
		GeckoLibMod.DISABLE_IN_DEV = true;
		GeckoLib.initialize();

		Optional<? extends ModContainer> opt = ModList.get().getModContainerById(MODID);
		if (opt.isPresent()) {
			IModInfo modInfo = opt.get().getModInfo();
			VERSION = modInfo.getVersion();
		} else LOGGER.warn("Cannot get version from mod info");

		LOGGER.debug(MODNAME + " is an Addon for: " + ChaosAwakens.MODNAME + "!");
		LOGGER.debug("The Mod Version of " + MODNAME + " is: " + VERSION);
		LOGGER.debug("The Mod ID of " + MODNAME + " is: " + MODID);
		LOGGER.debug("The Mod ID of " + ChaosAwakens.MODNAME + " is: " + ChaosAwakens.MODID);

		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

		//Register to the mod event bus
		eventBus.addListener(this::gatherData);

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, PCConfig.COMMON_SPEC);

		if (FMLEnvironment.dist == Dist.CLIENT) eventBus.addListener(ClientSetupEvent::onFMLClientSetupEvent);

		//Register the deferred registers
		PCBlocks.ITEM_BLOCKS.register(eventBus);
		PCBlocks.BLOCKS.register(eventBus);
		PCEntityTypes.ENTITY_TYPES.register(eventBus);
		PCItems.ITEMS.register(eventBus);

		//Register to the forge event bus
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addListener(EventPriority.HIGH, BiomeLoadEventSubscriber::onBiomeLoadingEvent);
		forgeBus.register(this);
	}

	private void gatherData(final GatherDataEvent event) {
		DataGenerator dataGenerator = event.getGenerator();
		final ExistingFileHelper existing = event.getExistingFileHelper();
		if (event.includeClient()) {
			dataGenerator.addProvider(new PCBlockModelProvider(dataGenerator, MODID, existing));
			dataGenerator.addProvider(new PCItemModelGenerator(dataGenerator, existing));
			dataGenerator.addProvider(new PCBlockStateProvider(dataGenerator, MODID, existing));
		}
		if (event.includeServer()) {
			dataGenerator.addProvider(new PCLootTableProvider(dataGenerator));
			dataGenerator.addProvider(new PCRecipeProvider(dataGenerator));
		}
	}
}
