package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster;

import com.github.L_Ender.cataclysm.entity.etc.Animation_Monsters;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;

public class Internal_Animation_Monster extends Animation_Monsters implements Enemy {
   public static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.m_135353_(Internal_Animation_Monster.class, EntityDataSerializers.f_135028_);
   public int attackTicks;
   public int attackCooldown;

   public Internal_Animation_Monster(EntityType entity, Level world) {
      super(entity, world);
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(ATTACK_STATE, 0);
   }

   public int getAttackState() {
      return (Integer)this.f_19804_.m_135370_(ATTACK_STATE);
   }

   public void setAttackState(int input) {
      this.attackTicks = 0;
      this.f_19804_.m_135381_(ATTACK_STATE, input);
      this.f_19853_.m_7605_(this, (byte)(-input));
   }

   public void m_7822_(byte id) {
      if (id <= 0) {
         this.attackTicks = 0;
      } else {
         super.m_7822_(id);
      }
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (this.getAttackState() > 0) {
         this.attackTicks++;
      }
   }

   @Override
   protected void m_6153_() {
      this.onDeathUpdate(this.deathtimer());
   }

   @Override
   public int deathtimer() {
      return 20;
   }

   @Override
   public void m_7334_(Entity entityIn) {
      if (!this.m_5803_() && !this.m_20365_(entityIn) && !entityIn.f_19794_ && !this.f_19794_) {
         double d0 = entityIn.m_20185_() - this.m_20185_();
         double d1 = entityIn.m_20189_() - this.m_20189_();
         double d2 = Mth.m_14005_(d0, d1);
         if (d2 >= 0.01F) {
            d2 = (double)Mth.m_14116_((float)d2);
            d0 /= d2;
            d1 /= d2;
            double d3 = 1.0 / d2;
            if (d3 > 1.0) {
               d3 = 1.0;
            }

            d0 *= d3;
            d1 *= d3;
            d0 *= 0.05F;
            d1 *= 0.05F;
            if (!this.m_20160_() && this.canBePushedByEntity(entityIn)) {
               this.m_5997_(-d0, 0.0, -d1);
            }

            if (!entityIn.m_20160_()) {
               entityIn.m_5997_(d0, 0.0, d1);
            }
         }
      }
   }
}
