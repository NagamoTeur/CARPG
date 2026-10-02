package com.hollingsworth.arsnouveau.common.items;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class FireEssence extends ModItem {
   public FireEssence(Properties properties) {
      super(properties);
   }

   public FireEssence() {
   }

   public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
      return 4000;
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip2, flagIn);
      tooltip2.add(Component.m_237115_("ars_nouveau.fire_essence.tooltip").m_130948_(Style.f_131099_.m_131140_(ChatFormatting.GOLD)));
   }
}
