package dev.latvian.mods.kubejs.gui;

import dev.architectury.registry.registries.DeferredRegister;
import dev.latvian.mods.kubejs.CommonProperties;
import dev.latvian.mods.kubejs.platform.MiscPlatformHelper;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class KubeJSMenu extends AbstractContainerMenu {
   public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create("kubejs", Registry.f_122913_);
   public static final Supplier<MenuType<KubeJSMenu>> KUBEJS_MENU = MENU_TYPES.register("menu", () -> MiscPlatformHelper.get().createMenuType());
   public final Player player;
   public final KubeJSGUI guiData;

   public static void init() {
      if (!CommonProperties.get().serverOnly) {
         MENU_TYPES.register();
      }
   }

   public KubeJSMenu(int id, Inventory inventory, KubeJSGUI guiData) {
      super(KUBEJS_MENU.get(), id);
      this.player = inventory.f_35978_;
      this.guiData = guiData;
      if (guiData.inventory.kjs$getSlots() > 0) {
         int k = (guiData.inventoryHeight - 4) * 18;

         for (int l = 0; l < guiData.inventoryHeight; l++) {
            for (int m = 0; m < 9; m++) {
               this.m_38897_(new InventoryKJSSlot(guiData.inventory, m + l * 9, 8 + m * 18, 18 + l * 18));
            }
         }

         if (guiData.playerSlotsX >= 0 && guiData.playerSlotsY >= 0) {
            for (int l = 0; l < 3; l++) {
               for (int m = 0; m < 9; m++) {
                  this.m_38897_(new Slot(inventory, m + l * 9 + 9, guiData.playerSlotsX + m * 18, guiData.playerSlotsY + l * 18 + k));
               }
            }

            for (int l = 0; l < 9; l++) {
               this.m_38897_(new Slot(inventory, l, guiData.playerSlotsX + l * 18, guiData.playerSlotsY + 58 + k));
            }
         }
      }
   }

   public KubeJSMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
      this(id, inventory, new KubeJSGUI(buf));
   }

   public ItemStack m_7648_(Player player, int i) {
      ItemStack itemStack = ItemStack.f_41583_;
      Slot slot = (Slot)this.f_38839_.get(i);
      int slotCount = this.guiData.inventory.kjs$getSlots();
      if (slot != null && slot.m_6657_()) {
         ItemStack itemStack2 = slot.m_7993_();
         itemStack = itemStack2.m_41777_();
         if (i < slotCount) {
            if (!this.m_38903_(itemStack2, slotCount, this.f_38839_.size(), true)) {
               return ItemStack.f_41583_;
            }
         } else if (!this.m_38903_(itemStack2, 0, slotCount, false)) {
            return ItemStack.f_41583_;
         }

         if (itemStack2.m_41619_()) {
            slot.m_5852_(ItemStack.f_41583_);
         } else {
            slot.m_6654_();
         }
      }

      return itemStack;
   }

   public boolean m_6875_(Player player) {
      return true;
   }
}
