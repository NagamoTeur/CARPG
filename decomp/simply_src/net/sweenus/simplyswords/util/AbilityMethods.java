package net.sweenus.simplyswords.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.sweenus.simplyswords.registry.EffectRegistry;
import net.sweenus.simplyswords.registry.SoundRegistry;

public class AbilityMethods {
   public static void tickAbilityStormJolt(ItemStack stack, Level world, Entity entity, int ability_timer, int skillCooldown, int radius) {
      if (!entity.f_19853_.m_5776_() && entity instanceof Player player) {
         if (ability_timer == 12 || ability_timer == 13 && player.m_6844_(EquipmentSlot.MAINHAND) == stack) {
            player.m_20256_(player.m_20154_().m_82490_(4.0));
            player.m_20334_(player.m_20184_().f_82479_, 0.0, player.m_20184_().f_82481_);
            player.f_19864_ = true;
            world.m_6269_(null, player, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_THUNDER_SHOOT_FLYBY_03.get(), SoundSource.PLAYERS, 0.3F, 1.6F);
            player.m_147207_(new MobEffectInstance(MobEffects.f_19596_, 80, 1), player);
            player.m_147207_(new MobEffectInstance(MobEffects.f_19599_, 10, 5), player);
            player.m_36335_().m_41524_(stack.m_41720_(), skillCooldown);
         }

         if (ability_timer < 5 && player.m_6844_(EquipmentSlot.MAINHAND) == stack) {
            player.m_20334_(0.0, 0.0, 0.0);
            player.f_19864_ = true;
            player.m_147207_(new MobEffectInstance(MobEffects.f_19598_, 80, 1), player);
         }

         if (player.f_19797_ % 2 == 0 && player.m_6844_(EquipmentSlot.MAINHAND) == stack) {
            double xpos = player.m_20185_() - (double)(radius + 1);
            double ypos = player.m_20186_();
            double zpos = player.m_20189_() - (double)(radius + 1);

            for (int i = radius * 2; i > 0; i--) {
               for (int j = radius * 2; j > 0; j--) {
                  float choose = (float)(Math.random() * 1.0);
                  HelperMethods.spawnParticle(
                     world, ParticleTypes.f_175830_, xpos + (double)i + (double)choose, ypos + 0.4, zpos + (double)j + (double)choose, 0.0, 0.1, 0.0
                  );
                  HelperMethods.spawnParticle(
                     world, ParticleTypes.f_123796_, xpos + (double)i + (double)choose, ypos + 0.1, zpos + (double)j + (double)choose, 0.0, 0.0, 0.0
                  );
                  HelperMethods.spawnParticle(
                     world, ParticleTypes.f_123785_, xpos + (double)i + (double)choose, ypos, zpos + (double)j + (double)choose, 0.0, 0.1, 0.0
                  );
               }
            }
         }
      }
   }

   public static void tickAbilityStorm(ItemStack stack, Level world, Entity entity, int ability_timer, int skillCooldown, int radius) {
      if (!entity.f_19853_.m_5776_() && entity instanceof Player player) {
         if (player.f_19797_ % 10 == 0) {
            double x = player.m_20185_();
            double y = player.m_20186_();
            double z = player.m_20189_();
            player.m_147207_(new MobEffectInstance(MobEffects.f_19606_, 15, 5), player);
            AABB box = new AABB(x + (double)radius, y + (double)radius, z + (double)radius, x - (double)radius, y - (double)radius, z - (double)radius);
            ServerLevel sworld = (ServerLevel)player.f_19853_;

            for (Entity e : world.m_6249_(player, box, EntitySelector.f_20403_)) {
               float choose = (float)(Math.random() * 1.0);
               if (e instanceof LivingEntity) {
                  LivingEntity ee = (LivingEntity)e;
                  if (HelperMethods.checkFriendlyFire(ee, player) && (double)choose > 0.7) {
                     BlockPos stormtarget = ee.m_20183_();
                     ee.m_147207_(new MobEffectInstance((MobEffect)EffectRegistry.FREEZE.get(), 10, 0), player);
                     LightningBolt storm = (LightningBolt)EntityType.f_20465_
                        .m_20600_(sworld, null, null, player, stormtarget, MobSpawnType.TRIGGERED, true, true);
                     if (storm != null) {
                        storm.m_20874_(true);
                     }

                     ee.m_6469_(DamageSource.f_19319_, 5.0F);
                  }
               }
            }
         }

         if (player.f_19797_ % 5 == 0) {
            double xpos = player.m_20185_() - (double)(radius + 1);
            double ypos = player.m_20186_();
            double zpos = player.m_20189_() - (double)(radius + 1);

            for (int i = radius * 2; i > 0; i--) {
               for (int j = radius * 2; j > 0; j--) {
                  float choose = (float)(Math.random() * 1.0);
                  HelperMethods.spawnParticle(
                     world, ParticleTypes.f_123796_, xpos + (double)i + (double)choose, ypos + 10.0, zpos + (double)j + (double)choose, 0.0, 0.0, 0.0
                  );
               }
            }
         }
      }
   }

   public static void tickAbilityThunderBlitz(
      ItemStack stack, Level world, Entity entity, int ability_timer, int ability_timer_max, int abilityDamage, int skillCooldown, int radius
   ) {
      if (!entity.f_19853_.m_5776_() && entity instanceof Player player) {
         if (ability_timer > ability_timer_max - 42 && ability_timer < ability_timer_max - 40) {
            player.m_20256_(player.m_20154_().m_82490_(6.0));
            player.m_20334_(player.m_20184_().f_82479_, 0.0, player.m_20184_().f_82481_);
            player.f_19864_ = true;
            player.m_36335_().m_41524_(stack.m_41720_(), skillCooldown);
            world.m_6269_(null, player, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_THUNDER_SHOOT_IMPACT_02.get(), SoundSource.PLAYERS, 0.3F, 1.6F);
         }

         if (ability_timer < 5) {
            player.m_20334_(0.0, 0.0, 0.0);
            player.f_19864_ = true;
            player.m_147207_(new MobEffectInstance(MobEffects.f_19598_, 80, 2), player);
         }

         if (player.f_19797_ % 3 == 0 && player.m_6844_(EquipmentSlot.MAINHAND) == stack) {
            AABB box = new AABB(
               player.m_20185_() + (double)radius,
               player.m_20186_() + (double)(radius * 2),
               player.m_20189_() + (double)radius,
               player.m_20185_() - (double)radius,
               player.m_20186_() - (double)radius,
               player.m_20189_() - (double)radius
            );

            for (Entity entities : world.m_6249_(player, box, EntitySelector.f_20403_)) {
               if (entities != null && entities instanceof LivingEntity) {
                  LivingEntity le = (LivingEntity)entities;
                  if (HelperMethods.checkFriendlyFire(le, player)) {
                     float choose = (float)(Math.random() * 1.0);
                     if (ability_timer > ability_timer_max - 40) {
                        le.m_6469_(DamageSource.f_19319_, (float)abilityDamage);
                        world.m_6269_(null, le, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_POISON_ATTACK_02.get(), SoundSource.PLAYERS, 0.1F, choose);
                     }

                     if (ability_timer < ability_timer_max - 40) {
                        le.m_6469_(DamageSource.f_19319_, (float)(abilityDamage * 10));
                        world.m_6269_(null, le, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_POISON_ATTACK_01.get(), SoundSource.PLAYERS, 0.1F, choose);
                     }
                  }
               }
            }

            double xpos = player.m_20185_() - (double)(radius + 1);
            double ypos = player.m_20186_();
            double zpos = player.m_20189_() - (double)(radius + 1);

            for (int i = radius * 2; i > 0; i--) {
               for (int j = radius * 2; j > 0; j--) {
                  float choosex = (float)(Math.random() * 1.0);
                  HelperMethods.spawnParticle(
                     world, ParticleTypes.f_175830_, xpos + (double)i + (double)choosex, ypos + 0.4, zpos + (double)j + (double)choosex, 0.0, 0.1, 0.0
                  );
                  HelperMethods.spawnParticle(
                     world, ParticleTypes.f_123796_, xpos + (double)i + (double)choosex, ypos + 0.1, zpos + (double)j + (double)choosex, 0.0, 0.0, 0.0
                  );
                  HelperMethods.spawnParticle(
                     world, ParticleTypes.f_123785_, xpos + (double)i + (double)choosex, ypos, zpos + (double)j + (double)choosex, 0.0, 0.1, 0.0
                  );
               }
            }
         }
      }
   }

   public static void tickAbilitySoulAnguish(
      ItemStack stack,
      Level world,
      Entity entity,
      int ability_timer,
      int ability_timer_max,
      int abilityDamage,
      int skillCooldown,
      int radius,
      int damageTracker,
      int chanceReduce,
      double lastX,
      double lastY,
      double lastZ,
      double targetX,
      double targetY,
      double targetZ,
      int range,
      float healAmount,
      LivingEntity abilityTarget
   ) {
      if (!entity.f_19853_.m_5776_() && entity instanceof Player player && abilityTarget != null) {
         float soundDistance = 0.2F - (float)player.m_20275_(lastX, lastY, lastZ) / 800.0F;
         if (player.f_19797_ % 5 == 0 && player.m_6844_(EquipmentSlot.MAINHAND) == stack) {
            AABB box = new AABB(
               lastX + (double)radius, lastY + (double)radius, lastZ + (double)radius, lastX - (double)radius, lastY - (double)radius, lastZ - (double)radius
            );

            for (Entity entities : world.m_6249_(player, box, EntitySelector.f_20403_)) {
               if (entities != null && entities instanceof LivingEntity) {
                  LivingEntity le = (LivingEntity)entities;
                  if (HelperMethods.checkFriendlyFire(le, player)) {
                     float choose = (float)(Math.random() * 1.0);
                     if (player.m_217043_().m_188503_(100) <= 8 - chanceReduce) {
                        world.m_6269_(null, le, (SoundEvent)SoundRegistry.DARK_SWORD_BREAKS.get(), SoundSource.PLAYERS, soundDistance, choose);
                        player.m_5634_(healAmount);
                        chanceReduce++;
                     }

                     le.m_6469_(DamageSource.f_19319_, (float)abilityDamage);
                     damageTracker += abilityDamage * 3;
                  }
               }
            }

            world.m_214150_(null, lastX, lastY, lastZ, (SoundEvent)SoundRegistry.DARK_SWORD_BLOCK.get(), SoundSource.PLAYERS, soundDistance, 0.3F, 100L);
            double xpos = lastX - (double)(radius + 1);
            double ypos = lastY;
            double zpos = lastZ - (double)(radius + 1);
            world.m_7785_(xpos, lastY, zpos, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_ICE_SHOOT_IMPACT_03.get(), SoundSource.PLAYERS, 0.1F, 0.2F, true);

            for (int i = radius * 2; i > 0; i--) {
               for (int j = radius * 2; j > 0; j--) {
                  float choose = (float)(Math.random() * 1.0);
                  HelperMethods.spawnParticle(
                     world,
                     ParticleTypes.f_123757_,
                     xpos + (double)i + (double)choose,
                     ypos,
                     zpos + (double)j + (double)choose,
                     (double)(choose / 3.0F),
                     -0.3,
                     (double)(choose / 3.0F)
                  );
                  choose = (float)(Math.random() * 1.0);
                  HelperMethods.spawnParticle(
                     world,
                     ParticleTypes.f_123746_,
                     xpos + (double)i + (double)choose,
                     ypos,
                     zpos + (double)j + (double)choose,
                     (double)(choose / 3.0F),
                     0.0,
                     (double)(choose / 3.0F)
                  );
               }
            }
         }
      }
   }

   public static void tickAbilityPermafrost(
      ItemStack stack,
      Level world,
      Entity entity,
      int ability_timer,
      int ability_timer_max,
      int abilityDamage,
      int skillCooldown,
      int radius,
      double lastX,
      double lastY,
      double lastZ
   ) {
      if (!entity.f_19853_.m_5776_() && entity instanceof Player player) {
         int rradius = radius * 2;
         if (ability_timer < 5) {
            player.m_21253_();
         }

         if (player.f_19797_ % 10 == 0 && player.m_6844_(EquipmentSlot.MAINHAND) == stack) {
            player.m_36324_().m_38703_(0.8F);
            AABB box = new AABB(
               player.m_20185_() + (double)rradius,
               player.m_20186_() + (double)radius,
               player.m_20189_() + (double)rradius,
               player.m_20185_() - (double)rradius,
               player.m_20186_() - (double)radius,
               player.m_20189_() - (double)rradius
            );

            for (Entity entities : world.m_6249_(player, box, EntitySelector.f_20403_)) {
               if (entities != null && entities instanceof LivingEntity) {
                  LivingEntity le = (LivingEntity)entities;
                  if (HelperMethods.checkFriendlyFire(le, player)) {
                     if (le.m_21023_(MobEffects.f_19597_)) {
                        int a = le.m_21124_(MobEffects.f_19597_).m_19564_() + 1;
                        if (a < 4) {
                           le.m_147207_(new MobEffectInstance(MobEffects.f_19597_, 120, a), player);
                        } else {
                           le.m_147207_(new MobEffectInstance(MobEffects.f_19597_, 120, a - 1), player);
                        }
                     } else {
                        le.m_147207_(new MobEffectInstance(MobEffects.f_19597_, 120, 0), player);
                     }

                     float choose = (float)(Math.random() * 1.0);
                     world.m_6269_(null, le, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_ICE_SHOOT_IMPACT_03.get(), SoundSource.PLAYERS, 0.1F, choose);
                     le.m_6469_(DamageSource.f_146701_, (float)(abilityDamage * 3));
                  }
               }
            }

            double xpos = lastX - (double)(rradius + 1);
            double ypos = lastY;
            double zpos = lastZ - (double)(rradius + 1);
            world.m_7785_(xpos, lastY, zpos, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_ICE_SHOOT_IMPACT_03.get(), SoundSource.PLAYERS, 0.1F, 0.2F, true);

            for (int i = rradius * 2; i > 0; i--) {
               for (int j = rradius * 2; j > 0; j--) {
                  float choose = (float)(Math.random() * 1.0);
                  HelperMethods.spawnParticle(
                     world,
                     ParticleTypes.f_175821_,
                     xpos + (double)i + (double)choose,
                     ypos + 6.0,
                     zpos + (double)j + (double)choose,
                     (double)(choose / 3.0F),
                     -0.3,
                     (double)(choose / 3.0F)
                  );
                  choose = (float)(Math.random() * 1.0);
                  HelperMethods.spawnParticle(
                     world,
                     ParticleTypes.f_123790_,
                     xpos + (double)i + (double)choose,
                     ypos + 6.0,
                     zpos + (double)j + (double)choose,
                     (double)(choose / 3.0F),
                     0.0,
                     (double)(choose / 3.0F)
                  );
               }
            }
         }
      }
   }

   public static void tickAbilityArcaneAssault(
      ItemStack stack, Level world, Entity entity, int ability_timer, int ability_timer_max, int abilityDamage, int skillCooldown, int radius
   ) {
      if (!entity.f_19853_.m_5776_() && entity instanceof Player player) {
         if (ability_timer < 5) {
            player.m_21253_();
         }

         if (player.f_19797_ % 10 == 0 && player.m_6844_(EquipmentSlot.MAINHAND) == stack) {
            AABB box = new AABB(
               player.m_20185_() + (double)radius,
               player.m_20186_() + (double)(radius * 2),
               player.m_20189_() + (double)radius,
               player.m_20185_() - (double)radius,
               player.m_20186_() - (double)radius,
               player.m_20189_() - (double)radius
            );

            for (Entity entities : world.m_6249_(player, box, EntitySelector.f_20403_)) {
               if (entities != null && entities instanceof LivingEntity) {
                  LivingEntity le = (LivingEntity)entities;
                  if (HelperMethods.checkFriendlyFire(le, player)) {
                     float choose = (float)(Math.random() * 1.0);
                     if (!le.m_21023_(MobEffects.f_19620_) && ability_timer > 30) {
                        le.m_147207_(new MobEffectInstance(MobEffects.f_19620_, 20, 1), player);
                        world.m_6269_(null, le, (SoundEvent)SoundRegistry.MAGIC_BOW_SHOOT_IMPACT_03.get(), SoundSource.PLAYERS, 0.1F, choose);
                     }

                     le.m_6469_(DamageSource.f_19319_, (float)abilityDamage);
                     if (ability_timer < 10) {
                        le.m_21195_(MobEffects.f_19620_);
                        le.m_6469_(DamageSource.f_19319_, (float)(abilityDamage * 10));
                        le.m_20334_(0.0, -10.0, 0.0);
                        player.m_21253_();
                        world.m_6269_(null, le, (SoundEvent)SoundRegistry.ELEMENTAL_SWORD_SCIFI_ATTACK_03.get(), SoundSource.PLAYERS, 0.3F, choose);
                     }
                  }
               }
            }

            world.m_6269_(null, player, (SoundEvent)SoundRegistry.MAGIC_BOW_CHARGE_SHORT_VERSION.get(), SoundSource.PLAYERS, 0.1F, 0.6F);
            double xpos = player.m_20185_() - (double)(radius + 1);
            double ypos = player.m_20186_();
            double zpos = player.m_20189_() - (double)(radius + 1);

            for (int i = radius * 2; i > 0; i--) {
               for (int j = radius * 2; j > 0; j--) {
                  float choosex = (float)(Math.random() * 1.0);
                  HelperMethods.spawnParticle(
                     world, ParticleTypes.f_123799_, xpos + (double)i + (double)choosex, ypos + 0.4, zpos + (double)j + (double)choosex, 0.0, 0.1, 0.0
                  );
                  HelperMethods.spawnParticle(
                     world, ParticleTypes.f_123760_, xpos + (double)i + (double)choosex, ypos + 0.1, zpos + (double)j + (double)choosex, 0.0, 0.0, 0.0
                  );
                  HelperMethods.spawnParticle(
                     world, ParticleTypes.f_123789_, xpos + (double)i + (double)choosex, ypos + 1.0, zpos + (double)j + (double)choosex, 0.0, 0.1, 0.0
                  );
               }
            }
         }
      }
   }

   public static void tickAbilityVolcanicFury(
      ItemStack stack, Level world, Entity entity, int ability_timer, int ability_timer_max, int abilityDamage, int skillCooldown, int radius, int chargePower
   ) {
      if (!entity.f_19853_.m_5776_() && entity instanceof Player player) {
         if (ability_timer < 5) {
            player.m_21253_();
         }

         if (player.f_19797_ % 20 == 0 && player.m_6844_(EquipmentSlot.MAINHAND) == stack) {
            if (ability_timer > 10) {
               player.m_147207_(new MobEffectInstance(MobEffects.f_19597_, 20, 5), player);
               player.m_147207_(new MobEffectInstance(MobEffects.f_19599_, 20, 5), player);
               player.m_147207_(new MobEffectInstance(MobEffects.f_19606_, 20, 5), player);
               world.m_6269_(
                  null, player, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_EARTH_SHOOT_IMPACT_02.get(), SoundSource.PLAYERS, 0.8F, 0.1F * (float)chargePower
               );
               if (player.m_21223_() > 2.0F && !player.m_7500_()) {
                  player.m_21153_(player.m_21223_() - 1.0F);
               }
            }

            AABB box = new AABB(
               player.m_20185_() + (double)(radius * 8),
               player.m_20186_() + (double)radius,
               player.m_20189_() + (double)(radius * 8),
               player.m_20185_() - (double)(radius * 8),
               player.m_20186_() - (double)radius,
               player.m_20189_() - (double)(radius * 8)
            );

            for (Entity entities : world.m_6249_(player, box, EntitySelector.f_20403_)) {
               if (entities != null && entities instanceof LivingEntity) {
                  LivingEntity le = (LivingEntity)entities;
                  if (HelperMethods.checkFriendlyFire(le, player) && ability_timer > 12) {
                     le.m_6469_(DamageSource.f_19319_, (float)abilityDamage);
                     le.m_20334_(
                        (player.m_20185_() - le.m_20185_()) / 10.0, (player.m_20186_() - le.m_20186_()) / 10.0, (player.m_20189_() - le.m_20189_()) / 10.0
                     );
                     le.m_147207_(new MobEffectInstance(MobEffects.f_19597_, 40, 3), player);
                  }
               }
            }

            double xpos = player.m_20185_() - (double)(radius + 1);
            double ypos = player.m_20186_();
            double zpos = player.m_20189_() - (double)(radius + 1);

            for (int i = radius * 2; i > 0; i--) {
               for (int j = radius * 2; j > 0; j--) {
                  float choose = (float)(Math.random() * 1.0);
                  HelperMethods.spawnParticle(
                     world, ParticleTypes.f_123785_, xpos + (double)i + (double)choose, ypos + 0.4, zpos + (double)j + (double)choose, 0.0, 0.1, 0.0
                  );
                  HelperMethods.spawnParticle(
                     world, ParticleTypes.f_123777_, xpos + (double)i + (double)choose, ypos + 0.1, zpos + (double)j + (double)choose, 0.0, 0.0, 0.0
                  );
                  HelperMethods.spawnParticle(
                     world, ParticleTypes.f_123756_, xpos + (double)i + (double)choose, ypos, zpos + (double)j + (double)choose, 0.0, 0.1, 0.0
                  );
               }
            }
         }
      }
   }
}
