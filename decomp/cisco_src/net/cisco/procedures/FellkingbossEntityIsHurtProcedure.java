package net.cisco.procedures;

import javax.annotation.Nullable;
import net.cisco.entity.FellkingbossEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class FellkingbossEntityIsHurtProcedure {
   @SubscribeEvent
   public static void onEntityAttacked(LivingAttackEvent event) {
      if (event != null && event.getEntity() != null) {
         execute(event, event.getEntity(), event.getSource().m_7639_(), (double)event.getAmount());
      }
   }

   public static void execute(Entity entity, Entity sourceentity, double amount) {
      execute(null, entity, sourceentity, amount);
   }

   private static void execute(@Nullable Event event, Entity entity, Entity sourceentity, double amount) {
      if (entity != null && sourceentity != null) {
         if (entity instanceof FellkingbossEntity && sourceentity instanceof Player) {
            sourceentity.m_6469_(DamageSource.f_19318_, (float)(amount - amount / 2.2));
         }
      }
   }
}
