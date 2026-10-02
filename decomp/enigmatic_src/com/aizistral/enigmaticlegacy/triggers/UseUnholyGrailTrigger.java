package com.aizistral.enigmaticlegacy.triggers;

import com.google.gson.JsonObject;
import javax.annotation.Nonnull;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.EntityPredicate.Composite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

public class UseUnholyGrailTrigger extends SimpleCriterionTrigger<UseUnholyGrailTrigger.Instance> {
   public static final ResourceLocation ID = new ResourceLocation("enigmaticlegacy", "unholy_grail_drink");
   public static final UseUnholyGrailTrigger INSTANCE = new UseUnholyGrailTrigger();

   private UseUnholyGrailTrigger() {
   }

   @Nonnull
   public ResourceLocation m_7295_() {
      return ID;
   }

   @Nonnull
   public UseUnholyGrailTrigger.Instance createInstance(@Nonnull JsonObject json, @Nonnull Composite playerPred, DeserializationContext conditions) {
      return new UseUnholyGrailTrigger.Instance(playerPred, GsonHelper.m_13851_(json, "is_the_worthy_one", null));
   }

   public void trigger(ServerPlayer player, boolean isTheWorthyOne) {
      this.m_66234_(player, instance -> instance.test(isTheWorthyOne));
   }

   static class Instance extends AbstractCriterionTriggerInstance {
      private final Boolean isTheWorthyOne;

      Instance(Composite playerPred, String isTheWorthyOne) {
         super(UseUnholyGrailTrigger.ID, playerPred);
         this.isTheWorthyOne = isTheWorthyOne != null ? Boolean.parseBoolean(isTheWorthyOne) : null;
      }

      @Nonnull
      public ResourceLocation m_7294_() {
         return UseUnholyGrailTrigger.ID;
      }

      boolean test(boolean isTheWorthyOne) {
         return this.isTheWorthyOne != null ? this.isTheWorthyOne == isTheWorthyOne : true;
      }
   }
}
