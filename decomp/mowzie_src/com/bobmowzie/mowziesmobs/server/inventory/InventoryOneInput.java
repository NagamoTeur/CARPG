package com.bobmowzie.mowziesmobs.server.inventory;

import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public abstract class InventoryOneInput implements Container {
   protected final MowzieEntity tradingEntity;
   protected ItemStack input = ItemStack.f_41583_;
   protected List<InventoryOneInput.ChangeListener> listeners;

   public InventoryOneInput(MowzieEntity tradingEntity) {
      this.tradingEntity = tradingEntity;
   }

   public void addListener(InventoryOneInput.ChangeListener listener) {
      if (this.listeners == null) {
         this.listeners = new ArrayList<>();
      }

      this.listeners.add(listener);
   }

   public int m_6643_() {
      return 1;
   }

   public void m_5856_(Player player) {
   }

   public void m_5785_(Player player) {
   }

   public ItemStack m_8020_(int index) {
      return index == 0 ? this.input : ItemStack.f_41583_;
   }

   public ItemStack m_7407_(int index, int count) {
      ItemStack stack;
      if (index == 0 && this.input != ItemStack.f_41583_ && count > 0) {
         ItemStack split = this.input.m_41620_(count);
         if (this.input.m_41613_() == 0) {
            this.input = ItemStack.f_41583_;
         }

         stack = split;
         this.m_6596_();
      } else {
         stack = ItemStack.f_41583_;
      }

      return stack;
   }

   public ItemStack m_8016_(int index) {
      if (index != 0) {
         return ItemStack.f_41583_;
      } else {
         ItemStack s = this.input;
         this.input = ItemStack.f_41583_;
         this.m_6596_();
         return s;
      }
   }

   public void m_6836_(int index, ItemStack stack) {
      if (index == 0) {
         this.input = stack;
         if (stack != ItemStack.f_41583_ && stack.m_41613_() > this.m_6893_()) {
            stack.m_41764_(this.m_6893_());
         }

         this.m_6596_();
      }
   }

   public boolean m_7013_(int index, ItemStack stack) {
      return true;
   }

   public void m_6211_() {
      this.input = ItemStack.f_41583_;
      this.m_6596_();
   }

   public boolean m_7983_() {
      return !this.input.m_41619_();
   }

   public int m_6893_() {
      return 64;
   }

   public void m_6596_() {
      if (this.listeners != null) {
         for (InventoryOneInput.ChangeListener listener : this.listeners) {
            listener.onChange(this);
         }
      }
   }

   public interface ChangeListener {
      void onChange(Container var1);
   }
}
