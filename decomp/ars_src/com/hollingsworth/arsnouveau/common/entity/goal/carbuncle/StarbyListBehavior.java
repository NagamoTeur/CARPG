package com.hollingsworth.arsnouveau.common.entity.goal.carbuncle;

import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.util.ColorPos;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class StarbyListBehavior extends StarbyBehavior {
   public List<BlockPos> FROM_LIST = new ArrayList<>();
   public List<BlockPos> TO_LIST = new ArrayList<>();

   public StarbyListBehavior(Starbuncle entity, CompoundTag tag) {
      super(entity, tag);

      for (int counter = 0; NBTUtil.hasBlockPos(tag, "from_" + counter); counter++) {
         BlockPos pos = NBTUtil.getBlockPos(tag, "from_" + counter);
         if (!this.FROM_LIST.contains(pos)) {
            this.FROM_LIST.add(pos);
         }
      }

      for (int var5 = 0; NBTUtil.hasBlockPos(tag, "to_" + var5); var5++) {
         BlockPos pos = NBTUtil.getBlockPos(tag, "to_" + var5);
         if (!this.TO_LIST.contains(pos)) {
            this.TO_LIST.add(pos);
         }
      }
   }

   @Override
   public boolean clearOrRemove() {
      return this.FROM_LIST.isEmpty() && this.TO_LIST.isEmpty();
   }

   @Override
   public void onWanded(Player playerEntity) {
      super.onWanded(playerEntity);
      this.FROM_LIST = new ArrayList<>();
      this.TO_LIST = new ArrayList<>();
      PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.connections.cleared"));
      this.syncTag();
   }

   @Override
   public List<ColorPos> getWandHighlight(List<ColorPos> list) {
      for (BlockPos toPos : this.TO_LIST) {
         list.add(ColorPos.centered(toPos, ParticleColor.TO_HIGHLIGHT));
      }

      for (BlockPos fromPos : this.FROM_LIST) {
         list.add(ColorPos.centered(fromPos, ParticleColor.FROM_HIGHLIGHT));
      }

      return list;
   }

   public void addFromPos(BlockPos fromPos) {
      if (!this.FROM_LIST.contains(fromPos)) {
         this.FROM_LIST.add(fromPos.m_7949_());
         this.syncTag();
      }
   }

   public void addToPos(BlockPos toPos) {
      if (!this.TO_LIST.contains(toPos)) {
         this.TO_LIST.add(toPos.m_7949_());
         this.syncTag();
      }
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      int counter = 0;

      for (BlockPos p : this.FROM_LIST) {
         NBTUtil.storeBlockPos(tag, "from_" + counter, p);
         counter++;
      }

      counter = 0;

      for (BlockPos p : this.TO_LIST) {
         NBTUtil.storeBlockPos(tag, "to_" + counter, p);
         counter++;
      }

      return super.toTag(tag);
   }

   @Override
   protected ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", "starby_list");
   }
}
