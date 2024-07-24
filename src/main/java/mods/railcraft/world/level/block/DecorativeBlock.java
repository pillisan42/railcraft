package mods.railcraft.world.level.block;

import net.minecraft.util.StringRepresentable;

public enum DecorativeBlock implements StringRepresentable {

  QUARRIED("quarried"),
  ABYSSAL("abyssal");

  private final String name;

  DecorativeBlock(String name) {
    this.name = name;
  }

  @Override
  public String getSerializedName() {
    return this.name;
  }
}
