package com.github.L_Ender.cataclysm.capabilities;

import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Halberd_Entity;
import com.github.L_Ender.cataclysm.init.ModCapabilities;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class RenderRushCapability {
   public static ResourceLocation ID = new ResourceLocation("cataclysm", "render_rush_cap");

   public interface IRenderRushCapability extends INBTSerializable<CompoundTag> {
      void tick(LivingEntity var1);

      void setRush(boolean var1);

      boolean isRush();

      void setTimer(int var1);

      int getTimer();

      void setdamage(float var1);

      float getdamage();
   }

   public static class RenderRushCapabilityImp implements RenderRushCapability.IRenderRushCapability {
      private boolean rush;
      public int Timer;
      public float damage;

      @Override
      public void tick(LivingEntity entity) {
         if (this.isRush()) {
            int standingOnY = Mth.m_14107_(entity.m_20186_()) - 3;
            double headY = entity.m_20186_() + 2.0;
            float yawRadians = (float)Math.toRadians((double)(90.0F + entity.m_146908_()));
            int temp = this.getTimer();
            this.setTimer(temp - 1);
            if (temp > 0) {
               double yaw = Math.toRadians((double)(entity.m_146908_() + 90.0F));
               double xExpand = 3.0 * Math.cos(yaw);
               double zExpand = 3.0 * Math.sin(yaw);
               AABB attackRange = entity.m_20191_().m_82363_(xExpand, 0.0, zExpand);

               for (LivingEntity target : entity.f_19853_.m_45976_(LivingEntity.class, attackRange)) {
                  if (!target.m_7307_(entity) && target != entity) {
                     target.m_6469_(DamageSource.m_19370_(entity), this.getdamage());
                  }
               }

               if (temp % 2 == 0) {
                  this.spawnFangs(entity.m_20185_(), headY, entity.m_20189_(), standingOnY, yawRadians, 1, entity.f_19853_, entity);
                  double x = entity.m_20185_();
                  double y = entity.m_20186_() + (double)(entity.m_20206_() / 2.0F);
                  double z = entity.m_20189_();
                  float yaw2 = (float)Math.toRadians((double)(-entity.m_146908_()));
                  float yaw3 = (float)Math.toRadians((double)(-entity.m_146908_() + 180.0F));
                  entity.f_19853_
                     .m_7106_(
                        new RingParticle.RingData(yaw2, 0.0F, 20, 0.337F, 0.925F, 0.8F, 1.0F, 30.0F, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK),
                        x,
                        y,
                        z,
                        0.0,
                        0.0,
                        0.0
                     );
                  entity.f_19853_
                     .m_7106_(
                        new RingParticle.RingData(yaw3, 0.0F, 20, 0.337F, 0.925F, 0.8F, 1.0F, 30.0F, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK),
                        x,
                        y,
                        z,
                        0.0,
                        0.0,
                        0.0
                     );
               }
            }

            if (temp == 0) {
               this.setRush(false);
            }
         }
      }

      private void spawnFangs(double x, double y, double z, int lowestYCheck, float yRot, int warmupDelayTicks, Level world, LivingEntity player) {
         BlockPos blockpos = new BlockPos(x, y, z);
         boolean flag = false;
         double d0 = 0.0;

         do {
            BlockPos blockpos1 = blockpos.m_7495_();
            BlockState blockstate = world.m_8055_(blockpos1);
            if (blockstate.m_60783_(world, blockpos1, Direction.UP)) {
               if (!world.m_46859_(blockpos)) {
                  BlockState blockstate1 = world.m_8055_(blockpos);
                  VoxelShape voxelshape = blockstate1.m_60812_(world, blockpos);
                  if (!voxelshape.m_83281_()) {
                     d0 = voxelshape.m_83297_(Axis.Y);
                  }
               }

               flag = true;
               break;
            }

            blockpos = blockpos.m_7495_();
         } while (blockpos.m_123342_() >= lowestYCheck);

         if (flag) {
            world.m_7967_(
               new Phantom_Halberd_Entity(world, x, (double)blockpos.m_123342_() + d0, z, yRot, warmupDelayTicks, player, (float)CMConfig.PhantomHalberddamage)
            );
         }
      }

      @Override
      public void setRush(boolean charge) {
         this.rush = charge;
      }

      @Override
      public boolean isRush() {
         return this.rush;
      }

      @Override
      public void setdamage(float damage) {
         this.damage = damage;
      }

      @Override
      public float getdamage() {
         return this.damage;
      }

      @Override
      public void setTimer(int timer) {
         this.Timer = timer;
      }

      @Override
      public int getTimer() {
         return this.Timer;
      }

      public CompoundTag serializeNBT() {
         CompoundTag tag = new CompoundTag();
         tag.m_128379_("isRush", this.isRush());
         tag.m_128350_("damage", this.getdamage());
         tag.m_128405_("timer", this.getTimer());
         return tag;
      }

      public void deserializeNBT(CompoundTag nbt) {
         this.setRush(nbt.m_128471_("isRush"));
         this.setdamage(nbt.m_128457_("damage"));
         this.setTimer(nbt.m_128451_("timer"));
      }

      public static class RenderRushProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
         private final LazyOptional<RenderRushCapability.IRenderRushCapability> instance = LazyOptional.of(RenderRushCapability.RenderRushCapabilityImp::new);

         public CompoundTag serializeNBT() {
            return (CompoundTag)((RenderRushCapability.IRenderRushCapability)this.instance.orElseThrow(NullPointerException::new)).serializeNBT();
         }

         public void deserializeNBT(CompoundTag nbt) {
            ((RenderRushCapability.IRenderRushCapability)this.instance.orElseThrow(NullPointerException::new)).deserializeNBT(nbt);
         }

         @Nonnull
         public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
            return ModCapabilities.RENDER_RUSH_CAPABILITY.orEmpty(cap, this.instance.cast());
         }
      }
   }
}
