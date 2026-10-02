package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class WindswirlAimProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         double xpos = 0.0;
         double ypos = 0.0;
         double zpos = 0.0;
         double k = 0.0;
         if (!world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 64.0, 64.0, 64.0), e -> true).isEmpty()) {
            xpos = world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 64.0, 64.0, 64.0), e -> true).stream().sorted((new Object() {
               Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                  return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
               }
            }).compareDistOf(x, y, z)).findFirst().orElse(null).m_20185_() - entity.m_20185_();
            ypos = world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 64.0, 64.0, 64.0), e -> true).stream().sorted((new Object() {
               Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                  return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
               }
            }).compareDistOf(x, y, z)).findFirst().orElse(null).m_20186_() + 1.0 - entity.m_20186_();
            zpos = world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 64.0, 64.0, 64.0), e -> true).stream().sorted((new Object() {
               Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                  return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
               }
            }).compareDistOf(x, y, z)).findFirst().orElse(null).m_20189_() - entity.m_20189_();
            k = Math.abs(ypos) + Math.abs(zpos) + Math.abs(xpos);
            xpos /= k;
            ypos /= k;
            zpos /= k;
            entity.getPersistentData().m_128347_("xVector", xpos);
            entity.getPersistentData().m_128347_("yVector", ypos);
            entity.getPersistentData().m_128347_("zVector", zpos);
         } else if (!entity.f_19853_.m_5776_()) {
            entity.m_146870_();
         }
      }
   }
}
