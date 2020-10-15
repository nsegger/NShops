package iamn5.shops;

import iamn5.shops.init.ModContainers;
import iamn5.shops.init.Registration;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nullable;

public class Proxy implements IProxy {
    @Nullable private static MinecraftServer server;

    public Proxy() {
        Registration.register();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(Proxy::commonSetup);
        modEventBus.addListener(Proxy::enqueueIMC);
        modEventBus.addListener(Proxy::processIMC);
    }

    private static void commonSetup(final FMLCommonSetupEvent event) {}

    private static void enqueueIMC(final InterModEnqueueEvent event) {}

    private static void processIMC(final InterModProcessEvent event) {}

    @Nullable
    @Override
    public MinecraftServer getServer() {
        return server;
    }

    @Nullable
    @Override
    public PlayerEntity getPlayer() {
        return null;
    }

    static class Client extends Proxy {
        Client() {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(Client::clientSetup);
        }

        private static void clientSetup(final FMLClientSetupEvent event) {
            NShops.LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);

            ModContainers.registerScreens(event);
        }

        @Nullable
        @Override
        public PlayerEntity getPlayer() {
            return Minecraft.getInstance().player;
        }
    }

    static class Server extends Proxy {
        Server() {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(Server::serverSetup);
        }

        private static void serverSetup(FMLServerStartingEvent event) {
            NShops.LOGGER.info("HELLO from server starting");
        }
    }
}
