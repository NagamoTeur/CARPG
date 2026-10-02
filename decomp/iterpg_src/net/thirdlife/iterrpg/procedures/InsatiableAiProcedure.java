package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.entity.BloatedEntity;
import net.thirdlife.iterrpg.entity.BlobEntity;
import net.thirdlife.iterrpg.entity.CaltropThrownEntity;
import net.thirdlife.iterrpg.entity.DropletProjectileEntity;
import net.thirdlife.iterrpg.entity.InsatiableEntity;
import net.thirdlife.iterrpg.entity.ScallopEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;

public class InsatiableAiProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         boolean shouldvomit = false;
         boolean divetp = false;
         boolean shouldtick = false;
         boolean shoulddive = false;
         double yiter = 0.0;
         double xiter = 0.0;
         double ziter = 0.0;
         double xentity = 0.0;
         double yentity = 0.0;
         double zentity = 0.0;
         double ypos = 0.0;
         double zpos = 0.0;
         double xpos = 0.0;
         double decide = 0.0;
         double mobamount = 0.0;
         double power = 0.0;
         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(16.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
            .collect(Collectors.toList())) {
            if (entityiterator == (entity instanceof Mob _mobEnt ? _mobEnt.m_5448_() : null)) {
               entity.getPersistentData().m_128347_("timer", entity.getPersistentData().m_128459_("timer") + 1.0);
            }
         }

         if (world.m_8055_(new BlockPos(x, y + 1.0, z)).m_60815_() || world.m_8055_(new BlockPos(x, y + 2.0, z)).m_60815_()) {
            entity.m_6021_(x, y + 0.25, z);
            if (entity instanceof ServerPlayer _serverPlayer) {
               _serverPlayer.f_8906_.m_9774_(x, y + 0.25, z, entity.m_146908_(), entity.m_146909_());
            }
         }

         _center = new Vec3(x, y, z);

         for (Entity entityiteratorx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(2.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
            .collect(Collectors.toList())) {
            if (entityiteratorx instanceof CaltropThrownEntity) {
               entityiteratorx.m_6469_(DamageSource.f_19318_, 8.0F);
            }
         }

         if (entity.getPersistentData().m_128459_("timer") >= entity.getPersistentData().m_128459_("cooldown")) {
            entity.getPersistentData().m_128347_("timer", 0.0);
            if (!world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 64.0, 64.0, 64.0), e -> true).isEmpty()) {
               if (entity.getPersistentData().m_128459_("decide") == 0.0) {
                  entity.getPersistentData().m_128347_("decide", 1.0);
                  entity.getPersistentData().m_128379_("vomitAttack", true);
                  if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                     _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 20, 4, false, false));
                  }

                  if (entity instanceof InsatiableEntity) {
                     ((InsatiableEntity)entity).setAnimation("animation.insatiable.vomit");
                  }

                  entity.getPersistentData().m_128347_("cooldown", 64.0);
               } else if (entity.getPersistentData().m_128459_("decide") == 1.0) {
                  entity.getPersistentData().m_128347_("decide", 2.0);
                  if (world.m_8055_(new BlockPos(x, y - 1.0, z)).m_60767_() != Material.f_164533_
                     && world.m_8055_(new BlockPos(x, y - 1.0, z)).m_60767_() != Material.f_76314_
                     && world.m_8055_(new BlockPos(x, y - 1.0, z)).m_60767_() != Material.f_76305_
                     && world.m_8055_(new BlockPos(x, y - 1.0, z)).m_60767_() != Material.f_76313_
                     && world.m_8055_(new BlockPos(x, y - 1.0, z)).m_60767_() != Material.f_76315_
                     && world.m_8055_(new BlockPos(x, y - 1.0, z)).m_60767_() != Material.f_76317_) {
                     entity.m_20256_(new Vec3(entity.m_20154_().f_82479_ * 1.5, 0.16, entity.m_20154_().f_82481_ * 1.5));
                     entity.getPersistentData().m_128347_("decide", 2.0);
                     entity.getPersistentData().m_128347_("cooldown", 16.0);
                  } else {
                     shoulddive = false;
                     _center = new Vec3(x, y, z);

                     for (Entity entityiteratorxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(16.0), e -> true)
                        .stream()
                        .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                        .collect(Collectors.toList())) {
                        if (entityiteratorxx == (entity instanceof Mob _mobEnt ? _mobEnt.m_5448_() : null)) {
                           shoulddive = true;
                        }
                     }

                     if (shoulddive) {
                        xentity = (entity instanceof Mob _mobEnt ? _mobEnt.m_5448_() : null).m_20185_();
                        yentity = (entity instanceof Mob _mobEntx ? _mobEntx.m_5448_() : null).m_20186_();
                        zentity = (entity instanceof Mob _mobEntxx ? _mobEntxx.m_5448_() : null).m_20189_();
                        entity.getPersistentData().m_128379_("diveState", true);
                        if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                           _entity.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 36, 4, false, false));
                        }

                        if (entity instanceof InsatiableEntity) {
                           ((InsatiableEntity)entity).setAnimation("animation.insatiable.dive-in");
                        }

                        entity.getPersistentData().m_128347_("cooldown", 80.0);
                     }
                  }
               } else if (entity.getPersistentData().m_128459_("decide") == 2.0) {
                  if (entity instanceof InsatiableEntity) {
                     ((InsatiableEntity)entity).setAnimation("animation.insatiable.throw");
                  }

                  entity.getPersistentData().m_128347_("decide", 3.0);
                  entity.getPersistentData().m_128379_("blobthrow", true);
                  entity.getPersistentData().m_128347_("cooldown", 32.0);
                  if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                     _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 20, 4, false, false));
                  }
               } else if (entity.getPersistentData().m_128459_("decide") == 3.0) {
                  if (entity instanceof InsatiableEntity) {
                     ((InsatiableEntity)entity).setAnimation("animation.insatiable.blobs");
                  }

                  entity.getPersistentData().m_128347_("decide", 4.0);
                  entity.getPersistentData().m_128379_("blobscatter", true);
                  entity.getPersistentData().m_128347_("cooldown", 64.0);
                  if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                     _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 64, 4, false, false));
                  }
               } else if (entity.getPersistentData().m_128459_("decide") == 4.0) {
                  entity.getPersistentData().m_128347_("decide", 5.0);
                  mobamount = 0.0;
                  _center = new Vec3(x, y, z);

                  for (Entity entityiteratorxxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(24.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                     .collect(Collectors.toList())) {
                     if (entityiteratorxxx instanceof BloatedEntity || entityiteratorxxx instanceof ScallopEntity || entityiteratorxxx instanceof Drowned) {
                        mobamount++;
                     }
                  }

                  if (mobamount < 3.0) {
                     if (world instanceof ServerLevel _serverLevelForEntitySpawn) {
                        Entity _entityForSpawning = new BloatedEntity((EntityType<BloatedEntity>)IterRpgModEntities.BLOATED.get(), _serverLevelForEntitySpawn);
                        _entityForSpawning.m_7678_(x, y + 0.16, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                        _entityForSpawning.m_20256_(
                           new Vec3(
                              Mth.m_216263_(RandomSource.m_216327_(), -0.16, 0.16),
                              Mth.m_216263_(RandomSource.m_216327_(), 0.0, 0.25),
                              Mth.m_216263_(RandomSource.m_216327_(), -0.16, 0.16)
                           )
                        );
                        if (_entityForSpawning instanceof Mob _mobForSpawning) {
                           _mobForSpawning.m_6518_(
                              _serverLevelForEntitySpawn, world.m_6436_(_entityForSpawning.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null
                           );
                        }

                        world.m_7967_(_entityForSpawning);
                     }

                     for (int index0 = 0; index0 < Mth.m_216271_(RandomSource.m_216327_(), 1, 2); index0++) {
                        if (world instanceof ServerLevel) {
                           ServerLevel _serverLevelForEntitySpawn = (ServerLevel)world;
                           Entity _entityForSpawning = new Drowned(EntityType.f_20562_, _serverLevelForEntitySpawn);
                           _entityForSpawning.m_7678_(x, y + 0.16, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                           _entityForSpawning.m_20256_(
                              new Vec3(
                                 Mth.m_216263_(RandomSource.m_216327_(), -0.16, 0.16),
                                 Mth.m_216263_(RandomSource.m_216327_(), 0.0, 0.25),
                                 Mth.m_216263_(RandomSource.m_216327_(), -0.16, 0.16)
                              )
                           );
                           if (_entityForSpawning instanceof Mob _mobForSpawning) {
                              _mobForSpawning.m_6518_(
                                 _serverLevelForEntitySpawn, world.m_6436_(_entityForSpawning.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null
                              );
                           }

                           world.m_7967_(_entityForSpawning);
                        }
                     }
                  }
               } else {
                  entity.getPersistentData().m_128347_("decide", 0.0);
                  entity.m_20256_(new Vec3(entity.m_20154_().f_82479_ * 1.5, 0.16, entity.m_20154_().f_82481_ * 1.5));
                  entity.getPersistentData().m_128347_("cooldown", 16.0);
               }
            }
         }

         if (entity.getPersistentData().m_128471_("vomitAttack")) {
            entity.getPersistentData().m_128379_("blobthrow", false);
            entity.getPersistentData().m_128379_("blobscatter", false);
            entity.getPersistentData().m_128379_("diveState", false);
            entity.getPersistentData().m_128347_("frame", entity.getPersistentData().m_128459_("frame") + 1.0);
            if (entity.getPersistentData().m_128459_("frame") >= 17.0) {
               entity.getPersistentData().m_128347_("frame", 0.0);
               entity.getPersistentData().m_128379_("vomitAttack", false);

               for (int index1 = 0; index1 < Mth.m_216271_(RandomSource.m_216327_(), 5, 6); index1++) {
                  Level projectileLevel = entity.f_19853_;
                  if (!projectileLevel.m_5776_()) {
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
                        .getArrow(projectileLevel, 6.0F, 0);
                     _entityToSpawn.m_6034_(entity.m_20185_(), entity.m_20188_() - 0.1, entity.m_20189_());
                     _entityToSpawn.m_6686_(
                        entity.m_20154_().f_82479_,
                        entity.m_20154_().f_82480_,
                        entity.m_20154_().f_82481_,
                        (float)Mth.m_216263_(RandomSource.m_216327_(), 1.0, 2.0),
                        8.0F
                     );
                     projectileLevel.m_7967_(_entityToSpawn);
                  }
               }
            }
         }

         if (entity.getPersistentData().m_128471_("diveState")) {
            entity.getPersistentData().m_128379_("vomitAttack", false);
            entity.getPersistentData().m_128379_("blobthrow", false);
            entity.getPersistentData().m_128379_("blobscatter", false);
            entity.getPersistentData().m_128347_("frame", entity.getPersistentData().m_128459_("frame") + 1.0);
            if (entity.getPersistentData().m_128459_("frame") == 30.0) {
               for (int index2 = 0; index2 < Mth.m_216271_(RandomSource.m_216327_(), 8, 12); index2++) {
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
                        .getArrow(projectileLevel, 6.0F, 0);
                     _entityToSpawn.m_6034_(x, y, z);
                     _entityToSpawn.m_6686_(0.0, 1.0, 0.0, (float)Mth.m_216263_(RandomSource.m_216327_(), 0.3, 0.8), 50.0F);
                     projectileLevel.m_7967_(_entityToSpawn);
                  }
               }
            }

            if (entity.getPersistentData().m_128459_("frame") == 32.0) {
               if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19609_, 32, 0, false, false));
               }

               if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 20, 4, false, false));
               }
            }

            if (entity.getPersistentData().m_128459_("frame") == 36.0) {
               _center = new Vec3(x, y, z);

               for (Entity entityiteratorxxxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(16.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if (entityiteratorxxxx == (entity instanceof Mob _mobEntxxx ? _mobEntxxx.m_5448_() : null)) {
                     entity.m_6021_(
                        entity.m_20185_() + ((entity instanceof Mob _mobEntxxxxxx ? _mobEntxxxxxx.m_5448_() : null).m_20185_() - entity.m_20185_()) * 0.75,
                        (entity.m_20186_() + (entity instanceof Mob _mobEntxxxxx ? _mobEntxxxxx.m_5448_() : null).m_20186_()) / 2.0,
                        entity.m_20189_() + ((entity instanceof Mob _mobEntxxxx ? _mobEntxxxx.m_5448_() : null).m_20189_() - entity.m_20189_()) * 0.75
                     );
                     if (entity instanceof ServerPlayer _serverPlayer) {
                        _serverPlayer.f_8906_
                           .m_9774_(
                              entity.m_20185_()
                                 + ((entity instanceof Mob _mobEntxxxxxxxxx ? _mobEntxxxxxxxxx.m_5448_() : null).m_20185_() - entity.m_20185_()) * 0.75,
                              (entity.m_20186_() + (entity instanceof Mob _mobEntxxxxxxxx ? _mobEntxxxxxxxx.m_5448_() : null).m_20186_()) / 2.0,
                              entity.m_20189_()
                                 + ((entity instanceof Mob _mobEntxxxxxxx ? _mobEntxxxxxxx.m_5448_() : null).m_20189_() - entity.m_20189_()) * 0.75,
                              entity.m_146908_(),
                              entity.m_146909_()
                           );
                     }
                  }
               }
            }

            if (entity.getPersistentData().m_128459_("frame") >= 48.0) {
               if (entity instanceof LivingEntity _entity) {
                  _entity.m_21195_(MobEffects.f_19609_);
               }

               if (entity instanceof InsatiableEntity) {
                  ((InsatiableEntity)entity).setAnimation("animation.insatiable.dive-out");
               }

               for (int index3 = 0; index3 < Mth.m_216271_(RandomSource.m_216327_(), 8, 12); index3++) {
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
                        .getArrow(projectileLevel, 6.0F, 0);
                     _entityToSpawn.m_6034_(x, y, z);
                     _entityToSpawn.m_6686_(0.0, 1.0, 0.0, (float)Mth.m_216263_(RandomSource.m_216327_(), 0.3, 0.8), 50.0F);
                     projectileLevel.m_7967_(_entityToSpawn);
                  }
               }

               entity.getPersistentData().m_128379_("diveState", false);
               entity.getPersistentData().m_128347_("frame", 0.0);
            }
         }

         if (entity.getPersistentData().m_128471_("blobthrow")) {
            entity.getPersistentData().m_128379_("diveState", false);
            entity.getPersistentData().m_128379_("vomitAttack", false);
            entity.getPersistentData().m_128379_("blobscatter", false);
            entity.getPersistentData().m_128347_("frame", entity.getPersistentData().m_128459_("frame") + 1.0);
            if (entity.getPersistentData().m_128459_("frame") >= 12.0) {
               entity.getPersistentData().m_128347_("frame", 0.0);
               entity.getPersistentData().m_128379_("blobthrow", false);
               if (Mth.m_216271_(RandomSource.m_216327_(), 1, 2) == 2) {
                  if (world instanceof ServerLevel _serverLevelForEntitySpawn) {
                     Entity _entityForSpawning = new ScallopEntity((EntityType<ScallopEntity>)IterRpgModEntities.SCALLOP.get(), _serverLevelForEntitySpawn);
                     _entityForSpawning.m_7678_(x, y + 0.9, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                     _entityForSpawning.m_20256_(new Vec3(entity.m_20154_().f_82479_ * 1.5, entity.m_20154_().f_82480_ * 1.5, entity.m_20154_().f_82481_ * 1.5));
                     if (_entityForSpawning instanceof Mob _mobForSpawning) {
                        _mobForSpawning.m_6518_(_serverLevelForEntitySpawn, world.m_6436_(_entityForSpawning.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                     }

                     world.m_7967_(_entityForSpawning);
                  }
               } else {
                  power = 1.0;

                  for (int index4 = 0; index4 < 5; index4++) {
                     if (world instanceof ServerLevel _serverLevelForEntitySpawn) {
                        Entity _entityForSpawning = new BlobEntity((EntityType<BlobEntity>)IterRpgModEntities.BLOB.get(), _serverLevelForEntitySpawn);
                        _entityForSpawning.m_7678_(x, y + 0.9, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                        _entityForSpawning.m_20256_(
                           new Vec3(
                              entity.m_20154_().f_82479_ * power * (double)Mth.m_216271_(RandomSource.m_216327_(), 0, 1),
                              entity.m_20154_().f_82480_ * power * (double)Mth.m_216271_(RandomSource.m_216327_(), 0, 1),
                              entity.m_20154_().f_82481_ * power * (double)Mth.m_216271_(RandomSource.m_216327_(), 0, 1)
                           )
                        );
                        _entityForSpawning.getPersistentData().m_128379_("IsFriendly", false);
                        _entityForSpawning.getPersistentData().m_128359_("owner", entity.m_20149_());
                        _entityForSpawning.getPersistentData().m_128347_("deathtime", (double)Mth.m_216271_(RandomSource.m_216327_(), 48, 64));
                        if (_entityForSpawning instanceof Mob _mobForSpawning) {
                           _mobForSpawning.m_6518_(
                              _serverLevelForEntitySpawn, world.m_6436_(_entityForSpawning.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null
                           );
                        }

                        world.m_7967_(_entityForSpawning);
                     }

                     power++;
                  }
               }
            }
         }

         if (entity.getPersistentData().m_128471_("blobscatter")) {
            entity.getPersistentData().m_128379_("diveState", false);
            entity.getPersistentData().m_128379_("vomitAttack", false);
            entity.getPersistentData().m_128379_("blobthrow", false);
            entity.getPersistentData().m_128347_("frame", entity.getPersistentData().m_128459_("frame") + 1.0);
            if (entity.getPersistentData().m_128459_("frame") >= 28.0) {
               entity.getPersistentData().m_128347_("frame", 0.0);
               entity.getPersistentData().m_128379_("blobscatter", false);

               for (int index5 = 0; index5 < 16; index5++) {
                  if (world instanceof ServerLevel) {
                     ServerLevel _serverLevelForEntitySpawn = (ServerLevel)world;
                     Entity _entityForSpawning = new BlobEntity((EntityType<BlobEntity>)IterRpgModEntities.BLOB.get(), _serverLevelForEntitySpawn);
                     _entityForSpawning.m_7678_(x, y + 0.9, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                     _entityForSpawning.m_20256_(
                        new Vec3(
                           Mth.m_216263_(RandomSource.m_216327_(), -1.25, 1.25),
                           Mth.m_216263_(RandomSource.m_216327_(), 1.0, 2.0),
                           Mth.m_216263_(RandomSource.m_216327_(), -1.25, 1.25)
                        )
                     );
                     _entityForSpawning.getPersistentData().m_128379_("IsFriendly", false);
                     _entityForSpawning.getPersistentData().m_128359_("owner", entity.m_20149_());
                     _entityForSpawning.getPersistentData().m_128347_("deathtime", (double)Mth.m_216271_(RandomSource.m_216327_(), 64, 128));
                     if (_entityForSpawning instanceof Mob _mobForSpawning) {
                        _mobForSpawning.m_6518_(_serverLevelForEntitySpawn, world.m_6436_(_entityForSpawning.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                     }

                     world.m_7967_(_entityForSpawning);
                  }
               }
            }
         }
      }
   }
}
