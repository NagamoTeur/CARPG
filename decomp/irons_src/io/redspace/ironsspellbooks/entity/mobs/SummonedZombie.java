package io.redspace.ironsspellbooks.entity.mobs;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.effect.SummonTimer;
import io.redspace.ironsspellbooks.entity.mobs.goals.GenericCopyOwnerTargetGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.GenericFollowOwnerGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.GenericHurtByTargetGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.GenericOwnerHurtByTargetGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.GenericOwnerHurtTargetGoal;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.util.OwnerHelper;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class SummonedZombie extends Zombie implements MagicSummon, IAnimatable {
   private static final EntityDataAccessor<Boolean> DATA_IS_ANIMATING_RISE = SynchedEntityData.m_135353_(SummonedZombie.class, EntityDataSerializers.f_135035_);
   protected LivingEntity cachedSummoner;
   protected UUID summonerUUID;
   private int riseAnimTime = 80;
   private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public SummonedZombie(EntityType<? extends Zombie> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.f_21364_ = 0;
   }

   public SummonedZombie(Level level, LivingEntity owner, boolean playRiseAnimation) {
      this((EntityType<? extends Zombie>)EntityRegistry.SUMMONED_ZOMBIE.get(), level);
      this.setSummoner(owner);
      if (playRiseAnimation) {
         this.triggerRiseAnimation();
      }
   }

   public void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new MeleeAttackGoal(this, 1.2F, true));
      this.f_21345_.m_25352_(7, new GenericFollowOwnerGoal(this, this::getSummoner, 0.9F, 15.0F, 5.0F, false, 25.0F));
      this.f_21345_.m_25352_(8, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.f_21346_.m_25352_(1, new GenericOwnerHurtByTargetGoal(this, this::getSummoner));
      this.f_21346_.m_25352_(2, new GenericOwnerHurtTargetGoal(this, this::getSummoner));
      this.f_21346_.m_25352_(3, new GenericCopyOwnerTargetGoal(this, this::getSummoner));
      this.f_21346_.m_25352_(4, new GenericHurtByTargetGoal(this, entity -> entity == this.getSummoner()).setAlertOthers());
   }

   public boolean m_6935_(Player pPlayer) {
      return !this.m_7307_(pPlayer);
   }

   protected boolean m_5884_() {
      return false;
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(DATA_IS_ANIMATING_RISE, false);
   }

   public SpawnGroupData m_6518_(
      ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag
   ) {
      RandomSource randomsource = Utils.random;
      this.m_213945_(randomsource, pDifficulty);
      if (randomsource.m_188500_() < 0.25) {
         this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42383_));
      }

      return pSpawnData;
   }

   public boolean m_7307_(Entity pEntity) {
      return super.m_7307_(pEntity) || this.isAlliedHelper(pEntity);
   }

   @Override
   public LivingEntity getSummoner() {
      return OwnerHelper.getAndCacheOwner(this.f_19853_, this.cachedSummoner, this.summonerUUID);
   }

   public void setSummoner(@Nullable LivingEntity owner) {
      if (owner != null) {
         this.summonerUUID = owner.m_20148_();
         this.cachedSummoner = owner;
      }
   }

   public void m_6667_(DamageSource pDamageSource) {
      this.onDeathHelper();
      super.m_6667_(pDamageSource);
   }

   public void onRemovedFromWorld() {
      this.onRemovedHelper(this, (SummonTimer)MobEffectRegistry.RAISE_DEAD_TIMER.get());
      super.onRemovedFromWorld();
   }

   public void m_142687_(RemovalReason pReason) {
      super.m_142687_(pReason);
   }

   public boolean m_7327_(Entity pEntity) {
      return Utils.doMeleeAttack(this, pEntity, ((AbstractSpell)SpellRegistry.RAISE_DEAD_SPELL.get()).getDamageSource(this, this.getSummoner()));
   }

   public boolean m_6469_(DamageSource pSource, float pAmount) {
      return pSource.m_19378_() || !this.isAnimatingRise() && !this.shouldIgnoreDamage(pSource) ? super.m_6469_(pSource, pAmount) : false;
   }

   public void m_8119_() {
      if (this.isAnimatingRise()) {
         if (this.f_19853_.f_46443_) {
            this.clientDiggingParticles(this);
         }

         if (--this.riseAnimTime < 0) {
            this.f_19804_.m_135381_(DATA_IS_ANIMATING_RISE, false);
            this.m_146926_(0.0F);
            this.m_146867_();
         }
      } else {
         super.m_8119_();
      }
   }

   protected boolean m_8028_() {
      return false;
   }

   @Override
   public void onUnSummon() {
      if (!this.f_19853_.f_46443_) {
         MagicManager.spawnParticles(this.f_19853_, ParticleTypes.f_123759_, this.m_20185_(), this.m_20186_(), this.m_20189_(), 25, 0.4, 0.8, 0.4, 0.03, false);
         this.m_146870_();
      }
   }

   public void m_7378_(CompoundTag compoundTag) {
      super.m_7378_(compoundTag);
      this.summonerUUID = OwnerHelper.deserializeOwner(compoundTag);
   }

   public void m_7380_(CompoundTag compoundTag) {
      super.m_7380_(compoundTag);
      OwnerHelper.serializeOwner(compoundTag, this.summonerUUID);
   }

   protected void clientDiggingParticles(LivingEntity livingEntity) {
      RandomSource randomsource = livingEntity.m_217043_();
      BlockState blockstate = livingEntity.m_20075_();
      if (blockstate.m_60799_() != RenderShape.INVISIBLE) {
         for (int i = 0; i < 15; i++) {
            double d0 = livingEntity.m_20185_() + (double)Mth.m_216283_(randomsource, -0.5F, 0.5F);
            double d1 = livingEntity.m_20186_();
            double d2 = livingEntity.m_20189_() + (double)Mth.m_216283_(randomsource, -0.5F, 0.5F);
            livingEntity.f_19853_.m_7106_(new BlockParticleOption(ParticleTypes.f_123794_, blockstate), d0, d1, d2, 0.0, 0.0, 0.0);
         }
      }
   }

   public boolean isAnimatingRise() {
      return (Boolean)this.f_19804_.m_135370_(DATA_IS_ANIMATING_RISE);
   }

   public void triggerRiseAnimation() {
      this.f_19804_.m_135381_(DATA_IS_ANIMATING_RISE, true);
   }

   public boolean m_6094_() {
      return super.m_6094_() && !this.isAnimatingRise();
   }

   protected boolean m_6107_() {
      return super.m_6107_() || this.isAnimatingRise();
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }

   public void registerControllers(AnimationData data) {
      data.addAnimationController(new AnimationController(this, "rise", 0.0F, this::risePredicate));
   }

   private PlayState risePredicate(AnimationEvent event) {
      if (!this.isAnimatingRise()) {
         return PlayState.STOP;
      } else {
         if (event.getController().getAnimationState() == AnimationState.Stopped) {
            String animation = new String[]{"rise_from_ground_01", "rise_from_ground_02", "rise_from_ground_03", "rise_from_ground_04"}[this.f_19796_
               .m_216332_(0, 3)];
            event.getController().setAnimation(new AnimationBuilder().addAnimation(animation, EDefaultLoopTypes.LOOP));
         }

         return PlayState.CONTINUE;
      }
   }
}
