package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.api.items.ICursed;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import java.util.List;
import javax.annotation.Nullable;
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
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TwistedMirror extends ItemBase implements ICursed, Vanishable {
   public TwistedMirror() {
      super(ItemBase.getDefaultProperties().m_41497_(Rarity.RARE).m_41487_(1));
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.twistedMirror1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.twistedMirror2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.twistedMirror3");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      ItemLoreHelper.indicateCursedOnesOnly(list);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      if (EnigmaticLegacy.PROXY.isInVanillaDimension(player) && SuperpositionHandler.isTheCursedOne(player) && !player.m_36335_().m_41519_(this)) {
         player.m_6672_(hand);
         if (player instanceof ServerPlayer) {
            SuperpositionHandler.backToSpawn((ServerPlayer)player);
            player.m_36335_().m_41524_(this, 200);
         }

         return new InteractionResultHolder(InteractionResult.SUCCESS, player.m_21120_(hand));
      } else {
         return new InteractionResultHolder(InteractionResult.PASS, player.m_21120_(hand));
      }
   }
}
