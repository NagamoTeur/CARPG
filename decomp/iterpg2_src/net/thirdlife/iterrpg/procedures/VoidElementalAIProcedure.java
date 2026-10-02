package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.entity.VoidPortalEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class VoidElementalAIProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         boolean flag = false;
         boolean shouldtick = false;
         boolean shouldspawn = false;
         double yspawn = 0.0;
         double ypos = 0.0;
         double zpos = 0.0;
         double xpos = 0.0;
         double attacktype = 0.0;
         double iteration = 0.0;
         double spawnamount = 0.0;
         if (Mth.m_216271_(RandomSource.m_216327_(), 1, 2) == 1) {
            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123760_, x, y + (double)(entity.m_20206_() / 2.0F), z, 1, 0.5, 0.5, 0.5, 0.0);
            }
         } else if (world instanceof ServerLevel _level) {
            _level.m_8767_(
               (SimpleParticleType)IterRpgModParticleTypes.ELEMENTAL_VOID.get(), x, y + (double)(entity.m_20206_() / 2.0F), z, 1, 0.5, 0.5, 0.5, 0.0
            );
         }

         if (entity.getPersistentData().m_128459_("attack") >= 80.0) {
            entity.getPersistentData().m_128347_("attack", 0.0);
            spawnamount = 1.0;

            for (int index0 = 0; index0 < 8; index0++) {
               xpos = Mth.m_216263_(RandomSource.m_216327_(), -2.0, 2.0);
               ypos = Mth.m_216263_(RandomSource.m_216327_(), 0.75, 3.0);
               zpos = Mth.m_216263_(RandomSource.m_216327_(), -2.0, 2.0);
               shouldspawn = false;
               if (world.m_46859_(new BlockPos(entity.m_20185_() + xpos, entity.m_20186_() + ypos, entity.m_20189_() + zpos)) && spawnamount == 1.0) {
                  spawnamount = 0.0;
                  if (world instanceof ServerLevel) {
                     ServerLevel _level = (ServerLevel)world;
                     Entity entityToSpawn = new VoidPortalEntity((EntityType<VoidPortalEntity>)IterRpgModEntities.VOID_PORTAL.get(), _level);
                     entityToSpawn.m_7678_(
                        entity.m_20185_() + xpos, entity.m_20186_() + yspawn, entity.m_20189_() + zpos, world.m_213780_().m_188501_() * 360.0F, 0.0F
                     );
                     if (entityToSpawn instanceof Mob _mobToSpawn) {
                        _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                     }

                     world.m_7967_(entityToSpawn);
                  }

                  if (world instanceof ServerLevel _level) {
                     _level.m_8767_(
                        (SimpleParticleType)IterRpgModParticleTypes.PORTAL_SPARK_PARTICLE.get(),
                        entity.m_20185_() + xpos,
                        entity.m_20186_() + yspawn + 0.2,
                        entity.m_20189_() + zpos,
                        8,
                        0.25,
                        0.5,
                        0.25,
                        0.0025
                     );
                  }
               }
            }
         } else {
            shouldtick = false;
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(12.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiterator instanceof Player
                  && (
                     (new Object() {
                              public boolean checkGamemode(Entity _ent) {
                                 if (_ent instanceof ServerPlayer _serverPlayer) {
                                    return _serverPlayer.f_8941_.m_9290_() == GameType.SURVIVAL;
                                 } else {
                                    return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                       ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                          && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.SURVIVAL
                                       : false;
                                 }
                              }
                           })
                           .checkGamemode(entityiterator)
                        || (new Object() {
                              public boolean checkGamemode(Entity _ent) {
                                 if (_ent instanceof ServerPlayer _serverPlayer) {
                                    return _serverPlayer.f_8941_.m_9290_() == GameType.ADVENTURE;
                                 } else {
                                    return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                                       ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                          && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.ADVENTURE
                                       : false;
                                 }
                              }
                           })
                           .checkGamemode(entityiterator)
                  )) {
                  shouldtick = true;
               }
            }

            if (shouldtick) {
               entity.getPersistentData().m_128347_("attack", entity.getPersistentData().m_128459_("attack") + 1.0);
            }
         }
      }
   }
}
