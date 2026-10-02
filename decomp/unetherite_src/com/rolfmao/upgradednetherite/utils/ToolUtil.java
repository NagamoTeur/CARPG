package com.rolfmao.upgradednetherite.utils;

import com.rolfmao.upgradednetherite.config.UpgradedNetheriteConfig;
import com.rolfmao.upgradednetherite.utils.check.EnderUtil;
import com.rolfmao.upgradednetherite.utils.check.FeatherUtil;
import com.rolfmao.upgradednetherite.utils.check.FireUtil;
import com.rolfmao.upgradednetherite.utils.check.PhantomUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class ToolUtil {
   public static InteractionResult EnderSetTag(UseOnContext context) {
      if (context.m_43723_().m_6047_() && !context.m_43723_().f_19853_.f_46443_) {
         IItemHandler iitemhandler = null;
         Player player = context.m_43723_();
         ItemStack heldItem = context.m_43722_();
         int x = context.m_8083_().m_123341_();
         int y = context.m_8083_().m_123342_();
         int z = context.m_8083_().m_123343_();
         BlockPos blockpos = context.m_8083_();
         Level world = context.m_43725_();
         BlockState state = world.m_8055_(blockpos);
         if (state.m_155947_()) {
            BlockEntity tileentity = world.m_7702_(blockpos);
            if (tileentity != null) {
               iitemhandler = (IItemHandler)((ImmutablePair)tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                     .map(capability -> ImmutablePair.of(capability, tileentity))
                     .get())
                  .getKey();
            }
         }

         if (iitemhandler != null && !player.m_36335_().m_41519_(heldItem.m_41720_()) && UpgradedNetheriteConfig.EnableTeleportChest) {
            String dim = world.m_46472_().m_135782_().m_135815_();
            if (heldItem.m_41783_() != null
               && heldItem.m_41783_().m_128441_("UpgradedNetherite_Tagged")
               && heldItem.m_41783_().m_128471_("UpgradedNetherite_Tagged")
               && dim.equals(heldItem.m_41783_().m_128461_("UpgradedNetherite_Dimension"))
               && context.m_8083_().m_123341_() == heldItem.m_41783_().m_128465_("UpgradedNetherite_Position")[0]
               && context.m_8083_().m_123342_() == heldItem.m_41783_().m_128465_("UpgradedNetherite_Position")[1]
               && context.m_8083_().m_123343_() == heldItem.m_41783_().m_128465_("UpgradedNetherite_Position")[2]) {
               heldItem.m_41784_().m_128473_("UpgradedNetherite_Position");
               heldItem.m_41784_().m_128473_("UpgradedNetherite_Dimension");
               heldItem.m_41784_().m_128473_("UpgradedNetherite_Tagged");
               player.m_36335_().m_41524_(heldItem.m_41720_(), 10);
               return InteractionResult.m_19078_(true);
            }

            heldItem.m_41784_().m_128385_("UpgradedNetherite_Position", new int[]{x, y, z});
            heldItem.m_41784_().m_128359_("UpgradedNetherite_Dimension", player.f_19853_.m_46472_().m_135782_().m_135815_());
            heldItem.m_41784_().m_128379_("UpgradedNetherite_Tagged", true);
            player.m_36335_().m_41524_(heldItem.m_41720_(), 10);
            return InteractionResult.m_19078_(true);
         }
      }

      return InteractionResult.PASS;
   }

   public static void toggleDisableEffect(Player player) {
      ItemStack heldItem = player.m_21205_();
      heldItem.m_41784_().m_128379_("UpgradedNetherite_DisableEffect", !getDisableEffect(heldItem));
      player.m_36335_().m_41524_(heldItem.m_41720_(), 10);
   }

   public static boolean getDisableEffect(ItemStack stack) {
      return stack.m_41783_() != null
         && stack.m_41783_().m_128441_("UpgradedNetherite_DisableEffect")
         && stack.m_41783_().m_128471_("UpgradedNetherite_DisableEffect");
   }

   public static boolean isUsingEffectTool(Player player) {
      return FireUtil.isFireToolOrWeapon(player.m_21205_()) && UpgradedNetheriteConfig.EnableAutoSmelt
         || EnderUtil.isEnderToolOrWeapon(player.m_21205_()) && UpgradedNetheriteConfig.EnableTeleportChest
         || PhantomUtil.isPhantomToolOrWeapon(player.m_21205_()) && UpgradedNetheriteConfig.EnableGlowingEffect
         || FeatherUtil.isFeatherToolOrWeapon(player.m_21205_()) && UpgradedNetheriteConfig.EnableAttractItem;
   }
}
