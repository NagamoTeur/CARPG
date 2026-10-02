package daripher.skilltree.skill.bonus.player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.init.PSTSkillBonuses;
import daripher.skilltree.skill.bonus.SkillBonus;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public final class RecipeUnlockBonus implements SkillBonus<RecipeUnlockBonus> {
   private ResourceLocation recipeId;

   public RecipeUnlockBonus(ResourceLocation recipeId) {
      this.recipeId = recipeId;
   }

   @Override
   public SkillBonus.Serializer getSerializer() {
      return (SkillBonus.Serializer)PSTSkillBonuses.RECIPE_UNLOCK.get();
   }

   public RecipeUnlockBonus copy() {
      return new RecipeUnlockBonus(this.recipeId);
   }

   public RecipeUnlockBonus multiply(double multiplier) {
      return this;
   }

   @Override
   public boolean canMerge(SkillBonus<?> other) {
      return false;
   }

   @Override
   public boolean sameBonus(SkillBonus<?> other) {
      return other instanceof RecipeUnlockBonus otherBonus ? otherBonus.recipeId.equals(this.recipeId) : false;
   }

   @Override
   public SkillBonus<RecipeUnlockBonus> merge(SkillBonus<?> other) {
      throw new UnsupportedOperationException();
   }

   @Override
   public MutableComponent getTooltip() {
      Component recipeDescription = Component.m_237115_(TooltipHelper.getRecipeDescriptionId(this.recipeId))
         .m_130948_(TooltipHelper.getItemBonusStyle(this.isPositive()));
      return Component.m_237110_(this.getDescriptionId(), new Object[]{recipeDescription}).m_130948_(TooltipHelper.getSkillBonusStyle(this.isPositive()));
   }

   @Override
   public void gatherInfo(Consumer<MutableComponent> consumer) {
      SkillBonus.super.gatherInfo(consumer);
      String recipeDescriptionId = TooltipHelper.getRecipeDescriptionId(this.recipeId);
      TooltipHelper.consumeTranslated(recipeDescriptionId + ".info", consumer);
   }

   @Override
   public boolean isPositive() {
      return true;
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, int row, Consumer<RecipeUnlockBonus> consumer) {
      editor.addLabel(0, 0, "Recipe ID", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addTextField(0, 0, 200, 14, this.recipeId.toString())
         .setSoftFilter(ResourceLocation::m_135830_)
         .m_94151_(value -> this.selectRecipeId(consumer, value));
      editor.increaseHeight(19);
   }

   private void selectRecipeId(Consumer<RecipeUnlockBonus> consumer, String value) {
      this.setRecipeId(new ResourceLocation(value));
      consumer.accept(this.copy());
   }

   public void setRecipeId(ResourceLocation recipeId) {
      this.recipeId = recipeId;
   }

   public ResourceLocation getRecipeId() {
      return this.recipeId;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (obj != null && obj.getClass() == this.getClass()) {
         RecipeUnlockBonus that = (RecipeUnlockBonus)obj;
         return Objects.equals(this.recipeId, that.recipeId);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.recipeId);
   }

   public static class Serializer implements SkillBonus.Serializer {
      public RecipeUnlockBonus deserialize(JsonObject json) throws JsonParseException {
         String recipeId = json.get("recipe_id").getAsString();
         return new RecipeUnlockBonus(new ResourceLocation(recipeId));
      }

      public void serialize(JsonObject json, SkillBonus<?> bonus) {
         if (bonus instanceof RecipeUnlockBonus commandBonus) {
            json.addProperty("recipe_id", commandBonus.recipeId.toString());
         } else {
            throw new IllegalArgumentException();
         }
      }

      public RecipeUnlockBonus deserialize(CompoundTag tag) {
         String recipeId = tag.m_128461_("recipe_id");
         return new RecipeUnlockBonus(new ResourceLocation(recipeId));
      }

      public CompoundTag serialize(SkillBonus<?> bonus) {
         if (bonus instanceof RecipeUnlockBonus commandBonus) {
            CompoundTag tag = new CompoundTag();
            tag.m_128359_("recipe_id", commandBonus.recipeId.toString());
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public RecipeUnlockBonus deserialize(FriendlyByteBuf buf) {
         return new RecipeUnlockBonus(new ResourceLocation(buf.m_130277_()));
      }

      public void serialize(FriendlyByteBuf buf, SkillBonus<?> bonus) {
         if (bonus instanceof RecipeUnlockBonus commandBonus) {
            buf.m_130070_(commandBonus.recipeId.toString());
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public SkillBonus<?> createDefaultInstance() {
         return new RecipeUnlockBonus(new ResourceLocation("skilltree:weapon_poisoning"));
      }
   }
}
