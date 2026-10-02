package com.hollingsworth.arsnouveau.api.util;

import com.hollingsworth.arsnouveau.api.RegistryHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class NBTUtil {
   public static CompoundTag storeBlockPos(CompoundTag tag, String prefix, BlockPos pos) {
      if (pos == null) {
         return tag;
      } else {
         writePositional(tag, prefix, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
         return tag;
      }
   }

   public static CompoundTag storeVec(CompoundTag tag, String prefix, Vec3 vec) {
      writePositional(tag, prefix, vec.f_82479_, vec.f_82480_, vec.f_82481_);
      return tag;
   }

   public static CompoundTag removeBlockPos(CompoundTag tag, String prefix) {
      tag.m_128473_(prefix + "_x");
      tag.m_128473_(prefix + "_y");
      tag.m_128473_(prefix + "_z");
      return tag;
   }

   public static CompoundTag writePositional(CompoundTag tag, String prefix, double x, double y, double z) {
      tag.m_128347_(prefix + "_x", x);
      tag.m_128347_(prefix + "_y", y);
      tag.m_128347_(prefix + "_z", z);
      return tag;
   }

   @Deprecated
   public static BlockPos getBlockPos(CompoundTag tag, String prefix) {
      return new BlockPos(tag.m_128459_(prefix + "_x"), tag.m_128459_(prefix + "_y"), tag.m_128459_(prefix + "_z"));
   }

   public static Vec3 getVec(CompoundTag tag, String prefix) {
      return tag == null ? null : new Vec3(tag.m_128459_(prefix + "_x"), tag.m_128459_(prefix + "_y"), tag.m_128459_(prefix + "_z"));
   }

   @Nullable
   public static BlockPos getNullablePos(CompoundTag tag, String prefix) {
      return !tag.m_128441_(prefix + "_x") ? null : new BlockPos(tag.m_128459_(prefix + "_x"), tag.m_128459_(prefix + "_y"), tag.m_128459_(prefix + "_z"));
   }

   public static boolean hasBlockPos(CompoundTag tag, String prefix) {
      return tag.m_128441_(prefix + "_x");
   }

   public static List<ItemStack> readItems(CompoundTag tag, String prefix) {
      List<ItemStack> stacks = new ArrayList<>();
      if (tag == null) {
         return stacks;
      } else {
         try {
            CompoundTag itemsTag = tag.m_128469_(prefix + "_tag");
            int numItems = itemsTag.m_128451_("itemsSize");

            for (int i = 0; i < numItems; i++) {
               String key = prefix + "_" + i;
               stacks.add(ItemStack.m_41712_(itemsTag.m_128469_(key)));
            }
         } catch (Exception var7) {
            var7.printStackTrace();
         }

         return stacks;
      }
   }

   public static void writeItems(CompoundTag tag, String prefix, List<ItemStack> items) {
      CompoundTag allItemsTag = new CompoundTag();

      for (int i = 0; i < items.size(); i++) {
         ItemStack stack = items.get(i);
         CompoundTag itemTag = new CompoundTag();
         stack.m_41739_(itemTag);
         allItemsTag.m_128365_(prefix + "_" + i, itemTag);
      }

      allItemsTag.m_128405_("itemsSize", items.size());
      tag.m_128365_(prefix + "_tag", allItemsTag);
   }

   public static List<String> readStrings(CompoundTag tag, String prefix) {
      List<String> strings = new ArrayList<>();
      if (tag == null) {
         return strings;
      } else {
         for (String s : tag.m_128431_()) {
            if (s.contains(prefix)) {
               strings.add(tag.m_128461_(s));
            }
         }

         return strings;
      }
   }

   public static void writeStrings(CompoundTag tag, String prefix, Collection<String> strings) {
      int i = 0;

      for (String s : strings) {
         tag.m_128359_(prefix + "_" + i, s);
         i++;
      }
   }

   public static void writeResourceLocations(CompoundTag tag, String prefix, Collection<ResourceLocation> resourceLocations) {
      writeStrings(tag, prefix, resourceLocations.stream().<String>map(ResourceLocation::toString).collect(Collectors.toList()));
   }

   public static List<ResourceLocation> readResourceLocations(CompoundTag tag, String prefix) {
      return readStrings(tag, prefix).stream().<ResourceLocation>map(ResourceLocation::new).collect(Collectors.toList());
   }

   public static String getItemKey(ItemStack stack, String prefix) {
      return prefix + RegistryHelper.getRegistryName(stack.m_41720_()).toString();
   }
}
