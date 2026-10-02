package com.aizistral.enigmaticlegacy.helpers;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerXpEvent.LevelChange;
import net.minecraftforge.event.entity.player.PlayerXpEvent.XpChange;

public class ExperienceHelper {
   public static int getPlayerXP(Player player) {
      return (int)((float)getExperienceForLevel(player.f_36078_) + player.f_36080_ * (float)player.m_36323_());
   }

   public static int getPlayerXPLevel(Player player) {
      return player.f_36078_;
   }

   public static void drainPlayerXP(Player player, int amount) {
      addPlayerXP(player, -amount);
   }

   public static void addPlayerXP(Player player, int amount) {
      XpChange eventXP = new XpChange(player, amount);
      amount = eventXP.getAmount();
      int oldLevel = getPlayerXPLevel(player);
      int newLevel = getLevelForExperience(getPlayerXP(player) + amount);
      int experience = getPlayerXP(player) + amount;
      if (oldLevel != newLevel) {
         LevelChange eventLvl = new LevelChange(player, newLevel - oldLevel);
         int remainder = experience - getExperienceForLevel(newLevel);
         newLevel = oldLevel + eventLvl.getLevels();
         amount = getExperienceForLevel(newLevel) - getExperienceForLevel(oldLevel) + remainder;
      }

      player.f_36079_ = experience;
      player.f_36078_ = getLevelForExperience(experience);
      int expForLevel = getExperienceForLevel(player.f_36078_);
      player.f_36080_ = (float)(experience - expForLevel) / (float)player.m_36323_();
   }

   public static int getExperienceForLevel(int level) {
      if (level <= 0) {
         return 0;
      } else if (level > 0 && level < 17) {
         return level * level + 6 * level;
      } else {
         return level > 16 && level < 32
            ? (int)(2.5 * (double)level * (double)level - 40.5 * (double)level + 360.0)
            : (int)(4.5 * (double)level * (double)level - 162.5 * (double)level + 2220.0);
      }
   }

   public static int getLevelForExperience(int experience) {
      if (experience <= 0) {
         return 0;
      } else {
         int i = 0;

         while (getExperienceForLevel(i) <= experience) {
            i++;
         }

         return i - 1;
      }
   }
}
