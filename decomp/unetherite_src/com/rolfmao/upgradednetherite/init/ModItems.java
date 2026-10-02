package com.rolfmao.upgradednetherite.init;

import com.rolfmao.upgradedcore.init.ModRarity;
import com.rolfmao.upgradednetherite.UpgradedNetheriteMod;
import com.rolfmao.upgradednetherite.content.UpgradedNetheriteArmor;
import com.rolfmao.upgradednetherite.content.UpgradedNetheriteAxe;
import com.rolfmao.upgradednetherite.content.UpgradedNetheriteBow;
import com.rolfmao.upgradednetherite.content.UpgradedNetheriteCrossbow;
import com.rolfmao.upgradednetherite.content.UpgradedNetheriteHorseArmor;
import com.rolfmao.upgradednetherite.content.UpgradedNetheritePickaxe;
import com.rolfmao.upgradednetherite.content.UpgradedNetheriteShield;
import com.rolfmao.upgradednetherite.content.UpgradedNetheriteShovel;
import com.rolfmao.upgradednetherite.content.UpgradedNetheriteSword;
import com.rolfmao.upgradednetherite.items.UpgradedNetheriteEssenceItemBase;
import com.rolfmao.upgradednetherite.items.UpgradedNetheriteIngotItemBase;
import com.rolfmao.upgradednetherite.utils.enums.ModArmorMaterial;
import com.rolfmao.upgradednetherite.utils.enums.ModItemTier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "upgradednetherite");
   public static final RegistryObject<Item> GOLD_UPGRADED_NETHERITE_INGOT = ITEMS.register("gold_upgraded_netherite_ingot", UpgradedNetheriteIngotItemBase::new);
   public static final RegistryObject<Item> FIRE_UPGRADED_NETHERITE_INGOT = ITEMS.register("fire_upgraded_netherite_ingot", UpgradedNetheriteIngotItemBase::new);
   public static final RegistryObject<Item> ENDER_UPGRADED_NETHERITE_INGOT = ITEMS.register(
      "ender_upgraded_netherite_ingot", UpgradedNetheriteIngotItemBase::new
   );
   public static final RegistryObject<Item> WATER_UPGRADED_NETHERITE_INGOT = ITEMS.register(
      "water_upgraded_netherite_ingot", UpgradedNetheriteIngotItemBase::new
   );
   public static final RegistryObject<Item> WITHER_UPGRADED_NETHERITE_INGOT = ITEMS.register(
      "wither_upgraded_netherite_ingot", UpgradedNetheriteIngotItemBase::new
   );
   public static final RegistryObject<Item> POISON_UPGRADED_NETHERITE_INGOT = ITEMS.register(
      "poison_upgraded_netherite_ingot", UpgradedNetheriteIngotItemBase::new
   );
   public static final RegistryObject<Item> PHANTOM_UPGRADED_NETHERITE_INGOT = ITEMS.register(
      "phantom_upgraded_netherite_ingot", UpgradedNetheriteIngotItemBase::new
   );
   public static final RegistryObject<Item> FEATHER_UPGRADED_NETHERITE_INGOT = ITEMS.register(
      "feather_upgraded_netherite_ingot", UpgradedNetheriteIngotItemBase::new
   );
   public static final RegistryObject<Item> CORRUPT_UPGRADED_NETHERITE_INGOT = ITEMS.register(
      "corrupt_upgraded_netherite_ingot", UpgradedNetheriteIngotItemBase::new
   );
   public static final RegistryObject<Item> ECHO_UPGRADED_NETHERITE_INGOT = ITEMS.register("echo_upgraded_netherite_ingot", UpgradedNetheriteIngotItemBase::new);
   public static final RegistryObject<Item> GOLD_ESSENCE = ITEMS.register("gold_essence", UpgradedNetheriteEssenceItemBase::new);
   public static final RegistryObject<Item> FIRE_ESSENCE = ITEMS.register("fire_essence", UpgradedNetheriteEssenceItemBase::new);
   public static final RegistryObject<Item> ENDER_ESSENCE = ITEMS.register("ender_essence", UpgradedNetheriteEssenceItemBase::new);
   public static final RegistryObject<Item> WATER_ESSENCE = ITEMS.register("water_essence", UpgradedNetheriteEssenceItemBase::new);
   public static final RegistryObject<Item> WITHER_ESSENCE = ITEMS.register("wither_essence", UpgradedNetheriteEssenceItemBase::new);
   public static final RegistryObject<Item> POISON_ESSENCE = ITEMS.register("poison_essence", UpgradedNetheriteEssenceItemBase::new);
   public static final RegistryObject<Item> PHANTOM_ESSENCE = ITEMS.register("phantom_essence", UpgradedNetheriteEssenceItemBase::new);
   public static final RegistryObject<Item> FEATHER_ESSENCE = ITEMS.register("feather_essence", UpgradedNetheriteEssenceItemBase::new);
   public static final RegistryObject<Item> CORRUPT_ESSENCE = ITEMS.register("corrupt_essence", UpgradedNetheriteEssenceItemBase::new);
   public static final RegistryObject<Item> ECHO_ESSENCE = ITEMS.register("echo_essence", UpgradedNetheriteEssenceItemBase::new);
   public static final RegistryObject<UpgradedNetheriteSword> GOLD_UPGRADED_NETHERITE_SWORD = ITEMS.register(
      "gold_upgraded_netherite_sword",
      () -> new UpgradedNetheriteSword(
            ModItemTier.GOLD_UPGRADED_NETHERITE, 4, -2.4F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteSword> FIRE_UPGRADED_NETHERITE_SWORD = ITEMS.register(
      "fire_upgraded_netherite_sword",
      () -> new UpgradedNetheriteSword(
            ModItemTier.FIRE_UPGRADED_NETHERITE, 4, -2.4F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteSword> ENDER_UPGRADED_NETHERITE_SWORD = ITEMS.register(
      "ender_upgraded_netherite_sword",
      () -> new UpgradedNetheriteSword(
            ModItemTier.ENDER_UPGRADED_NETHERITE, 4, -2.4F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteSword> WATER_UPGRADED_NETHERITE_SWORD = ITEMS.register(
      "water_upgraded_netherite_sword",
      () -> new UpgradedNetheriteSword(
            ModItemTier.WATER_UPGRADED_NETHERITE, 4, -2.4F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteSword> WITHER_UPGRADED_NETHERITE_SWORD = ITEMS.register(
      "wither_upgraded_netherite_sword",
      () -> new UpgradedNetheriteSword(
            ModItemTier.WITHER_UPGRADED_NETHERITE, 4, -2.4F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteSword> POISON_UPGRADED_NETHERITE_SWORD = ITEMS.register(
      "poison_upgraded_netherite_sword",
      () -> new UpgradedNetheriteSword(
            ModItemTier.POISON_UPGRADED_NETHERITE, 4, -2.4F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteSword> PHANTOM_UPGRADED_NETHERITE_SWORD = ITEMS.register(
      "phantom_upgraded_netherite_sword",
      () -> new UpgradedNetheriteSword(
            ModItemTier.PHANTOM_UPGRADED_NETHERITE, 4, -2.4F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteSword> FEATHER_UPGRADED_NETHERITE_SWORD = ITEMS.register(
      "feather_upgraded_netherite_sword",
      () -> new UpgradedNetheriteSword(
            ModItemTier.FEATHER_UPGRADED_NETHERITE, 4, -2.4F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteSword> CORRUPT_UPGRADED_NETHERITE_SWORD = ITEMS.register(
      "corrupt_upgraded_netherite_sword",
      () -> new UpgradedNetheriteSword(
            ModItemTier.CORRUPT_UPGRADED_NETHERITE, 4, -2.4F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteSword> ECHO_UPGRADED_NETHERITE_SWORD = ITEMS.register(
      "echo_upgraded_netherite_sword",
      () -> new UpgradedNetheriteSword(
            ModItemTier.ECHO_UPGRADED_NETHERITE, 4, -2.4F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteBow> NETHERITE_BOW = ITEMS.register(
      "netherite_bow", () -> new UpgradedNetheriteBow(new Properties().m_41503_(768).m_41491_(CreativeModeTab.f_40757_).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheriteBow> GOLD_UPGRADED_NETHERITE_BOW = ITEMS.register(
      "gold_upgraded_netherite_bow",
      () -> new UpgradedNetheriteBow(new Properties().m_41503_(768).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheriteBow> FIRE_UPGRADED_NETHERITE_BOW = ITEMS.register(
      "fire_upgraded_netherite_bow",
      () -> new UpgradedNetheriteBow(new Properties().m_41503_(768).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheriteBow> ENDER_UPGRADED_NETHERITE_BOW = ITEMS.register(
      "ender_upgraded_netherite_bow",
      () -> new UpgradedNetheriteBow(new Properties().m_41503_(768).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheriteBow> WATER_UPGRADED_NETHERITE_BOW = ITEMS.register(
      "water_upgraded_netherite_bow",
      () -> new UpgradedNetheriteBow(new Properties().m_41503_(768).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheriteBow> WITHER_UPGRADED_NETHERITE_BOW = ITEMS.register(
      "wither_upgraded_netherite_bow",
      () -> new UpgradedNetheriteBow(new Properties().m_41503_(768).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheriteBow> POISON_UPGRADED_NETHERITE_BOW = ITEMS.register(
      "poison_upgraded_netherite_bow",
      () -> new UpgradedNetheriteBow(new Properties().m_41503_(768).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheriteBow> PHANTOM_UPGRADED_NETHERITE_BOW = ITEMS.register(
      "phantom_upgraded_netherite_bow",
      () -> new UpgradedNetheriteBow(new Properties().m_41503_(768).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheriteBow> FEATHER_UPGRADED_NETHERITE_BOW = ITEMS.register(
      "feather_upgraded_netherite_bow",
      () -> new UpgradedNetheriteBow(new Properties().m_41503_(768).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheriteBow> CORRUPT_UPGRADED_NETHERITE_BOW = ITEMS.register(
      "corrupt_upgraded_netherite_bow",
      () -> new UpgradedNetheriteBow(new Properties().m_41503_(768).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheriteBow> ECHO_UPGRADED_NETHERITE_BOW = ITEMS.register(
      "echo_upgraded_netherite_bow",
      () -> new UpgradedNetheriteBow(new Properties().m_41503_(768).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheriteCrossbow> NETHERITE_CROSSBOW = ITEMS.register(
      "netherite_crossbow", () -> new UpgradedNetheriteCrossbow(new Properties().m_41503_(652).m_41491_(CreativeModeTab.f_40757_).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheriteCrossbow> GOLD_UPGRADED_NETHERITE_CROSSBOW = ITEMS.register(
      "gold_upgraded_netherite_crossbow",
      () -> new UpgradedNetheriteCrossbow(new Properties().m_41503_(652).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheriteCrossbow> FIRE_UPGRADED_NETHERITE_CROSSBOW = ITEMS.register(
      "fire_upgraded_netherite_crossbow",
      () -> new UpgradedNetheriteCrossbow(new Properties().m_41503_(652).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheriteCrossbow> ENDER_UPGRADED_NETHERITE_CROSSBOW = ITEMS.register(
      "ender_upgraded_netherite_crossbow",
      () -> new UpgradedNetheriteCrossbow(new Properties().m_41503_(652).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheriteCrossbow> WATER_UPGRADED_NETHERITE_CROSSBOW = ITEMS.register(
      "water_upgraded_netherite_crossbow",
      () -> new UpgradedNetheriteCrossbow(new Properties().m_41503_(652).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheriteCrossbow> WITHER_UPGRADED_NETHERITE_CROSSBOW = ITEMS.register(
      "wither_upgraded_netherite_crossbow",
      () -> new UpgradedNetheriteCrossbow(new Properties().m_41503_(652).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheriteCrossbow> POISON_UPGRADED_NETHERITE_CROSSBOW = ITEMS.register(
      "poison_upgraded_netherite_crossbow",
      () -> new UpgradedNetheriteCrossbow(new Properties().m_41503_(652).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheriteCrossbow> PHANTOM_UPGRADED_NETHERITE_CROSSBOW = ITEMS.register(
      "phantom_upgraded_netherite_crossbow",
      () -> new UpgradedNetheriteCrossbow(new Properties().m_41503_(652).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheriteCrossbow> FEATHER_UPGRADED_NETHERITE_CROSSBOW = ITEMS.register(
      "feather_upgraded_netherite_crossbow",
      () -> new UpgradedNetheriteCrossbow(new Properties().m_41503_(652).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheriteCrossbow> CORRUPT_UPGRADED_NETHERITE_CROSSBOW = ITEMS.register(
      "corrupt_upgraded_netherite_crossbow",
      () -> new UpgradedNetheriteCrossbow(new Properties().m_41503_(652).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheriteCrossbow> ECHO_UPGRADED_NETHERITE_CROSSBOW = ITEMS.register(
      "echo_upgraded_netherite_crossbow",
      () -> new UpgradedNetheriteCrossbow(new Properties().m_41503_(652).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<UpgradedNetheritePickaxe> GOLD_UPGRADED_NETHERITE_PICKAXE = ITEMS.register(
      "gold_upgraded_netherite_pickaxe",
      () -> new UpgradedNetheritePickaxe(
            ModItemTier.GOLD_UPGRADED_NETHERITE, 2, -2.8F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheritePickaxe> FIRE_UPGRADED_NETHERITE_PICKAXE = ITEMS.register(
      "fire_upgraded_netherite_pickaxe",
      () -> new UpgradedNetheritePickaxe(
            ModItemTier.FIRE_UPGRADED_NETHERITE, 2, -2.8F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheritePickaxe> ENDER_UPGRADED_NETHERITE_PICKAXE = ITEMS.register(
      "ender_upgraded_netherite_pickaxe",
      () -> new UpgradedNetheritePickaxe(
            ModItemTier.ENDER_UPGRADED_NETHERITE, 2, -2.8F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheritePickaxe> WATER_UPGRADED_NETHERITE_PICKAXE = ITEMS.register(
      "water_upgraded_netherite_pickaxe",
      () -> new UpgradedNetheritePickaxe(
            ModItemTier.WATER_UPGRADED_NETHERITE, 2, -2.8F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheritePickaxe> PHANTOM_UPGRADED_NETHERITE_PICKAXE = ITEMS.register(
      "phantom_upgraded_netherite_pickaxe",
      () -> new UpgradedNetheritePickaxe(
            ModItemTier.PHANTOM_UPGRADED_NETHERITE, 2, -2.8F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheritePickaxe> FEATHER_UPGRADED_NETHERITE_PICKAXE = ITEMS.register(
      "feather_upgraded_netherite_pickaxe",
      () -> new UpgradedNetheritePickaxe(
            ModItemTier.FEATHER_UPGRADED_NETHERITE, 2, -2.8F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheritePickaxe> CORRUPT_UPGRADED_NETHERITE_PICKAXE = ITEMS.register(
      "corrupt_upgraded_netherite_pickaxe",
      () -> new UpgradedNetheritePickaxe(
            ModItemTier.CORRUPT_UPGRADED_NETHERITE, 2, -2.8F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheritePickaxe> ECHO_UPGRADED_NETHERITE_PICKAXE = ITEMS.register(
      "echo_upgraded_netherite_pickaxe",
      () -> new UpgradedNetheritePickaxe(
            ModItemTier.ECHO_UPGRADED_NETHERITE, 2, -2.8F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteShovel> GOLD_UPGRADED_NETHERITE_SHOVEL = ITEMS.register(
      "gold_upgraded_netherite_shovel",
      () -> new UpgradedNetheriteShovel(
            ModItemTier.GOLD_UPGRADED_NETHERITE, 2.5F, -3.0F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteShovel> FIRE_UPGRADED_NETHERITE_SHOVEL = ITEMS.register(
      "fire_upgraded_netherite_shovel",
      () -> new UpgradedNetheriteShovel(
            ModItemTier.FIRE_UPGRADED_NETHERITE, 2.5F, -3.0F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteShovel> ENDER_UPGRADED_NETHERITE_SHOVEL = ITEMS.register(
      "ender_upgraded_netherite_shovel",
      () -> new UpgradedNetheriteShovel(
            ModItemTier.ENDER_UPGRADED_NETHERITE, 2.5F, -3.0F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteShovel> WATER_UPGRADED_NETHERITE_SHOVEL = ITEMS.register(
      "water_upgraded_netherite_shovel",
      () -> new UpgradedNetheriteShovel(
            ModItemTier.WATER_UPGRADED_NETHERITE, 2.5F, -3.0F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteShovel> PHANTOM_UPGRADED_NETHERITE_SHOVEL = ITEMS.register(
      "phantom_upgraded_netherite_shovel",
      () -> new UpgradedNetheriteShovel(
            ModItemTier.PHANTOM_UPGRADED_NETHERITE, 2.5F, -3.0F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteShovel> FEATHER_UPGRADED_NETHERITE_SHOVEL = ITEMS.register(
      "feather_upgraded_netherite_shovel",
      () -> new UpgradedNetheriteShovel(
            ModItemTier.FEATHER_UPGRADED_NETHERITE, 2.5F, -3.0F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteShovel> CORRUPT_UPGRADED_NETHERITE_SHOVEL = ITEMS.register(
      "corrupt_upgraded_netherite_shovel",
      () -> new UpgradedNetheriteShovel(
            ModItemTier.CORRUPT_UPGRADED_NETHERITE, 2.5F, -3.0F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteShovel> ECHO_UPGRADED_NETHERITE_SHOVEL = ITEMS.register(
      "echo_upgraded_netherite_shovel",
      () -> new UpgradedNetheriteShovel(
            ModItemTier.ECHO_UPGRADED_NETHERITE, 2.5F, -3.0F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteAxe> GOLD_UPGRADED_NETHERITE_AXE = ITEMS.register(
      "gold_upgraded_netherite_axe",
      () -> new UpgradedNetheriteAxe(
            ModItemTier.GOLD_UPGRADED_NETHERITE, 6, -3.0F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteAxe> FIRE_UPGRADED_NETHERITE_AXE = ITEMS.register(
      "fire_upgraded_netherite_axe",
      () -> new UpgradedNetheriteAxe(
            ModItemTier.FIRE_UPGRADED_NETHERITE, 6, -3.0F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteAxe> ENDER_UPGRADED_NETHERITE_AXE = ITEMS.register(
      "ender_upgraded_netherite_axe",
      () -> new UpgradedNetheriteAxe(
            ModItemTier.ENDER_UPGRADED_NETHERITE, 6, -3.0F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteAxe> WATER_UPGRADED_NETHERITE_AXE = ITEMS.register(
      "water_upgraded_netherite_axe",
      () -> new UpgradedNetheriteAxe(
            ModItemTier.WATER_UPGRADED_NETHERITE, 6, -3.0F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteAxe> WITHER_UPGRADED_NETHERITE_AXE = ITEMS.register(
      "wither_upgraded_netherite_axe",
      () -> new UpgradedNetheriteAxe(
            ModItemTier.WITHER_UPGRADED_NETHERITE, 6, -3.0F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteAxe> POISON_UPGRADED_NETHERITE_AXE = ITEMS.register(
      "poison_upgraded_netherite_axe",
      () -> new UpgradedNetheriteAxe(
            ModItemTier.POISON_UPGRADED_NETHERITE, 6, -3.0F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteAxe> PHANTOM_UPGRADED_NETHERITE_AXE = ITEMS.register(
      "phantom_upgraded_netherite_axe",
      () -> new UpgradedNetheriteAxe(
            ModItemTier.PHANTOM_UPGRADED_NETHERITE, 6, -3.0F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteAxe> FEATHER_UPGRADED_NETHERITE_AXE = ITEMS.register(
      "feather_upgraded_netherite_axe",
      () -> new UpgradedNetheriteAxe(
            ModItemTier.FEATHER_UPGRADED_NETHERITE, 6, -3.0F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteAxe> CORRUPT_UPGRADED_NETHERITE_AXE = ITEMS.register(
      "corrupt_upgraded_netherite_axe",
      () -> new UpgradedNetheriteAxe(
            ModItemTier.CORRUPT_UPGRADED_NETHERITE, 6, -3.0F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<UpgradedNetheriteAxe> ECHO_UPGRADED_NETHERITE_AXE = ITEMS.register(
      "echo_upgraded_netherite_axe",
      () -> new UpgradedNetheriteAxe(
            ModItemTier.ECHO_UPGRADED_NETHERITE, 6, -3.0F, new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<Item> NETHERITE_SHIELD = ITEMS.register(
      "netherite_shield", () -> new UpgradedNetheriteShield(new Properties().m_41503_(672).m_41491_(CreativeModeTab.f_40757_).m_41486_())
   );
   public static final RegistryObject<Item> GOLD_UPGRADED_NETHERITE_SHIELD = ITEMS.register(
      "gold_upgraded_netherite_shield",
      () -> new UpgradedNetheriteShield(new Properties().m_41503_(672).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<Item> FIRE_UPGRADED_NETHERITE_SHIELD = ITEMS.register(
      "fire_upgraded_netherite_shield",
      () -> new UpgradedNetheriteShield(new Properties().m_41503_(672).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<Item> ENDER_UPGRADED_NETHERITE_SHIELD = ITEMS.register(
      "ender_upgraded_netherite_shield",
      () -> new UpgradedNetheriteShield(new Properties().m_41503_(672).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<Item> WATER_UPGRADED_NETHERITE_SHIELD = ITEMS.register(
      "water_upgraded_netherite_shield",
      () -> new UpgradedNetheriteShield(new Properties().m_41503_(672).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<Item> WITHER_UPGRADED_NETHERITE_SHIELD = ITEMS.register(
      "wither_upgraded_netherite_shield",
      () -> new UpgradedNetheriteShield(new Properties().m_41503_(672).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<Item> POISON_UPGRADED_NETHERITE_SHIELD = ITEMS.register(
      "poison_upgraded_netherite_shield",
      () -> new UpgradedNetheriteShield(new Properties().m_41503_(672).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<Item> PHANTOM_UPGRADED_NETHERITE_SHIELD = ITEMS.register(
      "phantom_upgraded_netherite_shield",
      () -> new UpgradedNetheriteShield(new Properties().m_41503_(672).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<Item> FEATHER_UPGRADED_NETHERITE_SHIELD = ITEMS.register(
      "feather_upgraded_netherite_shield",
      () -> new UpgradedNetheriteShield(new Properties().m_41503_(672).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<Item> CORRUPT_UPGRADED_NETHERITE_SHIELD = ITEMS.register(
      "corrupt_upgraded_netherite_shield",
      () -> new UpgradedNetheriteShield(new Properties().m_41503_(672).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<Item> ECHO_UPGRADED_NETHERITE_SHIELD = ITEMS.register(
      "echo_upgraded_netherite_shield",
      () -> new UpgradedNetheriteShield(new Properties().m_41503_(672).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_())
   );
   public static final RegistryObject<ArmorItem> GOLD_UPGRADED_NETHERITE_HELMET = ITEMS.register(
      "gold_upgraded_netherite_helmet",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.GOLD_UPGRADED_NETHERITE,
            EquipmentSlot.HEAD,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> FIRE_UPGRADED_NETHERITE_HELMET = ITEMS.register(
      "fire_upgraded_netherite_helmet",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.FIRE_UPGRADED_NETHERITE,
            EquipmentSlot.HEAD,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> ENDER_UPGRADED_NETHERITE_HELMET = ITEMS.register(
      "ender_upgraded_netherite_helmet",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.ENDER_UPGRADED_NETHERITE,
            EquipmentSlot.HEAD,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> WATER_UPGRADED_NETHERITE_HELMET = ITEMS.register(
      "water_upgraded_netherite_helmet",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.WATER_UPGRADED_NETHERITE,
            EquipmentSlot.HEAD,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> WITHER_UPGRADED_NETHERITE_HELMET = ITEMS.register(
      "wither_upgraded_netherite_helmet",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.WITHER_UPGRADED_NETHERITE,
            EquipmentSlot.HEAD,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> POISON_UPGRADED_NETHERITE_HELMET = ITEMS.register(
      "poison_upgraded_netherite_helmet",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.POISON_UPGRADED_NETHERITE,
            EquipmentSlot.HEAD,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> PHANTOM_UPGRADED_NETHERITE_HELMET = ITEMS.register(
      "phantom_upgraded_netherite_helmet",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.PHANTOM_UPGRADED_NETHERITE,
            EquipmentSlot.HEAD,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> FEATHER_UPGRADED_NETHERITE_HELMET = ITEMS.register(
      "feather_upgraded_netherite_helmet",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.FEATHER_UPGRADED_NETHERITE,
            EquipmentSlot.HEAD,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> CORRUPT_UPGRADED_NETHERITE_HELMET = ITEMS.register(
      "corrupt_upgraded_netherite_helmet",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.CORRUPT_UPGRADED_NETHERITE,
            EquipmentSlot.HEAD,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> ECHO_UPGRADED_NETHERITE_HELMET = ITEMS.register(
      "echo_upgraded_netherite_helmet",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.ECHO_UPGRADED_NETHERITE,
            EquipmentSlot.HEAD,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> GOLD_UPGRADED_NETHERITE_CHESTPLATE = ITEMS.register(
      "gold_upgraded_netherite_chestplate",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.GOLD_UPGRADED_NETHERITE,
            EquipmentSlot.CHEST,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> FIRE_UPGRADED_NETHERITE_CHESTPLATE = ITEMS.register(
      "fire_upgraded_netherite_chestplate",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.FIRE_UPGRADED_NETHERITE,
            EquipmentSlot.CHEST,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> ENDER_UPGRADED_NETHERITE_CHESTPLATE = ITEMS.register(
      "ender_upgraded_netherite_chestplate",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.ENDER_UPGRADED_NETHERITE,
            EquipmentSlot.CHEST,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> WATER_UPGRADED_NETHERITE_CHESTPLATE = ITEMS.register(
      "water_upgraded_netherite_chestplate",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.WATER_UPGRADED_NETHERITE,
            EquipmentSlot.CHEST,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> WITHER_UPGRADED_NETHERITE_CHESTPLATE = ITEMS.register(
      "wither_upgraded_netherite_chestplate",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.WITHER_UPGRADED_NETHERITE,
            EquipmentSlot.CHEST,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> POISON_UPGRADED_NETHERITE_CHESTPLATE = ITEMS.register(
      "poison_upgraded_netherite_chestplate",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.POISON_UPGRADED_NETHERITE,
            EquipmentSlot.CHEST,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> PHANTOM_UPGRADED_NETHERITE_CHESTPLATE = ITEMS.register(
      "phantom_upgraded_netherite_chestplate",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.PHANTOM_UPGRADED_NETHERITE,
            EquipmentSlot.CHEST,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> FEATHER_UPGRADED_NETHERITE_CHESTPLATE = ITEMS.register(
      "feather_upgraded_netherite_chestplate",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.FEATHER_UPGRADED_NETHERITE,
            EquipmentSlot.CHEST,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> CORRUPT_UPGRADED_NETHERITE_CHESTPLATE = ITEMS.register(
      "corrupt_upgraded_netherite_chestplate",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.CORRUPT_UPGRADED_NETHERITE,
            EquipmentSlot.CHEST,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> ECHO_UPGRADED_NETHERITE_CHESTPLATE = ITEMS.register(
      "echo_upgraded_netherite_chestplate",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.ECHO_UPGRADED_NETHERITE,
            EquipmentSlot.CHEST,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> GOLD_UPGRADED_NETHERITE_LEGGINGS = ITEMS.register(
      "gold_upgraded_netherite_leggings",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.GOLD_UPGRADED_NETHERITE,
            EquipmentSlot.LEGS,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> FIRE_UPGRADED_NETHERITE_LEGGINGS = ITEMS.register(
      "fire_upgraded_netherite_leggings",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.FIRE_UPGRADED_NETHERITE,
            EquipmentSlot.LEGS,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> ENDER_UPGRADED_NETHERITE_LEGGINGS = ITEMS.register(
      "ender_upgraded_netherite_leggings",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.ENDER_UPGRADED_NETHERITE,
            EquipmentSlot.LEGS,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> WATER_UPGRADED_NETHERITE_LEGGINGS = ITEMS.register(
      "water_upgraded_netherite_leggings",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.WATER_UPGRADED_NETHERITE,
            EquipmentSlot.LEGS,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> WITHER_UPGRADED_NETHERITE_LEGGINGS = ITEMS.register(
      "wither_upgraded_netherite_leggings",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.WITHER_UPGRADED_NETHERITE,
            EquipmentSlot.LEGS,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> POISON_UPGRADED_NETHERITE_LEGGINGS = ITEMS.register(
      "poison_upgraded_netherite_leggings",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.POISON_UPGRADED_NETHERITE,
            EquipmentSlot.LEGS,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> PHANTOM_UPGRADED_NETHERITE_LEGGINGS = ITEMS.register(
      "phantom_upgraded_netherite_leggings",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.PHANTOM_UPGRADED_NETHERITE,
            EquipmentSlot.LEGS,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> FEATHER_UPGRADED_NETHERITE_LEGGINGS = ITEMS.register(
      "feather_upgraded_netherite_leggings",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.FEATHER_UPGRADED_NETHERITE,
            EquipmentSlot.LEGS,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> CORRUPT_UPGRADED_NETHERITE_LEGGINGS = ITEMS.register(
      "corrupt_upgraded_netherite_leggings",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.CORRUPT_UPGRADED_NETHERITE,
            EquipmentSlot.LEGS,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> ECHO_UPGRADED_NETHERITE_LEGGINGS = ITEMS.register(
      "echo_upgraded_netherite_leggings",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.ECHO_UPGRADED_NETHERITE,
            EquipmentSlot.LEGS,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> GOLD_UPGRADED_NETHERITE_BOOTS = ITEMS.register(
      "gold_upgraded_netherite_boots",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.GOLD_UPGRADED_NETHERITE,
            EquipmentSlot.FEET,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> FIRE_UPGRADED_NETHERITE_BOOTS = ITEMS.register(
      "fire_upgraded_netherite_boots",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.FIRE_UPGRADED_NETHERITE,
            EquipmentSlot.FEET,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> ENDER_UPGRADED_NETHERITE_BOOTS = ITEMS.register(
      "ender_upgraded_netherite_boots",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.ENDER_UPGRADED_NETHERITE,
            EquipmentSlot.FEET,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> WATER_UPGRADED_NETHERITE_BOOTS = ITEMS.register(
      "water_upgraded_netherite_boots",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.WATER_UPGRADED_NETHERITE,
            EquipmentSlot.FEET,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> WITHER_UPGRADED_NETHERITE_BOOTS = ITEMS.register(
      "wither_upgraded_netherite_boots",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.WITHER_UPGRADED_NETHERITE,
            EquipmentSlot.FEET,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> POISON_UPGRADED_NETHERITE_BOOTS = ITEMS.register(
      "poison_upgraded_netherite_boots",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.POISON_UPGRADED_NETHERITE,
            EquipmentSlot.FEET,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> PHANTOM_UPGRADED_NETHERITE_BOOTS = ITEMS.register(
      "phantom_upgraded_netherite_boots",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.PHANTOM_UPGRADED_NETHERITE,
            EquipmentSlot.FEET,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> FEATHER_UPGRADED_NETHERITE_BOOTS = ITEMS.register(
      "feather_upgraded_netherite_boots",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.FEATHER_UPGRADED_NETHERITE,
            EquipmentSlot.FEET,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> CORRUPT_UPGRADED_NETHERITE_BOOTS = ITEMS.register(
      "corrupt_upgraded_netherite_boots",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.CORRUPT_UPGRADED_NETHERITE,
            EquipmentSlot.FEET,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<ArmorItem> ECHO_UPGRADED_NETHERITE_BOOTS = ITEMS.register(
      "echo_upgraded_netherite_boots",
      () -> new UpgradedNetheriteArmor(
            ModArmorMaterial.ECHO_UPGRADED_NETHERITE,
            EquipmentSlot.FEET,
            new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(ModRarity.UPGRADED_SET).m_41486_()
         )
   );
   public static final RegistryObject<HorseArmorItem> NETHERITE_ARMOR_HORSE = ITEMS.register(
      "netherite_horse_armor",
      () -> new UpgradedNetheriteHorseArmor(
            15,
            "upgradednetherite",
            "textures/entity/horse/armor/netherite_horse_armor.png",
            new Properties().m_41487_(1).m_41491_(CreativeModeTab.f_40753_).m_41486_()
         )
   );
   public static final RegistryObject<HorseArmorItem> GOLD_UPGRADED_NETHERITE_ARMOR_HORSE = ITEMS.register(
      "gold_upgraded_netherite_horse_armor",
      () -> new UpgradedNetheriteHorseArmor(
            15,
            "upgradednetherite",
            "textures/entity/horse/armor/gold_upgraded_netherite_horse_armor.png",
            new Properties().m_41487_(1).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<HorseArmorItem> FIRE_UPGRADED_NETHERITE_ARMOR_HORSE = ITEMS.register(
      "fire_upgraded_netherite_horse_armor",
      () -> new UpgradedNetheriteHorseArmor(
            15,
            "upgradednetherite",
            "textures/entity/horse/armor/fire_upgraded_netherite_horse_armor.png",
            new Properties().m_41487_(1).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<HorseArmorItem> ENDER_UPGRADED_NETHERITE_ARMOR_HORSE = ITEMS.register(
      "ender_upgraded_netherite_horse_armor",
      () -> new UpgradedNetheriteHorseArmor(
            15,
            "upgradednetherite",
            "textures/entity/horse/armor/ender_upgraded_netherite_horse_armor.png",
            new Properties().m_41487_(1).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<HorseArmorItem> WATER_UPGRADED_NETHERITE_ARMOR_HORSE = ITEMS.register(
      "water_upgraded_netherite_horse_armor",
      () -> new UpgradedNetheriteHorseArmor(
            15,
            "upgradednetherite",
            "textures/entity/horse/armor/water_upgraded_netherite_horse_armor.png",
            new Properties().m_41487_(1).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<HorseArmorItem> WITHER_UPGRADED_NETHERITE_ARMOR_HORSE = ITEMS.register(
      "wither_upgraded_netherite_horse_armor",
      () -> new UpgradedNetheriteHorseArmor(
            15,
            "upgradednetherite",
            "textures/entity/horse/armor/wither_upgraded_netherite_horse_armor.png",
            new Properties().m_41487_(1).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<HorseArmorItem> POISON_UPGRADED_NETHERITE_ARMOR_HORSE = ITEMS.register(
      "poison_upgraded_netherite_horse_armor",
      () -> new UpgradedNetheriteHorseArmor(
            15,
            "upgradednetherite",
            "textures/entity/horse/armor/poison_upgraded_netherite_horse_armor.png",
            new Properties().m_41487_(1).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<HorseArmorItem> PHANTOM_UPGRADED_NETHERITE_ARMOR_HORSE = ITEMS.register(
      "phantom_upgraded_netherite_horse_armor",
      () -> new UpgradedNetheriteHorseArmor(
            15,
            "upgradednetherite",
            "textures/entity/horse/armor/phantom_upgraded_netherite_horse_armor.png",
            new Properties().m_41487_(1).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<HorseArmorItem> FEATHER_UPGRADED_NETHERITE_ARMOR_HORSE = ITEMS.register(
      "feather_upgraded_netherite_horse_armor",
      () -> new UpgradedNetheriteHorseArmor(
            15,
            "upgradednetherite",
            "textures/entity/horse/armor/feather_upgraded_netherite_horse_armor.png",
            new Properties().m_41487_(1).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<HorseArmorItem> CORRUPT_UPGRADED_NETHERITE_ARMOR_HORSE = ITEMS.register(
      "corrupt_upgraded_netherite_horse_armor",
      () -> new UpgradedNetheriteHorseArmor(
            15,
            "upgradednetherite",
            "textures/entity/horse/armor/corrupt_upgraded_netherite_horse_armor.png",
            new Properties().m_41487_(1).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );
   public static final RegistryObject<HorseArmorItem> ECHO_UPGRADED_NETHERITE_ARMOR_HORSE = ITEMS.register(
      "echo_upgraded_netherite_horse_armor",
      () -> new UpgradedNetheriteHorseArmor(
            15,
            "upgradednetherite",
            "textures/entity/horse/armor/echo_upgraded_netherite_horse_armor.png",
            new Properties().m_41487_(1).m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_()
         )
   );

   public static void init() {
      ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
   }
}
