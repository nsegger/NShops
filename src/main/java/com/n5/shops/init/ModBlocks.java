package com.n5.shops.init;

import com.n5.shops.NShops;
import com.n5.shops.blocks.shop.ShopBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public final class ModBlocks {
    public static final RegistryObject<Block> SHOP = register("shop",
            () -> new ShopBlock(AbstractBlock.Properties.create(Material.ROCK)
                    .hardnessAndResistance(1.5f, 12f))
    );

    public static void register() {}

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return Registration.BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        return register(name, block, ModBlocks::item);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block, Function<RegistryObject<T>, Supplier<? extends BlockItem>> blockItem) {
        RegistryObject<T> registeredBlock = registerNoItem(name, block);
        Registration.ITEMS.register(name, blockItem.apply(registeredBlock));
        return registeredBlock;
    }

    private static <T extends Block> Supplier<? extends BlockItem> item(RegistryObject<T> block) {
        return () -> new BlockItem(block.get(), new Item.Properties().group(NShops.ITEM_GROUP));
    }
}
