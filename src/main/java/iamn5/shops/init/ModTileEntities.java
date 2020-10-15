package iamn5.shops.init;

import net.minecraft.block.Block;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public final class ModTileEntities {

    public static <T extends LockableTileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<T> factory, Supplier<Block> blocks) {
        return Registration.TILE_ENTITIES.register(name, () -> {
            //noinspection ConstantConditions - null in build
            return TileEntityType.Builder.create(factory, blocks.get()).build(null);
        });
    }
}
