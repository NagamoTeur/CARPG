package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityTick;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.AABB;

public class VoidBlossomSpikeTick implements IEntityTick<ServerLevel> {
   private final VoidBlossomEntity entity;

   public VoidBlossomSpikeTick(VoidBlossomEntity entity) {
      this.entity = entity;
   }

   public void tick(ServerLevel level) {
      AABB spikeHitbox = new AABB(this.entity.m_20182_(), this.entity.m_20182_()).m_82377_(3.0, 3.0, 3.0).m_82386_(0.0, 1.5, 0.0);

      for (LivingEntity target : level.m_6443_(LivingEntity.class, spikeHitbox, livingEntity -> livingEntity != this.entity)) {
         float damage = (float)this.entity.m_21133_(Attributes.f_22281_);
         if (target.m_20182_().m_82557_(this.entity.m_20182_()) < Math.pow(3.0, 2.0)) {
            target.m_6469_(DamageSource.m_19335_(this.entity), damage);
         }
      }
   }
}
