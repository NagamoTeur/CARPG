package com.bobmowzie.mowziesmobs.server.advancement;

import net.minecraft.advancements.CriteriaTriggers;

public class AdvancementHandler {
   public static final StealIceCrystalTrigger STEAL_ICE_CRYSTAL_TRIGGER = (StealIceCrystalTrigger)CriteriaTriggers.m_10595_(new StealIceCrystalTrigger());
   public static final GrottolKillFortuneTrigger GROTTOL_KILL_FORTUNE_TRIGGER = (GrottolKillFortuneTrigger)CriteriaTriggers.m_10595_(
      new GrottolKillFortuneTrigger()
   );
   public static final GrottolKillSilkTouchTrigger GROTTOL_KILL_SILK_TOUCH_TRIGGER = (GrottolKillSilkTouchTrigger)CriteriaTriggers.m_10595_(
      new GrottolKillSilkTouchTrigger()
   );
   public static final SneakGroveTrigger SNEAK_VILLAGE_TRIGGER = (SneakGroveTrigger)CriteriaTriggers.m_10595_(new SneakGroveTrigger());

   public static void preInit() {
   }
}
