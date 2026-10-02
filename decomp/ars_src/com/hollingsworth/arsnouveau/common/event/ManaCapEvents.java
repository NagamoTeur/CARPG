package com.hollingsworth.arsnouveau.common.event;

import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.api.util.ManaUtil;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketUpdateMana;
import com.hollingsworth.arsnouveau.setup.config.ServerConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.network.PacketDistributor;

@EventBusSubscriber(
   modid = "ars_nouveau"
)
public class ManaCapEvents {
   public static double MEAN_TPS = 20.0;

   @SubscribeEvent
   public static void playerOnTick(PlayerTickEvent e) {
      if (!e.player.m_20193_().f_46443_ && e.player.m_20193_().m_46467_() % (long)((Integer)ServerConfig.REGEN_INTERVAL.get()).intValue() == 0L) {
         IManaCap mana = (IManaCap)CapabilityRegistry.getMana(e.player).orElse(null);
         if (mana != null) {
            boolean shouldIgnoreMax = e.player.m_9236_().m_46467_() % 60L == 0L;
            if (mana.getCurrentMana() != (double)mana.getMaxMana() || shouldIgnoreMax) {
               double regenPerSecond = ManaUtil.getManaRegen(e.player) / (double)Math.max(1, (int)MEAN_TPS / (Integer)ServerConfig.REGEN_INTERVAL.get());
               mana.addMana(regenPerSecond);
               Networking.INSTANCE
                  .send(
                     PacketDistributor.PLAYER.with(() -> (ServerPlayer)e.player),
                     new PacketUpdateMana(mana.getCurrentMana(), mana.getMaxMana(), mana.getGlyphBonus(), mana.getBookTier())
                  );
            }

            ManaUtil.Mana maxmana = ManaUtil.calcMaxMana(e.player);
            int max = maxmana.getRealMax();
            if (mana.getMaxMana() != max || shouldIgnoreMax) {
               mana.setMaxMana(max);
               Networking.INSTANCE
                  .send(
                     PacketDistributor.PLAYER.with(() -> (ServerPlayer)e.player),
                     new PacketUpdateMana(mana.getCurrentMana(), mana.getMaxMana(), mana.getGlyphBonus(), mana.getBookTier(), maxmana.Reserve())
                  );
            }
         }
      }
   }

   @SubscribeEvent
   public static void playerRespawn(PlayerRespawnEvent e) {
      syncPlayerEvent(e.getEntity());
   }

   @SubscribeEvent
   public static void playerClone(Clone e) {
      if (!e.getOriginal().f_19853_.f_46443_) {
         CapabilityRegistry.getMana(e.getEntity())
            .ifPresent(
               newMana -> CapabilityRegistry.getMana(e.getOriginal())
                     .ifPresent(
                        origMana -> {
                           newMana.setMaxMana(origMana.getMaxMana());
                           newMana.setGlyphBonus(origMana.getGlyphBonus());
                           newMana.setBookTier(origMana.getBookTier());
                           Networking.INSTANCE
                              .send(
                                 PacketDistributor.PLAYER.with(() -> (ServerPlayer)e.getEntity()),
                                 new PacketUpdateMana(newMana.getCurrentMana(), newMana.getMaxMana(), newMana.getGlyphBonus(), newMana.getBookTier())
                              );
                        }
                     )
            );
      }
   }

   @SubscribeEvent
   public static void playerLoggedIn(StartTracking e) {
      syncPlayerEvent(e.getEntity());
   }

   @SubscribeEvent
   public static void playerChangeDimension(PlayerChangedDimensionEvent e) {
      syncPlayerEvent(e.getEntity());
   }

   public static void syncPlayerEvent(Player playerEntity) {
      if (playerEntity instanceof ServerPlayer) {
         CapabilityRegistry.getMana(playerEntity)
            .ifPresent(
               mana -> {
                  ManaUtil.Mana manaCalc = ManaUtil.calcMaxMana(playerEntity);
                  mana.setMaxMana(manaCalc.getRealMax());
                  mana.setGlyphBonus(mana.getGlyphBonus());
                  mana.setBookTier(mana.getBookTier());
                  Networking.INSTANCE
                     .send(
                        PacketDistributor.PLAYER.with(() -> (ServerPlayer)playerEntity),
                        new PacketUpdateMana(mana.getCurrentMana(), mana.getMaxMana(), mana.getGlyphBonus(), mana.getBookTier(), manaCalc.Reserve())
                     );
               }
            );
      }
   }

   @SubscribeEvent
   public static void onTick(PlayerTickEvent e) {
      if (!e.player.f_19853_.f_46443_) {
         if (e.player.f_19853_.m_46467_() % 600L == 0L && e.player.m_20194_() != null) {
            double meanTickTime = (double)mean(e.player.m_20194_().f_129748_) * 1.0E-6;
            double meanTPS = Math.min(1000.0 / meanTickTime, 20.0);
            MEAN_TPS = Math.max(1.0, meanTPS);
         }
      }
   }

   private static long mean(long[] values) {
      long sum = 0L;

      for (long v : values) {
         sum += v;
      }

      return sum / (long)values.length;
   }
}
