package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.CMModelLayers;
import com.github.L_Ender.cataclysm.client.model.entity.Draugr_Model;
import com.github.L_Ender.cataclysm.client.render.layer.LayerGenericGlowing;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Draugr_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class Draugr_Renderer extends MobRenderer<Draugr_Entity, Draugr_Model> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("cataclysm", "textures/entity/draugar/draugr.png");
   private static final ResourceLocation LAYER = new ResourceLocation("cataclysm", "textures/entity/draugar/draugr_layer.png");

   public Draugr_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new Draugr_Model(renderManagerIn.m_174023_(CMModelLayers.DRAUGR_MODEL)), 0.5F);
      this.m_115326_(new LayerGenericGlowing(this, LAYER));
      this.m_115326_(new ItemInHandLayer(this, renderManagerIn.m_234598_()));
   }

   protected void scale(Draugr_Entity p_114907_, PoseStack p_114908_, float p_114909_) {
      float f = 1.0F;
      p_114908_.m_85841_(f, f, f);
      super.m_7546_(p_114907_, p_114908_, p_114909_);
   }

   public ResourceLocation getTextureLocation(Draugr_Entity entity) {
      return TEXTURE;
   }
}
