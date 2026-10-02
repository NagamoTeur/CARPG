package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBaseFood;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AstralPotato extends ItemBaseFood {
   public AstralPotato() {
      super(getDefaultProperties().m_41497_(Rarity.EPIC).m_41486_(), new Builder().m_38760_(8).m_38758_(1.4F).m_38767_());
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.astralPotato1");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }
   }

   public boolean m_5812_(ItemStack pStack) {
      return super.m_5812_(pStack);
   }

   @Override
   public void onConsumed(Level worldIn, Player player, ItemStack food) {
      if (player instanceof ServerPlayer playerMP) {
         player.f_19853_.m_5594_(null, player.m_20183_(), SoundEvents.f_11757_, SoundSource.PLAYERS, 0.5F, 1.0F);
         player.m_6021_(player.m_20185_() + (0.5 - random.nextDouble()) * 4.0, player.m_20186_(), player.m_20189_() + (0.5 - random.nextDouble()) * 4.0);
      }
   }
}
