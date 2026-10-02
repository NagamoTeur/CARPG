package net.cisco.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.cisco.init.CiscoModModParticleTypes;
import net.cisco.network.CiscoModModVariables;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ParticleentOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (world instanceof ServerLevel _level) {
            _level.m_8767_((SimpleParticleType)CiscoModModParticleTypes.DRAGON_SEEKER_PARTICLE.get(), x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
         }

         if (!CiscoModModVariables.MapVariables.get(world).IsBossAliveAndInBattle && !entity.f_19853_.m_5776_()) {
            entity.m_146870_();
         }

         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(3.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
            .collect(Collectors.toList())) {
            if (entityiterator instanceof Player && entityiterator instanceof LivingEntity) {
               LivingEntity _entity = (LivingEntity)entityiterator;
               if (!_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 180, 3));
               }
            }
         }
      }
   }
}
