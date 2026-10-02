package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.AttackMoveGoal;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.SimpleAnimationGoal;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.projectile.Ashen_Breath_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Blazing_Bone_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import java.util.EnumSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class Ignited_Revenant_Entity extends LLibrary_Boss_Monster {
   public static final Animation ASH_BREATH_ATTACK = Animation.create(53);
   public static final Animation BONE_STORM_ATTACK = Animation.create(49);
   public static final int BREATH_COOLDOWN = 200;
   public static final int STORM_COOLDOWN = 200;
   private float allowedHeightOffset = 0.5F;
   private int nextHeightOffsetChangeTick;
   private static final EntityDataAccessor<Boolean> ANGER = SynchedEntityData.m_135353_(Ignited_Revenant_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> SHIELD_DURABILITY = SynchedEntityData.m_135353_(
      Ignited_Revenant_Entity.class, EntityDataSerializers.f_135028_
   );
   public float angerProgress;
   public float prevangerProgress;
   private int breath_cooldown = 0;
   private int storm_cooldown = 0;

   public Ignited_Revenant_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 15;
      this.m_21441_(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
      setConfigattribute(this, CMConfig.RevenantHealthMultiplier, CMConfig.RevenantDamageMultiplier);
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
      return new Animation[]{NO_ANIMATION, ASH_BREATH_ATTACK, BONE_STORM_ATTACK};
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(2, new Ignited_Revenant_Entity.Ignited_Revenant_Goal());
      this.f_21345_.m_25352_(0, new Ignited_Revenant_Entity.BoneStormGoal(this, BONE_STORM_ATTACK));
      this.f_21345_.m_25352_(0, new Ignited_Revenant_Entity.ShootGoal(this, ASH_BREATH_ATTACK));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static Builder ignited_revenant() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22277_, 20.0)
         .m_22268_(Attributes.f_22279_, 0.28F)
         .m_22268_(Attributes.f_22281_, 6.0)
         .m_22268_(Attributes.f_22276_, 80.0)
         .m_22268_(Attributes.f_22284_, 12.0)
         .m_22268_(Attributes.f_22278_, 1.0);
   }

   protected int m_7302_(int air) {
      return air;
   }

   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
      return false;
   }

   public ItemEntity m_19983_(ItemStack stack) {
      ItemEntity itementity = this.m_5552_(stack, 0.0F);
      if (itementity != null) {
         itementity.m_146915_(true);
         itementity.m_32064_();
      }

      return itementity;
   }

   @Override
   public boolean m_6469_(DamageSource source, float damage) {
      Entity entity = source.m_7640_();
      if (!this.f_19853_.f_46443_ && this.getIsAnger() && entity instanceof LivingEntity && ((LivingEntity)entity).m_21205_().m_41720_() instanceof AxeItem) {
         double itemDamage = (double)(((AxeItem)((LivingEntity)entity).m_21205_().m_41720_()).m_41008_() + 1.0F);
         if ((double)damage >= itemDamage + itemDamage / 2.0 && this.getShieldDurability() < 4) {
            this.m_5496_(SoundEvents.f_12555_, 1.0F, 1.5F);
            this.setShieldDurability(this.getShieldDurability() + 1);
            return false;
         }
      }

      if (damage > 0.0F && this.canBlockDamageSource(source)) {
         this.m_7909_(damage);
         if (!source.m_19360_() && entity instanceof LivingEntity) {
            this.m_6728_((LivingEntity)entity);
         }

         this.m_5496_(SoundEvents.f_11669_, 0.3F, 0.5F);
         return false;
      } else {
         return super.m_6469_(source, damage);
      }
   }

   private boolean canBlockDamageSource(DamageSource damageSourceIn) {
      Entity entity = damageSourceIn.m_7640_();
      boolean flag = false;
      if (entity instanceof AbstractArrow abstractarrowentity && abstractarrowentity.m_36796_() > 0) {
         flag = true;
      }

      if (!damageSourceIn.m_19376_() && !flag && this.getIsAnger() && this.getShieldDurability() < 4) {
         Vec3 vector3d2 = damageSourceIn.m_7270_();
         if (vector3d2 != null) {
            Vec3 vector3d = this.m_20252_(1.0F);
            Vec3 vector3d1 = vector3d2.m_82505_(this.m_20182_()).m_82541_();
            vector3d1 = new Vec3(vector3d1.f_82479_, 0.0, vector3d1.f_82481_);
            return vector3d1.m_82526_(vector3d) < 0.0;
         }
      }

      return false;
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(ANGER, false);
      this.f_19804_.m_135372_(SHIELD_DURABILITY, 0);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
   }

   public void setIsAnger(boolean isAnger) {
      this.f_19804_.m_135381_(ANGER, isAnger);
   }

   public boolean getIsAnger() {
      return (Boolean)this.f_19804_.m_135370_(ANGER);
   }

   public void setShieldDurability(int ShieldDurability) {
      this.f_19804_.m_135381_(SHIELD_DURABILITY, ShieldDurability);
   }

   public int getShieldDurability() {
      return (Integer)this.f_19804_.m_135370_(SHIELD_DURABILITY);
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      LivingEntity target = this.m_5448_();
      if (!this.m_20096_() && this.m_20184_().f_82480_ < 0.0) {
         this.m_20256_(this.m_20184_().m_82542_(1.0, 0.6, 1.0));
      }

      this.prevangerProgress = this.angerProgress;
      if (this.getIsAnger() && this.angerProgress < 5.0F) {
         this.angerProgress++;
      }

      if (!this.getIsAnger() && this.angerProgress > 0.0F) {
         this.angerProgress--;
      }

      if (this.breath_cooldown > 0) {
         this.breath_cooldown--;
      }

      if (this.storm_cooldown > 0) {
         this.storm_cooldown--;
      }

      if (this.m_6084_()) {
         if (target != null && target.m_6084_()) {
            if (this.breath_cooldown <= 0
               && !this.m_21525_()
               && this.getAnimation() == NO_ANIMATION
               && this.f_19796_.m_188503_(35) == 0
               && this.m_20270_(target) < 4.5F
               && this.getShieldDurability() < 4) {
               this.breath_cooldown = 200;
               this.setAnimation(ASH_BREATH_ATTACK);
            } else if (this.storm_cooldown <= 0
               && this.m_20270_(target) < 6.0F
               && !this.m_21525_()
               && this.getAnimation() == NO_ANIMATION
               && this.f_19796_.m_188503_(15) == 0) {
               this.storm_cooldown = 200;
               this.setAnimation(BONE_STORM_ATTACK);
            } else if (!this.m_21525_()
               && this.getAnimation() == NO_ANIMATION
               && this.f_19796_.m_188503_(12) == 0
               && this.m_20270_(target) < 4.5F
               && this.getShieldDurability() > 3) {
               this.setAnimation(ASH_BREATH_ATTACK);
            }
         }

         if (this.getAnimation() == NO_ANIMATION
            && this.getIsAnger()
            && this.getShieldDurability() < 4
            && this.f_19797_ % (6 + this.getShieldDurability() * 2) == 0) {
            for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_(1.25))) {
               if (!this.m_7307_(entity) && !(entity instanceof Ignited_Revenant_Entity) && entity != this) {
                  boolean flag = entity.m_6469_(DamageSource.m_19370_(this), (float)this.m_21133_(Attributes.f_22281_));
                  if (flag) {
                     double d0 = entity.m_20185_() - this.m_20185_();
                     double d1 = entity.m_20189_() - this.m_20189_();
                     double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
                     entity.m_5997_(d0 / d2 * 1.5, 0.2, d1 / d2 * 1.5);
                  }
               }
            }
         }
      }
   }

   public boolean m_7307_(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.m_7307_(entityIn)) {
         return true;
      } else {
         return !(entityIn instanceof Ignited_Revenant_Entity) ? false : this.m_5647_() == null && entityIn.m_5647_() == null;
      }
   }

   protected SoundEvent m_7515_() {
      return (SoundEvent)ModSounds.REVENANT_IDLE.get();
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.REVENANT_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.REVENANT_DEATH.get();
   }

   @Override
   protected void onDeathAIUpdate() {
      super.onDeathAIUpdate();
   }

   protected BodyRotationControl m_7560_() {
      return new SmartBodyHelper2(this);
   }

   protected PathNavigation m_6037_(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
   }

   @Override
   protected void repelEntities(float x, float y, float z, float radius) {
      super.repelEntities(x, y, z, radius);
   }

   @Override
   public boolean canBePushedByEntity(Entity entity) {
      return false;
   }

   private void launchbone1() {
      this.m_5496_(SoundEvents.f_12520_, 1.0F, 0.75F);

      for (int i = 0; i < 8; i++) {
         float throwAngle = (float)i * (float) Math.PI / 4.0F;
         double sx = this.m_20185_() + (double)(Mth.m_14089_(throwAngle) * 1.0F);
         double sy = this.m_20186_() + (double)this.m_20206_() * 0.62;
         double sz = this.m_20189_() + (double)(Mth.m_14031_(throwAngle) * 1.0F);
         double vx = (double)Mth.m_14089_(throwAngle);
         double vy = 0.0;
         double vz = (double)Mth.m_14031_(throwAngle);
         Blazing_Bone_Entity projectile = new Blazing_Bone_Entity(this.f_19853_, (float)CMConfig.BlazingBonedamage, this);
         projectile.m_7678_(sx, sy, sz, (float)i * 45.0F, this.m_146909_());
         float speed = 0.5F;
         projectile.m_6686_(vx, vy, vz, speed, 1.0F);
         this.f_19853_.m_7967_(projectile);
      }
   }

   private void launchbone2() {
      this.m_5496_(SoundEvents.f_12520_, 1.0F, 0.75F);

      for (int i = 0; i < 6; i++) {
         float throwAngle = (float)i * (float) Math.PI / 3.0F;
         double sx = this.m_20185_() + (double)(Mth.m_14089_(throwAngle) * 1.0F);
         double sy = this.m_20186_() + (double)this.m_20206_() * 0.62;
         double sz = this.m_20189_() + (double)(Mth.m_14031_(throwAngle) * 1.0F);
         double vx = (double)Mth.m_14089_(throwAngle);
         double vy = 0.0;
         double vz = (double)Mth.m_14031_(throwAngle);
         Blazing_Bone_Entity projectile = new Blazing_Bone_Entity(this.f_19853_, (float)CMConfig.BlazingBonedamage, this);
         projectile.m_7678_(sx, sy, sz, (float)i * 60.0F, this.m_146909_());
         float speed = 0.6F;
         projectile.m_6686_(vx, vy, vz, speed, 1.0F);
         this.f_19853_.m_7967_(projectile);
      }
   }

   private void launchbone3() {
      this.m_5496_(SoundEvents.f_12520_, 1.0F, 0.75F);

      for (int i = 0; i < 10; i++) {
         float throwAngle = (float)i * (float) Math.PI / 5.0F;
         double sx = this.m_20185_() + (double)(Mth.m_14089_(throwAngle) * 1.0F);
         double sy = this.m_20186_() + (double)this.m_20206_() * 0.62;
         double sz = this.m_20189_() + (double)(Mth.m_14031_(throwAngle) * 1.0F);
         double vx = (double)Mth.m_14089_(throwAngle);
         double vy = 0.0;
         double vz = (double)Mth.m_14031_(throwAngle);
         Blazing_Bone_Entity projectile = new Blazing_Bone_Entity(this.f_19853_, (float)CMConfig.BlazingBonedamage, this);
         projectile.m_7678_(sx, sy, sz, (float)i * 36.0F, this.m_146909_());
         float speed = 0.4F;
         projectile.m_6686_(vx, vy, vz, speed, 1.0F);
         this.f_19853_.m_7967_(projectile);
      }
   }

   class BoneStormGoal extends SimpleAnimationGoal<Ignited_Revenant_Entity> {
      public BoneStormGoal(Ignited_Revenant_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
         }

         if (this.entity.getAnimationTick() == 5) {
            switch (Ignited_Revenant_Entity.this.f_19796_.m_188503_(3)) {
               case 0:
                  Ignited_Revenant_Entity.this.launchbone1();
                  break;
               case 1:
                  Ignited_Revenant_Entity.this.launchbone2();
                  break;
               case 2:
                  Ignited_Revenant_Entity.this.launchbone3();
            }
         }

         if (this.entity.getAnimationTick() == 10) {
            switch (Ignited_Revenant_Entity.this.f_19796_.m_188503_(3)) {
               case 0:
                  Ignited_Revenant_Entity.this.launchbone1();
                  break;
               case 1:
                  Ignited_Revenant_Entity.this.launchbone2();
                  break;
               case 2:
                  Ignited_Revenant_Entity.this.launchbone3();
            }
         }

         if (this.entity.getAnimationTick() == 15) {
            switch (Ignited_Revenant_Entity.this.f_19796_.m_188503_(3)) {
               case 0:
                  Ignited_Revenant_Entity.this.launchbone1();
                  break;
               case 1:
                  Ignited_Revenant_Entity.this.launchbone2();
                  break;
               case 2:
                  Ignited_Revenant_Entity.this.launchbone3();
            }
         }

         if (this.entity.getAnimationTick() == 20) {
            switch (Ignited_Revenant_Entity.this.f_19796_.m_188503_(3)) {
               case 0:
                  Ignited_Revenant_Entity.this.launchbone1();
                  break;
               case 1:
                  Ignited_Revenant_Entity.this.launchbone2();
                  break;
               case 2:
                  Ignited_Revenant_Entity.this.launchbone3();
            }
         }

         this.entity.nextHeightOffsetChangeTick--;
         if (this.entity.nextHeightOffsetChangeTick <= 0) {
            this.entity.nextHeightOffsetChangeTick = 100;
            this.entity.allowedHeightOffset = (float)this.entity.f_19796_.m_216328_(0.5, 6.891);
         }

         if (target != null && target.m_20188_() > this.entity.m_20188_() + (double)this.entity.allowedHeightOffset && this.entity.m_6779_(target)) {
            Vec3 vec3 = this.entity.m_20184_();
            this.entity.m_20256_(this.entity.m_20184_().m_82520_(0.0, (0.3F - vec3.f_82480_) * 0.3F, 0.0));
            this.entity.f_19812_ = true;
         }
      }
   }

   class Ignited_Revenant_Goal extends AttackMoveGoal {
      public Ignited_Revenant_Goal() {
         super(Ignited_Revenant_Entity.this, true, 1.1);
      }

      @Override
      public void m_8056_() {
         super.m_8056_();
         Ignited_Revenant_Entity.this.setIsAnger(true);
      }

      @Override
      public void m_8041_() {
         super.m_8041_();
         Ignited_Revenant_Entity.this.setIsAnger(false);
      }
   }

   class ShootGoal extends SimpleAnimationGoal<Ignited_Revenant_Entity> {
      public ShootGoal(Ignited_Revenant_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8056_() {
         super.m_8056_();
         Ignited_Revenant_Entity.this.setIsAnger(true);
      }

      public void m_8041_() {
         super.m_8041_();
         Ignited_Revenant_Entity.this.setIsAnger(false);
      }

      public void m_8037_() {
         LivingEntity target = Ignited_Revenant_Entity.this.m_5448_();
         if (target != null) {
            if (Ignited_Revenant_Entity.this.getAnimationTick() < 27) {
               Ignited_Revenant_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
            } else {
               Ignited_Revenant_Entity.this.m_21563_().m_24960_(target, 3.0F, 30.0F);
            }
         }

         if (Ignited_Revenant_Entity.this.getAnimationTick() == 21) {
            Ignited_Revenant_Entity.this.m_5496_((SoundEvent)ModSounds.REVENANT_BREATH.get(), 1.0F, 1.0F);
         }

         Vec3 mouthPos = new Vec3(0.0, 2.3, 0.0);
         mouthPos = mouthPos.m_82524_((float)Math.toRadians((double)(-Ignited_Revenant_Entity.this.m_146908_() - 90.0F)));
         mouthPos = mouthPos.m_82549_(Ignited_Revenant_Entity.this.m_20182_());
         mouthPos = mouthPos.m_82549_(
            new Vec3(0.0, 0.0, 0.0)
               .m_82496_((float)Math.toRadians((double)(-Ignited_Revenant_Entity.this.m_146909_())))
               .m_82524_((float)Math.toRadians((double)(-Ignited_Revenant_Entity.this.f_20885_)))
         );
         Ashen_Breath_Entity breath = new Ashen_Breath_Entity(
            (EntityType<? extends Ashen_Breath_Entity>)ModEntities.ASHEN_BREATH.get(),
            Ignited_Revenant_Entity.this.f_19853_,
            (float)CMConfig.Ashenbreathdamage,
            Ignited_Revenant_Entity.this
         );
         if (Ignited_Revenant_Entity.this.getAnimationTick() == 27) {
            breath.m_19890_(
               mouthPos.f_82479_, mouthPos.f_82480_, mouthPos.f_82481_, Ignited_Revenant_Entity.this.f_20885_, Ignited_Revenant_Entity.this.m_146909_()
            );
            Ignited_Revenant_Entity.this.f_19853_.m_7967_(breath);
         }
      }
   }
}
