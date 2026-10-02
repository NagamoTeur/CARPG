package com.cerbon.bosses_of_mass_destruction.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MaterialItem extends Item {
   public MaterialItem(Properties properties) {
      super(properties);
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
      tooltipComponents.add(Component.m_237115_("item.bosses_of_mass_destruction.crafting_material.tooltip").m_130940_(ChatFormatting.DARK_GRAY));
      super.m_7373_(stack, level, tooltipComponents, isAdvanced);
   }
}
