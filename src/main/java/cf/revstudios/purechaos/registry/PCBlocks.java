package cf.revstudios.purechaos.registry;

import cf.revstudios.purechaos.PureChaos;
import io.github.chaosawakens.common.blocks.CAOreBlock;
import io.github.chaosawakens.common.registry.CAItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = PureChaos.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PCBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PureChaos.MODID);
    public static final DeferredRegister<Item> ITEM_BLOCKS = DeferredRegister.create(ForgeRegistries.ITEMS, PureChaos.MODID);

    public static final RegistryObject<CAOreBlock> GALACTITE_ORE = registerBlock("galactite_ore", () -> new CAOreBlock(Block.Properties.of(Material.STONE).strength(9F, 3.5F).harvestLevel(5).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE)), CAItemGroups.BLOCKS);

    public static final RegistryObject<CAOreBlock> MEGANIUM_ORE = registerMeganiumBlock("meganium_ore", () -> new CAOreBlock(Block.Properties.of(Material.STONE).strength(12F, 3.5F).harvestLevel(6).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.STONE)), CAItemGroups.BLOCKS);
    public static final RegistryObject<Block> MEGANIUM_BLOCK = registerMeganiumBlock("meganium_block", () -> new Block(Block.Properties.copy(Blocks.NETHERITE_BLOCK).harvestLevel(6).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)), CAItemGroups.BLOCKS);

    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup) {
        return registerBlock(name, supplier, itemGroup, true);
    }

    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup, boolean generateItem) {
        return registerBlock(name, supplier, itemGroup, 64, generateItem);
    }

    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup, int stackSize, boolean generateItem) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        if (generateItem)
            ITEM_BLOCKS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(itemGroup).stacksTo(stackSize)));
        return block;
    }
    public static <B extends Block> RegistryObject<B> registerMeganiumBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup) {
        return registerMeganiumBlock(name, supplier, itemGroup, true);
    }

    public static <B extends Block> RegistryObject<B> registerMeganiumBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup, boolean generateItem) {
        return registerMeganiumBlock(name, supplier, itemGroup, 64, generateItem);
    }

    public static <B extends Block> RegistryObject<B> registerMeganiumBlock(String name, Supplier<? extends B> supplier, ItemGroup itemGroup, int stackSize, boolean generateItem) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        if (generateItem)
            ITEM_BLOCKS.register(name, () -> new BlockItem(block.get(), new Item.Properties().rarity(PCItems.RARITY_MEGANIUM).tab(itemGroup).stacksTo(stackSize)));
        return block;
    }
}
