package iamn5.shops.datagen;

import iamn5.shops.NShops;
import iamn5.shops.init.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStates extends BlockStateProvider {
    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, NShops.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerShop();
    }

    private void registerShop() {
        ResourceLocation front = new ResourceLocation(NShops.MOD_ID, "textures/block/shop/front");
        ResourceLocation right = new ResourceLocation(NShops.MOD_ID, "textures/block/shop/right");
        ResourceLocation left = new ResourceLocation(NShops.MOD_ID, "textures/block/shop/left");
        ResourceLocation back = new ResourceLocation(NShops.MOD_ID, "textures/block/shop/back");

        BlockModelBuilder modelShop = models().cube("shop", back, back, front, back, left, right);
        directionalBlock(Registration.SHOP_BLOCK.get(), blockState -> modelShop);
    }
}
