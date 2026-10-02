package com.aizistral.enigmaticlegacy.triggers;

import com.google.gson.JsonObject;
import javax.annotation.Nonnull;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.EntityPredicate.Composite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class BeheadingTrigger extends SimpleCriterionTrigger<BeheadingTrigger.Instance> {
   public static final ResourceLocation ID = new ResourceLocation("enigmaticlegacy", "forbidden_axe_beheading");
   public static final BeheadingTrigger INSTANCE = new BeheadingTrigger();

   private BeheadingTrigger() {
   }

   @Nonnull
   public ResourceLocation m_7295_() {
      return ID;
   }

   @Nonnull
   public BeheadingTrigger.Instance createInstance(@Nonnull JsonObject json, @Nonnull Composite playerPred, DeserializationContext conditions) {
      return new BeheadingTrigger.Instance(playerPred);
   }

   public void trigger(ServerPlayer player) {
      this.m_66234_(player, instance -> instance.test());
   }

   static class Instance extends AbstractCriterionTriggerInstance {
      Instance(Composite playerPred) {
         super(BeheadingTrigger.ID, playerPred);
      }

      @Nonnull
      public ResourceLocation m_7294_() {
         return BeheadingTrigger.ID;
      }

      boolean test() {
         return true;
      }
   }
}
