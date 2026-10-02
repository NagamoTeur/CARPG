package com.aqutheseal.celestisynth.common.events;

import com.aqutheseal.celestisynth.api.animation.player.AnimationManager;
import com.aqutheseal.celestisynth.api.item.CSWeapon;
import com.aqutheseal.celestisynth.api.item.CSWeaponUtil;
import com.aqutheseal.celestisynth.api.mixin.LivingMixinSupport;
import com.aqutheseal.celestisynth.api.mixin.PlayerMixinSupport;
import com.aqutheseal.celestisynth.common.entity.skill.SkillCastPoltergeistWard;
import com.aqutheseal.celestisynth.common.item.weapons.BreezebreakerItem;
import com.aqutheseal.celestisynth.common.registry.CSEntityTypes;
import com.aqutheseal.celestisynth.common.registry.CSParticleTypes;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import com.aqutheseal.celestisynth.util.ParticleUtil;
import com.google.common.collect.Streams;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CSCommonMiscEvents {
   @SubscribeEvent
   public static void onLivingTickEvent(LivingTickEvent event) {
      if (event.getEntity() instanceof LivingMixinSupport lms && lms.getQuasarImbued() != null) {
         double radius = 0.5 + (double)event.getEntity().m_20205_();
         double speed = 0.1;
         double offX = radius * Math.sin(speed * (double)event.getEntity().f_19797_);
         double offY = -Math.sin((double)event.getEntity().f_19797_) * 0.2;
         double offZ = radius * Math.cos(speed * (double)event.getEntity().f_19797_);
         ParticleUtil.sendParticle(
            event.getEntity().f_19853_,
            (SimpleParticleType)CSParticleTypes.RAINFALL_ENERGY_SMALL.get(),
            event.getEntity().m_20185_() + offX,
            event.getEntity().m_20186_() + offY + 1.0,
            event.getEntity().m_20189_() + offZ
         );
      }
   }

   @SubscribeEvent
   public static void onLivingAttackEvent(LivingAttackEvent event) {
      checkAndCancelAttack(event, event.getEntity().m_21205_());
      checkAndCancelAttack(event, event.getEntity().m_21206_());
   }

   @SubscribeEvent
   public static void onLivingHurtEvent(LivingHurtEvent event) {
      LivingEntity entity = event.getEntity();
      ItemStack itemR = entity.m_21205_();
      ItemStack itemL = entity.m_21206_();
      if (itemR.m_41720_() instanceof CSWeapon cs && !(itemL.m_41720_() instanceof CSWeapon)) {
         cs.onPlayerHurt(event, itemR, itemL);
      }

      if (itemL.m_41720_() instanceof CSWeapon cs && !(itemR.m_41720_() instanceof CSWeapon)) {
         cs.onPlayerHurt(event, itemR, itemL);
      }

      if (itemR.m_41720_() instanceof CSWeapon cs && itemL.m_41720_() instanceof CSWeapon) {
         cs.onPlayerHurt(event, itemR, itemL);
      }
   }

   @SubscribeEvent
   public static void onLivingDeathEvent(LivingDeathEvent event) {
      if (event.getEntity() instanceof Player player) {
         CSWeaponUtil.disableRunningWeapon(player);
      }

      if (event.getEntity() instanceof LivingMixinSupport lms && lms.getPhantomTagger() != null) {
         SkillCastPoltergeistWard poltergeistProjectile = (SkillCastPoltergeistWard)((EntityType)CSEntityTypes.POLTERGEIST_WARD.get())
            .m_20615_(event.getEntity().m_9236_());
         poltergeistProjectile.setOwnerUuid(lms.getPhantomTagger().m_20148_());
         poltergeistProjectile.m_6027_(event.getEntity().m_20185_(), event.getEntity().m_20186_(), event.getEntity().m_20189_());
         event.getEntity().m_9236_().m_7967_(poltergeistProjectile);
      }
   }

   @SubscribeEvent
   public static void onPlayerCopy(Clone event) {
      if (event.isWasDeath()) {
         CSWeaponUtil.disableRunningWeapon(event.getEntity());
      }
   }

   @SubscribeEvent
   public static void onLivingFallEvent(LivingFallEvent event) {
      LivingEntity entity = event.getEntity();
      Item itemR = entity.m_21205_().m_41720_();
      Item itemL = entity.m_21206_().m_41720_();
      if (entity instanceof Player player && player.m_9236_().m_5776_()) {
         AnimationManager.playAnimation(AnimationManager.AnimationsList.CLEAR, true);
      }

      if (itemR instanceof BreezebreakerItem || itemL instanceof BreezebreakerItem) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onLivingJumpEvent(LivingJumpEvent event) {
      LivingEntity entity = event.getEntity();
      Item itemR = entity.m_21205_().m_41720_();
      Item itemL = entity.m_21206_().m_41720_();
      if ((itemR instanceof BreezebreakerItem || itemL instanceof BreezebreakerItem) && entity instanceof Player player) {
         if (player.m_9236_().m_5776_()) {
            AnimationManager.playAnimation(AnimationManager.AnimationsList.ANIM_BREEZEBREAKER_JUMP, true);
         }

         player.m_216990_((SoundEvent)CSSoundEvents.CS_HOP.get());
         if (itemR instanceof CSWeapon wp) {
            wp.sendExpandingParticles(entity.f_19853_, ParticleTypes.f_123762_, player.m_20183_(), 75, 0.35F);
         } else {
            CSWeapon wp = (CSWeapon)itemL;
            wp.sendExpandingParticles(entity.f_19853_, ParticleTypes.f_123762_, player.m_20183_(), 75, 0.35F);
         }

         player.m_20256_(entity.m_20184_().m_82542_(2.75, 2.25, 2.75));
      }
   }

   @SubscribeEvent
   public static void onPlayerLoggedInEvent(PlayerLoggedInEvent event) {
      Inventory inv = event.getEntity().m_150109_();
      ObjectArrayList<ItemStack> invCompartments = Streams.concat(new Stream[]{inv.f_35974_.stream(), inv.f_35975_.stream(), inv.f_35976_.stream()})
         .collect(Collectors.toCollection(ObjectArrayList::new));
      ObjectListIterator var3 = invCompartments.iterator();

      while (var3.hasNext()) {
         ItemStack stack = (ItemStack)var3.next();
         if (!stack.m_41619_() && stack.m_41737_("csController") != null) {
            stack.m_41783_().m_128473_("csController");
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerTickEvent(PlayerTickEvent event) {
      if (event.side.isClient() && event.player.f_19853_.m_5776_() && event.player instanceof PlayerMixinSupport pms) {
         pms.setCameraAngleOrdinal(Minecraft.m_91087_().f_91066_.m_92176_().ordinal());
      }
   }

   private static void checkAndCancelAttack(LivingAttackEvent event, ItemStack itemStack) {
      CompoundTag tagElement = itemStack.m_41737_("csController");
      if (tagElement != null && tagElement.m_128471_("cs.hasAnimationBegun") && tagElement.m_128471_("cs.atkOngoing")) {
         event.setCanceled(true);
      }
   }
}
