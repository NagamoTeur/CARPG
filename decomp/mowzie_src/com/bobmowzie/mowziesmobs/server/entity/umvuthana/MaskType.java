package com.bobmowzie.mowziesmobs.server.entity.umvuthana;

import com.google.common.base.Defaults;
import java.util.EnumMap;
import java.util.Locale;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

public enum MaskType {
   FURY(MobEffects.f_19600_, 0.9F, 2.0F, true),
   FEAR(MobEffects.f_19596_),
   RAGE(MobEffects.f_19598_),
   BLISS(MobEffects.f_19603_),
   MISERY(MobEffects.f_19606_),
   FAITH(MobEffects.f_19616_, 0.9F, 2.0F, false);

   public static final int COUNT = values().length;
   public final MobEffect potion;
   public final float entityWidth;
   public final float entityHeight;
   public final boolean canBlock;
   public final String name;

   private MaskType(MobEffect potion) {
      this(potion, 0.8F, 1.8F, false);
   }

   private MaskType(MobEffect potion, float entityWidth, float entityHeight, boolean canBlock) {
      this.potion = potion;
      this.entityWidth = entityWidth;
      this.entityHeight = entityHeight;
      this.canBlock = canBlock;
      this.name = this.name().toLowerCase(Locale.ENGLISH);
   }

   public static MaskType from(int id) {
      return id >= 0 && id < COUNT ? values()[id] : MISERY;
   }

   public static <T> EnumMap<MaskType, T> newEnumMap(Class<T> type, T... defaultValues) {
      EnumMap map = new EnumMap<>(MaskType.class);
      MaskType[] masks = values();

      for (int i = 0; i < masks.length; i++) {
         map.put(masks[i], i >= 0 && i < defaultValues.length ? defaultValues[i] : Defaults.defaultValue(type));
      }

      return map;
   }
}
