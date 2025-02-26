package com.dragn0007.permafrost.datagen;

import com.dragn0007.permafrost.items.PFItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
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


        SimpleCookingRecipeBuilder.smelting(Ingredient.of(PFItems.MAMMOTH_MILK_JUG.get()), RecipeCategory.MISC, PFItems.MAMMOTH_CHEESE.get(), 0.35F, 200)
                .unlockedBy("has_mammoth_milk", has(PFItems.MAMMOTH_MILK_JUG.get())).save(pFinishedRecipeConsumer, new ResourceLocation("permafrost", "mammoth_cheese_smelting"));

    }
}