package com.hollingsworth.arsnouveau.common.potions;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "ars_nouveau"
)
public class GravityEffect extends MobEffect {
   protected GravityEffect() {
      super(MobEffectCategory.HARMFUL, 2039587);
   }

   public boolean m_6584_(int p_76397_1_, int p_76397_2_) {
      return true;
   }

   public void m_6742_(LivingEntity livingEntity, int p_76394_2_) {
      if (!livingEntity.m_20096_()) {
         boolean isTooHigh = true;
         Level world = livingEntity.f_19853_;
         if (livingEntity instanceof Player) {
            for (int i = 1; i < 3; i++) {
               if (world.m_8055_(livingEntity.m_20183_().m_6625_(i)).m_60767_() != Material.f_76296_) {
                  isTooHigh = false;
                  break;
               }
            }
         }

         if (isTooHigh) {
            livingEntity.m_20256_(livingEntity.m_20184_().m_82520_(0.0, -0.5, 0.0));
            livingEntity.f_19864_ = true;
         }
      }
   }

   @SubscribeEvent
   public static void entityTick(PlayerTickEvent e) {
      if (e.phase == Phase.END && e.player.m_21023_((MobEffect)ModPotions.GRAVITY_EFFECT.get()) && !e.player.m_20096_() && !e.player.m_7500_()) {
         e.player.f_36077_.f_35935_ = false;
      }
   }

   @SubscribeEvent
   public static void entityHurt(LivingHurtEvent e) {
      if (e.getSource().equals(DamageSource.f_19315_) && e.getEntity().m_21023_((MobEffect)ModPotions.GRAVITY_EFFECT.get())) {
         e.setAmount(e.getAmount() * 2.0F);
      }
   }
}
