package com.aizistral.enigmaticlegacy.api.capabilities;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class EnigmaticCapabilities {
   public static final ResourceLocation ID_PLAYTIME_COUNTER = new ResourceLocation("enigmaticlegacy", "playtime_counter");
   public static final Capability<IPlaytimeCounter> PLAYTIME_COUNTER = CapabilityManager.get(new CapabilityToken<IPlaytimeCounter>() {
   });
}
