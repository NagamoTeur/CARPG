package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.hollingsworth.arsnouveau.api.client.IVariantTextureProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public class TextureVariantRenderer<T extends LivingEntity & IVariantTextureProvider & IAnimatable> extends GenericRenderer<T> {
   public TextureVariantRenderer(Context renderManager, AnimatedGeoModel<T> modelProvider) {
      super(renderManager, modelProvider);
   }

   @Override
   public ResourceLocation getTextureLocation(T entity) {
      return entity.getTexture((T)entity);
   }
}
