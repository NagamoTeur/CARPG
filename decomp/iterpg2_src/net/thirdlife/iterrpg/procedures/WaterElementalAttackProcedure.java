package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.entity.BlobEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;

public class WaterElementalAttackProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         boolean shouldtick = false;
         boolean shouldspawn = false;
         double attack = 0.0;
         double timer = 0.0;
         double particle = 0.0;
         double fireforce = 0.0;
         double xpos = 0.0;
         double ypos = 0.0;
         double zpos = 0.0;
         double yspawn = 0.0;
         double decide = 0.0;
         WaterElementalDripProcedure.execute(world, x, y, z, entity);
         if (world.m_8055_(new BlockPos(entity.m_20185_(), entity.m_20186_(), entity.m_20189_())).m_60734_() == Blocks.f_49991_) {
            entity.m_6469_(DamageSource.f_19318_, 10.0F);
            if ((entity instanceof LivingEntity _livEnt ? _livEnt.m_21223_() : -1.0F) <= 6.0F) {
               for (int index0 = 0; index0 < 16; index0++) {
                  if (world instanceof ServerLevel _level) {
                     _level.m_8767_(ParticleTypes.f_123759_, x, y + 0.8, z, 6, 1.0, 1.0, 1.0, 0.025);
                  }

                  xpos = Mth.m_216263_(RandomSource.m_216327_(), -2.0, 2.0);
                  ypos = Mth.m_216263_(RandomSource.m_216327_(), -0.75, 0.75);
                  zpos = Mth.m_216263_(RandomSource.m_216327_(), -2.0, 2.0);
                  if (world.m_8055_(new BlockPos(entity.m_20185_() + xpos, entity.m_20186_() + ypos, entity.m_20189_() + zpos)).m_60734_() == Blocks.f_49991_) {
                     decide = (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 3);
                     if (decide == 1.0) {
                        world.m_7731_(new BlockPos(entity.m_20185_() + xpos, entity.m_20186_() + ypos, entity.m_20189_() + zpos), Blocks.f_50080_.m_49966_(), 3);
                     } else if (decide == 2.0) {
                        world.m_7731_(new BlockPos(entity.m_20185_() + xpos, entity.m_20186_() + ypos, entity.m_20189_() + zpos), Blocks.f_50450_.m_49966_(), 3);
                     } else if (decide == 3.0) {
                        world.m_7731_(new BlockPos(entity.m_20185_() + xpos, entity.m_20186_() + ypos, entity.m_20189_() + zpos), Blocks.f_50069_.m_49966_(), 3);
                     }
                  }
               }
            }
         }

         if (entity.m_20094_() >= 1) {
            entity.m_20095_();
            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.lava.extinguish")),
                     SoundSource.HOSTILE,
                     0.5F,
                     1.0F
                  );
               } else {
                  _level.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.lava.extinguish")),
                     SoundSource.HOSTILE,
                     0.5F,
                     1.0F,
                     false
                  );
               }
            }

            if (world instanceof ServerLevel _levelx) {
               _levelx.m_8767_(ParticleTypes.f_123759_, x, y + 0.8, z, 16, 0.5, 0.5, 0.5, 0.025);
            }

            entity.m_6469_(DamageSource.f_19318_, 4.0F);
            xpos = Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0);
            ypos = Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0);
            zpos = Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0);

            for (int index1 = 0; index1 < 6; index1++) {
               if (world.m_8055_(new BlockPos(entity.m_20185_() + xpos, entity.m_20186_() + ypos, entity.m_20189_() + zpos)).m_60734_() == Blocks.f_50083_) {
                  world.m_7731_(new BlockPos(entity.m_20185_() + xpos, entity.m_20186_() + ypos, entity.m_20189_() + zpos), Blocks.f_50016_.m_49966_(), 3);
               }
            }
         }

         if (entity.getPersistentData().m_128459_("attack") >= 120.0) {
            entity.getPersistentData().m_128347_("attack", 0.0);
            if (world instanceof ServerLevel _serverLevelForEntitySpawn) {
               Entity _entityForSpawning = new BlobEntity((EntityType<BlobEntity>)IterRpgModEntities.BLOB.get(), _serverLevelForEntitySpawn);
               _entityForSpawning.m_7678_(x, y + 0.9, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
               _entityForSpawning.m_20256_(new Vec3(entity.m_20154_().f_82479_ * 1.5, entity.m_20154_().f_82480_ * 1.5, entity.m_20154_().f_82481_ * 1.5));
               _entityForSpawning.getPersistentData().m_128379_("IsFriendly", false);
               _entityForSpawning.getPersistentData().m_128359_("owner", entity.m_20149_());
               _entityForSpawning.getPersistentData().m_128347_("deathtime", (double)Mth.m_216271_(RandomSource.m_216327_(), 72, 98));
               if (_entityForSpawning instanceof Mob _mobForSpawning) {
                  _mobForSpawning.m_6518_(_serverLevelForEntitySpawn, world.m_6436_(_entityForSpawning.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
               }

               world.m_7967_(_entityForSpawning);
            }
         } else {
            shouldtick = false;
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(12.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiterator == (entity instanceof Mob _mobEnt ? _mobEnt.m_5448_() : null)) {
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
