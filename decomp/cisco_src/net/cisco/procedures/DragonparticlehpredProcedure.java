package net.cisco.procedures;

import javax.annotation.Nullable;
import net.cisco.entity.DragonSeekerMissileEntity;
import net.cisco.entity.FellkingbossEntity;
import net.cisco.entity.LegionnaireJotunnEntity;
import net.cisco.entity.LegionnaireKingsguardEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class DragonparticlehpredProcedure {
   @SubscribeEvent
   public static void onEntityAttacked(LivingHurtEvent event) {
      if (event != null && event.getEntity() != null) {
         execute(event, event.getEntity(), event.getSource().m_7639_());
      }
   }

   public static void execute(Entity entity, Entity sourceentity) {
      execute(null, entity, sourceentity);
   }

   private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
      if (entity != null && sourceentity != null) {
         if (sourceentity instanceof DragonSeekerMissileEntity
               == (double)(entity instanceof LivingEntity _livEntx ? _livEntx.m_21223_() : -1.0F)
                  > (double)(entity instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F) / 4.9
            && entity instanceof LivingEntity _entity) {
            _entity.m_21153_(
               (entity instanceof LivingEntity _livEntxxx ? _livEntxxx.m_21223_() : -1.0F)
                  - (entity instanceof LivingEntity _livEntxx ? _livEntxx.m_21233_() : -1.0F) / 10.0F
            );
         }

         if (sourceentity instanceof FellkingbossEntity
               == (double)(entity instanceof LivingEntity _livEntx ? _livEntx.m_21223_() : -1.0F)
                  > (double)(entity instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F) / 4.9
            && entity instanceof LivingEntity _entity) {
            _entity.m_21153_(
               (entity instanceof LivingEntity _livEntxxx ? _livEntxxx.m_21223_() : -1.0F)
                  - (entity instanceof LivingEntity _livEntxx ? _livEntxx.m_21233_() : -1.0F) / 14.0F
            );
         }

         if (sourceentity instanceof LegionnaireJotunnEntity
               == (double)(entity instanceof LivingEntity _livEntx ? _livEntx.m_21223_() : -1.0F)
                  > (double)(entity instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F) / 4.9
            && entity instanceof LivingEntity _entity) {
            _entity.m_21153_(
               (entity instanceof LivingEntity _livEntxxx ? _livEntxxx.m_21223_() : -1.0F)
                  - (entity instanceof LivingEntity _livEntxx ? _livEntxx.m_21233_() : -1.0F) / 10.0F
            );
         }

         if (sourceentity instanceof LegionnaireKingsguardEntity
               == (double)(entity instanceof LivingEntity _livEntx ? _livEntx.m_21223_() : -1.0F)
                  > (double)(entity instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F) / 4.9
            && entity instanceof LivingEntity _entity) {
            _entity.m_21153_(
               (entity instanceof LivingEntity _livEntxxx ? _livEntxxx.m_21223_() : -1.0F)
                  - (entity instanceof LivingEntity _livEntxx ? _livEntxx.m_21233_() : -1.0F) / 10.0F
            );
         }
      }
   }
}
