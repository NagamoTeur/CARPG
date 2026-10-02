package com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class MobUtils {
   public static void setPos(Entity entity, Vec3 vec) {
      entity.m_20248_(vec.f_82479_, vec.f_82480_, vec.f_82481_);
   }

   public static Vec3 eyePos(Entity entity) {
      return entity.m_20299_(1.0F);
   }

   public static Vec3 lastRenderPos(Entity entity) {
      return new Vec3(entity.f_19790_, entity.f_19791_, entity.f_19792_);
   }
}
