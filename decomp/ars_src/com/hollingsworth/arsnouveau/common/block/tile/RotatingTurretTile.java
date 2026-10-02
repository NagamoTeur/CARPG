package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.item.IWandable;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class RotatingTurretTile extends BasicSpellTurretTile implements IWandable {
   public float rotationX;
   public float rotationY;
   public float neededRotationX;
   public float neededRotationY;
   public float clientNeededX;
   public float clientNeededY;

   public RotatingTurretTile(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
      super(blockEntityType, pos, state);
   }

   public RotatingTurretTile(BlockPos pos, BlockState state) {
      super((BlockEntityType<?>)BlockRegistry.ROTATING_TURRET_TILE.get(), pos, state);
   }

   @Override
   public void tick() {
      super.tick();
      if (this.f_58857_.f_46443_) {
         if (this.clientNeededX != this.neededRotationX) {
            float diff = this.neededRotationX - this.clientNeededX;
            if ((double)Math.abs(diff) < 0.1) {
               this.clientNeededX = this.neededRotationX;
            } else {
               this.clientNeededX += diff * 0.1F;
            }
         }

         if (this.clientNeededY != this.neededRotationY) {
            float diff = this.neededRotationY - this.clientNeededY;
            if ((double)Math.abs(diff) < 0.1) {
               this.clientNeededY = this.neededRotationY;
            } else {
               this.clientNeededY += diff * 0.1F;
            }
         }

         if (this.rotationX != this.clientNeededX) {
            float diff = this.clientNeededX - this.rotationX;
            if ((double)Math.abs(diff) < 0.1) {
               this.rotationX = this.clientNeededX;
            } else {
               this.rotationX += diff * 0.1F;
            }
         }

         if (this.rotationY != this.clientNeededY) {
            float diff = this.clientNeededY - this.rotationY;
            if ((double)Math.abs(diff) < 0.1) {
               this.rotationY = this.clientNeededY;
            } else {
               this.rotationY += diff * 0.1F;
            }
         }
      } else {
         if (this.rotationX != this.neededRotationX) {
            float diff = this.neededRotationX - this.rotationX;
            if ((double)Math.abs(diff) < 0.1) {
               this.setRotationX(this.neededRotationX);
            } else {
               this.setRotationX(this.rotationX + diff * 0.1F);
            }

            this.m_6596_();
         }

         if (this.rotationY != this.neededRotationY) {
            float diff = this.neededRotationY - this.rotationY;
            if ((double)Math.abs(diff) < 0.1) {
               this.setRotationY(this.neededRotationY);
            } else {
               this.setRotationY(this.rotationY + diff * 0.1F);
            }

            this.m_6596_();
         }
      }
   }

   public void aim(@Nullable BlockPos blockPos, Player playerEntity) {
      if (blockPos != null) {
         Vec3 thisVec = Vec3.m_82512_(this.m_58899_());
         Vec3 blockVec = Vec3.m_82512_(blockPos);
         Vec3 diffVec = blockVec.m_82546_(thisVec);
         Vec3 diffVec2D = new Vec3(diffVec.f_82479_, diffVec.f_82481_, 0.0);
         Vec3 rotVec = new Vec3(0.0, 1.0, 0.0);
         float angle = (float)(angleBetween(rotVec, diffVec2D) / Math.PI * 180.0);
         if (blockVec.f_82479_ < thisVec.f_82479_) {
            angle = -angle;
         }

         this.neededRotationX = angle + 90.0F;
         rotVec = new Vec3(diffVec.f_82479_, 0.0, diffVec.f_82481_);
         angle = (float)(angleBetween(diffVec, rotVec) * 180.0 / (float) Math.PI);
         if (blockVec.f_82480_ < thisVec.f_82480_) {
            angle = -angle;
         }

         this.neededRotationY = angle;
         this.updateBlock();
         ParticleUtil.beam(blockPos, this.m_58899_(), this.f_58857_);
         PortUtil.sendMessageNoSpam(playerEntity, Component.m_237113_("Turret now aims to " + blockPos.m_123344_()));
      }
   }

   public static double angleBetween(Vec3 a, Vec3 b) {
      double projection = a.m_82541_().m_82526_(b.m_82541_());
      return Math.acos(Mth.m_14008_(projection, -1.0, 1.0));
   }

   @Override
   public void onFinishedConnectionFirst(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
      if (storedPos != null) {
         this.aim(storedPos, playerEntity);
      }
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      tag.m_128350_("rotationY", this.rotationY);
      tag.m_128350_("rotationX", this.rotationX);
      tag.m_128350_("neededRotationY", this.neededRotationY);
      tag.m_128350_("neededRotationX", this.neededRotationX);
   }

   @Override
   public void m_142466_(CompoundTag tag) {
      super.m_142466_(tag);
      this.rotationX = tag.m_128457_("rotationX");
      this.rotationY = tag.m_128457_("rotationY");
      this.neededRotationX = tag.m_128457_("neededRotationX");
      this.neededRotationY = tag.m_128457_("neededRotationY");
   }

   public float getRotationX() {
      return this.rotationX;
   }

   public float getRotationY() {
      return this.rotationY;
   }

   public void setRotationX(float rot) {
      this.rotationX = rot;
   }

   public void setRotationY(float rot) {
      this.rotationY = rot;
   }

   public Vec3 getShootAngle() {
      float f = this.getRotationY() * (float) (Math.PI / 180.0);
      float f1 = (90.0F + this.getRotationX()) * (float) (Math.PI / 180.0);
      float f2 = Mth.m_14089_(f1);
      float f3 = Mth.m_14031_(f1);
      float f4 = Mth.m_14089_(f);
      float f5 = Mth.m_14031_(f);
      return new Vec3((double)(f3 * f4), (double)(-f5), (double)(f2 * f4)).m_82548_();
   }
}
