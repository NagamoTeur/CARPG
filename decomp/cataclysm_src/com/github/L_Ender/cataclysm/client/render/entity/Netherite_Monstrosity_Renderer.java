package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Old_Netherite_Monstrosity_Model;
import com.github.L_Ender.cataclysm.client.render.layer.Old_Netherite_Monstrosity_Layer;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Old_Netherite_Monstrosity_Entity;
import com.github.L_Ender.cataclysm.entity.partentity.Old_Netherite_Monstrosity_Part;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Netherite_Monstrosity_Renderer extends MobRenderer<Old_Netherite_Monstrosity_Entity, Old_Netherite_Monstrosity_Model> {
   private static final ResourceLocation NETHER_MONSTROSITY_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/netherite_monstrosity.png");

   public Netherite_Monstrosity_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new Old_Netherite_Monstrosity_Model(), 2.5F);
      this.m_115326_(new Old_Netherite_Monstrosity_Layer(this));
   }

   public ResourceLocation getTextureLocation(Old_Netherite_Monstrosity_Entity entity) {
      return NETHER_MONSTROSITY_TEXTURES;
   }

   public boolean shouldRender(Old_Netherite_Monstrosity_Entity livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
      if (super.m_5523_(livingEntityIn, camera, camX, camY, camZ)) {
         return true;
      } else {
         for (Old_Netherite_Monstrosity_Part part : livingEntityIn.monstrosityParts) {
            if (camera.m_113029_(part.m_20191_())) {
               return true;
            }
         }

         return false;
      }
   }

   protected void scale(Old_Netherite_Monstrosity_Entity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.m_85841_(1.0F, 1.0F, 1.0F);
   }

   protected float getFlipDegrees(Old_Netherite_Monstrosity_Entity entity) {
      return 0.0F;
   }
}
