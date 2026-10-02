package com.hollingsworth.arsnouveau.api.recipe;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantingApparatusRecipe;
import com.hollingsworth.arsnouveau.setup.RecipeRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SummonRitualRecipe implements Recipe<Container> {
   public final ResourceLocation id;
   public final Ingredient catalyst;
   public final SummonRitualRecipe.MobSource mobSource;
   public final int count;
   public ArrayList<SummonRitualRecipe.WeightedMobType> mobs;
   public final List<SummonRitualRecipe.WeightedMobType> mobTypes = new ArrayList<>();

   public SummonRitualRecipe(
      ResourceLocation id, Ingredient catalyst, SummonRitualRecipe.MobSource source, int count, ArrayList<SummonRitualRecipe.WeightedMobType> mobs
   ) {
      this.id = id;
      this.catalyst = catalyst;
      this.mobSource = source;
      this.count = count;
      this.mobs = mobs;
   }

   public SummonRitualRecipe(ResourceLocation id, Ingredient catalyst, SummonRitualRecipe.MobSource source, int count) {
      this.id = id;
      this.catalyst = catalyst;
      this.mobSource = source;
      this.count = count;
   }

   public boolean m_5818_(Container pContainer, Level pLevel) {
      return false;
   }

   public boolean matches(List<ItemStack> augments) {
      return EnchantingApparatusRecipe.doItemsMatch(
         augments, Arrays.stream(this.catalyst.m_43908_()).map(xva$0 -> Ingredient.m_43927_(new ItemStack[]{xva$0})).toList()
      );
   }

   public ItemStack m_5874_(Container pContainer) {
      return ItemStack.f_41583_;
   }

   public boolean m_8004_(int pWidth, int pHeight) {
      return false;
   }

   public ItemStack m_8043_() {
      return ItemStack.f_41583_;
   }

   public ResourceLocation m_6423_() {
      return this.id;
   }

   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)RecipeRegistry.SUMMON_RITUAL_SERIALIZER.get();
   }

   public RecipeType<?> m_6671_() {
      return (RecipeType<?>)RecipeRegistry.SUMMON_RITUAL_TYPE.get();
   }

   public boolean m_5598_() {
      return true;
   }

   public JsonElement asRecipe() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", "ars_nouveau:summon_ritual");
      JsonArray mobs = new JsonArray();
      this.mobs.forEach(mob -> mobs.add(mob.toJson()));
      jsonobject.add("mobs", mobs);
      jsonobject.addProperty("source", this.mobSource.toString());
      jsonobject.addProperty("count", this.count);
      jsonobject.add("augment", this.catalyst.m_43942_());
      return jsonobject;
   }

   public static enum MobSource {
      CURRENT_BIOME,
      MOB_LIST;
   }

   public static class Serializer implements RecipeSerializer<SummonRitualRecipe> {
      public SummonRitualRecipe fromJson(ResourceLocation pRecipeId, JsonObject json) {
         Ingredient augment = Ingredient.m_43917_(
            (JsonElement)(GsonHelper.m_13885_(json, "augment") ? GsonHelper.m_13933_(json, "augment") : GsonHelper.m_13930_(json, "augment"))
         );
         SummonRitualRecipe.MobSource source = SummonRitualRecipe.MobSource.valueOf(GsonHelper.m_13851_(json, "source", "MOB_LIST"));
         ArrayList<SummonRitualRecipe.WeightedMobType> mobs = new ArrayList<>();
         if (json.has("mob")) {
            mobs.add(new SummonRitualRecipe.WeightedMobType(ResourceLocation.m_135820_(json.get("mob").getAsString())));
         }

         if (json.has("mobs")) {
            GsonHelper.m_13933_(json, "mobs").forEach(el -> mobs.add(SummonRitualRecipe.WeightedMobType.fromJson(el.getAsJsonObject())));
         }

         int count = GsonHelper.m_13824_(json, "count", 1);
         return new SummonRitualRecipe(pRecipeId, augment, source, count, mobs);
      }

      @Nullable
      public SummonRitualRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
         Ingredient catalyst = Ingredient.m_43940_(pBuffer);
         SummonRitualRecipe.MobSource source = (SummonRitualRecipe.MobSource)pBuffer.m_130066_(SummonRitualRecipe.MobSource.class);
         int count = pBuffer.readInt();
         ArrayList<SummonRitualRecipe.WeightedMobType> mobs = (ArrayList<SummonRitualRecipe.WeightedMobType>)pBuffer.m_236838_(
            Lists::newArrayListWithCapacity, new SummonRitualRecipe.WeightedMobType.Reader()
         );
         return new SummonRitualRecipe(pRecipeId, catalyst, source, count, mobs);
      }

      public void toNetwork(FriendlyByteBuf pBuffer, SummonRitualRecipe pRecipe) {
         pRecipe.catalyst.m_43923_(pBuffer);
         pBuffer.m_130068_(pRecipe.mobSource);
         pBuffer.writeInt(pRecipe.count);
         pBuffer.m_236828_(pRecipe.mobs, new SummonRitualRecipe.WeightedMobType.Writer());
      }
   }

   public static record WeightedMobType(ResourceLocation mob, int weight) implements WeightedEntry {
      public WeightedMobType(ResourceLocation mob) {
         this(mob, 1);
      }

      public JsonObject toJson() {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("mob", this.mob.toString());
         jsonobject.addProperty("weight", this.weight);
         return jsonobject;
      }

      public static SummonRitualRecipe.WeightedMobType fromJson(JsonObject json) {
         return new SummonRitualRecipe.WeightedMobType(ResourceLocation.m_135820_(GsonHelper.m_13906_(json, "mob")), GsonHelper.m_13927_(json, "weight"));
      }

      @NotNull
      public Weight m_142631_() {
         return Weight.m_146282_(this.weight);
      }

      public static class Reader implements net.minecraft.network.FriendlyByteBuf.Reader<SummonRitualRecipe.WeightedMobType> {
         public SummonRitualRecipe.WeightedMobType apply(FriendlyByteBuf friendlyByteBuf) {
            return new SummonRitualRecipe.WeightedMobType(friendlyByteBuf.m_130281_(), friendlyByteBuf.readInt());
         }
      }

      public static class Writer implements net.minecraft.network.FriendlyByteBuf.Writer<SummonRitualRecipe.WeightedMobType> {
         public void accept(FriendlyByteBuf friendlyByteBuf, SummonRitualRecipe.WeightedMobType weightedMobType) {
            friendlyByteBuf.m_130085_(weightedMobType.mob);
            friendlyByteBuf.writeInt(weightedMobType.weight);
         }
      }
   }
}
