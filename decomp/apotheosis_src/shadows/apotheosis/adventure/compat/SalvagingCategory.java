package shadows.apotheosis.adventure.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Arrays;
import java.util.List;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.adventure.affix.salvaging.SalvagingRecipe;

public class SalvagingCategory implements IRecipeCategory<SalvagingRecipe> {
   public static final ResourceLocation TEXTURES = new ResourceLocation("apotheosis", "textures/gui/salvage_jei.png");
   private final Component title = Component.m_237115_("title.apotheosis.salvaging");
   private final IDrawable background;
   private final IDrawable icon;

   public SalvagingCategory(IGuiHelper guiHelper) {
      this.background = guiHelper.drawableBuilder(TEXTURES, 0, 0, 98, 74).addPadding(0, 0, 0, 0).build();
      this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack((ItemLike)Apoth.Blocks.SALVAGING_TABLE.get()));
   }

   public RecipeType<SalvagingRecipe> getRecipeType() {
      return AdventureJEIPlugin.SALVAGING;
   }

   public Component getTitle() {
      return this.title;
   }

   public IDrawable getBackground() {
      return this.background;
   }

   public IDrawable getIcon() {
      return this.icon;
   }

   public void draw(SalvagingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
      List<SalvagingRecipe.OutputData> outputs = recipe.getOutputs();
      Font font = Minecraft.m_91087_().f_91062_;
      int idx = 0;

      for (SalvagingRecipe.OutputData d : outputs) {
         stack.m_85836_();
         stack.m_85837_(0.0, 0.0, 200.0);
         String text = String.format("%d-%d", d.getMin(), d.getMax());
         float x = (float)(59 + 18 * (idx % 2)) + (16.0F - (float)font.m_92895_(text) * 0.5F);
         float y = 23.0F + (float)(18 * (idx / 2));
         float scale = 0.5F;
         stack.m_85841_(scale, scale, 1.0F);
         font.m_92750_(stack, text, x / scale, y / scale, 16777215);
         idx++;
         stack.m_85849_();
      }
   }

   public void setRecipe(IRecipeLayoutBuilder builder, SalvagingRecipe recipe, IFocusGroup focuses) {
      List<ItemStack> input = Arrays.asList(recipe.getInput().m_43908_());
      builder.addSlot(RecipeIngredientRole.INPUT, 5, 29).addIngredients(VanillaTypes.ITEM_STACK, input);
      List<SalvagingRecipe.OutputData> outputs = recipe.getOutputs();
      int idx = 0;

      for (SalvagingRecipe.OutputData d : outputs) {
         builder.addSlot(RecipeIngredientRole.OUTPUT, 59 + 18 * (idx % 2), 11 + 18 * (idx / 2)).addIngredient(VanillaTypes.ITEM_STACK, d.getStack());
         idx++;
      }
   }
}
