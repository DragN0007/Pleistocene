package com.dragn0007.permafrost.gui;

import com.dragn0007.dragnlivestock.items.custom.LightHorseArmorItem;
import com.dragn0007.dragnlivestock.util.LOTags;
import com.dragn0007.permafrost.entities.quagga.Quagga;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;

public class QuaggaMenu extends AbstractContainerMenu {

    public Container container;
    public Quagga quagga;

    public QuaggaMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, new SimpleContainer(extraData.readInt()), (Quagga) inventory.player.level().getEntity(extraData.readInt()));
    }

    public QuaggaMenu(int containerId, Inventory inventory, Container container, Quagga abstractOMount) {
        super(PFMenuTypes.QUAGGA_MENU.get(), containerId);
        this.container = container;
        this.quagga = abstractOMount;

        int quaggaSlots = 0;
        this.addSlot(new Slot(this.container, quaggaSlots++, 8, 18) {
            @Override
            public boolean mayPlace(ItemStack itemStack) {
                return itemStack.is(LOTags.Items.SADDLE) && !this.hasItem() && QuaggaMenu.this.quagga.isSaddleable();
            }

            @Override
            public boolean isActive() {
                return QuaggaMenu.this.quagga.isSaddleable();
            }
        });

        this.addSlot(new Slot(this.container, quaggaSlots++, 8, 36) {
            @Override
            public boolean mayPlace(ItemStack itemStack) {
                if (itemStack.getItem() instanceof HorseArmorItem || itemStack.getItem() instanceof LightHorseArmorItem) {
                    return !this.hasItem() && QuaggaMenu.this.quagga.canWearArmor();
                }
                if (itemStack.is(LOTags.Items.ARMOR_FOR_O_MOUNTS)) {
                    return !this.hasItem() && QuaggaMenu.this.quagga.canWearArmor();
                }
                return false;
            }

            @Override
            public boolean isActive() {
                return QuaggaMenu.this.quagga.canWearArmor();
            }
        });

        this.addSlot(new Slot(this.container, quaggaSlots++, 8, 54) {
            @Override
            public boolean mayPlace(ItemStack itemStack) {
                if (itemStack.is(LOTags.Items.DECOR_FOR_O_MOUNTS)) {
                    return !this.hasItem() && QuaggaMenu.this.quagga.canWearDecor();
                }
                return false;
            }

            @Override
            public boolean isActive() {
                return QuaggaMenu.this.quagga.canWearDecor();
            }
        });

        if(this.quagga.hasChest()) {
            for(int y = 0; y < 3; y++) {
                for(int x = 0; x < 5; x++) {
                    this.addSlot(new Slot(this.container, quaggaSlots++, 80 + x * 18, 18 + y * 18));
                }
            }
        }

        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 9; x++) {
                this.addSlot(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        for(int x = 0; x < 9; x++) {
            this.addSlot(new Slot(inventory, x, 8 + x * 18, 142));
        }
    }

    public boolean stillValid(Player player) {
        return !this.quagga.hasInventoryChanged(this.container) && this.container.stillValid(player) && this.quagga.isAlive() && this.quagga.distanceTo(player) < 8.0F;
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