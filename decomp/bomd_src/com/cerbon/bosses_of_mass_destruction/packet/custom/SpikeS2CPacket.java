package com.cerbon.bosses_of_mass_destruction.packet.custom;

import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.VoidBlossomEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;

public class SpikeS2CPacket {
   private final int id;
   private final List<BlockPos> spikePositions;

   public SpikeS2CPacket(int id, List<BlockPos> spikePositions) {
      this.id = id;
      this.spikePositions = spikePositions;
   }

   public SpikeS2CPacket(FriendlyByteBuf buf) {
      this.id = buf.readInt();
      int size = buf.readInt();
      this.spikePositions = new ArrayList<>();

      for (int i = 0; i < size; i++) {
         this.spikePositions.add(buf.m_130135_());
      }
   }

   public void write(FriendlyByteBuf buf) {
      buf.writeInt(this.id);
      buf.writeInt(this.spikePositions.size());

      for (BlockPos spikePos : this.spikePositions) {
         buf.m_130064_(spikePos);
      }
   }

   public void handle(Supplier<Context> supplier) {
      Context ctx = supplier.get();
      ctx.enqueueWork(() -> {
         Minecraft client = Minecraft.m_91087_();
         DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> client.execute(() -> {
                  ClientLevel clientLevel = client.f_91073_;
                  if (clientLevel != null) {
                     Entity entity = clientLevel.m_6815_(this.id);
                     if (entity instanceof VoidBlossomEntity) {
                        this.spikePositions.forEach(spikePos -> ((VoidBlossomEntity)entity).clientSpikeHandler.addSpike(spikePos));
                     }
                  }
               }));
      });
      ctx.setPacketHandled(true);
   }
}
