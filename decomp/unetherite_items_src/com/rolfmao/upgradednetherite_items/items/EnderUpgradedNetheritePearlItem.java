package com.rolfmao.upgradednetherite_items.items;

import com.rolfmao.upgradednetherite.UpgradedNetheriteMod;
import com.rolfmao.upgradednetherite_items.UpgradedNetherite_ItemsMod;
import com.rolfmao.upgradednetherite_items.config.UpgradedNetheriteItemsConfig;
import com.rolfmao.upgradednetherite_items.entity.EnderUpgradedNetheritePearlEntity;
import com.rolfmao.upgradednetherite_items.packets.PacketResetEnderUpgradedNetheritePearl;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;

public class EnderUpgradedNetheritePearlItem extends Item {
   public EnderUpgradedNetheritePearlItem() {
      super(new Properties().m_41487_(1).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_());
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      ItemStack itemstack = playerIn.m_21120_(handIn);
      ItemStack pearlstack = null;

      for (int i = 0; i < playerIn.m_150109_().m_6643_(); i++) {
         ItemStack itemstack1 = playerIn.m_150109_().m_8020_(i);
         if (itemstack1.m_41720_() instanceof EnderpearlItem) {
            pearlstack = itemstack1;
         }
      }

      if (!itemstack.m_41784_().m_128441_("EnderUpgradedNetheritePearlTarget") || !playerIn.m_150110_().f_35937_ && pearlstack == null) {
         if (!itemstack.m_41784_().m_128441_("EnderUpgradedNetheritePearlTarget")) {
            if (!playerIn.f_19853_.f_46443_) {
               playerIn.m_213846_(Component.m_237115_("upgradednetherite_items.EnderNotSelect.TT"));
            }

            return InteractionResultHolder.m_19100_(itemstack);
         } else if (pearlstack == null) {
            if (!playerIn.f_19853_.f_46443_) {
               playerIn.m_213846_(Component.m_237115_("upgradednetherite_items.EnderRequire.TT"));
            }

            return InteractionResultHolder.m_19100_(itemstack);
         } else {
            return InteractionResultHolder.m_19100_(itemstack);
         }
      } else {
         worldIn.m_6263_(
            null,
            playerIn.m_20185_(),
            playerIn.m_20186_(),
            playerIn.m_20189_(),
            SoundEvents.f_11857_,
            SoundSource.NEUTRAL,
            0.5F,
            0.4F / (worldIn.f_46441_.m_188501_() * 0.4F + 0.8F)
         );
         playerIn.m_36335_().m_41524_(this, 20);
         if (!worldIn.f_46443_) {
            EnderUpgradedNetheritePearlEntity enderupgradednetheritepearlentity = new EnderUpgradedNetheritePearlEntity(worldIn, playerIn);
            enderupgradednetheritepearlentity.m_37446_(itemstack);
            enderupgradednetheritepearlentity.m_37251_(playerIn, playerIn.m_146909_(), playerIn.m_146908_(), 0.0F, 1.5F, 1.0F);
            enderupgradednetheritepearlentity.getPersistentData()
               .m_128405_("EnderUpgradedNetheritePearlTarget", itemstack.m_41784_().m_128451_("EnderUpgradedNetheritePearlTarget"));
            worldIn.m_7967_(enderupgradednetheritepearlentity);
         }

         if (pearlstack != null && !playerIn.m_150110_().f_35937_) {
            pearlstack.m_41774_(1);
         }

         playerIn.m_36246_(Stats.f_12982_.m_12902_(this));
         itemstack.m_41784_().m_128473_("EnderUpgradedNetheritePearlTarget");
         return InteractionResultHolder.m_19092_(itemstack, worldIn.m_5776_());
      }
   }

   public void m_6883_(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!entityIn.f_19853_.f_46443_
         && entityIn instanceof ServerPlayer
         && stack.m_41784_().m_128441_("EnderUpgradedNetheritePearlTarget")
         && (
            entityIn.f_19853_.m_6815_(stack.m_41784_().m_128451_("EnderUpgradedNetheritePearlTarget")) == null
               || !entityIn.f_19853_.m_6815_(stack.m_41784_().m_128451_("EnderUpgradedNetheritePearlTarget")).m_6084_()
         )) {
         stack.m_41784_().m_128473_("EnderUpgradedNetheritePearlTarget");
         stack.m_41784_().m_128473_("EnderUpgradedNetheritePearlTargetName");
         UpgradedNetherite_ItemsMod.packetInstance
            .send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)entityIn), new PacketResetEnderUpgradedNetheritePearl(stack));
      }
   }

   public boolean m_5812_(ItemStack itemstack) {
      return itemstack.m_41784_().m_128441_("EnderUpgradedNetheritePearlTarget");
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip, flagIn);
      if (!UpgradedNetheriteItemsConfig.DisableTooltips) {
         tooltip.add(Component.m_237115_("upgradednetherite.Blank.TT"));
         if (Screen.m_96638_()) {
            if (stack.m_41784_().m_128441_("EnderUpgradedNetheritePearlTarget")) {
               tooltip.add(Component.m_237115_("upgradednetherite_items.Selected.TT"));
               if (worldIn != null) {
                  tooltip.add(Component.m_237113_("§7• §6" + ChatFormatting.m_126649_(stack.m_41784_().m_128461_("EnderUpgradedNetheritePearlTargetName"))));
               }
            } else {
               tooltip.add(Component.m_237115_("upgradednetherite_items.EnderSelected.TT"));
            }
         } else {
            tooltip.add(Component.m_237115_("upgradednetherite.HoldShift.TT"));
         }
      }
   }
}
