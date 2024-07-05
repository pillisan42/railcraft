package mods.railcraft.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ChimneyParticleOptions(int color) implements ParticleOptions {

  public static final MapCodec<ChimneyParticleOptions> CODEC =
      RecordCodecBuilder.mapCodec(instance -> instance.group(
          Codec.INT.fieldOf("color").forGetter(ChimneyParticleOptions::color)
      ).apply(instance, ChimneyParticleOptions::new));

  public static final StreamCodec<FriendlyByteBuf, ChimneyParticleOptions> STREAM_CODEC =
      StreamCodec.composite(
          ByteBufCodecs.VAR_INT, ChimneyParticleOptions::color,
          ChimneyParticleOptions::new);

  @Override
  public ParticleType<?> getType() {
    return RailcraftParticleTypes.CHIMNEY.get();
  }
}
