package iamn5.shops.init;

import iamn5.shops.NShops;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public final class ModBlocks {

    public static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return Registration.BLOCKS.register(name, block);
    }

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        return register(name, block, ModBlocks::item);
    }

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block, Function<RegistryObject<T>, Supplier<? extends BlockItem>> blockItem) {
        RegistryObject<T> registeredBlock = registerNoItem(name, block);
        Registration.ITEMS.register(name, blockItem.apply(registeredBlock));
        return registeredBlock;
    }

    private static <T extends Block> Supplier<? extends BlockItem> item(RegistryObject<T> block) {
        return () -> new BlockItem(block.get(), new Item.Properties().group(NShops.ITEM_GROUP));
    }
}
