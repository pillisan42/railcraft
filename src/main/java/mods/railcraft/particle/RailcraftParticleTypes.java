package mods.railcraft.particle;

import com.mojang.serialization.MapCodec;
import mods.railcraft.api.core.RailcraftConstants;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RailcraftParticleTypes {

  private static final DeferredRegister<ParticleType<?>> deferredRegister =
      DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, RailcraftConstants.ID);

  public static final DeferredHolder<ParticleType<?>, SimpleParticleType> STEAM =
      deferredRegister.register("steam", () -> new SimpleParticleType(false));

  public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SPARK =
      deferredRegister.register("spark", () -> new SimpleParticleType(false));

  public static final DeferredHolder<ParticleType<?>, ParticleType<FireSparkParticleOptions>> FIRE_SPARK =
      deferredRegister.register("fire_spark",
          () -> create(FireSparkParticleOptions.CODEC, FireSparkParticleOptions.STREAM_CODEC));

  public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PUMPKIN =
      deferredRegister.register("pumpkin", () -> new SimpleParticleType(false));

  public static final DeferredHolder<ParticleType<?>, ParticleType<TuningAuraParticleOptions>> TUNING_AURA =
      deferredRegister.register("tuning_aura",
          () -> create(TuningAuraParticleOptions.CODEC, TuningAuraParticleOptions.STREAM_CODEC));

  public static final DeferredHolder<ParticleType<?>, ParticleType<ForceSpawnParticleOptions>> FORCE_SPAWN =
      deferredRegister.register("force_spawn",
          () -> create(ForceSpawnParticleOptions.CODEC, ForceSpawnParticleOptions.STREAM_CODEC));

  public static final DeferredHolder<ParticleType<?>, ParticleType<ChimneyParticleOptions>> CHIMNEY =
      deferredRegister.register("chimney",
          () -> create(ChimneyParticleOptions.CODEC, ChimneyParticleOptions.STREAM_CODEC));

  public static final DeferredHolder<ParticleType<?>, ParticleType<ChunkLoaderParticleOptions>> CHUNK_LOADER =
      deferredRegister.register("chunk_loader",
          () -> create(ChunkLoaderParticleOptions.CODEC, ChunkLoaderParticleOptions.STREAM_CODEC));

  public static void register(IEventBus modEventBus) {
    deferredRegister.register(modEventBus);
  }

  private static <T extends ParticleOptions> ParticleType<T> create(
      MapCodec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
    return new ParticleType<>(false) {
      @Override
      public MapCodec<T> codec() {
        return codec;
      }

      @Override
      public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
        return streamCodec;
      }
    };
  }
}
