package mods.railcraft.world.level.material;

import mods.railcraft.Railcraft;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RailcraftFluids {

  private static final DeferredRegister<Fluid> deferredRegister =
      DeferredRegister.create(ForgeRegistries.FLUIDS, Railcraft.ID);

  public static final RegistryObject<Fluid> STEAM =
      deferredRegister.register("steam", SteamFluid::new);

  public static final RegistryObject<FlowingFluid> CREOSOTE =
      deferredRegister.register("creosote", CreosoteFluid.Source::new);

  public static final RegistryObject<FlowingFluid> FLOWING_CREOSOTE =
      deferredRegister.register("flowing_creosote", CreosoteFluid.Flowing::new);

  public static void register(IEventBus modEventBus) {
    deferredRegister.register(modEventBus);
  }
}