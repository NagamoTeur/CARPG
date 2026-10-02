package shadows.apotheosis.spawn.compat;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import shadows.apotheosis.spawn.modifiers.SpawnerModifier;
import shadows.apotheosis.spawn.modifiers.StatModifier;

public class SpawnerCategory implements IRecipeCategory<SpawnerModifier> {
   public static final ResourceLocation TEXTURES = new ResourceLocation("apotheosis", "textures/gui/spawner_jei.png");
   public static final ResourceLocation UID = new ResourceLocation("apotheosis", "spawner_modifiers");
   public static final RecipeType<SpawnerModifier> TYPE = RecipeType.create("apotheosis", "spawner_modifiers", SpawnerModifier.class);
   private IDrawable bg;
   private IDrawable icon;
   private Component title;

   public SpawnerCategory(IGuiHelper helper) {
      this.bg = helper.drawableBuilder(TEXTURES, 0, 0, 169, 75).build();
      this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.f_42007_));
      this.title = Component.m_237115_("title.apotheosis.spawner");
   }

   public RecipeType<SpawnerModifier> getRecipeType() {
      return TYPE;
   }

   public Component getTitle() {
      return this.title;
   }

   public IDrawable getBackground() {
      return this.bg;
   }

   public void setRecipe(IRecipeLayoutBuilder builder, SpawnerModifier recipe, IFocusGroup focuses) {
      builder.addSlot(RecipeIngredientRole.INPUT, 11, 11).addIngredients(recipe.getMainhandInput());
      if (recipe.getOffhandInput() != Ingredient.f_43901_) {
         builder.addSlot(RecipeIngredientRole.INPUT, 11, 48).addIngredients(recipe.getOffhandInput());
      }

      builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Blocks.f_50085_));
      builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Blocks.f_50085_));
   }

   public IDrawable getIcon() {
      return this.icon;
   }

   public void draw(SpawnerModifier recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
      if (recipe.getOffhandInput() == Ingredient.f_43901_) {
         GuiComponent.m_93143_(stack, 1, 31, 0, 0.0F, 88.0F, 28, 34, 256, 256);
      }

      Screen scn = Minecraft.m_91087_().f_91080_;
      if (scn != null) {
         if (mouseX >= -1.0 && mouseX < 9.0 && mouseY >= 13.0 && mouseY < 25.0) {
            GuiComponent.m_93143_(stack, -1, 13, 0, 0.0F, 75.0F, 10, 12, 256, 256);
            scn.m_96597_(stack, Arrays.asList(Component.m_237115_("misc.apotheosis.mainhand")), (int)mouseX, (int)mouseY);
         } else if (mouseX >= -1.0 && mouseX < 9.0 && mouseY >= 50.0 && mouseY < 62.0 && recipe.getOffhandInput() != Ingredient.f_43901_) {
            GuiComponent.m_93143_(stack, -1, 50, 0, 0.0F, 75.0F, 10, 12, 256, 256);
            scn.m_96597_(
               stack,
               Arrays.asList(Component.m_237115_("misc.apotheosis.offhand"), Component.m_237115_("misc.apotheosis.not_consumed").m_130940_(ChatFormatting.GRAY)),
               (int)mouseX,
               (int)mouseY
            );
         } else if (mouseX >= 33.0 && mouseX < 49.0 && mouseY >= 30.0 && mouseY < 46.0) {
            scn.m_96597_(stack, Arrays.asList(Component.m_237115_("misc.apotheosis.rclick_spawner")), (int)mouseX, (int)mouseY);
         }

         PoseStack mvStack = RenderSystem.m_157191_();
         mvStack.m_85836_();
         Matrix4f mvMatrix = mvStack.m_85850_().m_85861_();
         mvMatrix.m_27624_();
         mvMatrix.m_27644_(stack.m_85850_().m_85861_());
         mvStack.m_85837_(0.0, 0.5, -2000.0);
         Minecraft.m_91087_().m_91291_().m_115203_(new ItemStack(Items.f_42007_), 31, 29);
         mvStack.m_85849_();
         RenderSystem.m_157182_();
         Font font = Minecraft.m_91087_().f_91062_;
         int top = 37 - recipe.getStatModifiers().size() * (9 + 2) / 2 + 2;
         int left = 168;

         for (StatModifier<?> s : recipe.getStatModifiers()) {
            String value = s.value.toString();
            if ("true".equals(value)) {
               value = "+";
            } else if ("false".equals(value)) {
               value = "-";
            } else if (s.value instanceof Number num && num.intValue() > 0) {
               value = "+" + value;
            }

            Component msg = Component.m_237110_("misc.apotheosis.concat", new Object[]{value, s.stat.name()});
            int width = font.m_92852_(msg);
            boolean hover = mouseX >= (double)(left - width) && mouseX < (double)left && mouseY >= (double)top && mouseY < (double)(top + 9 + 1);
            font.m_92889_(stack, msg, (float)(left - font.m_92852_(msg)), (float)top, hover ? 8421631 : 3355443);
            int maxWidth = Minecraft.m_91087_().m_91268_().m_85445_();
            maxWidth = maxWidth - (maxWidth - 210) / 2 - 210;
            if (hover) {
               List<Component> list = new ArrayList<>();
               list.add(s.stat.name().m_130944_(new ChatFormatting[]{ChatFormatting.GREEN, ChatFormatting.UNDERLINE}));
               list.add(s.stat.desc().m_130940_(ChatFormatting.GRAY));
               if (s.value instanceof Number) {
                  if (((Number)s.min).intValue() > 0 || ((Number)s.max).intValue() != Integer.MAX_VALUE) {
                     list.add(Component.m_237113_(" "));
                  }

                  if (((Number)s.min).intValue() > 0) {
                     list.add(Component.m_237110_("misc.apotheosis.min_value", new Object[]{s.min}).m_130940_(ChatFormatting.GRAY));
                  }

                  if (((Number)s.max).intValue() != Integer.MAX_VALUE) {
                     list.add(Component.m_237110_("misc.apotheosis.max_value", new Object[]{s.max}).m_130940_(ChatFormatting.GRAY));
                  }
               }

               renderComponentTooltip(scn, stack, list, left + 6, (int)mouseY, maxWidth, font);
            }

            top += 9 + 2;
         }
      }
   }

   private static void renderComponentTooltip(Screen scn, PoseStack stack, List<Component> list, int x, int y, int maxWidth, Font font) {
      List<FormattedText> text = list.stream().map(c -> font.m_92865_().m_92414_(c, maxWidth, c.m_7383_())).flatMap(Collection::stream).toList();
      scn.renderComponentTooltip(stack, text, x, y, font);
   }
}
