package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.entity.ISummon;
import com.hollingsworth.arsnouveau.common.entity.goal.FollowSummonerGoal;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.scores.Team;
import org.jetbrains.annotations.Nullable;

public class SummonSkeleton extends Skeleton implements IFollowingSummon, ISummon {
   private final RangedBowAttackGoal<SummonSkeleton> bowGoal = new RangedBowAttackGoal(this, 1.0, 20, 15.0F);
   private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 2.2, true) {
      public void m_8041_() {
         super.m_8041_();
         SummonSkeleton.this.m_21561_(false);
      }

      public void m_8056_() {
         super.m_8056_();
         SummonSkeleton.this.m_21561_(true);
      }
   };
   private LivingEntity owner;
   @Nullable
   private BlockPos boundOrigin;
   private boolean limitedLifespan;
   private int limitedLifeTicks;

   public SummonSkeleton(EntityType<? extends Skeleton> entityType, Level level) {
      super(entityType, level);
   }

   public SummonSkeleton(Level level, LivingEntity owner, ItemStack item) {
      super((EntityType)ModEntities.SUMMON_SKELETON.get(), level);
      this.setWeapon(item);
      this.owner = owner;
      this.limitedLifespan = true;
      this.setOwnerID(owner.m_20148_());
   }

   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.SUMMON_SKELETON.get();
   }

   @Nullable
   public SpawnGroupData m_6518_(
      ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag
   ) {
      this.m_213945_(this.m_217043_(), difficultyIn);
      this.m_213946_(this.m_217043_(), difficultyIn);
      return super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
   }

   protected void m_213945_(RandomSource randomSource, DifficultyInstance pDifficulty) {
   }

   protected void m_6668_(DamageSource pDamageSource) {
   }

   protected boolean m_6125_() {
      return false;
   }

   protected void m_7472_(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
   }

   protected void m_5907_() {
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.f_21345_.m_25352_(2, new FollowSummonerGoal(this, this.owner, 1.0, 9.0F, 3.0F));
      this.f_21345_.m_25352_(4, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.f_21346_
         .m_25352_(
            2,
            new HurtByTargetGoal(this, SummonSkeleton.class) {
               protected boolean m_26150_(@Nullable LivingEntity pPotentialTarget, TargetingConditions pTargetPredicate) {
                  return pPotentialTarget != null
                     && super.m_26150_(pPotentialTarget, pTargetPredicate)
                     && !pPotentialTarget.m_20148_().equals(SummonSkeleton.this.m_21805_());
               }
            }
         );
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
               entity -> entity instanceof Mob mob && mob.m_5448_() != null && mob.m_5448_().equals(this.owner)
                     || entity != null && entity.m_21232_() != null && entity.m_21232_().equals(this.owner)
            )
         );
   }

   public void setOwner(LivingEntity owner) {
      this.owner = owner;
   }

   public void setWeapon(ItemStack item) {
      this.m_8061_(EquipmentSlot.MAINHAND, item);
      this.m_32164_();
   }

   public void m_32164_() {
      if (this.f_19853_ instanceof ServerLevel && this.m_21120_(InteractionHand.MAIN_HAND) != ItemStack.f_41583_) {
         this.f_21345_.m_25363_(this.meleeGoal);
         this.f_21345_.m_25363_(this.bowGoal);
         ItemStack itemstack = this.m_21120_(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof BowItem));
         if (itemstack.m_150930_(Items.f_42411_)) {
            this.bowGoal.m_25797_(20);
            this.f_21345_.m_25352_(4, this.bowGoal);
         } else {
            this.f_21345_.m_25352_(4, this.meleeGoal);
         }
      }
   }

   public boolean m_6469_(DamageSource pSource, float pAmount) {
      if (pSource instanceof EntityDamageSource eSource
         && eSource.m_7639_() instanceof ISummon summon
         && summon.m_21805_() != null
         && summon.m_21805_().equals(this.m_21805_())) {
         return false;
      }

      return super.m_6469_(pSource, pAmount);
   }

   public void m_8119_() {
      super.m_8119_();
      if (--this.limitedLifeTicks <= 0) {
         this.limitedLifeTicks = 20;
         this.m_6469_(DamageSource.f_19313_, 20.0F);
      }
   }

   public Team m_5647_() {
      return this.getSummoner() != null ? this.getSummoner().m_5647_() : super.m_5647_();
   }

   public boolean m_7307_(Entity pEntity) {
      LivingEntity summoner = this.getSummoner();
      if (summoner != null) {
         if (pEntity instanceof ISummon summon && summon.m_21805_() != null && summon.m_21805_().equals(this.m_21805_())) {
            return true;
         }

         return pEntity == summoner || summoner.m_7307_(pEntity);
      } else {
         return super.m_7307_(pEntity);
      }
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
         this.setLimitedLife(compound.m_128451_("LifeTicks"));
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

   public void setLimitedLife(int lifeTicks) {
      this.limitedLifeTicks = lifeTicks;
   }

   public LivingEntity getOwnerFromID() {
      try {
         UUID uuid = this.m_21805_();
         return uuid == null ? null : this.f_19853_.m_46003_(uuid);
      } catch (IllegalArgumentException var21) {
         return null;
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

   protected boolean m_21527_() {
      return false;
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

   @Nullable
   @Override
   public UUID m_21805_() {
      return (UUID)((Optional)this.f_19804_.m_135370_(OWNER_UNIQUE_ID)).orElse(null);
   }

   @Override
   public void setOwnerID(UUID uuid) {
      this.f_19804_.m_135381_(OWNER_UNIQUE_ID, Optional.ofNullable(uuid));
   }
}
