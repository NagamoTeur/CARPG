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

public class DriftbowBurstBlockProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world instanceof Level _level) {
         if (!_level.m_5776_()) {
            _level.m_5594_(
               null,
               new BlockPos(x, y, z),
               (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.splash")),
               SoundSource.PLAYERS,
               1.0F,
               1.0F
            );
         } else {
            _level.m_7785_(
               x,
               y,
               z,
               (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.splash")),
               SoundSource.PLAYERS,
               1.0F,
               1.0F,
               false
            );
         }
      }

      if (world instanceof ServerLevel _levelx) {
         _levelx.m_8767_(ParticleTypes.f_123769_, x + 0.5, y + 0.5, z + 0.5, 8, 0.25, 0.25, 0.25, 0.0);
      }

      if (world instanceof ServerLevel _levelx) {
         _levelx.m_8767_(ParticleTypes.f_123772_, x + 0.5, y + 0.5, z + 0.5, 8, 0.25, 0.25, 0.25, 0.025);
      }

      for (int index0 = 0; index0 < 6; index0++) {
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
               .getArrow(projectileLevel, 3.0F, 0, (byte)1);
            _entityToSpawn.m_6034_(x + 0.5, y + 1.1, z + 0.5);
            _entityToSpawn.m_6686_(
               Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0),
               Mth.m_216263_(RandomSource.m_216327_(), -0.25, 2.0),
               Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0),
               (float)Mth.m_216263_(RandomSource.m_216327_(), 0.25, 0.4),
               5.0F
            );
            projectileLevel.m_7967_(_entityToSpawn);
         }
      }
   }
}
