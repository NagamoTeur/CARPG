package com.bobmowzie.mowziesmobs.server.entity.effects;

import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleData;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.client.particle.util.RibbonComponent;
import com.bobmowzie.mowziesmobs.client.particle.util.RibbonParticleData;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.damage.DamageUtil;
import com.bobmowzie.mowziesmobs.server.entity.LeaderSunstrikeImmune;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntitySuperNova extends EntityMagicEffect {
   public static int DURATION = 40;

   public EntitySuperNova(EntityType<? extends EntitySuperNova> type, Level world) {
      super(type, world);
   }

   public EntitySuperNova(EntityType<? extends EntitySuperNova> type, Level world, LivingEntity caster, double x, double y, double z) {
      super(type, world, caster);
      this.m_6034_(x, y, z);
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (this.caster == null || this.caster.m_213877_() || !this.caster.m_6084_()) {
         this.m_146870_();
      }

      if (this.f_19797_ == 1) {
         EntityCameraShake.cameraShake(this.f_19853_, this.m_20182_(), 30.0F, 0.05F, 10, 30);
         this.m_5496_((SoundEvent)MMSounds.ENTITY_SUPERNOVA_END.get(), 3.0F, 1.0F);
         if (this.f_19853_.f_46443_) {
            float scale = 8.2F;

            for (int i = 0; i < 15; i++) {
               float phaseOffset = this.f_19796_.m_188501_();
               AdvancedParticleBase.spawnParticle(
                  this.f_19853_,
                  (ParticleType<AdvancedParticleData>)ParticleHandler.ARROW_HEAD.get(),
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  0.0,
                  0.0,
                  0.0,
                  false,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  8.0,
                  0.95,
                  0.9,
                  0.35,
                  1.0,
                  1.0,
                  30.0,
                  true,
                  true,
                  new ParticleComponent[]{
                     new ParticleComponent.Orbit(
                        new Vec3[]{this.m_20182_().m_82520_(0.0, (double)(this.m_20206_() / 2.0F), 0.0)},
                        ParticleComponent.KeyTrack.startAndEnd(0.0F + phaseOffset, 1.6F + phaseOffset),
                        new ParticleComponent.KeyTrack(
                           new float[]{0.2F * scale, 0.63F * scale, 0.87F * scale, 0.974F * scale, 0.998F * scale, 1.0F * scale},
                           new float[]{0.0F, 0.15F, 0.3F, 0.45F, 0.6F, 0.75F}
                        ),
                        ParticleComponent.KeyTrack.startAndEnd(this.f_19796_.m_188501_() * 2.0F - 1.0F, this.f_19796_.m_188501_() * 2.0F - 1.0F),
                        ParticleComponent.KeyTrack.startAndEnd(this.f_19796_.m_188501_() * 2.0F - 1.0F, this.f_19796_.m_188501_() * 2.0F - 1.0F),
                        ParticleComponent.KeyTrack.startAndEnd(this.f_19796_.m_188501_() * 2.0F - 1.0F, this.f_19796_.m_188501_() * 2.0F - 1.0F),
                        false
                     ),
                     new RibbonComponent(
                        (ParticleType<? extends RibbonParticleData>)ParticleHandler.RIBBON_FLAT.get(),
                        10,
                        0.0,
                        0.0,
                        0.0,
                        0.2F,
                        0.95,
                        0.9,
                        0.35,
                        1.0,
                        true,
                        true,
                        new ParticleComponent[]{
                           new RibbonComponent.PropertyOverLength(
                              RibbonComponent.PropertyOverLength.EnumRibbonProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F)
                           ),
                           new ParticleComponent.PropertyControl(
                              ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F), false
                           )
                        }
                     ),
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F), false
                     ),
                     new ParticleComponent.FaceMotion()
                  }
               );
            }
         }
      }

      if (this.caster != null) {
         float ageFrac = (float)this.f_19797_ / (float)DURATION;
         float scale = (float)Math.pow((double)ageFrac, 0.5) * 5.0F;
         this.m_20011_(this.m_20191_().m_82400_((double)scale));
         this.m_6034_(this.f_19854_, this.f_19855_, this.f_19856_);

         for (LivingEntity entity : this.getEntitiesNearbyCube(LivingEntity.class, (double)scale)) {
            if (this.caster != entity && (!(this.caster instanceof EntityUmvuthi) || !(entity instanceof LeaderSunstrikeImmune)) && this.caster.m_6779_(entity)
               )
             {
               float damageFire = 4.0F;
               float damageMob = 4.0F;
               if (this.caster instanceof EntityUmvuthi) {
                  damageFire = (float)((double)damageFire * (Double)ConfigHandler.COMMON.MOBS.UMVUTHI.combatConfig.attackMultiplier.get());
                  damageMob = (float)((double)damageMob * (Double)ConfigHandler.COMMON.MOBS.UMVUTHI.combatConfig.attackMultiplier.get());
               }

               if (this.caster instanceof Player) {
                  damageFire = (float)(
                     (double)damageFire * (Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.sunsBlessingAttackMultiplier.get() * 0.8
                  );
                  damageMob = (float)(
                     (double)damageMob * (Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.sunsBlessingAttackMultiplier.get() * 0.8
                  );
               }

               boolean hitWithFire = (Boolean)DamageUtil.dealMixedDamage(
                     entity, DamageSource.m_19340_(this, this.caster), damageMob, DamageSource.f_19307_, damageFire
                  )
                  .getRight();
               if (hitWithFire) {
                  Vec3 diff = entity.m_20182_().m_82546_(this.m_20182_());
                  diff = diff.m_82541_();
                  entity.m_147240_(0.4F, -diff.f_82479_, -diff.f_82481_);
                  entity.m_20254_(5);
               }
            }
         }
      }

      if (this.f_19797_ > DURATION) {
         this.m_146870_();
      }
   }

   public float m_213856_() {
      return 1.572888E7F;
   }
}
