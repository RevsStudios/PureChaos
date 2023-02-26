package cf.revstudios.purechaos.registry;

import cf.revstudios.purechaos.PureChaos;
import cf.revstudios.purechaos.items.MeganiumPickaxeItem;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PCGlobalLootModifiers {
	public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, PureChaos.MODID);

	public static final RegistryObject<MeganiumPickaxeItem.MeganiumAutoSmeltingModifier.Serializer> MEGANIUM_PICKAXE_SMELTING = LOOT_MODIFIERS.register("meganium_pickaxe_smelting", MeganiumPickaxeItem.MeganiumAutoSmeltingModifier.Serializer::new);
}
