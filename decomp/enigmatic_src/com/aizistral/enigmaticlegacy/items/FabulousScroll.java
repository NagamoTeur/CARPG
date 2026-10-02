package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ExperienceHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBaseCurio;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;

public class FabulousScroll extends HeavenScroll {
   public FabulousScroll() {
      super(ItemBaseCurio.getDefaultProperties().m_41497_(Rarity.EPIC));
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.fabulousScroll1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.fabulousScroll2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.fabulousScroll3");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.fabulousScroll4");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.fabulousScroll5");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }
   }

   @Override
   public void curioTick(SlotContext context, ItemStack stack) {
      if (!context.entity().f_19853_.f_46443_) {
         if (context.entity() instanceof Player player) {
            boolean inRange = SuperpositionHandler.isInBeaconRange(player);
            if (!SuperpositionHandler.isInBeaconRange(player) && Math.random() <= 0.0125 * 8.0 * xpCostModifier.getValue() && player.m_150110_().f_35935_) {
               ExperienceHelper.drainPlayerXP(player, 1);
            }

            this.handleFabulousFlight(player, inRange);
         }
      }
   }

   protected void handleFabulousFlight(Player player, boolean inRange) {
      try {
         if (ExperienceHelper.getPlayerXP(player) <= 0 && !inRange) {
            if (this.flyMap.get(player) > 1) {
               this.flyMap.put(player, this.flyMap.get(player) - 1);
            } else if (this.flyMap.get(player) == 1) {
               if (!player.m_7500_()) {
                  player.m_150110_().f_35936_ = false;
                  player.m_150110_().f_35935_ = false;
                  player.m_6885_();
                  player.m_7292_(new MobEffectInstance(MobEffects.f_19591_, 200, 0, true, false));
               }

               this.flyMap.put(player, 0);
            }
         } else {
            if (!player.m_150110_().f_35936_) {
               player.m_150110_().f_35936_ = true;
            }

            player.m_6885_();
            this.flyMap.put(player, 100);
         }
      } catch (NullPointerException var4) {
         this.flyMap.put(player, 0);
      }
   }

   @Override
   public void onUnequip(SlotContext context, ItemStack newStack, ItemStack stack) {
      if (context.entity() instanceof Player player) {
         if (!player.m_7500_()) {
            player.m_150110_().f_35936_ = false;
            player.m_150110_().f_35935_ = false;
            player.m_6885_();
         }

         this.flyMap.put(player, 0);
      }
   }
}
