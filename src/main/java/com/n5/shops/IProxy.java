package com.n5.shops;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nullable;

public interface IProxy {
    @Nullable
    MinecraftServer getServer();

    @Nullable
    PlayerEntity getPlayer();
}
