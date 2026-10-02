package com.hollingsworth.arsnouveau.common.camera;

import java.util.Objects;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientChunkCache.Storage;

public interface ANIChunkStorageProvider {
   default Storage ANnewStorage(int viewDistance) {
      if (this instanceof ClientChunkCache cache) {
         Objects.requireNonNull(cache);
         return new Storage(cache, viewDistance);
      } else {
         return null;
      }
   }
}
