package com.illuminatijoe.gregcrops.data.materials;

import com.illuminatijoe.gregcrops.api.data.chemical.material.properties.GregCropPropertyKeys;
import com.illuminatijoe.gregcrops.api.data.chemical.material.properties.SeedsProperty;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class GregCropsMaterials {

    public static void modifyMaterials() {
        // Periodic table elements
        Iron.setProperty(GregCropPropertyKeys.SEEDS, new SeedsProperty(TagPrefix.dust));
        Copper.setProperty(GregCropPropertyKeys.SEEDS, new SeedsProperty(TagPrefix.rawOre, Malachite));
        Tin.setProperty(GregCropPropertyKeys.SEEDS, new SeedsProperty(TagPrefix.ingot));
        Lead.setProperty(GregCropPropertyKeys.SEEDS, new SeedsProperty());
        Zinc.setProperty(GregCropPropertyKeys.SEEDS, new SeedsProperty());
        Gold.setProperty(GregCropPropertyKeys.SEEDS, new SeedsProperty());
        Aluminium.setProperty(GregCropPropertyKeys.SEEDS, new SeedsProperty());

        // Other materials
        Wood.setProperty(GregCropPropertyKeys.SEEDS, new SeedsProperty());
        Paper.setProperty(GregCropPropertyKeys.SEEDS, new SeedsProperty());
    }
}
