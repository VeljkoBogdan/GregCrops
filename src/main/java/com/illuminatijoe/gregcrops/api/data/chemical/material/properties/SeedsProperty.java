package com.illuminatijoe.gregcrops.api.data.chemical.material.properties;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.IMaterialProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.MaterialProperties;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class SeedsProperty implements IMaterialProperty {

    private final String dropItemId;
    private final TagPrefix dropTagPrefix;
    private final Material dropMaterial;

    public SeedsProperty(TagPrefix dropTagPrefix, Material dropMaterial) {
        this.dropTagPrefix = dropTagPrefix;
        this.dropMaterial = dropMaterial;
        this.dropItemId = "";
    }

    public SeedsProperty(TagPrefix dropTagPrefix) {
        this.dropTagPrefix = dropTagPrefix;
        this.dropMaterial = null;
        this.dropItemId = "";
    }

    public SeedsProperty(String dropItemId) {
        this.dropTagPrefix = null;
        this.dropMaterial = null;
        this.dropItemId = dropItemId;
    }

    public SeedsProperty() {
        this.dropTagPrefix = null;
        this.dropMaterial = null;
        this.dropItemId = "";
    }

    public Material resolveDropMaterial(Material self) {
        return dropMaterial != null ? dropMaterial : self;
    }

    public TagPrefix resolveDropTagPrefix(Material self) {
        if (dropTagPrefix == null) {
            if (self.hasProperty(PropertyKey.ORE)) return TagPrefix.rawOre;
            if (self.hasProperty(PropertyKey.DUST)) return TagPrefix.dust;
            if (self.hasProperty(PropertyKey.GEM)) return TagPrefix.gem;
            if (self.hasProperty(PropertyKey.INGOT)) return TagPrefix.ingot;
            return TagPrefix.block;
        }

        return dropTagPrefix;
    }

    public boolean hasItem() {
        return !dropItemId.isEmpty();
    }

    public Item getItem() {
        return hasItem() ? ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(dropItemId)).asItem() :
                ItemStack.EMPTY.getItem();
    }

    @Override
    public void verifyProperty(MaterialProperties materialProperties) {}
}
