package com.illuminatijoe.gregcrops.integration.kubejs;

import com.illuminatijoe.gregcrops.GregCrops;
import com.illuminatijoe.gregcrops.api.data.GregCropsMaterialIconType;
import com.illuminatijoe.gregcrops.api.data.GregCropsTagPrefix;
import com.illuminatijoe.gregcrops.api.data.chemical.material.properties.GregCropPropertyKeys;
import com.illuminatijoe.gregcrops.api.data.chemical.material.properties.SeedsProperty;
import com.illuminatijoe.gregcrops.data.blocks.GregCropBlock;
import com.illuminatijoe.gregcrops.data.blocks.GregCropsBlocks;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ClassFilter;

public class GregCropsKubeJSPlugin extends KubeJSPlugin {

    @Override
    public void initStartup() {
        super.initStartup();
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void registerClasses(ScriptType type, ClassFilter filter) {
        super.registerClasses(type, filter);
        filter.allow("com.illuminatijoe.gregcrops");
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        super.registerBindings(event);
        event.add("GregCrops", GregCrops.class);
        event.add("GregCropPropertyKeys", GregCropPropertyKeys.class);
        event.add("SeedsProperty", SeedsProperty.class);
        event.add("GregCropsMaterialIconType", GregCropsMaterialIconType.class);
        event.add("GregCropsMaterialTagPrefix", GregCropsTagPrefix.class);
        event.add("GregCropBlock", GregCropBlock.class);
        event.add("GregCropBlocks", GregCropsBlocks.class);
    }
}
