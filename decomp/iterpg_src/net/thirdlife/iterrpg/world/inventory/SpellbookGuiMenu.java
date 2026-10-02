package net.thirdlife.iterrpg.world.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.thirdlife.iterrpg.init.IterRpgModMenus;

public class SpellbookGuiMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
   public static final HashMap<String, Object> guistate = new HashMap<>();
   public final Level world;
   public final Player entity;
   public int x;
   public int y;
   public int z;
   private IItemHandler internal;
   private final Map<Integer, Slot> customSlots = new HashMap<>();
   private boolean bound = false;

   public SpellbookGuiMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
      super((MenuType)IterRpgModMenus.SPELLBOOK_GUI.get(), id);
      this.entity = inv.f_35978_;
      this.world = inv.f_35978_.f_19853_;
      this.internal = new ItemStackHandler(55);
      BlockPos pos = null;
      if (extraData != null) {
         pos = extraData.m_130135_();
         this.x = pos.m_123341_();
         this.y = pos.m_123342_();
         this.z = pos.m_123343_();
      }

      if (pos != null) {
         if (extraData.readableBytes() == 1) {
            byte hand = extraData.readByte();
            ItemStack itemstack;
            if (hand == 0) {
               itemstack = this.entity.m_21205_();
            } else {
               itemstack = this.entity.m_21206_();
            }

            itemstack.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
               this.internal = capability;
               this.bound = true;
            });
         } else if (extraData.readableBytes() > 1) {
            extraData.readByte();
            Entity entity = this.world.m_6815_(extraData.m_130242_());
            if (entity != null) {
               entity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                  this.internal = capability;
                  this.bound = true;
               });
            }
         } else {
            BlockEntity ent = inv.f_35978_ != null ? inv.f_35978_.f_19853_.m_7702_(pos) : null;
            if (ent != null) {
               ent.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                  this.internal = capability;
                  this.bound = true;
               });
            }
         }
      }

      this.customSlots.put(1, this.m_38897_(new SlotItemHandler(this.internal, 1, 45, 127) {
      }));
      this.customSlots.put(2, this.m_38897_(new SlotItemHandler(this.internal, 2, 79, 116) {
      }));
      this.customSlots.put(3, this.m_38897_(new SlotItemHandler(this.internal, 3, 113, 124) {
      }));
      this.customSlots.put(4, this.m_38897_(new SlotItemHandler(this.internal, 4, 178, 124) {
      }));
      this.customSlots.put(5, this.m_38897_(new SlotItemHandler(this.internal, 5, 213, 116) {
      }));
      this.customSlots.put(6, this.m_38897_(new SlotItemHandler(this.internal, 6, 247, 127) {
      }));
      this.customSlots.put(7, this.m_38897_(new SlotItemHandler(this.internal, 7, 34, 30) {
      }));
      this.customSlots.put(8, this.m_38897_(new SlotItemHandler(this.internal, 8, 52, 30) {
      }));
      this.customSlots.put(9, this.m_38897_(new SlotItemHandler(this.internal, 9, 70, 29) {
      }));
      this.customSlots.put(10, this.m_38897_(new SlotItemHandler(this.internal, 10, 88, 28) {
      }));
      this.customSlots.put(11, this.m_38897_(new SlotItemHandler(this.internal, 11, 106, 27) {
      }));
      this.customSlots.put(12, this.m_38897_(new SlotItemHandler(this.internal, 12, 124, 26) {
      }));
      this.customSlots.put(13, this.m_38897_(new SlotItemHandler(this.internal, 13, 168, 26) {
      }));
      this.customSlots.put(14, this.m_38897_(new SlotItemHandler(this.internal, 14, 186, 27) {
      }));
      this.customSlots.put(15, this.m_38897_(new SlotItemHandler(this.internal, 15, 204, 28) {
      }));
      this.customSlots.put(16, this.m_38897_(new SlotItemHandler(this.internal, 16, 222, 29) {
      }));
      this.customSlots.put(17, this.m_38897_(new SlotItemHandler(this.internal, 17, 240, 30) {
      }));
      this.customSlots.put(18, this.m_38897_(new SlotItemHandler(this.internal, 18, 258, 30) {
      }));
      this.customSlots.put(19, this.m_38897_(new SlotItemHandler(this.internal, 19, 34, 48) {
      }));
      this.customSlots.put(20, this.m_38897_(new SlotItemHandler(this.internal, 20, 52, 48) {
      }));
      this.customSlots.put(21, this.m_38897_(new SlotItemHandler(this.internal, 21, 70, 47) {
      }));
      this.customSlots.put(22, this.m_38897_(new SlotItemHandler(this.internal, 22, 88, 46) {
      }));
      this.customSlots.put(23, this.m_38897_(new SlotItemHandler(this.internal, 23, 106, 45) {
      }));
      this.customSlots.put(24, this.m_38897_(new SlotItemHandler(this.internal, 24, 124, 44) {
      }));
      this.customSlots.put(25, this.m_38897_(new SlotItemHandler(this.internal, 25, 168, 44) {
      }));
      this.customSlots.put(26, this.m_38897_(new SlotItemHandler(this.internal, 26, 186, 45) {
      }));
      this.customSlots.put(27, this.m_38897_(new SlotItemHandler(this.internal, 27, 204, 46) {
      }));
      this.customSlots.put(28, this.m_38897_(new SlotItemHandler(this.internal, 28, 222, 47) {
      }));
      this.customSlots.put(29, this.m_38897_(new SlotItemHandler(this.internal, 29, 240, 48) {
      }));
      this.customSlots.put(30, this.m_38897_(new SlotItemHandler(this.internal, 30, 258, 48) {
      }));
      this.customSlots.put(31, this.m_38897_(new SlotItemHandler(this.internal, 31, 34, 66) {
      }));
      this.customSlots.put(32, this.m_38897_(new SlotItemHandler(this.internal, 32, 52, 66) {
      }));
      this.customSlots.put(33, this.m_38897_(new SlotItemHandler(this.internal, 33, 70, 65) {
      }));
      this.customSlots.put(34, this.m_38897_(new SlotItemHandler(this.internal, 34, 88, 64) {
      }));
      this.customSlots.put(35, this.m_38897_(new SlotItemHandler(this.internal, 35, 106, 63) {
      }));
      this.customSlots.put(36, this.m_38897_(new SlotItemHandler(this.internal, 36, 124, 62) {
      }));
      this.customSlots.put(37, this.m_38897_(new SlotItemHandler(this.internal, 37, 168, 62) {
      }));
      this.customSlots.put(38, this.m_38897_(new SlotItemHandler(this.internal, 38, 186, 63) {
      }));
      this.customSlots.put(39, this.m_38897_(new SlotItemHandler(this.internal, 39, 204, 64) {
      }));
      this.customSlots.put(40, this.m_38897_(new SlotItemHandler(this.internal, 40, 222, 65) {
      }));
      this.customSlots.put(41, this.m_38897_(new SlotItemHandler(this.internal, 41, 240, 66) {
      }));
      this.customSlots.put(42, this.m_38897_(new SlotItemHandler(this.internal, 42, 258, 66) {
      }));
      this.customSlots.put(43, this.m_38897_(new SlotItemHandler(this.internal, 43, 34, 84) {
      }));
      this.customSlots.put(44, this.m_38897_(new SlotItemHandler(this.internal, 44, 52, 84) {
      }));
      this.customSlots.put(45, this.m_38897_(new SlotItemHandler(this.internal, 45, 70, 83) {
      }));
      this.customSlots.put(46, this.m_38897_(new SlotItemHandler(this.internal, 46, 88, 82) {
      }));
      this.customSlots.put(47, this.m_38897_(new SlotItemHandler(this.internal, 47, 106, 81) {
      }));
      this.customSlots.put(48, this.m_38897_(new SlotItemHandler(this.internal, 48, 124, 80) {
      }));
      this.customSlots.put(49, this.m_38897_(new SlotItemHandler(this.internal, 49, 168, 80) {
      }));
      this.customSlots.put(50, this.m_38897_(new SlotItemHandler(this.internal, 50, 186, 81) {
      }));
      this.customSlots.put(51, this.m_38897_(new SlotItemHandler(this.internal, 51, 204, 82) {
      }));
      this.customSlots.put(52, this.m_38897_(new SlotItemHandler(this.internal, 52, 222, 83) {
      }));
      this.customSlots.put(53, this.m_38897_(new SlotItemHandler(this.internal, 53, 240, 84) {
      }));
      this.customSlots.put(54, this.m_38897_(new SlotItemHandler(this.internal, 54, 258, 84) {
      }));

      for (int si = 0; si < 3; si++) {
         for (int sj = 0; sj < 9; sj++) {
            this.m_38897_(new Slot(inv, sj + (si + 1) * 9, 75 + sj * 18, 169 + si * 18));
         }
      }

      for (int si = 0; si < 9; si++) {
         this.m_38897_(new Slot(inv, si, 75 + si * 18, 227));
      }
   }

   public boolean m_6875_(Player player) {
      return true;
   }

   public ItemStack m_7648_(Player playerIn, int index) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = (Slot)this.f_38839_.get(index);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         if (index < 54) {
            if (!this.m_38903_(itemstack1, 54, this.f_38839_.size(), true)) {
               return ItemStack.f_41583_;
            }

            slot.m_40234_(itemstack1, itemstack);
         } else if (!this.m_38903_(itemstack1, 0, 54, false)) {
            if (index < 81) {
               if (!this.m_38903_(itemstack1, 81, this.f_38839_.size(), true)) {
                  return ItemStack.f_41583_;
               }
            } else if (!this.m_38903_(itemstack1, 54, 81, false)) {
               return ItemStack.f_41583_;
            }

            return ItemStack.f_41583_;
         }

         if (itemstack1.m_41613_() == 0) {
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

   protected boolean m_38903_(ItemStack p_38904_, int p_38905_, int p_38906_, boolean p_38907_) {
      boolean flag = false;
      int i = p_38905_;
      if (p_38907_) {
         i = p_38906_ - 1;
      }

      if (p_38904_.m_41753_()) {
         while (!p_38904_.m_41619_() && (p_38907_ ? i >= p_38905_ : i < p_38906_)) {
            Slot slot = (Slot)this.f_38839_.get(i);
            ItemStack itemstack = slot.m_7993_();
            if (slot.m_5857_(itemstack) && !itemstack.m_41619_() && ItemStack.m_150942_(p_38904_, itemstack)) {
               int j = itemstack.m_41613_() + p_38904_.m_41613_();
               int maxSize = Math.min(slot.m_6641_(), p_38904_.m_41741_());
               if (j <= maxSize) {
                  p_38904_.m_41764_(0);
                  itemstack.m_41764_(j);
                  slot.m_5852_(itemstack);
                  flag = true;
               } else if (itemstack.m_41613_() < maxSize) {
                  p_38904_.m_41774_(maxSize - itemstack.m_41613_());
                  itemstack.m_41764_(maxSize);
                  slot.m_5852_(itemstack);
                  flag = true;
               }
            }

            if (p_38907_) {
               i--;
            } else {
               i++;
            }
         }
      }

      if (!p_38904_.m_41619_()) {
         if (p_38907_) {
            i = p_38906_ - 1;
         } else {
            i = p_38905_;
         }

         while (p_38907_ ? i >= p_38905_ : i < p_38906_) {
            Slot slot1 = (Slot)this.f_38839_.get(i);
            ItemStack itemstack1 = slot1.m_7993_();
            if (itemstack1.m_41619_() && slot1.m_5857_(p_38904_)) {
               if (p_38904_.m_41613_() > slot1.m_6641_()) {
                  slot1.m_5852_(p_38904_.m_41620_(slot1.m_6641_()));
               } else {
                  slot1.m_5852_(p_38904_.m_41620_(p_38904_.m_41613_()));
               }

               slot1.m_6654_();
               flag = true;
               break;
            }

            if (p_38907_) {
               i--;
            } else {
               i++;
            }
         }
      }

      return flag;
   }

   public void m_6877_(Player playerIn) {
      super.m_6877_(playerIn);
      if (!this.bound && playerIn instanceof ServerPlayer serverPlayer) {
         if (serverPlayer.m_6084_() && !serverPlayer.m_9232_()) {
            for (int i = 0; i < this.internal.getSlots(); i++) {
               playerIn.m_150109_().m_150079_(this.internal.extractItem(i, this.internal.getStackInSlot(i).m_41613_(), false));
            }
         } else {
            for (int j = 0; j < this.internal.getSlots(); j++) {
               playerIn.m_36176_(this.internal.extractItem(j, this.internal.getStackInSlot(j).m_41613_(), false), false);
            }
         }
      }
   }

   public Map<Integer, Slot> get() {
      return this.customSlots;
   }
}
