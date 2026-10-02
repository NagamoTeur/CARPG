package com.aqutheseal.celestisynth.common.registry;

import com.aqutheseal.celestisynth.common.item.misc.CelestialCoreItem;
import com.aqutheseal.celestisynth.common.item.weapons.AquafloraItem;
import com.aqutheseal.celestisynth.common.item.weapons.BreezebreakerItem;
import com.aqutheseal.celestisynth.common.item.weapons.CrescentiaItem;
import com.aqutheseal.celestisynth.common.item.weapons.PoltergeistItem;
import com.aqutheseal.celestisynth.common.item.weapons.RainfallSerenityItem;
import com.aqutheseal.celestisynth.common.item.weapons.SolarisItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CSItems {
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "celestisynth");
   public static final RegistryObject<Item> CELESTIAL_CORE = ITEMS.register(
      "celestial_core", () -> new CelestialCoreItem(new Properties().m_41497_(Rarity.UNCOMMON).m_41491_(CSCreativeTabs.CELESTISYNTH))
   );
   public static final RegistryObject<Item> CELESTIAL_CORE_HEATED = ITEMS.register(
      "celestial_core_heated", () -> new CelestialCoreItem(new Properties().m_41497_(Rarity.UNCOMMON).m_41491_(CSCreativeTabs.CELESTISYNTH))
   );
   public static final RegistryObject<Item> SUPERNAL_NETHERITE_INGOT = ITEMS.register(
      "supernal_netherite_ingot", () -> new Item(new Properties().m_41497_(Rarity.UNCOMMON).m_41491_(CSCreativeTabs.CELESTISYNTH))
   );
   public static final RegistryObject<Item> CELESTIAL_NETHERITE_INGOT = ITEMS.register(
      "celestial_netherite_ingot", () -> new Item(new Properties().m_41497_(Rarity.RARE).m_41491_(CSCreativeTabs.CELESTISYNTH))
   );
   public static final RegistryObject<Item> LUNAR_SCRAP = ITEMS.register("lunar_scrap", () -> new Item(new Properties().m_41491_(CSCreativeTabs.CELESTISYNTH)));
   public static final RegistryObject<Item> EYEBOMINATION = ITEMS.register(
      "eyebomination", () -> new Item(new Properties().m_41491_(CSCreativeTabs.CELESTISYNTH))
   );
   public static final RegistryObject<Item> STARSTRUCK_SCRAP = ITEMS.register(
      "starstruck_scrap", () -> new Item(new Properties().m_41491_(CSCreativeTabs.CELESTISYNTH))
   );
   public static final RegistryObject<Item> STARSTRUCK_FEATHER = ITEMS.register(
      "starstruck_feather", () -> new Item(new Properties().m_41491_(CSCreativeTabs.CELESTISYNTH))
   );
   public static final RegistryObject<Item> TEMPEST_SPAWN_EGG = ITEMS.register(
      "tempest_spawn_egg", () -> new ForgeSpawnEggItem(CSEntityTypes.TEMPEST, 0, 0, new Properties())
   );
   public static final RegistryObject<Item> SOLARIS = ITEMS.register(
      "solaris",
      () -> new SolarisItem(Tiers.NETHERITE, 3, -2.5F, new Properties().m_41486_().m_41497_(CSRarityTypes.CELESTIAL).m_41491_(CSCreativeTabs.CELESTISYNTH))
   );
   public static final RegistryObject<Item> CRESCENTIA = ITEMS.register(
      "crescentia",
      () -> new CrescentiaItem(Tiers.NETHERITE, 4, -2.7F, new Properties().m_41486_().m_41497_(CSRarityTypes.CELESTIAL).m_41491_(CSCreativeTabs.CELESTISYNTH))
   );
   public static final RegistryObject<Item> BREEZEBREAKER = ITEMS.register(
      "breezebreaker",
      () -> new BreezebreakerItem(
            Tiers.NETHERITE, 1, -2.0F, new Properties().m_41486_().m_41497_(CSRarityTypes.CELESTIAL).m_41491_(CSCreativeTabs.CELESTISYNTH)
         )
   );
   public static final RegistryObject<Item> POLTERGEIST = ITEMS.register(
      "poltergeist",
      () -> new PoltergeistItem(Tiers.NETHERITE, 6, -3.1F, new Properties().m_41486_().m_41497_(CSRarityTypes.CELESTIAL).m_41491_(CSCreativeTabs.CELESTISYNTH))
   );
   public static final RegistryObject<Item> AQUAFLORA = ITEMS.register(
      "aquaflora",
      () -> new AquafloraItem(Tiers.NETHERITE, -2, -1.1F, new Properties().m_41486_().m_41497_(CSRarityTypes.CELESTIAL).m_41491_(CSCreativeTabs.CELESTISYNTH))
   );
   public static final RegistryObject<Item> RAINFALL_SERENITY = ITEMS.register(
      "rainfall_serenity",
      () -> new RainfallSerenityItem(new Properties().m_41486_().m_41503_(1200).m_41497_(CSRarityTypes.CELESTIAL).m_41491_(CSCreativeTabs.CELESTISYNTH))
   );
}
