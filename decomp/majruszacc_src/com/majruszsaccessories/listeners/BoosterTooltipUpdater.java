package com.majruszsaccessories.listeners;

import com.majruszlibrary.annotation.AutoInstance;
import com.majruszlibrary.events.OnItemTooltip;
import com.majruszlibrary.events.base.Condition;
import com.majruszlibrary.events.base.Events;
import com.majruszlibrary.text.TextHelper;
import com.majruszsaccessories.events.OnBoosterTooltip;
import com.majruszsaccessories.items.BoosterItem;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

@AutoInstance
public class BoosterTooltipUpdater {
   public BoosterTooltipUpdater() {
      OnItemTooltip.listen(this::addTooltip).addCondition(Condition.predicate(data -> data.itemStack.m_41720_() instanceof BoosterItem));
   }

   private void addTooltip(OnItemTooltip data) {
      data.components.addAll(this.buildGenericInfo());
      data.components.addAll(this.buildEffectsInfo(data));
   }

   private List<Component> buildGenericInfo() {
      return List.of(TextHelper.translatable("majruszsaccessories.items.booster_tooltip", new Object[0]).m_130940_(ChatFormatting.GOLD));
   }

   private List<Component> buildEffectsInfo(OnItemTooltip data) {
      return ((OnBoosterTooltip)Events.dispatch(new OnBoosterTooltip((BoosterItem)data.itemStack.m_41720_()))).components;
   }

   static final class Tooltips {
      static final String INFO = "majruszsaccessories.items.booster_tooltip";
   }
}
