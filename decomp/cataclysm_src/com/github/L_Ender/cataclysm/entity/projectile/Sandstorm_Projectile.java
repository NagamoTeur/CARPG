package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.client.particle.StormParticle;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModTag;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.event.ForgeEventFactory;

public class Sandstorm_Projectile extends Projectile {
   public double xPower;
   public double yPower;
   public double zPower;
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.m_135353_(Sandstorm_Projectile.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.m_135353_(Sandstorm_Projectile.class, EntityDataSerializers.f_135028_);
   public AnimationState SpawnAnimationState = new AnimationState();
   public AnimationState DespawnAnimationState = new AnimationState();
   private int lifetick;
   private int discardtick;

   public Sandstorm_Projectile(EntityType<? extends Sandstorm_Projectile> type, Level level) {
      super(type, level);
   }

   public Sandstorm_Projectile(
      EntityType<? extends Sandstorm_Projectile> type, double getX, double gety, double getz, double p_36821_, double p_36822_, double p_36823_, Level level
   ) {
      this(type, level);
      this.m_7678_(getX, gety, getz, this.m_146908_(), this.m_146909_());
      this.m_20090_();
      double d0 = Math.sqrt(p_36821_ * p_36821_ + p_36822_ * p_36822_ + p_36823_ * p_36823_);
      if (d0 != 0.0) {
         this.xPower = p_36821_ / d0 * 0.1;
         this.yPower = p_36822_ / d0 * 0.1;
         this.zPower = p_36823_ / d0 * 0.1;
      }
   }

   public Sandstorm_Projectile(LivingEntity p_36827_, double p_36828_, double p_36829_, double p_36830_, Level p_36831_, float damage) {
      this(
         (EntityType<? extends Sandstorm_Projectile>)ModEntities.SANDSTORM_PROJECTILE.get(),
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
      this.m_19915_(p_36827_.m_146908_(), p_36827_.m_146909_());
   }

   public Sandstorm_Projectile(
      EntityType<? extends Sandstorm_Projectile> type,
      LivingEntity p_36827_,
      double getX,
      double gety,
      double getz,
      double p_36821_,
      double p_36822_,
      double p_36823_,
      float damage,
      Level level
   ) {
      this(type, level);
      this.m_7678_(getX, gety, getz, this.m_146908_(), this.m_146909_());
      this.m_5602_(p_36827_);
      this.setDamage(damage);
      this.m_20090_();
      double d0 = Math.sqrt(p_36821_ * p_36821_ + p_36822_ * p_36822_ + p_36823_ * p_36823_);
      if (d0 != 0.0) {
         this.xPower = p_36821_ / d0 * 0.1;
         this.yPower = p_36822_ / d0 * 0.1;
         this.zPower = p_36823_ / d0 * 0.1;
      }
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(DAMAGE, 0.0F);
      this.f_19804_.m_135372_(STATE, 0);
   }

   public AnimationState getAnimationState(String input) {
      if (input == "spawn") {
         return this.SpawnAnimationState;
      } else {
         return input == "despawn" ? this.DespawnAnimationState : new AnimationState();
      }
   }

   public void m_7350_(EntityDataAccessor<?> p_21104_) {
      if (STATE.equals(p_21104_) && this.f_19853_.f_46443_) {
         switch (this.getState()) {
            case 0:
               this.stopAllAnimationStates();
               break;
            case 1:
               this.stopAllAnimationStates();
               this.SpawnAnimationState.m_216982_(this.f_19797_);
               break;
            case 2:
               this.stopAllAnimationStates();
               this.DespawnAnimationState.m_216982_(this.f_19797_);
         }
      }

      super.m_7350_(p_21104_);
   }

   public void stopAllAnimationStates() {
      this.DespawnAnimationState.m_216973_();
      this.SpawnAnimationState.m_216973_();
   }

   public int getState() {
      return (Integer)this.f_19804_.m_135370_(STATE);
   }

   public void setState(int state) {
      this.f_19804_.m_135381_(STATE, state);
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

   public void m_8119_() {
      Entity entity = this.m_37282_();
      if (this.f_19853_.f_46443_ || (entity == null || !entity.m_213877_()) && this.f_19853_.m_46805_(this.m_20183_())) {
         super.m_8119_();
         this.lifetick++;
         HitResult hitresult = ProjectileUtil.m_37294_(this, this::m_5603_);
         if (hitresult.m_6662_() != Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.m_6532_(hitresult);
         }

         if (this.getState() == 1 && this.lifetick > 5) {
            this.setState(0);
         }

         if (this.lifetick > 290 && this.getState() == 0) {
            this.setState(2);
         }

         if (this.getState() == 2) {
            this.discardtick++;
            if (this.discardtick > 10) {
               this.m_146870_();
            }
         }

         this.m_20101_();
         Vec3 vec3 = this.m_20184_();
         double d0 = this.m_20185_() + vec3.f_82479_;
         double d1 = this.m_20186_() + vec3.f_82480_;
         double d2 = this.m_20189_() + vec3.f_82481_;
         ProjectileUtil.m_37284_(this, 0.2F);
         float f = this.getInertia();
         float ran = 0.04F;
         float r = 0.89F + this.f_19796_.m_188501_() * ran;
         float g = 0.85F + this.f_19796_.m_188501_() * ran;
         float b = 0.69F + this.f_19796_.m_188501_() * ran * 1.5F;
         this.f_19853_
            .m_7106_(
               new StormParticle.OrbData(r, g, b, 0.25F + this.f_19796_.m_188501_() * 0.45F, 0.35F + this.f_19796_.m_188501_() * 0.45F, this.m_19879_()),
               this.m_20185_(),
               this.m_20186_(),
               this.m_20189_(),
               0.0,
               0.0,
               0.0
            );
         if (!this.m_20067_()) {
            Cataclysm.PROXY.playWorldSound(this, (byte)2);
         }

         this.m_20256_(vec3.m_82520_(this.xPower, this.yPower, this.zPower).m_82490_((double)f));
         this.m_6034_(d0, d1, d2);
      } else {
         this.m_146870_();
      }
   }

   protected void m_5790_(EntityHitResult p_37626_) {
      super.m_5790_(p_37626_);
      if (!this.f_19853_.f_46443_) {
         Entity entity = p_37626_.m_82443_();
         Entity entity1 = this.m_37282_();
         boolean flag = false;
         if (entity1 instanceof LivingEntity) {
            if (!entity.m_6095_().m_204039_(ModTag.TEAM_ANCIENT_REMNANT) || !entity1.m_6095_().m_204039_(ModTag.TEAM_ANCIENT_REMNANT)) {
               LivingEntity livingentity = (LivingEntity)entity1;
               flag = entity.m_6469_(DamageSource.m_19340_(this, livingentity), this.getDamage());
               if (flag && entity.m_6084_()) {
                  this.m_19970_(livingentity, entity);
               }
            }
         } else {
            flag = entity.m_6469_(DamageSource.f_19319_, this.getDamage());
         }

         if (flag && entity instanceof LivingEntity) {
            ((LivingEntity)entity).m_147207_(new MobEffectInstance((MobEffect)ModEffect.EFFECTCURSE_OF_DESERT.get(), 100, 1), this.m_150173_());
         }

         this.setState(2);
      }
   }

   protected void m_6532_(HitResult ray) {
      Type hitresult$type = ray.m_6662_();
      if (hitresult$type == Type.ENTITY) {
         this.m_5790_((EntityHitResult)ray);
         this.f_19853_.m_214171_(GameEvent.f_157777_, ray.m_82450_(), Context.m_223719_(this, (BlockState)null));
      } else if (hitresult$type == Type.BLOCK) {
         BlockHitResult blockhitresult = (BlockHitResult)ray;
         this.m_8060_(blockhitresult);
         BlockPos blockpos = blockhitresult.m_82425_();
         this.f_19853_.m_220407_(GameEvent.f_157777_, blockpos, Context.m_223719_(this, this.f_19853_.m_8055_(blockpos)));
      }
   }

   protected boolean m_5603_(Entity p_36842_) {
      return super.m_5603_(p_36842_) && !p_36842_.f_19794_;
   }

   protected float getInertia() {
      return 0.9F;
   }

   public void m_7380_(CompoundTag p_36848_) {
      super.m_7380_(p_36848_);
      p_36848_.m_128365_("power", this.m_20063_(new double[]{this.xPower, this.yPower, this.zPower}));
      p_36848_.m_128405_("state", this.getState());
      p_36848_.m_128350_("damage", this.getDamage());
   }

   public void m_7378_(CompoundTag p_36844_) {
      super.m_7378_(p_36844_);
      if (p_36844_.m_128425_("power", 9)) {
         ListTag listtag = p_36844_.m_128437_("power", 6);
         if (listtag.size() == 3) {
            this.xPower = listtag.m_128772_(0);
            this.yPower = listtag.m_128772_(1);
            this.zPower = listtag.m_128772_(2);
         }
      }

      this.setState(p_36844_.m_128451_("state"));
      this.setDamage(p_36844_.m_128457_("damage"));
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
