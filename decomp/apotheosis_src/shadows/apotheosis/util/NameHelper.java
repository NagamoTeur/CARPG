package shadows.apotheosis.util;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.placebo.config.Configuration;

public class NameHelper {
   private static String[] names = new String[]{
      "Prim",
      "Tyrael",
      "Bajorno",
      "Michael Morbius",
      "Morbius",
      "Arun",
      "Panez",
      "Doomsday",
      "Vanamar",
      "WhatTheDrunk",
      "Lothrazar",
      "Chelly",
      "Chelicia",
      "Darsh",
      "Dariush",
      "Cheese E Piloza",
      "Bing",
      "Royal",
      "NoWayHere",
      "SwankyStella",
      "Isosahedron",
      "Asfalis",
      "Biz",
      "Icicle",
      "Darko",
      "Shadows",
      "Katarina",
      "Faellynna",
      "Diliviel",
      "Jank",
      "Albert",
      "Andrew",
      "Anderson",
      "Andy",
      "Allan",
      "Arthur",
      "Aaron",
      "Allison",
      "Arielle",
      "Amanda",
      "Anne",
      "Annie",
      "Amy",
      "Alana",
      "Brandon",
      "Brady",
      "Bernard",
      "Ben",
      "Benjamin",
      "Bob",
      "Bobette",
      "Brooke",
      "Brandy",
      "Beatrice",
      "Bea",
      "Bella",
      "Becky",
      "Carlton",
      "Carl",
      "Calvin",
      "Cameron",
      "Carson",
      "Chase",
      "Cassandra",
      "Cassie",
      "Cas",
      "Carol",
      "Carly",
      "Cherise",
      "Charlotte",
      "Cheryl",
      "Chasity",
      "Danny",
      "Drake",
      "Daniel",
      "Derrel",
      "David",
      "Dave",
      "Donovan",
      "Don",
      "Donald",
      "Drew",
      "Derrick",
      "Darla",
      "Donna",
      "Dora",
      "Danielle",
      "Edward",
      "Elliot",
      "Ed",
      "Edson",
      "Elton",
      "Eddison",
      "Earl",
      "Eric",
      "Ericson",
      "Eddie",
      "Ediovany",
      "Emma",
      "Elizabeth",
      "Eliza",
      "Esperanza",
      "Esper",
      "Esmeralda",
      "Emi",
      "Emily",
      "Elaine",
      "Fernando",
      "Ferdinand",
      "Fred",
      "Feddie",
      "Fredward",
      "Frank",
      "Franklin",
      "Felix",
      "Felicia",
      "Fran",
      "Greg",
      "Gregory",
      "George",
      "Gerald",
      "Gina",
      "Geraldine",
      "Gabby",
      "Hendrix",
      "Henry",
      "Hobbes",
      "Herbert",
      "Heath",
      "Henderson",
      "Helga",
      "Hera",
      "Helen",
      "Helena",
      "Hannah",
      "Ike",
      "Issac",
      "Israel",
      "Ismael",
      "Irlanda",
      "Isabelle",
      "Irene",
      "Irenia",
      "Jimmy",
      "Jim",
      "Justin",
      "Jacob",
      "Jake",
      "Jon",
      "Johnson",
      "Jonny",
      "Jonathan",
      "Josh",
      "Joshua",
      "Julian",
      "Jesus",
      "Jericho",
      "Jeb",
      "Jess",
      "Joan",
      "Jill",
      "Jillian",
      "Jessica",
      "Jennifer",
      "Jenny",
      "Jen",
      "Judy",
      "Kenneth",
      "Kenny",
      "Ken",
      "Keith",
      "Kevin",
      "Karen",
      "Kassandra",
      "Kassie",
      "Leonard",
      "Leo",
      "Leroy",
      "Lee",
      "Lenny",
      "Luke",
      "Lucas",
      "Liam",
      "Lorraine",
      "Latasha",
      "Lauren",
      "Laquisha",
      "Livia",
      "Lydia",
      "Lila",
      "Lilly",
      "Lillian",
      "Lilith",
      "Lana",
      "Mason",
      "Mike",
      "Mickey",
      "Mario",
      "Manny",
      "Mark",
      "Marcus",
      "Martin",
      "Marty",
      "Matthew",
      "Matt",
      "Max",
      "Maximillian",
      "Marth",
      "Mia",
      "Marriah",
      "Maddison",
      "Maddie",
      "Marissa",
      "Miranda",
      "Mary",
      "Martha",
      "Melonie",
      "Melody",
      "Mel",
      "Minnie",
      "Nathan",
      "Nathaniel",
      "Nate",
      "Ned",
      "Nick",
      "Norman",
      "Nicholas",
      "Natasha",
      "Nicki",
      "Nora",
      "Nelly",
      "Nina",
      "Orville",
      "Oliver",
      "Orlando",
      "Owen",
      "Olsen",
      "Odin",
      "Olaf",
      "Ortega",
      "Olivia",
      "Patrick",
      "Pat",
      "Paul",
      "Perry",
      "Pinnochio",
      "Patrice",
      "Patricia",
      "Pennie",
      "Petunia",
      "Patti",
      "Pernelle",
      "Quade",
      "Quincy",
      "Quentin",
      "Quinn",
      "Roberto",
      "Robbie",
      "Rob",
      "Robert",
      "Roy",
      "Roland",
      "Ronald",
      "Richard",
      "Rick",
      "Ricky",
      "Rose",
      "Rosa",
      "Rhonda",
      "Rebecca",
      "Roberta",
      "Sparky",
      "Shiloh",
      "Stephen",
      "Steve",
      "Saul",
      "Sheen",
      "Shane",
      "Sean",
      "Sampson",
      "Samuel",
      "Sammy",
      "Stefan",
      "Sasha",
      "Sam",
      "Susan",
      "Suzy",
      "Shelby",
      "Samantha",
      "Sheila",
      "Sharon",
      "Sally",
      "Stephanie",
      "Sandra",
      "Sandy",
      "Sage",
      "Tim",
      "Thomas",
      "Thompson",
      "Tyson",
      "Tyler",
      "Tom",
      "Tyrone",
      "Timmothy",
      "Tamara",
      "Tabby",
      "Tabitha",
      "Tessa",
      "Tiara",
      "Tyra",
      "Uriel",
      "Ursala",
      "Uma",
      "Victor",
      "Vincent",
      "Vince",
      "Vance",
      "Vinny",
      "Velma",
      "Victoria",
      "Veronica",
      "Wilson",
      "Wally",
      "Wallace",
      "Will",
      "Wilard",
      "William",
      "Wilhelm",
      "Xavier",
      "Xandra",
      "Young",
      "Yvonne",
      "Yolanda",
      "Zach",
      "Zachary"
   };
   private static String[] nameParts = new String[]{
      "Prim",
      "Morb",
      "Ius",
      "Kat",
      "Chel",
      "Bing",
      "Darsh",
      "Jank",
      "Dark",
      "Osto",
      "Grab",
      "Thar",
      "Ger",
      "Ald",
      "Mas",
      "On",
      "O",
      "Din",
      "Thor",
      "Jon",
      "Ath",
      "Burb",
      "En",
      "A",
      "E",
      "I",
      "U",
      "Hab",
      "Bloo",
      "Ena",
      "Dit",
      "Aph",
      "Ern",
      "Bor",
      "Dav",
      "Id",
      "Toast",
      "Son",
      "For",
      "Wen",
      "Lob",
      "Van",
      "Zap",
      "Ear",
      "Ben",
      "Don",
      "Bran",
      "Gro",
      "Jen",
      "Bob",
      "Ette",
      "Ere",
      "Man",
      "Qua",
      "Bro",
      "Cree",
      "Per",
      "Skel",
      "Ton",
      "Zom",
      "Bie",
      "Wolf",
      "End",
      "Er",
      "Pig",
      "Sil",
      "Ver",
      "Fish",
      "Cow",
      "Chic",
      "Ken",
      "Sheep",
      "Squid",
      "Hell"
   };
   private static String[] prefixes = new String[]{
      "Dr. Michael",
      "Sir",
      "Mister",
      "Madam",
      "Doctor",
      "Father",
      "Mother",
      "Poppa",
      "Lord",
      "Lady",
      "Overseer",
      "Professor",
      "Mr.",
      "Mr. President",
      "Duke",
      "Duchess",
      "Dame",
      "The Honorable",
      "Chancellor",
      "Vice-Chancellor",
      "His Holiness",
      "Reverend",
      "Count",
      "Viscount",
      "Earl",
      "Captain",
      "Major",
      "General",
      "Senpai"
   };
   private static String[] suffixes = new String[]{
      "Morbius",
      "Dragonborn",
      "Rejected",
      "Mighty",
      "Supreme",
      "Superior",
      "Ultimate",
      "Lame",
      "Wimpy",
      "Curious",
      "Sneaky",
      "Pathetic",
      "Crying",
      "Eagle",
      "Errant",
      "Unholy",
      "Questionable",
      "Mean",
      "Hungry",
      "Thirsty",
      "Feeble",
      "Wise",
      "Sage",
      "Magical",
      "Mythical",
      "Legendary",
      "Not Very Nice",
      "Jerk",
      "Doctor",
      "Misunderstood",
      "Angry",
      "Knight",
      "Bishop",
      "Godly",
      "Special",
      "Toasty",
      "Shiny",
      "Shimmering",
      "Light",
      "Dark",
      "Odd-Smelling",
      "Funky",
      "Rock Smasher",
      "Son of Herobrine",
      "Cracked",
      "Sticky",
      "§kAlien§r",
      "Baby",
      "Manly",
      "Rough",
      "Scary",
      "Undoubtable",
      "Honest",
      "Non-Suspicious",
      "Boring",
      "Odd",
      "Lazy",
      "Super",
      "Nifty",
      "Ogre Slayer",
      "Pig Thief",
      "Dirt Digger",
      "Really Cool",
      "Doominator",
      "... Something",
      "Extra-Fishy",
      "Gorilla Slaughterer",
      "Marbles Winner",
      "AC Rizzlord",
      "President",
      "Burger Chef",
      "Professional Animator",
      "Cheese Sprayer",
      "Happiness Advocate",
      "Ghost Hunter",
      "Head of Potatoes",
      "Ninja",
      "Warrior",
      "Pyromancer"
   };
   private static String[] helms = new String[]{"Helmet", "Cap", "Crown", "Great Helm", "Bassinet", "Sallet", "Close Helm", "Barbute"};
   private static String[] chestplates = new String[]{"Chestplate", "Tunic", "Brigandine", "Hauberk", "Cuirass"};
   private static String[] leggings = new String[]{"Leggings", "Pants", "Tassets", "Cuisses", "Schynbalds"};
   private static String[] boots = new String[]{"Boots", "Shoes", "Greaves", "Sabatons", "Sollerets"};
   private static String[] swords = new String[]{
      "Sword",
      "Cutter",
      "Slicer",
      "Dicer",
      "Knife",
      "Blade",
      "Machete",
      "Brand",
      "Claymore",
      "Cutlass",
      "Foil",
      "Dagger",
      "Glaive",
      "Rapier",
      "Saber",
      "Scimitar",
      "Shortsword",
      "Longsword",
      "Broadsword",
      "Calibur"
   };
   private static String[] axes = new String[]{"Axe", "Chopper", "Hatchet", "Tomahawk", "Cleaver", "Hacker", "Tree-Cutter", "Truncator"};
   private static String[] pickaxes = new String[]{"Pickaxe", "Pick", "Mattock", "Rock-Smasher", "Miner"};
   private static String[] shovels = new String[]{"Shovel", "Spade", "Digger", "Excavator", "Trowel", "Scoop"};
   private static String[] bows = new String[]{
      "Bow", "Shortbow", "Longbow", "Flatbow", "Recurve Bow", "Reflex Bow", "Self Bow", "Composite Bow", "Arrow-Flinger"
   };
   private static String[] shields = new String[]{"Shield", "Buckler", "Targe", "Greatshield", "Blockade", "Bulwark", "Tower Shield", "Protector", "Aegis"};
   private static Map<Tier, String> tierKeys = new HashMap<>();
   private static Map<ArmorMaterial, String> materialKeys = new HashMap<>();
   private static Map<String, String[]> tierNames = new HashMap<>();
   private static Map<String, String[]> materialNames = new HashMap<>();
   public static String suffixFormat = "%s the %s";
   public static String ownershipFormat = "%s's";
   public static String chainFormat = "%s %s";

   public static String nameFromParts(RandomSource random) {
      String name = nameParts[random.m_188503_(nameParts.length)] + nameParts[random.m_188503_(nameParts.length)].toLowerCase();
      if (random.m_188501_() < 0.4F) {
         name = name + nameParts[random.m_188503_(nameParts.length)].toLowerCase();
      }

      if (random.m_188501_() < 0.15F) {
         name = name + nameParts[random.m_188503_(nameParts.length)].toLowerCase();
      }

      return name;
   }

   public static String setEntityName(RandomSource rand, Mob entity) {
      String root;
      if (names.length > 0 && nameParts.length > 0) {
         root = rand.m_188501_() < 0.8F ? names[rand.m_188503_(names.length)] : nameFromParts(rand);
      } else if (names.length > 0) {
         root = names[rand.m_188503_(names.length)];
      } else {
         root = nameFromParts(rand);
      }

      String name = root;
      if (rand.m_188501_() < 0.3F && prefixes.length > 0) {
         name = prefixes[rand.m_188503_(prefixes.length)] + " " + root;
      }

      if (rand.m_188501_() < 0.8F && suffixes.length > 0) {
         name = String.format(suffixFormat, name, suffixes[rand.m_188503_(suffixes.length)]);
      }

      entity.m_6593_(Component.m_237113_(name));
      entity.m_20340_(true);
      return root;
   }

   public static Component setItemName(RandomSource random, ItemStack stack) {
      MutableComponent name = (MutableComponent)stack.m_41720_().m_7626_(stack);
      String baseName = name.getString();
      if (stack.m_41720_() instanceof TieredItem) {
         Tier tier = ((TieredItem)stack.m_41720_()).m_43314_();
         String[] tierNames = getTierNames(tier);
         if (tierNames.length != 0) {
            name = Component.m_237113_(tierNames[random.m_188503_(tierNames.length)] + " ");
         } else {
            String[] split = baseName.split(" ");
            String rebuilt = "";

            for (int i = 0; i < split.length - 1; i++) {
               rebuilt = rebuilt + split[i] + " ";
            }

            name = Component.m_237113_(rebuilt);
         }

         String[] type = new String[]{"Tool"};
         Set<ToolAction> types = ToolAction.getActions().stream().filter(stack::canPerformAction).collect(Collectors.toSet());
         if (stack.m_41720_() instanceof SwordItem) {
            type = swords;
         } else if (types.contains(ToolActions.AXE_DIG)) {
            type = axes;
         } else if (types.contains(ToolActions.PICKAXE_DIG)) {
            type = pickaxes;
         } else if (types.contains(ToolActions.SHOVEL_DIG)) {
            type = shovels;
         } else if (types.contains(ToolActions.SHIELD_BLOCK)) {
            type = shields;
         }

         name.m_130946_(type[random.m_188503_(type.length)]);
      } else if (stack.m_41720_() instanceof ProjectileWeaponItem) {
         String[] type = bows;
         name = Component.m_237113_(type[random.m_188503_(type.length)]);
      } else if (stack.m_41720_() instanceof ArmorItem) {
         ArmorMaterial armorMat = ((ArmorItem)stack.m_41720_()).m_40401_();
         String[] matNames = getMaterialNames(armorMat);
         if (matNames.length != 0) {
            name = Component.m_237113_(matNames[random.m_188503_(matNames.length)] + " ");
         } else {
            String[] split = baseName.split(" ");
            String rebuilt = "";

            for (int i = 0; i < split.length - 1; i++) {
               rebuilt = rebuilt + split[i] + " ";
            }

            name = Component.m_237113_(rebuilt);
         }

         String[] type = new String[]{"Armor"};
         switch (((ArmorItem)stack.m_41720_()).m_40402_()) {
            case HEAD:
               type = helms;
               break;
            case CHEST:
               type = chestplates;
               break;
            case LEGS:
               type = leggings;
               break;
            case FEET:
               type = boots;
         }

         name.m_130946_(type[random.m_188503_(type.length)]);
      }

      stack.m_41714_(name);
      return name;
   }

   public static String[] getTierNames(Tier materialName) {
      return tierNames.computeIfAbsent(getKey(materialName), s -> new String[0]);
   }

   public static String[] getMaterialNames(ArmorMaterial materialName) {
      return materialNames.computeIfAbsent(getKey(materialName), s -> new String[0]);
   }

   public static void load(Configuration c) {
      names = c.getStringList(
         "Names", "entity", names, "A list of full names, which are used in the generation of boss names. May be empty only if name parts is not empty."
      );
      nameParts = c.getStringList(
         "Name Parts",
         "entity",
         nameParts,
         "A list of name pieces, which can be spliced together to create full names.  May be empty only if names is not empty."
      );
      Preconditions.checkArgument(
         names.length != 0 || nameParts.length != 0, "Both names and name parts are empty in apotheosis/names.cfg, this is not allowed."
      );
      prefixes = c.getStringList("Prefixes", "entity", prefixes, "A list of prefixes, which are used in the generation of boss names. May be empty.");
      suffixes = c.getStringList(
         "Suffixes",
         "entity",
         suffixes,
         "A list of suffixes, which are used in the generation of boss names. A suffix is always preceeded by \"The\". May be empty."
      );
      helms = c.getStringList("Helms", "items", helms, "A list of root names for helms, used in the generation of item names. May not be empty.");
      chestplates = c.getStringList(
         "chestplates", "items", chestplates, "A list of root names for chestplates, used in the generation of item names. May not be empty."
      );
      leggings = c.getStringList("leggings", "items", leggings, "A list of root names for leggings, used in the generation of item names. May not be empty.");
      boots = c.getStringList("boots", "items", boots, "A list of root names for boots, used in the generation of item names. May not be empty.");
      Preconditions.checkArgument(
         helms.length > 0 && chestplates.length > 0 && leggings.length > 0 && boots.length > 0,
         "Detected empty lists for armor root names in apotheosis/names.cfg, this is not allowed."
      );
      swords = c.getStringList("swords", "items", swords, "A list of root names for swords, used in the generation of item names. May not be empty.");
      axes = c.getStringList("axes", "items", axes, "A list of root names for axes, used in the generation of item names. May not be empty.");
      pickaxes = c.getStringList("pickaxes", "items", pickaxes, "A list of root names for pickaxes, used in the generation of item names. May not be empty.");
      shovels = c.getStringList("shovels", "items", shovels, "A list of root names for shovels, used in the generation of item names. May not be empty.");
      bows = c.getStringList("bows", "items", bows, "A list of root names for bows, used in the generation of item names. May not be empty.");
      shields = c.getStringList("shields", "items", shields, "A list of root names for shields, used in the generation of item names. May not be empty.");
      Preconditions.checkArgument(
         swords.length > 0 && axes.length > 0 && pickaxes.length > 0 && shovels.length > 0 && bows.length > 0,
         "Detected empty lists for weapon root names in apotheosis/names.cfg, this is not allowed."
      );
      Map<Tier, List<Item>> itemsByTier = new HashMap<>();
      Map<ArmorMaterial, List<Item>> armorsByTier = new HashMap<>();

      for (Item i : ForgeRegistries.ITEMS) {
         try {
            if (i instanceof TieredItem) {
               Tier mat = ((TieredItem)i).m_43314_();
               itemsByTier.computeIfAbsent(mat, m -> new ArrayList<>()).add(i);
            }

            if (i instanceof ArmorItem) {
               ArmorMaterial mat = ((ArmorItem)i).m_40401_();
               armorsByTier.computeIfAbsent(mat, m -> new ArrayList<>()).add(i);
            }
         } catch (Exception var9) {
            AdventureModule.LOGGER.error("The item {} has thrown an exception while attempting to access it's tier.", ForgeRegistries.ITEMS.getKey(i));
            var9.printStackTrace();
         }
      }

      for (Entry<Tier, List<Item>> e : itemsByTier.entrySet()) {
         Tier tier = e.getKey();
         List<Item> items = e.getValue();
         String key = getID(tier, items);
         tierKeys.put(tier, key);
         String[] read = c.getStringList(key, "tools", tierNames.getOrDefault(tier, new String[0]), computeComment(items, tier::m_6282_));
         if (read.length > 0) {
            tierNames.put(key, read);
         }
      }

      for (Entry<ArmorMaterial, List<Item>> ex : armorsByTier.entrySet()) {
         ArmorMaterial tier = ex.getKey();
         List<Item> items = ex.getValue();
         String key = getID(tier, items);
         String[] read = c.getStringList(key, "armors", materialNames.getOrDefault(tier, new String[0]), computeComment(items, tier::m_6230_));
         if (read.length > 0) {
            materialNames.put(key, read);
         }
      }

      suffixFormat = c.getString("Suffix Format", "formatting", suffixFormat, "The format string that will be used when a suffix is applied.");
      ownershipFormat = c.getString("Ownership Format", "formatting", ownershipFormat, "The format string that will be used to indicate ownership.");
      if (c.hasChanged()) {
         c.save();
      }
   }

   private static String computeComment(List<Item> items, Supplier<Ingredient> repair) {
      String cmt = "A list of material-based prefix names for this material group. May be empty.\n";
      cmt = cmt + "Items in this group: ";

      for (Item i : items) {
         cmt = cmt + ForgeRegistries.ITEMS.getKey(i) + ", ";
      }

      cmt = cmt.substring(0, cmt.length() - 2);
      return cmt + "\n";
   }

   private static String getID(Object o, List<Item> items) {
      if (o instanceof Enum) {
         return ((Enum)o).name();
      } else {
         ResourceLocation id = ForgeRegistries.ITEMS.getKey(items.get(0));
         return id.m_135827_() + "_" + id.m_135815_();
      }
   }

   private static String getKey(Tier tier) {
      return tierKeys.getOrDefault(tier, "");
   }

   private static String getKey(ArmorMaterial mat) {
      return materialKeys.getOrDefault(mat, "");
   }

   static {
      tierNames.put(Tiers.WOOD.name(), new String[]{"Wooden", "Wood", "Hardwood", "Balsa Wood", "Mahogany", "Plywood"});
      tierNames.put(Tiers.STONE.name(), new String[]{"Stone", "Rock", "Marble", "Cobblestone"});
      tierNames.put(Tiers.IRON.name(), new String[]{"Iron", "Steel", "Ferrous", "Rusty", "Wrought Iron"});
      tierNames.put(Tiers.GOLD.name(), new String[]{"Golden", "Gold", "Gilt", "Auric", "Ornate"});
      tierNames.put(Tiers.DIAMOND.name(), new String[]{"Diamond", "Zircon", "Gemstone", "Jewel", "Crystal"});
      tierNames.put(Tiers.NETHERITE.name(), new String[]{"Burnt", "Embered", "Fiery", "Hellborn", "Flameforged"});
      tierNames.put("twilightforest_ironwood_sword", new String[]{"Ironwood", "Earthbound", "Oaken", "Ironcapped"});
      tierNames.put("twilightforest_knightmetal_sword", new String[]{"Knightmetal", "Knightly", "Phantom-Forged"});
      tierNames.put("twilightforest_steeleaf_sword", new String[]{"Steeleaf", "Organic", "Natural", "Cobaltstem", "Tungstenpetal"});
      tierNames.put("twilightforest_fiery_sword", new String[]{"Fiery", "Flaming", "Hydra-Infused", "Infernal"});
      materialNames.put(ArmorMaterials.LEATHER.name(), new String[]{"Leather", "Rawhide", "Lamellar", "Cow Skin"});
      materialNames.put(ArmorMaterials.CHAIN.name(), new String[]{"Chainmail", "Chain", "Chain Link", "Scale"});
      materialNames.put(ArmorMaterials.IRON.name(), tierNames.get(Tiers.IRON.name()));
      materialNames.put(ArmorMaterials.GOLD.name(), tierNames.get(Tiers.GOLD.name()));
      materialNames.put(ArmorMaterials.DIAMOND.name(), tierNames.get(Tiers.DIAMOND.name()));
      materialNames.put(ArmorMaterials.NETHERITE.name(), tierNames.get(Tiers.NETHERITE.name()));
      materialNames.put(ArmorMaterials.TURTLE.name(), new String[]{"Tortollan", "Very Tragic", "Environmental", "Organic"});
      materialNames.put("ARMOR_IRONWOOD", tierNames.get("twilightforest_ironwood_sword"));
      materialNames.put("ARMOR_KNIGHTLY", tierNames.get("twilightforest_knightmetal_sword"));
      materialNames.put("ARMOR_STEELEAF", tierNames.get("twilightforest_steeleaf_sword"));
      materialNames.put("ARMOR_FIERY", tierNames.get("twilightforest_fiery_sword"));
      materialNames.put("ARMOR_ARCTIC", new String[]{"Arctic", "Frostforged", "Caribou Skin", "Gutskin", "Insulating"});
      materialNames.put("ARMOR_YETI", new String[]{"Yeti", "Abominable", "Snow-Demon", "Grinch"});
   }
}
