package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

public class Dimensional_Rift_Entity extends Entity {
   protected static final EntityDataAccessor<Integer> LIFESPAN = SynchedEntityData.m_135353_(Dimensional_Rift_Entity.class, EntityDataSerializers.f_135028_);
   protected static final EntityDataAccessor<Integer> STAGE = SynchedEntityData.m_135353_(Dimensional_Rift_Entity.class, EntityDataSerializers.f_135028_);
   private boolean madeOpenNoise = false;
   private boolean madeCloseNoise = false;
   private boolean madeParticle = false;
   @Nullable
   private LivingEntity owner;
   @Nullable
   private UUID ownerUUID;
   public int ambientSoundTime;
   private final ObjectArrayList<BlockPos> toBlow = new ObjectArrayList();

   public Dimensional_Rift_Entity(EntityType<?> entityTypeIn, Level worldIn) {
      super(entityTypeIn, worldIn);
   }

   public Dimensional_Rift_Entity(Level worldIn, double x, double y, double z, LivingEntity casterIn) {
      this((EntityType<?>)ModEntities.DIMENSIONAL_RIFT.get(), worldIn);
      this.setOwner(casterIn);
      this.setLifespan(300);
      this.m_6034_(x, y, z);
   }

   public Packet<?> m_5654_() {
      return new ClientboundAddEntityPacket(this);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.madeOpenNoise) {
         this.m_146850_(GameEvent.f_157810_);
         this.m_5496_((SoundEvent)ModSounds.BLACK_HOLE_OPENING.get(), 0.7F, 1.0F + this.f_19796_.m_188501_() * 0.2F);
         this.madeOpenNoise = true;
      }

      for (Entity entity : this.f_19853_.m_45933_(this, this.m_20191_().m_82400_(30.0))) {
         if (entity != this.owner
            && (!(entity instanceof Player) || !((Player)entity).m_150110_().f_35934_)
            && !this.m_7307_(entity)
            && !(entity instanceof The_Leviathan_Entity)) {
            Vec3 diff = entity.m_20182_().m_82546_(this.m_20182_().m_82520_(0.0, 0.0, 0.0));
            if (entity instanceof LivingEntity) {
               diff = diff.m_82541_().m_82490_((double)this.getStage() * 0.015);
               entity.m_20256_(entity.m_20184_().m_82546_(diff));
            } else if (!entity.m_6095_().m_204039_(ModTag.DIMENSIONAL_LIFT_IMMUNE)) {
               diff = diff.m_82541_().m_82490_((double)this.getStage() * 0.045);
               entity.m_20256_(entity.m_20184_().m_82546_(diff));
            }
         }
      }

      this.berserkBlockBreaking(15, 15, 15);

      for (LivingEntity livingentity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82377_(0.2, 0.0, 0.2))) {
         this.damage(livingentity);
      }

      for (Entity entityx : this.f_19853_.m_45933_(this, this.m_20191_().m_82400_(0.5))) {
         if (entityx instanceof Cm_Falling_Block_Entity) {
            entityx.m_142687_(RemovalReason.DISCARDED);
         }
      }

      if (this.f_19796_.m_188503_(3000) < this.ambientSoundTime++) {
         this.resetAmbientSoundTime();
         this.m_5496_((SoundEvent)ModSounds.BLACK_HOLE_LOOP.get(), 0.7F, 1.0F + this.f_19796_.m_188501_() * 0.2F);
      }

      this.setLifespan(this.getLifespan() - 1);
      if (this.getLifespan() <= 100) {
         if (!this.madeCloseNoise) {
            this.m_146850_(GameEvent.f_157810_);
            this.m_5496_((SoundEvent)ModSounds.BLACK_HOLE_CLOSING.get(), 0.7F, 1.0F + this.f_19796_.m_188501_() * 0.2F);
            this.madeCloseNoise = true;
         }

         if (this.f_19797_ % 40 == 0) {
            this.setStage(this.getStage() - 1);
         }

         if (this.getStage() <= 0) {
            if (!this.madeParticle) {
               if (this.f_19853_.f_46443_) {
                  this.f_19853_.m_7106_((ParticleOptions)ModParticle.SHOCK_WAVE.get(), this.m_20185_(), this.m_20186_(), this.m_20189_(), 0.0, 0.0, 0.0);
               } else {
                  this.f_19853_.m_46518_(this.owner, this.m_20185_(), this.m_20186_(), this.m_20189_(), 4.0F, false, BlockInteraction.NONE);
               }

               this.madeParticle = true;
            } else {
               this.m_146870_();
            }
         }
      }
   }

   private void damage(LivingEntity Hitentity) {
      LivingEntity livingentity = this.getOwner();
      if (Hitentity.m_6084_() && !Hitentity.m_20147_() && Hitentity != livingentity && !(Hitentity instanceof The_Leviathan_Entity) && this.f_19797_ % 5 == 0) {
         if (livingentity == null) {
            Hitentity.m_6469_(DamageSource.f_19319_, (float)CMConfig.DimensionalRiftdamage);
         } else {
            if (livingentity.m_7307_(Hitentity)) {
               return;
            }

            Hitentity.m_6469_(DamageSource.m_19367_(this, livingentity), (float)CMConfig.DimensionalRiftdamage);
         }
      }
   }

   private void berserkBlockBreaking(int x, int y, int z) {
      int MthX = Mth.m_14107_(this.m_20185_());
      int MthY = Mth.m_14107_(this.m_20186_());
      int MthZ = Mth.m_14107_(this.m_20189_());
      if (!this.f_19853_.f_46443_ && ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
         for (int k2 = -x; k2 <= x; k2++) {
            for (int l2 = -z; l2 <= z; l2++) {
               for (int j = -y; j <= y; j++) {
                  int i3 = MthX + k2;
                  int k = MthY + j;
                  int l = MthZ + l2;
                  BlockPos blockpos = new BlockPos(i3, k, l);
                  BlockPos blockonpos = new BlockPos(i3, k + 1, l);
                  BlockState block = this.f_19853_.m_8055_(blockpos);
                  BlockState blockon = this.f_19853_.m_8055_(blockonpos);
                  BlockEntity tileEntity = this.f_19853_.m_7702_(blockpos);
                  if ((blockon == Blocks.f_50016_.m_49966_() || blockon == Blocks.f_49990_.m_49966_())
                     && block != Blocks.f_50016_.m_49966_()
                     && !block.m_204336_(ModTag.LEVIATHAN_IMMUNE)
                     && tileEntity == null
                     && this.f_19796_.m_188503_(2000) == 0) {
                     this.f_19853_.m_7471_(blockpos, true);
                     Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(
                        this.f_19853_, (double)i3 + 0.5, (double)k + 0.5, (double)l + 0.5, block, 5
                     );
                     this.f_19853_.m_7731_(blockpos, block.m_60819_().m_76188_(), 3);
                     this.f_19853_.m_7967_(fallingBlockEntity);
                  }
               }
            }
         }
      }
   }

   public int getAmbientSoundInterval() {
      return 80;
   }

   private void resetAmbientSoundTime() {
      this.ambientSoundTime = -this.getAmbientSoundInterval();
   }

   public int getLifespan() {
      return (Integer)this.f_19804_.m_135370_(LIFESPAN);
   }

   public void setLifespan(int i) {
      this.f_19804_.m_135381_(LIFESPAN, i);
   }

   public int getStage() {
      return (Integer)this.f_19804_.m_135370_(STAGE);
   }

   public void setStage(int i) {
      this.f_19804_.m_135381_(STAGE, i);
   }

   public void setOwner(@Nullable LivingEntity p_19719_) {
      this.owner = p_19719_;
      this.ownerUUID = p_19719_ == null ? null : p_19719_.m_20148_();
   }

   @Nullable
   public LivingEntity getOwner() {
      if (this.owner == null && this.ownerUUID != null && this.f_19853_ instanceof ServerLevel) {
         Entity entity = ((ServerLevel)this.f_19853_).m_8791_(this.ownerUUID);
         if (entity instanceof LivingEntity) {
            this.owner = (LivingEntity)entity;
         }
      }

      return this.owner;
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(LIFESPAN, 300);
      this.f_19804_.m_135372_(STAGE, 0);
   }

   protected void m_7378_(CompoundTag compound) {
      this.setLifespan(compound.m_128451_("Lifespan"));
      this.setStage(compound.m_128451_("Stage"));
      if (compound.m_128403_("Owner")) {
         this.ownerUUID = compound.m_128342_("Owner");
      }
   }

   public boolean m_6783_(double p_36837_) {
      double d0 = this.m_20191_().m_82309_() * 4.0;
      if (Double.isNaN(d0)) {
         d0 = 4.0;
      }

      d0 *= 64.0;
      return p_36837_ < d0 * d0;
   }

   protected void m_7380_(CompoundTag compound) {
      compound.m_128405_("Lifespan", this.getLifespan());
      compound.m_128405_("Stage", this.getStage());
      if (this.ownerUUID != null) {
         compound.m_128362_("Owner", this.ownerUUID);
      }
   }
}
