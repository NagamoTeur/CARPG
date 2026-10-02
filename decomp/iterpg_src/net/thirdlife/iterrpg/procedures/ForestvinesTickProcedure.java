package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
import net.thirdlife.iterrpg.entity.ForestVinesEntity;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class ForestvinesTickProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity.getPersistentData().m_128459_("ascend") > 0.0) {
            entity.getPersistentData().m_128347_("ascend", entity.getPersistentData().m_128459_("ascend") - 0.05);
            entity.m_6021_(x, y + entity.getPersistentData().m_128459_("ascend") * 0.01, z);
            if (entity instanceof ServerPlayer _serverPlayer) {
               _serverPlayer.f_8906_.m_9774_(x, y + entity.getPersistentData().m_128459_("ascend") * 0.01, z, entity.m_146908_(), entity.m_146909_());
            }
         }

         if (entity.getPersistentData().m_128459_("lifetime") > -6.0) {
            entity.getPersistentData().m_128347_("lifetime", entity.getPersistentData().m_128459_("lifetime") - 1.0);
         } else {
            if (world instanceof ServerLevel _level) {
               _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.ELEMENTAL_LEAF.get(), x, y + 0.3, z, 24, 0.25, 0.5, 0.25, 0.025);
            }

            if (!entity.f_19853_.m_5776_()) {
               entity.m_146870_();
            }
         }

         if ((entity instanceof LivingEntity _livEntx ? _livEntx.m_21223_() : -1.0F)
            < (entity instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F) / 10.0F) {
            if (world instanceof ServerLevel _level) {
               _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.ELEMENTAL_LEAF.get(), x, y + 0.3, z, 32, 0.25, 0.5, 0.25, 0.025);
            }

            if (!entity.f_19853_.m_5776_()) {
               entity.m_146870_();
            }
         }

         if (entity.getPersistentData().m_128459_("ascend") <= 3.0) {
            Vec3 _center = new Vec3(x, y + Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0), z);

            for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(0.45), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (!entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                  && !(entityiterator instanceof ForestVinesEntity)
                  && entity != entityiterator
                  && !entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:elementals")))) {
                  entityiterator.m_6469_(DamageSource.f_19325_, 0.5F);
                  if (entityiterator instanceof LivingEntity) {
                     LivingEntity _entity = (LivingEntity)entityiterator;
                     if (!_entity.f_19853_.m_5776_()) {
                        _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 16, 4, true, false));
                     }
                  }
               }
            }
         }
      }
   }
}
