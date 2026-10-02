package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

public class GemOfBinding extends ItemBase implements Vanishable {
   public GemOfBinding() {
      super(ItemBase.getDefaultProperties().m_41497_(Rarity.UNCOMMON).m_41487_(1));
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.gemOfBinding1");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      if (ItemNBTHelper.verifyExistance(stack, "BoundPlayer")) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(
            list, "tooltip.enigmaticlegacy.boundToPlayer", ChatFormatting.DARK_RED, ItemNBTHelper.getString(stack, "BoundPlayer", "Herobrine")
         );
      }
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_5812_(ItemStack stack) {
      return ItemNBTHelper.verifyExistance(stack, "BoundPlayer") || super.m_5812_(stack);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      if (player.m_6047_()) {
         ItemNBTHelper.setString(itemstack, "BoundPlayer", player.m_5446_().getString());
         ItemNBTHelper.setUUID(itemstack, "BoundUUID", player.m_20148_());
         worldIn.m_5594_(null, player.m_20183_(), SoundEvents.f_11887_, SoundSource.PLAYERS, 1.0F, (float)(0.9F + Math.random() * 0.1F));
         player.m_6674_(hand);
         return new InteractionResultHolder(InteractionResult.SUCCESS, itemstack);
      } else {
         return new InteractionResultHolder(InteractionResult.FAIL, itemstack);
      }
   }
}
