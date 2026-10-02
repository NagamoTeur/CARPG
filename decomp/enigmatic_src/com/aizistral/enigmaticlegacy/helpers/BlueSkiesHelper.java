package com.aizistral.enigmaticlegacy.helpers;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import java.lang.reflect.Method;
import net.minecraft.world.entity.player.Player;

public class BlueSkiesHelper {
   private static final Class<?> SKIES_PLAYER;
   private static final Class<?> SKIES_PLAYER_INTERFACE;
   private static final Method SET_HEALTH;
   private static final Method GET_HEALTH;
   private static final Method GET_CAPABILITY;
   private static final boolean MOD_PRESENT;

   public static void maybeFixCapability(Player player) {
      if (MOD_PRESENT) {
         try {
            Object capability = GET_CAPABILITY.invoke(null, player);
            if (capability != null && GET_HEALTH.invoke(capability) instanceof Float health && health.isNaN()) {
               SET_HEALTH.invoke(capability, 0.0F);
               player.m_21153_(0.0F);
               EnigmaticLegacy.LOGGER.info("Fixed NaN natural health for player: " + player.m_36316_().getName());
            }
         } catch (Exception var4) {
            throw new RuntimeException(var4);
         }
      }
   }

   static {
      Class<?> player;
      Class<?> iface;
      Method setHealth;
      Method getHealth;
      Method getCapability;
      try {
         player = Class.forName("com.legacy.blue_skies.capability.SkiesPlayer");
         iface = Class.forName("com.legacy.blue_skies.capability.util.ISkiesPlayer");
         setHealth = iface.getMethod("setNatureHealth", float.class);
         getHealth = iface.getMethod("getNatureHealth");
         getCapability = player.getMethod("get", Player.class);
      } catch (Throwable var6) {
         player = null;
         iface = null;
         setHealth = null;
         getHealth = null;
         getCapability = null;
      }

      SKIES_PLAYER = player;
      SKIES_PLAYER_INTERFACE = iface;
      SET_HEALTH = setHealth;
      GET_HEALTH = getHealth;
      GET_CAPABILITY = getCapability;
      MOD_PRESENT = player != null && iface != null;
      EnigmaticLegacy.LOGGER.info("Blue Skies detected: " + MOD_PRESENT);
   }
}
