package net.xylonity.knightquest.common.entity.boss;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.NotNull;

public class AbstractNethermanProjectile extends Projectile {
   public double xPower;
   public double yPower;
   public double zPower;

   protected AbstractNethermanProjectile(EntityType<? extends AbstractNethermanProjectile> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   protected void m_8097_() {
   }

   public boolean m_6783_(double distance) {
      double size = this.m_20191_().m_82309_() * 4.0;
      size = Double.isNaN(size) ? 4.0 : size * 64.0;
      return distance < size * size;
   }

   public void m_8119_() {
      Entity entity = this.m_37282_();
      if (this.f_19853_.f_46443_ || (entity == null || !entity.m_213877_()) && this.f_19853_.m_46805_(this.m_20183_())) {
         super.m_8119_();
         HitResult hitresult = ProjectileUtil.m_37294_(this, this::m_5603_);
         if (hitresult.m_6662_() != Type.MISS) {
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

         this.m_20256_(vec3.m_82520_(this.xPower, this.yPower, this.zPower).m_82490_((double)f));
         this.f_19853_.m_7106_(this.getTrailParticle(), d0, d1 + 0.5, d2, 0.0, 0.0, 0.0);
         this.m_6034_(d0, d1, d2);
      } else {
         this.m_146870_();
      }
   }

   protected boolean m_5603_(@NotNull Entity entity) {
      return super.m_5603_(entity) && !entity.f_19794_;
   }

   protected ParticleOptions getTrailParticle() {
      return ParticleTypes.f_123762_;
   }

   protected float getInertia() {
      return 0.95F;
   }

   public void m_7380_(@NotNull CompoundTag pCompound) {
      super.m_7380_(pCompound);
      pCompound.m_128365_("power", this.m_20063_(new double[]{this.xPower, this.yPower, this.zPower}));
   }

   public void m_7378_(@NotNull CompoundTag pCompound) {
      super.m_7378_(pCompound);
      if (pCompound.m_128425_("power", 9)) {
         ListTag listtag = pCompound.m_128437_("power", 6);
         if (listtag.size() == 3) {
            this.xPower = listtag.m_128772_(0);
            this.yPower = listtag.m_128772_(1);
            this.zPower = listtag.m_128772_(2);
         }
      }
   }

   public boolean m_6087_() {
      return true;
   }

   public float m_6143_() {
      return 1.0F;
   }

   public boolean m_6469_(@NotNull DamageSource pSource, float pAmount) {
      if (this.m_6673_(pSource)) {
         return false;
      } else {
         this.m_5834_();
         Entity entity = pSource.m_7639_();
         if (entity != null) {
            if (!this.f_19853_.f_46443_) {
               Vec3 vec3 = entity.m_20154_();
               this.m_20256_(vec3);
               this.xPower = vec3.f_82479_ * 0.1;
               this.yPower = vec3.f_82480_ * 0.1;
               this.zPower = vec3.f_82481_ * 0.1;
               this.m_5602_(entity);
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public float m_213856_() {
      return 1.0F;
   }

   @NotNull
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

   public void m_141965_(@NotNull ClientboundAddEntityPacket pPacket) {
      super.m_141965_(pPacket);
      double d0 = pPacket.m_131503_();
      double d1 = pPacket.m_131504_();
      double d2 = pPacket.m_131505_();
      double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
      if (d3 != 0.0) {
         this.xPower = d0 / d3 * 0.1;
         this.yPower = d1 / d3 * 0.1;
         this.zPower = d2 / d3 * 0.1;
      }
   }
}
