package mods.railcraft.world.level.levelgen.feature;

import mods.railcraft.api.core.RailcraftConstants;
import mods.railcraft.world.level.levelgen.feature.configuration.QuarriedConfiguration;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RailcraftFeatures {

  private static final DeferredRegister<Feature<?>> deferredRegister =
      DeferredRegister.create(Registries.FEATURE, RailcraftConstants.ID);

  public static final DeferredHolder<Feature<?>, QuarriedFeature> QUARRIED_STONE =
      deferredRegister.register("quarried", () -> new QuarriedFeature(QuarriedConfiguration.CODEC));

  public static void register(IEventBus eventBus) {
    deferredRegister.register(eventBus);
  }
}
