package com.aqutheseal.celestisynth.client.renderers.misc;

import com.aqutheseal.celestisynth.Celestisynth;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class NullRenderer<T extends Entity> extends EntityRenderer<T> {
   public NullRenderer(Context p_174008_) {
      super(p_174008_);
   }

   public ResourceLocation m_5478_(T p_114482_) {
      return Celestisynth.prefix("textures/entity/solaris_air");
   }
}
