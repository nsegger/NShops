package iamn5.shops.datagen;

import iamn5.shops.NShops;
import iamn5.shops.init.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class Items extends ItemModelProvider {
    public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, NShops.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(Registration.SHOP_BLOCK.get().getRegistryName().getPath(), new ResourceLocation(NShops.MOD_ID, "block/shop"));
    }
}
