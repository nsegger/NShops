package iamn5.shops.datagen;

import iamn5.shops.NShops;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if (event.includeServer()) {
            NShops.LOGGER.debug("Server gatherData");
            generator.addProvider(new LootTables(generator));
            generator.addProvider(new Recipes(generator));
        } else if (event.includeClient()) {
            NShops.LOGGER.debug("Client gatherData");
            generator.addProvider(new BlockStates(generator, existingFileHelper));
            generator.addProvider(new Items(generator, existingFileHelper));
        }
    }
}
