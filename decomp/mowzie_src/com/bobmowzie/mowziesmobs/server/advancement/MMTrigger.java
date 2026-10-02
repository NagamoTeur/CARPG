package com.bobmowzie.mowziesmobs.server.advancement;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.server.PlayerAdvancements;

public abstract class MMTrigger<E extends CriterionTriggerInstance, T extends MMTrigger.Listener<E>> implements CriterionTrigger<E> {
   protected final Map<PlayerAdvancements, T> listeners = Maps.newHashMap();

   public void m_6467_(PlayerAdvancements playerAdvancements, net.minecraft.advancements.CriterionTrigger.Listener<E> listener) {
      MMTrigger.Listener<E> listeners = this.listeners.computeIfAbsent(playerAdvancements, this::createListener);
      listeners.add(listener);
   }

   public void m_6468_(PlayerAdvancements playerAdvancements, net.minecraft.advancements.CriterionTrigger.Listener<E> listener) {
      MMTrigger.Listener<E> listeners = this.listeners.get(playerAdvancements);
      if (listeners != null) {
         listeners.remove(listener);
         if (listeners.isEmpty()) {
            this.listeners.remove(playerAdvancements);
         }
      }
   }

   public void m_5656_(PlayerAdvancements playerAdvancementsIn) {
      this.listeners.remove(playerAdvancementsIn);
   }

   public abstract T createListener(PlayerAdvancements var1);

   static class Listener<E extends CriterionTriggerInstance> {
      protected final PlayerAdvancements playerAdvancements;
      protected final Set<net.minecraft.advancements.CriterionTrigger.Listener<E>> listeners = Sets.newHashSet();

      public Listener(PlayerAdvancements playerAdvancementsIn) {
         this.playerAdvancements = playerAdvancementsIn;
      }

      public boolean isEmpty() {
         return this.listeners.isEmpty();
      }

      public void add(net.minecraft.advancements.CriterionTrigger.Listener<E> listener) {
         this.listeners.add(listener);
      }

      public void remove(net.minecraft.advancements.CriterionTrigger.Listener<E> listener) {
         this.listeners.remove(listener);
      }
   }
}
