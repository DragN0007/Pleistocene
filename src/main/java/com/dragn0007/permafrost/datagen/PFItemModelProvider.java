package com.dragn0007.permafrost.datagen;

import com.dragn0007.permafrost.Permafrost;
import com.dragn0007.permafrost.items.PFItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class PFItemModelProvider extends ItemModelProvider {
    public PFItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Permafrost.MODID, existingFileHelper);
    }

    @Override
    public void registerModels() {
        simpleItem(PFItems.MAMMOTH);
        simpleItem(PFItems.COOKED_MAMMOTH);
        simpleItem(PFItems.MAMMOTH_MILK_BUCKET);
        simpleItem(PFItems.MAMMOTH_MILK_JUG);
        simpleItem(PFItems.MAMMOTH_CHEESE);
        advancedItem(PFItems.DEINOTHERIUM, "mammoth");
        advancedItem(PFItems.COOKED_DEINOTHERIUM, "cooked_mammoth");
        simpleItem(PFItems.DIREWOLF);
        simpleItem(PFItems.COOKED_DIREWOLF);
        simpleItem(PFItems.CERVID);
        simpleItem(PFItems.COOKED_CERVID);

        simpleItem(PFItems.PERMAFROST);
    }

    public ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Permafrost.MODID,"item/" + item.getId().getPath()));
    }

    public ItemModelBuilder advancedItem(RegistryObject<Item> item, String getTextureName) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Permafrost.MODID,"item/" + getTextureName));
    }
}