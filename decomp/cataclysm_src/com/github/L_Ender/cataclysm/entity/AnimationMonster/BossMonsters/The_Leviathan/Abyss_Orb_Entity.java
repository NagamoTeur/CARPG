package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan;

import com.github.L_Ender.cataclysm.client.particle.LightTrailParticle;
import com.github.L_Ender.cataclysm.client.particle.LightningParticle;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.event.ForgeEventFactory;

public class Abyss_Orb_Entity extends Projectile {
   public double xPower;
   public double yPower;
   public double zPower;
   @Nullable
   private Entity finalTarget;
   @Nullable
   private UUID targetId;
   private static final EntityDataAccessor<Boolean> TRACKING = SynchedEntityData.m_135353_(Abyss_Orb_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.m_135353_(Abyss_Orb_Entity.class, EntityDataSerializers.f_135029_);
   private int timer;
   private int lifetick;

   public Abyss_Orb_Entity(EntityType<? extends Abyss_Orb_Entity> p_36833_, Level p_36834_) {
      super(p_36833_, p_36834_);
   }

   public Abyss_Orb_Entity(Level worldIn, LivingEntity entity) {
      this((EntityType<? extends Abyss_Orb_Entity>)ModEntities.ABYSS_ORB.get(), worldIn);
      this.m_5602_(entity);
   }

   public Abyss_Orb_Entity(
      EntityType<? extends Abyss_Orb_Entity> p_36817_,
      double p_36818_,
      double p_36819_,
      double p_36820_,
      double p_36821_,
      double p_36822_,
      double p_36823_,
      Level p_36824_
   ) {
      this(p_36817_, p_36824_);
      this.m_7678_(p_36818_, p_36819_, p_36820_, this.m_146908_(), this.m_146909_());
      this.m_20090_();
      double d0 = Math.sqrt(p_36821_ * p_36821_ + p_36822_ * p_36822_ + p_36823_ * p_36823_);
      if (d0 != 0.0) {
         this.xPower = p_36821_ / d0 * 0.1;
         this.yPower = p_36822_ / d0 * 0.1;
         this.zPower = p_36823_ / d0 * 0.1;
      }
   }

   public Abyss_Orb_Entity(LivingEntity p_36827_, double p_36828_, double p_36829_, double p_36830_, Level p_36831_, float damage, LivingEntity finalTarget) {
      this(
         (EntityType<? extends Abyss_Orb_Entity>)ModEntities.ABYSS_ORB.get(),
         p_36827_.m_20185_(),
         p_36827_.m_20186_(),
         p_36827_.m_20189_(),
         p_36828_,
         p_36829_,
         p_36830_,
         p_36831_
      );
      this.m_5602_(p_36827_);
      this.setDamage(damage);
      this.finalTarget = finalTarget;
      this.m_19915_(p_36827_.m_146908_(), p_36827_.m_146909_());
   }

   public Abyss_Orb_Entity(Level worldIn, LivingEntity entity, LivingEntity finalTarget) {
      this((EntityType<? extends Abyss_Orb_Entity>)ModEntities.ABYSS_ORB.get(), worldIn);
      this.m_5602_(entity);
      this.finalTarget = finalTarget;
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(TRACKING, false);
      this.f_19804_.m_135372_(DAMAGE, 0.0F);
   }

   public float getDamage() {
      return (Float)this.f_19804_.m_135370_(DAMAGE);
   }

   public void setDamage(float damage) {
      this.f_19804_.m_135381_(DAMAGE, damage);
   }

   public boolean m_6783_(double p_36837_) {
      double d0 = this.m_20191_().m_82309_() * 4.0;
      if (Double.isNaN(d0)) {
         d0 = 4.0;
      }

      d0 *= 64.0;
      return p_36837_ < d0 * d0;
   }

   public void m_7380_(CompoundTag p_37357_) {
      super.m_7380_(p_37357_);
      if (this.finalTarget != null) {
         p_37357_.m_128362_("Target", this.finalTarget.m_20148_());
      }

      p_37357_.m_128365_("power", this.m_20063_(new double[]{this.xPower, this.yPower, this.zPower}));
      p_37357_.m_128405_("timer", this.timer);
      p_37357_.m_128379_("tracking", this.getTracking());
      p_37357_.m_128350_("damage", this.getDamage());
   }

   public void m_7378_(CompoundTag p_37353_) {
      super.m_7378_(p_37353_);
      if (p_37353_.m_128403_("Target")) {
         this.targetId = p_37353_.m_128342_("Target");
      }

      if (p_37353_.m_128425_("power", 9)) {
         ListTag listtag = p_37353_.m_128437_("power", 6);
         if (listtag.size() == 3) {
            this.xPower = listtag.m_128772_(0);
            this.yPower = listtag.m_128772_(1);
            this.zPower = listtag.m_128772_(2);
         }
      }

      this.timer = p_37353_.m_128451_("timer");
      this.setTracking(p_37353_.m_128471_("fired"));
      this.setDamage(p_37353_.m_128457_("damage"));
   }

   public void setTracking(boolean tracking) {
      this.f_19804_.m_135381_(TRACKING, tracking);
   }

   public boolean getTracking() {
      return (Boolean)this.f_19804_.m_135370_(TRACKING);
   }

   public void m_8119_() {
      Entity entity = this.m_37282_();
      if (this.f_19853_.f_46443_ || (entity == null || !entity.m_213877_()) && this.f_19853_.m_46805_(this.m_20183_())) {
         super.m_8119_();
         HitResult hitresult = ProjectileUtil.m_37294_(this, this::m_5603_);
         if (hitresult.m_6662_() != Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.m_6532_(hitresult);
         }

         this.m_20101_();
         Vec3 vec3 = this.m_20184_();
         double d0 = this.m_20185_() + vec3.f_82479_;
         double d1 = this.m_20186_() + vec3.f_82480_;
         double d2 = this.m_20189_() + vec3.f_82481_;
         ProjectileUtil.m_37284_(this, 0.2F);
         float f = this.getInertia();
         if (this.m_20069_()) {
            for (int i = 0; i < 4; i++) {
               float f1 = 0.25F;
               this.f_19853_
                  .m_7106_(
                     ParticleTypes.f_123795_,
                     d0 - vec3.f_82479_ * 0.25,
                     d1 - vec3.f_82480_ * 0.25,
                     d2 - vec3.f_82481_ * 0.25,
                     vec3.f_82479_,
                     vec3.f_82480_,
                     vec3.f_82481_
                  );
            }

            f = 0.8F;
         }

         this.f_19853_
            .m_7106_(
               ParticleTypes.f_123789_, this.m_20185_() - vec3.f_82479_, this.m_20186_() - vec3.f_82480_ + 0.15, this.m_20189_() - vec3.f_82481_, 0.0, 0.0, 0.0
            );
         this.f_19853_
            .m_7106_(
               new LightningParticle.OrbData(102, 26, 204),
               d0 - vec3.f_82479_ * 0.5 + this.f_19796_.m_188500_() * 0.6 - 0.3,
               d1 - vec3.f_82480_ * 0.5,
               d2 - vec3.f_82481_ * 0.5 + this.f_19796_.m_188500_() * 0.6 - 0.3,
               vec3.f_82479_,
               vec3.f_82480_,
               vec3.f_82481_
            );
         double dx = this.m_20185_() + (double)(1.5F * (this.f_19796_.m_188501_() - 0.5F));
         double dy = this.m_20186_() + (double)(1.5F * (this.f_19796_.m_188501_() - 0.5F));
         double dz = this.m_20189_() + (double)(1.5F * (this.f_19796_.m_188501_() - 0.5F));
         float r = 0.4F;
         float g = 0.101960786F;
         float b = 0.8F;
         this.f_19853_.m_7106_(new LightTrailParticle.OrbData(r, g, b, 0.1F, this.m_20206_() / 2.0F, this.m_19879_()), dx, dy, dz, 0.0, 0.0, 0.0);
         this.m_20256_(vec3.m_82520_(this.xPower, this.yPower, this.zPower).m_82490_((double)f));
         this.m_6034_(d0, d1, d2);
      } else {
         this.m_146870_();
      }

      if (!this.f_19853_.f_46443_) {
         this.timer--;
         this.lifetick++;
         if (this.timer <= 0 && !this.getTracking()) {
            this.setTracking(true);
         }

         if (this.lifetick >= 400 && this.getTracking()) {
            this.setTracking(false);
         }

         if (this.lifetick >= 500) {
            this.f_19853_.m_46518_(entity, this.m_20185_(), this.m_20186_(), this.m_20189_(), 1.0F, false, BlockInteraction.NONE);
            this.m_146870_();
         }

         if (this.finalTarget == null && this.targetId != null) {
            this.finalTarget = ((ServerLevel)this.f_19853_).m_8791_(this.targetId);
            if (this.finalTarget == null) {
               this.targetId = null;
            }
         }

         if (this.getTracking()) {
            if (this.finalTarget != null && this.finalTarget.m_6084_() && (!(this.finalTarget instanceof Player) || !this.finalTarget.m_5833_())) {
               double d = this.m_20280_(this.finalTarget);
               double dx = this.finalTarget.m_20185_() - this.m_20185_();
               double dy = this.finalTarget.m_20186_() + (double)(this.finalTarget.m_20206_() * 1.2F) - this.m_20186_();
               double dz = this.finalTarget.m_20189_() - this.m_20189_();
               double d13 = 2.0;
               dx /= d;
               dy /= d;
               dz /= d;
               this.xPower += dx * d13;
               this.yPower += dy * d13;
               this.zPower += dz * d13;
               this.xPower = Mth.m_14008_((double)((float)this.xPower), -0.05, 0.05);
               this.yPower = Mth.m_14008_((double)((float)this.yPower), -0.05, 0.05);
               this.zPower = Mth.m_14008_((double)((float)this.zPower), -0.05, 0.05);
            } else {
               this.yPower = -0.05;
            }
         }
      }
   }

   public void setUp(int delay) {
      this.setTracking(false);
      this.timer = delay;
   }

   protected void m_5790_(EntityHitResult result) {
      super.m_5790_(result);
      Entity entity1 = this.m_37282_();
      if (!this.f_19853_.f_46443_
         && (
            !(result.m_82443_() instanceof The_Leviathan_Part) && !(result.m_82443_() instanceof The_Leviathan_Entity)
               || !(entity1 instanceof The_Leviathan_Entity)
         )) {
         Entity entity = result.m_82443_();
         boolean flag;
         if (entity1 instanceof LivingEntity livingentity) {
            flag = entity.m_6469_(DamageSource.m_19340_(this, livingentity), this.getDamage());
         } else {
            flag = entity.m_6469_(DamageSource.f_19319_, this.getDamage());
         }

         if (flag && entity instanceof LivingEntity) {
            ((LivingEntity)entity).m_147207_(new MobEffectInstance((MobEffect)ModEffect.EFFECTABYSSAL_FEAR.get(), 100, 0), this.m_150173_());
         }

         this.f_19853_.m_46518_(entity1, this.m_20185_(), this.m_20186_(), this.m_20189_(), 1.0F, false, BlockInteraction.NONE);
         this.m_146870_();
      }
   }

   protected void m_8060_(BlockHitResult result) {
      super.m_8060_(result);
      Entity entity1 = this.m_37282_();
      if (this.getTracking()) {
         if (this.lifetick >= 200 && !this.f_19853_.f_46443_) {
            this.f_19853_.m_46518_(entity1, this.m_20185_(), this.m_20186_(), this.m_20189_(), 1.0F, false, BlockInteraction.NONE);
            this.m_146870_();
         }
      } else if (this.lifetick >= 400 && !this.f_19853_.f_46443_) {
         this.f_19853_.m_46518_(entity1, this.m_20185_(), this.m_20186_(), this.m_20189_(), 1.0F, false, BlockInteraction.NONE);
         this.m_146870_();
      }
   }

   protected void m_6532_(HitResult ray) {
      Type raytraceresult$type = ray.m_6662_();
      if (raytraceresult$type == Type.ENTITY) {
         this.m_5790_((EntityHitResult)ray);
      } else if (raytraceresult$type == Type.BLOCK) {
         this.m_8060_((BlockHitResult)ray);
      }
   }

   protected boolean m_5603_(Entity p_36842_) {
      return super.m_5603_(p_36842_) && !p_36842_.f_19794_;
   }

   protected float getInertia() {
      return 0.6F;
   }

   public boolean m_6087_() {
      return false;
   }

   public float m_6143_() {
      return 1.0F;
   }

   public boolean m_6469_(DamageSource p_37616_, float p_37617_) {
      return false;
   }

   public float m_213856_() {
      return 1.0F;
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      Entity entity = this.m_37282_();
      int i = entity == null ? 0 : entity.m_19879_();
      return new ClientboundAddEntityPacket(
         this.m_19879_(),
         this.m_20148_(),
         this.m_20185_(),
         this.m_20186_(),
         this.m_20189_(),
         this.m_146909_(),
         this.m_146908_(),
         this.m_6095_(),
         i,
         new Vec3(this.xPower, this.yPower, this.zPower),
         0.0
      );
   }

   public void m_141965_(ClientboundAddEntityPacket p_150128_) {
      super.m_141965_(p_150128_);
      double d0 = p_150128_.m_131503_();
      double d1 = p_150128_.m_131504_();
      double d2 = p_150128_.m_131505_();
      double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
      if (d3 != 0.0) {
         this.xPower = d0 / d3 * 0.1;
         this.yPower = d1 / d3 * 0.1;
         this.zPower = d2 / d3 * 0.1;
      }
   }
}
