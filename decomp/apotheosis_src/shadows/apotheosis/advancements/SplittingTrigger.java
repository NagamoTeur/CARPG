package shadows.apotheosis.advancements;

import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTrigger.Listener;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate.Composite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;

public class SplittingTrigger implements CriterionTrigger<AbstractCriterionTriggerInstance> {
   private static final ResourceLocation ID = new ResourceLocation("apotheosis", "splitting");
   Map<PlayerAdvancements, Set<Listener<AbstractCriterionTriggerInstance>>> listeners = new HashMap<>();

   public ResourceLocation m_7295_() {
      return ID;
   }

   public void m_6467_(PlayerAdvancements adv, Listener<AbstractCriterionTriggerInstance> listener) {
      this.listeners.computeIfAbsent(adv, a -> new HashSet<>()).add(listener);
   }

   public void m_6468_(PlayerAdvancements adv, Listener<AbstractCriterionTriggerInstance> listener) {
      this.listeners.computeIfAbsent(adv, a -> new HashSet<>()).remove(listener);
   }

   public void m_5656_(PlayerAdvancements adv) {
      this.listeners.remove(adv);
   }

   public AbstractCriterionTriggerInstance createInstance(JsonObject json, DeserializationContext parser) {
      return new AbstractCriterionTriggerInstance(ID, Composite.f_36667_) {
      };
   }

   public void trigger(PlayerAdvancements adv) {
      if (this.listeners.containsKey(adv)) {
         new HashSet<>(this.listeners.get(adv)).forEach(t -> t.m_13686_(adv));
      }
   }
}
