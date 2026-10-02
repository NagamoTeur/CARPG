package com.github.L_Ender.cataclysm.capabilities;

import com.github.L_Ender.cataclysm.init.ModCapabilities;
import java.util.UUID;
import javax.annotation.Nonnull;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class TidalTentacleCapability {
   public static ResourceLocation ID = new ResourceLocation("cataclysm", "tentacle_cap");

   public interface ITentacleCapability extends INBTSerializable<CompoundTag> {
      void setHasTentacle(boolean var1);

      boolean hasTentacle();

      void setLastTentacleID(int var1);

      int getLastTentacleID();

      UUID getLastTentacleUUID();

      void setLastTentacleUUID(UUID var1);
   }

   public static class TentacleCapabilityImp implements TidalTentacleCapability.ITentacleCapability {
      private UUID lastTentacle;
      private boolean tentacle;
      public int id;

      @Override
      public void setHasTentacle(boolean Tentacle) {
         this.tentacle = Tentacle;
      }

      @Override
      public boolean hasTentacle() {
         return this.tentacle;
      }

      @Override
      public void setLastTentacleID(int frozenPitch) {
         this.id = frozenPitch;
      }

      @Override
      public int getLastTentacleID() {
         return this.id;
      }

      @Override
      public void setLastTentacleUUID(UUID livingEntity) {
         this.lastTentacle = livingEntity;
      }

      @Override
      public UUID getLastTentacleUUID() {
         return this.lastTentacle;
      }

      public CompoundTag serializeNBT() {
         CompoundTag tag = new CompoundTag();
         tag.m_128379_("hasTentacle", this.hasTentacle());
         tag.m_128405_("getLastTentacleID", this.getLastTentacleID());
         if (this.getLastTentacleUUID() != null) {
            tag.m_128362_("getLastTentacleUUID", this.getLastTentacleUUID());
         }

         return tag;
      }

      public void deserializeNBT(CompoundTag nbt) {
         this.setHasTentacle(nbt.m_128471_("hasTentacle"));
         this.setLastTentacleID(nbt.m_128451_("getLastTentacleID"));

         try {
            this.setLastTentacleUUID(nbt.m_128342_("getLastTentacleUUID"));
         } catch (NullPointerException var3) {
         }
      }

      public static class TentacleProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
         private final LazyOptional<TidalTentacleCapability.ITentacleCapability> instance = LazyOptional.of(TidalTentacleCapability.TentacleCapabilityImp::new);

         public CompoundTag serializeNBT() {
            return (CompoundTag)((TidalTentacleCapability.ITentacleCapability)this.instance.orElseThrow(NullPointerException::new)).serializeNBT();
         }

         public void deserializeNBT(CompoundTag nbt) {
            ((TidalTentacleCapability.ITentacleCapability)this.instance.orElseThrow(NullPointerException::new)).deserializeNBT(nbt);
         }

         @Nonnull
         public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
            return ModCapabilities.TENTACLE_CAPABILITY.orEmpty(cap, this.instance.cast());
         }
      }
   }
}
