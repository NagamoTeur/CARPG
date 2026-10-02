package com.hollingsworth.arsnouveau.api.particle;

import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.RainbowParticleColor;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class ParticleColorRegistry {
   private static ConcurrentHashMap<ResourceLocation, Function<CompoundTag, ParticleColor>> MAP = new ConcurrentHashMap<>();

   public static void register(ResourceLocation id, Function<CompoundTag, ParticleColor> factory) {
      MAP.put(id, factory);
   }

   public static ParticleColor from(@Nullable CompoundTag compoundTag) {
      return compoundTag == null
         ? new ParticleColor(0, 0, 0)
         : MAP.getOrDefault(new ResourceLocation(compoundTag.m_128461_("type")), ParticleColor::new).apply(compoundTag);
   }

   static {
      MAP.put(ParticleColor.ID, ParticleColor::new);
      MAP.put(RainbowParticleColor.ID, RainbowParticleColor::new);
   }
}
