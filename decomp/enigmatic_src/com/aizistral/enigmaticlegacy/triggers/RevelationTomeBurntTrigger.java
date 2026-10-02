package com.aizistral.enigmaticlegacy.triggers;

import com.google.gson.JsonObject;
import javax.annotation.Nonnull;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.EntityPredicate.Composite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class RevelationTomeBurntTrigger extends SimpleCriterionTrigger<RevelationTomeBurntTrigger.Instance> {
   public static final ResourceLocation ID = new ResourceLocation("enigmaticlegacy", "revelation_tome_burnt");
   public static final RevelationTomeBurntTrigger INSTANCE = new RevelationTomeBurntTrigger();

   private RevelationTomeBurntTrigger() {
   }

   @Nonnull
   public ResourceLocation m_7295_() {
      return ID;
   }

   @Nonnull
   public RevelationTomeBurntTrigger.Instance createInstance(@Nonnull JsonObject json, @Nonnull Composite playerPred, DeserializationContext conditions) {
      return new RevelationTomeBurntTrigger.Instance(playerPred);
   }

   public void trigger(ServerPlayer player) {
      this.m_66234_(player, instance -> instance.test());
   }

   static class Instance extends AbstractCriterionTriggerInstance {
      Instance(Composite playerPred) {
         super(RevelationTomeBurntTrigger.ID, playerPred);
      }

      @Nonnull
      public ResourceLocation m_7294_() {
         return RevelationTomeBurntTrigger.ID;
      }

      boolean test() {
         return true;
      }
   }
}
