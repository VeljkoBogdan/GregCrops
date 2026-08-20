package com.illuminatijoe.gregcrops.data.blocks;

import com.illuminatijoe.gregcrops.GregCrops;
import com.illuminatijoe.gregcrops.api.data.GregCropsTagPrefix;
import com.illuminatijoe.gregcrops.api.data.chemical.material.properties.GregCropPropertyKeys;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.registry.MaterialRegistry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

import com.tterrag.registrate.providers.loot.RegistrateLootTableProvider;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import com.google.common.collect.ImmutableTable;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraftforge.common.IPlantable;
import org.apache.logging.log4j.Level;

import static com.gregtechceu.gtceu.common.registry.GTRegistration.REGISTRATE;
import static com.illuminatijoe.gregcrops.GregCrops.GREGCROPS_REGISTRATE;

@SuppressWarnings("removal")
public class GregCropsBlocks {

    static ImmutableTable.Builder<TagPrefix, Material, BlockEntry<GregCropBlock>> CROP_BLOCKS_BUILDER = ImmutableTable
            .builder();

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

        GTCEu.LOGGER.log(Level.INFO, "[GregCrops]: Generating GTCEu Crop Blocks... Complete!");
    }

    private static void registerCropBlock(Material material, GTRegistrate registrate) {
        var tagPrefix = GregCropsTagPrefix.seeds;
        String id = tagPrefix.idPattern().formatted(material.getName());
        int color = material.getMaterialRGB();

        var blockEntry = registrate
                .block(id, p -> new GregCropBlock(p, tagPrefix, material))
                .initialProperties(() -> Blocks.WHEAT)
//                .properties(p -> p
//                        .noCollission()
//                        .randomTicks()
//                        .instabreak()
//                        .sound(SoundType.CROP)
//                        .noOcclusion())
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
                .setData(ProviderType.LOOT, (ctx, prov) ->
                        prov.addLootAction(RegistrateLootTableProvider.LootType.BLOCK, (tb) -> {
                            var block = ctx.getEntry();
                            if (!block.getLootTable().equals(BuiltInLootTables.EMPTY)) {
                                var seedItem = block.asItem(); //ChemicalHelper.get(tagPrefix, material).getItem();
                                var matureCondition = LootItemBlockStatePropertyCondition
                                        .hasBlockStateProperties(block)
                                        .setProperties(StatePropertiesPredicate.Builder
                                                .properties()
                                                .hasProperty(GregCropBlock.AGE, GregCropBlock.MAX_AGE));
                                tb.add(block, tb.createCropDrops(block, seedItem, seedItem, matureCondition));
                            }
                        }))
                .setData(ProviderType.LANG, NonNullBiConsumer.noop())
                .color(() -> () -> (BlockColor) (state, level, pos, tintIndex) -> tintIndex == 0 ? color : -1)
                //.item((b, p) -> tagPrefix.itemConstructor().create(p, tagPrefix, material))
                .item()
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
