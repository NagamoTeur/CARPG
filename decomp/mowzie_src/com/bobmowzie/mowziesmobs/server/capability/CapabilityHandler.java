package com.bobmowzie.mowziesmobs.server.capability;

import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public final class CapabilityHandler {
   public static final Capability<FrozenCapability.IFrozenCapability> FROZEN_CAPABILITY = CapabilityManager.get(
      new CapabilityToken<FrozenCapability.IFrozenCapability>() {
      }
   );
   public static final Capability<LivingCapability.ILivingCapability> LIVING_CAPABILITY = CapabilityManager.get(
      new CapabilityToken<LivingCapability.ILivingCapability>() {
      }
   );
   public static final Capability<PlayerCapability.IPlayerCapability> PLAYER_CAPABILITY = CapabilityManager.get(
      new CapabilityToken<PlayerCapability.IPlayerCapability>() {
      }
   );
   public static final Capability<AbilityCapability.IAbilityCapability> ABILITY_CAPABILITY = CapabilityManager.get(
      new CapabilityToken<AbilityCapability.IAbilityCapability>() {
      }
   );

   public static void registerCapabilities(RegisterCapabilitiesEvent event) {
      event.register(FrozenCapability.IFrozenCapability.class);
      event.register(LivingCapability.ILivingCapability.class);
      event.register(PlayerCapability.IPlayerCapability.class);
      event.register(AbilityCapability.IAbilityCapability.class);
   }

   public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> e) {
      if (e.getObject() instanceof LivingEntity) {
         e.addCapability(LivingCapability.ID, new LivingCapability.LivingProvider());
         e.addCapability(FrozenCapability.ID, new FrozenCapability.FrozenProvider());
         if (e.getObject() instanceof Player) {
            e.addCapability(PlayerCapability.ID, new PlayerCapability.PlayerProvider());
            e.addCapability(AbilityCapability.ID, new AbilityCapability.AbilityProvider());
         }
      }
   }

   @Nullable
   public static <T> T getCapability(Entity entity, Capability<T> capability) {
      if (entity == null) {
         return null;
      } else if (entity.m_213877_()) {
         return null;
      } else {
         return (T)(entity.getCapability(capability).isPresent()
            ? entity.getCapability(capability).orElseThrow(() -> new IllegalArgumentException("Lazy optional must not be empty"))
            : null);
      }
   }
}
