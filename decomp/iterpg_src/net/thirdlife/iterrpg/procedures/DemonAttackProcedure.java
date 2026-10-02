package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.entity.CarcassEntity;
import net.thirdlife.iterrpg.entity.DemonspineEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class DemonAttackProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         boolean shouldtick = false;
         boolean shouldspawn = false;
         boolean dospawn = false;
         double attack = 0.0;
         double timer = 0.0;
         double particle = 0.0;
         double fireforce = 0.0;
         double xpos = 0.0;
         double ypos = 0.0;
         double zpos = 0.0;
         double yspawn = 0.0;
         double distance = 0.0;
         double yfinal = 0.0;
         if (entity.getPersistentData().m_128459_("manum") >= 100.0) {
            entity.getPersistentData().m_128347_("manum", 0.0);
            attack = (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 5);
            if (attack == 1.0) {
               entity.getPersistentData().m_128347_("manum", 0.0);
               if (world instanceof Level _level) {
                  if (!_level.m_5776_()) {
                     _level.m_5594_(
                        null,
                        new BlockPos(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_elytra")),
                        SoundSource.HOSTILE,
                        1.0F,
                        0.7F
                     );
                  } else {
                     _level.m_7785_(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_elytra")),
                        SoundSource.HOSTILE,
                        1.0F,
                        0.7F,
                        false
                     );
                  }
               }

               if (world instanceof ServerLevel _levelx) {
                  _levelx.m_8767_(ParticleTypes.f_123744_, x, y + 1.6, z, 64, 0.25, 0.5, 0.25, 0.25);
               }

               if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 30, 0, true, true));
               }

               entity.m_20256_(new Vec3(2.6 * entity.m_20154_().f_82479_, 2.6 * entity.m_20154_().f_82480_ + 0.26, 2.6 * entity.m_20154_().f_82481_));
            } else if (attack == 2.0) {
               entity.getPersistentData().m_128347_("manum", 0.0);
               if (world instanceof Level _levelx) {
                  if (!_levelx.m_5776_()) {
                     _levelx.m_5594_(
                        null,
                        new BlockPos(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.elder_guardian.curse")),
                        SoundSource.HOSTILE,
                        1.0F,
                        0.7F
                     );
                  } else {
                     _levelx.m_7785_(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.elder_guardian.curse")),
                        SoundSource.HOSTILE,
                        1.0F,
                        0.7F,
                        false
                     );
                  }
               }

               if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 20, 8, true, false));
               }

               if (entity instanceof CarcassEntity) {
                  ((CarcassEntity)entity).setAnimation("animation.carcass.evoke");
               }

               if (world instanceof ServerLevel _levelxx) {
                  _levelxx.m_8767_((SimpleParticleType)IterRpgModParticleTypes.DEMONBLOOD.get(), x, y + 1.6, z, 64, 0.25, 0.5, 0.25, 0.25);
               }

               for (int index0 = 0; index0 < 16; index0++) {
                  xpos = Mth.m_216263_(RandomSource.m_216327_(), -6.0, 6.0);
                  ypos = -4.0;
                  zpos = Mth.m_216263_(RandomSource.m_216327_(), -6.0, 6.0);
                  shouldspawn = false;

                  for (int index1 = 0; index1 < 8; index1++) {
                     if (world.m_46859_(new BlockPos(x + xpos, y + ypos, z + zpos))
                        && world.m_8055_(new BlockPos(x + xpos, y + ypos - 1.0, z + zpos)).m_60815_()) {
                        yspawn = ypos;
                        shouldspawn = true;
                     }

                     ypos++;
                  }

                  if (shouldspawn && world instanceof ServerLevel _serverLevelForEntitySpawn) {
                     Entity _entityForSpawning = new DemonspineEntity(
                        (EntityType<DemonspineEntity>)IterRpgModEntities.DEMONSPINE.get(), _serverLevelForEntitySpawn
                     );
                     _entityForSpawning.m_7678_(x + xpos, y + yspawn, z + zpos, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                     _entityForSpawning.getPersistentData().m_128379_("friendly", false);
                     _entityForSpawning.getPersistentData().m_128359_("owner", entity.m_20149_());
                     if (_entityForSpawning instanceof Mob _mobForSpawning) {
                        _mobForSpawning.m_6518_(_serverLevelForEntitySpawn, world.m_6436_(_entityForSpawning.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                     }

                     world.m_7967_(_entityForSpawning);
                  }
               }
            } else if (attack == 3.0) {
               entity.getPersistentData().m_128347_("manum", 0.0);
               if (world instanceof ServerLevel _levelxx) {
                  _levelxx.m_8767_(ParticleTypes.f_123744_, x, y + 1.6, z, 128, 0.25, 0.5, 0.25, 0.36);
               }

               if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 16, 8, true, false));
               }

               if (world instanceof Level _levelxx) {
                  if (!_levelxx.m_5776_()) {
                     _levelxx.m_5594_(
                        null,
                        new BlockPos(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use")),
                        SoundSource.HOSTILE,
                        1.0F,
                        0.7F
                     );
                  } else {
                     _levelxx.m_7785_(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use")),
                        SoundSource.HOSTILE,
                        1.0F,
                        0.7F,
                        false
                     );
                  }
               }

               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(8.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if (!entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))) {
                     entityiterator.m_20254_(8);
                  }
               }
            } else if (attack == 4.0) {
               entity.getPersistentData().m_128347_("manum", 0.0);
               if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 20, 8, true, false));
               }

               if (entity instanceof CarcassEntity) {
                  ((CarcassEntity)entity).setAnimation("animation.carcass.evoke");
               }

               xpos = Mth.m_216263_(RandomSource.m_216327_(), -2.0, 2.0);
               zpos = Mth.m_216263_(RandomSource.m_216327_(), -2.0, 2.0);
               if (world instanceof Level _levelxxx) {
                  if (!_levelxxx.m_5776_()) {
                     _levelxxx.m_5594_(
                        null,
                        new BlockPos(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.enchantment_table.use")),
                        SoundSource.HOSTILE,
                        0.6F,
                        0.7F
                     );
                  } else {
                     _levelxxx.m_7785_(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.enchantment_table.use")),
                        SoundSource.HOSTILE,
                        0.6F,
                        0.7F,
                        false
                     );
                  }
               }

               if (Mth.m_216271_(RandomSource.m_216327_(), 0, 1) == 1) {
                  if (world instanceof ServerLevel _levelxxxx) {
                     Entity entityToSpawn = new WitherSkeleton(EntityType.f_20497_, _levelxxxx);
                     entityToSpawn.m_7678_(x + xpos, y, z + zpos, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                     if (entityToSpawn instanceof Mob _mobToSpawn) {
                        _mobToSpawn.m_6518_(_levelxxxx, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                     }

                     world.m_7967_(entityToSpawn);
                  }

                  if (world instanceof ServerLevel _levelxxxx) {
                     _levelxxxx.m_8767_(ParticleTypes.f_123744_, x + xpos, y + 1.0, z + zpos, 32, 0.25, 0.3, 0.25, 0.025);
                  }
               } else {
                  if (world instanceof ServerLevel _levelxxxx) {
                     Entity entityToSpawn = new Skeleton(EntityType.f_20524_, _levelxxxx);
                     entityToSpawn.m_7678_(x + xpos, y, z + zpos, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                     if (entityToSpawn instanceof Mob _mobToSpawn) {
                        _mobToSpawn.m_6518_(_levelxxxx, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                     }

                     world.m_7967_(entityToSpawn);
                  }

                  if (world instanceof ServerLevel _levelxxxx) {
                     _levelxxxx.m_8767_(ParticleTypes.f_123744_, x + xpos, y + 1.0, z + zpos, 32, 0.25, 0.3, 0.25, 0.025);
                  }
               }
            } else if (attack == 5.0) {
               entity.getPersistentData().m_128347_("barrage", 24.0);
            }
         } else {
            shouldtick = false;
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiteratorx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(16.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiteratorx == (entity instanceof Mob _mobEnt ? _mobEnt.m_5448_() : null)) {
                  shouldtick = true;
               }
            }

            if (shouldtick) {
               entity.getPersistentData().m_128347_("manum", entity.getPersistentData().m_128459_("manum") + 1.0);
               if (entity.getPersistentData().m_128459_("barrage") >= 1.0 && Mth.m_216271_(RandomSource.m_216327_(), 0, 2) == 1) {
                  if (world instanceof Level _levelxxxx) {
                     if (!_levelxxxx.m_5776_()) {
                        _levelxxxx.m_5594_(
                           null,
                           new BlockPos(x, y, z),
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use")),
                           SoundSource.HOSTILE,
                           1.0F,
                           1.3F
                        );
                     } else {
                        _levelxxxx.m_7785_(
                           x,
                           y,
                           z,
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use")),
                           SoundSource.HOSTILE,
                           1.0F,
                           1.3F,
                           false
                        );
                     }
                  }

                  if (world instanceof ServerLevel projectileLevel) {
                     Projectile _entityToSpawn = (new Object() {
                        public Projectile getFireball(Level level, double ax, double ay, double az) {
                           AbstractHurtingProjectile entityToSpawn = new SmallFireball(EntityType.f_20527_, level);
                           entityToSpawn.f_36813_ = ax;
                           entityToSpawn.f_36814_ = ay;
                           entityToSpawn.f_36815_ = az;
                           return entityToSpawn;
                        }
                     }).getFireball(projectileLevel, entity.m_20154_().f_82479_ / 6.66, -0.05, entity.m_20154_().f_82481_ / 6.66);
                     _entityToSpawn.m_6034_(
                        x + Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0),
                        y + Mth.m_216263_(RandomSource.m_216327_(), 1.0, 3.0),
                        z + Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0)
                     );
                     _entityToSpawn.m_6686_(entity.m_20154_().f_82479_, entity.m_20154_().f_82480_, entity.m_20154_().f_82481_, 1.0F, 6.0F);
                     projectileLevel.m_7967_(_entityToSpawn);
                  }

                  entity.getPersistentData().m_128347_("barrage", entity.getPersistentData().m_128459_("barrage") - 1.0);
               }
            }
         }
      }
   }
}
