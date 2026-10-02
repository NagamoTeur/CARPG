package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class TearBurstAttackProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         double offset = 0.0;
         if (entity.getPersistentData().m_128459_("attackTime") <= 24.0) {
            if (entity.getPersistentData().m_128459_("AuraDelay") <= 0.0) {
               entity.getPersistentData().m_128347_("attackTime", entity.getPersistentData().m_128459_("attackTime") + 1.0);
               if (world instanceof ServerLevel _level) {
                  _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.WEEPER_TEAR_PARTICLE.get(), x, y + 0.25, z, 4, 0.25, 0.25, 0.25, 0.025);
               }

               entity.getPersistentData().m_128347_("speed", entity.getPersistentData().m_128459_("speed") - 0.01);
               entity.getPersistentData().m_128347_("spread", entity.getPersistentData().m_128459_("spread") + 0.2);
               if (entity.getPersistentData().m_128459_("attackTime") >= 20.0) {
                  Vec3 _center = new Vec3(x, y + 1.5, z);

                  for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(2.0), e -> true)
                     .stream()
                     .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                     .collect(Collectors.toList())) {
                     if (!entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:mourners")))
                        && !entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))) {
                        entityiterator.m_6469_(DamageSource.f_19318_, 8.0F);
                     }
                  }

                  if (world instanceof ServerLevel _level) {
                     _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.WEEPER_TEAR_PARTICLE.get(), x, y + 0.5, z, 16, 0.4, 0.4, 0.4, 0.16);
                  }
               }
            } else {
               entity.getPersistentData().m_128347_("AuraDelay", entity.getPersistentData().m_128459_("AuraDelay") - 1.0);
               offset = 0.0;
            }
         } else if (!entity.f_19853_.m_5776_()) {
            entity.m_146870_();
         }
      }
   }
}
