package com.hollingsworth.arsnouveau.api.camera;

import com.hollingsworth.arsnouveau.common.entity.ScryerCamera;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketSetCameraView;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.network.PacketDistributor;

public interface ICameraMountable {
   default void mountCamera(Level level, BlockPos pos, Player player) {
      if (!level.f_46443_) {
         ServerLevel serverLevel = (ServerLevel)level;
         ServerPlayer serverPlayer = (ServerPlayer)player;
         SectionPos chunkPos = SectionPos.m_123199_(pos);
         int viewDistance = serverPlayer.f_8924_.m_6846_().m_11312_();
         ScryerCamera dummyEntity;
         if (serverPlayer.m_8954_() instanceof ScryerCamera cam) {
            dummyEntity = new ScryerCamera(level, pos, cam);
         } else {
            dummyEntity = new ScryerCamera(level, pos);
         }

         level.m_7967_(dummyEntity);

         for (int x = chunkPos.m_123341_() - viewDistance; x <= chunkPos.m_123341_() + viewDistance; x++) {
            for (int z = chunkPos.m_123343_() - viewDistance; z <= chunkPos.m_123343_() + viewDistance; z++) {
               ForgeChunkManager.forceChunk(serverLevel, "ars_nouveau", dummyEntity, x, z, true, false);
            }
         }

         serverPlayer.f_8926_ = dummyEntity;
         Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new PacketSetCameraView(dummyEntity));
         this.startViewing();
      }
   }

   void startViewing();

   void stopViewing();
}
