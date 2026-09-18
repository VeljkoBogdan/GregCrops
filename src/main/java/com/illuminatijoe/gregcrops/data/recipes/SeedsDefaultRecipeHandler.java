package com.illuminatijoe.gregcrops.data.recipes;

import com.illuminatijoe.gregcrops.api.data.chemical.material.properties.GregCropPropertyKeys;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.illuminatijoe.gregcrops.api.data.GregCropsTagPrefix.*;

public class SeedsDefaultRecipeHandler {

    private static final List<TagPrefix> SEED_CRAFTING_PRIORITY = List.of(block, dust, ingot, gem, plate, nugget);

    private SeedsDefaultRecipeHandler() {}

    public static void run(@NotNull Consumer<FinishedRecipe> provider, @NotNull Material material) {
        processSeed(provider, material);
    }

    private static void processSeed(@NotNull Consumer<FinishedRecipe> provider, @NotNull Material material) {
        if (!material.hasProperty(GregCropPropertyKeys.SEEDS)) return;

        TagPrefix craftingPrefix = null;
        for (TagPrefix prefix : SEED_CRAFTING_PRIORITY) {
            if (!ChemicalHelper.get(prefix, material).isEmpty()) {
                craftingPrefix = prefix;
                break;
            }
        }

        if (craftingPrefix == null) {
            GTCEu.LOGGER.warn("[GregCrops] No valid crafting ingredient found for seed: {}", material.getName());
            return;
        }

        VanillaRecipeHelper.addShapedRecipe(provider, String.format("seed_%s", material.getName()),
                ChemicalHelper.get(seeds, material), " B ", "BSB", " B ", 'B',
                new MaterialEntry(craftingPrefix, material), 'S', new ItemStack(Items.WHEAT_SEEDS));
    }
}
