package com.bobmowzie.mowziesmobs.server.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate.Composite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;

public class StealIceCrystalTrigger extends MMTrigger<AbstractCriterionTriggerInstance, StealIceCrystalTrigger.Listener> {
   public static final ResourceLocation ID = new ResourceLocation("mowziesmobs", "steal_ice_crystal");

   public ResourceLocation m_7295_() {
      return ID;
   }

   public AbstractCriterionTriggerInstance createInstance(JsonObject object, DeserializationContext conditions) {
      Composite player = Composite.m_36677_(object, "player", conditions);
      return new StealIceCrystalTrigger.Instance(player);
   }

   public StealIceCrystalTrigger.Listener createListener(PlayerAdvancements playerAdvancements) {
      return new StealIceCrystalTrigger.Listener(playerAdvancements);
   }

   public void trigger(ServerPlayer player) {
      StealIceCrystalTrigger.Listener listeners = this.listeners.get(player.m_8960_());
      if (listeners != null) {
         listeners.trigger();
      }
   }

   public static class Instance extends AbstractCriterionTriggerInstance {
      public Instance(Composite player) {
         super(StealIceCrystalTrigger.ID, player);
      }
   }

   static class Listener extends MMTrigger.Listener<AbstractCriterionTriggerInstance> {
      public Listener(PlayerAdvancements playerAdvancementsIn) {
         super(playerAdvancementsIn);
      }

      public void trigger() {
         this.listeners.stream().findFirst().ifPresent(listener -> listener.m_13686_(this.playerAdvancements));
      }
   }
}
