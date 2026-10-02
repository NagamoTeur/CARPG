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

public class NetheriteApple extends Item {
   public NetheriteApple() {
      super(
         new Properties()
            .m_41491_(CreativeModeTab.f_40755_)
            .m_41497_(Rarity.RARE)
            .m_41486_()
            .m_41489_(
               new Builder()
                  .m_38760_(0)
                  .m_38758_(0.0F)
                  .m_38762_(new MobEffectInstance(MobEffects.f_19612_, 900, 1), 1.0F)
                  .m_38762_(new MobEffectInstance(MobEffects.f_19597_, 400, 0), 1.0F)
                  .m_38762_(new MobEffectInstance(MobEffects.f_19607_, 1200, 0), 1.0F)
                  .effect(() -> new MobEffectInstance((MobEffect)UpgradedNetheriteEffects.NETHERITE_STRENGTH.get(), 900, 0, false, true, true), 1.0F)
                  .effect(() -> new MobEffectInstance((MobEffect)UpgradedNetheriteEffects.NETHERITE_RESISTANCE.get(), 400, 0, false, true, true), 1.0F)
                  .m_38765_()
                  .m_38767_()
            )
      );
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip, flagIn);
      if (!UpgradedNetheriteItemsConfig.DisableTooltips) {
         tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
         if (Screen.m_96638_()) {
            TooltipHelper.addTWO(tooltip, "upgradednetherite_items.Netherite_Apple1.TT", new Object[]{"§5II", "§500:45"});
            TooltipHelper.addTWO(tooltip, "upgradednetherite_items.Netherite_Apple2.TT", new Object[]{"§5I", "§500:20"});
            TooltipHelper.addTWO(tooltip, "upgradednetherite_items.Netherite_Apple3.TT", new Object[]{"§501:00"});
            TooltipHelper.addTWO(tooltip, "upgradednetherite_items.Netherite_Apple4.TT", new Object[]{"§5I", "§500:45"});
            TooltipHelper.addTWO(tooltip, "upgradednetherite_items.Netherite_Apple5.TT", new Object[]{"§5I", "§500:20"});
         } else {
            tooltip.add(Component.m_237115_("upgradednetherite.HoldShift.TT"));
         }
      }
   }
}
