package com.hollingsworth.arsnouveau.common.event;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.event.ITimedEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraftforge.common.util.FakePlayer;

public class OpenChestEvent implements ITimedEvent {
   ServerLevel level;
   public int duration;
   public BlockPos pos;
   FakePlayer fakePlayer;

   public OpenChestEvent(ServerLevel level, BlockPos pos, int duration) {
      this.duration = duration;
      this.level = level;
      this.pos = pos;
      this.fakePlayer = ANFakePlayer.getPlayer(level);
   }

   public void open() {
      try {
         if (this.level.m_7702_(this.pos) instanceof ChestBlockEntity chestBlockEntity) {
            this.fakePlayer.f_19853_ = this.level;
            this.fakePlayer.m_9217_();
            this.fakePlayer.m_6034_((double)this.pos.m_123341_() + 0.5, (double)this.pos.m_123342_() + 0.5, (double)this.pos.m_123343_() + 0.5);
            this.fakePlayer.f_36096_ = chestBlockEntity.m_7208_(this.fakePlayer.f_8940_, this.fakePlayer.f_36093_, this.fakePlayer);
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }
   }

   public void attemptClose() {
      try {
         if (this.level.m_7702_(this.pos) instanceof ChestBlockEntity) {
            this.fakePlayer.f_19853_ = this.level;
            this.fakePlayer.m_6034_((double)this.pos.m_123341_() + 0.5, (double)this.pos.m_123342_() + 0.5, (double)this.pos.m_123343_() + 0.5);
            this.fakePlayer.f_36096_ = null;
         }
      } catch (Exception var2) {
         var2.printStackTrace();
      }
   }

   @Override
   public void tick(boolean serverSide) {
      this.duration--;
      if (this.duration <= 0) {
         this.attemptClose();
      }
   }

   @Override
   public boolean isExpired() {
      return this.duration <= 0;
   }
}
