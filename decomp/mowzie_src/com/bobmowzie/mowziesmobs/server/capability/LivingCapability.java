package com.bobmowzie.mowziesmobs.server.capability;

import javax.annotation.Nonnull;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class LivingCapability {
   public static ResourceLocation ID = new ResourceLocation("mowziesmobs", "living_cap");

   public interface ILivingCapability extends INBTSerializable<CompoundTag> {
      void setLastDamage(float var1);

      float getLastDamage();

      void setHasSunblock(boolean var1);

      boolean getHasSunblock();

      void tick(LivingEntity var1);
   }

   public static class LivingCapabilityImp implements LivingCapability.ILivingCapability {
      float lastDamage = 0.0F;
      boolean hasSunblock;

      @Override
      public void setLastDamage(float damage) {
         this.lastDamage = damage;
      }

      @Override
      public float getLastDamage() {
         return this.lastDamage;
      }

      @Override
      public void setHasSunblock(boolean hasSunblock) {
         this.hasSunblock = hasSunblock;
      }

      @Override
      public boolean getHasSunblock() {
         return this.hasSunblock;
      }

      @Override
      public void tick(LivingEntity entity) {
      }

      public CompoundTag serializeNBT() {
         return new CompoundTag();
      }

      public void deserializeNBT(CompoundTag nbt) {
      }
   }

   public static class LivingProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
      private final LazyOptional<LivingCapability.ILivingCapability> instance = LazyOptional.of(LivingCapability.LivingCapabilityImp::new);

      public CompoundTag serializeNBT() {
         return (CompoundTag)((LivingCapability.ILivingCapability)this.instance.orElseThrow(NullPointerException::new)).serializeNBT();
      }

      public void deserializeNBT(CompoundTag nbt) {
         ((LivingCapability.ILivingCapability)this.instance.orElseThrow(NullPointerException::new)).deserializeNBT(nbt);
      }

      @Nonnull
      public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
         return CapabilityHandler.LIVING_CAPABILITY.orEmpty(cap, this.instance.cast());
      }
   }
}
