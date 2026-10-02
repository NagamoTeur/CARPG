package net.xylonity.knightquest.common.entity.entities;

import dev.xylonity.knightlib.compat.registry.KnightLibParticles;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.xylonity.knightquest.config.values.KQConfigValues;
import net.xylonity.knightquest.registry.KnightQuestParticles;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class GhostyEntity extends Monster implements IAnimatable {
   private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
   private final Level serverWorld;
   private static final double ANGULAR_SPEED = 0.1;
   private double angle;
   private double baseY;
   Player target;

   public GhostyEntity(EntityType<? extends Monster> entityType, Level world) {
      super(entityType, world);
      this.serverWorld = world;
   }

   public static AttributeSupplier setAttributes() {
      return Monster.m_21552_()
         .m_22268_(Attributes.f_22276_, 1.0)
         .m_22268_(Attributes.f_22281_, 0.0)
         .m_22268_(Attributes.f_22283_, 0.0)
         .m_22268_(Attributes.f_22279_, 0.63F)
         .m_22268_(Attributes.f_22277_, 35.0)
         .m_22265_();
   }

   public void registerControllers(AnimationData animationData) {
      animationData.addAnimationController(new AnimationController(this, "controller", 0.0F, this::predicate));
   }

   private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
      event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", EDefaultLoopTypes.LOOP));
      return PlayState.CONTINUE;
   }

   public boolean m_6469_(DamageSource pSource, float pAmount) {
      this.serverWorld.m_7106_(ParticleTypes.f_123813_, this.m_20185_(), this.m_20186_() + 0.5, this.m_20189_(), 1.2, 0.0, 0.0);
      this.serverWorld.m_5594_(null, this.m_20183_(), SoundEvents.f_11949_, SoundSource.HOSTILE, 1.0F, 1.0F);
      this.m_142687_(RemovalReason.KILLED);
      return false;
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.m_21527_()) {
         this.m_20254_(2);
      }

      if (this.f_19797_ % 15 == 0) {
         for (Player player : this.serverWorld.m_45976_(Player.class, this.m_20191_().m_82400_(1.0))) {
            if (!player.m_7500_()) {
               player.m_6469_(DamageSource.f_19318_, 1.0F);
               Vec3 direction = player.m_20182_().m_82546_(this.m_20182_()).m_82541_().m_82490_(0.1);
               player.m_5997_(direction.f_82479_, direction.f_82480_ + 0.5, direction.f_82481_);
            }
         }
      }

      for (Monster monster : this.serverWorld.m_45976_(Monster.class, this.m_20191_().m_82400_((double)KQConfigValues.INVULNERABILITY_RADIUS_GHOSTY))) {
         monster.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 1, 4, false, false, false));
         if (!(monster instanceof GhostyEntity)) {
            for (int i = 0; i < 4 && this.f_19797_ % 25 == 0; i++) {
               this.serverWorld
                  .m_7106_(
                     (ParticleOptions)KnightLibParticles.STARSET_PARTICLE.get(),
                     monster.m_20185_(),
                     monster.m_20186_() - 0.48,
                     monster.m_20189_(),
                     1.2,
                     0.0,
                     0.0
                  );
            }
         }
      }

      if (this.baseY == 0.0 && this.m_20186_() != 0.0) {
         this.baseY = this.m_20186_();
      }

      this.target = this.serverWorld.m_45930_(this, 20.0);
      double offsetY = Math.sin(this.angle * 2.0) * 0.1;
      double currentX = this.m_20185_();
      double currentY = this.m_20186_();
      double currentZ = this.m_20189_();
      if (this.target != null && !this.target.m_7500_()) {
         this.m_21391_(this.target, 360.0F, 360.0F);
         double speed = 0.1;
         double dx = this.target.m_20185_() - currentX;
         double dy = this.target.m_20186_() + 0.5 - currentY;
         double dz = this.target.m_20189_() - currentZ;
         double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
         if (distance > 1.0) {
            double motionX = dx / distance * speed;
            double motionY = dy / distance * speed;
            double motionZ = dz / distance * speed;
            currentX += motionX;
            currentY += motionY + offsetY;
            currentZ += motionZ;
         } else {
            currentY += offsetY;
         }
      } else {
         currentY += offsetY;
      }

      this.m_6034_(currentX, currentY, currentZ);
      if (this.serverWorld.m_45930_(this, -1.0) != null) {
         this.m_21391_(Objects.requireNonNull(this.serverWorld.m_45930_(this, -1.0)), 360.0F, 360.0F);
      }

      this.angle += 0.1;
      if (this.angle >= Math.PI * 2) {
         this.angle -= Math.PI * 2;
      }

      ParticleOptions particleType = (ParticleOptions)KnightQuestParticles.GHOSTY_PARTICLE.get();

      for (int i = 0; i < 1 && this.f_19797_ % 15 == 0; i++) {
         double d0 = this.f_19796_.m_188583_() * 0.02;
         double d1 = this.f_19796_.m_188583_() * 0.02;
         double d2 = this.f_19796_.m_188583_() * 0.02;
         this.serverWorld
            .m_7106_(
               particleType,
               this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 1.0F) - (double)this.m_20205_(),
               this.m_20186_() + (double)(this.f_19796_.m_188501_() * this.m_20206_()),
               this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 1.0F) - (double)this.m_20205_(),
               d0,
               d1,
               d2
            );
      }
   }

   public boolean m_21255_() {
      return super.m_21255_();
   }

   public boolean m_5843_() {
      return false;
   }

   protected void m_21218_() {
   }

   public boolean m_142535_(float pFallDistance, float pMultiplier, DamageSource pSource) {
      return false;
   }

   public boolean m_20068_() {
      return true;
   }

   public boolean m_5829_() {
      return false;
   }

   protected void m_6138_() {
   }

   public void m_7334_(Entity pEntity) {
   }

   public void m_20221_(BlockPos pPos) {
   }

   public boolean m_6060_() {
      return false;
   }

   public boolean m_20077_() {
      return false;
   }

   public boolean m_20069_() {
      return false;
   }

   public boolean m_5830_() {
      return false;
   }

   protected void m_20076_() {
   }

   protected void m_21184_() {
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }

   protected SoundEvent m_5501_() {
      return null;
   }

   protected SoundEvent m_5592_() {
      return null;
   }

   public void m_216990_(SoundEvent pSound) {
   }

   public void m_5496_(SoundEvent pSound, float pVolume, float pPitch) {
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return null;
   }

   protected void m_6677_(DamageSource pSource) {
   }

   protected SoundEvent m_7975_(DamageSource pDamageSource) {
      return null;
   }

   protected void m_7355_(BlockPos pPos, BlockState pState) {
   }
}
