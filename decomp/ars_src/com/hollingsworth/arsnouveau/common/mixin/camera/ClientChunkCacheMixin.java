package com.hollingsworth.arsnouveau.common.mixin.camera;

import com.hollingsworth.arsnouveau.common.camera.ANIChunkStorageProvider;
import com.hollingsworth.arsnouveau.common.camera.CameraController;
import com.hollingsworth.arsnouveau.common.util.CameraUtil;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientChunkCache.Storage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData.BlockEntityTagOutput;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.ChunkEvent.Load;
import net.minecraftforge.event.level.ChunkEvent.Unload;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
   value = {ClientChunkCache.class},
   priority = 1100
)
public abstract class ClientChunkCacheMixin implements ANIChunkStorageProvider {
   @Shadow
   volatile Storage f_104410_;
   @Shadow
   @Final
   ClientLevel f_104411_;

   @Shadow
   private static boolean m_104438_(LevelChunk chunk, int x, int z) {
      throw new IllegalStateException("Shadowing isValidChunk did not work!");
   }

   @Inject(
      method = {"<init>"},
      at = {@At("TAIL")}
   )
   public void onInit(ClientLevel level, int viewDistance, CallbackInfo ci) {
      CameraController.setCameraStorage(this.ANnewStorage(Math.max(2, viewDistance) + 3));
   }

   @Inject(
      method = {"updateViewRadius"},
      at = {@At("HEAD")}
   )
   public void onUpdateViewRadius(int viewDistance, CallbackInfo ci) {
      CameraController.setCameraStorage(this.ANnewStorage(Math.max(2, viewDistance) + 3));
   }

   @Inject(
      method = {"drop"},
      at = {@At("HEAD")}
   )
   public void onDrop(int x, int z, CallbackInfo ci) {
      Storage cameraStorage = CameraController.getCameraStorage();
      if (cameraStorage.m_104500_(x, z)) {
         int i = cameraStorage.m_104481_(x, z);
         LevelChunk chunk = cameraStorage.m_104479_(i);
         if (chunk != null && chunk.m_7697_().f_45578_ == x && chunk.m_7697_().f_45579_ == z) {
            MinecraftForge.EVENT_BUS.post(new Unload(chunk));
            cameraStorage.m_104487_(i, chunk, null);
         }
      }
   }

   @Inject(
      method = {"replaceWithPacketData"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onReplace(
      int x, int z, FriendlyByteBuf buffer, CompoundTag chunkTag, Consumer<BlockEntityTagOutput> tagOutputConsumer, CallbackInfoReturnable<LevelChunk> callback
   ) {
      Storage cameraStorage = CameraController.getCameraStorage();
      if (CameraUtil.isPlayerMountedOnCamera(Minecraft.m_91087_().f_91074_) && !this.f_104410_.m_104500_(x, z) && cameraStorage.m_104500_(x, z)) {
         int index = cameraStorage.m_104481_(x, z);
         LevelChunk chunk = cameraStorage.m_104479_(index);
         ChunkPos chunkPos = new ChunkPos(x, z);
         if (!m_104438_(chunk, x, z)) {
            chunk = new LevelChunk(this.f_104411_, chunkPos);
            chunk.m_187971_(buffer, chunkTag, tagOutputConsumer);
            cameraStorage.m_104484_(index, chunk);
         } else {
            chunk.m_187971_(buffer, chunkTag, tagOutputConsumer);
         }

         this.f_104411_.m_171649_(chunkPos);
         MinecraftForge.EVENT_BUS.post(new Load(chunk));
         callback.setReturnValue(chunk);
      }
   }

   @Inject(
      method = {"getChunk(IILnet/minecraft/world/level/chunk/ChunkStatus;Z)Lnet/minecraft/world/level/chunk/LevelChunk;"},
      at = {@At("TAIL")},
      cancellable = true
   )
   private void onGetChunk(int x, int z, ChunkStatus requiredStatus, boolean load, CallbackInfoReturnable<LevelChunk> callback) {
      if (CameraUtil.isPlayerMountedOnCamera(Minecraft.m_91087_().f_91074_)
         && !this.f_104410_.m_104500_(x, z)
         && CameraController.getCameraStorage().m_104500_(x, z)) {
         LevelChunk chunk = CameraController.getCameraStorage().m_104479_(CameraController.getCameraStorage().m_104481_(x, z));
         if (chunk != null && chunk.m_7697_().f_45578_ == x && chunk.m_7697_().f_45579_ == z) {
            callback.setReturnValue(chunk);
         }
      }
   }
}
