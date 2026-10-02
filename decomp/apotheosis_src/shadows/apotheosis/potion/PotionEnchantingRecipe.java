package shadows.apotheosis.potion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.ench.table.EnchantingRecipe;
import shadows.apotheosis.ench.table.EnchantingStatManager;

public class PotionEnchantingRecipe extends EnchantingRecipe {
   public static final PotionEnchantingRecipe.Serializer SERIALIZER = new PotionEnchantingRecipe.Serializer();

   public PotionEnchantingRecipe(EnchantingStatManager.Stats requirements, EnchantingStatManager.Stats maxRequirements) {
      super(new ResourceLocation("apotheosis", "potion_charm_enchanting"), charm(), potion(), requirements, maxRequirements);
   }

   private static ItemStack charm() {
      ItemStack out = new ItemStack((ItemLike)Apoth.Items.POTION_CHARM.get());
      out.m_41784_().m_128379_("Unbreakable", true);
      return out;
   }

   private static Ingredient potion() {
      List<ItemStack> potionStacks = new ArrayList<>();

      for (Potion p : ForgeRegistries.POTIONS) {
         if (p.m_43488_().size() == 1 && !((MobEffectInstance)p.m_43488_().get(0)).m_19544_().m_8093_()) {
            ItemStack potion = new ItemStack((ItemLike)Apoth.Items.POTION_CHARM.get());
            PotionUtils.m_43549_(potion, p);
            potionStacks.add(potion);
         }
      }

      return Ingredient.m_43927_(potionStacks.toArray(new ItemStack[0]));
   }

   @Override
   public boolean matches(ItemStack input, float eterna, float quanta, float arcana) {
      return input.m_41782_() && input.m_41783_().m_128471_("Unbreakable") ? false : super.matches(input, eterna, quanta, arcana);
   }

   @Override
   public ItemStack assemble(ItemStack input, float eterna, float quanta, float arcana) {
      ItemStack out = input.m_41777_();
      out.m_41721_(0);
      out.m_41784_().m_128379_("Unbreakable", true);
      return out;
   }

   @Override
   public RecipeSerializer<?> m_7707_() {
      return SERIALIZER;
   }

   public static class Serializer implements RecipeSerializer<PotionEnchantingRecipe> {
      protected static final Gson GSON = new GsonBuilder().create();

      public PotionEnchantingRecipe fromJson(ResourceLocation id, JsonObject obj) {
         Pair<EnchantingStatManager.Stats, EnchantingStatManager.Stats> requirements = PotionEnchantingRecipe.readStats(id, obj);
         return new PotionEnchantingRecipe((EnchantingStatManager.Stats)requirements.getLeft(), (EnchantingStatManager.Stats)requirements.getRight());
      }

      public PotionEnchantingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
         EnchantingStatManager.Stats stats = EnchantingStatManager.Stats.read(buf);
         EnchantingStatManager.Stats maxStats = buf.readBoolean() ? EnchantingStatManager.Stats.read(buf) : EnchantingRecipe.NO_MAX;
         return new PotionEnchantingRecipe(stats, maxStats);
      }

      public void toNetwork(FriendlyByteBuf buf, PotionEnchantingRecipe recipe) {
         recipe.requirements.write(buf);
         buf.writeBoolean(recipe.maxRequirements != EnchantingRecipe.NO_MAX);
         if (recipe.maxRequirements != EnchantingRecipe.NO_MAX) {
            recipe.maxRequirements.write(buf);
         }
      }
   }
}
