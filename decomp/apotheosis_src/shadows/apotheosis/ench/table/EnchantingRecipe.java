package shadows.apotheosis.ench.table;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.apache.commons.lang3.tuple.Pair;
import shadows.apotheosis.Apoth;

public class EnchantingRecipe implements Recipe<Container> {
   public static final EnchantingRecipe.Serializer SERIALIZER = new EnchantingRecipe.Serializer();
   public static final EnchantingStatManager.Stats NO_MAX = new EnchantingStatManager.Stats(-1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1);
   protected final ResourceLocation id;
   protected final ItemStack output;
   protected final Ingredient input;
   protected final EnchantingStatManager.Stats requirements;
   protected final EnchantingStatManager.Stats maxRequirements;

   public EnchantingRecipe(
      ResourceLocation id, ItemStack output, Ingredient input, EnchantingStatManager.Stats requirements, EnchantingStatManager.Stats maxRequirements
   ) {
      this.id = id;
      this.output = output;
      this.input = input;
      this.requirements = requirements;
      this.maxRequirements = maxRequirements;
   }

   public boolean matches(ItemStack input, float eterna, float quanta, float arcana) {
      return (!(this.maxRequirements.eterna() > -1.0F) || !(eterna > this.maxRequirements.eterna()))
            && (!(this.maxRequirements.quanta() > -1.0F) || !(quanta > this.maxRequirements.quanta()))
            && (!(this.maxRequirements.arcana() > -1.0F) || !(arcana > this.maxRequirements.arcana()))
         ? this.input.test(input) && eterna >= this.requirements.eterna() && quanta >= this.requirements.quanta() && arcana >= this.requirements.arcana()
         : false;
   }

   public EnchantingStatManager.Stats getRequirements() {
      return this.requirements;
   }

   public EnchantingStatManager.Stats getMaxRequirements() {
      return this.maxRequirements;
   }

   public Ingredient getInput() {
      return this.input;
   }

   @Deprecated
   public boolean m_5818_(Container pContainer, Level pLevel) {
      return false;
   }

   @Deprecated
   public ItemStack m_5874_(Container pContainer) {
      return ItemStack.f_41583_;
   }

   @Deprecated
   public boolean m_8004_(int pWidth, int pHeight) {
      return false;
   }

   public ItemStack m_8043_() {
      return this.output;
   }

   public ItemStack assemble(ItemStack input, float eterna, float quanta, float arcana) {
      return this.m_8043_().m_41777_();
   }

   public ResourceLocation m_6423_() {
      return this.id;
   }

   public RecipeSerializer<?> m_7707_() {
      return SERIALIZER;
   }

   public RecipeType<?> m_6671_() {
      return Apoth.RecipeTypes.INFUSION;
   }

   protected static Pair<EnchantingStatManager.Stats, EnchantingStatManager.Stats> readStats(ResourceLocation id, JsonObject obj) {
      EnchantingStatManager.Stats stats = (EnchantingStatManager.Stats)((com.mojang.datafixers.util.Pair)EnchantingStatManager.Stats.CODEC
            .decode(JsonOps.INSTANCE, obj.get("requirements"))
            .get()
            .left()
            .get())
         .getFirst();
      EnchantingStatManager.Stats maxStats = obj.has("max_requirements")
         ? (EnchantingStatManager.Stats)((com.mojang.datafixers.util.Pair)EnchantingStatManager.Stats.CODEC
               .decode(JsonOps.INSTANCE, obj.get("max_requirements"))
               .get()
               .left()
               .get())
            .getFirst()
         : NO_MAX;
      if (maxStats.eterna() != -1.0F && stats.eterna() > maxStats.eterna()) {
         throw new JsonParseException("An enchanting recipe (" + id + ") has invalid min/max eterna bounds (min > max).");
      } else if (maxStats.quanta() != -1.0F && stats.quanta() > maxStats.quanta()) {
         throw new JsonParseException("An enchanting recipe (" + id + ") has invalid min/max quanta bounds (min > max).");
      } else if (maxStats.arcana() != -1.0F && stats.arcana() > maxStats.arcana()) {
         throw new JsonParseException("An enchanting recipe (" + id + ") has invalid min/max arcana bounds (min > max).");
      } else {
         return Pair.of(stats, maxStats);
      }
   }

   @Nullable
   public static EnchantingRecipe findMatch(Level level, ItemStack input, float eterna, float quanta, float arcana) {
      List<EnchantingRecipe> recipes = new ArrayList<>(level.m_7465_().m_44013_(Apoth.RecipeTypes.INFUSION));
      recipes.sort((r1, r2) -> -Float.compare(r1.requirements.eterna(), r2.requirements.eterna()));

      for (EnchantingRecipe r : recipes) {
         if (r.matches(input, eterna, quanta, arcana)) {
            return r;
         }
      }

      return null;
   }

   public static EnchantingRecipe findItemMatch(Level level, ItemStack toEnchant) {
      return level.m_7465_().m_44013_(Apoth.RecipeTypes.INFUSION).stream().filter(r -> r.getInput().test(toEnchant)).findFirst().orElse(null);
   }

   public static class Serializer implements RecipeSerializer<EnchantingRecipe> {
      protected static final Gson GSON = new GsonBuilder().create();

      public EnchantingRecipe fromJson(ResourceLocation id, JsonObject obj) {
         ItemStack output = CraftingHelper.getItemStack(obj.get("result").getAsJsonObject(), true, true);
         Ingredient input = Ingredient.m_43917_(obj.get("input"));
         Pair<EnchantingStatManager.Stats, EnchantingStatManager.Stats> requirements = EnchantingRecipe.readStats(id, obj);
         return new EnchantingRecipe(
            id, output, input, (EnchantingStatManager.Stats)requirements.getLeft(), (EnchantingStatManager.Stats)requirements.getRight()
         );
      }

      public EnchantingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
         ItemStack output = buf.m_130267_();
         Ingredient input = Ingredient.m_43940_(buf);
         EnchantingStatManager.Stats stats = EnchantingStatManager.Stats.read(buf);
         EnchantingStatManager.Stats maxStats = buf.readBoolean() ? EnchantingStatManager.Stats.read(buf) : EnchantingRecipe.NO_MAX;
         return new EnchantingRecipe(id, output, input, stats, maxStats);
      }

      public void toNetwork(FriendlyByteBuf buf, EnchantingRecipe recipe) {
         buf.m_130055_(recipe.output);
         recipe.input.m_43923_(buf);
         recipe.requirements.write(buf);
         buf.writeBoolean(recipe.maxRequirements != EnchantingRecipe.NO_MAX);
         if (recipe.maxRequirements != EnchantingRecipe.NO_MAX) {
            recipe.maxRequirements.write(buf);
         }
      }
   }
}
