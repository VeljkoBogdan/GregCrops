# GregCrops

GregCrops adds farmable crops for GregTech materials. Designed mainly for modpack makers.

## Features:

- Works with any material - any material can be easily registered for a crop
- Fully configurable drops - you can add *any* item drop to a crop
- Java and KubeJS API - new crops can be added through either an addon mod or kubejs with just a single line of code

## How it works

Plant a material's seed on farmland like any vanilla crop. It grows through 6 stages. At the end you can harvest it to get the crop's drop and seed.

Crops don't allow bone-mealing.

## Adding Crops to Materials

### Java
```java
@SubscribeEvent
public static void materialModification(PostMaterialEvent event) {
    // Default: drops raw ore of the crop's own material when mature if it exists, if not then it finds another drop (dust, ingot, etc)
    GTMaterials.Copper.setProperty(GregCropPropertyKeys.SEEDS, new SeedsProperty());

    // Choose a specific drop tag prefix (dust in this case)
    GTMaterials.Iron.setProperty(GregCropPropertyKeys.SEEDS, new SeedsProperty(TagPrefix.dust));

    // You can also use any other material as a drop
    GTMaterials.Aluminium.setProperty(GregCropPropertyKeys.SEEDS,
            new SeedsProperty(TagPrefix.rawOre, GTMaterials.Bauxite));
    
    // You can also just use any other item using its id
    GTMaterials.Tin.setProperty(GregCropPropertyKeys.SEEDS,
            new SeedsProperty('minecraft:gunpowder'));
}
```
### KubeJS

```javascript
const $TagPrefix = Java.loadClass('com.gregtechceu.gtceu.api.data.tag.TagPrefix');

GTCEuStartupEvents.materialModification(event => {
    // GregCrops exposes SeedsProperty and GregCropPropertyKeys as globals
    GTMaterials.Copper.setProperty(GregCropPropertyKeys.SEEDS, new SeedsProperty());

    GTMaterials.Iron.setProperty(GregCropPropertyKeys.SEEDS, new SeedsProperty($TagPrefix.dust));

    GTMaterials.Aluminium.setProperty(GregCropPropertyKeys.SEEDS,
        new SeedsProperty($TagPrefix.rawOre, GTMaterials.Bauxite));

    GTMaterials.Tin.setProperty(GregCropPropertyKeys.SEEDS,
        new SeedsProperty('minecraft:gunpowder'));
});
```

### Overriding an Existing Crop

There is a mod config to remove all properties if you want to start from scratch.

If you don't want to start from scratch, you have to remove the property before setting a new one, same as any other GTCEu material property:

```java
GTMaterials.Copper.removeProperty(GregCropPropertyKeys.SEEDS);
GTMaterials.Copper.setProperty(GregCropPropertyKeys.SEEDS, new SeedsProperty(TagPrefix.ingot));
```
