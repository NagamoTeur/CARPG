package com.cerbon.bosses_of_mass_destruction.capability;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.data.HistoricalData;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AutoRegisterCapability
public class PlayerMoveHistoryProvider implements ICapabilityProvider {
   public static final Capability<HistoricalData<Vec3>> HISTORICAL_DATA = CapabilityManager.get(new CapabilityToken<HistoricalData<Vec3>>() {
   });
   private HistoricalData<Vec3> positionalHistory;
   private final LazyOptional<HistoricalData<Vec3>> optional = LazyOptional.of(this::createHistoricalData);

   private HistoricalData<Vec3> createHistoricalData() {
      if (this.positionalHistory == null) {
         this.positionalHistory = new HistoricalData<>(Vec3.f_82478_, 10);
      }

      return this.positionalHistory;
   }

   @NotNull
   public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
      return cap == HISTORICAL_DATA ? this.optional.cast() : LazyOptional.empty();
   }
}
