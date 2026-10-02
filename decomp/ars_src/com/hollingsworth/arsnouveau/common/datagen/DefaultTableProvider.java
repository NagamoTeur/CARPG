package com.hollingsworth.arsnouveau.common.datagen;

import com.google.common.collect.ImmutableList;
import com.hollingsworth.arsnouveau.common.block.AlterationTable;
import com.hollingsworth.arsnouveau.common.block.ScribesBlock;
import com.hollingsworth.arsnouveau.common.block.ThreePartBlock;
import com.hollingsworth.arsnouveau.common.lib.LibBlockNames;
import com.hollingsworth.arsnouveau.common.util.RegistryWrapper;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DefaultTableProvider extends net.minecraft.data.loot.LootTableProvider {
   private static final float[] DEFAULT_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
   private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> tables = ImmutableList.of(
      Pair.of(DefaultTableProvider.BlockLootTable::new, LootContextParamSets.f_81421_)
   );

   public DefaultTableProvider(DataGenerator dataGeneratorIn) {
      super(dataGeneratorIn);
   }

   protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> getTables() {
      return this.tables;
   }

   protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
      map.forEach((p_218436_2_, p_218436_3_) -> LootTables.m_79202_(validationtracker, p_218436_2_, p_218436_3_));
   }

   public static class BlockLootTable extends BlockLoot {
      public List<Block> list = new ArrayList<>();

      protected void addTables() {
         this.registerDropSelf(BlockRegistry.ENCHANTED_SPELL_TURRET);
         this.registerDropSelf(BlockRegistry.BLAZING_LOG);
         this.registerDropSelf(BlockRegistry.VEXING_LOG);
         this.registerDropSelf(BlockRegistry.CASCADING_LOG);
         this.registerDropSelf(BlockRegistry.FLOURISHING_LOG);
         this.registerDropSelf(BlockRegistry.BLAZING_SAPLING);
         this.registerDropSelf(BlockRegistry.VEXING_SAPLING);
         this.registerDropSelf(BlockRegistry.CASCADING_SAPLING);
         this.registerDropSelf(BlockRegistry.FLOURISHING_SAPLING);
         this.registerDropSelf(BlockRegistry.ARCHWOOD_PLANK);
         this.registerDrop(BlockRegistry.WIXIE_CAULDRON, Items.f_42544_);

         for (FlowerPotBlock pot : BlockRegistry.flowerPots.values()) {
            this.list.add(pot);
            this.m_124252_(pot);
         }

         this.registerLeavesAndSticks(BlockRegistry.BLAZING_LEAVES, BlockRegistry.BLAZING_SAPLING);
         this.registerLeavesAndSticks(BlockRegistry.CASCADING_LEAVE, BlockRegistry.CASCADING_SAPLING);
         this.registerLeavesAndSticks(BlockRegistry.FLOURISHING_LEAVES, BlockRegistry.FLOURISHING_SAPLING);
         this.registerLeavesAndSticks(BlockRegistry.VEXING_LEAVES, BlockRegistry.VEXING_SAPLING);
         this.registerDropSelf(BlockRegistry.BLAZING_WOOD);
         this.registerDropSelf(BlockRegistry.VEXING_WOOD);
         this.registerDropSelf(BlockRegistry.CASCADING_WOOD);
         this.registerDropSelf(BlockRegistry.FLOURISHING_WOOD);
         this.registerDropSelf(BlockRegistry.ARCHWOOD_BUTTON);
         this.registerDropSelf(BlockRegistry.ARCHWOOD_STAIRS);
         this.registerSlabItemTable(BlockRegistry.ARCHWOOD_SLABS);
         this.registerDropSelf(BlockRegistry.MAGELIGHT_TORCH);
         this.registerDropSelf(BlockRegistry.ARCHWOOD_FENCE_GATE);
         this.registerDropSelf(BlockRegistry.ARCHWOOD_TRAPDOOR);
         this.registerDropSelf(BlockRegistry.ARCHWOOD_PPlate);
         this.registerDropSelf(BlockRegistry.ARCHWOOD_FENCE);
         this.registerDropSelf(BlockRegistry.STRIPPED_AWLOG_BLUE);
         this.registerDropSelf(BlockRegistry.STRIPPED_AWWOOD_BLUE);
         this.registerDropSelf(BlockRegistry.STRIPPED_AWLOG_GREEN);
         this.registerDropSelf(BlockRegistry.STRIPPED_AWWOOD_GREEN);
         this.registerDropSelf(BlockRegistry.STRIPPED_AWLOG_RED);
         this.registerDropSelf(BlockRegistry.STRIPPED_AWWOOD_RED);
         this.registerDropSelf(BlockRegistry.STRIPPED_AWLOG_PURPLE);
         this.registerDropSelf(BlockRegistry.STRIPPED_AWWOOD_PURPLE);
         this.registerDropDoor(BlockRegistry.ARCHWOOD_DOOR);
         this.registerDropSelf(BlockRegistry.SOURCE_GEM_BLOCK);
         this.registerDropSelf(BlockRegistry.POTION_MELDER);
         this.registerDropSelf(BlockRegistry.RITUAL_BLOCK);
         this.registerDropSelf(BlockRegistry.SCONCE_BLOCK);
         this.registerBedCondition(BlockRegistry.SCRIBES_BLOCK, ScribesBlock.PART, ThreePartBlock.HEAD);
         this.registerDrop(BlockRegistry.DRYGMY_BLOCK, Items.f_41998_);
         this.registerDropSelf(BlockRegistry.VITALIC_BLOCK);
         this.registerDropSelf(BlockRegistry.ALCHEMICAL_BLOCK);
         this.registerDropSelf(BlockRegistry.MYCELIAL_BLOCK);
         this.registerDropSelf(BlockRegistry.TIMER_SPELL_TURRET);
         this.registerDropSelf(BlockRegistry.BASIC_SPELL_TURRET);
         this.registerDropSelf(BlockRegistry.ARCHWOOD_CHEST);
         this.registerDropSelf(BlockRegistry.SPELL_PRISM);
         this.registerDropSelf(BlockRegistry.LAVA_LILY);
         this.registerDropSelf(BlockRegistry.AGRONOMIC_SOURCELINK);
         this.registerDropSelf(BlockRegistry.ENCHANTING_APP_BLOCK);
         this.registerDropSelf(BlockRegistry.ARCANE_PEDESTAL);
         this.registerDropSelf(BlockRegistry.ARCANE_PLATFORM);
         this.registerDropSelf(BlockRegistry.RELAY);
         this.registerDropSelf(BlockRegistry.RELAY_SPLITTER);
         this.registerDropSelf(BlockRegistry.ARCANE_CORE_BLOCK);
         this.registerDropSelf(BlockRegistry.IMBUEMENT_BLOCK);
         this.registerDropSelf(BlockRegistry.VOLCANIC_BLOCK);
         this.registerDropSelf(BlockRegistry.LAVA_LILY);
         this.registerDropSelf(BlockRegistry.BRAZIER_RELAY);
         this.registerDropSelf(BlockRegistry.RELAY_WARP);
         this.registerDropSelf(BlockRegistry.RELAY_DEPOSIT);
         this.registerDropSelf(BlockRegistry.RELAY_COLLECTOR);
         this.registerDropSelf(BlockRegistry.CRAFTING_LECTERN.get());
         this.registerDropSelf(BlockRegistry.RED_SBED);
         this.registerDropSelf(BlockRegistry.YELLOW_SBED);
         this.registerDropSelf(BlockRegistry.GREEN_SBED);
         this.registerDropSelf(BlockRegistry.PURPLE_SBED);
         this.registerDropSelf(BlockRegistry.BLUE_SBED);
         this.registerDropSelf(BlockRegistry.ORANGE_SBED);
         this.registerDropSelf(BlockRegistry.SCRYERS_CRYSTAL);
         this.registerDropSelf(BlockRegistry.SCRYERS_OCULUS);
         this.registerDropSelf(BlockRegistry.POTION_DIFFUSER);

         for (String s : LibBlockNames.DECORATIVE_SOURCESTONE) {
            this.registerDropSelf(BlockRegistry.getBlock(s));
            Block block = (Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation("ars_nouveau", s + "_stairs"));
            this.registerDropSelf(block);
            Block slab = (Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation("ars_nouveau", s + "_slab"));
            this.registerDropSelf(slab);
         }

         this.registerBedCondition(BlockRegistry.ALTERATION_TABLE, AlterationTable.PART, ThreePartBlock.HEAD);
         this.registerDropSelf(BlockRegistry.VOID_PRISM);
         this.registerDropSelf(BlockRegistry.MAGEBLOOM_BLOCK);
         this.registerDropSelf(BlockRegistry.GHOST_WEAVE);
         this.registerDropSelf(BlockRegistry.FALSE_WEAVE);
         this.registerDropSelf(BlockRegistry.MIRROR_WEAVE);
         this.registerDropSelf(BlockRegistry.ITEM_DETECTOR);
         this.registerDropSelf(BlockRegistry.SKY_WEAVE);
         this.registerDropSelf(BlockRegistry.ROTATING_TURRET);
         this.registerDropSelf(BlockRegistry.SPELL_SENSOR);
         this.registerDropSelf(BlockRegistry.REDSTONE_RELAY);
      }

      protected void registerSlabItemTable(Block p_124291_) {
         this.list.add(p_124291_);
         this.m_124165_(
            p_124291_,
            LootTable.m_79147_()
               .m_79161_(
                  LootPool.m_79043_()
                     .m_165133_(ConstantValue.m_165692_(1.0F))
                     .m_79076_(
                        (net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder)m_236221_(
                           p_124291_,
                           LootItem.m_79579_(p_124291_)
                              .m_79078_(
                                 SetItemCountFunction.m_165412_(ConstantValue.m_165692_(2.0F))
                                    .m_79080_(
                                       LootItemBlockStatePropertyCondition.m_81769_(p_124291_)
                                          .m_81784_(
                                             net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.m_67693_()
                                                .m_67697_(SlabBlock.f_56353_, SlabType.DOUBLE)
                                          )
                                    )
                              )
                        )
                     )
               )
         );
      }

      protected <T extends Comparable<T> & StringRepresentable> void registerBedCondition(Block block, Property<T> prop, T isValue) {
         this.list.add(block);
         this.m_124165_(
            block,
            LootTable.m_79147_()
               .m_79161_(
                  (net.minecraft.world.level.storage.loot.LootPool.Builder)m_236224_(
                     block,
                     LootPool.m_79043_()
                        .m_165133_(ConstantValue.m_165692_(1.0F))
                        .m_79076_(
                           LootItem.m_79579_(block)
                              .m_79080_(
                                 LootItemBlockStatePropertyCondition.m_81769_(block)
                                    .m_81784_(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.m_67693_().m_67697_(prop, isValue))
                              )
                        )
                  )
               )
         );
      }

      public void registerLeavesAndSticks(Block leaves, Block sapling) {
         this.list.add(leaves);
         this.m_124175_(leaves, l_state -> m_124157_(l_state, sapling, DefaultTableProvider.DEFAULT_SAPLING_DROP_RATES));
      }

      public void registerDropDoor(Block block) {
         this.list.add(block);
         this.m_124175_(block, BlockLoot::m_124137_);
      }

      public void registerDropSelf(RegistryWrapper block) {
         this.list.add((Block)block.get());
         this.m_124288_((Block)block.get());
      }

      public void registerDropSelf(Block block) {
         this.list.add(block);
         this.m_124288_(block);
      }

      public void registerDropSelf(RegistryObject<Block> block) {
         this.list.add((Block)block.get());
         this.m_124288_((Block)block.get());
      }

      public void registerDrop(Block input, ItemLike output) {
         this.list.add(input);
         this.m_124147_(input, output);
      }

      protected Iterable<Block> getKnownBlocks() {
         return this.list;
      }
   }
}
