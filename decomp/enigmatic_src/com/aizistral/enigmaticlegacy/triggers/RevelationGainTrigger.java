package com.aizistral.enigmaticlegacy.triggers;

import com.aizistral.enigmaticlegacy.items.RevelationTome;
import com.google.gson.JsonObject;
import javax.annotation.Nonnull;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.EntityPredicate.Composite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

public class RevelationGainTrigger extends SimpleCriterionTrigger<RevelationGainTrigger.Instance> {
   public static final ResourceLocation ID = new ResourceLocation("enigmaticlegacy", "embrace_revelation");
   public static final RevelationGainTrigger INSTANCE = new RevelationGainTrigger();

   private RevelationGainTrigger() {
   }

   @Nonnull
   public ResourceLocation m_7295_() {
      return ID;
   }

   @Nonnull
   public RevelationGainTrigger.Instance createInstance(@Nonnull JsonObject json, @Nonnull Composite playerPred, DeserializationContext conditions) {
      return new RevelationGainTrigger.Instance(playerPred, GsonHelper.m_13906_(json, "point_type"), GsonHelper.m_13927_(json, "point_amount"));
   }

   public void trigger(ServerPlayer player, RevelationTome.TomeType type, int amount) {
      this.m_66234_(player, instance -> instance.test(type, amount));
   }

   static class Instance extends AbstractCriterionTriggerInstance {
      private final String revelationType;
      private final int requiredAmount;

      Instance(Composite playerPred, String type, int amount) {
         super(RevelationGainTrigger.ID, playerPred);
         this.revelationType = type;
         this.requiredAmount = amount;
      }

      @Nonnull
      public ResourceLocation m_7294_() {
         return RevelationGainTrigger.ID;
      }

      boolean test(RevelationTome.TomeType type, int count) {
         return RevelationTome.TomeType.resolveType(this.revelationType).equals(type) && count >= this.requiredAmount;
      }
   }
}
