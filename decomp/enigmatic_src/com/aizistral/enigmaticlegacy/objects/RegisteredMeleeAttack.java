package com.aizistral.enigmaticlegacy.objects;

import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class RegisteredMeleeAttack {
   private static final Map<Player, RegisteredMeleeAttack> attackRegistry = new WeakHashMap<>();
   public final Player player;
   public final int ticksSinceLastSwing;
   public final int ticksExisted;
   public final float cooledAttackStrength;

   public static boolean hasRegisteredMeleeAttack(Player player) {
      return attackRegistry.containsKey(player);
   }

   public static RegisteredMeleeAttack getRegisteredMeleeAttack(Player player) {
      return attackRegistry.get(player);
   }

   public static void registerAttack(Player player) {
      attackRegistry.put(player, new RegisteredMeleeAttack(player));
   }

   public static float getRegisteredAttackStregth(LivingEntity attacker) {
      float attackStregth = 1.0F;
      if (attacker instanceof Player && hasRegisteredMeleeAttack((Player)attacker)) {
         RegisteredMeleeAttack attack = getRegisteredMeleeAttack((Player)attacker);
         if (attack.ticksExisted == attacker.f_19797_) {
            attackStregth = attack.cooledAttackStrength;
         }
      }

      return attackStregth;
   }

   public static void clearRegistry() {
      attackRegistry.clear();
   }

   private RegisteredMeleeAttack(Player player) {
      this.player = player;
      this.ticksExisted = player.f_19797_;
      this.ticksSinceLastSwing = player.f_20922_;
      this.cooledAttackStrength = player.m_36403_(0.5F);
   }
}
