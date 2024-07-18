package mods.railcraft.world.level.material;

import java.util.function.Supplier;
import mods.railcraft.api.core.RailcraftConstants;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class RailcraftFluidTypes {

  private static final DeferredRegister<FluidType> deferredRegister =
      DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, RailcraftConstants.ID);

  public static final Supplier<FluidType> STEAM =
      deferredRegister.register("steam", () ->
          new FluidType(FluidType.Properties.create()
              .temperature(400) // in kelvin, 150c
              .density(-1000)
              .viscosity(500)));

  public static final Supplier<FluidType> CREOSOTE =
      deferredRegister.register("creosote", () ->
          new FluidType(FluidType.Properties.create()
              .density(1100)
              .viscosity(3000)));

  public static void register(IEventBus modEventBus) {
    deferredRegister.register(modEventBus);
  }
}
