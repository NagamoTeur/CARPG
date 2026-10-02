package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;

public class Void_Scatter_Arrow_Entity extends Arrow {
   public Void_Scatter_Arrow_Entity(EntityType type, Level worldIn) {
      super(type, worldIn);
   }

   public Void_Scatter_Arrow_Entity(EntityType type, double x, double y, double z, Level worldIn) {
      this(type, worldIn);
      this.m_6034_(x, y, z);
   }

   public Void_Scatter_Arrow_Entity(Level worldIn, LivingEntity shooter) {
      this((EntityType)ModEntities.VOID_SCATTER_ARROW.get(), shooter.m_20185_(), shooter.m_20188_() - 0.1F, shooter.m_20189_(), worldIn);
      this.m_5602_(shooter);
      if (shooter instanceof Player) {
         this.f_36705_ = Pickup.ALLOWED;
      }
   }

   public Void_Scatter_Arrow_Entity(SpawnEntity spawnEntity, Level world) {
      this((EntityType)ModEntities.VOID_SCATTER_ARROW.get(), world);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected ItemStack m_7941_() {
      return new ItemStack(Items.f_42412_);
   }

   protected void m_6532_(HitResult hit) {
      super.m_6532_(hit);
      double x = this.m_20185_();
      double y = this.m_20186_();
      double z = this.m_20189_();
      if (this.f_19853_.f_46443_) {
         for (int l2 = 0; l2 < 8; l2++) {
            this.f_19853_
               .m_7106_(
                  new ItemParticleOption(ParticleTypes.f_123752_, new ItemStack((ItemLike)ModItems.VOID_SCATTER_ARROW.get())),
                  x,
                  y,
                  z,
                  this.f_19796_.m_188583_() * 0.1,
                  this.f_19796_.m_188500_() * 0.15,
                  this.f_19796_.m_188583_() * 0.1
               );
         }
      } else {
         for (Vec3 vec : this.getShootVectors(this.f_19796_, 0.0F)) {
            Entity target = null;
            Direction dir = Direction.UP;
            if (hit.m_6662_() == Type.ENTITY) {
               target = ((EntityHitResult)hit).m_82443_();
            } else if (hit.m_6662_() == Type.BLOCK) {
               dir = ((BlockHitResult)hit).m_82434_();
            }

            vec = vec.m_82490_(0.35F);
            vec = this.mulPoseVector(vec, dir);
            Void_Shard_Entity shard = new Void_Shard_Entity(
               this.f_19853_, (LivingEntity)this.m_37282_(), x + vec.f_82479_, y + vec.f_82480_ + 0.25, vec.f_82481_ + z, vec, target
            );
            this.f_19853_.m_7967_(shard);
         }

         this.m_5496_(SoundEvents.f_11983_, 1.1F, 0.8F);
      }

      this.m_146870_();
   }

   public List<Vec3> getShootVectors(RandomSource random, float uncertainty) {
      List<Vec3> vectors = new ArrayList<>();
      float turnFraction = (1.0F + Mth.m_14116_(5.0F)) / 2.0F;
      int numPoints = 17;
      double fullness = 0.8;

      for (int i = 1; i <= numPoints; i++) {
         float dst = (float)i / (float)numPoints;
         float inclination = (random.m_188501_() - 0.5F) * uncertainty + (float)Math.acos(1.0 - fullness * (double)dst);
         float azimuth = (float)((double)((random.m_188501_() - 0.5F) * uncertainty) + (Math.PI * 2) * (double)(random.m_188501_() + turnFraction * (float)i));
         double x = Math.sin((double)inclination) * Math.cos((double)azimuth);
         double z = Math.sin((double)inclination) * Math.sin((double)azimuth);
         double y = Math.cos((double)inclination);
         Vec3 vec = new Vec3(x, y, z);
         if (i == 1) {
            vec = vec.m_82520_(0.0, 1.0, 0.0);
            vec = vec.m_82490_(0.5);
         }

         vectors.add(vec);
      }

      return vectors;
   }

   private Vec3 mulPoseVector(Vec3 v, Direction dir) {
      switch (dir) {
         case UP:
         default:
            return v;
         case DOWN:
            return v.m_82542_(0.0, -1.0, 0.0);
         case NORTH:
            return new Vec3(v.f_82481_, v.f_82479_, -v.f_82480_);
         case SOUTH:
            return new Vec3(v.f_82481_, v.f_82479_, v.f_82480_);
         case WEST:
            return new Vec3(-v.f_82480_, v.f_82481_, v.f_82479_);
         case EAST:
            return new Vec3(v.f_82480_, v.f_82481_, v.f_82479_);
      }
   }
}
