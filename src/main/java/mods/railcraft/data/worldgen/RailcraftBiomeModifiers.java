package mods.railcraft.data.worldgen;

import mods.railcraft.api.core.RailcraftConstants;
import mods.railcraft.data.worldgen.placements.RailcraftOrePlacements;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class RailcraftBiomeModifiers {

  private static final ResourceKey<BiomeModifier> LEAD_ORE = createKey("add_lead_ore");
  private static final ResourceKey<BiomeModifier> TIN_ORE_SMALL = createKey("add_tin_ore_small");
  private static final ResourceKey<BiomeModifier> TIN_ORE_LARGE = createKey("add_tin_ore_large");
  private static final ResourceKey<BiomeModifier> SULFUR_ORE_UPPER =
      createKey("add_sulfur_ore_upper");
  private static final ResourceKey<BiomeModifier> SULFUR_ORE_LOWER =
      createKey("add_sulfur_ore_lower");
  private static final ResourceKey<BiomeModifier> ZINC_ORE = createKey("add_zinc_ore");
  private static final ResourceKey<BiomeModifier> NICKEL_ORE_UPPER =
      createKey("add_nickel_ore_upper");
  private static final ResourceKey<BiomeModifier> NICKEL_ORE_MIDDLE =
      createKey("add_nickel_ore_middle");
  private static final ResourceKey<BiomeModifier> NICKEL_ORE_SMALL =
      createKey("add_nickel_ore_small");
  private static final ResourceKey<BiomeModifier> SILVER_ORE = createKey("add_silver_ore");
  private static final ResourceKey<BiomeModifier> SILVER_ORE_LOWER =
      createKey("add_silver_ore_lower");
  private static final ResourceKey<BiomeModifier> QUARRIED_STONE = createKey("add_quarried_stone");
  private static final ResourceKey<BiomeModifier> SALTPETER = createKey("add_saltpeter");
  private static final ResourceKey<BiomeModifier> FIRESTONE = createKey("add_firestone");

  public static void bootstrap(BootstrapContext<BiomeModifier> context) {
    var overworldTag = context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD);
    var netherTag = context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_NETHER);
    var forestTag = context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_FOREST);

    context.register(LEAD_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
        overworldTag,
        getPlacedFeature(context, RailcraftOrePlacements.LEAD_ORE),
        Decoration.UNDERGROUND_ORES));
    context.register(TIN_ORE_SMALL, new BiomeModifiers.AddFeaturesBiomeModifier(
        overworldTag,
        getPlacedFeature(context, RailcraftOrePlacements.TIN_ORE_SMALL),
        Decoration.UNDERGROUND_ORES));
    context.register(TIN_ORE_LARGE, new BiomeModifiers.AddFeaturesBiomeModifier(
        overworldTag,
        getPlacedFeature(context, RailcraftOrePlacements.TIN_ORE_LARGE),
        Decoration.UNDERGROUND_ORES));
    context.register(SULFUR_ORE_UPPER, new BiomeModifiers.AddFeaturesBiomeModifier(
        overworldTag,
        getPlacedFeature(context, RailcraftOrePlacements.SULFUR_ORE_UPPER),
        Decoration.UNDERGROUND_ORES));
    context.register(SULFUR_ORE_LOWER, new BiomeModifiers.AddFeaturesBiomeModifier(
        overworldTag,
        getPlacedFeature(context, RailcraftOrePlacements.SULFUR_ORE_LOWER),
        Decoration.UNDERGROUND_ORES));
    context.register(ZINC_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
        overworldTag,
        getPlacedFeature(context, RailcraftOrePlacements.ZINC_ORE),
        Decoration.UNDERGROUND_ORES));
    context.register(NICKEL_ORE_UPPER, new BiomeModifiers.AddFeaturesBiomeModifier(
        overworldTag,
        getPlacedFeature(context, RailcraftOrePlacements.NICKEL_ORE_UPPER),
        Decoration.UNDERGROUND_ORES));
    context.register(NICKEL_ORE_MIDDLE, new BiomeModifiers.AddFeaturesBiomeModifier(
        overworldTag,
        getPlacedFeature(context, RailcraftOrePlacements.NICKEL_ORE_MIDDLE),
        Decoration.UNDERGROUND_ORES));
    context.register(NICKEL_ORE_SMALL, new BiomeModifiers.AddFeaturesBiomeModifier(
        overworldTag,
        getPlacedFeature(context, RailcraftOrePlacements.NICKEL_ORE_SMALL),
        Decoration.UNDERGROUND_ORES));
    context.register(SILVER_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
        overworldTag,
        getPlacedFeature(context, RailcraftOrePlacements.SILVER_ORE),
        Decoration.UNDERGROUND_ORES));
    context.register(SILVER_ORE_LOWER, new BiomeModifiers.AddFeaturesBiomeModifier(
        overworldTag,
        getPlacedFeature(context, RailcraftOrePlacements.SILVER_ORE_LOWER),
        Decoration.UNDERGROUND_ORES));
    context.register(QUARRIED_STONE, new BiomeModifiers.AddFeaturesBiomeModifier(
        forestTag,
        getPlacedFeature(context, RailcraftOrePlacements.QUARRIED_STONE),
        Decoration.UNDERGROUND_ORES));
    context.register(SALTPETER, new BiomeModifiers.AddFeaturesBiomeModifier(
        overworldTag,
        getPlacedFeature(context, RailcraftOrePlacements.SALTPETER),
        Decoration.UNDERGROUND_ORES));
    context.register(FIRESTONE, new BiomeModifiers.AddFeaturesBiomeModifier(
        netherTag,
        getPlacedFeature(context, RailcraftOrePlacements.FIRESTONE),
        Decoration.UNDERGROUND_ORES));
  }

  private static HolderSet<PlacedFeature> getPlacedFeature(
      BootstrapContext<BiomeModifier> context, ResourceKey<PlacedFeature> resourceKey) {
    return HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(resourceKey));
  }

  private static ResourceKey<BiomeModifier> createKey(String name) {
    return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, RailcraftConstants.rl(name));
  }
}
