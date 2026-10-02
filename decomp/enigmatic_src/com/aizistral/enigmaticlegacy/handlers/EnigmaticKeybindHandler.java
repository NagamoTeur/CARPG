package com.aizistral.enigmaticlegacy.handlers;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.config.OmniconfigHandler;
import com.aizistral.enigmaticlegacy.packets.server.PacketEnderRingKey;
import com.aizistral.enigmaticlegacy.packets.server.PacketSpellstoneKey;
import com.aizistral.enigmaticlegacy.packets.server.PacketXPScrollKey;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;

public class EnigmaticKeybindHandler {
   @OnlyIn(Dist.CLIENT)
   public static boolean checkVariable;
   public KeyMapping enderRingKey;
   public KeyMapping spellstoneAbilityKey;
   public KeyMapping xpScrollKey;
   private boolean spaceDown = false;

   @SubscribeEvent
   @OnlyIn(Dist.CLIENT)
   public void onKeyInput(ClientTickEvent event) {
      if (event.phase == Phase.START && Minecraft.m_91087_().m_91302_() && Minecraft.m_91087_().f_91074_ != null) {
         boolean spaceDown = Minecraft.m_91087_().f_91066_.f_92089_.m_90857_();
         boolean jumpClicked = false;
         if (this.spaceDown != spaceDown) {
            this.spaceDown = spaceDown;
            if (spaceDown) {
               jumpClicked = true;
            }
         }

         if (Minecraft.m_91087_().f_91074_.m_21255_()) {
            jumpClicked = Minecraft.m_91087_().f_91066_.f_92089_.m_90857_();
         }

         if (!OmniconfigHandler.angelBlessingDoubleJump.getValue()) {
            jumpClicked = false;
         }

         if (this.enderRingKey.m_90859_() && Minecraft.m_91087_().m_91302_()) {
            EnigmaticLegacy.packetInstance.send(PacketDistributor.SERVER.noArg(), new PacketEnderRingKey(true));
         }

         if (this.xpScrollKey.m_90859_()) {
            EnigmaticLegacy.packetInstance.send(PacketDistributor.SERVER.noArg(), new PacketXPScrollKey(true));
         }

         if (this.spellstoneAbilityKey.m_90857_() && SuperpositionHandler.hasCurio(Minecraft.m_91087_().f_91074_, EnigmaticItems.ENIGMATIC_ITEM)) {
            EnigmaticLegacy.packetInstance.send(PacketDistributor.SERVER.noArg(), new PacketSpellstoneKey(true));
         } else if (this.spellstoneAbilityKey.m_90859_() && SuperpositionHandler.hasSpellstone(Minecraft.m_91087_().f_91074_)) {
            EnigmaticLegacy.packetInstance.send(PacketDistributor.SERVER.noArg(), new PacketSpellstoneKey(true));
         } else if (jumpClicked) {
            LocalPlayer player = Minecraft.m_91087_().f_91074_;
            if (!player.m_20069_()
               && !player.m_20096_()
               && !player.m_7500_()
               && !player.m_5833_()
               && SuperpositionHandler.hasCurio(player, EnigmaticItems.ANGEL_BLESSING)) {
               EnigmaticLegacy.packetInstance.send(PacketDistributor.SERVER.noArg(), new PacketSpellstoneKey(true));
            }
         }
      }
   }

   @SubscribeEvent
   @OnlyIn(Dist.CLIENT)
   public void onLivingJump(LivingJumpEvent event) {
      if (event.getEntity() instanceof LocalPlayer var2) {
         ;
      }
   }
}
