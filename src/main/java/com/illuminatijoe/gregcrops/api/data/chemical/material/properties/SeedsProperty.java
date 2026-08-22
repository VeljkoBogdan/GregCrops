package com.illuminatijoe.gregcrops.api.data.chemical.material.properties;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.IMaterialProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.MaterialProperties;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;

public class SeedsProperty implements IMaterialProperty {

    private final TagPrefix dropTagPrefix;
    private final Material dropMaterial;

    public SeedsProperty(TagPrefix dropTagPrefix, Material dropMaterial) {
        this.dropTagPrefix = dropTagPrefix;
        this.dropMaterial = dropMaterial;
    }

    public SeedsProperty(TagPrefix dropTagPrefix) {
        this.dropTagPrefix = dropTagPrefix;
        this.dropMaterial = null;
    }

    public SeedsProperty() {
        this.dropTagPrefix = null;
        this.dropMaterial = null;
    }

    public Material resolveDropMaterial(Material self) {
        return dropMaterial != null ? dropMaterial : self;
    }

    public TagPrefix resolveDropTagPrefix(Material self) {
        if (dropTagPrefix == null) {
            if (self.hasProperty(PropertyKey.ORE)) return TagPrefix.rawOre;
            if (self.hasProperty(PropertyKey.WOOD)) return TagPrefix.log;
            if (self.hasProperty(PropertyKey.DUST)) return TagPrefix.dust;
            if (self.hasProperty(PropertyKey.GEM)) return TagPrefix.gem;
            if (self.hasProperty(PropertyKey.INGOT)) return TagPrefix.ingot;
        }

        return dropTagPrefix;
    }

    @Override
    public void verifyProperty(MaterialProperties materialProperties) {}
}
