package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class BoulderExplodeProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (!world.m_46859_(
               new BlockPos(x + (double)Mth.m_216271_(RandomSource.m_216327_(), 0, 0), y - 0.001, z + (double)Mth.m_216271_(RandomSource.m_216327_(), 0, 0))
            )
            || entity.getPersistentData().m_128459_("BoulderExplode") >= 60.0) {
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(1.6), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiterator != entity
                  && !entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                  && !entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:mourners")))) {
                  entityiterator.m_6469_(DamageSource.f_19318_, (float)Mth.m_216263_(RandomSource.m_216327_(), 8.0, 10.0));
                  if (entityiterator instanceof LivingEntity) {
                     LivingEntity _entity = (LivingEntity)entityiterator;
                     if (!_entity.f_19853_.m_5776_()) {
                        _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 80, 0, false, true));
                     }
                  }
               }
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123813_, x, y, z, 8, 0.5, 0.5, 0.5, 0.0);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.WEEPER_TEAR_PARTICLE.get(), x, y, z, 16, 1.0, 1.0, 1.0, 0.025);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123745_, x, y, z, 16, 1.0, 1.0, 1.0, 0.025);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123746_, x, y, z, 16, 1.0, 1.0, 1.0, 0.025);
            }

            if (!entity.f_19853_.m_5776_()) {
               entity.m_146870_();
            }
         }

         entity.getPersistentData().m_128347_("BoulderExplode", entity.getPersistentData().m_128459_("BoulderExplode") + 1.0);
      }
   }
}
