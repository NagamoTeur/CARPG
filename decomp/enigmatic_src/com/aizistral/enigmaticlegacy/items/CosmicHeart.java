package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CosmicHeart extends ItemBase implements Vanishable {
   public static BiConsumer<ItemStack, Player> blessableHandler = (stack, player) -> {
   };
   public static BiConsumer<ItemStack, List<Component>> tooltipHandler = (stack, tooltip) -> {
   };

   public CosmicHeart() {
      super(ItemBase.getDefaultProperties().m_41497_(Rarity.EPIC).m_41487_(1));
   }

   public void m_6883_(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (entityIn instanceof Player && !entityIn.f_19853_.f_46443_) {
         Player player = (Player)entityIn;
         if (SuperpositionHandler.isTheBlessedOne(player)) {
            if (!ItemNBTHelper.getBoolean(stack, "isBelieverBlessed", false)) {
               ItemNBTHelper.setBoolean(stack, "isBelieverBlessed", true);
            }
         } else if (ItemNBTHelper.getBoolean(stack, "isBelieverBlessed", false)) {
            ItemNBTHelper.setBoolean(stack, "isBelieverBlessed", false);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      if (ItemNBTHelper.getBoolean(stack, "isBelieverBlessed", false)) {
         ItemLoreHelper.addLocalizedString(tooltip, "tooltip.enigmaticlegacy.blessed1");
         ItemLoreHelper.addLocalizedString(tooltip, "tooltip.enigmaticlegacy.blessed2");
      }
   }
}
