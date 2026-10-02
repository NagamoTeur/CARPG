package shadows.apotheosis.advancements;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTrigger.Listener;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.EntityPredicate.Composite;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import shadows.apotheosis.spawn.modifiers.SpawnerModifier;
import shadows.apotheosis.spawn.modifiers.SpawnerStats;
import shadows.apotheosis.spawn.spawner.ApothSpawnerTile;

public class ModifierTrigger implements CriterionTrigger<ModifierTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("apotheosis", "spawner_modifier");
   private final Map<PlayerAdvancements, ModifierTrigger.Listeners> listeners = Maps.newHashMap();

   public ResourceLocation m_7295_() {
      return ID;
   }

   public void m_6467_(PlayerAdvancements playerAdvancementsIn, Listener<ModifierTrigger.Instance> listener) {
      ModifierTrigger.Listeners ModifierTrigger$listeners = this.listeners.get(playerAdvancementsIn);
      if (ModifierTrigger$listeners == null) {
         ModifierTrigger$listeners = new ModifierTrigger.Listeners(playerAdvancementsIn);
         this.listeners.put(playerAdvancementsIn, ModifierTrigger$listeners);
      }

      ModifierTrigger$listeners.add(listener);
   }

   public void m_6468_(PlayerAdvancements playerAdvancementsIn, Listener<ModifierTrigger.Instance> listener) {
      ModifierTrigger.Listeners ModifierTrigger$listeners = this.listeners.get(playerAdvancementsIn);
      if (ModifierTrigger$listeners != null) {
         ModifierTrigger$listeners.remove(listener);
         if (ModifierTrigger$listeners.isEmpty()) {
            this.listeners.remove(playerAdvancementsIn);
         }
      }
   }

   public void m_5656_(PlayerAdvancements playerAdvancementsIn) {
      this.listeners.remove(playerAdvancementsIn);
   }

   public ModifierTrigger.Instance createInstance(JsonObject json, DeserializationContext conditionsParser) {
      Ints minDelay = Ints.m_55373_(json.get(SpawnerStats.MIN_DELAY.getId()));
      Ints maxDelay = Ints.m_55373_(json.get(SpawnerStats.MAX_DELAY.getId()));
      Ints spawnCount = Ints.m_55373_(json.get(SpawnerStats.SPAWN_COUNT.getId()));
      Ints nearbyEnts = Ints.m_55373_(json.get(SpawnerStats.MAX_NEARBY_ENTITIES.getId()));
      Ints playerRange = Ints.m_55373_(json.get(SpawnerStats.REQ_PLAYER_RANGE.getId()));
      Ints spawnRange = Ints.m_55373_(json.get(SpawnerStats.SPAWN_RANGE.getId()));
      Boolean ignorePlayers = json.has(SpawnerStats.IGNORE_PLAYERS.getId()) ? json.get(SpawnerStats.IGNORE_PLAYERS.getId()).getAsBoolean() : null;
      Boolean ignoreConditions = json.has(SpawnerStats.IGNORE_CONDITIONS.getId()) ? json.get(SpawnerStats.IGNORE_CONDITIONS.getId()).getAsBoolean() : null;
      Boolean redstone = json.has(SpawnerStats.REDSTONE_CONTROL.getId()) ? json.get(SpawnerStats.REDSTONE_CONTROL.getId()).getAsBoolean() : null;
      Boolean ignoreLight = json.has(SpawnerStats.IGNORE_LIGHT.getId()) ? json.get(SpawnerStats.IGNORE_LIGHT.getId()).getAsBoolean() : null;
      Boolean noAI = json.has(SpawnerStats.NO_AI.getId()) ? json.get(SpawnerStats.NO_AI.getId()).getAsBoolean() : null;
      return new ModifierTrigger.Instance(
         minDelay, maxDelay, spawnCount, nearbyEnts, playerRange, spawnRange, ignorePlayers, ignoreConditions, redstone, ignoreLight, noAI
      );
   }

   public void trigger(ServerPlayer player, ApothSpawnerTile tile, SpawnerModifier modif) {
      ModifierTrigger.Listeners ModifierTrigger$listeners = this.listeners.get(player.m_8960_());
      if (ModifierTrigger$listeners != null) {
         ModifierTrigger$listeners.trigger(tile, modif);
      }
   }

   public static class Instance extends AbstractCriterionTriggerInstance {
      private final Ints minDelay;
      private final Ints maxDelay;
      private final Ints spawnCount;
      private final Ints nearbyEnts;
      private final Ints playerRange;
      private final Ints spawnRange;
      private final Boolean ignorePlayers;
      private final Boolean ignoreConditions;
      private final Boolean redstone;
      private final Boolean ignoreLight;
      private final Boolean noAI;

      public Instance(
         Ints minDelay,
         Ints maxDelay,
         Ints spawnCount,
         Ints nearbyEnts,
         Ints playerRange,
         Ints spawnRange,
         Boolean ignorePlayers,
         Boolean ignoreConditions,
         Boolean redstone,
         Boolean ignoreLight,
         Boolean noAI
      ) {
         super(ModifierTrigger.ID, Composite.f_36667_);
         this.minDelay = minDelay;
         this.maxDelay = maxDelay;
         this.spawnCount = spawnCount;
         this.nearbyEnts = nearbyEnts;
         this.playerRange = playerRange;
         this.spawnRange = spawnRange;
         this.ignorePlayers = ignorePlayers;
         this.ignoreConditions = ignoreConditions;
         this.redstone = redstone;
         this.ignoreLight = ignoreLight;
         this.noAI = noAI;
      }

      public JsonObject m_7683_(SerializationContext serializer) {
         return new JsonObject();
      }

      public boolean test(ApothSpawnerTile tile, SpawnerModifier modif) {
         ApothSpawnerTile.SpawnerLogicExt logic = (ApothSpawnerTile.SpawnerLogicExt)tile.f_59788_;
         if (!this.minDelay.m_55390_(logic.f_45447_)
            || !this.maxDelay.m_55390_(logic.f_45448_)
            || !this.spawnCount.m_55390_(logic.f_45449_)
            || !this.nearbyEnts.m_55390_(logic.f_45451_)) {
            return false;
         } else if (!this.playerRange.m_55390_(logic.f_45452_)) {
            return false;
         } else if (!this.spawnRange.m_55390_(logic.f_45453_)) {
            return false;
         } else if (this.ignorePlayers != null && tile.ignoresPlayers != this.ignorePlayers) {
            return false;
         } else if (this.ignoreConditions != null && tile.ignoresConditions != this.ignoreConditions) {
            return false;
         } else if (this.redstone != null && tile.redstoneControl != this.redstone) {
            return false;
         } else {
            return this.ignoreLight != null && tile.ignoresLight != this.ignoreLight ? false : this.noAI == null || tile.hasNoAI == this.noAI;
         }
      }
   }

   static class Listeners {
      private final PlayerAdvancements playerAdvancements;
      private final Set<Listener<ModifierTrigger.Instance>> listeners = Sets.newHashSet();

      public Listeners(PlayerAdvancements playerAdvancementsIn) {
         this.playerAdvancements = playerAdvancementsIn;
      }

      public boolean isEmpty() {
         return this.listeners.isEmpty();
      }

      public void add(Listener<ModifierTrigger.Instance> listener) {
         this.listeners.add(listener);
      }

      public void remove(Listener<ModifierTrigger.Instance> listener) {
         this.listeners.remove(listener);
      }

      public void trigger(ApothSpawnerTile tile, SpawnerModifier modif) {
         List<Listener<ModifierTrigger.Instance>> list = null;

         for (Listener<ModifierTrigger.Instance> listener : this.listeners) {
            if (((ModifierTrigger.Instance)listener.m_13685_()).test(tile, modif)) {
               if (list == null) {
                  list = Lists.newArrayList();
               }

               list.add(listener);
            }
         }

         if (list != null) {
            for (Listener<ModifierTrigger.Instance> listener1 : list) {
               listener1.m_13686_(this.playerAdvancements);
            }
         }
      }
   }
}
