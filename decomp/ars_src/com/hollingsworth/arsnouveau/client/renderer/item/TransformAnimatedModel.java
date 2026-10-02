package com.hollingsworth.arsnouveau.client.renderer.item;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.model.AnimatedGeoModel;

public abstract class TransformAnimatedModel<T extends IAnimatable> extends AnimatedGeoModel<T> {
   public ResourceLocation getModelResource(T object) {
      return this.getModelResource(object, null);
   }

   public abstract ResourceLocation getModelResource(T var1, @Nullable TransformType var2);
}
