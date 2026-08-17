package com.illuminatijoe.gregcrops.data.blocks;

import com.google.common.collect.ImmutableTable;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.block.MaterialBlock;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.registry.MaterialRegistry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.item.MaterialBlockItem;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.models.GTModels;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.illuminatijoe.gregcrops.GregCrops;
import com.illuminatijoe.gregcrops.api.data.GregCropsTagPrefix;
import com.illuminatijoe.gregcrops.api.data.chemical.material.properties.GregCropPropertyKeys;
import com.lowdragmc.lowdraglib.utils.ColorUtils;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.Util;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.Tags;
import org.apache.logging.log4j.Level;

import static com.gregtechceu.gtceu.common.registry.GTRegistration.REGISTRATE;
import static com.illuminatijoe.gregcrops.api.data.GregCropsTagPrefix.hasSeedsProperty;

@SuppressWarnings("removal")
public class GregCropsBlocks {
    static ImmutableTable.Builder<TagPrefix, Material, BlockEntry<? extends Block>> CROP_BLOCKS_BUILDER = ImmutableTable.builder();

    public static void generateCropBlocks() {
        GregCrops.LOGGER.log(Level.INFO, "[GregCrops]: Generating GTCEu Crop Blocks...");

        for (MaterialRegistry registry : GTCEuAPI.materialManager.getRegistries()) {
            GTRegistrate registrate = registry.getRegistrate();
            GregCrops.LOGGER.log(Level.INFO, "[GregCrops]: Generating Crop Blocks for Registrate: " + registrate.getModid());

            for (Material material : registry.getAllMaterials()) {
                if (material.hasProperty(GregCropPropertyKeys.SEEDS)) {
                    registerCropBlock(material, registrate);
                    GregCrops.LOGGER.log(Level.INFO, "[GregCrops]: Generating Crop Blocks for Material: " + material.getName());
                }
            }
        }

        GregCrops.LOGGER.log(Level.INFO, "[GregCrops]: Generating GTCEu Crop Blocks... Complete!");
    }

    private static void registerCropBlock(Material material, GTRegistrate registrate) {
        var tagPrefix = GregCropsTagPrefix.seeds;
        String id = tagPrefix.idPattern().formatted(material.getName());
        int color = material.getMaterialRGB();

        CROP_BLOCKS_BUILDER.put(tagPrefix, material, registrate
                .block(id, p -> new GregCropBlock(p, material))
                .initialProperties(() -> Blocks.WHEAT)
                .properties(p -> p
                        .noCollission()
                        .randomTicks()
                        .instabreak()
                        .sound(SoundType.CROP)
                        .noOcclusion()
                )
                .addLayer(() -> RenderType::cutout)
                .setData(ProviderType.BLOCKSTATE, (ctx, prov) -> {
                    var builder = prov.getVariantBuilder(ctx.getEntry());
                    for (int age = 0; age <= GregCropBlock.MAX_AGE; age++) {
                        builder.partialState().with(GregCropBlock.AGE, age).setModels(
                                new ConfiguredModel(
                                        prov.models().withExistingParent(id + "_stage" + age, "block/crop")
                                                .texture("crop", GTCEu.id("block/crop_stage" + age))
                                )
                        );
                    }
                })
                .loot((prov, block) -> {
                    var seedItem = ChemicalHelper.get(tagPrefix, material).getItem();
                    var matureCondition = LootItemBlockStatePropertyCondition
                            .hasBlockStateProperties(block)
                            .setProperties(StatePropertiesPredicate.Builder
                                    .properties()
                                    .hasProperty(GregCropBlock.AGE, GregCropBlock.MAX_AGE));

                    prov.add(block, prov.createCropDrops(block, seedItem, seedItem, matureCondition));
                })
                .setData(ProviderType.LANG, NonNullBiConsumer.noop())
                .color(() -> () -> (BlockColor)(state, level, pos, tintIndex) -> tintIndex == 0 ? color : -1)
                .item((b, p) -> tagPrefix.itemConstructor().create(p, tagPrefix, material))
                .color(() -> () -> (ItemColor)(stack, tintIndex) -> tintIndex == 0 ? color : -1)
                .build()
                .register()
        );
    }
}
