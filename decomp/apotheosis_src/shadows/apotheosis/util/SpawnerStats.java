package shadows.apotheosis.util;

import com.google.gson.annotations.SerializedName;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;

public class SpawnerStats {
   @SerializedName("spawn_delay")
   protected final int spawnDelay;
   @SerializedName("min_delay")
   protected final int minDelay;
   @SerializedName("max_delay")
   protected final int maxDelay;
   @SerializedName("spawn_count")
   protected final int spawnCount;
   @SerializedName("max_nearby_entities")
   protected final int maxNearbyEntities;
   @SerializedName("spawn_range")
   protected final int spawnRange;
   @SerializedName("player_activation_range")
   protected final int playerRange;

   public SpawnerStats() {
      this(20, 200, 800, 4, 6, 4, 16);
   }

   public SpawnerStats(int delay, int min, int max, int count, int nearby, int range, int playerRange) {
      this.spawnDelay = delay;
      this.minDelay = min;
      this.maxDelay = max;
      this.spawnCount = count;
      this.maxNearbyEntities = nearby;
      this.spawnRange = range;
      this.playerRange = playerRange;
   }

   public void apply(SpawnerBlockEntity entity) {
      BaseSpawner base = entity.f_59788_;
      base.f_45442_ = this.spawnDelay;
      base.f_45447_ = this.minDelay;
      base.f_45448_ = this.maxDelay;
      base.f_45449_ = this.spawnCount;
      base.f_45451_ = this.maxNearbyEntities;
      base.f_45453_ = this.spawnRange;
      base.f_45452_ = this.playerRange;
   }
}
