package iamn5.shops.containers.base;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class BaseContainer extends Container {
    protected static final int X_SPACING = 18;
    protected static final int Y_SPACING = 18;
    protected static final int HOTBAR_SPACING = 4;
    protected static final int COLUMNS = 9;

    protected final IInventory inventory;

    protected BaseContainer(@Nullable ContainerType<?> type, int id, IInventory inventory) {
        super(type, id);
        this.inventory = inventory;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return inventory.isUsableByPlayer(playerIn);
    }

    protected ArrayList<Slot> createPlayerSlots(PlayerInventory playerInventory , int startX, int startY) {
        ArrayList<Slot> arrayList = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(0);

        // Hotbar slots
        IntStream.range(0, 9).forEach(index -> {
            int x = startX + (X_SPACING * index);
            int y = startY + HOTBAR_SPACING + (Y_SPACING * 3);

            arrayList.add(new Slot(playerInventory, count.getAndIncrement(), x, y));
        });

        // Inventory slots
        IntStream.range(0, 3).forEach(row -> {
            int y = startY + (Y_SPACING * row);

            IntStream.range(0, 9).forEach(index -> {
                int x = startX + (X_SPACING * index);

                arrayList.add(new Slot(playerInventory, count.getAndIncrement(), x, y));
            });
        });

        return arrayList;
    }
}
