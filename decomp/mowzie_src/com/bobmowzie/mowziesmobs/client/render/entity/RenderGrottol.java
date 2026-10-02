package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelGrottol;
import com.bobmowzie.mowziesmobs.server.entity.grottol.EntityGrottol;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class RenderGrottol extends MobRenderer<EntityGrottol, ModelGrottol<EntityGrottol>> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("mowziesmobs", "textures/entity/grottol.png");
   private static final ResourceLocation TEXTURE_DEEPSLATE = new ResourceLocation("mowziesmobs", "textures/entity/grottol_deepslate.png");

   public RenderGrottol(Context mgr) {
      super(mgr, new ModelGrottol(), 0.6F);
   }

   protected float getFlipDegrees(EntityGrottol entity) {
      return 0.0F;
   }

   public ResourceLocation getTextureLocation(EntityGrottol entity) {
      return entity.getDeepslate() ? TEXTURE_DEEPSLATE : TEXTURE;
   }
}
