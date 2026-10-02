package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.entity.WindswirlEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;

public class AirElementalAttackProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         double attack = 0.0;
         double timer = 0.0;
         double particle = 0.0;
         double fireforce = 0.0;
         double xpos = 0.0;
         double ypos = 0.0;
         double zpos = 0.0;
         double yspawn = 0.0;
         boolean shouldtick = false;
         boolean shouldspawn = false;
         AirElementalPoofProcedure.execute(world, x, y, z, entity);
         if (entity.getPersistentData().m_128459_("attack") >= 100.0) {
            entity.getPersistentData().m_128347_("attack", 0.0);
            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123759_, x, y + 0.4, z, 32, 0.25, 0.25, 0.25, 0.05);
            }

            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = new WindswirlEntity((EntityType<WindswirlEntity>)IterRpgModEntities.WINDSWIRL.get(), _level);
               entityToSpawn.m_7678_(entity.m_20185_(), entity.m_20186_() + 0.6, entity.m_20189_(), world.m_213780_().m_188501_() * 360.0F, 0.0F);
               if (entityToSpawn instanceof Mob _mobToSpawn) {
                  _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
               }

               world.m_7967_(entityToSpawn);
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
