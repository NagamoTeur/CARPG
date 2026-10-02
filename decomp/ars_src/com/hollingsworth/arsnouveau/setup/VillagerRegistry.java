package com.hollingsworth.arsnouveau.setup;

import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VillagerRegistry {
   static String ARS_TRADER = "shady_wizard";
   public static final DeferredRegister<VillagerProfession> VILLAGERS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, "ars_nouveau");
   public static final DeferredRegister<PoiType> POIs = DeferredRegister.create(ForgeRegistries.POI_TYPES, "ars_nouveau");
   public static final RegistryObject<PoiType> ARCANE_POI = POIs.register(
      "arcane_poi", () -> new PoiType(ImmutableSet.copyOf(BlockRegistry.ARCANE_CORE_BLOCK.m_49965_().m_61056_()), 1, 1)
   );
   public static final RegistryObject<VillagerProfession> SHARDS_TRADER = VILLAGERS.register(
      ARS_TRADER,
      () -> new VillagerProfession(
            ARS_TRADER, x -> x.get() == ARCANE_POI.get(), x -> x.get() == ARCANE_POI.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.f_12566_
         )
   );
}
