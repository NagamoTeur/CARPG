package com.hollingsworth.arsnouveau.common.datagen;

import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.RegistryHelper;
import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder;
import com.hollingsworth.arsnouveau.api.perk.IPerk;
import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.common.datagen.patchouli.ApparatusPage;
import com.hollingsworth.arsnouveau.common.datagen.patchouli.ApparatusTextPage;
import com.hollingsworth.arsnouveau.common.datagen.patchouli.CraftingPage;
import com.hollingsworth.arsnouveau.common.datagen.patchouli.EnchantingPage;
import com.hollingsworth.arsnouveau.common.datagen.patchouli.EntityPage;
import com.hollingsworth.arsnouveau.common.datagen.patchouli.GlyphScribePage;
import com.hollingsworth.arsnouveau.common.datagen.patchouli.IPatchouliPage;
import com.hollingsworth.arsnouveau.common.datagen.patchouli.ImagePage;
import com.hollingsworth.arsnouveau.common.datagen.patchouli.ImbuementPage;
import com.hollingsworth.arsnouveau.common.datagen.patchouli.LinkPage;
import com.hollingsworth.arsnouveau.common.datagen.patchouli.MultiblockPage;
import com.hollingsworth.arsnouveau.common.datagen.patchouli.PatchouliBuilder;
import com.hollingsworth.arsnouveau.common.datagen.patchouli.RelationsPage;
import com.hollingsworth.arsnouveau.common.datagen.patchouli.SpotlightPage;
import com.hollingsworth.arsnouveau.common.datagen.patchouli.TextPage;
import com.hollingsworth.arsnouveau.common.enchantment.EnchantmentRegistry;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import com.hollingsworth.arsnouveau.common.items.PerkItem;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import com.hollingsworth.arsnouveau.common.perk.EmptyPerk;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.io.FilenameUtils;

public class PatchouliProvider implements DataProvider {
   public final DataGenerator generator;
   public static ResourceLocation AUTOMATION = new ResourceLocation("ars_nouveau", "automation");
   public static ResourceLocation ENCHANTMENTS = new ResourceLocation("ars_nouveau", "enchantments");
   public static ResourceLocation EQUIPMENT = new ResourceLocation("ars_nouveau", "equipment");
   public static ResourceLocation FAMILIARS = new ResourceLocation("ars_nouveau", "familiars");
   public static ResourceLocation GETTING_STARTED = new ResourceLocation("ars_nouveau", "getting_started");
   public static ResourceLocation MOD_NEWS = new ResourceLocation("ars_nouveau", "mod_news");
   public static ResourceLocation MACHINES = new ResourceLocation("ars_nouveau", "machines");
   public static ResourceLocation RESOURCES = new ResourceLocation("ars_nouveau", "resources");
   public static ResourceLocation RITUALS = new ResourceLocation("ars_nouveau", "rituals");
   public static ResourceLocation SOURCE = new ResourceLocation("ars_nouveau", "source");
   public static ResourceLocation GLYPHS_1 = new ResourceLocation("ars_nouveau", "glyphs_1");
   public static ResourceLocation GLYPHS_2 = new ResourceLocation("ars_nouveau", "glyphs_2");
   public static ResourceLocation GLYPHS_3 = new ResourceLocation("ars_nouveau", "glyphs_3");
   public static ResourceLocation ARMOR = new ResourceLocation("ars_nouveau", "armor");
   public List<PatchouliProvider.PatchouliPage> pages = new ArrayList<>();
   List<Enchantment> enchants = Arrays.asList(
      Enchantments.f_44971_,
      Enchantments.f_44979_,
      Enchantments.f_44968_,
      Enchantments.f_44973_,
      Enchantments.f_44984_,
      Enchantments.f_44967_,
      Enchantments.f_44981_,
      Enchantments.f_44966_,
      Enchantments.f_44990_,
      Enchantments.f_44987_,
      Enchantments.f_44952_,
      Enchantments.f_44980_,
      Enchantments.f_44982_,
      Enchantments.f_44959_,
      Enchantments.f_44961_,
      Enchantments.f_44988_,
      Enchantments.f_44969_,
      Enchantments.f_44965_,
      Enchantments.f_44989_,
      Enchantments.f_44960_,
      Enchantments.f_44970_,
      Enchantments.f_44977_,
      Enchantments.f_44985_,
      Enchantments.f_44978_,
      Enchantments.f_44983_,
      Enchantments.f_44972_,
      Enchantments.f_44986_,
      (Enchantment)EnchantmentRegistry.MANA_BOOST_ENCHANTMENT.get(),
      (Enchantment)EnchantmentRegistry.MANA_REGEN_ENCHANTMENT.get()
   );

   public PatchouliProvider(DataGenerator generatorIn) {
      this.generator = generatorIn;
   }

   public void addEntries() {
      Block SOURCESTONE = BlockRegistry.getBlock("sourcestone");

      for (Enchantment g : this.enchants) {
         this.addEnchantmentPage(g);
      }

      for (AbstractRitual r : ArsNouveauAPI.getInstance().getRitualMap().values()) {
         if (r.getRegistryName().m_135827_().equals("ars_nouveau")) {
            this.addRitualPage(r);
         }
      }

      for (AbstractFamiliarHolder rx : ArsNouveauAPI.getInstance().getFamiliarHolderMap().values()) {
         if (rx.getRegistryName().m_135827_().equals("ars_nouveau")) {
            this.addFamiliarPage(rx);
         }
      }

      for (AbstractSpellPart s : ArsNouveauAPI.getInstance().getSpellpartMap().values()) {
         if (s.getRegistryName().m_135827_().equals("ars_nouveau")) {
            this.addGlyphPage(s);
         }
      }

      for (IPerk perk : ArsNouveauAPI.getInstance().getPerkMap().values()) {
         if (perk.getRegistryName().m_135827_().equals("ars_nouveau") && !(perk instanceof EmptyPerk)) {
            this.addPerkPage(perk);
         }
      }

      this.addPage(
         new PatchouliBuilder(GETTING_STARTED, "spell_casting")
            .withIcon(ItemsRegistry.NOVICE_SPELLBOOK)
            .withSortNum(1)
            .withLocalizedText()
            .withLocalizedText()
            .withLocalizedText()
            .withPage(new CraftingPage(ItemsRegistry.NOVICE_SPELLBOOK)),
         this.getPath(GETTING_STARTED, "spell_casting")
      );
      this.addPage(
         new PatchouliBuilder(GETTING_STARTED, "spell_mana").withSortNum(2).withIcon(ItemsRegistry.NOVICE_SPELLBOOK).withLocalizedText().withLocalizedText(),
         this.getPath(GETTING_STARTED, "spell_mana")
      );
      this.addPage(
         new PatchouliBuilder(GETTING_STARTED, "obtaining_gems")
            .withIcon(BlockRegistry.IMBUEMENT_BLOCK)
            .withSortNum(3)
            .withLocalizedText()
            .withPage(new RelationsPage().withEntry(GETTING_STARTED, "source").withEntry(EQUIPMENT, "dowsing_rod")),
         this.getPath(GETTING_STARTED, "obtaining_gems")
      );
      this.addPage(
         new PatchouliBuilder(GETTING_STARTED, "new_glyphs")
            .withIcon(ItemsRegistry.BLANK_GLYPH)
            .withSortNum(4)
            .withLocalizedText()
            .withPage(new RelationsPage().withEntry(MACHINES, "scribes_block").withEntry(GETTING_STARTED, "source")),
         this.getPath(GETTING_STARTED, "new_glyphs")
      );
      this.addPage(
         new PatchouliBuilder(GETTING_STARTED, "source")
            .withSortNum(5)
            .withIcon(BlockRegistry.SOURCE_JAR)
            .withLocalizedText()
            .withPage(new RelationsPage().withEntry(SOURCE, "source_jar").withEntry(SOURCE, "agronomic_sourcelink")),
         this.getPath(GETTING_STARTED, "source")
      );
      this.addPage(
         new PatchouliBuilder(GETTING_STARTED, "apparatus_crafting")
            .withSortNum(6)
            .withIcon(BlockRegistry.ENCHANTING_APP_BLOCK)
            .withLocalizedText()
            .withPage(new RelationsPage().withEntry(MACHINES, "enchanting_apparatus")),
         this.getPath(GETTING_STARTED, "apparatus_crafting")
      );
      this.addPage(
         new PatchouliBuilder(GETTING_STARTED, "better_casting")
            .withSortNum(7)
            .withIcon(ItemsRegistry.NOVICE_ROBES)
            .withLocalizedText()
            .withPage(new RelationsPage().withEntry(MACHINES, "enchanting_apparatus")),
         this.getPath(GETTING_STARTED, "better_casting")
      );
      this.addPage(
         new PatchouliBuilder(GETTING_STARTED, "world_generation")
            .withSortNum(8)
            .withIcon(ItemsRegistry.SOURCE_GEM)
            .withLocalizedText()
            .withPage(new RelationsPage().withEntry(MACHINES, "imbuement_chamber").withEntry(RESOURCES, "archwood").withEntry(RESOURCES, "sourceberry")),
         this.getPath(GETTING_STARTED, "world_generation")
      );
      this.addPage(
         new PatchouliBuilder(GETTING_STARTED, "upgrades")
            .withSortNum(9)
            .withIcon(ItemsRegistry.ARCHMAGE_SPELLBOOK)
            .withLocalizedText()
            .withPage(new RelationsPage().withEntry(EQUIPMENT, "spell_books").withEntry(EQUIPMENT, "armor")),
         this.getPath(GETTING_STARTED, "upgrades")
      );
      this.addPage(
         new PatchouliBuilder(GETTING_STARTED, "starting_automation")
            .withSortNum(10)
            .withIcon(BlockRegistry.BASIC_SPELL_TURRET)
            .withLocalizedText()
            .withPage(
               new RelationsPage()
                  .withEntry(AUTOMATION, "spell_turret")
                  .withEntry(AUTOMATION, "spell_prism")
                  .withEntry(AUTOMATION, "starbuncle_charm")
                  .withEntry(AUTOMATION, "wixie_charm")
            ),
         this.getPath(GETTING_STARTED, "starting_automation")
      );
      this.addPage(
         new PatchouliBuilder(GETTING_STARTED, "trinkets")
            .withIcon(ItemsRegistry.WARP_SCROLL)
            .withSortNum(11)
            .withLocalizedText()
            .withPage(
               new RelationsPage()
                  .withEntry(EQUIPMENT, "ring_of_greater_discount")
                  .withEntry(EQUIPMENT, "jar_of_light")
                  .withEntry(EQUIPMENT, "void_jar")
                  .withEntry(EQUIPMENT, "warp_scroll")
            ),
         this.getPath(GETTING_STARTED, "trinkets")
      );
      this.addBasicItem(ItemsRegistry.AMULET_OF_MANA_BOOST, EQUIPMENT, new ApparatusPage(ItemsRegistry.AMULET_OF_MANA_BOOST));
      this.addBasicItem(ItemsRegistry.AMULET_OF_MANA_REGEN, EQUIPMENT, new ApparatusPage(ItemsRegistry.AMULET_OF_MANA_REGEN));
      this.addBasicItem(ItemsRegistry.BELT_OF_LEVITATION, EQUIPMENT, new ApparatusPage(ItemsRegistry.BELT_OF_LEVITATION));
      this.addBasicItem(ItemsRegistry.BELT_OF_UNSTABLE_GIFTS, EQUIPMENT, new ApparatusPage(ItemsRegistry.BELT_OF_UNSTABLE_GIFTS));
      this.addBasicItem(ItemsRegistry.JAR_OF_LIGHT, EQUIPMENT, new ApparatusPage(ItemsRegistry.JAR_OF_LIGHT));
      PatchouliProvider.PatchouliPage starby = this.addPage(
         new PatchouliBuilder(AUTOMATION, ItemsRegistry.STARBUNCLE_CHARM)
            .withLocalizedText()
            .withPage(new ApparatusPage(ItemsRegistry.STARBUNCLE_CHARM))
            .withPage(
               new EntityPage(RegistryHelper.getRegistryName((EntityType<?>)ModEntities.STARBUNCLE_TYPE.get()).toString())
                  .withText(this.getLangPath("starbuncle_charm", 2))
            )
            .withPage(new TextPage(this.getLangPath("starbuncle_charm", 3)).withTitle("ars_nouveau.summoning"))
            .withPage(new TextPage(this.getLangPath("starbuncle_charm", 4)).withTitle("ars_nouveau.item_transport"))
            .withPage(new TextPage(this.getLangPath("starbuncle_charm", 5)).withTitle("ars_nouveau.filtering"))
            .withPage(new TextPage(this.getLangPath("starbuncle_charm", 6)).withTitle("ars_nouveau.pathing"))
            .withPage(new TextPage(this.getLangPath("starbuncle_charm", 7)).withTitle("ars_nouveau.starbuncle_bed")),
         this.getPath(AUTOMATION, "starbuncle_charm")
      );
      this.addPage(
         new PatchouliBuilder(AUTOMATION, ItemsRegistry.DRYGMY_CHARM)
            .withLocalizedText()
            .withPage(new ApparatusPage(ItemsRegistry.DRYGMY_CHARM))
            .withPage(
               new EntityPage(RegistryHelper.getRegistryName((EntityType<?>)ModEntities.ENTITY_DRYGMY.get()).toString())
                  .withText(this.getLangPath("drygmy_charm", 2))
            )
            .withPage(new TextPage(this.getLangPath("drygmy_charm", 3)).withTitle("ars_nouveau.summoning"))
            .withPage(new TextPage(this.getLangPath("drygmy_charm", 4)).withTitle("ars_nouveau.happiness"))
            .withPage(new TextPage(this.getLangPath("drygmy_charm", 5)).withTitle("ars_nouveau.production"))
            .withPage(new RelationsPage().withEntry(MACHINES, "mob_jar")),
         this.getPath(AUTOMATION, "drygmy_charm")
      );
      this.addPage(
         new PatchouliBuilder(EQUIPMENT, ItemsRegistry.DULL_TRINKET)
            .withPage(new CraftingPage(ItemsRegistry.DULL_TRINKET).withRecipe2(ItemsRegistry.MUNDANE_BELT))
            .withPage(new CraftingPage(ItemsRegistry.RING_OF_POTENTIAL)),
         this.getPath(EQUIPMENT, "dull_items")
      );
      this.addPage(
         new PatchouliBuilder(RESOURCES, BlockRegistry.MAGE_BLOOM_CROP)
            .withTextPage("ars_nouveau.page.magebloom_crop")
            .withPage(new ApparatusPage(BlockRegistry.MAGE_BLOOM_CROP))
            .withPage(new CraftingPage(ItemsRegistry.MAGE_FIBER).withRecipe2(BlockRegistry.MAGEBLOOM_BLOCK)),
         this.getPath(RESOURCES, "magebloom_crop")
      );
      this.addPage(
         new PatchouliBuilder(EQUIPMENT, ItemsRegistry.POTION_FLASK)
            .withLocalizedText()
            .withPage(new ApparatusPage(ItemsRegistry.POTION_FLASK))
            .withLocalizedText()
            .withPage(new ApparatusPage(ItemsRegistry.POTION_FLASK_EXTEND_TIME))
            .withLocalizedText()
            .withPage(new ApparatusPage(ItemsRegistry.POTION_FLASK_AMPLIFY)),
         this.getPath(EQUIPMENT, "potion_flask")
      );
      this.addPage(
         new PatchouliBuilder(EQUIPMENT, "reactive_enchantment")
            .withIcon(Items.f_42690_)
            .withLocalizedText()
            .withPage(
               new EnchantingPage(
                  "ars_nouveau:" + RegistryHelper.getRegistryName((Enchantment)EnchantmentRegistry.REACTIVE_ENCHANTMENT.get()).m_135815_() + "_1"
               )
            )
            .withLocalizedText()
            .withPage(
               new EnchantingPage(
                  "ars_nouveau:" + RegistryHelper.getRegistryName((Enchantment)EnchantmentRegistry.REACTIVE_ENCHANTMENT.get()).m_135815_() + "_2"
               )
            )
            .withPage(
               new EnchantingPage(
                  "ars_nouveau:" + RegistryHelper.getRegistryName((Enchantment)EnchantmentRegistry.REACTIVE_ENCHANTMENT.get()).m_135815_() + "_3"
               )
            )
            .withPage(
               new EnchantingPage(
                  "ars_nouveau:" + RegistryHelper.getRegistryName((Enchantment)EnchantmentRegistry.REACTIVE_ENCHANTMENT.get()).m_135815_() + "_4"
               )
            )
            .withLocalizedText()
            .withPage(new ApparatusTextPage("ars_nouveau:spell_write")),
         this.getPath(EQUIPMENT, "reactive_enchantment")
      );
      this.addBasicItem(ItemsRegistry.RING_OF_GREATER_DISCOUNT, EQUIPMENT, new ApparatusPage(ItemsRegistry.RING_OF_GREATER_DISCOUNT));
      this.addBasicItem(ItemsRegistry.RING_OF_LESSER_DISCOUNT, EQUIPMENT, new ApparatusPage(ItemsRegistry.RING_OF_LESSER_DISCOUNT));
      PatchouliProvider.PatchouliPage turrets = this.addPage(
         new PatchouliBuilder(AUTOMATION, BlockRegistry.BASIC_SPELL_TURRET)
            .withLocalizedText()
            .withPage(new CraftingPage(BlockRegistry.BASIC_SPELL_TURRET).withRecipe2(BlockRegistry.ROTATING_TURRET))
            .withLocalizedText()
            .withPage(new ApparatusPage(BlockRegistry.ENCHANTED_SPELL_TURRET))
            .withLocalizedText()
            .withPage(new ApparatusPage(BlockRegistry.TIMER_SPELL_TURRET))
            .withLocalizedText(),
         this.getPath(AUTOMATION, "spell_turret")
      );
      this.addBasicItem(ItemsRegistry.SUMMONING_FOCUS, EQUIPMENT, new ApparatusPage(ItemsRegistry.SUMMONING_FOCUS));
      this.addBasicItem(ItemsRegistry.VOID_JAR, EQUIPMENT, new ApparatusPage(ItemsRegistry.VOID_JAR));
      this.addBasicItem(ItemsRegistry.WAND, EQUIPMENT, new ApparatusPage(ItemsRegistry.WAND));
      this.addPage(
         new PatchouliBuilder(AUTOMATION, ItemsRegistry.WHIRLISPRIG_CHARM)
            .withLocalizedText()
            .withPage(new ApparatusPage(ItemsRegistry.WHIRLISPRIG_CHARM))
            .withPage(
               new EntityPage(RegistryHelper.getRegistryName((EntityType<?>)ModEntities.WHIRLISPRIG_TYPE.get()).toString())
                  .withText(this.getLangPath("whirlisprig_charm", 2))
            )
            .withPage(new TextPage(this.getLangPath("whirlisprig_charm", 3)).withTitle("ars_nouveau.summoning"))
            .withPage(new TextPage(this.getLangPath("whirlisprig_charm", 4)).withTitle("ars_nouveau.happiness"))
            .withPage(new TextPage(this.getLangPath("whirlisprig_charm", 5)).withTitle("ars_nouveau.important"))
            .withPage(new TextPage(this.getLangPath("whirlisprig_charm", 6)).withTitle("ars_nouveau.production")),
         this.getPath(AUTOMATION, "whirlisprig_charm")
      );
      PatchouliProvider.PatchouliPage wixie = this.addPage(
         new PatchouliBuilder(AUTOMATION, ItemsRegistry.WIXIE_CHARM)
            .withLocalizedText()
            .withPage(new ApparatusPage(ItemsRegistry.WIXIE_CHARM))
            .withPage(
               new EntityPage(RegistryHelper.getRegistryName((EntityType<?>)ModEntities.ENTITY_WIXIE_TYPE.get()).toString())
                  .withText(this.getLangPath("wixie_charm", 2))
            )
            .withPage(new TextPage(this.getLangPath("wixie_charm", 3)).withTitle("ars_nouveau.item_crafting"))
            .withPage(new TextPage(this.getLangPath("wixie_charm", 4)).withTitle("ars_nouveau.item_crafting_setting"))
            .withPage(new TextPage(this.getLangPath("wixie_charm", 7)).withTitle("ars_nouveau.binding_inventories"))
            .withPage(new TextPage(this.getLangPath("wixie_charm", 5)).withTitle("ars_nouveau.potion_crafting"))
            .withPage(new TextPage(this.getLangPath("wixie_charm", 6))),
         this.getPath(AUTOMATION, "wixie_charm")
      );
      this.addPage(
         new PatchouliBuilder(RESOURCES, "archwood")
            .withIcon(BlockRegistry.BOMBEGRANTE_POD)
            .withLocalizedText()
            .withPage(new SpotlightPage(BlockRegistry.BOMBEGRANTE_POD).withText("ars_nouveau.page.bombegrante").linkRecipe(true))
            .withPage(new SpotlightPage(BlockRegistry.MENDOSTEEN_POD).withText("ars_nouveau.page.mendosteen").linkRecipe(true))
            .withPage(new SpotlightPage(BlockRegistry.FROSTAYA_POD).withText("ars_nouveau.page.frostaya").linkRecipe(true))
            .withPage(new SpotlightPage(BlockRegistry.BASTION_POD).withText("ars_nouveau.page.bastion_fruit").linkRecipe(true)),
         this.getPath(RESOURCES, "archwood")
      );
      this.addPage(
         new PatchouliBuilder(RESOURCES, "archwood_forest").withIcon(BlockRegistry.BLAZING_SAPLING).withLocalizedText(),
         this.getPath(RESOURCES, "archwood_forest")
      );
      this.addPage(
         new PatchouliBuilder(RESOURCES, "decorative").withIcon(SOURCESTONE).withPage(new CraftingPage(SOURCESTONE)).withLocalizedText(),
         this.getPath(RESOURCES, "decorative")
      );
      this.addPage(
         new PatchouliBuilder(RESOURCES, BlockRegistry.LAVA_LILY).withLocalizedText().withPage(new CraftingPage(BlockRegistry.LAVA_LILY)),
         this.getPath(RESOURCES, "lava_lily")
      );
      this.addPage(
         new PatchouliBuilder(RESOURCES, BlockRegistry.SOURCEBERRY_BUSH)
            .withLocalizedText()
            .withPage(new CraftingPage(ItemsRegistry.SOURCE_BERRY_PIE).withRecipe2(ItemsRegistry.SOURCE_BERRY_ROLL)),
         this.getPath(RESOURCES, "sourceberry")
      );
      this.addPage(
         new PatchouliBuilder(RESOURCES, "weald_walker")
            .withIcon((ItemLike)ArsNouveauAPI.getInstance().getRitualItemMap().get(new ResourceLocation("ars_nouveau", RitualLib.AWAKENING)))
            .withLocalizedText()
            .withPage(
               new EntityPage(RegistryHelper.getRegistryName((EntityType<?>)ModEntities.ENTITY_BLAZING_WEALD.get()).toString())
                  .withText(this.getLangPath("weald_walker", 2))
            )
            .withPage(
               new EntityPage(RegistryHelper.getRegistryName((EntityType<?>)ModEntities.ENTITY_CASCADING_WEALD.get()).toString())
                  .withText(this.getLangPath("weald_walker", 3))
            )
            .withPage(
               new EntityPage(RegistryHelper.getRegistryName((EntityType<?>)ModEntities.ENTITY_FLOURISHING_WEALD.get()).toString())
                  .withText(this.getLangPath("weald_walker", 4))
            )
            .withPage(
               new EntityPage(RegistryHelper.getRegistryName((EntityType<?>)ModEntities.ENTITY_VEXING_WEALD.get()).toString())
                  .withText(this.getLangPath("weald_walker", 5))
            ),
         this.getPath(RESOURCES, "weald_walker")
      );
      this.addPage(
         new PatchouliBuilder(RESOURCES, "wilden")
            .withIcon(ItemsRegistry.WILDEN_SPIKE)
            .withLocalizedText()
            .withPage(
               new EntityPage(RegistryHelper.getRegistryName((EntityType<?>)ModEntities.WILDEN_HUNTER.get()).toString())
                  .withScale(0.55F)
                  .withText(this.getLangPath("wilden", 3))
            )
            .withPage(
               new EntityPage(RegistryHelper.getRegistryName((EntityType<?>)ModEntities.WILDEN_STALKER.get()).toString())
                  .withScale(0.55F)
                  .withText(this.getLangPath("wilden", 4))
            )
            .withPage(
               new EntityPage(RegistryHelper.getRegistryName((EntityType<?>)ModEntities.WILDEN_GUARDIAN.get()).toString())
                  .withScale(0.55F)
                  .withText(this.getLangPath("wilden", 5))
            )
            .withPage(
               new EntityPage(RegistryHelper.getRegistryName((EntityType<?>)ModEntities.WILDEN_BOSS.get()).toString())
                  .withScale(0.55F)
                  .withText(this.getLangPath("wilden", 6))
            )
            .withPage(new TextPage(this.getLangPath("wilden", 7))),
         this.getPath(RESOURCES, "wilden")
      );
      final PatchouliProvider.PatchouliPage denyScroll = this.addPage(
         new PatchouliBuilder(AUTOMATION, ItemsRegistry.DENY_ITEM_SCROLL)
            .withLocalizedText()
            .withPage(new CraftingPage(ItemsRegistry.BLANK_PARCHMENT).withRecipe2(ItemsRegistry.DENY_ITEM_SCROLL)),
         this.getPath(AUTOMATION, "deny_scroll")
      );
      final PatchouliProvider.PatchouliPage mimicScroll = this.addPage(
         new PatchouliBuilder(AUTOMATION, ItemsRegistry.MIMIC_ITEM_SCROLL)
            .withLocalizedText()
            .withPage(new CraftingPage(ItemsRegistry.BLANK_PARCHMENT).withRecipe2(ItemsRegistry.MIMIC_ITEM_SCROLL)),
         this.getPath(AUTOMATION, "mimic_scroll")
      );
      final PatchouliProvider.PatchouliPage allowScroll = this.addPage(
         new PatchouliBuilder(AUTOMATION, ItemsRegistry.ALLOW_ITEM_SCROLL)
            .withLocalizedText()
            .withPage(new CraftingPage(ItemsRegistry.BLANK_PARCHMENT).withRecipe2(ItemsRegistry.ALLOW_ITEM_SCROLL)),
         this.getPath(AUTOMATION, "allow_scroll")
      );
      PatchouliProvider.PatchouliPage dominionWand = this.addBasicItem(ItemsRegistry.DOMINION_ROD, AUTOMATION, new ApparatusPage(ItemsRegistry.DOMINION_ROD));
      PatchouliProvider.PatchouliPage prisms = this.addBasicItem(BlockRegistry.SPELL_PRISM, AUTOMATION, new CraftingPage(BlockRegistry.SPELL_PRISM));
      this.addPage(
         new PatchouliBuilder(RESOURCES, BlockRegistry.MAGELIGHT_TORCH)
            .withLocalizedText()
            .withPage(new CraftingPage(BlockRegistry.SCONCE_BLOCK).withRecipe2(BlockRegistry.MAGELIGHT_TORCH)),
         this.getPath(RESOURCES, "magelighting")
      );
      this.addPage(
         new PatchouliBuilder(EQUIPMENT, "spell_books")
            .withIcon(ItemsRegistry.ARCHMAGE_SPELLBOOK)
            .withLocalizedText()
            .withPage(new CraftingPage(ItemsRegistry.NOVICE_SPELLBOOK).withRecipe2("ars_nouveau:apprentice_spell_book_upgrade"))
            .withPage(new CraftingPage("ars_nouveau:archmage_spell_book_upgrade")),
         this.getPath(EQUIPMENT, "spell_books")
      );
      this.addBasicItem(ItemsRegistry.ENCHANTERS_MIRROR, EQUIPMENT, new ApparatusPage(ItemsRegistry.ENCHANTERS_MIRROR));
      this.addBasicItem(ItemsRegistry.ENCHANTERS_SHIELD, EQUIPMENT, new ApparatusPage(ItemsRegistry.ENCHANTERS_SHIELD));
      this.addBasicItem(ItemsRegistry.ENCHANTERS_SWORD, EQUIPMENT, new ApparatusPage(ItemsRegistry.ENCHANTERS_SWORD));
      this.addPage(
         new PatchouliBuilder(EQUIPMENT, ItemsRegistry.SPELL_BOW)
            .withLocalizedText()
            .withPage(new ApparatusPage(ItemsRegistry.SPELL_BOW))
            .withPage(new ImbuementPage("ars_nouveau:imbuement_amplify_arrow"))
            .withPage(new ImbuementPage("ars_nouveau:imbuement_pierce_arrow"))
            .withPage(new ImbuementPage("ars_nouveau:imbuement_split_arrow")),
         this.getPath(EQUIPMENT, "spell_bow")
      );
      this.addPage(
         new PatchouliBuilder(EQUIPMENT, ItemsRegistry.SPELL_CROSSBOW)
            .withLocalizedText()
            .withPage(new ApparatusPage(ItemsRegistry.SPELL_CROSSBOW))
            .withPage(new ImbuementPage("ars_nouveau:imbuement_amplify_arrow"))
            .withPage(new ImbuementPage("ars_nouveau:imbuement_pierce_arrow"))
            .withPage(new ImbuementPage("ars_nouveau:imbuement_split_arrow")),
         this.getPath(EQUIPMENT, "spell_crossbow")
      );
      this.addBasicItem(ItemsRegistry.RUNIC_CHALK, EQUIPMENT, new CraftingPage(ItemsRegistry.RUNIC_CHALK));
      this.addPage(
         new PatchouliBuilder(MACHINES, BlockRegistry.IMBUEMENT_BLOCK)
            .withLocalizedText()
            .withPage(new CraftingPage(BlockRegistry.IMBUEMENT_BLOCK))
            .withPage(new ImbuementPage("ars_nouveau:imbuement_lapis"))
            .withPage(new ImbuementPage("ars_nouveau:imbuement_amethyst"))
            .withPage(new ImbuementPage("ars_nouveau:imbuement_amethyst_block"))
            .withPage(new ImbuementPage("ars_nouveau:imbuement_" + ItemsRegistry.FIRE_ESSENCE.getRegistryName()))
            .withPage(new ImbuementPage("ars_nouveau:imbuement_" + ItemsRegistry.EARTH_ESSENCE.getRegistryName()))
            .withPage(new ImbuementPage("ars_nouveau:imbuement_" + ItemsRegistry.WATER_ESSENCE.getRegistryName()))
            .withPage(new ImbuementPage("ars_nouveau:imbuement_" + ItemsRegistry.AIR_ESSENCE.getRegistryName()))
            .withPage(new ImbuementPage("ars_nouveau:imbuement_" + ItemsRegistry.ABJURATION_ESSENCE.getRegistryName()))
            .withPage(new ImbuementPage("ars_nouveau:imbuement_" + ItemsRegistry.CONJURATION_ESSENCE.getRegistryName()))
            .withPage(new ImbuementPage("ars_nouveau:imbuement_" + ItemsRegistry.MANIPULATION_ESSENCE.getRegistryName())),
         this.getPath(MACHINES, "imbuement_chamber")
      );
      this.addPage(
         new PatchouliBuilder(MACHINES, BlockRegistry.ENCHANTING_APP_BLOCK)
            .withLocalizedText()
            .withPage(new CraftingPage(BlockRegistry.ARCANE_PEDESTAL).withRecipe2(BlockRegistry.ARCANE_PLATFORM))
            .withPage(new CraftingPage(BlockRegistry.ENCHANTING_APP_BLOCK).withRecipe2(BlockRegistry.ARCANE_CORE_BLOCK)),
         this.getPath(MACHINES, "enchanting_apparatus")
      );
      PatchouliProvider.PatchouliPage potionJar = this.addBasicItem(BlockRegistry.POTION_JAR, MACHINES, new CraftingPage(BlockRegistry.POTION_JAR));
      PatchouliProvider.PatchouliPage melder = this.addBasicItem(BlockRegistry.POTION_MELDER, MACHINES, new ApparatusPage(BlockRegistry.POTION_MELDER));
      PatchouliProvider.PatchouliPage diffuser = this.addBasicItem(BlockRegistry.POTION_DIFFUSER, MACHINES, new ApparatusPage(BlockRegistry.POTION_DIFFUSER));
      wixie.builder.withPage(new RelationsPage().withEntry(potionJar).withEntry(melder).withEntry(diffuser));
      potionJar.builder.withPage(new RelationsPage().withEntry(melder).withEntry(diffuser).withEntry(wixie));
      melder.builder.withPage(new RelationsPage().withEntry(potionJar).withEntry(diffuser).withEntry(wixie));
      diffuser.builder.withPage(new RelationsPage().withEntry(potionJar).withEntry(melder).withEntry(wixie));
      this.addBasicItem(BlockRegistry.RITUAL_BLOCK, MACHINES, new CraftingPage(BlockRegistry.RITUAL_BLOCK));
      this.addBasicItem(BlockRegistry.BRAZIER_RELAY, MACHINES, new ApparatusPage(BlockRegistry.BRAZIER_RELAY));
      this.addPage(
         new PatchouliBuilder(MACHINES, BlockRegistry.SCRIBES_BLOCK)
            .withPage(new TextPage(this.getLangPath("scribes_table", 1)).withTitle("ars_nouveau.glyph_crafting"))
            .withPage(new TextPage(this.getLangPath("scribes_table", 2)).withTitle("ars_nouveau.scribing"))
            .withPage(new CraftingPage(ItemsRegistry.BLANK_PARCHMENT).withRecipe2(BlockRegistry.SCRIBES_BLOCK)),
         this.getPath(MACHINES, "scribes_block")
      );
      PatchouliProvider.PatchouliPage portal = this.addPage(
         new PatchouliBuilder(MACHINES, "warp_portal")
            .withIcon(ItemsRegistry.WARP_SCROLL)
            .withLocalizedText()
            .withLocalizedText()
            .withPage(
               new MultiblockPage(this.getLangPath("warp_portal", 3), new String[][]{{" BB "}, {"BPPB"}, {"B0PB"}, {"BPPB"}, {" BB "}})
                  .withMapping("B", "ars_nouveau:sourcestone")
                  .withMapping("P", "ars_nouveau:portal")
                  .withMapping("0", "ars_nouveau:portal")
                  .withText(this.getLangPath("warp_portal", 4))
            ),
         this.getPath(MACHINES, "warp_portal")
      );
      PatchouliProvider.PatchouliPage scroll = this.addBasicItem(ItemsRegistry.WARP_SCROLL, EQUIPMENT, new CraftingPage(ItemsRegistry.WARP_SCROLL));
      PatchouliProvider.PatchouliPage stableScroll = this.addBasicItem(
         ItemsRegistry.STABLE_WARP_SCROLL, EQUIPMENT, new ApparatusPage(ItemsRegistry.STABLE_WARP_SCROLL)
      );
      stableScroll.builder.withPage(new RelationsPage().withEntry(scroll.relationPath()).withEntry(portal.relationPath()));
      scroll.builder.withPage(new RelationsPage().withEntry(stableScroll.relationPath()).withEntry(portal.relationPath()));
      portal.builder.withPage(new RelationsPage().withEntry(scroll.relationPath()).withEntry(stableScroll.relationPath()));
      this.addBasicItem(BlockRegistry.AGRONOMIC_SOURCELINK, SOURCE, new CraftingPage(BlockRegistry.AGRONOMIC_SOURCELINK));
      this.addBasicItem(BlockRegistry.ALCHEMICAL_BLOCK, SOURCE, new CraftingPage(BlockRegistry.ALCHEMICAL_BLOCK));
      this.addBasicItem(BlockRegistry.MYCELIAL_BLOCK, SOURCE, new CraftingPage(BlockRegistry.MYCELIAL_BLOCK));
      this.addBasicItem(BlockRegistry.SOURCE_JAR, SOURCE, new CraftingPage(BlockRegistry.SOURCE_JAR));
      this.addBasicItem(BlockRegistry.RELAY, SOURCE, new CraftingPage(BlockRegistry.RELAY).withText("ars_nouveau.page2.relay"));
      this.addBasicItem(BlockRegistry.RELAY_DEPOSIT, SOURCE, new ApparatusPage(BlockRegistry.RELAY_DEPOSIT));
      this.addBasicItem(BlockRegistry.RELAY_SPLITTER, SOURCE, new ApparatusPage(BlockRegistry.RELAY_SPLITTER));
      this.addBasicItem(BlockRegistry.RELAY_WARP, SOURCE, new ApparatusPage(BlockRegistry.RELAY_WARP));
      this.addBasicItem(BlockRegistry.RELAY_COLLECTOR, SOURCE, new ApparatusPage(BlockRegistry.RELAY_COLLECTOR));
      this.addBasicItem(BlockRegistry.VITALIC_BLOCK, SOURCE, new CraftingPage(BlockRegistry.VITALIC_BLOCK));
      this.addPage(
         new PatchouliBuilder(SOURCE, BlockRegistry.VOLCANIC_BLOCK)
            .withLocalizedText()
            .withPage(new TextPage(this.getLangPath("volcanic_sourcelink", 2)).withTitle("ars_nouveau.active_generation"))
            .withPage(new TextPage(this.getLangPath("volcanic_sourcelink", 3)).withTitle("ars_nouveau.heat"))
            .withPage(new CraftingPage(BlockRegistry.VOLCANIC_BLOCK).withRecipe2(BlockRegistry.LAVA_LILY))
            .withPage(new TextPage(this.getLangPath("volcanic_sourcelink", 4))),
         this.getPath(SOURCE, "volcanic_sourcelink")
      );
      this.addPage(
         new PatchouliBuilder(ENCHANTMENTS, "how_to_enchant")
            .withIcon(BlockRegistry.ENCHANTING_APP_BLOCK)
            .withSortNum(-1)
            .withLocalizedText()
            .withLocalizedText()
            .withPage(new RelationsPage().withEntry(MACHINES, "enchanting_apparatus")),
         this.getPath(ENCHANTMENTS, "how_to_enchant")
      );
      this.addPage(
         new PatchouliBuilder(RITUALS, "performing_rituals")
            .withSortNum(-1)
            .withIcon(BlockRegistry.RITUAL_BLOCK)
            .withLocalizedText()
            .withLocalizedText()
            .withPage(new CraftingPage((ItemLike)ArsNouveauAPI.getInstance().getRitualItemMap().get(new ResourceLocation("ars_nouveau", RitualLib.SUNRISE))))
            .withPage(new RelationsPage().withEntry(MACHINES, "ritual_brazier")),
         this.getPath(RITUALS, "performing_rituals")
      );
      this.addPage(
         new PatchouliBuilder(FAMILIARS, "summoning_familiars")
            .withSortNum(-1)
            .withIcon((ItemLike)ArsNouveauAPI.getInstance().getRitualItemMap().get(new ResourceLocation("ars_nouveau", RitualLib.BINDING)))
            .withLocalizedText()
            .withLocalizedText()
            .withPage(new CraftingPage((ItemLike)ArsNouveauAPI.getInstance().getRitualItemMap().get(new ResourceLocation("ars_nouveau", RitualLib.BINDING))))
            .withPage(new RelationsPage().withEntry(MACHINES, "ritual_brazier").withEntry(RITUALS, "binding")),
         this.getPath(FAMILIARS, "summoning_familiars")
      );
      this.addPage(
         new PatchouliBuilder(MOD_NEWS, "mod_news")
            .withIcon(ItemsRegistry.SPELL_PARCHMENT)
            .withPage(new LinkPage("https://discord.gg/y7TMXZu", "ars_nouveau.discord_text", "ars_nouveau.community")),
         this.getPath(MOD_NEWS, "mod_news")
      );
      this.addBasicItem(ItemsRegistry.DOWSING_ROD, EQUIPMENT, new CraftingPage(ItemsRegistry.DOWSING_ROD));
      this.addPage(
         new PatchouliBuilder(AUTOMATION, ItemsRegistry.AMETHYST_GOLEM_CHARM)
            .withLocalizedText()
            .withPage(
               new EntityPage(RegistryHelper.getRegistryName((EntityType<?>)ModEntities.AMETHYST_GOLEM.get()).toString())
                  .withText(this.getLangPath("amethyst_golem_charm", 2))
                  .withScale(0.75F)
                  .withOffset(0.2F)
            )
            .withPage(new TextPage(this.getLangPath("amethyst_golem_charm", 3)).withTitle("ars_nouveau.summoning"))
            .withPage(new TextPage(this.getLangPath("amethyst_golem_charm", 4)).withTitle("ars_nouveau.amethyst_farming"))
            .withPage(new TextPage(this.getLangPath("amethyst_golem_charm", 5)).withTitle("ars_nouveau.amethyst_storage")),
         this.getPath(AUTOMATION, "amethyst_golem_charm")
      );
      this.addBasicItem(ItemsRegistry.ANNOTATED_CODEX, EQUIPMENT, new CraftingPage(ItemsRegistry.ANNOTATED_CODEX));
      PatchouliProvider.PatchouliPage starbyBed = this.addPage(
         new PatchouliBuilder(AUTOMATION, BlockRegistry.ORANGE_SBED)
            .withName("ars_nouveau.summon_bed")
            .withLocalizedText("summon_bed")
            .withPage(new CraftingPage(BlockRegistry.ORANGE_SBED).withRecipe2(BlockRegistry.BLUE_SBED))
            .withPage(new CraftingPage(BlockRegistry.GREEN_SBED).withRecipe2(BlockRegistry.YELLOW_SBED))
            .withPage(new CraftingPage(BlockRegistry.RED_SBED).withRecipe2(BlockRegistry.PURPLE_SBED)),
         this.getPath(AUTOMATION, "summon_bed")
      );
      PatchouliProvider.PatchouliPage scryCaster = this.addBasicItem(ItemsRegistry.SCRY_CASTER, EQUIPMENT, new ApparatusPage(ItemsRegistry.SCRY_CASTER));
      PatchouliProvider.PatchouliPage scryCrystal = this.addBasicItem(BlockRegistry.SCRYERS_CRYSTAL, MACHINES, new CraftingPage(BlockRegistry.SCRYERS_CRYSTAL));
      PatchouliProvider.PatchouliPage oculus = this.addBasicItem(BlockRegistry.SCRYERS_OCULUS, MACHINES, new ApparatusPage(BlockRegistry.SCRYERS_OCULUS));
      PatchouliProvider.PatchouliPage scryScroll = this.addBasicItem(ItemsRegistry.SCRYER_SCROLL, MACHINES, null);
      scryCrystal.builder.withPage(new RelationsPage().withEntry(scryCaster).withEntry(scryScroll).withEntry(oculus));
      scryCaster.builder.withPage(new RelationsPage().withEntry(scryCrystal.relationPath()));
      oculus.builder.withPage(new RelationsPage().withEntry(scryScroll).withEntry(scryCrystal));
      scryScroll.builder.withPage(new RelationsPage().withEntry(scryCaster).withEntry(oculus).withEntry(scryCrystal));
      PatchouliProvider.PatchouliPage starbyShades = this.addBasicItem(
         ItemsRegistry.STARBUNCLE_SHADES, AUTOMATION, new CraftingPage(ItemsRegistry.STARBUNCLE_SHADES)
      );
      PatchouliProvider.PatchouliPage wixieHat = this.addBasicItem(ItemsRegistry.WIXIE_HAT, AUTOMATION, new CraftingPage(ItemsRegistry.WIXIE_HAT));
      this.addPage(
         new PatchouliBuilder(MOD_NEWS, "support_mod")
            .withIcon(ItemsRegistry.STARBUNCLE_CHARM)
            .withPage(new LinkPage("https://www.patreon.com/arsnouveau", "ars_nouveau.patreon_text", "ars_nouveau.patreon"))
            .withPage(new EntityPage((EntityType)ModEntities.LILY.get()).withText("ars_nouveau.lily"))
            .withPage(new LinkPage("https://www.redbubble.com/people/Gootastic/explore?page=1&sortOrder=recent", "ars_nouveau.store_text", "ars_nouveau.store")),
         this.getPath(MOD_NEWS, "support_mod")
      );
      this.addPage(
         new PatchouliBuilder(EQUIPMENT, ItemsRegistry.SHAPERS_FOCUS)
            .withLocalizedText()
            .withPage(new ApparatusPage(ItemsRegistry.SHAPERS_FOCUS))
            .withPage(new TextPage(this.getLangPath("shapers_focus", 2)).withTitle("ars_nouveau.shapers_focus.blocks"))
            .withPage(new TextPage(this.getLangPath("shapers_focus", 3)).withTitle("ars_nouveau.shapers_focus.entities"))
            .withPage(new TextPage(this.getLangPath("shapers_focus", 4)).withTitle("ars_nouveau.shapers_focus.examples")),
         this.getPath(EQUIPMENT, "shapers_focus")
      );
      this.addBasicItem(ItemsRegistry.ALCHEMISTS_CROWN, EQUIPMENT, new ApparatusPage(ItemsRegistry.ALCHEMISTS_CROWN));
      this.addPage(
         new PatchouliBuilder(EQUIPMENT, "flask_cannons")
            .withLocalizedText()
            .withIcon(ItemsRegistry.SPLASH_LAUNCHER)
            .withPage(new ApparatusPage(ItemsRegistry.SPLASH_LAUNCHER))
            .withPage(new ApparatusPage(ItemsRegistry.LINGERING_LAUNCHER)),
         this.getPath(EQUIPMENT, "flask_launcher")
      );
      PatchouliBuilder ARMOR_ENTRY = new PatchouliBuilder(EQUIPMENT, "armor")
         .withIcon(ItemsRegistry.NOVICE_ROBES)
         .withLocalizedText()
         .withPage(new CraftingPage(ItemsRegistry.MAGE_FIBER))
         .withPage(new TextPage("ars_nouveau.page.threads").withTitle("ars_nouveau.threads"))
         .withPage(
            new ImagePage()
               .withEntry(new ResourceLocation("ars_nouveau", "textures/gui/entries/sorcerer_diagram.png"))
               .withEntry(new ResourceLocation("ars_nouveau", "textures/gui/entries/arcanist_thread_diagram.png"))
               .withEntry(new ResourceLocation("ars_nouveau", "textures/gui/entries/battlemage_diagram.png"))
               .withBorder()
               .withTitle("ars_nouveau.thread_layout")
               .withText("ars_nouveau.page.layout_desc")
         )
         .withPage(new ApparatusPage(ItemsRegistry.NOVICE_HOOD))
         .withPage(new ApparatusPage(ItemsRegistry.NOVICE_ROBES))
         .withPage(new ApparatusPage(ItemsRegistry.NOVICE_LEGGINGS))
         .withPage(new ApparatusPage(ItemsRegistry.NOVICE_BOOTS))
         .withPage(new ApparatusPage(ItemsRegistry.APPRENTICE_HOOD))
         .withPage(new ApparatusPage(ItemsRegistry.APPRENTICE_ROBES))
         .withPage(new ApparatusPage(ItemsRegistry.APPRENTICE_LEGGINGS))
         .withPage(new ApparatusPage(ItemsRegistry.APPRENTICE_BOOTS))
         .withPage(new ApparatusPage(ItemsRegistry.ARCHMAGE_HOOD))
         .withPage(new ApparatusPage(ItemsRegistry.ARCHMAGE_ROBES))
         .withPage(new ApparatusPage(ItemsRegistry.ARCHMAGE_LEGGINGS))
         .withPage(new ApparatusPage(ItemsRegistry.ARCHMAGE_BOOTS))
         .withPage(new RelationsPage().withEntry(ARMOR, "armor_upgrade"));
      this.addPage(ARMOR_ENTRY.withCategory(ARMOR), this.getPath(ARMOR, "armor"));
      this.addPage(
         new PatchouliBuilder(ARMOR, "armor_upgrading")
            .withLocalizedText()
            .withPage(new TextPage(this.getLangPath("armor_upgrading", 2)).withTitle("ars_nouveau.armor_tiers"))
            .withIcon(ItemsRegistry.APPRENTICE_HOOD)
            .withPage(new ApparatusTextPage("ars_nouveau:upgrade_1"))
            .withPage(new ApparatusTextPage("ars_nouveau:upgrade_2"))
            .withPage(new RelationsPage().withEntry(ARMOR, "armor").withEntry(ARMOR, "alteration_table"))
            .withSortNum(1),
         this.getPath(ARMOR, "armor_upgrade")
      );
      this.addPage(
         new PatchouliBuilder(ARMOR, "applying_perks")
            .withLocalizedText()
            .withLocalizedText()
            .withPage(new RelationsPage().withEntry(ARMOR, "alteration_table"))
            .withIcon(ItemsRegistry.BLANK_THREAD)
            .withSortNum(2),
         this.getPath(ARMOR, "applying_perks")
      );
      this.addPage(
         new PatchouliBuilder(ARMOR, "alteration_table")
            .withLocalizedText()
            .withPage(new CraftingPage(BlockRegistry.ALTERATION_TABLE).withRecipe2(ItemsRegistry.BLANK_THREAD))
            .withPage(new RelationsPage().withEntry(ARMOR, "armor_upgrade").withEntry(ARMOR, "armor"))
            .withIcon(BlockRegistry.ALTERATION_TABLE)
            .withSortNum(3),
         this.getPath(ARMOR, "alteration_table")
      );
      this.addPage(
         new PatchouliBuilder(MACHINES, "mob_jar")
            .withIcon(BlockRegistry.MOB_JAR)
            .withLocalizedText()
            .withPage(new TextPage("ars_nouveau.page2.mob_jar").withTitle("ars_nouveau.title.mob_jar"))
            .withPage(new EntityPage(EntityType.f_20492_).withText("mob_jar.villager"))
            .withPage(new EntityPage(EntityType.f_20511_).withText("mob_jar.piglin"))
            .withPage(new TextPage("mob_jar.allay").withTitle("mob_jar.allay.title"))
            .withPage(new EntityPage(EntityType.f_217014_))
            .withPage(new EntityPage(EntityType.f_20565_).withText("mob_jar.ender_dragon"))
            .withPage(new EntityPage(EntityType.f_20520_).withText("mob_jar.sheep"))
            .withPage(new EntityPage(EntityType.f_20555_).withText("mob_jar.chicken"))
            .withPage(new EntityPage(EntityType.f_20557_).withText("mob_jar.cow"))
            .withPage(new EntityPage(EntityType.f_20504_).withText("mob_jar.mooshroom"))
            .withPage(new EntityPage(EntityType.f_20516_).withText("mob_jar.pufferfish"))
            .withPage(new EntityPage(EntityType.f_217012_).withText("mob_jar.frog"))
            .withPage(new EntityPage(EntityType.f_20507_).withText("mob_jar.panda"))
            .withPage(new EntityPage((EntityType)ModEntities.ENTITY_DUMMY.get()).withText("mob_jar.dummy"))
            .withPage(new CraftingPage(BlockRegistry.MOB_JAR))
            .withPage(new RelationsPage().withEntry(RITUALS, RitualLib.CONTAINMENT).withEntry(AUTOMATION, "drygmy_charm")),
         this.getPath(MACHINES, "mob_jar")
      );
      PatchouliProvider.PatchouliPage voidPrism = this.addBasicItem(BlockRegistry.VOID_PRISM, AUTOMATION, new CraftingPage(BlockRegistry.VOID_PRISM));
      turrets.builder.withPage(new RelationsPage().withEntry(prisms).withEntry(voidPrism));
      prisms.builder.withPage(new RelationsPage().withEntry(turrets).withEntry(voidPrism));
      voidPrism.builder.withPage(new RelationsPage().withEntry(turrets).withEntry(prisms));
      this.addPage(
         new PatchouliBuilder(RESOURCES, "illusion_blocks")
            .withIcon(BlockRegistry.GHOST_WEAVE)
            .withLocalizedText()
            .withPage(new CraftingPage(BlockRegistry.MIRROR_WEAVE))
            .withLocalizedText()
            .withPage(new CraftingPage(BlockRegistry.FALSE_WEAVE))
            .withLocalizedText()
            .withPage(new CraftingPage(BlockRegistry.GHOST_WEAVE))
            .withLocalizedText()
            .withPage(new CraftingPage(BlockRegistry.SKY_WEAVE)),
         this.getPath(RESOURCES, "illusion_blocks")
      );
      PatchouliProvider.PatchouliPage bookwyrm = this.addPage(
         new PatchouliBuilder(MACHINES, ItemsRegistry.BOOKWYRM_CHARM)
            .withLocalizedText()
            .withPage(
               new EntityPage(RegistryHelper.getRegistryName((EntityType<?>)ModEntities.ENTITY_BOOKWYRM_TYPE.get()).toString())
                  .withText(this.getLangPath("bookwyrm_charm", 2))
            ),
         this.getPath(MACHINES, "bookwyrm_charm")
      );
      PatchouliProvider.PatchouliPage storageLectern = this.addPage(
         new PatchouliBuilder(MACHINES, BlockRegistry.CRAFTING_LECTERN)
            .withLocalizedText()
            .withPage(new TextPage(this.getLangPath("storage", 2)).withTitle("ars_nouveau.storage"))
            .withPage(new TextPage(this.getLangPath("storage", 3)).withTitle("ars_nouveau.storage_tabs"))
            .withPage(new ApparatusPage(BlockRegistry.CRAFTING_LECTERN)),
         this.getPath(MACHINES, "storage_lectern")
      );
      bookwyrm.builder.withPage(new RelationsPage().withEntry(storageLectern.relationPath()));
      PatchouliProvider.PatchouliPage displayCase = this.addPage(
         new PatchouliBuilder(MACHINES, BlockRegistry.ITEM_DETECTOR)
            .withLocalizedText()
            .withLocalizedText()
            .withPage(new CraftingPage(BlockRegistry.ITEM_DETECTOR)),
         this.getPath(MACHINES, "item_detector")
      );
      PatchouliProvider.PatchouliPage repository = this.addPage(
         new PatchouliBuilder(MACHINES, BlockRegistry.REPOSITORY)
            .withLocalizedText()
            .withPage(new CraftingPage(BlockRegistry.REPOSITORY).withRecipe2(BlockRegistry.ARCHWOOD_CHEST)),
         this.getPath(MACHINES, "repository")
      );
      var scrollRelations = new ArrayList<PatchouliProvider.PatchouliPage>() {
         {
            this.add(denyScroll);
            this.add(mimicScroll);
            this.add(allowScroll);
         }
      };
      storageLectern.builder
         .withPage(new RelationsPage().withEntry(bookwyrm.relationPath()).withEntry(displayCase.relationPath()).withEntry(repository.relationPath()));
      repository.builder.withPage(new RelationsPage().withEntry(storageLectern.relationPath()));
      starby.builder
         .withPage(
            new RelationsPage()
               .withEntries(scrollRelations)
               .withEntry(dominionWand)
               .withEntry(storageLectern)
               .withEntry(starbyShades)
               .withEntry(wixieHat)
               .withEntry(starbyBed)
         );
      denyScroll.builder
         .withPage(
            new RelationsPage()
               .withEntry(mimicScroll)
               .withEntry(allowScroll)
               .withEntry(dominionWand)
               .withEntry(starby)
               .withEntry(storageLectern)
               .withEntry(displayCase)
         );
      mimicScroll.builder
         .withPage(
            new RelationsPage()
               .withEntry(denyScroll)
               .withEntry(allowScroll)
               .withEntry(dominionWand)
               .withEntry(starby)
               .withEntry(storageLectern)
               .withEntry(displayCase)
         );
      allowScroll.builder
         .withPage(
            new RelationsPage()
               .withEntry(denyScroll)
               .withEntry(mimicScroll)
               .withEntry(dominionWand)
               .withEntry(starby)
               .withEntry(storageLectern)
               .withEntry(displayCase)
         );
      displayCase.builder.withPage(new RelationsPage().withEntry(bookwyrm).withEntry(storageLectern).withEntry(dominionWand).withEntries(scrollRelations));
      dominionWand.builder.withPage(new RelationsPage().withEntry(storageLectern).withEntry(displayCase).withEntry(starby));
      this.addPage(
         new PatchouliBuilder(AUTOMATION, BlockRegistry.SPELL_SENSOR).withLocalizedText().withPage(new ApparatusPage(BlockRegistry.SPELL_SENSOR)),
         this.getPath(AUTOMATION, "spell_sensor")
      );
      this.addPage(
         new PatchouliBuilder(EQUIPMENT, ItemsRegistry.JUMP_RING).withLocalizedText().withPage(new ApparatusPage(ItemsRegistry.JUMP_RING)),
         this.getPath(EQUIPMENT, "jump_ring")
      );
      this.addPage(
         new PatchouliBuilder(AUTOMATION, BlockRegistry.REDSTONE_RELAY).withLocalizedText().withPage(new CraftingPage(BlockRegistry.REDSTONE_RELAY)),
         this.getPath(AUTOMATION, "redstone_relay")
      );
   }

   public String getLangPath(String name, int count) {
      return "ars_nouveau.page" + count + "." + name;
   }

   public String getLangPath(String name) {
      return "ars_nouveau.page." + name;
   }

   public PatchouliProvider.PatchouliPage addPage(PatchouliBuilder builder, Path path) {
      return this.addPage(new PatchouliProvider.PatchouliPage(builder, path));
   }

   public PatchouliProvider.PatchouliPage addPage(PatchouliProvider.PatchouliPage patchouliPage) {
      this.pages.add(patchouliPage);
      return patchouliPage;
   }

   public PatchouliBuilder buildBasicItem(ItemLike item, ResourceLocation category, IPatchouliPage recipePage) {
      PatchouliBuilder builder = new PatchouliBuilder(category, item.m_5456_().m_5524_())
         .withIcon(item.m_5456_())
         .withPage(new TextPage("ars_nouveau.page." + RegistryHelper.getRegistryName(item.m_5456_()).m_135815_()));
      if (recipePage != null) {
         builder.withPage(recipePage);
      }

      return builder;
   }

   public PatchouliProvider.PatchouliPage addBasicItem(ItemLike item, ResourceLocation category, IPatchouliPage recipePage) {
      PatchouliBuilder builder = this.buildBasicItem(item, category, recipePage);
      return this.addPage(new PatchouliProvider.PatchouliPage(builder, this.getPath(category, RegistryHelper.getRegistryName(item.m_5456_()))));
   }

   public void addBasicItem(RegistryObject<? extends ItemLike> item, ResourceLocation category, IPatchouliPage recipePage) {
      this.addBasicItem((ItemLike)item.get(), category, recipePage);
   }

   public Path getPath(ResourceLocation category, ResourceLocation fileName) {
      return this.generator
         .m_123916_()
         .resolve("data/ars_nouveau/patchouli_books/worn_notebook/en_us/entries/" + category.m_135815_() + "/" + fileName.m_135815_() + ".json");
   }

   public Path getPath(ResourceLocation category, String fileName) {
      return this.generator
         .m_123916_()
         .resolve("data/ars_nouveau/patchouli_books/worn_notebook/en_us/entries/" + category.m_135815_() + "/" + fileName + ".json");
   }

   public void m_213708_(CachedOutput cache) throws IOException {
      this.addEntries();

      for (PatchouliProvider.PatchouliPage patchouliPage : this.pages) {
         DataProvider.m_236072_(cache, patchouliPage.build(), patchouliPage.path);
      }
   }

   public void addGlyphPage(AbstractSpellPart spellPart) {
      ResourceLocation category = switch (spellPart.defaultTier().value) {
         case 1 -> GLYPHS_1;
         case 2 -> GLYPHS_2;
         default -> GLYPHS_3;
      };
      PatchouliBuilder builder = new PatchouliBuilder(category, spellPart.getName())
         .withName("ars_nouveau.glyph_name." + spellPart.getRegistryName().m_135815_())
         .withIcon(spellPart.getRegistryName().toString())
         .withSortNum(spellPart instanceof AbstractCastMethod ? 1 : (spellPart instanceof AbstractEffect ? 2 : 3))
         .withPage(new TextPage("ars_nouveau.glyph_desc." + spellPart.getRegistryName().m_135815_()))
         .withPage(new GlyphScribePage(spellPart));
      this.pages.add(new PatchouliProvider.PatchouliPage(builder, this.getPath(category, spellPart.getRegistryName().m_135815_())));
   }

   public void addFamiliarPage(AbstractFamiliarHolder familiarHolder) {
      PatchouliBuilder builder = new PatchouliBuilder(FAMILIARS, "entity.ars_nouveau." + familiarHolder.getRegistryName().m_135815_())
         .withIcon("ars_nouveau:" + familiarHolder.getRegistryName().m_135815_())
         .withTextPage("ars_nouveau.familiar_desc." + familiarHolder.getRegistryName().m_135815_())
         .withPage(new EntityPage(familiarHolder.getRegistryName().toString()));
      this.pages
         .add(
            new PatchouliProvider.PatchouliPage(
               builder,
               this.generator
                  .m_123916_()
                  .resolve(
                     "data/"
                        + familiarHolder.getRegistryName().m_135827_()
                        + "/patchouli_books/worn_notebook/en_us/entries/familiars/"
                        + familiarHolder.getRegistryName().m_135815_()
                        + ".json"
                  )
            )
         );
   }

   public void addRitualPage(AbstractRitual ritual) {
      PatchouliBuilder builder = new PatchouliBuilder(RITUALS, "item." + ritual.getRegistryName().m_135827_() + "." + ritual.getRegistryName().m_135815_())
         .withIcon(ritual.getRegistryName().toString())
         .withTextPage(ritual.getDescriptionKey())
         .withPage(new CraftingPage(ritual.getRegistryName().toString()));
      this.pages
         .add(
            new PatchouliProvider.PatchouliPage(
               builder,
               this.generator
                  .m_123916_()
                  .resolve(
                     "data/"
                        + ritual.getRegistryName().m_135827_()
                        + "/patchouli_books/worn_notebook/en_us/entries/rituals/"
                        + ritual.getRegistryName().m_135815_()
                        + ".json"
                  )
            )
         );
   }

   public void addEnchantmentPage(Enchantment enchantment) {
      PatchouliBuilder builder = new PatchouliBuilder(ENCHANTMENTS, enchantment.m_44704_()).withIcon(RegistryHelper.getRegistryName(Items.f_42690_).toString());

      for (int i = enchantment.m_44702_(); i <= enchantment.m_6586_(); i++) {
         builder.withPage(new EnchantingPage("ars_nouveau:" + RegistryHelper.getRegistryName(enchantment).m_135815_() + "_" + i));
      }

      this.pages
         .add(
            new PatchouliProvider.PatchouliPage(
               builder,
               this.generator
                  .m_123916_()
                  .resolve("data/ars_nouveau/patchouli_books/worn_notebook/en_us/entries/" + RegistryHelper.getRegistryName(enchantment).m_135815_() + ".json")
            )
         );
   }

   public void addPerkPage(IPerk perk) {
      PerkItem perkItem = ArsNouveauAPI.getInstance().getPerkItemMap().get(perk.getRegistryName());
      PatchouliBuilder builder = new PatchouliBuilder(ARMOR, perkItem)
         .withIcon(perkItem)
         .withTextPage(perk.getDescriptionKey())
         .withPage(new ApparatusPage(perkItem))
         .withSortNum(99);
      this.pages
         .add(
            new PatchouliProvider.PatchouliPage(
               builder,
               this.generator
                  .m_123916_()
                  .resolve(
                     "data/"
                        + perk.getRegistryName().m_135827_()
                        + "/patchouli_books/worn_notebook/en_us/entries/armor/"
                        + perk.getRegistryName().m_135815_()
                        + ".json"
                  )
            )
         );
   }

   public String m_6055_() {
      return "Patchouli";
   }

   public static record PatchouliPage(PatchouliBuilder builder, Path path) {
      public JsonObject build() {
         return this.builder.build();
      }

      public String relationPath() {
         String fileName = this.path.getFileName().toString();
         fileName = FilenameUtils.removeExtension(fileName);
         return this.builder.category.toString() + "/" + fileName;
      }
   }
}
