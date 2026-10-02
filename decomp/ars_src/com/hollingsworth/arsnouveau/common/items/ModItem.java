package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ModItem extends Item {
   public List<Component> tooltip = new ArrayList<>();
   public Rarity f_41369_;

   public ModItem(Properties properties) {
      super(properties);
   }

   public ModItem() {
      this(ItemsRegistry.defaultItemProperties());
   }

   public ModItem withTooltip(Component tip) {
      this.tooltip.add(tip);
      return this;
   }

   public ModItem withTooltip(String tip) {
      this.tooltip.add(Component.m_237115_(tip));
      return this;
   }

   public ModItem withRarity(Rarity rarity) {
      this.f_41369_ = rarity;
      return this;
   }

   public Rarity m_41460_(ItemStack stack) {
      return this.f_41369_ != null ? this.f_41369_ : super.m_41460_(stack);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      if (this.tooltip != null && !this.tooltip.isEmpty()) {
         tooltip2.addAll(this.tooltip);
      }
   }
}
