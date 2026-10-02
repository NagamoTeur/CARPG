package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.CMModelLayers;
import com.github.L_Ender.cataclysm.client.model.entity.Kobolediator_Model;
import com.github.L_Ender.cataclysm.client.render.layer.Kobolediator_Layer;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Kobolediator_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Kobolediator_Renderer extends MobRenderer<Kobolediator_Entity, Kobolediator_Model> {
   private static final ResourceLocation KOBOLEDIATOR_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/koboleton/kobolediator.png");

   public Kobolediator_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new Kobolediator_Model(renderManagerIn.m_174023_(CMModelLayers.KOBOLEDIATOR_MODEL)), 1.25F);
      this.m_115326_(new Kobolediator_Layer(this));
   }

   public ResourceLocation getTextureLocation(Kobolediator_Entity entity) {
      return KOBOLEDIATOR_TEXTURES;
   }

   protected float getFlipDegrees(Kobolediator_Entity entity) {
      return 0.0F;
   }

   protected void scale(Kobolediator_Entity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.m_85841_(1.0F, 1.0F, 1.0F);
   }
}
