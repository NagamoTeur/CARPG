package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.entity.VoidElementalEntity;

public class VoidElementalSpawnConditionProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z) {
      double count = 0.0;
      count = 0.0;
      Vec3 _center = new Vec3(x, y, z);

      for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(512.0), e -> true)
         .stream()
         .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
         .collect(Collectors.toList())) {
         if (entityiterator instanceof VoidElementalEntity) {
            count++;
         }
      }

      return Math.abs(x) + Math.abs(z) > 1250.0 && (world instanceof Level _lvl ? _lvl.m_46472_() : Level.f_46428_) == Level.f_46430_ && count <= 1.0;
   }
}
