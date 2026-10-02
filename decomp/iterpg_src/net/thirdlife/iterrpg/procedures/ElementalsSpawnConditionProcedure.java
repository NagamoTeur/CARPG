package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ElementalsSpawnConditionProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z) {
      double elementsnear = 0.0;
      double elementstotal = 0.0;
      elementsnear = 0.0;
      elementstotal = 0.0;
      if (ElementalsConfigConditionProcedure.execute()) {
         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(16.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
            .collect(Collectors.toList())) {
            if (entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:elementals")))) {
               elementsnear++;
            }
         }

         _center = new Vec3(x, y, z);

         for (Entity entityiteratorx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(125.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
            .collect(Collectors.toList())) {
            if (entityiteratorx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:elementals")))) {
               elementstotal++;
            }
         }

         if (elementsnear < 1.0 && elementstotal < 2.0) {
            return true;
         }
      }

      return false;
   }
}
