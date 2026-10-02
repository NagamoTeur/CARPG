package com.aizistral.enigmaticlegacy.packets.clients;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.helpers.ExperienceHelper;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketUpdateExperience {
   private int experience;

   public PacketUpdateExperience(int xp) {
      this.experience = xp;
   }

   public static void encode(PacketUpdateExperience msg, FriendlyByteBuf buf) {
      buf.writeInt(msg.experience);
   }

   public static PacketUpdateExperience decode(FriendlyByteBuf buf) {
      return new PacketUpdateExperience(buf.readInt());
   }

   public static void handle(PacketUpdateExperience msg, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         Player player = EnigmaticLegacy.PROXY.getClientPlayer();
         int diff = msg.experience - ExperienceHelper.getPlayerXP(player);
         ExperienceHelper.addPlayerXP(player, diff);
      });
      ctx.get().setPacketHandled(true);
   }
}
