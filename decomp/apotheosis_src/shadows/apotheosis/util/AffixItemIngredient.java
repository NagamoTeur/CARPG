package shadows.apotheosis.util;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraftforge.common.crafting.AbstractIngredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.affix.AffixInstance;
import shadows.apotheosis.adventure.loot.LootController;
import shadows.apotheosis.adventure.loot.LootRarity;

public class AffixItemIngredient extends AbstractIngredient {
   protected final LootRarity rarity;
   protected ItemStack[] items;

   public AffixItemIngredient(LootRarity rarity) {
      this.rarity = rarity;
   }

   public boolean test(ItemStack stack) {
      LootRarity rarity = AffixHelper.getRarity(stack);
      Map<Affix, AffixInstance> affixes = AffixHelper.getAffixes(stack);
      return affixes.size() > 0 && rarity != null && rarity == this.rarity;
   }

   public ItemStack[] m_43908_() {
      if (this.items == null) {
         this.items = createFakeDisplayItems(this.rarity).toArray(new ItemStack[0]);
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
      return AffixItemIngredient.Serializer.INSTANCE;
   }

   public JsonElement m_43942_() {
      return new JsonObject();
   }

   public LootRarity getRarity() {
      return this.rarity;
   }

   private static List<ItemStack> createFakeDisplayItems(LootRarity rarity) {
      RandomSource src = new LegacyRandomSource(0L);
      List<ItemStack> out = Arrays.asList(Items.f_42388_, Items.f_42390_, Items.f_42472_, Items.f_42473_, Items.f_42474_, Items.f_42475_)
         .stream()
         .<ItemStack>map(ItemStack::new)
         .toList();
      out.forEach(
         stack -> {
            LootController.createLootItem(stack, rarity, src);
            AffixHelper.setName(
               stack,
               Component.m_237110_("text.apotheosis.any_x_item", new Object[]{rarity.toComponent(), ""}).m_130948_(Style.f_131099_.m_131148_(rarity.color()))
            );
         }
      );
      return out;
   }

   public static class Serializer implements IIngredientSerializer<AffixItemIngredient> {
      public static final AffixItemIngredient.Serializer INSTANCE = new AffixItemIngredient.Serializer();

      public AffixItemIngredient parse(FriendlyByteBuf buffer) {
         LootRarity rarity = LootRarity.byId(buffer.m_130277_());
         return new AffixItemIngredient(rarity);
      }

      public AffixItemIngredient parse(JsonObject json) {
         LootRarity rarity = LootRarity.byId(GsonHelper.m_13906_(json, "rarity"));
         Preconditions.checkNotNull(rarity);
         return new AffixItemIngredient(rarity);
      }

      public void write(FriendlyByteBuf buffer, AffixItemIngredient ingredient) {
         buffer.m_130070_(ingredient.rarity.id());
      }
   }
}
