package net.xylonity.knightquest.common.entity.boss;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.Vec3;
import net.xylonity.knightquest.common.ai.navigator.GroundNavigator;
import net.xylonity.knightquest.common.entity.boss.ai.NethermanAttackGoal;
import net.xylonity.knightquest.common.entity.boss.ai.NethermanClonesGoal;
import net.xylonity.knightquest.common.entity.boss.ai.NethermanDarknessGoal;
import net.xylonity.knightquest.common.entity.boss.ai.NethermanFlameGoal;
import net.xylonity.knightquest.common.entity.boss.ai.NethermanIceGoal;
import net.xylonity.knightquest.common.entity.boss.ai.NethermanLavaTeleportGoal;
import net.xylonity.knightquest.common.entity.boss.ai.NethermanNearestAttackableTargetGoal;
import net.xylonity.knightquest.common.entity.boss.ai.NethermanProjectileChargesGoal;
import net.xylonity.knightquest.common.entity.boss.ai.NethermanTeleport2PlayerGoal;
import net.xylonity.knightquest.config.values.KQConfigValues;
import net.xylonity.knightquest.registry.KnightQuestItems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class NethermanEntity extends Monster implements IAnimatable {
   private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
   private final ServerBossEvent bossInfo = (ServerBossEvent)new ServerBossEvent(this.m_5446_(), BossBarColor.RED, BossBarOverlay.PROGRESS).m_7003_(true);
   private final Map<BlockPos, BlockState> changedBlocks = new HashMap<>();
   private final AnimationBuilder DEATH = new AnimationBuilder().playOnce("death");
   private final AnimationBuilder SUMMONANIM = new AnimationBuilder().playOnce("summon");
   private final AnimationBuilder WALKANIM = new AnimationBuilder().loop("walk");
   private final AnimationBuilder IDLEANIM = new AnimationBuilder().playOnce("idle");
   private final AnimationBuilder SPECIALATTACKANIM = new AnimationBuilder().playOnce("attack_special");
   private final AnimationBuilder SPECIALATTACK2ANIM = new AnimationBuilder().playOnce("attack_special2");
   private final AnimationBuilder SPECIALATTACK3ANIM = new AnimationBuilder().playOnce("attack_special3");
   private final AnimationBuilder PHASE_SWITCH_2 = new AnimationBuilder().playOnce("phase2");
   private final AnimationBuilder PHASE_SWITCH_3 = new AnimationBuilder().playOnce("phase3");
   private static final EntityDataAccessor<Boolean> SUMMON = SynchedEntityData.m_135353_(NethermanEntity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> NOMOVEMENT = SynchedEntityData.m_135353_(NethermanEntity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> INVULNERABLE = SynchedEntityData.m_135353_(NethermanEntity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> IS_DOING_FLAME_ATTACK = SynchedEntityData.m_135353_(NethermanEntity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> IS_DOING_SPECIAL_ATTACK2 = SynchedEntityData.m_135353_(
      NethermanEntity.class, EntityDataSerializers.f_135035_
   );
   private static final EntityDataAccessor<Boolean> IS_DOING_SPECIAL_ATTACK3 = SynchedEntityData.m_135353_(
      NethermanEntity.class, EntityDataSerializers.f_135035_
   );
   private static final EntityDataAccessor<Boolean> SHOULD_SEARCH_TARGET = SynchedEntityData.m_135353_(NethermanEntity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.m_135353_(NethermanEntity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> REVERT_FLAME_ATTACK = SynchedEntityData.m_135353_(NethermanEntity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> REVERT_SPECIAL_ATTACK2 = SynchedEntityData.m_135353_(NethermanEntity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> REVERT_SPECIAL_ATTACK3 = SynchedEntityData.m_135353_(NethermanEntity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> COUNTER_SWITCH_PHASE_2 = SynchedEntityData.m_135353_(NethermanEntity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> COUNTER_SWITCH_PHASE_3 = SynchedEntityData.m_135353_(NethermanEntity.class, EntityDataSerializers.f_135028_);
   private boolean hasBeenSwitchedToPhase2 = false;
   private boolean hasBeenSwitchedToPhase3 = false;

   public NethermanEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   @NotNull
   protected PathNavigation m_6037_(@NotNull Level pLevel) {
      return new GroundNavigator(this, pLevel);
   }

   public static Builder setAttributes() {
      return Monster.m_21552_()
         .m_22268_(Attributes.f_22276_, 450.0)
         .m_22268_(Attributes.f_22281_, 16.0)
         .m_22268_(Attributes.f_22283_, 1.2F)
         .m_22268_(Attributes.f_22279_, 0.8F)
         .m_22268_(Attributes.f_22277_, 75.0)
         .m_22268_(Attributes.f_22278_, 5.0);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new NethermanAttackGoal(this, 0.5, true));
      this.f_21345_.m_25352_(4, new NethermanTeleport2PlayerGoal(this));
      this.f_21345_.m_25352_(2, new NethermanLavaTeleportGoal(this));
      this.f_21345_.m_25352_(3, new NethermanFlameGoal(this));
      this.f_21345_.m_25352_(2, new NethermanClonesGoal(this));
      this.f_21345_.m_25352_(3, new NethermanIceGoal(this));
      this.f_21345_.m_25352_(2, new NethermanProjectileChargesGoal(this));
      this.f_21345_.m_25352_(3, new NethermanDarknessGoal(this));
      this.f_21346_.m_25352_(1, new NethermanNearestAttackableTargetGoal(this, Player.class, true));
   }

   @Nullable
   public SpawnGroupData m_6518_(
      @NotNull ServerLevelAccessor pLevel,
      @NotNull DifficultyInstance pDifficulty,
      @NotNull MobSpawnType pReason,
      @Nullable SpawnGroupData pSpawnData,
      @Nullable CompoundTag pDataTag
   ) {
      AttributeInstance maxHealth = this.m_21051_(Attributes.f_22276_);
      if (maxHealth != null) {
         maxHealth.m_22100_(KQConfigValues.NETHERMAN_HEALTH);
         this.m_21153_((float)KQConfigValues.NETHERMAN_HEALTH);
      }

      AttributeInstance attackDamageAttribute = this.m_21051_(Attributes.f_22281_);
      if (attackDamageAttribute != null) {
         attackDamageAttribute.m_22100_(KQConfigValues.NETHERMAN_DAMAGE);
      }

      return super.m_6518_(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
   }

   public boolean getInvulnerability() {
      return (Boolean)this.f_19804_.m_135370_(INVULNERABLE);
   }

   public void setInvulnerability(boolean invulnerability) {
      this.f_19804_.m_135381_(INVULNERABLE, invulnerability);
   }

   public int getPhase() {
      return (Integer)this.f_19804_.m_135370_(PHASE);
   }

   public void setPhase(int phase) {
      this.f_19804_.m_135381_(PHASE, phase);
   }

   public boolean getIsSummoning() {
      return (Boolean)this.f_19804_.m_135370_(SUMMON);
   }

   public void setIsSummoning(boolean summon) {
      this.f_19804_.m_135381_(SUMMON, summon);
   }

   public boolean getNoMovement() {
      return (Boolean)this.f_19804_.m_135370_(NOMOVEMENT);
   }

   public void setNoMovement(boolean noMovement) {
      this.f_19804_.m_135381_(NOMOVEMENT, noMovement);
   }

   public boolean getShouldSearchTarget() {
      return (Boolean)this.f_19804_.m_135370_(SHOULD_SEARCH_TARGET);
   }

   public void setShouldSearchTarget(boolean shouldSearchTarget) {
      this.f_19804_.m_135381_(SHOULD_SEARCH_TARGET, shouldSearchTarget);
   }

   public boolean getIsDoingFlameAttack() {
      return (Boolean)this.f_19804_.m_135370_(IS_DOING_FLAME_ATTACK);
   }

   public void setIsDoingFlameAttack(boolean isDoingFlameAttack) {
      this.f_19804_.m_135381_(IS_DOING_FLAME_ATTACK, isDoingFlameAttack);
   }

   public int getRevertFlameAttack() {
      return (Integer)this.f_19804_.m_135370_(REVERT_FLAME_ATTACK);
   }

   public void setRevertFlameAttack(int revertFlameAttack) {
      this.f_19804_.m_135381_(REVERT_FLAME_ATTACK, revertFlameAttack);
   }

   public int getRevertSpecialAttack2() {
      return (Integer)this.f_19804_.m_135370_(REVERT_SPECIAL_ATTACK2);
   }

   public void setRevertSpecialAttack2(int revertSpecialAttack) {
      this.f_19804_.m_135381_(REVERT_SPECIAL_ATTACK2, revertSpecialAttack);
   }

   public int getCounterSwitchPhase2() {
      return (Integer)this.f_19804_.m_135370_(COUNTER_SWITCH_PHASE_2);
   }

   public void setCounterSwitchPhase2(int counterSwitchPhase2) {
      this.f_19804_.m_135381_(COUNTER_SWITCH_PHASE_2, counterSwitchPhase2);
   }

   public int getCounterSwitchPhase3() {
      return (Integer)this.f_19804_.m_135370_(COUNTER_SWITCH_PHASE_3);
   }

   public void setCounterSwitchPhase3(int counterSwitchPhase3) {
      this.f_19804_.m_135381_(COUNTER_SWITCH_PHASE_3, counterSwitchPhase3);
   }

   public boolean getIsDoingSpecialAttack2() {
      return (Boolean)this.f_19804_.m_135370_(IS_DOING_SPECIAL_ATTACK2);
   }

   public void setIsDoingSpecialAttack2(boolean isDoingSpecialAttack) {
      this.f_19804_.m_135381_(IS_DOING_SPECIAL_ATTACK2, isDoingSpecialAttack);
   }

   public boolean getIsDoingSpecialAttack3() {
      return (Boolean)this.f_19804_.m_135370_(IS_DOING_SPECIAL_ATTACK3);
   }

   public void setIsDoingSpecialAttack3(boolean isDoingSpecialAttack) {
      this.f_19804_.m_135381_(IS_DOING_SPECIAL_ATTACK3, isDoingSpecialAttack);
   }

   public int getRevertSpecialAttack3() {
      return (Integer)this.f_19804_.m_135370_(REVERT_SPECIAL_ATTACK3);
   }

   public void setRevertSpecialAttack3(int revertSpecialAttack) {
      this.f_19804_.m_135381_(REVERT_SPECIAL_ATTACK3, revertSpecialAttack);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(PHASE, 1);
      this.f_19804_.m_135372_(INVULNERABLE, true);
      this.f_19804_.m_135372_(SUMMON, true);
      this.f_19804_.m_135372_(NOMOVEMENT, true);
      this.f_19804_.m_135372_(SHOULD_SEARCH_TARGET, false);
      this.f_19804_.m_135372_(IS_DOING_FLAME_ATTACK, false);
      this.f_19804_.m_135372_(IS_DOING_SPECIAL_ATTACK2, false);
      this.f_19804_.m_135372_(IS_DOING_SPECIAL_ATTACK3, false);
      this.f_19804_.m_135372_(REVERT_FLAME_ATTACK, 0);
      this.f_19804_.m_135372_(REVERT_SPECIAL_ATTACK2, 0);
      this.f_19804_.m_135372_(REVERT_SPECIAL_ATTACK3, 0);
      this.f_19804_.m_135372_(COUNTER_SWITCH_PHASE_2, 0);
      this.f_19804_.m_135372_(COUNTER_SWITCH_PHASE_3, 0);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.m_5776_() && this.f_19797_ == 100) {
         this.setIsSummoning(false);
         this.setNoMovement(false);
         this.setInvulnerability(false);
         this.setShouldSearchTarget(true);
      }

      this.bossInfo.m_142711_(this.m_21223_() / this.m_21233_());
      if (this.m_6060_()) {
         this.m_7311_(0);
      }

      if (this.getNoMovement()) {
         this.m_21573_().m_26573_();
         this.m_20256_(Vec3.f_82478_);
      }

      if (this.getNoMovement()) {
         if (this.getIsDoingFlameAttack()) {
            this.setRevertFlameAttack(this.getRevertFlameAttack() + 1);
            if (this.getRevertFlameAttack() == 30) {
               this.setNoMovement(false);
               this.setIsDoingFlameAttack(false);
               this.setRevertFlameAttack(0);
            }
         }

         if (this.getIsDoingSpecialAttack2()) {
            this.setRevertSpecialAttack2(this.getRevertSpecialAttack2() + 1);
            if (this.getRevertSpecialAttack2() == 30) {
               this.setNoMovement(false);
               this.setIsDoingSpecialAttack2(false);
               this.setRevertSpecialAttack2(0);
            }
         }

         if (this.getIsDoingSpecialAttack3()) {
            this.setRevertSpecialAttack3(this.getRevertSpecialAttack3() + 1);
            if (this.getRevertSpecialAttack3() == 30) {
               this.setNoMovement(false);
               this.setIsDoingSpecialAttack3(false);
               this.setRevertSpecialAttack3(0);
            }
         }
      }

      if ((double)this.m_21223_() < (double)this.m_21233_() * 0.66 && this.getPhase() != 2 && this.getPhase() != 3) {
         this.setPhase(2);
         this.setNoMovement(true);
         this.setShouldSearchTarget(false);
         this.m_6710_(null);
         this.setInvulnerability(true);
      }

      if (this.getPhase() == 2 && this.getCounterSwitchPhase2() < 130) {
         this.setCounterSwitchPhase2(this.getCounterSwitchPhase2() + 1);
      }

      if (this.getCounterSwitchPhase2() == 130 && !this.hasBeenSwitchedToPhase2) {
         this.setShouldSearchTarget(true);
         this.setNoMovement(false);
         this.setInvulnerability(false);
         this.hasBeenSwitchedToPhase2 = true;
      }

      if ((double)this.m_21223_() < (double)this.m_21233_() * 0.33 && this.getPhase() != 3) {
         this.setPhase(3);
         this.setNoMovement(true);
         this.setShouldSearchTarget(false);
         this.m_6710_(null);
         this.setInvulnerability(true);
      }

      if (this.getPhase() == 3 && this.getCounterSwitchPhase3() < 160) {
         this.setCounterSwitchPhase3(this.getCounterSwitchPhase3() + 1);
      }

      if (this.getCounterSwitchPhase3() == 160 && !this.hasBeenSwitchedToPhase3) {
         this.setShouldSearchTarget(true);
         this.setNoMovement(false);
         this.setInvulnerability(false);
         this.hasBeenSwitchedToPhase3 = true;
      }
   }

   protected void m_7472_(@NotNull DamageSource pSource, int pLooting, boolean pRecentlyHit) {
      super.m_7472_(pSource, pLooting, pRecentlyHit);
      this.m_19983_(new ItemStack((ItemLike)KnightQuestItems.CHAOTIC_ESSENCE.get()));
   }

   private void restoreBlocks() {
      for (Entry<BlockPos, BlockState> entry : this.changedBlocks.entrySet()) {
         this.f_19853_.m_7731_(entry.getKey(), entry.getValue(), 3);
      }

      this.changedBlocks.clear();
   }

   public void m_6667_(@NotNull DamageSource pDamageSource) {
      super.m_6667_(pDamageSource);
      if (KQConfigValues.RESTORE_BLOCKS_POST_DEATH && this.f_19853_.m_46469_().m_46207_(GameRules.f_46132_)) {
         this.restoreBlocks();
      }
   }

   public void saveBlockState(BlockPos pos) {
      if (!this.changedBlocks.containsKey(pos)) {
         BlockState state = this.f_19853_.m_8055_(pos);
         this.changedBlocks.put(pos, state);
      }
   }

   public boolean m_6785_(double pDistanceToClosestPlayer) {
      return false;
   }

   public boolean m_6469_(@NotNull DamageSource pSource, float pAmount) {
      if (!this.getInvulnerability()
         && (!pSource.m_19384_() || this.getPhase() != 1)
         && (!pSource.m_19387_() || this.getPhase() != 3)
         && !pSource.m_19372_()
         && this.f_19797_ >= 40) {
         boolean isDamaged = super.m_6469_(pSource, pAmount);
         if (KQConfigValues.TELEPORT_ON_HIT && (!pSource.m_19387_() || !this.m_21023_(MobEffects.f_19614_))) {
            this.teleport();
         }

         return isDamaged;
      } else {
         return false;
      }
   }

   protected boolean teleport() {
      if (!this.f_19853_.m_5776_() && this.m_6084_()) {
         double d = this.m_20185_() + (this.f_19796_.m_188500_() - 0.5) * 40.0;
         double e = this.m_20186_() + (double)(this.f_19796_.m_188503_(64) - 32);
         double f = this.m_20189_() + (this.f_19796_.m_188500_() - 0.5) * 40.0;
         return this.teleport(d, e, f);
      } else {
         return false;
      }
   }

   private boolean teleport(double x, double y, double z) {
      MutableBlockPos mutableBlockPos = new MutableBlockPos(x, y, z);

      while (mutableBlockPos.m_123342_() > this.f_19853_.m_141937_() && !this.f_19853_.m_8055_(mutableBlockPos).m_60767_().m_76334_()) {
         mutableBlockPos.m_122173_(Direction.DOWN);
      }

      BlockState blockState = this.f_19853_.m_8055_(mutableBlockPos);
      boolean bl = blockState.m_60767_().m_76334_();
      boolean bl2 = blockState.m_60819_().m_205070_(FluidTags.f_13131_);
      if (bl && !bl2) {
         Vec3 vec3 = this.m_20182_();
         boolean bl3 = this.m_20984_(x, y, z, true);
         if (bl3) {
            this.f_19853_.m_214171_(GameEvent.f_238175_, vec3, Context.m_223717_(this));
            this.f_19853_.m_6263_(null, this.f_19854_, this.f_19855_, this.f_19856_, SoundEvents.f_11852_, this.m_5720_(), 1.0F, 1.0F);
            this.m_5496_(SoundEvents.f_11852_, 1.0F, 1.0F);
         }

         return bl3;
      } else {
         return false;
      }
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_215779_;
   }

   protected void m_6153_() {
      this.f_20919_++;
      if (this.f_19853_ instanceof ServerLevel && this.f_20919_ > 0 && this.f_20919_ % 10 == 0) {
         ExperienceOrb.m_147082_((ServerLevel)this.f_19853_, this.m_20182_(), Mth.m_14143_((float)KQConfigValues.EXPERIENCE_DROP_AMOUNT * 0.08F));
      }

      if (this.f_20919_ >= 80 && !this.f_19853_.m_5776_() && !this.m_213877_()) {
         this.f_19853_.m_7605_(this, (byte)60);
         this.m_142687_(RemovalReason.KILLED);
      }
   }

   public void m_7378_(@NotNull CompoundTag pCompound) {
      super.m_7378_(pCompound);
      this.f_19797_ = pCompound.m_128451_("tickCount");
      if (!pCompound.m_128441_("shouldPlaySummonAnimation")) {
         this.setIsSummoning(true);
      } else {
         this.setIsSummoning(pCompound.m_128471_("shouldPlaySummonAnimation"));
      }

      if (!pCompound.m_128441_("shouldSearchTarget")) {
         this.setShouldSearchTarget(false);
      } else {
         this.setShouldSearchTarget(pCompound.m_128471_("shouldSearchTarget"));
      }

      if (!pCompound.m_128441_("isInvulnerable")) {
         this.setInvulnerability(true);
      } else {
         this.setInvulnerability(pCompound.m_128471_("isInvulnerable"));
      }

      if (!pCompound.m_128441_("isNoMovement")) {
         this.setNoMovement(true);
      } else {
         this.setNoMovement(pCompound.m_128471_("isNoMovement"));
      }

      this.setCounterSwitchPhase2(pCompound.m_128451_("counterSwitchPhase2"));
      this.setCounterSwitchPhase3(pCompound.m_128451_("counterSwitchPhase3"));
      if (!pCompound.m_128441_("phase")) {
         this.setPhase(1);
      } else {
         this.setPhase(pCompound.m_128451_("phase"));
      }

      if (this.m_8077_()) {
         this.bossInfo.m_6456_(this.m_5446_());
      }
   }

   public void m_7380_(@NotNull CompoundTag pCompound) {
      super.m_7380_(pCompound);
      pCompound.m_128405_("tickCount", this.f_19797_);
      pCompound.m_128379_("shouldPlaySummonAnimation", this.getIsSummoning());
      pCompound.m_128379_("shouldSearchTarget", this.getShouldSearchTarget());
      pCompound.m_128379_("isInvulnerable", this.getInvulnerability());
      pCompound.m_128379_("isNoMovement", this.getNoMovement());
      pCompound.m_128405_("counterSwitchPhase2", this.getCounterSwitchPhase2());
      pCompound.m_128405_("counterSwitchPhase3", this.getCounterSwitchPhase3());
      pCompound.m_128405_("phase", this.getPhase());
   }

   public void m_6593_(@Nullable Component pName) {
      super.m_6593_(pName);
      this.bossInfo.m_6456_(this.m_5446_());
   }

   public void m_6457_(@NotNull ServerPlayer pPlayer) {
      super.m_6457_(pPlayer);
      this.bossInfo.m_6543_(pPlayer);
   }

   public void m_6452_(@NotNull ServerPlayer pPlayer) {
      super.m_6452_(pPlayer);
      this.bossInfo.m_6539_(pPlayer);
   }

   public void registerControllers(AnimationData animationData) {
      animationData.addAnimationController(new AnimationController(this, "controller", 0.0F, this::movementPredicate));
      animationData.addAnimationController(new AnimationController(this, "attackcontroller", 0.0F, this::attackPredicate));
   }

   private PlayState movementPredicate(AnimationEvent<?> event) {
      if (this.m_21224_()) {
         event.getController().setAnimation(this.DEATH);
      } else if (this.getIsSummoning()) {
         event.getController().setAnimation(this.SUMMONANIM);
      } else if (this.getCounterSwitchPhase3() < 160 && this.getPhase() == 3) {
         event.getController().setAnimation(this.PHASE_SWITCH_3);
      } else if (this.getCounterSwitchPhase2() < 130 && this.getPhase() == 2) {
         event.getController().setAnimation(this.PHASE_SWITCH_2);
      } else if (this.getIsDoingSpecialAttack3()) {
         event.getController().setAnimation(this.SPECIALATTACK3ANIM);
      } else if (this.getIsDoingSpecialAttack2()) {
         event.getController().setAnimation(this.SPECIALATTACK2ANIM);
      } else if (this.getIsDoingFlameAttack()) {
         event.getController().setAnimation(this.SPECIALATTACKANIM);
      } else if (event.isMoving()) {
         event.getController().setAnimation(this.WALKANIM);
      } else {
         event.getController().setAnimation(this.IDLEANIM);
      }

      return PlayState.CONTINUE;
   }

   private PlayState attackPredicate(AnimationEvent<?> event) {
      if (this.f_20911_ && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
         event.getController().markNeedsReload();

         String attackPattern = switch (this.f_19796_.m_188503_(11)) {
            case 3, 4, 5 -> "attack2";
            case 6, 7, 8 -> "attack3";
            default -> "attack";
         };
         event.getController().setAnimation(new AnimationBuilder().playOnce(attackPattern));
         this.f_20911_ = false;
      }

      return PlayState.CONTINUE;
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }
}
