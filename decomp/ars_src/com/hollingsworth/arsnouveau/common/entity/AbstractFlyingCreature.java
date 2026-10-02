package com.hollingsworth.arsnouveau.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractFlyingCreature extends PathfinderMob {
   protected AbstractFlyingCreature(EntityType<? extends PathfinderMob> type, Level worldIn) {
      super(type, worldIn);
   }

   public boolean causeFallDamage(float distance, float damageMultiplier) {
      return false;
   }

   protected void m_7840_(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
   }

   public void m_7023_(Vec3 positionIn) {
      if (this.m_20069_()) {
         this.m_19920_(0.02F, positionIn);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.8F));
      } else if (this.m_20077_()) {
         this.m_19920_(0.02F, positionIn);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.5));
      } else {
         BlockPos ground = new BlockPos(this.m_20185_(), this.m_20186_() - 1.0, this.m_20189_());
         float f = 0.91F;
         if (this.f_19861_) {
            f = this.f_19853_.m_8055_(ground).getFriction(this.f_19853_, ground, this) * 0.91F;
         }

         float f1 = 0.16277137F / (f * f * f);
         f = 0.91F;
         if (this.f_19861_) {
            f = this.f_19853_.m_8055_(ground).getFriction(this.f_19853_, ground, this) * 0.91F;
         }

         this.m_19920_(this.f_19861_ ? 0.1F * f1 : 0.02F, positionIn);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_((double)f));
      }

      this.f_20923_ = this.f_20924_;
      double d1 = this.m_20185_() - this.f_19854_;
      double d0 = this.m_20189_() - this.f_19856_;
      float f2 = Mth.m_14116_((float)(d1 * d1 + d0 * d0)) * 4.0F;
      if (f2 > 1.0F) {
         f2 = 1.0F;
      }

      this.f_20924_ = this.f_20924_ + (f2 - this.f_20924_) * 0.4F;
      this.f_20925_ = this.f_20925_ + this.f_20924_;
   }

   public boolean m_6147_() {
      return false;
   }
}
