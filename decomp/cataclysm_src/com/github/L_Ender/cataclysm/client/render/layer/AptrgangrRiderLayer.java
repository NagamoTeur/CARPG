package com.github.L_Ender.cataclysm.client.render.layer;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.client.model.entity.Aptrgangr_Model;
import com.github.L_Ender.cataclysm.client.render.entity.Aptrgangr_Renderer;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Aptrgangr_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class AptrgangrRiderLayer extends RenderLayer<Aptrgangr_Entity, Aptrgangr_Model> {
   public AptrgangrRiderLayer(Aptrgangr_Renderer render) {
      super(render);
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource bufferIn,
      int packedLightIn,
      Aptrgangr_Entity entity,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      float bodyYaw = entity.f_20884_ + (entity.f_20883_ - entity.f_20884_) * partialTicks;
      if (entity.m_20160_()) {
         Vec3 offset = new Vec3(0.0, 0.0, 0.0);
         Vec3 ridePos = this.getRiderPosition(offset);

         for (Entity passenger : entity.m_20197_()) {
            if (passenger != Minecraft.m_91087_().f_91074_ || !Minecraft.m_91087_().f_91066_.m_92176_().m_90612_()) {
               poseStack.m_85836_();
               poseStack.m_85837_(ridePos.f_82479_, ridePos.f_82480_ - 0.65F + (double)passenger.m_20206_(), ridePos.f_82481_);
               poseStack.m_85845_(Vector3f.f_122222_.m_122240_(180.0F));
               poseStack.m_85845_(Vector3f.f_122224_.m_122240_(360.0F - bodyYaw));
               Cataclysm.PROXY.releaseRenderingEntity(passenger.m_20148_());
               renderPassenger(passenger, 0.0, 0.0, 0.0, 0.0F, partialTicks, poseStack, bufferIn, packedLightIn);
               Cataclysm.PROXY.blockRenderingEntity(passenger.m_20148_());
               poseStack.m_85849_();
            }
         }
      }
   }

   public Vec3 getRiderPosition(Vec3 offsetIn) {
      PoseStack translationStack = new PoseStack();
      translationStack.m_85836_();
      ((Aptrgangr_Model)this.m_117386_()).translateToHand(translationStack);
      Vector4f armOffsetVec = new Vector4f((float)offsetIn.f_82479_, (float)offsetIn.f_82480_, (float)offsetIn.f_82481_, 1.0F);
      armOffsetVec.m_123607_(translationStack.m_85850_().m_85861_());
      Vec3 vec3 = new Vec3((double)armOffsetVec.m_123601_(), (double)armOffsetVec.m_123615_(), (double)armOffsetVec.m_123616_());
      translationStack.m_85849_();
      return vec3;
   }

   public static <E extends Entity> void renderPassenger(
      E entityIn, double x, double y, double z, float yaw, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferIn, int packedLight
   ) {
      EntityRenderer<? super E> render = null;
      EntityRenderDispatcher manager = Minecraft.m_91087_().m_91290_();

      try {
         render = manager.m_114382_(entityIn);
         if (render != null) {
            try {
               render.m_7392_(entityIn, yaw, partialTicks, matrixStack, bufferIn, packedLight);
            } catch (Throwable var18) {
               throw new ReportedException(CrashReport.m_127521_(var18, "Rendering entity in world"));
            }
         }
      } catch (Throwable var19) {
         CrashReport crashreport = CrashReport.m_127521_(var19, "Rendering entity in world");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Entity being rendered");
         entityIn.m_7976_(crashreportcategory);
         CrashReportCategory crashreportcategory1 = crashreport.m_127514_("Renderer details");
         crashreportcategory1.m_128159_("Assigned renderer", render);
         crashreportcategory1.m_128159_("Rotation", yaw);
         crashreportcategory1.m_128159_("Delta", partialTicks);
         throw new ReportedException(crashreport);
      }
   }
}
