package net.xylonity.knightquest.common.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.xylonity.knightquest.config.values.KQConfigValues;

public class KQFullSetChecker {
   public static boolean hasFullSetOn(Player player, ArmorMaterial material) {
      int requiredPieces = KQConfigValues.REQUIRED_ARMOR_PIECES;
      int equippedPieces = 0;

      for (ItemStack armorStack : player.m_150109_().f_35975_) {
         if (!armorStack.m_41619_()) {
            Item var7 = armorStack.m_41720_();
            if (var7 instanceof ArmorItem) {
               ArmorItem armorItem = (ArmorItem)var7;
               if (armorItem.m_40401_() == material) {
                  equippedPieces++;
               }
            }
         }
      }

      return equippedPieces >= requiredPieces;
   }
}
