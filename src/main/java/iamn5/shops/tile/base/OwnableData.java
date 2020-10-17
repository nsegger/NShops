package iamn5.shops.tile.base;

import iamn5.shops.NShops;
import iamn5.shops.tile.interfaces.IOwnable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

import java.lang.ref.WeakReference;
import java.util.UUID;

public class OwnableData implements INBTSerializable<CompoundNBT>, IOwnable {
    private WeakReference<PlayerEntity> ownerReference;
    private UUID ownerID;
    private String ownerName;

    public OwnableData() {
        ownerReference = new WeakReference<>(null);
    }

    public UUID getOwnerID() { return ownerID; }

    @Override
    public void setOwner(LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            ownerID = entity.getUniqueID();
            ownerName = entity.getDisplayName().getString();
            ownerReference = new WeakReference<>((PlayerEntity) entity);
        } else {
            ownerReference = new WeakReference<>(null);
        }
    }

    @Override
    public PlayerEntity getOwner(World world) {
        PlayerEntity owner = ownerReference.get();
        if (owner == null || !owner.isAlive()) {
            owner = findOwner(world);
            ownerReference = new WeakReference<>(owner);
        }

        return owner;
    }

    @Override
    public PlayerEntity findOwner(World world) {
        if (ownerID == null) return null;
        if (world == null) return null;

        return world.getPlayerByUuid(ownerID);
    }

    @Override
    public boolean isOwner(LivingEntity entity) {
        return entity != null && ownerID != null && entity.getUniqueID().equals(ownerID);
    }

    public String getOwnerName() { return ownerName; }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putUniqueId("ownerID", ownerID);

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT tag) {
        ownerID = tag.getUniqueId("ownerID");
    }
}
