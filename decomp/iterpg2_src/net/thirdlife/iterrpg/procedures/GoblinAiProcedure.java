package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.entity.GoblinWarriorEntity;

public class GoblinAiProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (!world.m_6443_(GoblinWarriorEntity.class, AABB.m_165882_(new Vec3(x, y, z), 32.0, 32.0, 32.0), e -> true).isEmpty()
            && world.m_6443_(GoblinWarriorEntity.class, AABB.m_165882_(new Vec3(x, y, z), 16.0, 16.0, 16.0), e -> true).isEmpty()
            && entity instanceof Mob _entity) {
            _entity.m_21573_()
               .m_26519_(
                  world.m_6443_(GoblinWarriorEntity.class, AABB.m_165882_(new Vec3(x, y, z), 32.0, 32.0, 32.0), e -> true).stream().sorted((new Object() {
                     Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                        return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
                     }
                  }).compareDistOf(x, y, z)).findFirst().orElse(null).m_20185_() + (double)Mth.m_216271_(RandomSource.m_216327_(), -4, 4),
                  world.m_6443_(GoblinWarriorEntity.class, AABB.m_165882_(new Vec3(x, y, z), 32.0, 32.0, 32.0), e -> true).stream().sorted((new Object() {
                     Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                        return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
                     }
                  }).compareDistOf(x, y, z)).findFirst().orElse(null).m_20186_(),
                  world.m_6443_(GoblinWarriorEntity.class, AABB.m_165882_(new Vec3(x, y, z), 32.0, 32.0, 32.0), e -> true).stream().sorted((new Object() {
                     Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                        return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
                     }
                  }).compareDistOf(x, y, z)).findFirst().orElse(null).m_20189_() + (double)Mth.m_216271_(RandomSource.m_216327_(), -4, 4),
                  1.0
               );
         }

         CoinTimerTickProcedure.execute(world, x, y, z, entity);
      }
   }
}
