package com.bobmowzie.mowziesmobs.server.entity.effects.geomancy;

import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleData;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleRotation;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityCameraShake;
import com.bobmowzie.mowziesmobs.server.potion.EffectGeomancy;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EntityBoulderProjectile extends EntityBoulderBase {
   private final List<Entity> ridingEntities = new ArrayList<>();
   private boolean travelling = false;
   private float speed = 1.5F;
   private int damage = 8;

   public EntityBoulderProjectile(EntityType<? extends EntityBoulderProjectile> type, Level world) {
      super(type, world);
   }

   public EntityBoulderProjectile(
      EntityType<? extends EntityBoulderProjectile> type,
      Level world,
      LivingEntity caster,
      BlockState blockState,
      BlockPos pos,
      EntityGeomancyBase.GeomancyTier tier
   ) {
      super(type, world, caster, blockState, pos, tier);
   }

   @Override
   public void setSizeParams() {
      super.setSizeParams();
      EntityGeomancyBase.GeomancyTier size = this.getTier();
      if (size == EntityGeomancyBase.GeomancyTier.MEDIUM) {
         this.damage = 12;
         this.speed = 1.2F;
      } else if (size == EntityGeomancyBase.GeomancyTier.LARGE) {
         this.damage = 16;
         this.speed = 1.0F;
      } else if (size == EntityGeomancyBase.GeomancyTier.HUGE) {
         this.damage = 20;
         this.speed = 0.65F;
      }

      if (this.caster instanceof Player) {
         this.damage = (int)((double)this.damage * (Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.geomancyAttackMultiplier.get());
      }
   }

   @NotNull
   @Override
   protected AABB m_142242_() {
      AABB boundingBox = super.m_142242_();
      if (!this.travelling) {
         boundingBox = boundingBox.m_82363_(0.0, -0.5, 0.0);
      }

      return boundingBox;
   }

   @Override
   public void m_8119_() {
      if (this.f_19797_ == 1) {
         this.activate();
      }

      super.m_8119_();
      if (this.ridingEntities != null) {
         this.ridingEntities.clear();
      }

      for (Entity entity : this.f_19853_
         .m_45933_(
            this,
            this.m_20191_()
               .m_82310_(0.0, (double)(this.m_20206_() - 1.0F), 0.0)
               .m_82383_(new Vec3(0.0, (double)this.m_20206_() - 0.5, 0.0))
               .m_82377_(0.6, 0.5, 0.6)
         )) {
         if (entity != null && entity.m_6087_() && !(entity instanceof EntityBoulderProjectile) && entity.m_20186_() >= this.m_20186_() + 0.2) {
            this.ridingEntities.add(entity);
         }
      }

      if (this.travelling) {
         for (Entity entityx : this.ridingEntities) {
            entityx.m_6478_(MoverType.SHULKER_BOX, this.m_20184_());
         }
      }

      List<LivingEntity> entitiesHit = this.getEntityLivingBaseNearby(1.7);
      if (this.travelling && !entitiesHit.isEmpty()) {
         for (Entity entityx : entitiesHit) {
            if (!this.f_19853_.f_46443_ && entityx != this.caster && !this.ridingEntities.contains(entityx)) {
               if (this.caster != null) {
                  entityx.m_6469_(DamageSource.m_19340_(this, this.caster), (float)this.damage);
               } else {
                  entityx.m_6469_(DamageSource.f_19322_, (float)this.damage);
               }

               if (this.m_6084_() && this.boulderSize != EntityGeomancyBase.GeomancyTier.HUGE) {
                  this.explode();
               }
            }
         }
      }

      List<EntityBoulderProjectile> bouldersHit = this.f_19853_
         .m_45976_(EntityBoulderProjectile.class, this.m_20191_().m_82377_(0.2, 0.2, 0.2).m_82383_(this.m_20184_().m_82541_().m_82490_(0.5)));
      if (this.travelling && !bouldersHit.isEmpty()) {
         for (EntityBoulderProjectile entityxx : bouldersHit) {
            if (!entityxx.travelling) {
               entityxx.m_7313_(this);
               this.explode();
            }
         }
      }

      if (this.travelling
         && (
            !this.f_19853_.m_6249_(this, this.m_20191_().m_82400_(0.1), e -> !this.ridingEntities.contains(e) && e.m_5829_()).isEmpty()
               || Iterables.size(this.f_19853_.m_186434_(this, this.m_20191_().m_82400_(0.1))) > 0
         )) {
         this.explode();
      }
   }

   @Override
   public boolean m_6087_() {
      return true;
   }

   public boolean m_7313_(Entity entityIn) {
      if (this.risingTick > this.finishedRisingTick - 1 && !this.travelling) {
         if (entityIn instanceof Player && EffectGeomancy.canUse((Player)entityIn)) {
            Player player = (Player)entityIn;
            if (this.ridingEntities.contains(player)) {
               Vec3 lateralLookVec = Vec3.m_82498_(0.0F, player.m_146908_()).m_82541_();
               this.m_20334_((double)this.speed * 0.5 * lateralLookVec.f_82479_, this.m_20184_().f_82480_, (double)this.speed * 0.5 * lateralLookVec.f_82481_);
            } else {
               this.m_20256_(player.m_20154_().m_82490_((double)this.speed * 0.5));
            }

            AbilityHandler.INSTANCE.sendAbilityMessage(player, AbilityHandler.HIT_BOULDER_ABILITY);
         } else {
            if (!(entityIn instanceof EntityBoulderProjectile) || !((EntityBoulderProjectile)entityIn).travelling) {
               return super.m_7313_(entityIn);
            }

            EntityBoulderProjectile boulder = (EntityBoulderProjectile)entityIn;
            Vec3 thisPos = this.m_20182_();
            Vec3 boulderPos = boulder.m_20182_();
            Vec3 velVec = thisPos.m_82546_(boulderPos).m_82541_();
            this.m_20256_(velVec.m_82490_((double)this.speed * 0.5));
         }

         if (!this.travelling) {
            this.setDeathTime(60);
         }

         this.travelling = true;
         this.m_20011_(this.m_6095_().m_20585_(this.m_20185_(), this.m_20186_(), this.m_20189_()));
         if (this.boulderSize == EntityGeomancyBase.GeomancyTier.SMALL) {
            this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_HIT_SMALL.get(), 1.5F, 1.3F);
            this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_SMALL.get(), 1.5F, 0.9F);
         } else if (this.boulderSize == EntityGeomancyBase.GeomancyTier.MEDIUM) {
            this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_HIT_SMALL.get(), 1.5F, 0.9F);
            this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_SMALL.get(), 1.5F, 0.5F);
         } else if (this.boulderSize == EntityGeomancyBase.GeomancyTier.LARGE) {
            this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_HIT_SMALL.get(), 1.5F, 0.5F);
            this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_BIG.get(), 1.5F, 1.3F);
            EntityCameraShake.cameraShake(this.f_19853_, this.m_20182_(), 10.0F, 0.05F, 0, 20);
         } else if (this.boulderSize == EntityGeomancyBase.GeomancyTier.HUGE) {
            this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_HIT_MEDIUM_1.get(), 1.5F, 1.0F);
            this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_BIG.get(), 1.5F, 0.9F);
            EntityCameraShake.cameraShake(this.f_19853_, this.m_20182_(), 15.0F, 0.05F, 0, 20);
         }

         if (this.f_19853_.f_46443_) {
            Vec3 ringOffset = this.m_20184_().m_82490_(-1.0).m_82541_();
            ParticleRotation.OrientVector rotation = new ParticleRotation.OrientVector(ringOffset);
            AdvancedParticleBase.spawnParticle(
               this.f_19853_,
               (ParticleType<AdvancedParticleData>)ParticleHandler.RING2.get(),
               (double)((float)this.m_20185_() + (float)ringOffset.f_82479_),
               (double)((float)this.m_20186_() + 0.5F + (float)ringOffset.f_82480_),
               (double)((float)this.m_20189_() + (float)ringOffset.f_82481_),
               0.0,
               0.0,
               0.0,
               rotation,
               3.5,
               0.83F,
               1.0,
               0.39F,
               1.0,
               1.0,
               (double)((int)(5.0F + 2.0F * this.m_20205_())),
               true,
               true,
               new ParticleComponent[]{
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(0.7F, 0.0F), false
                  ),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                     ParticleComponent.KeyTrack.startAndEnd(0.0F, (1.0F + 0.5F * this.m_20205_()) * 8.0F),
                     false
                  )
               }
            );
         }
      }

      return super.m_7313_(entityIn);
   }

   public boolean isTravelling() {
      return this.travelling;
   }

   public void setTravelling(boolean travel) {
      this.travelling = travel;
   }

   public void setDamage(int dam) {
      this.damage = dam;
   }
}
