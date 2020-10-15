package iamn5.shops.blocks.shop;

import iamn5.shops.init.ModContainers;
import iamn5.shops.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ShopStorageContainer extends Container {
    private static final int X_SPACING = 18;
    private static final int Y_SPACING = 18;
    private static final int HOTBAR_SPACING = 4;

    private static final int OUTPUT_X = 8;
    private static final int OUTPUT_Y = 18;
    private static final int INPUT_X = 179;
    private static final int[] INPUT_Y = {14, 65};


    private final IInventory inventory;

    public ShopStorageContainer(int windowId, PlayerInventory playerInventory, PacketBuffer extraData) {
        this(windowId, playerInventory, new Inventory(extraData.readInt()));
    }

    public ShopStorageContainer(int windowId, PlayerInventory playerInventory, IInventory inventory) {
        super(Registration.SHOP_STORAGE_CONTAINER.get(), windowId);
        this.inventory = inventory;

        addSlot(new Slot(inventory, 0, INPUT_X, INPUT_Y[0]) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return !inventory.getStackInSlot(1).isItemEqual(stack);
            }
        });

        addSlot(new Slot(inventory, 1, INPUT_X, INPUT_Y[1]) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return !inventory.getStackInSlot(0).isItemEqual(stack);
            }
        });

        // Builds "chest" GUI from index 2 to 28 = 27 stacks
        int columns = 9;
        int rows = ShopTileEntity.OUTPUT_SLOTS.length / columns;
        IntStream.range(0, rows).forEach(row -> {
            int y = OUTPUT_Y + (Y_SPACING * row);

            IntStream.range(0, columns).forEach(index -> {
                int x = OUTPUT_X + (X_SPACING * index);

                addSlot(new SlotStorage(inventory, ShopTileEntity.OUTPUT_SLOTS[index], x, y));
            });
        });

        createPlayerSlots(playerInventory,8, 85).forEach(this::addSlot);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return inventory.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 29) {
                // Getting items out of our storage.
                if (!mergeItemStack(itemstack1, 29, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(itemstack1, 2, 29, false)) {
                    return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    private ArrayList<Slot> createPlayerSlots(PlayerInventory playerInventory ,int startX, int startY) {
        ArrayList<Slot> arrayList = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(0);

        // Hotbar slots
        IntStream.range(0, 9).forEach(index -> {
            int x = startX + (X_SPACING * index);
            int y = startY + HOTBAR_SPACING + (Y_SPACING * 3);

            arrayList.add(new Slot(playerInventory, count.get(), x, y));
            count.getAndIncrement();
        });

        // Inventory slots
        IntStream.range(0, 3).forEach(row -> {
            int y = startY + (Y_SPACING * row);

            IntStream.range(0, 9).forEach(index -> {
                int x = startX + (X_SPACING * index);

                arrayList.add(new Slot(playerInventory, count.get(), x, y));
                count.getAndIncrement();
            });
        });



        return arrayList;
    }


    static class SlotStorage extends Slot {

        public SlotStorage(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return true;
        }
    }
}
