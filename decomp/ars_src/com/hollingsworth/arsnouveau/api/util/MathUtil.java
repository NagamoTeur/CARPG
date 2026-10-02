package com.hollingsworth.arsnouveau.api.util;

import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class MathUtil {
   public static long NIGHT_TIME = 13000L;
   public static long DAY_TIME = 1000L;

   @Nullable
   public static EntityHitResult getLookedAtEntity(Entity entity, int range) {
      Vec3 vec3d = entity.m_20299_(1.0F);
      Vec3 vec3d1 = entity.m_20252_(1.0F);
      Vec3 vec3d2 = vec3d.m_82520_(vec3d1.f_82479_ * (double)range, vec3d1.f_82480_ * (double)range, vec3d1.f_82481_ * (double)range);
      AABB axisalignedbb = entity.m_20191_().m_82369_(vec3d1.m_82490_((double)range)).m_82377_(1.0, 1.0, 1.0);
      return traceEntities(entity, vec3d, vec3d2, axisalignedbb, e -> !e.m_5833_() && e.m_6087_(), (double)range);
   }

   @Nullable
   public static EntityHitResult traceEntities(Entity shooter, Vec3 startVec, Vec3 endVec, AABB boundingBox, Predicate<Entity> filter, double distance) {
      Level world = shooter.f_19853_;
      double d0 = distance;
      Entity entity = null;
      Vec3 vec3d = null;

      for (Entity entity1 : world.m_6249_(shooter, boundingBox, filter)) {
         AABB axisalignedbb = entity1.m_20191_().m_82400_((double)entity1.m_6143_());
         Optional<Vec3> optional = axisalignedbb.m_82371_(startVec, endVec);
         if (axisalignedbb.m_82390_(startVec)) {
            if (d0 >= 0.0) {
               entity = entity1;
               vec3d = optional.orElse(startVec);
               d0 = 0.0;
            }
         } else if (optional.isPresent()) {
            Vec3 vec3d1 = optional.get();
            double d1 = startVec.m_82557_(vec3d1);
            if (d1 < d0 || d0 == 0.0) {
               if (entity1.m_20201_() != shooter.m_20201_() || entity1.canRiderInteract()) {
                  entity = entity1;
                  vec3d = vec3d1;
                  d0 = d1;
               } else if (d0 == 0.0) {
                  entity = entity1;
                  vec3d = vec3d1;
               }
            }
         }
      }

      return entity == null ? null : new EntityHitResult(entity, vec3d);
   }

   public static long getNextDaysTime(Level world, long timeOfDay) {
      long dayTime = world.m_46468_();
      long newTime = dayTime + 24000L;
      newTime -= newTime % 24000L;
      return newTime + timeOfDay;
   }
}
