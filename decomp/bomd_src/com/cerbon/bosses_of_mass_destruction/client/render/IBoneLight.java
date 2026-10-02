package com.cerbon.bosses_of_mass_destruction.client.render;

import com.mojang.math.Vector4f;
import software.bernie.geckolib3.geo.render.built.GeoBone;

@FunctionalInterface
public interface IBoneLight {
   int fullbright = 15728880;

   int getLightForBone(GeoBone var1, int var2);

   default Vector4f getColorForBone(GeoBone bone, Vector4f rgbaColor) {
      return rgbaColor;
   }
}
