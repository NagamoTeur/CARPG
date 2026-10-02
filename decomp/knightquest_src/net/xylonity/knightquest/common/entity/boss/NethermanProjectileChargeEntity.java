package net.xylonity.knightquest.common.entity.boss;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.phys.BlockHitResult;
import net.xylonity.knightquest.config.values.KQConfigValues;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class NethermanProjectileChargeEntity extends AbstractNethermanProjectile implements IAnimatable {
   private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
   private int explosionTimer;
   private boolean shouldGoDown;

   public NethermanProjectileChargeEntity(EntityType<? extends AbstractNethermanProjectile> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.m_20242_(true);
      this.explosionTimer = this.f_19796_.m_188503_(100) + 20;
      if (!this.f_19853_.f_46443_) {
         this.shouldGoDown = this.f_19796_.m_188499_();
      }
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_) {
         this.explosionTimer--;
         if (this.explosionTimer <= 0) {
            this.explode();
         }
      }

      if (this.shouldGoDown && !this.f_19853_.f_46443_) {
         this.m_20256_(this.m_20184_().m_82520_(0.0, -0.05, 0.0));
      }
   }

   protected void m_8060_(@NotNull BlockHitResult pResult) {
      super.m_8060_(pResult);
      if (!this.f_19853_.f_46443_) {
         this.explode();
      }
   }

   private void explode() {
      this.f_19853_
         .m_46511_(this, this.m_20185_(), this.m_20186_(), this.m_20189_(), (float)KQConfigValues.NETHERMAN_PROJECTILE_EXPLOSION_RADIUS, BlockInteraction.NONE);
      this.m_146870_();
   }

   public void registerControllers(AnimationData controllers) {
      controllers.addAnimationController(new AnimationController(this, "rotateController", 0.0F, this::rotatePredicate));
      controllers.addAnimationController(new AnimationController(this, "coreController", 0.0F, this::corePredicate));
   }

   private PlayState corePredicate(AnimationEvent<?> event) {
      if (this.f_19797_ < 10) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("summon", EDefaultLoopTypes.PLAY_ONCE));
      } else if (this.explosionTimer < 11) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("die", EDefaultLoopTypes.PLAY_ONCE));
      }

      return PlayState.CONTINUE;
   }

   private PlayState rotatePredicate(AnimationEvent<?> event) {
      event.getController().setAnimation(new AnimationBuilder().addAnimation("rotate", EDefaultLoopTypes.LOOP));
      return PlayState.CONTINUE;
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }
}
