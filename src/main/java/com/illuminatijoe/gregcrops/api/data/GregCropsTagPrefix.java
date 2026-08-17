package com.illuminatijoe.gregcrops.api.data;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.illuminatijoe.gregcrops.api.data.chemical.material.properties.GregCropPropertyKeys;

import java.util.function.Predicate;

public class GregCropsTagPrefix {

    public static TagPrefix seeds;

    public static final Predicate<Material> hasSeedsProperty = mat -> mat.hasProperty(GregCropPropertyKeys.SEEDS);

    public static void initTagPrefixes() {
        seeds = new TagPrefix("seeds")
                .langValue("%s Seeds")
                .idPattern("%s_seeds")
                .defaultTagPath("seeds/%s")
                .defaultTagPath("seeds")
                .materialIconType(GregCropsMaterialIconType.seeds)
                .unificationEnabled(true)
                .generateItem(false)
                .generationCondition(hasSeedsProperty);
    }
}
