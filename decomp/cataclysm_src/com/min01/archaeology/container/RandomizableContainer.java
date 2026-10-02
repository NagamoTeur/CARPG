package com.min01.archaeology.container;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public interface RandomizableContainer extends Container {
   String LOOT_TABLE_TAG = "LootTable";
   String LOOT_TABLE_SEED_TAG = "LootTableSeed";

   @Nullable
   ResourceLocation getLootTable();

   void setLootTable(@Nullable ResourceLocation var1);

   default void setLootTable(ResourceLocation lootTable, long lootTableSeed) {
      this.setLootTable(lootTable);
      this.setLootTableSeed(lootTableSeed);
   }

   long getLootTableSeed();

   void setLootTableSeed(long var1);

   BlockPos getBlockPos();

   @Nullable
   Level getLevel();

   static void setBlockEntityLootTable(BlockGetter blockGetter, RandomSource random, BlockPos position, ResourceLocation lootTable) {
      if (blockGetter.m_7702_(position) instanceof RandomizableContainer randomizableContainer) {
         randomizableContainer.setLootTable(lootTable, random.m_188505_());
      }
   }

   default boolean tryLoadLootTable(CompoundTag tag) {
      if (tag.m_128425_("LootTable", 8)) {
         this.setLootTable(new ResourceLocation(tag.m_128461_("LootTable")));
         this.setLootTableSeed(tag.m_128454_("LootTableSeed"));
         return true;
      } else {
         return false;
      }
   }

   default boolean trySaveLootTable(CompoundTag tag) {
      ResourceLocation lootTable = this.getLootTable();
      if (lootTable == null) {
         return false;
      } else {
         tag.m_128359_("LootTable", lootTable.toString());
         long lootTableSeed = this.getLootTableSeed();
         if (lootTableSeed != 0L) {
            tag.m_128356_("LootTableSeed", lootTableSeed);
         }

         return true;
      }
   }

   default void unpackLootTable(@Nullable Player player) {
      Level level = this.getLevel();
      BlockPos position = this.getBlockPos();
      ResourceLocation lootTableRaw = this.getLootTable();
      if (lootTableRaw != null && level instanceof ServerLevel serverLevel) {
         LootTable lootTable = serverLevel.m_7654_().m_129898_().m_79217_(lootTableRaw);
         Builder builder = new Builder(serverLevel).m_78972_(LootContextParams.f_81460_, Vec3.m_82512_(position));
         this.setLootTable(null);
         if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.f_10563_.m_54597_(serverPlayer, lootTableRaw);
            builder.m_78963_(serverPlayer.m_36336_()).m_78972_(LootContextParams.f_81455_, serverPlayer);
         }

         lootTable.m_79123_(this, builder.m_78975_(LootContextParamSets.f_81411_));
      }
   }
}
