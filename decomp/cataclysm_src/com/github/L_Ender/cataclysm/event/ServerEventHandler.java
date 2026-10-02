package com.github.L_Ender.cataclysm.event;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.capabilities.ChargeCapability;
import com.github.L_Ender.cataclysm.capabilities.HookCapability;
import com.github.L_Ender.cataclysm.capabilities.ParryCapability;
import com.github.L_Ender.cataclysm.capabilities.RenderRushCapability;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Royal_Draugr_Entity;
import com.github.L_Ender.cataclysm.init.ModCapabilities;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.items.ILeftClick;
import com.github.L_Ender.cataclysm.message.MessageParticle;
import com.github.L_Ender.cataclysm.message.MessageSwingArm;
import com.github.L_Ender.lionfishapi.server.event.StandOnFluidEvent;
import java.util.List;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Start;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Stop;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Tick;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
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
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

@EventBusSubscriber(
   modid = "cataclysm",
   bus = Bus.FORGE
)
public class ServerEventHandler {
   @SubscribeEvent
   public void onShieldDamage(ShieldBlockEvent event) {
      DamageSource source = event.getDamageSource();
      LivingEntity entity = event.getEntity();
      Item item = entity.m_21211_().m_41720_();
      Entity directEntity = source.m_7640_();
      if (source.m_19385_().equals("cataclysm.maledictio_sagitta")) {
         event.setShieldTakesDamage(false);
      }

      ParryCapability.IParryCapability ParryCapability = ModCapabilities.getCapability(event.getEntity(), ModCapabilities.PARRY_CAPABILITY);
      if (item == ModItems.BULWARK_OF_THE_FLAME.get()
         && ParryCapability != null
         && ParryCapability.getParryFrame() < 15
         && directEntity instanceof LivingEntity livingEntity) {
         livingEntity.m_20254_(3);
         livingEntity.m_5496_(SoundEvents.f_11668_, 0.8F, 1.3F);
         MobEffectInstance effectinstance = new MobEffectInstance((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get(), 100, 0);
         livingEntity.m_147240_(0.5, entity.m_20185_() - livingEntity.m_20185_(), entity.m_20189_() - livingEntity.m_20189_());
         livingEntity.m_7292_(effectinstance);
      }
   }

   @SubscribeEvent
   public void DeathEvent(LivingDeathEvent event) {
      DamageSource source = event.getSource();
      if (!event.getEntity().f_19853_.f_46443_ && !source.m_19378_() && this.tryCursiumPlateRebirth(event.getEntity())) {
         event.setCanceled(true);
      }
   }

   private boolean tryCursiumPlateRebirth(LivingEntity living) {
      ItemStack chestplate = living.m_6844_(EquipmentSlot.CHEST);
      if (chestplate.m_41720_() == ModItems.CURSIUM_CHESTPLATE.get()
         && !living.m_21023_((MobEffect)ModEffect.EFFECTGHOST_SICKNESS.get())
         && !living.m_21023_((MobEffect)ModEffect.EFFECTGHOST_FORM.get())) {
         living.m_21153_(5.0F);
         living.m_7292_(new MobEffectInstance(MobEffects.f_19607_, 200, 0));
         living.m_7292_(new MobEffectInstance((MobEffect)ModEffect.EFFECTGHOST_FORM.get(), 100, 0));
         double d0 = living.m_20185_();
         double d1 = living.m_20186_() + 0.3F;
         double d2 = living.m_20189_();
         float size = 3.0F;

         for (ServerPlayer serverplayer : ((ServerLevel)living.f_19853_).m_6907_()) {
            if (serverplayer.m_20238_(Vec3.m_82512_(living.m_20183_())) < 1024.0) {
               MessageParticle particlePacket = new MessageParticle();

               for (float i = -size; i <= size; i++) {
                  for (float j = -size; j <= size; j++) {
                     for (float k = -size; k <= size; k++) {
                        double d3 = (double)j + (living.m_217043_().m_188500_() - living.m_217043_().m_188500_()) * 0.5;
                        double d4 = (double)i + (living.m_217043_().m_188500_() - living.m_217043_().m_188500_()) * 0.5;
                        double d5 = (double)k + (living.m_217043_().m_188500_() - living.m_217043_().m_188500_()) * 0.5;
                        double d6 = (double)Mth.m_14116_((float)(d3 * d3 + d4 * d4 + d5 * d5)) / 0.5 + living.m_217043_().m_188583_() * 0.05;
                        particlePacket.queueParticle((ParticleOptions)ModParticle.CURSED_FLAME.get(), false, d0, d1, d2, d3 / d6, d4 / d6, d5 / d6);
                        if (i != -size && i != size && j != -size && j != size) {
                           k += size * 2.0F - 1.0F;
                        }
                     }
                  }
               }

               Cataclysm.NETWORK_WRAPPER.send(PacketDistributor.PLAYER.with(() -> serverplayer), particlePacket);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @SubscribeEvent
   public void onLivingAttack(LivingAttackEvent event) {
      if (event.getEntity().m_21023_((MobEffect)ModEffect.EFFECTGHOST_FORM.get()) && !event.getSource().m_19378_()) {
         event.setCanceled(true);
      }

      if (!event.getEntity().m_6844_(EquipmentSlot.LEGS).m_41619_()
         && event.getEntity().m_6844_(EquipmentSlot.LEGS).m_41720_() == ModItems.CURSIUM_LEGGINGS.get()) {
         if (event.getSource().m_19378_()) {
            if (event.getEntity().m_217043_().m_188501_() < 0.15F) {
               event.setCanceled(true);
            }
         } else if (!event.getSource().m_19378_() && event.getEntity().m_217043_().m_188501_() < 0.08F) {
            event.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public void onLivingAttack(CriticalHitEvent event) {
      ItemStack weapon = event.getEntity().m_21205_();
      if (!weapon.m_41619_() && event.getTarget() instanceof LivingEntity livingEntity) {
         if (weapon.m_41720_() == ModItems.THE_ANNIHILATOR.get()) {
            event.setDamageModifier(2.25F);
         }

         if (weapon.m_41720_() == ModItems.THE_IMMOLATOR.get()) {
            if (livingEntity.m_21023_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get())) {
               event.setResult(Result.ALLOW);
            }

            event.setDamageModifier(2.0F);
         }
      }
   }

   @SubscribeEvent
   public void onLivingFall(LivingFallEvent event) {
      if (!event.getEntity().m_6844_(EquipmentSlot.FEET).m_41619_() && event.getEntity().m_6844_(EquipmentSlot.FEET).m_41720_() == ModItems.CURSIUM_BOOTS.get()
         )
       {
         event.setDistance(event.getDistance() * 0.3F);
      }
   }

   @SubscribeEvent
   public void onLivingUpdateEvent(LivingTickEvent event) {
      HookCapability.IHookCapability hookCapability = ModCapabilities.getCapability(event.getEntity(), ModCapabilities.HOOK_CAPABILITY);
      if (hookCapability != null) {
         hookCapability.tick(event.getEntity());
      }

      ChargeCapability.IChargeCapability chargeCapability = ModCapabilities.getCapability(event.getEntity(), ModCapabilities.CHARGE_CAPABILITY);
      if (chargeCapability != null) {
         chargeCapability.tick(event.getEntity());
      }

      RenderRushCapability.IRenderRushCapability RushCapability = ModCapabilities.getCapability(event.getEntity(), ModCapabilities.RENDER_RUSH_CAPABILITY);
      if (RushCapability != null) {
         RushCapability.tick(event.getEntity());
      }
   }

   @SubscribeEvent
   public void StandOnFluidEventEvent(StandOnFluidEvent event) {
      if (!event.getEntity().m_6844_(EquipmentSlot.FEET).m_41619_()
         && event.getEntity().m_6844_(EquipmentSlot.FEET).m_41720_() == ModItems.IGNITIUM_BOOTS.get()
         && !event.getEntity().m_6144_()
         && (event.getFluidState().m_192917_(Fluids.f_76195_) || event.getFluidState().m_192917_(Fluids.f_76194_))) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onLivingDamage(LivingHurtEvent event) {
      LivingEntity target = event.getEntity();
      if (!target.f_19853_.m_5776_() && event.getSource().m_7640_() instanceof LivingEntity living) {
         ItemStack weapon = living.m_21205_();
         if (!weapon.m_41619_()) {
            if (weapon.m_150930_((Item)ModItems.ZWEIENDER.get())) {
               Vec3 lookDir = new Vec3(target.m_20154_().f_82479_, 0.0, target.m_20154_().f_82481_).m_82541_();
               Vec3 vecBetween = new Vec3(target.m_20185_() - living.m_20185_(), 0.0, target.m_20189_() - living.m_20189_()).m_82541_();
               double dot = lookDir.m_82526_(vecBetween);
               if (dot > 0.05) {
                  event.setAmount(event.getAmount() * 2.0F);
                  target.m_5496_(SoundEvents.f_11852_, 0.75F, 0.5F);
               }
            }

            if (weapon.m_150930_((Item)ModItems.FINAL_FRACTAL.get())) {
               event.setAmount(event.getAmount() + target.m_21233_() * 0.03F);
            }
         }
      }

      if (event.getSource().m_7640_() instanceof LivingEntity livingx) {
         List<SlotResult> slot = CuriosApi.getCuriosHelper().findCurios(livingx, stack -> stack.m_150930_((Item)ModItems.BLAZING_GRIPS.get()));
         if (!slot.isEmpty() && event.getEntity().m_217043_().m_188501_() < 0.15F * (float)slot.size()) {
            MobEffectInstance effectinstance = new MobEffectInstance((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get(), 60, 0);
            target.m_7292_(effectinstance);
         }
      }
   }

   @SubscribeEvent
   public void onPlayerAttack(AttackEntityEvent event) {
      if (event.isCancelable() && event.getEntity().m_21023_((MobEffect)ModEffect.EFFECTSTUN.get())) {
         event.setCanceled(true);
      }

      if (event.isCancelable() && event.getEntity().m_21023_((MobEffect)ModEffect.EFFECTGHOST_FORM.get())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void BlockHeal(LivingHealEvent event) {
      if (event.getEntity().m_21023_((MobEffect)ModEffect.EFFECTABYSSAL_FEAR.get())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onLivingJump(LivingJumpEvent event) {
      LivingEntity entity = event.getEntity();
      if (entity.m_21124_((MobEffect)ModEffect.EFFECTSTUN.get()) != null) {
         entity.m_20334_(entity.m_20184_().m_7096_(), 0.0, entity.m_20184_().m_7094_());
      }
   }

   @SubscribeEvent
   public void onPlayerLeftClick(LeftClickBlock event) {
      Player player = event.getEntity();
      if (event.isCancelable() && player.m_21023_((MobEffect)ModEffect.EFFECTSTUN.get())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onUseItem(LivingEntityUseItemEvent event) {
      LivingEntity living = event.getEntity();
      if (event.isCancelable() && living.m_21023_((MobEffect)ModEffect.EFFECTSTUN.get())) {
         event.setCanceled(true);
      }

      if (event.isCancelable() && living.m_21023_((MobEffect)ModEffect.EFFECTGHOST_FORM.get())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onPlaceBlock(EntityPlaceEvent event) {
      if (event.getEntity() instanceof LivingEntity living && event.isCancelable() && living.m_21023_((MobEffect)ModEffect.EFFECTSTUN.get())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void KnockbackEvent(LivingKnockBackEvent event) {
      if (event.getEntity() instanceof Royal_Draugr_Entity royalDraugr && royalDraugr.isDraugrBlocking()) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onFillBucket(FillBucketEvent event) {
      LivingEntity living = event.getEntity();
      if (living != null && event.isCancelable() && living.m_21023_((MobEffect)ModEffect.EFFECTSTUN.get())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onBreakBlock(BreakEvent event) {
      if (event.isCancelable() && event.getPlayer().m_21023_((MobEffect)ModEffect.EFFECTSTUN.get())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onPlayerInteract(RightClickEmpty event) {
      if (event.isCancelable() && event.getEntity().m_21023_((MobEffect)ModEffect.EFFECTSTUN.get())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onStartUsing(Start event) {
      Item item = event.getItem().m_41720_();
      if (item == ModItems.BULWARK_OF_THE_FLAME.get()
         && event.getEntity() instanceof Player player
         && player.f_20921_ == 0.0F
         && !player.m_36335_().m_41519_(item)) {
         ParryCapability.IParryCapability ParryCapability = ModCapabilities.getCapability(event.getEntity(), ModCapabilities.PARRY_CAPABILITY);
         if (ParryCapability != null) {
            ParryCapability.setParryFrame(0);
         }
      }
   }

   @SubscribeEvent
   public void onUseTick(Tick event) {
      Item item = event.getItem().m_41720_();
      if (item == ModItems.BULWARK_OF_THE_FLAME.get() && event.getEntity() instanceof Player player) {
         ParryCapability.IParryCapability ParryCapability = ModCapabilities.getCapability(event.getEntity(), ModCapabilities.PARRY_CAPABILITY);
         if (ParryCapability != null) {
            ParryCapability.setParryFrame(ParryCapability.getParryFrame() + 1);
         }
      }
   }

   @SubscribeEvent
   public void onStopUsing(Stop event) {
      Item item = event.getItem().m_41720_();
      if (item == ModItems.BULWARK_OF_THE_FLAME.get() && event.getEntity() instanceof Player) {
         ParryCapability.IParryCapability ParryCapability = ModCapabilities.getCapability(event.getEntity(), ModCapabilities.PARRY_CAPABILITY);
         if (ParryCapability != null) {
            ParryCapability.setParryFrame(0);
         }
      }
   }

   @SubscribeEvent
   public void onPlayerInteract(LeftClickEmpty event) {
      if (event.isCancelable() && event.getEntity().m_21023_((MobEffect)ModEffect.EFFECTSTUN.get())) {
         event.setCanceled(true);
      }

      boolean flag = false;
      ItemStack leftItem = event.getEntity().m_21206_();
      ItemStack rightItem = event.getEntity().m_21205_();
      if (!event.getEntity().m_21023_((MobEffect)ModEffect.EFFECTSTUN.get())) {
         if (leftItem.m_41720_() instanceof ILeftClick) {
            ((ILeftClick)leftItem.m_41720_()).onLeftClick(leftItem, event.getEntity());
            flag = true;
         }

         if (rightItem.m_41720_() instanceof ILeftClick) {
            ((ILeftClick)rightItem.m_41720_()).onLeftClick(rightItem, event.getEntity());
            flag = true;
         }

         if (event.getLevel().f_46443_ && flag) {
            Cataclysm.sendMSGToServer(MessageSwingArm.INSTANCE);
         }
      }
   }

   @SubscribeEvent
   public void onPlayerInteract(EntityInteract event) {
      if (event.isCancelable() && event.getEntity().m_21023_((MobEffect)ModEffect.EFFECTSTUN.get())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onPlayerInteract(RightClickBlock event) {
      if (event.isCancelable() && event.getEntity().m_21023_((MobEffect)ModEffect.EFFECTSTUN.get())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onPlayerInteract(LeftClickBlock event) {
      if (event.isCancelable() && event.getEntity().m_21023_((MobEffect)ModEffect.EFFECTSTUN.get())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onLivingSetTargetEvent(LivingChangeTargetEvent event) {
      if (event.getNewTarget() != null
         && event.getEntity() instanceof Mob mob
         && mob.m_6095_().m_204039_(ModTag.LAVA_MONSTER)
         && event.getEntity().m_21188_() != event.getNewTarget()
         && event.getNewTarget().m_6844_(EquipmentSlot.HEAD).m_150930_((Item)ModItems.IGNITIUM_HELMET.get())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onLivingDamage(LivingDamageEvent event) {
      LivingEntity entity = event.getEntity();
      if (entity.m_21223_() <= event.getAmount() && entity.m_21023_((MobEffect)ModEffect.EFFECTSTUN.get())) {
         entity.m_21195_((MobEffect)ModEffect.EFFECTSTUN.get());
      }

      if (!event.getEntity().m_6844_(EquipmentSlot.LEGS).m_41619_()
         && event.getSource() != null
         && event.getSource().m_7639_() != null
         && event.getEntity().m_6844_(EquipmentSlot.LEGS).m_41720_() == ModItems.IGNITIUM_LEGGINGS.get()) {
         Entity attacker = event.getSource().m_7639_();
         if (attacker instanceof LivingEntity && attacker != event.getEntity() && event.getEntity().m_217043_().m_188501_() < 0.5F) {
            MobEffectInstance effectinstance1 = ((LivingEntity)attacker).m_21124_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get());
            int i = 1;
            if (effectinstance1 != null) {
               i += effectinstance1.m_19564_();
               ((LivingEntity)attacker).m_6234_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get());
            } else {
               i--;
            }

            i = Mth.m_14045_(i, 0, 4);
            MobEffectInstance effectinstance = new MobEffectInstance((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get(), 100, i, false, false, true);
            ((LivingEntity)attacker).m_7292_(effectinstance);
            if (!attacker.m_6060_()) {
               attacker.m_20254_(5);
            }
         }
      }
   }

   @SubscribeEvent
   public void onPlayerInteract(RightClickItem event) {
      if (event.isCancelable() && event.getEntity().m_21023_((MobEffect)ModEffect.EFFECTSTUN.get())) {
         event.setCanceled(true);
      }
   }
}
