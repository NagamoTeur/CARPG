package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.api.items.ICursed;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBaseCurio;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;

public class AvariceScroll extends ItemBaseCurio implements ICursed {
   public static Omniconfig.IntParameter emeraldChance;

   @SubscribeConfig
   public static void onConfig(OmniconfigWrapper builder) {
      builder.pushPrefix("AvariceScroll");
      emeraldChance = builder.comment("Chance to get an Emerald when slaying any mob with Pact of Infinite Avarice equipped.")
         .max(100.0)
         .getInt("EmeraldChance", 15);
      builder.popPrefix();
   }

   public AvariceScroll() {
      super(ItemBaseCurio.getDefaultProperties().m_41497_(Rarity.EPIC).m_41487_(1).m_41486_());
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.avariceScroll1", ChatFormatting.GOLD, 1);
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.avariceScroll2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.avariceScroll3");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.avariceScroll4");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.avariceScroll5", ChatFormatting.GOLD, emeraldChance.getValue() + "%");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.avariceScroll6");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.avariceScroll7");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      ItemLoreHelper.indicateCursedOnesOnly(list);
   }

   @Override
   public boolean canEquip(SlotContext context, ItemStack stack) {
      return super.canEquip(context, stack) && context.entity() instanceof Player player && SuperpositionHandler.isTheCursedOne(player);
   }

   public int getFortuneLevel(SlotContext slotContext, LootContext lootContext, ItemStack curio) {
      return super.getFortuneLevel(slotContext, lootContext, curio) + 1;
   }
}
