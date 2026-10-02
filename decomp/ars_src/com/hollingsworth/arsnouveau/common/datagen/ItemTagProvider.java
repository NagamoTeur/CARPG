package com.hollingsworth.arsnouveau.common.datagen;

import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ItemTagProvider extends ItemTagsProvider {
   public static TagKey<Item> SUMMON_BED_ITEMS = ItemTags.create(new ResourceLocation("ars_nouveau", "summon_bed"));
   public static TagKey<Item> SOURCE_GEM_TAG = ItemTags.create(new ResourceLocation("forge:gems/source"));
   public static TagKey<Item> SOURCE_GEM_BLOCK_TAG = ItemTags.create(new ResourceLocation("forge:storage_blocks/source"));
   public static TagKey<Item> ARCHWOOD_LOG_TAG = ItemTags.create(new ResourceLocation("forge:logs/archwood"));
   public static TagKey<Item> MAGIC_FOOD = ItemTags.create(new ResourceLocation("ars_nouveau", "magic_food"));
   public static TagKey<Item> WILDEN_DROP_TAG = ItemTags.create(new ResourceLocation("ars_nouveau", "wilden_drop"));
   public static TagKey<Item> SHARD_TAG = ItemTags.create(new ResourceLocation("ars_nouveau", "golem/shard"));
   public static TagKey<Item> BERRY_TAG = ItemTags.create(new ResourceLocation("forge", "fruits/berry"));
   public static final TagKey<Item> SUMMON_SHARDS_TAG = ItemTags.create(new ResourceLocation("ars_nouveau", "magic_shards"));
   public static TagKey<Item> JAR_ITEM_BLACKLIST = ItemTags.create(new ResourceLocation("ars_nouveau", "interact_jar_blacklist"));

   public ItemTagProvider(DataGenerator p_126530_, BlockTagsProvider p_126531_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
      super(p_126530_, p_126531_, modId, existingFileHelper);
   }

   protected void m_6577_() {
      this.m_206424_(SUMMON_SHARDS_TAG)
         .m_126584_(
            new Item[]{
               ItemsRegistry.DRYGMY_SHARD.get(), ItemsRegistry.STARBUNCLE_SHARD.get(), ItemsRegistry.WIXIE_SHARD.get(), ItemsRegistry.WHIRLISPRIG_SHARDS.get()
            }
         );
      this.m_206424_(BERRY_TAG).m_126582_(BlockRegistry.SOURCEBERRY_BUSH.m_5456_());
      this.m_206424_(ItemTags.f_13158_)
         .m_126584_(new Item[]{ItemsRegistry.FIREL_DISC.get(), ItemsRegistry.WILD_HUNT.get(), ItemsRegistry.SOUND_OF_GLASS.get()});
      this.m_206424_(MAGIC_FOOD).m_126584_(new Item[]{ItemsRegistry.SOURCE_BERRY_PIE.get(), ItemsRegistry.SOURCE_BERRY_ROLL.get()});
      this.m_206424_(ItemTags.create(new ResourceLocation("ars_nouveau", "whirlisprig/denied_drop")))
         .m_126582_(Items.f_42329_)
         .m_206428_(net.minecraftforge.common.Tags.Items.SEEDS);
      this.m_206424_(net.minecraftforge.common.Tags.Items.FENCES).m_126582_(BlockRegistry.ARCHWOOD_FENCE.m_5456_());
      this.m_206424_(net.minecraftforge.common.Tags.Items.FENCES_WOODEN).m_126582_(BlockRegistry.ARCHWOOD_FENCE.m_5456_());
      this.m_206424_(net.minecraftforge.common.Tags.Items.FENCE_GATES).m_126582_(BlockRegistry.ARCHWOOD_FENCE_GATE.m_5456_());
      this.m_206424_(net.minecraftforge.common.Tags.Items.FENCE_GATES_WOODEN).m_126582_(BlockRegistry.ARCHWOOD_FENCE_GATE.m_5456_());
      this.m_206424_(SOURCE_GEM_TAG).m_126582_(ItemsRegistry.SOURCE_GEM.get());
      this.m_206424_(SHARD_TAG).m_126582_(Items.f_151049_);
      this.m_206424_(ARCHWOOD_LOG_TAG)
         .m_126584_(
            new Item[]{
               BlockRegistry.BLAZING_LOG.m_5456_(),
               BlockRegistry.CASCADING_LOG.m_5456_(),
               BlockRegistry.VEXING_LOG.m_5456_(),
               BlockRegistry.FLOURISHING_LOG.m_5456_(),
               BlockRegistry.BLAZING_WOOD.m_5456_(),
               BlockRegistry.CASCADING_WOOD.m_5456_(),
               BlockRegistry.FLOURISHING_WOOD.m_5456_(),
               BlockRegistry.VEXING_WOOD.m_5456_(),
               BlockRegistry.STRIPPED_AWLOG_BLUE.m_5456_(),
               BlockRegistry.STRIPPED_AWWOOD_BLUE.m_5456_(),
               BlockRegistry.STRIPPED_AWLOG_GREEN.m_5456_(),
               BlockRegistry.STRIPPED_AWWOOD_GREEN.m_5456_(),
               BlockRegistry.STRIPPED_AWLOG_RED.m_5456_(),
               BlockRegistry.STRIPPED_AWWOOD_RED.m_5456_(),
               BlockRegistry.STRIPPED_AWLOG_PURPLE.m_5456_(),
               BlockRegistry.STRIPPED_AWWOOD_PURPLE.m_5456_()
            }
         );
      this.m_206424_(ItemTags.f_13182_)
         .m_126584_(
            new Item[]{
               BlockRegistry.BLAZING_LOG.m_5456_(),
               BlockRegistry.CASCADING_LOG.m_5456_(),
               BlockRegistry.VEXING_LOG.m_5456_(),
               BlockRegistry.FLOURISHING_LOG.m_5456_(),
               BlockRegistry.BLAZING_WOOD.m_5456_(),
               BlockRegistry.CASCADING_WOOD.m_5456_(),
               BlockRegistry.FLOURISHING_WOOD.m_5456_(),
               BlockRegistry.VEXING_WOOD.m_5456_(),
               BlockRegistry.STRIPPED_AWLOG_BLUE.m_5456_(),
               BlockRegistry.STRIPPED_AWWOOD_BLUE.m_5456_(),
               BlockRegistry.STRIPPED_AWLOG_GREEN.m_5456_(),
               BlockRegistry.STRIPPED_AWWOOD_GREEN.m_5456_(),
               BlockRegistry.STRIPPED_AWLOG_RED.m_5456_(),
               BlockRegistry.STRIPPED_AWWOOD_RED.m_5456_(),
               BlockRegistry.STRIPPED_AWLOG_PURPLE.m_5456_(),
               BlockRegistry.STRIPPED_AWWOOD_PURPLE.m_5456_()
            }
         );
      this.m_206424_(ItemTags.f_13143_)
         .m_126584_(
            new Item[]{
               BlockRegistry.VEXING_LEAVES.m_5456_(),
               BlockRegistry.CASCADING_LEAVE.m_5456_(),
               BlockRegistry.BLAZING_LEAVES.m_5456_(),
               BlockRegistry.FLOURISHING_LEAVES.m_5456_()
            }
         );
      this.m_206424_(ItemTags.f_13181_)
         .m_126584_(
            new Item[]{
               BlockRegistry.BLAZING_LOG.m_5456_(),
               BlockRegistry.CASCADING_LOG.m_5456_(),
               BlockRegistry.VEXING_LOG.m_5456_(),
               BlockRegistry.FLOURISHING_LOG.m_5456_(),
               BlockRegistry.BLAZING_WOOD.m_5456_(),
               BlockRegistry.CASCADING_WOOD.m_5456_(),
               BlockRegistry.FLOURISHING_WOOD.m_5456_(),
               BlockRegistry.VEXING_WOOD.m_5456_(),
               BlockRegistry.STRIPPED_AWLOG_BLUE.m_5456_(),
               BlockRegistry.STRIPPED_AWWOOD_BLUE.m_5456_(),
               BlockRegistry.STRIPPED_AWLOG_GREEN.m_5456_(),
               BlockRegistry.STRIPPED_AWWOOD_GREEN.m_5456_(),
               BlockRegistry.STRIPPED_AWLOG_RED.m_5456_(),
               BlockRegistry.STRIPPED_AWWOOD_RED.m_5456_(),
               BlockRegistry.STRIPPED_AWLOG_PURPLE.m_5456_(),
               BlockRegistry.STRIPPED_AWWOOD_PURPLE.m_5456_()
            }
         );
      this.m_206424_(ItemTags.create(new ResourceLocation("forge", "planks/archwood"))).m_126582_(BlockRegistry.ARCHWOOD_PLANK.m_5456_());
      this.m_206424_(net.minecraftforge.common.Tags.Items.SEEDS).m_126582_(BlockRegistry.MAGE_BLOOM_CROP.m_5456_());
      this.m_206424_(net.minecraftforge.common.Tags.Items.CROPS).m_126582_(ItemsRegistry.MAGE_BLOOM.m_5456_());
      this.m_206424_(net.minecraftforge.common.Tags.Items.STORAGE_BLOCKS).m_126582_(BlockRegistry.SOURCE_GEM_BLOCK.m_5456_());
      this.m_206424_(SOURCE_GEM_BLOCK_TAG).m_126582_(BlockRegistry.SOURCE_GEM_BLOCK.m_5456_());
      this.m_206424_(net.minecraftforge.common.Tags.Items.GEMS).m_126582_(ItemsRegistry.SOURCE_GEM.get());
      this.m_206424_(ItemTags.f_13168_).m_126582_(BlockRegistry.ARCHWOOD_PLANK.m_5456_());
      this.m_206424_(ItemTags.f_13147_).m_126582_(BlockRegistry.ARCHWOOD_FENCE.m_5456_());
      this.m_206424_(ItemTags.f_13176_).m_126582_(BlockRegistry.ARCHWOOD_FENCE.m_5456_());
      this.m_206424_(ItemTags.f_13164_).m_126582_(ItemsRegistry.SOURCE_GEM.get());
      this.m_206424_(ItemTags.f_13171_).m_126582_(BlockRegistry.ARCHWOOD_BUTTON.m_5456_());
      this.m_206424_(ItemTags.f_13170_).m_126582_(BlockRegistry.ARCHWOOD_BUTTON.m_5456_());
      this.m_206424_(ItemTags.f_13179_).m_126582_(BlockRegistry.ARCHWOOD_DOOR.m_5456_());
      this.m_206424_(ItemTags.f_13173_).m_126582_(BlockRegistry.ARCHWOOD_DOOR.m_5456_());
      this.m_206424_(ItemTags.f_13180_)
         .m_126584_(
            new Item[]{
               BlockRegistry.BLAZING_SAPLING.m_5456_(),
               BlockRegistry.CASCADING_SAPLING.m_5456_(),
               BlockRegistry.FLOURISHING_SAPLING.m_5456_(),
               BlockRegistry.VEXING_SAPLING.m_5456_()
            }
         );
      this.m_206424_(ItemTags.f_13139_).m_126582_(BlockRegistry.ARCHWOOD_SLABS.m_5456_());
      this.m_206424_(ItemTags.f_13175_).m_126582_(BlockRegistry.ARCHWOOD_SLABS.m_5456_());
      this.m_206424_(ItemTags.f_13138_).m_126582_(BlockRegistry.ARCHWOOD_STAIRS.m_5456_());
      this.m_206424_(ItemTags.f_13174_).m_126582_(BlockRegistry.ARCHWOOD_STAIRS.m_5456_());
      this.m_206424_(ItemTags.f_13144_).m_126582_(BlockRegistry.ARCHWOOD_TRAPDOOR.m_5456_());
      this.m_206424_(ItemTags.f_13178_).m_126582_(BlockRegistry.ARCHWOOD_TRAPDOOR.m_5456_());
      this.m_206424_(ItemTags.f_13177_).m_126582_(BlockRegistry.ARCHWOOD_PPlate.m_5456_());
      this.m_206424_(WILDEN_DROP_TAG).m_126584_(new Item[]{ItemsRegistry.WILDEN_HORN.get(), ItemsRegistry.WILDEN_SPIKE.get(), ItemsRegistry.WILDEN_WING.get()});
      this.m_206424_(SUMMON_BED_ITEMS)
         .m_126584_(
            new Item[]{
               BlockRegistry.RED_SBED.m_5456_(),
               BlockRegistry.GREEN_SBED.m_5456_(),
               BlockRegistry.YELLOW_SBED.m_5456_(),
               BlockRegistry.BLUE_SBED.m_5456_(),
               BlockRegistry.ORANGE_SBED.m_5456_(),
               BlockRegistry.PURPLE_SBED.m_5456_()
            }
         );
      this.m_206424_(ItemTags.f_13162_)
         .m_126584_(
            new Item[]{
               ItemsRegistry.WORN_NOTEBOOK.m_5456_(),
               ItemsRegistry.NOVICE_SPELLBOOK.m_5456_(),
               ItemsRegistry.ARCHMAGE_SPELLBOOK.m_5456_(),
               ItemsRegistry.APPRENTICE_SPELLBOOK.m_5456_(),
               ItemsRegistry.CREATIVE_SPELLBOOK.m_5456_()
            }
         );
      this.m_206424_(net.minecraftforge.common.Tags.Items.ARMORS)
         .m_126584_(
            new Item[]{
               ItemsRegistry.NOVICE_ROBES.m_5456_(),
               ItemsRegistry.APPRENTICE_ROBES.m_5456_(),
               ItemsRegistry.ARCHMAGE_ROBES.m_5456_(),
               ItemsRegistry.NOVICE_BOOTS.m_5456_(),
               ItemsRegistry.APPRENTICE_BOOTS.m_5456_(),
               ItemsRegistry.ARCHMAGE_BOOTS.m_5456_(),
               ItemsRegistry.NOVICE_LEGGINGS.m_5456_(),
               ItemsRegistry.APPRENTICE_LEGGINGS.m_5456_(),
               ItemsRegistry.ARCHMAGE_LEGGINGS.m_5456_(),
               ItemsRegistry.NOVICE_HOOD.m_5456_(),
               ItemsRegistry.APPRENTICE_HOOD.m_5456_(),
               ItemsRegistry.ARCHMAGE_HOOD.m_5456_()
            }
         );
      this.m_206424_(net.minecraftforge.common.Tags.Items.ARMORS_BOOTS)
         .m_126584_(new Item[]{ItemsRegistry.NOVICE_BOOTS.m_5456_(), ItemsRegistry.APPRENTICE_BOOTS.m_5456_(), ItemsRegistry.ARCHMAGE_BOOTS.m_5456_()});
      this.m_206424_(net.minecraftforge.common.Tags.Items.ARMORS_CHESTPLATES)
         .m_126584_(new Item[]{ItemsRegistry.NOVICE_ROBES.m_5456_(), ItemsRegistry.APPRENTICE_ROBES.m_5456_(), ItemsRegistry.ARCHMAGE_ROBES.m_5456_()});
      this.m_206424_(net.minecraftforge.common.Tags.Items.ARMORS_HELMETS)
         .m_126584_(new Item[]{ItemsRegistry.NOVICE_HOOD.m_5456_(), ItemsRegistry.APPRENTICE_HOOD.m_5456_(), ItemsRegistry.ARCHMAGE_HOOD.m_5456_()});
      this.m_206424_(net.minecraftforge.common.Tags.Items.ARMORS_LEGGINGS)
         .m_126584_(new Item[]{ItemsRegistry.NOVICE_LEGGINGS.m_5456_(), ItemsRegistry.APPRENTICE_LEGGINGS.m_5456_(), ItemsRegistry.ARCHMAGE_LEGGINGS.m_5456_()});
      this.m_206424_(net.minecraftforge.common.Tags.Items.CHESTS).m_126582_(BlockRegistry.ARCHWOOD_CHEST.m_5456_());
      this.m_206424_(net.minecraftforge.common.Tags.Items.CHESTS_WOODEN).m_126582_(BlockRegistry.ARCHWOOD_CHEST.m_5456_());
      this.m_206424_(JAR_ITEM_BLACKLIST);
   }
}
