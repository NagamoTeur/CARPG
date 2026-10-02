package com.hollingsworth.arsnouveau.common.mixin.light;

import com.hollingsworth.arsnouveau.api.event.EventQueue;
import com.hollingsworth.arsnouveau.api.event.FadeLightTimedEvent;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Entity.class})
public abstract class LightEntityMixin implements LambDynamicLight {
   @Shadow
   public Level f_19853_;
   @Shadow
   private ChunkPos f_185933_;
   @Unique
   protected int lambdynlights$luminance = 0;
   @Unique
   private int lambdynlights$lastLuminance = 0;
   @Unique
   private long lambdynlights$lastUpdate = 0L;
   @Unique
   private double lambdynlights$prevX;
   @Unique
   private double lambdynlights$prevY;
   @Unique
   private double lambdynlights$prevZ;
   @Unique
   private LongOpenHashSet lambdynlights$trackedLitChunkPos = new LongOpenHashSet();

   @Shadow
   public abstract double m_20185_();

   @Shadow
   public abstract double m_20188_();

   @Shadow
   public abstract double m_20189_();

   @Shadow
   public abstract double m_20186_();

   @Shadow
   public abstract EntityType<?> m_6095_();

   @Shadow
   public abstract BlockPos m_20183_();

   @Shadow
   public abstract boolean m_213877_();

   @Shadow
   public abstract Level m_9236_();

   @Shadow
   public abstract BlockPos m_20097_();

   @Shadow
   public abstract double m_20246_(double var1);

   @Shadow
   public abstract Vec3 m_20182_();

   @Inject(
      method = {"tick"},
      at = {@At("TAIL")}
   )
   public void onTick(CallbackInfo ci) {
      if (this.f_19853_.f_46443_ && !LightManager.shouldUpdateDynamicLight()) {
         this.lambdynlights$luminance = 0;
      }

      if (this.f_19853_.m_5776_() && LightManager.shouldUpdateDynamicLight()) {
         if (this.m_213877_()) {
            this.setDynamicLightEnabled(false);
         } else {
            this.dynamicLightTick();
            LightManager.updateTracking(this);
         }
      }
   }

   @Inject(
      method = {"remove"},
      at = {@At("TAIL")}
   )
   public void onRemove(CallbackInfo ci) {
      if (this.f_19853_.m_5776_()) {
         this.setDynamicLightEnabled(false);
      }
   }

   @Inject(
      method = {"onClientRemoval"},
      at = {@At("TAIL")}
   )
   public void removed(CallbackInfo ci) {
      if (this.f_19853_.m_5776_()) {
         this.setDynamicLightEnabled(false);
         if (this.lambdynlights$luminance > 0) {
            EventQueue.getClientQueue().addEvent(new FadeLightTimedEvent(this.m_9236_(), this.m_20182_(), 8, this.lambdynlights$luminance));
         }
      }
   }

   @Override
   public double getDynamicLightX() {
      return this.m_20185_();
   }

   @Override
   public double getDynamicLightY() {
      return this.m_20188_();
   }

   @Override
   public double getDynamicLightZ() {
      return this.m_20189_();
   }

   @Override
   public Level getDynamicLightWorld() {
      return this.f_19853_;
   }

   @Override
   public void resetDynamicLight() {
      this.lambdynlights$lastLuminance = 0;
   }

   @Override
   public boolean shouldUpdateDynamicLight() {
      return LightManager.shouldUpdateDynamicLight() && DynamLightUtil.couldGiveLight((Entity)this);
   }

   @Override
   public void dynamicLightTick() {
      this.lambdynlights$luminance = 0;
      int luminance = DynamLightUtil.lightForEntity((Entity)this);
      if (luminance > this.lambdynlights$luminance) {
         this.lambdynlights$luminance = luminance;
      }
   }

   @Override
   public int getLuminance() {
      return this.lambdynlights$luminance;
   }

   @Override
   public boolean lambdynlights$updateDynamicLight(LevelRenderer renderer) {
      if (!this.shouldUpdateDynamicLight()) {
         return false;
      } else {
         double deltaX = this.m_20185_() - this.lambdynlights$prevX;
         double deltaY = this.m_20186_() - this.lambdynlights$prevY;
         double deltaZ = this.m_20189_() - this.lambdynlights$prevZ;
         int luminance = this.getLuminance();
         if (!(Math.abs(deltaX) > 0.1) && !(Math.abs(deltaY) > 0.1) && !(Math.abs(deltaZ) > 0.1) && luminance == this.lambdynlights$lastLuminance) {
            return false;
         } else {
            this.lambdynlights$prevX = this.m_20185_();
            this.lambdynlights$prevY = this.m_20186_();
            this.lambdynlights$prevZ = this.m_20189_();
            this.lambdynlights$lastLuminance = luminance;
            LongOpenHashSet newPos = new LongOpenHashSet();
            if (luminance > 0) {
               ChunkPos entityChunkPos = this.f_185933_;
               MutableBlockPos chunkPos = new MutableBlockPos(entityChunkPos.f_45578_, DynamLightUtil.getSectionCoord(this.m_20188_()), entityChunkPos.f_45579_);
               LightManager.scheduleChunkRebuild(renderer, chunkPos);
               LightManager.updateTrackedChunks(chunkPos, this.lambdynlights$trackedLitChunkPos, newPos);
               Direction directionX = (this.m_20183_().m_123341_() & 15) >= 8 ? Direction.EAST : Direction.WEST;
               Direction directionY = (Mth.m_14080_(this.m_20188_()) & 15) >= 8 ? Direction.UP : Direction.DOWN;
               Direction directionZ = (this.m_20183_().m_123343_() & 15) >= 8 ? Direction.SOUTH : Direction.NORTH;

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
   }

   @Override
   public void lambdynlights$scheduleTrackedChunksRebuild(LevelRenderer renderer) {
      if (Minecraft.m_91087_().f_91073_ == this.f_19853_) {
         LongIterator var2 = this.lambdynlights$trackedLitChunkPos.iterator();

         while (var2.hasNext()) {
            long pos = (Long)var2.next();
            LightManager.scheduleChunkRebuild(renderer, pos);
         }
      }
   }
}
