package com.cerbon.bosses_of_mass_destruction.projectile;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.damagesource.UnshieldableDamageSource;
import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith.RiftBurst;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.particle.ClientParticleBuilder;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDColors;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class SporeBallProjectile extends BaseThrownItemProjectile implements IAnimatable, IAnimationTickable {
   private final List<Vec3> circlePoints = MathUtils.buildBlockCircle(7.0);
   private final ClientParticleBuilder projectileParticles = new ClientParticleBuilder((ParticleOptions)BMDParticles.DISAPPEARING_SWIRL.get())
      .color(BMDColors.GREEN)
      .colorVariation(0.4)
      .scale(0.2F)
      .brightness(15728880);
   private final byte particle = 5;
   private float impactedPitch = 0.0F;
   public float impactedTicks = 0.0F;
   public boolean impacted = false;
   public static final int explosionDelay = 30;

   public SporeBallProjectile(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
      super(entityType, level);
      this.collisionPredicate = hitResult -> true;
   }

   public SporeBallProjectile(LivingEntity livingEntity, Level level, Predicate<EntityHitResult> entityPredicate) {
      super((EntityType<? extends ThrowableItemProjectile>)BMDEntities.SPORE_BALL.get(), livingEntity, level, entityPredicate);
      this.collisionPredicate = hitResult -> true;
   }

   protected void m_8060_(@NotNull BlockHitResult result) {
      this.onImpact();
   }

   @Override
   public void clientTick() {
      super.clientTick();
      if (this.impacted) {
         this.impactedTicks++;
      }
   }

   @NotNull
   public Vec3 m_20184_() {
      return !this.impacted ? super.m_20184_() : Vec3.f_82478_;
   }

   private void onImpact() {
      if (!this.impacted) {
         this.impactedPitch = this.m_146909_();
         this.impacted = true;
         if (this.m_37282_() instanceof LivingEntity livingEntity) {
            this.doExplosion(livingEntity);
         } else if (!this.f_19853_.m_5776_()) {
            this.m_146870_();
         }
      }
   }

   public float m_146909_() {
      return this.impacted ? this.impactedPitch : (float)this.f_19797_ * 5.0F;
   }

   public float m_146908_() {
      return 0.0F;
   }

   private void doExplosion(LivingEntity owner) {
      this.f_19853_.m_7605_(this, (byte)5);
      this.m_5496_((SoundEvent)BMDSounds.SPORE_BALL_LAND.get(), 1.0F, BMDUtils.randomPitch(this.f_19796_) - 0.2F);
      EventScheduler eventScheduler = BMDCapabilities.getLevelEventScheduler(this.f_19853_);
      Consumer<LivingEntity> onImpact = entity -> {
         float damage = (float)owner.m_21133_(Attributes.f_22281_);
         if (this.m_37282_() != null) {
            entity.m_6469_(new UnshieldableDamageSource(this.m_37282_()), damage);
            entity.m_147207_(new MobEffectInstance(MobEffects.f_19614_, 140), this.m_37282_());
         }
      };
      if (!this.f_19853_.f_46443_) {
         RiftBurst riftBurst = new RiftBurst(
            owner,
            (ServerLevel)this.f_19853_,
            (ParticleOptions)BMDParticles.SPORE_INDICATOR.get(),
            (ParticleOptions)BMDParticles.SPORE.get(),
            30,
            eventScheduler,
            onImpact,
            this::isOpenBlock,
            this::posFinder
         );
         eventScheduler.addEvent(new TimedEvent(() -> {
            this.m_5496_((SoundEvent)BMDSounds.SPORE_IMPACT.get(), 1.5F, BMDUtils.randomPitch(this.f_19796_));
            this.m_146870_();
         }, 30));
         Vec3 center = VecUtils.asVec3(this.m_20183_()).m_82549_(VecUtils.unit.m_82490_(0.5));

         for (Vec3 point : this.circlePoints) {
            riftBurst.tryPlaceRift(center.m_82549_(point));
         }
      }
   }

   private BlockPos posFinder(Vec3 pos) {
      BlockPos above = new BlockPos(pos.m_82549_(VecUtils.yAxis.m_82490_(2.0)));
      BlockPos groundPos = BMDUtils.findGroundBelow(this.f_19853_, above, pos1 -> true);
      BlockPos up = groundPos.m_7494_();
      return up.m_123342_() + 8 >= above.m_123342_() && this.isOpenBlock(up) ? up : null;
   }

   private boolean isOpenBlock(BlockPos up) {
      BlockState blockState = this.f_19853_.m_8055_(up);
      return blockState.m_60629_(new DirectionalPlaceContext(this.f_19853_, up, Direction.DOWN, ItemStack.f_41583_, Direction.UP))
         || blockState.m_60734_() == Blocks.f_152543_;
   }

   public void m_7822_(byte id) {
      if (id == 5) {
         for (Vec3 point : MathUtils.circlePoints(0.8, 16, VecUtils.yAxis)) {
            this.projectileParticles.build(point.m_82549_(this.m_20182_()), point.m_82490_(0.1));
         }
      }

      super.m_7822_(id);
   }

   @Override
   public void entityHit(EntityHitResult entityHitResult) {
      if (!this.f_19853_.m_5776_()) {
         Entity owner = this.m_37282_();
         Entity entity = entityHitResult.m_82443_();
         if (owner instanceof LivingEntity livingEntity && entity != livingEntity) {
            float damage = (float)livingEntity.m_21133_(Attributes.f_22281_);
            entity.m_6469_(DamageSource.m_19361_(this, livingEntity), damage);
         }
      }
   }

   public void registerControllers(AnimationData animationData) {
   }

   public AnimationFactory getFactory() {
      return new AnimationFactory(this);
   }

   public int tickTimer() {
      return 0;
   }
}
