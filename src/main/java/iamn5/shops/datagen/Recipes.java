package iamn5.shops.datagen;

import iamn5.shops.NShops;
import iamn5.shops.init.Registration;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(Registration.SHOP_BLOCK.get())
                .patternLine("xxx")
                .patternLine("xcx")
                .patternLine("dxh")
                .key('x', Tags.Items.INGOTS_IRON)
                .key('c', Tags.Items.CHESTS_WOODEN)
                .key('d', Blocks.DISPENSER)
                .key('h', Blocks.HOPPER)
                .setGroup(NShops.MOD_ID)
                .addCriterion("iron_ingot", InventoryChangeTrigger.Instance.forItems(Items.IRON_INGOT))
                .build(consumer);
    }
}
