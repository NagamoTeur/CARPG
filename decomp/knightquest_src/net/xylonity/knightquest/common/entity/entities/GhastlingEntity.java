package net.xylonity.knightquest.common.entity.entities;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
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
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class GhastlingEntity extends Monster implements IAnimatable {
   private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
   private final Level serverWorld;
   private static final double RADIUS = 2.0;
   private static final double ANGULAR_SPEED = 0.1;
   private double angle;

   public GhastlingEntity(EntityType<? extends Monster> entityType, Level world) {
      super(entityType, world);
      this.serverWorld = world;
   }

   public static AttributeSupplier setAttributes() {
      return Monster.m_21552_()
         .m_22268_(Attributes.f_22276_, 1.0)
         .m_22268_(Attributes.f_22281_, 5.0)
         .m_22268_(Attributes.f_22283_, 1.0)
         .m_22268_(Attributes.f_22279_, 0.63F)
         .m_22268_(Attributes.f_22277_, 35.0)
         .m_22265_();
   }

   public void registerControllers(AnimationData animationData) {
      animationData.addAnimationController(new AnimationController(this, "controller", 0.0F, this::predicate));
   }

   private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
      return PlayState.CONTINUE;
   }

   public boolean m_6469_(DamageSource pSource, float pAmount) {
      this.serverWorld.m_7106_(ParticleTypes.f_123813_, this.m_20185_(), this.m_20186_() + 0.5, this.m_20189_(), 1.2, 0.0, 0.0);
      this.m_9236_().m_5594_(null, this.m_20183_(), SoundEvents.f_12502_, SoundSource.HOSTILE, 1.0F, 1.0F);
      this.serverWorld.m_45976_(Player.class, this.m_20191_().m_82400_(3.0)).forEach(player -> {
         Vec3 direction = player.m_20182_().m_82546_(this.m_20182_()).m_82541_().m_82490_(0.4);
         player.m_5997_(direction.f_82479_, direction.f_82480_ + 0.5, direction.f_82481_);
      });
      this.m_142687_(RemovalReason.KILLED);
      return false;
   }

   protected InteractionResult m_6071_(Player pPlayer, InteractionHand pHand) {
      return super.m_6071_(pPlayer, pHand);
   }

   public boolean m_5829_() {
      return false;
   }

   public void m_8119_() {
      super.m_8119_();
      GremlinEntity owner = this.findClosestGremlin();
      if (owner != null && !owner.m_21224_() && owner.getPhase() != 1) {
         Vec3 ownerPos = owner.m_20182_();
         double offsetX = Math.cos(this.angle) * 2.0;
         double offsetZ = Math.sin(this.angle) * 2.0;
         double offsetY = Math.sin(this.angle * 2.0) * 0.5;
         this.m_6034_(ownerPos.m_7096_() + offsetX, ownerPos.m_7098_() + 0.5 + offsetY, ownerPos.m_7094_() + offsetZ);
         if (this.m_9236_().m_45930_(this, -1.0) != null) {
            this.m_21391_(Objects.requireNonNull(this.m_9236_().m_45930_(this, -1.0)), 360.0F, 360.0F);
         }

         this.angle += 0.1;
         if (this.angle >= Math.PI * 2) {
            this.angle -= Math.PI * 2;
         }

         ParticleOptions particleType = ParticleTypes.f_123762_;

         for (int i = 0; i < 2; i++) {
            double d0 = this.f_19796_.m_188583_() * 0.02;
            double d1 = this.f_19796_.m_188583_() * 0.02;
            double d2 = this.f_19796_.m_188583_() * 0.02;
            this.m_9236_()
               .m_7106_(
                  particleType,
                  this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(),
                  this.m_20186_() + (double)(this.f_19796_.m_188501_() * this.m_20206_()),
                  this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(),
                  d0,
                  d1,
                  d2
               );
         }
      } else {
         this.m_142687_(RemovalReason.KILLED);
      }
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

   private GremlinEntity findClosestGremlin() {
      double closestDistance = Double.MAX_VALUE;
      GremlinEntity closestGremlin = null;

      for (GremlinEntity entity : this.m_9236_().m_45976_(GremlinEntity.class, this.m_20191_().m_82400_(4.0))) {
         if (entity instanceof GremlinEntity) {
            double distance = this.m_20280_(entity);
            if (distance < closestDistance) {
               closestDistance = distance;
               closestGremlin = entity;
            }
         }
      }

      return closestGremlin;
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

   public boolean m_20068_() {
      return true;
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
