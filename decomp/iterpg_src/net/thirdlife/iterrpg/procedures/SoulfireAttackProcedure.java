package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SoulfireAttackProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         double offset = 0.0;
         boolean should = false;
         if (entity.getPersistentData().m_128459_("attackTime") <= 50.0) {
            should = false;
            if (entity.getPersistentData().m_128459_("AuraDelay") <= 0.0) {
               entity.getPersistentData().m_128347_("attackTime", entity.getPersistentData().m_128459_("attackTime") + 1.0);
               Vec3 _center = new Vec3(x, Mth.m_216263_(RandomSource.m_216327_(), y, y + entity.getPersistentData().m_128459_("offset")), z);

               for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(0.75), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if (!entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:mourners")))
                     && !entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))) {
                     entityiterator.m_6469_(DamageSource.f_19318_, 6.0F);
                     entityiterator.m_6469_(DamageSource.f_19305_, 1.0F);
                     entityiterator.m_20254_(10);
                  }
               }

               entity.getPersistentData().m_128347_("offset", entity.getPersistentData().m_128459_("offset") + 0.16);
               if (world instanceof ServerLevel _level) {
                  _level.m_8767_(
                     ParticleTypes.f_123745_,
                     x,
                     y + entity.getPersistentData().m_128459_("offset") / 2.0,
                     z,
                     6,
                     0.16,
                     entity.getPersistentData().m_128459_("offset") / 4.0,
                     0.16,
                     0.032
                  );
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
