package com.github.L_Ender.cataclysm.util;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class CMDamageTypes {
   public static final DamageSource EMP = new DamageSource("cataclysm.emp").m_19380_();
   public static final DamageSource ABYSSAL_BURN = new DamageSource("cataclysm.abyssal_burn").m_19380_();

   public static DamageSource causeLaserDamage(Entity entity, @Nullable LivingEntity livingentity) {
      return new IndirectEntityDamageSource("cataclysm.laser", entity, livingentity).m_19366_();
   }

   public static DamageSource causeDeathLaserDamage(Entity entity, @Nullable LivingEntity livingentity) {
      return new IndirectEntityDamageSource("cataclysm.deathlaser", entity, livingentity).m_19366_().m_19380_();
   }

   public static DamageSource causeShredderDamage(LivingEntity attacker) {
      return new EntityDamageSource("cataclysm.shredder", attacker).m_19375_();
   }

   public static DamageSource causeSwordDanceDamage(LivingEntity attacker) {
      return new EntityDamageSource("cataclysm.sword_dance", attacker).m_19375_();
   }

   public static DamageSource causeMaledictioDamage(LivingEntity attacker) {
      return new EntityDamageSource("cataclysm.maledictio", attacker).m_19380_();
   }

   public static DamageSource causeMaledictioSagittaDamage(Entity attacker, Entity caster) {
      return new IndirectEntityDamageSource("cataclysm.maledictio_sagitta", attacker, caster).m_19366_().m_19375_();
   }

   public static DamageSource causeMaledictioMagicaeDamage(Entity attacker, Entity caster) {
      return new IndirectEntityDamageSource("cataclysm.maledictio_magicae", attacker, caster).m_19380_();
   }
}
