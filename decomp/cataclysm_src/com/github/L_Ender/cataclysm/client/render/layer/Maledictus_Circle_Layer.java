package com.github.L_Ender.cataclysm.client.render.layer;

import com.github.L_Ender.cataclysm.client.model.entity.Maledictus_Model;
import com.github.L_Ender.cataclysm.client.render.entity.Maledictus_Renderer;
import com.github.L_Ender.cataclysm.client.render.etc.LightningBoltData;
import com.github.L_Ender.cataclysm.client.render.etc.LightningRender;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Maledictus.Maledictus_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector4f;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Maledictus_Circle_Layer extends RenderLayer<Maledictus_Entity, Maledictus_Model> {
   protected final EntityRenderDispatcher entityRenderDispatcher;
   private Map<UUID, LightningRender> lightningRenderMap = new HashMap<>();
   private final RandomSource rnd = RandomSource.m_216327_();

   public Maledictus_Circle_Layer(Maledictus_Renderer renderIn, Context context) {
      super(renderIn);
      this.entityRenderDispatcher = context.m_174022_();
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      Maledictus_Entity entity,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      this.rendercicle(matrixStackIn, bufferIn, packedLightIn, entity, true);
      this.rendercicle(matrixStackIn, bufferIn, packedLightIn, entity, false);
      this.renderLightning(matrixStackIn, bufferIn, entity, partialTicks, true);
      this.renderLightning(matrixStackIn, bufferIn, entity, partialTicks, false);
   }

   private void rendercicle(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Maledictus_Entity entity, boolean right) {
      Quaternion camera = this.entityRenderDispatcher.m_114470_();
      matrixStackIn.m_85836_();
      matrixStackIn.m_85836_();
      Vec3 offset = new Vec3(0.0, 0.0, 0.0);
      Vec3 ridePos = this.getRiderPosition(offset, right);
      matrixStackIn.m_85837_(ridePos.f_82479_, ridePos.f_82480_, ridePos.f_82481_);
      matrixStackIn.m_85845_(camera);
      matrixStackIn.m_85837_(0.0, -0.1F, 0.0);
      matrixStackIn.m_85841_(0.9F, 0.9F, 0.9F);
      Pose posestack$pose = matrixStackIn.m_85850_();
      Matrix4f matrix4f = posestack$pose.m_85861_();
      Matrix3f matrix3f = posestack$pose.m_85864_();
      VertexConsumer portalStatic = bufferIn.m_6299_(RenderType.m_110454_(new ResourceLocation("cataclysm", "textures/particle/ring_1.png"), true));
      matrixStackIn.m_85837_(0.0, 0.1F, 0.0);
      if (entity.attackTicks > 1) {
         if (entity.getAttackState() == 1 && entity.attackTicks <= 50) {
            this.drawCircle(portalStatic, matrix4f, matrix3f, packedLightIn, 0.95F, 0.5215F, 0.1333F);
         }

         if (entity.getAttackState() == 2 && entity.attackTicks <= 50) {
            this.drawCircle(portalStatic, matrix4f, matrix3f, packedLightIn, 0.09F, 0.42F, 0.35F);
         }

         if (entity.getAttackState() == 3 && entity.attackTicks >= 15 && entity.attackTicks <= 65) {
            this.drawCircle(portalStatic, matrix4f, matrix3f, packedLightIn, 0.09F, 0.42F, 0.35F);
         }

         if (entity.getAttackState() == 7 && entity.attackTicks <= 50) {
            this.drawCircle(portalStatic, matrix4f, matrix3f, packedLightIn, 0.423F, 0.062F, 0.019F);
         }

         if (entity.getAttackState() == 8 && entity.attackTicks <= 50) {
            this.drawCircle(portalStatic, matrix4f, matrix3f, packedLightIn, 0.423F, 0.062F, 0.019F);
         }

         if ((entity.getAttackState() == 12 || entity.getAttackState() == 13 || entity.getAttackState() == 14 || entity.getAttackState() == 11)
            && entity.attackTicks <= 50) {
            this.drawCircle(portalStatic, matrix4f, matrix3f, packedLightIn, 0.423F, 0.062F, 0.019F);
         }

         if ((entity.getAttackState() == 15 || entity.getAttackState() == 16) && entity.attackTicks <= 50) {
            this.drawCircle(portalStatic, matrix4f, matrix3f, packedLightIn, 0.423F, 0.062F, 0.019F);
         }

         if (entity.getAttackState() == 18) {
            if (entity.attackTicks <= 21) {
               this.drawCircle(portalStatic, matrix4f, matrix3f, packedLightIn, 0.09F, 0.42F, 0.35F);
            }

            if (entity.attackTicks >= 25 && entity.attackTicks <= 34) {
               this.drawCircle(portalStatic, matrix4f, matrix3f, packedLightIn, 0.95F, 0.5215F, 0.1333F);
            }
         }

         if (entity.getAttackState() == 19) {
            if (entity.attackTicks <= 10) {
               this.drawCircle(portalStatic, matrix4f, matrix3f, packedLightIn, 0.09F, 0.42F, 0.35F);
            }

            if (entity.attackTicks >= 13 && entity.attackTicks <= 20) {
               this.drawCircle(portalStatic, matrix4f, matrix3f, packedLightIn, 0.95F, 0.5215F, 0.1333F);
            }
         }

         if (entity.getAttackState() == 21) {
            if (entity.attackTicks <= 10) {
               this.drawCircle(portalStatic, matrix4f, matrix3f, packedLightIn, 0.423F, 0.062F, 0.019F);
            }

            if (entity.attackTicks >= 13 && entity.attackTicks <= 20) {
               this.drawCircle(portalStatic, matrix4f, matrix3f, packedLightIn, 0.423F, 0.062F, 0.019F);
            }
         }

         if ((entity.getAttackState() == 22 || entity.getAttackState() == 23) && entity.attackTicks <= 21) {
            this.drawCircle(portalStatic, matrix4f, matrix3f, packedLightIn, 0.95F, 0.5215F, 0.1333F);
         }

         if (entity.getAttackState() == 24 && entity.attackTicks <= 50) {
            this.drawCircle(portalStatic, matrix4f, matrix3f, packedLightIn, 0.423F, 0.062F, 0.019F);
         }

         if (entity.getAttackState() == 27 && entity.attackTicks <= 44) {
            this.drawCircle(portalStatic, matrix4f, matrix3f, packedLightIn, 0.95F, 0.5215F, 0.1333F);
         }

         if (entity.getAttackState() == 28 && entity.attackTicks <= 26) {
            this.drawCircle(portalStatic, matrix4f, matrix3f, packedLightIn, 0.423F, 0.062F, 0.019F);
         }

         if (entity.getAttackState() == 29 && entity.attackTicks <= 26) {
            this.drawCircle(portalStatic, matrix4f, matrix3f, packedLightIn, 0.423F, 0.062F, 0.019F);
         }
      }

      matrixStackIn.m_85849_();
      matrixStackIn.m_85849_();
   }

   private void renderLightning(PoseStack matrixStackIn, MultiBufferSource bufferIn, Maledictus_Entity entity, float partialtick, boolean right) {
      matrixStackIn.m_85836_();
      Vec3 offset = new Vec3(0.0, 0.0, 0.0);
      Vec3 ridePos = this.getRiderPosition(offset, right);
      matrixStackIn.m_85837_(ridePos.f_82479_, ridePos.f_82480_, ridePos.f_82481_);
      if (entity.attackTicks > 1) {
         if (entity.getAttackState() == 1 && entity.attackTicks <= 50) {
            this.drawLightning(matrixStackIn, bufferIn, entity, 0.95F, 0.5215F, 0.1333F, partialtick);
         }

         if (entity.getAttackState() == 2 && entity.attackTicks <= 50) {
            this.drawLightning(matrixStackIn, bufferIn, entity, 0.09F, 0.42F, 0.35F, partialtick);
         }

         if (entity.getAttackState() == 3 && entity.attackTicks >= 15 && entity.attackTicks <= 65) {
            this.drawLightning(matrixStackIn, bufferIn, entity, 0.09F, 0.42F, 0.35F, partialtick);
         }

         if (entity.getAttackState() == 7 && entity.attackTicks <= 50) {
            this.drawLightning(matrixStackIn, bufferIn, entity, 0.423F, 0.062F, 0.019F, partialtick);
         }

         if (entity.getAttackState() == 8 && entity.attackTicks <= 50) {
            this.drawLightning(matrixStackIn, bufferIn, entity, 0.423F, 0.062F, 0.019F, partialtick);
         }

         if ((entity.getAttackState() == 12 || entity.getAttackState() == 13 || entity.getAttackState() == 14 || entity.getAttackState() == 11)
            && entity.attackTicks <= 50) {
            this.drawLightning(matrixStackIn, bufferIn, entity, 0.423F, 0.062F, 0.019F, partialtick);
         }

         if ((entity.getAttackState() == 15 || entity.getAttackState() == 16) && entity.attackTicks <= 50) {
            this.drawLightning(matrixStackIn, bufferIn, entity, 0.423F, 0.062F, 0.019F, partialtick);
         }

         if (entity.getAttackState() == 18) {
            if (entity.attackTicks <= 21) {
               this.drawLightning(matrixStackIn, bufferIn, entity, 0.09F, 0.42F, 0.35F, partialtick);
            }

            if (entity.attackTicks >= 25 && entity.attackTicks <= 34) {
               this.drawLightning(matrixStackIn, bufferIn, entity, 0.95F, 0.5215F, 0.1333F, partialtick);
            }
         }

         if (entity.getAttackState() == 19) {
            if (entity.attackTicks <= 10) {
               this.drawLightning(matrixStackIn, bufferIn, entity, 0.95F, 0.5215F, 0.1333F, partialtick);
            }

            if (entity.attackTicks >= 13 && entity.attackTicks <= 20) {
               this.drawLightning(matrixStackIn, bufferIn, entity, 0.95F, 0.5215F, 0.1333F, partialtick);
            }
         }

         if (entity.getAttackState() == 21) {
            if (entity.attackTicks <= 10) {
               this.drawLightning(matrixStackIn, bufferIn, entity, 0.423F, 0.062F, 0.019F, partialtick);
            }

            if (entity.attackTicks >= 13 && entity.attackTicks <= 20) {
               this.drawLightning(matrixStackIn, bufferIn, entity, 0.423F, 0.062F, 0.019F, partialtick);
            }
         }

         if ((entity.getAttackState() == 22 || entity.getAttackState() == 23) && entity.attackTicks <= 21) {
            this.drawLightning(matrixStackIn, bufferIn, entity, 0.95F, 0.5215F, 0.1333F, partialtick);
         }

         if (entity.getAttackState() == 24 && entity.attackTicks <= 50) {
            this.drawLightning(matrixStackIn, bufferIn, entity, 0.423F, 0.062F, 0.019F, partialtick);
         }

         if (entity.getAttackState() == 27 && entity.attackTicks <= 44) {
            this.drawLightning(matrixStackIn, bufferIn, entity, 0.95F, 0.5215F, 0.1333F, partialtick);
         }

         if (entity.getAttackState() == 28 && entity.attackTicks <= 26) {
            this.drawLightning(matrixStackIn, bufferIn, entity, 0.423F, 0.062F, 0.019F, partialtick);
         }

         if (entity.getAttackState() == 29 && entity.attackTicks <= 26) {
            this.drawLightning(matrixStackIn, bufferIn, entity, 0.423F, 0.062F, 0.019F, partialtick);
         }
      }

      matrixStackIn.m_85849_();
   }

   private void drawLightning(PoseStack matrixStackIn, MultiBufferSource bufferIn, Maledictus_Entity entity, float r, float g, float b, float partialTicks) {
      matrixStackIn.m_85836_();
      double x = (double)(this.rnd.m_188501_() - 0.25F);
      double y = (double)(this.rnd.m_188501_() - 0.25F);
      double z = (double)(this.rnd.m_188501_() - 0.25F);
      LightningBoltData.BoltRenderInfo blueBoltData = new LightningBoltData.BoltRenderInfo(0.5F, 0.1F, 0.5F, 0.85F, new Vector4f(r, g, b, 0.8F), 0.1F);
      LightningBoltData bolt1 = new LightningBoltData(blueBoltData, Vec3.f_82478_, new Vec3(x, y, z), 8)
         .size(0.1F)
         .lifespan(1)
         .spawn(LightningBoltData.SpawnFunction.CONSECUTIVE);
      LightningRender lightningRender = this.getLightingRender(entity.m_20148_());
      lightningRender.update(entity, bolt1, partialTicks);
      lightningRender.render(partialTicks, matrixStackIn, bufferIn);
      matrixStackIn.m_85849_();
      if (!entity.m_6084_() && this.lightningRenderMap.containsKey(entity.m_20148_())) {
         this.lightningRenderMap.remove(entity.m_20148_());
      }
   }

   private LightningRender getLightingRender(UUID uuid) {
      if (this.lightningRenderMap.get(uuid) == null) {
         this.lightningRenderMap.put(uuid, new LightningRender());
      }

      return this.lightningRenderMap.get(uuid);
   }

   private void drawCircle(VertexConsumer vertex, Matrix4f matrix4f, Matrix3f matrix3f, int packedLightIn, float r, float g, float b) {
      cirlceVertex(vertex, matrix4f, matrix3f, packedLightIn, 0.0F, 0, 0, 1, 1.0F, r, g, b);
      cirlceVertex(vertex, matrix4f, matrix3f, packedLightIn, 1.0F, 0, 1, 1, 1.0F, r, g, b);
      cirlceVertex(vertex, matrix4f, matrix3f, packedLightIn, 1.0F, 1, 1, 0, 1.0F, r, g, b);
      cirlceVertex(vertex, matrix4f, matrix3f, packedLightIn, 0.0F, 1, 0, 0, 1.0F, r, g, b);
   }

   private static void cirlceVertex(
      VertexConsumer vertex,
      Matrix4f mat4f,
      Matrix3f mat3f,
      int p_114093_,
      float p_114094_,
      int p_114095_,
      int p_114096_,
      int p_114097_,
      float alpha,
      float r,
      float g,
      float b
   ) {
      vertex.m_85982_(mat4f, p_114094_ - 0.5F, (float)p_114095_ - 0.25F, 0.0F)
         .m_85950_(r, g, b, alpha)
         .m_7421_((float)p_114096_, (float)p_114097_)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(240)
         .m_85977_(mat3f, 0.0F, -1.0F, 0.0F)
         .m_5752_();
   }

   public Vec3 getRiderPosition(Vec3 offsetIn, boolean right) {
      PoseStack translationStack = new PoseStack();
      translationStack.m_85836_();
      ((Maledictus_Model)this.m_117386_()).translateToHand(translationStack, right);
      Vector4f armOffsetVec = new Vector4f((float)offsetIn.f_82479_, (float)offsetIn.f_82480_, (float)offsetIn.f_82481_, 1.0F);
      armOffsetVec.m_123607_(translationStack.m_85850_().m_85861_());
      Vec3 vec3 = new Vec3((double)armOffsetVec.m_123601_(), (double)armOffsetVec.m_123615_(), (double)armOffsetVec.m_123616_());
      translationStack.m_85849_();
      return vec3;
   }
}
