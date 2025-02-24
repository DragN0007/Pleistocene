package com.dragn0007.permafrost.gui;

import com.dragn0007.dragnlivestock.util.LivestockOverhaulClientConfig;
import com.dragn0007.permafrost.Permafrost;
import com.dragn0007.permafrost.entities.mammoth.Mammoth;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MammothScreen extends AbstractContainerScreen<MammothMenu> {

    public static final ResourceLocation INVENTORY_LOCATION = new ResourceLocation(Permafrost.MODID, "textures/gui/mammoth.png");
    public final Mammoth mammoth;
    protected int baseColorLabelX;
    protected int baseColorLabelY;
    protected int genderFLabelX;
    protected int genderMLabelX;
    protected int genderLabelY;

    public MammothScreen(MammothMenu mammothMenu, Inventory inventory, Component component) {
        super(mammothMenu, inventory, component);
        this.mammoth = mammothMenu.mammoth;
    }

    @Override
    protected void init() {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        genderFLabelX = leftPos + 140;
        genderMLabelX = leftPos + 152;
        genderLabelY = topPos - 8;

        baseColorLabelX = leftPos + 1;
        baseColorLabelY = topPos + 170;
    }

    public void renderBg(GuiGraphics graphics, float f, int i, int j) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, INVENTORY_LOCATION);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(INVENTORY_LOCATION, x, y, 0, 0, this.imageWidth, this.imageHeight);


        if (this.mammoth.isSaddleable()) {
            graphics.blit(INVENTORY_LOCATION, x + 7, y + 17, 18, this.imageHeight + 54, 18, 18);
        }

        if (this.mammoth.canWearArmor()) {
            graphics.blit(INVENTORY_LOCATION, x + 7, y + 35, 36, this.imageHeight + 54, 18, 18);
        }

        if (this.mammoth.hasChest()) {
            graphics.blit(INVENTORY_LOCATION, x + 25, y + 17, 0, this.imageHeight, 145, 54);
        }

        if (this.mammoth.isFemale()) {
            graphics.blit(INVENTORY_LOCATION, x + 161, y + 9, 90, this.imageHeight + 54, 8, 8);
        }

        if (this.mammoth.isMale()) {
            graphics.blit(INVENTORY_LOCATION, x + 161, y + 9, 98, this.imageHeight + 54, 8, 8);
        }

        if (LivestockOverhaulClientConfig.HORSE_COAT_GUI.get()) {
            renderBaseCoatLabel(graphics);
        }

        if (LivestockOverhaulClientConfig.ACCESSIBILITY_GENDER_IDENTIFIER.get()) {
            renderGenderLabel(graphics);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int i, int i1, float v) {
        this.renderBackground(graphics);
        super.render(graphics, i, i1, v);
        this.renderTooltip(graphics, i, i1);
    }

    private void renderBaseCoatLabel(GuiGraphics graphics) {
        String text = this.mammoth.getTextureLocation().toString();
        String noFillerText = text.replaceAll(".+mammoth_", "");
        String noUnderscoresText = noFillerText.replaceAll("_", " ");
        String noPNGText = noUnderscoresText.replace(".png", "");
        String labelText = "Base Coat: " + noPNGText.toUpperCase();

        String noTextureText = "Base Coat: " + "No Coat Found.";

        if (this.mammoth.getTextureLocation() == null) {
            graphics.drawString(this.font, noTextureText, baseColorLabelX, baseColorLabelY, 0xFFFFFF, false);
        } else {
            graphics.drawString(this.font, labelText, baseColorLabelX, baseColorLabelY, 0xFFFFFF, false);
        }
    }

    private void renderGenderLabel(GuiGraphics graphics) {
        String female = "FEMALE";
        String male = "MALE";
        String error = "NBT Error";

        if (this.mammoth.getGender() == 0) {
            graphics.drawString(this.font, female, genderFLabelX, genderLabelY, 0xFFFFFF, false);
        } else if (this.mammoth.getGender() == 1) {
            graphics.drawString(this.font, male, genderMLabelX, genderLabelY, 0xFFFFFF, false);
        } else {
            graphics.drawString(this.font, error, genderFLabelX, genderLabelY, 0xFFFFFF, false);
        }
    }

}


