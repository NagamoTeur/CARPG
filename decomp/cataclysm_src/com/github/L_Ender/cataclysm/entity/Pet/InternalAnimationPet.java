package com.github.L_Ender.cataclysm.entity.Pet;

import com.github.L_Ender.cataclysm.entity.etc.IFollower;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class InternalAnimationPet extends AnimationPet implements IFollower {
   public static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.m_135353_(InternalAnimationPet.class, EntityDataSerializers.f_135028_);
   public int attackTicks;

   public InternalAnimationPet(EntityType entity, Level world) {
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

   public void m_8119_() {
      super.m_8119_();
      if (this.getAttackState() > 0) {
         this.attackTicks++;
      }
   }
}
