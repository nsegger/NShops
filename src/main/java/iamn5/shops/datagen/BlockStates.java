package iamn5.shops.datagen;

import iamn5.shops.NShops;
import iamn5.shops.init.Registration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class BlockStates extends BlockStateProvider {
    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, NShops.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerShop();
    }

    private void registerShop() {
        ResourceLocation front = new ResourceLocation(NShops.MOD_ID, "block/shop/front");
        ResourceLocation right = new ResourceLocation(NShops.MOD_ID, "block/shop/right");
        ResourceLocation left = new ResourceLocation(NShops.MOD_ID, "block/shop/left");
        ResourceLocation back = new ResourceLocation(NShops.MOD_ID, "block/shop/back");

        BlockModelBuilder modelShop = models().cube("shop", back, back, front, back, left, right);
        orientedBlock(Registration.SHOP_BLOCK.get(), blockState -> modelShop);
    }

    private void orientedBlock(Block block, Function<BlockState, ModelFile> modelFunc) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction dir = state.get(BlockStateProperties.FACING);
                    return ConfiguredModel.builder()
                            .modelFile(modelFunc.apply(state))
                            .rotationX(dir.getAxis() == Direction.Axis.Y ?  dir.getAxisDirection().getOffset() * -90 : 0)
                            .rotationY(dir.getAxis() != Direction.Axis.Y ? ((dir.getHorizontalIndex() + 2) % 4) * 90 : 0)
                            .build();
                });
    }
}
