package iamn5.shops.blocks.interfaces;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public interface IHasTile<T extends TileEntity> {

    TileEntityType<? extends T> getTileType();
}
