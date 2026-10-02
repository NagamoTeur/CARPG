package com.rolfmao.upgradednetherite_items.handlers;

import com.rolfmao.upgradednetherite_items.init.ModItems;
import com.rolfmao.upgradednetherite_items.init.UpgradedNetheriteEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
   modid = "upgradednetherite_items",
   bus = Bus.FORGE
)
public class ItemEventHandler {
   @SubscribeEvent(
      receiveCanceled = true,
      priority = EventPriority.HIGHEST
   )
   public static void onAttackEntityEvent(AttackEntityEvent event) {
      if (!event.getEntity().f_19853_.f_46443_) {
         Player player = event.getEntity();
         Entity target = event.getTarget();
         ItemStack heldItem = player.m_21205_();
         if (ItemStack.m_41758_(heldItem, new ItemStack((ItemLike)ModItems.ENDER_UPGRADED_NETHERITE_PEARL.get()))
            && !player.m_36335_().m_41519_((Item)ModItems.ENDER_UPGRADED_NETHERITE_PEARL.get())
            && target instanceof LivingEntity
            && !(target instanceof Player)
            && !(target instanceof WitherBoss)
            && !(target instanceof EnderDragon)) {
            heldItem.m_41784_().m_128405_("EnderUpgradedNetheritePearlTarget", target.m_19879_());
            heldItem.m_41784_().m_128359_("EnderUpgradedNetheritePearlTargetName", player.f_19853_.m_6815_(target.m_19879_()).m_7755_().getString());
            player.m_36335_().m_41524_((Item)ModItems.ENDER_UPGRADED_NETHERITE_PEARL.get(), 20);
         }
      }
   }

   @SubscribeEvent(
      receiveCanceled = true,
      priority = EventPriority.LOWEST
   )
   public static void onDamageEntity(LivingHurtEvent event) {
      if (!event.getEntity().f_19853_.f_46443_) {
         if (event.getEntity() instanceof Player
            && event.getEntity().m_21023_((MobEffect)UpgradedNetheriteEffects.NETHERITE_RESISTANCE.get())
            && event.getSource() != DamageSource.f_19317_) {
            if (event.getEntity().m_21124_((MobEffect)UpgradedNetheriteEffects.NETHERITE_RESISTANCE.get()).m_19564_() == 0 && event.getAmount() > 4.0F) {
               event.setAmount(4.0F);
            } else if (event.getEntity().m_21124_((MobEffect)UpgradedNetheriteEffects.NETHERITE_RESISTANCE.get()).m_19564_() >= 1
               && event.getAmount() > (float)(4 / (event.getEntity().m_21124_((MobEffect)UpgradedNetheriteEffects.NETHERITE_RESISTANCE.get()).m_19564_() * 2))) {
               event.setAmount((float)(4 / (event.getEntity().m_21124_((MobEffect)UpgradedNetheriteEffects.NETHERITE_RESISTANCE.get()).m_19564_() * 2)));
            }
         }

         if (event.getSource().m_7639_() instanceof ServerPlayer
            && ItemStack.m_41758_(
               ((ServerPlayer)event.getSource().m_7639_()).m_21205_(), new ItemStack((ItemLike)ModItems.ENDER_UPGRADED_NETHERITE_PEARL.get())
            )) {
            event.setAmount(0.0F);
         }
      }
   }

   @SubscribeEvent(
      receiveCanceled = true,
      priority = EventPriority.LOWEST
   )
   public static void onDamageEntity(LivingAttackEvent event) {
   }
}
