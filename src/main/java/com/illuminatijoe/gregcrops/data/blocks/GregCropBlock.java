package com.illuminatijoe.gregcrops.data.blocks;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.illuminatijoe.gregcrops.api.data.GregCropsTagPrefix;
import lombok.Getter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

@Getter
public class GregCropBlock extends CropBlock {
    public static final int MAX_AGE = 5;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;

    private final Material material;

    public GregCropBlock(Properties properties, Material material) {
        super(properties);
        this.material = material;
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return ChemicalHelper.get(GregCropsTagPrefix.seeds, material).getItem();
    }

    @Override
    protected @NotNull IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }
}
