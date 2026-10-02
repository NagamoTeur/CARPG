package com.hollingsworth.arsnouveau.common.event.timed;

import com.hollingsworth.arsnouveau.api.event.ITimedEvent;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.block.PortalBlock;
import com.hollingsworth.arsnouveau.common.block.tile.PortalTile;
import com.hollingsworth.arsnouveau.common.block.tile.TemporaryTile;
import com.hollingsworth.arsnouveau.common.items.WarpScroll;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.SoundRegistry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BuildPortalEvent implements ITimedEvent {
   Level level;
   BlockPos targetPos;
   Direction direction;
   List<BlockPos> framePos = new ArrayList<>();
   List<BlockPos> portalPos = new ArrayList<>();
   int ticks;
   List<BlockPos> placedBlocks = new ArrayList<>();
   WarpScroll.WarpScrollData warpScrollData;

   public BuildPortalEvent(Level level, BlockPos targetPos, Direction direction, WarpScroll.WarpScrollData warpScrollData) {
      this.level = level;
      this.targetPos = targetPos;
      this.direction = direction;
      this.ticks = 1;
      this.warpScrollData = warpScrollData;
      BlockPos aboveTarget = targetPos.m_7494_();
      int width = 2;
      BlockPos leftBotTarget = aboveTarget.m_7918_(width * direction.m_122429_(), 0, width * direction.m_122431_());
      BlockPos rightBotTarget = aboveTarget.m_7918_(width * -direction.m_122429_(), 0, width * -direction.m_122431_());

      for (BlockPos pos1 : BlockPos.m_121940_(leftBotTarget, rightBotTarget)) {
         this.framePos.add(pos1.m_7949_());
      }

      for (BlockPos pos1 : BlockPos.m_121940_(rightBotTarget.m_7494_(), rightBotTarget.m_6630_(3))) {
         this.framePos.add(pos1.m_7949_());
      }

      for (BlockPos pos1 : BlockPos.m_121940_(leftBotTarget.m_6630_(4), rightBotTarget.m_6630_(4))) {
         this.framePos.add(pos1.m_7949_());
      }

      for (BlockPos pos1 : BlockPos.m_121940_(leftBotTarget.m_7494_(), leftBotTarget.m_6630_(3))) {
         this.framePos.add(pos1.m_7949_());
      }

      BlockPos leftPortalOffset = aboveTarget.m_7918_(direction.m_122429_(), 1, direction.m_122431_());
      BlockPos rightPortalOffset = aboveTarget.m_7918_(-direction.m_122429_(), 3, -direction.m_122431_());

      for (BlockPos pos1 : BlockPos.m_121940_(leftPortalOffset, rightPortalOffset)) {
         this.portalPos.add(pos1.m_7949_());
      }

      Collections.shuffle(this.portalPos);
   }

   @Override
   public void tick(boolean serverSide) {
      this.ticks++;
      if (this.ticks >= 5) {
         if (serverSide && this.level.m_46467_() % 3L == 0L) {
            boolean destroyPortal = false;
            boolean placingFrame = !this.framePos.isEmpty();
            if (placingFrame) {
               BlockPos pos = this.framePos.get(0);
               this.framePos.remove(pos);
               if (this.level.m_8055_(pos).m_60767_().m_76336_()) {
                  this.level.m_7731_(pos, BlockRegistry.TEMPORARY_BLOCK.get().m_49966_(), 3);
                  if (this.level.m_7702_(pos) instanceof TemporaryTile tile) {
                     tile.mimicState = BlockRegistry.getBlock("sourcestone").m_49966_();
                     tile.tickDuration = 1200;
                     tile.updateBlock();
                  }

                  this.level
                     .m_5594_(null, pos, BlockRegistry.getBlock("sourcestone").m_49962_(this.level.m_8055_(pos)).m_56777_(), SoundSource.BLOCKS, 1.0F, 1.0F);
                  ParticleUtil.spawnTouchPacket(this.level, pos, ParticleColor.makeRandomColor(255, 255, 255, this.level.f_46441_).toWrapper());
                  this.placedBlocks.add(pos);
                  return;
               }

               destroyPortal = true;
               ServerLevel serverLevel = (ServerLevel)this.level;
               serverLevel.m_8767_(
                  ParticleTypes.f_123813_, (double)pos.m_123341_() + 0.5, (double)(pos.m_123342_() + 1), (double)pos.m_123343_() + 0.5, 1, 0.0, 0.0, 0.0, 0.0
               );
            }

            boolean placingPortal = !this.portalPos.isEmpty() && this.framePos.isEmpty() && !destroyPortal;
            if (placingPortal) {
               for (BlockPos pos : this.portalPos) {
                  if (!this.level.m_8055_(pos).m_60767_().m_76336_()) {
                     destroyPortal = true;
                     ServerLevel serverLevel = (ServerLevel)this.level;
                     serverLevel.m_8767_(
                        ParticleTypes.f_123813_,
                        (double)pos.m_123341_() + 0.5,
                        (double)(pos.m_123342_() + 1),
                        (double)pos.m_123343_() + 0.5,
                        1,
                        0.0,
                        0.0,
                        0.0,
                        0.0
                     );
                     break;
                  }

                  this.level.m_7731_(pos, (BlockState)BlockRegistry.PORTAL_BLOCK.m_49966_().m_61124_(PortalBlock.AXIS, this.direction.m_122434_()), 2);
                  this.level.m_5594_(null, pos, BlockRegistry.PORTAL_BLOCK.m_49962_(this.level.m_8055_(pos)).m_56777_(), SoundSource.BLOCKS, 1.0F, 1.0F);
                  this.placedBlocks.add(pos);
               }

               this.portalPos.clear();
            }

            if (destroyPortal) {
               for (BlockPos pos : this.placedBlocks) {
                  this.level.m_46961_(pos, false);
               }

               this.level.m_5594_(null, this.targetPos, SoundRegistry.GAIA_SPELL_SOUND.getSoundEvent(), SoundSource.BLOCKS, 1.0F, 1.0F);
               this.portalPos.clear();
               this.framePos.clear();
            } else {
               if (this.portalPos.isEmpty()) {
                  this.level.m_5594_(null, this.targetPos.m_6630_(2), SoundRegistry.TEMPESTRY_SPELL_SOUND.getSoundEvent(), SoundSource.BLOCKS, 1.0F, 1.0F);

                  for (BlockPos pos : this.placedBlocks) {
                     if (this.level.m_7702_(pos) instanceof PortalTile portalTile) {
                        portalTile.setFromScroll(this.warpScrollData);
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean isExpired() {
      return this.portalPos.isEmpty() && this.framePos.isEmpty();
   }

   @Override
   public CompoundTag serialize(CompoundTag tag) {
      return ITimedEvent.super.serialize(tag);
   }

   @Override
   public Void onPacketHandled() {
      return ITimedEvent.super.onPacketHandled();
   }

   @Override
   public String getID() {
      return ITimedEvent.super.getID();
   }
}
