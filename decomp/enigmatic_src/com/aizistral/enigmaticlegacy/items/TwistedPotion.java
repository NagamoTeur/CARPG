package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.api.items.ICursed;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBasePotion;
import com.aizistral.enigmaticlegacy.packets.clients.PacketPortalParticles;
import com.aizistral.enigmaticlegacy.packets.clients.PacketRecallParticles;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.TargetPoint;

public class TwistedPotion extends ItemBasePotion implements ICursed {
   public TwistedPotion() {
      super(getDefaultProperties().m_41487_(1).m_41497_(Rarity.EPIC));
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.twistedPotion1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.twistedPotion2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.twistedPotion3");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      ItemLoreHelper.indicateCursedOnesOnly(list);
   }

   @Override
   public void onConsumed(Level worldIn, Player playerIn, ItemStack potion) {
      if (playerIn instanceof ServerPlayer player) {
         CompoundTag location = this.getLastDeathLocation(player);
         if (location != null) {
            double x = location.m_128459_("x");
            double y = location.m_128459_("y");
            double z = location.m_128459_("z");
            ResourceKey<Level> dimension = ResourceKey.m_135785_(Registry.f_122819_, new ResourceLocation(location.m_128461_("dimension")));
            player.f_19853_.m_5594_(null, player.m_20183_(), SoundEvents.f_11852_, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2));
            player.f_19853_.m_5594_(null, player.m_20183_(), SoundEvents.f_12323_, SoundSource.PLAYERS, 1.0F, 1.0F);
            EnigmaticLegacy.packetInstance
               .send(
                  PacketDistributor.NEAR
                     .with(() -> new TargetPoint(player.m_20185_(), player.m_20186_(), player.m_20189_(), 128.0, player.f_19853_.m_46472_())),
                  new PacketPortalParticles(player.m_20185_(), player.m_20186_() + (double)(player.m_20206_() / 2.0F), player.m_20189_(), 100, 1.25, false)
               );
            SuperpositionHandler.sendToDimension(player, dimension);
            player.m_6027_(x, y, z);
            player.m_21153_(1.0F);
            player.f_19853_.m_5594_(null, player.m_20183_(), SoundEvents.f_11852_, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2));
            player.f_19853_.m_5594_(null, player.m_20183_(), SoundEvents.f_12323_, SoundSource.PLAYERS, 1.0F, 1.0F);
            EnigmaticLegacy.packetInstance
               .send(
                  PacketDistributor.NEAR
                     .with(() -> new TargetPoint(player.m_20185_(), player.m_20186_(), player.m_20189_(), 128.0, player.f_19853_.m_46472_())),
                  new PacketRecallParticles(player.m_20185_(), player.m_20186_() + (double)(player.m_20206_() / 2.0F), player.m_20189_(), 48, false)
               );
         }
      }
   }

   @Override
   public boolean canDrink(Level world, Player player, ItemStack potion) {
      return SuperpositionHandler.isTheCursedOne(player) && this.getLastDeathLocation(player) != null;
   }

   private CompoundTag getLastDeathLocation(Player player) {
      return (CompoundTag)SuperpositionHandler.getPersistentTag(player, "LastDeathLocation", null);
   }
}
