package com.github.alexthe666.alexsmobs.misc;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;

public class AMAdvancementTriggerRegistry {
   public static AMAdvancementTrigger MOSQUITO_SICK = new AMAdvancementTrigger(new ResourceLocation("alexsmobs:mosquito_sick"));
   public static AMAdvancementTrigger EMU_DODGE = new AMAdvancementTrigger(new ResourceLocation("alexsmobs:emu_dodge"));
   public static AMAdvancementTrigger STOMP_LEAFCUTTER_ANTHILL = new AMAdvancementTrigger(new ResourceLocation("alexsmobs:stomp_leafcutter_anthill"));
   public static AMAdvancementTrigger BALD_EAGLE_CHALLENGE = new AMAdvancementTrigger(new ResourceLocation("alexsmobs:bald_eagle_challenge"));
   public static AMAdvancementTrigger VOID_WORM_SUMMON = new AMAdvancementTrigger(new ResourceLocation("alexsmobs:void_worm_summon"));
   public static AMAdvancementTrigger VOID_WORM_SPLIT = new AMAdvancementTrigger(new ResourceLocation("alexsmobs:void_worm_split"));
   public static AMAdvancementTrigger VOID_WORM_SLAY_HEAD = new AMAdvancementTrigger(new ResourceLocation("alexsmobs:void_worm_kill"));
   public static AMAdvancementTrigger SEAGULL_STEAL = new AMAdvancementTrigger(new ResourceLocation("alexsmobs:seagull_steal"));
   public static AMAdvancementTrigger LAVIATHAN_FOUR_PASSENGERS = new AMAdvancementTrigger(new ResourceLocation("alexsmobs:laviathan_four_passengers"));
   public static AMAdvancementTrigger TRANSMUTE_1000_ITEMS = new AMAdvancementTrigger(new ResourceLocation("alexsmobs:transmute_1000_items"));
   public static AMAdvancementTrigger UNDERMINE_UNDERMINER = new AMAdvancementTrigger(new ResourceLocation("alexsmobs:undermine_underminer"));
   public static AMAdvancementTrigger ELEPHANT_SWAG = new AMAdvancementTrigger(new ResourceLocation("alexsmobs:elephant_swag"));
   public static AMAdvancementTrigger SKUNK_SPRAY = new AMAdvancementTrigger(new ResourceLocation("alexsmobs:skunk_spray"));

   public static void init() {
      CriteriaTriggers.m_10595_(MOSQUITO_SICK);
      CriteriaTriggers.m_10595_(EMU_DODGE);
      CriteriaTriggers.m_10595_(STOMP_LEAFCUTTER_ANTHILL);
      CriteriaTriggers.m_10595_(BALD_EAGLE_CHALLENGE);
      CriteriaTriggers.m_10595_(VOID_WORM_SUMMON);
      CriteriaTriggers.m_10595_(VOID_WORM_SPLIT);
      CriteriaTriggers.m_10595_(VOID_WORM_SLAY_HEAD);
      CriteriaTriggers.m_10595_(SEAGULL_STEAL);
      CriteriaTriggers.m_10595_(LAVIATHAN_FOUR_PASSENGERS);
      CriteriaTriggers.m_10595_(TRANSMUTE_1000_ITEMS);
      CriteriaTriggers.m_10595_(UNDERMINE_UNDERMINER);
      CriteriaTriggers.m_10595_(ELEPHANT_SWAG);
      CriteriaTriggers.m_10595_(SKUNK_SPRAY);
   }
}
