package io.redspace.ironsspellbooks.entity.mobs.keeper;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.mobs.IAnimatedAttacker;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.entity.mobs.goals.AttackAnimationData;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class KeeperEntity extends AbstractSpellCastingMob implements Enemy, IAnimatedAttacker {
   AnimationBuilder animationToPlay = null;
   private final AnimationController<KeeperEntity> meleeController = new AnimationController(this, "keeper_animations", 0.0F, this::predicate);

   public KeeperEntity(EntityType<? extends AbstractSpellCastingMob> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.f_21364_ = 25;
      this.f_19793_ = 1.0F;
      this.f_21365_ = this.createLookControl();
      this.f_21342_ = this.createMoveControl();
   }

   protected float m_6431_(Pose pPose, EntityDimensions pDimensions) {
      return pDimensions.f_20378_;
   }

   public KeeperEntity(Level pLevel) {
      this((EntityType<? extends AbstractSpellCastingMob>)EntityRegistry.KEEPER.get(), pLevel);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(4, new KeeperAnimatedWarlockAttackGoal(this, 1.0, 10, 30, 3.5F));
      this.f_21345_.m_25352_(4, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, Mob.class, true, entity -> !(entity instanceof KeeperEntity)));
   }

   protected BodyRotationControl m_7560_() {
      return new BodyRotationControl(this);
   }

   @Override
   protected LookControl createLookControl() {
      return new LookControl(this) {
         protected float m_24956_(float pFrom, float pTo, float pMaxDelta) {
            return super.m_24956_(pFrom, pTo, pMaxDelta * 2.5F);
         }
      };
   }

   protected MoveControl createMoveControl() {
      return new MoveControl(this) {
         protected float m_24991_(float pSourceAngle, float pTargetAngle, float pMaximumChange) {
            double d0 = this.f_24975_ - this.f_24974_.m_20185_();
            double d1 = this.f_24977_ - this.f_24974_.m_20189_();
            return d0 * d0 + d1 * d1 < 0.5 ? pSourceAngle : super.m_24991_(pSourceAngle, pTargetAngle, pMaximumChange * 0.25F);
         }
      };
   }

   @NotNull
   protected SoundEvent m_7515_() {
      return (SoundEvent)SoundRegistry.KEEPER_IDLE.get();
   }

   public void m_8032_() {
      this.m_5496_(this.m_7515_(), 1.0F, (float)Mth.m_216287_(this.m_217043_(), 5, 10) * 0.1F);
   }

   protected SoundEvent m_7975_(DamageSource pDamageSource) {
      return (SoundEvent)SoundRegistry.KEEPER_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)SoundRegistry.KEEPER_DEATH.get();
   }

   public SpawnGroupData m_6518_(
      ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag
   ) {
      RandomSource randomsource = Utils.random;
      this.m_213945_(randomsource, pDifficulty);
      return pSpawnData;
   }

   protected void m_213945_(RandomSource pRandom, DifficultyInstance pDifficulty) {
      this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)ItemRegistry.KEEPER_FLAMBERGE.get()));
   }

   public static Builder prepareAttributes() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22281_, 10.0)
         .m_22268_(Attributes.f_22276_, 60.0)
         .m_22268_(Attributes.f_22277_, 25.0)
         .m_22268_(Attributes.f_22278_, 0.8)
         .m_22268_(Attributes.f_22282_, 2.0)
         .m_22268_(Attributes.f_22279_, 0.19);
   }

   public boolean m_5825_() {
      return true;
   }

   public boolean m_6469_(DamageSource pSource, float pAmount) {
      if (pSource.m_7640_() instanceof Projectile projectile) {
         pAmount *= 0.75F;
      }

      return super.m_6469_(pSource, pAmount);
   }

   protected void m_7355_(BlockPos pPos, BlockState pState) {
      super.m_7355_(pPos, pState);
      if (!pState.m_60767_().m_76332_()) {
         this.m_5496_((SoundEvent)SoundRegistry.KEEPER_STEP.get(), 0.25F, 1.0F);
      }
   }

   protected float m_6059_() {
      return this.f_19788_ + 0.8F;
   }

   public boolean m_6673_(DamageSource pSource) {
      return super.m_6673_(pSource) || pSource.m_146707_();
   }

   @Override
   public void playAnimation(String animationId) {
      try {
         KeeperEntity.AttackType attackType = KeeperEntity.AttackType.valueOf(animationId);
         this.animationToPlay = new AnimationBuilder().addAnimation(attackType.data.animationId, EDefaultLoopTypes.PLAY_ONCE);
      } catch (Exception var3) {
         IronsSpellbooks.LOGGER.error("Entity {} Failed to play animation: {}", this, animationId);
      }
   }

   private PlayState predicate(AnimationEvent<KeeperEntity> animationEvent) {
      AnimationController<KeeperEntity> controller = animationEvent.getController();
      if (this.animationToPlay != null) {
         controller.markNeedsReload();
         controller.setAnimation(this.animationToPlay);
         this.animationToPlay = null;
      }

      return PlayState.CONTINUE;
   }

   @Override
   public void registerControllers(AnimationData data) {
      data.addAnimationController(this.meleeController);
      super.registerControllers(data);
   }

   @Override
   public boolean isAnimating() {
      return this.meleeController.getAnimationState() != AnimationState.Stopped || super.isAnimating();
   }

   @Override
   public boolean shouldAlwaysAnimateLegs() {
      return false;
   }

   public static enum AttackType {
      Double_Slash(43, "sword_double_slash", 13, 29),
      Single_Upward(26, "sword_single_upward", 13),
      Single_Horizontal(28, "sword_single_horizontal", 12),
      Single_Horizontal_Fast(24, "sword_single_horizontal_fast", 12),
      Single_Stab(21, "sword_stab", 11),
      Lunge(76, "sword_lunge", 56, 57, 58, 59, 60, 61, 62, 63, 64);

      public final AttackAnimationData data;

      private AttackType(int lengthInTicks, String animationId, int... attackTimestamps) {
         this.data = new AttackAnimationData(lengthInTicks, animationId, attackTimestamps);
      }
   }
}
