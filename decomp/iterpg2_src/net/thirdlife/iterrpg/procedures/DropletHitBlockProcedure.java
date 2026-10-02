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
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.IterRpgMod;
import net.thirdlife.iterrpg.entity.DropletProjectileEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;

public class DropletHitBlockProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world.m_8055_(new BlockPos(x, y + 1.0, z)).m_60734_() == Blocks.f_50083_ || world.m_8055_(new BlockPos(x, y + 1.0, z)).m_60734_() == Blocks.f_50084_) {
         world.m_7731_(new BlockPos(x, y + 1.0, z), Blocks.f_50016_.m_49966_(), 3);
         if (world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123755_, x + 0.5, y + 1.0, z + 0.5, 8, 0.25, 0.25, 0.25, 0.15);
         }
      }

      if (world instanceof ServerLevel _level) {
         _level.m_8767_(ParticleTypes.f_123769_, x + 0.5, y + 1.0, z + 0.5, 8, 0.25, 0.25, 0.25, 0.15);
      }

      if (world instanceof ServerLevel _level) {
         _level.m_8767_(ParticleTypes.f_123772_, x + 0.5, y + 1.0, z + 0.5, 4, 0.25, 0.25, 0.25, 0.15);
      }

      if (world instanceof Level _level) {
         if (!_level.m_5776_()) {
            _level.m_5594_(
               null,
               new BlockPos(x, y, z),
               (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.splash")),
               SoundSource.NEUTRAL,
               0.25F,
               (float)Mth.m_216263_(RandomSource.m_216327_(), 2.0, 5.0)
            );
         } else {
            _level.m_7785_(
               x,
               y,
               z,
               (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.splash")),
               SoundSource.NEUTRAL,
               0.25F,
               (float)Mth.m_216263_(RandomSource.m_216327_(), 2.0, 5.0),
               false
            );
         }
      }

      if (world.m_8055_(new BlockPos(x, y, z)).m_60815_() && world.m_46859_(new BlockPos(x, y - 1.0, z)) && Mth.m_216271_(RandomSource.m_216327_(), 1, 2) == 2) {
         if (world instanceof ServerLevel _levelx) {
            _levelx.m_8767_(ParticleTypes.f_123772_, x + 0.5, y, z + 0.5, 4, 0.25, 0.25, 0.25, 0.15);
         }

         if (world instanceof ServerLevel _levelx) {
            _levelx.m_8767_(ParticleTypes.f_123761_, x + 0.5, y, z + 0.5, 4, 0.25, 0.25, 0.25, 0.15);
         }

         IterRpgMod.queueServerWork(
            Mth.m_216271_(RandomSource.m_216327_(), 10, 100),
            () -> {
               if (world instanceof ServerLevel _levelx) {
                  _levelx.m_8767_(ParticleTypes.f_123761_, x + 0.5, y, z + 0.5, 4, 0.25, 0.25, 0.25, 0.15);
               }

               if (world instanceof ServerLevel projectileLevel) {
                  Projectile _entityToSpawn = (new Object() {
                        public Projectile getArrow(Level level, float damage, int knockback, byte piercing) {
                           AbstractArrow entityToSpawn = new DropletProjectileEntity(
                              (EntityType<? extends DropletProjectileEntity>)IterRpgModEntities.DROPLET_PROJECTILE.get(), level
                           );
                           entityToSpawn.m_36781_((double)damage);
                           entityToSpawn.m_36735_(knockback);
                           entityToSpawn.m_20225_(true);
                           entityToSpawn.m_36767_(piercing);
                           return entityToSpawn;
                        }
                     })
                     .getArrow(projectileLevel, 5.0F, 1, (byte)1);
                  _entityToSpawn.m_6034_(
                     x + Mth.m_216263_(RandomSource.m_216327_(), -1.0, 2.0), y - 0.25, z + Mth.m_216263_(RandomSource.m_216327_(), -1.0, 2.0)
                  );
                  _entityToSpawn.m_6686_(0.0, -1.0, 0.0, 0.0F, 5.0F);
                  projectileLevel.m_7967_(_entityToSpawn);
               }
            }
         );
      }
   }
}
