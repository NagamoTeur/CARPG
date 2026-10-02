package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.entity.ScallopEntity;

public class ScallopSpawnConditionProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z) {
      double count = 0.0;
      if (world.m_204166_(new BlockPos(x, y, z)).m_203656_(TagKey.m_203882_(Registry.f_122885_, new ResourceLocation("iter_rpg:scallop_biomes")))
         && y > 0.0
         && y < 80.0) {
         count = 0.0;
         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(32.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
            .collect(Collectors.toList())) {
            if (entityiterator instanceof ScallopEntity) {
               count++;
            }
         }

         if (count <= 6.0) {
            return true;
         }
      }

      return false;
   }
}
