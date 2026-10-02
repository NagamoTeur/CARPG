package com.hollingsworth.arsnouveau.common.block.tile;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ModdedTile extends BlockEntity {
   public ModdedTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
      super(tileEntityTypeIn, pos, state);
   }

   @Nullable
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.m_195640_(this);
   }

   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
   }

   public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
      super.onDataPacket(net, pkt);
      this.handleUpdateTag(pkt.m_131708_() == null ? new CompoundTag() : pkt.m_131708_());
   }

   public boolean updateBlock() {
      if (this.f_58857_ != null) {
         BlockState state = this.f_58857_.m_8055_(this.f_58858_);
         this.f_58857_.m_7260_(this.f_58858_, state, state, 3);
         this.m_6596_();
         return true;
      } else {
         return false;
      }
   }

   public CompoundTag m_5995_() {
      CompoundTag tag = new CompoundTag();
      this.m_183515_(tag);
      return tag;
   }

   public double getX() {
      return (double)this.f_58858_.m_123341_();
   }

   public double getY() {
      return (double)this.f_58858_.m_123342_();
   }

   public double getZ() {
      return (double)this.f_58858_.m_123343_();
   }
}
