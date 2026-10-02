package com.obscuria.aquamirae.common.entities.projectiles;

import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.obscureapi.api.common.DynamicProjectile;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages.SpawnEntity;

public class PoisonedChakra extends DynamicProjectile {
   public PoisonedChakra(SpawnEntity packet, Level world) {
      this((EntityType<PoisonedChakra>)AquamiraeEntities.POISONED_CHAKRA.get(), world);
   }

   public PoisonedChakra(EntityType<PoisonedChakra> type, Level world) {
      super(type, world);
      this.f_19811_ = true;
   }

   public boolean attack(LivingEntity entity) {
      boolean attack = super.attack(entity);
      if (attack) {
         entity.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 60, 1));
      }

      return attack;
   }

   public float getAttackRange() {
      return 1.3F;
   }
}
