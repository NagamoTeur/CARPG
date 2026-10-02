package com.github.L_Ender.cataclysm.inventory;

import com.github.L_Ender.cataclysm.entity.Pet.Netherite_Ministrosity_Entity;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MinistrostiyMenu extends AbstractContainerMenu {
   private final Container horseContainer;
   private final Netherite_Ministrosity_Entity horse;

   public MinistrostiyMenu(int p_39656_, Inventory p_39657_, Container p_39658_, Netherite_Ministrosity_Entity p_39659_) {
      super((MenuType)null, p_39656_);
      this.horseContainer = p_39658_;
      this.horse = p_39659_;
      int i = 3;
      p_39658_.m_5856_(p_39657_.f_35978_);
      int j = -18;

      for (int k = 0; k < 3; k++) {
         for (int l = 0; l < p_39659_.getInventoryColumns(); l++) {
            this.m_38897_(new MinistrositySlot(p_39658_, 2 + l + k * p_39659_.getInventoryColumns(), 71 + l * 18, 18 + k * 18));
         }
      }

      for (int i1 = 0; i1 < 3; i1++) {
         for (int k1 = 0; k1 < 9; k1++) {
            this.m_38897_(new Slot(p_39657_, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + -18));
         }
      }

      for (int j1 = 0; j1 < 9; j1++) {
         this.m_38897_(new Slot(p_39657_, j1, 8 + j1 * 18, 142));
      }
   }

   public boolean m_6875_(Player p_39661_) {
      return !this.horse.hasInventoryChanged(this.horseContainer)
         && this.horseContainer.m_6542_(p_39661_)
         && this.horse.m_6084_()
         && this.horse.m_20270_(p_39661_) < 8.0F;
   }

   public ItemStack m_7648_(Player p_40199_, int p_40200_) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = (Slot)this.f_38839_.get(p_40200_);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         if (p_40200_ < this.horseContainer.m_6643_()) {
            if (!this.m_38903_(itemstack1, this.horseContainer.m_6643_(), this.f_38839_.size(), true)) {
               return ItemStack.f_41583_;
            }
         } else if (!this.m_38903_(itemstack1, 0, this.horseContainer.m_6643_(), false)) {
            return ItemStack.f_41583_;
         }

         if (itemstack1.m_41619_()) {
            slot.m_5852_(ItemStack.f_41583_);
         } else {
            slot.m_6654_();
         }
      }

      return itemstack;
   }

   public boolean mayPlace(ItemStack p_40231_) {
      return true;
   }

   public void m_6877_(Player p_39663_) {
      super.m_6877_(p_39663_);
      this.horseContainer.m_5785_(p_39663_);
      if (this.horse != null) {
         this.horse.setAttackState(5);
      }
   }
}
