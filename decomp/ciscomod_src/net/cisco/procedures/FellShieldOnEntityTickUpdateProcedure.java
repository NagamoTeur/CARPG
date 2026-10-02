package net.cisco.procedures;

import java.util.Comparator;
import net.cisco.entity.FellkingbossEntity;
import net.cisco.network.CiscoModModVariables;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class FellShieldOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (!world.m_6443_(FellkingbossEntity.class, AABB.m_165882_(new Vec3(x, y, z), 20.0, 20.0, 20.0), e -> true).isEmpty()) {
            entity.m_6021_(
               world.m_6443_(FellkingbossEntity.class, AABB.m_165882_(new Vec3(x, y, z), 20.0, 20.0, 20.0), e -> true).stream().sorted((new Object() {
                  Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                     return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
                  }
               }).compareDistOf(x, y, z)).findFirst().orElse(null).m_20185_(),
               world.m_6443_(FellkingbossEntity.class, AABB.m_165882_(new Vec3(x, y, z), 20.0, 20.0, 20.0), e -> true).stream().sorted((new Object() {
                  Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                     return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
                  }
               }).compareDistOf(x, y, z)).findFirst().orElse(null).m_20186_() + 5.0,
               world.m_6443_(FellkingbossEntity.class, AABB.m_165882_(new Vec3(x, y, z), 20.0, 20.0, 20.0), e -> true).stream().sorted((new Object() {
                  Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                     return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
                  }
               }).compareDistOf(x, y, z)).findFirst().orElse(null).m_20189_()
            );
            if (entity instanceof ServerPlayer _serverPlayer) {
               _serverPlayer.f_8906_
                  .m_9774_(
                     world.m_6443_(FellkingbossEntity.class, AABB.m_165882_(new Vec3(x, y, z), 20.0, 20.0, 20.0), e -> true).stream().sorted((new Object() {
                        Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                           return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
                        }
                     }).compareDistOf(x, y, z)).findFirst().orElse(null).m_20185_(),
                     world.m_6443_(FellkingbossEntity.class, AABB.m_165882_(new Vec3(x, y, z), 20.0, 20.0, 20.0), e -> true).stream().sorted((new Object() {
                        Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                           return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
                        }
                     }).compareDistOf(x, y, z)).findFirst().orElse(null).m_20186_() + 5.0,
                     world.m_6443_(FellkingbossEntity.class, AABB.m_165882_(new Vec3(x, y, z), 20.0, 20.0, 20.0), e -> true).stream().sorted((new Object() {
                        Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                           return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
                        }
                     }).compareDistOf(x, y, z)).findFirst().orElse(null).m_20189_(),
                     entity.m_146908_(),
                     entity.m_146909_()
                  );
            }
         }

         if (!CiscoModModVariables.MapVariables.get(world).FellKingLives && !entity.f_19853_.m_5776_()) {
            entity.m_146870_();
         }
      }
   }
}
