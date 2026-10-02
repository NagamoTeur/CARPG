package com.cerbon.bosses_of_mass_destruction.packet.custom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.PacketUtils;
import com.cerbon.bosses_of_mass_destruction.block.custom.VoidBlossomBlock;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;

public class PlaceS2CPacket {
   private final Vec3 pos;

   public PlaceS2CPacket(Vec3 pos) {
      this.pos = pos;
   }

   public PlaceS2CPacket(FriendlyByteBuf buf) {
      this.pos = PacketUtils.readVec3(buf);
   }

   public void write(FriendlyByteBuf buf) {
      PacketUtils.writeVec3(buf, this.pos);
   }

   public void handle(Supplier<Context> supplier) {
      Context ctx = supplier.get();
      ctx.enqueueWork(() -> {
         Minecraft client = Minecraft.m_91087_();
         DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> client.execute(() -> VoidBlossomBlock.handleVoidBlossomPlace(this.pos)));
      });
      ctx.setPacketHandled(true);
   }
}
