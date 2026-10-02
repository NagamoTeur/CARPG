package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBasePotion;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IchorBottle extends ItemBasePotion {
   public IchorBottle() {
      super(getDefaultProperties().m_41487_(1).m_41497_(Rarity.UNCOMMON).m_41486_());
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.ichorBottle1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.ichorBottle2");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }
   }

   @Override
   public boolean m_5812_(ItemStack pStack) {
      return true;
   }

   @Override
   public void onConsumed(Level worldIn, Player player, ItemStack potion) {
      if (player instanceof ServerPlayer playerMP) {
         SuperpositionHandler.setPersistentBoolean(playerMP, "ConsumedIchorBottle", true);
         SuperpositionHandler.unlockSpecialSlot("charm", playerMP);
         playerMP.f_19853_.m_5594_(null, playerMP.m_20183_(), SoundEvents.f_11736_, SoundSource.PLAYERS, 1.0F, 1.0F);
         double multiplier = 1.0;
         player.m_7292_(new MobEffectInstance(MobEffects.f_19605_, (int)(1200.0 * multiplier), 2, false, true));
         player.m_7292_(new MobEffectInstance(MobEffects.f_19617_, (int)(800.0 * multiplier), 4, false, true));
         player.m_7292_(new MobEffectInstance(MobEffects.f_19606_, (int)(1600.0 * multiplier), 2, false, true));
         player.m_7292_(new MobEffectInstance(MobEffects.f_19607_, (int)(3000.0 * multiplier), 0, false, true));
      }
   }

   @Override
   public boolean canDrink(Level world, Player player, ItemStack potion) {
      return true;
   }
}
