package io.redspace.ironsspellbooks.capabilities.magic;

import io.redspace.ironsspellbooks.item.armor.UpgradeType;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class UpgradeData {
   public static final String Upgrades = "ISBUpgrades";
   public static final String Upgrade_Key = "id";
   public static final String Slot_Key = "slot";
   public static final String Upgrade_Count = "upgrades";
   public static final UpgradeData NONE = new UpgradeData(Map.of(), EquipmentSlot.MAINHAND.m_20751_());
   private final Map<UpgradeType, Integer> upgrades;
   private String upgradedSlot;

   protected UpgradeData(Map<UpgradeType, Integer> upgrades, String slot) {
      this.upgrades = upgrades;
      this.upgradedSlot = slot;
   }

   public static UpgradeData getUpgradeData(ItemStack itemStack) {
      if (!hasUpgradeData(itemStack)) {
         return NONE;
      } else {
         ListTag upgrades = itemStack.m_41784_().m_128437_("ISBUpgrades", 10);
         Map<UpgradeType, Integer> map = new HashMap<>();
         String upgradedSlot = null;

         for (Tag tag : upgrades) {
            if (tag instanceof CompoundTag) {
               CompoundTag compoundTag = (CompoundTag)tag;
               if (upgradedSlot == null) {
                  upgradedSlot = compoundTag.m_128461_("slot");
               }

               ResourceLocation upgradeKey = new ResourceLocation(compoundTag.m_128461_("id"));
               UpgradeType.getUpgrade(upgradeKey).ifPresent(upgrade -> map.put(upgrade, compoundTag.m_128451_("upgrades")));
            }
         }

         return new UpgradeData(map, upgradedSlot);
      }
   }

   public static boolean hasUpgradeData(ItemStack itemStack) {
      return itemStack.m_41783_() != null && itemStack.m_41783_().m_128441_("ISBUpgrades");
   }

   public static void setUpgradeData(ItemStack itemStack, UpgradeData upgradeData) {
      if (upgradeData == NONE) {
         if (hasUpgradeData(itemStack)) {
            itemStack.m_41749_("ISBUpgrades");
         }
      } else {
         ListTag upgrades = new ListTag();

         for (Entry<UpgradeType, Integer> upgradeInstance : upgradeData.upgrades.entrySet()) {
            CompoundTag upgradeTag = new CompoundTag();
            upgradeTag.m_128359_("id", upgradeInstance.getKey().getId().toString());
            upgradeTag.m_128359_("slot", upgradeData.upgradedSlot);
            upgradeTag.m_128405_("upgrades", upgradeInstance.getValue());
            upgrades.add(upgradeTag);
         }

         itemStack.m_41700_("ISBUpgrades", upgrades);
      }
   }

   public static void removeUpgradeData(ItemStack itemStack) {
      setUpgradeData(itemStack, NONE);
   }

   public UpgradeData addUpgrade(ItemStack stack, UpgradeType upgradeType, String slot) {
      if (this == NONE) {
         Map<UpgradeType, Integer> map = new HashMap<>();
         map.put(upgradeType, 1);
         UpgradeData upgrade = new UpgradeData(map, slot);
         setUpgradeData(stack, upgrade);
         return upgrade;
      } else {
         if (this.upgrades.containsKey(upgradeType)) {
            this.upgrades.put(upgradeType, this.upgrades.get(upgradeType) + 1);
         } else {
            this.upgrades.put(upgradeType, 1);
         }

         setUpgradeData(stack, this);
         return this;
      }
   }

   public int getCount() {
      int count = 0;

      for (Entry<UpgradeType, Integer> upgradeInstance : this.upgrades.entrySet()) {
         count += upgradeInstance.getValue();
      }

      return count;
   }

   public String getUpgradedSlot() {
      return this.upgradedSlot;
   }

   public Map<UpgradeType, Integer> getUpgrades() {
      return this.upgrades;
   }
}
