package com.hollingsworth.arsnouveau.api.event;

import com.hollingsworth.arsnouveau.common.light.DynamLightUtil;
import com.hollingsworth.arsnouveau.common.light.LambDynamicLight;
import com.hollingsworth.arsnouveau.common.light.LightManager;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FadeLightTimedEvent implements ITimedEvent, LambDynamicLight {
   protected int lambdynlights$luminance = 0;
   private int lambdynlights$lastLuminance = 0;
   private long lambdynlights$lastUpdate = 0L;
   private LongOpenHashSet lambdynlights$trackedLitChunkPos = new LongOpenHashSet();
   public Vec3 targetPos;
   public int ticksLeft;
   int starterTicks;
   int startLuminance;
   Level level;

   public FadeLightTimedEvent(Level level, Vec3 pos, int duration, int startLuminance) {
      this.targetPos = pos;
      this.ticksLeft = duration;
      this.starterTicks = duration;
      this.startLuminance = startLuminance;
      this.level = level;
   }

   @Override
   public void tick(boolean serverSide) {
      if (!serverSide && !LightManager.shouldUpdateDynamicLight()) {
         this.lambdynlights$luminance = 0;
      }

      if (!serverSide && LightManager.shouldUpdateDynamicLight()) {
         if (this.isExpired()) {
            this.setDynamicLightEnabled(false);
         } else {
            this.dynamicLightTick();
            LightManager.updateTracking(this);
         }
      }

      this.ticksLeft--;
      if (this.ticksLeft <= 0) {
         this.setDynamicLightEnabled(false);
      }
   }

   @Override
   public boolean isExpired() {
      return this.ticksLeft <= 0;
   }

   @Override
   public double getDynamicLightX() {
      return this.targetPos.f_82479_;
   }

   @Override
   public double getDynamicLightY() {
      return this.targetPos.f_82480_;
   }

   @Override
   public double getDynamicLightZ() {
      return this.targetPos.f_82481_;
   }

   @Override
   public Level getDynamicLightWorld() {
      return this.level;
   }

   @Override
   public void resetDynamicLight() {
      this.lambdynlights$lastLuminance = 0;
   }

   @Override
   public int getLuminance() {
      return this.lambdynlights$luminance;
   }

   @Override
   public void dynamicLightTick() {
      this.lambdynlights$luminance = this.starterTicks == 0 ? 0 : (int)((double)this.startLuminance * ((double)this.ticksLeft / (double)this.starterTicks));
   }

   @Override
   public boolean shouldUpdateDynamicLight() {
      return LightManager.shouldUpdateDynamicLight();
   }

   @Override
   public boolean lambdynlights$updateDynamicLight(LevelRenderer renderer) {
      int luminance = this.getLuminance();
      if (luminance == this.lambdynlights$lastLuminance) {
         return false;
      } else {
         this.lambdynlights$lastLuminance = luminance;
         LongOpenHashSet newPos = new LongOpenHashSet();
         if (luminance > 0) {
            ChunkPos entityChunkPos = new ChunkPos(new BlockPos(this.targetPos));
            MutableBlockPos chunkPos = new MutableBlockPos(
               entityChunkPos.f_45578_, DynamLightUtil.getSectionCoord(this.targetPos.f_82480_), entityChunkPos.f_45579_
            );
            LightManager.scheduleChunkRebuild(renderer, chunkPos);
            LightManager.updateTrackedChunks(chunkPos, this.lambdynlights$trackedLitChunkPos, newPos);
            BlockPos blockPos = new BlockPos(this.targetPos);
            Direction directionX = (blockPos.m_123341_() & 15) >= 8 ? Direction.EAST : Direction.WEST;
            Direction directionY = (Mth.m_14080_((double)blockPos.m_123342_()) & 15) >= 8 ? Direction.UP : Direction.DOWN;
            Direction directionZ = (blockPos.m_123343_() & 15) >= 8 ? Direction.SOUTH : Direction.NORTH;

            for (int i = 0; i < 7; i++) {
               if (i % 4 == 0) {
                  chunkPos.m_122173_(directionX);
               } else if (i % 4 == 1) {
                  chunkPos.m_122173_(directionZ);
               } else if (i % 4 == 2) {
                  chunkPos.m_122173_(directionX.m_122424_());
               } else {
                  chunkPos.m_122173_(directionZ.m_122424_());
                  chunkPos.m_122173_(directionY);
               }

               LightManager.scheduleChunkRebuild(renderer, chunkPos);
               LightManager.updateTrackedChunks(chunkPos, this.lambdynlights$trackedLitChunkPos, newPos);
            }
         }

         this.lambdynlights$scheduleTrackedChunksRebuild(renderer);
         this.lambdynlights$trackedLitChunkPos = newPos;
         return true;
      }
   }

   @Override
   public void lambdynlights$scheduleTrackedChunksRebuild(LevelRenderer renderer) {
      if (Minecraft.m_91087_().f_91073_ == this.level) {
         LongIterator var2 = this.lambdynlights$trackedLitChunkPos.iterator();

         while (var2.hasNext()) {
            long pos = (Long)var2.next();
            LightManager.scheduleChunkRebuild(renderer, pos);
         }
      }
   }
}
