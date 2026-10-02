package com.hollingsworth.arsnouveau.common.advancement;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.AABB;

public class ANCriteriaTriggers {
   public static final PlayerTrigger POOF_MOB = register(new PlayerTrigger(new ResourceLocation("ars_nouveau", "poof_mob")));
   public static final PlayerTrigger FAMILIAR = register(new PlayerTrigger(new ResourceLocation("ars_nouveau", "familiar")));
   public static final PlayerTrigger CHIMERA_EXPLOSION = register(new PlayerTrigger(new ResourceLocation("ars_nouveau", "chimera_explosion")));
   public static final PlayerTrigger CREATE_PORTAL = register(new PlayerTrigger(new ResourceLocation("ars_nouveau", "portals")));
   public static final PlayerTrigger PRISMATIC = register(new PlayerTrigger(new ResourceLocation("ars_nouveau", "prismatic")));
   public static final PlayerTrigger SHRUNK_STARBY = register(new PlayerTrigger(new ResourceLocation("ars_nouveau", "shrunk_starby")));
   public static final PlayerTrigger CAUGHT_LIGHTNING = register(new PlayerTrigger(new ResourceLocation("ars_nouveau", "catch_lightning")));
   public static final PlayerTrigger TIME_IN_BOTTLE = register(new PlayerTrigger(new ResourceLocation("ars_nouveau", "time_in_bottle")));

   public static void rewardNearbyPlayers(PlayerTrigger criteria, ServerLevel level, BlockPos pos, int radius) {
      AABB aabb = new AABB(pos).m_82400_((double)radius);

      for (ServerPlayer player : level.m_6907_()) {
         if (aabb.m_82393_(player.m_20185_(), player.m_20186_(), player.m_20189_())) {
            criteria.m_222618_(player);
         }
      }
   }

   public static <T extends CriterionTrigger<?>> T register(T trigger) {
      return (T)CriteriaTriggers.m_10595_(trigger);
   }

   public static void init() {
   }
}
