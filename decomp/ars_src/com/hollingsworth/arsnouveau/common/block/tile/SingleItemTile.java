package com.hollingsworth.arsnouveau.common.block.tile;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;

public class SingleItemTile extends ModdedTile implements Container {
   private final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> new InvWrapper(this));
   protected ItemStack stack = ItemStack.f_41583_;
   public ItemEntity renderEntity;

   public SingleItemTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
      super(tileEntityTypeIn, pos, state);
   }

   public int m_6643_() {
      return 1;
   }

   public boolean m_7983_() {
      return this.stack.m_41619_();
   }

   public ItemStack m_8020_(int pSlot) {
      return this.stack;
   }

   public ItemStack m_7407_(int pSlot, int pAmount) {
      ItemStack copyStack = this.stack.m_41777_().m_41620_(pAmount);
      this.stack.m_41774_(pAmount);
      this.updateBlock();
      return copyStack;
   }

   public ItemStack m_8016_(int pSlot) {
      ItemStack stack = this.stack.m_41777_();
      this.stack = ItemStack.f_41583_;
      this.updateBlock();
      return stack;
   }

   public void m_6836_(int pSlot, ItemStack pStack) {
      this.stack = pStack;
      this.updateBlock();
   }

   public boolean m_7013_(int pIndex, ItemStack pStack) {
      return this.stack.m_41619_();
   }

   public boolean m_6542_(Player pPlayer) {
      return false;
   }

   public int m_6893_() {
      return 1;
   }

   public void m_6211_() {
      this.stack = ItemStack.f_41583_;
      this.updateBlock();
   }

   public ItemStack getStack() {
      return this.stack;
   }

   public void setStack(ItemStack otherStack) {
      this.stack = otherStack;
      this.updateBlock();
   }

   @NotNull
   public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
      return cap == ForgeCapabilities.ITEM_HANDLER ? this.itemHandler.cast() : super.getCapability(cap, side);
   }

   public void invalidateCaps() {
      this.itemHandler.invalidate();
      super.invalidateCaps();
   }

   public void m_142466_(CompoundTag compound) {
      super.m_142466_(compound);
      this.stack = ItemStack.m_41712_((CompoundTag)compound.m_128423_("itemStack"));
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      if (this.stack != null) {
         CompoundTag stackTag = new CompoundTag();
         this.stack.m_41739_(stackTag);
         tag.m_128365_("itemStack", stackTag);
      }
   }
}
