package net.xylonity.knightquest.registry;

import java.util.function.Supplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.registries.RegistryObject;
import net.xylonity.knightquest.KnightQuest;
import net.xylonity.knightquest.common.item.weapons.CleaverWeapon;
import net.xylonity.knightquest.common.item.weapons.KhopeshWeapon;
import net.xylonity.knightquest.common.item.weapons.KukriWeapon;
import net.xylonity.knightquest.common.item.weapons.NailWeapon;
import net.xylonity.knightquest.common.item.weapons.PaladinWeapon;
import net.xylonity.knightquest.common.item.weapons.UchigatanaWeapon;
import net.xylonity.knightquest.common.material.KQItemMaterials;

public class KnightQuestWeapons {
   public static final RegistryObject<Item> PALADIN_SWORD = register(
      "paladin_sword", () -> new PaladinWeapon(KQItemMaterials.PALADIN, -1, -2.6F, new Properties().m_41491_(KnightQuest.CREATIVE_MODE_TAB))
   );
   public static final RegistryObject<Item> NAIL = register(
      "nail_glaive", () -> new NailWeapon(KQItemMaterials.NAIL, -1, -2.4F, new Properties().m_41491_(KnightQuest.CREATIVE_MODE_TAB))
   );
   public static final RegistryObject<Item> UCHIGATANA = register(
      "uchigatana_katana", () -> new UchigatanaWeapon(KQItemMaterials.UCHIGATANA, -1, -2.2F, new Properties().m_41491_(KnightQuest.CREATIVE_MODE_TAB))
   );
   public static final RegistryObject<Item> KUKRI = register(
      "kukri_dagger", () -> new KukriWeapon(KQItemMaterials.KUKRI, -1, -1.0F, new Properties().m_41491_(KnightQuest.CREATIVE_MODE_TAB))
   );
   public static final RegistryObject<Item> KHOPESH = register(
      "khopesh_claymore", () -> new KhopeshWeapon(KQItemMaterials.KHOPESH, -1, -2.6F, new Properties().m_41491_(KnightQuest.CREATIVE_MODE_TAB))
   );
   public static final RegistryObject<Item> CLEAVER = register(
      "cleaver", () -> new CleaverWeapon(KQItemMaterials.CLEAVER, -1, -3.2F, new Properties().m_41491_(KnightQuest.CREATIVE_MODE_TAB))
   );

   public static void init() {
   }

   private static RegistryObject<Item> register(String id, Supplier<Item> item) {
      return KnightQuestItems.ITEMS.register(id, item);
   }
}
