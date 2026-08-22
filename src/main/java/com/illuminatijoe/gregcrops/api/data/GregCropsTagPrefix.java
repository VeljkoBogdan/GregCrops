package com.illuminatijoe.gregcrops.api.data;

import com.illuminatijoe.gregcrops.api.data.chemical.material.properties.GregCropPropertyKeys;
import com.illuminatijoe.gregcrops.data.blocks.GregCropsBlocks;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;

import java.util.function.Predicate;

public class GregCropsTagPrefix {

    public static TagPrefix seeds;

    public static final Predicate<Material> hasSeedsProperty = mat -> mat.hasProperty(GregCropPropertyKeys.SEEDS);

    public static void initTagPrefixes() {
        seeds = new TagPrefix("seeds")
                .idPattern("%s_seeds")
                .langValue("%s Seeds")
                .defaultTagPath("seeds/%s")
                .defaultTagPath("seeds")
                .materialIconType(GregCropsMaterialIconType.seeds)
                .unificationEnabled(true)
                .itemTable(() -> GregCropsBlocks.CROP_BLOCKS)
                .generationCondition(hasSeedsProperty);
    }
}
