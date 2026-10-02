package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModParticle;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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

public class Axe_Blade_Entity extends Entity {
   public double xPower;
   public double yPower;
   public double zPower;
   private LivingEntity caster;
   private UUID casterUuid;
   private boolean leftOwner;
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.m_135353_(Axe_Blade_Entity.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Integer> TRANSPARENCY = SynchedEntityData.m_135353_(Axe_Blade_Entity.class, EntityDataSerializers.f_135028_);
   public int lifetick = 0;
   public AnimationState idleAnimationState = new AnimationState();

   public Axe_Blade_Entity(EntityType<? extends Axe_Blade_Entity> type, Level level) {
      super(type, level);
   }

   public Axe_Blade_Entity(
      EntityType<? extends Axe_Blade_Entity> type,
      double getX,
      double gety,
      double getz,
      double p_36821_,
      double p_36822_,
      double p_36823_,
      Level level,
      float Yrot
   ) {
      this(type, level);
      this.m_20343_(getX, gety, getz);
      double d0 = Math.sqrt(p_36821_ * p_36821_ + p_36822_ * p_36822_ + p_36823_ * p_36823_);
      if (d0 != 0.0) {
         this.xPower = p_36821_ / d0 * 0.1;
         this.yPower = p_36822_ / d0 * 0.1;
         this.zPower = p_36823_ / d0 * 0.1;
      }
   }

   public Axe_Blade_Entity(LivingEntity p_36827_, double p_36828_, double p_36829_, double p_36830_, Level p_36831_, float damage, float Yrot) {
      this(
         (EntityType<? extends Axe_Blade_Entity>)ModEntities.AXE_BLADE.get(),
         p_36827_.m_20185_(),
         p_36827_.m_20186_(),
         p_36827_.m_20189_(),
         p_36828_,
         p_36829_,
         p_36830_,
         p_36831_,
         Yrot
      );
      this.setOwner(p_36827_);
      this.setDamage(damage);
      this.m_146922_(Yrot);
   }

   public Axe_Blade_Entity(
      EntityType<? extends Axe_Blade_Entity> type,
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
      this.setOwner(p_36827_);
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
      this.f_19804_.m_135372_(TRANSPARENCY, 0);
   }

   public void setOwner(@Nullable LivingEntity p_190549_1_) {
      this.caster = p_190549_1_;
      this.casterUuid = p_190549_1_ == null ? null : p_190549_1_.m_20148_();
   }

   @Nullable
   public LivingEntity getOwner() {
      if (this.caster == null && this.casterUuid != null && this.f_19853_ instanceof ServerLevel) {
         Entity entity = ((ServerLevel)this.f_19853_).m_8791_(this.casterUuid);
         if (entity instanceof LivingEntity) {
            this.caster = (LivingEntity)entity;
         }
      }

      return this.caster;
   }

   public AnimationState getAnimationState(String input) {
      return input == "idle" ? this.idleAnimationState : new AnimationState();
   }

   public float getDamage() {
      return (Float)this.f_19804_.m_135370_(DAMAGE);
   }

   public void setDamage(float damage) {
      this.f_19804_.m_135381_(DAMAGE, damage);
   }

   public int getTransparency() {
      return (Integer)this.f_19804_.m_135370_(TRANSPARENCY);
   }

   public void setTransparency(int trans) {
      this.f_19804_.m_135381_(TRANSPARENCY, trans);
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
      super.m_8119_();
      if (!this.leftOwner) {
         this.leftOwner = this.checkLeftOwner();
      }

      if (!this.f_19853_.f_46443_) {
         this.lifetick++;
         this.setTransparency(this.lifetick);
         if (this.lifetick >= 80) {
            this.m_146870_();
         }
      } else {
         this.animateWhen(this.idleAnimationState, true, this.f_19797_);
      }

      HitResult raytraceresult = ProjectileUtil.m_37294_(this, this::canHitEntity);
      if (raytraceresult.m_6662_() != Type.MISS) {
         this.onHit(raytraceresult);
      }

      this.m_20101_();
      Vec3 vec3 = this.m_20184_();
      double d0 = this.m_20185_() + vec3.f_82479_;
      double d1 = this.m_20186_() + vec3.f_82480_;
      double d2 = this.m_20189_() + vec3.f_82481_;
      float f = this.getInertia();
      this.f_19853_
         .m_7106_(
            (ParticleOptions)ModParticle.PHANTOM_WING_FLAME.get(),
            this.m_20185_() - vec3.f_82479_,
            this.m_20186_() - vec3.f_82480_,
            this.m_20189_() - vec3.f_82481_,
            0.0,
            0.0,
            0.0
         );
      this.m_20256_(vec3.m_82520_(this.xPower, this.yPower, this.zPower).m_82490_((double)f));
      this.m_6034_(d0, d1, d2);
   }

   public void animateWhen(AnimationState state, boolean p_252220_, int p_249486_) {
      if (p_252220_) {
         state.m_216982_(p_249486_);
      } else {
         state.m_216973_();
      }
   }

   protected void onHitEntity(EntityHitResult p_37626_) {
      if (!this.f_19853_.f_46443_) {
         Entity entity = p_37626_.m_82443_();
         Entity entity1 = this.getOwner();
         if (entity1 instanceof LivingEntity livingentity) {
            entity.m_6469_(DamageSource.m_19340_(this, livingentity), this.getDamage());
            if (entity1 instanceof LivingEntity) {
               this.m_19970_((LivingEntity)entity1, entity);
            }
         }
      }
   }

   protected void onHitBlock(BlockHitResult result) {
   }

   protected void onHit(HitResult p_37260_) {
      Type hitresult$type = p_37260_.m_6662_();
      if (hitresult$type == Type.ENTITY) {
         this.onHitEntity((EntityHitResult)p_37260_);
         this.f_19853_.m_214171_(GameEvent.f_157777_, p_37260_.m_82450_(), Context.m_223719_(this, (BlockState)null));
      } else if (hitresult$type == Type.BLOCK) {
         BlockHitResult blockhitresult = (BlockHitResult)p_37260_;
         this.onHitBlock(blockhitresult);
         BlockPos blockpos = blockhitresult.m_82425_();
         this.f_19853_.m_220407_(GameEvent.f_157777_, blockpos, Context.m_223719_(this, this.f_19853_.m_8055_(blockpos)));
      }
   }

   protected boolean canHitEntity(Entity p_36842_) {
      return this.canHit(p_36842_) && !p_36842_.f_19794_;
   }

   protected boolean canHit(Entity p_37250_) {
      if (!this.canBeHitByProjectile(p_37250_)) {
         return false;
      } else {
         Entity entity = this.getOwner();
         return entity == null || this.leftOwner || !entity.m_20365_(p_37250_);
      }
   }

   public boolean canBeHitByProjectile(Entity entity) {
      return entity.m_6084_() && entity.m_6087_();
   }

   protected float getInertia() {
      return 0.85F;
   }

   protected void m_7378_(CompoundTag compound) {
      if (compound.m_128403_("Owner")) {
         this.casterUuid = compound.m_128342_("Owner");
      }

      if (compound.m_128425_("power", 9)) {
         ListTag listtag = compound.m_128437_("power", 6);
         if (listtag.size() == 3) {
            this.xPower = listtag.m_128772_(0);
            this.yPower = listtag.m_128772_(1);
            this.zPower = listtag.m_128772_(2);
         }
      }

      this.leftOwner = compound.m_128471_("LeftOwner");
   }

   protected void m_7380_(CompoundTag compound) {
      if (this.casterUuid != null) {
         compound.m_128362_("Owner", this.casterUuid);
      }

      if (this.leftOwner) {
         compound.m_128379_("LeftOwner", true);
      }

      compound.m_128365_("power", this.m_20063_(new double[]{this.xPower, this.yPower, this.zPower}));
   }

   private boolean checkLeftOwner() {
      Entity entity = this.getOwner();
      if (entity != null) {
         for (Entity entity1 : this.f_19853_
            .m_6249_(this, this.m_20191_().m_82369_(this.m_20184_()).m_82400_(1.0), p_234613_0_ -> !p_234613_0_.m_5833_() && p_234613_0_.m_6087_())) {
            if (entity1.m_20201_() == entity.m_20201_()) {
               return false;
            }
         }
      }

      return true;
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
      Entity entity = this.getOwner();
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
   }
}
