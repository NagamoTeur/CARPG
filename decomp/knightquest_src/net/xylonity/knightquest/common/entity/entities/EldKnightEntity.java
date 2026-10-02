package net.xylonity.knightquest.common.entity.entities;

import dev.xylonity.knightlib.compat.registry.KnightLibParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.xylonity.knightquest.config.values.KQConfigValues;
import net.xylonity.knightquest.registry.KnightQuestEntities;
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

public class EldKnightEntity extends Monster implements IAnimatable {
   private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
   private final Level serverWorld;
   private boolean summoned = false;
   private int counter;

   public EldKnightEntity(EntityType<? extends Monster> entityType, Level world) {
      super(entityType, world);
      this.serverWorld = world;
   }

   public static AttributeSupplier setAttributes() {
      return Monster.m_21552_()
         .m_22268_(Attributes.f_22276_, 90.0)
         .m_22268_(Attributes.f_22281_, 12.0)
         .m_22268_(Attributes.f_22283_, 0.4F)
         .m_22268_(Attributes.f_22279_, 0.55F)
         .m_22268_(Attributes.f_22278_, 0.8F)
         .m_22265_();
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new MeleeAttackGoal(this, 0.45, false));
      this.f_21345_.m_25352_(2, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public void registerControllers(AnimationData animationData) {
      animationData.addAnimationController(new AnimationController(this, "controller", 0.0F, this::predicate));
      animationData.addAnimationController(new AnimationController(this, "attackcontroller", 0.0F, this::attackPredicate));
   }

   private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
      if (this.f_20911_ && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
         event.getController().markNeedsReload();
         event.getController().setAnimation(new AnimationBuilder().addAnimation("attack", EDefaultLoopTypes.PLAY_ONCE));
         this.f_20911_ = false;
      }

      return PlayState.CONTINUE;
   }

   private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
      if (event.isMoving()) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", EDefaultLoopTypes.LOOP));
      } else {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", EDefaultLoopTypes.LOOP));
      }

      return PlayState.CONTINUE;
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }

   protected SoundEvent m_5501_() {
      return SoundEvents.f_144067_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12059_;
   }

   protected SoundEvent m_7975_(DamageSource pDamageSource) {
      return SoundEvents.f_12008_;
   }

   protected void m_7355_(BlockPos pPos, BlockState pState) {
      this.m_5496_(SoundEvents.f_12010_, 0.15F, 1.0F);
   }

   public void m_8119_() {
      super.m_8119_();
      this.counter++;
      if (!this.summoned && (double)this.m_21223_() < (double)this.m_21233_() * 0.5) {
         this.summoned = true;
         this.summonMinions();
      }

      if (this.summoned && this.counter > 80) {
         if (KQConfigValues.POISON_ELDKNIGHT) {
            this.summonParticle();
            this.poisonNearbyPlayers();
         }

         if ((double)this.m_21223_() < (double)this.m_21233_() * 0.75) {
            this.m_5634_(KQConfigValues.HEAL_ELDKNIGHT);
         }

         this.counter = 0;
      }
   }

   public boolean m_6469_(DamageSource pSource, float pAmount) {
      if (pSource.m_19372_()) {
         return super.m_6469_(pSource, pAmount * 0.15F);
      } else {
         return pSource.m_19360_() ? super.m_6469_(pSource, pAmount * 0.1F) : super.m_6469_(pSource, pAmount);
      }
   }

   private void summonMinions() {
      double distance = 3.0;
      double angle = KQConfigValues.HEAL_ELDKNIGHT != 0.0F ? Math.toRadians(360.0 / (double)KQConfigValues.NUM_ELDBOMB_ELDKNIGHT) : Math.toRadians(120.0);
      boolean punch = false;

      for (int i = 0; i < KQConfigValues.NUM_ELDBOMB_ELDKNIGHT; i++) {
         double xOffset = distance * Math.cos(angle * (double)i);
         double zOffset = distance * Math.sin(angle * (double)i);
         EldBombEntity entity = (EldBombEntity)((EntityType)KnightQuestEntities.ELDBOMB.get()).m_20615_(this.serverWorld);
         if (entity != null) {
            BlockPos spawnPos = this.m_20183_().m_7918_((int)xOffset, 0, (int)zOffset);
            if (!this.serverWorld.m_8055_(spawnPos).m_60795_()) {
               for (int d = 1; (double)d <= distance; d++) {
                  BlockPos adjustedPos = this.m_20183_().m_7918_((int)(xOffset / (double)d), 0, (int)(zOffset / (double)d));
                  if (this.serverWorld.m_8055_(adjustedPos).m_60795_()) {
                     spawnPos = adjustedPos;
                     break;
                  }
               }
            }

            entity.m_20035_(spawnPos, 0.0F, 0.0F);
            this.serverWorld.m_7967_(entity);

            for (int j = 0; j < 20; j++) {
               this.serverWorld
                  .m_7106_(
                     ParticleTypes.f_123806_,
                     (double)spawnPos.m_123341_() + 0.5 + this.serverWorld.f_46441_.m_188500_() - 0.5,
                     (double)spawnPos.m_123342_() + 0.5 + this.serverWorld.f_46441_.m_188500_() - 0.5,
                     (double)spawnPos.m_123343_() + 0.5 + this.serverWorld.f_46441_.m_188500_() - 0.5,
                     0.0,
                     0.0,
                     0.0
                  );
            }

            if (!punch) {
               this.serverWorld.m_45976_(Player.class, entity.m_20191_().m_82400_(5.0)).forEach(player -> {
                  Vec3 direction = player.m_20182_().m_82546_(this.m_20182_()).m_82541_().m_82490_(1.5);
                  player.m_5997_(direction.f_82479_, direction.f_82480_ + 0.5, direction.f_82481_);
               });
               punch = true;
            }
         }
      }

      int particleCount = 120;
      double particleRadius = 4.0;

      for (int ix = 0; ix < particleCount; ix++) {
         double angleOffset = (Math.PI * 2) / (double)particleCount * (double)ix;
         double xParticleOffset = particleRadius * Math.cos(angleOffset);
         double zParticleOffset = particleRadius * Math.sin(angleOffset);
         this.serverWorld
            .m_7106_(ParticleTypes.f_123796_, this.m_20185_() + xParticleOffset, this.m_20186_() - 0.1, this.m_20189_() + zParticleOffset, 0.0, 0.05, 0.0);
      }

      this.serverWorld.m_5594_(null, this.m_20183_(), SoundEvents.f_11868_, SoundSource.BLOCKS, 1.0F, 1.0F);
   }

   private void poisonNearbyPlayers() {
      this.serverWorld
         .m_45976_(Player.class, this.m_20191_().m_82400_(3.5))
         .forEach(player -> player.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 200, 1)));
   }

   private void summonParticle() {
      this.serverWorld
         .m_7106_((ParticleOptions)KnightLibParticles.STARSET_PARTICLE.get(), this.m_20185_(), this.m_20186_() - 0.48, this.m_20189_(), 4.0, 0.0, 0.0);
   }
}
