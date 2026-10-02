package net.thirdlife.iterrpg.procedures;

import javax.annotation.Nullable;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ChainedMobTickProcedure {
   @SubscribeEvent
   public static void onEntityTick(LivingTickEvent event) {
      execute(event, event.getEntity());
   }

   public static void execute(Entity entity) {
      execute(null, entity);
   }

   private static void execute(@Nullable Event event, Entity entity) {
      if (entity != null) {
         if (entity.getPersistentData().m_128459_("chainedTime") > 1.0) {
            entity.getPersistentData().m_128347_("chainedTime", entity.getPersistentData().m_128459_("chainedTime") - 1.0);
            entity.m_6021_(
               entity.getPersistentData().m_128459_("brainX"), entity.getPersistentData().m_128459_("brainY"), entity.getPersistentData().m_128459_("brainZ")
            );
            if (entity instanceof ServerPlayer _serverPlayer) {
               _serverPlayer.f_8906_
                  .m_9774_(
                     entity.getPersistentData().m_128459_("brainX"),
                     entity.getPersistentData().m_128459_("brainY"),
                     entity.getPersistentData().m_128459_("brainZ"),
                     entity.m_146908_(),
                     entity.m_146909_()
                  );
            }

            entity.f_19789_ = 0.0F;
         }
      }
   }
}
