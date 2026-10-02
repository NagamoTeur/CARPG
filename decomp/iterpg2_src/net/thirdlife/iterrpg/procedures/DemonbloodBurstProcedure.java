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

public class DemonbloodBurstProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world instanceof ServerLevel _level) {
         _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.DEMONBLOOD.get(), x, y, z, 32, 0.5, 0.5, 0.5, 0.05);
      }

      Vec3 _center = new Vec3(x, y, z);

      for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(4.0), e -> true)
         .stream()
         .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
         .collect(Collectors.toList())) {
         if (!entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))) {
            entityiterator.m_6469_(DamageSource.f_19318_, 4.0F);
            if (world instanceof ServerLevel _level) {
               _level.m_8767_(
                  (SimpleParticleType)IterRpgModParticleTypes.DEMONBLOOD.get(),
                  entityiterator.m_20185_(),
                  entityiterator.m_20186_(),
                  entityiterator.m_20189_(),
                  8,
                  0.16,
                  0.16,
                  0.16,
                  0.08
               );
            }
         }
      }
   }
}
