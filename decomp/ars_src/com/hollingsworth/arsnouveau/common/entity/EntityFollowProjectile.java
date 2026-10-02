package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.client.particle.GlowParticleData;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;

public class EntityFollowProjectile extends ColoredProjectile {
   public static final EntityDataAccessor<BlockPos> to = SynchedEntityData.m_135353_(EntityFollowProjectile.class, EntityDataSerializers.f_135038_);
   public static final EntityDataAccessor<BlockPos> from = SynchedEntityData.m_135353_(EntityFollowProjectile.class, EntityDataSerializers.f_135038_);
   public static final EntityDataAccessor<Boolean> SPAWN_TOUCH = SynchedEntityData.m_135353_(EntityFollowProjectile.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Integer> DESPAWN = SynchedEntityData.m_135353_(EntityFollowProjectile.class, EntityDataSerializers.f_135028_);
   private int age;
   int maxAge = 500;

   public EntityFollowProjectile(Level world) {
      super((EntityType<? extends ColoredProjectile>)ModEntities.ENTITY_FOLLOW_PROJ.get(), world, 0.0, 0.0, 0.0);
   }

   public void setDespawnDistance(int distance) {
      this.m_20088_().m_135381_(DESPAWN, distance);
   }

   public EntityFollowProjectile(Level worldIn, Vec3 from, Vec3 to) {
      this((EntityType<? extends EntityFollowProjectile>)ModEntities.ENTITY_FOLLOW_PROJ.get(), worldIn);
      this.f_19804_.m_135381_(EntityFollowProjectile.to, new BlockPos(to));
      this.f_19804_.m_135381_(EntityFollowProjectile.from, new BlockPos(from));
      this.m_6034_(from.f_82479_ + 0.5, from.f_82480_ + 0.5, from.f_82481_ + 0.5);
      this.f_19804_.m_135381_(RED, 255);
      this.f_19804_.m_135381_(GREEN, 25);
      this.f_19804_.m_135381_(BLUE, 180);
      double distance = BlockUtil.distanceFrom(new BlockPos(from), new BlockPos(to));
      this.setDespawnDistance((int)(distance + 10.0));
   }

   public EntityFollowProjectile(Level worldIn, BlockPos from, BlockPos to, int r, int g, int b) {
      this(
         worldIn,
         new Vec3((double)from.m_123341_(), (double)from.m_123342_(), (double)from.m_123343_()),
         new Vec3((double)to.m_123341_(), (double)to.m_123342_(), (double)to.m_123343_())
      );
      this.f_19804_.m_135381_(RED, Math.min(r, 255));
      this.f_19804_.m_135381_(GREEN, Math.min(g, 255));
      this.f_19804_.m_135381_(BLUE, Math.min(b, 255));
   }

   public EntityFollowProjectile(Level worldIn, BlockPos from, BlockPos to, ParticleColor.IntWrapper color) {
      this(worldIn, from, to, color.r, color.g, color.b);
   }

   public EntityFollowProjectile(Level worldIn, BlockPos from, BlockPos to) {
      this(
         worldIn,
         new Vec3((double)from.m_123341_(), (double)from.m_123342_(), (double)from.m_123343_()),
         new Vec3((double)to.m_123341_(), (double)to.m_123342_(), (double)to.m_123343_())
      );
   }

   public EntityFollowProjectile(EntityType<? extends EntityFollowProjectile> entityAOEProjectileEntityType, Level world) {
      super(entityAOEProjectileEntityType, world);
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(to, new BlockPos(0, 0, 0));
      this.f_19804_.m_135372_(from, new BlockPos(0, 0, 0));
      this.f_19804_.m_135372_(SPAWN_TOUCH, this.defaultsBurst());
      this.f_19804_.m_135372_(DESPAWN, 10);
   }

   public boolean defaultsBurst() {
      return false;
   }

   public void m_8119_() {
      super.m_8119_();
      this.age++;
      if (this.age > this.maxAge) {
         this.m_142687_(RemovalReason.DISCARDED);
      } else {
         Vec3 vec3d2 = this.m_20184_();
         BlockPos dest = (BlockPos)this.f_19804_.m_135370_(to);
         if (!(BlockUtil.distanceFrom(this.m_20183_(), dest) < 1.0)
            && this.age <= 1000
            && !(BlockUtil.distanceFrom(this.m_20183_(), dest) > (double)((Integer)this.f_19804_.m_135370_(DESPAWN)).intValue())) {
            double posX = this.m_20185_();
            double posY = this.m_20186_();
            double posZ = this.m_20189_();
            double motionX = this.m_20184_().f_82479_;
            double motionY = this.m_20184_().f_82480_;
            double motionZ = this.m_20184_().f_82481_;
            if (dest.m_123341_() != 0 || dest.m_123342_() != 0 || dest.m_123343_() != 0) {
               double targetX = (double)dest.m_123341_() + 0.5;
               double targetY = (double)dest.m_123342_() + 0.5;
               double targetZ = (double)dest.m_123343_() + 0.5;
               Vec3 targetVector = new Vec3(targetX - posX, targetY - posY, targetZ - posZ);
               double length = targetVector.m_82553_();
               targetVector = targetVector.m_82490_(0.3 / length);
               double weight = 0.0;
               if (length <= 3.0) {
                  weight = 0.9 * ((3.0 - length) / 3.0);
               }

               motionX = (0.9 - weight) * motionX + (0.1 + weight) * targetVector.f_82479_;
               motionY = (0.9 - weight) * motionY + (0.1 + weight) * targetVector.f_82480_;
               motionZ = (0.9 - weight) * motionZ + (0.1 + weight) * targetVector.f_82481_;
            }

            posX += motionX;
            posY += motionY;
            posZ += motionZ;
            this.m_6034_(posX, posY, posZ);
            this.m_20334_(motionX, motionY, motionZ);
            if (this.f_19853_.f_46443_ && this.age > 1) {
               double deltaX = this.m_20185_() - this.f_19790_;
               double deltaY = this.m_20186_() - this.f_19791_;
               double deltaZ = this.m_20189_() - this.f_19792_;
               float dist = (float)(Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) * 8.0);

               for (double i = 0.0; i <= (double)dist; i++) {
                  double coeff = i / (double)dist;
                  this.f_19853_
                     .m_7106_(
                        GlowParticleData.createData(
                           new ParticleColor(
                              (Integer)this.f_19804_.m_135370_(RED), (Integer)this.f_19804_.m_135370_(GREEN), (Integer)this.f_19804_.m_135370_(BLUE)
                           )
                        ),
                        this.m_20185_() + deltaX * coeff,
                        this.m_20186_() + deltaY * coeff,
                        this.m_20189_() + deltaZ * coeff,
                        (double)(0.0125F * (this.f_19796_.m_188501_() - 0.5F)),
                        (double)(0.0125F * (this.f_19796_.m_188501_() - 0.5F)),
                        (double)(0.0125F * (this.f_19796_.m_188501_() - 0.5F))
                     );
               }
            }
         } else {
            if (this.f_19853_.f_46443_ && (Boolean)this.f_19804_.m_135370_(SPAWN_TOUCH)) {
               ParticleUtil.spawnTouch(
                  (ClientLevel)this.f_19853_,
                  this.m_20097_(),
                  new ParticleColor((Integer)this.f_19804_.m_135370_(RED), (Integer)this.f_19804_.m_135370_(GREEN), (Integer)this.f_19804_.m_135370_(BLUE))
               );
            }

            this.m_142687_(RemovalReason.DISCARDED);
         }
      }
   }

   public void m_142467_(RemovalReason reason) {
      if (reason == RemovalReason.UNLOADED_TO_CHUNK) {
         reason = RemovalReason.DISCARDED;
      }

      super.m_142467_(reason);
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.f_19804_.m_135381_(from, NBTUtil.getBlockPos(compound, "from"));
      this.f_19804_.m_135381_(to, NBTUtil.getBlockPos(compound, "to"));
   }

   @Override
   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      if (from != null) {
         NBTUtil.storeBlockPos(compound, "from", (BlockPos)this.f_19804_.m_135370_(from));
      }

      if (to != null) {
         NBTUtil.storeBlockPos(compound, "to", (BlockPos)this.f_19804_.m_135370_(to));
      }
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public EntityFollowProjectile(SpawnEntity packet, Level world) {
      super((EntityType<? extends ColoredProjectile>)ModEntities.ENTITY_FOLLOW_PROJ.get(), world);
   }

   public EntityType<?> m_6095_() {
      return (EntityType<?>)ModEntities.ENTITY_FOLLOW_PROJ.get();
   }

   public boolean m_20068_() {
      return true;
   }
}
