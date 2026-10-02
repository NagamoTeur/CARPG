package net.mindoth.dreadsteel.registries;

import net.mindoth.dreadsteel.item.CosmeticKit;
import net.mindoth.dreadsteel.item.DreadsteelIngot;
import net.mindoth.dreadsteel.item.armor.DreadsteelArmor;
import net.mindoth.dreadsteel.item.weapon.DreadsteelScythe;
import net.mindoth.dreadsteel.item.weapon.DreadsteelShield;
import net.mindoth.dreadsteel.item.weapon.DreadsteelTier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DreadsteelItems {
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "dreadsteel");
   public static final RegistryObject<Item> SCYTHE_PROJECTILE_DEFAULT = ITEMS.register("dreadsteel_scythe_projectile_default", () -> new Item(new Properties()));
   public static final RegistryObject<Item> SCYTHE_PROJECTILE_BLACK = ITEMS.register("dreadsteel_scythe_projectile_black", () -> new Item(new Properties()));
   public static final RegistryObject<Item> SCYTHE_PROJECTILE_BRONZE = ITEMS.register("dreadsteel_scythe_projectile_bronze", () -> new Item(new Properties()));
   public static final RegistryObject<Item> SCYTHE_PROJECTILE_WHITE = ITEMS.register("dreadsteel_scythe_projectile_white", () -> new Item(new Properties()));
   public static final RegistryObject<Item> DEFAULT_KIT = ITEMS.register(
      "kit_default", () -> new CosmeticKit(new Properties().m_41491_(CreativeModeTab.f_40759_))
   );
   public static final RegistryObject<Item> WHITE_KIT = ITEMS.register("kit_white", () -> new CosmeticKit(new Properties().m_41491_(CreativeModeTab.f_40759_)));
   public static final RegistryObject<Item> BLACK_KIT = ITEMS.register("kit_black", () -> new CosmeticKit(new Properties().m_41491_(CreativeModeTab.f_40759_)));
   public static final RegistryObject<Item> BRONZE_KIT = ITEMS.register(
      "kit_bronze", () -> new CosmeticKit(new Properties().m_41491_(CreativeModeTab.f_40759_))
   );
   public static final RegistryObject<Item> DREADSTEEL_INGOT = ITEMS.register(
      "dreadsteel_ingot", () -> new DreadsteelIngot(new Properties().m_41491_(CreativeModeTab.f_40759_).m_41486_())
   );
   public static final RegistryObject<Item> DREADSTEEL_HELMET = ITEMS.register(
      "dreadsteel_helmet", () -> new DreadsteelArmor(DreadsteelArmor.MaterialDreadsteel.DREADSTEEL, EquipmentSlot.HEAD, itemBuilder())
   );
   public static final RegistryObject<Item> DREADSTEEL_CHESTPLATE = ITEMS.register(
      "dreadsteel_chestplate", () -> new DreadsteelArmor(DreadsteelArmor.MaterialDreadsteel.DREADSTEEL, EquipmentSlot.CHEST, itemBuilder())
   );
   public static final RegistryObject<Item> DREADSTEEL_LEGGINGS = ITEMS.register(
      "dreadsteel_leggings", () -> new DreadsteelArmor(DreadsteelArmor.MaterialDreadsteel.DREADSTEEL, EquipmentSlot.LEGS, itemBuilder())
   );
   public static final RegistryObject<Item> DREADSTEEL_BOOTS = ITEMS.register(
      "dreadsteel_boots", () -> new DreadsteelArmor(DreadsteelArmor.MaterialDreadsteel.DREADSTEEL, EquipmentSlot.FEET, itemBuilder())
   );
   public static final RegistryObject<Item> DREADSTEEL_SCYTHE = ITEMS.register(
      "dreadsteel_scythe", () -> new DreadsteelScythe(DreadsteelTier.DREADSTEEL, 0, -2.4F, itemBuilder())
   );
   public static final RegistryObject<Item> DREADSTEEL_SHIELD = ITEMS.register("dreadsteel_shield", DreadsteelShield::new);

   private static Properties itemBuilder() {
      return new Properties().m_41491_(CreativeModeTab.f_40757_).m_41486_();
   }
}
