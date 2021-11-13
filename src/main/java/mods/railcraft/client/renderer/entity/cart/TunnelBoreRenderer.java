package mods.railcraft.client.renderer.entity.cart;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mods.railcraft.Railcraft;
import mods.railcraft.api.carts.TunnelBoreHead;
import mods.railcraft.client.model.TunnelBoreModel;
import mods.railcraft.season.Seasons;
import mods.railcraft.world.entity.cart.TunnelBoreEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class TunnelBoreRenderer extends EntityRenderer<TunnelBoreEntity> {

  private static final ResourceLocation TEXTURE =
      new ResourceLocation(Railcraft.ID, "textures/entity/tunnel_bore/tunnel_bore.png");

  protected TunnelBoreModel modelTunnelBore;

  public TunnelBoreRenderer(EntityRendererManager renderManager) {
    super(renderManager);
    this.shadowRadius = 0.5F;
    modelTunnelBore = new TunnelBoreModel();
  }

  // A lot of this is copied from the minecart renderer.
  @Override
  public void render(TunnelBoreEntity bore, float yaw, float partialTicks,
      MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int packedLight) {
    matrixStack.pushPose();
    {
      long var10 = (long) bore.getId() * 493286711L;
      var10 = var10 * var10 * 4392167121L + var10 * 98761L;
      float tx = (((float) (var10 >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float ty = (((float) (var10 >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float tz = (((float) (var10 >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      matrixStack.translate(tx, ty, tz);

      matrixStack.translate(0F, 0.375F, 0F);

      matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - yaw));
      matrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));

      float roll = (float) bore.getHurtTime() - partialTicks;
      float damage = bore.getDamage() - partialTicks;
      if (damage < 0.0F)
        damage = 0.0F;
      if (roll > 0.0F) {
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(
            MathHelper.sin(roll) * roll * damage / 10.0F * (float) bore.getHurtDir()));
      }

      float light = bore.getBrightness();
      light = light + ((1.0f - light) * 0.4f);

      boolean ghostTrain = Seasons.isGhostTrain(bore);
      float colorIntensity = ghostTrain ? 0.5F : 1.0F;

      TunnelBoreHead head = bore.getBoreHead();
      ResourceLocation textureLocation;
      if (head != null) {
        textureLocation = head.getTextureLocation();
        modelTunnelBore.setRenderBoreHead(true);
      } else {
        textureLocation = TEXTURE;
        modelTunnelBore.setRenderBoreHead(false);
      }

      matrixStack.scale(-1F, -1F, 1.0F);

      this.modelTunnelBore.setBoreHeadRotation(bore.getBoreRotationAngle());
      this.modelTunnelBore.setBoreActive(bore.isMinecartPowered());
      this.modelTunnelBore.setupAnim(bore, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F);
      IVertexBuilder vertexBuilder = renderTypeBuffer.getBuffer(
          this.modelTunnelBore.renderType(textureLocation));
      this.modelTunnelBore.renderToBuffer(matrixStack, vertexBuilder, packedLight,
          OverlayTexture.NO_OVERLAY, colorIntensity, colorIntensity, colorIntensity,
          ghostTrain ? 0.8F : 1.0F);
    }
    matrixStack.popPose();
  }

  @Override
  public ResourceLocation getTextureLocation(TunnelBoreEntity entity) {
    TunnelBoreHead head = entity.getBoreHead();
    return head == null ? TEXTURE : head.getTextureLocation();
  }
}