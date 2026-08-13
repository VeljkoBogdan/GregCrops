package com.illuminatijoe.gregcrops.data.materials;

import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.illuminatijoe.gregcrops.api.data.chemical.material.properties.GregCropPropertyKeys;
import com.illuminatijoe.gregcrops.api.data.chemical.material.properties.SeedsProperty;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class GregCropsMaterials {

    public static void modifyMaterials() {
        Iron.setProperty(GregCropPropertyKeys.SEEDS, new SeedsProperty());
    }
}
