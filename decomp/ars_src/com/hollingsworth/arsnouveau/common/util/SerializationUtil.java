package com.hollingsworth.arsnouveau.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;

public class SerializationUtil {
   public static final int COMPOUND_TAG_TYPE = 10;
   private static final String X = "x";
   private static final String Y = "y";
   private static final String Z = "z";

   public static ListTag serializeItemList(List<ItemStack> items) {
      ListTag itemList = new ListTag();
      items.forEach(itemstack -> itemList.add(itemstack.serializeNBT()));
      return itemList;
   }

   public static List<ItemStack> deserializeItemList(CompoundTag compoundTag, String tag) {
      List<ItemStack> itemStacks = new ArrayList<>();
      if (!compoundTag.m_128441_(tag)) {
         return itemStacks;
      } else {
         ListTag itemList = compoundTag.m_128437_(tag, 10);

         for (int i = 0; i < itemList.size(); i++) {
            itemStacks.add(ItemStack.m_41712_(itemList.m_128728_(i)));
         }

         return itemStacks;
      }
   }

   public static ListTag serializeTagList(List<CompoundTag> tags) {
      ListTag tagList = new ListTag();
      tagList.addAll(tags);
      return tagList;
   }

   public static <T> List<T> mapFromTags(ListTag tagList, Function<CompoundTag, T> transformer) {
      List<T> list = new ArrayList<>();

      for (int i = 0; i < tagList.size(); i++) {
         list.add(transformer.apply(tagList.m_128728_(i)));
      }

      return list;
   }

   public static ListTag serializeBlockPosList(List<BlockPos> blockPositions) {
      ListTag serializedBlockPositions = new ListTag();
      blockPositions.forEach(blockPos -> serializedBlockPositions.add(serializeBlockPos(blockPos)));
      return serializedBlockPositions;
   }

   public static CompoundTag serializeBlockPos(BlockPos blockPos) {
      CompoundTag serializedBlockPos = new CompoundTag();
      serializedBlockPos.m_128405_("x", blockPos.m_123341_());
      serializedBlockPos.m_128405_("y", blockPos.m_123342_());
      serializedBlockPos.m_128405_("z", blockPos.m_123343_());
      return serializedBlockPos;
   }

   public static List<BlockPos> deserializeBlockPosList(CompoundTag tag, String key) {
      ListTag serializedBlockPositions = tag.m_128437_(key, 10);
      List<BlockPos> blockPositions = new ArrayList<>();

      for (int i = 0; i < serializedBlockPositions.size(); i++) {
         blockPositions.add(deserializeBlockPos(serializedBlockPositions.m_128728_(i)));
      }

      return blockPositions;
   }

   public static BlockPos deserializeBlockPos(CompoundTag serializedBlockPosition) {
      return new BlockPos(serializedBlockPosition.m_128451_("x"), serializedBlockPosition.m_128451_("y"), serializedBlockPosition.m_128451_("z"));
   }
}
