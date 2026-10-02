package shadows.apotheosis.adventure.affix.salvaging;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import shadows.apotheosis.Apoth;
import shadows.placebo.cap.InternalItemHandler;

public class SalvagingTableTile extends BlockEntity {
   protected final InternalItemHandler output = new InternalItemHandler(6);
   protected final LazyOptional<SalvagingTableTile.SalvagingItemHandler> itemHandler = LazyOptional.of(() -> new SalvagingTableTile.SalvagingItemHandler());

   public SalvagingTableTile(BlockPos pPos, BlockState pBlockState) {
      super((BlockEntityType)Apoth.Tiles.SALVAGING_TABLE.get(), pPos, pBlockState);
   }

   public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
      return cap == ForgeCapabilities.ITEM_HANDLER ? this.itemHandler.cast() : super.getCapability(cap, side);
   }

   protected void m_183515_(CompoundTag tag) {
      tag.m_128365_("output", this.output.serializeNBT());
      super.m_183515_(tag);
   }

   public void m_142466_(CompoundTag tag) {
      if (tag.m_128441_("output")) {
         this.output.deserializeNBT(tag.m_128469_("output"));
      }

      super.m_142466_(tag);
   }

   protected class SalvagingItemHandler implements IItemHandler {
      public int getSlots() {
         return 1 + SalvagingTableTile.this.output.getSlots();
      }

      public ItemStack getStackInSlot(int slot) {
         return slot == 0 ? ItemStack.f_41583_ : SalvagingTableTile.this.output.getStackInSlot(slot - 1);
      }

      public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
         if (slot != 0) {
            return stack;
         } else {
            List<ItemStack> outputs = SalvagingMenu.getBestPossibleSalvageResults(SalvagingTableTile.this.f_58857_, stack);
            if (outputs.isEmpty()) {
               return stack;
            } else {
               IntSet skipSlots = new IntOpenHashSet();

               for (ItemStack out : outputs) {
                  for (int i = 0; i < 6; i++) {
                     if (!skipSlots.contains(i)) {
                        int size = out.m_41613_();
                        out = SalvagingTableTile.this.output.insertItem(i, out, true);
                        if (size != out.m_41613_()) {
                           skipSlots.add(i);
                        }

                        if (out.m_41619_()) {
                           break;
                        }
                     }
                  }

                  if (!out.m_41619_()) {
                     return stack;
                  }
               }

               if (!simulate) {
                  for (ItemStack out : outputs) {
                     for (int ix = 0; ix < 6; ix++) {
                        out = SalvagingTableTile.this.output.insertItem(ix, out, false);
                        if (out.m_41619_()) {
                           break;
                        }
                     }

                     if (!out.m_41619_()) {
                        return stack;
                     }
                  }
               }

               return ItemStack.f_41583_;
            }
         }
      }

      public ItemStack extractItem(int slot, int amount, boolean simulate) {
         return slot == 0 ? ItemStack.f_41583_ : SalvagingTableTile.this.output.extractItem(slot - 1, amount, simulate);
      }

      public int getSlotLimit(int slot) {
         return slot == 0 ? 1 : SalvagingTableTile.this.output.getSlotLimit(slot - 1);
      }

      public boolean isItemValid(int slot, ItemStack stack) {
         return slot == 0 ? SalvagingMenu.findMatch(SalvagingTableTile.this.f_58857_, stack) != null : false;
      }
   }
}
