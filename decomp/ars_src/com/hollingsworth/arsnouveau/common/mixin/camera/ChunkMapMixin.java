package com.hollingsworth.arsnouveau.common.mixin.camera;

import com.hollingsworth.arsnouveau.common.entity.ScryerCamera;
import com.hollingsworth.arsnouveau.common.util.CameraUtil;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.List;
import net.minecraft.core.SectionPos;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(
   value = {ChunkMap.class},
   priority = 1100
)
public abstract class ChunkMapMixin {
   @Shadow
   int f_140126_;

   @Shadow
   protected abstract void m_183754_(ServerPlayer var1, ChunkPos var2, MutableObject<ClientboundLevelChunkWithLightPacket> var3, boolean var4, boolean var5);

   @Shadow
   public abstract List<ServerPlayer> m_183262_(ChunkPos var1, boolean var2);

   @Inject(
      method = {"setViewDistance"},
      at = {@At(
         value = "NEW",
         target = "org/apache/commons/lang3/mutable/MutableObject",
         shift = Shift.AFTER
      )},
      locals = LocalCapture.CAPTURE_FAILSOFT,
      cancellable = true,
      remap = false
   )
   private void updateAccordingToCamera(
      int viewDistance, CallbackInfo callback, int i, int j, ObjectIterator<?> objectIterator, ChunkHolder chunkHolder, ChunkPos chunkPos
   ) {
      MutableObject<ClientboundLevelChunkWithLightPacket> mutableObject = new MutableObject();
      this.m_183262_(chunkPos, false).forEach(player -> {
         SectionPos sectionPos;
         if (CameraUtil.isPlayerMountedOnCamera(player)) {
            sectionPos = SectionPos.m_235861_(player.m_8954_());
         } else {
            sectionPos = player.m_8965_();
         }

         boolean flag = ChunkMap.m_200878_(chunkPos.f_45578_, chunkPos.f_45579_, sectionPos.m_123170_(), sectionPos.m_123222_(), j);
         boolean flag1 = ChunkMap.m_200878_(chunkPos.f_45578_, chunkPos.f_45579_, sectionPos.m_123170_(), sectionPos.m_123222_(), viewDistance);
         this.m_183754_(player, chunkPos, mutableObject, flag, flag1);
      });
      callback.cancel();
   }

   @Inject(
      method = {"move"},
      at = {@At("TAIL")}
   )
   private void trackCameraLoadedChunks(ServerPlayer player, CallbackInfo callback) {
      if (CameraUtil.isPlayerMountedOnCamera(player)) {
         SectionPos pos = SectionPos.m_235861_(player.m_8954_());
         ScryerCamera camera = (ScryerCamera)player.m_8954_();

         for (int i = pos.m_123170_() - this.f_140126_; i <= pos.m_123170_() + this.f_140126_; i++) {
            for (int j = pos.m_123222_() - this.f_140126_; j <= pos.m_123222_() + this.f_140126_; j++) {
               this.m_183754_(player, new ChunkPos(i, j), new MutableObject(), camera.hasLoadedChunks(), true);
            }
         }

         camera.setHasLoadedChunks(this.f_140126_);
      }
   }
}
