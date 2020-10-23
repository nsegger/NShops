package iamn5.shops.containers;

import iamn5.shops.blocks.shop.ShopTileEntity;
import iamn5.shops.containers.base.BaseContainer;
import iamn5.shops.init.Registration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ShopStorageContainer extends BaseContainer {
    private static final int OUTPUT_X = 8;
    private static final int OUTPUT_Y = 18;
    public static final int INPUT_X = 179;
    public static final int[] INPUT_Y = {14, 65};

    private static final int ROWS = ShopTileEntity.OUTPUT_SLOTS.length / COLUMNS;

    public ShopStorageContainer(int windowId, PlayerInventory playerInventory, PacketBuffer extraData) {
        this(windowId, playerInventory, new Inventory(extraData.readInt()));
    }

    public ShopStorageContainer(int windowId, PlayerInventory playerInventory, IInventory inventory) {
        super(Registration.SHOP_STORAGE_CONTAINER.get(), windowId, inventory);

        assertInventorySize(inventory, ShopTileEntity.DEFAULT_SIZE);

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
        AtomicInteger count = new AtomicInteger(2);
        IntStream.range(0, ROWS).forEach(row -> {
            int y = OUTPUT_Y + (Y_SPACING * row);

            IntStream.range(0, COLUMNS).forEach(index -> {
                int x = OUTPUT_X + (X_SPACING * index);

                addSlot(new Slot(inventory,  count.getAndIncrement(), x, y));
            });
        });

        createPlayerSlots(playerInventory,8, 85).forEach(this::addSlot);
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
}
