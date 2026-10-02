package lykrast.meetyourfight.entity;

import javax.annotation.Nonnull;
import lykrast.meetyourfight.registry.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;

public class ProjectileLineEntity extends AbstractHurtingProjectile {
   private static final EntityDataAccessor<Integer> PROJECTILE_VARIANT = SynchedEntityData.m_135353_(
      ProjectileLineEntity.class, EntityDataSerializers.f_135028_
   );
   public static final int VAR_BELLRINGER = 0;
   public static final int VAR_DAME_FORTUNA = 1;
   public static final int VAR_ROSALYNE = 2;
   private double dirX;
   private double dirY;
   private double dirZ;
   private double startX;
   private double startY;
   private double startZ;
   private int timer;
   private boolean fired;

   public ProjectileLineEntity(EntityType<? extends ProjectileLineEntity> type, Level world) {
      super(type, world);
   }

   public ProjectileLineEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
      super((EntityType)ModEntities.PROJECTILE_LINE.get(), shooter, accelX, accelY, accelZ, worldIn);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(PROJECTILE_VARIANT, 0);
   }

   protected void m_5790_(EntityHitResult raytrace) {
      super.m_5790_(raytrace);
      if (!this.f_19853_.f_46443_ && this.fired) {
         Entity hit = raytrace.m_82443_();
         if (this.m_37282_() instanceof LivingEntity livingentity) {
            boolean wasHit = hit.m_6469_(DamageSource.m_19340_(this, livingentity).m_19366_().m_19389_(), 16.0F);
            if (wasHit && hit.m_6084_()) {
               this.m_19970_(livingentity, hit);
            }

            this.m_142687_(RemovalReason.KILLED);
         } else {
            boolean var6 = hit.m_6469_(DamageSource.f_19319_, 5.0F);
         }
      }
   }

   public void setUp(int delay, double dirX, double dirY, double dirZ, double startX, double startY, double startZ) {
      this.fired = false;
      this.timer = delay;
      this.dirX = dirX;
      this.dirY = dirY;
      this.dirZ = dirZ;
      this.startX = startX;
      this.startY = startY;
      this.startZ = startZ;
   }

   public void setUpTowards(int delay, double startX, double startY, double startZ, double endX, double endY, double endZ, double speed) {
      Vec3 vec = new Vec3(endX - startX, endY - startY, endZ - startZ).m_82541_().m_82490_(speed);
      this.setUp(delay, vec.f_82479_, vec.f_82480_, vec.f_82481_, startX, startY, startZ);
   }

   protected boolean m_5603_(Entity target) {
      return this.getVariant() != 2 || !(target instanceof RoseSpiritEntity) && !(target instanceof RosalyneEntity) ? super.m_5603_(target) : false;
   }

   public void m_8119_() {
      if (!this.f_19853_.f_46443_) {
         this.timer--;
         if (this.timer <= 0) {
            if (this.fired) {
               this.m_142687_(RemovalReason.KILLED);
            } else {
               this.fired = true;
               this.m_20256_(new Vec3(0.0, 0.0, 0.0));
               this.timer = 30;
            }
         }

         Vec3 motion = this.m_20184_();
         double d0 = this.m_20185_();
         double d1 = this.m_20186_();
         double d2 = this.m_20189_();
         if (this.fired) {
            if (motion.m_82556_() <= 16.0) {
               this.m_20256_(motion.m_82520_(this.dirX * 0.1, this.dirY * 0.1, this.dirZ * 0.1));
            }
         } else {
            this.m_20256_(new Vec3(this.startX - d0, this.startY - d1, this.startZ - d2).m_82490_(1.0 / (double)this.timer));
         }
      }

      Entity shooter = this.m_37282_();
      if (this.f_19853_.f_46443_ || (shooter == null || !shooter.m_213877_()) && this.f_19853_.m_46805_(this.m_20183_())) {
         this.superTick();
         HitResult raytraceresult = ProjectileUtil.m_37294_(this, this::m_5603_);
         if (raytraceresult.m_6662_() != Type.MISS && !ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
            this.m_6532_(raytraceresult);
         }

         this.m_20101_();
         Vec3 vector3d = this.m_20184_();
         double d0 = this.m_20185_() + vector3d.f_82479_;
         double d1 = this.m_20186_() + vector3d.f_82480_;
         double d2 = this.m_20189_() + vector3d.f_82481_;
         ProjectileUtil.m_37284_(this, 0.2F);
         this.f_19853_.m_7106_(ParticleTypes.f_123810_, d0, d1 + 0.5, d2, 0.0, 0.0, 0.0);
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

   public void setVariant(int variant) {
      this.f_19804_.m_135381_(PROJECTILE_VARIANT, variant);
   }

   public int getVariant() {
      return (Integer)this.f_19804_.m_135370_(PROJECTILE_VARIANT);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128347_("DX", this.dirX);
      compound.m_128347_("DY", this.dirY);
      compound.m_128347_("DZ", this.dirZ);
      compound.m_128347_("SX", this.startX);
      compound.m_128347_("SY", this.startY);
      compound.m_128347_("SZ", this.startZ);
      compound.m_128405_("Timer", this.timer);
      compound.m_128379_("Fired", this.fired);
      compound.m_128405_("Variant", this.getVariant());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.dirX = compound.m_128459_("DX");
      this.dirY = compound.m_128459_("DY");
      this.dirZ = compound.m_128459_("DZ");
      this.startX = compound.m_128459_("SX");
      this.startY = compound.m_128459_("SY");
      this.startZ = compound.m_128459_("SZ");
      this.timer = compound.m_128451_("Timer");
      this.fired = compound.m_128471_("Fired");
      this.setVariant(compound.m_128451_("Variant"));
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
