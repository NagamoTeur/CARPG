package net.thirdlife.iterrpg.procedures;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.entity.FireballProjectileEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;

public class FireElementalBurstProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      for (int index0 = 0; index0 < Mth.m_216271_(RandomSource.m_216327_(), 8, 12); index0++) {
         if (world instanceof ServerLevel projectileLevel) {
            Projectile _entityToSpawn = (new Object() {
                  public Projectile getArrow(Level level, float damage, int knockback) {
                     AbstractArrow entityToSpawn = new FireballProjectileEntity(
                        (EntityType<? extends FireballProjectileEntity>)IterRpgModEntities.FIREBALL_PROJECTILE.get(), level
                     );
                     entityToSpawn.m_36781_((double)damage);
                     entityToSpawn.m_36735_(knockback);
                     entityToSpawn.m_20225_(true);
                     return entityToSpawn;
                  }
               })
               .getArrow(projectileLevel, 7.0F, 1);
            _entityToSpawn.m_6034_(x, y + 0.5, z);
            _entityToSpawn.m_6686_(
               Mth.m_216263_(RandomSource.m_216327_(), -0.25, 0.25),
               1.0,
               Mth.m_216263_(RandomSource.m_216327_(), -0.25, 0.25),
               (float)Mth.m_216263_(RandomSource.m_216327_(), 0.4, 0.6),
               15.0F
            );
            projectileLevel.m_7967_(_entityToSpawn);
         }
      }
   }
}
