package net.thirdlife.iterrpg.procedures;

import com.mojang.util.UUIDTypeAdapter;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.entity.BloatedEntity;
import net.thirdlife.iterrpg.entity.DropletProjectileEntity;
import net.thirdlife.iterrpg.entity.InsatiableEntity;
import net.thirdlife.iterrpg.entity.ScallopEntity;
import net.thirdlife.iterrpg.entity.WaterElementalEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class BlobExplodeProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         double damage = 0.0;
         double attacktype = 0.0;
         if (entity.getPersistentData().m_128459_("timer") >= entity.getPersistentData().m_128459_("deathtime")
            && entity.getPersistentData().m_128459_("timer") >= 2.0) {
            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123772_, x, y, z, 16, 0.5, 0.5, 0.5, 0.025);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123772_, x, y, z, 16, 1.0, 1.0, 1.0, 0.025);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123769_, x, y, z, 16, 1.0, 1.0, 1.0, 0.025);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.ELEMENTAL_DROPLET.get(), x, y, z, 16, 1.0, 1.0, 1.0, 0.025);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123804_, x, y, z, 16, 1.0, 1.0, 1.0, 0.25);
            }

            for (int index0 = 0; index0 < 8; index0++) {
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
                     .getArrow(projectileLevel, 1.0F, 0);
                  _entityToSpawn.m_6034_(x, y, z);
                  _entityToSpawn.m_6686_(
                     Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0),
                     Mth.m_216263_(RandomSource.m_216327_(), 0.0, 1.0),
                     Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0),
                     (float)Mth.m_216263_(RandomSource.m_216327_(), 0.25, 0.32),
                     0.0F
                  );
                  projectileLevel.m_7967_(_entityToSpawn);
               }
            }

            if (entity.getPersistentData().m_128459_("timer") >= entity.getPersistentData().m_128459_("deathtime")
               && entity.getPersistentData().m_128471_("IsFriendly")) {
               attacktype = 0.0;
               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(128.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if (entity.getPersistentData().m_128461_("owner").equals(entityiterator.m_20149_())) {
                     attacktype = 1.0;
                  }
               }

               if (attacktype == 1.0) {
                  _center = new Vec3(x, y, z);

                  for (Entity entityiteratorx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(2.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                     .collect(Collectors.toList())) {
                     if (!entityiteratorx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                        && entityiteratorx != entity
                        && !entityiteratorx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("forge:player_allies")))
                        && !entity.getPersistentData().m_128461_("owner").equals(entityiteratorx.m_20149_())) {
                        entityiteratorx.m_6469_(
                           new EntityDamageSource(
                              "generic.player", world instanceof ServerLevel _serverLevelForGettingEntity ? (new Function<String, Entity>() {
                                 public Entity apply(String _uuidForEntity) {
                                    Entity _entityFromUUID = null;

                                    try {
                                       _entityFromUUID = _serverLevelForGettingEntity.m_8791_(UUIDTypeAdapter.fromString(_uuidForEntity));
                                    } catch (Exception var4) {
                                       _entityFromUUID = null;
                                    }

                                    return _entityFromUUID;
                                 }
                              }).apply(entity.getPersistentData().m_128461_("owner")) : null
                           ),
                           8.0F
                        );
                     }
                  }
               } else {
                  _center = new Vec3(x, y, z);

                  for (Entity entityiteratorxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(2.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                     .collect(Collectors.toList())) {
                     if (!entityiteratorxx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                        && entityiteratorxx != entity
                        && !entityiteratorxx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("forge:player_allies")))
                        && !entity.getPersistentData().m_128461_("owner").equals(entityiteratorxx.m_20149_())) {
                        entityiteratorxx.m_6469_(DamageSource.f_19318_, 8.0F);
                     }
                  }
               }

               if (!entity.f_19853_.m_5776_()) {
                  entity.m_146870_();
               }
            } else if (entity.getPersistentData().m_128459_("timer") >= entity.getPersistentData().m_128459_("deathtime")
               && !entity.getPersistentData().m_128471_("IsFriendly")) {
               attacktype = 0.0;
               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiteratorxxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(128.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if (entity.getPersistentData().m_128461_("owner").equals(entityiteratorxxx.m_20149_())) {
                     attacktype = 1.0;
                  }
               }

               if (attacktype == 1.0) {
                  _center = new Vec3(x, y, z);

                  for (Entity entityiteratorxxxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(2.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                     .collect(Collectors.toList())) {
                     if (!entityiteratorxxxx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                        && entityiteratorxxxx != entity
                        && !(entityiteratorxxxx instanceof WaterElementalEntity)
                        && !(entityiteratorxxxx instanceof BloatedEntity)
                        && !(entityiteratorxxxx instanceof InsatiableEntity)
                        && !(entityiteratorxxxx instanceof ScallopEntity)
                        && !(entityiteratorxxxx instanceof Drowned)
                        && !entity.getPersistentData().m_128461_("owner").equals(entityiteratorxxxx.m_20149_())) {
                        entityiteratorxxxx.m_6469_(
                           new EntityDamageSource(
                              "generic.player", world instanceof ServerLevel _serverLevelForGettingEntity ? (new Function<String, Entity>() {
                                 public Entity apply(String _uuidForEntity) {
                                    Entity _entityFromUUID = null;

                                    try {
                                       _entityFromUUID = _serverLevelForGettingEntity.m_8791_(UUIDTypeAdapter.fromString(_uuidForEntity));
                                    } catch (Exception var4) {
                                       _entityFromUUID = null;
                                    }

                                    return _entityFromUUID;
                                 }
                              }).apply(entity.getPersistentData().m_128461_("owner")) : null
                           ),
                           8.0F
                        );
                     }
                  }
               } else {
                  _center = new Vec3(x, y, z);

                  for (Entity entityiteratorxxxxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(2.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                     .collect(Collectors.toList())) {
                     if (!entityiteratorxxxxx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                        && entityiteratorxxxxx != entity
                        && !(entityiteratorxxxxx instanceof WaterElementalEntity)
                        && !(entityiteratorxxxxx instanceof BloatedEntity)
                        && !(entityiteratorxxxxx instanceof InsatiableEntity)
                        && !(entityiteratorxxxxx instanceof ScallopEntity)
                        && !(entityiteratorxxxxx instanceof Drowned)
                        && !entity.getPersistentData().m_128461_("owner").equals(entityiteratorxxxxx.m_20149_())) {
                        entityiteratorxxxxx.m_6469_(DamageSource.f_19318_, 8.0F);
                     }
                  }
               }

               if (!entity.f_19853_.m_5776_()) {
                  entity.m_146870_();
               }
            }

            if (!entity.f_19853_.m_5776_()) {
               entity.m_146870_();
            }
         } else {
            entity.getPersistentData().m_128347_("timer", entity.getPersistentData().m_128459_("timer") + 1.0);
         }

         if (world instanceof ServerLevel _level) {
            _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.ELEMENTAL_DROPLET.get(), x, y, z, 2, 0.25, 0.25, 0.25, 0.025);
         }

         if (entity.getPersistentData().m_128459_("timer") >= entity.getPersistentData().m_128459_("deathtime") * 0.5 && world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123769_, x, y, z, 2, 0.25, 0.25, 0.25, 0.025);
         }

         if (entity.getPersistentData().m_128459_("timer") >= entity.getPersistentData().m_128459_("deathtime") * 0.75 && world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123772_, x, y, z, 2, 0.25, 0.25, 0.25, 0.025);
         }
      }
   }
}
