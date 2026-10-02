package shadows.apotheosis.ench.table;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.apache.commons.lang3.tuple.Pair;

public class KeepNBTEnchantingRecipe extends EnchantingRecipe {
   public static final KeepNBTEnchantingRecipe.Serializer SERIALIZER = new KeepNBTEnchantingRecipe.Serializer();

   public KeepNBTEnchantingRecipe(
      ResourceLocation id, ItemStack output, Ingredient input, EnchantingStatManager.Stats requirements, EnchantingStatManager.Stats maxRequirements
   ) {
      super(id, output, input, requirements, maxRequirements);
   }

   @Override
   public ItemStack assemble(ItemStack input, float eterna, float quanta, float arcana) {
      ItemStack out = this.m_8043_().m_41777_();
      if (input.m_41782_()) {
         out.m_41751_(input.m_41783_().m_6426_());
      }

      return out;
   }

   @Override
   public RecipeSerializer<?> m_7707_() {
      return SERIALIZER;
   }

   public static class Serializer extends EnchantingRecipe.Serializer {
      public KeepNBTEnchantingRecipe fromJson(ResourceLocation id, JsonObject obj) {
         ItemStack output = CraftingHelper.getItemStack(obj.get("result").getAsJsonObject(), true, true);
         Ingredient input = Ingredient.m_43917_(obj.get("input"));
         Pair<EnchantingStatManager.Stats, EnchantingStatManager.Stats> requirements = EnchantingRecipe.readStats(id, obj);
         return new KeepNBTEnchantingRecipe(
            id, output, input, (EnchantingStatManager.Stats)requirements.getLeft(), (EnchantingStatManager.Stats)requirements.getRight()
         );
      }
   }
}
