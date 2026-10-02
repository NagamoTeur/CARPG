package com.aizistral.enigmaticlegacy.helpers;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemLoreHelper {
   @OnlyIn(Dist.CLIENT)
   public static void indicateCursedOnesOnly(List<Component> list) {
      ChatFormatting format;
      if (Minecraft.m_91087_().f_91074_ != null) {
         format = SuperpositionHandler.isTheCursedOne(Minecraft.m_91087_().f_91074_) ? ChatFormatting.GOLD : ChatFormatting.DARK_RED;
      } else {
         format = ChatFormatting.DARK_RED;
      }

      list.add(Component.m_237115_("tooltip.enigmaticlegacy.cursedOnesOnly1").m_130940_(format));
      list.add(Component.m_237115_("tooltip.enigmaticlegacy.cursedOnesOnly2").m_130940_(format));
   }

   @OnlyIn(Dist.CLIENT)
   public static void indicateWorthyOnesOnly(List<Component> list) {
      ChatFormatting format = ChatFormatting.DARK_RED;
      Player player = Minecraft.m_91087_().f_91074_;
      if (player != null) {
         format = SuperpositionHandler.isTheWorthyOne(Minecraft.m_91087_().f_91074_) ? ChatFormatting.GOLD : ChatFormatting.DARK_RED;
      }

      list.add(Component.m_237115_("tooltip.enigmaticlegacy.worthyOnesOnly1"));
      list.add(Component.m_237115_("tooltip.enigmaticlegacy.worthyOnesOnly2"));
      list.add(Component.m_237115_("tooltip.enigmaticlegacy.worthyOnesOnly3"));
      list.add(Component.m_237115_("tooltip.enigmaticlegacy.void"));
      list.add(
         Component.m_237115_("tooltip.enigmaticlegacy.worthyOnesOnly4")
            .m_130940_(format)
            .m_7220_(Component.m_237113_(" " + SuperpositionHandler.getSufferingTime(player)).m_130940_(ChatFormatting.LIGHT_PURPLE))
      );
   }

   @OnlyIn(Dist.CLIENT)
   public static void indicateBlessedOnesOnly(List<Component> list) {
      ChatFormatting format;
      if (EnigmaticLegacy.PROXY.getClientPlayer() != null) {
         format = SuperpositionHandler.isTheBlessedOne(EnigmaticLegacy.PROXY.getClientPlayer()) ? ChatFormatting.GOLD : ChatFormatting.DARK_RED;
      } else {
         format = ChatFormatting.DARK_RED;
      }

      list.add(Component.m_237115_("tooltip.enigmaticlegacy.blessedOnesOnly1").m_130940_(format));
      list.add(Component.m_237115_("tooltip.enigmaticlegacy.blessedOnesOnly2").m_130940_(format));
   }

   public static void addLocalizedFormattedString(List<Component> list, String str, ChatFormatting format) {
      list.add(Component.m_237115_(str).m_130940_(format));
   }

   public static void addLocalizedString(List<Component> list, String str) {
      list.add(Component.m_237115_(str));
   }

   public static void addLocalizedString(List<Component> list, String str, @Nullable ChatFormatting format, Object... values) {
      Component[] stringValues = new Component[values.length];
      int counter = 0;

      for (Object value : values) {
         if (!(value instanceof MutableComponent comp)) {
            comp = Component.m_237113_(value.toString());
         }

         if (format != null) {
            comp.m_130940_(format);
         }

         stringValues[counter] = comp;
         counter++;
      }

      list.add(Component.m_237110_(str, stringValues));
   }

   public static ItemStack mergeDisplayData(ItemStack from, ItemStack to) {
      CompoundTag nbt = from.m_41698_("display");
      ListTag loreList = nbt.m_128437_("Lore", 8).size() > 0 ? nbt.m_128437_("Lore", 8) : to.m_41698_("display").m_128437_("Lore", 8);
      StringTag displayName = nbt.m_128461_("Name").length() > 0
         ? StringTag.m_129297_(nbt.m_128461_("Name"))
         : StringTag.m_129297_(to.m_41698_("display").m_128461_("Name"));
      CompoundTag mergedData = new CompoundTag();
      mergedData.m_128365_("Lore", loreList.m_6426_());
      mergedData.m_128365_("Name", displayName.m_6426_());
      to.m_41784_().m_128365_("display", mergedData);
      return to;
   }

   public static ItemStack addLoreString(ItemStack stack, String string) {
      CompoundTag nbt = stack.m_41698_("display");
      ListTag loreList = nbt.m_128437_("Lore", 8);
      loreList.add(StringTag.m_129297_(Serializer.m_130703_(Component.m_237113_(string))));
      nbt.m_128365_("Lore", loreList);
      return stack;
   }

   public static ItemStack setLoreString(ItemStack stack, String string, int index) {
      CompoundTag nbt = stack.m_41698_("display");
      ListTag loreList = nbt.m_128437_("Lore", 8);
      if (loreList.size() - 1 >= index) {
         loreList.set(index, StringTag.m_129297_(Serializer.m_130703_(Component.m_237113_(string))));
      } else {
         loreList.add(StringTag.m_129297_(Serializer.m_130703_(Component.m_237113_(string))));
      }

      nbt.m_128365_("Lore", loreList);
      return stack;
   }

   public static ItemStack removeLoreString(ItemStack stack, int index) {
      CompoundTag nbt = stack.m_41698_("display");
      ListTag loreList = nbt.m_128437_("Lore", 8);
      if (loreList.size() > 0) {
         if (index == -1) {
            loreList.remove(loreList.size() - 1);
         } else if (loreList.size() - 1 >= index) {
            loreList.remove(index);
         }
      }

      nbt.m_128365_("Lore", loreList);
      return stack;
   }

   public static ItemStack setLastLoreString(ItemStack stack, String string) {
      CompoundTag nbt = stack.m_41698_("display");
      ListTag loreList = nbt.m_128437_("Lore", 8);
      if (loreList.size() > 0) {
         loreList.set(loreList.size() - 1, StringTag.m_129297_(Serializer.m_130703_(Component.m_237113_(string))));
      } else {
         loreList.add(StringTag.m_129297_(Serializer.m_130703_(Component.m_237113_(string))));
      }

      nbt.m_128365_("Lore", loreList);
      return stack;
   }

   public static ItemStack setDisplayName(ItemStack stack, String name) {
      CompoundTag nbt = stack.m_41698_("display");
      nbt.m_128359_("Name", Serializer.m_130703_(Component.m_237113_(name)));
      return stack;
   }

   public static class AnvilParser {
      private boolean isLore;
      private int loreIndex = -1;
      private boolean removeString;
      private String handledString;

      private AnvilParser(String string) {
         this.handledString = string.toString();
         this.isLore = this.handledString.startsWith("!");
         this.removeString = this.handledString.startsWith("-!");
         if (this.isLore) {
            this.handledString = this.handledString.replaceFirst("!", "");
            String index = parseIndex(this.handledString);
            this.loreIndex = Integer.parseInt(index);
            if (this.loreIndex != -1) {
               this.handledString = this.handledString.replaceFirst(index, "");
            }
         } else if (this.removeString) {
            this.handledString = this.handledString.replaceFirst("-!", "");
            String index = parseIndex(this.handledString);
            this.loreIndex = Integer.parseInt(index);
            if (this.loreIndex != -1) {
               this.handledString = this.handledString.replaceFirst(index, "");
            }
         }

         this.handledString = parseFormatting(this.handledString);
      }

      public static ItemLoreHelper.AnvilParser parseField(String field) {
         return new ItemLoreHelper.AnvilParser(field);
      }

      private static String parseFormatting(String field) {
         String formatter = Component.m_237115_("tooltip.enigmaticlegacy.paragraph").getString();
         String subformat = Component.m_237115_("tooltip.enigmaticlegacy.subformat").getString();
         return field.replace(subformat, formatter);
      }

      private static String parseIndex(String field) {
         String number = "";
         int index = -1;

         for (char symbol : field.toCharArray()) {
            if (!Character.isDigit(symbol)) {
               break;
            }

            number = number + symbol;
            if (number.length() >= 2) {
               break;
            }
         }

         return !number.equals("") ? number : index + "";
      }

      public boolean isLoreString() {
         return this.isLore;
      }

      public boolean shouldRemoveString() {
         return this.removeString;
      }

      public int getLoreIndex() {
         return this.loreIndex;
      }

      public String getFormattedString() {
         return this.handledString;
      }
   }
}
