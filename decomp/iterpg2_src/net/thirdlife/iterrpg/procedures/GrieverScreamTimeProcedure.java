package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GrieverScreamTimeProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         MournersAiProcedure.execute(world, x, y, z, entity);
         if (entity.getPersistentData().m_128459_("screamTime") >= 1250.0) {
            entity.getPersistentData().m_128347_("screamTime", 1200.0);
         } else {
            entity.getPersistentData().m_128347_("screamTime", entity.getPersistentData().m_128459_("screamTime") + 1.0);
         }

         if (entity.getPersistentData().m_128459_("buffTime") >= 125.0) {
            entity.getPersistentData().m_128347_("buffTime", 0.0);
            if (world instanceof ServerLevel _level) {
               _level.m_8767_(
                  ParticleTypes.f_123771_,
                  x,
                  y + (double)(entity.m_20206_() / 2.0F),
                  z,
                  6,
                  (double)entity.m_20205_() / 2.5,
                  (double)entity.m_20206_() / 2.5,
                  (double)entity.m_20205_() / 2.5,
                  0.01
               );
            }

            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(5.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:mourners")))) {
                  if (entityiterator instanceof LivingEntity) {
                     LivingEntity _entity = (LivingEntity)entityiterator;
                     if (!_entity.f_19853_.m_5776_()) {
                        _entity.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 120, 0, false, true));
                     }
                  }

                  if (entityiterator instanceof LivingEntity) {
                     LivingEntity _entity = (LivingEntity)entityiterator;
                     if (!_entity.f_19853_.m_5776_()) {
                        _entity.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 50, 0, false, true));
                     }
                  }

                  if (world instanceof ServerLevel _level) {
                     _level.m_8767_(
                        ParticleTypes.f_123808_,
                        entityiterator.m_20185_(),
                        entityiterator.m_20186_() + (double)(entityiterator.m_20206_() / 2.0F),
                        entityiterator.m_20189_(),
                        6,
                        (double)entityiterator.m_20205_() / 2.5,
                        (double)entityiterator.m_20206_() / 2.5,
                        (double)entityiterator.m_20205_() / 2.5,
                        0.01
                     );
                  }
               }
            }
         } else {
            entity.getPersistentData().m_128347_("buffTime", entity.getPersistentData().m_128459_("buffTime") + 1.0);
         }
      }
   }
}
