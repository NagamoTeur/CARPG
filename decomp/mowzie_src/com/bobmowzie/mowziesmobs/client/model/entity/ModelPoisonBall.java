package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.server.entity.effects.EntityPoisonBall;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelBase;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.phys.Vec3;

public class ModelPoisonBall<T extends EntityPoisonBall> extends AdvancedModelBase<T> {
   private final AdvancedModelRenderer inner;
   private final AdvancedModelRenderer outer;

   public ModelPoisonBall() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.inner = new AdvancedModelRenderer(this, 0, 16);
      this.inner.setRotationPoint(0.0F, 3.5F, 0.0F);
      this.inner.addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
      this.outer = new AdvancedModelRenderer(this, 0, 0);
      this.outer.setRotationPoint(0.0F, 3.5F, 0.0F);
      this.outer.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
      this.inner.setOpacity(1.0F);
      this.outer.setOpacity(0.6F);
   }

   public void m_7695_(
      PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha
   ) {
      this.inner.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
      this.outer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
   }

   public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float delta = ageInTicks - (float)entityIn.f_19797_;
      Vec3 prevV = new Vec3(entityIn.prevMotionX, entityIn.prevMotionY, entityIn.prevMotionZ);
      Vec3 dv = prevV.m_82549_(entityIn.m_20184_().m_82546_(prevV).m_82490_((double)delta));
      double d = Math.sqrt(dv.f_82479_ * dv.f_82479_ + dv.f_82480_ * dv.f_82480_ + dv.f_82481_ * dv.f_82481_);
      if (d != 0.0) {
         double a = dv.f_82480_ / d;
         a = Math.max(-1.0, Math.min(1.0, a));
         float pitch = -((float)Math.asin(a));
         this.inner.rotateAngleX = pitch + (float) (Math.PI / 2);
         this.outer.rotateAngleX = pitch + (float) (Math.PI / 2);
      }
   }

   public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
      modelRenderer.f_104200_ = x;
      modelRenderer.f_104201_ = y;
      modelRenderer.f_104202_ = z;
   }
}
