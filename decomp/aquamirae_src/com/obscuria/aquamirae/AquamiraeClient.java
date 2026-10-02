package com.obscuria.aquamirae;

import com.obscuria.aquamirae.client.AquamiraeBossBars;
import com.obscuria.aquamirae.client.screen.ConfigScreen;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.event.ObscureAPIRegisterBossBarsEvent;
import com.obscuria.obscureapi.util.ItemUtils;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.jetbrains.annotations.NotNull;

public final class AquamiraeClient {
   private static int biomeMusic = 0;
   private static int corneliaMusic = 0;

   public static Supplier<ConfigScreenFactory> getConfig() {
      return () -> new ConfigScreenFactory((minecraft, screen) -> new ConfigScreen(screen));
   }

   public static void scrollEffect(int type) {
      if (Minecraft.m_91087_().f_91074_ != null) {
         Minecraft.m_91087_().f_91074_.m_216990_((SoundEvent)AquamiraeSounds.EFFECT_MYSTERY.get());
      }
   }

   public static void spawnParticles(Player player) {
      if ((Boolean)AquamiraeConfig.Client.particles.get()) {
         player.m_9236_()
            .m_6493_(
               ParticleTypes.f_123790_,
               false,
               player.m_20185_() - 6.0 + 12.0 * player.m_217043_().m_188500_(),
               player.m_20186_() + 4.0 - 3.0 * player.m_217043_().m_188500_(),
               player.m_20189_() - 6.0 + 12.0 * player.m_217043_().m_188500_(),
               0.0,
               100.0,
               0.0
            );
      }
   }

   public static void playAmbientSounds(Player player, boolean config) {
      if (config) {
         if ((Boolean)AquamiraeConfig.Client.ambientSounds.get() && Math.random() <= 0.01 && Math.random() <= 0.1) {
            player.m_9236_()
               .m_7785_(
                  (double)(player.m_146903_() - 10) + 20.0 * Math.random(),
                  (double)player.m_146904_(),
                  (double)(player.m_146907_() - 10) + 20.0 * Math.random(),
                  (SoundEvent)AquamiraeSounds.AMBIENT_SHIP_HORN.get(),
                  SoundSource.AMBIENT,
                  1.0F,
                  1.0F,
                  false
               );
         }
      } else {
         player.m_9236_()
            .m_7785_(
               (double)(player.m_146903_() - 10) + 20.0 * Math.random(),
               (double)player.m_146904_(),
               (double)(player.m_146907_() - 10) + 20.0 * Math.random(),
               (SoundEvent)AquamiraeSounds.AMBIENT_SHIP_HORN.get(),
               SoundSource.AMBIENT,
               1.0F,
               1.0F,
               false
            );
      }
   }

   public static void playCorneliaMusic(Player player) {
      if ((Boolean)AquamiraeConfig.Client.bossMusic.get() && player.m_6084_() && corneliaMusic <= 0) {
         corneliaMusic = 3000;
         Minecraft.m_91087_().m_91397_().m_120186_();
         Minecraft.m_91087_().m_91397_().m_120184_(new Music((SoundEvent)AquamiraeSounds.MUSIC_FORSAKEN_DROWNAGE.get(), 10, 100, true));
      }
   }

   private static void playBiomeMusic(Player player) {
      if ((Boolean)AquamiraeConfig.Client.biomeMusic.get() && player.m_6084_() && biomeMusic <= 0 && corneliaMusic <= 0) {
         biomeMusic = 20 * new Random().nextInt(180, 300);
         Minecraft.m_91087_().m_91397_().m_120186_();
         Minecraft.m_91087_().m_91397_().m_120184_(new Music((SoundEvent)AquamiraeSounds.MUSIC_ICE_MAZE_THEME.get(), 10, 100, true));
      }
   }

   @EventBusSubscriber({Dist.CLIENT})
   public static class ForgeEvents {
      @SubscribeEvent
      public static void tick(@NotNull ClientTickEvent event) {
         if (event.phase == Phase.END && Minecraft.m_91087_().f_91074_ != null && !Minecraft.m_91087_().m_91104_()) {
            if (AquamiraeClient.biomeMusic > 0) {
               AquamiraeClient.biomeMusic--;
            }

            if (AquamiraeClient.corneliaMusic > 0) {
               AquamiraeClient.corneliaMusic--;
            }

            Player player = Minecraft.m_91087_().f_91074_;
            if (AquamiraeUtils.isInIceMaze(player)) {
               AquamiraeClient.playAmbientSounds(player, true);
               AquamiraeClient.playBiomeMusic(player);
               AquamiraeClient.spawnParticles(player);
            }
         }
      }

      @SubscribeEvent(
         priority = EventPriority.HIGHEST
      )
      public static void fixOldPerk(@NotNull ItemTooltipEvent event) {
         if (ItemUtils.hasPerk(event.getItemStack(), "rune_of_the_storm")) {
            ItemUtils.removePerk(event.getItemStack(), "rune_of_the_storm");
            ItemUtils.addPerk(event.getItemStack(), new ResourceLocation("aquamirae", "rune_of_the_storm"), 1);
         }
      }
   }

   @EventBusSubscriber(
      value = {Dist.CLIENT},
      bus = Bus.MOD
   )
   public static class ModEvents {
      @SubscribeEvent
      public static void registerBossBars(ObscureAPIRegisterBossBarsEvent event) {
         if ((Boolean)AquamiraeConfig.Client.stylizedBossbar.get()) {
            event.register("entity.aquamirae.captain_cornelia", false, true, true, AquamiraeBossBars::cornelia);
         }
      }
   }
}
