package com.bobmowzie.mowziesmobs.server.damage;

import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.LivingCapability;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.tuple.Pair;

public class DamageUtil {
   public static Pair<Boolean, Boolean> dealMixedDamage(LivingEntity target, DamageSource source1, float amount1, DamageSource source2, float amount2) {
      if (target.f_19853_.m_5776_()) {
         return Pair.of(false, false);
      } else {
         boolean flag1 = source1.m_7639_() != null && target.m_7307_(source1.m_7639_());
         boolean flag2 = source2.m_7639_() != null && target.m_7307_(source2.m_7639_());
         if (!flag1 && !flag2) {
            LivingCapability.ILivingCapability lastDamageCapability = CapabilityHandler.getCapability(target, CapabilityHandler.LIVING_CAPABILITY);
            if (lastDamageCapability != null) {
               lastDamageCapability.setLastDamage(-1.0F);
               float damageSoFar = 0.0F;
               float origLastDamage = target.f_20898_;
               boolean hit1 = target.m_6469_(source1, amount1);
               boolean hit1Registered = hit1;
               if (lastDamageCapability.getLastDamage() != -1.0F) {
                  hit1Registered = true;
               }

               if (lastDamageCapability.getLastDamage() != 0.0F) {
                  damageSoFar += amount1;
               }

               target.f_20898_ = Math.max(target.f_20898_ - amount1, 0.0F);
               lastDamageCapability.setLastDamage(-1.0F);
               boolean hit2 = target.m_6469_(source2, amount2);
               if (lastDamageCapability.getLastDamage() != -1.0F) {
                  boolean hit2Registered = true;
               }

               if (lastDamageCapability.getLastDamage() != 0.0F) {
                  damageSoFar += amount2;
               }

               target.f_20898_ = origLastDamage;
               if (damageSoFar > target.f_20898_) {
                  target.f_20898_ = damageSoFar;
               }

               if (hit2 && hit1Registered) {
                  onHit2(target, source2);
                  if (target instanceof Player) {
                     SoundEvent sound = SoundEvents.f_12323_;
                     if (source2 == DamageSource.f_19307_) {
                        sound = SoundEvents.f_12273_;
                     } else if (source2 == DamageSource.f_19312_) {
                        sound = SoundEvents.f_12324_;
                     }

                     target.m_5496_(sound, 1.0F, getSoundPitch(target));
                  }
               }

               return Pair.of(hit1, hit2);
            } else {
               return Pair.of(false, false);
            }
         } else {
            return Pair.of(false, false);
         }
      }
   }

   private static float getSoundPitch(LivingEntity target) {
      return (target.m_217043_().m_188501_() - target.m_217043_().m_188501_()) * 0.2F + 1.0F;
   }

   private static void onHit2(LivingEntity target, DamageSource source) {
      if (source instanceof EntityDamageSource && ((EntityDamageSource)source).m_19403_()) {
         target.f_19853_.m_7605_(target, (byte)33);
      } else {
         byte b0;
         if (source == DamageSource.f_19312_) {
            b0 = 36;
         } else if (source.m_19384_()) {
            b0 = 37;
         } else {
            b0 = 2;
         }

         target.f_19853_.m_7605_(target, b0);
      }

      Entity entity1 = source.m_7639_();
      if (entity1 != null) {
         double d1 = entity1.m_20185_() - target.m_20185_();

         double d0;
         for (d0 = entity1.m_20189_() - target.m_20189_(); d1 * d1 + d0 * d0 < 1.0E-4; d0 = (Math.random() - Math.random()) * 0.01) {
            d1 = (Math.random() - Math.random()) * 0.01;
         }

         target.f_20918_ = (float)(Mth.m_14136_(d0, d1) * (180.0 / Math.PI) - (double)target.m_146908_());
         target.m_147240_(0.4F, d1, d0);
      } else {
         target.f_20918_ = (float)((int)(Math.random() * 2.0) * 180);
      }
   }
}
