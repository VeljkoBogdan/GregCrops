package com.illuminatijoe.gregcrops.data.recipes;

import com.illuminatijoe.gregcrops.GregCrops;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.registry.MaterialRegistry;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class GregCropsRecipes {

    public static void init(Consumer<FinishedRecipe> consumer) {
        if (GregCrops.config.generateDefaultSeeds) {
            for (MaterialRegistry registry : GTCEuAPI.materialManager.getRegistries()) {
                for (Material material : registry.getAllMaterials()) {
                    SeedsDefaultRecipeHandler.run(consumer, material);
                }
            }
        }
    }
}
