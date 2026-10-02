package shadows.apotheosis;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import shadows.apotheosis.adventure.affix.AffixManager;
import shadows.apotheosis.adventure.affix.effect.DurableAffix;
import shadows.apotheosis.adventure.affix.effect.FestiveAffix;
import shadows.apotheosis.adventure.affix.effect.MagicalArrowAffix;
import shadows.apotheosis.adventure.affix.effect.OmneticAffix;
import shadows.apotheosis.adventure.affix.effect.RadialAffix;
import shadows.apotheosis.adventure.affix.effect.TelepathicAffix;
import shadows.apotheosis.adventure.affix.reforging.ReforgingMenu;
import shadows.apotheosis.adventure.affix.reforging.ReforgingRecipe;
import shadows.apotheosis.adventure.affix.reforging.ReforgingTableBlock;
import shadows.apotheosis.adventure.affix.reforging.ReforgingTableTile;
import shadows.apotheosis.adventure.affix.salvaging.SalvagingMenu;
import shadows.apotheosis.adventure.affix.salvaging.SalvagingRecipe;
import shadows.apotheosis.adventure.affix.salvaging.SalvagingTableBlock;
import shadows.apotheosis.adventure.affix.salvaging.SalvagingTableTile;
import shadows.apotheosis.adventure.affix.socket.SocketAffix;
import shadows.apotheosis.adventure.affix.socket.gem.cutting.GemCuttingBlock;
import shadows.apotheosis.adventure.affix.socket.gem.cutting.GemCuttingMenu;
import shadows.apotheosis.adventure.boss.BossSpawnerBlock;
import shadows.apotheosis.ench.anvil.AnvilTile;
import shadows.apotheosis.ench.anvil.ObliterationEnchant;
import shadows.apotheosis.ench.anvil.SplittingEnchant;
import shadows.apotheosis.ench.enchantments.ChromaticEnchant;
import shadows.apotheosis.ench.enchantments.InertEnchantment;
import shadows.apotheosis.ench.enchantments.NaturesBlessingEnchant;
import shadows.apotheosis.ench.enchantments.ReflectiveEnchant;
import shadows.apotheosis.ench.enchantments.SpearfishingEnchant;
import shadows.apotheosis.ench.enchantments.StableFootingEnchant;
import shadows.apotheosis.ench.enchantments.TemptingEnchant;
import shadows.apotheosis.ench.enchantments.corrupted.BerserkersFuryEnchant;
import shadows.apotheosis.ench.enchantments.corrupted.LifeMendingEnchant;
import shadows.apotheosis.ench.enchantments.masterwork.ChainsawEnchant;
import shadows.apotheosis.ench.enchantments.masterwork.CrescendoEnchant;
import shadows.apotheosis.ench.enchantments.masterwork.EarthsBoonEnchant;
import shadows.apotheosis.ench.enchantments.masterwork.EndlessQuiverEnchant;
import shadows.apotheosis.ench.enchantments.masterwork.GrowthSerumEnchant;
import shadows.apotheosis.ench.enchantments.masterwork.KnowledgeEnchant;
import shadows.apotheosis.ench.enchantments.masterwork.ScavengerEnchant;
import shadows.apotheosis.ench.enchantments.twisted.ExploitationEnchant;
import shadows.apotheosis.ench.enchantments.twisted.MinersFervorEnchant;
import shadows.apotheosis.ench.library.EnchLibraryBlock;
import shadows.apotheosis.ench.library.EnchLibraryContainer;
import shadows.apotheosis.ench.library.EnchLibraryTile;
import shadows.apotheosis.ench.table.ApothEnchantmentMenu;
import shadows.apotheosis.ench.table.EnchantingRecipe;
import shadows.apotheosis.garden.EnderLeadItem;
import shadows.apotheosis.potion.LuckyFootItem;
import shadows.apotheosis.potion.PotionCharmItem;
import shadows.apotheosis.spawn.enchantment.CapturingEnchant;
import shadows.apotheosis.spawn.modifiers.SpawnerModifier;
import shadows.apotheosis.village.fletching.FletchingContainer;
import shadows.apotheosis.village.fletching.FletchingRecipe;
import shadows.apotheosis.village.fletching.arrows.BroadheadArrowEntity;
import shadows.apotheosis.village.fletching.arrows.BroadheadArrowItem;
import shadows.apotheosis.village.fletching.arrows.ExplosiveArrowEntity;
import shadows.apotheosis.village.fletching.arrows.ExplosiveArrowItem;
import shadows.apotheosis.village.fletching.arrows.MiningArrowEntity;
import shadows.apotheosis.village.fletching.arrows.MiningArrowItem;
import shadows.apotheosis.village.fletching.arrows.ObsidianArrowEntity;
import shadows.apotheosis.village.fletching.arrows.ObsidianArrowItem;
import shadows.placebo.json.DynamicRegistryObject;
import shadows.placebo.util.PlaceboUtil;
import shadows.placebo.util.RegObjHelper;

public class Apoth {
   public static final RegObjHelper R = new RegObjHelper("apotheosis");

   public static final class Affixes {
      public static final DynamicRegistryObject<SocketAffix> SOCKET = AffixManager.INSTANCE.makeObj(Apotheosis.loc("socket"));
      public static final DynamicRegistryObject<DurableAffix> DURABLE = AffixManager.INSTANCE.makeObj(Apotheosis.loc("durable"));
      public static final DynamicRegistryObject<MagicalArrowAffix> MAGICAL = AffixManager.INSTANCE.makeObj(Apotheosis.loc("ranged/special/magical"));
      public static final DynamicRegistryObject<FestiveAffix> FESTIVE = AffixManager.INSTANCE.makeObj(Apotheosis.loc("sword/special/festive"));
      public static final DynamicRegistryObject<TelepathicAffix> TELEPATHIC = AffixManager.INSTANCE.makeObj(Apotheosis.loc("telepathic"));
      public static final DynamicRegistryObject<OmneticAffix> OMNETIC = AffixManager.INSTANCE.makeObj(Apotheosis.loc("breaker/special/omnetic"));
      public static final DynamicRegistryObject<RadialAffix> RADIAL = AffixManager.INSTANCE.makeObj(Apotheosis.loc("breaker/special/radial"));
   }

   public static final class Blocks {
      public static final RegistryObject<EnchLibraryBlock> LIBRARY = Apoth.R.block("LIBRARY");
      public static final RegistryObject<Block> HELLSHELF = Apoth.R.block("HELLSHELF");
      public static final RegistryObject<Block> INFUSED_HELLSHELF = Apoth.R.block("INFUSED_HELLSHELF");
      public static final RegistryObject<Block> BLAZING_HELLSHELF = Apoth.R.block("BLAZING_HELLSHELF");
      public static final RegistryObject<Block> GLOWING_HELLSHELF = Apoth.R.block("GLOWING_HELLSHELF");
      public static final RegistryObject<Block> SEASHELF = Apoth.R.block("SEASHELF");
      public static final RegistryObject<Block> INFUSED_SEASHELF = Apoth.R.block("INFUSED_SEASHELF");
      public static final RegistryObject<Block> CRYSTAL_SEASHELF = Apoth.R.block("CRYSTAL_SEASHELF");
      public static final RegistryObject<Block> HEART_SEASHELF = Apoth.R.block("HEART_SEASHELF");
      public static final RegistryObject<Block> DORMANT_DEEPSHELF = Apoth.R.block("DORMANT_DEEPSHELF");
      public static final RegistryObject<Block> DEEPSHELF = Apoth.R.block("DEEPSHELF");
      public static final RegistryObject<Block> ECHOING_DEEPSHELF = Apoth.R.block("ECHOING_DEEPSHELF");
      public static final RegistryObject<Block> SOUL_TOUCHED_DEEPSHELF = Apoth.R.block("SOUL_TOUCHED_DEEPSHELF");
      public static final RegistryObject<Block> ECHOING_SCULKSHELF = Apoth.R.block("ECHOING_SCULKSHELF");
      public static final RegistryObject<Block> SOUL_TOUCHED_SCULKSHELF = Apoth.R.block("SOUL_TOUCHED_SCULKSHELF");
      public static final RegistryObject<Block> ENDSHELF = Apoth.R.block("ENDSHELF");
      public static final RegistryObject<Block> PEARL_ENDSHELF = Apoth.R.block("PEARL_ENDSHELF");
      public static final RegistryObject<Block> DRACONIC_ENDSHELF = Apoth.R.block("DRACONIC_ENDSHELF");
      public static final RegistryObject<Block> BEESHELF = Apoth.R.block("BEESHELF");
      public static final RegistryObject<Block> MELONSHELF = Apoth.R.block("MELONSHELF");
      public static final RegistryObject<Block> STONESHELF = Apoth.R.block("STONESHELF");
      public static final RegistryObject<Block> RECTIFIER = Apoth.R.block("RECTIFIER");
      public static final RegistryObject<Block> RECTIFIER_T2 = Apoth.R.block("RECTIFIER_T2");
      public static final RegistryObject<Block> RECTIFIER_T3 = Apoth.R.block("RECTIFIER_T3");
      public static final RegistryObject<Block> SIGHTSHELF = Apoth.R.block("SIGHTSHELF");
      public static final RegistryObject<Block> SIGHTSHELF_T2 = Apoth.R.block("SIGHTSHELF_T2");
      public static final RegistryObject<EnchLibraryBlock> ENDER_LIBRARY = Apoth.R.block("ENDER_LIBRARY");
      public static final RegistryObject<BossSpawnerBlock> BOSS_SPAWNER = Apoth.R.block("BOSS_SPAWNER");
      public static final RegistryObject<ReforgingTableBlock> SIMPLE_REFORGING_TABLE = Apoth.R.block("SIMPLE_REFORGING_TABLE");
      public static final RegistryObject<ReforgingTableBlock> REFORGING_TABLE = Apoth.R.block("REFORGING_TABLE");
      public static final RegistryObject<SalvagingTableBlock> SALVAGING_TABLE = Apoth.R.block("SALVAGING_TABLE");
      public static final RegistryObject<GemCuttingBlock> GEM_CUTTING_TABLE = Apoth.R.block("gem_cutting_table");
   }

   public static final class Enchantments {
      public static final RegistryObject<EndlessQuiverEnchant> ENDLESS_QUIVER = Apoth.R.enchant("ENDLESS_QUIVER");
      public static final RegistryObject<CapturingEnchant> CAPTURING = Apoth.R.enchant("CAPTURING");
      public static final RegistryObject<BerserkersFuryEnchant> BERSERKERS_FURY = Apoth.R.enchant("BERSERKERS_FURY");
      public static final RegistryObject<CrescendoEnchant> CRESCENDO = Apoth.R.enchant("CRESCENDO");
      public static final RegistryObject<KnowledgeEnchant> KNOWLEDGE = Apoth.R.enchant("KNOWLEDGE");
      public static final RegistryObject<LifeMendingEnchant> LIFE_MENDING = Apoth.R.enchant("LIFE_MENDING");
      public static final RegistryObject<MinersFervorEnchant> MINERS_FERVOR = Apoth.R.enchant("MINERS_FERVOR");
      public static final RegistryObject<NaturesBlessingEnchant> NATURES_BLESSING = Apoth.R.enchant("NATURES_BLESSING");
      public static final RegistryObject<ReflectiveEnchant> REFLECTIVE = Apoth.R.enchant("REFLECTIVE");
      public static final RegistryObject<ScavengerEnchant> SCAVENGER = Apoth.R.enchant("SCAVENGER");
      public static final RegistryObject<StableFootingEnchant> STABLE_FOOTING = Apoth.R.enchant("STABLE_FOOTING");
      public static final RegistryObject<TemptingEnchant> TEMPTING = Apoth.R.enchant("TEMPTING");
      public static final RegistryObject<ObliterationEnchant> OBLITERATION = Apoth.R.enchant("OBLITERATION");
      public static final RegistryObject<SplittingEnchant> SPLITTING = Apoth.R.enchant("SPLITTING");
      public static final RegistryObject<InertEnchantment> INFUSION = Apoth.R.enchant("INFUSION");
      public static final RegistryObject<ChromaticEnchant> CHROMATIC = Apoth.R.enchant("CHROMATIC");
      public static final RegistryObject<ExploitationEnchant> EXPLOITATION = Apoth.R.enchant("EXPLOITATION");
      public static final RegistryObject<GrowthSerumEnchant> GROWTH_SERUM = Apoth.R.enchant("GROWTH_SERUM");
      public static final RegistryObject<EarthsBoonEnchant> EARTHS_BOON = Apoth.R.enchant("EARTHS_BOON");
      public static final RegistryObject<ChainsawEnchant> CHAINSAW = Apoth.R.enchant("CHAINSAW");
      public static final RegistryObject<SpearfishingEnchant> SPEARFISHING = Apoth.R.enchant("SPEARFISHING");
   }

   public static final class Entities {
      public static final RegistryObject<EntityType<ObsidianArrowEntity>> OBSIDIAN_ARROW = Apoth.R.entity("OBSIDIAN_ARROW");
      public static final RegistryObject<EntityType<BroadheadArrowEntity>> BROADHEAD_ARROW = Apoth.R.entity("BROADHEAD_ARROW");
      public static final RegistryObject<EntityType<ExplosiveArrowEntity>> EXPLOSIVE_ARROW = Apoth.R.entity("EXPLOSIVE_ARROW");
      public static final RegistryObject<EntityType<MiningArrowEntity>> MINING_ARROW = Apoth.R.entity("MINING_ARROW");
   }

   public static final class Items {
      public static final RegistryObject<PotionCharmItem> POTION_CHARM = Apoth.R.item("POTION_CHARM");
      public static final RegistryObject<LuckyFootItem> LUCKY_FOOT = Apoth.R.item("LUCKY_FOOT");
      public static final RegistryObject<ObsidianArrowItem> OBSIDIAN_ARROW = Apoth.R.item("OBSIDIAN_ARROW");
      public static final RegistryObject<BroadheadArrowItem> BROADHEAD_ARROW = Apoth.R.item("BROADHEAD_ARROW");
      public static final RegistryObject<ExplosiveArrowItem> EXPLOSIVE_ARROW = Apoth.R.item("EXPLOSIVE_ARROW");
      public static final RegistryObject<MiningArrowItem> IRON_MINING_ARROW = Apoth.R.item("IRON_MINING_ARROW");
      public static final RegistryObject<MiningArrowItem> DIAMOND_MINING_ARROW = Apoth.R.item("DIAMOND_MINING_ARROW");
      public static final RegistryObject<EnderLeadItem> ENDER_LEAD = Apoth.R.item("ENDER_LEAD");
      public static final RegistryObject<Item> PRISMATIC_WEB = Apoth.R.item("PRISMATIC_WEB");
      public static final RegistryObject<Item> SCRAP_TOME = Apoth.R.item("SCRAP_TOME");
      public static final RegistryObject<Item> OTHER_TOME = Apoth.R.item("OTHER_TOME");
      public static final RegistryObject<Item> HELMET_TOME = Apoth.R.item("HELMET_TOME");
      public static final RegistryObject<Item> CHESTPLATE_TOME = Apoth.R.item("CHESTPLATE_TOME");
      public static final RegistryObject<Item> LEGGINGS_TOME = Apoth.R.item("LEGGINGS_TOME");
      public static final RegistryObject<Item> BOOTS_TOME = Apoth.R.item("BOOTS_TOME");
      public static final RegistryObject<Item> WEAPON_TOME = Apoth.R.item("WEAPON_TOME");
      public static final RegistryObject<Item> PICKAXE_TOME = Apoth.R.item("PICKAXE_TOME");
      public static final RegistryObject<Item> FISHING_TOME = Apoth.R.item("FISHING_TOME");
      public static final RegistryObject<Item> BOW_TOME = Apoth.R.item("BOW_TOME");
      public static final RegistryObject<Item> GEM = Apoth.R.item("GEM");
      public static final RegistryObject<Item> GEM_DUST = Apoth.R.item("GEM_DUST");
      public static final RegistryObject<Item> VIAL_OF_EXPULSION = Apoth.R.item("VIAL_OF_EXPULSION");
      public static final RegistryObject<Item> VIAL_OF_EXTRACTION = Apoth.R.item("VIAL_OF_EXTRACTION");
      public static final RegistryObject<Item> VIAL_OF_UNNAMING = Apoth.R.item("VIAL_OF_UNNAMING");
      public static final RegistryObject<Item> WARDEN_TENDRIL = Apoth.R.item("WARDEN_TENDRIL");
      public static final RegistryObject<Item> SKULL_FRAGMENT = RegistryObject.create(new ResourceLocation("wstweaks", "fragment"), ForgeRegistries.ITEMS);
   }

   public static final class LootTables {
      public static final ResourceLocation CHEST_VALUABLE = Apotheosis.loc("chests/chest_valuable");
      public static final ResourceLocation SPAWNER_BRUTAL_ROTATE = Apotheosis.loc("chests/spawner_brutal_rotate");
      public static final ResourceLocation SPAWNER_BRUTAL = Apotheosis.loc("chests/spawner_brutal");
      public static final ResourceLocation SPAWNER_SWARM = Apotheosis.loc("chests/spawner_swarm");
      public static final ResourceLocation TOME_TOWER = Apotheosis.loc("chests/tome_tower");
   }

   public static final class Menus {
      public static final RegistryObject<MenuType<FletchingContainer>> FLETCHING = Apoth.R.menu("fletching");
      public static final RegistryObject<MenuType<EnchLibraryContainer>> LIBRARY = Apoth.R.menu("library");
      public static final RegistryObject<MenuType<ApothEnchantmentMenu>> ENCHANTING_TABLE = Apoth.R.menu("enchanting_table");
      public static final RegistryObject<MenuType<ReforgingMenu>> REFORGING = Apoth.R.menu("reforging");
      public static final RegistryObject<MenuType<SalvagingMenu>> SALVAGE = Apoth.R.menu("salvage");
      public static final RegistryObject<MenuType<GemCuttingMenu>> GEM_CUTTING = Apoth.R.menu("gem_cutting");
   }

   public static final class Particles {
      public static final RegistryObject<SimpleParticleType> ENCHANT_FIRE = Apoth.R.particle("enchant_fire");
      public static final RegistryObject<SimpleParticleType> ENCHANT_WATER = Apoth.R.particle("enchant_water");
      public static final RegistryObject<SimpleParticleType> ENCHANT_SCULK = Apoth.R.particle("enchant_sculk");
      public static final RegistryObject<SimpleParticleType> ENCHANT_END = Apoth.R.particle("enchant_end");
      public static final RegistryObject<SimpleParticleType> APOTH_CRIT = Apoth.R.particle("apoth_crit");
   }

   public static final class Potions {
      public static final RegistryObject<Potion> RESISTANCE = Apoth.R.potion("RESISTANCE");
      public static final RegistryObject<Potion> LONG_RESISTANCE = Apoth.R.potion("LONG_RESISTANCE");
      public static final RegistryObject<Potion> STRONG_RESISTANCE = Apoth.R.potion("STRONG_RESISTANCE");
      public static final RegistryObject<Potion> ABSORPTION = Apoth.R.potion("ABSORPTION");
      public static final RegistryObject<Potion> LONG_ABSORPTION = Apoth.R.potion("LONG_ABSORPTION");
      public static final RegistryObject<Potion> STRONG_ABSORPTION = Apoth.R.potion("STRONG_ABSORPTION");
      public static final RegistryObject<Potion> HASTE = Apoth.R.potion("HASTE");
      public static final RegistryObject<Potion> LONG_HASTE = Apoth.R.potion("LONG_HASTE");
      public static final RegistryObject<Potion> STRONG_HASTE = Apoth.R.potion("STRONG_HASTE");
      public static final RegistryObject<Potion> FATIGUE = Apoth.R.potion("FATIGUE");
      public static final RegistryObject<Potion> LONG_FATIGUE = Apoth.R.potion("LONG_FATIGUE");
      public static final RegistryObject<Potion> STRONG_FATIGUE = Apoth.R.potion("STRONG_FATIGUE");
      public static final RegistryObject<Potion> SUNDERING = Apoth.R.potion("SUNDERING");
      public static final RegistryObject<Potion> LONG_SUNDERING = Apoth.R.potion("LONG_SUNDERING");
      public static final RegistryObject<Potion> STRONG_SUNDERING = Apoth.R.potion("STRONG_SUNDERING");
      public static final RegistryObject<Potion> KNOWLEDGE = Apoth.R.potion("KNOWLEDGE");
      public static final RegistryObject<Potion> LONG_KNOWLEDGE = Apoth.R.potion("LONG_KNOWLEDGE");
      public static final RegistryObject<Potion> STRONG_KNOWLEDGE = Apoth.R.potion("STRONG_KNOWLEDGE");
      public static final RegistryObject<Potion> WITHER = Apoth.R.potion("WITHER");
      public static final RegistryObject<Potion> LONG_WITHER = Apoth.R.potion("LONG_WITHER");
      public static final RegistryObject<Potion> STRONG_WITHER = Apoth.R.potion("STRONG_WITHER");
      public static final RegistryObject<Potion> VITALITY = Apoth.R.potion("VITALITY");
      public static final RegistryObject<Potion> LONG_VITALITY = Apoth.R.potion("LONG_VITALITY");
      public static final RegistryObject<Potion> STRONG_VITALITY = Apoth.R.potion("STRONG_VITALITY");
      public static final RegistryObject<Potion> GRIEVOUS = Apoth.R.potion("GRIEVOUS");
      public static final RegistryObject<Potion> LONG_GRIEVOUS = Apoth.R.potion("LONG_GRIEVOUS");
      public static final RegistryObject<Potion> STRONG_GRIEVOUS = Apoth.R.potion("STRONG_GRIEVOUS");
   }

   public static final class RecipeTypes {
      public static final RecipeType<FletchingRecipe> FLETCHING = PlaceboUtil.makeRecipeType("apotheosis:fletching");
      public static final RecipeType<EnchantingRecipe> INFUSION = PlaceboUtil.makeRecipeType("apotheosis:enchanting");
      public static final RecipeType<SpawnerModifier> MODIFIER = PlaceboUtil.makeRecipeType("apotheosis:spawner_modifier");
      public static final RecipeType<SalvagingRecipe> SALVAGING = PlaceboUtil.makeRecipeType("apotheosis:salvaging");
      public static final RecipeType<ReforgingRecipe> REFORGING = PlaceboUtil.makeRecipeType("apotheosis:reforging");
   }

   public static final class Tags {
      public static final TagKey<Item> BOON_DROPS = ItemTags.create(new ResourceLocation("apotheosis", "boon_drops"));
      public static final TagKey<Item> SPEARFISHING_DROPS = ItemTags.create(new ResourceLocation("apotheosis", "spearfishing_drops"));
   }

   public static final class Tiles {
      public static final RegistryObject<BlockEntityType<EnchLibraryTile>> LIBRARY = Apoth.R.blockEntity("LIBRARY");
      public static final RegistryObject<BlockEntityType<AnvilTile>> ANVIL = Apoth.R.blockEntity("ANVIL");
      public static final RegistryObject<BlockEntityType<EnchLibraryTile>> ENDER_LIBRARY = Apoth.R.blockEntity("ENDER_LIBRARY");
      public static final RegistryObject<BlockEntityType<BossSpawnerBlock.BossSpawnerTile>> BOSS_SPAWNER = Apoth.R.blockEntity("BOSS_SPAWNER");
      public static final RegistryObject<BlockEntityType<ReforgingTableTile>> REFORGING_TABLE = Apoth.R.blockEntity("REFORGING_TABLE");
      public static final RegistryObject<BlockEntityType<SalvagingTableTile>> SALVAGING_TABLE = Apoth.R.blockEntity("SALVAGING_TABLE");
   }
}
