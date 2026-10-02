package net.cisco.procedures;

import javax.annotation.Nullable;
import net.cisco.entity.CiscoEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class FallenCiscoDesperationProcedure {
   @SubscribeEvent
   public static void onEntityAttacked(LivingHurtEvent event) {
      if (event != null && event.getEntity() != null) {
         execute(event, event.getEntity(), (double)event.getAmount());
      }
   }

   public static void execute(Entity entity, double amount) {
      execute(null, entity, amount);
   }

   private static void execute(@Nullable Event event, Entity entity, double amount) {
      if (entity != null) {
         if ((entity instanceof LivingEntity _livEntx ? _livEntx.m_21223_() : -1.0F)
               < (entity instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F) / 2.0F
            && entity instanceof CiscoEntity) {
            if (amount > 9000.0) {
               ((LivingHurtEvent)event).setAmount(50.0F);
            } else {
               ((LivingHurtEvent)event).setAmount((float)(amount - amount / 1.7));
            }
         }
      }
   }
}
