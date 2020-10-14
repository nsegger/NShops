package com.n5.shops;

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

    public static final ItemGroup ITEM_GROUP = new ItemGroup(MOD_NAME) {
        @Override
        public ItemStack createIcon() {
            return null;
        }
    };

    public NShops() {
        DistExecutor.safeRunForDist(() -> Proxy.Client::new, () -> Proxy.Server::new);
    }

}
