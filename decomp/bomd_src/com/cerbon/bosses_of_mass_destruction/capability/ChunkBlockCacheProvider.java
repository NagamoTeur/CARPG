package com.cerbon.bosses_of_mass_destruction.capability;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AutoRegisterCapability
public class ChunkBlockCacheProvider implements ICapabilityProvider {
   public static final Capability<ChunkBlockCache> CHUNK_BLOCK_CACHE = CapabilityManager.get(new CapabilityToken<ChunkBlockCache>() {
   });
   private ChunkBlockCache chunkBlockCache;
   private final LazyOptional<ChunkBlockCache> optional = LazyOptional.of(this::createChunkCache);

   private ChunkBlockCache createChunkCache() {
      if (this.chunkBlockCache == null) {
         this.chunkBlockCache = new ChunkBlockCache();
      }

      return this.chunkBlockCache;
   }

   @NotNull
   public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
      return cap == CHUNK_BLOCK_CACHE ? this.optional.cast() : LazyOptional.empty();
   }
}
