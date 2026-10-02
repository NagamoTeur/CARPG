package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity;
import com.github.L_Ender.cataclysm.init.ModKeybind;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class Sandstorm_In_A_Bottle extends Item {
   public Sandstorm_In_A_Bottle(Properties properties) {
      super(properties);
   }

   @Nonnull
   public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, @Nonnull InteractionHand hand) {
      ItemStack stack = player.m_21120_(hand);
      if (!level.m_5776_()) {
         for (int i = 0; i < 2; i++) {
            float angle = (float)i * (float) Math.PI;
            double sx = player.m_20185_() + (double)(Mth.m_14089_(angle) * 6.0F);
            double sy = player.m_20186_();
            double sz = player.m_20189_() + (double)(Mth.m_14031_(angle) * 6.0F);
            Sandstorm_Entity projectile = new Sandstorm_Entity(player.f_19853_, sx, sy, sz, 200, angle, player.m_20148_());
            player.f_19853_.m_7967_(projectile);
         }
      }

      if (!level.f_46443_) {
         player.m_36335_().m_41524_(this, CMConfig.SandstormInABottleCOOLDOWN);
      }

      return InteractionResultHolder.m_19090_(stack);
   }

   public boolean m_8120_(ItemStack stack) {
      return false;
   }

   public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
      return false;
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(
         Component.m_237110_("item.cataclysm.sandstorm_in_a_bottle.desc", new Object[]{ModKeybind.KEY_ABILITY.m_90863_()}).m_130940_(ChatFormatting.DARK_GREEN)
      );
   }
}
