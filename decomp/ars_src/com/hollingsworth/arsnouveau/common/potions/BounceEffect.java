package com.hollingsworth.arsnouveau.common.potions;

import com.hollingsworth.arsnouveau.api.event.BounceTimedEvent;
import com.hollingsworth.arsnouveau.api.event.EventQueue;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "ars_nouveau"
)
public class BounceEffect extends MobEffect {
   public BounceEffect() {
      super(MobEffectCategory.BENEFICIAL, 2039587);
   }

   @SubscribeEvent
   public static void onFall(LivingFallEvent event) {
      LivingEntity entity = event.getEntity();
      if (entity != null && entity.m_21023_((MobEffect)ModPotions.BOUNCE_EFFECT.get())) {
         boolean isPlayer = entity instanceof Player;
         boolean isClient = entity.f_19853_.f_46443_;
         if (!isClient || isPlayer) {
            if (event.getDistance() > 2.0F) {
               if (entity.m_6047_()) {
                  event.setDamageMultiplier(0.0F);
               } else {
                  event.setDamageMultiplier(0.0F);
                  entity.f_19789_ = 0.0F;
                  if (!isPlayer || isClient) {
                     double f = 0.95 - 0.1 * (double)entity.m_21124_((MobEffect)ModPotions.BOUNCE_EFFECT.get()).m_19564_();
                     entity.m_20334_(entity.m_20184_().f_82479_ / f, entity.m_20184_().f_82480_ * -0.9, entity.m_20184_().f_82481_ / f);
                     entity.f_19864_ = true;
                     entity.m_6853_(false);
                  }

                  if (isClient) {
                     EventQueue.getClientQueue().addEvent(new BounceTimedEvent(entity, entity.m_20184_().f_82480_));
                  } else {
                     EventQueue.getServerInstance().addEvent(new BounceTimedEvent(entity, entity.m_20184_().f_82480_));
                  }

                  event.setCanceled(true);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onFlyWallDamage(LivingHurtEvent event) {
      LivingEntity entity = event.getEntity();
      if (entity != null && entity.m_21023_((MobEffect)ModPotions.BOUNCE_EFFECT.get())) {
         boolean isPlayer = entity instanceof Player;
         if (isPlayer) {
            if (event.getSource() == DamageSource.f_19316_) {
               event.setAmount(0.0F);
               Vec3 lookAngle = entity.m_20154_();
               entity.m_20256_(lookAngle.m_82490_(-2.0));
               entity.f_19864_ = true;
            }
         }
      }
   }
}
