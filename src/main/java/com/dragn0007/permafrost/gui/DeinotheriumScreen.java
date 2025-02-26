package com.dragn0007.permafrost.gui;

import com.dragn0007.dragnlivestock.util.LivestockOverhaulClientConfig;
import com.dragn0007.permafrost.Permafrost;
import com.dragn0007.permafrost.entities.deinotherium.Deinotherium;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DeinotheriumScreen extends AbstractContainerScreen<DeinotheriumMenu> {

    public static final ResourceLocation INVENTORY_LOCATION = new ResourceLocation(Permafrost.MODID, "textures/gui/mammoth.png");
    public final Deinotherium deinotherium;
    protected int baseColorLabelX;
    protected int baseColorLabelY;
    protected int genderFLabelX;
    protected int genderMLabelX;
    protected int genderLabelY;

    public DeinotheriumScreen(DeinotheriumMenu deinotheriumMenu, Inventory inventory, Component component) {
        super(deinotheriumMenu, inventory, component);
        this.deinotherium = deinotheriumMenu.deinotherium;
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


        if (this.deinotherium.isSaddleable()) {
            graphics.blit(INVENTORY_LOCATION, x + 7, y + 17, 18, this.imageHeight + 54, 18, 18);
        }

        if (this.deinotherium.canWearArmor()) {
            graphics.blit(INVENTORY_LOCATION, x + 7, y + 35, 36, this.imageHeight + 54, 18, 18);
        }

        if (this.deinotherium.hasChest()) {
            graphics.blit(INVENTORY_LOCATION, x + 25, y + 17, 0, this.imageHeight, 145, 54);
        }

        if (this.deinotherium.isFemale()) {
            graphics.blit(INVENTORY_LOCATION, x + 161, y + 9, 90, this.imageHeight + 54, 8, 8);
        }

        if (this.deinotherium.isMale()) {
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
        String text = this.deinotherium.getTextureLocation().toString();
        String noFillerText = text.replaceAll(".+deinotherium_", "");
        String noUnderscoresText = noFillerText.replaceAll("_", " ");
        String noPNGText = noUnderscoresText.replace(".png", "");
        String labelText = "Base Coat: " + noPNGText.toUpperCase();

        String noTextureText = "Base Coat: " + "No Coat Found.";

        if (this.deinotherium.getTextureLocation() == null) {
            graphics.drawString(this.font, noTextureText, baseColorLabelX, baseColorLabelY, 0xFFFFFF, false);
        } else {
            graphics.drawString(this.font, labelText, baseColorLabelX, baseColorLabelY, 0xFFFFFF, false);
        }
    }

    private void renderGenderLabel(GuiGraphics graphics) {
        String female = "FEMALE";
        String male = "MALE";
        String error = "NBT Error";

        if (this.deinotherium.getGender() == 0) {
            graphics.drawString(this.font, female, genderFLabelX, genderLabelY, 0xFFFFFF, false);
        } else if (this.deinotherium.getGender() == 1) {
            graphics.drawString(this.font, male, genderMLabelX, genderLabelY, 0xFFFFFF, false);
        } else {
            graphics.drawString(this.font, error, genderFLabelX, genderLabelY, 0xFFFFFF, false);
        }
    }

}


