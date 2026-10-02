package shadows.apotheosis.util;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.stream.Stream;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.AbstractIngredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.adventure.affix.socket.gem.Gem;
import shadows.apotheosis.adventure.affix.socket.gem.GemItem;
import shadows.apotheosis.adventure.affix.socket.gem.GemManager;
import shadows.apotheosis.adventure.loot.LootRarity;

public class GemIngredient extends AbstractIngredient {
   protected final LootRarity rarity;
   protected ItemStack[] items;

   public GemIngredient(LootRarity rarity) {
      super(Stream.empty());
      this.rarity = rarity;
   }

   public boolean test(ItemStack stack) {
      LootRarity rarity = GemItem.getLootRarity(stack);
      return stack.m_41720_() == Apoth.Items.GEM.get() && rarity != null && rarity == this.rarity;
   }

   public ItemStack[] m_43908_() {
      if (this.items == null) {
         Collection<Gem> gems = GemManager.INSTANCE.getValues();
         if (gems.size() == 0) {
            return new ItemStack[0];
         }

         this.items = new ItemStack[gems.size()];
         int i = 0;

         for (Gem g : GemManager.INSTANCE.getValues()) {
            this.items[i++] = GemManager.createGemStack(g, this.rarity);
         }
      }

      return this.items;
   }

   protected void invalidate() {
      super.invalidate();
      this.items = null;
   }

   public boolean isSimple() {
      return false;
   }

   public IIngredientSerializer<? extends Ingredient> getSerializer() {
      return GemIngredient.Serializer.INSTANCE;
   }

   public JsonElement m_43942_() {
      return new JsonObject();
   }

   public LootRarity getRarity() {
      return this.rarity;
   }

   public static class Serializer implements IIngredientSerializer<GemIngredient> {
      public static final GemIngredient.Serializer INSTANCE = new GemIngredient.Serializer();

      public GemIngredient parse(FriendlyByteBuf buffer) {
         LootRarity rarity = LootRarity.byId(buffer.m_130277_());
         return new GemIngredient(rarity);
      }

      public GemIngredient parse(JsonObject json) {
         LootRarity rarity = LootRarity.byId(GsonHelper.m_13906_(json, "rarity"));
         Preconditions.checkNotNull(rarity);
         return new GemIngredient(rarity);
      }

      public void write(FriendlyByteBuf buffer, GemIngredient ingredient) {
         buffer.m_130070_(ingredient.rarity.id());
      }
   }
}
