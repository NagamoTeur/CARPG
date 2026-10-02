package net.cisco.procedures;

import net.cisco.entity.AfterImageEntity;
import net.cisco.entity.CiscoEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class CiscoInvunerabilityProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         double chain = 0.0;
         double ChainWait = 0.0;
         if ((entity instanceof Mob _mobEnt ? _mobEnt.m_5448_() : null) != null && entity instanceof CiscoEntity) {
            if (!world.m_6443_(AfterImageEntity.class, AABB.m_165882_(new Vec3(x, y, z), 18.0, 18.0, 18.0), e -> true).isEmpty()) {
               entity.m_20331_(true);
            } else {
               entity.m_20331_(false);
            }

            if (entity.m_20147_() && entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
               _entity.m_7292_(new MobEffectInstance(MobEffects.f_19619_, 60, 1));
            }
         }
      }
   }
}
