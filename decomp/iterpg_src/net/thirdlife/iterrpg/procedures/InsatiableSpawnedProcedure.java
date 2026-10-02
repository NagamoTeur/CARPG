package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.IterRpgMod;
import net.thirdlife.iterrpg.entity.InsatiableEntity;

public class InsatiableSpawnedProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
            _entity.m_7292_(new MobEffectInstance(MobEffects.f_19609_, 5, 5, false, false));
         }

         if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
            _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 80, 5, false, false));
         }

         if (entity instanceof InsatiableEntity) {
            ((InsatiableEntity)entity).setAnimation("animation.insatiable.spawn");
         }

         entity.getPersistentData().m_128347_("cooldown", 80.0);
         entity.getPersistentData().m_128347_("decide", 0.0);
         IterRpgMod.queueServerWork(1, () -> {
            if (entity instanceof LivingEntity _entityx && !_entityx.f_19853_.m_5776_()) {
               _entityx.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 80, 5, false, false));
            }

            if (entity instanceof InsatiableEntity) {
               ((InsatiableEntity)entity).setAnimation("animation.insatiable.spawn");
            }

            entity.getPersistentData().m_128347_("cooldown", 80.0);
            entity.getPersistentData().m_128347_("decide", 0.0);
         });
      }
   }
}
