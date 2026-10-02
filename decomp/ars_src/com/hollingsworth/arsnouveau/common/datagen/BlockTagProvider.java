package com.hollingsworth.arsnouveau.common.datagen;

import com.hollingsworth.arsnouveau.common.lib.LibBlockNames;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.nio.file.Path;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockTagProvider extends BlockTagsProvider {
   public static TagKey<Block> IGNORE_TILE = BlockTags.create(new ResourceLocation("ars_nouveau", "ignore_tile"));
   public static TagKey<Block> SUMMON_BED = BlockTags.create(new ResourceLocation("ars_nouveau", "summon_bed"));
   public static TagKey<Block> SUMMON_SLEEPABLE = BlockTags.create(new ResourceLocation("ars_nouveau", "summon_sleepable"));
   public static TagKey<Block> DECORATIVE_AN = BlockTags.create(new ResourceLocation("ars_nouveau", "an_decorative"));
   public static TagKey<Block> MAGIC_SAPLINGS = BlockTags.create(new ResourceLocation("ars_nouveau", "magic_saplings"));
   public static TagKey<Block> MAGIC_PLANTS = BlockTags.create(new ResourceLocation("ars_nouveau", "magic_plants"));
   public static TagKey<Block> HARVEST_FOLIAGE = BlockTags.create(new ResourceLocation("ars_nouveau", "harvest/foliage"));
   public static TagKey<Block> HARVEST_STEMS = BlockTags.create(new ResourceLocation("ars_nouveau", "harvest/stems"));
   public static TagKey<Block> BREAK_BLACKLIST = BlockTags.create(new ResourceLocation("ars_nouveau", "break_blacklist"));
   public static TagKey<Block> GRAVITY_BLACKLIST = BlockTags.create(new ResourceLocation("ars_nouveau", "gravity_blacklist"));
   public static TagKey<Block> NO_BREAK_DROP = BlockTags.create(new ResourceLocation("ars_nouveau", "no_break_drop"));
   public static TagKey<Block> FELLABLE = BlockTags.create(new ResourceLocation("ars_nouveau", "harvest/fellable"));
   public static TagKey<Block> BUDDING_BLOCKS = BlockTags.create(new ResourceLocation("ars_nouveau", "golem/budding"));
   public static TagKey<Block> CLUSTER_BLOCKS = BlockTags.create(new ResourceLocation("ars_nouveau", "golem/cluster"));
   public static TagKey<Block> BREAK_WITH_PICKAXE = BlockTags.create(new ResourceLocation("ars_nouveau", "break_with_pickaxe"));
   public static TagKey<Block> AUTOPULL_DISABLED = BlockTags.create(new ResourceLocation("ars_nouveau", "storage/autopull_disabled"));
   public static TagKey<Block> RELOCATION_NOT_SUPPORTED = BlockTags.create(new ResourceLocation("forge", "relocation_not_supported"));
   public static TagKey<Block> OCCLUDES_SPELL_SENSOR = BlockTags.create(new ResourceLocation("ars_nouveau", "occludes_spell_sensor"));
   private final DataGenerator generator;

   public BlockTagProvider(DataGenerator generatorIn, ExistingFileHelper helper) {
      super(generatorIn, "ars_nouveau", helper);
      this.generator = generatorIn;
   }

   protected void m_6577_() {
      this.m_206424_(OCCLUDES_SPELL_SENSOR).m_126582_(BlockRegistry.MAGEBLOOM_BLOCK);
      this.m_206424_(RELOCATION_NOT_SUPPORTED);
      this.m_206424_(BUDDING_BLOCKS).m_126582_(Blocks.f_152491_);
      this.m_206424_(CLUSTER_BLOCKS).m_126582_(Blocks.f_152492_);
      this.m_206424_(BlockTags.f_144282_)
         .m_126584_(
            new Block[]{
               BlockRegistry.RELAY,
               BlockRegistry.ARCANE_CORE_BLOCK,
               BlockRegistry.ENCHANTING_APP_BLOCK,
               BlockRegistry.ARCANE_PEDESTAL.get(),
               BlockRegistry.ARCANE_PLATFORM.get(),
               BlockRegistry.MAGELIGHT_TORCH.get(),
               BlockRegistry.CREATIVE_SOURCE_JAR,
               BlockRegistry.RUNE_BLOCK,
               BlockRegistry.IMBUEMENT_BLOCK,
               BlockRegistry.SOURCE_JAR,
               BlockRegistry.RELAY_SPLITTER,
               BlockRegistry.ENCHANTED_SPELL_TURRET,
               BlockRegistry.VOLCANIC_BLOCK,
               BlockRegistry.LAVA_LILY,
               BlockRegistry.WIXIE_CAULDRON,
               BlockRegistry.SOURCE_GEM_BLOCK,
               BlockRegistry.RITUAL_BLOCK.get(),
               BlockRegistry.POTION_JAR,
               BlockRegistry.POTION_MELDER,
               BlockRegistry.SCONCE_BLOCK,
               BlockRegistry.DRYGMY_BLOCK,
               BlockRegistry.ALCHEMICAL_BLOCK,
               BlockRegistry.VITALIC_BLOCK,
               BlockRegistry.MYCELIAL_BLOCK,
               BlockRegistry.RELAY_DEPOSIT,
               BlockRegistry.RELAY_WARP,
               BlockRegistry.BASIC_SPELL_TURRET,
               BlockRegistry.TIMER_SPELL_TURRET,
               BlockRegistry.SPELL_PRISM,
               BlockRegistry.SCRYERS_CRYSTAL,
               BlockRegistry.SCRYERS_OCULUS,
               BlockRegistry.POTION_DIFFUSER,
               BlockRegistry.MOB_JAR,
               BlockRegistry.VOID_PRISM,
               BlockRegistry.BRAZIER_RELAY.get(),
               BlockRegistry.REDSTONE_RELAY.get()
            }
         );
      this.m_206424_(BlockTags.f_144280_)
         .m_126584_(
            new Block[]{
               BlockRegistry.SCRIBES_BLOCK,
               BlockRegistry.CASCADING_LOG,
               BlockRegistry.CASCADING_WOOD,
               BlockRegistry.BLAZING_LOG,
               BlockRegistry.BLAZING_WOOD,
               BlockRegistry.VEXING_LOG,
               BlockRegistry.VEXING_WOOD,
               BlockRegistry.FLOURISHING_LOG,
               BlockRegistry.FLOURISHING_WOOD,
               BlockRegistry.ARCHWOOD_PLANK,
               BlockRegistry.ARCHWOOD_BUTTON,
               BlockRegistry.ARCHWOOD_STAIRS,
               BlockRegistry.ARCHWOOD_SLABS,
               BlockRegistry.ARCHWOOD_FENCE_GATE,
               BlockRegistry.ARCHWOOD_TRAPDOOR,
               BlockRegistry.ARCHWOOD_PPlate,
               BlockRegistry.ARCHWOOD_FENCE,
               BlockRegistry.ARCHWOOD_DOOR,
               BlockRegistry.STRIPPED_AWLOG_BLUE,
               BlockRegistry.STRIPPED_AWWOOD_BLUE,
               BlockRegistry.STRIPPED_AWLOG_GREEN,
               BlockRegistry.STRIPPED_AWWOOD_GREEN,
               BlockRegistry.STRIPPED_AWLOG_RED,
               BlockRegistry.STRIPPED_AWWOOD_RED,
               BlockRegistry.STRIPPED_AWLOG_PURPLE,
               BlockRegistry.STRIPPED_AWWOOD_PURPLE,
               BlockRegistry.CRAFTING_LECTERN.get(),
               BlockRegistry.ARCHWOOD_CHEST,
               BlockRegistry.ALTERATION_TABLE,
               BlockRegistry.ITEM_DETECTOR.get(),
               BlockRegistry.REPOSITORY
            }
         );
      this.m_206424_(BlockTags.f_144281_)
         .m_126584_(new Block[]{BlockRegistry.CASCADING_LEAVE, BlockRegistry.BLAZING_LEAVES, BlockRegistry.FLOURISHING_LEAVES, BlockRegistry.VEXING_LEAVES});
      this.m_206424_(net.minecraftforge.common.Tags.Blocks.CHESTS).m_126582_(BlockRegistry.ARCHWOOD_CHEST);
      this.m_206424_(net.minecraftforge.common.Tags.Blocks.CHESTS_WOODEN).m_126582_(BlockRegistry.ARCHWOOD_CHEST);

      for (String s : LibBlockNames.DECORATIVE_SOURCESTONE) {
         Block block = (Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation("ars_nouveau", s));
         Block stair = (Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation("ars_nouveau", s + "_stairs"));
         Block slab = (Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation("ars_nouveau", s + "_slab"));
         this.m_206424_(DECORATIVE_AN).m_126584_(new Block[]{block, stair, slab});
         this.m_206424_(BlockTags.f_144282_).m_126584_(new Block[]{block, stair, slab});
      }

      this.m_206424_(DECORATIVE_AN)
         .m_126584_(new Block[]{BlockRegistry.FALSE_WEAVE, BlockRegistry.MIRROR_WEAVE, BlockRegistry.GHOST_WEAVE, BlockRegistry.MAGEBLOOM_BLOCK});
      this.m_206424_(HARVEST_FOLIAGE)
         .m_206428_(BlockTags.f_13035_)
         .m_126584_(
            new Block[]{
               Blocks.f_50180_,
               Blocks.f_50181_,
               Blocks.f_50451_,
               Blocks.f_50692_,
               Blocks.f_50701_,
               Blocks.f_50191_,
               Blocks.f_152538_,
               Blocks.f_50704_,
               Blocks.f_50133_,
               Blocks.f_50186_,
               Blocks.f_50702_,
               Blocks.f_220833_
            }
         );
      this.m_206424_(HARVEST_STEMS).m_126584_(new Block[]{Blocks.f_50571_, Blocks.f_50130_, Blocks.f_50128_});
      this.m_206424_(FELLABLE).m_126582_(Blocks.f_50182_).addTags(new TagKey[]{BlockTags.f_13106_, HARVEST_FOLIAGE, HARVEST_STEMS});
      TagKey<Block> WHIRLISPRIG_KINDA_LIKES = BlockTags.create(new ResourceLocation("ars_nouveau", "whirlisprig/kinda_likes"));
      TagKey<Block> WHIRLISPRIG_GREATLY_LIKES = BlockTags.create(new ResourceLocation("ars_nouveau", "whirlisprig/greatly_likes"));
      this.m_206424_(WHIRLISPRIG_GREATLY_LIKES)
         .m_126584_(
            new Block[]{
               Blocks.f_50182_,
               Blocks.f_50180_,
               Blocks.f_50181_,
               Blocks.f_50701_,
               Blocks.f_50692_,
               Blocks.f_50451_,
               Blocks.f_50128_,
               Blocks.f_50130_,
               Blocks.f_50491_,
               Blocks.f_50490_
            }
         );
      this.m_206424_(WHIRLISPRIG_KINDA_LIKES);
      this.m_206424_(MAGIC_SAPLINGS)
         .m_126584_(
            new Block[]{BlockRegistry.BLAZING_SAPLING, BlockRegistry.CASCADING_SAPLING, BlockRegistry.FLOURISHING_SAPLING, BlockRegistry.VEXING_SAPLING}
         );
      this.m_206424_(BlockTags.f_13104_)
         .m_126584_(
            new Block[]{BlockRegistry.BLAZING_SAPLING, BlockRegistry.CASCADING_SAPLING, BlockRegistry.FLOURISHING_SAPLING, BlockRegistry.VEXING_SAPLING}
         );
      this.m_206424_(MAGIC_PLANTS)
         .m_206428_(MAGIC_SAPLINGS)
         .m_126584_(
            new Block[]{
               BlockRegistry.SOURCEBERRY_BUSH,
               BlockRegistry.MAGE_BLOOM_CROP,
               BlockRegistry.FROSTAYA_POD,
               BlockRegistry.MENDOSTEEN_POD,
               BlockRegistry.BASTION_POD,
               BlockRegistry.BOMBEGRANTE_POD
            }
         );
      this.m_206424_(net.minecraftforge.common.Tags.Blocks.FENCES).m_126582_(BlockRegistry.ARCHWOOD_FENCE);
      this.m_206424_(net.minecraftforge.common.Tags.Blocks.FENCES_WOODEN).m_126582_(BlockRegistry.ARCHWOOD_FENCE);
      this.m_206424_(net.minecraftforge.common.Tags.Blocks.FENCE_GATES).m_126582_(BlockRegistry.ARCHWOOD_FENCE_GATE);
      this.m_206424_(net.minecraftforge.common.Tags.Blocks.FENCE_GATES_WOODEN).m_126582_(BlockRegistry.ARCHWOOD_FENCE_GATE);
      this.m_206424_(BlockTags.f_13106_)
         .m_126584_(
            new Block[]{
               BlockRegistry.VEXING_LOG,
               BlockRegistry.CASCADING_LOG,
               BlockRegistry.FLOURISHING_LOG,
               BlockRegistry.BLAZING_LOG,
               BlockRegistry.STRIPPED_AWLOG_BLUE,
               BlockRegistry.STRIPPED_AWWOOD_BLUE,
               BlockRegistry.STRIPPED_AWLOG_GREEN,
               BlockRegistry.STRIPPED_AWWOOD_GREEN,
               BlockRegistry.STRIPPED_AWLOG_RED,
               BlockRegistry.STRIPPED_AWWOOD_RED,
               BlockRegistry.STRIPPED_AWLOG_PURPLE,
               BlockRegistry.STRIPPED_AWWOOD_PURPLE
            }
         );
      this.m_206424_(BlockTags.f_13105_)
         .m_126584_(
            new Block[]{
               BlockRegistry.VEXING_LOG,
               BlockRegistry.CASCADING_LOG,
               BlockRegistry.FLOURISHING_LOG,
               BlockRegistry.BLAZING_LOG,
               BlockRegistry.STRIPPED_AWLOG_BLUE,
               BlockRegistry.STRIPPED_AWWOOD_BLUE,
               BlockRegistry.STRIPPED_AWLOG_GREEN,
               BlockRegistry.STRIPPED_AWWOOD_GREEN,
               BlockRegistry.STRIPPED_AWLOG_RED,
               BlockRegistry.STRIPPED_AWWOOD_RED,
               BlockRegistry.STRIPPED_AWLOG_PURPLE,
               BlockRegistry.STRIPPED_AWWOOD_PURPLE
            }
         );
      this.m_206424_(BlockTags.f_13090_).m_126582_(BlockRegistry.ARCHWOOD_PLANK);
      this.m_206424_(BlockTags.f_13055_).m_126582_(BlockRegistry.ARCHWOOD_FENCE_GATE);
      this.m_206424_(BlockTags.f_13039_).m_126582_(BlockRegistry.ARCHWOOD_FENCE);
      this.m_206424_(BlockTags.f_13098_).m_126582_(BlockRegistry.ARCHWOOD_FENCE);
      TagKey<Block> ARCHWOOD_LEAVES = BlockTags.create(new ResourceLocation("minecraft", "leaves/archwood_leaves"));
      this.m_206424_(ARCHWOOD_LEAVES)
         .m_126584_(new Block[]{BlockRegistry.VEXING_LEAVES, BlockRegistry.CASCADING_LEAVE, BlockRegistry.BLAZING_LEAVES, BlockRegistry.FLOURISHING_LEAVES});
      this.m_206424_(BlockTags.f_13035_)
         .m_126584_(new Block[]{BlockRegistry.VEXING_LEAVES, BlockRegistry.CASCADING_LEAVE, BlockRegistry.BLAZING_LEAVES, BlockRegistry.FLOURISHING_LEAVES});
      this.m_206424_(BlockTags.f_13074_).m_126582_(BlockRegistry.MAGE_BLOOM_CROP);
      this.m_206424_(BlockTags.f_13093_).m_126582_(BlockRegistry.ARCHWOOD_BUTTON);
      this.m_206424_(BlockTags.f_13073_).m_126582_(BlockRegistry.MAGE_BLOOM_CROP);
      this.m_206424_(BlockTags.f_13031_).m_126582_(BlockRegistry.ARCHWOOD_SLABS);
      this.m_206424_(BlockTags.f_13030_).m_126582_(BlockRegistry.ARCHWOOD_STAIRS);
      this.m_206424_(BlockTags.f_13036_).m_126582_(BlockRegistry.ARCHWOOD_TRAPDOOR);
      this.m_206424_(BlockTags.f_13092_).m_126582_(BlockRegistry.ARCHWOOD_BUTTON);
      this.m_206424_(BlockTags.f_13095_).m_126582_(BlockRegistry.ARCHWOOD_DOOR);
      this.m_206424_(BlockTags.f_13103_).m_126582_(BlockRegistry.ARCHWOOD_DOOR);
      this.m_206424_(BlockTags.f_13097_).m_126582_(BlockRegistry.ARCHWOOD_SLABS);
      this.m_206424_(BlockTags.f_13096_).m_126582_(BlockRegistry.ARCHWOOD_STAIRS);
      this.m_206424_(BlockTags.f_13102_).m_126582_(BlockRegistry.ARCHWOOD_TRAPDOOR);
      this.m_206424_(IGNORE_TILE)
         .m_126584_(
            new Block[]{
               BlockRegistry.INTANGIBLE_AIR,
               BlockRegistry.MAGE_BLOCK,
               BlockRegistry.SCONCE_BLOCK,
               BlockRegistry.LIGHT_BLOCK,
               BlockRegistry.GHOST_WEAVE,
               BlockRegistry.SKY_WEAVE.get()
            }
         );
      this.m_206424_(SUMMON_BED)
         .m_126584_(
            new Block[]{
               BlockRegistry.RED_SBED,
               BlockRegistry.GREEN_SBED,
               BlockRegistry.YELLOW_SBED,
               BlockRegistry.BLUE_SBED,
               BlockRegistry.ORANGE_SBED,
               BlockRegistry.PURPLE_SBED
            }
         );
      this.m_206424_(SUMMON_SLEEPABLE).m_206428_(SUMMON_BED).m_206428_(BlockTags.f_13038_);
      this.m_206424_(BREAK_BLACKLIST);
      this.m_206424_(NO_BREAK_DROP).m_126582_(Blocks.f_50578_);
      this.m_206424_(GRAVITY_BLACKLIST).m_126584_(new Block[]{Blocks.f_50752_, BlockRegistry.MAGE_BLOCK}).m_206428_(RELOCATION_NOT_SUPPORTED);
      this.m_206424_(BREAK_WITH_PICKAXE).m_126582_(Blocks.f_152492_);
      this.m_206424_(AUTOPULL_DISABLED).m_126584_(new Block[]{BlockRegistry.SCRIBES_BLOCK, BlockRegistry.ALTERATION_TABLE});
   }

   protected Path getPath(ResourceLocation p_126514_) {
      return this.generator.m_123916_().resolve("data/" + p_126514_.m_135827_() + "/tags/blocks/" + p_126514_.m_135815_() + ".json");
   }

   public String m_6055_() {
      return "AN tags";
   }
}
