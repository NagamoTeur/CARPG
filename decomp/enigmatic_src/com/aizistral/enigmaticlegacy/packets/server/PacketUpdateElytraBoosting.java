package com.aizistral.enigmaticlegacy.packets.server;

import com.aizistral.enigmaticlegacy.objects.TransientPlayerData;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketUpdateElytraBoosting {
   private boolean boosting;

   public PacketUpdateElytraBoosting(boolean boosting) {
      this.boosting = boosting;
   }

   public static void encode(PacketUpdateElytraBoosting msg, FriendlyByteBuf buf) {
      buf.writeBoolean(msg.boosting);
   }

   public static PacketUpdateElytraBoosting decode(FriendlyByteBuf buf) {
      return new PacketUpdateElytraBoosting(buf.readBoolean());
   }

   public static void handle(PacketUpdateElytraBoosting msg, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         ServerPlayer player = ctx.get().getSender();
         if (msg.boosting) {
            player.f_19853_.m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_11898_, SoundSource.AMBIENT, 3.0F, 1.0F);
         }

         TransientPlayerData data = TransientPlayerData.get(player);
         boolean wasBoosting = data.isElytraBoosting();
         data.setElytraBoosting(msg.boosting);
         if (wasBoosting != msg.boosting) {
            data.syncToAllClients();
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
