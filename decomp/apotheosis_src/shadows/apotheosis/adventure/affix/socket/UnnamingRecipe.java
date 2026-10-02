package shadows.apotheosis.adventure.affix.socket;

import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.loot.LootRarity;

public class UnnamingRecipe extends AdventureModule.ApothUpgradeRecipe {
   private static final ResourceLocation ID = new ResourceLocation("apotheosis:unnaming");

   public UnnamingRecipe() {
      super(ID, Ingredient.f_43901_, Ingredient.m_43929_(new ItemLike[]{(ItemLike)Apoth.Items.VIAL_OF_UNNAMING.get()}), ItemStack.f_41583_);
   }

   public boolean m_5818_(Container pInv, Level pLevel) {
      if (pInv.m_8020_(0).m_41619_()) {
         return false;
      } else {
         CompoundTag afxData = pInv.m_8020_(0).m_41737_("affix_data");
         boolean hasName = afxData != null && afxData.m_128425_("name", 8);
         return hasName && pInv.m_8020_(1).m_41720_() == Apoth.Items.VIAL_OF_UNNAMING.get();
      }
   }

   public ItemStack m_5874_(Container pInv) {
      ItemStack out = pInv.m_8020_(0).m_41777_();
      CompoundTag afxData = out.m_41737_("affix_data");
      LootRarity rarity = AffixHelper.getRarity(afxData);
      if (afxData != null && rarity != null) {
         Component comp = Component.m_237110_("%2$s", new Object[]{"", ""}).m_130948_(Style.f_131099_.m_131148_(rarity.color()));
         AffixHelper.setName(out, comp);
         return out;
      } else {
         return ItemStack.f_41583_;
      }
   }

   public RecipeSerializer<?> m_7707_() {
      return UnnamingRecipe.Serializer.INSTANCE;
   }

   public RecipeType<?> m_6671_() {
      return RecipeType.f_44113_;
   }

   public boolean m_5598_() {
      return true;
   }

   public static class Serializer implements RecipeSerializer<UnnamingRecipe> {
      public static UnnamingRecipe.Serializer INSTANCE = new UnnamingRecipe.Serializer();

      public UnnamingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
         return new UnnamingRecipe();
      }

      public UnnamingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
         return new UnnamingRecipe();
      }

      public void toNetwork(FriendlyByteBuf pBuffer, UnnamingRecipe pRecipe) {
      }
   }
}
