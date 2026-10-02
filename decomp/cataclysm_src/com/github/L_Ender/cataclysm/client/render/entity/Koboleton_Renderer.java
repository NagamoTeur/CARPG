package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Koboleton_Model;
import com.github.L_Ender.cataclysm.client.render.layer.LayerKoboletonItem;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.Koboleton_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Koboleton_Renderer extends MobRenderer<Koboleton_Entity, Koboleton_Model> {
   private static final ResourceLocation KOBOLETON_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/koboleton/koboleton.png");

   public Koboleton_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new Koboleton_Model(), 0.5F);
      this.m_115326_(new LayerKoboletonItem(this, renderManagerIn.m_234598_()));
   }

   public ResourceLocation getTextureLocation(Koboleton_Entity entity) {
      return KOBOLETON_TEXTURES;
   }

   protected void scale(Koboleton_Entity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.m_85841_(1.0F, 1.0F, 1.0F);
   }
}
