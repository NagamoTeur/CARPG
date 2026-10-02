package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class PeeperTurnBackProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (!world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 32.0, 32.0, 32.0), e -> true).isEmpty()) {
            entity.getPersistentData()
               .m_128347_("aggro", entity.getPersistentData().m_128459_("aggro") + (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 2));
         } else {
            entity.getPersistentData()
               .m_128347_("aggro", entity.getPersistentData().m_128459_("aggro") + (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 4));
         }

         if (entity.getPersistentData().m_128459_("aggro") >= 128.0
            && !world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 32.0, 32.0, 32.0), e -> true).isEmpty()) {
            if (entity instanceof Mob _entity) {
               Entity var10 = world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 32.0, 32.0, 32.0), e -> true).stream().sorted((new Object() {
                  Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                     return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
                  }
               }).compareDistOf(x, y, z)).findFirst().orElse(null);
               if (var10 instanceof LivingEntity _ent) {
                  _entity.m_6710_(_ent);
               }
            }

            if (entity instanceof Mob _entityx) {
               _entityx.m_21573_()
                  .m_26519_(
                     world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 32.0, 32.0, 32.0), e -> true).stream().sorted((new Object() {
                        Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                           return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
                        }
                     }).compareDistOf(x, y, z)).findFirst().orElse(null).m_20185_(),
                     world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 32.0, 32.0, 32.0), e -> true).stream().sorted((new Object() {
                        Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                           return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
                        }
                     }).compareDistOf(x, y, z)).findFirst().orElse(null).m_20186_(),
                     world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 32.0, 32.0, 32.0), e -> true).stream().sorted((new Object() {
                        Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                           return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
                        }
                     }).compareDistOf(x, y, z)).findFirst().orElse(null).m_20189_(),
                     1.2
                  );
            }

            entity.getPersistentData().m_128347_("aggro", 0.0);
         }
      }
   }
}
