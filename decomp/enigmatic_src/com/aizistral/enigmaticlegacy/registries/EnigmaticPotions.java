package com.aizistral.enigmaticlegacy.registries;

import com.aizistral.enigmaticlegacy.objects.AdvancedPotion;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.List;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class EnigmaticPotions {
   private static final EnigmaticPotions INSTANCE = new EnigmaticPotions();
   public static final List<AdvancedPotion> ULTIMATE_POTIONS;
   public static final List<AdvancedPotion> COMMON_POTIONS;
   public static final AdvancedPotion ULTIMATE_NIGHT_VISION = new AdvancedPotion("ultimate_night_vision", new MobEffectInstance(MobEffects.f_19611_, 19200));
   public static final AdvancedPotion ULTIMATE_INVISIBILITY = new AdvancedPotion("ultimate_invisibility", new MobEffectInstance(MobEffects.f_19609_, 19200));
   public static final AdvancedPotion ULTIMATE_FIRE_RESISTANCE = new AdvancedPotion(
      "ultimate_fire_resistance", new MobEffectInstance(MobEffects.f_19607_, 19200)
   );
   public static final AdvancedPotion ULTIMATE_LEAPING = new AdvancedPotion("ultimate_leaping", new MobEffectInstance(MobEffects.f_19603_, 9600, 1));
   public static final AdvancedPotion ULTIMATE_SWIFTNESS = new AdvancedPotion("ultimate_swiftness", new MobEffectInstance(MobEffects.f_19596_, 9600, 1));
   public static final AdvancedPotion ULTIMATE_SLOWNESS = new AdvancedPotion("ultimate_slowness", new MobEffectInstance(MobEffects.f_19597_, 1200, 3));
   public static final AdvancedPotion ULTIMATE_TURTLE_MASTER = new AdvancedPotion(
      "ultimate_turtle_master", new MobEffectInstance(MobEffects.f_19597_, 800, 5), new MobEffectInstance(MobEffects.f_19606_, 800, 3)
   );
   public static final AdvancedPotion ULTIMATE_WATER_BREATHING = new AdvancedPotion(
      "ultimate_water_breathing", new MobEffectInstance(MobEffects.f_19608_, 19200)
   );
   public static final AdvancedPotion ULTIMATE_HEALING = new AdvancedPotion("ultimate_healing", new MobEffectInstance(MobEffects.f_19601_, 1, 2));
   public static final AdvancedPotion ULTIMATE_HARMING = new AdvancedPotion("ultimate_harming", new MobEffectInstance(MobEffects.f_19602_, 1, 2));
   public static final AdvancedPotion ULTIMATE_POISON = new AdvancedPotion("ultimate_poison", new MobEffectInstance(MobEffects.f_19614_, 1800, 1));
   public static final AdvancedPotion ULTIMATE_REGENERATION = new AdvancedPotion("ultimate_regeneration", new MobEffectInstance(MobEffects.f_19605_, 1800, 1));
   public static final AdvancedPotion ULTIMATE_STRENGTH = new AdvancedPotion("ultimate_strength", new MobEffectInstance(MobEffects.f_19600_, 9600, 1));
   public static final AdvancedPotion ULTIMATE_WEAKNESS = new AdvancedPotion("ultimate_weakness", new MobEffectInstance(MobEffects.f_19613_, 9600));
   public static final AdvancedPotion ULTMATE_SLOW_FALLING = new AdvancedPotion("ultimate_slow_falling", new MobEffectInstance(MobEffects.f_19591_, 9600));
   public static final AdvancedPotion HASTE = new AdvancedPotion("haste", new MobEffectInstance(MobEffects.f_19598_, 3600));
   public static final AdvancedPotion LONG_HASTE = new AdvancedPotion("long_haste", new MobEffectInstance(MobEffects.f_19598_, 9600));
   public static final AdvancedPotion STRONG_HASTE = new AdvancedPotion("strong_haste", new MobEffectInstance(MobEffects.f_19598_, 1800, 1));
   public static final AdvancedPotion ULTIMATE_HASTE = new AdvancedPotion("ultimate_haste", new MobEffectInstance(MobEffects.f_19598_, 9600, 1));
   public static final AdvancedPotion MOLTEN_HEART = new AdvancedPotion("molten_heart", new MobEffectInstance(EnigmaticEffects.MOLTEN_HEART, 3600));
   public static final AdvancedPotion LONG_MOLTEN_HEART = new AdvancedPotion("long_molten_heart", new MobEffectInstance(EnigmaticEffects.MOLTEN_HEART, 9600));
   public static final AdvancedPotion ULTIMATE_MOLTEN_HEART = new AdvancedPotion(
      "ultimate_molten_heart", new MobEffectInstance(EnigmaticEffects.MOLTEN_HEART, 19200)
   );
   public static final AdvancedPotion EMPTY_POTION = new AdvancedPotion("empty");

   private EnigmaticPotions() {
   }

   static {
      Builder<AdvancedPotion> ultimatePotions = ImmutableList.builder();
      Builder<AdvancedPotion> commonPotions = ImmutableList.builder();
      ultimatePotions.add(ULTIMATE_NIGHT_VISION);
      ultimatePotions.add(ULTIMATE_INVISIBILITY);
      ultimatePotions.add(ULTIMATE_LEAPING);
      ultimatePotions.add(ULTIMATE_FIRE_RESISTANCE);
      ultimatePotions.add(ULTIMATE_SLOWNESS);
      ultimatePotions.add(ULTIMATE_TURTLE_MASTER);
      ultimatePotions.add(ULTIMATE_WATER_BREATHING);
      ultimatePotions.add(ULTIMATE_HEALING);
      ultimatePotions.add(ULTIMATE_HARMING);
      ultimatePotions.add(ULTIMATE_POISON);
      ultimatePotions.add(ULTIMATE_REGENERATION);
      ultimatePotions.add(ULTIMATE_STRENGTH);
      ultimatePotions.add(ULTIMATE_WEAKNESS);
      ultimatePotions.add(ULTMATE_SLOW_FALLING);
      commonPotions.add(HASTE);
      commonPotions.add(LONG_HASTE);
      commonPotions.add(STRONG_HASTE);
      ultimatePotions.add(ULTIMATE_HASTE);
      commonPotions.add(MOLTEN_HEART);
      commonPotions.add(LONG_MOLTEN_HEART);
      ultimatePotions.add(ULTIMATE_MOLTEN_HEART);
      ULTIMATE_POTIONS = ultimatePotions.build();
      COMMON_POTIONS = commonPotions.build();
   }
}
