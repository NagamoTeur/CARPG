package com.cerbon.bosses_of_mass_destruction.packet.custom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.PacketUtils;
import com.cerbon.bosses_of_mass_destruction.util.VecId;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;

public class SendVec3S2CPacket {
   private final Vec3 pos;
   private final int id;

   public SendVec3S2CPacket(Vec3 pos, int id) {
      this.pos = pos;
      this.id = id;
   }

   public SendVec3S2CPacket(FriendlyByteBuf buf) {
      this.pos = PacketUtils.readVec3(buf);
      this.id = buf.readInt();
   }

   public void write(FriendlyByteBuf buf) {
      PacketUtils.writeVec3(buf, this.pos);
      buf.writeInt(this.id);
   }

   public void handle(Supplier<Context> supplier) {
      Context ctx = supplier.get();
      ctx.enqueueWork(() -> {
         Minecraft client = Minecraft.m_91087_();
         ClientLevel level = client.f_91073_;
         VecId vecId = VecId.fromInt(this.id);
         if (level != null) {
            if (vecId != null) {
               DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> client.execute(() -> vecId.effectHandler.get().clientHandler(level, this.pos)));
            }
         }
      });
      ctx.setPacketHandled(true);
   }
}
