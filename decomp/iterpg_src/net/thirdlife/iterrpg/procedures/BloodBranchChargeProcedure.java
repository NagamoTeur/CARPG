package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.IterRpgMod;
import net.thirdlife.iterrpg.entity.DemonbloodProjectileEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;

public class BloodBranchChargeProcedure {
   public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         itemstack.m_41784_().m_128347_("CustomModelData", 0.0);
         if (entity instanceof Player _player) {
            _player.m_36335_().m_41524_(itemstack.m_41720_(), 32);
         }

         itemstack.m_41784_().m_128347_("CustomModelData", 1.0);
         IterRpgMod.queueServerWork(
            5,
            () -> {
               itemstack.m_41784_().m_128347_("CustomModelData", 2.0);
               IterRpgMod.queueServerWork(
                  5,
                  () -> {
                     itemstack.m_41784_().m_128347_("CustomModelData", 3.0);
                     IterRpgMod.queueServerWork(
                        5,
                        () -> {
                           itemstack.m_41784_().m_128347_("CustomModelData", 0.0);
                           entity.m_6469_(DamageSource.f_19319_, 1.5F);
                           Level projectileLevel = entity.f_19853_;
                           if (!projectileLevel.m_5776_()) {
                              Projectile _entityToSpawn = (new Object() {
                                    public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
                                       AbstractArrow entityToSpawn = new DemonbloodProjectileEntity(
                                          (EntityType<? extends DemonbloodProjectileEntity>)IterRpgModEntities.DEMONBLOOD_PROJECTILE.get(), level
                                       );
                                       entityToSpawn.m_5602_(shooter);
                                       entityToSpawn.m_36781_((double)damage);
                                       entityToSpawn.m_36735_(knockback);
                                       entityToSpawn.m_20225_(true);
                                       return entityToSpawn;
                                    }
                                 })
                                 .getArrow(projectileLevel, entity, 3.0F, 0);
                              _entityToSpawn.m_6034_(entity.m_20185_(), entity.m_20188_() - 0.1, entity.m_20189_());
                              _entityToSpawn.m_6686_(entity.m_20154_().f_82479_, entity.m_20154_().f_82480_, entity.m_20154_().f_82481_, 3.0F, 0.0F);
                              projectileLevel.m_7967_(_entityToSpawn);
                           }
                        }
                     );
                  }
               );
            }
         );
      }
   }
}
