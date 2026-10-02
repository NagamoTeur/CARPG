package com.hollingsworth.arsnouveau.common.network;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraftforge.network.NetworkEvent.Context;

public class ChangeBiomePacket {
   private final BlockPos pos;
   private final ResourceKey<Biome> biomeId;

   public ChangeBiomePacket(BlockPos pos, ResourceKey<Biome> id) {
      this.pos = pos;
      this.biomeId = id;
   }

   public ChangeBiomePacket(FriendlyByteBuf buf) {
      this.pos = new BlockPos(buf.readInt(), 0, buf.readInt());
      this.biomeId = ResourceKey.m_135785_(Registry.f_122885_, buf.m_130281_());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.pos.m_123341_());
      buf.writeInt(this.pos.m_123343_());
      buf.m_130085_(this.biomeId.m_135782_());
   }

   public static class Handler {
      public static boolean onMessage(final ChangeBiomePacket message, Supplier<Context> ctx) {
         ctx.get().enqueueWork(new Runnable() {
            @Override
            public void run() {
               ClientLevel world = Minecraft.m_91087_().f_91073_;
               LevelChunk chunkAt = (LevelChunk)world.m_46865_(message.pos);
               Holder<Biome> biome = world.m_5962_().m_175515_(Registry.f_122885_).m_206081_(message.biomeId);
               int minY = QuartPos.m_175400_(world.m_141937_());
               int maxY = minY + QuartPos.m_175400_(world.m_141928_()) - 1;
               int x = QuartPos.m_175400_(message.pos.m_123341_());
               int z = QuartPos.m_175400_(message.pos.m_123343_());

               for (LevelChunkSection section : chunkAt.m_7103_()) {
                  for (int sy = 0; sy < 16; sy += 4) {
                     int y = Mth.m_14045_(QuartPos.m_175400_(section.m_63017_() + sy), minY, maxY);
                     if (section.m_187996_() instanceof PalettedContainer<Holder<Biome>> container) {
                        container.m_156470_(x & 3, y & 3, z & 3, biome);
                     }

                     SectionPos pos = SectionPos.m_123173_(message.pos.m_123341_() >> 4, (section.m_63017_() >> 4) + sy, message.pos.m_123343_() >> 4);
                     world.m_104793_(pos.m_123170_(), pos.m_123206_(), pos.m_123222_());
                  }
               }

               world.m_171649_(new ChunkPos(message.pos));
            }
         });
         ctx.get().setPacketHandled(true);
         return true;
      }
   }
}
