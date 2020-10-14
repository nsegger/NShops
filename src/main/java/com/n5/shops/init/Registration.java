package com.n5.shops.init;

import com.n5.shops.NShops;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
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

        ModBlocks.register();
        ModContainers.register();
        ModTileEntities.register();
    }

    private static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> createRegister(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, NShops.MOD_ID);
    }
}
