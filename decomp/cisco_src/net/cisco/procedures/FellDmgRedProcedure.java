package net.cisco.procedures;

import javax.annotation.Nullable;
import net.cisco.entity.FellkingbossEntity;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class FellDmgRedProcedure {
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
         if (entity instanceof FellkingbossEntity) {
            ((LivingHurtEvent)event).setAmount((float)(amount - amount / 1.7));
         }
      }
   }
}
