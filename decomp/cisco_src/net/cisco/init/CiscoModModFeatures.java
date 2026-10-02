package net.cisco.init;

import net.cisco.world.features.OwlTowerFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber
public class CiscoModModFeatures {
   public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, "cisco_mod");
   public static final RegistryObject<Feature<?>> OWL_TOWER = REGISTRY.register("owl_tower", OwlTowerFeature::feature);
}
