package com.github.L_Ender.cataclysm.entity.Deepling;

import com.github.L_Ender.cataclysm.entity.AI.MobAIFindWater;
import com.github.L_Ender.cataclysm.entity.AI.MobAILeaveWater;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.LLibrary_Monster;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Coralssus_Entity;
import com.github.L_Ender.cataclysm.entity.etc.ISemiAquatic;
import com.github.L_Ender.cataclysm.entity.etc.path.GroundPathNavigatorWide;
import com.github.L_Ender.cataclysm.entity.etc.path.SemiAquaticPathNavigator;
import com.github.L_Ender.cataclysm.init.ModTag;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

public class AbstractDeepling extends LLibrary_Monster implements ISemiAquatic, Enemy {
   private int moistureAttackTime = 0;
   public float LayerBrightness;
   public float oLayerBrightness;
   public int LayerTicks;
   private boolean isLandNavigator;
   private static final EntityDataAccessor<Integer> MOISTNESS = SynchedEntityData.m_135353_(AbstractDeepling.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Boolean> DEEPLINGSWIM = SynchedEntityData.m_135353_(AbstractDeepling.class, EntityDataSerializers.f_135035_);

   public AbstractDeepling(EntityType entity, Level world) {
      super(entity, world);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(4, new MobAIFindWater(this, 1.0));
      this.f_21345_.m_25352_(4, new MobAILeaveWater(this));
      this.f_21345_.m_25352_(7, new RandomStrollGoal(this, 1.0));
      this.f_21345_.m_25352_(5, new AbstractDeepling.RidingCoralssus(this));
      this.f_21345_.m_25352_(3, new AbstractDeepling.StopRiding(this));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(MOISTNESS, 40000);
      this.f_19804_.m_135372_(DEEPLINGSWIM, false);
   }

   public boolean m_7307_(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.m_7307_(entityIn)) {
         return true;
      } else {
         return !entityIn.m_6095_().m_204039_(ModTag.TEAM_THE_LEVIATHAN) ? false : this.m_5647_() == null && entityIn.m_5647_() == null;
      }
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (this.m_20069_() && this.isLandNavigator) {
         this.switchNavigator(false);
      }

      if (!this.m_20069_() && !this.isLandNavigator) {
         this.switchNavigator(true);
      }

      if (this.m_21525_()) {
         this.m_20301_(this.m_6062_());
      } else if (this.m_20071_()) {
         this.setMoistness(6000);
      } else {
         int dry = this.f_19853_.m_46461_() ? 2 : 1;
         this.setMoistness(this.getMoistness() - dry);
         if (this.getMoistness() <= 0 && this.moistureAttackTime-- <= 0) {
            this.m_6469_(DamageSource.f_19324_, this.f_19796_.m_188503_(2) == 0 ? 1.0F : 0.0F);
            this.moistureAttackTime = 20;
         }
      }

      boolean flag1 = this.canInFluidType(this.getEyeInFluidType());
      if (flag1) {
         if (this.f_19853_.m_45756_(this, this.getSwimmingBox())) {
            if (!this.getDeeplingSwim()) {
               this.setDeeplingSwim(true);
            }

            this.m_6210_();
         }
      } else if (this.f_19853_.m_45756_(this, this.getNormalBox())) {
         if (this.getDeeplingSwim()) {
            this.setDeeplingSwim(false);
         }

         this.m_6210_();
      }

      if (this.f_19853_.f_46443_) {
         this.LayerTicks++;
         this.LayerBrightness = this.LayerBrightness + (0.0F - this.LayerBrightness) * 0.8F;
      }
   }

   private boolean canInFluidType(FluidType type) {
      ForgeMod.WATER_TYPE.get();
      return type.canSwim(this.self());
   }

   public boolean m_6067_() {
      return this.getDeeplingSwim();
   }

   public void switchNavigator(boolean onLand) {
      if (onLand) {
         this.f_21344_ = new GroundPathNavigatorWide(this, this.f_19853_);
         this.isLandNavigator = true;
      } else {
         this.f_21344_ = new SemiAquaticPathNavigator(this, this.f_19853_);
         this.isLandNavigator = false;
      }
   }

   public AABB getSwimmingBox() {
      return new AABB(
         this.m_20185_() - 1.15F, this.m_20186_(), this.m_20189_() - 1.15F, this.m_20185_() + 1.15F, this.m_20186_() + 0.6F, this.m_20189_() + 1.15F
      );
   }

   public AABB getNormalBox() {
      return new AABB(this.m_20185_() - 0.6F, this.m_20186_(), this.m_20189_() - 0.6F, this.m_20185_() + 0.6F, this.m_20186_() + 2.3F, this.m_20189_() + 0.6F);
   }

   public EntityDimensions getSwimmingSize() {
      return this.m_6095_().m_20680_().m_20388_(this.m_6134_());
   }

   public EntityDimensions m_6972_(Pose poseIn) {
      return this.getDeeplingSwim() ? this.getSwimmingSize() : super.m_6972_(poseIn);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("Moisture", this.getMoistness());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setMoistness(compound.m_128451_("Moisture"));
   }

   public int getMoistness() {
      return (Integer)this.f_19804_.m_135370_(MOISTNESS);
   }

   public void setMoistness(int p_211137_1_) {
      this.f_19804_.m_135381_(MOISTNESS, p_211137_1_);
   }

   public boolean getDeeplingSwim() {
      return (Boolean)this.f_19804_.m_135370_(DEEPLINGSWIM);
   }

   public void setDeeplingSwim(boolean swim) {
      this.f_19804_.m_135381_(DEEPLINGSWIM, swim);
   }

   public MobType m_6336_() {
      return MobType.f_21644_;
   }

   public boolean m_6063_() {
      return !this.m_6069_();
   }

   public boolean m_6040_() {
      return true;
   }

   @Override
   public boolean shouldEnterWater() {
      return this.getMoistness() < 300;
   }

   @Override
   public boolean shouldLeaveWater() {
      return this.m_5448_() != null && !this.m_5448_().m_20069_();
   }

   @Override
   public boolean shouldStopMoving() {
      return false;
   }

   @Override
   public int getWaterSearchRange() {
      return 32;
   }

   static class RidingCoralssus extends Goal {
      private final AbstractDeepling drowned;

      public RidingCoralssus(AbstractDeepling p_32440_) {
         this.drowned = p_32440_;
      }

      public boolean m_8036_() {
         Coralssus_Entity sus = this.getClosestCoralssus_Entity();
         return !this.drowned.m_20159_() && sus != null && this.drowned.getMoistness() > 300 && sus.m_6084_() && !sus.m_20160_();
      }

      public void m_8056_() {
         Coralssus_Entity sus = this.getClosestCoralssus_Entity();
         if (sus != null) {
            this.drowned.m_21573_().m_5624_(sus, 1.0);
         }
      }

      public void m_8037_() {
         Coralssus_Entity sus = this.getClosestCoralssus_Entity();
         if (sus != null) {
            this.drowned.m_21573_().m_5624_(sus, 1.0);
            if (this.drowned.m_20270_(sus) < 4.0F) {
               this.drowned.m_7998_(sus, true);
            }
         }
      }

      public void m_8041_() {
         this.drowned.m_21573_().m_26573_();
      }

      private Coralssus_Entity getClosestCoralssus_Entity() {
         List<Coralssus_Entity> list = this.drowned.f_19853_.m_45976_(Coralssus_Entity.class, this.drowned.m_20191_().m_82377_(15.0, 15.0, 15.0));
         Coralssus_Entity closest = null;
         if (!list.isEmpty()) {
            for (Coralssus_Entity entity : list) {
               if (closest == null || closest.m_20270_(entity) > entity.m_20270_(entity)) {
                  closest = entity;
               }
            }
         }

         return closest;
      }
   }

   static class StopRiding extends Goal {
      private final AbstractDeepling drowned;

      public StopRiding(AbstractDeepling p_32440_) {
         this.drowned = p_32440_;
      }

      public boolean m_8036_() {
         return this.drowned.getMoistness() < 300 && this.drowned.m_20159_();
      }

      public void m_8056_() {
         this.drowned.m_8127_();
      }
   }
}
