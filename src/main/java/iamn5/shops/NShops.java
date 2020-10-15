package iamn5.shops;

import iamn5.shops.init.Registration;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(NShops.MOD_ID)
public class NShops {
    // Directly reference a log4j logger.
    public static final String MOD_ID = "nshops";
    public static final String MOD_NAME = "N5's Shops";

    public static final String RESOURCE_PREFIX = MOD_ID + ":";

    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public static final ItemGroup ITEM_GROUP = new ItemGroup(NShops.MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Registration.SHOP_BLOCK.get());
        }
    };

    public NShops() {
        DistExecutor.safeRunForDist(() -> Proxy.Client::new, () -> Proxy.Server::new);
    }

}
