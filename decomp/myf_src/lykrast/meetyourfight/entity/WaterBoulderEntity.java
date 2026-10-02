package lykrast.meetyourfight.entity;

import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lykrast.meetyourfight.registry.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class WaterBoulderEntity extends AbstractHurtingProjectile {
   private double dirX;
   private double dirY;
   private double dirZ;
   private double offsetX;
   private double offsetY;
   private double offsetZ;
   private int timer;
   private boolean fired;
   @Nullable
   private Entity target;
   @Nullable
   private UUID targetId;

   public WaterBoulderEntity(EntityType<? extends WaterBoulderEntity> type, Level world) {
      super(type, world);
   }

   public WaterBoulderEntity(Level worldIn, LivingEntity shooter, Entity target) {
      super((EntityType)ModEntities.WATER_BOULDER.get(), shooter, 0.0, 0.0, 0.0, worldIn);
      this.target = target;
   }

   private void onHit(Entity hit) {
      if (this.m_37282_() instanceof LivingEntity livingentity) {
         boolean wasHit = hit.m_6469_(DamageSource.m_19340_(this, livingentity).m_19366_().m_19389_(), 35.0F);
         if (wasHit && hit.m_6084_()) {
            this.m_19970_(livingentity, hit);
         }
      } else {
         boolean wasHit = hit.m_6469_(DamageSource.f_19319_, 5.0F);
      }
   }

   public void setUp(int delay, double dirX, double dirY, double dirZ, double startX, double startY, double startZ) {
      this.fired = false;
      this.timer = delay;
      this.dirX = dirX;
      this.dirY = dirY;
      this.dirZ = dirZ;
      this.offsetX = startX;
      this.offsetY = startY;
      this.offsetZ = startZ;
   }

   public void m_8119_() {
      if (!this.f_19853_.f_46443_) {
         if (this.target == null && this.targetId != null) {
            this.target = ((ServerLevel)this.f_19853_).m_8791_(this.targetId);
            if (this.target == null) {
               this.targetId = null;
            }
         }

         this.timer--;
         if (this.timer <= 0) {
            if (this.fired) {
               this.m_142687_(RemovalReason.KILLED);
            } else {
               this.fired = true;
               this.timer = 40;
               this.m_20334_(this.dirX, this.dirY, this.dirZ);
            }
         }

         if (!this.fired) {
            if (this.target != null && !this.target.m_213877_()) {
               double scale = 1.0;
               if (this.timer > 40) {
                  scale = 1.0 / (double)(this.timer - 40);
               }

               this.m_20256_(
                  new Vec3(
                        this.target.m_20185_() + this.offsetX - this.m_20185_(),
                        this.target.m_20186_() + this.offsetY - this.m_20186_(),
                        this.target.m_20189_() + this.offsetZ - this.m_20189_()
                     )
                     .m_82490_(scale)
               );
            } else {
               this.m_20334_(0.0, 0.0, 0.0);
            }
         }
      }

      Entity shooter = this.m_37282_();
      if (this.f_19853_.f_46443_ || (shooter == null || !shooter.m_213877_()) && this.f_19853_.m_46805_(this.m_20183_())) {
         this.superTick();
         if (!this.f_19853_.f_46443_ && this.fired) {
            for (Entity e : this.f_19853_.m_6249_(this, this.m_20191_().m_82369_(this.m_20184_()).m_82406_(0.4), x$0 -> this.m_5603_(x$0))) {
               this.onHit(e);
            }
         }

         this.m_20101_();
         Vec3 vector3d = this.m_20184_();
         double d0 = this.m_20185_() + vector3d.f_82479_;
         double d1 = this.m_20186_() + vector3d.f_82480_;
         double d2 = this.m_20189_() + vector3d.f_82481_;
         this.f_19853_
            .m_7106_(
               ParticleTypes.f_123803_, d0 + (this.f_19796_.m_188500_() - 0.5) * 3.0, d1 + 0.5, d2 + (this.f_19796_.m_188500_() - 0.5) * 3.0, 0.0, 0.0, 0.0
            );
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
      compound.m_128347_("SX", this.offsetX);
      compound.m_128347_("SY", this.offsetY);
      compound.m_128347_("SZ", this.offsetZ);
      compound.m_128405_("Timer", this.timer);
      compound.m_128379_("Fired", this.fired);
      if (this.target != null) {
         compound.m_128362_("Target", this.target.m_20148_());
      }
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.dirX = compound.m_128459_("DX");
      this.dirY = compound.m_128459_("DY");
      this.dirZ = compound.m_128459_("DZ");
      this.offsetX = compound.m_128459_("SX");
      this.offsetY = compound.m_128459_("SY");
      this.offsetZ = compound.m_128459_("SZ");
      this.timer = compound.m_128451_("Timer");
      this.fired = compound.m_128471_("Fired");
      if (compound.m_128403_("Target")) {
         this.targetId = compound.m_128342_("Target");
      }
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
