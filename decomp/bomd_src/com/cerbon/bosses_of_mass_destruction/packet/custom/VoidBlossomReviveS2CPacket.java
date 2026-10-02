package com.cerbon.bosses_of_mass_destruction.packet.custom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.PacketUtils;
import com.cerbon.bosses_of_mass_destruction.structure.structure_repair.VoidBlossomStructureRepair;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;

public class VoidBlossomReviveS2CPacket {
   private final Vec3 pos;

   public VoidBlossomReviveS2CPacket(Vec3 pos) {
      this.pos = pos;
   }

   public VoidBlossomReviveS2CPacket(FriendlyByteBuf buf) {
      this.pos = PacketUtils.readVec3(buf);
   }

   public void write(FriendlyByteBuf buf) {
      PacketUtils.writeVec3(buf, this.pos);
   }

   public void handle(Supplier<Context> supplier) {
      Context ctx = supplier.get();
      ctx.enqueueWork(
         () -> {
            Minecraft client = Minecraft.m_91087_();
            ClientLevel level = client.f_91073_;
            if (level != null) {
               DistExecutor.unsafeRunWhenOn(
                  Dist.CLIENT, () -> () -> client.execute(() -> VoidBlossomStructureRepair.handleVoidBlossomRevivePacket(this.pos, level))
               );
            }
         }
      );
      ctx.setPacketHandled(true);
   }
}
