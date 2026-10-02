package com.aizistral.enigmaticlegacy.helpers;

import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public final class ItemNBTHelper {
   public static boolean detectNBT(ItemStack stack) {
      return stack.m_41782_();
   }

   public static void initNBT(ItemStack stack) {
      if (!detectNBT(stack)) {
         injectNBT(stack, new CompoundTag());
      }
   }

   public static void injectNBT(ItemStack stack, CompoundTag nbt) {
      stack.m_41751_(nbt);
   }

   public static CompoundTag getNBT(ItemStack stack) {
      initNBT(stack);
      return stack.m_41783_();
   }

   public static void setBoolean(ItemStack stack, String tag, boolean b) {
      getNBT(stack).m_128379_(tag, b);
   }

   public static void setByte(ItemStack stack, String tag, byte b) {
      getNBT(stack).m_128344_(tag, b);
   }

   public static void setShort(ItemStack stack, String tag, short s) {
      getNBT(stack).m_128376_(tag, s);
   }

   public static void setInt(ItemStack stack, String tag, int i) {
      getNBT(stack).m_128405_(tag, i);
   }

   public static void setLong(ItemStack stack, String tag, long l) {
      getNBT(stack).m_128356_(tag, l);
   }

   public static void setFloat(ItemStack stack, String tag, float f) {
      getNBT(stack).m_128350_(tag, f);
   }

   public static void setDouble(ItemStack stack, String tag, double d) {
      getNBT(stack).m_128347_(tag, d);
   }

   public static void setString(ItemStack stack, String tag, String s) {
      getNBT(stack).m_128359_(tag, s);
   }

   public static void setUUID(ItemStack stack, String tag, UUID id) {
      getNBT(stack).m_128362_(tag, id);
   }

   public static boolean verifyExistance(ItemStack stack, String tag) {
      return stack != null && getNBT(stack).m_128441_(tag);
   }

   public static boolean getBoolean(ItemStack stack, String tag, boolean defaultExpected) {
      return verifyExistance(stack, tag) ? getNBT(stack).m_128471_(tag) : defaultExpected;
   }

   public static boolean containsUUID(ItemStack stack, String tag) {
      return stack != null && getNBT(stack).m_128403_(tag);
   }

   public static UUID getUUID(ItemStack stack, String tag, UUID defaultExpected) {
      return containsUUID(stack, tag) ? getNBT(stack).m_128342_(tag) : defaultExpected;
   }

   public static byte getByte(ItemStack stack, String tag, byte defaultExpected) {
      return verifyExistance(stack, tag) ? getNBT(stack).m_128445_(tag) : defaultExpected;
   }

   public static short getShort(ItemStack stack, String tag, short defaultExpected) {
      return verifyExistance(stack, tag) ? getNBT(stack).m_128448_(tag) : defaultExpected;
   }

   public static int getInt(ItemStack stack, String tag, int defaultExpected) {
      return verifyExistance(stack, tag) ? getNBT(stack).m_128451_(tag) : defaultExpected;
   }

   public static long getLong(ItemStack stack, String tag, long defaultExpected) {
      return verifyExistance(stack, tag) ? getNBT(stack).m_128454_(tag) : defaultExpected;
   }

   public static float getFloat(ItemStack stack, String tag, float defaultExpected) {
      return verifyExistance(stack, tag) ? getNBT(stack).m_128457_(tag) : defaultExpected;
   }

   public static double getDouble(ItemStack stack, String tag, double defaultExpected) {
      return verifyExistance(stack, tag) ? getNBT(stack).m_128459_(tag) : defaultExpected;
   }

   public static CompoundTag getCompound(ItemStack stack, String tag, boolean nullifyOnFail) {
      return verifyExistance(stack, tag) ? getNBT(stack).m_128469_(tag) : (nullifyOnFail ? null : new CompoundTag());
   }

   public static String getString(ItemStack stack, String tag, String defaultExpected) {
      return verifyExistance(stack, tag) ? getNBT(stack).m_128461_(tag) : defaultExpected;
   }
}
