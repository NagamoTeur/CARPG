package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AI.CmAttackGoal;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.Endermaptera_Entity;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.projectile.Void_Rune_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;

public class Ender_Golem_Entity extends LLibrary_Boss_Monster {
   public static final Animation ANIMATION_ATTACK1 = Animation.create(25);
   public static final Animation ANIMATION_ATTACK2 = Animation.create(25);
   public static final Animation ANIMATION_EARTHQUAKE = Animation.create(35);
   public static final Animation VOID_RUNE_ATTACK = Animation.create(83);
   public static final Animation ENDER_GOLEM_DEATH = Animation.create(95);
   public static final int VOID_RUNE_ATTACK_COOLDOWN = 250;
   private static final EntityDataAccessor<Boolean> IS_AWAKEN = SynchedEntityData.m_135353_(Ender_Golem_Entity.class, EntityDataSerializers.f_135035_);
   private int void_rune_attack_cooldown = 0;
   private int timeWithoutTarget;
   public float deactivateProgress;
   public float prevdeactivateProgress;
   public boolean Breaking = CMConfig.EndergolemBlockBreaking;

   public Ender_Golem_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 15;
      this.m_21441_(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
      setConfigattribute(this, CMConfig.EnderGolemHealthMultiplier, CMConfig.EnderGolemDamageMultiplier);
   }

   public float getStepHeight() {
      return 1.5F;
   }

   protected int m_5639_(float p_21237_, float p_21238_) {
      return 0;
   }

   public boolean m_6673_(DamageSource p_20122_) {
      return super.m_6673_(p_20122_) || p_20122_.m_146707_();
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION, ANIMATION_ATTACK1, ANIMATION_ATTACK2, ANIMATION_EARTHQUAKE, VOID_RUNE_ATTACK, ENDER_GOLEM_DEATH};
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(2, new CmAttackGoal(this, 1.0));
      this.f_21345_.m_25352_(1, new Ender_Golem_Entity.AttackGoal());
      this.f_21345_.m_25352_(0, new Ender_Golem_Entity.AwakenGoal());
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
   }

   public static Builder ender_golem() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22277_, 20.0)
         .m_22268_(Attributes.f_22279_, 0.28F)
         .m_22268_(Attributes.f_22281_, 10.0)
         .m_22268_(Attributes.f_22276_, 150.0)
         .m_22268_(Attributes.f_22284_, 12.0)
         .m_22268_(Attributes.f_22278_, 1.0);
   }

   protected int m_7302_(int air) {
      return air;
   }

   public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
      return false;
   }

   private static Animation getRandomAttack(RandomSource rand) {
      switch (rand.m_188503_(3)) {
         case 0:
            return ANIMATION_ATTACK1;
         case 1:
            return ANIMATION_ATTACK2;
         case 2:
            return ANIMATION_EARTHQUAKE;
         default:
            return ANIMATION_EARTHQUAKE;
      }
   }

   @Override
   public boolean m_6469_(DamageSource source, float damage) {
      if ((this.getAnimation() == VOID_RUNE_ATTACK || !this.getIsAwaken()) && (source.m_19378_() || !source.m_19387_())) {
         damage = (float)((double)damage * 0.5);
      }

      double range = this.calculateRange(source);
      if (range > CMConfig.EndergolemLongRangelimit * CMConfig.EndergolemLongRangelimit) {
         return false;
      } else {
         Entity entity = source.m_7640_();
         if (entity instanceof AbstractGolem) {
            damage = (float)((double)damage * 0.5);
         }

         return super.m_6469_(source, damage);
      }
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(IS_AWAKEN, false);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128379_("is_Awaken", this.getIsAwaken());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setIsAwaken(compound.m_128471_("is_Awaken"));
   }

   public void setIsAwaken(boolean isAwaken) {
      this.f_19804_.m_135381_(IS_AWAKEN, isAwaken);
   }

   public boolean getIsAwaken() {
      return (Boolean)this.f_19804_.m_135370_(IS_AWAKEN);
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      this.m_146922_(this.f_20883_);
      this.repelEntities(1.7F, 3.7F, 1.7F, 1.7F);
      LivingEntity target = this.m_5448_();
      this.prevdeactivateProgress = this.deactivateProgress;
      if (!this.getIsAwaken() && this.deactivateProgress < 30.0F) {
         this.deactivateProgress++;
      }

      if (this.getIsAwaken() && this.deactivateProgress > 0.0F) {
         this.deactivateProgress--;
      }

      if (!this.getIsAwaken() && this.f_19797_ % 20 == 0) {
         this.m_5634_(2.0F);
      }

      if (this.deactivateProgress == 0.0F && this.m_6084_()) {
         if (target != null && target.m_6084_()) {
            if (this.void_rune_attack_cooldown > 0
               || this.m_21525_()
               || this.getAnimation() != NO_ANIMATION
               || !target.m_20096_()
               || (this.f_19796_.m_188503_(45) != 0 || !(this.m_20270_(target) < 4.0F))
                  && (this.f_19796_.m_188503_(24) != 0 || !(this.m_20270_(target) < 10.0F))) {
               if (this.m_20270_(target) < 4.0F && !this.m_21525_() && this.getAnimation() == NO_ANIMATION) {
                  Animation animation = getRandomAttack(this.f_19796_);
                  this.setAnimation(animation);
               }
            } else {
               this.void_rune_attack_cooldown = 250;
               this.setAnimation(VOID_RUNE_ATTACK);
            }
         }

         if (this.getAnimation() == ANIMATION_EARTHQUAKE && this.getAnimationTick() == 19) {
            this.EarthQuake(5.0F, 6);
            this.EarthQuakeParticle();
            if (!this.f_19853_.f_46443_) {
               if (this.Breaking) {
                  this.BlockBreaking(4, 4, 4);
               } else if (ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
                  this.BlockBreaking(4, 4, 4);
               }
            }
         }

         if ((this.getAnimation() == ANIMATION_ATTACK1 || this.getAnimation() == ANIMATION_ATTACK2) && this.getAnimationTick() == 13) {
            this.m_5496_((SoundEvent)ModSounds.GOLEMATTACK.get(), 1.0F, 1.0F);
            if (target != null && target.m_6084_() && this.m_20270_(target) < 4.75F) {
               target.m_6469_(DamageSource.m_19370_(this), (float)this.m_21133_(Attributes.f_22281_) + (float)this.f_19796_.m_188503_(4));
               target.m_147240_(1.25, this.m_20185_() - target.m_20185_(), this.m_20189_() - target.m_20189_());
            }
         }

         if (this.getAnimation() == VOID_RUNE_ATTACK) {
            if (this.getAnimationTick() == 22) {
               this.EarthQuake(4.25F, 4);
               this.EarthQuakeParticle();
               if (!this.f_19853_.f_46443_) {
                  if (this.Breaking) {
                     this.BlockBreaking(4, 4, 4);
                  } else if (ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
                     this.BlockBreaking(4, 4, 4);
                  }
               }
            }

            if (this.getAnimationTick() == 28) {
               this.VoidRuneAttack();
            }
         }
      }

      if (this.void_rune_attack_cooldown > 0) {
         this.void_rune_attack_cooldown--;
      }

      if (!this.f_19853_.f_46443_) {
         this.timeWithoutTarget++;
         if (target != null) {
            this.timeWithoutTarget = 0;
            if (!this.getIsAwaken()) {
               this.setIsAwaken(true);
            }
         }

         if (this.timeWithoutTarget > 400 && this.getIsAwaken() && target == null) {
            this.timeWithoutTarget = 0;
            this.setIsAwaken(false);
         }
      }
   }

   private void BlockBreaking(int x, int y, int z) {
      int MthX = Mth.m_14107_(this.m_20185_());
      int MthY = Mth.m_14107_(this.m_20186_());
      int MthZ = Mth.m_14107_(this.m_20189_());
      boolean flag = false;

      for (int k2 = -x; k2 <= x; k2++) {
         for (int l2 = -z; l2 <= z; l2++) {
            for (int j = 0; j <= y; j++) {
               int i3 = MthX + k2;
               int k = MthY + j;
               int l = MthZ + l2;
               BlockPos blockpos = new BlockPos(i3, k, l);
               BlockState block = this.f_19853_.m_8055_(blockpos);
               if (block != Blocks.f_50016_.m_49966_()
                  && block.m_204336_(ModTag.ENDER_GOLEM_CAN_DESTROY)
                  && block.canEntityDestroy(this.f_19853_, blockpos, this)
                  && ForgeEventFactory.onEntityDestroyBlock(this, blockpos, block)) {
                  flag = this.f_19853_.m_46953_(blockpos, true, this) || flag;
               }
            }
         }
      }
   }

   private void EarthQuakeParticle() {
      if (this.f_19853_.f_46443_) {
         BlockState block = this.f_19853_.m_8055_(this.m_20183_().m_7495_());

         for (int i1 = 0; i1 < 20 + this.f_19796_.m_188503_(12); i1++) {
            double DeltaMovementX = this.m_217043_().m_188583_() * 0.07;
            double DeltaMovementY = this.m_217043_().m_188583_() * 0.07;
            double DeltaMovementZ = this.m_217043_().m_188583_() * 0.07;
            float angle = (float) (Math.PI / 180.0) * this.f_20883_ + (float)i1;
            double extraX = (double)(4.0F * Mth.m_14031_((float)(Math.PI + (double)angle)));
            double extraY = 0.3F;
            double extraZ = (double)(4.0F * Mth.m_14089_(angle));
            if (block.m_60799_() != RenderShape.INVISIBLE) {
               this.f_19853_
                  .m_7106_(
                     new BlockParticleOption(ParticleTypes.f_123794_, block),
                     this.m_20185_() + extraX,
                     this.m_20186_() + extraY,
                     this.m_20189_() + extraZ,
                     DeltaMovementX,
                     DeltaMovementY,
                     DeltaMovementZ
                  );
            }
         }
      }
   }

   private void EarthQuake(float grow, int damage) {
      this.m_5496_(SoundEvents.f_11913_, 1.5F, 1.0F + this.m_217043_().m_188501_() * 0.1F);

      for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_((double)grow))) {
         if (!this.m_7307_(entity) && !(entity instanceof Ender_Golem_Entity) && entity != this) {
            entity.m_6469_(DamageSource.m_19370_(this), (float)this.m_21133_(Attributes.f_22281_) + (float)this.f_19796_.m_188503_(damage));
            this.launch(entity, true);
         }
      }
   }

   private void VoidRuneAttack() {
      LivingEntity target = this.m_5448_();
      if (target != null) {
         double d0 = Math.min(target.m_20186_(), this.m_20186_());
         double d1 = Math.max(target.m_20186_(), this.m_20186_()) + 1.0;
         float f = (float)Mth.m_14136_(target.m_20189_() - this.m_20189_(), target.m_20185_() - this.m_20185_());
         float f2 = Mth.m_14089_(this.m_146908_() * (float) (Math.PI / 180.0)) * 2.0F;
         float f3 = Mth.m_14031_(this.m_146908_() * (float) (Math.PI / 180.0)) * 2.0F;

         for (int l = 0; l < 10; l++) {
            double d2 = 1.5 * (double)(l + 1);
            int j = (int)(1.25F * (float)l);
            this.spawnFangs(
               this.m_20185_() + (double)f2 + (double)Mth.m_14089_(f) * d2, this.m_20189_() + (double)f3 + (double)Mth.m_14031_(f) * d2, d0, d1, f, j
            );
            this.spawnFangs(
               this.m_20185_() - (double)f2 + (double)Mth.m_14089_(f) * d2, this.m_20189_() - (double)f3 + (double)Mth.m_14031_(f) * d2, d0, d1, f, j
            );
         }

         for (int k = 0; k < 6; k++) {
            float f4 = f + (float)k * (float) Math.PI * 2.0F / 6.0F + 0.83775806F;
            this.spawnFangs(this.m_20185_() + (double)Mth.m_14089_(f4) * 2.5, this.m_20189_() + (double)Mth.m_14031_(f4) * 2.5, d0, d1, f2, 5);
         }

         for (int k = 0; k < 8; k++) {
            this.spawnFangs(this.m_20185_() + this.f_19796_.m_188583_() * 4.5, this.m_20189_() + this.f_19796_.m_188583_() * 4.5, d0, d1, f3, 15);
         }
      }
   }

   private void spawnFangs(double x, double z, double minY, double maxY, float rotation, int delay) {
      BlockPos blockpos = new BlockPos(x, maxY, z);
      boolean flag = false;
      double d0 = 0.0;

      do {
         BlockPos blockpos1 = blockpos.m_7495_();
         BlockState blockstate = this.f_19853_.m_8055_(blockpos1);
         if (blockstate.m_60783_(this.f_19853_, blockpos1, Direction.UP)) {
            if (!this.f_19853_.m_46859_(blockpos)) {
               BlockState blockstate1 = this.f_19853_.m_8055_(blockpos);
               VoxelShape voxelshape = blockstate1.m_60812_(this.f_19853_, blockpos);
               if (!voxelshape.m_83281_()) {
                  d0 = voxelshape.m_83297_(Axis.Y);
               }
            }

            flag = true;
            break;
         }

         blockpos = blockpos.m_7495_();
      } while (blockpos.m_123342_() >= Mth.m_14107_(minY) - 1);

      if (flag) {
         this.f_19853_
            .m_7967_(new Void_Rune_Entity(this.f_19853_, x, (double)blockpos.m_123342_() + d0, z, rotation, delay, (float)CMConfig.Voidrunedamage, this));
      }
   }

   private void launch(LivingEntity e, boolean huge) {
      double d0 = e.m_20185_() - this.m_20185_();
      double d1 = e.m_20189_() - this.m_20189_();
      double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
      float f = huge ? 2.0F : 0.5F;
      e.m_5997_(d0 / d2 * (double)f, huge ? 0.5 : 0.2F, d1 / d2 * (double)f);
   }

   public boolean m_7307_(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.m_7307_(entityIn)) {
         return true;
      } else {
         return !(entityIn instanceof Ender_Golem_Entity)
               && !(entityIn instanceof Ender_Guardian_Entity)
               && !(entityIn instanceof Shulker)
               && !(entityIn instanceof Endermaptera_Entity)
            ? false
            : this.m_5647_() == null && entityIn.m_5647_() == null;
      }
   }

   @Override
   protected void onDeathAIUpdate() {
      super.onDeathAIUpdate();
      this.m_20334_(0.0, this.m_20184_().f_82480_, 0.0);
      if (this.f_20919_ == 40) {
         this.m_5496_((SoundEvent)ModSounds.MONSTROSITYLAND.get(), 1.0F, 1.0F);
      }
   }

   @Override
   protected void repelEntities(float x, float y, float z, float radius) {
      super.repelEntities(x, y, z, radius);
   }

   @Override
   public boolean canBePushedByEntity(Entity entity) {
      return false;
   }

   @Nullable
   @Override
   public Animation getDeathAnimation() {
      return ENDER_GOLEM_DEATH;
   }

   protected void m_7355_(BlockPos pos, BlockState blockIn) {
      this.m_5496_(SoundEvents.f_12010_, 1.0F, 1.0F);
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.GOLEMHURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.GOLEMDEATH.get();
   }

   protected BodyRotationControl m_7560_() {
      return new SmartBodyHelper2(this);
   }

   protected PathNavigation m_6037_(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
   }

   class AttackGoal extends Goal {
      public AttackGoal() {
         this.m_7021_(EnumSet.of(Flag.JUMP, Flag.LOOK, Flag.MOVE));
      }

      public boolean m_8036_() {
         return Ender_Golem_Entity.this.getAnimation() == Ender_Golem_Entity.ANIMATION_EARTHQUAKE
            || Ender_Golem_Entity.this.getAnimation() == Ender_Golem_Entity.VOID_RUNE_ATTACK;
      }

      public boolean m_183429_() {
         return true;
      }

      public void m_8041_() {
         super.m_8041_();
      }

      public void m_8037_() {
         Ender_Golem_Entity.this.m_20334_(0.0, Ender_Golem_Entity.this.m_20184_().f_82480_, 0.0);
         LivingEntity target = Ender_Golem_Entity.this.m_5448_();
         if (Ender_Golem_Entity.this.getAnimation() == Ender_Golem_Entity.ANIMATION_EARTHQUAKE) {
            if (Ender_Golem_Entity.this.getAnimationTick() < 19 && target != null) {
               Ender_Golem_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
            } else {
               Ender_Golem_Entity.this.m_146922_(Ender_Golem_Entity.this.f_19859_);
            }
         }

         if (Ender_Golem_Entity.this.getAnimation() == Ender_Golem_Entity.VOID_RUNE_ATTACK) {
            if (Ender_Golem_Entity.this.getAnimationTick() < 22 && target != null) {
               Ender_Golem_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
            } else {
               Ender_Golem_Entity.this.m_146922_(Ender_Golem_Entity.this.f_19859_);
            }
         }
      }
   }

   class AwakenGoal extends Goal {
      public AwakenGoal() {
         this.m_7021_(EnumSet.of(Flag.JUMP, Flag.LOOK, Flag.MOVE));
      }

      public boolean m_8036_() {
         return Ender_Golem_Entity.this.deactivateProgress > 0.0F;
      }

      public boolean m_183429_() {
         return true;
      }

      public void m_8037_() {
         Ender_Golem_Entity.this.m_20334_(0.0, Ender_Golem_Entity.this.m_20184_().f_82480_, 0.0);
      }
   }
}
