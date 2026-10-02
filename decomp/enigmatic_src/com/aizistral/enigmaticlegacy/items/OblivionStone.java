package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import com.aizistral.enigmaticlegacy.registries.EnigmaticSounds;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class OblivionStone extends ItemBase implements Vanishable {
   public static Omniconfig.IntParameter itemSoftcap;
   public static Omniconfig.IntParameter itemHardcap;

   @SubscribeConfig
   public static void onConfig(OmniconfigWrapper builder) {
      builder.pushPrefix("OblivionStone");
      itemSoftcap = builder.comment(
            "Soft cap for Keystone of The Oblivion. When it's reached, the list view seen in it's Ctrl tooltip will be fixed at this amount of items, and become chaotic and unreadable. Required since monitors are not infinitely large these days."
         )
         .min(1.0)
         .getInt("Softcap", 25);
      itemHardcap = builder.comment(
            "Hard cap for Keystone of The Oblivion. When it's reached, you will no longer be able to add new items to it's list via crafting. Required to prevent potential perfomance issues with ridiculously large lists."
         )
         .min(1.0)
         .getInt("Hardcap", 100);
      builder.popPrefix();
   }

   public OblivionStone() {
      super(ItemBase.getDefaultProperties().m_41487_(1).m_41497_(Rarity.RARE).m_41486_());
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidStone1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidStone2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidStone3");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidStone4");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidStone5");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidStone6");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidStone7");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidStone8");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidStone9");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidStone10");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidStone11");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidStone12");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidStone13");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidStone14");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidStone15");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidStone16");
      } else if (Screen.m_96637_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidStoneCtrlList");
         if (stack.m_41782_()) {
            CompoundTag nbt = stack.m_41783_();
            ListTag arr = nbt.m_128437_("SupersolidID", 8);
            int counter = 0;
            if (arr.size() <= itemSoftcap.getValue()) {
               for (Tag s_uncast : arr) {
                  String s = ((StringTag)s_uncast).m_7916_();
                  Item something = (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation(s));
                  if (something != null) {
                     ItemStack displayStack = new ItemStack(something, 1);
                     list.add(Component.m_237113_(" - " + displayStack.m_41786_().getString()).m_130940_(ChatFormatting.GOLD));
                  }

                  counter++;
               }
            } else {
               for (int s = 0; s < itemSoftcap.getValue(); s++) {
                  int randomID = random.nextInt(arr.size());
                  Item something = (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation(((StringTag)arr.get(randomID)).m_7916_()));
                  if (something != null) {
                     ItemStack displayStack = new ItemStack(something, 1);
                     list.add(Component.m_237113_(" - " + displayStack.m_41786_().getString()).m_130940_(ChatFormatting.GOLD).m_130940_(ChatFormatting.GOLD));
                  }
               }
            }
         }
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidStoneHoldCtrl");
      }

      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      MutableComponent mode;
      if (ItemNBTHelper.getBoolean(stack, "IsActive", true)) {
         mode = Component.m_237115_("tooltip.enigmaticlegacy.voidStoneMode" + ItemNBTHelper.getInt(stack, "ConsumptionMode", 0));
      } else {
         mode = Component.m_237115_("tooltip.enigmaticlegacy.voidStoneModeInactive");
      }

      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.voidStoneModeDesc", null, mode.getString());
   }

   public boolean m_142305_(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
      if (action != ClickAction.PRIMARY && slot.m_5857_(stack) && slot.m_8010_(player) && !other.m_41619_()) {
         other.m_41764_(0);
         if (player.f_19853_.f_46443_) {
            player.f_19853_.m_5594_(player, player.m_20183_(), SoundEvents.f_11757_, SoundSource.PLAYERS, 0.25F, 1.2F + (float)Math.random() * 0.4F);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean m_142207_(ItemStack stack, Slot slot, ClickAction action, Player player) {
      if (action != ClickAction.PRIMARY && slot.m_5857_(stack) && slot.m_8010_(player) && slot.m_6657_()) {
         slot.m_5852_(ItemStack.f_41583_);
         if (player.f_19853_.f_46443_) {
            player.f_19853_.m_5594_(player, player.m_20183_(), SoundEvents.f_11757_, SoundSource.PLAYERS, 0.25F, 1.2F + (float)Math.random() * 0.4F);
         }

         return true;
      } else {
         return false;
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack stack = player.m_21120_(hand);
      int mode = ItemNBTHelper.getInt(stack, "ConsumptionMode", 0);
      if (player.m_6047_()) {
         world.m_5594_(
            null,
            player.m_20183_(),
            ItemNBTHelper.getBoolean(stack, "IsActive", true) ? EnigmaticSounds.CHARGED_OFF : EnigmaticSounds.CHARGED_ON,
            SoundSource.PLAYERS,
            (float)(0.8F + Math.random() * 0.2F),
            (float)(0.8F + Math.random() * 0.2F)
         );
         ItemNBTHelper.setBoolean(stack, "IsActive", !ItemNBTHelper.getBoolean(stack, "IsActive", true));
      } else {
         if (mode >= 0 && mode < 2) {
            ItemNBTHelper.setInt(stack, "ConsumptionMode", mode + 1);
         } else {
            ItemNBTHelper.setInt(stack, "ConsumptionMode", 0);
         }

         world.m_5594_(null, player.m_20183_(), SoundEvents.f_11871_, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2F));
      }

      player.m_6674_(hand);
      return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
   }

   public void m_6883_(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
      if (entity instanceof Player && entity.f_19797_ % 4 == 0) {
         Player player = (Player)entity;
         if (ItemNBTHelper.getBoolean(stack, "IsActive", true) && stack.m_41784_().m_128437_("SupersolidID", 8).size() >= 1) {
            CompoundTag nbt = stack.m_41784_();
            ListTag arr = nbt.m_128437_("SupersolidID", 8);
            consumeStuff(player, arr, ItemNBTHelper.getInt(stack, "ConsumptionMode", 0));
         }
      }
   }

   public static void consumeStuff(Player player, ListTag list, int mode) {
      HashMap<Integer, ItemStack> stackMap = new HashMap<>();
      int cycleCounter = 0;
      int filledStacks = 0;

      for (int slot = 0; slot < player.m_150109_().f_35974_.size(); slot++) {
         if (!((ItemStack)player.m_150109_().f_35974_.get(slot)).m_41619_()) {
            filledStacks++;
            if (((ItemStack)player.m_150109_().f_35974_.get(slot)).m_41720_() != EnigmaticItems.VOID_STONE) {
               stackMap.put(slot, (ItemStack)player.m_150109_().f_35974_.get(slot));
            }
         }
      }

      if (stackMap.size() != 0) {
         if (mode == 0) {
            for (Tag sID : list) {
               String str = ((StringTag)sID).m_7916_();

               for (int slotx : stackMap.keySet()) {
                  if (stackMap.get(slotx).m_41720_() == ForgeRegistries.ITEMS.getValue(new ResourceLocation(str))) {
                     player.m_150109_().m_6836_(slotx, ItemStack.f_41583_);
                  }
               }

               cycleCounter++;
            }
         } else if (mode == 1) {
            for (Tag sID : list) {
               String str = ((StringTag)sID).m_7916_();
               HashMap<Integer, ItemStack> localStackMap = new HashMap<>(stackMap);
               Multimap<Integer, Integer> stackSizeMultimap = ArrayListMultimap.create();

               for (int slotxx : stackMap.keySet()) {
                  if (stackMap.get(slotxx).m_41720_() != ForgeRegistries.ITEMS.getValue(new ResourceLocation(str))) {
                     localStackMap.remove(slotxx);
                  }
               }

               for (int slotxxx : localStackMap.keySet()) {
                  stackSizeMultimap.put(localStackMap.get(slotxxx).m_41613_(), slotxxx);
               }

               while (
                  localStackMap.size()
                     > (((ItemStack)player.m_150109_().f_35976_.get(0)).m_41720_() == ForgeRegistries.ITEMS.getValue(new ResourceLocation(str)) ? 0 : 1)
               ) {
                  int smallestStackSize = Collections.min(stackSizeMultimap.keySet());
                  Collection<Integer> smallestStacks = stackSizeMultimap.get(smallestStackSize);
                  int slotWithSmallestStack = Collections.max(smallestStacks);
                  player.m_150109_().m_6836_(slotWithSmallestStack, ItemStack.f_41583_);
                  stackSizeMultimap.remove(smallestStackSize, slotWithSmallestStack);
                  localStackMap.remove(slotWithSmallestStack);
               }

               cycleCounter++;
            }
         } else if (mode == 2 && filledStacks >= player.m_150109_().f_35974_.size()) {
            for (Tag sID : list) {
               String str = ((StringTag)sID).m_7916_();
               HashMap<Integer, ItemStack> localStackMap = new HashMap<>(stackMap);
               Multimap<Integer, Integer> stackSizeMultimap = ArrayListMultimap.create();

               for (int slotxxx : stackMap.keySet()) {
                  if (stackMap.get(slotxxx).m_41720_() != ForgeRegistries.ITEMS.getValue(new ResourceLocation(str))) {
                     localStackMap.remove(slotxxx);
                  }
               }

               for (int slotxxxx : localStackMap.keySet()) {
                  stackSizeMultimap.put(localStackMap.get(slotxxxx).m_41613_(), slotxxxx);
               }

               if (localStackMap.size() > 0) {
                  int smallestStackSize = Collections.min(stackSizeMultimap.keySet());
                  Collection<Integer> smallestStacks = stackSizeMultimap.get(smallestStackSize);
                  int slotWithSmallestStack = Collections.max(smallestStacks);
                  player.m_150109_().m_6836_(slotWithSmallestStack, ItemStack.f_41583_);
                  return;
               }

               cycleCounter++;
            }
         }
      }
   }
}
