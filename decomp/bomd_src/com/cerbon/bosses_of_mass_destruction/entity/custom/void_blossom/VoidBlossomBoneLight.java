package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.client.render.IBoneLight;
import com.cerbon.bosses_of_mass_destruction.client.render.IRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector4f;
import net.minecraft.client.renderer.MultiBufferSource;
import software.bernie.geckolib3.geo.render.built.GeoBone;

public class VoidBlossomBoneLight implements IBoneLight, IRenderer<VoidBlossomEntity> {
   private VoidBlossomEntity entity;
   private Float partialTicks;

   @Override
   public int getLightForBone(GeoBone bone, int packedLight) {
      return !bone.getName().contains("Spike") && !bone.getName().contains("Thorn") && !bone.getName().contains("FlowerCenter") ? packedLight : 15728880;
   }

   @Override
   public Vector4f getColorForBone(GeoBone bone, Vector4f rgbaColor) {
      if (this.entity == null) {
         return rgbaColor;
      } else {
         float partialTicks1 = this.partialTicks != null ? this.partialTicks : 0.0F;
         Vector4f newColor = new Vector4f(rgbaColor.m_123601_(), rgbaColor.m_123615_(), rgbaColor.m_123616_(), rgbaColor.m_123617_());
         if (this.entity.m_21224_()) {
            float interceptedTime = MathUtils.ratioLerp((float)this.entity.f_20919_, 0.5F, 70.0F, partialTicks1);
            newColor.m_176870_(1.0F - interceptedTime * 0.5F);
         }

         return newColor;
      }
   }

   public void render(VoidBlossomEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
      this.entity = entity;
      this.partialTicks = partialTicks;
   }
}
