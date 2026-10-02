package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class ItemGrantSunsBlessing extends Item {
   public ItemGrantSunsBlessing(Properties properties) {
      super(properties);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand hand) {
      playerIn.m_7292_(
         new MobEffectInstance(
            (MobEffect)EffectHandler.SUNS_BLESSING.get(),
            (Integer)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.effectDuration.get() * 60 * 20,
            0,
            false,
            false
         )
      );
      return super.m_7203_(worldIn, playerIn, hand);
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip, flagIn);
      int effectDuration = (Integer)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.effectDuration.get();
      int solarBeamCost = (Integer)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.solarBeamCost.get();
      int supernovaCost = (Integer)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.supernovaCost.get();
      tooltip.add(
         Component.m_237115_(this.m_5524_() + ".text.0")
            .m_130946_(" " + effectDuration + " ")
            .m_7220_(Component.m_237115_(this.m_5524_() + ".text.1"))
            .m_6270_(ItemHandler.TOOLTIP_STYLE)
      );
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.2").m_6270_(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.3").m_6270_(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(
         Component.m_237115_(this.m_5524_() + ".text.4")
            .m_130946_(" " + solarBeamCost + " ")
            .m_7220_(Component.m_237115_(this.m_5524_() + ".text.5"))
            .m_6270_(ItemHandler.TOOLTIP_STYLE)
      );
      MutableComponent supernovaComponent = Component.m_237115_(this.m_5524_() + ".text.6");
      if (supernovaCost >= effectDuration) {
         supernovaComponent.m_7220_(Component.m_237115_(this.m_5524_() + ".text.7"));
      } else {
         supernovaComponent.m_130946_(" " + supernovaCost + " minutes");
      }

      supernovaComponent.m_7220_(Component.m_237115_(this.m_5524_() + ".text.8"));
      supernovaComponent.m_130948_(ItemHandler.TOOLTIP_STYLE);
      tooltip.add(supernovaComponent);
   }
}
