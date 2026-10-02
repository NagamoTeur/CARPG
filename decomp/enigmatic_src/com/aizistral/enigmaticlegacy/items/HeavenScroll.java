package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ExperienceHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBaseCurio;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;

public class HeavenScroll extends ItemBaseCurio {
   public static Omniconfig.DoubleParameter xpCostModifier;
   public Map<Player, Integer> flyMap = new WeakHashMap<>();
   public final double baseXpConsumptionProbability = 0.0125;

   @SubscribeConfig
   public static void onConfig(OmniconfigWrapper builder) {
      builder.pushPrefix("HeavenScroll");
      xpCostModifier = builder.comment("Multiplier for experience consumption by Gift of the Heaven.").getDouble("XPCostModifier", 1.0);
      builder.popPrefix();
   }

   public HeavenScroll() {
      super(ItemBaseCurio.getDefaultProperties().m_41497_(Rarity.EPIC));
   }

   public HeavenScroll(Properties properties) {
      super(properties);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.heavenTome1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.heavenTome2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.heavenTome3");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.heavenTome4");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }
   }

   @Override
   public void curioTick(SlotContext context, ItemStack stack) {
      if (!context.entity().f_19853_.f_46443_) {
         if (context.entity() instanceof Player player) {
            if (Math.random() <= 0.0125 * xpCostModifier.getValue() && player.m_150110_().f_35935_) {
               ExperienceHelper.drainPlayerXP(player, 1);
            }

            this.handleFlight(player);
         }
      }
   }

   protected void handleFlight(Player player) {
      try {
         if (ExperienceHelper.getPlayerXP(player) > 0 && SuperpositionHandler.isInBeaconRange(player)) {
            if (!player.m_150110_().f_35936_) {
               player.m_150110_().f_35936_ = true;
            }

            player.m_6885_();
            this.flyMap.put(player, 100);
         } else if (this.flyMap.get(player) > 1) {
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
      } catch (NullPointerException var3) {
         var3.printStackTrace();
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
