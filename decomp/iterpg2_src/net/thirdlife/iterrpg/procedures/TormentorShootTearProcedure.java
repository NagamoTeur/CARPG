package net.thirdlife.iterrpg.procedures;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.thirdlife.iterrpg.entity.WeeperTearEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;

public class TormentorShootTearProcedure {
   public static void execute(Entity entity, ItemStack itemstack) {
      if (entity != null) {
         if (itemstack.m_41784_().m_128459_("TearCharge") > 0.0) {
            Level projectileLevel = entity.f_19853_;
            if (!projectileLevel.m_5776_()) {
               Projectile _entityToSpawn = (new Object() {
                  public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
                     AbstractArrow entityToSpawn = new WeeperTearEntity((EntityType<? extends WeeperTearEntity>)IterRpgModEntities.WEEPER_TEAR.get(), level);
                     entityToSpawn.m_5602_(shooter);
                     entityToSpawn.m_36781_((double)damage);
                     entityToSpawn.m_36735_(knockback);
                     entityToSpawn.m_20225_(true);
                     return entityToSpawn;
                  }
               }).getArrow(projectileLevel, entity, 1.0F, 0);
               _entityToSpawn.m_6034_(entity.m_20185_(), entity.m_20188_() - 0.1, entity.m_20189_());
               _entityToSpawn.m_6686_(entity.m_20154_().f_82479_, entity.m_20154_().f_82480_, entity.m_20154_().f_82481_, 2.25F, 0.0F);
               projectileLevel.m_7967_(_entityToSpawn);
            }

            for (int index0 = 0; index0 < (int)((itemstack.m_41784_().m_128459_("TearCharge") - 1.0) * 2.0); index0++) {
               Level projectileLevelx = entity.f_19853_;
               if (!projectileLevelx.m_5776_()) {
                  Projectile _entityToSpawn = (new Object() {
                     public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
                        AbstractArrow entityToSpawn = new WeeperTearEntity((EntityType<? extends WeeperTearEntity>)IterRpgModEntities.WEEPER_TEAR.get(), level);
                        entityToSpawn.m_5602_(shooter);
                        entityToSpawn.m_36781_((double)damage);
                        entityToSpawn.m_36735_(knockback);
                        entityToSpawn.m_20225_(true);
                        return entityToSpawn;
                     }
                  }).getArrow(projectileLevelx, entity, 1.0F, 0);
                  _entityToSpawn.m_6034_(entity.m_20185_(), entity.m_20188_() - 0.1, entity.m_20189_());
                  _entityToSpawn.m_6686_(
                     entity.m_20154_().f_82479_,
                     entity.m_20154_().f_82480_,
                     entity.m_20154_().f_82481_,
                     (float)Mth.m_216263_(RandomSource.m_216327_(), 1.75, 2.5),
                     12.0F
                  );
                  projectileLevelx.m_7967_(_entityToSpawn);
               }
            }

            itemstack.m_41784_().m_128347_("TearCharge", 0.0);
            if (itemstack.m_220157_(Mth.m_216271_(RandomSource.m_216327_(), 1, 2), RandomSource.m_216327_(), null)) {
               itemstack.m_41774_(1);
               itemstack.m_41721_(0);
            }
         }
      }
   }
}
