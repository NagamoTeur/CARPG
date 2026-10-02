package com.bobmowzie.mowziesmobs.server.entity.effects.geomancy;

import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.IAnimationTickable;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityCameraShake;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityFallingBlock;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityMagicEffect;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class EntityGeomancyBase extends EntityMagicEffect implements IAnimatable, IAnimationTickable {
   private static final byte EXPLOSION_PARTICLES_ID = 69;
   protected static final EntityDataAccessor<Optional<BlockState>> BLOCK_STATE = SynchedEntityData.m_135353_(
      EntityGeomancyBase.class, EntityDataSerializers.f_135034_
   );
   private static final EntityDataAccessor<Integer> TIER = SynchedEntityData.m_135353_(EntityGeomancyBase.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> DEATH_TIME = SynchedEntityData.m_135353_(EntityGeomancyBase.class, EntityDataSerializers.f_135028_);
   private AnimationFactory factory = new AnimationFactory(this);
   private boolean doRemoveTimer = true;

   public EntityGeomancyBase(EntityType<? extends EntityMagicEffect> type, Level worldIn) {
      super(type, worldIn);
   }

   public EntityGeomancyBase(EntityType<? extends EntityMagicEffect> type, Level worldIn, LivingEntity caster, BlockState blockState, BlockPos pos) {
      super(type, worldIn, caster);
      if (!worldIn.f_46443_ && blockState != null) {
         Block block = blockState.m_60734_();
         BlockState newBlock = blockState;
         Material mat = blockState.m_60767_();
         if (blockState.m_60734_() == Blocks.f_50440_ || blockState.m_60734_() == Blocks.f_50195_ || mat == Material.f_76314_) {
            newBlock = Blocks.f_50493_.m_49966_();
         } else if (mat == Material.f_76278_) {
            if (ForgeRegistries.BLOCKS.getKey(block) != null && ForgeRegistries.BLOCKS.getKey(block).m_135815_().contains("ore")) {
               newBlock = Blocks.f_50069_.m_49966_();
            }

            if (blockState.m_60734_() == Blocks.f_50331_) {
               newBlock = Blocks.f_50134_.m_49966_();
            }

            if (blockState.m_60734_() == Blocks.f_50094_ || blockState.m_60734_() == Blocks.f_50061_ || blockState.m_60734_() == Blocks.f_50286_) {
               newBlock = Blocks.f_50652_.m_49966_();
            }
         } else if (mat == Material.f_76313_) {
            if (blockState.m_60734_() == Blocks.f_50129_) {
               newBlock = Blocks.f_50352_.m_49966_();
            }
         } else if (mat == Material.f_76317_) {
            if (blockState.m_60734_() == Blocks.f_49992_) {
               newBlock = Blocks.f_50062_.m_49966_();
            } else if (blockState.m_60734_() == Blocks.f_49993_) {
               newBlock = Blocks.f_50394_.m_49966_();
            } else if (blockState.m_60734_() == Blocks.f_49994_) {
               newBlock = Blocks.f_50652_.m_49966_();
            } else if (blockState.m_60734_() == Blocks.f_50135_) {
               newBlock = Blocks.f_50134_.m_49966_();
            }
         }

         if (!newBlock.m_60796_(worldIn, pos)) {
            newBlock = Blocks.f_50069_.m_49966_();
         }

         this.setBlock(newBlock);
      }
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (this.doRemoveTimer()) {
         int newDeathTime = this.getDeathTime() - 1;
         this.setDeathTime(newDeathTime);
         if (newDeathTime < 0) {
            this.explode();
         }
      }
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(BLOCK_STATE, Optional.of(Blocks.f_50493_.m_49966_()));
      this.m_20088_().m_135372_(DEATH_TIME, 1200);
      this.m_20088_().m_135372_(TIER, 0);
   }

   public boolean m_5829_() {
      return true;
   }

   public boolean m_6051_() {
      return false;
   }

   @Override
   public PushReaction m_7752_() {
      return PushReaction.BLOCK;
   }

   public boolean m_20067_() {
      return false;
   }

   public void m_5496_(SoundEvent soundIn, float volume, float pitch) {
      super.m_5496_(soundIn, volume, pitch + this.f_19796_.m_188501_() * 0.25F - 0.125F);
   }

   public boolean m_6128_() {
      return true;
   }

   protected void explode() {
      this.f_19853_.m_7605_(this, (byte)69);
      EntityGeomancyBase.GeomancyTier tier = this.getTier();
      if (tier == EntityGeomancyBase.GeomancyTier.SMALL) {
         this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_SMALL.get(), 1.5F, 0.9F);
         this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_BREAK.get(), 1.5F, 1.0F);
      } else if (tier == EntityGeomancyBase.GeomancyTier.MEDIUM) {
         this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_SMALL.get(), 1.5F, 0.7F);
         this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_BREAK_MEDIUM_3.get(), 1.5F, 1.5F);
      } else if (tier == EntityGeomancyBase.GeomancyTier.LARGE) {
         this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_BIG.get(), 1.5F, 1.0F);
         this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_BREAK_MEDIUM_1.get(), 1.5F, 0.9F);
         EntityCameraShake.cameraShake(this.f_19853_, this.m_20182_(), 15.0F, 0.05F, 0, 20);

         for (int i = 0; i < 5; i++) {
            Vec3 particlePos = new Vec3((double)(this.f_19796_.m_188501_() * 2.0F), 0.0, 0.0);
            particlePos = particlePos.m_82524_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
            particlePos = particlePos.m_82496_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
            particlePos = particlePos.m_82549_(new Vec3(0.0, (double)(this.m_20206_() / 4.0F), 0.0));
            EntityFallingBlock fallingBlock = new EntityFallingBlock((EntityType<?>)EntityHandler.FALLING_BLOCK.get(), this.f_19853_, 70, this.getBlock());
            fallingBlock.m_6034_(this.m_20185_() + particlePos.f_82479_, this.m_20186_() + 0.5 + particlePos.f_82480_, this.m_20189_() + particlePos.f_82481_);
            fallingBlock.m_20334_(
               (double)((float)particlePos.f_82479_ * 0.3F), (double)(0.2F + this.f_19796_.m_188501_() * 0.6F), (double)((float)particlePos.f_82481_ * 0.3F)
            );
            this.f_19853_.m_7967_(fallingBlock);
         }
      } else if (tier == EntityGeomancyBase.GeomancyTier.HUGE) {
         this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_BIG.get(), 1.5F, 0.5F);
         this.m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_BREAK_LARGE_1.get(), 1.5F, 0.5F);
         EntityCameraShake.cameraShake(this.f_19853_, this.m_20182_(), 20.0F, 0.05F, 0, 20);

         for (int i = 0; i < 7; i++) {
            Vec3 particlePos = new Vec3((double)(this.f_19796_.m_188501_() * 2.5F), 0.0, 0.0);
            particlePos = particlePos.m_82524_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
            particlePos = particlePos.m_82496_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
            particlePos = particlePos.m_82549_(new Vec3(0.0, (double)(this.m_20206_() / 4.0F), 0.0));
            EntityFallingBlock fallingBlock = new EntityFallingBlock((EntityType<?>)EntityHandler.FALLING_BLOCK.get(), this.f_19853_, 70, this.getBlock());
            fallingBlock.m_6034_(this.m_20185_() + particlePos.f_82479_, this.m_20186_() + 0.5 + particlePos.f_82480_, this.m_20189_() + particlePos.f_82481_);
            fallingBlock.m_20334_(
               (double)((float)particlePos.f_82479_ * 0.3F), (double)(0.2F + this.f_19796_.m_188501_() * 0.6F), (double)((float)particlePos.f_82481_ * 0.3F)
            );
            this.f_19853_.m_7967_(fallingBlock);
         }
      }

      this.m_146870_();
   }

   private void spawnExplosionParticles() {
      for (int i = 0; (float)i < 40.0F * this.m_20205_(); i++) {
         Vec3 particlePos = new Vec3((double)this.f_19796_.m_188501_() * 0.7 * (double)this.m_20205_(), 0.0, 0.0);
         particlePos = particlePos.m_82524_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
         particlePos = particlePos.m_82496_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
         this.f_19853_
            .m_7106_(
               new BlockParticleOption(ParticleTypes.f_123794_, this.getBlock()),
               this.m_20185_() + particlePos.f_82479_,
               this.m_20186_() + 0.5 + particlePos.f_82480_,
               this.m_20189_() + particlePos.f_82481_,
               particlePos.f_82479_,
               particlePos.f_82480_,
               particlePos.f_82481_
            );
      }
   }

   public void m_7822_(byte id) {
      if (id == 69) {
         this.spawnExplosionParticles();
      } else {
         super.m_7822_(id);
      }
   }

   public BlockState getBlock() {
      Optional<BlockState> bsOp = (Optional<BlockState>)this.m_20088_().m_135370_(BLOCK_STATE);
      return bsOp.orElse(null);
   }

   public void setBlock(BlockState block) {
      this.m_20088_().m_135381_(BLOCK_STATE, Optional.of(block));
   }

   public EntityGeomancyBase.GeomancyTier getTier() {
      return EntityGeomancyBase.GeomancyTier.values()[this.f_19804_.m_135370_(TIER)];
   }

   public void setTier(EntityGeomancyBase.GeomancyTier size) {
      this.f_19804_.m_135381_(TIER, size.ordinal());
   }

   public int getDeathTime() {
      return (Integer)this.f_19804_.m_135370_(DEATH_TIME);
   }

   public void setDeathTime(int deathTime) {
      this.f_19804_.m_135381_(DEATH_TIME, deathTime);
   }

   @Override
   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      BlockState blockState = this.getBlock();
      if (blockState != null) {
         compound.m_128365_("block", NbtUtils.m_129202_(blockState));
      }

      if (this.doRemoveTimer()) {
         compound.m_128405_("deathTime", this.getDeathTime());
      }

      compound.m_128405_("tier", this.getTier().ordinal());
   }

   @Override
   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      Tag blockStateCompound = compound.m_128423_("block");
      if (blockStateCompound != null) {
         BlockState blockState = NbtUtils.m_129241_((CompoundTag)blockStateCompound);
         this.setBlock(blockState);
      }

      if (compound.m_128441_("deathTime")) {
         this.doRemoveTimer = true;
         this.setDeathTime(compound.m_128451_("deathTime"));
      } else {
         this.doRemoveTimer = false;
      }

      this.setTier(EntityGeomancyBase.GeomancyTier.values()[compound.m_128451_("tier")]);
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }

   @Override
   public int tickTimer() {
      return this.f_19797_;
   }

   public void registerControllers(AnimationData data) {
   }

   public boolean doRemoveTimer() {
      return this.doRemoveTimer;
   }

   public void setDoRemoveTimer(boolean doRemoveTimer) {
      this.doRemoveTimer = doRemoveTimer;
   }

   public static enum GeomancyTier {
      NONE,
      SMALL,
      MEDIUM,
      LARGE,
      HUGE;
   }
}
