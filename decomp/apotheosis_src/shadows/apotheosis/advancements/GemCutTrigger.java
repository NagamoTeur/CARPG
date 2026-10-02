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
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.EntityPredicate.Composite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import shadows.apotheosis.adventure.loot.LootRarity;

public class GemCutTrigger implements CriterionTrigger<GemCutTrigger.Instance> {
   private static final ResourceLocation ID = new ResourceLocation("apotheosis", "gem_cutting");
   private final Map<PlayerAdvancements, GemCutTrigger.Listeners> listeners = Maps.newHashMap();

   public ResourceLocation m_7295_() {
      return ID;
   }

   public void m_6467_(PlayerAdvancements playerAdvancementsIn, Listener<GemCutTrigger.Instance> listener) {
      GemCutTrigger.Listeners ModifierTrigger$listeners = this.listeners.get(playerAdvancementsIn);
      if (ModifierTrigger$listeners == null) {
         ModifierTrigger$listeners = new GemCutTrigger.Listeners(playerAdvancementsIn);
         this.listeners.put(playerAdvancementsIn, ModifierTrigger$listeners);
      }

      ModifierTrigger$listeners.add(listener);
   }

   public void m_6468_(PlayerAdvancements playerAdvancementsIn, Listener<GemCutTrigger.Instance> listener) {
      GemCutTrigger.Listeners ModifierTrigger$listeners = this.listeners.get(playerAdvancementsIn);
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

   public GemCutTrigger.Instance createInstance(JsonObject json, DeserializationContext conditionsParser) {
      json = json.getAsJsonObject("conditions");
      if (json != null) {
         ItemPredicate item = ItemPredicate.m_45051_(json.get("item"));
         LootRarity rarity = LootRarity.byId(GsonHelper.m_13851_(json, "rarity", ""));
         return new GemCutTrigger.Instance(item, rarity);
      } else {
         return new GemCutTrigger.Instance(ItemPredicate.f_45028_, null);
      }
   }

   public void trigger(ServerPlayer player, ItemStack stack, LootRarity rarity) {
      GemCutTrigger.Listeners ModifierTrigger$listeners = this.listeners.get(player.m_8960_());
      if (ModifierTrigger$listeners != null) {
         ModifierTrigger$listeners.trigger(stack, rarity);
      }
   }

   public static class Instance extends AbstractCriterionTriggerInstance {
      private final ItemPredicate gem;
      private final LootRarity rarity;

      public Instance(ItemPredicate gem, LootRarity rarity) {
         super(GemCutTrigger.ID, Composite.f_36667_);
         this.gem = gem;
         this.rarity = rarity;
      }

      public JsonObject m_7683_(SerializationContext serializer) {
         return new JsonObject();
      }

      public boolean test(ItemStack stack, LootRarity rarity) {
         return this.gem.m_45049_(stack) && (this.rarity == null || rarity == this.rarity);
      }
   }

   static class Listeners {
      private final PlayerAdvancements playerAdvancements;
      private final Set<Listener<GemCutTrigger.Instance>> listeners = Sets.newHashSet();

      public Listeners(PlayerAdvancements playerAdvancementsIn) {
         this.playerAdvancements = playerAdvancementsIn;
      }

      public boolean isEmpty() {
         return this.listeners.isEmpty();
      }

      public void add(Listener<GemCutTrigger.Instance> listener) {
         this.listeners.add(listener);
      }

      public void remove(Listener<GemCutTrigger.Instance> listener) {
         this.listeners.remove(listener);
      }

      public void trigger(ItemStack stack, LootRarity rarity) {
         List<Listener<GemCutTrigger.Instance>> list = null;

         for (Listener<GemCutTrigger.Instance> listener : this.listeners) {
            if (((GemCutTrigger.Instance)listener.m_13685_()).test(stack, rarity)) {
               if (list == null) {
                  list = Lists.newArrayList();
               }

               list.add(listener);
            }
         }

         if (list != null) {
            for (Listener<GemCutTrigger.Instance> listener1 : list) {
               listener1.m_13686_(this.playerAdvancements);
            }
         }
      }
   }
}
