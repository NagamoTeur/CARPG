package daripher.skilltree.data.generation.loot;

import com.google.common.collect.Maps;
import daripher.skilltree.data.generation.PSTGemTypesProvider;
import daripher.skilltree.item.gem.GemType;
import daripher.skilltree.item.gem.loot.GemLootPoolEntry;
import java.util.Map;
import java.util.function.BiConsumer;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import org.jetbrains.annotations.NotNull;

public class PSTBlockLoot extends BlockLoot {
   private final Map<ResourceLocation, Builder> lootTables = Maps.newHashMap();
   private final PSTGemTypesProvider gemTypesProvider;

   public PSTBlockLoot(PSTGemTypesProvider gemTypesProvider) {
      this.gemTypesProvider = gemTypesProvider;
   }

   protected void addTables() {
      this.lootTables.put(new ResourceLocation("skilltree", "gems"), this.gemsLootTable());
      this.lootTables.put(new ResourceLocation("skilltree", "apotheosis_gems"), this.apotheosisGemsLootTable());
   }

   protected Builder gemsLootTable() {
      net.minecraft.world.level.storage.loot.LootPool.Builder lootPool = LootPool.m_79043_();
      this.gemTypesProvider.getGemTypes().values().forEach(gemType -> lootPool.m_79076_(new GemLootPoolEntry.Builder(gemType.id())));
      return LootTable.m_79147_().m_79161_(lootPool);
   }

   protected Builder apotheosisGemsLootTable() {
      net.minecraft.world.level.storage.loot.LootPool.Builder lootPool = LootPool.m_79043_();
      this.gemTypesProvider
         .getGemTypes()
         .values()
         .stream()
         .map(GemType::id)
         .filter(id -> !id.m_135815_().contains("vacucite") && !id.m_135815_().contains("iriscite"))
         .forEach(id -> lootPool.m_79076_(new GemLootPoolEntry.Builder(id)));
      return LootTable.m_79147_().m_79161_(lootPool);
   }

   public void accept(@NotNull BiConsumer<ResourceLocation, Builder> consumer) {
      this.addTables();
      this.lootTables.forEach(consumer);
   }
}
