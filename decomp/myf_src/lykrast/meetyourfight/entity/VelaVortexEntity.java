package lykrast.meetyourfight.entity;

import javax.annotation.Nonnull;
import lykrast.meetyourfight.registry.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class VelaVortexEntity extends AbstractHurtingProjectile {
   public static final int ACTIVATION = 20;
   public static final int LIFESPAN = 40;
   private double dirX;
   private double dirY;
   private double dirZ;
   private int timer = 20;
   private boolean fired;

   public VelaVortexEntity(EntityType<? extends VelaVortexEntity> type, Level world) {
      super(type, world);
   }

   public VelaVortexEntity(Level worldIn, LivingEntity shooter) {
      super((EntityType)ModEntities.VELA_VORTEX.get(), shooter, 0.0, 0.0, 0.0, worldIn);
   }

   private void onHit(Entity hit) {
      boolean wasHit;
      if (this.m_37282_() instanceof LivingEntity livingentity) {
         wasHit = hit.m_6469_(DamageSource.m_19340_(this, livingentity).m_19366_().m_19389_(), 5.0F);
         if (wasHit && hit.m_6084_()) {
            this.m_19970_(livingentity, hit);
         }
      } else {
         wasHit = hit.m_6469_(DamageSource.f_19319_, 5.0F);
      }

      if (wasHit && hit instanceof LivingEntity livinghit) {
         livinghit.m_7292_(new MobEffectInstance(MobEffects.f_19620_, 60));
         livinghit.m_20256_(livinghit.m_20184_().m_82520_(0.0, 0.2, 0.0));
      }
   }

   private void setUp(double dirX, double dirY, double dirZ) {
      this.fired = false;
      this.dirX = dirX;
      this.dirY = dirY;
      this.dirZ = dirZ;
      this.m_20334_(-dirX * 20.0 / 2.0, dirY, -dirZ * 20.0 / 2.0);
   }

   public void setUpTowards(double tX, double tY, double tZ, double speed) {
      Vec3 direction = new Vec3(tX - this.m_20185_(), 0.0, tZ - this.m_20189_()).m_82541_().m_82490_(2.0 * speed / 20.0);
      this.setUp(direction.f_82479_, (tY - this.m_20186_()) / 20.0, direction.f_82481_);
   }

   public void m_8119_() {
      if (!this.f_19853_.f_46443_) {
         this.timer--;
         if (this.timer <= 0) {
            if (this.fired) {
               this.m_142687_(RemovalReason.KILLED);
            } else {
               this.fired = true;
               this.timer = 40;
               this.m_20256_(this.m_20184_().m_82542_(1.0, 0.0, 1.0));
            }
         } else if (!this.fired) {
            this.m_20256_(this.m_20184_().m_82520_(this.dirX, 0.0, this.dirZ));
         }
      }

      Entity shooter = this.m_37282_();
      if (this.f_19853_.f_46443_ || (shooter == null || !shooter.m_213877_()) && this.f_19853_.m_46805_(this.m_20183_())) {
         this.superTick();
         if (!this.f_19853_.f_46443_ && this.fired) {
            for (Entity e : this.f_19853_.m_6249_(this, this.m_20191_().m_82369_(this.m_20184_()).m_82406_(0.33), x$0 -> this.m_5603_(x$0))) {
               this.onHit(e);
            }
         }

         this.m_20101_();
         Vec3 vector3d = this.m_20184_();
         double d0 = this.m_20185_() + vector3d.f_82479_;
         double d1 = this.m_20186_() + vector3d.f_82480_;
         double d2 = this.m_20189_() + vector3d.f_82481_;
         this.m_6034_(d0, d1, d2);
      } else {
         this.m_142687_(RemovalReason.KILLED);
      }
   }

   private void superTick() {
      if (!this.f_19853_.f_46443_) {
         this.m_20115_(6, this.m_142038_());
      }

      this.m_6075_();
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128347_("DX", this.dirX);
      compound.m_128347_("DY", this.dirY);
      compound.m_128347_("DZ", this.dirZ);
      compound.m_128405_("Timer", this.timer);
      compound.m_128379_("Fired", this.fired);
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.dirX = compound.m_128459_("DX");
      this.dirY = compound.m_128459_("DY");
      this.dirZ = compound.m_128459_("DZ");
      this.timer = compound.m_128451_("Timer");
      this.fired = compound.m_128471_("Fired");
   }

   public SoundSource m_5720_() {
      return SoundSource.HOSTILE;
   }

   public boolean m_6087_() {
      return false;
   }

   public boolean m_6469_(DamageSource source, float amount) {
      return false;
   }

   protected boolean m_5931_() {
      return false;
   }

   @Nonnull
   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
