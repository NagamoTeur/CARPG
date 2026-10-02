package com.hollingsworth.arsnouveau.common.datagen;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.common.items.FamiliarScript;
import com.hollingsworth.arsnouveau.common.items.Glyph;
import com.hollingsworth.arsnouveau.common.items.PerkItem;
import com.hollingsworth.arsnouveau.common.items.RitualTablet;
import com.hollingsworth.arsnouveau.common.lib.LibBlockNames;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

public class LangDatagen extends LanguageProvider {
   private final Map<String, String> data = new TreeMap<>();

   public LangDatagen(DataGenerator gen, String modid, String locale) {
      super(gen, modid, locale);
   }

   protected void addTranslations() {
      ArsNouveauAPI arsNouveauAPI = ArsNouveauAPI.getInstance();

      for (Supplier<Glyph> supplier : arsNouveauAPI.getGlyphItemMap().values()) {
         Glyph i = supplier.get();
         if (supplier.get().spellPart.getRegistryName().m_135827_().equals("ars_nouveau")) {
            this.add("ars_nouveau.glyph_desc." + i.spellPart.getRegistryName().m_135815_(), i.spellPart.getBookDescription());
            this.add("ars_nouveau.glyph_name." + i.spellPart.getRegistryName().m_135815_(), i.spellPart.getName());
         }
      }

      for (FamiliarScript i : arsNouveauAPI.getFamiliarScriptMap().values()) {
         if (i.familiar.getRegistryName().m_135827_().equals("ars_nouveau")) {
            this.add("ars_nouveau.familiar_desc." + i.familiar.getRegistryName().m_135815_(), i.familiar.getBookDescription());
            this.add("ars_nouveau.familiar_name." + i.familiar.getRegistryName().m_135815_(), i.familiar.getBookName());
            this.add("item.ars_nouveau." + i.familiar.getRegistryName().m_135815_(), i.familiar.getBookName());
         }
      }

      for (RitualTablet ix : arsNouveauAPI.getRitualItemMap().values()) {
         if (ix.ritual.getRegistryName().m_135827_().equals("ars_nouveau")) {
            this.add("ars_nouveau.ritual_desc." + ix.ritual.getRegistryName().m_135815_(), ix.ritual.getLangDescription());
            this.add("item.ars_nouveau." + ix.ritual.getRegistryName().m_135815_(), ix.ritual.getLangName());
         }
      }

      for (PerkItem ixx : arsNouveauAPI.getPerkItemMap().values()) {
         if (ixx.perk.getRegistryName().m_135827_().equals("ars_nouveau") && !ixx.perk.getRegistryName().m_135815_().equals("blank_thread")) {
            this.add("ars_nouveau.perk_desc." + ixx.perk.getRegistryName().m_135815_(), ixx.perk.getLangDescription());
            this.add("item.ars_nouveau." + ixx.perk.getRegistryName().m_135815_(), ixx.perk.getLangName());
         }
      }

      this.add("key.category.ars_nouveau.general", "Ars Nouveau");
      this.add("key.ars_nouveau.previous_slot", "(Spell Book) Previous Slot");
      this.add("key.ars_nouveau.next_slot", "(Spell Book) Next Slot");
      this.add("key.ars_nouveau.open_book", "(Spell Book) Open Book");
      this.add("key.ars_nouveau.selection_hud", "Toggle Selection HUD");
      this.add("itemGroup.ars_nouveau", "Ars Nouveau");
      this.add("itemGroup.ars_glyphs", "Ars Nouveau Glyphs");
      this.add("item.ars_nouveau.novice_spell_book", "Novice Spell Book");
      this.add("item.ars_nouveau.apprentice_spell_book", "Mage's Spell Book");
      this.add("item.ars_nouveau.archmage_spell_book", "Archmage Spell Book");
      this.add("enchantment.ars_nouveau.mana_regen.desc", "Increases the mana regeneration of the player.");
      this.add("enchantment.ars_nouveau.mana_boost.desc", "Increases the maximum mana of the player.");
      this.add("enchantment.ars_nouveau.reactive.desc", "Has a chance to cast the inscribed spell on tool use or player hurt.");
      this.add("attribute.name.ars_nouveau.mana_regen", "Mana Regeneration");
      this.add("attribute.name.ars_nouveau.max_mana", "Max Mana");
      this.add("entity.ars_nouveau.bookwyrm", "Bookwyrm");
      this.add("entity.ars_nouveau.starbuncle", "Starbuncle");
      this.add("item.ars_nouveau.archmage_robes", "Battlemage's Gambeson");
      this.add("item.ars_nouveau.archmage_leggings", "Battlemage's Legguards");
      this.add("item.ars_nouveau.archmage_boots", "Battlemage's Boots");
      this.add("item.ars_nouveau.archmage_hood", "Battlemage's Hood");
      this.add("item.ars_nouveau.novice_boots", "Sorceror's Footpads");
      this.add("item.ars_nouveau.novice_leggings", "Sorceror's Leggings");
      this.add("item.ars_nouveau.novice_robes", "Sorceror's Wrap");
      this.add("item.ars_nouveau.novice_hood", "Sorceror's Collar");
      this.add("item.ars_nouveau.apprentice_robes", "Arcanist's Robes");
      this.add("item.ars_nouveau.apprentice_leggings", "Arcanist's Pants");
      this.add("item.ars_nouveau.apprentice_boots", "Arcanist's Treads");
      this.add("item.ars_nouveau.apprentice_hood", "Arcanist's Hat");
      this.add("item.ars_nouveau.ring_of_amplify", "Ring of Amplify");
      this.add("item.ars_nouveau.lesser_discount_ring", "Ring of Lesser Discount");
      this.add("item.ars_nouveau.greater_discount_ring", "Ring of Greater Discount");
      this.add("item.ars_nouveau.belt_of_levitation", "Belt of Levitation");
      this.add("item.ars_nouveau.belt_of_unstable_gifts", "Belt of Unstable Gifts");
      this.add("item.ars_nouveau.mundane_belt", "Mundane Belt");
      this.add("item.ars_nouveau.ring_of_potential", "Ring of Potential");
      this.add("item.ars_nouveau.jar_of_light", "Jar of Light");
      this.add("item.ars_nouveau.bookwyrm_charm", "Bookwyrm Charm");
      this.add("item.ars_nouveau.starbuncle_charm", "Starbuncle Charm");
      this.add("item.ars_nouveau.starbuncle_shards", "Starbuncle Shards");
      this.add("item.ars_nouveau.creative_spell_book", "Creative Spell Book");
      this.add("item.ars_nouveau.dull_trinket", "Dull Trinket");
      this.add("item.ars_nouveau.amulet_of_mana_boost", "Amulet of Mana Boost");
      this.add("item.ars_nouveau.amulet_of_mana_regen", "Amulet of Mana Regen");
      this.add("item.ars_nouveau.dominion_wand", "Dominion Wand");
      this.add("block.ars_nouveau.scribes_table", "Scribe's Table");
      this.add("block.ars_nouveau.light_block", "Magelight");
      this.add("block.ars_nouveau.temporary_light_block", "Temporary Magelight");
      this.add("block.ars_nouveau.mage_block", "Mage Block");
      this.add("block.ars_nouveau.agronomic_sourcelink", "Agronomic Sourcelink");
      this.add("block.ars_nouveau.source_jar", "Source Jar");
      this.add("block.ars_nouveau.rune", "Rune");
      this.add("block.ars_nouveau.warding_stone", "Warding Stone");
      this.add("block.ars_nouveau.arcane_pedestal", "Arcane Pedestal");
      this.add("block.ars_nouveau.enchanting_apparatus", "Enchanting Apparatus");
      this.add("block.ars_nouveau.portal", "Warp Portal");
      this.add("block.ars_nouveau.arcane_core", "Arcane Core");
      this.add("block.ars_nouveau.imbuement_chamber", "Imbuement Chamber");
      this.add("ars_nouveau.page.jar_of_light", "Summons a light that will follow the user as they move. Can be summoned and dismissed at any time.");
      this.add("ars_nouveau.page.amulet_of_mana_boost", "Increases max mana by a moderate amount.");
      this.add("ars_nouveau.page.amulet_of_mana_regen", "Increases mana regeneration by a moderate amount.");
      this.add(
         "ars_nouveau.page.magebloom_crop",
         "A magically infused flower, Mageblooms provide additional source to nearby Agronomic Sourcelinks as they grow and provide a source of Magebloom Fiber. Mageblooms can also be used in crafting Potions of Spell Damage, increasing the damage of your spells."
      );
      this.add(
         "ars_nouveau.page.belt_of_levitation",
         "A belt that allows the user levitate a moderate distance above the ground. Useful for climbing mountains! Simply sneak in the air while falling (or jumping) to rise. Reduces a small amount of fall damage while worn."
      );
      this.add(
         "ars_nouveau.page.ring_of_lesser_discount",
         "In addition to providing a small bonus to maximum mana and mana regen, Rings of Discount reduce the total cost to cast a spell."
      );
      this.add("ars_nouveau.page.ring_of_greater_discount", "Provides a slightly larger discount over the Lesser Ring of Discount.");
      this.add(
         "ars_nouveau.page.belt_of_unstable_gifts",
         "Occasionally grants a random positive potion effect for a short duration. These effects can vary in strength."
      );
      this.add(
         "ars_nouveau.page.reactive",
         "Reactive is an enchantment that can be applied to ANY item and can be applied and upgraded by the Enchanting Apparatus. Tools and armor with the Reactive enchantment have a chance to automatically cast a spell on use or when the player is hurt. The spell that the enchantment will cast is dependent on the spell inscribed in the first crafting phase, and the user must have enough mana to cast the spell."
      );
      this.add(
         "ars_nouveau.page.reactive2",
         "To enchant an item with Reactive, place any item or armor in the apparatus. The spell parchment placed on the pedestal must be inscribed with a valid spell. See the Scribes Table for additional information. Reactive Enchantment also requires jars of source near the Enchanting Apparatus. The first level enchantment requires approximately one third of a jar."
      );
      this.add(
         "ars_nouveau.page.reactive3",
         "To upgrade the enchantment, replace the reagent with any item that has an existing Reactive enchantment. The Tier 2 reactive enchantment requires an item with Reactive I, and the Tier 3 Reactive enchantment requires an item with Reactive II. Higher levels will require a significantly higher amount of source nearby."
      );
      this.add(
         "ars_nouveau.page.reactive4",
         "The spell that Reactive gear casts can be changed by placing an enchanted piece of gear into the apparatus with a new inscribed spell parchment. "
      );
      this.add("enchantment.ars_nouveau.mana_boost", "Mana Boost");
      this.add("enchantment.ars_nouveau.reactive", "Reactive");
      this.add("enchantment.ars_nouveau.mana_regen", "Mana Regen");
      this.add("item.ars_nouveau.bucket_of_source", "Source Bucket");
      this.add("item.ars_nouveau.magic_clay", "Magic Clay");
      this.add("item.ars_nouveau.marvelous_clay", "Marvelous Clay");
      this.add("item.ars_nouveau.mythical_clay", "Mythical Clay");
      this.add("item.ars_nouveau.runic_chalk", "Runic Chalk");
      this.add("item.ars_nouveau.magebloom", "Magebloom");
      this.add("item.ars_nouveau.magebloom_fiber", "Magebloom Fiber");
      this.add("item.ars_nouveau.blaze_fiber", "Blaze Fiber");
      this.add("item.ars_nouveau.end_fiber", "End Fiber");
      this.add("block.ars_nouveau.magebloom_crop", "Magebloom Seed");
      this.add("item.ars_nouveau.source_gem", "Source Gem");
      this.add("item.ars_nouveau.blank_glyph", "Blank Glyph");
      this.add("item.ars_nouveau.worn_notebook", "Worn Notebook");
      this.add("item.ars_nouveau.warp_scroll", "Warp Scroll");
      this.add("item.ars_nouveau.spell_parchment", "Spell Parchment");
      this.add("item.ars_nouveau.ring_of_greater_discount", "Ring of Greater Discount");
      this.add("item.ars_nouveau.ring_of_lesser_discount", "Ring of Lesser Discount");
      this.add("effect.ars_nouveau.shield", "Magic Shield");
      this.add("alert.core", "An Arcane Core must be placed beneath this block.");
      this.add("whirlisprig.unhappy", "The Whirlisprig seems very unhappy in her home. Try sprucing it up!");
      this.add("whirlisprig.content", "The Whirlisprig seems content, but could be better.");
      this.add("whirlisprig.happy", "The Whirlisprig appears happy enough.");
      this.add("whirlisprig.very_happy", "The Whirlisprig is very happy!");
      this.add("whirlisprig.extremely_happy", "The Whirlisprig is extremely happy! It doesn't seem like it wants anything else.");
      this.add("whirlisprig.okay_diversity", "There does not appear to be much diversity.");
      this.add("whirlisprig.diverse_enough", "There is some diversity, but could be better.");
      this.add("whirlisprig.very_diverse", "The Whirlisprig thinks her home is very diverse.");
      this.add("whirlisprig.extremely_diverse", "The home is extremely diverse, but Whirlisprig will never turn down a new addition to their home!");
      this.add("whirlisprig.notinterested", "The Whirlisprig does not seem interested in this item.");
      this.add("whirlisprig.likes", "The Whirlisprig likes this block!");
      this.add("whirlisprig.excited", "The Whirlisprig would be excited to have this in their home!");
      this.add("whirlisprig.toomuch", "The Whirlisprig likes this item, but has enough of it already.");
      this.add("item.ars_nouveau.whirlisprig_charm", "Whirlisprig Charm");
      this.add("item.ars_nouveau.whirlisprig_shards", "Whirlisprig Shards");
      this.add("entity.ars_nouveau.whirlisprig", "Whirlisprig");
      this.add(
         "ars_nouveau.page.crystallizer1",
         "The crystallizer provides a renewable source of Source Gems by condensing source over time. The Crystallizer can work passively at a very slow rate, or it may be given source from adjacent jars and create Source Gems as a much faster rate. The Crystallizer may also be accessed by Hoppers and Starbuncles."
      );
      this.add("tooltip.source_gem", "Obtained by placing lapis or amethyst in an Imbuement Chamber.");
      this.add("tooltip.magebloom", "Crafted using the Enchanting Apparatus");
      this.add("ars_nouveau.alert.turret_type", "Selected form cannot be used by a turret.");
      this.add("ars_nouveau.alert.spell_set", "Spell set.");
      this.add("ars_nouveau.alert.duplicate_method", "No duplicate cast methods are allowed.");
      this.add("ars_nouveau.relay.no_to", "No send location set.");
      this.add("ars_nouveau.relay.one_to", "Sending to %d location(s).");
      this.add("ars_nouveau.relay.no_from", "No take location set.");
      this.add("ars_nouveau.relay.one_from", "Taking from %d location(s).");
      this.add("ars_nouveau.rune.touch", "Runes must have Touch as their first glyph.");
      this.add("ars_nouveau.bookwyrm.strict_mode", "Strict mode set to %s");
      this.add("item.ars_nouveau.allow_scroll", "Item Scroll: Allow");
      this.add("item.ars_nouveau.deny_scroll", "Item Scroll: Deny");
      this.add("item.ars_nouveau.wixie_shards", "Wixie Shards");
      this.add("item.ars_nouveau.wixie_charm", "Wixie Charm");
      this.add("block.ars_nouveau.wixie_cauldron", "Wixie Cauldron");
      this.add("ars_nouveau.rune.error", "Rune threw an error. Please report this! Bye rune!");
      this.add("item.ars_nouveau.blank_parchment", "Blank Parchment");
      this.add("ars_nouveau.scribe.item_added", "Item added.");
      this.add("ars_nouveau.scribe.item_removed", "Item removed.");
      this.add("ars_nouveau.allow_set", "Starbuncle will only take these items.");
      this.add("ars_nouveau.ignore_set", "Starbuncle will ignore these items.");
      this.add("block.ars_nouveau.lava_lily", "Lava Lily");
      this.add("block.ars_nouveau.volcanic_sourcelink", "Volcanic Sourcelink");
      this.add("ars_nouveau.spell.disabled", "This spell has been disabled in the config.");
      this.add("block.ars_nouveau.sourceberry_bush", "Sourceberry");
      this.add("ars_nouveau.connections.cleared", "Connections cleared.");
      this.add("ars_nouveau.connections.take", "Relay set to take from %s");
      this.add("ars_nouveau.connections.send", "Relay set to send to %s");
      this.add("item.minecraft.splash_potion.effect.mana_regen_potion_strong", "Splash Potion of Mana Regeneration");
      this.add("item.minecraft.splash_potion.effect.mana_regen_potion", "Splash Potion of Mana Regeneration");
      this.add("item.minecraft.splash_potion.effect.mana_regen_potion_long", "Splash Potion of Mana Regeneration");
      this.add("item.minecraft.potion.effect.mana_regen_potion_strong", "Potion of Mana Regeneration");
      this.add("item.minecraft.potion.effect.mana_regen_potion", "Potion of Mana Regeneration");
      this.add("item.minecraft.potion.effect.mana_regen_potion_long", "Potion of Mana Regeneration");
      this.add("item.minecraft.lingering_potion.effect.mana_regen_potion_strong", "Lingering Potion of Mana Regeneration");
      this.add("item.minecraft.lingering_potion.effect.mana_regen_potion", "Lingering Potion of Mana Regeneration");
      this.add("item.minecraft.lingering_potion.effect.mana_regen_potion_long", "Lingering Potion of Mana Regeneration");
      this.add("item.minecraft.tipped_arrow.effect.mana_regen_potion_strong", "Arrow of Mana Regeneration");
      this.add("item.minecraft.tipped_arrow.effect.mana_regen_potion", "Arrow of Mana Regeneration");
      this.add("item.minecraft.tipped_arrow.effect.mana_regen_potion_long", "Arrow of Mana Regeneration");
      this.add("effect.ars_nouveau.mana_regen", "Mana Regeneration");
      this.add("ars_nouveau.on", "Active: Yes");
      this.add("ars_nouveau.off", "Active: No");
      this.add("item.ars_nouveau.void_jar", "Jar of Voiding");
      this.add("ars_nouveau.rune.setperm", "This rune is now permanent.");
      this.add(
         "ars_nouveau.page.void_jar",
         "A jar that can destroy items on pickup and grants a small amount of mana in return. To turn the jar on and off, use the jar while sneaking. To add or remove an item to be destroyed by the jar, use the jar with an item in the off hand, or use an item on the Scribes Table with the jar placed on it. The jar must be in your hotbar to function."
      );
      this.add(
         "ars_nouveau.page.runic_chalk",
         "Runic chalk can be used to place permanent Runes on the ground that will cast spells on entities that walk over them. To give a rune a spell, inscribe spell parchment using the scribes table. Once the rune has cast the spell, it will become uncharged. An uncharged rune will charge itself from nearby source jars. Using Runic Chalk on a temporary rune will convert it to a permanent one."
      );
      this.add("ars_nouveau.wand.invalid", "Invalid spell. Wands accept Effects and Augments only.");
      this.add("item.ars_nouveau.wand", "Enchanter's Wand");
      this.add("ars_nouveau.wixie.has_wixie", "This cauldron already has a wixie.");
      this.add("ars_nouveau.wixie.no_recipe", "No recipes found.");
      this.add("ars_nouveau.wixie.recipe_set", "Recipe set.");
      this.add("tooltip.wixie_shard", "Obtained by using Dispel on a witch while it is less than half health.");
      this.add("tooltip.starbuncle_shard", "Obtained by giving a wild Starbuncle a Gold Nugget.");
      this.add("tooltip.whirlisprig_shard", "Obtained by growing a tree near a wild Whirlisprig.");
      this.add("entity.ars_nouveau.wixie", "Wixie");
      this.add("block.ars_nouveau.creative_source_jar", "Creative Source Jar");
      this.add("block.ars_nouveau.purple_archwood_log", "Vexing Archwood Log");
      this.add("block.ars_nouveau.blue_archwood_log", "Cascading Archwood Log");
      this.add("block.ars_nouveau.green_archwood_log", "Flourishing Archwood Log");
      this.add("block.ars_nouveau.red_archwood_log", "Blazing Archwood Log");
      this.add("block.ars_nouveau.purple_archwood_sapling", "Vexing Archwood Sapling");
      this.add("block.ars_nouveau.blue_archwood_sapling", "Cascading Archwood Sapling");
      this.add("block.ars_nouveau.green_archwood_sapling", "Flourishing Archwood Sapling");
      this.add("block.ars_nouveau.red_archwood_sapling", "Blazing Archwood Sapling");
      this.add("block.ars_nouveau.purple_archwood_leaves", "Vexing Archwood Leaves");
      this.add("block.ars_nouveau.blue_archwood_leaves", "Cascading Archwood Leaves");
      this.add("block.ars_nouveau.green_archwood_leaves", "Flourishing Archwood Leaves");
      this.add("block.ars_nouveau.red_archwood_leaves", "Blazing Archwood Leaves");
      this.add("block.ars_nouveau.archwood_planks", "Archwood Planks");
      this.add(
         "ars_nouveau.page.wand",
         "Wands accept only a single spell, and are inscribed using the Scribes Table. A Wand always starts with Projectile -> Accelerate, and MUST be inscribed with a spell that does not have another method. This allows you to cast spells beyond the 10 spell cap. If you want a wand that casts Break, inscribe the wand with JUST break, and your result will be a wand with Projectile -> Acclerate -> Break."
      );
      this.add("ars_nouveau.starbuncle.cleared", "Tasks cleared.");
      this.add("block.ars_nouveau.red_archwood_wood", "Blazing Archwood Wood");
      this.add("block.ars_nouveau.green_archwood_wood", "Flourishing Archwood Wood");
      this.add("block.ars_nouveau.purple_archwood_wood", "Vexing Archwood Wood");
      this.add("block.ars_nouveau.blue_archwood_wood", "Cascading Archwood Wood");
      this.add("ars_nouveau.connections.fail", "Too far away.");
      this.add("block.ars_nouveau.archwood_slab", "Archwood Slab");
      this.add("block.ars_nouveau.archwood_fence", "Archwood Fence");
      this.add("block.ars_nouveau.archwood_fence_gate", "Archwood Fence Gate");
      this.add("block.ars_nouveau.archwood_trapdoor", "Archwood Trapdoor");
      this.add("block.ars_nouveau.archwood_pressure_plate", "Archwood Pressure Plate");
      this.add("block.ars_nouveau.archwood_door", "Archwood Door");
      this.add("block.ars_nouveau.stripped_blue_archwood_log", "Stripped Cascading Archwood Log");
      this.add("block.ars_nouveau.stripped_blue_archwood_wood", "Stripped Cascading Archwood Wood");
      this.add("block.ars_nouveau.stripped_red_archwood_log", "Stripped Blazing Archwood Log");
      this.add("block.ars_nouveau.stripped_red_archwood_wood", "Stripped Blazing Archwood Wood");
      this.add("block.ars_nouveau.stripped_green_archwood_log", "Stripped Flourishing Archwood Log");
      this.add("block.ars_nouveau.stripped_green_archwood_wood", "Stripped Flourishing Archwood Wood");
      this.add("block.ars_nouveau.stripped_purple_archwood_log", "Stripped Vexing Archwood Log");
      this.add("block.ars_nouveau.stripped_purple_archwood_wood", "Stripped Vexing Archwood Wood");
      this.add("block.ars_nouveau.archwood_stairs", "Archwood Stairs");
      this.add("block.ars_nouveau.archwood_button", "Archwood Button");
      this.add("block.ars_nouveau.source_gem_block", "Source Gem Block");
      this.add("item.ars_nouveau.spell_bow", "Enchanter's Bow");
      this.add("item.ars_nouveau.split_arrow", "Augment Arrow: Split");
      this.add("item.ars_nouveau.amplify_arrow", "Augment Arrow: Amplify");
      this.add("item.ars_nouveau.pierce_arrow", "Augment Arrow: Pierce");
      this.add(
         "ars_nouveau.page1.spell_bow",
         "A bow that can be inscribed with a spell using the Scribes Table. If the player has enough mana, arrows will become Spell Arrows and will apply the spell on their target. If no arrows are in the inventory, a spell arrow that deals 0 damage will be cast. If there is not enough mana, regular arrows will be fired. Enchanter's Bows may use special Augment Arrows for empowering the inscribed spell."
      );
      this.add("ars_nouveau.page.amplify_arrow", "Adds two amplifies to the end of the inscribed spell when used. Each recipe makes 32.");
      this.add("ars_nouveau.page.pierce_arrow", "Adds two pierce to the beginning of the chain. Each recipe makes 32.");
      this.add(
         "ars_nouveau.page.split_arrow",
         "Adds two split to the beginning of the chain. If a spell arrow will deal damage, each split arrow will as well. Each recipe makes 32."
      );
      this.add("ars_nouveau.bow.invalid", "This tool may only accept spells that do not contain a form.");
      this.add("ars_nouveau.norecipe", "No recipe found.");
      this.add("ars_nouveau.apparatus.nomana", "Not enough source nearby.");
      this.add("ars_nouveau.reset.cleared", "Progress cleared.");
      this.add("effect.ars_nouveau.summoning_sickness", "Summoning Sickness");
      this.add("entity.ars_nouveau.wilden_guardian", "Wilden Defender");
      this.add("entity.ars_nouveau.wilden_stalker", "Wilden Stalker");
      this.add("entity.ars_nouveau.wilden_hunter", "Wilden Hunter");
      this.add("item.ars_nouveau.wilden_spike", "Wilden Spike");
      this.add("item.ars_nouveau.wilden_horn", "Wilden Horn");
      this.add("item.ars_nouveau.wilden_wing", "Wilden Wing");
      this.add("tooltip.wilden_spike", "Drops from Wilden Defenders, found in Wilden Dens in cold biomes, or by using a Tablet of Summon Wilden.");
      this.add("tooltip.wilden_wing", "Drops from Wilden Stalkers found in Wilden Dens, or by using a Tablet of Summon Wilden.");
      this.add("tooltip.wilden_horn", "Drops from Wilden Pack Hunters found in Wilden Dens, or by using a Tablet of Summon Wilden.");
      this.add("entity.ars_nouveau.summon_wolf", "Summoned Wolf");
      this.add("tooltip.worn_notebook", "Documentation for Ars Nouveau");
      this.add("effect.ars_nouveau.shocked", "Shocked");
      this.add("ars_nouveau.flask.charges", "Charges: %d");
      this.add("item.ars_nouveau.potion_flask", "Potion Flask");
      this.add("ars_nouveau.glyph_of", "Glyph of %s");
      this.add("ars_nouveau.thread_of", "Thread of %s");
      this.add("ars_nouveau.book.name", "Worn Notebook");
      this.add(
         "ars_nouveau.book.landing_text",
         "Ars Nouveau provides spell crafting, magical devices, powerful trinkets, and magical entity automation. To help  development or report issues, join the community: https://discord.gg/y7TMXZu Thank you for playing."
      );
      this.add("ars_nouveau.spell_tier.1", "ONE");
      this.add("ars_nouveau.spell_tier.2", "TWO");
      this.add("ars_nouveau.spell_tier.3", "THREE");
      this.add("ars_nouveau.mana_cost.none", "None");
      this.add("ars_nouveau.mana_cost.low", "Low");
      this.add("ars_nouveau.mana_cost.medium", "Medium");
      this.add("ars_nouveau.mana_cost.high", "High");
      this.add("ars_nouveau.spell_book.copied", "Copied %d new glyphs to the book.");
      this.add("ars_nouveau.spell_book.create_mode", "Create Mode");
      this.add("ars_nouveau.spell_book.select", "Press %s to quick select");
      this.add("ars_nouveau.spell_book.craft", "Press %s to quick craft");
      this.add("ars_nouveau.spell_book_gui.spell_name", "Spell Name");
      this.add("ars_nouveau.spell_book_gui.search", "Search");
      this.add("ars_nouveau.spell_book_gui.form", "Form");
      this.add("ars_nouveau.spell_book_gui.effect", "Effect");
      this.add("ars_nouveau.spell_book_gui.augment", "Augment");
      this.add("ars_nouveau.spell_book_gui.create", "Create");
      this.add("ars_nouveau.spell_book_gui.clear", "Clear");
      this.add("ars_nouveau.color_gui.red_slider", "Red: ");
      this.add("ars_nouveau.color_gui.green_slider", "Green: ");
      this.add("ars_nouveau.color_gui.blue_slider", "Blue: ");
      this.add("ars_nouveau.color_gui.title", "Spell Color");
      this.add("ars_nouveau.color_gui.presets", "Presets");
      this.add("ars_nouveau.color_gui.default", "Default");
      this.add("ars_nouveau.color_gui.purple", "Purple");
      this.add("ars_nouveau.color_gui.blue", "Blue");
      this.add("ars_nouveau.color_gui.red", "Red");
      this.add("ars_nouveau.color_gui.green", "Green");
      this.add("ars_nouveau.color_gui.yellow", "Yellow");
      this.add("ars_nouveau.color_gui.white", "White");
      this.add("ars_nouveau.color_gui.orange", "Orange");
      this.add("ars_nouveau.color_gui.cyan", "Cyan");
      this.add("ars_nouveau.color_gui.save", "Save");
      this.add("ars_nouveau.dominion_wand.stored_entity", "Stored entity");
      this.add("ars_nouveau.dominion_wand.position_set", "Position set.");
      this.add("ars_nouveau.dominion_wand.no_entity", "No entity set");
      this.add("ars_nouveau.dominion_wand.entity_stored", "Entity stored.");
      this.add("ars_nouveau.dominion_wand.no_location", "No location set.");
      this.add("ars_nouveau.dominion_wand.position_stored", "Stored: %s");
      this.add("ars_nouveau.position", "X: %1$d Y: %2$d Z: %3$d");
      this.add("ars_nouveau.spell_arrow.desc", "Augments spells when used with an Enchanter's Bow.");
      this.add("ars_nouveau.spell_parchment.no_spell", "Set your spell book to a spell.");
      this.add("ars_nouveau.spell_parchment.inscribed", "Spell inscribed.");
      this.add("ars_nouveau.warp_scroll.wrong_dim", "Using this scroll from a different dimension would be a bad idea.");
      this.add("ars_nouveau.warp_scroll.inv_full", "There is no room in your inventory.");
      this.add("ars_nouveau.warp_scroll.recorded", "You record your location to your Warp Scroll.");
      this.add("ars_nouveau.warp_scroll.no_location", "Use while sneaking to set a location.");
      this.add("ars_nouveau.wand.spell_invalid", "Invalid Spell.");
      this.add("ars_nouveau.drygmy.blacklist", "Drygmy Blacklisted");
      this.add("ars_nouveau.starbuncle.store", "Starbuncle will store items here.");
      this.add("ars_nouveau.starbuncle.take", "Starbuncle take from this inventory.");
      this.add("ars_nouveau.starbuncle.whitelist", "Whitelisted: ");
      this.add("ars_nouveau.starbuncle.blacklist", "Ignoring: ");
      this.add("ars_nouveau.starbuncle.storing", "Storing items at %d locations");
      this.add("ars_nouveau.starbuncle.taking", "Taking items from %d locations");
      this.add("ars_nouveau.whirlisprig.ignore", "Whirlisprig will ignore these items");
      this.add("ars_nouveau.whirlisprig.ignore_list", "Ignoring: ");
      this.add("ars_nouveau.whirlisprig.tooltip_extremely_happy", "Extremely happy");
      this.add("ars_nouveau.whirlisprig.tooltip_very_happy", "Very happy");
      this.add("ars_nouveau.whirlisprig.tooltip_happy", "Happy");
      this.add("ars_nouveau.whirlisprig.tooltip_content", "Content");
      this.add("ars_nouveau.whirlisprig.tooltip_unhappy", "Very unhappy");
      this.add("ars_nouveau.whirlisprig.tooltip_mood", "Mood: ");
      this.add("ars_nouveau.source_jar.fullness", "%d%% full");
      this.add("ars_nouveau.spell_turret.casting", "Casting: ");
      this.add("ars_nouveau.wixie.crafting", "Crafting: ");
      this.add("ars_nouveau.wixie.need_mana", "Source needed.");
      this.add("ars_nouveau.wixie.needs", "Needs: ");
      this.add("ars_nouveau.wixie.needs_storage", "Needs: Storage for potion.");
      this.add("ars_nouveau.true", "true");
      this.add("ars_nouveau.false", "false");
      this.add("ars_nouveau.spell_hud.crafting_mode", "Crafting Mode");
      this.add("ars_nouveau.spell.validation.crafting.invalid", "There are problems with this spell.");
      this.add("ars_nouveau.spell.validation.crafting.invalid_glyphs", "One or more glyphs in this spell are invalid.");
      this.add("ars_nouveau.spell.validation.exists._comment", "__ These messages appear when reporting on an existing spell that is invalid. __");
      this.add("ars_nouveau.spell.validation.exists.non_empty_spell", "The spell may not be empty.");
      this.add("ars_nouveau.spell.validation.exists.max_one_cast_method", "The spell has an extra form glyph: %s");
      this.add("ars_nouveau.spell.validation.exists.starting_cast_method", "The spell does not start with a form glyph.");
      this.add("ars_nouveau.spell.validation.exists.action_augmentation_policy", "%s was augmented by %s too many times.");
      this.add("ars_nouveau.spell.validation.exists.action_augmentation_policy.zero", "%s may not be augmented by %s.");
      this.add("ars_nouveau.spell.validation.exists.glyph_occurrences_policy", "%s appears too many times.");
      this.add("ars_nouveau.spell.validation.exists.augment_compatibility", "%s cannot be augmented by %s");
      this.add("ars_nouveau.spell.validation.exists.glyph_tier", "%s is too powerful for your current spell book.");
      this.add("ars_nouveau.spell.validation.exists.invalid_combination_policy", "%s cannot be used with %s");
      this.add("ars_nouveau.spell.validation.adding._comment", "__ These messages appear when attempting to add a glyph that would make a spell invalid. __");
      this.add("ars_nouveau.spell.validation.adding.non_empty_spell", "The spell may not be empty.");
      this.add("ars_nouveau.spell.validation.adding.max_one_cast_method", "The spell already has a form glyph.");
      this.add("ars_nouveau.spell.validation.adding.starting_cast_method", "The spell must start with a form glyph.");
      this.add("ars_nouveau.spell.validation.adding.action_augmentation_policy", "%s is already augmented to the limit by %s.");
      this.add("ars_nouveau.spell.validation.adding.action_augmentation_policy.zero", "%s may not be augmented by %s.");
      this.add("ars_nouveau.spell.validation.adding.glyph_occurrences_policy", "%s has appeared its maximum number of times.");
      this.add("ars_nouveau.spell.validation.adding.augment_compatibility", "%s cannot be augmented by %s");
      this.add("ars_nouveau.spell.validation.adding.invalid_combination_policy", "%s cannot be used with %s");
      this.add("ars_nouveau.spell.validation.adding.glyph_tier", "%s is too powerful for your current spell book.");
      this.add("ars_nouveau.spell.no_mana", "Not enough Mana.");
      this.add("block.ars_nouveau.potion_jar", "Potion Jar");
      this.add("block.ars_nouveau.potion_melder", "Potion Melder");
      this.add("item.ars_nouveau.potion_flask_extend_time", "Enchanted Potion Flask: Extend Time");
      this.add("item.ars_nouveau.potion_flask_amplify", "Enchanted Potion Flask: Amplify");
      this.add("tooltip.potion_flask_extend_time", "Increases the duration of potions by 50%.");
      this.add("tooltip.potion_flask_amplify", "Increases the level of potions, but reduces their duration.");
      this.add("tooltip.potion_flask", "Holds 8 charges of potion.");
      this.add("tooltip.discount_item", "Reduce the cost of all spells by %d.");
      this.add("ars_nouveau.locked", "Locked");
      this.add("ars_nouveau.unlocked", "Unlocked");
      this.add(
         "ars_nouveau.page5.wixie_charm",
         "Wixies will autocraft potions using nearby Potion Jars and items. Potions that require Water will be supplied by the Wixie. Potions that require another potion as a base will be taken from nearby Potion Jars. A Wixie will output 3 doses of a Potion into a nearby Potion Jar when complete. To begin, place down an empty Potion Jar, right click the cauldron with an Awkward Potion, and supply Nether Wart from a nearby chest."
      );
      this.add(
         "ars_nouveau.page6.wixie_charm",
         "When using multiple Wixies, you may wish to lock a jar to a specific potion. Wixies will only output potion to jars that contain the same potion, or an unlocked empty potion. Each craft costs Source. See the section on the Potion Jar for more information."
      );
      this.add("entity.ars_nouveau.summon_horse", "Summoned Horse");
      this.add("block.ars_nouveau.sconce", "Magelight Sconce");
      this.add("block.ars_nouveau.ritual_brazier", "Ritual Brazier");
      this.add("ars_nouveau.tooltip.consumed", "Items Consumed:");
      this.add("ars_nouveau.tooltip.waiting", "Awaiting Activation");
      this.add("ars_nouveau.tooltip.running", "Running");
      this.add("ars_nouveau.tooltip.conditions_unmet", "Conditions Unmet");
      this.add("ars_nouveau.tooltip.exp_gem", "Grants experience on use. Sneak to consume the entire stack.");
      this.add("item.ars_nouveau.experience_gem", "Experience Gem");
      this.add("item.ars_nouveau.greater_experience_gem", "Greater Experience Gem");
      this.add("effect.ars_nouveau.hex", "Hex");
      this.add("ars_nouveau.set_spell", "Set spell.");
      this.add("ars_nouveau.invalid_spell", "Invalid Spell");
      this.add("effect.ars_nouveau.scrying", "Scrying");
      this.add("ars_nouveau.tooltip.magebloom", "Grown from seeds crafted using the Enchanting Apparatus.");
      this.add("ars_nouveau.tooltip.dull", "A dull trinket, cannot be worn. Used for crafting.");
      this.add("ars_nouveau.sword.invalid", "Invalid spell. Swords accept Effects and Augments only, such as Freeze -> Extend Time.");
      this.add("item.ars_nouveau.enchanters_sword", "Enchanter's Sword");
      this.add("item.ars_nouveau.enchanters_shield", "Enchanter's Shield");
      this.add(
         "ars_nouveau.page.enchanters_sword",
         "Applies a Touch spell before damaging an entity. Additionally, all spells gain one additional Amplify augment on the last effect in the spell. Apply a spell at the Scribes Table that does NOT contain a form, such as Ignite -> Extend Time."
      );
      this.add(
         "ars_nouveau.page.enchanters_shield",
         "Upon blocking damage, the user will gain a short duration of Mana Regeneration and Spell Damage. Additionally, this shield will repair over time using the wearers mana."
      );
      this.add("ars_nouveau.tooltip.can_inscribe", "Can be inscribed with a spell at the Scribes Table.");
      this.add("biome.ars_nouveau.archwood_forest", "Archwood Forest");
      this.add("ars_nouveau.tooltip.turned_off", "Turned off");
      this.add("effect.ars_nouveau.glide", "Glide");
      this.add("effect.ars_nouveau.snared", "Snared");
      this.add("item.ars_nouveau.starbuncle_se", "Starbuncle Spawn Egg");
      this.add("item.ars_nouveau.drygmy_se", "Drygmy Spawn Egg");
      this.add("item.ars_nouveau.whirlisprig_se", "Whirlisprig Spawn Egg");
      this.add("item.ars_nouveau.wilden_hunter_se", "Wilden Hunter Spawn Egg");
      this.add("item.ars_nouveau.wilden_guardian_se", "Wilden Guardian Spawn Egg");
      this.add("item.ars_nouveau.wilden_stalker_se", "Wilden Stalker Spawn Egg");
      this.add("tooltip.ars_nouveau.caster_tome", "Casts a spell at half the cost or the users entire mana bar, whichever is smaller.");
      this.add("item.ars_nouveau.caster_tome", "Caster Tome");
      this.add("tooltip.ars_nouveau.glyph_disabled", "Disabled. Cannot be used.");
      this.add("tooltip.ars_nouveau.glyph_level", "A tier %s glyph.");
      this.add("tooltip.ars_nouveau.caster_level", "Can cast tier %s glyphs or lower.");
      this.add("tooltip.ars_nouveau.hold_shift", "Hold %s for more info.");
      this.add("item.ars_nouveau.drygmy_shard", "Drygmy Shards");
      this.add("item.ars_nouveau.drygmy_charm", "Drygmy Charm");
      this.add("block.ars_nouveau.drygmy_stone", "Drygmy Henge");
      this.add("tooltip.ars_nouveau.drygmy_shard", "Obtained by giving a Drygmy a Wilden Horn.");
      this.add("entity.ars_nouveau.drygmy", "Drygmy");
      this.add("effect.ars_nouveau.flight", "Flight");
      this.add("effect.ars_nouveau.gravity", "Gravity");
      this.add("tooltip.ars_nouveau.tablet", "A tablet used for rituals. Consumed on use.");
      this.add("ars_nouveau.enchanting.bad_level", "This item must have the previous level of enchantment.");
      this.add("ars_nouveau.enchanting.incompatible", "This enchantment is incompatible with this item.");
      this.add("entity.ars_nouveau.wilden_boss", "Wilden Chimera");
      this.add("ars_nouveau.chimera.rage", "The chimera begins raging.");
      this.add("entity.ars_nouveau.spike", "Chimera Spike");
      this.add("tooltip.ars_nouveau.wilden_tribute", "Obtained by defeating the Wilden Chimera. See Ritual of Summon Wilden.");
      this.add("item.ars_nouveau.wilden_tribute", "Wilden Tribute");
      this.add("item.ars_nouveau.mimic_scroll", "Mimic Scroll");
      this.add("tooltip.ars_nouveau.spell_damage", "Spell Damage: %s");
      this.add("tooltip.ars_nouveau.duration_modifier", "Duration Modifier: %s");
      this.add("tooltip.ars_nouveau.amp_modifier", "Amplification Modifier: %s");
      this.add("item.ars_nouveau.summon_focus", "Focus of Summoning");
      this.add("tooltip.ars_nouveau.summon_focus", "Increases the duration and power of your summons.");
      this.add("curios.identifier.an_focus", "Spell Focus");
      this.add("ars_nouveau.school.manipulation", "Manipulation");
      this.add("ars_nouveau.school.abjuration", "Abjuration");
      this.add("ars_nouveau.school.conjuration", "Conjuration");
      this.add("ars_nouveau.school.air", "Elemental Air");
      this.add("ars_nouveau.school.earth", "Elemental Earth");
      this.add("ars_nouveau.school.fire", "Elemental Fire");
      this.add("ars_nouveau.school.water", "Elemental Water");
      this.add("ars_nouveau.spell_set", "Spell set.");
      this.add("block.ars_nouveau.vitalic_sourcelink", "Vitalic Sourcelink");
      this.add("block.ars_nouveau.alchemical_sourcelink", "Alchemical Sourcelink");
      this.add(
         "ars_nouveau.page1.armor",
         "Magical robes will increase the wearers mana regen and can be upgraded with special abilities using Threads. The Sorceror's set provides the lowest defence, but provides the most powerful set of slots for Threads. The Arcanist's and Battlemage's sets provide increasingly more defence, but fewer and less powerful Thread Slots. For more information on Threads, see the section on Armor and Perks."
      );
      this.add(
         "ars_nouveau.page.potion_jar",
         "A jar that stores up to 100 potions. Potion can be removed by using an Empty Bottle, Potion Flask, or Arrows on the jar. Wixies will use these jars during Potion Autocrafting. The jar may be locked by using a Dominion Wand while sneaking. Locked Jars will only receive the potion it is locked to from Wixies. Can be used with a Comparator."
      );
      this.add("ars_nouveau.glyph_crafting", "Glyph Crafting");
      this.add(
         "ars_nouveau.page1.scribes_table",
         "To craft new glyphs, use a spell book on the table to open the codex. Each glyph requires a set of items and experience points to unlock. Select a glyph by clicking on it in the menu, and hit select. Throw the items onto the table as rendered above, and the table will scribe a new glyph. The table will also pull items from nearby inventories. Using the dominion wand on the table will disable auto-pull."
      );
      this.add(
         "ars_nouveau.page2.scribes_table",
         "You may also inscribe a spell onto Spell Parchment or Enchanters Items. To do this, place a Blank Parchment on the table. Then, with your spell book in hand, change your spell book to your desired spell as if you were going to cast it. Then, use the book on the table while sneaking. Your item will now contain that spell. Using Manipulation Essence will permanently hide the scribed spell."
      );
      this.add(
         "ars_nouveau.page1.lava_lily",
         "A decorative block that can be placed on any liquid or block. The texture of this block varies if it is placed on Lava, a Magma Block, or any other block."
      );
      this.add(
         "ars_nouveau.page1.wilden",
         "Wilden are hostile creatures that can be commonly found at night around Wilden Dens. While Wilden Defenders may only be found in cold biomes, Stalker and Hunter Dens can be found in any forest biome."
      );
      this.add("ars_nouveau.page3.wilden", "An aggressive and fast hunter that can summon allied wolves.");
      this.add("ars_nouveau.page4.wilden", "Generally spawning in small groups, Stalkers have ground and aerial attacks.");
      this.add("ars_nouveau.page5.wilden", "Found in cold biomes, a Defender is a slow moving heavy hitter with a ranged attack.");
      this.add("ars_nouveau.page6.wilden", "Summoned with a Ritual of Wilden Tribute, the chimera is a powerful and destructive mid-level boss.");
      this.add(
         "ars_nouveau.page7.wilden",
         "The Chimera is a worthy fight for a caster with Tier 2 spells and equipment. The Chimera resists cold damage, and a variety of spells including buffs, dispel, and mobility are suggested."
      );
      this.add(
         "ars_nouveau.page1.allow_scroll",
         "Provides a list of items to automation related entities. To inscribe an item, place on the Scribes Table and use blocks and items on the table while sneaking. When given to a Starbuncle, the Starbuncle will only pickup and take items on the scroll."
      );
      this.add(
         "ars_nouveau.page1.deny_scroll",
         "Provides a list of items to automation related entities. To inscribe an item, place on the Scribes Table and use blocks and items on the table while sneaking. When given to a Starbuncle, the Starbuncle will pickup and take any item that is NOT on this scroll."
      );
      this.add(
         "ars_nouveau.page1.mimic_scroll",
         "Provides a list of items to automation related entities. When this scroll is attached to an inventory, entities will only insert items that already exist in the inventory. To attach the scroll, place an item frame on the inventory, and place a Mimic Scroll in it."
      );
      this.add("block.ars_nouveau.mycelial_sourcelink", "Mycelial Sourcelink");
      this.add("item.ars_nouveau.source_berry_pie", "Source Berry Pie");
      this.add("item.ars_nouveau.source_berry_roll", "Source Berry Roll");
      this.add("tooltip.ars_nouveau.source_food", "Grants mana regeneration when consumed.");
      this.add("block.ars_nouveau.relay_warp", "Source Relay: Warper");
      this.add("block.ars_nouveau.relay_deposit", "Source Relay: Depositor");
      this.add(
         "ars_nouveau.page.relay_deposit",
         "Operates similar to the Source Relay, but will deposit to jars it is not linked to within 5 blocks. See the instructions on the Source Relay for use."
      );
      this.add(
         "ars_nouveau.page.relay_warp",
         "Operates similar to the Source Relay: Splitter but can teleport source an endless distance between other Warp relays. For distances beyond 30 blocks, there is a chance that some source will be lost during warp."
      );
      this.add("item.minecraft.splash_potion.effect.spell_damage_potion_strong", "Splash Potion of Spell Damage");
      this.add("item.minecraft.splash_potion.effect.spell_damage_potion", "Splash Potion of Spell Damage");
      this.add("item.minecraft.splash_potion.effect.spell_damage_potion_long", "Splash Potion of Spell Damage");
      this.add("item.minecraft.potion.effect.spell_damage_potion_strong", "Potion of Spell Damage");
      this.add("item.minecraft.potion.effect.spell_damage_potion", "Potion of Spell Damage");
      this.add("item.minecraft.potion.effect.spell_damage_potion_long", "Potion of Spell Damage");
      this.add("item.minecraft.lingering_potion.effect.spell_damage_potion_strong", "Lingering Potion of Spell Damage");
      this.add("item.minecraft.lingering_potion.effect.spell_damage_potion", "Lingering Potion of Spell Damage");
      this.add("item.minecraft.lingering_potion.effect.spell_damage_potion_long", "Lingering Potion of Spell Damage");
      this.add("item.minecraft.tipped_arrow.effect.spell_damage_potion_strong", "Arrow of Spell Damage");
      this.add("item.minecraft.tipped_arrow.effect.spell_damage_potion", "Arrow of Spell Damage");
      this.add("item.minecraft.tipped_arrow.effect.spell_damage_potion_long", "Arrow of Spell Damage");
      this.add("effect.ars_nouveau.spell_damage", "Spell Damage");
      this.add("ars_nouveau.spell_book_gui.familiar", "Familiars");
      this.add("ars_nouveau.spell_book_gui.close", "Close");
      this.add("effect.ars_nouveau.familiar_sickness", "Familiar Sickness");
      this.add("ars_nouveau.familiar.sickness", "You must wait before summoning another familiar.");
      this.add("ars_nouveau.gui.notebook", "Documentation");
      this.add("ars_nouveau.gui.color", "Color Picker");
      this.add("ars_nouveau.gui.familiar", "Familiars");
      this.add(
         "ars_nouveau.category.familiars",
         "Familiars can provide passive buffs and assistance in combat. For more information, see the section on Summoning a Familiar."
      );
      this.add("ars_nouveau.familiar.owned", "You already own this familiar.");
      this.add("ars_nouveau.familiar.unlocked", "Familiar unlocked.");
      this.add("ars_nouveau.familiar.script", "Used for obtaining familiars. Obtained from the Ritual of Binding.");
      this.add("ars_nouveau.schools", "Schools: ");
      this.add("entity.ars_nouveau.familiar_bookwyrm", "Bookwyrm Familiar");
      this.add("entity.ars_nouveau.familiar_starbuncle", "Starbuncle Familiar");
      this.add("entity.ars_nouveau.familiar_whirlisprig", "Whirlisprig Familiar");
      this.add("entity.ars_nouveau.familiar_wixie", "Wixie Familiar");
      this.add("entity.ars_nouveau.familiar_drygmy", "Drygmy Familiar");
      this.add("entity.ars_nouveau.familiar_amethyst_golem", "Amethyst Golem Familiar");
      this.add("ars_nouveau.mirror.invalid", "Invalid spell. Mirrors accept Effects and Augments only.");
      this.add("item.ars_nouveau.enchanters_mirror", "Enchanter's Mirror");
      this.add(
         "ars_nouveau.page.enchanters_mirror",
         "Applies a self spell to the user. Spells cast with this mirror are discounted and gain additional bonus duration to all glyphs. Apply a spell at the Scribe's table that DOES NOT contain a form such as Heal -> Heal."
      );
      this.add("block.ars_nouveau.bookwyrm_lectern", "Bookwyrm Lectern");
      this.add("ars_nouveau.seconds", "%s seconds");
      this.add("block.ars_nouveau.basic_spell_turret", "Basic Spell Turret");
      this.add("block.ars_nouveau.spell_turret", "Enchanted Spell Turret");
      this.add("block.ars_nouveau.timer_spell_turret", "Timer Spell Turret");
      this.add("block.ars_nouveau.rotating_spell_turret", "Adjustable Spell Turret");
      this.add("effect.ars_nouveau.bounce", "Bounce");
      this.add("ars_nouveau.starbuncle.path", "Starbuncle will prefer to path on this block if it is on their way.");
      this.add("ars_nouveau.starbuncle.pathing", "Preferring to path on %s");
      this.add("ars_nouveau.summoning", "Summoning");
      this.add("ars_nouveau.item_transport", "Item Transport");
      this.add("ars_nouveau.filtering", "Filtering");
      this.add("ars_nouveau.filtering_with", "Filtering with: %s");
      this.add("ars_nouveau.filter_set", "Filter set.");
      this.add("ars_nouveau.pathing", "Pathing");
      this.add("block.ars_nouveau.archwood_chest", "Archwood Chest");
      this.add("block.ars_nouveau.spell_prism", "Spell Prism");
      this.add(
         "ars_nouveau.page.spell_prism",
         "When a projectile spell hits this block, it is redirected the direction the block is facing. Spell Prisms will send a signal to nearby Observers if a spell is redirected."
      );
      this.add("ars_nouveau.weald_walker.home", "Guarding: %s");
      this.add("ars_nouveau.nothing", "Nothing");
      this.add("ars_nouveau.weald_walker", "Weald Walker");
      this.add("entity.ars_nouveau.cascading_weald_walker", "Cascading Weald Walker");
      this.add("entity.ars_nouveau.flourishing_weald_walker", "Flourishing Weald Walker");
      this.add("entity.ars_nouveau.blazing_weald_walker", "Blazing Weald Walker");
      this.add("entity.ars_nouveau.vexing_weald_walker", "Vexing Weald Walker");
      this.add(
         "ars_nouveau.page1.weald_walker",
         "Weald Walkers are living Archwood Trees that guard their homes against hostile monsters. A Weald Walker can be created from the Ritual of Awakening. When summoned, the Weald Walker will roam randomly unless given a home position. To give the Weald Walker a home, use the dominion wand on the Weald Walker, and then the block you wish it to guard."
      );
      this.add("ars_nouveau.page2.weald_walker", "Casts Flare at nearby enemies, careful not to ignite any blocks in the process.");
      this.add("ars_nouveau.page3.weald_walker", "Casts Freeze and Cold Snap at nearby enemies.");
      this.add("ars_nouveau.page4.weald_walker", "Casts an amplified Harm with a Snare effect at nearby enemies.");
      this.add("ars_nouveau.page5.weald_walker", "Casts Hex and an amplified Wither at nearby enemies.");
      this.add(
         "ars_nouveau.page1.weald_waddler",
         "If a Weald Walker dies, it will be turned into a Weald Waddler. Weald Waddlers will slowly grow back into Weald Walkers, and can be sped up by giving them bonemeal. A Weald Waddler cannot fight or protect itself until it has grown back to a Walker."
      );
      this.add("ars_nouveau.source", "Source: %s");
      this.add("ars_nouveau.crush_recipe", "Crush Glyph");
      this.add("ars_nouveau.enchanting_apparatus", "Enchanting Apparatus");
      this.add("ars_nouveau.armor_upgrade", "Magic Armor Upgrade");
      this.add("ars_nouveau.page.apparatus_crafting", "Apparatus Crafting");
      this.add(
         "ars_nouveau.page1.apparatus_crafting",
         "The Enchanting Apparatus is used for crafting special machines, curios, and equipment used to progress in Ars Nouveau. Crafting with the Enchanting Apparatus requires up to eight Arcane Pedestals, an Arcane Core, and the Enchanting Apparatus block. Once you have setup your apparatus, you should craft your first Magebloom Seed."
      );
      this.add("ars_nouveau.page.better_casting", "Better Casting");
      this.add(
         "ars_nouveau.page1.better_casting",
         "Your mana pool may be expanded with special mage armors, enchantments, learning new glyphs, or by drinking potions. Once you have acquired a Magebloom Seed, you may craft Novice Robes which will expand your casting abilities significantly. These robes will self-repair using your mana pool, have a high enchantability, and provide decent armor."
      );
      this.add("ars_nouveau.page.new_glyphs", "New Glyphs");
      this.add(
         "ars_nouveau.page1.new_glyphs",
         "Accessing new spells will require a small amount of setup, resources, and base building. New spells can be learned by obtaining Glyphs. Glyphs are created using the Scribe's Table with Experience and items. Once you have obtained a glyph, simply use it to memorize the glyph. See the section on the Scribes Table for more information."
      );
      this.add("ars_nouveau.page.source", "Source");
      this.add(
         "ars_nouveau.page1.source",
         "Source is a special resource that must be gathered using devices in the world. Source is used for powering devices like the Imbuement Chamber and Enchanting Apparatus. To begin gathering Source, you will need a Source Jar and a Sourcelink."
      );
      this.add("ars_nouveau.page.spell_casting", "Spell Casting");
      this.add(
         "ars_nouveau.page1.spell_casting",
         "To begin spell casting, you will need to first obtain a Spellbook. A spellbook will allow you to create, store, and cast spells using Mana. A higher tier spell book will provide additional spell slots, allowing you to craft more complex spells. $(br) To craft your first spell, you must first select the $(bold)Form$() that the spell will take on. A $(bold)Form$() glyph must always be the first glyph in a spell recipe."
      );
      this.add(
         "ars_nouveau.page2.spell_casting",
         "Next, add any number of $(bold)Effects$() to the chain. Effects refer to $(italic)what$() the spell will do and they will resolve in the order they are placed in the book at the target or location the spell hits. An $(bold)Augment$() can be used to modify the way an Effect or Form behaves. $(bold)Augments$() may be placed after an Effect or Form. An Augment will only apply to the glyph to the $(bold)left$() of it. Multiple augments may also be applied on the same Effect or Form by chaining Augments together."
      );
      this.add(
         "ars_nouveau.page3.spell_casting",
         "If you would like to set a spell to a different tab, select the tab from the right side and repeat the above process. Several keybindings are provided for using the spellbook. $(br)Open Spellbook: $(k:ars_nouveau.open_book) $(br)Quick Select: $(k:ars_nouveau.selection_hud) $(br)Next Spell: $(k:ars_nouveau.next_slot) $(br)Previous Spell: $(k:ars_nouveau.previous_slot)"
      );
      this.add("ars_nouveau.page.spell_mana", "Spell Mana");
      this.add(
         "ars_nouveau.page1.spell_mana",
         "Spell Mana is used to cast spells with a $(thing)Spellbook$(). The maximum amount of mana, and the speed at which it regenerates, may be increased by wearing special $(item)Mage Armor$() or by applying the $(item)Mana Boost$() or $(item)Mana Regen$() enchantments on your gear. Additionally, you will gain bonus mana and regeneration for each glyph unlocked in your spellbook."
      );
      this.add(
         "ars_nouveau.page2.spell_mana",
         "Adding glyphs to your spell book will also increase your maximum amount of mana and mana regeneration. This bonus also scales with the tier of your spell book."
      );
      this.add("ars_nouveau.page.starting_automation", "Starting Automation");
      this.add(
         "ars_nouveau.page1.starting_automation",
         "Spells may be used in Automation using Spell Turrets. Use these to create auto harvesters, tree farms, quarries, cake farms, glass factories, and more! For item transport, autocrafting, or resource generation, see the variety of magical entities that may be summoned using Charms."
      );
      this.add("ars_nouveau.page.trinkets", "Trinkets");
      this.add(
         "ars_nouveau.page1.trinkets",
         "Items and curios can expand your casting and can provide unique buffs. For more casting, you may want to craft a Ring of Discount or an Amulet of Mana Regen. For travel, see the Belt of Levitation, or improve your mining efficiency with the Jar of Voiding."
      );
      this.add("ars_nouveau.page.upgrades", "Upgrades");
      this.add(
         "ars_nouveau.page1.upgrades",
         "Tier 2 and 3 glyphs will require an Apprentice and Archmage spell book respectively. Higher tier books will allow you to cast higher tier spells, use them in automation, and provide additional mana and mana regeneration as a bonus. Once you are able to upgrade your book, upgrading your armor to the next tier of robes will also grant you another boost in casting."
      );
      this.add("ars_nouveau.page.world_generation", "World Generation");
      this.add(
         "ars_nouveau.page1.world_generation",
         "Several resources can spawn in the world, each with their own magical properties. Archwood trees come in several decorative variants and may be used to craft Casting Wands. Source Berries, found in Taigas, are essential for crafting Mana Regeneration potions."
      );
      this.add("ars_nouveau.page.archwood", "Archwood Trees");
      this.add(
         "ars_nouveau.page1.archwood",
         "Archwood Trees have a small chance to spawn in any biome, and come in four types. Rarely, you may stumble upon an Archwood Forest, a biome full of magical creatures, naturally spawning lights, and Archwood trees. Can be used as decoration, rituals, or for crafting wands."
      );
      this.add("ars_nouveau.page1.decorative", "Purely decorative blocks. To see the full list, place Arcane Stone in a Stonecutter.");
      this.add("ars_nouveau.wilden", "Wilden");
      this.add("ars_nouveau.page.decorative", "Decorative Blocks");
      this.add(
         "ars_nouveau.page1.magelight_torch",
         "Decorative lights. To ignite, cast Light on the sconce. The color of the flame corresponds with your spell color. The Magelight Torch on a wall can change the direction of its flames by interacting. Use Touch or Projectile Sensitive to target the sconce."
      );
      this.add(
         "ars_nouveau.page1.source_berry",
         "A Sourceberry Bush can be found in Taiga and Archwood Forest biomes, and produces Sourceberries. A Sourceberry can be used to craft a Potion of Mana Regeneration or consumed as food. Starbuncles will automatically harvest fully grown Source Berry Bushes, making them useful for early automation of the Agronomic Sourcelink. Sourceberry foods will also grant Mana Regeneration."
      );
      this.add("ars_nouveau.page.wilden", "Wilden");
      this.add("ars_nouveau.page.weald_walker", "Weald Walkers");
      this.add("ars_nouveau.spell_schools", "Spell Schools");
      this.add("ars_nouveau.casting_cost", "Casting Cost");
      this.add("ars_nouveau.tier", "Tier %s");
      this.add(
         "ars_nouveau.page.agronomic_sourcelink",
         "The Agronomic Sourcelink generates source from crop and tree growth within 15 blocks. Bonus source is generated for magical plants such as Mageblooms, Source Berry Bushes, and Archwood Saplings. Source will be output from the Sourcelink to nearby jars within 5 blocks. Note: Bonemealing crops will not grant Source."
      );
      this.add(
         "ars_nouveau.page.source_jar",
         "Source Jars store source gathered from nearby Sourcelinks. Source is used in glyphs and rituals by powering devices like the Imbuement Chamber and Enchanting Apparatus. Source may be moved using a bucket, or the jar can be picked up and moved. To use Source in a jar, simply place the jar near your desired device. Source Jars will provide a signal to Redstone Comparators based on their fill level."
      );
      this.add(
         "ars_nouveau.page1.volcanic_sourcelink",
         "The Volcanic Sourcelink generates Source by consuming burnable items. Archwood logs will generate bonus Source, with Blazing Archwood generating the most. As the Volcanic Sourcelink produces Source, it also produces $(item)heat$(), used for spawning Lava Lilies and converting stone into lava. The Volcanic Sourcelink automatically outputs to nearby jars, starting with the one closest to it."
      );
      this.add(
         "ars_nouveau.page2.volcanic_sourcelink",
         "Nearby Blazing Archwood items will be burned up in exchange for a chunk of Source and a moderate amount of $(item)heat$(). The Volcanic Sourcelink will also take items from surrounding pedestals."
      );
      this.add(
         "ars_nouveau.page3.volcanic_sourcelink",
         "The Volcanic Sourcelink will occasionally convert Stone into Magma Blocks, and Magma Blocks into Lava, given that these blocks exist beneath it in its 3x3 area. This conversion is dependent on the amount of $(item)heat$() it has produced over time. The Volcanic Sourcelink will also spawn a Lava Lily adjacent to it given that there is nothing covering the lava. Lava Lilys may be harvested and used for decoration."
      );
      this.add("ars_nouveau.page4.volcanic_sourcelink", "The color of a Lava Lily changes if it is placed above lava, magma, or other blocks.");
      this.add("ars_nouveau.active_generation", "Active Generation");
      this.add("ars_nouveau.heat", "Heat");
      this.add(
         "ars_nouveau.page.alchemical_sourcelink",
         "Generates source by consuming potions from adjacent potion jars. The amount of source varies per potion and is dependent on the complexity of the potion. Bonus source is given for the length and level of the potion with multipliers for each effect a potion contains. Utilizing Wixies and Potion Melders is recommended for creating highly complex potions."
      );
      this.add(
         "ars_nouveau.page.vitalic_sourcelink",
         "Generates a moderate amount of source from nearby mob death and animal breeding. Additionally, the Vitalic Sourcelink will generate passive Source from nearby baby animals and will accelerate their growth."
      );
      this.add(
         "ars_nouveau.page.mycelial_sourcelink",
         "Generates a moderate amount of source from nearby food, generating more for more nourishing food. Source Berry food is worth far more than other mundane foods. Additionally, the Mycelial Sourcelink will convert Grass or Dirt in the 3x3 below it into Mycelium and will grow mushrooms around it given that the space is empty. The Sourcelink will also pull items from nearby pedestals."
      );
      this.add("block.ars_nouveau.relay", "Source Relay");
      this.add("block.ars_nouveau.relay_splitter", "Source Relay: Splitter");
      this.add("block.ars_nouveau.whirlisprig_flower", "Whirlisprig Blossom");
      this.add(
         "ars_nouveau.page.relay",
         "Enables the transport of source between Source Jars and other Source Relays. To pull source from jars, use the Dominion Wand on the jar, and then on the relay. To send between relays or from a relay to a jar, use the wand on the relay and then the target you wish to send source to. Relays may only reach up to 30 blocks away. To clear connections, sneak while using the Dominion Wand on the relay."
      );
      this.add(
         "ars_nouveau.page.relay_splitter",
         "Operates similar to the Source Relay, but will support taking from and transferring to multiple jars at once. The splitter has a much larger through-put than the Source Relay, and will split this throughput amongst all of its jars. See the instructions on the Source Relay for use."
      );
      this.add(
         "ars_nouveau.page1.enchanting_apparatus",
         "The Enchanting Apparatus utilizes pedestals and Source for crafting. To use the Enchanting Apparatus, place any number of Arcane Pedestals within 3 blocks with their items. Once you have filled the pedestals, use the middle item on the Enchanting Apparatus block. The Enchanting Apparatus requires an Arcane Core beneath it in order to work."
      );
      this.add(
         "ars_nouveau.page1.imbuement_chamber",
         "Imbues certain items with Source to create new items. The primary way to obtain Source Gems, amethyst and lapis may be used to create Source Gems. The Imbuement Chamber will passively accumulate source for recipes, or will draw from Source Jars 2 block away. Some recipes require additional items placed in pedestals within 1 block of the Imbuement Chamber, such as Essences. Items in pedestals will not be consumed."
      );
      this.add(
         "ars_nouveau.page.potion_melder",
         "Converts three doses of a potion from two Potion Jars and outputs a potion with the combined effects. Use the Dominion Wand from a Potion Jar to Melder to link a jar for consumption. Link two input potion jars to the melder. Then, use the wand on the Melder and then to a third jar to set the output. The Potion Melder requires source per mix."
      );
      this.add("ars_nouveau.page.warp_portal", "Warp Portals");
      this.add(
         "ars_nouveau.page1.warp_portal",
         "Warp portals, like warp scrolls, provide a one-way teleport to any location, provided it is in the same dimension. To construct a Warp Portal, build a frame from Sourcestone or its variants in the shape of a rectangle and provide a full Source Jar nearby. Then, throw a warp scroll with a written location into the frame. Given there is enough source nearby, the portal will be created."
      );
      this.add(
         "ars_nouveau.page2.warp_portal",
         "Portals can be built horizontal or vertical, from 1x1 to 21x21 in size. Warping does not cost any source after creation. Using a Dominion Wand on the portal will change the texture of the portal."
      );
      this.add("ars_nouveau.page3.warp_portal", "Warp Portal");
      this.add("ars_nouveau.page4.warp_portal", "A magical portal that can send players, mobs, spells, and items to any location in the same dimension.");
      this.add(
         "ars_nouveau.page.ritual_brazier",
         "A brazier that may be used as decoration or for performing rituals. To light the brazier for decoration, cast a Light spell on the brazier. The color of the brazier corresponds with the color of the spell. Applying a redstone signal will disable a running ritual. For information on performing rituals, see the dedicated section on rituals."
      );
      this.add(
         "ars_nouveau.page1.basic_spell_turret",
         "Turrets can be used to cast spells when given a redstone signal, functioning like a dispenser. Turrets will accept spells that use Touch and Projectile. Spells may be set using an inscribed piece of Spell Parchment. In order to cast spells, turrets will draw source from nearby Source Jars. Turrets may use Item Pickup and Place Block as long as an inventory is placed adjacent to this block."
      );
      this.add("ars_nouveau.page2.basic_spell_turret", "Enchanted Spell Turrets cast spells at half the source cost compared to basic spell turrets.");
      this.add(
         "ars_nouveau.page3.basic_spell_turret",
         "Timer Spell Turrets will automatically fire on a timer. Defaulted to 1 second, the time may be increased by right-clicking the block. Punching will decrease the time. Sneaking will allow you to configure it in 10 second intervals. To prevent further changes, lock and unlock the turret using the dominion wand. Setting the turret to 0 seconds or providing a redstone signal will disable it. Casts Projectile, Touch, and Redstone for free."
      );
      this.add(
         "ars_nouveau.page4.basic_spell_turret",
         "Turrets can provide compact and efficient automation. Examples include: configurable redstone clocks, one block tree or crop farms, rapid smelting with fortune, or mob farms with looting."
      );
      this.add(
         "ars_nouveau.page1.bookwyrm_charm",
         "Bookwyrm Charms can be used on a Storage Lectern to increase the number of accessible inventories. Augment a Ritual of Awakening with Book and Quills in order to obtain charms. Bookwyrms can be dyed using white, black, blue, green, red, or purple dye."
      );
      this.add("ars_nouveau.page2.bookwyrm_charm", "In the event that they die or are dispelled, they will drop their charm.");
      this.add(
         "ars_nouveau.page.dominion_wand",
         "A tool for configuring Source Relays and automation entities. To set a transfer path, use the wand on the object that you would like to take source from, and then use it on the block you would like to send source to. For example: Source Jar to Source Relay, Source Relay to Source Relay, or Source Relay to Source Jar. To clear connections, sneak and use this wand on a relay."
      );
      this.add(
         "ars_nouveau.page1.drygmy_charm",
         "Drygmys are often found following and tending to animals around it. They can be found anywhere, though somewhat rarely. Drygmys can be given a home in the world, and will produce items from nearby monsters and animals as if they were slain, without harming them. A wild drygmy may be befriended by throwing a Wilden Horn near it! You may dye a Drygmy Cyan, Orange, or Brown."
      );
      this.add("ars_nouveau.page2.drygmy_charm", "A Drygmy can also produce experience gems!");
      this.add(
         "ars_nouveau.page3.drygmy_charm",
         "To summon a Drygmy, use a Drymy Charm on a block of Mossy Cobblestone. After a short time, the cobblestone will transform into a Drygmy Henge and summon your Drgymy! To summon additional drygmys, use more charms on the henge. Casting dispel or killing the Drygmy will return your charm."
      );
      this.add(
         "ars_nouveau.page4.drygmy_charm",
         "A Drygmy considers its home to be 10 blocks in every direction from its home. The drygmy will use this area to produce items from any entities nearby. Your drgymy's efficiency is dependent on its happiness. This may be increased for each entity nearby, with a bonus for each unique type in its home. Nearby Containment Jars will also count as an entity."
      );
      this.add(
         "ars_nouveau.page5.drygmy_charm",
         "Each Drygmy working around a henge contributes progress. Once maximum progress has been reached, the henge will generate items and experience gems and deposit them into adjacent chests. Each time this occurs, the henge will require Source to recharge. The number of drops and experience gems is equal to the Drygmy happiness and experience value of the entities. To get started, place a chest and jar of Source next to the Henge."
      );
      this.add("ars_nouveau.happiness", "Happiness");
      this.add("ars_nouveau.production", "Production");
      this.add("ars_nouveau.important", "Important");
      this.add(
         "ars_nouveau.page1.starbuncle_charm",
         "Starbuncles naturally appear in wooded areas in search of golden nuggets. While Starbuncles are normally afraid of humans, they will allow someone to approach if they are holding a gold nugget. When a Starbuncle has picked up a golden nugget, it will vanish from this world and leave behind Starbuncle Shards."
      );
      this.add("ars_nouveau.page2.starbuncle_charm", "While wild Starbuncles cannot be tamed, their shards may be used to summon a Starbuncle.");
      this.add(
         "ars_nouveau.page3.starbuncle_charm",
         "To summon a Starbuncle, use a Starbuncle charm on the ground. Summoned Starbuncles will pickup nearby items and can move items between inventories such as chests. Starbuncles will harvest fully grown Source Berry bushes around it. A Starbuncle will drop its charm when Dispelled or when killed. You may dye them any color."
      );
      this.add(
         "ars_nouveau.page4.starbuncle_charm",
         "To bind a Starbuncle to place items into a chest, use the dominion wand on the Starbuncle and then the inventory. To take items from an inventory, use the wand on the inventory and then the Starbuncle. Starbuncles will move items between as many inventories as you desire. Looking at a Starbuncle will tell you how many chests are being taken from, and input to. Using the Dominion Wand on a Starbuncle while sneaking will reset them."
      );
      this.add(
         "ars_nouveau.page5.starbuncle_charm",
         "You may dictate where items go and may be picked up by using Item Scrolls or Item Frames. A Starbuncle may be given an Item Scroll: Allow or Deny, and will only pickup and move items respecting that filter. Alternatively, you may place an Item Frame on the inventory a Starbuncle is interacting with. You may either place an Item Scroll or a single item directly on the frame. Starbuncle interacting with that inventory will respect item framed filters."
      );
      this.add(
         "ars_nouveau.page6.starbuncle_charm",
         "Using a block on a Starbuncle will set them to prefer that block for pathing between areas, as long as it is on the way. They will also naturally prefer grass paths."
      );
      this.add(
         "ars_nouveau.page7.starbuncle_charm",
         "Starbuncles may be bound to a Magebloom Bed using the Dominion Wand and will rest on the bed when there are no other tasks to be done. Useful for keeping them out of the way, or returning them to a spot where items drop. Providing a redstone signal to the bed will disable starbuncles and go back to their beds."
      );
      this.add("ars_nouveau.starbuncle_bed", "Resting");
      this.add(
         "ars_nouveau.page1.whirlisprig_charm",
         "Whirlisprigs are curious nature sprites that are exclusively found in forested areas. Summoned Whirlisprigs can be given a home in the world, and will begin producing natural materials including wood, crops, seeds, and flowers that exist around them. Wild Whirlisprigs can be befriended and will drop Whirlisprig Shards if a tree is grown near them."
      );
      this.add(
         "ars_nouveau.page2.whirlisprig_charm",
         "Whirlisprigs will follow animals, players, and monsters! They will also grow grass around them every once and a while."
      );
      this.add(
         "ars_nouveau.page3.whirlisprig_charm",
         "To summon a Whirlisprig, use a Whirlisprig charm on any flower. Whirlisprigs consider their home to be 10 blocks in any direction from the flower. Whirlisprigs require source nearby to operate, and will only generate items if there is a chest placed next to the flower. You can get your charm back by using Dispel on a Whirlisprig. They may be given orange, yellow, white, or green dye."
      );
      this.add(
         "ars_nouveau.page4.whirlisprig_charm",
         "Summoned Whirlisprigs must be happy in order to produce materials, and their mood is determined by the number and diversity of natural materials in their home. You may use blocks on the Whirlisprig to gain additional info if a Whirlisprig would enjoy that block in their home. Interacting with the Whirlisprig with an empty hand will give you additional info on the Whirlisprig's happiness."
      );
      this.add(
         "ars_nouveau.page5.whirlisprig_charm",
         "Note: It can take several minutes for a Whirlisprig to update its mood after placing a block. Whirlisprigs value diversity, and too much of one block will no longer count."
      );
      this.add(
         "ars_nouveau.page6.whirlisprig_charm",
         "Drop rates are determined by happiness, diversity, and the proportions of blocks in a Whirlisprig home. For example, to generate a lot of logs, grow more trees. To gain more seeds and crop harvests, plant more crops."
      );
      this.add(
         "ars_nouveau.page1.wixie_charm",
         "A Wixie can automatically craft items for you at the expense of source. To obtain a Wixie Shard, cast Dispel on a Witch while it is half health or less. Once you have obtained a Wixie Charm, use it on a Cauldron to summon your Wixie. A Wixie can be dyed Red, White, Black, Blue, or Green."
      );
      this.add("ars_nouveau.page2.wixie_charm", "To select an item for crafting, use your item or block on the Wixie Cauldron.");
      this.add(
         "ars_nouveau.page3.wixie_charm",
         "The Wixie will select the recipe for crafting based on the inventories nearby, you need not specify the exact materials for the recipe. For example, if you want to craft Sticks, the Wixie will mix and match planks from nearby chests in order to fulfill the recipe. Each craft requires a small amount of source and will be drained from nearby Source Jars. A redstone signal on the cauldron will stop crafting."
      );
      this.add(
         "ars_nouveau.page4.wixie_charm",
         "Wixies can craft multiple items at once by placing pedestals adjacent to the cauldron. Wixie's will attempt to craft items in the pedestal, rotating round robin. Powering the pedestal will disable the wixie from crafting its item."
      );
      this.add("ars_nouveau.item_crafting_setting", "Multi-Item Crafting");
      this.add("ars_nouveau.item_crafting", "Item Crafting");
      this.add("ars_nouveau.potion_crafting", "Potion Crafting");
      this.add(
         "ars_nouveau.page.summon_focus",
         "A special casting focus. Grants summons from spells additional duration, strength, speed, and deals damage to enemies that kill them. Additionally, casting spells that target you like Self and Orbit will cast a copy of the spell on your nearby summons."
      );
      this.add(
         "ars_nouveau.page1.potion_flask",
         "A flask that stores 8 charges of a potion. To fill the flask, use the flask on a Potion Jar, or craft the flask in a Crafting Table with another potion. You may empty the flask by using the flask on a Potion Jar while sneaking."
      );
      this.add("ars_nouveau.page2.potion_flask", "An enchanted flask that extends the time of effects by 50%%");
      this.add("ars_nouveau.page3.potion_flask", "An enchanted flask that increases the power of effects by 1, but reduces their time in half.");
      this.add(
         "ars_nouveau.page.warp_scroll",
         "A scroll that may be used a single time to teleport to a recorded location. However, teleporting across dimensions is not possible. Can be used to warp other entities if an inscribed scroll is held in the offhand and the holder casts Blink on an entity."
      );
      this.add("ars_nouveau.page.armor", "Magical Armor");
      this.add("ars_nouveau.page.reactive_enchantment", "Reactive");
      this.add("ars_nouveau.page.spell_books", "Spell Books");
      this.add(
         "ars_nouveau.page1.spell_books",
         "Accessing higher tier spells will require a better spell book. While a novice spell book only has access to Tier 1 spells, the Apprentice and Archmage spell books will unlock tiers two and three. Upgrading your spell book will transfer all of the spells that you have learned into your new book. Books may be dyed by crafting them with a piece of dye."
      );
      this.add("ars_nouveau.page.summoning_familiars", "Summoning Familiars");
      this.add(
         "ars_nouveau.page1.summoning_familiars",
         "To begin summoning familiars, you will need to obtain a Bound Script of the entity you wish to befriend. These can be obtained by performing the Ritual of Binding near a relevant entity. See the full list of eligible entities in the Familiars section. Once you have obtained a Bound Script, use it to learn the familiar."
      );
      this.add(
         "ars_nouveau.page2.summoning_familiars",
         "Once you have obtained a bound script, you may access your list of familiars from your Spellbook crafting menu in the Familiars section. Selecting a Familiar will summon it in the world and give you Familiar Sickness, preventing you from summoning another one for a short time. To obtain your first familiar, perform the Ritual of Binding near a Starbuncle. Familiars are bound to the player and cannot be transferred between books."
      );
      this.add(
         "ars_nouveau.page3.summoning_familiars",
         "Typically only one familiar may be out at a time, and summoning another familiar will remove others bound to you. You may dismiss your own familiar by casting Dispel on it. In exchange for empowering the owner, familiars will reserve a portion of max mana from their owner for as long as they persist in the world."
      );
      this.add("ars_nouveau.page.performing_rituals", "Performing Rituals");
      this.add(
         "ars_nouveau.page1.performing_rituals",
         "Performing a ritual requires a Ritual Brazier, and a tablet. Once you have obtained a tablet and brazier, place your brazier in the world and use a tablet on it. The brazier will ignite, and is awaiting activation. In this state, you may throw in any additional items as a way to augment the ritual. If you wish to augment a ritual, simply toss the item on top of the brazier."
      );
      this.add(
         "ars_nouveau.page2.performing_rituals",
         "To activate your ritual, interact with the brazier with an empty hand. Once activated, your ritual can no longer be augmented and has been consumed permanently. If a ritual requires source to operate, the brazier will pull from source jars within 6 blocks. Information related to rituals and their requirements can be found in their respective entries."
      );
      this.add("ars_nouveau.automation", "Automation");
      this.add("ars_nouveau.automation_desc", "Magical Automation");
      this.add("ars_nouveau.enchanting", "Enchanting");
      this.add(
         "ars_nouveau.enchanting_desc",
         "Once you have acquired a jar of Source and an Enchanting Apparatus, you may begin enchanting items. For more information, see the section on the Enchanting Apparatus."
      );
      this.add("ars_nouveau.equipment", "Magical Equipment");
      this.add("ars_nouveau.equipment_desc", "Magical Equipment");
      this.add("ars_nouveau.familiars", "Familiars");
      this.add("ars_nouveau.familiars_desc", "Familiars may be summoned to provide passive buffs for spell casting, passive buffs, and more.");
      this.add("ars_nouveau.getting_started", "Getting Started");
      this.add("ars_nouveau.getting_started_desc", "An introduction to Ars Nouveau. It is recommended to follow each section in order.");
      this.add("ars_nouveau.tier_1_spells", "Tier 1 Glyphs");
      this.add("ars_nouveau.tier_1_spells_desc", "Glyphs that may be cast using a Novice Spellbook.");
      this.add("ars_nouveau.tier_2_spells", "Tier 2 Glyphs");
      this.add("ars_nouveau.tier_2_spells_desc", "Glyphs that may be cast using a Mage's Spellbook.");
      this.add("ars_nouveau.tier_3_spells", "Tier 3 Glyphs");
      this.add("ars_nouveau.tier_3_spells_desc", "Glyphs that may be cast using a Archmage's Spellbook.");
      this.add("ars_nouveau.machines", "Machines");
      this.add("ars_nouveau.machines_desc", "Magical Machines");
      this.add("ars_nouveau.resources", "Resources and Decoration");
      this.add("ars_nouveau.resources_desc", "Resources found in the world.");
      this.add("ars_nouveau.rituals", "Rituals");
      this.add(
         "ars_nouveau.rituals_desc",
         "Rituals are more powerful versions of spells, and come in semi-permanent or single use forms. To get started with Rituals, you will need a Ritual Brazier and a tablet of the ritual you would like to perform."
      );
      this.add("ars_nouveau.category.source", "Source");
      this.add("ars_nouveau.source_desc", "Source can be used to power rituals, summons, and machines.");
      this.add("ars_nouveau.page.how_to_enchant", "How to Enchant");
      this.add(
         "ars_nouveau.page1.how_to_enchant",
         "The Enchanting Apparatus may add new enchantments or upgrade existing ones by using Source and items. To begin, select a level 1 enchantment and add its items to the pedestals. Place a jar of Source nearby, and use the item you want to enchant on the apparatus. The apparatus may only apply enchantments that are valid to the item you have given it."
      );
      this.add(
         "ars_nouveau.page2.how_to_enchant",
         "To apply a level 2 or higher enchantment, the item must already have the previous level. For example, to apply Smite 3, the item must already have Smite 2."
      );
      this.add("ars_nouveau.mod_news", "Mod News");
      this.add("ars_nouveau.mod_news_desc", "The latest releases and news");
      this.add("ars_nouveau.page.mod_news", "Join the Community!");
      this.add(
         "ars_nouveau.store", "Support Ars Nouveau through our Redbubble store! Get the latest summon-themed merchandise including stickers, mugs, and more!"
      );
      this.add(
         "ars_nouveau.community", "Join the Ars Nouveau community! Get ideas for spells, help with the mod, report bugs and issues, or request new features!"
      );
      this.add(
         "ars_nouveau.page1.reactive_enchantment",
         "Items with Reactive have a chance to cast spells when swung. The spell on the Spell Parchment determines the spell that will be inscribed on the item."
      );
      this.add(
         "ars_nouveau.page2.reactive_enchantment",
         "Like other enchantments, Reactive levels can only be applied to an item with the previous level of enchantment. Reactive 2 requires Reactive 1, etc."
      );
      this.add(
         "ars_nouveau.page3.reactive_enchantment",
         "The spell inscribed for Reactive can be changed by placing the item in the apparatus with a new inscribed spell parchment."
      );
      this.add("ars_nouveau.discord_text", "Join Discord!");
      this.add("ars_nouveau.store_text", "Shop Redbubble!");
      this.add("ars_nouveau.page.obtaining_gems", "Obtaining Source Gems");
      this.add(
         "ars_nouveau.page1.obtaining_gems",
         "To obtain Source Gems, you must first build an Imbuement Chamber. An Imbuement Chamber imbues items inside it with Source, and will convert them to a new item. To obtain a source gem, place an Amethyst or Lapis inside your Imbuement Chamber and wait. Imbuement Chambers will consume source from nearby Source Jars to speed up any crafting. A Dowsing Rod can be used for finding Budding Amethyst early."
      );
      this.add("ars_nouveau.reagent", "Reagent:");
      this.add("ars_nouveau.level", "Level");
      this.add("ars_nouveau.any_item", "ANY ITEM");
      this.add("item.ars_nouveau.abjuration_essence", "Abjuration Essence");
      this.add("item.ars_nouveau.conjuration_essence", "Conjuration Essence");
      this.add("item.ars_nouveau.air_essence", "Air Essence");
      this.add("item.ars_nouveau.earth_essence", "Earth Essence");
      this.add("item.ars_nouveau.manipulation_essence", "Manipulation Essence");
      this.add("item.ars_nouveau.water_essence", "Water Essence");
      this.add("item.ars_nouveau.fire_essence", "Fire Essence");
      this.add("item.ars_nouveau.dowsing_rod", "Dowsing Rod");
      this.add("effect.ars_nouveau.magic_find", "Magic Find");
      this.add("tooltip.ars_nouveau.essences", "Magical Essences created in an Imbuement Chamber.");
      this.add(
         "ars_nouveau.page.dowsing_rod",
         "A Dowsing Rod provides the user a short duration of Scrying for Budding Amethyst and Magic Find, which will cause magical creatures to glow within 75 blocks of you. The Dowsing Rod has a limited number of uses."
      );
      this.add("ars_nouveau.apparatus.norecipe", "No recipe found. Pedestals must be within 3 blocks.");
      this.add("ars_nouveau.imbuement.norecipe", "No recipe found. If pedestals are required, they must be placed within the 1 block cube around the chamber.");
      this.add("ars_nouveau.spell_book_gui.select", "Select");
      this.add("ars_nouveau.levels_required", "Levels required: %s");
      this.add("ars_nouveau.crafting", "Crafting: %s");
      this.add("ars_nouveau.all_glyphs", "All Glyphs");
      this.add("ars_nouveau.not_enough_exp", "Not enough EXP for this glyph.");
      this.add("ars_nouveau.exp", "EXP levels required: %s");
      this.add("ars_nouveau.scribing", "Scribing Spells");
      this.add("ars_nouveau.home_set", "Home set.");
      this.add("ars_nouveau.gathering_at", "Gathering at: %s");
      this.add("entity.ars_nouveau.amethyst_golem", "Amethyst Golem");
      this.add("tooltip.ars_nouveau.amethyst_charm", "Obtained by performing the Ritual of Awakening near Budding Amethyst");
      this.add("item.ars_nouveau.amethyst_golem_charm", "Amethyst Golem Charm");
      this.add(
         "ars_nouveau.page1.amethyst_golem_charm",
         "Amethyst Golems will harvest, grow, and collect Amethyst near its home. To obtain an Amethyst Golem, perform the Ritual of Awakening near Budding Amethyst to obtain the Amethyst Golem Charm."
      );
      this.add("ars_nouveau.page2.amethyst_golem_charm", "");
      this.add(
         "ars_nouveau.page3.amethyst_golem_charm",
         "Summon the Amethyst Golem by using a charm on a block. Before an Amethyst Golem will perform tasks, they must first have a home. Set their home by using the Dominion Wand on the golem, and then on a block. The golem's home is considered to be 10 blocks in any direction from the set position."
      );
      this.add("ars_nouveau.amethyst_farming", "Farming Amethyst");
      this.add(
         "ars_nouveau.page4.amethyst_golem_charm",
         "Once a home has been set, the golem will begin performing tasks over time. They will convert Amethyst Blocks into Budding Amethyst, harvest Amethyst Clusters, speed up Budding Amethyst growth, and pick up and store Amethyst Shards."
      );
      this.add("ars_nouveau.amethyst_storage", "Storing Amethyst");
      this.add(
         "ars_nouveau.page5.amethyst_golem_charm",
         "If a golem's home has been set to an inventory, golems will pick up and store Amethyst Shards in their bounded inventory block. If their home is not an inventory, they will simply ignore items on the ground."
      );
      this.add("ars_nouveau.recorded_codex", "You record your known glyphs to the codex.");
      this.add("ars_nouveau.updated_codex", "You have updated the codex with %s glyphs");
      this.add("ars_nouveau.consumed_codex", "You consume to codex to learn %s glyphs.");
      this.add("ars_nouveau.codex_no_use", "This codex would not teach you anything new.");
      this.add("ars_nouveau.codex_up_to_date", "This codex is already up to date.");
      this.add("ars_nouveau.codex_not_enough_exp", "Not enough levels to record glyphs. %s levels needed.");
      this.add("ars_nouveau.recorded_by", "Created by %s");
      this.add("ars_nouveau.contains_glyphs", "Contains %s glyphs.");
      this.add("ars_nouveau.codex_tooltip", "Use to record your glyphs.");
      this.add("item.ars_nouveau.annotated_codex", "Annotated Codex");
      this.add(
         "ars_nouveau.page.annotated_codex",
         "The Annoted Codex allows players to share their knowledge of glyphs with other players. To record your known glyphs, simply use the codex. Recording glyphs requires EXP for each glyph known, and the EXP will be consumed upon using the item. Using the item again will update the list of known glyphs. Other players may use the book to learn the glyphs, consuming the codex in the process."
      );
      this.add("ars_nouveau.ritual.no_start", "Start the current ritual or break the block to remove it.");
      this.add(
         "ars_nouveau.lights_on",
         "You have turned dynamic lights on. This can cause lag for users with weaker CPUs, low RAM allocation, and unexpected results with 'performance' mods like Optifine. Run this command or change the ars_nouveau-client config to disable this."
      );
      this.add("ars_nouveau.lights_off", "You have turned dynamic lights off.");
      this.add(
         "ars_nouveau.page.relay_collector",
         "Operates similar to the Source Relay, but will automatically take from jars it is not linked to within 5 blocks. See the instructions on the Source Relay for use."
      );
      this.add("block.ars_nouveau.relay_collector", "Source Relay: Collector");
      this.add("ars_nouveau.page2.relay", "A redstone signal will disable the relay.");
      this.add("ars_nouveau.sounds.pitch", "Pitch: ");
      this.add("ars_nouveau.sounds.volume", "Volume: ");
      this.add("ars_nouveau.gui.sounds", "Sounds");
      this.add("ars_nouveau.sounds.title", "Spell Sound");
      this.add("ars_nouveau.sound.empty", "No Sound");
      this.add("ars_nouveau.earth_essence.tooltip", "Can be used on Dirt to turn it into Grass.");
      this.add("ars_nouveau.fire_essence.tooltip", "Can be used as a fuel source.");
      this.add("tooltip.ars_nouveau.glyph_known", "You have unlocked this glyph.");
      this.add("tooltip.ars_nouveau.glyph_unknown", "You have not unlocked this glyph.");
      this.add("ars_nouveau.sounds.test", "Test");
      this.add("ars_nouveau.gui.settings", "Settings and Rewards");
      this.add("ars_nouveau.settings.title", "Settings");
      this.add(
         "ars_nouveau.dynamic_lights.button_on",
         "Dynamic lights are turned on.  This can cause lag for users with weaker CPUs, low RAM allocation, and unexpected results with 'performance' mods like Optifine."
      );
      this.add("ars_nouveau.dynamic_lights.button_off", "Dynamic lights are turned off.");
      this.add("block.ars_nouveau.orange_sbed", "Orange Magebloom Bed");
      this.add("block.ars_nouveau.blue_sbed", "Blue Magebloom Bed");
      this.add("block.ars_nouveau.green_sbed", "Green Magebloom Bed");
      this.add("block.ars_nouveau.purple_sbed", "Purple Magebloom Bed");
      this.add("block.ars_nouveau.red_sbed", "Red Magebloom Bed");
      this.add("block.ars_nouveau.yellow_sbed", "Yellow Magebloom Bed");
      this.add("ars_nouveau.starbuncle.set_bed", "Set bed.");
      this.add("ars_nouveau.summon_bed", "Magebloom Bed");
      this.add(
         "ars_nouveau.page1.summon_bed",
         "A decorative bed. Starbuncles can be bound to a bed using the Dominion Wand, and they will rest on the bed when there are no other tasks."
      );
      this.add("block.ars_nouveau.scryers_oculus", "Scryer's Oculus");
      this.add("block.ars_nouveau.scryers_crystal", "Scry Crystal");
      this.add("item.ars_nouveau.scryer_scroll", "Scryer's Scroll");
      this.add("ars_nouveau.scryers_oculus.no_pos", "No position set on scroll.");
      this.add("ars_nouveau.scryers_oculus.no_scrolls", "No scrolls found nearby. Place a linked Scryer's Scroll on a nearby pedestal.");
      this.add("ars_nouveau.scryer_scroll.bound", "Bound to %s.");
      this.add("tooltip.ars_nouveau.scryer_scroll", "Use on a Scryer's Crystal to bind the location to this scroll.");
      this.add(
         "tooltip.ars_nouveau.dowsing_rod",
         "Grants Magic Find and Scrying on use, causing magical creatures to glow and Amethyst to be revealed through blocks. Can be used on Imbuement Chamber and Enchanting Apparatus to highlight linked pedestals."
      );
      this.add("ars_nouveau.camera.move", "%1$s%2$s%3$s%4$s - Move");
      this.add("ars_nouveau.camera.exit", "%s - Exit");
      this.add("ars_nouveau.tooltip.scryers_oculus", "If you stare into the eye, the Starbuncle stares back at you.");
      this.add(
         "ars_nouveau.page.scryers_crystal",
         "Can be used to look through as if you were standing there. Right click to enter the camera, or bind the camera to a Scryer's Scroll by using Blank Parchment on the block. A Scryer's Scroll will let you remotely access the block via a Scryer's Oculus. "
      );
      this.add(
         "ars_nouveau.page.scryer_scroll",
         "Stores the location of a Scryer's Crystal. To create one, use a Blank Parchment on a Scry Crystal. You can remotely access the stored position by placing this item on a pedestal near a Scryer's Oculus. Naming this item will allow you to easily recognize it in the Scryer's Oculus interface."
      );
      this.add(
         "ars_nouveau.page.scryers_oculus",
         "Allows you to remotely access Scry Crystals. To use, place Scryer's Scrolls on nearby pedestals and interact with the Oculus to select which Scry Crystal you would like to access. Scry Crystals must be chunk loaded."
      );
      this.add("ars_nouveau.scryer_scroll.craft", "Created by using a Blank Parchment on a Scry Crystal.");
      this.add("item.ars_nouveau.starbuncle_shades", "Starbuncle Shades");
      this.add(
         "tooltip.starbuncle_shades",
         "Using these on a Starbuncle will increase their coolness, but disable their ability to pickup items off the ground or pick Sourceberries."
      );
      this.add(
         "ars_nouveau.page.starbuncle_shades",
         "Using these on a Starbuncle will disable their ability to pick up items off the ground or pick Sourceberries. Wanding the starbuncle will drop the glasses."
      );
      this.add("entity.ars_nouveau.ally_vex", "Summoned Vex");
      this.add("ars_nouveau.scryers_eye.no_scrolls", "Place a Scryer's Scroll on a nearby pedestal.");
      this.add("ars_nouveau.camera.not_loaded", "The block has been removed or is not chunk loaded.");
      this.add("ars_nouveau.page.support_mod", "Support Ars Nouveau!");
      this.add(
         "ars_nouveau.patreon",
         "Join the Ars Nouveau patreon and get a special Discord role, contribute a custom Tome, receive merchandise, summon a Lily dog, and more!"
      );
      this.add("ars_nouveau.patreon_text", "Patreon");
      this.add("entity.ars_nouveau.summon_skeleton", "Summoned Skeleton");
      this.add("item.ars_nouveau.shapers_focus", "Focus of Block Shaping");
      this.add("ars_nouveau.shapers_focus.tooltip", "Can be used to create a block.");
      this.add(
         "ars_nouveau.page1.shapers_focus",
         "A focus that modifies effects that move, create, or modify blocks. Blocks that you move with effects like Launch, Gravity, Pull, Knockback, etc. will now deal damage to entities they hit. Damage is increased by Spell Damage, block hardness, and the speed of the block. Additionally, effects that target or create blocks will duplicate the rest of the spell targeting the new block or moving block."
      );
      this.add("ars_nouveau.shapers_focus.blocks", "Block Targeting");
      this.add(
         "ars_nouveau.page2.shapers_focus",
         "Modifying or creating a block will duplicate the rest of your spell onto that new block. For example, Freeze -> Break will freeze the block, and cast break onto that block. Without the focus, break would only be applied to the block that was hit originally. Effects that will duplicate this target include glyphs such as Conjure Mageblock, Freeze, Break, Exchange, Place Block, and more. Using AOE on these effects will duplicate the spell onto every block."
      );
      this.add(
         "ars_nouveau.page3.shapers_focus",
         "Effects that move blocks will duplicate the rest of the spell onto those moving blocks. To see this in action, try using Conjure Mageblock -> Launch -> Delay -> Knockback to send a block flying in the direction you are looking. This targeting system applies to all moved blocks. Using AOE on block moving effects will let you manipulate many blocks at once."
      );
      this.add("ars_nouveau.shapers_focus.entities", "Entity Targeting");
      this.add("ars_nouveau.shapers_focus.examples", "Spell Examples");
      this.add(
         "ars_nouveau.page4.shapers_focus",
         "$(bold)Throw Ice:$() Conjure Water -> Freeze -> Launch -> Delay -> Knockback. $(bold)Damage and ignite hit targets:$() Conjure Mageblock -> Launch -> Ignite -> Delay -> Knockback. $(bold)Ignite TNT:$() Place Block (TNT) -> Ignite. $(bold)Throw Exploding Terrain:$() Launch -> Delay -> Knockback -> Delay -> Explosion. $(bold)Throw many blocks:$() Launch -> AOE x2 -> Delay -> Knockback. $(bold)Pull blocks:$() Pull -> AOE x2 -> Delay -> Duration Down -> Launch -> Delay -> Knockback. $(bold)Harvest Sand:$() Crush (on stone) -> Aoe -> Break"
      );
      this.add("tooltip.ars_nouveau.shapers_focus", "Duplicates spells that modify or move blocks. Can be used to create damaging moving blocks.");
      this.add("ars_nouveau.tablet_of", "Tablet of %s");
      this.add("ars_nouveau.bound_script", "Bound Script: %s");
      this.add("block.ars_nouveau.sourcestone", "Sourcestone");
      this.add("block.ars_nouveau.sourcestone_mosaic", "Sourcestone: Mosaic");
      this.add("block.ars_nouveau.sourcestone_basketweave", "Sourcestone: Basketweave");
      this.add("block.ars_nouveau.sourcestone_alternating", "Sourcestone: Alternating");
      this.add("block.ars_nouveau.sourcestone_large_bricks", "Sourcestone: Large Bricks");
      this.add("block.ars_nouveau.sourcestone_small_bricks", "Sourcestone: Small Bricks");
      this.add("block.ars_nouveau.smooth_sourcestone", "Smooth Sourcestone");
      this.add("block.ars_nouveau.smooth_sourcestone_mosaic", "Smooth Sourcestone: Mosaic");
      this.add("block.ars_nouveau.smooth_sourcestone_basketweave", "Smooth Sourcestone: Basketweave");
      this.add("block.ars_nouveau.smooth_sourcestone_alternating", "Smooth Sourcestone: Alternating");
      this.add("block.ars_nouveau.smooth_sourcestone_large_bricks", "Smooth Sourcestone: Large Bricks");
      this.add("block.ars_nouveau.smooth_sourcestone_small_bricks", "Smooth Sourcestone: Small Bricks");
      this.add("block.ars_nouveau.gilded_sourcestone_mosaic", "Gilded Sourcestone: Mosaic");
      this.add("block.ars_nouveau.gilded_sourcestone_basketweave", "Gilded Sourcestone: Basketweave");
      this.add("block.ars_nouveau.gilded_sourcestone_alternating", "Gilded Sourcestone: Alternating");
      this.add("block.ars_nouveau.gilded_sourcestone_large_bricks", "Gilded Sourcestone: Large Bricks");
      this.add("block.ars_nouveau.gilded_sourcestone_small_bricks", "Gilded Sourcestone: Small Bricks");
      this.add("block.ars_nouveau.smooth_gilded_sourcestone_mosaic", "Smooth Gilded Sourcestone: Mosaic");
      this.add("block.ars_nouveau.smooth_gilded_sourcestone_basketweave", "Smooth Gilded Sourcestone: Basketweave");
      this.add("block.ars_nouveau.smooth_gilded_sourcestone_alternating", "Smooth Gilded Sourcestone: Alternating");
      this.add("block.ars_nouveau.smooth_gilded_sourcestone_large_bricks", "Smooth Gilded Sourcestone: Large Bricks");
      this.add("block.ars_nouveau.smooth_gilded_sourcestone_small_bricks", "Smooth Gilded Sourcestone: Small Bricks");
      this.add("tooltip.item_scroll", "Use with an item in the offhand to add to the scroll, or scribe on the Scribes table.");
      this.add("item.ars_nouveau.mendosteen_pod", "Mendosteen");
      this.add("item.ars_nouveau.frostaya_pod", "Frostaya");
      this.add("item.ars_nouveau.bombegranate_pod", "Bombegranate");
      this.add("item.ars_nouveau.bastion_pod", "Bastion Fruit");
      this.add("item.ars_nouveau.alchemists_crown", "Alchemist's Crown");
      this.add("block.ars_nouveau.potion_diffuser", "Potion Diffuser");
      this.add("block.ars_nouveau.bastion_pod", "Bastion Fruit");
      this.add("block.ars_nouveau.bombegranate_pod", "Bombegranate");
      this.add("block.ars_nouveau.frostaya_pod", "Frostaya");
      this.add("block.ars_nouveau.intangible_air", "Intangible Air");
      this.add("block.ars_nouveau.mendosteen_pod", "Mendosteen");
      this.add("block.ars_nouveau.redstone_air", "Redstone Air");
      this.add("entity.ars_nouveau.dummy", "Dummy");
      this.add("entity.ars_nouveau.enchanted_falling_block", "Enchanted Falling Block");
      this.add("entity.ars_nouveau.enchanted_mage_block", "Enchanted Falling Block");
      this.add("entity.ars_nouveau.enchanted_head_block", "Enchanted Falling Block");
      this.add("entity.ars_nouveau.familiar_jabberwog", "Familiar Jabberwog");
      this.add("entity.ars_nouveau.fangs", "Fangs");
      this.add("entity.ars_nouveau.ritual", "Ritual");
      this.add(
         "ars_nouveau.melder.from_capped", "Melders can only meld two jars. Clear the melder by using the Dominion Wand while sneaking to remove all jars."
      );
      this.add("ars_nouveau.melder.to_set", "Melder will send combined potions to this jar.");
      this.add("ars_nouveau.melder.from_set", "%s of 2 melding jars set.");
      this.add("ars_nouveau.melder.no_to_pos", "Wand the Melder to your desired Potion Jar.");
      this.add("ars_nouveau.melder.needs_potion", "Linked jars need potion");
      this.add("ars_nouveau.melder.destination_invalid", "Destination cannot accept the mixed potion.");
      this.add("arsnouveau.debug.log_created", "Log file created: %s");
      this.add("ars_nouveau.page.archwood_forest", "Archwood Forest");
      this.add(
         "ars_nouveau.page1.archwood_forest",
         "The Archwood Forest is a somewhat rare biome filled with magical lights and archwood trees. It contains an increased amount of gold. Additionally, magical creatures such as Starbuncles, Whirlisprigs, Archwood Treants, and Drygmys have a much higher chance of spawning. Terrablender is required to be installed to generate this biome."
      );
      this.add("ars_nouveau.melder.too_far", "Jars must be within 3 blocks.");
      this.add("ars_nouveau.starbuncle.storing_potions", "Storing potions at %s locations");
      this.add("ars_nouveau.starbuncle.taking_potions", "Taking potions from %s locations");
      this.add("ars_nouveau.starbuncle.potion_to", "Starbuncle will send potions to this jar.");
      this.add("ars_nouveau.starbuncle.potion_from", "Starbuncle will take potions from this jar.");
      this.add("ars_nouveau.dominion_wand.cleared", "Dominion wand cleared.");
      this.add("ars_nouveau.starbuncle.potion_behavior_set", "Starbuncle will now transport potions!");
      this.add("key.ars_nouveau.qc1", "Quick Cast Slot 01");
      this.add("key.ars_nouveau.qc2", "Quick Cast Slot 02");
      this.add("key.ars_nouveau.qc3", "Quick Cast Slot 03");
      this.add("key.ars_nouveau.qc4", "Quick Cast Slot 04");
      this.add("key.ars_nouveau.qc5", "Quick Cast Slot 05");
      this.add("key.ars_nouveau.qc6", "Quick Cast Slot 06");
      this.add("key.ars_nouveau.qc7", "Quick Cast Slot 07");
      this.add("key.ars_nouveau.qc8", "Quick Cast Slot 08");
      this.add("key.ars_nouveau.qc9", "Quick Cast Slot 09");
      this.add("key.ars_nouveau.qc10", "Quick Cast Slot 10");
      this.add("item.minecraft.splash_potion.effect.recovery_potion_strong", "Splash Potion of Recovery");
      this.add("item.minecraft.splash_potion.effect.recovery_potion", "Splash Potion of Recovery");
      this.add("item.minecraft.splash_potion.effect.recovery_potion_long", "Splash Potion of Recovery");
      this.add("item.minecraft.potion.effect.recovery_potion_strong", "Potion of Recovery");
      this.add("item.minecraft.potion.effect.recovery_potion", "Potion of Recovery");
      this.add("item.minecraft.potion.effect.recovery_potion_long", "Potion of Recovery");
      this.add("item.minecraft.lingering_potion.effect.recovery_potion_strong", "Lingering Potion of Recovery");
      this.add("item.minecraft.lingering_potion.effect.recovery_potion", "Lingering Potion of Recovery");
      this.add("item.minecraft.lingering_potion.effect.recovery_potion_long", "Lingering Potion of Recovery");
      this.add("item.minecraft.tipped_arrow.effect.recovery_potion_strong", "Arrow of Recovery");
      this.add("item.minecraft.tipped_arrow.effect.recovery_potion", "Arrow of Recovery");
      this.add("item.minecraft.tipped_arrow.effect.recovery_potion_long", "Arrow of Recovery");
      this.add("item.minecraft.splash_potion.effect.blasting_potion_strong", "Splash Potion of Blasting");
      this.add("item.minecraft.splash_potion.effect.blasting_potion", "Splash Potion of Blasting");
      this.add("item.minecraft.splash_potion.effect.blasting_potion_long", "Splash Potion of Blasting");
      this.add("item.minecraft.potion.effect.blasting_potion_strong", "Potion of Blasting");
      this.add("item.minecraft.potion.effect.blasting_potion", "Potion of Blasting");
      this.add("item.minecraft.potion.effect.blasting_potion_long", "Potion of Blasting");
      this.add("item.minecraft.lingering_potion.effect.blasting_potion_strong", "Lingering Potion of Blasting");
      this.add("item.minecraft.lingering_potion.effect.blasting_potion", "Lingering Potion of Blasting");
      this.add("item.minecraft.lingering_potion.effect.blasting_potion_long", "Lingering Potion of Blasting");
      this.add("item.minecraft.tipped_arrow.effect.blasting_potion_strong", "Arrow of Blasting");
      this.add("item.minecraft.tipped_arrow.effect.blasting_potion", "Arrow of Blasting");
      this.add("item.minecraft.tipped_arrow.effect.blasting_potion_long", "Arrow of Blasting");
      this.add("item.minecraft.splash_potion.effect.freezing_potion_strong", "Splash Potion of Freezing");
      this.add("item.minecraft.splash_potion.effect.freezing_potion", "Splash Potion of Freezing");
      this.add("item.minecraft.splash_potion.effect.freezing_potion_long", "Splash Potion of Freezing");
      this.add("item.minecraft.potion.effect.freezing_potion_strong", "Potion of Freezing");
      this.add("item.minecraft.potion.effect.freezing_potion", "Potion of Freezing");
      this.add("item.minecraft.potion.effect.freezing_potion_long", "Potion of Freezing");
      this.add("item.minecraft.lingering_potion.effect.freezing_potion_strong", "Lingering Potion of Freezing");
      this.add("item.minecraft.lingering_potion.effect.freezing_potion", "Lingering Potion of Freezing");
      this.add("item.minecraft.lingering_potion.effect.freezing_potion_long", "Lingering Potion of Freezing");
      this.add("item.minecraft.tipped_arrow.effect.freezing_potion_strong", "Arrow of Freezing");
      this.add("item.minecraft.tipped_arrow.effect.freezing_potion", "Arrow of Freezing");
      this.add("item.minecraft.tipped_arrow.effect.freezing_potion_long", "Arrow of Freezing");
      this.add("item.minecraft.splash_potion.effect.shielding_potion_strong", "Splash Potion of Shielding");
      this.add("item.minecraft.splash_potion.effect.shielding_potion", "Splash Potion of Shielding");
      this.add("item.minecraft.splash_potion.effect.shielding_potion_long", "Splash Potion of Shielding");
      this.add("item.minecraft.potion.effect.shielding_potion_strong", "Potion of Shielding");
      this.add("item.minecraft.potion.effect.shielding_potion", "Potion of Shielding");
      this.add("item.minecraft.potion.effect.shielding_potion_long", "Potion of Shielding");
      this.add("item.minecraft.lingering_potion.effect.shielding_potion_strong", "Lingering Potion of Shielding");
      this.add("item.minecraft.lingering_potion.effect.shielding_potion", "Lingering Potion of Shielding");
      this.add("item.minecraft.lingering_potion.effect.shielding_potion_long", "Lingering Potion of Shielding");
      this.add("item.minecraft.tipped_arrow.effect.shielding_potion_strong", "Arrow of Shielding");
      this.add("item.minecraft.tipped_arrow.effect.shielding_potion", "Arrow of Shielding");
      this.add("item.minecraft.tipped_arrow.effect.shielding_potion_long", "Arrow of Shielding");
      this.add("effect.ars_nouveau.recovery", "Recovery");
      this.add("effect.ars_nouveau.blasting", "Blasting");
      this.add("effect.ars_nouveau.freezing", "Freezing");
      this.add("effect.ars_nouveau.shielding", "Shielding");
      this.add("tooltip.ars_nouveau.alchemists_crown", "");
      this.add("item.ars_nouveau.wixie_hat", "Wixie Hat");
      this.add("tooltip.ars_nouveau.wixie_hat", "Give to a Starbuncle to make them transport potions.");
      this.add("ars_nouveau.starbuncle.default_behavior", "Starbuncle will now transport items!");
      this.add("ars_nouveau.potion_diffuser.set_pos", "Diffuser will spread this potion to nearby entities.");
      this.add("ars_nouveau.potion_diffuser.bind_to_jar", "You must bind the diffuser to a Potion Jar to use it.");
      this.add("ars_nouveau.potion_diffuser.no_pos", "Bind a Potion Jar to the diffuser using the Dominion Wand.");
      this.add("ars_nouveau.potion_diffuser.off", "Diffuser is off.");
      this.add("key.ars_nouveau.head_curio_hotkey", "Head Curio Menu");
      this.add("ars_nouveau.alchemists_crown.no_flasks", "No flasks or potions equipped.");
      this.add("ars_nouveau.page.alchemists_crown", "Allows the wearer to consume potions and flasks instantly from their inventory.");
      this.add("ars_nouveau.tooltip.alchemists_crown", "Press %s to open the potion radial menu.");
      this.add(
         "ars_nouveau.page.wixie_hat",
         "Allows starbuncles to transport potions. Once wearing a Wixie Hat, use the Dominion Wand to connect them between Potion Jars."
      );
      this.add(
         "ars_nouveau.page.potion_diffuser",
         "Consumes a potion and applies it to nearby entities, greatly extending the use of the potion. To use, bind a Potion Jar to the diffuser using the Dominion Wand. Every 10 minutes the diffuser will consume a single potion and apply it every few seconds."
      );
      this.add(
         "ars_nouveau.page.bombegrante",
         "A fruit that packs an explosive punch. Can be brewed into a Potion of Blasting, causing the target to explode when the duration ends."
      );
      this.add(
         "ars_nouveau.page.mendosteen", "A fruit that can be brewed into a Potion of Recovery, increases the amount of healing received from all sources."
      );
      this.add("ars_nouveau.page.frostaya", "A fruit that can be brewed into a Potion of Freezing, freezing the target over time.");
      this.add("ars_nouveau.page.bastion_fruit", "A fruit that can be brewed into a Potion of Defence, reducing the amount of damage taken.");
      this.add("ars_nouveau.wixie_familiar.applied", "Wixie applies %s");
      this.add("ars_nouveau.flask_cannon.no_potion", "No potions in inventory.");
      this.add("item.ars_nouveau.lingering_flask_cannon", "Lingering Flask Cannon");
      this.add("item.ars_nouveau.splash_flask_cannon", "Splash Flask Cannon");
      this.add("ars_nouveau.page.flask_cannons", "Flask Cannons");
      this.add(
         "ars_nouveau.page1.flask_cannons",
         "Flask Cannons can consume potions from bottles and flasks from the players inventory and convert the potion into a Splash or Lingering potion. To select the potion to be thrown, use the Radial Menu to select your flask or potion and use the launcher."
      );
      this.add("ars_nouveau.imbuement.crafting_started", "Crafting %s. Add source nearby to increase craft speed.");
      this.add("ars_nouveau.crafting_progress", "Crafting Progress: %s");
      this.add("ars_nouveau.scribes_table.throw_items", "Toss remaining items onto the table.");
      this.add("ars_nouveau.scribes_table.started_crafting", "Toss items as they appear above to complete crafting.");
      this.add("ars_nouveau.gui.discord", "Join the Discord for spells, updates, and support!");
      this.add("entity.ars_nouveau.an_lightning", "Lightning");
      this.add("entity.ars_nouveau.flying_item", "Flying Item");
      this.add("entity.ars_nouveau.follow_proj", "Projectile");
      this.add("entity.ars_nouveau.linger", "Linger");
      this.add("entity.ars_nouveau.orbit", "Orbit");
      this.add("entity.ars_nouveau.scryer_camera", "Scryer Camera");
      this.add("entity.ars_nouveau.spell_arrow", "Spell Arrow");
      this.add("entity.ars_nouveau.spell_proj", "Spell Projectile");
      this.add("ars_nouveau.armor", "Armor and Perks");
      this.add("ars_nouveau.page.armor_upgrading", "Armor Upgrading");
      this.add("ars_nouveau.armor_tiers", "Armor Tiers");
      this.add(
         "ars_nouveau.armor_desc",
         "Threads can be slotted into magical armor to provide additional effects. Armor can be upgraded to new tiers, unlocking additional and more powerful thread slots."
      );
      this.add(
         "ars_nouveau.page1.armor_upgrading",
         "Magical armor can be upgraded with Threads to provide additional effects. Each type of armor has a different number of slots, and those slots vary in size. Larger slots will increase the power of threads, and some threads require a slot of a certain size or larger. To apply threads, see the section on the Alteration Table."
      );
      this.add(
         "ars_nouveau.page2.armor_upgrading",
         "Armor also has three tiers, and these tiers may be increased using the Enchanting Apparatus and the upgrade recipes found in this section. Each tier will increase the amount of mana regen the armor grants, and increases the number and size of the slots of the armor."
      );
      this.add("block.ars_nouveau.alteration_table", "Alteration Table");
      this.add("ars_nouveau.page.alteration_table", "Alteration Table");
      this.add(
         "ars_nouveau.page1.alteration_table",
         "Used to inscribe Threads onto magical armors. To use the table, place armor onto the stand of the table. The tablet will display the available Thread Slots on the armor. To add or remove a Thread, use the thread on the tablet of the table. Removing the armor will apply the threads to the armor. To remove a thread, place the armor on the table and interact with the display with an empty hand."
      );
      this.add("item.ars_nouveau.blank_thread", "Blank Thread");
      this.add("tooltip.ars_nouveau.blank_thread", "Used to craft threads that may be used to empower your magical armor.");
      this.add("ars_nouveau.perk.invalid_for_slot", "This perk requires a level %s or higher slot.");
      this.add("ars_nouveau.perk.warding", "Warding");
      this.add("ars_nouveau.perk.mana_regen", "Mana Regen");
      this.add("ars_nouveau.perk.percent_max_mana", "Max Mana");
      this.add("ars_nouveau.perk.flat_max_mana", "Max Mana");
      this.add("ars_nouveau.perk.spell_damage", "Spell Power");
      this.add("ars_nouveau.perk.saturation", "Bonus Saturation");
      this.add("ars_nouveau.perk.wixie", "Potion Duration");
      this.add("ars_nouveau.perk.jump_height", "Jump Power");
      this.add("ars_nouveau.perk.feather", "Feather Falling");
      this.add(
         "ars_nouveau.page.threads",
         "Each type of armor has its own unique set of Thread Slots. Upgrading the armor to a new tier will unlock and add additional slots to the armor. The Sorcerors set provides the least amount of defence while providing the most powerful slots, while the Battlemage's set provides defence but much weaker slots. For recipes on upgrading your armor to the next tier, see the section in the Armor and Perks category."
      );
      this.add("ars_nouveau.threads", "Thread Slots");
      this.add("ars_nouveau.thread_layout", "Thread Tiers");
      this.add("ars_nouveau.page.applying_perks", "Applying Threads");
      this.add(
         "ars_nouveau.page1.applying_perks",
         "Threads may be applied to armor using the Alteration Apparatus. Each piece of armor has a unique set of Thread Slots with their own variety of levels. Some Threads may require a Thread Slot of a minimum level, but many threads simply increase in power based on the slot they are given."
      );
      this.add(
         "ars_nouveau.page2.applying_perks",
         "Threads only apply a single time on an entire armor set, and they do not stack. For more information on applying threads, see the Alteration Table."
      );
      this.add("ars_nouveau.perk.not_perk", "Use a thread to apply it onto the selected armor.");
      this.add("ars_nouveau.perk.set_armor", "Place armor onto the table.");
      this.add("ars_nouveau.perk.max_perks", "This armor has reached its maximum number of perks.");
      this.add("ars_nouveau.page.layout_desc", "Each set of armor has its own unique set of Thread Slots.");
      this.add("ars_nouveau.armor_upgrade.book_desc", "Accepts Tier %s Armor");
      this.add("ars_nouveau.spell_write.book_desc", "Accepts Reactive Items");
      this.add(
         "ars_nouveau.perks.duplicated", "You have equipped armor that contains a perk you already have. You will only receive the effect of the perk once."
      );
      this.add("ars_nouveau.totem_perk.trigger", "Thread of Undying will reactive the next time you sleep.");
      this.add("ars_nouveau.totem_perk.active", "Thread of Undying is now active.");
      this.add("ars_nouveau.sound.fire_family", "Fire Family");
      this.add("ars_nouveau.sound.default_family", "Default");
      this.add("ars_nouveau.sound.tempestry_family", "Tempestry Family");
      this.add("ars_nouveau.sound.gaia_family", "Gaia Family");
      this.add("block.ars_nouveau.mob_jar", "Containment Jar");
      this.add("ars_nouveau.page.mob_jar", "Containment Jar");
      this.add(
         "ars_nouveau.page1.mob_jar",
         "Allows you to capture and store mobs for transportation or decoration. To capture a mob, you must perform a Ritual of Containment. See the Ritual of Containment for more info. To release a mob, cast Dispel on the jar and the mob will be released above the jar. Note Blocks placed above a jar will play an ambient sound of the mob inside."
      );
      this.add(
         "ars_nouveau.page2.mob_jar",
         "Many entities can be interacted with inside the jar and will continue to simulate while inside the jar. Some examples include: chickens will lay eggs, sheep can be sheared, cows can be milked, and some mobs like the Blaze will turn the jar into a light source. Experiment with a variety of mobs to create aesthetic and functional farms. Drygmys will also treat the jar as if it were a normal entity in the area."
      );
      this.add("ars_nouveau.title.mob_jar", "Functionality");
      this.add("ars_nouveau.advancement.title.root", "Ars Nouveau");
      this.add("ars_nouveau.advancement.desc.root", "Acquire a Worn Notebook");
      this.add("ars_nouveau.adv.title.ritual_brazier", "Brazen");
      this.add("ars_nouveau.adv.desc.ritual_brazier", "Acquire a Ritual Brazier");
      this.add("ars_nouveau.adv.title.familiar", "This Seems Familiar...");
      this.add("ars_nouveau.adv.desc.familiar", "Bind a magical creature into a Familiar");
      this.add("ars_nouveau.adv.title.amethyst_golem_charm", "Purple Amethyst Eater");
      this.add("ars_nouveau.adv.desc.amethyst_golem_charm", "Acquire an Amethyst Golem Charm");
      this.add("ars_nouveau.adv.title.novice_spell_book", "Unbreaking X");
      this.add("ars_nouveau.adv.desc.novice_spell_book", "Acquire a Novice Spell Book");
      this.add("ars_nouveau.adv.title.apprentice_spell_book", "Mage's Spell Book");
      this.add("ars_nouveau.adv.desc.apprentice_spell_book", "Acquire a Mage's Spell Book");
      this.add("ars_nouveau.adv.title.archmage_spell_book", "Archmage Spell Book");
      this.add("ars_nouveau.adv.desc.archmage_spell_book", "Acquire an Archmage Spell Book");
      this.add("ars_nouveau.adv.title.shapers_focus", "Throw Another Rock!");
      this.add("ars_nouveau.adv.desc.shapers_focus", "Acquire a Shaper's Focus");
      this.add("ars_nouveau.adv.title.imbuement_chamber", "Imbued");
      this.add("ars_nouveau.adv.desc.imbuement_chamber", "Acquire an Imbuement Chamber");
      this.add("ars_nouveau.adv.title.eat_bombegranate", "To Die For");
      this.add("ars_nouveau.adv.desc.eat_bombegranate", "Eat a Bombegranate");
      this.add("ars_nouveau.adv.title.poof_mob", "Where did it go?");
      this.add("ars_nouveau.adv.desc.poof_mob", "Make a \"Trade\" with a Magical Creature");
      this.add("ars_nouveau.adv.title.enchanting_apparatus", "Magic Mod Mechanic");
      this.add("ars_nouveau.adv.desc.enchanting_apparatus", "Acquire an Enchanting Apparatus");
      this.add("ars_nouveau.adv.title.starby_charm", "The Cutest Hopper");
      this.add("ars_nouveau.adv.desc.starby_charm", "Acquire a Starbuncle Charm");
      this.add("ars_nouveau.adv.title.wixie_charm", "Free the Inner Child");
      this.add("ars_nouveau.adv.desc.wixie_charm", "Acquire a Wixie Charm");
      this.add("ars_nouveau.adv.title.whirlisprig_charm", "Whirli");
      this.add("ars_nouveau.adv.desc.whirlisprig_charm", "Acquire a Whirlisprig Charm");
      this.add("ars_nouveau.adv.title.magebloom_crop", "Magebloom");
      this.add("ars_nouveau.adv.desc.magebloom_crop", "Acquire a Magebloom Seed");
      this.add("ars_nouveau.adv.title.basic_spell_turret", "It's a Magic Mod, I Swear!");
      this.add("ars_nouveau.adv.desc.basic_spell_turret", "Acquire a Basic Spell Turret");
      this.add("ars_nouveau.adv.title.spell_prism", "Prismatic Redirection");
      this.add("ars_nouveau.adv.desc.spell_prism", "Acquire a Spell Prism");
      this.add("ars_nouveau.adv.title.scryers_oculus", "Eye Spy");
      this.add("ars_nouveau.adv.desc.scryers_oculus", "Acquire a Scryer's Oculus");
      this.add("ars_nouveau.adv.title.potion_jar", "Potion Storage");
      this.add("ars_nouveau.adv.desc.potion_jar", "Acquire a Potion Jar");
      this.add("ars_nouveau.adv.title.alteration_table", "Armor Alteration");
      this.add("ars_nouveau.adv.desc.alteration_table", "Acquire an Alteration Table");
      this.add("ars_nouveau.adv.desc.blank_thread", "Acquire a Blank Thread");
      this.add("ars_nouveau.adv.title.blank_thread", "Empty Canvas");
      this.add("ars_nouveau.adv.title.potion_melder", "Meld'em, Mash'em, Stick'em in a Cauldron");
      this.add("ars_nouveau.adv.desc.potion_melder", "Acquire a Potion Melder");
      this.add("ars_nouveau.adv.title.potion_diffuser", "Do you smell that?");
      this.add("ars_nouveau.adv.desc.potion_diffuser", "Acquire a Potion Diffuser");
      this.add("ars_nouveau.adv.title.potion_flask", "Stackable Potions");
      this.add("ars_nouveau.adv.desc.potion_flask", "Acquire a Potion Flask");
      this.add("ars_nouveau.adv.title.wilden_tribute", "Wilden Tribute");
      this.add("ars_nouveau.adv.desc.wilden_tribute", "Acquire a Wilden Tribute");
      this.add("ars_nouveau.adv.title.summon_focus", "Summoning Focus");
      this.add("ars_nouveau.adv.desc.summon_focus", "Acquire a Summoning Focus");
      this.add("ars_nouveau.adv.title.wixie_hat", "Potion Transport");
      this.add("ars_nouveau.adv.desc.wixie_hat", "Acquire a Wixie Hat");
      this.add("ars_nouveau.adv.title.starbuncle_shades", "One Cool Starbuncle");
      this.add("ars_nouveau.adv.desc.starbuncle_shades", "Acquire Starbuncle Shades");
      this.add("ars_nouveau.adv.title.wilden_explosion", "Wait, it can fly?");
      this.add("ars_nouveau.adv.desc.wilden_explosion", "Witness an explosive divebomb");
      this.add("ars_nouveau.adv.title.prismatic", "Thinking with Prisms");
      this.add("ars_nouveau.adv.desc.prismatic", "Redirect a Spell using a Prism four or more times");
      this.add("ars_nouveau.adv.title.create_portal", "Thinking with...wrong game");
      this.add("ars_nouveau.adv.desc.create_portal", "Create a Warp Portal");
      this.add("ars_nouveau.adv.title.warp_scroll", "Warped");
      this.add("ars_nouveau.adv.desc.warp_scroll", "Acquire a Warp Scroll");
      this.add("ars_nouveau.adv.title.drygmy_charm", "Moose Dance");
      this.add("ars_nouveau.adv.desc.drygmy_charm", "Acquire a Drygmy Charm");
      this.add("ars_nouveau.adv.title.source_jar", "Not a Fluid!");
      this.add("ars_nouveau.adv.desc.source_jar", "Acquire a Source Jar");
      this.add("ars_nouveau.adv.title.mob_jar", "Tiny Home");
      this.add("ars_nouveau.adv.desc.mob_jar", "Acquire a Containment Jar");
      this.add("ars_nouveau.adv.title.shrunk_starbuncle", "Honey I Shrunk the Starbuncle");
      this.add("ars_nouveau.adv.desc.shrunk_starbuncle", "Put a Starbuncle in a Containment Jar");
      this.add("ars_nouveau.adv.title.ritual_gravity", "You're Grounded");
      this.add("ars_nouveau.adv.desc.ritual_gravity", "Be affected by a Ritual of Gravity");
      this.add("block.ars_nouveau.void_prism", "Void Prism");
      this.add("ars_nouveau.page.void_prism", "Destroys any spell projectiles that pass through it.");
      this.add("item.ars_nouveau.music_disc_aria_biblio", "Music Disc");
      this.add("item.ars_nouveau.music_disc_aria_biblio.desc", "Firel - Aria Biblio");
      this.add("item.ars_nouveau.starby_gift", "Starbuncle Gift");
      this.add("ars_nouveau.present.give", "Give this to a friend and they will get bonus items, or open it for yourself!");
      this.add("ars_nouveau.present.from", "A gift from %s");
      this.add("entity.ars_nouveau.gift_starby", "Starbuncle");
      this.add("block.ars_nouveau.falseweave", "Falseweave");
      this.add("block.ars_nouveau.ghostweave", "Ghostweave");
      this.add("block.ars_nouveau.mirrorweave", "Mirrorweave");
      this.add("block.ars_nouveau.magebloom_block", "Magebloom Block");
      this.add("ars_nouveau.page.illusion_blocks", "Illusion Blocks");
      this.add(
         "ars_nouveau.page1.illusion_blocks",
         "Mirrorweave can replicate the appearance of any block that is used on it. These blocks will take on the same collisions and light as their replicated block. Sense Magic will reveal the illusion. All illusion blocks can also be used as frames for portals. Casting light on an illusion block will allow it to emit light."
      );
      this.add(
         "ars_nouveau.page2.illusion_blocks",
         "Falseweave has the same properties as Mirrorweave, but can be passed through as if it were air. Sense Magic will cause the block to become invisible, revealing any hidden paths."
      );
      this.add(
         "ars_nouveau.page3.illusion_blocks",
         "Ghostweave has the same properties as Mirrorweave, but can be turned into an invisible wall by casting Invisbility on it. Casting Dispel will reveal the block again. Sense Magic will cause the block to appear solid again."
      );
      this.add("item.ars_nouveau.spell_crossbow", "Enchanter's Crossbow");
      this.add(
         "ars_nouveau.page1.spell_crossbow",
         "A crossbow that can be inscribed with a spell. The mana cost of the spell will be deducted when the bow is loaded if mana is present. Enchanter's Crossbows may use special augment arrows to empower their spells."
      );
      this.add("ars_nouveau.tooltip.from_blank", "Created by scribing a Blank Parchment in the scribes table.");
      this.add("ars_nouveau.manipulation_essence.tooltip", "Can be scribed onto a Spell Parchment or caster tool to permanently hide the spell.");
      this.add("ars_nouveau.spell_hidden", "Spell is now hidden.");
      this.add("entity.minecraft.villager.ars_nouveau.shady_wizard", "Shady Wizard");
      this.add("entity.minecraft.villager.shady_wizard", "Shady Wizard");
      this.add("death.attack.an_enchantedBlock", "%1$s was crushed by %2$s magic blocks");
      this.add("death.attack.freeze.item", "%1$s was frozen to death by %2$s using %3$");
      this.add("block.ars_nouveau.magelight_torch", "Magelight Torch");
      this.add("block.ars_nouveau.arcane_platform", "Arcane Platform");
      this.add("ars_nouveau.arcane_platform.tooltip", "Can be placed in any direction and can be used in place of a pedestal.");
      this.add("ars_nouveau.brazier_relay.connected", "Brazier connected.");
      this.add("block.ars_nouveau.brazier_relay", "Ritual Brazier Relay");
      this.add(
         "ars_nouveau.page.brazier_relay",
         "Allows the users to bind a Ritual Brazier ritual to a new location. Multiple rituals can be connected to the same brazier relay. To connect, use the Dominion Wand on your Ritual Brazier, then the relay. Source is consumed at the original braziers location. Can be bound within 15 blocks."
      );
      this.add("item.ars_nouveau.stable_warp_scroll", "Stabilized Warp Scroll");
      this.add("overworld.minecraft.name", "Overworld");
      this.add("the_nether.minecraft.name", "The Nether");
      this.add("the_end.minecraft.name", "The End");
      this.add(
         "ars_nouveau.page.stable_warp_scroll",
         "Creates a temporary Warp Portal when used on a block. This scroll is not consumed on use and can teleport between dimensions. Using a Stabilized Warp Scroll to create a Warp Portal will create a cross dimension warp portal, but consume the scroll. This scroll may only be bound to a location a single time."
      );
      this.add("ars_nouveau.adv.title.catch_lightning", "Lightning in a Bottle");
      this.add("ars_nouveau.adv.desc.catch_lightning", "Catch a lightning bolt in a containment jar.");
      this.add("ars_nouveau.adv.title.time_in_a_bottle", "Time in a Bottle");
      this.add("ars_nouveau.adv.desc.time_in_a_bottle", "Put a clock in a containment jar");
      this.add("ars_nouveau.light_message", "Ars Nouveau adds built in dynamic lights. Enable with `/ars-light on`. This won't appear again!");
      this.add("ars_nouveau.warp_scroll.already_recorded", "This scroll is already bound to a location.");
      this.add(
         "ars_nouveau.page4.illusion_blocks",
         "Skyweave will display the skybox of the dimension it is placed in. To toggle the skybox and show the facade, cast Dispel on it. The facade may be set to another block like Mirrorweave."
      );
      this.add("block.ars_nouveau.sky_block", "Skyweave");
      this.add("ars_nouveau.scry_caster.not_crystal", "The bound crystal is no longer present.");
      this.add("ars_nouveau.scry_caster.no_pos", "No position set. Use this on a Scry Crystal or hold a Scryer's Scroll in the offhand.");
      this.add("ars_nouveau.scry_caster.invalid_behavior", "Invalid spell bound. Rebind a new valid spell.");
      this.add("entity.ars_nouveau.animated_block", "Animated Block");
      this.add("entity.ars_nouveau.animated_head", "Animated Head");
      this.add("item.ars_nouveau.enchanters_eye", "Enchanter's Eye");
      this.add(
         "ars_nouveau.page.enchanters_eye",
         "A caster tool that can cast inscribed spells remotely through a Scry Crystal. Use the eye on a Scry Crystal to bind the location, or hold a bound Scry Parchment in the offhand. Spells will be cast through the eye similar to a Spell Turret, but you are considered the caster for all effects. Useful for remote teleportation or item movement."
      );
      this.add("ars_nouveau.warp_scroll.disabled_warp_portal", "Permanent warp portals are currently disabled");
      this.add("ars_nouveau.lectern_out_of_range", "The lectern is out of range.");
      this.add("tooltip.ars_nouveau.items_missing", "Items Missing");
      this.add("narrator.ars_nouveau.search", "Search");
      this.add("tooltip.ars_nouveau.amount", "Total: %s");
      this.add("tooltip.ars_nouveau.sorting_1", "Sorting Alphabetically");
      this.add("tooltip.ars_nouveau.sorting_0", "Sorting by Amount");
      this.add("ars_nouveau.storage.from_set", "Inventory added.");
      this.add("ars_nouveau.storage.too_many", "Maximum number of inventories reached. Add more Bookwyrms to increase the limit.");
      this.add("ars_nouveau.storage.num_connected", "%s connected inventories");
      this.add("ars_nouveau.storage_lectern", "Storage Lectern");
      this.add("tooltip.ars_nouveau.search_0", "Non-synced Search");
      this.add("tooltip.ars_nouveau.search_1", "Synced Search");
      this.add("ars_nouveau.invalid_lectern", "Invalid lectern connected.");
      this.add("ars_nouveau.storage.lectern_chained", "Linked to x: %s y: %s z: %s");
      this.add("ars_nouveau.storage.num_bookwyrms", "%s Bookwyrms");
      this.add("ars_nouveau.storage.removed", "Inventory removed.");
      this.add("ars_nouveau.storage.not_lectern", "Connect to another Lectern to connect the views.");
      this.add("ars_nouveau.storage.lectern_too_far", "Lectern must be within 30 blocks.");
      this.add("ars_nouveau.storage.inv_too_far", "Inventory must be within 30 blocks.");
      this.add("ars_nouveau.storage.no_tile", "Not a valid inventory.");
      this.add("block.ars_nouveau.storage_lectern", "Storage Lectern");
      this.add(
         "ars_nouveau.page1.storage_lectern",
         "The Storage Lectern can used to view, manage, and craft from multiple connected inventories. The number of inventories that may be connected is determined by the number of Bookwyrms bound to the lectern. You can add more Bookwyrms to the lectern by using a Bookwyrm Charm. Use the Dominion Wand from an inventory to the lectern in order to bind or remove access. Inventories can be connected 30 blocks away."
      );
      this.add("ars_nouveau.storage", "Advanced Usage");
      this.add(
         "ars_nouveau.page2.storage",
         "Items can be automatically inserted into the lectern by placing them into an unbound inventory that is adjacent to the lectern. You may also link a lectern to the 'main' lectern in order to extend the view and access of the original lectern, these lecterns can be chained together within 30 blocks indefinitely. Once a lectern is linked to another lectern, it will no longer be able to connect to inventories or accept bookwyrms."
      );
      this.add("ars_nouveau.tooltip.bookwyrm", "Obtained by augmenting a Ritual of Awakening with Book and Quills.");
      this.add("ars_nouveau.item_detector.count", "Emit at %s");
      this.add("block.ars_nouveau.item_detector", "Display Case");
      this.add("ars_nouveau.item_detector.item", "%s");
      this.add("ars_nouveau.item_detector.powered", "Powered: %s");
      this.add("ars_nouveau.item_detector.connected", "Detecting items at x: %s y: %s z: %s");
      this.add(
         "ars_nouveau.page1.item_detector",
         "A Display Case can be configured to output a redstone signal when a certain level of inventory is reached. To set the item for tracking, use an item on the case. Interact with the block to increase the count, and punch to decrease. To link to an inventory, Dominion Wand an inventory to the display case. Wanding while sneaking will invert if the signal outputs below or greater than the set count."
      );
      this.add("ars_nouveau.item_detector.inverted", "Signal Inverted: %s");
      this.add("block.ars_nouveau.repository", "Repository");
      this.add("ars_nouveau.removed_familiars", "Dispelled Familiars");
      this.add("key.ars_nouveau.familiar_toggle", "Summon/Dispel Familiar");
      this.add("ars_nouveau.spell_book_gui.dispel", "Dispel");
      this.add("tooltip.ars_nouveau.master_tab", "All Items");
      this.add(
         "ars_nouveau.page1.repository",
         "A repository can store a double chests worth of items. When named, it will display the name as a tooltip, and preserve it when dropped as an item. Useful for creating named inventory tabs with the Storage Lectern."
      );
      this.add("ars_nouveau.storage_tabs", "Storage Tabs");
      this.add(
         "ars_nouveau.page3.storage",
         "Linked inventories that are named will create a tab in the Storage Lectern, allowing you to view and manipulate all inventories that share that name. Unlike normal chests, Repositories will preserve their name when dropped. The Name Effect can also name inventories placed in the world."
      );
      this.add("ars_nouveau.page2.item_detector", "If a Filter Scroll is given to the Display Case, it will count all items that match the filter.");
      this.add("config.jade.plugin_ars_nouveau.mob_jar", "Mob Jar");
      this.add("mob_jar.villager", "Can be traded with in a jar, periodically resetting its trades.");
      this.add("mob_jar.piglin", "Can be traded with by throwing gold ingots at the jar.");
      this.add("mob_jar.ender_dragon", "Use a bottle to obtain Dragon's Breath.");
      this.add("mob_jar.sheep", "Can be sheared. Will eat Grass beneath the jar if available.");
      this.add("mob_jar.chicken", "Will occasionally lay eggs.");
      this.add("mob_jar.cow", "Can be milked.");
      this.add("mob_jar.mooshroom", "Can use a bowl to obtain mushroom stew. Can be sheared into a normal cow.");
      this.add("mob_jar.pufferfish", "Will inflate when mobs are nearby. While inflated, the jar produces a redstone signal.");
      this.add("mob_jar.frog", "Will eat nearby slimes.");
      this.add("mob_jar.panda", "Baby pandas will occasionally sneeze, creating Slimeballs. Sick Pandas will sneeze more often.");
      this.add("mob_jar.allay.title", "Allay Behavior");
      this.add(
         "mob_jar.allay",
         "A jarred Allay can pickup and deposit items within 5 blocks of the jar. Giving an Allay an item will cause it to only pickup that item. Giving an Allay an Item Scroll will pickup any item that matches the scroll. Items will be deposited into inventories placed adjacent to the jar. Allays will also respect any filters placed on the adjacent inventories."
      );
      this.add("ars_nouveau.cauldron.num_bounded", "%s bounded inventories");
      this.add("ars_nouveau.wixie_cauldron.bound", "Inventory bound.");
      this.add("ars_nouveau.wixie_cauldron.removed", "Inventory removed.");
      this.add("ars_nouveau.wixie_cauldron.cleared", "Bound inventories cleared.");
      this.add("ars_nouveau.binding_inventories", "Binding Inventories");
      this.add(
         "ars_nouveau.page7.wixie_charm",
         "You can select specific inventories for the wixie by using a dominion wand on an inventory, and then the cauldron. If any inventories are selected, only these inventories can be used and the Wixie will no longer pull from all nearby inventories by default."
      );
      this.add("tooltip.ars_nouveau.direction_0", "Sorting Descending");
      this.add("tooltip.ars_nouveau.direction_1", "Sorting Ascending");
      this.add("tooltip.ars_nouveau.clear_grid", "Clear");
      this.add("tooltip.ars_nouveau.open_recipe", "Recipe Book");
      this.add("ars_nouveau.color_gui.rainbow", "Rainbow");
      this.add("ars_nouveau.adopter", "Adopted by %s");
      this.add("entity.ars_nouveau.lily", "Lily");
      this.add("ars_nouveau.lily", "Patrons may summon Lily, a faithful tail wagging companion.");
      this.add("ars_nouveau.settings.summon_lily", "Summon Lily");
      this.add("ars_nouveau.settings.unsummon_lily", "Unsummon Lily");
      this.add(
         "ars_nouveau.rewards.enabled", "Ars Nouveau supporter rewards enabled, thank you! Rewards can be accessed in the Settings page of the spell book."
      );
      this.add("mob_jar.dummy", "A player dummy in a jar will attract nearby mobs.");
      this.add("ars_nouveau.turret.tooltip", "Can be rotated to face any direction. Use a dominion wand on the turret, and then on the target block.");
      this.add("ars_nouveau.scribes_table.auto_take_disabled", "Auto Take Disabled");
      this.add("ars_nouveau.alert.turret_needs_form", "Spell must have a form.");
      this.add("item.ars_nouveau.music_disc_thistle_the_sound_of_glass", "Music Disc");
      this.add("item.ars_nouveau.music_disc_thistle_the_sound_of_glass.desc", "Thistle - The Sound of Glass");
      this.add("item.ars_nouveau.music_disc_firel_the_wild_hunt", "Music Disc");
      this.add("item.ars_nouveau.music_disc_firel_the_wild_hunt.desc", "Firel - The Wild Hunt");
      this.add("ars_nouveau.sensor.set_spell", "Sensor will now trigger on this spell only.");
      this.add("ars_nouveau.sensor.on_resolve", "Mode: On Resolve");
      this.add("ars_nouveau.sensor.on_cast", "Mode: On Cast");
      this.add("block.ars_nouveau.spell_sensor", "Spell Sensor");
      this.add(
         "ars_nouveau.page1.spell_sensor",
         "Outputs a redstone signal when a spell is cast nearby. Output strength is determined by the length of the spell cast. Using a Dominion Wand will cause it to trigger when a spell resolves nearby, instead of being cast. Using a Spell Parchment will set the sensor to only output when that exact spell is detected."
      );
      this.add("ars_nouveau.no_stack_crafting", "No valid craft nearby.");
      this.add("item.ars_nouveau.jump_ring", "Ring of Jumping");
      this.add("ars_nouveau.page1.jump_ring", "Allows the user to continue jumping in the air. Each jump will expend mana.");
      this.add("ars_nouveau.connections.remove", "Connection removed.");
      this.add("ars_nouveau.powered_from", "Receiving signal from %d relays");
      this.add("block.ars_nouveau.redstone_relay", "Redstone Relay");
      this.add(
         "ars_nouveau.page1.redstone_relay",
         "Can be connected to other Redstone Relays to wirelessly send a redstone signal. Takes input from one side and outputs in all other directions. Can be connected within 30 blocks of another relay, and multiple relays can be connected."
      );

      for (String s : LibBlockNames.DECORATIVE_SOURCESTONE) {
         String key = "block.ars_nouveau." + s;
         String val = this.data.get(key);
         this.add(key + "_slab", val + " Slab");
         this.add(key + "_stairs", val + " Stairs");
      }
   }

   public void add(Item key, String name) {
      super.add(key, name);
   }

   public void add(String key, String value) {
      super.add(key, value);
      this.data.put(key, value);
   }
}
