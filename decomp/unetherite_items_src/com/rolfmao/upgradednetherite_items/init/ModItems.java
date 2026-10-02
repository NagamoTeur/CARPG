package com.rolfmao.upgradednetherite_items.init;

import com.rolfmao.upgradedcore.helpers.TabHelper;
import com.rolfmao.upgradedcore.init.ModRarity;
import com.rolfmao.upgradednetherite_items.items.EnchantedGolderiteApple;
import com.rolfmao.upgradednetherite_items.items.EnchantedNetheriteApple;
import com.rolfmao.upgradednetherite_items.items.EnderUpgradedNetheritePearlItem;
import com.rolfmao.upgradednetherite_items.items.GolderiteApple;
import com.rolfmao.upgradednetherite_items.items.NetheriteApple;
import com.rolfmao.upgradednetherite_items.items.NetheriteTotemBase;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "upgradednetherite_items");
   public static final RegistryObject<NetheriteApple> NETHERITE_APPLE = ITEMS.register("netherite_apple", NetheriteApple::new);
   public static final RegistryObject<EnchantedNetheriteApple> ENCHANTED_NETHERITE_APPLE = ITEMS.register(
      "enchanted_netherite_apple", EnchantedNetheriteApple::new
   );
   public static final RegistryObject<GolderiteApple> GOLD_UPGRADED_NETHERITE_APPLE = ITEMS.register("gold_upgraded_netherite_apple", GolderiteApple::new);
   public static final RegistryObject<EnchantedGolderiteApple> ENCHANTED_GOLD_UPGRADED_NETHERITE_APPLE = ITEMS.register(
      "enchanted_gold_upgraded_netherite_apple", EnchantedGolderiteApple::new
   );
   public static final RegistryObject<EnderUpgradedNetheritePearlItem> ENDER_UPGRADED_NETHERITE_PEARL = ITEMS.register(
      "ender_upgraded_netherite_pearl", EnderUpgradedNetheritePearlItem::new
   );
   public static final RegistryObject<Item> NETHERITE_TOTEM = ITEMS.register(
      "netherite_totem", () -> new NetheriteTotemBase(new Properties().m_41487_(1).m_41491_(CreativeModeTab.f_40757_).m_41497_(Rarity.UNCOMMON).m_41486_())
   );
   public static final RegistryObject<Item> GOLD_UPGRADED_NETHERITE_TOTEM = ITEMS.register(
      "gold_upgraded_netherite_totem", () -> new NetheriteTotemBase(new Properties().m_41487_(1).m_41491_(TabHelper.modTab()).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<Item> FIRE_UPGRADED_NETHERITE_TOTEM = ITEMS.register(
      "fire_upgraded_netherite_totem", () -> new NetheriteTotemBase(new Properties().m_41487_(1).m_41491_(TabHelper.modTab()).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<Item> ENDER_UPGRADED_NETHERITE_TOTEM = ITEMS.register(
      "ender_upgraded_netherite_totem",
      () -> new NetheriteTotemBase(new Properties().m_41487_(1).m_41491_(TabHelper.modTab()).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<Item> WATER_UPGRADED_NETHERITE_TOTEM = ITEMS.register(
      "water_upgraded_netherite_totem",
      () -> new NetheriteTotemBase(new Properties().m_41487_(1).m_41491_(TabHelper.modTab()).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<Item> WITHER_UPGRADED_NETHERITE_TOTEM = ITEMS.register(
      "wither_upgraded_netherite_totem",
      () -> new NetheriteTotemBase(new Properties().m_41487_(1).m_41491_(TabHelper.modTab()).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<Item> POISON_UPGRADED_NETHERITE_TOTEM = ITEMS.register(
      "poison_upgraded_netherite_totem",
      () -> new NetheriteTotemBase(new Properties().m_41487_(1).m_41491_(TabHelper.modTab()).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<Item> PHANTOM_UPGRADED_NETHERITE_TOTEM = ITEMS.register(
      "phantom_upgraded_netherite_totem",
      () -> new NetheriteTotemBase(new Properties().m_41487_(1).m_41491_(TabHelper.modTab()).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<Item> FEATHER_UPGRADED_NETHERITE_TOTEM = ITEMS.register(
      "feather_upgraded_netherite_totem",
      () -> new NetheriteTotemBase(new Properties().m_41487_(1).m_41491_(TabHelper.modTab()).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<Item> CORRUPT_UPGRADED_NETHERITE_TOTEM = ITEMS.register(
      "corrupt_upgraded_netherite_totem",
      () -> new NetheriteTotemBase(new Properties().m_41487_(1).m_41491_(TabHelper.modTab()).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<Item> ECHO_UPGRADED_NETHERITE_TOTEM = ITEMS.register(
      "echo_upgraded_netherite_totem", () -> new NetheriteTotemBase(new Properties().m_41487_(1).m_41491_(TabHelper.modTab()).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<Item> ULTIMATE_UPGRADED_NETHERITE_TOTEM = ITEMS.register(
      "ultimate_upgraded_netherite_totem",
      () -> new NetheriteTotemBase(new Properties().m_41487_(1).m_41491_(TabHelper.modTab()).m_41497_(ModRarity.UPGRADED_ULTIMATE).m_41486_())
   );
   public static final RegistryObject<Item> CREATIVE_UPGRADED_NETHERITE_TOTEM = ITEMS.register(
      "creative_upgraded_netherite_totem",
      () -> new NetheriteTotemBase(new Properties().m_41487_(1).m_41491_(TabHelper.modTabCrea()).m_41497_(ModRarity.UPGRADED_CREATIVE).m_41486_())
   );
}
