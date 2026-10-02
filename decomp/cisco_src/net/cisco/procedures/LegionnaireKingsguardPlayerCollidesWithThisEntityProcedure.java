package net.cisco.procedures;

import java.util.Comparator;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LegionnaireKingsguardPlayerCollidesWithThisEntityProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      Entity var8 = world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 6.0, 6.0, 6.0), e -> true).stream().sorted((new Object() {
         Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
            return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
         }
      }).compareDistOf(x, y, z)).findFirst().orElse(null);
      if (var8 instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
         _entity.m_7292_(new MobEffectInstance(MobEffects.f_19615_, 180, 3));
      }

      var8 = world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 6.0, 6.0, 6.0), e -> true).stream().sorted((new Object() {
         Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
            return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
         }
      }).compareDistOf(x, y, z)).findFirst().orElse(null);
      if (var8 instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
         _entity.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 180, 3));
      }

      var8 = world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 6.0, 6.0, 6.0), e -> true).stream().sorted((new Object() {
         Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
            return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
         }
      }).compareDistOf(x, y, z)).findFirst().orElse(null);
      if (var8 instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
         _entity.m_7292_(new MobEffectInstance(MobEffects.f_19612_, 180, 3));
      }
   }
}
