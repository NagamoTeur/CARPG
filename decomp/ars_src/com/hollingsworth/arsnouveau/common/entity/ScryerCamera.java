package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.camera.ICameraMountable;
import com.hollingsworth.arsnouveau.common.block.ScryerCrystal;
import com.hollingsworth.arsnouveau.common.block.tile.ScryerCrystalTile;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketSetCameraView;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.network.PacketDistributor;

public class ScryerCamera extends Entity {
   public final double cameraSpeed = 3.3;
   public int screenshotSoundCooldown = 0;
   protected int redstoneCooldown = 0;
   protected int toggleNightVisionCooldown = 0;
   private boolean shouldProvideNightVision = false;
   public float zoomAmount = 1.0F;
   public boolean zooming = false;
   private int viewDistance = -1;
   private boolean loadedChunks = false;

   public ScryerCamera(EntityType<ScryerCamera> type, Level level) {
      super(type, level);
      this.f_19794_ = true;
   }

   public ScryerCamera(Level level, BlockPos pos) {
      this((EntityType<ScryerCamera>)ModEntities.SCRYER_CAMERA.get(), level);
      if (level.m_7702_(pos) instanceof ScryerCrystalTile cam) {
         double x = (double)pos.m_123341_() + 0.5;
         double y = (double)pos.m_123342_() + 0.5;
         double z = (double)pos.m_123343_() + 0.5;
         if (cam.down) {
            y += 0.25;
         }

         this.m_6034_(x, y, z);
         this.setInitialPitchYaw();
      } else {
         this.m_146870_();
      }
   }

   public ScryerCamera(Level level, BlockPos pos, ScryerCamera oldCamera) {
      this(level, pos);
      oldCamera.discardCamera();
   }

   private void setInitialPitchYaw() {
      Direction facing = (Direction)this.f_19853_.m_8055_(this.m_20183_()).m_61143_(ScryerCrystal.FACING);
      if (facing == Direction.NORTH) {
         this.m_146922_(180.0F);
      } else if (facing == Direction.WEST) {
         this.m_146922_(90.0F);
      } else if (facing == Direction.SOUTH) {
         this.m_146922_(0.0F);
      } else if (facing == Direction.EAST) {
         this.m_146922_(270.0F);
      } else if (facing == Direction.DOWN) {
         this.m_146926_(75.0F);
      }
   }

   protected boolean m_6093_() {
      return false;
   }

   public void m_8119_() {
      if (this.f_19853_.f_46443_) {
         if (this.screenshotSoundCooldown > 0) {
            this.screenshotSoundCooldown--;
         }

         if (this.redstoneCooldown > 0) {
            this.redstoneCooldown--;
         }

         if (this.toggleNightVisionCooldown > 0) {
            this.toggleNightVisionCooldown--;
         }
      } else if (this.f_19853_.m_8055_(this.m_20183_()).m_60734_() != BlockRegistry.SCRYERS_CRYSTAL) {
         this.m_146870_();
      }
   }

   public float getZoomAmount() {
      return this.zoomAmount;
   }

   public boolean isCameraDown() {
      if (this.f_19853_.m_7702_(this.m_20183_()) instanceof ScryerCrystalTile cam && cam.down) {
         return true;
      }

      return false;
   }

   public void setRotation(float yaw, float pitch) {
      this.m_19915_(yaw, pitch);
   }

   public void stopViewing(ServerPlayer player) {
      if (!this.f_19853_.f_46443_) {
         this.discardCamera();
         player.f_8926_ = player;
         Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketSetCameraView(player));
      }
   }

   public void discardCamera() {
      if (!this.f_19853_.f_46443_) {
         if (this.f_19853_.m_7702_(this.m_20183_()) instanceof ICameraMountable camMount) {
            camMount.stopViewing();
         }

         SectionPos chunkPos = SectionPos.m_123199_(this.m_20183_());
         int view = this.viewDistance <= 0 ? this.f_19853_.m_7654_().m_6846_().m_11312_() : this.viewDistance;

         for (int x = chunkPos.m_123341_() - view; x <= chunkPos.m_123341_() + view; x++) {
            for (int z = chunkPos.m_123343_() - view; z <= chunkPos.m_123343_() + view; z++) {
               ForgeChunkManager.forceChunk((ServerLevel)this.f_19853_, "ars_nouveau", this, x, z, false, false);
            }
         }
      }

      this.m_146870_();
   }

   public void setHasLoadedChunks(int initialViewDistance) {
      this.loadedChunks = true;
      this.viewDistance = initialViewDistance;
   }

   public boolean hasLoadedChunks() {
      return this.loadedChunks;
   }

   protected void m_8097_() {
   }

   public void m_7380_(CompoundTag tag) {
   }

   public void m_7378_(CompoundTag tag) {
   }

   public Packet<?> m_5654_() {
      return new ClientboundAddEntityPacket(this);
   }

   public boolean m_142389_() {
      return true;
   }
}
