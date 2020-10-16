package iamn5.shops.tile.interfaces;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface IOwnable {

    void setOwner(LivingEntity owner);

    PlayerEntity getOwner(World world);

    PlayerEntity findOwner(World world);

    boolean isOwner(LivingEntity entity);

    String getOwnerName();
}
