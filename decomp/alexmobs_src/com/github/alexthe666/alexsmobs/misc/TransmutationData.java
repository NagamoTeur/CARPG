package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.config.AMConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public class TransmutationData {
   private Map<ItemStack, Double> itemstackData = new HashMap<>();

   public void onTransmuteItem(ItemStack beingTransmuted, ItemStack turnedInto) {
      double fromWeight = this.getWeight(beingTransmuted);
      double toWeight = this.getWeight(turnedInto);
      this.putWeight(beingTransmuted, fromWeight + calculateAddWeight(beingTransmuted.m_41613_()));
      this.putWeight(turnedInto, toWeight + calculateRemoveWeight(turnedInto.m_41613_()));
   }

   public double getWeight(ItemStack stack) {
      for (Entry<ItemStack, Double> entry : this.itemstackData.entrySet()) {
         if (ItemStack.m_150942_(stack, entry.getKey())) {
            return entry.getValue();
         }
      }

      return 0.0;
   }

   private static double calculateAddWeight(int count) {
      return Math.log(Math.pow((double)count, AMConfig.transmutingWeightAddStep));
   }

   private static double calculateRemoveWeight(int count) {
      return -Math.log(Math.pow((double)count, AMConfig.transmutingWeightRemoveStep));
   }

   public void putWeight(ItemStack stack, double newWeight) {
      ItemStack replace = stack;

      for (Entry<ItemStack, Double> entry : this.itemstackData.entrySet()) {
         if (ItemStack.m_150942_(stack, entry.getKey())) {
            replace = entry.getKey();
            break;
         }
      }

      this.itemstackData.put(replace, Math.max(newWeight, 0.0));
   }

   @Nullable
   public ItemStack getRandomItem(RandomSource random) {
      ItemStack result = null;
      double bestValue = Double.MAX_VALUE;

      for (Entry<ItemStack, Double> entry : this.itemstackData.entrySet()) {
         if (!(entry.getValue() <= 0.0)) {
            double value = -Math.log(random.m_188500_()) / entry.getValue();
            if (value < bestValue) {
               bestValue = value;
               result = entry.getKey().m_41777_();
            }
         }
      }

      return result;
   }

   public CompoundTag saveAsNBT() {
      CompoundTag compound = new CompoundTag();
      ListTag listTag = new ListTag();

      for (Entry<ItemStack, Double> entry : this.itemstackData.entrySet()) {
         CompoundTag tag = new CompoundTag();
         tag.m_128365_("Item", entry.getKey().m_41739_(new CompoundTag()));
         tag.m_128347_("Weight", entry.getValue());
         listTag.add(tag);
      }

      compound.m_128365_("TransmutationData", listTag);
      return compound;
   }

   public static TransmutationData fromNBT(CompoundTag compound) {
      TransmutationData data = new TransmutationData();
      if (compound.m_128441_("TransmutationData")) {
         ListTag listtag = compound.m_128437_("TransmutationData", 10);

         for (int i = 0; i < listtag.size(); i++) {
            CompoundTag innerTag = listtag.m_128728_(i);

            try {
               ItemStack from = ItemStack.m_41712_(innerTag.m_128469_("Item"));
               if (!from.m_41619_()) {
                  data.putWeight(from, innerTag.m_128459_("Weight"));
               }
            } catch (Exception var6) {
               var6.printStackTrace();
            }
         }
      }

      return data;
   }

   public double getTotalWeight() {
      double total = 0.0;

      for (Entry<ItemStack, Double> entry : this.itemstackData.entrySet()) {
         total += entry.getValue();
      }

      return total;
   }
}
