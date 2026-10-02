package com.rolfmao.upgradednetherite.handlers;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.rolfmao.upgradednetherite.config.UpgradedNetheriteConfig;
import com.rolfmao.upgradednetherite.utils.EntityDataUtil;
import com.rolfmao.upgradednetherite.utils.check.EchoUtil;
import com.rolfmao.upgradednetherite.utils.check.EnderUtil;
import com.rolfmao.upgradednetherite.utils.check.FeatherUtil;
import com.rolfmao.upgradednetherite.utils.check.FireUtil;
import com.rolfmao.upgradednetherite.utils.check.GoldUtil;
import com.rolfmao.upgradednetherite.utils.check.PhantomUtil;
import com.rolfmao.upgradednetherite.utils.check.PoisonUtil;
import com.rolfmao.upgradednetherite.utils.check.WaterUtil;
import com.rolfmao.upgradednetherite.utils.check.WitherUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent.Added;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
   modid = "upgradednetherite",
   bus = Bus.FORGE
)
public class HorseArmorEventHandler {
   @SubscribeEvent(
      receiveCanceled = true,
      priority = EventPriority.LOWEST
   )
   public static void onDamageEntity(LivingHurtEvent event) {
      if (!event.getEntity().f_19853_.f_46443_) {
         if (event.getEntity() instanceof Player
            && event.getEntity().m_20202_() instanceof Horse
            && FeatherUtil.isHorseWearingFeatherArmor((Horse)event.getEntity().m_20202_())
            && event.getSource() == DamageSource.f_19315_
            && UpgradedNetheriteConfig.EnableReduceFallDamage) {
            event.setAmount(event.getAmount() / 2.0F);
         } else if (event.getEntity() instanceof Horse
            && FeatherUtil.isHorseWearingFeatherArmor((Horse)event.getEntity())
            && event.getSource() == DamageSource.f_19315_
            && UpgradedNetheriteConfig.EnableReduceFallDamage) {
            event.setAmount(event.getAmount() / 2.0F);
         }

         if (event.getEntity() instanceof Horse
            && "sonic_boom".equals(event.getSource().f_19326_)
            && EchoUtil.isHorseWearingEchoArmor((Horse)event.getEntity())
            && UpgradedNetheriteConfig.EnableReduceDamageEchoArmor) {
            float reduceDamage = (float)UpgradedNetheriteConfig.ReduceDamageEchoArmor;
            event.setAmount(event.getAmount() - event.getAmount() * Math.min(1.0F, reduceDamage / 100.0F));
         }
      }
   }

   @SubscribeEvent(
      receiveCanceled = true,
      priority = EventPriority.LOWEST
   )
   public static void onLooting(LootingLevelEvent event) {
      if (event.getDamageSource() != null && event.getDamageSource().m_7639_() instanceof ServerPlayer) {
         ServerPlayer player = (ServerPlayer)event.getDamageSource().m_7639_();
         if (player.m_20202_() instanceof Horse) {
            Horse horse = (Horse)player.m_20202_();
            if (GoldUtil.isHorseWearingGoldArmor(horse) && UpgradedNetheriteConfig.EnableLootingBonus) {
               event.setLootingLevel(event.getLootingLevel() + 1);
            }
         }
      }
   }

   private Multimap<Attribute, AttributeModifier> SwimHorseAttributeMap() {
      Multimap<Attribute, AttributeModifier> attributesDefault = HashMultimap.create();
      attributesDefault.put(
         (Attribute)ForgeMod.SWIM_SPEED.get(),
         new AttributeModifier(UUID.fromString("fbfd69fe-3369-11eb-adc1-0242ac120002"), "upgradednetherite:swim_bonus", 1.5, Operation.MULTIPLY_BASE)
      );
      return attributesDefault;
   }

   @SubscribeEvent
   public void onLivingUpdate(LivingTickEvent event) {
      if (event.getEntity() instanceof Horse && ((Horse)event.getEntity()).m_30614_()) {
         Horse horse = (Horse)event.getEntity();
         Player rider = null;
         if (!horse.m_20197_().isEmpty()) {
            for (Entity entity : horse.m_20197_()) {
               if (entity instanceof Player) {
                  rider = (Player)entity;
               }
            }
         }

         if (horse.m_20077_() && FireUtil.isHorseWearingFireArmor(horse) && UpgradedNetheriteConfig.EnableLavaSpeed) {
            horse.m_20256_(horse.m_20184_().m_82542_(1.66, 1.0, 1.66));
         }

         if (EnderUtil.isHorseWearingEnderArmor(horse) && UpgradedNetheriteConfig.EnableVoidSave) {
            BlockPos blockpos1 = horse.m_20183_().m_7495_();
            BlockState blockstate = horse.f_19853_.m_8055_(blockpos1);
            if (blockstate.m_60767_().m_76334_()) {
               EntityDataUtil.setAbilityEnderPos(horse, true);
            }
         } else if (!EnderUtil.isHorseWearingEnderArmor(horse) && EntityDataUtil.getAbilityEnderPos(horse) != null) {
            EntityDataUtil.setAbilityEnderPos(horse, false);
         }

         if (WaterUtil.isHorseWearingWaterArmor(horse) && UpgradedNetheriteConfig.EnableWaterSpeed) {
            horse.m_21204_().m_22178_(this.SwimHorseAttributeMap());
         } else if (!WaterUtil.isHorseWearingWaterArmor(horse)) {
            horse.m_21204_().m_22161_(this.SwimHorseAttributeMap());
         }

         if (PoisonUtil.isHorseWearingPoisonArmor(horse) && UpgradedNetheriteConfig.EnableClimbWall) {
            if (rider != null && EntityDataUtil.getAbilityClimbwall(horse)) {
               if (horse.m_20184_().m_7098_() < 0.0) {
                  horse.m_6853_(true);
                  horse.f_19789_ = 0.0F;
                  horse.m_20256_(horse.m_20184_().m_82520_(-horse.m_20184_().m_7096_() / 10.0, -horse.m_20184_().m_7098_(), -horse.m_20184_().m_7094_() / 10.0));
               }

               if (horse.m_20184_().m_7096_() != 0.0 && horse.m_20184_().m_7094_() != 0.0) {
                  EntityDataUtil.setAbilityClimbwall(horse, false);
               }
            }

            if (rider != null && horse.f_19862_ && !horse.m_20077_() && !horse.m_20069_()) {
               Double LookAt = horse.m_20154_().f_82480_;
               if (LookAt > 0.1) {
                  LookAt = 0.1;
               }

               if (LookAt < -0.1) {
                  LookAt = -0.1;
               }

               if (rider.f_19853_.f_46443_ && ((LocalPlayer)rider).f_108618_.f_108567_ < 0.0F) {
                  LookAt = LookAt * -1.0;
               }

               horse.f_19789_ = 0.0F;
               horse.m_20256_(
                  horse.m_20184_().m_82520_(-horse.m_20184_().m_7096_() / 10.0, LookAt - horse.m_20184_().f_82480_, -horse.m_20184_().m_7094_() / 10.0)
               );
               if (horse.f_19853_.f_46443_) {
                  EntityFallDistanceUpdateHandler.EntityFallDistanceUpdate(horse.m_19879_(), horse.f_19789_);
               }

               EntityDataUtil.setAbilityClimbwall(horse, true);
            }
         } else if (!PoisonUtil.isHorseWearingPoisonArmor(horse) && EntityDataUtil.getAbilityClimbwall(horse)) {
            EntityDataUtil.setAbilityClimbwall(horse, false);
         }

         if (horse.m_20146_() < horse.m_6062_() && WaterUtil.isHorseWearingWaterArmor(horse) && UpgradedNetheriteConfig.EnableWaterBreath) {
            horse.m_20301_(horse.m_20146_() + 1);
         }

         if (horse.m_21023_(MobEffects.f_19615_) && WitherUtil.isHorseWearingWitherArmor(horse) && UpgradedNetheriteConfig.EnableWitherImmune) {
            horse.m_21195_(MobEffects.f_19615_);
         }

         if (horse.m_21023_(MobEffects.f_19614_) && PoisonUtil.isHorseWearingPoisonArmor(horse) && UpgradedNetheriteConfig.EnablePoisonImmune) {
            horse.m_21195_(MobEffects.f_19614_);
         }

         if ((horse.f_19853_.m_6425_(horse.m_20183_()).m_205070_(FluidTags.f_13132_) || horse.f_19853_.m_6425_(horse.m_20183_()).m_205070_(FluidTags.f_13131_))
            && FeatherUtil.isHorseWearingFeatherArmor(horse)
            && UpgradedNetheriteConfig.EnableWaterLavaWalking) {
            if (!horse.m_20069_() && !horse.m_20077_()) {
               horse.m_6853_(true);
               horse.m_6862_(false);
               horse.m_20256_(horse.m_20184_().m_82520_(0.0, -horse.m_20184_().m_7098_(), 0.0));
            } else if (!(horse.m_20184_().f_82480_ < 0.3) || !horse.m_204029_(FluidTags.f_13132_) && !horse.m_204029_(FluidTags.f_13131_)) {
               if (horse.m_20184_().f_82480_ < 0.15 && !horse.m_204029_(FluidTags.f_13132_) && !horse.m_204029_(FluidTags.f_13131_)) {
                  horse.m_20256_(horse.m_20184_().m_82520_(0.0, 0.15 - horse.m_20184_().f_82480_, 0.0));
               }
            } else {
               horse.m_20256_(horse.m_20184_().m_82520_(0.0, 0.3 - horse.m_20184_().f_82480_, 0.0));
            }
         }

         if (EchoUtil.isHorseWearingEchoArmor(horse)
            && UpgradedNetheriteConfig.EnableHealEchoArmor
            && !EntityDataUtil.hasEchoHealCooldownHorse(horse)
            && horse.m_21223_() < horse.m_21233_()) {
            horse.getPersistentData().m_128405_("upgraded_netherite_echo_heal_cd", UpgradedNetheriteConfig.HealEchoArmorDelay * 20);
            horse.m_5634_(1.0F);
         }

         EntityDataUtil.tickCooldownHorse(horse);
      }

      if (event.getEntity() instanceof Player) {
         Player player = (Player)event.getEntity();
         if (player.m_20202_() instanceof Horse && player.f_19853_.f_46443_) {
            Horse horsex = (Horse)player.m_20202_();
            if ((
                  WaterUtil.isHorseWearingWaterArmor(horsex) && horsex.f_19853_.m_6425_(player.m_20183_()).m_205070_(FluidTags.f_13131_)
                     || FireUtil.isHorseWearingFireArmor(horsex) && horsex.f_19853_.m_6425_(player.m_20183_()).m_205070_(FluidTags.f_13132_)
               )
               && player.f_19853_.f_46443_
               && player instanceof LocalPlayer
               && ((LocalPlayer)player).f_108618_.f_108567_ != 0.0F) {
               double d3 = player.m_20154_().f_82480_;
               double d4 = d3 < -0.2 ? 0.085 : 0.06;
               double d5;
               if (player.f_19853_.f_46443_ && player instanceof LocalPlayer && ((LocalPlayer)player).f_108618_.f_108567_ > 0.0F) {
                  d5 = (d3 - horsex.m_20184_().f_82480_) * d4;
               } else {
                  d5 = -((d3 - horsex.m_20184_().f_82480_) * d4) / 4.0;
               }

               horsex.m_20256_(horsex.m_20184_().m_82520_(0.0, d5, 0.0));
            }
         }
      }
   }

   @SubscribeEvent(
      receiveCanceled = true,
      priority = EventPriority.HIGHEST
   )
   public void onLivingAttackEvent(LivingAttackEvent event) {
      if (event.getEntity() instanceof Player
         && event.getEntity().m_20202_() instanceof Horse
         && ((Horse)event.getEntity().m_20202_()).m_30614_()
         && PhantomUtil.isHorseWearingPhantomArmor((Horse)event.getEntity().m_20202_())
         && event.isCancelable()) {
         event.setCanceled(true);
      }

      if (event.getEntity() instanceof Horse && ((Horse)event.getEntity()).m_30614_()) {
         Horse horse = (Horse)event.getEntity();
         if (event.getSource().m_19384_() && FireUtil.isHorseWearingFireArmor(horse) && UpgradedNetheriteConfig.EnableFireImmune) {
            if (event.isCancelable()) {
               event.setCanceled(true);
            }

            horse.m_20095_();
         } else if (event.getSource() != DamageSource.f_19315_ || !PhantomUtil.isHorseWearingPhantomArmor(horse) || !UpgradedNetheriteConfig.EnableFallImmune) {
            if (event.getSource() == DamageSource.f_19312_ && WaterUtil.isHorseWearingWaterArmor(horse) && UpgradedNetheriteConfig.EnableWaterBreath) {
               if (event.isCancelable()) {
                  event.setCanceled(true);
               }

               horse.m_20301_(horse.m_6062_());
            } else if (event.getSource() == DamageSource.f_19317_
               && EnderUtil.isHorseWearingEnderArmor(horse)
               && UpgradedNetheriteConfig.EnableVoidSave
               && horse.m_20186_() < -63.0) {
               if (event.isCancelable()) {
                  event.setCanceled(true);
               }

               Level world = horse.f_19853_;
               if (!horse.m_20197_().isEmpty()) {
                  horse.m_20153_();
               }

               horse.f_19789_ = 0.0F;
               Boolean validtp = false;
               if (EntityDataUtil.getAbilityEnderPos(horse) != null) {
                  if (horse.m_20984_(
                     (double)horse.getPersistentData().m_128465_("upgraded_netherite_ender_pos")[0] + 0.5,
                     (double)horse.getPersistentData().m_128465_("upgraded_netherite_ender_pos")[1],
                     (double)horse.getPersistentData().m_128465_("upgraded_netherite_ender_pos")[2] + 0.5,
                     true
                  )) {
                     SoundEvent soundevent = SoundEvents.f_11852_;
                     horse.f_19853_
                        .m_6263_(
                           null,
                           (double)horse.getPersistentData().m_128465_("upgraded_netherite_ender_pos")[0] + 0.5,
                           (double)horse.getPersistentData().m_128465_("upgraded_netherite_ender_pos")[1],
                           (double)horse.getPersistentData().m_128465_("upgraded_netherite_ender_pos")[2] + 0.5,
                           soundevent,
                           SoundSource.PLAYERS,
                           1.0F,
                           1.0F
                        );
                     horse.m_5496_(soundevent, 1.0F, 1.0F);
                     validtp = true;
                  } else {
                     BlockPos blockpos = EntityDataUtil.getAbilityEnderPos(horse);
                     List<BlockPos> validTpList = new ArrayList<>();

                     for (BlockPos blockpos1 : BlockPos.m_121940_(blockpos.m_7918_(-10, -10, -10), blockpos.m_7918_(10, 10, 10))) {
                        if (world.m_8055_(blockpos1.m_7495_()).m_60767_().m_76334_()
                           && (horse.f_19853_.m_6425_(blockpos1).m_76178_() || horse.f_19853_.m_8055_(blockpos1).m_60713_(Blocks.f_50628_))
                           && horse.f_19853_.m_8055_(blockpos1).m_60647_(horse.f_19853_, blockpos1, PathComputationType.LAND)
                           && (
                              horse.f_19853_.m_6425_(blockpos1.m_7918_(-1, 1, 0)).m_76178_()
                                 || horse.f_19853_.m_8055_(blockpos1.m_7918_(-1, 1, 0)).m_60713_(Blocks.f_50628_)
                           )
                           && horse.f_19853_
                              .m_8055_(blockpos1.m_7918_(-1, 1, 0))
                              .m_60647_(horse.f_19853_, blockpos1.m_7918_(-1, 1, 0), PathComputationType.LAND)
                           && (
                              horse.f_19853_.m_6425_(blockpos1.m_7918_(-1, 1, -1)).m_76178_()
                                 || horse.f_19853_.m_8055_(blockpos1.m_7918_(-1, 1, -1)).m_60713_(Blocks.f_50628_)
                           )
                           && horse.f_19853_
                              .m_8055_(blockpos1.m_7918_(-1, 1, -1))
                              .m_60647_(horse.f_19853_, blockpos1.m_7918_(-1, 1, -1), PathComputationType.LAND)
                           && (
                              horse.f_19853_.m_6425_(blockpos1.m_7918_(-1, 1, 1)).m_76178_()
                                 || horse.f_19853_.m_8055_(blockpos1.m_7918_(-1, 1, 1)).m_60713_(Blocks.f_50628_)
                           )
                           && horse.f_19853_
                              .m_8055_(blockpos1.m_7918_(-1, 1, 1))
                              .m_60647_(horse.f_19853_, blockpos1.m_7918_(-1, 1, 1), PathComputationType.LAND)
                           && (
                              horse.f_19853_.m_6425_(blockpos1.m_7918_(0, 1, 0)).m_76178_()
                                 || horse.f_19853_.m_8055_(blockpos1.m_7918_(0, 1, 0)).m_60713_(Blocks.f_50628_)
                           )
                           && horse.f_19853_.m_8055_(blockpos1.m_7918_(0, 1, 0)).m_60647_(horse.f_19853_, blockpos1.m_7918_(0, 1, 0), PathComputationType.LAND)
                           && (
                              horse.f_19853_.m_6425_(blockpos1.m_7918_(0, 1, -1)).m_76178_()
                                 || horse.f_19853_.m_8055_(blockpos1.m_7918_(0, 1, -1)).m_60713_(Blocks.f_50628_)
                           )
                           && horse.f_19853_
                              .m_8055_(blockpos1.m_7918_(0, 1, -1))
                              .m_60647_(horse.f_19853_, blockpos1.m_7918_(0, 1, -1), PathComputationType.LAND)
                           && (
                              horse.f_19853_.m_6425_(blockpos1.m_7918_(0, 1, 1)).m_76178_()
                                 || horse.f_19853_.m_8055_(blockpos1.m_7918_(0, 1, 1)).m_60713_(Blocks.f_50628_)
                           )
                           && horse.f_19853_.m_8055_(blockpos1.m_7918_(0, 1, 1)).m_60647_(horse.f_19853_, blockpos1.m_7918_(0, 1, 1), PathComputationType.LAND)
                           && (
                              horse.f_19853_.m_6425_(blockpos1.m_7918_(1, 1, 0)).m_76178_()
                                 || horse.f_19853_.m_8055_(blockpos1.m_7918_(1, 1, 0)).m_60713_(Blocks.f_50628_)
                           )
                           && horse.f_19853_.m_8055_(blockpos1.m_7918_(1, 1, 0)).m_60647_(horse.f_19853_, blockpos1.m_7918_(1, 1, 0), PathComputationType.LAND)
                           && (
                              horse.f_19853_.m_6425_(blockpos1.m_7918_(1, 1, -1)).m_76178_()
                                 || horse.f_19853_.m_8055_(blockpos1.m_7918_(1, 1, -1)).m_60713_(Blocks.f_50628_)
                           )
                           && horse.f_19853_
                              .m_8055_(blockpos1.m_7918_(1, 1, -1))
                              .m_60647_(horse.f_19853_, blockpos1.m_7918_(1, 1, -1), PathComputationType.LAND)
                           && (
                              horse.f_19853_.m_6425_(blockpos1.m_7918_(1, 1, 1)).m_76178_()
                                 || horse.f_19853_.m_8055_(blockpos1.m_7918_(1, 1, 1)).m_60713_(Blocks.f_50628_)
                           )
                           && horse.f_19853_.m_8055_(blockpos1.m_7918_(1, 1, 1)).m_60647_(horse.f_19853_, blockpos1.m_7918_(1, 1, 1), PathComputationType.LAND)
                           )
                         {
                           validTpList.add(blockpos1.m_7949_());
                        }
                     }

                     if (validTpList.size() > 0) {
                        Integer IRNG = horse.m_217043_().m_188503_(validTpList.size());
                        horse.m_6021_(
                           (double)validTpList.get(IRNG).m_123341_() + 0.5,
                           (double)validTpList.get(IRNG).m_123342_(),
                           (double)validTpList.get(IRNG).m_123343_() + 0.5
                        );
                        SoundEvent soundevent = SoundEvents.f_11852_;
                        horse.f_19853_
                           .m_6263_(
                              null,
                              (double)validTpList.get(IRNG).m_123341_() + 0.5,
                              (double)validTpList.get(IRNG).m_123342_(),
                              (double)validTpList.get(IRNG).m_123343_() + 0.5,
                              soundevent,
                              SoundSource.PLAYERS,
                              1.0F,
                              1.0F
                           );
                        horse.m_5496_(soundevent, 1.0F, 1.0F);
                        validtp = true;
                     }
                  }
               }

               if (!validtp) {
                  BlockPos blockpos = new BlockPos(100.5, 49.0, 0.5);

                  for (BlockPos blockpos1x : BlockPos.m_121940_(blockpos.m_7918_(-1, -1, -1), blockpos.m_7918_(1, -1, 1))) {
                     world.m_46597_(blockpos1x, Blocks.f_50080_.m_49966_());
                  }

                  for (BlockPos blockpos1x : BlockPos.m_121940_(blockpos.m_7918_(-1, 0, -1), blockpos.m_7918_(1, 1, 1))) {
                     world.m_46597_(blockpos1x, Blocks.f_50016_.m_49966_());
                  }

                  horse.m_6021_(100.5, 49.0, 0.5);
                  SoundEvent soundevent = SoundEvents.f_11852_;
                  horse.f_19853_.m_6263_(null, 100.5, 49.0, 0.5, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                  horse.m_5496_(soundevent, 1.0F, 1.0F);
               }
            }
         } else if (event.isCancelable()) {
            event.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public void onJump(LivingJumpEvent event) {
      if (event.getEntity() instanceof Horse && ((Horse)event.getEntity()).m_30614_()) {
         Horse horse = (Horse)event.getEntity();
         EntityDataUtil.setAbilityClimbwall(horse, false);
      }
   }

   @SubscribeEvent(
      receiveCanceled = true,
      priority = EventPriority.HIGHEST
   )
   public void onApplyEffect(Added event) {
      if (event.getEntity() instanceof Horse && ((Horse)event.getEntity()).m_30614_()) {
         Horse horse = (Horse)event.getEntity();
         if (event.getEffectInstance().m_19544_() == MobEffects.f_19615_
            && WitherUtil.isHorseWearingWitherArmor(horse)
            && UpgradedNetheriteConfig.EnableWitherImmune) {
            event.setResult(Result.DENY);
            if (event.isCancelable()) {
               event.setCanceled(true);
            }
         } else if (event.getEffectInstance().m_19544_() == MobEffects.f_19614_
            && PoisonUtil.isHorseWearingPoisonArmor(horse)
            && UpgradedNetheriteConfig.EnablePoisonImmune) {
            event.setResult(Result.DENY);
            if (event.isCancelable()) {
               event.setCanceled(true);
            }
         }
      }
   }

   @SubscribeEvent
   public void onUnmount(EntityMountEvent event) {
      if (event.isDismounting()
         && event.getEntity() instanceof Player
         && !event.getEntity().f_19853_.m_5776_()
         && !event.getEntity().m_20164_()
         && event.getEntity().m_20202_() instanceof Horse) {
         Horse horse = (Horse)event.getEntity().m_20202_();
         if (horse.m_30614_()
            && horse.m_204029_(FluidTags.f_13131_)
            && WaterUtil.isHorseWearingWaterArmor(horse)
            && (UpgradedNetheriteConfig.EnableWaterBreath || UpgradedNetheriteConfig.EnableWaterSpeed)) {
            event.setCanceled(true);
         }
      }
   }
}
