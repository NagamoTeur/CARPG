package com.github.L_Ender.cataclysm.items.CuriosItem;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio.SoundInfo;

public class Blazing_Grips extends CuriosItem {
   public Blazing_Grips(Properties group) {
      super(group);
   }

   public SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
      return new SoundInfo(SoundEvents.f_11679_, 1.0F, 1.0F);
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.cataclysm.blazing_grips.desc").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
