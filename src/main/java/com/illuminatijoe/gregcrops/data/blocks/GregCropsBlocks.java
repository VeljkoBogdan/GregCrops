package com.illuminatijoe.gregcrops.data.blocks;

import com.illuminatijoe.gregcrops.GregCrops;
import com.illuminatijoe.gregcrops.api.data.GregCropsTagPrefix;
import com.illuminatijoe.gregcrops.api.data.chemical.material.properties.GregCropPropertyKeys;
import com.illuminatijoe.gregcrops.api.data.chemical.material.properties.SeedsProperty;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.registry.MaterialRegistry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.gregtechceu.gtceu.common.data.GTBlocks;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import com.google.common.collect.ImmutableTable;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import org.apache.logging.log4j.Level;

import static com.illuminatijoe.gregcrops.GregCrops.GREGCROPS_REGISTRATE;

@SuppressWarnings("removal")
public class GregCropsBlocks {

    static ImmutableTable.Builder<TagPrefix, Material, BlockEntry<GregCropBlock>> CROP_BLOCKS_BUILDER = ImmutableTable
            .builder();

    public static ImmutableTable<TagPrefix, Material, BlockEntry<GregCropBlock>> CROP_BLOCKS;

    public static void generateCropBlocks() {
        GTCEu.LOGGER.log(Level.INFO, "[GregCrops]: Generating GTCEu Crop Blocks...");

        for (MaterialRegistry registry : GTCEuAPI.materialManager.getRegistries()) {
            GTRegistrate registrate = registry.getRegistrate();
            GTCEu.LOGGER.log(Level.INFO,
                    "[GregCrops]: Generating Crop Blocks for Registrate: " + registrate.getModid());

            for (Material material : registry.getAllMaterials()) {
                if (material.hasProperty(GregCropPropertyKeys.SEEDS)) {
                    GTCEu.LOGGER.log(Level.INFO,
                            "[GregCrops]: Generating Crop Blocks for Material: " + material.getName());
                    registerCropBlock(material, GREGCROPS_REGISTRATE);
                }
            }
        }

        CROP_BLOCKS = CROP_BLOCKS_BUILDER.build();
        GTCEu.LOGGER.log(Level.INFO, "[GregCrops]: Generating GTCEu Crop Blocks... Complete!");
    }

    private static void registerCropBlock(Material material, GTRegistrate registrate) {
        var tagPrefix = GregCropsTagPrefix.seeds;
        String id = tagPrefix.idPattern().formatted(material.getName());
        int color = material.getMaterialRGB();

        var blockEntry = registrate
                .block(id, p -> new GregCropBlock(p, tagPrefix, material))
                .initialProperties(() -> Blocks.WHEAT)
                .properties(p -> p
                        .noCollission()
                        .randomTicks()
                        .instabreak()
                        .sound(SoundType.CROP)
                        .noOcclusion())
                .transform(GTBlocks.unificationBlock(tagPrefix, material))
                .addLayer(() -> RenderType::cutout)
                .setData(ProviderType.BLOCKSTATE, (ctx, prov) -> {
                    var builder = prov.getVariantBuilder(ctx.getEntry());
                    for (int age = 0; age <= GregCropBlock.MAX_AGE; age++) {
                        builder.partialState().with(GregCropBlock.AGE, age).setModels(
                                new ConfiguredModel(
                                        prov.models().withExistingParent(id + "_stage" + age, "block/tinted_cross")
                                                .texture("cross", GregCrops.id("block/crop_stage" + age))));
                    }
                })
                .loot((prov, block) -> {
                    SeedsProperty prop = material.getProperty(GregCropPropertyKeys.SEEDS);
                    var seedItem = block.asItem();
                    var grownCropItem = ChemicalHelper
                            .get(prop.resolveDropTagPrefix(material), prop.resolveDropMaterial(material)).getItem();
                    var matureCondition = LootItemBlockStatePropertyCondition
                            .hasBlockStateProperties(block)
                            .setProperties(StatePropertiesPredicate.Builder
                                    .properties()
                                    .hasProperty(GregCropBlock.AGE, GregCropBlock.MAX_AGE));
                    prov.add(block, LootTable.lootTable()
                            .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                    .add(LootItem.lootTableItem(seedItem)))
                            .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                    .when(matureCondition)
                                    .add(LootItem.lootTableItem(grownCropItem))));
                })
                .setData(ProviderType.LANG, NonNullBiConsumer.noop())
                .color(() -> () -> (BlockColor) (state, level, pos, tintIndex) -> tintIndex == 0 ? color : -1)
                .item(BlockItem::new)
                .model((ctx, prov) -> prov.withExistingParent(
                        ctx.getName(), new ResourceLocation("item/generated"))
                        .texture("layer0", GregCrops.id("item/seeds"))
                        .texture("layer1", GregCrops.id("item/seeds_secondary")))
                .color(() -> () -> (ItemColor) (stack, tintIndex) -> tintIndex == 0 ? color : -1)
                .build()
                .register();

        CROP_BLOCKS_BUILDER.put(tagPrefix, material, blockEntry);
    }
}
