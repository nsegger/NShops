package com.n5.shops.init;

import com.n5.shops.blocks.shop.ShopTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public final class ModTileEntities {
    public static final RegistryObject<TileEntityType<ShopTileEntity>> SHOP = register("shop", ShopTileEntity::new, ModBlocks.SHOP.get());

    public static void register() {};

    private static <T extends LockableTileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<T> factory, Block... blocks) {
        return Registration.TILE_ENTITIES.register(name, () -> {
            //noinspection ConstantConditions - null in build
            return TileEntityType.Builder.create(factory, blocks).build(null);
        });
    }
}
