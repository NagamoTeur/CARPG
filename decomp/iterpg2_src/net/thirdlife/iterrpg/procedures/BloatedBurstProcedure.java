package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.entity.DropletProjectileEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;

public class BloatedBurstProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world instanceof Level _level) {
         if (!_level.m_5776_()) {
            _level.m_5594_(
               null,
               new BlockPos(x, y, z),
               (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.splash")),
               SoundSource.NEUTRAL,
               1.0F,
               1.0F
            );
         } else {
            _level.m_7785_(
               x,
               y,
               z,
               (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.splash")),
               SoundSource.NEUTRAL,
               1.0F,
               1.0F,
               false
            );
         }
      }

      if (world instanceof ServerLevel _levelx) {
         _levelx.m_8767_(ParticleTypes.f_123769_, x, y + 1.0, z, 24, 0.4, 0.4, 0.4, 0.0);
      }

      if (world instanceof ServerLevel _levelx) {
         _levelx.m_8767_(ParticleTypes.f_123772_, x, y + 1.0, z, 24, 0.4, 0.4, 0.4, 0.025);
      }

      for (int index0 = 0; index0 < 16; index0++) {
         if (world instanceof ServerLevel projectileLevel) {
            Projectile _entityToSpawn = (new Object() {
                  public Projectile getArrow(Level level, float damage, int knockback) {
                     AbstractArrow entityToSpawn = new DropletProjectileEntity(
                        (EntityType<? extends DropletProjectileEntity>)IterRpgModEntities.DROPLET_PROJECTILE.get(), level
                     );
                     entityToSpawn.m_36781_((double)damage);
                     entityToSpawn.m_36735_(knockback);
                     entityToSpawn.m_20225_(true);
                     return entityToSpawn;
                  }
               })
               .getArrow(projectileLevel, 3.0F, 1);
            _entityToSpawn.m_6034_(x, y + 0.4, z);
            _entityToSpawn.m_6686_(0.0, 1.0, 0.0, (float)Mth.m_216263_(RandomSource.m_216327_(), 0.75, 1.2), 5.0F);
            projectileLevel.m_7967_(_entityToSpawn);
         }
      }

      for (int index1 = 0; index1 < 8; index1++) {
         if (world instanceof ServerLevel projectileLevel) {
            Projectile _entityToSpawn = (new Object() {
                  public Projectile getArrow(Level level, float damage, int knockback) {
                     AbstractArrow entityToSpawn = new DropletProjectileEntity(
                        (EntityType<? extends DropletProjectileEntity>)IterRpgModEntities.DROPLET_PROJECTILE.get(), level
                     );
                     entityToSpawn.m_36781_((double)damage);
                     entityToSpawn.m_36735_(knockback);
                     entityToSpawn.m_20225_(true);
                     return entityToSpawn;
                  }
               })
               .getArrow(projectileLevel, 3.0F, 1);
            _entityToSpawn.m_6034_(x, y + 0.4, z);
            _entityToSpawn.m_6686_(
               Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0),
               Mth.m_216263_(RandomSource.m_216327_(), 0.0, 1.0),
               Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0),
               (float)Mth.m_216263_(RandomSource.m_216327_(), 0.4, 0.5),
               5.0F
            );
            projectileLevel.m_7967_(_entityToSpawn);
         }
      }
   }
}
