package com.hollingsworth.arsnouveau.common.capability;

import com.hollingsworth.arsnouveau.api.mana.IManaCap;
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

public class ManaCapAttacher {
   public static void attach(AttachCapabilitiesEvent<Entity> event) {
      ManaCapAttacher.ManaCapProvider provider = new ManaCapAttacher.ManaCapProvider();
      event.addCapability(ManaCapAttacher.ManaCapProvider.IDENTIFIER, provider);
   }

   private static class ManaCapProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
      public static final ResourceLocation IDENTIFIER = new ResourceLocation("ars_nouveau", "mana");
      private final IManaCap backend = new ManaCap(null);
      private final LazyOptional<IManaCap> optionalData = LazyOptional.of(() -> this.backend);

      @NotNull
      public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
         return CapabilityRegistry.MANA_CAPABILITY.orEmpty(cap, this.optionalData);
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
