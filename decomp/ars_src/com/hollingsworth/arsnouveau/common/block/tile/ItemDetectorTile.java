package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.item.IWandable;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.util.ColorPos;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.common.items.ItemScroll;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.Nullable;

public class ItemDetectorTile extends ModdedTile implements ITickable, IWandable, ITooltipProvider {
   public BlockPos connectedPos;
   public boolean isPowered;
   public int neededCount;
   public ItemStack filterStack = ItemStack.f_41583_;
   public boolean inverted;

   public ItemDetectorTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
      super(tileEntityTypeIn, pos, state);
   }

   public ItemDetectorTile(BlockPos pos, BlockState state) {
      super((BlockEntityType<?>)BlockRegistry.ITEM_DETECTOR_TILE.get(), pos, state);
   }

   @Override
   public void tick() {
      if (!this.f_58857_.f_46443_ && this.connectedPos != null && this.f_58857_.m_46467_() % 20L == 0L) {
         BlockEntity tile = this.f_58857_.m_7702_(this.connectedPos);
         if (tile != null) {
            IItemHandler handler = (IItemHandler)tile.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
            if (handler != null) {
               int found = 0;

               for (int i = 0; i < handler.getSlots(); i++) {
                  ItemStack ghostStack = handler.getStackInSlot(i);
                  ItemStack extractedStack = handler.extractItem(i, ghostStack.m_41613_(), true);
                  if (ghostStack.m_41613_() > extractedStack.m_41613_() && extractedStack.m_41741_() == extractedStack.m_41613_()) {
                     found += this.getCountForStack(ghostStack);
                  } else {
                     found += this.getCountForStack(extractedStack);
                  }

                  if (found > this.neededCount) {
                     this.setReachedCount(true);
                     return;
                  }
               }

               this.setReachedCount(false);
            }
         }
      }
   }

   public int getCountForStack(ItemStack stack) {
      if (this.filterStack.m_41720_() instanceof ItemScroll scroll) {
         ItemScroll.SortPref pref = scroll.getSortPref(stack, this.filterStack, new CombinedInvWrapper(new IItemHandlerModifiable[0]));
         if (pref != ItemScroll.SortPref.INVALID) {
            return stack.m_41613_();
         }
      }

      if (ItemStack.m_41746_(stack, this.filterStack) && ItemStack.m_41658_(stack, this.filterStack)) {
         return this.filterStack.m_41619_() && stack.m_41619_() ? 1 : stack.m_41613_();
      } else {
         return 0;
      }
   }

   public void setReachedCount(boolean reachedCount) {
      boolean old = this.isPowered;
      this.isPowered = reachedCount;
      if (old != this.isPowered) {
         this.updateBlock();
         this.f_58857_.m_46672_(this.f_58858_, BlockRegistry.ITEM_DETECTOR.get());
      }
   }

   public boolean getPoweredState() {
      return this.inverted != this.isPowered;
   }

   public void addCount(int count) {
      this.neededCount += count;
      if (this.neededCount < 0) {
         this.neededCount = 0;
      }

      this.updateBlock();
   }

   public void setFilterStack(ItemStack stack) {
      this.filterStack = stack.m_41777_();
      this.updateBlock();
   }

   @Override
   public void onFinishedConnectionLast(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
      if (storedPos != null) {
         if (this.f_58857_.m_7702_(storedPos) == null || !this.f_58857_.m_7702_(storedPos).getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()) {
            return;
         }

         if (BlockUtil.distanceFrom(storedPos, this.f_58858_) > 30.0) {
            PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.storage.inv_too_far"));
            return;
         }

         this.connectedPos = storedPos.m_7949_();
         PortUtil.sendMessage(
            playerEntity,
            Component.m_237110_("ars_nouveau.item_detector.connected", new Object[]{storedPos.m_123341_(), storedPos.m_123342_(), storedPos.m_123343_()})
         );
         this.updateBlock();
      }
   }

   @Override
   public void onWanded(Player playerEntity) {
      this.inverted = !this.inverted;
      PortUtil.sendMessage(playerEntity, Component.m_237110_("ars_nouveau.item_detector.inverted", new Object[]{this.inverted}));
      this.updateBlock();
      this.f_58857_.m_46672_(this.f_58858_, BlockRegistry.ITEM_DETECTOR.get());
   }

   @Override
   public List<ColorPos> getWandHighlight(List<ColorPos> list) {
      if (this.connectedPos != null) {
         list.add(ColorPos.centered(this.connectedPos, ParticleColor.FROM_HIGHLIGHT));
      }

      return list;
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      if (this.connectedPos != null) {
         tag.m_128356_("connectedPos", this.connectedPos.m_121878_());
      }

      tag.m_128405_("neededCount", this.neededCount);
      if (!this.filterStack.m_41619_()) {
         tag.m_128365_("filterStack", this.filterStack.m_41739_(new CompoundTag()));
      }

      tag.m_128379_("isPowered", this.isPowered);
      tag.m_128379_("inverted", this.inverted);
   }

   public void m_142466_(CompoundTag pTag) {
      super.m_142466_(pTag);
      if (pTag.m_128441_("connectedPos")) {
         this.connectedPos = BlockPos.m_122022_(pTag.m_128454_("connectedPos"));
      }

      this.neededCount = pTag.m_128451_("neededCount");
      if (pTag.m_128441_("filterStack")) {
         this.filterStack = ItemStack.m_41712_(pTag.m_128469_("filterStack"));
      }

      this.isPowered = pTag.m_128471_("isPowered");
      this.inverted = pTag.m_128471_("inverted");
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      tooltip.add(Component.m_237110_("ars_nouveau.item_detector.count", new Object[]{(this.inverted ? "< " : "> ") + this.neededCount}));
      if (this.filterStack.m_41720_() instanceof ItemScroll && this.filterStack.m_41782_()) {
         ItemScroll.ItemScrollData scrollData = new ItemScroll.ItemScrollData(this.filterStack);

         for (ItemStack s : scrollData.getItems()) {
            tooltip.add(Component.m_237113_(s.m_41786_().getString()).m_130940_(ChatFormatting.GOLD));
         }
      } else {
         tooltip.add(
            Component.m_237110_("ars_nouveau.item_detector.item", new Object[]{this.filterStack.m_41786_().getString()}).m_130940_(ChatFormatting.GOLD)
         );
      }

      tooltip.add(Component.m_237110_("ars_nouveau.item_detector.powered", new Object[]{this.getPoweredState()}));
   }
}
