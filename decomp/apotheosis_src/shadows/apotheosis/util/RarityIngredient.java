package shadows.apotheosis.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.AbstractIngredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.loot.LootRarity;

public class RarityIngredient extends AbstractIngredient {
   protected final LootRarity rarity;

   public RarityIngredient(LootRarity rarity) {
      this.rarity = rarity;
   }

   public boolean test(ItemStack stack) {
      return AffixHelper.getRarity(stack) == this.rarity;
   }

   public boolean isSimple() {
      return false;
   }

   public IIngredientSerializer<? extends Ingredient> getSerializer() {
      return RarityIngredient.Serializer.INSTANCE;
   }

   public JsonElement m_43942_() {
      return new JsonObject();
   }

   public LootRarity getRarity() {
      return this.rarity;
   }

   public static class Serializer implements IIngredientSerializer<RarityIngredient> {
      public static final RarityIngredient.Serializer INSTANCE = new RarityIngredient.Serializer();

      public RarityIngredient parse(FriendlyByteBuf buffer) {
         LootRarity rarity = LootRarity.byId(buffer.m_130277_());
         return new RarityIngredient(rarity);
      }

      public RarityIngredient parse(JsonObject json) {
         LootRarity rarity = LootRarity.byId(GsonHelper.m_13906_(json, "rarity"));
         if (rarity == null) {
            throw new JsonParseException("Invalid Rarity");
         } else {
            return new RarityIngredient(rarity);
         }
      }

      public void write(FriendlyByteBuf buffer, RarityIngredient ingredient) {
         buffer.m_130070_(ingredient.rarity.id());
      }
   }
}
