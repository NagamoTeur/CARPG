package com.majruszsaccessories.listeners;

import com.majruszlibrary.annotation.AutoInstance;
import com.majruszlibrary.client.ClientHelper;
import com.majruszlibrary.events.OnItemTooltip;
import com.majruszlibrary.events.base.Condition;
import com.majruszlibrary.events.base.Events;
import com.majruszlibrary.math.Range;
import com.majruszlibrary.text.TextHelper;
import com.majruszlibrary.time.TimeHelper;
import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.common.AccessoryHolder;
import com.majruszsaccessories.common.AccessoryHolders;
import com.majruszsaccessories.events.OnAccessoryTooltip;
import com.majruszsaccessories.items.AccessoryItem;
import com.majruszsaccessories.tooltip.TooltipHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

@AutoInstance
public class AccessoryTooltipUpdater {
   static final int PAGE_SIZE = 7;

   public AccessoryTooltipUpdater() {
      OnItemTooltip.listen(this::addTooltip).addCondition(Condition.predicate(data -> data.itemStack.m_41720_() instanceof AccessoryItem));
   }

   private void addTooltip(OnItemTooltip data) {
      AccessoryHolder holder = Optional.ofNullable(data.player)
         .map(player -> AccessoryHolders.find(player).get().stream().filter(subholder -> subholder.getItemStack() == data.itemStack).findFirst())
         .flatMap(subholder -> subholder)
         .orElse(AccessoryHolder.create(data.itemStack));
      if (holder.hasBonusRangeDefined() && !holder.hasBonusDefined()) {
         data.components.addAll(this.buildBonusRangeInfo(holder));
      } else {
         data.components.addAll(this.buildBonusInfo(holder));
      }

      data.components.addAll(this.buildUseInfo(holder, data.player));
      data.components.addAll(this.buildEffectsInfo(holder));
   }

   private List<Component> buildBonusRangeInfo(AccessoryHolder holder) {
      Range<Float> range = holder.getClampedBonusRange();
      Component min = TextHelper.literal(TextHelper.signedPercent((Float)range.from)).m_130940_(AccessoryHolder.getBonusFormatting((Float)range.from));
      Component max = TextHelper.literal(TextHelper.signedPercent((Float)range.to)).m_130940_(AccessoryHolder.getBonusFormatting((Float)range.to));
      return List.of(TextHelper.translatable("majruszsaccessories.items.bonus", new Object[]{TooltipHelper.asRange(min, max)}).m_130940_(ChatFormatting.GRAY));
   }

   private List<Component> buildBonusInfo(AccessoryHolder holder) {
      float baseBonus = holder.getBaseBonus();
      float bonus = holder.getBonus();
      if (baseBonus == 0.0F && bonus == 0.0F) {
         return List.of();
      } else {
         MutableComponent component;
         if (ClientHelper.isShiftDown() && holder.getExtraBonus() != 0.0F) {
            component = TooltipHelper.asFormula(
               TextHelper.literal(TextHelper.signedPercent(baseBonus)).m_130940_(AccessoryHolder.getBonusFormatting(baseBonus)),
               TextHelper.literal(TextHelper.signedPercent(holder.getExtraBonus())).m_130940_(AccessoryHolder.getBonusFormatting(bonus))
            );
         } else {
            component = TextHelper.literal(TextHelper.signedPercent(bonus));
         }

         return List.of(TextHelper.translatable("majruszsaccessories.items.bonus", new Object[]{component}).m_130940_(holder.getBonusFormatting()));
      }
   }

   private List<Component> buildUseInfo(AccessoryHolder holder, @Nullable Player player) {
      return MajruszsAccessories.SLOT_INTEGRATION.isInstalled()
         ? List.of()
         : List.of(TextHelper.translatable("majruszsaccessories.items.accessory_item", new Object[0]).m_130940_(this.getUseFormatting(holder, player)));
   }

   private ChatFormatting getUseFormatting(AccessoryHolder holder, @Nullable Player player) {
      return player != null && AccessoryHolders.get(player).get(holder::getItem) == holder ? ChatFormatting.GOLD : ChatFormatting.DARK_GRAY;
   }

   private List<Component> buildEffectsInfo(AccessoryHolder holder) {
      OnAccessoryTooltip tooltipData = (OnAccessoryTooltip)Events.dispatch(new OnAccessoryTooltip(holder));
      boolean cannotFitSinglePage = tooltipData.components.size() > 7;
      return cannotFitSinglePage ? this.getCurrentPageSublist(tooltipData.components) : tooltipData.components;
   }

   private List<Component> getCurrentPageSublist(List<Component> components) {
      int totalPages = (int)Math.ceil((double)components.size() / 7.0);
      int currentPage = (int)(Math.floor((double)TimeHelper.getTicks() / (double)TimeHelper.toTicks(14.0)) % (double)totalPages);
      List<Component> pageComponents = new ArrayList<>(components.subList(currentPage * 7, Math.min((currentPage + 1) * 7, components.size())));
      pageComponents.add(
         TextHelper.translatable("majruszsaccessories.items.page", new Object[]{currentPage + 1, totalPages}).m_130940_(ChatFormatting.DARK_GRAY)
      );
      return pageComponents;
   }

   static final class Tooltips {
      static final String INVENTORY = "majruszsaccessories.items.accessory_item";
      static final String BONUS = "majruszsaccessories.items.bonus";
      static final String PAGE = "majruszsaccessories.items.page";
   }
}
