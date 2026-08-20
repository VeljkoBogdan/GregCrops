package com.illuminatijoe.gregcrops.api.data;

import com.illuminatijoe.gregcrops.api.data.chemical.material.properties.GregCropPropertyKeys;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.illuminatijoe.gregcrops.data.blocks.GregCropBlock;
import net.minecraft.client.renderer.RenderType;

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
                .blockConstructor(GregCropBlock::new)
                .blockProperties(new TagPrefix.BlockProperties(
                        () -> RenderType::cutout,
                        p -> p.noCollission().noOcclusion().instabreak()
                ))
                .generationCondition(hasSeedsProperty);
    }
}
