package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.api.items.ICursed;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import java.util.List;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.DigDurabilityEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class InfernalShield extends ItemBase implements ICursed, Vanishable {
   public InfernalShield() {
      super(ItemBase.getDefaultProperties().m_41486_().m_41497_(Rarity.EPIC).m_41487_(1).m_41503_(10000));
      DispenserBlock.m_52672_(this, ArmorItem.f_40376_);
   }

   public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(tooltip, "tooltip.enigmaticlegacy.infernalShield1");
         ItemLoreHelper.addLocalizedString(tooltip, "tooltip.enigmaticlegacy.infernalShield2");
         ItemLoreHelper.addLocalizedString(tooltip, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(tooltip, "tooltip.enigmaticlegacy.infernalShield3");
         ItemLoreHelper.addLocalizedString(tooltip, "tooltip.enigmaticlegacy.infernalShield4");
         ItemLoreHelper.addLocalizedString(tooltip, "tooltip.enigmaticlegacy.infernalShield5");
         ItemLoreHelper.addLocalizedString(tooltip, "tooltip.enigmaticlegacy.infernalShield6");
         ItemLoreHelper.addLocalizedString(tooltip, "tooltip.enigmaticlegacy.infernalShield7");
         ItemLoreHelper.addLocalizedString(tooltip, "tooltip.enigmaticlegacy.infernalShield8");
         ItemLoreHelper.addLocalizedString(tooltip, "tooltip.enigmaticlegacy.infernalShield9");
         ItemLoreHelper.addLocalizedString(tooltip, "tooltip.enigmaticlegacy.infernalShield10");
         ItemLoreHelper.addLocalizedString(tooltip, "tooltip.enigmaticlegacy.infernalShield11");
      } else {
         ItemLoreHelper.addLocalizedString(tooltip, "tooltip.enigmaticlegacy.holdShift");
      }

      ItemLoreHelper.addLocalizedString(tooltip, "tooltip.enigmaticlegacy.void");
      ItemLoreHelper.indicateCursedOnesOnly(tooltip);
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.BLOCK;
   }

   public int m_8105_(ItemStack stack) {
      return 72000;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack stack = player.m_21120_(hand);
      if (SuperpositionHandler.isTheCursedOne(player)) {
         player.m_6672_(hand);
         return InteractionResultHolder.m_19096_(stack);
      } else {
         return InteractionResultHolder.m_19098_(stack);
      }
   }

   public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
      return ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction);
   }

   public boolean m_6832_(ItemStack stack, ItemStack repairStack) {
      return repairStack.m_41720_() instanceof BlockItem blockItem ? blockItem.m_40614_() == Blocks.f_50080_ : super.m_6832_(stack, repairStack);
   }

   public void m_6883_(ItemStack stack, Level world, Entity holder, int slot, boolean hand) {
      if (holder instanceof ServerPlayer player
         && player.m_6060_()
         && SuperpositionHandler.isTheCursedOne(player)
         && (player.m_21205_() == stack || player.m_21206_() == stack)) {
         player.m_20095_();
      }
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return enchantment instanceof DigDurabilityEnchantment || super.canApplyAtEnchantingTable(stack, enchantment);
   }

   public boolean m_8120_(ItemStack pStack) {
      return true;
   }

   public int getEnchantmentValue(ItemStack stack) {
      return 16;
   }
}
