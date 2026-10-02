package dev.latvian.mods.kubejs.item;

import dev.latvian.mods.kubejs.core.InventoryKJS;
import dev.latvian.mods.kubejs.platform.LevelPlatformHelper;
import java.util.Objects;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemHandlerUtils {
   public static void giveItemToPlayer(Player player, @NotNull ItemStack stack, int preferredSlot) {
      if (!stack.m_41619_()) {
         InventoryKJS inventory = new PlayerMainInvWrapper(player.m_150109_());
         Level level = player.f_19853_;
         ItemStack remainder = stack;
         if (preferredSlot >= 0 && preferredSlot < inventory.kjs$getSlots()) {
            remainder = inventory.kjs$insertItem(preferredSlot, stack, false);
         }

         if (!remainder.m_41619_()) {
            remainder = insertItemStacked(inventory, remainder, false);
         }

         if (remainder.m_41619_() || remainder.m_41613_() != stack.m_41613_()) {
            level.m_6263_(
               null,
               player.m_20185_(),
               player.m_20186_() + 0.5,
               player.m_20189_(),
               SoundEvents.f_12019_,
               SoundSource.PLAYERS,
               0.2F,
               ((level.f_46441_.m_188501_() - level.f_46441_.m_188501_()) * 0.7F + 1.0F) * 2.0F
            );
         }

         if (!remainder.m_41619_() && !level.f_46443_) {
            ItemEntity itemEntity = new ItemEntity(level, player.m_20185_(), player.m_20186_() + 0.5, player.m_20189_(), remainder);
            itemEntity.m_32010_(40);
            itemEntity.m_20256_(itemEntity.m_20184_().m_82542_(0.0, 1.0, 0.0));
            level.m_7967_(itemEntity);
         }
      }
   }

   @NotNull
   public static ItemStack insertItemStacked(InventoryKJS inventory, @NotNull ItemStack stack, boolean simulate) {
      if (inventory == null || stack.m_41619_()) {
         return stack;
      } else if (!stack.m_41753_()) {
         return insertItem(inventory, stack, simulate);
      } else {
         int sizeInventory = inventory.kjs$getSlots();

         for (int i = 0; i < sizeInventory; i++) {
            ItemStack slot = inventory.kjs$getStackInSlot(i);
            if (canItemStacksStackRelaxed(slot, stack)) {
               stack = inventory.kjs$insertItem(i, stack, simulate);
               if (stack.m_41619_()) {
                  break;
               }
            }
         }

         if (!stack.m_41619_()) {
            for (int ix = 0; ix < sizeInventory; ix++) {
               if (inventory.kjs$getStackInSlot(ix).m_41619_()) {
                  stack = inventory.kjs$insertItem(ix, stack, simulate);
                  if (stack.m_41619_()) {
                     break;
                  }
               }
            }
         }

         return stack;
      }
   }

   @NotNull
   public static ItemStack insertItem(InventoryKJS dest, @NotNull ItemStack stack, boolean simulate) {
      if (dest != null && !stack.m_41619_()) {
         for (int i = 0; i < dest.kjs$getSlots(); i++) {
            stack = dest.kjs$insertItem(i, stack, simulate);
            if (stack.m_41619_()) {
               return ItemStack.f_41583_;
            }
         }

         return stack;
      } else {
         return stack;
      }
   }

   public static boolean canItemStacksStackRelaxed(@NotNull ItemStack a, @NotNull ItemStack b) {
      if (a.m_41619_() || b.m_41619_() || a.m_41720_() != b.m_41720_()) {
         return false;
      } else if (!a.m_41753_()) {
         return false;
      } else if (a.m_41782_() != b.m_41782_()) {
         return false;
      } else {
         return a.m_41782_() && !Objects.equals(a.m_41783_(), b.m_41783_()) ? false : LevelPlatformHelper.get().areCapsCompatible(a, b);
      }
   }

   public static boolean canItemStacksStack(@NotNull ItemStack a, @NotNull ItemStack b) {
      if (a.m_41619_() || !a.m_41656_(b) || a.m_41782_() != b.m_41782_()) {
         return false;
      } else {
         return a.m_41782_() && !Objects.equals(a.m_41783_(), b.m_41783_()) ? false : LevelPlatformHelper.get().areCapsCompatible(a, b);
      }
   }
}
