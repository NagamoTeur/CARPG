package com.cerbon.bosses_of_mass_destruction.packet.custom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.PacketUtils;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;

public class SendDeltaMovementS2CPacket {
   private final Vec3 deltaMovement;

   public SendDeltaMovementS2CPacket(Vec3 deltaMovement) {
      this.deltaMovement = deltaMovement;
   }

   public SendDeltaMovementS2CPacket(FriendlyByteBuf buf) {
      this.deltaMovement = PacketUtils.readVec3(buf);
   }

   public void write(FriendlyByteBuf buf) {
      PacketUtils.writeVec3(buf, this.deltaMovement);
   }

   public void handle(Supplier<Context> supplier) {
      Context ctx = supplier.get();
      ctx.enqueueWork(() -> {
         Minecraft client = Minecraft.m_91087_();
         LocalPlayer localPlayer = client.f_91074_;
         if (localPlayer != null) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> client.execute(() -> localPlayer.m_20256_(this.deltaMovement)));
         }
      });
      ctx.setPacketHandled(true);
   }
}
