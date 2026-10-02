package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.api.items.ICursed;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TheTwist extends TheAcknowledgment implements ICursed {
   public static Omniconfig.DoubleParameter attackDamage;
   public static Omniconfig.DoubleParameter attackSpeed;
   public static Omniconfig.PerhapsParameter bossDamageBonus;
   public static Omniconfig.PerhapsParameter knockbackBonus;

   @SubscribeConfig
   public static void onConfig(OmniconfigWrapper builder) {
      builder.pushPrefix("TheTwist");
      attackDamage = builder.comment("Attack damage of The Twist, actual damage shown in tooltip will be is 1 + this_value.")
         .max(32768.0)
         .getDouble("AttackDamage", 8.0);
      attackSpeed = builder.comment("Attack speed of The Twist.").minMax(32768.0).getDouble("AttackSpeed", -1.8);
      bossDamageBonus = builder.comment("Attack damage bonus of The Twist against players and bossess.").getPerhaps("BossDamageBonus", 300);
      knockbackBonus = builder.comment("Knockback bonus of The Twist. For Phantoms, this value is multiplied by 1.5.").getPerhaps("KnockbackPowerBonus", 300);
      builder.popPrefix();
   }

   public TheTwist() {
      super(getDefaultProperties().m_41497_(Rarity.EPIC).m_41487_(1).m_41486_(), "the_twist", attackDamage.getValue(), attackSpeed.getValue());
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void m_7373_(ItemStack stack, Level world, List<Component> list, TooltipFlag flag) {
      if (Screen.m_96638_()) {
         if (Minecraft.m_91087_().f_91074_ != null && SuperpositionHandler.isTheCursedOne(Minecraft.m_91087_().f_91074_)) {
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theTwist4");
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theTwist5");
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         }

         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theTwist6", ChatFormatting.GOLD, bossDamageBonus + "%");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theTwist7", ChatFormatting.GOLD, knockbackBonus + "%");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theTwist1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theTwist2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      ItemLoreHelper.indicateCursedOnesOnly(list);
      if (stack.m_41793_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      }
   }

   @Override
   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      if (!SuperpositionHandler.isTheCursedOne(player)) {
         return InteractionResultHolder.m_19098_(player.m_21120_(hand));
      } else {
         if (hand == InteractionHand.MAIN_HAND) {
            ItemStack offhandStack = player.m_21206_();
            if (offhandStack != null && offhandStack.m_41720_().m_6164_(offhandStack) == UseAnim.BLOCK) {
               return new InteractionResultHolder(InteractionResult.PASS, player.m_21120_(hand));
            }
         }

         return super.m_7203_(world, player, hand);
      }
   }
}
