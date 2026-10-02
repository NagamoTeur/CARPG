package com.hollingsworth.arsnouveau.api.ritual;

import com.hollingsworth.arsnouveau.common.network.ChangeBiomePacket;
import com.hollingsworth.arsnouveau.common.network.Networking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraftforge.network.PacketDistributor;

public class RitualUtil {
   public static BlockPos betweenClosed(int pX1, int pY1, int pZ1, int pX2, int pY2, int pZ2, int index) {
      int i = pX2 - pX1 + 1;
      int j = pY2 - pY1 + 1;
      int k = pZ2 - pZ1 + 1;
      int l = i * j * k;
      int i1 = index % i;
      int j1 = index / i;
      int k1 = j1 % j;
      int l1 = j1 / j;
      return new BlockPos(pX1 + i1, pY1 + k1, pZ1 + l1);
   }

   public static BlockPos betweenClosed(BlockPos pFirstPos, BlockPos pSecondPos, int index) {
      return betweenClosed(
         Math.min(pFirstPos.m_123341_(), pSecondPos.m_123341_()),
         Math.min(pFirstPos.m_123342_(), pSecondPos.m_123342_()),
         Math.min(pFirstPos.m_123343_(), pSecondPos.m_123343_()),
         Math.max(pFirstPos.m_123341_(), pSecondPos.m_123341_()),
         Math.max(pFirstPos.m_123342_(), pSecondPos.m_123342_()),
         Math.max(pFirstPos.m_123343_(), pSecondPos.m_123343_()),
         index
      );
   }

   public static void changeBiome(Level level, BlockPos pos, ResourceKey<Biome> target) {
      Holder<Biome> biome = level.m_5962_().m_175515_(Registry.f_122885_).m_206081_(target);
      if (!level.m_204166_(pos).m_203565_(target)) {
         int minY = QuartPos.m_175400_(level.m_141937_());
         int maxY = minY + QuartPos.m_175400_(level.m_141928_()) - 1;
         int x = QuartPos.m_175400_(pos.m_123341_());
         int z = QuartPos.m_175400_(pos.m_123343_());
         LevelChunk chunkAt = level.m_6325_(pos.m_123341_() >> 4, pos.m_123343_() >> 4);

         for (LevelChunkSection section : chunkAt.m_7103_()) {
            for (int sy = 0; sy < 16; sy += 4) {
               int y = Mth.m_14045_(QuartPos.m_175400_(section.m_63017_() + sy), minY, maxY);
               if (!((Holder)section.m_187996_().m_63087_(x & 3, y & 3, z & 3)).m_203565_(target)
                  && section.m_187996_() instanceof PalettedContainer<Holder<Biome>> container) {
                  container.m_156470_(x & 3, y & 3, z & 3, biome);
               }
            }
         }

         if (level instanceof ServerLevel server) {
            if (!chunkAt.m_6344_()) {
               chunkAt.m_8092_(true);
            }

            ChangeBiomePacket message = new ChangeBiomePacket(pos, target);
            Networking.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunkAt), message);
         }
      }
   }
}
