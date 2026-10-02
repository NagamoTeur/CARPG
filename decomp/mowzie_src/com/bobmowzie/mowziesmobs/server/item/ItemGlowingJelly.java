package com.bobmowzie.mowziesmobs.server.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class ItemGlowingJelly extends Item {
   public static FoodProperties GLOWING_JELLY_FOOD = new Builder()
      .m_38760_(1)
      .m_38758_(0.1F)
      .m_38762_(new MobEffectInstance(MobEffects.f_19611_, 1200, 0), 1.0F)
      .m_38767_();

   public ItemGlowingJelly(Properties properties) {
      super(properties);
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip, flagIn);
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.0").m_6270_(ItemHandler.TOOLTIP_STYLE));
   }
}
