package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.The_Leviathan_Model;
import com.github.L_Ender.cataclysm.client.model.entity.The_Leviathan_Tongue_End_Model;
import com.github.L_Ender.cataclysm.client.model.entity.The_Leviathan_Tongue_Model;
import com.github.L_Ender.cataclysm.client.render.RenderUtils;
import com.github.L_Ender.cataclysm.client.render.layer.LayerBasicGlow;
import com.github.L_Ender.cataclysm.client.render.layer.The_Leviathan_Layer;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.The_Leviathan_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.The_Leviathan_Part;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class The_Leviathan_Renderer extends MobRenderer<The_Leviathan_Entity, The_Leviathan_Model> {
   private static final ResourceLocation LEVIATHAN_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/leviathan/the_leviathan.png");
   private static final ResourceLocation BURNING_LEVIATHAN_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/leviathan/the_burning_leviathan.png");
   private static final ResourceLocation LEVIATHAN_TEXTURE_EYES = new ResourceLocation("cataclysm", "textures/entity/leviathan/the_leviathan_eye.png");
   private final RandomSource rnd = RandomSource.m_216327_();
   private static final The_Leviathan_Tongue_Model TONGUE_MODEL = new The_Leviathan_Tongue_Model();
   private static final The_Leviathan_Tongue_End_Model TONGUE_END_MODEL = new The_Leviathan_Tongue_End_Model();

   public The_Leviathan_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new The_Leviathan_Model(), 1.5F);
      this.m_115326_(new The_Leviathan_Layer(this));
      this.m_115326_(new LayerBasicGlow(this, LEVIATHAN_TEXTURE_EYES));
   }

   public ResourceLocation getTextureLocation(The_Leviathan_Entity entity) {
      return entity.getMeltDown() ? BURNING_LEVIATHAN_TEXTURES : LEVIATHAN_TEXTURES;
   }

   public boolean shouldRender(The_Leviathan_Entity livingentity, Frustum camera, double camX, double camY, double camZ) {
      if (super.m_5523_(livingentity, camera, camX, camY, camZ)) {
         return true;
      } else {
         for (The_Leviathan_Part part : livingentity.leviathanParts) {
            if (camera.m_113029_(part.m_20191_())) {
               return true;
            }
         }

         return false;
      }
   }

   public Vec3 getRenderOffset(The_Leviathan_Entity entity, float partialTicks) {
      if (entity.getAnimation() == The_Leviathan_Entity.LEVIATHAN_ABYSS_BLAST && entity.getAnimationTick() <= 66) {
         double d0 = 0.01;
         return new Vec3(this.rnd.m_188583_() * d0, 0.0, this.rnd.m_188583_() * d0);
      } else {
         return super.m_7860_(entity, partialTicks);
      }
   }

   public void render(The_Leviathan_Entity entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      super.m_7392_(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
      if (entity.getAnimation() == The_Leviathan_Entity.LEVIATHAN_TAIL_WHIPS) {
         Vec3 bladePos = RenderUtils.matrixStackFromCitadelModel(entity, entityYaw, ((The_Leviathan_Model)this.f_115290_).Tail_Particle);
         entity.setSocketPosArray(0, bladePos);
      }

      double x = Mth.m_14139_((double)partialTicks, entity.f_19790_, entity.m_20185_());
      double y = Mth.m_14139_((double)partialTicks, entity.f_19791_, entity.m_20186_());
      double z = Mth.m_14139_((double)partialTicks, entity.f_19792_, entity.m_20189_());
      float yaw = entity.f_20884_ + (entity.f_20883_ - entity.f_20884_) * partialTicks;
      Entity weapon = entity.getTongue();
      if (weapon != null && entity.m_6084_() && weapon.m_6084_()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(-x, -y, -z);
         Vec3 headModelPos = ((The_Leviathan_Model)this.m_7200_()).translateToTongue(new Vec3(0.0, 0.0, 0.0), yaw).m_82490_(0.2);
         Vec3 fromVec = entity.getTonguePosition().m_82549_(headModelPos);
         Vec3 toVec = weapon.m_20318_(partialTicks);
         int segmentCount = 0;
         Vec3 currentNeckButt = fromVec;
         VertexConsumer neckConsumer = bufferIn.m_6299_(RenderType.m_110458_(LEVIATHAN_TEXTURES));

         for (double remainingDistance = toVec.m_82554_(fromVec); segmentCount < 128 && remainingDistance > 0.0; segmentCount++) {
            remainingDistance = Math.min(fromVec.m_82554_(toVec), 0.5);
            Vec3 linearVec = toVec.m_82546_(currentNeckButt);
            Vec3 powVec = new Vec3(this.modifyVecAngle(linearVec.f_82479_), this.modifyVecAngle(linearVec.f_82480_), this.modifyVecAngle(linearVec.f_82481_));
            Vec3 next = powVec.m_82541_().m_82490_(remainingDistance).m_82549_(currentNeckButt);
            int neckLight = this.getLightColor(entity, toVec.m_82549_(currentNeckButt).m_82520_(x, y, z));
            renderNeckCube(currentNeckButt, next, matrixStackIn, neckConsumer, neckLight, OverlayTexture.f_118083_, 0.0F);
            currentNeckButt = next;
         }

         VertexConsumer clawConsumer = bufferIn.m_6299_(RenderType.m_110458_(LEVIATHAN_TEXTURES));
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(toVec.f_82479_, toVec.f_82480_, toVec.f_82481_);
         matrixStackIn.m_85837_(0.0, -0.5, 0.0);
         float rotY = (float)(Mth.m_14136_(toVec.f_82479_, toVec.f_82481_) * 180.0F / (float)Math.PI);
         float rotX = (float)(-(Mth.m_14136_(toVec.f_82480_, toVec.m_165924_()) * 180.0F / (float)Math.PI));
         TONGUE_END_MODEL.setAttributes(rotX, rotY);
         TONGUE_END_MODEL.m_7695_(
            matrixStackIn, clawConsumer, this.getLightColor(entity, toVec.m_82520_(x, y, z)), OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F
         );
         matrixStackIn.m_85849_();
         matrixStackIn.m_85849_();
      }
   }

   public static void renderNeckCube(Vec3 from, Vec3 to, PoseStack poseStack, VertexConsumer buffer, int packedLightIn, int overlayCoords, float additionalYaw) {
      Vec3 sub = from.m_82546_(to);
      double d = sub.m_165924_();
      float rotY = (float)(Mth.m_14136_(sub.f_82479_, sub.f_82481_) * 180.0F / (float)Math.PI);
      float rotX = (float)(-(Mth.m_14136_(sub.f_82480_, d) * 180.0F / (float)Math.PI)) - 90.0F;
      poseStack.m_85836_();
      poseStack.m_85837_(from.f_82479_, from.f_82480_, from.f_82481_);
      poseStack.m_85837_(0.0, -0.5, 0.0);
      TONGUE_MODEL.setAttributes((float)sub.m_82553_(), rotX, rotY, additionalYaw);
      TONGUE_MODEL.m_7695_(poseStack, buffer, packedLightIn, overlayCoords, 1.0F, 1.0F, 1.0F, 1.0F);
      poseStack.m_85849_();
   }

   private double modifyVecAngle(double dimension) {
      float abs = (float)Math.abs(dimension);
      return Math.signum(dimension) * Mth.m_14008_(Math.pow((double)abs, 0.1), 0.05 * (double)abs, (double)abs);
   }

   private int getLightColor(Entity head, Vec3 vec3) {
      return 15728880;
   }

   protected void scale(The_Leviathan_Entity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.m_85841_(1.75F, 1.75F, 1.75F);
   }

   protected float getFlipDegrees(The_Leviathan_Entity entity) {
      return 0.0F;
   }
}
