package net.sweenus.simplyswords.util;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.entity.BattleStandardEntity;
import net.sweenus.simplyswords.registry.EffectRegistry;
import net.sweenus.simplyswords.registry.EntityRegistry;
import net.sweenus.simplyswords.registry.SoundRegistry;

public class RunicMethods {
   public static void postHitRunicFreeze(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int fhitchance = (int)SimplySwordsConfig.getFloatValue("freeze_chance");
      int fduration = (int)SimplySwordsConfig.getFloatValue("freeze_duration");
      int sduration = (int)SimplySwordsConfig.getFloatValue("slowness_duration");
      target.m_147207_(new MobEffectInstance(MobEffects.f_19597_, sduration, 1), attacker);
      if (attacker.m_217043_().m_188503_(100) <= fhitchance) {
         target.m_147207_(new MobEffectInstance((MobEffect)EffectRegistry.FREEZE.get(), fduration, 1), attacker);
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.1F, 1.8F);
      }
   }

   public static void postHitRunicWildfire(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int phitchance = (int)SimplySwordsConfig.getFloatValue("wildfire_chance");
      int pduration = (int)SimplySwordsConfig.getFloatValue("wildfire_duration");
      if (attacker.m_217043_().m_188503_(100) <= phitchance) {
         target.m_147207_(new MobEffectInstance((MobEffect)EffectRegistry.WILDFIRE.get(), pduration, 3), attacker);
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.1F, 1.8F);
      }
   }

   public static void postHitRunicSlow(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int shitchance = (int)SimplySwordsConfig.getFloatValue("slowness_chance");
      int sduration = (int)SimplySwordsConfig.getFloatValue("slowness_duration");
      if (attacker.m_217043_().m_188503_(100) <= shitchance) {
         target.m_147207_(new MobEffectInstance(MobEffects.f_19597_, sduration, 1), attacker);
      }
   }

   public static void postHitRunicGreaterSlow(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int shitchance = (int)SimplySwordsConfig.getFloatValue("slowness_chance");
      int sduration = (int)SimplySwordsConfig.getFloatValue("slowness_duration");
      if (attacker.m_217043_().m_188503_(100) <= shitchance) {
         target.m_147207_(new MobEffectInstance(MobEffects.f_19597_, sduration, 2), attacker);
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.1F, 1.8F);
      }
   }

   public static void postHitRunicSwiftness(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int shitchance = (int)SimplySwordsConfig.getFloatValue("speed_chance");
      int sduration = (int)SimplySwordsConfig.getFloatValue("speed_duration");
      if (attacker.m_217043_().m_188503_(100) <= shitchance) {
         attacker.m_147207_(new MobEffectInstance(MobEffects.f_19596_, sduration, 0), attacker);
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.1F, 1.8F);
      }
   }

   public static void postHitRunicGreaterSwiftness(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int shitchance = (int)SimplySwordsConfig.getFloatValue("speed_chance");
      int sduration = (int)SimplySwordsConfig.getFloatValue("speed_duration");
      if (attacker.m_217043_().m_188503_(100) <= shitchance) {
         attacker.m_147207_(new MobEffectInstance(MobEffects.f_19596_, sduration, 1), attacker);
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.1F, 1.8F);
      }
   }

   public static void postHitRunicFloat(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int lhitchance = (int)SimplySwordsConfig.getFloatValue("levitation_chance");
      int lduration = (int)SimplySwordsConfig.getFloatValue("levitation_duration");
      if (attacker.m_217043_().m_188503_(100) <= lhitchance) {
         target.m_147207_(new MobEffectInstance(MobEffects.f_19620_, lduration, 2), attacker);
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.1F, 1.8F);
      }
   }

   public static void postHitRunicGreaterFloat(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int lhitchance = (int)SimplySwordsConfig.getFloatValue("levitation_chance");
      int lduration = (int)SimplySwordsConfig.getFloatValue("levitation_duration");
      if (attacker.m_217043_().m_188503_(100) <= lhitchance) {
         target.m_147207_(new MobEffectInstance(MobEffects.f_19620_, lduration, 3), attacker);
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.1F, 1.8F);
      }
   }

   public static void postHitRunicZephyr(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int lhitchance = (int)SimplySwordsConfig.getFloatValue("zephyr_chance");
      int lduration = (int)SimplySwordsConfig.getFloatValue("zephyr_duration");
      if (attacker.m_217043_().m_188503_(100) <= lhitchance) {
         attacker.m_147207_(new MobEffectInstance(MobEffects.f_19598_, lduration, 0), attacker);
         attacker.m_147207_(new MobEffectInstance(MobEffects.f_19596_, lduration, 0), attacker);
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.1F, 1.8F);
      }
   }

   public static void postHitRunicGreaterZephyr(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int lhitchance = (int)SimplySwordsConfig.getFloatValue("zephyr_chance");
      int lduration = (int)SimplySwordsConfig.getFloatValue("zephyr_duration");
      if (attacker.m_217043_().m_188503_(100) <= lhitchance) {
         attacker.m_147207_(new MobEffectInstance(MobEffects.f_19598_, lduration, 1), attacker);
         attacker.m_147207_(new MobEffectInstance(MobEffects.f_19596_, lduration, 1), attacker);
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.1F, 1.8F);
      }
   }

   public static void postHitRunicShielding(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int lhitchance = (int)SimplySwordsConfig.getFloatValue("shielding_chance");
      int lduration = (int)SimplySwordsConfig.getFloatValue("shielding_duration");
      if (attacker.m_217043_().m_188503_(100) <= lhitchance) {
         attacker.m_147207_(new MobEffectInstance(MobEffects.f_19617_, lduration, 0), attacker);
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.1F, 1.8F);
      }
   }

   public static void postHitRunicGreaterShielding(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int lhitchance = (int)SimplySwordsConfig.getFloatValue("shielding_chance");
      int lduration = (int)SimplySwordsConfig.getFloatValue("shielding_duration");
      if (attacker.m_217043_().m_188503_(100) <= lhitchance) {
         attacker.m_147207_(new MobEffectInstance(MobEffects.f_19617_, lduration, 1), attacker);
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.1F, 1.8F);
      }
   }

   public static void postHitRunicStoneskin(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int lhitchance = (int)SimplySwordsConfig.getFloatValue("stoneskin_chance");
      int lduration = (int)SimplySwordsConfig.getFloatValue("stoneskin_duration");
      if (attacker.m_217043_().m_188503_(100) <= lhitchance) {
         attacker.m_147207_(new MobEffectInstance(MobEffects.f_19606_, lduration, 1), attacker);
         attacker.m_147207_(new MobEffectInstance(MobEffects.f_19607_, lduration, 0), attacker);
         attacker.m_147207_(new MobEffectInstance(MobEffects.f_19597_, lduration, 0), attacker);
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.ELEMENTAL_SWORD_EARTH_ATTACK_02.get(), SoundSource.PLAYERS, 0.3F, 1.3F);
      }
   }

   public static void postHitRunicGreaterStoneskin(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int lhitchance = (int)SimplySwordsConfig.getFloatValue("stoneskin_chance");
      int lduration = (int)SimplySwordsConfig.getFloatValue("stoneskin_duration");
      if (attacker.m_217043_().m_188503_(100) <= lhitchance) {
         attacker.m_147207_(new MobEffectInstance(MobEffects.f_19606_, lduration, 2), attacker);
         attacker.m_147207_(new MobEffectInstance(MobEffects.f_19607_, lduration, 0), attacker);
         attacker.m_147207_(new MobEffectInstance(MobEffects.f_19597_, lduration, 1), attacker);
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.ELEMENTAL_SWORD_EARTH_ATTACK_02.get(), SoundSource.PLAYERS, 0.3F, 1.1F);
      }
   }

   public static void postHitRunicTrailblaze(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int lhitchance = (int)SimplySwordsConfig.getFloatValue("trailblaze_chance");
      int lduration = (int)SimplySwordsConfig.getFloatValue("trailblaze_duration");
      if (attacker.m_217043_().m_188503_(100) <= lhitchance) {
         attacker.m_147207_(new MobEffectInstance(MobEffects.f_19596_, lduration, 1), attacker);
         attacker.m_20254_(lduration / 20);
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.1F, 1.8F);
      }
   }

   public static void postHitRunicGreaterTrailblaze(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int lhitchance = (int)SimplySwordsConfig.getFloatValue("trailblaze_chance");
      int lduration = (int)SimplySwordsConfig.getFloatValue("trailblaze_duration");
      if (attacker.m_217043_().m_188503_(100) <= lhitchance) {
         attacker.m_147207_(new MobEffectInstance(MobEffects.f_19596_, lduration, 2), attacker);
         attacker.m_20254_(lduration / 20);
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.1F, 1.8F);
      }
   }

   public static void postHitRunicWeaken(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int lhitchance = (int)SimplySwordsConfig.getFloatValue("weaken_chance");
      int lduration = (int)SimplySwordsConfig.getFloatValue("weaken_duration");
      if (attacker.m_217043_().m_188503_(100) <= lhitchance) {
         target.m_147207_(new MobEffectInstance(MobEffects.f_19613_, lduration, 0), attacker);
         target.m_147207_(new MobEffectInstance(MobEffects.f_19597_, lduration, 1), attacker);
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.1F, 1.8F);
      }
   }

   public static void postHitRunicGreaterWeaken(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int lhitchance = (int)SimplySwordsConfig.getFloatValue("weaken_chance");
      int lduration = (int)SimplySwordsConfig.getFloatValue("weaken_duration");
      if (attacker.m_217043_().m_188503_(100) <= lhitchance) {
         target.m_147207_(new MobEffectInstance(MobEffects.f_19613_, lduration, 1), attacker);
         target.m_147207_(new MobEffectInstance(MobEffects.f_19597_, lduration, 2), attacker);
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.1F, 1.8F);
      }
   }

   public static void postHitRunicImbued(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int hitchance = (int)SimplySwordsConfig.getFloatValue("imbued_chance");
      int damage = 6 - stack.m_41773_() / stack.m_41776_() * 100 / 20;
      if (attacker.m_217043_().m_188503_(100) <= hitchance) {
         target.f_19802_ = 0;
         target.m_6469_(DamageSource.f_19319_, (float)damage);
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.2F, 1.8F);
      }
   }

   public static void postHitRunicGreaterImbued(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int hitchance = (int)SimplySwordsConfig.getFloatValue("imbued_chance");
      int damage = 10 - stack.m_41773_() / stack.m_41776_() * 100 / 10;
      if (attacker.m_217043_().m_188503_(100) <= hitchance) {
         target.f_19802_ = 0;
         target.m_6469_(DamageSource.f_19319_, (float)damage);
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.2F, 1.8F);
      }
   }

   public static void postHitRunicPinCushion(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int stuckArrows = attacker.m_21234_();
      target.m_6469_(DamageSource.f_19318_, (float)stuckArrows);
      attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.1F, 1.8F);
   }

   public static void postHitRunicGreaterPinCushion(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int stuckArrows = attacker.m_21234_();
      target.m_6469_(DamageSource.f_19318_, (float)(stuckArrows * 2));
      attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.1F, 1.8F);
   }

   public static void postHitNetherEcho(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int amp = 0;
      if (HelperMethods.isUniqueTwohanded(stack)) {
         amp = 2;
      }

      target.m_147207_(new MobEffectInstance((MobEffect)EffectRegistry.ECHO.get(), 20, amp), attacker);
   }

   public static void postHitNetherBerserk(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      int amp = 2;
      if (HelperMethods.isUniqueTwohanded(stack)) {
         amp = 4;
      }

      if (attacker.m_21230_() < 10) {
         target.m_21153_(target.m_21223_() - (float)amp);
         attacker.m_5634_((float)amp / 2.0F);
      }
   }

   public static void postHitNetherRadiance(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (target.m_21023_(MobEffects.f_19613_)) {
         attacker.m_147207_(new MobEffectInstance((MobEffect)EffectRegistry.IMMOLATION.get(), 100, 0), attacker);
      }
   }

   public static void postHitNetherOnslaught(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (target.m_21023_(MobEffects.f_19597_) && !attacker.m_21023_(MobEffects.f_19613_)) {
         attacker.m_147207_(new MobEffectInstance((MobEffect)EffectRegistry.ONSLAUGHT.get(), 80, 0), attacker);
      }
   }

   public static void postHitNetherNullification(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (!attacker.m_21023_((MobEffect)EffectRegistry.BATTLE_FATIGUE.get()) && attacker instanceof Player user && !user.f_19853_.m_5776_()) {
         ServerLevel serverWorld = (ServerLevel)user.f_19853_;
         BlockState currentState = serverWorld.m_8055_(user.m_20183_().m_6630_(4).m_5484_(user.m_6374_(), 3));
         BlockState state = Blocks.f_50016_.m_49966_();
         if (currentState == state) {
            serverWorld.m_6269_(null, user, (SoundEvent)SoundRegistry.ELEMENTAL_SWORD_EARTH_ATTACK_01.get(), SoundSource.PLAYERS, 0.4F, 0.8F);
            BattleStandardEntity banner = (BattleStandardEntity)((EntityType)EntityRegistry.BATTLESTANDARD.get())
               .m_20600_(
                  serverWorld,
                  null,
                  Component.m_237110_("entity.simplyswords.battlestandard.name", new Object[]{user.m_7755_()}),
                  user,
                  user.m_20183_().m_6630_(4).m_5484_(user.m_6374_(), 3),
                  MobSpawnType.MOB_SUMMONED,
                  true,
                  true
               );
            if (banner != null) {
               banner.m_20334_(0.0, -1.0, 0.0);
               banner.ownerEntity = user;
               banner.decayRate = 3;
               banner.standardType = "nullification";
            }

            attacker.m_147207_(new MobEffectInstance((MobEffect)EffectRegistry.BATTLE_FATIGUE.get(), 800, 0), attacker);
         }
      }
   }

   public static void postHitRunicEmpty(ItemStack stack, LivingEntity target, LivingEntity attacker) {
   }

   public static void stoppedUsingRunicMomentum(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
      if (user.m_6844_(EquipmentSlot.MAINHAND) == stack) {
         user.m_20334_(0.0, 0.0, 0.0);
         user.f_19864_ = true;
      }
   }

   public static void usageTickRunicMomentum(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
      int skillCooldown = (int)SimplySwordsConfig.getFloatValue("momentum_cooldown");
      if (user.m_6844_(EquipmentSlot.MAINHAND) == stack
         && user.m_20096_()
         && user instanceof Player player
         && (remainingUseTicks == 12 || remainingUseTicks == 13 && player.m_6844_(EquipmentSlot.MAINHAND) == stack)) {
         player.m_20256_(player.m_20154_().m_82490_(3.0));
         player.m_20334_(player.m_20184_().f_82479_, 0.0, player.m_20184_().f_82481_);
         player.f_19864_ = true;
         player.m_36335_().m_41524_(stack.m_41720_(), skillCooldown);
      }
   }

   public static void usageTickRunicGreaterMomentum(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
      int skillCooldown = (int)SimplySwordsConfig.getFloatValue("momentum_cooldown");
      if (user.m_6844_(EquipmentSlot.MAINHAND) == stack
         && user.m_20096_()
         && user instanceof Player player
         && (remainingUseTicks == 10 || remainingUseTicks == 13 && player.m_6844_(EquipmentSlot.MAINHAND) == stack)) {
         player.m_20256_(player.m_20154_().m_82490_(3.0));
         player.m_20334_(player.m_20184_().f_82479_, 0.0, player.m_20184_().f_82481_);
         player.f_19864_ = true;
         player.m_36335_().m_41524_(stack.m_41720_(), skillCooldown);
      }
   }

   public static void inventoryTickRunicUnstable(ItemStack stack, Level world, Player player, int slot, boolean selected) {
      int lduration = (int)SimplySwordsConfig.getFloatValue("unstable_duration");
      int lfrequency = (int)SimplySwordsConfig.getFloatValue("unstable_frequency");
      if (player.f_19797_ % lfrequency == 0) {
         int random = (int)(Math.random() * 100.0);
         if (random >= 0 && random < 10) {
            player.m_7292_(new MobEffectInstance(MobEffects.f_19596_, lduration));
         }

         if (random >= 10 && random < 20) {
            player.m_7292_(new MobEffectInstance(MobEffects.f_19606_, lduration));
         }

         if (random >= 20 && random < 30) {
            player.m_7292_(new MobEffectInstance(MobEffects.f_19605_, lduration));
         }

         if (random >= 30 && random < 40) {
            player.m_7292_(new MobEffectInstance(MobEffects.f_19609_, lduration));
         }

         if (random >= 40 && random < 50) {
            player.m_7292_(new MobEffectInstance(MobEffects.f_19607_, lduration));
         }

         if (random >= 50 && random < 60) {
            player.m_7292_(new MobEffectInstance(MobEffects.f_19603_, lduration));
         }

         if (random >= 60 && random < 70) {
            player.m_7292_(new MobEffectInstance(MobEffects.f_19598_, lduration));
         }

         if (random >= 70 && random < 80) {
            player.m_7292_(new MobEffectInstance(MobEffects.f_19593_, lduration));
         }

         if (random >= 80 && random < 90) {
            player.m_7292_(new MobEffectInstance(MobEffects.f_19613_, lduration));
         }

         if (random >= 90 && random < 95) {
            player.m_7292_(new MobEffectInstance(MobEffects.f_19620_, lduration));
         }

         if (random >= 95 && random < 100) {
            player.m_7292_(new MobEffectInstance(MobEffects.f_19591_, lduration));
         }
      }
   }

   public static void inventoryTickRunicActiveDefence(ItemStack stack, Level world, Player player, int slot, boolean selected) {
      int lfrequency = (int)SimplySwordsConfig.getFloatValue("active_defence_frequency");
      if (player.f_19797_ % lfrequency == 0) {
         int sradius = (int)SimplySwordsConfig.getFloatValue("active_defence_radius");
         int vradius = (int)(SimplySwordsConfig.getFloatValue("active_defence_radius") / 2.0F);
         double x = player.m_20185_();
         double y = player.m_20186_();
         double z = player.m_20189_();
         ServerLevel sworld = (ServerLevel)player.f_19853_;
         AABB box = new AABB(x + (double)sradius, y + (double)vradius, z + (double)sradius, x - (double)sradius, y - (double)vradius, z - (double)sradius);

         for (Entity entities : sworld.m_6249_(player, box, EntitySelector.f_20403_)) {
            if (entities != null && entities instanceof LivingEntity) {
               LivingEntity le = (LivingEntity)entities;
               if (player.m_150109_().m_36063_(Items.f_42412_.m_7968_()) && HelperMethods.checkFriendlyFire(le, player)) {
                  int arrowstack = player.m_150109_().m_36030_(Items.f_42412_.m_7968_());
                  ItemStack astack = player.m_150109_().m_8020_(arrowstack);
                  int randomc = (int)(Math.random() * 100.0);
                  if (randomc < 15) {
                     astack.m_41764_(astack.m_41613_() - 1);
                  }

                  if (le.m_20270_(player) < (float)sradius) {
                     double ex = le.m_20185_();
                     double ey = le.m_20186_();
                     double ez = le.m_20189_();
                     BlockPos position = player.m_20183_();
                     Vec3 rotation = le.m_20252_(1.0F);
                     Vec3 newPos = player.m_20182_().m_82549_(rotation);
                     Arrow arrow = new Arrow(EntityType.f_20548_, (ServerLevel)world);
                     arrow.m_20248_(player.m_20185_(), player.m_20186_() + 1.5, player.m_20189_());
                     arrow.m_5602_(player);
                     arrow.m_20334_(le.m_20185_() - player.m_20185_(), le.m_20186_() - player.m_20186_() - 1.0, le.m_20189_() - player.m_20189_());
                     sworld.m_7967_(arrow);
                     break;
                  }
               }
            }
         }
      }
   }

   public static void inventoryTickRunicFrostWard(ItemStack stack, Level world, Player player, int slot, boolean selected) {
      int lfrequency = (int)SimplySwordsConfig.getFloatValue("frostward_frequency");
      int lduration = (int)SimplySwordsConfig.getFloatValue("frostward_slow_duration");
      if (player.f_19797_ % lfrequency == 0) {
         int sradius = (int)SimplySwordsConfig.getFloatValue("frostward_radius");
         int vradius = (int)(SimplySwordsConfig.getFloatValue("frostward_radius") / 2.0F);
         double x = player.m_20185_();
         double y = player.m_20186_();
         double z = player.m_20189_();
         ServerLevel sworld = (ServerLevel)player.f_19853_;
         AABB box = new AABB(x + (double)sradius, y + (double)vradius, z + (double)sradius, x - (double)sradius, y - (double)vradius, z - (double)sradius);

         for (Entity entities : sworld.m_6249_(player, box, EntitySelector.f_20403_)) {
            if (entities != null && entities instanceof LivingEntity) {
               LivingEntity le = (LivingEntity)entities;
               if (HelperMethods.checkFriendlyFire(le, player) && le.m_20270_(player) < (float)sradius) {
                  double ex = le.m_20185_();
                  double ey = le.m_20186_();
                  double ez = le.m_20189_();
                  BlockPos position = player.m_20183_();
                  Vec3 rotation = le.m_20252_(1.0F);
                  Vec3 newPos = player.m_20182_().m_82549_(rotation);
                  Snowball snowball = new Snowball(EntityType.f_20477_, (ServerLevel)world);
                  snowball.m_20248_(player.m_20185_(), player.m_20186_() + 1.5, player.m_20189_());
                  snowball.m_5602_(player);
                  le.m_7292_(new MobEffectInstance(MobEffects.f_19597_, lduration));
                  snowball.m_20334_(le.m_20185_() - player.m_20185_(), le.m_20186_() - player.m_20186_() - 1.0, le.m_20189_() - player.m_20189_());
                  sworld.m_7967_(snowball);
               }
            }
         }
      }
   }
}
