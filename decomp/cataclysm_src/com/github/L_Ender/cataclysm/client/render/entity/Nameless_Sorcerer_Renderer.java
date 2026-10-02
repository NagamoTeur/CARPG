package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Nameless_Sorcerer_Model;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Nameless_Sorcerer_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Nameless_Sorcerer_Renderer extends MobRenderer<Nameless_Sorcerer_Entity, Nameless_Sorcerer_Model> {
   private static final ResourceLocation NAMELESS_SORCERER_TEXTURES = new ResourceLocation("cataclysm", "textures/entity/nameless_sorcerer.png");

   public Nameless_Sorcerer_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new Nameless_Sorcerer_Model(), 0.5F);
   }

   public ResourceLocation getTextureLocation(Nameless_Sorcerer_Entity entity) {
      return NAMELESS_SORCERER_TEXTURES;
   }

   protected void scale(Nameless_Sorcerer_Entity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.m_85841_(0.9375F, 0.9375F, 0.9375F);
   }
}
