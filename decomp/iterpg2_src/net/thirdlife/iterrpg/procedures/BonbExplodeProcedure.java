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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class BonbExplodeProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity immediatesourceentity) {
      if (immediatesourceentity != null) {
         double damage = 0.0;
         double distance = 0.0;
         boolean hit = false;
         boolean particle = false;
         if (world instanceof ServerLevel _level) {
            _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.GOBSTEEL_SHARDS.get(), x, y, z, 32, 0.016, 0.016, 0.016, 0.24);
         }

         if (world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123762_, x, y, z, 8, 0.016, 0.016, 0.016, 0.08);
         }

         if (world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123777_, x, y, z, 4, 0.016, 0.016, 0.016, 0.08);
         }

         if (world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123813_, x, y, z, 1, 0.0, 0.0, 0.0, 0.032);
         }

         if (world instanceof Level _level) {
            if (!_level.m_5776_()) {
               _level.m_5594_(
                  null,
                  new BlockPos(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")),
                  SoundSource.NEUTRAL,
                  1.0F,
                  1.6F
               );
            } else {
               _level.m_7785_(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")),
                  SoundSource.NEUTRAL,
                  1.0F,
                  1.6F,
                  false
               );
            }
         }

         distance = 1.0;
         damage = 6.0;

         for (int index0 = 0; index0 < 5; index0++) {
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(distance / 2.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if ((!(entityiterator instanceof TamableAnimal _tamEnt) || !_tamEnt.m_21824_())
                  && !entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))) {
                  entityiterator.m_6469_(DamageSource.f_19318_, (float)damage);
               }
            }

            damage--;
            distance++;
         }

         if (world instanceof Level _levelx && !_levelx.m_5776_()) {
            _levelx.m_46511_(null, x, y, z, 1.0F, BlockInteraction.NONE);
         }

         if (!immediatesourceentity.f_19853_.m_5776_()) {
            immediatesourceentity.m_146870_();
         }
      }
   }
}
