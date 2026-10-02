package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.source.AbstractSourceMachine;
import com.hollingsworth.arsnouveau.api.source.IMultiSourceTargetProvider;
import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.client.util.ColorPos;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class RelaySplitterTile extends RelayTile implements IMultiSourceTargetProvider {
   ArrayList<BlockPos> toList = new ArrayList<>();
   ArrayList<BlockPos> fromList = new ArrayList<>();

   public RelaySplitterTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.RELAY_SPLITTER_TILE, pos, state);
   }

   public RelaySplitterTile(BlockEntityType<?> type, BlockPos pos, BlockState state) {
      super(type, pos, state);
   }

   @Override
   public boolean setTakeFrom(BlockPos pos) {
      return this.closeEnough(pos) && this.fromList.add(pos) && this.updateBlock();
   }

   @Override
   public boolean setSendTo(BlockPos pos) {
      return this.closeEnough(pos) && this.toList.add(pos) && this.updateBlock();
   }

   @Override
   public List<ColorPos> getWandHighlight(List<ColorPos> list) {
      for (BlockPos toPos : this.toList) {
         list.add(ColorPos.centered(toPos, ParticleColor.TO_HIGHLIGHT));
      }

      for (BlockPos fromPos : this.fromList) {
         list.add(ColorPos.centered(fromPos, ParticleColor.FROM_HIGHLIGHT));
      }

      return list;
   }

   @Override
   public void clearPos() {
      this.toList.clear();
      this.fromList.clear();
      this.updateBlock();
   }

   public void processFromList() {
      if (!this.fromList.isEmpty()) {
         ArrayList<BlockPos> stale = new ArrayList<>();
         int ratePer = this.getTransferRate() / this.fromList.size();

         for (BlockPos fromPos : this.fromList) {
            if (this.f_58857_.m_46749_(fromPos)) {
               BlockEntity var6 = this.f_58857_.m_7702_(fromPos);
               if (var6 instanceof AbstractSourceMachine) {
                  AbstractSourceMachine fromTile = (AbstractSourceMachine)var6;
                  if (this.transferSource(fromTile, this, ratePer) > 0) {
                     this.createParticles(fromPos, this.f_58858_);
                  }
               } else {
                  stale.add(fromPos);
               }
            }
         }

         for (BlockPos s : stale) {
            this.fromList.remove(s);
            this.updateBlock();
         }
      }
   }

   public void createParticles(BlockPos from, BlockPos to) {
      ParticleUtil.spawnFollowProjectile(this.f_58857_, from, to);
   }

   public void processToList() {
      if (!this.toList.isEmpty()) {
         ArrayList<BlockPos> stale = new ArrayList<>();
         int ratePer = this.getSource() / this.toList.size();

         for (BlockPos toPos : this.toList) {
            if (this.f_58857_.m_46749_(toPos)) {
               BlockEntity transfer = this.f_58857_.m_7702_(toPos);
               if (transfer instanceof AbstractSourceMachine) {
                  AbstractSourceMachine toTile = (AbstractSourceMachine)transfer;
                  int transferx = this.transferSource(this, toTile, ratePer);
                  if (transferx > 0) {
                     this.createParticles(this.f_58858_, toPos);
                  }
               } else {
                  stale.add(toPos);
               }
            }
         }

         for (BlockPos s : stale) {
            this.toList.remove(s);
            this.updateBlock();
         }
      }
   }

   @Override
   public void tick() {
      if (this.f_58857_.m_46467_() % 20L == 0L && !this.toList.isEmpty() && !this.f_58857_.f_46443_ && !this.disabled) {
         this.processFromList();
         this.processToList();
         this.updateBlock();
      }
   }

   @Override
   public int getTransferRate() {
      return 2500;
   }

   @Override
   public int getMaxSource() {
      return 2500;
   }

   @Override
   public void m_142466_(CompoundTag tag) {
      super.m_142466_(tag);
      this.fromList = new ArrayList<>();
      this.toList = new ArrayList<>();

      for (int counter = 0; NBTUtil.hasBlockPos(tag, "from_" + counter); counter++) {
         BlockPos pos = NBTUtil.getBlockPos(tag, "from_" + counter);
         if (!this.fromList.contains(pos)) {
            this.fromList.add(pos);
         }
      }

      for (int var4 = 0; NBTUtil.hasBlockPos(tag, "to_" + var4); var4++) {
         BlockPos pos = NBTUtil.getBlockPos(tag, "to_" + var4);
         if (!this.toList.contains(pos)) {
            this.toList.add(NBTUtil.getBlockPos(tag, "to_" + var4));
         }
      }
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      int counter = 0;

      for (BlockPos p : this.fromList) {
         NBTUtil.storeBlockPos(tag, "from_" + counter, p);
         counter++;
      }

      counter = 0;

      for (BlockPos p : this.toList) {
         NBTUtil.storeBlockPos(tag, "to_" + counter, p);
         counter++;
      }
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      if (this.toList != null && !this.toList.isEmpty()) {
         tooltip.add(Component.m_237110_("ars_nouveau.relay.one_to", new Object[]{this.toList.size()}));
      } else {
         tooltip.add(Component.m_237115_("ars_nouveau.relay.no_to"));
      }

      if (this.fromList != null && !this.fromList.isEmpty()) {
         tooltip.add(Component.m_237110_("ars_nouveau.relay.one_from", new Object[]{this.fromList.size()}));
      } else {
         tooltip.add(Component.m_237115_("ars_nouveau.relay.no_from"));
      }
   }

   @Override
   public List<BlockPos> getFromList() {
      return this.fromList;
   }

   @Override
   public List<BlockPos> getToList() {
      return this.toList;
   }
}
