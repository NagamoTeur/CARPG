package com.hollingsworth.arsnouveau.api.item;

import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ICosmeticItem {
   default String getBone() {
      return "head";
   }

   Vec3 getTranslations();

   Vec3 getScaling();

   default Vec3 getTranslations(LivingEntity entity) {
      return this.getTranslations();
   }

   default Vec3 getScaling(LivingEntity entity) {
      return this.getScaling();
   }

   default boolean canWear(LivingEntity entity) {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   default TransformType getTransformType() {
      return TransformType.GROUND;
   }
}
