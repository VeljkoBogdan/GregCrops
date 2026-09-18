package com.illuminatijoe.gregcrops;

import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;

@Config(id = GregCrops.MOD_ID)
public final class GregCropsConfig {

    @Configurable
    @Configurable.Comment({ "If default seeds and drops should generate", "Restart game to take effect" })
    public boolean generateDefaultSeeds = true;

    @Configurable
    @Configurable.Comment({ "If default recipes should generate" })
    public boolean generateDefaultRecipes = true;
}
