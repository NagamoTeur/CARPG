package com.github.L_Ender.cataclysm.capabilities;

import com.github.L_Ender.cataclysm.init.ModCapabilities;
import javax.annotation.Nonnull;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class ParryCapability {
   public static ResourceLocation ID = new ResourceLocation("cataclysm", "parry_cap");

   public interface IParryCapability extends INBTSerializable<CompoundTag> {
      void setParryFrame(int var1);

      int getParryFrame();
   }

   public static class ParryCapabilityImp implements ParryCapability.IParryCapability {
      public int frame;

      @Override
      public void setParryFrame(int timer) {
         this.frame = timer;
      }

      @Override
      public int getParryFrame() {
         return this.frame;
      }

      public CompoundTag serializeNBT() {
         CompoundTag tag = new CompoundTag();
         tag.m_128405_("frame", this.getParryFrame());
         return tag;
      }

      public void deserializeNBT(CompoundTag nbt) {
         this.setParryFrame(nbt.m_128451_("frame"));
      }

      public static class ParryProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
         private final LazyOptional<ParryCapability.IParryCapability> instance = LazyOptional.of(ParryCapability.ParryCapabilityImp::new);

         public CompoundTag serializeNBT() {
            return (CompoundTag)((ParryCapability.IParryCapability)this.instance.orElseThrow(NullPointerException::new)).serializeNBT();
         }

         public void deserializeNBT(CompoundTag nbt) {
            ((ParryCapability.IParryCapability)this.instance.orElseThrow(NullPointerException::new)).deserializeNBT(nbt);
         }

         @Nonnull
         public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
            return ModCapabilities.PARRY_CAPABILITY.orEmpty(cap, this.instance.cast());
         }
      }
   }
}
