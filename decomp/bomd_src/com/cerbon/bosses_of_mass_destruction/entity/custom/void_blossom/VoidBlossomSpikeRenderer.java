package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.client.render.IRenderer;
import com.cerbon.bosses_of_mass_destruction.util.BMDColors;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopies;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import java.util.Map.Entry;
import java.util.function.Function;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class VoidBlossomSpikeRenderer implements IRenderer<VoidBlossomEntity> {
   private final ResourceLocation spikeTexture = new ResourceLocation("bosses_of_mass_destruction", "textures/entity/void_blossom_spike.png");
   private final RenderType type = RenderType.m_110458_(this.spikeTexture);

   public void render(VoidBlossomEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
      for (Entry<BlockPos, VoidBlossomClientSpikeHandler.Spike> kv : entity.clientSpikeHandler.getSpikes().entrySet()) {
         this.renderBeam(entity, kv.getValue(), partialTicks, poseStack, buffer, this.type);
      }
   }

   private void renderBeam(
      LivingEntity actor, VoidBlossomClientSpikeHandler.Spike spike, float tickDelta, PoseStack poseStack, MultiBufferSource bufferSource, RenderType type
   ) {
      float numTextures = 8.0F;
      float lifeRatio = 2.0F;
      float textureProgress = Math.max(0.0F, ((float)spike.age() + tickDelta) * lifeRatio / (float)spike.maxAge() - lifeRatio + 1.0F);
      if (!(textureProgress >= 1.0F)) {
         float spikeHeight = spike.height();
         float textureRatio = 0.34375F;
         float spikeWidth = textureRatio * spikeHeight * 0.5F;
         double upProgress = (Math.sin(Math.min((double)((float)spike.age() + tickDelta) / ((double)spike.maxAge() * 0.4), 1.0) * Math.PI * 0.5) - 1.0)
            * (double)spikeHeight;
         Function<Float, Float> texTransformer = this.textureMultiplier(
            1.0F / numTextures, (float)(Math.floor((double)(textureProgress * numTextures)) / (double)numTextures)
         );
         poseStack.m_85836_();
         Vec3 offset = VanillaCopies.fromLerpedPosition(actor, 0.0, tickDelta).m_82546_(spike.pos());
         poseStack.m_85837_(-offset.f_82479_, upProgress - offset.f_82480_, -offset.f_82481_);
         Vec3 bottomPos = spike.offset();
         float n = (float)Math.acos(bottomPos.f_82480_);
         float o = (float)Math.atan2(bottomPos.f_82481_, bottomPos.f_82479_);
         poseStack.m_85845_(Vector3f.f_122225_.m_122240_(((float) (Math.PI / 2) - o) * (180.0F / (float)Math.PI)));
         poseStack.m_85845_(Vector3f.f_122223_.m_122240_(n * (180.0F / (float)Math.PI)));
         float q = 0.0F;
         int red = (int)(BMDColors.WHITE.f_82479_ * 255.0);
         int green = (int)(BMDColors.WHITE.f_82480_ * 255.0);
         int blue = (int)(BMDColors.WHITE.f_82481_ * 255.0);
         float af = Mth.m_14089_(q + (float) Math.PI) * spikeWidth;
         float ag = Mth.m_14031_(q + (float) Math.PI) * spikeWidth;
         float ah = Mth.m_14089_(q + 0.0F) * spikeWidth;
         float ai = Mth.m_14031_(q + 0.0F) * spikeWidth;
         float aj = Mth.m_14089_(q + (float) (Math.PI / 2)) * spikeWidth;
         float ak = Mth.m_14031_(q + (float) (Math.PI / 2)) * spikeWidth;
         float al = Mth.m_14089_(q + (float) (Math.PI * 3.0 / 2.0)) * spikeWidth;
         float am = Mth.m_14031_(q + (float) (Math.PI * 3.0 / 2.0)) * spikeWidth;
         VertexConsumer vertexConsumer = bufferSource.m_6299_(type);
         Pose entry = poseStack.m_85850_();
         Matrix4f matrix4f = entry.m_85861_();
         Matrix3f matrix3f = entry.m_85864_();
         float c0 = texTransformer.apply(0.4999F);
         float c2 = texTransformer.apply(0.0F);
         float c1 = texTransformer.apply(1.0F);
         VanillaCopies.vertex(vertexConsumer, matrix4f, matrix3f, af, spikeHeight, ag, red, green, blue, c0, 0.0F);
         VanillaCopies.vertex(vertexConsumer, matrix4f, matrix3f, af, 0.0F, ag, red, green, blue, c0, 1.0F);
         VanillaCopies.vertex(vertexConsumer, matrix4f, matrix3f, ah, 0.0F, ai, red, green, blue, c2, 1.0F);
         VanillaCopies.vertex(vertexConsumer, matrix4f, matrix3f, ah, spikeHeight, ai, red, green, blue, c2, 0.0F);
         VanillaCopies.vertex(vertexConsumer, matrix4f, matrix3f, aj, spikeHeight, ak, red, green, blue, c1, 0.0F);
         VanillaCopies.vertex(vertexConsumer, matrix4f, matrix3f, aj, 0.0F, ak, red, green, blue, c1, 1.0F);
         VanillaCopies.vertex(vertexConsumer, matrix4f, matrix3f, al, 0.0F, am, red, green, blue, c0, 1.0F);
         VanillaCopies.vertex(vertexConsumer, matrix4f, matrix3f, al, spikeHeight, am, red, green, blue, c0, 0.0F);
         poseStack.m_85849_();
      }
   }

   public Function<Float, Float> textureMultiplier(Float multiplier, float adjustment) {
      return texCoord -> texCoord * multiplier + adjustment;
   }
}
