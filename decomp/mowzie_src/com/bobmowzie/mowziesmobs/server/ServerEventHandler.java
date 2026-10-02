package com.bobmowzie.mowziesmobs.server;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.ParticleVanillaCloudExtended;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleData;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleRotation;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ai.AvoidEntityIfNotTamedGoal;
import com.bobmowzie.mowziesmobs.server.capability.AbilityCapability;
import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.FrozenCapability;
import com.bobmowzie.mowziesmobs.server.capability.LivingCapability;
import com.bobmowzie.mowziesmobs.server.capability.PlayerCapability;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.LeaderSunstrikeImmune;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import com.bobmowzie.mowziesmobs.server.entity.foliaath.EntityFoliaath;
import com.bobmowzie.mowziesmobs.server.entity.frostmaw.EntityFrostmaw;
import com.bobmowzie.mowziesmobs.server.entity.naga.EntityNaga;
import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthana;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaCrane;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaFollowerToPlayer;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaMinion;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.MaskType;
import com.bobmowzie.mowziesmobs.server.entity.wroughtnaut.EntityWroughtnaut;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.item.ItemNagaFangDagger;
import com.bobmowzie.mowziesmobs.server.item.ItemSpear;
import com.bobmowzie.mowziesmobs.server.item.ItemUmvuthanaMask;
import com.bobmowzie.mowziesmobs.server.message.MessageFreezeEffect;
import com.bobmowzie.mowziesmobs.server.message.MessagePlayerAttackMob;
import com.bobmowzie.mowziesmobs.server.message.MessageSunblockEffect;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.bobmowzie.mowziesmobs.server.power.Power;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent.Added;
import net.minecraftforge.event.entity.living.MobEffectEvent.Expired;
import net.minecraftforge.event.entity.living.MobEffectEvent.Remove;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import net.minecraftforge.event.level.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.PacketDistributor;

public final class ServerEventHandler {
   @SubscribeEvent
   public void onJoinWorld(EntityJoinLevelEvent event) {
      if (event.getEntity() instanceof Player || event.getEntity() instanceof MowzieGeckoEntity) {
         AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability((LivingEntity)event.getEntity());
         if (abilityCapability != null) {
            abilityCapability.instanceAbilities((LivingEntity)event.getEntity());
         }
      }

      if (event.getEntity() instanceof Player) {
         PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability((Player)event.getEntity(), CapabilityHandler.PLAYER_CAPABILITY);
         if (playerCapability != null) {
            playerCapability.addedToWorld(event);
         }
      }

      if (!event.getLevel().f_46443_) {
         Entity entity = event.getEntity();
         if (entity instanceof Zombie && !(entity instanceof ZombifiedPiglin)) {
            ((PathfinderMob)entity).f_21346_.m_25352_(2, new NearestAttackableTargetGoal((PathfinderMob)entity, EntityFoliaath.class, 0, true, false, null));
            ((PathfinderMob)entity).f_21346_.m_25352_(3, new NearestAttackableTargetGoal((PathfinderMob)entity, EntityUmvuthana.class, 0, true, false, null));
            ((PathfinderMob)entity).f_21346_.m_25352_(2, new NearestAttackableTargetGoal((PathfinderMob)entity, EntityUmvuthi.class, 0, true, false, null));
         }

         if (entity instanceof AbstractSkeleton) {
            ((PathfinderMob)entity).f_21346_.m_25352_(3, new NearestAttackableTargetGoal((PathfinderMob)entity, EntityUmvuthana.class, 0, true, false, null));
            ((PathfinderMob)entity).f_21346_.m_25352_(2, new NearestAttackableTargetGoal((PathfinderMob)entity, EntityUmvuthi.class, 0, true, false, null));
         }

         if (entity instanceof Parrot) {
            ((PathfinderMob)entity).f_21345_.m_25352_(3, new AvoidEntityGoal((PathfinderMob)entity, EntityFoliaath.class, 6.0F, 1.0, 1.2));
         }

         if (entity instanceof Animal) {
            ((PathfinderMob)entity).f_21345_.m_25352_(3, new AvoidEntityIfNotTamedGoal((PathfinderMob)entity, EntityFoliaath.class, 6.0F, 1.0, 1.2));
            ((PathfinderMob)entity).f_21345_.m_25352_(3, new AvoidEntityIfNotTamedGoal((PathfinderMob)entity, EntityUmvuthana.class, 6.0F, 1.0, 1.2));
            ((PathfinderMob)entity).f_21345_.m_25352_(3, new AvoidEntityIfNotTamedGoal((PathfinderMob)entity, EntityUmvuthi.class, 6.0F, 1.0, 1.2));
            ((PathfinderMob)entity).f_21345_.m_25352_(3, new AvoidEntityIfNotTamedGoal((PathfinderMob)entity, EntityNaga.class, 10.0F, 1.0, 1.2));
            ((PathfinderMob)entity).f_21345_.m_25352_(3, new AvoidEntityIfNotTamedGoal((PathfinderMob)entity, EntityFrostmaw.class, 10.0F, 1.0, 1.2));
         }

         if (entity instanceof AbstractVillager) {
            ((PathfinderMob)entity).f_21345_.m_25352_(3, new AvoidEntityGoal((PathfinderMob)entity, EntityUmvuthana.class, 6.0F, 1.0, 1.2));
            ((PathfinderMob)entity).f_21345_.m_25352_(3, new AvoidEntityGoal((PathfinderMob)entity, EntityUmvuthi.class, 6.0F, 1.0, 1.2));
            ((PathfinderMob)entity).f_21345_.m_25352_(3, new AvoidEntityGoal((PathfinderMob)entity, EntityNaga.class, 10.0F, 1.0, 1.2));
            ((PathfinderMob)entity).f_21345_.m_25352_(3, new AvoidEntityGoal((PathfinderMob)entity, EntityFrostmaw.class, 10.0F, 1.0, 1.2));
         }
      }
   }

   @SubscribeEvent
   public void onLivingTick(LivingTickEvent event) {
      if (event.getEntity() instanceof LivingEntity) {
         LivingEntity entity = event.getEntity();
         if (entity.m_21124_((MobEffect)EffectHandler.POISON_RESIST.get()) != null && entity.m_21124_(MobEffects.f_19614_) != null) {
            entity.m_6234_(MobEffects.f_19614_);
         }

         if (!entity.f_19853_.f_46443_ && entity.m_6844_(EquipmentSlot.HEAD).m_41720_() instanceof ItemUmvuthanaMask mask) {
            EffectHandler.addOrCombineEffect(entity, mask.getPotion(), 50, 0, true, false);
         }

         if (entity instanceof Mob && !(entity instanceof EntityUmvuthanaCrane)) {
            Mob mob = (Mob)entity;
            if (mob.m_5448_() instanceof EntityUmvuthi && mob.m_5448_().m_21023_((MobEffect)EffectHandler.SUNBLOCK.get())) {
               EntityUmvuthanaCrane sunblocker = (EntityUmvuthanaCrane)mob.f_19853_
                  .m_45963_(
                     EntityUmvuthanaCrane.class,
                     TargetingConditions.f_26872_,
                     mob,
                     mob.m_20185_(),
                     mob.m_20186_() + (double)mob.m_20192_(),
                     mob.m_20189_(),
                     mob.m_20191_().m_82377_(40.0, 15.0, 40.0)
                  );
               mob.m_6710_(sunblocker);
            }
         }

         FrozenCapability.IFrozenCapability frozenCapability = CapabilityHandler.getCapability(entity, CapabilityHandler.FROZEN_CAPABILITY);
         if (frozenCapability != null) {
            frozenCapability.tick(entity);
         }

         LivingCapability.ILivingCapability livingCapability = CapabilityHandler.getCapability(entity, CapabilityHandler.LIVING_CAPABILITY);
         if (livingCapability != null) {
            livingCapability.tick(entity);
         }

         AbilityCapability.IAbilityCapability abilityCapability = CapabilityHandler.getCapability(entity, CapabilityHandler.ABILITY_CAPABILITY);
         if (abilityCapability != null) {
            abilityCapability.tick(entity);
         }
      }
   }

   @SubscribeEvent
   public void onAddPotionEffect(Added event) {
      if (event.getEffectInstance().m_19544_() == EffectHandler.SUNBLOCK.get()) {
         if (!event.getEntity().f_19853_.m_5776_()) {
            MowziesMobs.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(event::getEntity), new MessageSunblockEffect(event.getEntity(), true));
         }

         MowziesMobs.PROXY.playSunblockSound(event.getEntity());
      }

      if (event.getEffectInstance().m_19544_() == EffectHandler.FROZEN.get() && !event.getEntity().f_19853_.m_5776_()) {
         MowziesMobs.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(event::getEntity), new MessageFreezeEffect(event.getEntity(), true));
         FrozenCapability.IFrozenCapability frozenCapability = CapabilityHandler.getCapability(event.getEntity(), CapabilityHandler.FROZEN_CAPABILITY);
         if (frozenCapability != null) {
            frozenCapability.onFreeze(event.getEntity());
         }
      }
   }

   @SubscribeEvent
   public void onRemovePotionEffect(Remove event) {
      if (event.getEffectInstance() != null) {
         if (!event.getEntity().f_19853_.m_5776_() && event.getEffectInstance().m_19544_() == EffectHandler.SUNBLOCK.get()) {
            MowziesMobs.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(event::getEntity), new MessageSunblockEffect(event.getEntity(), false));
         }

         if (!event.getEntity().f_19853_.m_5776_() && event.getEffectInstance().m_19544_() == EffectHandler.FROZEN.get()) {
            MowziesMobs.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(event::getEntity), new MessageFreezeEffect(event.getEntity(), false));
            FrozenCapability.IFrozenCapability frozenCapability = CapabilityHandler.getCapability(event.getEntity(), CapabilityHandler.FROZEN_CAPABILITY);
            if (frozenCapability != null) {
               frozenCapability.onUnfreeze(event.getEntity());
            }
         }
      }
   }

   @SubscribeEvent
   public void onPotionEffectExpire(Expired event) {
      MobEffectInstance effectInstance = event.getEffectInstance();
      if (!event.getEntity().f_19853_.m_5776_() && effectInstance != null && effectInstance.m_19544_() == EffectHandler.SUNBLOCK.get()) {
         MowziesMobs.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(event::getEntity), new MessageSunblockEffect(event.getEntity(), false));
      }

      if (!event.getEntity().f_19853_.m_5776_() && effectInstance != null && effectInstance.m_19544_() == EffectHandler.FROZEN.get()) {
         MowziesMobs.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(event::getEntity), new MessageFreezeEffect(event.getEntity(), false));
         FrozenCapability.IFrozenCapability frozenCapability = CapabilityHandler.getCapability(event.getEntity(), CapabilityHandler.FROZEN_CAPABILITY);
         if (frozenCapability != null) {
            frozenCapability.onUnfreeze(event.getEntity());
         }
      }
   }

   @SubscribeEvent
   public void onLivingHurt(LivingHurtEvent event) {
      DamageSource source = event.getSource();
      LivingEntity livingEntity = event.getEntity();
      if (source != null && livingEntity != null) {
         float damage = event.getAmount();
         if (!source.m_19379_() && livingEntity.m_21023_((MobEffect)EffectHandler.SUNBLOCK.get()) && source != DamageSource.f_19317_) {
            int i = (livingEntity.m_21124_((MobEffect)EffectHandler.SUNBLOCK.get()).m_19564_() + 2) * 5;
            int j = 25 - i;
            float f = damage * (float)j;
            float var11 = Math.max(f / 25.0F, 0.0F);
            float f2 = damage - var11;
            if (f2 > 0.0F && f2 < 3.4028235E37F) {
               if (livingEntity instanceof ServerPlayer) {
                  ((ServerPlayer)livingEntity).m_36222_(Stats.f_12934_, Math.round(f2 * 10.0F));
               } else if (source.m_7639_() instanceof ServerPlayer) {
                  ((ServerPlayer)source.m_7639_()).m_36222_(Stats.f_12930_, Math.round(f2 * 10.0F));
               }
            }
         }

         if (event.getSource().m_19384_()) {
            event.getEntity().m_6234_((MobEffect)EffectHandler.FROZEN.get());
            MowziesMobs.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(event::getEntity), new MessageFreezeEffect(event.getEntity(), false));
            FrozenCapability.IFrozenCapability frozenCapability = CapabilityHandler.getCapability(event.getEntity(), CapabilityHandler.FROZEN_CAPABILITY);
            if (frozenCapability != null) {
               frozenCapability.onUnfreeze(event.getEntity());
            }
         }

         if (event.getEntity() instanceof Player) {
            PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(event.getEntity(), CapabilityHandler.PLAYER_CAPABILITY);
            if (playerCapability != null) {
               Power[] powers = playerCapability.getPowers();

               for (Power power : powers) {
                  power.onTakeDamage(event);
               }
            }
         }

         if (event.getEntity() != null) {
            LivingEntity living = event.getEntity();
            LivingCapability.ILivingCapability capability = CapabilityHandler.getCapability(living, CapabilityHandler.LIVING_CAPABILITY);
            if (capability != null) {
               capability.setLastDamage(event.getAmount());
            }
         }
      }
   }

   @SubscribeEvent
   public void onPlayerTick(PlayerTickEvent event) {
      if (event.phase != Phase.START && event.player != null) {
         Player player = event.player;
         PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(player, CapabilityHandler.PLAYER_CAPABILITY);
         if (playerCapability != null) {
            playerCapability.tick(event);
            Power[] powers = playerCapability.getPowers();

            for (Power power : powers) {
               power.tick(event);
            }
         }
      }
   }

   @SubscribeEvent
   public void onUseItem(LivingEntityUseItemEvent event) {
      LivingEntity living = event.getEntity();
      if (event.isCancelable() && living.m_21023_((MobEffect)EffectHandler.FROZEN.get())) {
         event.setCanceled(true);
      } else {
         AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(living);
         if (abilityCapability != null && event.isCancelable() && abilityCapability.itemUsePrevented(event.getItem())) {
            event.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public void onPlaceBlock(EntityPlaceEvent event) {
      Entity entity = event.getEntity();
      if (entity instanceof LivingEntity living) {
         if (event.isCancelable() && living.m_21023_((MobEffect)EffectHandler.FROZEN.get())) {
            event.setCanceled(true);
            return;
         }

         AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(living);
         if (abilityCapability != null && event.isCancelable() && abilityCapability.blockBreakingBuildingPrevented()) {
            event.setCanceled(true);
            return;
         }

         if (entity instanceof Player) {
            this.cheatSculptor((Player)entity);
            BlockState block = event.getPlacedBlock();
            if (block.m_60734_() == Blocks.f_50083_
               || block.m_60734_() == Blocks.f_50077_
               || block.m_60734_() == Blocks.f_50724_
               || block.m_60734_() == Blocks.f_50061_
               || block.m_60734_() == Blocks.f_50128_) {
               this.aggroUmvuthana((Player)entity);
            }
         }
      }
   }

   @SubscribeEvent
   public void onFillBucket(FillBucketEvent event) {
      LivingEntity living = event.getEntity();
      if (living != null) {
         if (event.isCancelable() && living.m_21023_((MobEffect)EffectHandler.FROZEN.get())) {
            event.setCanceled(true);
            return;
         }

         AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(event.getEntity());
         if (abilityCapability != null && event.isCancelable() && abilityCapability.interactingPrevented()) {
            event.setCanceled(true);
            return;
         }

         if (event.getEmptyBucket().m_41720_() == Items.f_42448_) {
            this.aggroUmvuthana(event.getEntity());
         }

         if (event.getEmptyBucket().m_41720_() == Items.f_42447_) {
            this.cheatSculptor(event.getEntity());
         }
      }
   }

   @SubscribeEvent
   public void onBreakBlock(BreakEvent event) {
      if (event.isCancelable() && event.getPlayer().m_21023_((MobEffect)EffectHandler.FROZEN.get())) {
         event.setCanceled(true);
      } else {
         AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(event.getPlayer());
         if (abilityCapability != null && event.isCancelable() && abilityCapability.blockBreakingBuildingPrevented()) {
            event.setCanceled(true);
         } else {
            this.cheatSculptor(event.getPlayer());
            BlockState block = event.getState();
            if (block.m_60734_() == Blocks.f_50074_
               || block.m_60767_() == Material.f_76320_
               || block.m_204336_(BlockTags.f_13035_)
               || block.m_60734_() == Blocks.f_50295_
               || block.m_60734_() == Blocks.f_50301_
               || block.m_60734_() == Blocks.f_50644_
               || block.m_60734_() == Blocks.f_50473_
               || block.m_60734_() == Blocks.f_50630_
               || block.m_60734_() == Blocks.f_50683_
               || block.m_60734_() == Blocks.f_50183_
               || block.m_60734_() == Blocks.f_50310_
               || block.m_60734_() == Blocks.f_50081_) {
               this.aggroUmvuthana(event.getPlayer());
            }
         }
      }
   }

   public <T extends Entity> List<T> getEntitiesNearby(Entity startEntity, Class<T> entityClass, double r) {
      return startEntity.f_19853_.m_6443_(entityClass, startEntity.m_20191_().m_82377_(r, r, r), e -> e != startEntity && (double)startEntity.m_20270_(e) <= r);
   }

   private List<LivingEntity> getEntityBaseNearby(LivingEntity user, double distanceX, double distanceY, double distanceZ, double radius) {
      List<Entity> list = user.f_19853_.m_45933_(user, user.m_20191_().m_82377_(distanceX, distanceY, distanceZ));
      return list.stream()
         .filter(entityNeighbor -> entityNeighbor instanceof LivingEntity && (double)user.m_20270_(entityNeighbor) <= radius)
         .map(entityNeighbor -> (LivingEntity)entityNeighbor)
         .collect(Collectors.toCollection(ArrayList::new));
   }

   @SubscribeEvent
   public void onPlayerInteract(RightClickEmpty event) {
      if (event.isCancelable() && event.getEntity().m_21023_((MobEffect)EffectHandler.FROZEN.get())) {
         event.setCanceled(true);
      } else {
         AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(event.getEntity());
         if (abilityCapability != null && event.isCancelable() && abilityCapability.interactingPrevented()) {
            event.setCanceled(true);
         } else {
            Player player = event.getEntity();
            PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(player, CapabilityHandler.PLAYER_CAPABILITY);
            if (playerCapability != null) {
               if (event.getLevel().f_46443_ && player.m_150109_().m_36056_().m_41619_() && player.m_21023_((MobEffect)EffectHandler.SUNS_BLESSING.get())) {
                  if (player.m_6144_()) {
                     AbilityHandler.INSTANCE.sendPlayerTryAbilityMessage(event.getEntity(), AbilityHandler.SOLAR_BEAM_ABILITY);
                  } else {
                     AbilityHandler.INSTANCE.sendPlayerTryAbilityMessage(event.getEntity(), AbilityHandler.SUNSTRIKE_ABILITY);
                  }
               }

               Power[] powers = playerCapability.getPowers();

               for (Power power : powers) {
                  power.onRightClickEmpty(event);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public void onPlayerInteract(EntityInteract event) {
      if (event.isCancelable() && event.getEntity().m_21023_((MobEffect)EffectHandler.FROZEN.get())) {
         event.setCanceled(true);
      } else {
         AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(event.getEntity());
         if (abilityCapability != null && event.isCancelable() && abilityCapability.interactingPrevented()) {
            event.setCanceled(true);
         } else {
            PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(event.getEntity(), CapabilityHandler.PLAYER_CAPABILITY);
            if (playerCapability != null) {
               Power[] powers = playerCapability.getPowers();

               for (Power power : powers) {
                  power.onRightClickEntity(event);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public void onPlayerInteract(RightClickBlock event) {
      if (event.isCancelable() && event.getEntity().m_21023_((MobEffect)EffectHandler.FROZEN.get())) {
         event.setCanceled(true);
      } else {
         AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(event.getEntity());
         if (abilityCapability != null && event.isCancelable() && abilityCapability.interactingPrevented()) {
            event.setCanceled(true);
         } else {
            Player player = event.getEntity();
            if (player.f_19853_.m_8055_(event.getPos()).m_60734_() instanceof ChestBlock) {
               this.aggroUmvuthana(player);
            }

            PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(player, CapabilityHandler.PLAYER_CAPABILITY);
            if (playerCapability != null) {
               ItemStack item = event.getItemStack();
               if (item.m_41720_() == Items.f_42409_ || item.m_41720_() == Items.f_42693_) {
                  this.aggroUmvuthana(player);
               }

               if (event.getSide() == LogicalSide.CLIENT
                  && player.m_150109_().m_36056_().m_41619_()
                  && player.m_21023_((MobEffect)EffectHandler.SUNS_BLESSING.get())) {
                  if (player.m_6144_()) {
                     AbilityHandler.INSTANCE.sendPlayerTryAbilityMessage(event.getEntity(), AbilityHandler.SOLAR_BEAM_ABILITY);
                  } else {
                     AbilityHandler.INSTANCE.sendPlayerTryAbilityMessage(event.getEntity(), AbilityHandler.SUNSTRIKE_ABILITY);
                  }
               }

               if (player.f_19853_.m_8055_(event.getPos()).m_60750_(player.f_19853_, event.getPos()) != null) {
                  player.m_36334_();
                  return;
               }

               Power[] powers = playerCapability.getPowers();

               for (Power power : powers) {
                  power.onRightClickBlock(event);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public void onPlayerLeftClick(LeftClickEmpty event) {
      double range = 6.5;
      Player player = event.getEntity();
      PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(player, CapabilityHandler.PLAYER_CAPABILITY);
      if (player.m_21205_() != null && player.m_21205_().m_41720_() == ItemHandler.SPEAR) {
         LivingEntity entityHit = ItemSpear.raytraceEntities(player.m_20193_(), player, range);
         if (entityHit != null) {
            MowziesMobs.NETWORK.sendToServer(new MessagePlayerAttackMob(entityHit));
         }
      }

      if (playerCapability != null) {
         Power[] powers = playerCapability.getPowers();

         for (Power power : powers) {
            power.onLeftClickEmpty(event);
         }
      }
   }

   @SubscribeEvent
   public void onLivingDamage(LivingDamageEvent event) {
      LivingEntity entity = event.getEntity();
      if (entity.m_21223_() <= event.getAmount() && entity.m_21023_((MobEffect)EffectHandler.FROZEN.get())) {
         entity.m_6234_((MobEffect)EffectHandler.FROZEN.get());
         FrozenCapability.IFrozenCapability frozenCapability = CapabilityHandler.getCapability(entity, CapabilityHandler.FROZEN_CAPABILITY);
         MowziesMobs.NETWORK.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(event::getEntity), new MessageFreezeEffect(event.getEntity(), false));
         if (frozenCapability != null) {
            frozenCapability.onUnfreeze(entity);
         }
      }
   }

   @SubscribeEvent
   public void onPlayerInteract(RightClickItem event) {
      if (event.isCancelable() && event.getEntity().m_21023_((MobEffect)EffectHandler.FROZEN.get())) {
         event.setCanceled(true);
      } else {
         AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(event.getEntity());
         if (abilityCapability != null && event.isCancelable() && abilityCapability.itemUsePrevented(event.getItemStack())) {
            event.setCanceled(true);
         } else {
            PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(event.getEntity(), CapabilityHandler.PLAYER_CAPABILITY);
            if (playerCapability != null) {
               Power[] powers = playerCapability.getPowers();

               for (Power power : powers) {
                  power.onRightClickWithItem(event);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public void onPlayerLeftClick(LeftClickBlock event) {
      Player player = event.getEntity();
      if (event.isCancelable() && player.m_21023_((MobEffect)EffectHandler.FROZEN.get())) {
         event.setCanceled(true);
      } else {
         AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(event.getEntity());
         if (abilityCapability != null && event.isCancelable() && abilityCapability.blockBreakingBuildingPrevented()) {
            event.setCanceled(true);
         } else {
            PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(player, CapabilityHandler.PLAYER_CAPABILITY);
            if (playerCapability != null) {
               Power[] powers = playerCapability.getPowers();

               for (Power power : powers) {
                  power.onLeftClickBlock(event);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public void onLivingJump(LivingJumpEvent event) {
      if (event.getEntity() instanceof LivingEntity) {
         LivingEntity entity = event.getEntity();
         if (entity.m_21023_((MobEffect)EffectHandler.FROZEN.get()) && entity.m_20096_()) {
            entity.m_20256_(entity.m_20184_().m_82542_(1.0, 0.0, 1.0));
         }
      }

      if (event.getEntity() instanceof Player) {
         PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(event.getEntity(), CapabilityHandler.PLAYER_CAPABILITY);
         if (playerCapability != null) {
            Power[] powers = playerCapability.getPowers();

            for (Power power : powers) {
               power.onJump(event);
            }
         }
      }
   }

   @SubscribeEvent
   public void onPlayerAttack(AttackEntityEvent event) {
      if (event.isCancelable() && event.getEntity().m_21023_((MobEffect)EffectHandler.FROZEN.get())) {
         event.setCanceled(true);
      } else {
         if (event.getEntity() instanceof Player) {
            AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(event.getEntity());
            if (abilityCapability != null && event.isCancelable() && abilityCapability.attackingPrevented()) {
               event.setCanceled(true);
               return;
            }

            PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(event.getEntity(), CapabilityHandler.PLAYER_CAPABILITY);
            if (playerCapability != null) {
               playerCapability.setPrevCooledAttackStrength(event.getEntity().m_36403_(0.5F));
               Power[] powers = playerCapability.getPowers();

               for (Power power : powers) {
                  power.onLeftClickEntity(event);
               }

               if (event.getTarget() instanceof ItemFrame) {
                  ItemFrame itemFrame = (ItemFrame)event.getTarget();
                  if (itemFrame.m_31822_().m_41720_() instanceof ItemUmvuthanaMask) {
                     this.aggroUmvuthana(event.getEntity());
                  }
               }

               if (event.getTarget() instanceof LeaderSunstrikeImmune) {
                  this.aggroUmvuthana(event.getEntity());
               }

               if (!(event.getTarget() instanceof LivingEntity)) {
                  return;
               }

               if (event.getTarget() instanceof EntityUmvuthanaFollowerToPlayer) {
                  return;
               }

               if (!event.getEntity().f_19853_.m_5776_()) {
                  for (int i = 0; i < playerCapability.getPackSize(); i++) {
                     EntityUmvuthanaFollowerToPlayer barakoa = playerCapability.getTribePack().get(i);
                     LivingEntity living = (LivingEntity)event.getTarget();
                     if (barakoa.getMaskType() != MaskType.FAITH && !living.m_20147_()) {
                        barakoa.m_6710_(living);
                     }
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public void checkCritEvent(CriticalHitEvent event) {
      ItemStack weapon = event.getEntity().m_21205_();
      Player attacker = event.getEntity();
      PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(event.getEntity(), CapabilityHandler.PLAYER_CAPABILITY);
      if (playerCapability != null && playerCapability.getPrevCooledAttackStrength() == 1.0F && !weapon.m_41619_() && event.getTarget() instanceof LivingEntity
         )
       {
         LivingEntity target = (LivingEntity)event.getTarget();
         if (weapon.m_41720_() instanceof ItemNagaFangDagger) {
            Vec3 lookDir = new Vec3(target.m_20154_().f_82479_, 0.0, target.m_20154_().f_82481_).m_82541_();
            Vec3 vecBetween = new Vec3(target.m_20185_() - event.getEntity().m_20185_(), 0.0, target.m_20189_() - event.getEntity().m_20189_()).m_82541_();
            double dot = lookDir.m_82526_(vecBetween);
            if (dot > 0.7) {
               event.setResult(Result.ALLOW);
               event.setDamageModifier(((Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.NAGA_FANG_DAGGER.backstabDamageMultiplier.get()).floatValue());
               target.m_5496_((SoundEvent)MMSounds.ENTITY_NAGA_ACID_HIT.get(), 1.0F, 1.2F);
               AbilityHandler.INSTANCE.sendAbilityMessage(attacker, AbilityHandler.BACKSTAB_ABILITY);
               if (target.f_19853_.m_5776_() && target != null && attacker != null) {
                  Vec3 ringOffset = attacker.m_20154_().m_82490_((double)(-target.m_20205_() / 2.0F));
                  ParticleRotation.OrientVector rotation = new ParticleRotation.OrientVector(ringOffset);
                  Vec3 pos = target.m_20182_().m_82520_(0.0, (double)(target.m_20206_() / 2.0F), 0.0).m_82549_(ringOffset);
                  AdvancedParticleBase.spawnParticle(
                     target.f_19853_,
                     (ParticleType<AdvancedParticleData>)ParticleHandler.RING_SPARKS.get(),
                     pos.m_7096_(),
                     pos.m_7098_(),
                     pos.m_7094_(),
                     0.0,
                     0.0,
                     0.0,
                     rotation,
                     3.5,
                     0.83F,
                     1.0,
                     0.39F,
                     1.0,
                     1.0,
                     6.0,
                     false,
                     true,
                     new ParticleComponent[]{
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                           new ParticleComponent.KeyTrack(new float[]{1.0F, 1.0F, 0.0F}, new float[]{0.0F, 0.5F, 1.0F}),
                           false
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(0.0F, 15.0F), false
                        )
                     }
                  );
                  RandomSource rand = attacker.f_19853_.m_213780_();
                  float explodeSpeed = 2.5F;

                  for (int i = 0; i < 10; i++) {
                     Vec3 particlePos = new Vec3((double)rand.m_188501_() * 0.25, 0.0, 0.0);
                     particlePos = particlePos.m_82524_((float)((double)(rand.m_188501_() * 2.0F) * Math.PI));
                     particlePos = particlePos.m_82496_((float)((double)(rand.m_188501_() * 2.0F) * Math.PI));
                     double value = (double)(rand.m_188501_() * 0.1F);
                     double life = (double)(rand.m_188501_() * 8.0F + 15.0F);
                     ParticleVanillaCloudExtended.spawnVanillaCloud(
                        target.f_19853_,
                        pos.m_7096_(),
                        pos.m_7098_(),
                        pos.m_7094_(),
                        particlePos.f_82479_ * (double)explodeSpeed,
                        particlePos.f_82480_ * (double)explodeSpeed,
                        particlePos.f_82481_ * (double)explodeSpeed,
                        1.0,
                        0.25 + value,
                        0.75 + value,
                        0.25 + value,
                        0.6,
                        life
                     );
                  }

                  for (int i = 0; i < 10; i++) {
                     Vec3 particlePos = new Vec3((double)rand.m_188501_() * 0.25, 0.0, 0.0);
                     particlePos = particlePos.m_82524_((float)((double)(rand.m_188501_() * 2.0F) * Math.PI));
                     particlePos = particlePos.m_82496_((float)((double)(rand.m_188501_() * 2.0F) * Math.PI));
                     double value = (double)(rand.m_188501_() * 0.1F);
                     double life = (double)(rand.m_188501_() * 2.5F + 5.0F);
                     AdvancedParticleBase.spawnParticle(
                        target.f_19853_,
                        (ParticleType<AdvancedParticleData>)ParticleHandler.PIXEL.get(),
                        pos.m_7096_(),
                        pos.m_7098_(),
                        pos.m_7094_(),
                        particlePos.f_82479_ * (double)explodeSpeed,
                        particlePos.f_82480_ * (double)explodeSpeed,
                        particlePos.f_82481_ * (double)explodeSpeed,
                        true,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        3.0,
                        0.07 + value,
                        0.25 + value,
                        0.07 + value,
                        1.0,
                        0.6,
                        life * 0.95,
                        false,
                        true
                     );
                  }

                  for (int i = 0; i < 6; i++) {
                     Vec3 particlePos = new Vec3((double)rand.m_188501_() * 0.25, 0.0, 0.0);
                     particlePos = particlePos.m_82524_((float)((double)(rand.m_188501_() * 2.0F) * Math.PI));
                     particlePos = particlePos.m_82496_((float)((double)(rand.m_188501_() * 2.0F) * Math.PI));
                     double value = (double)(rand.m_188501_() * 0.1F);
                     double life = (double)(rand.m_188501_() * 5.0F + 10.0F);
                     AdvancedParticleBase.spawnParticle(
                        target.f_19853_,
                        (ParticleType<AdvancedParticleData>)ParticleHandler.BUBBLE.get(),
                        pos.m_7096_(),
                        pos.m_7098_(),
                        pos.m_7094_(),
                        particlePos.f_82479_ * (double)explodeSpeed,
                        particlePos.f_82480_ * (double)explodeSpeed,
                        particlePos.f_82481_ * (double)explodeSpeed,
                        true,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        3.0,
                        0.25 + value,
                        0.75 + value,
                        0.25 + value,
                        1.0,
                        0.6,
                        life * 0.95,
                        false,
                        true
                     );
                  }
               }
            }
         } else if (weapon.m_41720_() instanceof ItemSpear
            && target instanceof Animal
            && target.m_21233_() <= 30.0F
            && (double)attacker.f_19853_.m_213780_().m_188501_() <= 0.334) {
            event.setResult(Result.ALLOW);
            event.setDamageModifier(400.0F);
         }
      }
   }

   @SubscribeEvent
   public void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
      if (event.getObject() instanceof LivingEntity) {
         event.addCapability(new ResourceLocation("mowziesmobs", "frozen"), new FrozenCapability.FrozenProvider());
         event.addCapability(new ResourceLocation("mowziesmobs", "last_damage"), new LivingCapability.LivingProvider());
         event.addCapability(new ResourceLocation("mowziesmobs", "ability"), new AbilityCapability.AbilityProvider());
      }

      if (event.getObject() instanceof Player) {
         event.addCapability(new ResourceLocation("mowziesmobs", "player"), new PlayerCapability.PlayerProvider());
      }
   }

   @SubscribeEvent
   public void onRideEntity(EntityMountEvent event) {
      if (event.getEntityMounting() instanceof EntityUmvuthi
         || event.getEntityMounting() instanceof EntityFrostmaw
         || event.getEntityMounting() instanceof EntityWroughtnaut) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onPlayerRespawn(PlayerRespawnEvent event) {
      for (MowzieEntity mob : this.getEntitiesNearby(event.getEntity(), MowzieEntity.class, 40.0)) {
         if (mob.resetHealthOnPlayerRespawn()) {
            mob.m_21153_(mob.m_21233_());
         }
      }
   }

   private void aggroUmvuthana(Player player) {
      for (EntityUmvuthi barako : this.getEntitiesNearby(player, EntityUmvuthi.class, 50.0)) {
         if ((barako.m_5448_() == null || !(barako.m_5448_() instanceof Player))
            && !player.m_7500_()
            && !player.m_5833_()
            && player.m_20183_().m_123331_(barako.m_21534_()) < 900.0
            && barako.m_6779_(player)) {
            barako.setMisbehavedPlayerId(player.m_20148_());
         }
      }

      for (EntityUmvuthanaMinion barakoa : this.getEntitiesNearby(player, EntityUmvuthanaMinion.class, 50.0)) {
         if ((barakoa.m_5448_() == null || !(barakoa.m_5448_() instanceof Player))
            && player.m_20183_().m_123331_(barakoa.m_21534_()) < 900.0
            && barakoa.m_6779_(player)) {
            barakoa.setMisbehavedPlayerId(player.m_20148_());
         }
      }
   }

   private void cheatSculptor(Player player) {
      for (EntitySculptor sculptor : player.f_19853_
         .m_6443_(
            EntitySculptor.class,
            player.m_20191_().m_82377_((double)(EntitySculptor.TEST_RADIUS + 3), (double)EntitySculptor.TEST_HEIGHT, (double)(EntitySculptor.TEST_RADIUS + 3)),
            EntitySculptor::isTesting
         )) {
         sculptor.playerCheated();
      }
   }
}
