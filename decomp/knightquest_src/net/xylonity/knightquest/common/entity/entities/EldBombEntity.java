package net.xylonity.knightquest.common.entity.entities;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SwellGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.gameevent.GameEvent;
import net.xylonity.knightquest.registry.KnightQuestParticles;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.easing.EasingType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class EldBombEntity extends Creeper implements IAnimatable {
   private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
   private int oldSwell;
   private int swell;
   private int maxSwell = 30;
   private int explosionRadius = 3;

   public EldBombEntity(EntityType<? extends Creeper> entityType, Level world) {
      super(entityType, world);
   }

   public static AttributeSupplier setAttributes() {
      return Creeper.m_21552_()
         .m_22268_(Attributes.f_22276_, 7.0)
         .m_22268_(Attributes.f_22281_, 0.5)
         .m_22268_(Attributes.f_22283_, 1.0)
         .m_22268_(Attributes.f_22279_, 0.5)
         .m_22265_();
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new SwellGoal(this));
      this.f_21345_.m_25352_(3, new AvoidEntityGoal(this, PolarBear.class, 35.0F, 1.0, 0.5));
      this.f_21345_.m_25352_(4, new MeleeAttackGoal(this, 0.5, false));
      this.f_21345_.m_25352_(5, new WaterAvoidingRandomStrollGoal(this, 0.5));
      this.f_21345_.m_25352_(7, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
      if (this.swell > 10) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("sneak", EDefaultLoopTypes.LOOP));
      } else if (event.isMoving()) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", EDefaultLoopTypes.LOOP));
      } else {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", EDefaultLoopTypes.LOOP));
      }

      return PlayState.CONTINUE;
   }

   public int getSwell() {
      return this.swell;
   }

   public void m_8119_() {
      if (this.m_6084_()) {
         this.oldSwell = this.swell;
         if (this.m_32311_()) {
            this.m_32283_(1);
         }

         int $$0 = this.m_32310_();
         if ($$0 > 0 && this.swell == 0) {
            this.m_5496_(SoundEvents.f_11837_, 1.0F, 0.5F);
            this.m_146850_(GameEvent.f_157776_);
         }

         this.swell += $$0;
         if (this.swell < 0) {
            this.swell = 0;
         }

         if (this.swell >= this.maxSwell) {
            this.swell = this.maxSwell;
            this.poisonNearbyPlayers();
            this.explode();
         }
      }

      super.m_8119_();
   }

   private void poisonNearbyPlayers() {
      this.f_19853_.m_45976_(Player.class, this.m_20191_().m_82400_(3.5)).forEach(player -> player.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 160, 0)));
   }

   private void explode() {
      if (!this.f_19853_.f_46443_) {
         float power = this.m_7090_() ? 2.5F : 1.0F;
         this.f_20890_ = true;
         this.f_19853_.m_46511_(this, this.m_20185_(), this.m_20186_(), this.m_20189_(), (float)this.explosionRadius * power, BlockInteraction.DESTROY);
         if (this.f_19853_ instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 6; i++) {
               double particleX = this.m_20185_();
               double particleY = this.m_20186_() + 1.0;
               double particleZ = this.m_20189_();
               ClientboundLevelParticlesPacket packet = new ClientboundLevelParticlesPacket(
                  (SimpleParticleType)KnightQuestParticles.POISON_PARTICLE.get(), true, particleX, particleY, particleZ, 1.2F, 1.2F, 1.2F, 0.05F, 1
               );
               serverLevel.m_7654_().m_6846_().m_11241_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), 50.0, serverLevel.m_46472_(), packet);
            }

            float[] arrayX = new float[]{0.5F, -1.0F, 1.0F};
            float[] arrayZ = new float[]{1.0F, 0.0F, -0.5F};

            for (int i = 0; i < 3; i++) {
               double particleX = this.m_20185_() + (double)arrayX[i];
               double particleY = this.m_20186_() - 0.2;
               double particleZ = this.m_20189_() + (double)arrayZ[i];
               ClientboundLevelParticlesPacket packet = new ClientboundLevelParticlesPacket(
                  (SimpleParticleType)KnightQuestParticles.POISON_CLOUD_PARTICLE.get(), true, particleX, particleY, particleZ, 0.0F, 0.15F, 0.0F, 1.0F, 1
               );
               serverLevel.m_7654_().m_6846_().m_11241_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), 50.0, serverLevel.m_46472_(), packet);
            }
         }

         this.m_146870_();
      }
   }

   public void registerControllers(AnimationData animationData) {
      animationData.addAnimationController(new AnimationController(this, "controller", 1.0F, EasingType.Linear, this::predicate));
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }
}
