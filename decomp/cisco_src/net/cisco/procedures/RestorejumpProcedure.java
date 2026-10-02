package net.cisco.procedures;

import javax.annotation.Nullable;
import net.cisco.CiscoModMod;
import net.cisco.network.CiscoModModVariables;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class RestorejumpProcedure {
   @SubscribeEvent
   public static void onEntityFall(LivingFallEvent event) {
      if (event != null && event.getEntity() != null) {
         execute(event, event.getEntity().f_19853_, event.getEntity());
      }
   }

   public static void execute(LevelAccessor world, Entity entity) {
      execute(null, world, entity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
      if (entity != null) {
         if (entity instanceof Player) {
            boolean _setval = false;
            entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.jumpvariable = _setval;
               capability.syncPlayerVariables(entity);
            });
            if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
               _entity.m_7292_(new MobEffectInstance(MobEffects.f_19603_, 15, 0, true, false));
            }
         }

         CiscoModMod.queueServerWork(15, () -> {
         });
         if (entity instanceof LivingEntity _entity) {
            _entity.m_21195_(MobEffects.f_19603_);
         }
      }
   }
}
