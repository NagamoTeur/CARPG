package com.bobmowzie.mowziesmobs.client.render.entity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderDart extends ArrowRenderer {
   private static final ResourceLocation TEXTURE = new ResourceLocation("mowziesmobs", "textures/entity/dart.png");

   public RenderDart(Context mgr) {
      super(mgr);
   }

   public ResourceLocation m_5478_(Entity entity) {
      return TEXTURE;
   }
}
