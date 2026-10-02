package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Endermaptera_Model;
import com.github.L_Ender.cataclysm.client.render.layer.LayerGenericGlowing;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.Endermaptera_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Endermaptera_Renderer extends MobRenderer<Endermaptera_Entity, Endermaptera_Model> {
   private static final ResourceLocation SSAPBUG_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/ender_ssap_bug.png");
   private static final ResourceLocation SSAPBUG_LAYER_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/ender_ssap_bug_layer.png");

   public Endermaptera_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new Endermaptera_Model(), 0.7F);
      this.m_115326_(new LayerGenericGlowing(this, SSAPBUG_LAYER_TEXTURES));
   }

   public ResourceLocation getTextureLocation(Endermaptera_Entity entity) {
      return SSAPBUG_TEXTURES;
   }

   protected void scale(Endermaptera_Entity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.m_85841_(1.0F, 1.0F, 1.0F);
   }

   protected void setupRotations(Endermaptera_Entity entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
      if (this.m_5936_(entityLiving)) {
         rotationYaw += (float)(Math.cos((double)entityLiving.f_19797_ * 3.25) * Math.PI * 0.4F);
      }

      float trans = 0.5F;
      Pose pose = entityLiving.m_20089_();
      if (pose != Pose.SLEEPING) {
         float progresso = 1.0F
            - (entityLiving.prevAttachChangeProgress + (entityLiving.attachChangeProgress - entityLiving.prevAttachChangeProgress) * partialTicks);
         if (entityLiving.getAttachmentFacing() == Direction.DOWN) {
            matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(180.0F - rotationYaw));
            matrixStackIn.m_85837_(0.0, (double)trans, 0.0);
            if (entityLiving.f_19855_ < entityLiving.m_20186_()) {
               matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(90.0F * (1.0F - progresso)));
            } else {
               matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(-90.0F * (1.0F - progresso)));
            }

            matrixStackIn.m_85837_(0.0, (double)(-trans), 0.0);
         } else if (entityLiving.getAttachmentFacing() == Direction.UP) {
            matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(180.0F - rotationYaw));
            matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(180.0F));
            matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
            matrixStackIn.m_85837_(0.0, (double)(-trans), 0.0);
         } else {
            matrixStackIn.m_85837_(0.0, (double)trans, 0.0);
            switch (entityLiving.getAttachmentFacing()) {
               case NORTH:
                  matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(90.0F * progresso));
                  matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(0.0F));
                  break;
               case SOUTH:
                  matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
                  matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(90.0F * progresso));
                  break;
               case WEST:
                  matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
                  matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(90.0F - 90.0F * progresso));
                  matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(-90.0F));
                  break;
               case EAST:
                  matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
                  matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(90.0F * progresso - 90.0F));
                  matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(90.0F));
            }

            if (entityLiving.m_20184_().f_82480_ <= -0.001F) {
               matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(-180.0F));
            }

            matrixStackIn.m_85837_(0.0, (double)(-trans), 0.0);
         }
      }

      if (entityLiving.f_20919_ > 0) {
         float f = ((float)entityLiving.f_20919_ + partialTicks - 1.0F) / 20.0F * 1.6F;
         f = Mth.m_14116_(f);
         if (f > 1.0F) {
            f = 1.0F;
         }

         matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(f * this.m_6441_(entityLiving)));
      } else if (entityLiving.m_21209_()) {
         matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(-90.0F - entityLiving.m_146909_()));
         matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(((float)entityLiving.f_19797_ + partialTicks) * -75.0F));
      } else if (pose != Pose.SLEEPING && entityLiving.m_8077_()) {
         String s = ChatFormatting.m_126649_(entityLiving.m_7755_().getString());
         if ("Dinnerbone".equals(s) || "Grumm".equals(s)) {
            matrixStackIn.m_85837_(0.0, (double)(entityLiving.m_20206_() + 0.1F), 0.0);
            matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(180.0F));
         }
      }
   }
}
