package iamn5.shops.blocks.shop;

import com.mojang.blaze3d.matrix.MatrixStack;
import iamn5.shops.NShops;
import iamn5.shops.containers.ShopStorageContainer;
import iamn5.shops.init.Registration;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ShopStorageScreen extends ContainerScreen<ShopStorageContainer> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(NShops.MOD_ID, "textures/gui/shopstorage_27.png");

    public ShopStorageScreen(ShopStorageContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        xSize = 204;
        ySize = 167;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        ITextComponent title = Registration.SHOP_BLOCK.get().getTranslatedName();

        font.drawString(matrixStack, title.getString(), 7, 6, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        if (minecraft == null) return;

        minecraft.getTextureManager().bindTexture(TEXTURE);

        int posX = (this.width - this.xSize) / 2;
        int posY = (this.height - this.ySize) / 2;

        blit(matrixStack, posX, posY, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void renderHoveredTooltip(MatrixStack matrixStack, int x, int y) {
        if (minecraft != null && minecraft.player != null && !minecraft.player.inventory.getItemStack().isEmpty()) return;

        ITextComponent hoveringText = null;

        if (isInRect(ShopStorageContainer.INPUT_X + guiLeft,
                ShopStorageContainer.INPUT_Y[0] + guiTop, 16, 16, x, y)) {
            hoveringText = new TranslationTextComponent("screen.nshops.sellSlot");

        } else if (isInRect(ShopStorageContainer.INPUT_X + guiLeft,
                ShopStorageContainer.INPUT_Y[1] + guiTop, 16, 16, x, y))
        {
            hoveringText = new TranslationTextComponent("screen.nshops.buySlot");
        }

        if (hoveringText != null){
            renderTooltip(matrixStack, hoveringText, x, y);
        } else {
            super.renderHoveredTooltip(matrixStack, x, y);
        }

    }

    public static boolean isInRect(int x, int y, int width, int height, int mouseX, int mouseY) {
        return (mouseX >= x && mouseX <= x + width - 1) && (mouseY >= y && mouseY <= y + height - 1);
    }
}
