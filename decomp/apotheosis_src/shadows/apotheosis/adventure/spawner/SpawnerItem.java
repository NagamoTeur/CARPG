package shadows.apotheosis.adventure.spawner;

import com.google.gson.annotations.SerializedName;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.adventure.AdventureConfig;
import shadows.apotheosis.util.SpawnerStats;
import shadows.placebo.json.PSerializer;
import shadows.placebo.json.TypeKeyed.TypeKeyedBase;
import shadows.placebo.json.WeightedJsonReloadListener.ILuckyWeighted;
import shadows.placebo.util.ChestBuilder;

public class SpawnerItem extends TypeKeyedBase<SpawnerItem> implements ILuckyWeighted {
   public static final PSerializer<SpawnerItem> SERIALIZER = PSerializer.basic(
      "Rogue Spawner", obj -> (SpawnerItem)RandomSpawnerManager.GSON.fromJson(obj, SpawnerItem.class)
   );
   public static final Block[] FILLER_BLOCKS = new Block[]{Blocks.f_50224_, Blocks.f_50079_, Blocks.f_50723_, Blocks.f_50729_};
   protected final int weight;
   protected final SpawnerStats stats;
   @SerializedName("spawn_potentials")
   protected final SimpleWeightedRandomList<SpawnData> spawnPotentials;
   @SerializedName("loot_table")
   protected final ResourceLocation lootTable;

   public SpawnerItem(SpawnerStats stats, ResourceLocation lootTable, SimpleWeightedRandomList<SpawnData> potentials, int weight) {
      this.weight = weight;
      this.stats = stats;
      this.lootTable = lootTable;
      this.spawnPotentials = potentials;
   }

   public int getWeight() {
      return this.weight;
   }

   public float getQuality() {
      return 0.0F;
   }

   public void place(WorldGenLevel world, BlockPos pos, RandomSource rand) {
      world.m_7731_(pos, Blocks.f_50085_.m_49966_(), 2);
      SpawnerBlockEntity entity = (SpawnerBlockEntity)world.m_7702_(pos);
      this.stats.apply(entity);
      entity.f_59788_.f_45443_ = this.spawnPotentials;
      entity.f_59788_.m_142667_(null, pos, (SpawnData)this.spawnPotentials.m_216820_(rand).get());
      ChestBuilder.place(world, pos.m_7495_(), rand.m_188501_() <= AdventureConfig.spawnerValueChance ? Apoth.LootTables.CHEST_VALUABLE : this.lootTable);
      world.m_7731_(pos.m_7494_(), FILLER_BLOCKS[rand.m_188503_(FILLER_BLOCKS.length)].m_49966_(), 2);

      for (Direction f : Plane.HORIZONTAL) {
         if (world.m_8055_(pos.m_121945_(f)).m_60795_()) {
            BooleanProperty side = (BooleanProperty)Blocks.f_50191_.m_49965_().m_61081_(f.m_122424_().m_122433_());
            world.m_7731_(pos.m_121945_(f), (BlockState)Blocks.f_50191_.m_49966_().m_61124_(side, true), 2);
         }
      }
   }

   public PSerializer<? extends SpawnerItem> getSerializer() {
      return SERIALIZER;
   }
}
