package com.bobmowzie.mowziesmobs.client.sound;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

public class BossMusicPlayer {
   public static BossMusicSound bossMusic;

   public static void playBossMusic(MowzieEntity entity) {
      if ((Boolean)ConfigHandler.CLIENT.playBossMusic.get()) {
         SoundEvent soundEvent = entity.getBossMusic();
         if (soundEvent != null && entity.m_6084_()) {
            Player player = Minecraft.m_91087_().f_91074_;
            if (bossMusic != null) {
               float f2 = Minecraft.m_91087_().f_91066_.m_92147_(SoundSource.MUSIC);
               if (f2 <= 0.0F) {
                  bossMusic = null;
               } else if (bossMusic.getBoss() == entity && !entity.canPlayerHearMusic(player)) {
                  bossMusic.setBoss(null);
               } else if (bossMusic.getBoss() == null && bossMusic.getSoundEvent() == soundEvent) {
                  bossMusic.setBoss(entity);
               }
            } else if (entity.canPlayerHearMusic(player)) {
               bossMusic = new BossMusicSound(entity.getBossMusic(), entity);
            }

            if (bossMusic != null && !Minecraft.m_91087_().m_91106_().m_120403_(bossMusic)) {
               Minecraft.m_91087_().m_91106_().m_120367_(bossMusic);
            }
         }
      }
   }

   public static void stopBossMusic(MowzieEntity entity) {
      if ((Boolean)ConfigHandler.CLIENT.playBossMusic.get()) {
         if (bossMusic != null && bossMusic.getBoss() == entity) {
            bossMusic.setBoss(null);
         }
      }
   }
}
