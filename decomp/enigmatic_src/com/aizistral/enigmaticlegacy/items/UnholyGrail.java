package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import com.aizistral.enigmaticlegacy.triggers.UseUnholyGrailTrigger;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class UnholyGrail extends ItemBase implements Vanishable {
   public UnholyGrail() {
      super(ItemBase.getDefaultProperties().m_41487_(1).m_41497_(Rarity.EPIC).m_41486_());
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.unholyGrail1");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }
   }

   public ItemStack m_5922_(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
      if (!(entityLiving instanceof Player player)) {
         return stack;
      } else {
         if (!worldIn.f_46443_) {
            boolean isTheWorthyOne = SuperpositionHandler.isTheCursedOne(player) && EnigmaticItems.FORBIDDEN_FRUIT.haveConsumedFruit(player);
            if (!isTheWorthyOne) {
               player.m_7292_(new MobEffectInstance(MobEffects.f_19615_, 100, 2, false, true));
               player.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 160, 1, false, true));
               player.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 240, 0, false, true));
               player.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 200, 1, false, true));
               player.m_7292_(new MobEffectInstance(MobEffects.f_19612_, 160, 2, false, true));
               player.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 240, 0, false, true));
               UseUnholyGrailTrigger.INSTANCE.trigger((ServerPlayer)player, false);
            } else {
               player.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 500, 2, false, true));
               player.m_7292_(new MobEffectInstance(MobEffects.f_19617_, 800, 1, false, true));
               player.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 1200, 1, false, true));
               player.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 1000, 1, false, true));
               player.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 1200, 0, false, true));
               UseUnholyGrailTrigger.INSTANCE.trigger((ServerPlayer)player, true);
            }
         }

         player.m_36246_(Stats.f_12982_.m_12902_(this));
         return stack;
      }
   }

   public int m_8105_(ItemStack stack) {
      return 32;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.DRINK;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      playerIn.m_6672_(handIn);
      return new InteractionResultHolder(InteractionResult.CONSUME, playerIn.m_21120_(handIn));
   }
}
