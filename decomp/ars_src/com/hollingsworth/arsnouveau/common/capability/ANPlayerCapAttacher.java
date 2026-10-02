package com.hollingsworth.arsnouveau.common.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ANPlayerCapAttacher {
   public static void attach(AttachCapabilitiesEvent<Entity> event) {
      ANPlayerCapAttacher.ANPlayerCapProvider provider = new ANPlayerCapAttacher.ANPlayerCapProvider();
      event.addCapability(ANPlayerCapAttacher.ANPlayerCapProvider.IDENTIFIER, provider);
   }

   private static class ANPlayerCapProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
      public static final ResourceLocation IDENTIFIER = new ResourceLocation("ars_nouveau", "player_data");
      private final IPlayerCap backend = new ANPlayerDataCap();
      private final LazyOptional<IPlayerCap> optionalData = LazyOptional.of(() -> this.backend);

      @NotNull
      public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
         return CapabilityRegistry.PLAYER_DATA_CAP.orEmpty(cap, this.optionalData);
      }

      void invalidate() {
         this.optionalData.invalidate();
      }

      public CompoundTag serializeNBT() {
         return (CompoundTag)this.backend.serializeNBT();
      }

      public void deserializeNBT(CompoundTag nbt) {
         this.backend.deserializeNBT(nbt);
      }
   }
}
