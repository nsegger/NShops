package iamn5.shops.init;

import iamn5.shops.NShops;
import iamn5.shops.blocks.shop.ShopBlock;
import iamn5.shops.blocks.shop.ShopStorageContainer;
import iamn5.shops.blocks.shop.ShopTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public final class Registration {
    public static final DeferredRegister<Block> BLOCKS = createRegister(ForgeRegistries.BLOCKS);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = createRegister(ForgeRegistries.CONTAINERS);
    public static final DeferredRegister<EntityType<?>> ENTITIES = createRegister(ForgeRegistries.ENTITIES);
    public static final DeferredRegister<Item> ITEMS = createRegister(ForgeRegistries.ITEMS);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = createRegister(ForgeRegistries.TILE_ENTITIES);


    public static void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(modEventBus);
        CONTAINERS.register(modEventBus);
        ENTITIES.register(modEventBus);
        ITEMS.register(modEventBus);
        TILE_ENTITIES.register(modEventBus);
    }

    private static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> createRegister(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, NShops.MOD_ID);
    }

    public static final RegistryObject<Block> SHOP_BLOCK = ModBlocks.register("shop",
            () -> new ShopBlock(AbstractBlock.Properties.create(Material.ROCK)
                    .hardnessAndResistance(1.5f, 12f))
    );

    public static final RegistryObject<ContainerType<ShopStorageContainer>> SHOP_STORAGE_CONTAINER = ModContainers
            .register("shop", ShopStorageContainer::new);


    public static final RegistryObject<TileEntityType<ShopTileEntity>> SHOP_TILE = ModTileEntities
            .register("shop", ShopTileEntity::new, SHOP_BLOCK::get);

}
