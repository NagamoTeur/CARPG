package com.aizistral.enigmaticlegacy.gui.containers;

import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.LoreFragment;
import com.aizistral.enigmaticlegacy.registries.EnigmaticMenus;
import com.aizistral.enigmaticlegacy.registries.EnigmaticSounds;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoreInscriberContainer extends AbstractContainerMenu {
   protected final ResultContainer craftResultInv = new ResultContainer();
   protected final Container craftSlotsInv = new SimpleContainer(2) {
      public void m_6596_() {
         super.m_6596_();
         LoreInscriberContainer.this.m_6199_(this);
      }
   };
   protected final ContainerLevelAccess worldPosCallable;
   protected final Player player;
   private static final Logger LOGGER = LogManager.getLogger();
   private String unparsedInputField;

   public LoreInscriberContainer(int syncID, Inventory playerInv) {
      this(syncID, playerInv, ContainerLevelAccess.m_39289_(playerInv.f_35978_.f_19853_, playerInv.f_35978_.m_20183_()));
   }

   public LoreInscriberContainer(int syncID, Inventory playerInv, FriendlyByteBuf extras) {
      this(syncID, playerInv, ContainerLevelAccess.m_39289_(playerInv.f_35978_.f_19853_, playerInv.f_35978_.m_20183_()));
   }

   private LoreInscriberContainer(int id, Inventory Inventory, ContainerLevelAccess worldPosCallable) {
      this(EnigmaticMenus.LORE_INSCRIBER_CONTAINER, id, Inventory, worldPosCallable);
   }

   private LoreInscriberContainer(@Nullable MenuType<?> p_i231587_1_, int p_i231587_2_, Inventory playerInv, ContainerLevelAccess p_i231587_4_) {
      super(p_i231587_1_, p_i231587_2_);
      this.worldPosCallable = p_i231587_4_;
      this.player = playerInv.f_35978_;
      this.m_38897_(new Slot(this.craftSlotsInv, 0, 40, 51) {
         public boolean m_5857_(ItemStack stack) {
            return stack.m_41720_() instanceof LoreFragment;
         }

         public int m_6641_() {
            return 1;
         }
      });
      this.m_38897_(new Slot(this.craftResultInv, 2, 116, 51) {
         public boolean m_5857_(ItemStack stack) {
            return false;
         }

         public boolean m_8010_(Player playerIn) {
            return LoreInscriberContainer.this.canCraft(playerIn, this.m_6657_());
         }

         public void m_142406_(Player thePlayer, ItemStack stack) {
            this.m_6654_();
            LoreInscriberContainer.this.claimResult(thePlayer, stack);
         }
      });

      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 9; j++) {
            this.m_38897_(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for (int k = 0; k < 9; k++) {
         if (k == this.player.m_150109_().f_35977_) {
            this.m_38897_(new Slot(playerInv, k, 8 + k * 18, 142) {
               public boolean m_8010_(Player playerIn) {
                  return false;
               }

               public boolean m_5857_(ItemStack stack) {
                  return false;
               }

               public int m_6641_() {
                  return 0;
               }
            });
         } else {
            this.m_38897_(new Slot(playerInv, k, 8 + k * 18, 142));
         }
      }
   }

   public void m_6199_(Container inventoryIn) {
      super.m_6199_(inventoryIn);
      if (inventoryIn == this.craftSlotsInv) {
         this.updateRepairOutput();
      }
   }

   public void m_6877_(Player playerIn) {
      super.m_6877_(playerIn);
      this.m_150411_(playerIn, this.craftSlotsInv);
   }

   public boolean m_6875_(Player playerIn) {
      return true;
   }

   public void m_150399_(int slotId, int dragType, ClickType clickTypeIn, Player player) {
      if (clickTypeIn != ClickType.SWAP || dragType != player.m_150109_().f_35977_ && slotId != 29 + player.m_150109_().f_35977_) {
         super.m_150399_(slotId, dragType, clickTypeIn, player);
      }
   }

   public ItemStack m_7648_(Player playerIn, int index) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = (Slot)this.f_38839_.get(index);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         if (index != 0 && index != 1) {
            if (index >= 2 && index < 38 && !this.m_38903_(itemstack1, 0, 1, false)) {
               return ItemStack.f_41583_;
            }
         } else if (!this.m_38903_(itemstack1, 2, 38, false)) {
            return ItemStack.f_41583_;
         }

         if (itemstack1.m_41619_()) {
            slot.m_5852_(ItemStack.f_41583_);
         } else {
            slot.m_6654_();
         }

         if (itemstack1.m_41613_() == itemstack.m_41613_()) {
            return ItemStack.f_41583_;
         }

         slot.m_142406_(playerIn, itemstack1);
      }

      return itemstack;
   }

   protected boolean canCraft(Player player, boolean hasOutputStack) {
      return hasOutputStack;
   }

   protected ItemStack claimResult(Player player, ItemStack stack) {
      this.craftSlotsInv.m_6836_(0, ItemStack.f_41583_);
      if (!player.f_19853_.f_46443_) {
         player.f_19853_.m_5594_(null, player.m_20183_(), EnigmaticSounds.WRITE, SoundSource.PLAYERS, 1.0F, (float)(0.9F + Math.random() * 0.1F));
      }

      return stack;
   }

   public void updateRepairOutput() {
      ItemStack input = this.craftSlotsInv.m_8020_(0);
      if (input.m_41619_()) {
         this.craftResultInv.m_6836_(0, ItemStack.f_41583_);
      } else {
         ItemStack output = input.m_41777_();
         if (StringUtils.isBlank(this.unparsedInputField)) {
            if (input.m_41788_()) {
               output.m_41787_();
            } else {
               output = ItemStack.f_41583_;
            }
         } else if (!this.unparsedInputField.equals(input.m_41786_().getString())) {
            this.unleashAnvilParser(output);
         } else {
            output = ItemStack.f_41583_;
         }

         this.craftResultInv.m_6836_(0, output);
         this.m_38946_();
      }
   }

   public void updateItemName(String newName) {
      this.unparsedInputField = newName;
      this.updateRepairOutput();
   }

   private void unleashAnvilParser(ItemStack itemstack) {
      ItemLoreHelper.AnvilParser parser = ItemLoreHelper.AnvilParser.parseField(this.unparsedInputField);
      if (!parser.getFormattedString().equals("") || parser.shouldRemoveString()) {
         if (parser.isLoreString()) {
            if (parser.getLoreIndex() != -1) {
               ItemLoreHelper.setLoreString(itemstack, parser.getFormattedString(), parser.getLoreIndex());
            } else {
               ItemLoreHelper.addLoreString(itemstack, parser.getFormattedString());
            }
         } else if (parser.shouldRemoveString()) {
            ItemLoreHelper.removeLoreString(itemstack, parser.getLoreIndex());
         } else {
            ItemLoreHelper.setDisplayName(itemstack, parser.getFormattedString());
         }
      }
   }
}
