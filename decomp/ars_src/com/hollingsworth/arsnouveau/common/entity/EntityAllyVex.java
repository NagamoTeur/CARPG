package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.entity.ISummon;
import com.hollingsworth.arsnouveau.common.entity.goal.FollowSummonerFlyingGoal;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;

public class EntityAllyVex extends Vex implements IFollowingSummon, ISummon {
   private LivingEntity owner;
   @Nullable
   private BlockPos boundOrigin;
   private boolean limitedLifespan;
   private int limitedLifeTicks;

   public EntityAllyVex(EntityType<? extends Vex> p_i50190_1_, Level p_i50190_2_) {
      super((EntityType)ModEntities.ALLY_VEX.get(), p_i50190_2_);
   }

   public EntityAllyVex(Level p_i50190_2_, LivingEntity owner) {
      super(EntityType.f_20491_, p_i50190_2_);
      this.owner = owner;
      this.limitedLifespan = false;
      this.setOwnerID(owner.m_20148_());
      this.f_21342_ = new EntityAllyVex.MoveHelperController(this);
   }

   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.ALLY_VEX.get();
   }

   @Nullable
   public SpawnGroupData m_6518_(
      ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag
   ) {
      this.populateDefaultEquipmentSlots(difficultyIn);
      this.m_213946_(this.m_217043_(), difficultyIn);
      return super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
   }

   protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
      this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42388_));
      this.m_21409_(EquipmentSlot.MAINHAND, 0.0F);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new EntityAllyVex.ChargeAttackGoal());
      this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.f_21345_.m_25352_(2, new FollowSummonerFlyingGoal(this, this.owner, 1.0, 6.0F, 3.0F));
      this.f_21346_.m_25352_(1, new IFollowingSummon.CopyOwnerTargetGoal(this));
      this.f_21346_
         .m_25352_(
            3,
            new NearestAttackableTargetGoal(
               this,
               Mob.class,
               10,
               false,
               true,
               entity -> entity instanceof Mob && ((Mob)entity).m_5448_() != null && ((Mob)entity).m_5448_().equals(this.owner)
                     || entity instanceof LivingEntity && entity.m_21232_() != null && entity.m_21232_().equals(this.owner)
            )
         );
   }

   protected PathNavigation m_6037_(Level worldIn) {
      FlyingPathNavigation flyingpathnavigator = new FlyingPathNavigation(this, worldIn);
      flyingpathnavigator.m_26440_(false);
      flyingpathnavigator.m_7008_(true);
      flyingpathnavigator.m_26443_(true);
      return flyingpathnavigator;
   }

   public void setOwner(LivingEntity owner) {
      this.owner = owner;
   }

   @Override
   public Level getWorld() {
      return this.f_19853_;
   }

   @Override
   public PathNavigation getPathNav() {
      return this.f_21344_;
   }

   @Override
   public Mob getSelfEntity() {
      return this;
   }

   @Override
   public LivingEntity getSummoner() {
      return this.getOwnerFromID();
   }

   public LivingEntity getActualOwner() {
      return this.owner;
   }

   public int m_213860_() {
      return 0;
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      if (compound.m_128441_("BoundX")) {
         this.boundOrigin = new BlockPos(compound.m_128451_("BoundX"), compound.m_128451_("BoundY"), compound.m_128451_("BoundZ"));
      }

      if (compound.m_128441_("LifeTicks")) {
         this.m_33987_(compound.m_128451_("LifeTicks"));
      }

      UUID s;
      if (compound.m_128425_("OwnerUUID", 8)) {
         s = compound.m_128342_("OwnerUUID");
      } else {
         String s1 = compound.m_128461_("Owner");
         s = OldUsersConverter.m_11083_(this.m_20194_(), s1);
      }

      if (s != null) {
         try {
            this.setOwnerID(s);
         } catch (Throwable var4) {
         }
      }
   }

   public LivingEntity getOwnerFromID() {
      try {
         UUID uuid = this.m_21805_();
         return uuid == null ? null : this.f_19853_.m_46003_(uuid);
      } catch (IllegalArgumentException var21) {
         return null;
      }
   }

   public Team m_5647_() {
      if (this.m_34026_() != null) {
         LivingEntity livingentity = this.m_34026_();
         if (livingentity != null) {
            return livingentity.m_5647_();
         }
      }

      return super.m_5647_();
   }

   public boolean m_7307_(Entity pEntity) {
      if (this.getSummoner() == null) {
         return super.m_7307_(pEntity);
      } else {
         LivingEntity livingentity = this.getSummoner();
         return pEntity == livingentity || livingentity.m_7307_(pEntity);
      }
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(OWNER_UNIQUE_ID, Optional.empty());
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      if (this.boundOrigin != null) {
         compound.m_128405_("BoundX", this.boundOrigin.m_123341_());
         compound.m_128405_("BoundY", this.boundOrigin.m_123342_());
         compound.m_128405_("BoundZ", this.boundOrigin.m_123343_());
      }

      if (this.limitedLifespan) {
         compound.m_128405_("LifeTicks", this.limitedLifeTicks);
      }

      if (this.m_21805_() == null) {
         compound.m_128362_("OwnerUUID", Util.f_137441_);
      } else {
         compound.m_128362_("OwnerUUID", this.m_21805_());
      }
   }

   public void m_6667_(DamageSource cause) {
      super.m_6667_(cause);
      this.onSummonDeath(this.f_19853_, cause, false);
   }

   @Override
   public int getTicksLeft() {
      return this.limitedLifeTicks;
   }

   @Override
   public void setTicksLeft(int ticks) {
      this.limitedLifeTicks = ticks;
   }

   @Override
   public UUID m_21805_() {
      return (UUID)((Optional)this.f_19804_.m_135370_(OWNER_UNIQUE_ID)).orElse(null);
   }

   @Override
   public void setOwnerID(UUID uuid) {
      this.f_19804_.m_135381_(OWNER_UNIQUE_ID, Optional.ofNullable(uuid));
   }

   class ChargeAttackGoal extends Goal {
      public ChargeAttackGoal() {
         this.m_7021_(EnumSet.of(Flag.MOVE));
      }

      public boolean m_8036_() {
         return EntityAllyVex.this.m_5448_() != null && !EntityAllyVex.this.m_21566_().m_24995_() && EntityAllyVex.this.f_19796_.m_188503_(7) == 0
            ? EntityAllyVex.this.m_20280_(EntityAllyVex.this.m_5448_()) > 4.0
            : false;
      }

      public boolean m_8045_() {
         return EntityAllyVex.this.m_21566_().m_24995_()
            && EntityAllyVex.this.m_34028_()
            && EntityAllyVex.this.m_5448_() != null
            && EntityAllyVex.this.m_5448_().m_6084_();
      }

      public void m_8056_() {
         LivingEntity livingentity = EntityAllyVex.this.m_5448_();
         Vec3 vec3d = livingentity.m_20299_(1.0F);
         EntityAllyVex.this.f_21342_.m_6849_(vec3d.f_82479_, vec3d.f_82480_, vec3d.f_82481_, 1.0);
         EntityAllyVex.this.m_34042_(true);
         EntityAllyVex.this.m_5496_(SoundEvents.f_12500_, 1.0F, 1.0F);
      }

      public void m_8041_() {
         EntityAllyVex.this.m_34042_(false);
      }

      public void m_8037_() {
         LivingEntity livingentity = EntityAllyVex.this.m_5448_();
         if (EntityAllyVex.this.m_20191_().m_82381_(livingentity.m_20191_())) {
            EntityAllyVex.this.m_7327_(livingentity);
            EntityAllyVex.this.m_34042_(false);
         } else {
            double d0 = EntityAllyVex.this.m_20280_(livingentity);
            if (d0 < 9.0) {
               Vec3 vec3d = livingentity.m_20299_(1.0F);
               EntityAllyVex.this.f_21342_.m_6849_(vec3d.f_82479_, vec3d.f_82480_, vec3d.f_82481_, 1.0);
            }
         }
      }
   }

   class MoveHelperController extends MoveControl {
      public MoveHelperController(Vex vex) {
         super(vex);
      }

      public void m_8126_() {
         if (this.f_24981_ == Operation.MOVE_TO) {
            Vec3 vec3d = new Vec3(
               this.f_24975_ - EntityAllyVex.this.m_20185_(), this.f_24976_ - EntityAllyVex.this.m_20186_(), this.f_24977_ - EntityAllyVex.this.m_20189_()
            );
            double d0 = vec3d.m_82553_();
            if (d0 < EntityAllyVex.this.m_20191_().m_82309_()) {
               this.f_24981_ = Operation.WAIT;
               EntityAllyVex.this.m_20256_(EntityAllyVex.this.m_20184_().m_82490_(0.5));
            } else {
               EntityAllyVex.this.m_20256_(EntityAllyVex.this.m_20184_().m_82549_(vec3d.m_82490_(this.f_24978_ * 0.05 / d0)));
               if (EntityAllyVex.this.m_5448_() == null) {
                  Vec3 vec3d1 = EntityAllyVex.this.m_20184_();
                  EntityAllyVex.this.f_19857_ = -((float)Mth.m_14136_(vec3d1.f_82479_, vec3d1.f_82481_)) * (180.0F / (float)Math.PI);
                  EntityAllyVex.this.f_20883_ = EntityAllyVex.this.f_19857_;
               } else {
                  double d2 = EntityAllyVex.this.m_5448_().m_20185_() - EntityAllyVex.this.m_20185_();
                  double d1 = EntityAllyVex.this.m_5448_().m_20189_() - EntityAllyVex.this.m_20189_();
                  EntityAllyVex.this.f_19857_ = -((float)Mth.m_14136_(d2, d1)) * (180.0F / (float)Math.PI);
                  EntityAllyVex.this.f_20883_ = EntityAllyVex.this.f_19857_;
               }
            }
         }
      }
   }
}
