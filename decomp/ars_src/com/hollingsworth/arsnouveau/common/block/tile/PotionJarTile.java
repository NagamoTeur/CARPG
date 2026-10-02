package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.item.IWandable;
import com.hollingsworth.arsnouveau.api.potion.PotionData;
import com.hollingsworth.arsnouveau.common.block.SourceJar;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class PotionJarTile extends ModdedTile implements ITooltipProvider, IWandable {
   public boolean isLocked;
   private PotionData data = new PotionData();
   int currentFill;

   public PotionJarTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
      super(tileEntityTypeIn, pos, state);
   }

   public PotionJarTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.POTION_JAR_TYPE, pos, state);
   }

   @Override
   public void onWanded(Player playerEntity) {
      if (!this.isLocked) {
         this.isLocked = true;
         playerEntity.m_213846_(Component.m_237115_("ars_nouveau.locked"));
      } else {
         this.isLocked = false;
         playerEntity.m_213846_(Component.m_237115_("ars_nouveau.unlocked"));
      }

      this.updateBlock();
   }

   @Override
   public boolean updateBlock() {
      BlockState state = this.f_58857_.m_8055_(this.f_58858_);
      int fillState = 0;
      if (this.getAmount() > 0 && this.getAmount() < 1000) {
         fillState = 1;
      } else if (this.getAmount() != 0) {
         fillState = this.getAmount() / 1000 + 1;
      }

      this.f_58857_.m_7731_(this.f_58858_, (BlockState)state.m_61124_(SourceJar.fill, fillState), 3);
      return super.updateBlock();
   }

   public void handleUpdateTag(CompoundTag tag) {
      super.handleUpdateTag(tag);
      this.f_58857_.m_7260_(this.f_58858_, this.f_58857_.m_8055_(this.f_58858_), this.f_58857_.m_8055_(this.f_58858_), 8);
   }

   @NotNull
   public PotionData getData() {
      return this.data;
   }

   public int getColor() {
      return this.data.getPotion() == null ? 16253176 : PotionUtils.m_43564_(this.data.fullEffects());
   }

   public boolean canAccept(PotionData otherData, int amount) {
      return otherData != null && otherData.getPotion() != Potions.f_43598_
         ? !this.isLocked && this.getAmount() <= 0 || amount <= this.getMaxFill() - this.getAmount() && otherData.areSameEffects(this.data)
         : false;
   }

   public void add(PotionData other, int amount) {
      if (this.currentFill == 0) {
         if (!this.data.equals(other) || this.data.getPotion() == Potions.f_43598_) {
            this.data = other;
         }

         this.currentFill += amount;
      } else {
         this.currentFill = Math.min(this.getAmount() + amount, this.getMaxFill());
      }

      this.currentFill = Math.min(this.currentFill, this.getMaxFill());
      this.updateBlock();
   }

   public void remove(int amount) {
      this.currentFill = Math.max(this.currentFill - amount, 0);
      if (this.currentFill == 0 && !this.isLocked) {
         this.data = new PotionData();
      }

      this.updateBlock();
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      this.data.appendHoverText(tooltip);
      tooltip.add(Component.m_237110_("ars_nouveau.source_jar.fullness", new Object[]{this.getAmount() * 100 / this.getMaxFill()}));
      if (this.isLocked) {
         tooltip.add(Component.m_237115_("ars_nouveau.locked"));
      }
   }

   public void m_142466_(CompoundTag tag) {
      super.m_142466_(tag);
      if (tag.m_128441_("potionData")) {
         this.data = PotionData.fromTag(tag.m_128469_("potionData"));
      }

      this.isLocked = tag.m_128471_("locked");
      this.currentFill = tag.m_128451_("currentFill");
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      tag.m_128365_("potionData", this.data.toTag());
      tag.m_128379_("locked", this.isLocked);
      tag.m_128405_("currentFill", this.currentFill);
      Set<Potion> potionSet = this.data.getIncludedPotions();
      List<String> potionNames = new ArrayList<>(potionSet.stream().map(potion -> Registry.f_122828_.m_7981_(potion).toString()).toList());
      potionNames.sort(String::compareTo);
      tag.m_128359_("potionNames", String.join(",", potionNames));
   }

   public int getMaxFill() {
      return 10000;
   }

   public int getAmount() {
      return this.currentFill;
   }
}
