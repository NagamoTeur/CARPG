package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.PlayerCapability;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class ItemWroughtAxe extends MowzieAxeItem {
   public ItemWroughtAxe(Properties properties) {
      super(
         Tiers.IRON,
         -3.0F + ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.toolConfig.attackDamageValue,
         -4.0F + ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.toolConfig.attackSpeedValue,
         properties
      );
   }

   public boolean m_6832_(ItemStack itemStack, ItemStack itemStackMaterial) {
      return (Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.breakable.get() && super.m_6832_(itemStack, itemStackMaterial);
   }

   public boolean m_8120_(ItemStack p_77616_1_) {
      return true;
   }

   public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
      PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(player, CapabilityHandler.PLAYER_CAPABILITY);
      return playerCapability == null || !playerCapability.getAxeCanAttack() && playerCapability.getUntilAxeSwing() > 0;
   }

   public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
      PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(entity, CapabilityHandler.PLAYER_CAPABILITY);
      return playerCapability != null && playerCapability.getUntilAxeSwing() > 0;
   }

   public boolean m_7579_(ItemStack heldItemStack, LivingEntity entityHit, LivingEntity attacker) {
      if ((Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.breakable.get()) {
         heldItemStack.m_41622_(2, attacker, p -> p.m_21190_(InteractionHand.MAIN_HAND));
      }

      if (!entityHit.f_19853_.f_46443_) {
         entityHit.m_5496_(SoundEvents.f_11668_, 0.3F, 0.5F);
      }

      return true;
   }

   public boolean m_41465_() {
      return (Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.breakable.get();
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      if (hand == InteractionHand.MAIN_HAND && player.m_36403_(0.5F) == 1.0F) {
         PlayerCapability.IPlayerCapability playerCapability = CapabilityHandler.getCapability(player, CapabilityHandler.PLAYER_CAPABILITY);
         if (playerCapability != null && playerCapability.getUntilAxeSwing() <= 0) {
            boolean verticalAttack = player.m_6144_() && player.m_20096_();
            if (verticalAttack) {
               AbilityHandler.INSTANCE.sendAbilityMessage(player, AbilityHandler.WROUGHT_AXE_SLAM_ABILITY);
            } else {
               AbilityHandler.INSTANCE.sendAbilityMessage(player, AbilityHandler.WROUGHT_AXE_SWING_ABILITY);
            }

            playerCapability.setVerticalSwing(verticalAttack);
            playerCapability.setUntilAxeSwing(30);
            player.m_6672_(hand);
            if ((Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.breakable.get() && !player.m_150110_().f_35937_) {
               player.m_21120_(hand).m_41622_(2, player, p -> p.m_21190_(hand));
            }
         }

         return new InteractionResultHolder(InteractionResult.SUCCESS, player.m_21120_(hand));
      } else {
         return super.m_7203_(world, player, hand);
      }
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip, flagIn);
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.0").m_6270_(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.1").m_6270_(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.2").m_6270_(ItemHandler.TOOLTIP_STYLE));
   }

   @Override
   public ConfigHandler.ToolConfig getConfig() {
      return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.AXE_OF_A_THOUSAND_METALS.toolConfig;
   }
}
