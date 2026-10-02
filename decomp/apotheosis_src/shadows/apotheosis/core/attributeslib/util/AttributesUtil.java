package shadows.apotheosis.core.attributeslib.util;

import net.minecraft.world.damagesource.DamageSource;

public class AttributesUtil {
   public static boolean isPhysicalDamage(DamageSource src) {
      return !src.m_19387_() && !src.m_19384_() && !src.m_19372_();
   }
}
