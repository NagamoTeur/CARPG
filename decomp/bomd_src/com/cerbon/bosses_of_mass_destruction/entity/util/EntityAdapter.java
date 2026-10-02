package com.cerbon.bosses_of_mass_destruction.entity.util;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MobUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

public class EntityAdapter implements IEntity {
   private final LivingEntity entity;

   public EntityAdapter(LivingEntity entity) {
      this.entity = entity;
   }

   @Override
   public Vec3 getDeltaMovement() {
      return this.entity.m_20184_();
   }

   @Override
   public Vec3 getPos() {
      return this.entity.m_20182_();
   }

   @Override
   public Vec3 getEyePos() {
      return MobUtils.eyePos(this.entity);
   }

   @Override
   public Vec3 getLookAngle() {
      return this.entity.m_20154_();
   }

   @Override
   public int getTickCount() {
      return this.entity.f_19797_;
   }

   @Override
   public boolean isAlive() {
      return this.entity.m_6084_();
   }

   @Override
   public IEntity target() {
      if (this.entity instanceof Mob) {
         LivingEntity target = ((Mob)this.entity).m_5448_();
         if (target != null) {
            return new EntityAdapter(target);
         }
      }

      return null;
   }
}
