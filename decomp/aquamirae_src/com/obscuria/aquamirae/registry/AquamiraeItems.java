package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.DeadSeaScrollItem;
import com.obscuria.aquamirae.common.items.LuminescentBubbleItem;
import com.obscuria.aquamirae.common.items.OxygeliumItem;
import com.obscuria.aquamirae.common.items.PiratePouchItem;
import com.obscuria.aquamirae.common.items.RuneOfTheStormItem;
import com.obscuria.aquamirae.common.items.SharpBonesItem;
import com.obscuria.aquamirae.common.items.ShellHornItem;
import com.obscuria.aquamirae.common.items.ShipGraveyardEchoItem;
import com.obscuria.aquamirae.common.items.SpinefishItem;
import com.obscuria.aquamirae.common.items.StewItem;
import com.obscuria.aquamirae.common.items.TreasurePouchItem;
import com.obscuria.aquamirae.common.items.WisteriaNiveisItem;
import com.obscuria.aquamirae.common.items.armor.AbyssalArmorExtraItem;
import com.obscuria.aquamirae.common.items.armor.AbyssalArmorItem;
import com.obscuria.aquamirae.common.items.armor.TerribleArmorItem;
import com.obscuria.aquamirae.common.items.armor.ThreeBoltArmorItem;
import com.obscuria.aquamirae.common.items.weapon.CoralLanceItem;
import com.obscuria.aquamirae.common.items.weapon.DaggerOfGreedItem;
import com.obscuria.aquamirae.common.items.weapon.DividerItem;
import com.obscuria.aquamirae.common.items.weapon.FinCutterItem;
import com.obscuria.aquamirae.common.items.weapon.MazeRoseItem;
import com.obscuria.aquamirae.common.items.weapon.PoisonedBladeItem;
import com.obscuria.aquamirae.common.items.weapon.PoisonedChakraItem;
import com.obscuria.aquamirae.common.items.weapon.RemnantsSaberItem;
import com.obscuria.aquamirae.common.items.weapon.SweetLanceItem;
import com.obscuria.aquamirae.common.items.weapon.TerribleSwordItem;
import com.obscuria.aquamirae.common.items.weapon.WhisperOfTheAbyssItem;
import com.obscuria.obscureapi.common.items.ObscureRarity;
import com.obscuria.obscureapi.registry.ObscureAPIMobEffects;
import java.util.function.Supplier;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class AquamiraeItems {
   public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, "aquamirae");
   public static final RegistryObject<Item> RUNE_OF_THE_STORM = REGISTRY.register("rune_of_the_storm", RuneOfTheStormItem::new);
   public static final RegistryObject<Item> GOLDEN_MOTH_SPAWN_EGG = REGISTRY.register(
      "golden_moth_spawn_egg",
      () -> new ForgeSpawnEggItem(AquamiraeEntities.GOLDEN_MOTH, -3381760, -205, new Properties().m_41497_(Rarity.UNCOMMON).m_41491_(Aquamirae.TAB))
   );
   public static final RegistryObject<Item> TERRIBLE_SWORD = REGISTRY.register("terrible_sword", TerribleSwordItem::new);
   public static final RegistryObject<Item> FIN_CUTTER = REGISTRY.register("fin_cutter", FinCutterItem::new);
   public static final RegistryObject<Item> TERRIBLE_HELMET = REGISTRY.register("terrible_helmet", TerribleArmorItem.Helmet::new);
   public static final RegistryObject<Item> TERRIBLE_CHESTPLATE = REGISTRY.register("terrible_chestplate", TerribleArmorItem.Chestplate::new);
   public static final RegistryObject<Item> TERRIBLE_LEGGINGS = REGISTRY.register("terrible_leggings", TerribleArmorItem.Leggings::new);
   public static final RegistryObject<Item> TERRIBLE_BOOTS = REGISTRY.register("terrible_boots", TerribleArmorItem.Boots::new);
   public static final RegistryObject<Item> SHIP_GRAVEYARD_ECHO = REGISTRY.register("ship_graveyard_echo", ShipGraveyardEchoItem::new);
   public static final RegistryObject<Item> MAW_SPAWN_EGG = REGISTRY.register(
      "maw_spawn_egg", () -> new ForgeSpawnEggItem(AquamiraeEntities.MAW, -16764109, -16737895, new Properties().m_41491_(Aquamirae.TAB))
   );
   public static final RegistryObject<Item> ANGLERFISH_SPAWN_EGG = REGISTRY.register(
      "anglerfish_spawn_egg", () -> new ForgeSpawnEggItem(AquamiraeEntities.ANGLERFISH, -12963033, -4942746, new Properties().m_41491_(Aquamirae.TAB))
   );
   public static final RegistryObject<Item> DIVIDER = REGISTRY.register("divider", DividerItem::new);
   public static final RegistryObject<Item> WHISPER_OF_THE_ABYSS = REGISTRY.register("whisper_of_the_abyss", WhisperOfTheAbyssItem::new);
   public static final RegistryObject<Item> ABYSSAL_HEAUME = REGISTRY.register("abyssal_heaume", AbyssalArmorItem.Heaume::new);
   public static final RegistryObject<Item> ABYSSAL_BRIGANTINE = REGISTRY.register("abyssal_brigantine", AbyssalArmorItem.Brigantine::new);
   public static final RegistryObject<Item> ABYSSAL_LEGGINGS = REGISTRY.register("abyssal_leggings", AbyssalArmorItem.Leggings::new);
   public static final RegistryObject<Item> ABYSSAL_BOOTS = REGISTRY.register("abyssal_boots", AbyssalArmorItem.Boots::new);
   public static final RegistryObject<Item> ABYSSAL_TIARA = REGISTRY.register("abyssal_tiara", AbyssalArmorExtraItem.Tiara::new);
   public static final RegistryObject<Item> MAZE_MOTHER_SPAWN_EGG = REGISTRY.register(
      "maze_mother_spawn_egg", () -> new ForgeSpawnEggItem(AquamiraeEntities.MAZE_MOTHER, -10092442, -39169, new Properties().m_41491_(Aquamirae.TAB))
   );
   public static final RegistryObject<Item> CAPTAIN_CORNELIA_SPAWN_EGG = REGISTRY.register(
      "captain_cornelia_spawn_egg",
      () -> new ForgeSpawnEggItem(AquamiraeEntities.CAPTAIN_CORNELIA, -7760229, -13355980, new Properties().m_41497_(Rarity.EPIC).m_41491_(Aquamirae.TAB))
   );
   public static final RegistryObject<Item> REMNANTS_SABER = REGISTRY.register("remnants_saber", RemnantsSaberItem::new);
   public static final RegistryObject<Item> POISONED_BLADE = REGISTRY.register("poisoned_blade", PoisonedBladeItem::new);
   public static final RegistryObject<Item> THREE_BOLT_HELMET = REGISTRY.register("three_bolt_helmet", ThreeBoltArmorItem.Helmet::new);
   public static final RegistryObject<Item> THREE_BOLT_SUIT = REGISTRY.register("three_bolt_suit", ThreeBoltArmorItem.Chestplate::new);
   public static final RegistryObject<Item> THREE_BOLT_LEGGINGS = REGISTRY.register("three_bolt_leggings", ThreeBoltArmorItem.Leggings::new);
   public static final RegistryObject<Item> THREE_BOLT_BOOTS = REGISTRY.register("three_bolt_boots", ThreeBoltArmorItem.Boots::new);
   public static final RegistryObject<Item> DEAD_SEA_SCROLL = REGISTRY.register("dead_sea_scroll", DeadSeaScrollItem::new);
   public static final RegistryObject<Item> PILLAGERS_PATROL_SPAWN_EGG = REGISTRY.register(
      "pillagers_patrol_spawn_egg",
      () -> new ForgeSpawnEggItem(AquamiraeEntities.PILLAGERS_PATROL, -16751002, -16711681, new Properties().m_41491_(Aquamirae.TAB))
   );
   public static final RegistryObject<Item> TORTURED_SOUL_SPAWN_EGG = REGISTRY.register(
      "tortured_soul_spawn_egg", () -> new ForgeSpawnEggItem(AquamiraeEntities.TORTURED_SOUL, -13421773, -16724737, new Properties().m_41491_(Aquamirae.TAB))
   );
   public static final RegistryObject<Item> CORAL_LANCE = REGISTRY.register("coral_lance", CoralLanceItem::new);
   public static final RegistryObject<Item> DAGGER_OF_GREED = REGISTRY.register("dagger_of_greed", DaggerOfGreedItem::new);
   public static final RegistryObject<Item> SHELL_HORN = REGISTRY.register("shell_horn", ShellHornItem::new);
   public static final RegistryObject<Item> FROZEN_KEY = REGISTRY.register(
      "frozen_key", () -> new Item(new Properties().m_41487_(1).m_41497_(Rarity.UNCOMMON).m_41491_(Aquamirae.TAB)) {
            public void m_6787_(@NotNull CreativeModeTab tab, @NotNull NonNullList<ItemStack> list) {
               super.m_6787_(tab, list);
               if (tab == Aquamirae.TAB) {
                  list.addAll(AquamiraeCreativeTab.logBooks());
               }
            }
         }
   );
   public static final RegistryObject<Item> EEL_SPAWN_EGG = REGISTRY.register(
      "eel_spawn_egg", () -> new ForgeSpawnEggItem(AquamiraeEntities.EEL, -16764109, -16737844, new Properties().m_41491_(Aquamirae.TAB))
   );
   public static final RegistryObject<Item> SWEET_LANCE = REGISTRY.register("sweet_lance", SweetLanceItem::new);
   public static final RegistryObject<Item> SPINEFISH_SPAWN_EGG = REGISTRY.register(
      "spinefish_spawn_egg", () -> new ForgeSpawnEggItem(AquamiraeEntities.SPINEFISH, 1589067, 11451069, new Properties().m_41491_(Aquamirae.TAB)) {
            public void m_6787_(@NotNull CreativeModeTab tab, @NotNull NonNullList<ItemStack> list) {
               super.m_6787_(tab, list);
               if (tab == Aquamirae.TAB && Aquamirae.winterEvent()) {
                  list.add(((Item)AquamiraeItems.SWEET_LANCE.get()).m_7968_());
               }
            }
         }
   );
   public static final RegistryObject<Item> POISONED_CHAKRA = REGISTRY.register("poisoned_chakra", PoisonedChakraItem::new);
   public static final RegistryObject<Item> MAZE_ROSE = REGISTRY.register("maze_rose", MazeRoseItem::new);
   public static final RegistryObject<Item> PIRATE_POUCH = REGISTRY.register("pirate_pouch", PiratePouchItem::new);
   public static final RegistryObject<Item> TREASURE_POUCH = REGISTRY.register("treasure_pouch", TreasurePouchItem::new);
   public static final RegistryObject<Item> MUSIC_DISC_HORIZON = REGISTRY.register(
      "music_disc_horizon",
      () -> new RecordItem(0, AquamiraeSounds.RECORD_HORIZON, new Properties().m_41491_(Aquamirae.TAB).m_41487_(1).m_41497_(Rarity.RARE), 0)
   );
   public static final RegistryObject<Item> MUSIC_DISC_FORSAKEN_DROWNAGE = REGISTRY.register(
      "music_disc_forsaken_drownage",
      () -> new RecordItem(0, AquamiraeSounds.RECORD_FORSAKEN_DROWNAGE, new Properties().m_41491_(Aquamirae.TAB).m_41487_(1).m_41497_(ObscureRarity.MYTHIC), 0)
   );
   public static final RegistryObject<Item> FIN = REGISTRY.register(
      "fin",
      () -> new Item(
            new Properties()
               .m_41491_(Aquamirae.TAB)
               .m_41487_(64)
               .m_41497_(Rarity.COMMON)
               .m_41489_(new Builder().m_38760_(1).m_38758_(0.0F).m_38757_().m_38767_())
         )
   );
   public static final RegistryObject<Item> ESCA = REGISTRY.register(
      "esca",
      () -> new Item(
            new Properties()
               .m_41491_(Aquamirae.TAB)
               .m_41487_(64)
               .m_41497_(Rarity.COMMON)
               .m_41489_(new Builder().m_38760_(6).m_38758_(0.2F).effect(() -> new MobEffectInstance(MobEffects.f_19619_, 200, 0), 1.0F).m_38767_())
         )
   );
   public static final RegistryObject<Item> ANGLERS_FANG = REGISTRY.register(
      "anglers_fang", () -> new Item(new Properties().m_41491_(Aquamirae.TAB).m_41487_(64).m_41497_(Rarity.UNCOMMON))
   );
   public static final RegistryObject<Item> ABYSSAL_AMETHYST = REGISTRY.register(
      "abyssal_amethyst", () -> new Item(new Properties().m_41491_(Aquamirae.TAB).m_41487_(64).m_41497_(Rarity.UNCOMMON))
   );
   public static final RegistryObject<Item> SHARP_BONES = REGISTRY.register("sharp_bones", SharpBonesItem::new);
   public static final RegistryObject<Item> PAINTING_ANGLERFISH = block(
      AquamiraeBlocks.PAINTING_ANGLERFISH, new Properties().m_41491_(Aquamirae.TAB).m_41497_(Rarity.UNCOMMON)
   );
   public static final RegistryObject<Item> PAINTING_OXYGELIUM = block(
      AquamiraeBlocks.PAINTING_OXYGELIUM, new Properties().m_41491_(Aquamirae.TAB).m_41497_(Rarity.UNCOMMON)
   );
   public static final RegistryObject<Item> PAINTING_TORTURED_SOUL = block(
      AquamiraeBlocks.PAINTING_TORTURED_SOUL, new Properties().m_41491_(Aquamirae.TAB).m_41497_(Rarity.UNCOMMON)
   );
   public static final RegistryObject<Item> PAINTING_AURORA = block(
      AquamiraeBlocks.PAINTING_AURORA, new Properties().m_41491_(Aquamirae.TAB).m_41497_(Rarity.UNCOMMON)
   );
   public static final RegistryObject<Item> GOLDEN_MOTH_IN_A_JAR = block(AquamiraeBlocks.GOLDEN_MOTH_IN_A_JAR, new Properties().m_41491_(Aquamirae.TAB));
   public static final RegistryObject<Item> OXYGELIUM = REGISTRY.register("oxygelium", OxygeliumItem::new);
   public static final RegistryObject<Item> WISTERIA_NIVEIS = REGISTRY.register("wisteria_niveis", WisteriaNiveisItem::new);
   public static final RegistryObject<Item> OXYGEN_TANK = REGISTRY.register(
      "oxygen_tank", () -> new Item(new Properties().m_41491_(Aquamirae.TAB).m_41487_(1).m_41497_(Rarity.COMMON))
   );
   public static final RegistryObject<Item> FROZEN_CHEST = block(
      AquamiraeBlocks.FROZEN_CHEST, new Properties().m_41491_(Aquamirae.TAB).m_41497_(Rarity.UNCOMMON)
   );
   public static final RegistryObject<Item> LUMINESCENT_BUBBLE = REGISTRY.register("luminescent_bubble", LuminescentBubbleItem::new);
   public static final RegistryObject<Item> LUMINESCENT_LAMP = block(AquamiraeBlocks.LUMINESCENT_LAMP, new Properties().m_41491_(Aquamirae.TAB));
   public static final RegistryObject<Item> SEA_CASSEROLE = REGISTRY.register(
      "sea_casserole",
      () -> new Item(
            new Properties()
               .m_41491_(Aquamirae.TAB)
               .m_41487_(64)
               .m_41497_(Rarity.COMMON)
               .m_41489_(
                  new Builder()
                     .m_38760_(6)
                     .m_38758_(0.4F)
                     .m_38765_()
                     .effect(() -> new MobEffectInstance((MobEffect)ObscureAPIMobEffects.RUSH.get(), 1800, 1), 1.0F)
                     .effect(() -> new MobEffectInstance(MobEffects.f_19600_, 600, 0), 1.0F)
                     .m_38767_()
               )
         )
   );
   public static final RegistryObject<Item> SPINEFISH_BUCKET = REGISTRY.register(
      "spinefish_bucket",
      () -> new MobBucketItem(
            AquamiraeEntities.SPINEFISH, () -> Fluids.f_76193_, () -> SoundEvents.f_11779_, new Properties().m_41491_(Aquamirae.TAB).m_41487_(1)
         )
   );
   public static final RegistryObject<Item> SPINEFISH = REGISTRY.register(
      "spinefish",
      () -> new SpinefishItem(
            new Properties()
               .m_41491_(Aquamirae.TAB)
               .m_41487_(64)
               .m_41497_(Rarity.COMMON)
               .m_41489_(new Builder().m_38760_(2).m_38758_(0.0F).m_38757_().m_38767_())
         )
   );
   public static final RegistryObject<Item> COOKED_SPINEFISH = REGISTRY.register(
      "cooked_spinefish",
      () -> new SpinefishItem(
            new Properties()
               .m_41491_(Aquamirae.TAB)
               .m_41487_(64)
               .m_41497_(Rarity.COMMON)
               .m_41489_(new Builder().m_38760_(6).m_38758_(0.4F).m_38757_().m_38767_())
         )
   );
   public static final RegistryObject<Item> SEA_STEW = REGISTRY.register(
      "sea_stew",
      () -> new StewItem(
            new Properties()
               .m_41491_(Aquamirae.TAB)
               .m_41487_(1)
               .m_41497_(Rarity.UNCOMMON)
               .m_41489_(
                  new Builder()
                     .m_38760_(12)
                     .m_38758_(0.8F)
                     .m_38765_()
                     .effect(() -> new MobEffectInstance((MobEffect)ObscureAPIMobEffects.FURY.get(), 2400, 2), 1.0F)
                     .effect(() -> new MobEffectInstance(MobEffects.f_19600_, 2400, 0), 1.0F)
                     .m_38767_()
               )
         )
   );
   public static final RegistryObject<Item> POSEIDONS_BREAKFAST = REGISTRY.register(
      "poseidons_breakfast",
      () -> new StewItem(
            new Properties()
               .m_41491_(Aquamirae.TAB)
               .m_41487_(1)
               .m_41497_(Rarity.EPIC)
               .m_41489_(
                  new Builder()
                     .m_38760_(20)
                     .m_38758_(1.0F)
                     .m_38765_()
                     .effect(() -> new MobEffectInstance((MobEffect)ObscureAPIMobEffects.FURY.get(), 3600, 9), 1.0F)
                     .effect(() -> new MobEffectInstance((MobEffect)ObscureAPIMobEffects.RUSH.get(), 3600, 4), 1.0F)
                     .m_38767_()
               )
         ) {
            @OnlyIn(Dist.CLIENT)
            public boolean m_5812_(@NotNull ItemStack itemstack) {
               return true;
            }
         }
   );
   public static final RegistryObject<Item> ELODEA = REGISTRY.register(
      AquamiraeBlocks.ELODEA.getId().m_135815_(), () -> new BlockItem((Block)AquamiraeBlocks.ELODEA.get(), new Properties().m_41491_(Aquamirae.TAB)) {
            public void m_6787_(@NotNull CreativeModeTab tab, @NotNull NonNullList<ItemStack> list) {
               super.m_6787_(tab, list);
               if (tab == Aquamirae.TAB) {
                  list.addAll(Aquamirae.SetBuilder.common());
                  list.addAll(Aquamirae.SetBuilder.rare());
               }
            }
         }
   );

   private static RegistryObject<Item> block(RegistryObject<Block> block, Properties properties) {
      return REGISTRY.register(block.getId().m_135815_(), () -> new BlockItem((Block)block.get(), properties));
   }
}
