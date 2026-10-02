package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelFoliaathBaby;
import com.bobmowzie.mowziesmobs.server.entity.foliaath.EntityBabyFoliaath;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderFoliaathBaby extends MobRenderer<EntityBabyFoliaath, ModelFoliaathBaby<EntityBabyFoliaath>> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("mowziesmobs", "textures/entity/foliaath_baby.png");

   public RenderFoliaathBaby(Context mgr) {
      super(mgr, new ModelFoliaathBaby(), 0.0F);
   }

   protected float getFlipDegrees(EntityBabyFoliaath entity) {
      return 0.0F;
   }

   public ResourceLocation getTextureLocation(EntityBabyFoliaath entity) {
      return TEXTURE;
   }
}
