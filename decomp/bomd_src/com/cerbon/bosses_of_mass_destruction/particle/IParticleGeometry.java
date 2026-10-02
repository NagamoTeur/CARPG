package com.cerbon.bosses_of_mass_destruction.particle;

import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;

@FunctionalInterface
public interface IParticleGeometry {
   Vector3f[] getGeometry(Camera var1, float var2, double var3, double var5, double var7, double var9, double var11, double var13, float var15, float var16);
}
