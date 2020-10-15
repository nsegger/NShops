package iamn5.shops.blocks.shop;

import iamn5.shops.NShops;
import iamn5.shops.init.Registration;
import iamn5.shops.util.OwnableObject;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class ShopTileEntity extends LockableTileEntity implements ISidedInventory {
    public static final int SELL_SLOT = 0;
    public static final int BUY_SLOT = 1;
    public static final int[] INPUT_SLOTS = {SELL_SLOT, BUY_SLOT};
    public static final int[] OUTPUT_SLOTS = IntStream.range(2, 29).toArray();
    public static final int DEFAULT_SIZE = OUTPUT_SLOTS.length + INPUT_SLOTS.length;
    public static final int[] SLOTS = IntStream.range(0, DEFAULT_SIZE).toArray();

    private NonNullList<ItemStack> shopContents;
    private OwnableObject ownerData;

    public ShopTileEntity() {
        this(DEFAULT_SIZE);
    }

    public ShopTileEntity(int inventorySize) {
        super(Registration.SHOP_TILE.get());

        shopContents = NonNullList.withSize(inventorySize, ItemStack.EMPTY);
        ownerData = new OwnableObject();
    }

    public void setOwner(LivingEntity owner) {
        ownerData.setOwner(owner);
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.nshops.shop", ownerData.getOwnerName());
    }

    @Override
    protected Container createMenu(int windowID, PlayerInventory playerInventory) {
        PlayerEntity playerEntity = playerInventory.player;

        if (ownerData.isOwner(playerEntity)) {
            return new ShopStorageContainer(windowID, playerInventory, this);
        }

        return null;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        switch (side) {
            case UP:
                return INPUT_SLOTS;
            case DOWN:
                return OUTPUT_SLOTS;
            default:
                return SLOTS;
        }
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stackIn) {
        ItemStack stackInSlot = getStackInSlot(index);

        if (stackIn.isEmpty() || (!stackInSlot.isEmpty() && !stackInSlot.isItemEqual(stackIn))) {
            return false;
        }

        if (index == SELL_SLOT) {
            return canAcceptSell(stackIn);
        } else if (index == BUY_SLOT) {
            return canAcceptBuy(stackIn);
        }

        return true;
    }

    boolean canAcceptSell(ItemStack itemStackIn) {
        return !getStackInSlot(BUY_SLOT).isItemEqual(itemStackIn);
    }

    boolean canAcceptBuy(ItemStack itemStackIn) {
        return !getStackInSlot(SELL_SLOT).isItemEqual(itemStackIn);
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        return index != SELL_SLOT && index != BUY_SLOT;
    }

    @Override
    public int getSizeInventory() {
        return shopContents.size();
    }

    @Override
    public boolean isEmpty() {
        return shopContents.isEmpty();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if (index < 0 || index >= shopContents.size()) {
            return ItemStack.EMPTY;
        }
        return shopContents.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(shopContents, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(shopContents, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (stack.getCount() > getInventoryStackLimit()) {
            stack.setCount(getInventoryStackLimit());
        }

        shopContents.set(index, stack);
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return world != null && world.getTileEntity(pos) == this && player.getDistanceSq(Vector3d.copyCentered(pos)) <= 64;
    }

    @Override
    public void clear() {
        shopContents.clear();
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        super.read(state, tag);

        shopContents = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(tag, shopContents);
        ownerData.deserializeNBT(tag.getCompound("ownerData"));
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        super.write(tag);

        ItemStackHelper.saveAllItems(tag, shopContents);
        tag.put("ownerData", ownerData.serializeNBT());

        return tag;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
        assert world != null;
        read(world.getBlockState(packet.getPos()), packet.getNbtCompound());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT tag = getUpdateTag();

        ItemStackHelper.saveAllItems(tag, shopContents);
        tag.put("ownerData", ownerData.serializeNBT());

        return new SUpdateTileEntityPacket(pos, 0, tag);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT tag = new CompoundNBT();
        write(tag);
        return tag;
    }
}
