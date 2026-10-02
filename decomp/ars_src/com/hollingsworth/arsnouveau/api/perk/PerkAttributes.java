package com.hollingsworth.arsnouveau.api.perk;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(
   modid = "ars_nouveau",
   bus = Bus.MOD
)
public class PerkAttributes {
   public static final HashMap<RegistryObject<Attribute>, UUID> UUIDS = new HashMap<>();
   public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, "ars_nouveau");
   public static final RegistryObject<Attribute> WARDING = registerAttribute(
      "ars_nouveau.perk.warding", id -> new RangedAttribute(id, 0.0, 0.0, 1024.0).m_22084_(true), "07625fbb-f186-46c3-8b5f-989b747f29f8"
   );
   public static final RegistryObject<Attribute> MANA_REGEN_BONUS = registerAttribute(
      "ars_nouveau.perk.mana_regen", id -> new RangedAttribute(id, 0.0, 0.0, 2000.0).m_22084_(true), "0c877091-ee4f-4eda-9868-4194d9a18833"
   );
   public static final RegistryObject<Attribute> MAX_MANA_BONUS = registerAttribute(
      "ars_nouveau.perk.percent_max_mana", id -> new RangedAttribute(id, 1.0, 0.0, 10000.0).m_22084_(true), "69a84402-96fd-4388-955e-57dc643c5ef0"
   );
   public static final RegistryObject<Attribute> FLAT_MANA_BONUS = registerAttribute(
      "ars_nouveau.perk.flat_max_mana", id -> new RangedAttribute(id, 0.0, 0.0, 10000.0).m_22084_(true), "22980b24-83e5-4683-a215-8997c4011389"
   );
   public static final RegistryObject<Attribute> SPELL_DAMAGE_BONUS = registerAttribute(
      "ars_nouveau.perk.spell_damage", id -> new RangedAttribute(id, 0.0, 0.0, 10000.0).m_22084_(true), "50b50137-9c92-4e64-b350-6044e9e609de"
   );
   public static final RegistryObject<Attribute> WHIRLIESPRIG = registerAttribute(
      "ars_nouveau.perk.saturation", id -> new RangedAttribute(id, 1.0, 0.0, 10000.0).m_22084_(true), "152810f7-0d01-484e-a512-73fe70af3db7"
   );
   public static final RegistryObject<Attribute> WIXIE = registerAttribute(
      "ars_nouveau.perk.wixie", id -> new RangedAttribute(id, 1.0, 0.0, 1024.0).m_22084_(true), "bae5d566-c9f6-4abf-9fe0-6ac140a34db1"
   );
   public static final RegistryObject<Attribute> FEATHER = registerAttribute(
      "ars_nouveau.perk.feather", id -> new RangedAttribute(id, 0.0, 0.0, 1.0).m_22084_(true), "ee3a4090-c5f5-4a26-a9c2-69837237b35f"
   );
   public static final RegistryObject<Attribute> TOUGHNESS = registerAttribute(
      "ars_nouveau.perk.toughness", id -> new RangedAttribute(id, 0.0, 0.0, 1024.0).m_22084_(true), "eb1ccdaf-38e3-4a1a-a5fb-b0dc698157ff"
   );

   public static RegistryObject<Attribute> registerAttribute(String name, Function<String, Attribute> attribute, String uuid) {
      return registerAttribute(name, attribute, UUID.fromString(uuid));
   }

   public static RegistryObject<Attribute> registerAttribute(String name, Function<String, Attribute> attribute, UUID uuid) {
      RegistryObject<Attribute> registryObject = ATTRIBUTES.register(name, () -> attribute.apply(name));
      UUIDS.put(registryObject, uuid);
      return registryObject;
   }

   @SubscribeEvent
   public static void modifyEntityAttributes(EntityAttributeModificationEvent event) {
      event.getTypes().stream().filter(e -> e == EntityType.f_20532_).forEach(e -> ATTRIBUTES.getEntries().forEach(v -> event.add(e, (Attribute)v.get())));
   }
}
