package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.entity.EarthBoulderEntity;

public class EarthBoulderTickProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         double ypos = 0.0;
         double zpos = 0.0;
         double xpos = 0.0;
         double k = 0.0;
         if (entity.getPersistentData().m_128459_("ascend") > 0.0) {
            entity.getPersistentData().m_128347_("ascend", entity.getPersistentData().m_128459_("ascend") - 0.05);
            entity.m_20256_(new Vec3(0.0, entity.getPersistentData().m_128459_("ascend") * 0.025, 0.0));
         }

         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(0.45), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
            .collect(Collectors.toList())) {
            if (!entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
               && !(entityiterator instanceof EarthBoulderEntity)
               && entity != entityiterator
               && !entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:elementals")))) {
               entityiterator.m_6469_(DamageSource.f_19318_, 6.0F);
               if (world instanceof Level) {
                  Level _level = (Level)world;
                  if (!_level.m_5776_()) {
                     _level.m_5594_(
                        null,
                        new BlockPos(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.basalt.break")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _level.m_7785_(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.basalt.break")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }

               if (!entity.f_19853_.m_5776_()) {
                  entity.m_146870_();
               }
            }
         }

         if (entity.getPersistentData().m_128459_("lifetime") == 32.0) {
            if (!world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 64.0, 64.0, 64.0), e -> true).isEmpty()) {
               xpos = world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 64.0, 64.0, 64.0), e -> true).stream().sorted((new Object() {
                  Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                     return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
                  }
               }).compareDistOf(x, y, z)).findFirst().orElse(null).m_20185_() - entity.m_20185_();
               ypos = world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 64.0, 64.0, 64.0), e -> true).stream().sorted((new Object() {
                  Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                     return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
                  }
               }).compareDistOf(x, y, z)).findFirst().orElse(null).m_20186_() + 1.0 - entity.m_20186_();
               zpos = world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 64.0, 64.0, 64.0), e -> true).stream().sorted((new Object() {
                  Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                     return Comparator.comparingDouble(_entcnd -> _entcnd.m_20275_(_x, _y, _z));
                  }
               }).compareDistOf(x, y, z)).findFirst().orElse(null).m_20189_() - entity.m_20189_();
               k = Math.abs(ypos) + Math.abs(zpos) + Math.abs(xpos);
               xpos /= k;
               ypos /= k;
               zpos /= k;
               entity.getPersistentData().m_128347_("xVector", xpos);
               entity.getPersistentData().m_128347_("yVector", ypos);
               entity.getPersistentData().m_128347_("zVector", zpos);
            } else {
               if (world instanceof Level _level) {
                  if (!_level.m_5776_()) {
                     _level.m_5594_(
                        null,
                        new BlockPos(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.basalt.break")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _level.m_7785_(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.basalt.break")),
                        SoundSource.HOSTILE,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }

               if (!entity.f_19853_.m_5776_()) {
                  entity.m_146870_();
               }
            }
         }

         for (int index0 = 0; index0 < 2; index0++) {
            world.m_7106_(
               ParticleTypes.f_123797_,
               x + Mth.m_216263_(RandomSource.m_216327_(), -0.5, 0.5),
               y + Mth.m_216263_(RandomSource.m_216327_(), -0.16, 0.64),
               z + Mth.m_216263_(RandomSource.m_216327_(), -0.5, 0.5),
               entity.getPersistentData().m_128459_("xVector") / -8.0,
               entity.getPersistentData().m_128459_("yVector") / -8.0,
               entity.getPersistentData().m_128459_("zVector") / -8.0
            );
         }

         if (entity.getPersistentData().m_128459_("lifetime") > 350.0) {
            if (!entity.f_19853_.m_5776_()) {
               entity.m_146870_();
            }

            if (world instanceof Level _levelx) {
               if (!_levelx.m_5776_()) {
                  _levelx.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.basalt.break")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F
                  );
               } else {
                  _levelx.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.basalt.break")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }
         }

         entity.getPersistentData().m_128347_("lifetime", entity.getPersistentData().m_128459_("lifetime") + 1.0);
         if (world.m_8055_(
               new BlockPos(
                  x + Mth.m_216263_(RandomSource.m_216327_(), -0.1, 0.1),
                  y + Mth.m_216263_(RandomSource.m_216327_(), -0.1, 0.1),
                  z + Mth.m_216263_(RandomSource.m_216327_(), -0.1, 0.1)
               )
            )
            .m_60815_()) {
            if (!entity.f_19853_.m_5776_()) {
               entity.m_146870_();
            }

            if (world instanceof Level _levelxx) {
               if (!_levelxx.m_5776_()) {
                  _levelxx.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.basalt.break")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F
                  );
               } else {
                  _levelxx.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.basalt.break")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }
         }

         if (entity.getPersistentData().m_128459_("lifetime") >= 33.0) {
            entity.m_20256_(
               new Vec3(
                  entity.getPersistentData().m_128459_("xVector") / 1.5,
                  entity.getPersistentData().m_128459_("yVector") / 1.5,
                  entity.getPersistentData().m_128459_("zVector") / 1.5
               )
            );
         }
      }
   }
}
