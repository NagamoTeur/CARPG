package net.xylonity.knightquest.common.entity.boss;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.xylonity.knightquest.common.ai.navigator.GroundNavigator;
import net.xylonity.knightquest.common.item.KQFullSetChecker;
import net.xylonity.knightquest.common.material.KQArmorMaterials;
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

public class NethermanCloneEntity extends Monster implements IAnimatable {
   private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public NethermanCloneEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   @NotNull
   protected PathNavigation m_6037_(@NotNull Level pLevel) {
      return new GroundNavigator(this, pLevel);
   }

   public static Builder setAttributes() {
      return Monster.m_21552_()
         .m_22268_(Attributes.f_22276_, 0.1F)
         .m_22268_(Attributes.f_22281_, 8.0)
         .m_22268_(Attributes.f_22283_, 1.0)
         .m_22268_(Attributes.f_22279_, 1.0)
         .m_22268_(Attributes.f_22277_, 100.0)
         .m_22268_(Attributes.f_22278_, 5.0);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new MeleeAttackGoal(this, 0.4F, false));
      this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public void m_6667_(@NotNull DamageSource pDamageSource) {
      super.m_6667_(pDamageSource);

      for (Player player : this.f_19853_.m_6907_()) {
         if (player instanceof ServerPlayer serverPlayer) {
            for (int u = 0; u < 30; u++) {
               double speed = 0.5 + this.m_217043_().m_188500_() * 0.2;
               double x = this.m_20185_() + (this.m_217043_().m_188500_() - 0.5) * 0.2;
               double y = this.m_20186_() + (double)this.m_20192_() + (this.m_217043_().m_188500_() - 0.5) * 0.2;
               double z = this.m_20189_() + (this.m_217043_().m_188500_() - 0.5) * 0.2;
               Vec3 look = this.m_20154_();
               double vx = look.f_82479_ * speed;
               double vy = look.f_82480_ * speed;
               double vz = look.f_82481_ * speed;
               serverPlayer.f_8906_
                  .m_9829_(new ClientboundLevelParticlesPacket(ParticleTypes.f_175821_, true, x, y, z, (float)vx, (float)vy, (float)vz, 0.2F, 2));
            }
         }

         if (this.m_20270_(player) <= 3.0F && !KQFullSetChecker.hasFullSetOn(player, KQArmorMaterials.POLAR)) {
            player.m_146917_(player.m_146888_() + KQConfigValues.CLONE_EXPLOSION_FREEZE_TICKS);
         }
      }
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_144242_;
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_) {
         for (Player player : this.f_19853_.m_6907_()) {
            if ((double)this.m_20270_(player) <= 2.0) {
               this.m_6074_();
               break;
            }
         }
      }
   }

   protected void m_6153_() {
      this.f_20919_++;
      if (this.f_20919_ >= 1 && !this.f_19853_.m_5776_() && !this.m_213877_()) {
         this.f_19853_.m_7605_(this, (byte)60);
         this.m_142687_(RemovalReason.KILLED);
      }
   }

   public void registerControllers(AnimationData animationData) {
      animationData.addAnimationController(new AnimationController(this, "coreController", 0.0F, this::predicate));
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
}
