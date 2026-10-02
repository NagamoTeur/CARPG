package com.cerbon.bosses_of_mass_destruction.packet.custom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.PacketUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.block.custom.VoidLilyBlockEntity;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;

public class SendParticleS2CPacket {
   private final BlockPos pos;
   private final Vec3 dir;

   public SendParticleS2CPacket(BlockPos pos, Vec3 dir) {
      this.pos = pos;
      this.dir = dir;
   }

   public SendParticleS2CPacket(FriendlyByteBuf buf) {
      this.pos = buf.m_130135_();
      this.dir = PacketUtils.readVec3(buf);
   }

   public void write(FriendlyByteBuf buf) {
      buf.m_130064_(this.pos);
      PacketUtils.writeVec3(buf, this.dir);
   }

   public void handle(Supplier<Context> supplier) {
      Context ctx = supplier.get();
      ctx.enqueueWork(
         () -> {
            Minecraft client = Minecraft.m_91087_();
            ClientLevel level = client.f_91073_;
            if (level != null) {
               DistExecutor.unsafeRunWhenOn(
                  Dist.CLIENT, () -> () -> client.execute(() -> VoidLilyBlockEntity.spawnVoidLilyParticles(level, VecUtils.asVec3(this.pos), this.dir))
               );
            }
         }
      );
      ctx.setPacketHandled(true);
   }
}
