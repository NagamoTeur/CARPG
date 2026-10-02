package com.aqutheseal.celestisynth.client.gui.celestialcrafting;

import com.aqutheseal.celestisynth.common.events.CSRecipeBookSetupEvents;
import com.aqutheseal.celestisynth.common.recipe.celestialcrafting.CelestialCraftingRecipe;
import com.aqutheseal.celestisynth.common.registry.CSBlocks;
import com.aqutheseal.celestisynth.common.registry.CSMenuTypes;
import com.aqutheseal.celestisynth.common.registry.CSRecipeTypes;
import java.util.Optional;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class CelestialCraftingMenu extends RecipeBookMenu<CraftingContainer> {
   private final ContainerLevelAccess access;
   private final Player player;
   private final CraftingContainer craftSlots = new CraftingContainer(this, 3, 3);
   private final ResultContainer resultSlots = new ResultContainer();

   public CelestialCraftingMenu(int pContainerId, Inventory pPlayerInventory) {
      this(pContainerId, pPlayerInventory, ContainerLevelAccess.f_39287_);
   }

   public CelestialCraftingMenu(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
      super((MenuType)CSMenuTypes.CELESTIAL_CRAFTING.get(), pContainerId);
      this.access = pAccess;
      this.player = pPlayerInventory.f_35978_;
      this.m_38897_(new ResultSlot(pPlayerInventory.f_35978_, this.craftSlots, this.resultSlots, 0, 124, 35) {
         public void m_142406_(Player pPlayer, ItemStack pStack) {
            if (pPlayer.f_19853_.m_5776_()) {
               CelestialCraftingMenu.this.addCraftedEffect(pPlayer);
            }

            super.m_142406_(pPlayer, pStack);
         }
      });

      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            this.m_38897_(new Slot(this.craftSlots, j + i * 3, 30 + j * 18, 17 + i * 18));
         }
      }

      for (int k = 0; k < 3; k++) {
         for (int i1 = 0; i1 < 9; i1++) {
            this.m_38897_(new Slot(pPlayerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
         }
      }

      for (int l = 0; l < 9; l++) {
         this.m_38897_(new Slot(pPlayerInventory, l, 8 + l * 18, 142));
      }
   }

   protected static void slotChangedCraftingGrid(
      AbstractContainerMenu pMenu, Level pLevel, Player pPlayer, CraftingContainer pContainer, ResultContainer pResult
   ) {
      if (!pLevel.f_46443_) {
         ServerPlayer craftingServerPlayer = (ServerPlayer)pPlayer;
         ItemStack resultStack = ItemStack.f_41583_;
         Optional<CelestialCraftingRecipe> potentialCelestialRecipe = pLevel.m_7654_()
            .m_129894_()
            .m_44015_((RecipeType)CSRecipeTypes.CELESTIAL_CRAFTING_TYPE.get(), pContainer, pLevel);
         if (potentialCelestialRecipe.isPresent()) {
            CelestialCraftingRecipe curRecipe = potentialCelestialRecipe.get();
            if (pResult.m_40135_(pLevel, craftingServerPlayer, curRecipe)) {
               resultStack = curRecipe.assemble(pContainer);
            }
         }

         pResult.m_6836_(0, resultStack);
         pMenu.m_150404_(0, resultStack);
         craftingServerPlayer.f_8906_.m_9829_(new ClientboundContainerSetSlotPacket(pMenu.f_38840_, pMenu.m_182425_(), 0, resultStack));
      }
   }

   public void addCraftedEffect(Player player) {
      player.m_5496_(SoundEvents.f_12275_, 1.0F, 0.1F);
   }

   public void m_6199_(Container pInventory) {
      this.access.m_39292_((curLevel, targetPos) -> slotChangedCraftingGrid(this, curLevel, this.player, this.craftSlots, this.resultSlots));
   }

   public void m_5816_(StackedContents pItemHelper) {
      this.craftSlots.m_5809_(pItemHelper);
   }

   public void m_6650_() {
      this.craftSlots.m_6211_();
      this.resultSlots.m_6211_();
   }

   public boolean m_6032_(Recipe<? super CraftingContainer> pRecipe) {
      return pRecipe.m_5818_(this.craftSlots, this.player.f_19853_);
   }

   public void m_6877_(Player pPlayer) {
      super.m_6877_(pPlayer);
      this.access.m_39292_((curLevel, targetPos) -> this.m_150411_(pPlayer, this.craftSlots));
   }

   public boolean m_6875_(Player pPlayer) {
      return m_38889_(this.access, pPlayer, (Block)CSBlocks.CELESTIAL_CRAFTING_TABLE.get());
   }

   public ItemStack m_7648_(Player pPlayer, int pIndex) {
      ItemStack targetStack = ItemStack.f_41583_;
      Slot targetSlot = (Slot)this.f_38839_.get(pIndex);
      if (targetSlot != null && targetSlot.m_6657_()) {
         ItemStack targetSlotItem = targetSlot.m_7993_();
         targetStack = targetSlotItem.m_41777_();
         if (pIndex == 0) {
            this.access.m_39292_((curLevel, targetPos) -> targetSlotItem.m_41720_().m_7836_(targetSlotItem, curLevel, pPlayer));
            if (!this.m_38903_(targetSlotItem, 10, 46, true)) {
               return ItemStack.f_41583_;
            }

            targetSlot.m_40234_(targetSlotItem, targetStack);
         } else if (pIndex >= 10 && pIndex < 46) {
            if (!this.m_38903_(targetSlotItem, 1, 10, false)) {
               if (pIndex < 37 && !this.m_38903_(targetSlotItem, 37, 46, false)) {
                  return ItemStack.f_41583_;
               }

               if (!this.m_38903_(targetSlotItem, 10, 37, false)) {
                  return ItemStack.f_41583_;
               }
            }
         } else if (!this.m_38903_(targetSlotItem, 10, 46, false)) {
            return ItemStack.f_41583_;
         }

         if (targetSlotItem.m_41619_()) {
            targetSlot.m_5852_(ItemStack.f_41583_);
         } else {
            targetSlot.m_6654_();
         }

         if (targetSlotItem.m_41613_() == targetStack.m_41613_()) {
            return ItemStack.f_41583_;
         }

         targetSlot.m_142406_(pPlayer, targetSlotItem);
         if (pIndex == 0) {
            pPlayer.m_36176_(targetSlotItem, false);
         }
      }

      return targetStack;
   }

   public boolean m_5882_(ItemStack pStack, Slot pSlot) {
      return pSlot.f_40218_ != this.resultSlots && super.m_5882_(pStack, pSlot);
   }

   public int m_6636_() {
      return 0;
   }

   public int m_6635_() {
      return this.craftSlots.m_39347_();
   }

   public int m_6656_() {
      return this.craftSlots.m_39346_();
   }

   public int m_6653_() {
      return 10;
   }

   public RecipeBookType m_5867_() {
      return CSRecipeBookSetupEvents.CELESTIAL_CRAFTING;
   }

   public boolean m_142157_(int pSlotIndex) {
      return pSlotIndex != this.m_6636_();
   }
}
