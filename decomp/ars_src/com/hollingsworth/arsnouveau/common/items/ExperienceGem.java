package com.hollingsworth.arsnouveau.common.items;

import java.util.Map.Entry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public abstract class ExperienceGem extends ModItem {
   public ExperienceGem(Properties properties) {
      super(properties);
   }

   public ExperienceGem() {
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player playerEntity, InteractionHand hand) {
      if (!world.f_46443_) {
         if (playerEntity.m_6047_()) {
            int val = this.getValue() * playerEntity.m_21120_(hand).m_41613_();
            val = this.repairPlayerItems(playerEntity, val, val);
            if (val > 0) {
               playerEntity.m_6756_(val);
            }

            playerEntity.m_21120_(hand).m_41774_(playerEntity.m_21120_(hand).m_41613_());
         } else {
            int val = this.getValue();
            val = this.repairPlayerItems(playerEntity, val, val);
            if (val > 0) {
               playerEntity.m_6756_(val);
            }

            playerEntity.m_21120_(hand).m_41774_(1);
         }
      }

      return InteractionResultHolder.m_19098_(playerEntity.m_21120_(hand));
   }

   public int repairPlayerItems(Player p_147093_, int remainingExp, int initialValue) {
      Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.m_44839_(Enchantments.f_44962_, p_147093_, ItemStack::m_41768_);
      if (entry != null) {
         ItemStack itemstack = entry.getValue();
         int i = Math.min((int)((float)initialValue * itemstack.getXpRepairRatio()), itemstack.m_41773_());
         itemstack.m_41721_(itemstack.m_41773_() - i);
         int j = remainingExp - this.durabilityToXp(i);
         return j > 0 ? this.repairPlayerItems(p_147093_, j, initialValue) : 0;
      } else {
         return remainingExp;
      }
   }

   public int durabilityToXp(int pDurability) {
      return pDurability / 2;
   }

   public abstract int getValue();
}
