package com.aizistral.enigmaticlegacy.triggers;

import com.google.gson.JsonObject;
import javax.annotation.Nonnull;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.EntityPredicate.Composite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ForbiddenFruitTrigger extends SimpleCriterionTrigger<ForbiddenFruitTrigger.Instance> {
   public static final ResourceLocation ID = new ResourceLocation("enigmaticlegacy", "consume_forbidden_fruit");
   public static final ForbiddenFruitTrigger INSTANCE = new ForbiddenFruitTrigger();

   private ForbiddenFruitTrigger() {
   }

   @Nonnull
   public ResourceLocation m_7295_() {
      return ID;
   }

   @Nonnull
   public ForbiddenFruitTrigger.Instance createInstance(@Nonnull JsonObject json, @Nonnull Composite playerPred, DeserializationContext conditions) {
      return new ForbiddenFruitTrigger.Instance(playerPred);
   }

   public void trigger(ServerPlayer player) {
      this.m_66234_(player, instance -> instance.test());
   }

   static class Instance extends AbstractCriterionTriggerInstance {
      Instance(Composite playerPred) {
         super(ForbiddenFruitTrigger.ID, playerPred);
      }

      @Nonnull
      public ResourceLocation m_7294_() {
         return ForbiddenFruitTrigger.ID;
      }

      boolean test() {
         return true;
      }
   }
}
