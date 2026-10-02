package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.api.items.IEldritch;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TheInfinitum extends TheAcknowledgment implements IEldritch {
   public static Omniconfig.DoubleParameter attackDamage;
   public static Omniconfig.DoubleParameter attackSpeed;
   public static Omniconfig.PerhapsParameter bossDamageBonus;
   public static Omniconfig.PerhapsParameter knockbackBonus;
   public static Omniconfig.PerhapsParameter lifestealBonus;
   public static Omniconfig.PerhapsParameter undeadProbability;

   @SubscribeConfig
   public static void onConfig(OmniconfigWrapper builder) {
      builder.pushPrefix("TheInfinitum");
      attackDamage = builder.comment("Attack damage of The Infinitum, actual damage shown in tooltip will be is 1 + this_value.")
         .max(32768.0)
         .getDouble("AttackDamage", 15.0);
      attackSpeed = builder.comment("Attack speed of The Infinitum.").minMax(32768.0).getDouble("AttackSpeed", -2.0);
      bossDamageBonus = builder.comment("Attack damage bonus of The Infinitum against players and bosses.").getPerhaps("BossDamageBonus", 200);
      knockbackBonus = builder.comment("Knockback bonus of The Infinitum. For Phantoms, this value is multiplied by 1.5.")
         .getPerhaps("KnockbackPowerBonus", 200);
      lifestealBonus = builder.comment("Lifesteal bonus of The Infinitum.").getPerhaps("LifestealBonus", 10);
      undeadProbability = builder.comment("Chance of lethal damage prevention when holding The Infinitum.").max(100.0).getPerhaps("UndeadProbability", 85);
      builder.popPrefix();
   }

   public TheInfinitum() {
      super(getDefaultProperties().m_41497_(Rarity.EPIC).m_41487_(1).m_41486_(), "the_infinitum", attackDamage.getValue(), attackSpeed.getValue());
      this.setAllowAllEnchantments(true);
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void m_7373_(ItemStack stack, Level world, List<Component> list, TooltipFlag flag) {
      if (Screen.m_96638_()) {
         if (Minecraft.m_91087_().f_91074_ != null && SuperpositionHandler.isTheCursedOne(Minecraft.m_91087_().f_91074_)) {
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theInfinitum2");
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theInfinitum3");
            ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         }

         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theInfinitum4", ChatFormatting.GOLD, bossDamageBonus + "%");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theInfinitum5", ChatFormatting.GOLD, knockbackBonus + "%");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theInfinitum6", ChatFormatting.GOLD, lifestealBonus + "%");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theInfinitum7");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theInfinitum8");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theInfinitum9");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theInfinitum10");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.indicateWorthyOnesOnly(list);
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.theInfinitum1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.indicateCursedOnesOnly(list);
      }

      if (stack.m_41793_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      }
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (attacker instanceof ServerPlayer player && SuperpositionHandler.isTheWorthyOne(player)) {
         target.m_7292_(new MobEffectInstance(MobEffects.f_19615_, 160, 3, false, true));
         target.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 500, 3, false, true));
         target.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 300, 3, false, true));
         target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 300, 3, false, true));
         target.m_7292_(new MobEffectInstance(MobEffects.f_19599_, 300, 3, false, true));
         target.m_7292_(new MobEffectInstance(MobEffects.f_19610_, 100, 3, false, true));
      }

      return false;
   }

   @Override
   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      if (!SuperpositionHandler.isTheWorthyOne(player)) {
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
