package com.hollingsworth.arsnouveau.setup;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder;
import com.hollingsworth.arsnouveau.api.perk.IPerk;
import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.armor.HeavyArmor;
import com.hollingsworth.arsnouveau.common.armor.LightArmor;
import com.hollingsworth.arsnouveau.common.armor.MediumArmor;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import com.hollingsworth.arsnouveau.common.items.AlchemistsCrown;
import com.hollingsworth.arsnouveau.common.items.AnnotatedCodex;
import com.hollingsworth.arsnouveau.common.items.BlankParchmentItem;
import com.hollingsworth.arsnouveau.common.items.CasterTome;
import com.hollingsworth.arsnouveau.common.items.Debug;
import com.hollingsworth.arsnouveau.common.items.DominionWand;
import com.hollingsworth.arsnouveau.common.items.DowsingRod;
import com.hollingsworth.arsnouveau.common.items.EarthEssence;
import com.hollingsworth.arsnouveau.common.items.EnchantersMirror;
import com.hollingsworth.arsnouveau.common.items.EnchantersShield;
import com.hollingsworth.arsnouveau.common.items.EnchantersSword;
import com.hollingsworth.arsnouveau.common.items.ExperienceGem;
import com.hollingsworth.arsnouveau.common.items.FamiliarScript;
import com.hollingsworth.arsnouveau.common.items.FireEssence;
import com.hollingsworth.arsnouveau.common.items.FlaskCannon;
import com.hollingsworth.arsnouveau.common.items.FormSpellArrow;
import com.hollingsworth.arsnouveau.common.items.Glyph;
import com.hollingsworth.arsnouveau.common.items.JarOfLight;
import com.hollingsworth.arsnouveau.common.items.ManipulationEssence;
import com.hollingsworth.arsnouveau.common.items.ModItem;
import com.hollingsworth.arsnouveau.common.items.PerkItem;
import com.hollingsworth.arsnouveau.common.items.PotionFlask;
import com.hollingsworth.arsnouveau.common.items.Present;
import com.hollingsworth.arsnouveau.common.items.RitualTablet;
import com.hollingsworth.arsnouveau.common.items.RunicChalk;
import com.hollingsworth.arsnouveau.common.items.ScryCaster;
import com.hollingsworth.arsnouveau.common.items.ScryerScroll;
import com.hollingsworth.arsnouveau.common.items.SpellArrow;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.hollingsworth.arsnouveau.common.items.SpellBow;
import com.hollingsworth.arsnouveau.common.items.SpellCrossbow;
import com.hollingsworth.arsnouveau.common.items.SpellParchment;
import com.hollingsworth.arsnouveau.common.items.StableWarpScroll;
import com.hollingsworth.arsnouveau.common.items.StarbuncleShades;
import com.hollingsworth.arsnouveau.common.items.StarbuncleShard;
import com.hollingsworth.arsnouveau.common.items.VoidJar;
import com.hollingsworth.arsnouveau.common.items.Wand;
import com.hollingsworth.arsnouveau.common.items.WarpScroll;
import com.hollingsworth.arsnouveau.common.items.WixieHat;
import com.hollingsworth.arsnouveau.common.items.WornNotebook;
import com.hollingsworth.arsnouveau.common.items.curios.AbstractManaCurio;
import com.hollingsworth.arsnouveau.common.items.curios.BeltOfLevitation;
import com.hollingsworth.arsnouveau.common.items.curios.BeltOfUnstableGifts;
import com.hollingsworth.arsnouveau.common.items.curios.DiscountRing;
import com.hollingsworth.arsnouveau.common.items.curios.JumpingRing;
import com.hollingsworth.arsnouveau.common.items.curios.ShapersFocus;
import com.hollingsworth.arsnouveau.common.items.curios.SummoningFocus;
import com.hollingsworth.arsnouveau.common.items.itemscrolls.AllowItemScroll;
import com.hollingsworth.arsnouveau.common.items.itemscrolls.DenyItemScroll;
import com.hollingsworth.arsnouveau.common.items.itemscrolls.MimicItemScroll;
import com.hollingsworth.arsnouveau.common.items.summon_charms.AmethystGolemCharm;
import com.hollingsworth.arsnouveau.common.items.summon_charms.BookwyrmCharm;
import com.hollingsworth.arsnouveau.common.items.summon_charms.DrygmyCharm;
import com.hollingsworth.arsnouveau.common.items.summon_charms.StarbuncleCharm;
import com.hollingsworth.arsnouveau.common.items.summon_charms.WhirlisprigCharm;
import com.hollingsworth.arsnouveau.common.items.summon_charms.WixieCharm;
import com.hollingsworth.arsnouveau.common.perk.EmptyPerk;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSplit;
import com.hollingsworth.arsnouveau.common.util.RegistryWrapper;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;

public class ItemsRegistry {
   public static FoodProperties SOURCE_BERRY_FOOD = new Builder()
      .m_38760_(2)
      .m_38758_(0.1F)
      .effect(() -> new MobEffectInstance((MobEffect)ModPotions.MANA_REGEN_EFFECT.get(), 100), 1.0F)
      .m_38765_()
      .m_38767_();
   public static FoodProperties SOURCE_PIE_FOOD = new Builder()
      .m_38760_(9)
      .m_38758_(0.9F)
      .effect(() -> new MobEffectInstance((MobEffect)ModPotions.MANA_REGEN_EFFECT.get(), 1200, 1), 1.0F)
      .m_38765_()
      .m_38767_();
   public static FoodProperties SOURCE_ROLL_FOOD = new Builder()
      .m_38760_(8)
      .m_38758_(0.6F)
      .effect(() -> new MobEffectInstance((MobEffect)ModPotions.MANA_REGEN_EFFECT.get(), 1200), 1.0F)
      .m_38765_()
      .m_38767_();
   public static FoodProperties MENDOSTEEN_FOOD = new Builder()
      .m_38760_(4)
      .m_38758_(0.6F)
      .effect(() -> new MobEffectInstance((MobEffect)ModPotions.RECOVERY_EFFECT.get(), 1200), 1.0F)
      .m_38765_()
      .m_38767_();
   public static FoodProperties BLASTING_FOOD = new Builder()
      .m_38760_(4)
      .m_38758_(0.6F)
      .effect(() -> new MobEffectInstance((MobEffect)ModPotions.BLAST_EFFECT.get(), 200), 1.0F)
      .m_38765_()
      .m_38767_();
   public static FoodProperties BASTION_FOOD = new Builder()
      .m_38760_(4)
      .m_38758_(0.6F)
      .effect(() -> new MobEffectInstance((MobEffect)ModPotions.DEFENCE_EFFECT.get(), 1200), 1.0F)
      .m_38765_()
      .m_38767_();
   public static FoodProperties FROSTAYA_FOOD = new Builder()
      .m_38760_(4)
      .m_38758_(0.6F)
      .effect(() -> new MobEffectInstance((MobEffect)ModPotions.FREEZING_EFFECT.get(), 600), 1.0F)
      .m_38765_()
      .m_38767_();
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "ars_nouveau");
   public static final RegistryWrapper<RunicChalk> RUNIC_CHALK = register("runic_chalk", () -> new RunicChalk());
   public static RegistryWrapper<SpellBook> NOVICE_SPELLBOOK = register("novice_spell_book", () -> new SpellBook(SpellTier.ONE));
   public static RegistryWrapper<SpellBook> APPRENTICE_SPELLBOOK = register("apprentice_spell_book", () -> new SpellBook(SpellTier.TWO));
   public static RegistryWrapper<SpellBook> ARCHMAGE_SPELLBOOK = register("archmage_spell_book", () -> new SpellBook(SpellTier.THREE));
   public static RegistryWrapper<SpellBook> CREATIVE_SPELLBOOK = register("creative_spell_book", () -> new SpellBook(SpellTier.CREATIVE));
   public static RegistryWrapper<Item> BLANK_GLYPH = register("blank_glyph");
   public static RegistryWrapper<ModItem> BUCKET_OF_SOURCE = register("bucket_of_source");
   public static RegistryWrapper<ModItem> MAGE_BLOOM = register(
      "magebloom", () -> new ModItem().withTooltip(Component.m_237115_("ars_nouveau.tooltip.magebloom"))
   );
   public static RegistryWrapper<ModItem> MAGE_FIBER = register("magebloom_fiber");
   public static RegistryWrapper<ModItem> BLAZE_FIBER = register("blaze_fiber");
   public static RegistryWrapper<ModItem> END_FIBER = register("end_fiber");
   public static RegistryWrapper<ModItem> MUNDANE_BELT = register(
      "mundane_belt", () -> new ModItem().withTooltip(Component.m_237115_("ars_nouveau.tooltip.dull"))
   );
   public static RegistryWrapper<JarOfLight> JAR_OF_LIGHT = register("jar_of_light", () -> new JarOfLight());
   public static RegistryWrapper<BeltOfLevitation> BELT_OF_LEVITATION = register("belt_of_levitation", () -> new BeltOfLevitation());
   public static RegistryWrapper<WornNotebook> WORN_NOTEBOOK = register(
      "worn_notebook", () -> new WornNotebook().withTooltip(Component.m_237115_("tooltip.worn_notebook"))
   );
   public static RegistryWrapper<ModItem> RING_OF_POTENTIAL = register(
      "ring_of_potential", () -> new ModItem().withTooltip(Component.m_237115_("ars_nouveau.tooltip.dull"))
   );
   public static RegistryWrapper<DiscountRing> RING_OF_LESSER_DISCOUNT = register("ring_of_lesser_discount", () -> new DiscountRing() {
         @Override
         public int getManaDiscount() {
            return 10;
         }
      });
   public static RegistryWrapper<DiscountRing> RING_OF_GREATER_DISCOUNT = register("ring_of_greater_discount", () -> new DiscountRing() {
         @Override
         public int getManaDiscount() {
            return 20;
         }
      });
   public static RegistryWrapper<BeltOfUnstableGifts> BELT_OF_UNSTABLE_GIFTS = register("belt_of_unstable_gifts", () -> new BeltOfUnstableGifts());
   public static RegistryWrapper<WarpScroll> WARP_SCROLL = register("warp_scroll", () -> new WarpScroll());
   public static RegistryWrapper<SpellParchment> SPELL_PARCHMENT = register("spell_parchment", () -> new SpellParchment());
   public static RegistryWrapper<BookwyrmCharm> BOOKWYRM_CHARM = register(
      "bookwyrm_charm", () -> new BookwyrmCharm().withTooltip("ars_nouveau.tooltip.bookwyrm")
   );
   public static RegistryWrapper<DominionWand> DOMINION_ROD = register("dominion_wand", () -> new DominionWand());
   public static RegistryWrapper<AbstractManaCurio> AMULET_OF_MANA_BOOST = register("amulet_of_mana_boost", () -> new AbstractManaCurio() {
         @Override
         public int getMaxManaBoost(ItemStack i) {
            return 50;
         }
      });
   public static RegistryWrapper<AbstractManaCurio> AMULET_OF_MANA_REGEN = register("amulet_of_mana_regen", () -> new AbstractManaCurio() {
         @Override
         public int getManaRegenBonus(ItemStack i) {
            return 3;
         }
      });
   public static RegistryWrapper<ModItem> DULL_TRINKET = register(
      "dull_trinket", () -> new ModItem().withTooltip(Component.m_237115_("ars_nouveau.tooltip.dull"))
   );
   public static RegistryWrapper<StarbuncleCharm> STARBUNCLE_CHARM = register("starbuncle_charm", () -> new StarbuncleCharm());
   public static RegistryWrapper<Debug> debug = register("debug", () -> new Debug());
   public static RegistryWrapper<StarbuncleShard> STARBUNCLE_SHARD = register(
      "starbuncle_shards", () -> new StarbuncleShard().withTooltip(Component.m_237115_("tooltip.starbuncle_shard"))
   );
   public static RegistryWrapper<StarbuncleShades> STARBUNCLE_SHADES = register(
      "starbuncle_shades", () -> new StarbuncleShades().withTooltip(Component.m_237115_("tooltip.starbuncle_shades"))
   );
   public static RegistryWrapper<WhirlisprigCharm> WHIRLISPRIG_CHARM = register("whirlisprig_charm", () -> new WhirlisprigCharm());
   public static RegistryWrapper<ModItem> WHIRLISPRIG_SHARDS = register(
      "whirlisprig_shards", () -> new ModItem().withTooltip(Component.m_237115_("tooltip.whirlisprig_shard"))
   );
   public static RegistryWrapper<ModItem> SOURCE_GEM = register("source_gem", () -> new ModItem().withTooltip(Component.m_237115_("tooltip.source_gem")));
   public static RegistryWrapper<AllowItemScroll> ALLOW_ITEM_SCROLL = register("allow_scroll", () -> new AllowItemScroll());
   public static RegistryWrapper<DenyItemScroll> DENY_ITEM_SCROLL = register("deny_scroll", () -> new DenyItemScroll());
   public static RegistryWrapper<MimicItemScroll> MIMIC_ITEM_SCROLL = register("mimic_scroll", () -> new MimicItemScroll());
   public static RegistryWrapper<BlankParchmentItem> BLANK_PARCHMENT = register("blank_parchment", () -> new BlankParchmentItem());
   public static RegistryWrapper<Wand> WAND = register("wand", () -> new Wand());
   public static RegistryWrapper<VoidJar> VOID_JAR = register("void_jar", () -> new VoidJar());
   public static RegistryWrapper<WixieCharm> WIXIE_CHARM = register("wixie_charm", () -> new WixieCharm());
   public static RegistryWrapper<ModItem> WIXIE_SHARD = register("wixie_shards", () -> new ModItem().withTooltip(Component.m_237115_("tooltip.wixie_shard")));
   public static RegistryWrapper<SpellBow> SPELL_BOW = register("spell_bow", () -> new SpellBow());
   public static RegistryWrapper<SpellArrow> AMPLIFY_ARROW = register("amplify_arrow", () -> new SpellArrow(AugmentAmplify.INSTANCE, 2));
   public static RegistryWrapper<FormSpellArrow> SPLIT_ARROW = register("split_arrow", () -> new FormSpellArrow(AugmentSplit.INSTANCE, 2));
   public static RegistryWrapper<FormSpellArrow> PIERCE_ARROW = register("pierce_arrow", () -> new FormSpellArrow(AugmentPierce.INSTANCE, 2));
   public static RegistryWrapper<ModItem> WILDEN_HORN = register("wilden_horn", () -> new ModItem().withTooltip(Component.m_237115_("tooltip.wilden_horn")));
   public static RegistryWrapper<ModItem> WILDEN_SPIKE = register("wilden_spike", () -> new ModItem().withTooltip(Component.m_237115_("tooltip.wilden_spike")));
   public static RegistryWrapper<ModItem> WILDEN_WING = register("wilden_wing", () -> new ModItem().withTooltip(Component.m_237115_("tooltip.wilden_wing")));
   public static RegistryWrapper<PotionFlask> POTION_FLASK = register("potion_flask", () -> (new PotionFlask() {
         @NotNull
         @Override
         public MobEffectInstance getEffectInstance(MobEffectInstance effectInstance) {
            return effectInstance;
         }
      }).withTooltip(Component.m_237115_("tooltip.potion_flask")));
   public static RegistryWrapper<PotionFlask> POTION_FLASK_AMPLIFY = register(
      "potion_flask_amplify",
      () -> (new PotionFlask() {
               @Override
               public MobEffectInstance getEffectInstance(MobEffectInstance effectInstance) {
                  return new MobEffectInstance(
                     effectInstance.m_19544_(),
                     effectInstance.m_19557_() / 2,
                     Math.min((Integer)Config.ENCHANTED_FLASK_CAP.get(), effectInstance.m_19564_() + 1)
                  );
               }
            })
            .withTooltip(Component.m_237115_("tooltip.potion_flask_amplify"))
   );
   public static RegistryWrapper<PotionFlask> POTION_FLASK_EXTEND_TIME = register("potion_flask_extend_time", () -> (new PotionFlask() {
         @Override
         public MobEffectInstance getEffectInstance(MobEffectInstance effectInstance) {
            return new MobEffectInstance(effectInstance.m_19544_(), effectInstance.m_19557_() + effectInstance.m_19557_() / 2, effectInstance.m_19564_());
         }
      }).withTooltip(Component.m_237115_("tooltip.potion_flask_extend_time")));
   public static RegistryWrapper<ExperienceGem> EXPERIENCE_GEM = register("experience_gem", () -> (new ExperienceGem() {
         @Override
         public int getValue() {
            return 3;
         }
      }).withTooltip(Component.m_237115_("ars_nouveau.tooltip.exp_gem")));
   public static RegistryWrapper<ExperienceGem> GREATER_EXPERIENCE_GEM = register("greater_experience_gem", () -> (new ExperienceGem() {
         @Override
         public int getValue() {
            return 12;
         }
      }).withTooltip(Component.m_237115_("ars_nouveau.tooltip.exp_gem")));
   public static RegistryWrapper<EnchantersSword> ENCHANTERS_SWORD = register("enchanters_sword", () -> new EnchantersSword(Tiers.NETHERITE, 3, -2.4F));
   public static RegistryWrapper<EnchantersShield> ENCHANTERS_SHIELD = register("enchanters_shield", () -> new EnchantersShield());
   public static RegistryWrapper<CasterTome> CASTER_TOME = register("caster_tome", () -> new CasterTome());
   public static RegistryWrapper<DrygmyCharm> DRYGMY_CHARM = register("drygmy_charm", () -> new DrygmyCharm());
   public static RegistryWrapper<ModItem> DRYGMY_SHARD = register(
      "drygmy_shard", () -> new ModItem().withTooltip(Component.m_237115_("tooltip.ars_nouveau.drygmy_shard"))
   );
   public static RegistryWrapper<ModItem> WILDEN_TRIBUTE = register(
      "wilden_tribute",
      () -> new ModItem()
            .withTooltip(Component.m_237115_("tooltip.ars_nouveau.wilden_tribute").m_130948_(Style.f_131099_.m_131155_(true).m_131140_(ChatFormatting.BLUE)))
            .withRarity(Rarity.EPIC)
   );
   public static RegistryWrapper<SummoningFocus> SUMMONING_FOCUS = register("summon_focus", () -> new SummoningFocus());
   public static RegistryWrapper<ShapersFocus> SHAPERS_FOCUS = register(
      "shapers_focus", () -> new ShapersFocus(defaultItemProperties().m_41487_(1)).withTooltip(Component.m_237115_("tooltip.ars_nouveau.shapers_focus"))
   );
   public static RegistryWrapper<ModItem> SOURCE_BERRY_PIE = register(
      "source_berry_pie",
      () -> new ModItem(defaultItemProperties().m_41489_(SOURCE_PIE_FOOD)).withTooltip(Component.m_237115_("tooltip.ars_nouveau.source_food"))
   );
   public static RegistryWrapper<ModItem> SOURCE_BERRY_ROLL = register(
      "source_berry_roll",
      () -> new ModItem(defaultItemProperties().m_41489_(SOURCE_ROLL_FOOD)).withTooltip(Component.m_237115_("tooltip.ars_nouveau.source_food"))
   );
   public static RegistryWrapper<EnchantersMirror> ENCHANTERS_MIRROR = register(
      "enchanters_mirror", () -> new EnchantersMirror(defaultItemProperties().m_41487_(1))
   );
   public static RegistryWrapper<LightArmor> NOVICE_BOOTS = register("novice_boots", () -> new LightArmor(EquipmentSlot.FEET));
   public static RegistryWrapper<LightArmor> NOVICE_LEGGINGS = register("novice_leggings", () -> new LightArmor(EquipmentSlot.LEGS));
   public static RegistryWrapper<LightArmor> NOVICE_ROBES = register("novice_robes", () -> new LightArmor(EquipmentSlot.CHEST));
   public static RegistryWrapper<LightArmor> NOVICE_HOOD = register("novice_hood", () -> new LightArmor(EquipmentSlot.HEAD));
   public static RegistryWrapper<MediumArmor> APPRENTICE_BOOTS = register("apprentice_boots", () -> new MediumArmor(EquipmentSlot.FEET));
   public static RegistryWrapper<MediumArmor> APPRENTICE_LEGGINGS = register("apprentice_leggings", () -> new MediumArmor(EquipmentSlot.LEGS));
   public static RegistryWrapper<MediumArmor> APPRENTICE_ROBES = register("apprentice_robes", () -> new MediumArmor(EquipmentSlot.CHEST));
   public static RegistryWrapper<MediumArmor> APPRENTICE_HOOD = register("apprentice_hood", () -> new MediumArmor(EquipmentSlot.HEAD));
   public static RegistryWrapper<HeavyArmor> ARCHMAGE_BOOTS = register("archmage_boots", () -> new HeavyArmor(EquipmentSlot.FEET));
   public static RegistryWrapper<HeavyArmor> ARCHMAGE_LEGGINGS = register("archmage_leggings", () -> new HeavyArmor(EquipmentSlot.LEGS));
   public static RegistryWrapper<HeavyArmor> ARCHMAGE_ROBES = register("archmage_robes", () -> new HeavyArmor(EquipmentSlot.CHEST));
   public static RegistryWrapper<HeavyArmor> ARCHMAGE_HOOD = register("archmage_hood", () -> new HeavyArmor(EquipmentSlot.HEAD));
   public static RegistryWrapper<DowsingRod> DOWSING_ROD = register(
      "dowsing_rod", () -> new DowsingRod().withTooltip(Component.m_237115_("tooltip.ars_nouveau.dowsing_rod"))
   );
   public static RegistryWrapper<ModItem> ABJURATION_ESSENCE = register(
      "abjuration_essence", () -> new ModItem().withTooltip(Component.m_237115_("tooltip.ars_nouveau.essences"))
   );
   public static RegistryWrapper<ModItem> CONJURATION_ESSENCE = register(
      "conjuration_essence", () -> new ModItem().withTooltip(Component.m_237115_("tooltip.ars_nouveau.essences"))
   );
   public static RegistryWrapper<ModItem> AIR_ESSENCE = register(
      "air_essence", () -> new ModItem().withTooltip(Component.m_237115_("tooltip.ars_nouveau.essences"))
   );
   public static RegistryWrapper<EarthEssence> EARTH_ESSENCE = register(
      "earth_essence", () -> new EarthEssence().withTooltip(Component.m_237115_("tooltip.ars_nouveau.essences"))
   );
   public static RegistryWrapper<FireEssence> FIRE_ESSENCE = register(
      "fire_essence", () -> new FireEssence().withTooltip(Component.m_237115_("tooltip.ars_nouveau.essences"))
   );
   public static RegistryWrapper<ModItem> MANIPULATION_ESSENCE = register(
      "manipulation_essence", () -> new ManipulationEssence().withTooltip(Component.m_237115_("tooltip.ars_nouveau.essences"))
   );
   public static RegistryWrapper<ModItem> WATER_ESSENCE = register(
      "water_essence", () -> new ModItem().withTooltip(Component.m_237115_("tooltip.ars_nouveau.essences"))
   );
   public static RegistryWrapper<AmethystGolemCharm> AMETHYST_GOLEM_CHARM = register(
      "amethyst_golem_charm", () -> new AmethystGolemCharm().withTooltip(Component.m_237115_("tooltip.ars_nouveau.amethyst_charm"))
   );
   public static RegistryWrapper<AnnotatedCodex> ANNOTATED_CODEX = register("annotated_codex", () -> new AnnotatedCodex());
   public static RegistryWrapper<ScryerScroll> SCRYER_SCROLL = register(
      "scryer_scroll", () -> new ScryerScroll().withTooltip(Component.m_237115_("tooltip.ars_nouveau.scryer_scroll"))
   );
   public static RegistryWrapper<ModItem> WIXIE_HAT = register("wixie_hat", () -> new WixieHat().withTooltip("tooltip.ars_nouveau.wixie_hat"));
   public static RegistryWrapper<ModItem> ALCHEMISTS_CROWN = register("alchemists_crown", () -> new AlchemistsCrown(defaultItemProperties().m_41487_(1)));
   public static RegistryWrapper<ModItem> SPLASH_LAUNCHER = register(
      "splash_flask_cannon", () -> new FlaskCannon.SplashLauncher(defaultItemProperties().m_41487_(1))
   );
   public static RegistryWrapper<ModItem> LINGERING_LAUNCHER = register(
      "lingering_flask_cannon", () -> new FlaskCannon.LingeringLauncher(defaultItemProperties().m_41487_(1))
   );
   public static PerkItem BLANK_THREAD;
   public static RegistryWrapper<Item> FIREL_DISC = register(
      "music_disc_aria_biblio",
      () -> new RecordItem(9, () -> (SoundEvent)SoundRegistry.ARIA_BIBLIO.get(), defaultItemProperties().m_41487_(1).m_41497_(Rarity.RARE), 4800)
   );
   public static RegistryWrapper<Item> SOUND_OF_GLASS = register(
      "music_disc_thistle_the_sound_of_glass",
      () -> new RecordItem(9, () -> (SoundEvent)SoundRegistry.SOUND_OF_GLASS.get(), defaultItemProperties().m_41487_(1).m_41497_(Rarity.RARE), 3640)
   );
   public static RegistryWrapper<Item> WILD_HUNT = register(
      "music_disc_firel_the_wild_hunt",
      () -> new RecordItem(9, () -> (SoundEvent)SoundRegistry.WILD_HUNT.get(), defaultItemProperties().m_41487_(1).m_41497_(Rarity.RARE), 2420)
   );
   public static RegistryWrapper<Item> STARBY_GIFY = register("starby_gift", () -> new Present(defaultItemProperties().m_41497_(Rarity.EPIC)));
   public static RegistryWrapper<Item> SPELL_CROSSBOW = register("spell_crossbow", () -> new SpellCrossbow(defaultItemProperties().m_41487_(1)));
   public static RegistryWrapper<Item> STABLE_WARP_SCROLL = register("stable_warp_scroll", () -> new StableWarpScroll(defaultItemProperties().m_41487_(1)));
   public static RegistryWrapper<ScryCaster> SCRY_CASTER = register("enchanters_eye", () -> new ScryCaster(defaultItemProperties().m_41487_(1)));
   public static RegistryWrapper<JumpingRing> JUMP_RING = register("jump_ring", () -> new JumpingRing());

   public static RegistryWrapper register(String name, Supplier<? extends Item> item) {
      return new RegistryWrapper(ITEMS.register(name, item));
   }

   public static RegistryWrapper register(String name) {
      return register(name, () -> new ModItem());
   }

   public static void onItemRegistry(IForgeRegistry<Item> registry) {
      ArsNouveauAPI api = ArsNouveauAPI.getInstance();

      for (Entry<ResourceLocation, Supplier<Glyph>> glyphEntry : api.getGlyphItemMap().entrySet()) {
         Glyph glyph = glyphEntry.getValue().get();
         registry.register(glyphEntry.getKey(), glyph);
         glyph.spellPart.glyphItem = glyph;
      }

      for (AbstractRitual ritual : api.getRitualMap().values()) {
         RitualTablet tablet = new RitualTablet(ritual);
         registry.register(ritual.getRegistryName(), tablet);
         api.getRitualItemMap().put(ritual.getRegistryName(), tablet);
      }

      for (AbstractFamiliarHolder holder : api.getFamiliarHolderMap().values()) {
         FamiliarScript script = new FamiliarScript(holder);
         api.getFamiliarScriptMap().put(holder.getRegistryName(), script);
         registry.register(holder.getRegistryName(), script);
      }

      for (IPerk perk : api.getPerkMap().values()) {
         PerkItem perkItem = new PerkItem(perk);
         api.getPerkItemMap().put(perk.getRegistryName(), perkItem);
         registry.register(perk.getRegistryName(), perkItem);
         if (perk instanceof EmptyPerk) {
            BLANK_THREAD = perkItem;
         }
      }

      registry.register("drygmy_se", new ForgeSpawnEggItem(ModEntities.ENTITY_DRYGMY, 10051392, 16770611, defaultItemProperties()));
      registry.register("starbuncle_se", new ForgeSpawnEggItem(ModEntities.STARBUNCLE_TYPE, 16757299, 16770611, defaultItemProperties()));
      registry.register("whirlisprig_se", new ForgeSpawnEggItem(ModEntities.WHIRLISPRIG_TYPE, 7864115, 16775936, defaultItemProperties()));
      registry.register("wilden_hunter_se", new ForgeSpawnEggItem(ModEntities.WILDEN_HUNTER, 16645629, 13281663, defaultItemProperties()));
      registry.register("wilden_guardian_se", new ForgeSpawnEggItem(ModEntities.WILDEN_GUARDIAN, 16777215, 16752128, defaultItemProperties()));
      registry.register("wilden_stalker_se", new ForgeSpawnEggItem(ModEntities.WILDEN_STALKER, 10183948, 15669272, defaultItemProperties()));
   }

   public static Properties defaultItemProperties() {
      return new Properties().m_41491_(ArsNouveau.itemGroup);
   }
}
