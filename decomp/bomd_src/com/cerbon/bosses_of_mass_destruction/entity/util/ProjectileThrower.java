package com.cerbon.bosses_of_mass_destruction.entity.util;

import java.util.function.Supplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;

public class ProjectileThrower {
   private final Supplier<ProjectileThrower.ProjectileData> projectileProvider;

   public ProjectileThrower(Supplier<ProjectileThrower.ProjectileData> projectileProvider) {
      this.projectileProvider = projectileProvider;
   }

   public void throwProjectile(Vec3 target) {
      ProjectileThrower.ProjectileData projectileData = this.projectileProvider.get();
      Vec3 direction = target.m_82546_(projectileData.projectile().m_20182_());
      double h = Math.sqrt(direction.f_82479_ * direction.f_82479_ + direction.f_82481_ * direction.f_82481_) * projectileData.gravityCompensation();
      projectileData.projectile().m_6686_(direction.f_82479_, direction.f_82480_ + h, direction.f_82481_, projectileData.speed(), projectileData.divergence());
      projectileData.projectile().f_19853_.m_7967_(projectileData.projectile());
   }

   public static record ProjectileData(Projectile projectile, float speed, float divergence, double gravityCompensation) {
   }
}
