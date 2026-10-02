package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.CMModelLayers;
import com.github.L_Ender.cataclysm.client.model.entity.Aptrgangr_Model;
import com.github.L_Ender.cataclysm.client.render.layer.AptrgangrRiderLayer;
import com.github.L_Ender.cataclysm.client.render.layer.Aptrgangr_Layer;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Aptrgangr_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Aptrgangr_Renderer extends MobRenderer<Aptrgangr_Entity, Aptrgangr_Model> {
   private final RandomSource rnd = RandomSource.m_216327_();
   private static final ResourceLocation APTRGANGR_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/draugar/aptrgangr.png");

   public Aptrgangr_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new Aptrgangr_Model(renderManagerIn.m_174023_(CMModelLayers.APTRGANGR_MODEL)), 1.25F);
      this.m_115326_(new AptrgangrRiderLayer(this));
      this.m_115326_(new Aptrgangr_Layer(this));
   }

   public Vec3 getRenderOffset(Aptrgangr_Entity entityIn, float partialTicks) {
      if (entityIn.getAttackState() == 4) {
         double d0 = 0.01;
         return new Vec3(this.rnd.m_188583_() * d0, this.rnd.m_188583_() * d0, this.rnd.m_188583_() * d0);
      } else {
         return super.m_7860_(entityIn, partialTicks);
      }
   }

   public ResourceLocation getTextureLocation(Aptrgangr_Entity entity) {
      return APTRGANGR_TEXTURES;
   }

   protected float getFlipDegrees(Aptrgangr_Entity entity) {
      return 0.0F;
   }

   protected void scale(Aptrgangr_Entity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.m_85841_(1.35F, 1.35F, 1.35F);
   }
}
