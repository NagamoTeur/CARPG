package net.thirdlife.iterrpg.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.thirdlife.iterrpg.init.IterRpgModBlockEntities;

public class SacredSaplingBlockEntity extends BlockEntity {
   public SacredSaplingBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)IterRpgModBlockEntities.SACRED_SAPLING.get(), pos, state);
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.m_195640_(this);
   }

   public CompoundTag m_5995_() {
      return this.m_187480_();
   }
}
