package shadows.apotheosis.ench.table;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags.Items;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ApothEnchantTile extends EnchantmentTableBlockEntity {
   protected ItemStackHandler inv = new ItemStackHandler(1) {
      public boolean isItemValid(int slot, ItemStack stack) {
         return stack.m_204117_(Items.ENCHANTING_FUELS);
      }
   };
   LazyOptional<IItemHandler> invCap = LazyOptional.of(() -> this.inv);

   public ApothEnchantTile(BlockPos pos, BlockState state) {
      super(pos, state);
   }

   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      tag.m_128365_("inventory", this.inv.serializeNBT());
   }

   public void m_142466_(CompoundTag tag) {
      super.m_142466_(tag);
      this.inv.deserializeNBT(tag.m_128469_("inventory"));
   }

   public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
      return cap == ForgeCapabilities.ITEM_HANDLER ? this.invCap.cast() : super.getCapability(cap, side);
   }

   public void invalidateCaps() {
      super.invalidateCaps();
      this.invCap.invalidate();
   }

   public void reviveCaps() {
      super.reviveCaps();
      this.invCap = LazyOptional.of(() -> this.inv);
   }
}
