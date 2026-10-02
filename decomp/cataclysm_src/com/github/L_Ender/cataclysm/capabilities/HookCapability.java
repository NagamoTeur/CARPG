package com.github.L_Ender.cataclysm.capabilities;

import com.github.L_Ender.cataclysm.init.ModCapabilities;
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

public class HookCapability {
   public static ResourceLocation ID = new ResourceLocation("cataclysm", "hook_cap");

   public static class HookCapabilityImp implements HookCapability.IHookCapability {
      private boolean hook;

      @Override
      public void tick(LivingEntity entity) {
         if (this.hasHook() && !entity.m_20096_()) {
            entity.m_183634_();
         }
      }

      @Override
      public void setHasHook(boolean hook) {
         this.hook = hook;
      }

      @Override
      public boolean hasHook() {
         return this.hook;
      }

      public CompoundTag serializeNBT() {
         CompoundTag tag = new CompoundTag();
         tag.m_128379_("hasHook", this.hasHook());
         return tag;
      }

      public void deserializeNBT(CompoundTag nbt) {
         this.setHasHook(nbt.m_128471_("hasHook"));
      }

      public static class HookProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
         private final LazyOptional<HookCapability.IHookCapability> instance = LazyOptional.of(HookCapability.HookCapabilityImp::new);

         public CompoundTag serializeNBT() {
            return (CompoundTag)((HookCapability.IHookCapability)this.instance.orElseThrow(NullPointerException::new)).serializeNBT();
         }

         public void deserializeNBT(CompoundTag nbt) {
            ((HookCapability.IHookCapability)this.instance.orElseThrow(NullPointerException::new)).deserializeNBT(nbt);
         }

         @Nonnull
         public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
            return ModCapabilities.HOOK_CAPABILITY.orEmpty(cap, this.instance.cast());
         }
      }
   }

   public interface IHookCapability extends INBTSerializable<CompoundTag> {
      void tick(LivingEntity var1);

      void setHasHook(boolean var1);

      boolean hasHook();
   }
}
