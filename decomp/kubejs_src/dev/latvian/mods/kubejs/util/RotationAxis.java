package dev.latvian.mods.kubejs.util;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

public enum RotationAxis {
   XN(Vector3f.f_122222_::m_122270_, new Vector3f(-1.0F, 0.0F, 0.0F)),
   XP(Vector3f.f_122223_::m_122270_, new Vector3f(1.0F, 0.0F, 0.0F)),
   YN(Vector3f.f_122224_::m_122270_, new Vector3f(0.0F, -1.0F, 0.0F)),
   YP(Vector3f.f_122225_::m_122270_, new Vector3f(0.0F, 1.0F, 0.0F)),
   ZN(Vector3f.f_122226_::m_122270_, new Vector3f(0.0F, 0.0F, -1.0F)),
   ZP(Vector3f.f_122227_::m_122270_, new Vector3f(0.0F, 0.0F, 1.0F));

   private final RotationAxis.Func func;
   public final Vector3f vec;

   private RotationAxis(RotationAxis.Func func, Vector3f vec) {
      this.func = func;
      this.vec = vec;
   }

   public Quaternion rad(float f) {
      return this.func.rotation(f);
   }

   public Quaternion deg(float f) {
      return this.func.rotation(f * (float) (Math.PI / 180.0));
   }

   private interface Func {
      Quaternion rotation(float var1);
   }
}
