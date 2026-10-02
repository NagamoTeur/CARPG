package net.cisco.procedures;

import javax.annotation.Nullable;
import net.cisco.init.CiscoModModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class FrostfangInHandProcedure {
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
         if ((entity instanceof LivingEntity _livEntx ? _livEntx.m_21205_() : ItemStack.f_41583_).m_41720_() == CiscoModModItems.FROSTFANG.get()
            && amount > (double)(entity instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F) / 2.5) {
            ((LivingHurtEvent)event).setAmount((float)((double)(entity instanceof LivingEntity _livEntxx ? _livEntxx.m_21233_() : -1.0F) / 2.5));
         }
      }
   }
}
