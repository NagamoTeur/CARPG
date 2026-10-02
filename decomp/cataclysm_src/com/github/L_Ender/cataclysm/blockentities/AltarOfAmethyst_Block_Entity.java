package com.github.L_Ender.cataclysm.blockentities;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.crafting.AltarOfAmethystRecipe;
import com.github.L_Ender.cataclysm.init.ModRecipeTypes;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import com.github.L_Ender.cataclysm.message.MessageUpdateblockentity;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Clearable;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.RecipeManager.CachedCheck;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AltarOfAmethyst_Block_Entity extends BlockEntity implements Clearable {
   private static final int NUM_SLOTS = 1;
   private final NonNullList<ItemStack> items = NonNullList.m_122780_(1, ItemStack.f_41583_);
   public int blessingProgress;
   public int tickCounts;
   public boolean brightThisTick = false;
   private final CachedCheck<Container, AltarOfAmethystRecipe> quickCheck = RecipeManager.m_220267_((RecipeType)ModRecipeTypes.AMETHYST_BLESS.get());

   public AltarOfAmethyst_Block_Entity(BlockPos p_155301_, BlockState p_155302_) {
      super((BlockEntityType)ModTileentites.ALTAR_OF_AMETHYST.get(), p_155301_, p_155302_);
   }

   public static void cookTick(Level p_155307_, BlockPos p_155308_, BlockState p_155309_, AltarOfAmethyst_Block_Entity p_155310_) {
      p_155310_.brightThisTick = false;
      p_155310_.tickCounts++;
      ItemStack itemstack = (ItemStack)p_155310_.items.get(0);
      if (!itemstack.m_41619_()) {
         Container container = new SimpleContainer(new ItemStack[]{itemstack});
         Optional<AltarOfAmethystRecipe> ingredient = p_155310_.quickCheck.m_213657_(container, p_155307_);
         ItemStack finale = ingredient.<ItemStack>map(p_270054_ -> p_270054_.getResult().m_41777_()).orElse(itemstack);
         if (ingredient.isPresent()) {
            p_155310_.brightThisTick = true;
            if (p_155310_.blessingProgress >= ingredient.get().getTime()) {
               ItemStack current = p_155310_.getItem(0).m_41777_();
               current.m_41774_(1);
               if (!current.m_41619_()) {
                  ItemEntity itemEntity = new ItemEntity(
                     p_155307_,
                     (double)((float)p_155308_.m_123341_() + 0.5F),
                     (double)((float)p_155308_.m_123342_() + 0.5F),
                     (double)((float)p_155308_.m_123343_() + 0.5F),
                     current
                  );
                  if (!p_155307_.f_46443_) {
                     p_155307_.m_7967_(itemEntity);
                  }
               }

               p_155310_.setItem(0, finale);
            }
         }
      }

      if (!p_155310_.brightThisTick) {
         p_155310_.blessingProgress = 0;
      } else {
         p_155310_.blessingProgress++;
      }
   }

   public int getContainerSize() {
      return this.items.size();
   }

   public ItemStack getItem(int index) {
      return (ItemStack)this.items.get(index);
   }

   public void setItem(int index, ItemStack stack) {
      this.items.set(index, stack);
      if (!stack.m_41619_() && stack.m_41613_() > this.getMaxStackSize()) {
         stack.m_41764_(this.getMaxStackSize());
      }

      this.m_183515_(this.m_5995_());
      if (!this.f_58857_.f_46443_) {
         Cataclysm.sendMSGToAll(new MessageUpdateblockentity(this.m_58899_().m_121878_(), (ItemStack)this.items.get(0)));
      }
   }

   public int getMaxStackSize() {
      return 1;
   }

   public NonNullList<ItemStack> getItems() {
      return this.items;
   }

   public void m_142466_(CompoundTag p_155312_) {
      super.m_142466_(p_155312_);
      this.items.clear();
      ContainerHelper.m_18980_(p_155312_, this.items);
      if (p_155312_.m_128425_("blessingProgress", 11)) {
         this.blessingProgress = p_155312_.m_128451_("blessingProgress");
      }
   }

   protected void m_183515_(CompoundTag p_187486_) {
      super.m_183515_(p_187486_);
      ContainerHelper.m_18976_(p_187486_, this.items, true);
      p_187486_.m_128405_("blessingProgress", this.blessingProgress);
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.m_195640_(this);
   }

   public CompoundTag m_5995_() {
      CompoundTag compoundtag = new CompoundTag();
      ContainerHelper.m_18976_(compoundtag, this.items, true);
      return compoundtag;
   }

   public Optional<AltarOfAmethystRecipe> getCookableRecipe(ItemStack p_59052_) {
      return this.items.stream().noneMatch(ItemStack::m_41619_)
         ? Optional.empty()
         : this.quickCheck.m_213657_(new SimpleContainer(new ItemStack[]{p_59052_}), this.f_58857_);
   }

   public void m_6211_() {
      this.items.clear();
   }
}
