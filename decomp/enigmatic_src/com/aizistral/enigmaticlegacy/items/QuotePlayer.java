package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.api.items.IHidden;
import com.aizistral.enigmaticlegacy.client.Quote;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class QuotePlayer extends ItemBase implements IHidden {
   public QuotePlayer() {
      super(getDefaultProperties().m_41497_(Rarity.EPIC).m_41487_(1).m_41486_());
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.quotePlayer1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.quotePlayer2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.quotePlayer3");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      ItemLoreHelper.addLocalizedString(
         list, "tooltip.enigmaticlegacy.quotePlayerSelection", ChatFormatting.GOLD, Quote.getAllQuotes().get(stack.m_41773_()).getName().toUpperCase()
      );
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      if (player.m_6047_()) {
         player.m_6672_(hand);
         if (itemstack.m_41773_() < Quote.getAllQuotes().size() - 1) {
            itemstack.m_41721_(itemstack.m_41773_() + 1);
         } else {
            itemstack.m_41721_(0);
         }

         player.m_6674_(hand);
         if (player instanceof ServerPlayer) {
            player.m_5661_(Component.m_237113_("Quote: " + Quote.getAllQuotes().get(itemstack.m_41773_()).getName().toUpperCase()), true);
         }
      } else if (player instanceof ServerPlayer splayer) {
         Quote.getAllQuotes().get(itemstack.m_41773_()).play(splayer, 10);
      }

      return new InteractionResultHolder(InteractionResult.SUCCESS, itemstack);
   }

   public InteractionResult m_6225_(UseOnContext context) {
      return this.m_7203_(context.m_43725_(), context.m_43723_(), context.m_43724_()).m_19089_();
   }
}
