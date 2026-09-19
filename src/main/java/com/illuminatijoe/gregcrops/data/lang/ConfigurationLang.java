package com.illuminatijoe.gregcrops.data.lang;

import com.tterrag.registrate.providers.RegistrateLangProvider;

public class ConfigurationLang {

    public static void init(final RegistrateLangProvider provider) {
        provider.add("config.screen.gregcrops", "Greg Crops Configuration");

        provider.add("config.gregcrops.option.generateDefaultSeeds", "Generate default seeds");
        provider.add("config.gregcrops.option.generateDefaultRecipes", "Generate default seed recipes");
    }
}
