package com.dragn0007.permafrost.datagen;

import com.dragn0007.dragnlivestock.util.LOTags;
import com.dragn0007.permafrost.items.PFItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class PFRecipeMaker extends RecipeProvider implements IConditionBuilder {
    public PFRecipeMaker(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, PFItems.RAW_MAMMOTH_CHEESE.get())
                .requires(PFItems.MAMMOTH_MILK_JUG.get())
                .requires(PFItems.MAMMOTH_MILK_JUG.get())
                .unlockedBy("has_milk", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(LOTags.Items.MILK)
                        .build()))
                .save(pFinishedRecipeConsumer);

        SimpleCookingRecipeBuilder.smoking(Ingredient.of(PFItems.MAMMOTH.get()), RecipeCategory.MISC, PFItems.COOKED_MAMMOTH.get(), 0.35F, 100)
                .unlockedBy("has_mammoth", has(PFItems.MAMMOTH.get())).save(pFinishedRecipeConsumer, new ResourceLocation("permafrost", "cooked_mammoth_smoking"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(PFItems.MAMMOTH.get()), RecipeCategory.MISC, PFItems.COOKED_MAMMOTH.get(), 0.35F, 200)
                .unlockedBy("has_mammoth", has(PFItems.MAMMOTH.get())).save(pFinishedRecipeConsumer, new ResourceLocation("permafrost", "cooked_mammoth_smelting"));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(PFItems.MAMMOTH.get()), RecipeCategory.MISC, PFItems.COOKED_MAMMOTH.get(), 0.35F, 600)
                .unlockedBy("has_mammoth", has(PFItems.MAMMOTH.get())).save(pFinishedRecipeConsumer, new ResourceLocation("permafrost", "cooked_mammoth_campfire_cooking"));

        SimpleCookingRecipeBuilder.smoking(Ingredient.of(PFItems.DEINOTHERIUM.get()), RecipeCategory.MISC, PFItems.COOKED_DEINOTHERIUM.get(), 0.35F, 100)
                .unlockedBy("has_deinotherium", has(PFItems.DEINOTHERIUM.get())).save(pFinishedRecipeConsumer, new ResourceLocation("permafrost", "cooked_deinotherium_smoking"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(PFItems.DEINOTHERIUM.get()), RecipeCategory.MISC, PFItems.COOKED_DEINOTHERIUM.get(), 0.35F, 200)
                .unlockedBy("has_deinotherium", has(PFItems.DEINOTHERIUM.get())).save(pFinishedRecipeConsumer, new ResourceLocation("permafrost", "cooked_deinotherium_smelting"));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(PFItems.DEINOTHERIUM.get()), RecipeCategory.MISC, PFItems.COOKED_DEINOTHERIUM.get(), 0.35F, 600)
                .unlockedBy("has_deinotherium", has(PFItems.DEINOTHERIUM.get())).save(pFinishedRecipeConsumer, new ResourceLocation("permafrost", "cooked_deinotherium_campfire_cooking"));

        SimpleCookingRecipeBuilder.smoking(Ingredient.of(PFItems.DIREWOLF.get()), RecipeCategory.MISC, PFItems.COOKED_DIREWOLF.get(), 0.35F, 100)
                .unlockedBy("has_direwolf", has(PFItems.DIREWOLF.get())).save(pFinishedRecipeConsumer, new ResourceLocation("permafrost", "cooked_direwolf_smoking"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(PFItems.DIREWOLF.get()), RecipeCategory.MISC, PFItems.COOKED_DIREWOLF.get(), 0.35F, 200)
                .unlockedBy("has_direwolf", has(PFItems.DIREWOLF.get())).save(pFinishedRecipeConsumer, new ResourceLocation("permafrost", "cooked_direwolf_smelting"));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(PFItems.DIREWOLF.get()), RecipeCategory.MISC, PFItems.COOKED_DIREWOLF.get(), 0.35F, 600)
                .unlockedBy("has_direwolf", has(PFItems.DIREWOLF.get())).save(pFinishedRecipeConsumer, new ResourceLocation("permafrost", "cooked_direwolf_campfire_cooking"));

        SimpleCookingRecipeBuilder.smoking(Ingredient.of(PFItems.CERVID.get()), RecipeCategory.MISC, PFItems.COOKED_CERVID.get(), 0.35F, 100)
                .unlockedBy("has_cervid", has(PFItems.CERVID.get())).save(pFinishedRecipeConsumer, new ResourceLocation("permafrost", "cooked_cervid_smoking"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(PFItems.CERVID.get()), RecipeCategory.MISC, PFItems.COOKED_CERVID.get(), 0.35F, 200)
                .unlockedBy("has_cervid", has(PFItems.CERVID.get())).save(pFinishedRecipeConsumer, new ResourceLocation("permafrost", "cooked_cervid_smelting"));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(PFItems.CERVID.get()), RecipeCategory.MISC, PFItems.COOKED_CERVID.get(), 0.35F, 600)
                .unlockedBy("has_cervid", has(PFItems.CERVID.get())).save(pFinishedRecipeConsumer, new ResourceLocation("permafrost", "cooked_cervid_campfire_cooking"));

        SimpleCookingRecipeBuilder.smoking(Ingredient.of(PFItems.PARACERATHERIUM.get()), RecipeCategory.MISC, PFItems.COOKED_PARACERATHERIUM.get(), 0.35F, 100)
                .unlockedBy("has_paraceratherium", has(PFItems.PARACERATHERIUM.get())).save(pFinishedRecipeConsumer, new ResourceLocation("permafrost", "cooked_paraceratherium_smoking"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(PFItems.PARACERATHERIUM.get()), RecipeCategory.MISC, PFItems.COOKED_PARACERATHERIUM.get(), 0.35F, 200)
                .unlockedBy("has_paraceratherium", has(PFItems.PARACERATHERIUM.get())).save(pFinishedRecipeConsumer, new ResourceLocation("permafrost", "cooked_paraceratherium_smelting"));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(PFItems.PARACERATHERIUM.get()), RecipeCategory.MISC, PFItems.COOKED_PARACERATHERIUM.get(), 0.35F, 600)
                .unlockedBy("has_paraceratherium", has(PFItems.PARACERATHERIUM.get())).save(pFinishedRecipeConsumer, new ResourceLocation("permafrost", "cooked_paraceratherium_campfire_cooking"));

        SimpleCookingRecipeBuilder.smoking(Ingredient.of(PFItems.DINOFELIS.get()), RecipeCategory.MISC, PFItems.COOKED_DINOFELIS.get(), 0.35F, 100)
                .unlockedBy("has_dinofelis", has(PFItems.DINOFELIS.get())).save(pFinishedRecipeConsumer, new ResourceLocation("permafrost", "cooked_dinofelis_smoking"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(PFItems.DINOFELIS.get()), RecipeCategory.MISC, PFItems.COOKED_DINOFELIS.get(), 0.35F, 200)
                .unlockedBy("has_dinofelis", has(PFItems.DINOFELIS.get())).save(pFinishedRecipeConsumer, new ResourceLocation("permafrost", "cooked_dinofelis_smelting"));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(PFItems.DINOFELIS.get()), RecipeCategory.MISC, PFItems.COOKED_DINOFELIS.get(), 0.35F, 600)
                .unlockedBy("has_dinofelis", has(PFItems.DINOFELIS.get())).save(pFinishedRecipeConsumer, new ResourceLocation("permafrost", "cooked_dinofelis_campfire_cooking"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(PFItems.MAMMOTH_MILK_JUG.get()), RecipeCategory.MISC, PFItems.MAMMOTH_CHEESE.get(), 0.35F, 200)
                .unlockedBy("has_mammoth_milk", has(PFItems.MAMMOTH_MILK_JUG.get())).save(pFinishedRecipeConsumer, new ResourceLocation("permafrost", "mammoth_cheese_smelting"));

    }
}