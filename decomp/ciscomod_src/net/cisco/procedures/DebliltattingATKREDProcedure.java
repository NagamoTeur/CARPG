package net.cisco.procedures;

import javax.annotation.Nullable;
import net.cisco.init.CiscoModModMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class DebliltattingATKREDProcedure {
   @SubscribeEvent
   public static void onEntityAttacked(LivingHurtEvent event) {
      if (event != null && event.getEntity() != null) {
         execute(event, event.getSource().m_7639_(), (double)event.getAmount());
      }
   }

   public static void execute(Entity sourceentity, double amount) {
      execute(null, sourceentity, amount);
   }

   private static void execute(@Nullable Event event, Entity sourceentity, double amount) {
      if (sourceentity != null) {
         if (sourceentity instanceof LivingEntity _livEnt && _livEnt.m_21023_((MobEffect)CiscoModModMobEffects.DEBILITATING_DESIRE.get())) {
            ((LivingHurtEvent)event).setAmount((float)(amount - amount / 0.8));
         }
      }
   }
}
