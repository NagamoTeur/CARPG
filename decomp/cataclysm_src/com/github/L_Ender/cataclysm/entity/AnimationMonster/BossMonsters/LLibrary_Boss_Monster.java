package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.LLibrary_Monster;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;

public class LLibrary_Boss_Monster extends LLibrary_Monster implements IAnimatedEntity, Enemy {
   public LLibrary_Boss_Monster(EntityType entity, Level world) {
      super(entity, world);
   }

   public boolean m_6469_(DamageSource source, float damage) {
      if (source.m_19378_()) {
         return super.m_6469_(source, damage);
      } else {
         damage = Math.min(this.DamageCap(), damage);
         return super.m_6469_(source, damage);
      }
   }

   public float DamageCap() {
      return Float.MAX_VALUE;
   }

   @Override
   protected void onDeathAIUpdate() {
   }

   public boolean m_7301_(MobEffectInstance p_34192_) {
      return ModTag.EFFECTIVE_FOR_BOSSES_LOOKUP.contains(p_34192_.m_19544_()) && super.m_7301_(p_34192_);
   }

   public boolean m_6785_(double p_21542_) {
      return false;
   }

   protected boolean m_8028_() {
      return false;
   }

   protected boolean m_7341_(Entity p_31508_) {
      return false;
   }
}
