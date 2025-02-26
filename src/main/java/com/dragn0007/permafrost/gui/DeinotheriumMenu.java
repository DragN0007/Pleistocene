package com.dragn0007.permafrost.gui;

import com.dragn0007.dragnlivestock.util.LOTags;
import com.dragn0007.permafrost.entities.deinotherium.Deinotherium;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;

public class DeinotheriumMenu extends AbstractContainerMenu {

    public Container container;
    public Deinotherium deinotherium;

    public DeinotheriumMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, new SimpleContainer(extraData.readInt()), (Deinotherium) inventory.player.level().getEntity(extraData.readInt()));
    }

    public DeinotheriumMenu(int containerId, Inventory inventory, Container container, Deinotherium deinotherium) {
        super(PFMenuTypes.DEINOTHERIUM_MENU.get(), containerId);
        this.container = container;
        this.deinotherium = deinotherium;

        int DeinotheriumSlots = 0;
        this.addSlot(new Slot(this.container, DeinotheriumSlots++, 8, 18) {
            @Override
            public boolean mayPlace(ItemStack itemStack) {
                return itemStack.is(LOTags.Items.SADDLE) && !this.hasItem() && DeinotheriumMenu.this.deinotherium.isSaddleable();
            }

            @Override
            public boolean isActive() {
                return DeinotheriumMenu.this.deinotherium.isSaddleable();
            }
        });

        this.addSlot(new Slot(this.container, DeinotheriumSlots++, 8, 36) {
            @Override
            public boolean mayPlace(ItemStack itemStack) {
                if (itemStack.getItem() instanceof HorseArmorItem) {
                    return !this.hasItem() && DeinotheriumMenu.this.deinotherium.canWearArmor();
                } else if (itemStack.is(ItemTags.WOOL_CARPETS)) {
                    return !this.hasItem() && DeinotheriumMenu.this.deinotherium.canWearArmor();
                }
                return false;
            }

            @Override
            public boolean isActive() {
                return DeinotheriumMenu.this.deinotherium.canWearArmor();
            }
        });

        if (this.deinotherium.hasChest()) {
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 8; x++) {
                    this.addSlot(new Slot(this.container, DeinotheriumSlots++, 26 + x * 18, 18 + y * 18));
                }
            }
        }

        int playerSlots = 0;
        for(int x = 0; x < 9; x++) {
            this.addSlot(new Slot(inventory, playerSlots++, 8 + x * 18, 142));
        }

        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 9; x++) {
                this.addSlot(new Slot(inventory, playerSlots++, 8 + x * 18, 84 + y * 18));
            }
        }
    }

    public boolean stillValid(Player player) {
        return !this.deinotherium.hasInventoryChanged(this.container) && this.container.stillValid(player) && this.deinotherium.isAlive() && this.deinotherium.distanceTo(player) < 8.0F;
    }


    @Override
    public ItemStack quickMoveStack(Player player, int slotId) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotId);
        if(slot.hasItem()) {
            itemStack = slot.getItem().copy();
            int containerSize = this.container.getContainerSize();

            if(slotId < containerSize) {
                if(!this.moveItemStackTo(itemStack, containerSize, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if(!this.moveItemStackTo(itemStack, 0, containerSize, false)) {
                return ItemStack.EMPTY;
            }

            if(itemStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemStack;
    }
}