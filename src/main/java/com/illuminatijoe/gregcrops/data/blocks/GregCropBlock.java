package com.illuminatijoe.gregcrops.data.blocks;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.common.PlantType;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class GregCropBlock extends CropBlock {

    public static final int MAX_AGE = 5;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;

    private final Material material;

    public GregCropBlock(Properties properties, TagPrefix tagPrefix, Material material) {
        super(properties);
        this.material = material;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return this.asItem();
    }

    protected @NotNull IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public @NotNull BlockState getPlant(BlockGetter blockGetter, BlockPos blockPos) {
        BlockState state = blockGetter.getBlockState(blockPos);
        return state.getBlock() != this ? this.defaultBlockState() : state;
    }

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return PlantType.CROP;
    }
}
