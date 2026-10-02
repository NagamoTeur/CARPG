package com.cerbon.bosses_of_mass_destruction.packet.custom;

import com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet.GauntletEntity;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;

public class BlindnessS2CPacket {
   private final int entityId;
   private final int[] playerIds;

   public BlindnessS2CPacket(int entityId, int[] playerIds) {
      this.entityId = entityId;
      this.playerIds = playerIds;
   }

   public BlindnessS2CPacket(FriendlyByteBuf buf) {
      this.entityId = buf.readInt();
      this.playerIds = buf.m_130100_();
   }

   public void write(FriendlyByteBuf buf) {
      buf.writeInt(this.entityId);
      buf.m_130089_(this.playerIds);
   }

   public void handle(Supplier<Context> supplier) {
      Context ctx = supplier.get();
      ctx.enqueueWork(
         () -> {
            Minecraft client = Minecraft.m_91087_();
            ClientLevel level = client.f_91073_;
            if (level != null) {
               DistExecutor.unsafeRunWhenOn(
                  Dist.CLIENT,
                  () -> () -> client.execute(
                           () -> {
                              Entity entity = level.m_6815_(this.entityId);
                              List<Player> players = Arrays.stream(this.playerIds)
                                 .<Entity>mapToObj(level::m_6815_)
                                 .filter(id -> id instanceof Player)
                                 .map(id -> (Player)id)
                                 .toList();
                              if (entity instanceof GauntletEntity) {
                                 ((GauntletEntity)entity).clientBlindnessHandler.handlePlayerEffects(players);
                              }
                           }
                        )
               );
            }
         }
      );
      ctx.setPacketHandled(true);
   }
}
