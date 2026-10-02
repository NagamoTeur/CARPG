package com.aizistral.enigmaticlegacy.crafting;

import com.aizistral.enigmaticlegacy.items.OblivionStone;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class OblivionStoneCombineRecipe extends CustomRecipe {
   public static final SimpleRecipeSerializer<OblivionStoneCombineRecipe> SERIALIZER = new SimpleRecipeSerializer(OblivionStoneCombineRecipe::new);

   public OblivionStoneCombineRecipe(ResourceLocation id) {
      super(id);
   }

   public ItemStack assemble(CraftingContainer inv) {
      List<ItemStack> stackList = new ArrayList<>();
      ItemStack voidStone = null;

      for (int i = 0; i < inv.m_6643_(); i++) {
         ItemStack checkedItemStack = inv.m_8020_(i);
         if (!checkedItemStack.m_41619_()) {
            if (checkedItemStack.m_41720_() == EnigmaticItems.VOID_STONE) {
               if (voidStone != null) {
                  return ItemStack.f_41583_;
               }

               voidStone = checkedItemStack;
            } else {
               stackList.add(checkedItemStack);
            }
         }
      }

      if (voidStone != null && stackList.size() == 1) {
         ItemStack savedStack = stackList.get(0).m_41777_();
         CompoundTag nbt = voidStone.m_41784_();
         ListTag arr = nbt.m_128437_("SupersolidID", 8);
         int counter = 0;
         if (arr.size() >= OblivionStone.itemHardcap.getValue()) {
            return null;
         } else {
            for (Tag s_uncast : arr) {
               counter++;
               String s = ((StringTag)s_uncast).m_7916_();
               if (s.equals(ForgeRegistries.ITEMS.getKey(savedStack.m_41720_()).toString())) {
                  return ItemStack.f_41583_;
               }
            }

            ListTag arrCopy = arr.m_6426_();
            CompoundTag nbtCopy = nbt.m_6426_();
            arrCopy.add(StringTag.m_129297_(ForgeRegistries.ITEMS.getKey(savedStack.m_41720_()).toString()));
            nbtCopy.m_128365_("SupersolidID", arrCopy);
            ItemStack returnedStack = voidStone.m_41777_();
            returnedStack.m_41751_(nbtCopy);
            return returnedStack;
         }
      } else if (voidStone != null && stackList.size() == 0) {
         ItemStack returnedStack = new ItemStack(EnigmaticItems.VOID_STONE, 1);
         returnedStack.m_41751_(voidStone.m_41784_().m_6426_());
         returnedStack.m_41749_("SupersolidID");
         return returnedStack;
      } else {
         return ItemStack.f_41583_;
      }
   }

   public boolean matches(CraftingContainer inv, Level world) {
      List<ItemStack> stackList = new ArrayList<>();
      ItemStack voidStone = null;

      for (int i = 0; i < inv.m_6643_(); i++) {
         ItemStack checkedItemStack = inv.m_8020_(i);
         if (!checkedItemStack.m_41619_()) {
            if (checkedItemStack.m_41720_() == EnigmaticItems.VOID_STONE) {
               if (voidStone != null) {
                  return false;
               }

               voidStone = checkedItemStack;
            } else {
               stackList.add(checkedItemStack);
            }
         }
      }

      if (voidStone != null && stackList.size() == 1) {
         ItemStack savedStack = stackList.get(0).m_41777_();
         CompoundTag nbt = voidStone.m_41784_();
         ListTag arr = nbt.m_128437_("SupersolidID", 8);
         int counter = 0;
         if (arr.size() >= OblivionStone.itemHardcap.getValue()) {
            return false;
         } else {
            for (Tag s_uncast : arr) {
               counter++;
               String s = ((StringTag)s_uncast).m_7916_();
               if (s.equals(ForgeRegistries.ITEMS.getKey(savedStack.m_41720_()).toString())) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return voidStone != null && stackList.size() == 0;
      }
   }

   public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
      return NonNullList.m_122780_(inv.m_6643_(), ItemStack.f_41583_);
   }

   public boolean m_8004_(int width, int height) {
      return width * height >= 2;
   }

   public RecipeSerializer<?> m_7707_() {
      return SERIALIZER;
   }
}
