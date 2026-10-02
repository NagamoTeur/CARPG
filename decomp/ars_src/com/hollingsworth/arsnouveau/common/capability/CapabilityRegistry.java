package com.hollingsworth.arsnouveau.common.capability;

import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketSyncPlayerCap;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class CapabilityRegistry {
   public static final Capability<IManaCap> MANA_CAPABILITY = CapabilityManager.get(new CapabilityToken<IManaCap>() {
   });
   public static final Capability<IPlayerCap> PLAYER_DATA_CAP = CapabilityManager.get(new CapabilityToken<IPlayerCap>() {
   });
   public static final Direction DEFAULT_FACING = null;

   public static LazyOptional<IManaCap> getMana(LivingEntity entity) {
      return entity == null ? LazyOptional.empty() : entity.getCapability(MANA_CAPABILITY);
   }

   public static LazyOptional<IPlayerCap> getPlayerDataCap(LivingEntity entity) {
      return entity == null ? LazyOptional.empty() : entity.getCapability(PLAYER_DATA_CAP);
   }

   @EventBusSubscriber(
      modid = "ars_nouveau"
   )
   public static class EventHandler {
      @SubscribeEvent
      public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
         if (event.getObject() instanceof Player) {
            ManaCapAttacher.attach(event);
            ANPlayerCapAttacher.attach(event);
         }
      }

      @SubscribeEvent
      public static void registerCapabilities(RegisterCapabilitiesEvent event) {
         event.register(IManaCap.class);
         event.register(IPlayerCap.class);
      }

      @SubscribeEvent
      public static void playerClone(Clone event) {
         Player oldPlayer = event.getOriginal();
         oldPlayer.revive();
         CapabilityRegistry.getMana(oldPlayer).ifPresent(oldMaxMana -> CapabilityRegistry.getMana(event.getEntity()).ifPresent(newMaxMana -> {
               newMaxMana.setMaxMana(oldMaxMana.getMaxMana());
               newMaxMana.setMana(oldMaxMana.getCurrentMana());
               newMaxMana.setBookTier(oldMaxMana.getBookTier());
               newMaxMana.setGlyphBonus(oldMaxMana.getGlyphBonus());
            }));
         CapabilityRegistry.getPlayerDataCap(oldPlayer).ifPresent(oldPlayerCap -> {
            IPlayerCap playerDataCap = (IPlayerCap)CapabilityRegistry.getPlayerDataCap(event.getEntity()).orElse(new ANPlayerDataCap());
            CompoundTag tag = (CompoundTag)oldPlayerCap.serializeNBT();
            playerDataCap.deserializeNBT(tag);
            syncPlayerCap(event.getEntity());
         });
         event.getOriginal().invalidateCaps();
      }

      @SubscribeEvent
      public static void onPlayerLoginEvent(PlayerLoggedInEvent event) {
         if (event.getEntity() instanceof ServerPlayer) {
            syncPlayerCap(event.getEntity());
         }
      }

      @SubscribeEvent
      public static void respawnEvent(PlayerRespawnEvent event) {
         if (event.getEntity() instanceof ServerPlayer) {
            syncPlayerCap(event.getEntity());
         }
      }

      @SubscribeEvent
      public static void onPlayerStartTrackingEvent(StartTracking event) {
         if (event.getTarget() instanceof Player && event.getEntity() instanceof ServerPlayer) {
            syncPlayerCap(event.getEntity());
         }
      }

      @SubscribeEvent
      public static void onPlayerDimChangedEvent(PlayerChangedDimensionEvent event) {
         if (event.getEntity() instanceof ServerPlayer) {
            syncPlayerCap(event.getEntity());
         }
      }

      public static void syncPlayerCap(Player player) {
         IPlayerCap cap = (IPlayerCap)CapabilityRegistry.getPlayerDataCap(player).orElse(new ANPlayerDataCap());
         CompoundTag tag = (CompoundTag)cap.serializeNBT();
         if (player instanceof ServerPlayer serverPlayer) {
            Networking.sendToPlayerClient(new PacketSyncPlayerCap(tag), serverPlayer);
         }
      }
   }
}
