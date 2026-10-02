package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Deepling_Brute_Model;
import com.github.L_Ender.cataclysm.client.render.layer.AbstractDeepling_Layer;
import com.github.L_Ender.cataclysm.client.render.layer.LayerDeeplingBruteItem;
import com.github.L_Ender.cataclysm.entity.Deepling.Deepling_Brute_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Deepling_Brute_Renderer extends MobRenderer<Deepling_Brute_Entity, Deepling_Brute_Model> {
   private static final ResourceLocation SSAPBUG_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/deepling/deepling_brute.png");
   private static final ResourceLocation DEEPLING_LAYER_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/deepling/deepling_brute_layer.png");

   public Deepling_Brute_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new Deepling_Brute_Model(), 0.7F);
      this.m_115326_(new AbstractDeepling_Layer(this, DEEPLING_LAYER_TEXTURES));
      this.m_115326_(new LayerDeeplingBruteItem(this, renderManagerIn.m_234598_()));
   }

   public ResourceLocation getTextureLocation(Deepling_Brute_Entity entity) {
      return SSAPBUG_TEXTURES;
   }

   protected void scale(Deepling_Brute_Entity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.m_85841_(1.125F, 1.125F, 1.125F);
   }

   protected void setupRotations(Deepling_Brute_Entity p_115317_, PoseStack p_115318_, float p_115319_, float p_115320_, float p_115321_) {
      if (this.m_5936_(p_115317_)) {
         p_115320_ += (float)(Math.cos((double)p_115317_.f_19797_ * 3.25) * Math.PI * 0.4F);
      }

      if (!p_115317_.m_217003_(Pose.SLEEPING)) {
         p_115318_.m_85845_(Vector3f.f_122225_.m_122240_(180.0F - p_115320_));
      }

      if (p_115317_.f_20919_ > 0) {
         float f = ((float)p_115317_.f_20919_ + p_115321_ - 1.0F) / 20.0F * 1.6F;
         f = Mth.m_14116_(f);
         if (f > 1.0F) {
            f = 1.0F;
         }

         p_115318_.m_85845_(Vector3f.f_122227_.m_122240_(f * this.m_6441_(p_115317_)));
      } else if (p_115317_.m_21209_() || p_115317_.getSpinAttack()) {
         p_115318_.m_85845_(Vector3f.f_122223_.m_122240_(-90.0F - p_115317_.m_146909_()));
         p_115318_.m_85845_(Vector3f.f_122225_.m_122240_(((float)p_115317_.f_19797_ + p_115321_) * -75.0F));
      } else if (p_115317_.m_217003_(Pose.SLEEPING)) {
         Direction direction = p_115317_.m_21259_();
         float f1 = direction != null ? sleepDirectionToRotation(direction) : p_115320_;
         p_115318_.m_85845_(Vector3f.f_122225_.m_122240_(f1));
         p_115318_.m_85845_(Vector3f.f_122227_.m_122240_(this.m_6441_(p_115317_)));
         p_115318_.m_85845_(Vector3f.f_122225_.m_122240_(270.0F));
      } else if (m_194453_(p_115317_)) {
         p_115318_.m_85837_(0.0, (double)(p_115317_.m_20206_() + 0.1F), 0.0);
         p_115318_.m_85845_(Vector3f.f_122227_.m_122240_(180.0F));
      }
   }

   private static float sleepDirectionToRotation(Direction p_115329_) {
      switch (p_115329_) {
         case SOUTH:
            return 90.0F;
         case WEST:
            return 0.0F;
         case NORTH:
            return 270.0F;
         case EAST:
            return 180.0F;
         default:
            return 0.0F;
      }
   }
}
