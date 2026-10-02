package shadows.apotheosis.spawn.modifiers;

import com.google.gson.JsonElement;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.util.Mth;
import shadows.apotheosis.spawn.spawner.ApothSpawnerTile;

public class SpawnerStats {
   public static final Map<String, SpawnerStat<?>> REGISTRY = new HashMap<>();
   public static final SpawnerStat<Integer> MIN_DELAY = register(
      new SpawnerStats.IntStat("min_delay", s -> s.f_59788_.f_45447_, (s, v) -> s.f_59788_.f_45447_ = v)
   );
   public static final SpawnerStat<Integer> MAX_DELAY = register(
      new SpawnerStats.IntStat("max_delay", s -> s.f_59788_.f_45448_, (s, v) -> s.f_59788_.f_45448_ = v)
   );
   public static final SpawnerStat<Integer> SPAWN_COUNT = register(
      new SpawnerStats.IntStat("spawn_count", s -> s.f_59788_.f_45449_, (s, v) -> s.f_59788_.f_45449_ = v)
   );
   public static final SpawnerStat<Integer> MAX_NEARBY_ENTITIES = register(
      new SpawnerStats.IntStat("max_nearby_entities", s -> s.f_59788_.f_45451_, (s, v) -> s.f_59788_.f_45451_ = v)
   );
   public static final SpawnerStat<Integer> REQ_PLAYER_RANGE = register(
      new SpawnerStats.IntStat("req_player_range", s -> s.f_59788_.f_45452_, (s, v) -> s.f_59788_.f_45452_ = v)
   );
   public static final SpawnerStat<Integer> SPAWN_RANGE = register(
      new SpawnerStats.IntStat("spawn_range", s -> s.f_59788_.f_45453_, (s, v) -> s.f_59788_.f_45453_ = v)
   );
   public static final SpawnerStat<Boolean> IGNORE_PLAYERS = register(
      new SpawnerStats.BoolStat("ignore_players", s -> s.ignoresPlayers, (s, v) -> s.ignoresPlayers = v)
   );
   public static final SpawnerStat<Boolean> IGNORE_CONDITIONS = register(
      new SpawnerStats.BoolStat("ignore_conditions", s -> s.ignoresConditions, (s, v) -> s.ignoresConditions = v)
   );
   public static final SpawnerStat<Boolean> REDSTONE_CONTROL = register(
      new SpawnerStats.BoolStat("redstone_control", s -> s.redstoneControl, (s, v) -> s.redstoneControl = v)
   );
   public static final SpawnerStat<Boolean> IGNORE_LIGHT = register(
      new SpawnerStats.BoolStat("ignore_light", s -> s.ignoresLight, (s, v) -> s.ignoresLight = v)
   );
   public static final SpawnerStat<Boolean> NO_AI = register(new SpawnerStats.BoolStat("no_ai", s -> s.hasNoAI, (s, v) -> s.hasNoAI = v));
   public static final SpawnerStat<Boolean> SILENT = register(new SpawnerStats.BoolStat("silent", s -> s.silent, (s, v) -> s.silent = v));

   private static <T extends SpawnerStat<?>> T register(T t) {
      REGISTRY.put(t.getId(), t);
      return t;
   }

   private abstract static class Base<T> implements SpawnerStat<T> {
      protected final String id;
      protected final Function<ApothSpawnerTile, T> getter;
      protected final BiConsumer<ApothSpawnerTile, T> setter;

      private Base(String id, Function<ApothSpawnerTile, T> getter, BiConsumer<ApothSpawnerTile, T> setter) {
         this.id = id;
         this.getter = getter;
         this.setter = setter;
      }

      @Override
      public String getId() {
         return this.id;
      }
   }

   private static class BoolStat extends SpawnerStats.Base<Boolean> {
      private BoolStat(String id, Function<ApothSpawnerTile, Boolean> getter, BiConsumer<ApothSpawnerTile, Boolean> setter) {
         super(id, getter, setter);
      }

      public Boolean parseValue(JsonElement value) {
         return value == null ? false : value.getAsBoolean();
      }

      public boolean apply(Boolean value, Boolean min, Boolean max, ApothSpawnerTile spawner) {
         boolean old = this.getter.apply(spawner);
         this.setter.accept(spawner, value);
         return old != this.getter.apply(spawner);
      }

      @Override
      public Class<Boolean> getTypeClass() {
         return Boolean.class;
      }
   }

   private static class IntStat extends SpawnerStats.Base<Integer> {
      private IntStat(String id, Function<ApothSpawnerTile, Integer> getter, BiConsumer<ApothSpawnerTile, Integer> setter) {
         super(id, getter, setter);
      }

      public Integer parseValue(JsonElement value) {
         return value == null ? 0 : value.getAsInt();
      }

      public boolean apply(Integer value, Integer min, Integer max, ApothSpawnerTile spawner) {
         int old = this.getter.apply(spawner);
         this.setter.accept(spawner, Mth.m_14045_(old + value, min, max));
         return old != this.getter.apply(spawner);
      }

      @Override
      public Class<Integer> getTypeClass() {
         return Integer.class;
      }
   }
}
