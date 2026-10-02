package com.github.L_Ender.cataclysm.util;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;

public class EffectUtil {
   private static final Holder<MobEffect> type = null;

   public EffectUtil(Holder<MobEffect> type) {
   }

   public static boolean is(TagKey<MobEffect> p_270890_) {
      return type.m_203656_(p_270890_);
   }

   public static boolean is(ResourceKey<MobEffect> p_276108_) {
      return type.m_203565_(p_276108_);
   }

   public static MobEffect type() {
      return (MobEffect)type.m_203334_();
   }

   public static Holder<MobEffect> typeHolder() {
      return type;
   }
}
