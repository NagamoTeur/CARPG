package com.rolfmao.upgradednetherite.utils;

import com.rolfmao.upgradednetherite.config.UpgradedNetheriteConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;

public class EntityDataUtil {
   public static void setAbilityEnderPos(Entity entity, Boolean bool) {
      if (bool) {
         if (getAbilityEnderPos(entity) == null || !new BlockPos(getAbilityEnderPos(entity)).equals(entity.m_20183_())) {
            entity.getPersistentData()
               .m_128385_(
                  "upgraded_netherite_ender_pos", new int[]{entity.m_20183_().m_123341_(), entity.m_20183_().m_123342_(), entity.m_20183_().m_123343_()}
               );
         }
      } else {
         entity.getPersistentData().m_128473_("upgraded_netherite_ender_pos");
      }
   }

   public static BlockPos getAbilityEnderPos(Entity entity) {
      return entity.getPersistentData().m_128441_("upgraded_netherite_ender_pos")
         ? new BlockPos(
            entity.getPersistentData().m_128465_("upgraded_netherite_ender_pos")[0],
            entity.getPersistentData().m_128465_("upgraded_netherite_ender_pos")[1],
            entity.getPersistentData().m_128465_("upgraded_netherite_ender_pos")[2]
         )
         : null;
   }

   public static void setAbilityClimbwall(Entity entity, Boolean bool) {
      entity.getPersistentData().m_128379_("upgraded_netherite_climbstick", bool);
   }

   public static boolean getAbilityClimbwall(Entity entity) {
      return entity.getPersistentData().m_128441_("upgraded_netherite_climbstick") && entity.getPersistentData().m_128471_("upgraded_netherite_climbstick");
   }

   public static void setAbilityStepHeight(Entity entity, Boolean bool) {
      entity.getPersistentData().m_128379_("upgraded_netherite_stepheight", bool);
   }

   public static boolean getAbilityStepHeight(Entity entity) {
      return entity.getPersistentData().m_128441_("upgraded_netherite_stepheight") && entity.getPersistentData().m_128471_("upgraded_netherite_stepheight");
   }

   public static void setAbilityMultiJump(Entity entity, Boolean bool) {
      if (bool) {
         entity.getPersistentData().m_128405_("upgraded_netherite_multi_jump", UpgradedNetheriteConfig.MultiJump);
      } else {
         entity.getPersistentData().m_128405_("upgraded_netherite_multi_jump", 0);
      }
   }

   public static void decreaseAbilityMultiJump(Entity entity) {
      entity.getPersistentData().m_128405_("upgraded_netherite_multi_jump", entity.getPersistentData().m_128451_("upgraded_netherite_multi_jump") - 1);
   }

   public static boolean getAbilityMultiJump(Entity entity) {
      return entity.getPersistentData().m_128441_("upgraded_netherite_multi_jump") && entity.getPersistentData().m_128451_("upgraded_netherite_multi_jump") > 0;
   }

   public static void setCorrupterite(Entity entity, Integer integer) {
      if (integer > 0) {
         entity.getPersistentData().m_128405_("upgraded_netherite_corrupterite", integer);
      } else {
         entity.getPersistentData().m_128473_("upgraded_netherite_corrupterite");
      }
   }

   public static int getCorrupterite(Entity entity) {
      return entity.getPersistentData().m_128441_("upgraded_netherite_corrupterite")
         ? entity.getPersistentData().m_128451_("upgraded_netherite_corrupterite")
         : 0;
   }

   public static void setMalusCorruption(Entity entity, Integer integer) {
      if (integer > 0) {
         entity.getPersistentData().m_128405_("upgraded_netherite_corruption", integer);
      } else {
         entity.getPersistentData().m_128473_("upgraded_netherite_corruption");
      }
   }

   public static int getMalusCorruption(Entity entity) {
      return entity.getPersistentData().m_128441_("upgraded_netherite_corruption") ? entity.getPersistentData().m_128451_("upgraded_netherite_corruption") : 0;
   }

   public static void tickCooldown(Player player) {
      if (player.getPersistentData().m_128441_("upgraded_netherite_fire_durability_loss")
         && player.getPersistentData().m_128451_("upgraded_netherite_fire_durability_loss") > 0) {
         player.getPersistentData()
            .m_128350_("upgraded_netherite_fire_durability_loss", (float)(player.getPersistentData().m_128451_("upgraded_netherite_fire_durability_loss") - 1));
      }

      if (player.getPersistentData().m_128441_("upgraded_netherite_fire_durability_loss")
         && player.getPersistentData().m_128451_("upgraded_netherite_fire_durability_loss") <= 0) {
         player.getPersistentData().m_128473_("upgraded_netherite_fire_durability_loss");
      }

      if (player.getPersistentData().m_128441_("upgraded_netherite_water_durability_loss")
         && player.getPersistentData().m_128451_("upgraded_netherite_water_durability_loss") > 0) {
         player.getPersistentData()
            .m_128350_(
               "upgraded_netherite_water_durability_loss", (float)(player.getPersistentData().m_128451_("upgraded_netherite_water_durability_loss") - 1)
            );
      }

      if (player.getPersistentData().m_128441_("upgraded_netherite_water_durability_loss")
         && player.getPersistentData().m_128451_("upgraded_netherite_water_durability_loss") <= 0) {
         player.getPersistentData().m_128473_("upgraded_netherite_water_durability_loss");
      }

      if (player.getPersistentData().m_128441_("upgraded_netherite_wither_durability_loss")
         && player.getPersistentData().m_128451_("upgraded_netherite_wither_durability_loss") > 0) {
         player.getPersistentData()
            .m_128350_(
               "upgraded_netherite_wither_durability_loss", (float)(player.getPersistentData().m_128451_("upgraded_netherite_wither_durability_loss") - 1)
            );
      }

      if (player.getPersistentData().m_128441_("upgraded_netherite_wither_durability_loss")
         && player.getPersistentData().m_128451_("upgraded_netherite_wither_durability_loss") <= 0) {
         player.getPersistentData().m_128473_("upgraded_netherite_wither_durability_loss");
      }

      if (player.getPersistentData().m_128441_("upgraded_netherite_poison_durability_loss")
         && player.getPersistentData().m_128451_("upgraded_netherite_poison_durability_loss") > 0) {
         player.getPersistentData()
            .m_128350_(
               "upgraded_netherite_poison_durability_loss", (float)(player.getPersistentData().m_128451_("upgraded_netherite_poison_durability_loss") - 1)
            );
      }

      if (player.getPersistentData().m_128441_("upgraded_netherite_poison_durability_loss")
         && player.getPersistentData().m_128451_("upgraded_netherite_poison_durability_loss") <= 0) {
         player.getPersistentData().m_128473_("upgraded_netherite_poison_durability_loss");
      }

      if (player.getPersistentData().m_128441_("upgraded_netherite_feather_durability_loss")
         && player.getPersistentData().m_128451_("upgraded_netherite_feather_durability_loss") > 0) {
         player.getPersistentData()
            .m_128350_(
               "upgraded_netherite_feather_durability_loss", (float)(player.getPersistentData().m_128451_("upgraded_netherite_feather_durability_loss") - 1)
            );
      }

      if (player.getPersistentData().m_128441_("upgraded_netherite_feather_durability_loss")
         && player.getPersistentData().m_128451_("upgraded_netherite_feather_durability_loss") <= 0) {
         player.getPersistentData().m_128473_("upgraded_netherite_feather_durability_loss");
      }

      if (player.getPersistentData().m_128441_("upgraded_netherite_corrupt_durability_gain")
         && player.getPersistentData().m_128451_("upgraded_netherite_corrupt_durability_gain") > 0) {
         player.getPersistentData()
            .m_128350_(
               "upgraded_netherite_corrupt_durability_gain", (float)(player.getPersistentData().m_128451_("upgraded_netherite_corrupt_durability_gain") - 1)
            );
      }

      if (player.getPersistentData().m_128441_("upgraded_netherite_corrupt_durability_gain")
         && player.getPersistentData().m_128451_("upgraded_netherite_corrupt_durability_gain") <= 0) {
         player.getPersistentData().m_128473_("upgraded_netherite_corrupt_durability_gain");
      }

      if (player.getPersistentData().m_128441_("upgraded_netherite_ender_teleport_cd")
         && player.getPersistentData().m_128451_("upgraded_netherite_ender_teleport_cd") > 0) {
         player.getPersistentData()
            .m_128350_("upgraded_netherite_ender_teleport_cd", (float)(player.getPersistentData().m_128451_("upgraded_netherite_ender_teleport_cd") - 1));
      }

      if (player.getPersistentData().m_128441_("upgraded_netherite_ender_teleport_cd")
         && player.getPersistentData().m_128451_("upgraded_netherite_ender_teleport_cd") <= 0) {
         player.getPersistentData().m_128473_("upgraded_netherite_ender_teleport_cd");
      }

      if (player.getPersistentData().m_128441_("upgraded_netherite_echo_heal_cd")
         && player.getPersistentData().m_128451_("upgraded_netherite_echo_heal_cd") > 0) {
         player.getPersistentData()
            .m_128350_("upgraded_netherite_echo_heal_cd", (float)(player.getPersistentData().m_128451_("upgraded_netherite_echo_heal_cd") - 1));
      }

      if (player.getPersistentData().m_128441_("upgraded_netherite_echo_heal_cd")
         && player.getPersistentData().m_128451_("upgraded_netherite_echo_heal_cd") <= 0) {
         player.getPersistentData().m_128473_("upgraded_netherite_echo_heal_cd");
      }
   }

   public static void tickCooldownHorse(Horse horse) {
      if (horse.getPersistentData().m_128441_("upgraded_netherite_echo_heal_cd") && horse.getPersistentData().m_128451_("upgraded_netherite_echo_heal_cd") > 0) {
         horse.getPersistentData()
            .m_128350_("upgraded_netherite_echo_heal_cd", (float)(horse.getPersistentData().m_128451_("upgraded_netherite_echo_heal_cd") - 1));
      }

      if (horse.getPersistentData().m_128441_("upgraded_netherite_echo_heal_cd") && horse.getPersistentData().m_128451_("upgraded_netherite_echo_heal_cd") <= 0
         )
       {
         horse.getPersistentData().m_128473_("upgraded_netherite_echo_heal_cd");
      }
   }

   public static boolean hasFireDurabilityCooldown(Player player) {
      return player.getPersistentData().m_128441_("upgraded_netherite_fire_durability_loss")
         && player.getPersistentData().m_128451_("upgraded_netherite_fire_durability_loss") > 0;
   }

   public static boolean hasWaterDurabilityCooldown(Player player) {
      return player.getPersistentData().m_128441_("upgraded_netherite_water_durability_loss")
         && player.getPersistentData().m_128451_("upgraded_netherite_water_durability_loss") > 0;
   }

   public static boolean hasWitherDurabilityCooldown(Player player) {
      return player.getPersistentData().m_128441_("upgraded_netherite_wither_durability_loss")
         && player.getPersistentData().m_128451_("upgraded_netherite_wither_durability_loss") > 0;
   }

   public static boolean hasPoisonDurabilityCooldown(Player player) {
      return player.getPersistentData().m_128441_("upgraded_netherite_poison_durability_loss")
         && player.getPersistentData().m_128451_("upgraded_netherite_poison_durability_loss") > 0;
   }

   public static boolean hasFeatherDurabilityCooldown(Player player) {
      return player.getPersistentData().m_128441_("upgraded_netherite_feather_durability_loss")
         && player.getPersistentData().m_128451_("upgraded_netherite_feather_durability_loss") > 0;
   }

   public static boolean hasCorruptDurabilityCooldown(Player player) {
      return player.getPersistentData().m_128441_("upgraded_netherite_corrupt_durability_gain")
         && player.getPersistentData().m_128451_("upgraded_netherite_corrupt_durability_gain") > 0;
   }

   public static boolean hasEnderTeleportCooldown(Player player) {
      return player.getPersistentData().m_128441_("upgraded_netherite_ender_teleport_cd")
         && player.getPersistentData().m_128451_("upgraded_netherite_ender_teleport_cd") > 0;
   }

   public static boolean hasEchoHealCooldown(Player player) {
      return player.getPersistentData().m_128441_("upgraded_netherite_echo_heal_cd")
         && player.getPersistentData().m_128451_("upgraded_netherite_echo_heal_cd") > 0;
   }

   public static boolean hasEchoHealCooldownHorse(Horse horse) {
      return horse.getPersistentData().m_128441_("upgraded_netherite_echo_heal_cd")
         && horse.getPersistentData().m_128451_("upgraded_netherite_echo_heal_cd") > 0;
   }
}
