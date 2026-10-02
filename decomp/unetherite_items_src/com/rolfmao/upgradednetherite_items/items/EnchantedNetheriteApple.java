package com.rolfmao.upgradednetherite_items.items;

import com.rolfmao.upgradedcore.helpers.TooltipHelper;
import com.rolfmao.upgradednetherite_items.config.UpgradedNetheriteItemsConfig;
import com.rolfmao.upgradednetherite_items.init.UpgradedNetheriteEffects;
import java.util.List;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnchantedNetheriteApple extends Item {
   public EnchantedNetheriteApple() {
      super(
         new Properties()
            .m_41491_(CreativeModeTab.f_40755_)
            .m_41497_(Rarity.EPIC)
            .m_41486_()
            .m_41489_(
               new Builder()
                  .m_38760_(0)
                  .m_38758_(0.0F)
                  .m_38762_(new MobEffectInstance(MobEffects.f_19612_, 3600, 3), 1.0F)
                  .m_38762_(new MobEffectInstance(MobEffects.f_19597_, 1600, 1), 1.0F)
                  .m_38762_(new MobEffectInstance(MobEffects.f_19607_, 6000, 0), 1.0F)
                  .effect(() -> new MobEffectInstance((MobEffect)UpgradedNetheriteEffects.NETHERITE_STRENGTH.get(), 3600, 1, false, true, true), 1.0F)
                  .effect(() -> new MobEffectInstance((MobEffect)UpgradedNetheriteEffects.NETHERITE_RESISTANCE.get(), 1600, 1, false, true, true), 1.0F)
                  .m_38765_()
                  .m_38767_()
            )
      );
   }

   public boolean m_5812_(ItemStack stack) {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip, flagIn);
      if (!UpgradedNetheriteItemsConfig.DisableTooltips) {
         tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
         if (Screen.m_96638_()) {
            TooltipHelper.addTWO(tooltip, "upgradednetherite_items.Netherite_Apple1.TT", new Object[]{"§5IV", "§503:00"});
            TooltipHelper.addTWO(tooltip, "upgradednetherite_items.Netherite_Apple2.TT", new Object[]{"§5II", "§501:20"});
            TooltipHelper.addTWO(tooltip, "upgradednetherite_items.Netherite_Apple3.TT", new Object[]{"§505:00"});
            TooltipHelper.addTWO(tooltip, "upgradednetherite_items.Netherite_Apple4.TT", new Object[]{"§5II", "§503:00"});
            TooltipHelper.addTWO(tooltip, "upgradednetherite_items.Netherite_Apple5.TT", new Object[]{"§5II", "§501:20"});
         } else {
            tooltip.add(Component.m_237115_("upgradednetherite.HoldShift.TT"));
         }
      }
   }
}
