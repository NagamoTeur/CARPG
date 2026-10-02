package com.aizistral.enigmaticlegacy.objects;

import com.aizistral.enigmaticlegacy.config.OmniconfigHandler;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.common.crafting.conditions.ICondition.IContext;
import net.minecraftforge.registries.ForgeRegistries;

public class EnabledCondition implements ICondition {
   private static final ResourceLocation ID = new ResourceLocation("enigmaticlegacy", "is_enabled");
   private final ResourceLocation item;

   public EnabledCondition(ResourceLocation item) {
      this.item = item;
   }

   public ResourceLocation getID() {
      return ID;
   }

   public boolean test(IContext context) {
      Item item = (Item)ForgeRegistries.ITEMS.getValue(this.item);
      return this.item.toString().equals("enigmaticlegacy:bonuswoolrecipes")
         ? OmniconfigHandler.bonusWoolRecipesEnabled.getValue()
         : OmniconfigHandler.isItemEnabled(item);
   }

   public static class Serializer implements IConditionSerializer<EnabledCondition> {
      public static final EnabledCondition.Serializer INSTANCE = new EnabledCondition.Serializer();

      public void write(JsonObject json, EnabledCondition value) {
         json.addProperty("item", value.item.toString());
      }

      public EnabledCondition read(JsonObject json) {
         return new EnabledCondition(new ResourceLocation(GsonHelper.m_13906_(json, "item")));
      }

      public ResourceLocation getID() {
         return EnabledCondition.ID;
      }
   }
}
