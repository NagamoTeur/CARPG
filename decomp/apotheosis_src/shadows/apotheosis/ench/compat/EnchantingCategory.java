package shadows.apotheosis.ench.compat;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
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
import net.minecraft.client.gui.screens.inventory.EnchantmentNames;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.ench.enchantments.InertEnchantment;
import shadows.apotheosis.ench.table.EnchantingRecipe;
import shadows.apotheosis.ench.table.EnchantingStatManager;

public class EnchantingCategory implements IRecipeCategory<EnchantingRecipe> {
   public static final ResourceLocation UID = new ResourceLocation("apotheosis", "enchanting");
   public static final RecipeType<EnchantingRecipe> TYPE = RecipeType.create("apotheosis", "enchanting", EnchantingRecipe.class);
   public static final ResourceLocation TEXTURES = new ResourceLocation("apotheosis", "textures/gui/enchanting_jei.png");
   private static final Map<Class<?>, EnchantingCategory.Extension<?>> EXTENSIONS = new HashMap<>();
   private final IDrawable background;
   private final IDrawable icon;
   private final Component localizedName;

   public EnchantingCategory(IGuiHelper guiHelper) {
      this.background = guiHelper.createDrawable(TEXTURES, 0, 0, 170, 56);
      this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Blocks.f_50201_));
      this.localizedName = Component.m_237115_("apotheosis.recipes.enchanting");
   }

   public IDrawable getBackground() {
      return this.background;
   }

   public IDrawable getIcon() {
      return this.icon;
   }

   public Component getTitle() {
      return this.localizedName;
   }

   public RecipeType<EnchantingRecipe> getRecipeType() {
      return TYPE;
   }

   public void setRecipe(IRecipeLayoutBuilder builder, EnchantingRecipe recipe, IFocusGroup focuses) {
      IRecipeSlotBuilder input = builder.addSlot(RecipeIngredientRole.INPUT, 6, 6);
      IRecipeSlotBuilder output = builder.addSlot(RecipeIngredientRole.OUTPUT, 37, 6);
      EnchantingCategory.Extension<?> ext = EXTENSIONS.get(recipe.getClass());
      if (ext != null) {
         ext.setRecipe(builder, input, output, recipe, focuses);
      } else {
         input.addIngredients(VanillaTypes.ITEM_STACK, Arrays.asList(recipe.getInput().m_43908_()));
         output.addIngredient(VanillaTypes.ITEM_STACK, recipe.m_8043_());
      }
   }

   public void draw(EnchantingRecipe recipe, IRecipeSlotsView slots, PoseStack stack, double mouseX, double mouseY) {
      boolean hover = false;
      if (mouseX > 57.0 && mouseX <= 165.0 && mouseY > 4.0 && mouseY <= 23.0) {
         GuiComponent.m_93143_(stack, 57, 4, 0, 0.0F, 71.0F, 108, 19, 256, 256);
         hover = true;
      }

      Font font = Minecraft.m_91087_().f_91062_;
      EnchantingStatManager.Stats stats = recipe.getRequirements();
      EnchantingStatManager.Stats maxStats = recipe.getMaxRequirements();
      font.m_92883_(stack, I18n.m_118938_("gui.apotheosis.enchant.eterna", new Object[0]), 16.0F, 26.0F, 4044093);
      font.m_92883_(stack, I18n.m_118938_("gui.apotheosis.enchant.quanta", new Object[0]), 16.0F, 36.0F, 16536660);
      font.m_92883_(stack, I18n.m_118938_("gui.apotheosis.enchant.arcana", new Object[0]), 16.0F, 46.0F, 11010216);
      int level = (int)(stats.eterna() * 2.0F);
      String s = level + "";
      int width = 86 - font.m_92895_(s);
      EnchantmentNames.m_98734_().m_98735_((long)recipe.m_6423_().hashCode());
      FormattedText itextproperties = EnchantmentNames.m_98734_().m_98737_(font, width);
      int color = hover ? 16777088 : 6839882;
      drawWordWrap(font, itextproperties, 77, 6, width, color, stack);
      color = 8453920;
      font.m_92750_(stack, s, (float)(77 + width), 13.0F, color);
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, TEXTURES);
      int[] pos = new int[]{
         (int)(stats.eterna() / EnchantingStatManager.getAbsoluteMaxEterna() * 110.0F),
         (int)(stats.quanta() / 100.0F * 110.0F),
         (int)(stats.arcana() / 100.0F * 110.0F)
      };
      if (stats.eterna() > 0.0F) {
         GuiComponent.m_93133_(stack, 56, 27, 0.0F, 56.0F, pos[0], 5, 256, 256);
      }

      if (stats.quanta() > 0.0F) {
         GuiComponent.m_93133_(stack, 56, 37, 0.0F, 61.0F, pos[1], 5, 256, 256);
      }

      if (stats.arcana() > 0.0F) {
         GuiComponent.m_93133_(stack, 56, 47, 0.0F, 66.0F, pos[2], 5, 256, 256);
      }

      RenderSystem.m_69478_();
      if (maxStats.eterna() > 0.0F) {
         GuiComponent.m_93133_(
            stack,
            56 + pos[0],
            27,
            (float)pos[0],
            90.0F,
            (int)((maxStats.eterna() - stats.eterna()) / EnchantingStatManager.getAbsoluteMaxEterna() * 110.0F),
            5,
            256,
            256
         );
      }

      if (maxStats.quanta() > 0.0F) {
         GuiComponent.m_93133_(stack, 56 + pos[1], 37, (float)pos[1], 95.0F, (int)((maxStats.quanta() - stats.quanta()) / 100.0F * 110.0F), 5, 256, 256);
      }

      if (maxStats.arcana() > 0.0F) {
         GuiComponent.m_93133_(stack, 56 + pos[2], 47, (float)pos[2], 100.0F, (int)((maxStats.arcana() - stats.arcana()) / 100.0F * 110.0F), 5, 256, 256);
      }

      RenderSystem.m_69461_();
      Screen scn = Minecraft.m_91087_().f_91080_;
      if (scn != null) {
         if (hover) {
            List<Component> list = new ArrayList<>();
            list.add(
               Component.m_237110_("container.enchant.clue", new Object[]{((InertEnchantment)Apoth.Enchantments.INFUSION.get()).m_44700_(1).getString()})
                  .m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC})
            );
            scn.m_96597_(stack, list, (int)mouseX, (int)mouseY);
         } else if (mouseX > 56.0 && mouseX <= 166.0 && mouseY > 26.0 && mouseY <= 32.0) {
            List<Component> list = new ArrayList<>();
            list.add(Component.m_237115_("gui.apotheosis.enchant.eterna").m_130940_(ChatFormatting.GREEN));
            if (maxStats.eterna() == stats.eterna()) {
               list.add(
                  Component.m_237110_("info.apotheosis.eterna_exact", new Object[]{stats.eterna(), EnchantingStatManager.getAbsoluteMaxEterna()})
                     .m_130940_(ChatFormatting.GRAY)
               );
            } else {
               list.add(
                  Component.m_237110_("info.apotheosis.eterna_at_least", new Object[]{stats.eterna(), EnchantingStatManager.getAbsoluteMaxEterna()})
                     .m_130940_(ChatFormatting.GRAY)
               );
               if (maxStats.eterna() > -1.0F) {
                  list.add(
                     Component.m_237110_("info.apotheosis.eterna_at_most", new Object[]{maxStats.eterna(), EnchantingStatManager.getAbsoluteMaxEterna()})
                        .m_130940_(ChatFormatting.GRAY)
                  );
               }
            }

            scn.m_96597_(stack, list, (int)mouseX, (int)mouseY);
         } else if (mouseX > 56.0 && mouseX <= 166.0 && mouseY > 36.0 && mouseY <= 42.0) {
            List<Component> list = new ArrayList<>();
            list.add(Component.m_237115_("gui.apotheosis.enchant.quanta").m_130940_(ChatFormatting.RED));
            if (maxStats.quanta() == stats.quanta()) {
               list.add(Component.m_237110_("info.apotheosis.percent_exact", new Object[]{stats.quanta()}).m_130940_(ChatFormatting.GRAY));
            } else {
               list.add(Component.m_237110_("info.apotheosis.percent_at_least", new Object[]{stats.quanta()}).m_130940_(ChatFormatting.GRAY));
               if (maxStats.quanta() > -1.0F) {
                  list.add(Component.m_237110_("info.apotheosis.percent_at_most", new Object[]{maxStats.quanta()}).m_130940_(ChatFormatting.GRAY));
               }
            }

            scn.m_96597_(stack, list, (int)mouseX, (int)mouseY);
         } else if (mouseX > 56.0 && mouseX <= 166.0 && mouseY > 46.0 && mouseY <= 52.0) {
            List<Component> list = new ArrayList<>();
            list.add(Component.m_237115_("gui.apotheosis.enchant.arcana").m_130940_(ChatFormatting.DARK_PURPLE));
            if (maxStats.arcana() == stats.arcana()) {
               list.add(Component.m_237110_("info.apotheosis.percent_exact", new Object[]{stats.arcana()}).m_130940_(ChatFormatting.GRAY));
            } else {
               list.add(Component.m_237110_("info.apotheosis.percent_at_least", new Object[]{stats.arcana()}).m_130940_(ChatFormatting.GRAY));
               if (maxStats.arcana() > -1.0F) {
                  list.add(Component.m_237110_("info.apotheosis.percent_at_most", new Object[]{maxStats.arcana()}).m_130940_(ChatFormatting.GRAY));
               }
            }

            scn.m_96597_(stack, list, (int)mouseX, (int)mouseY);
         }
      }
   }

   public static void drawWordWrap(Font font, FormattedText pText, int pX, int pY, int pMaxWidth, int pColor, PoseStack stack) {
      for (FormattedCharSequence formattedcharsequence : font.m_92923_(pText, pMaxWidth)) {
         font.m_92877_(stack, formattedcharsequence, (float)pX, (float)pY, pColor);
         pY += 9;
      }
   }

   public static <T extends EnchantingRecipe> void registerExtension(Class<T> cls, EnchantingCategory.Extension<T> ext) {
      EXTENSIONS.put(cls, ext);
   }

   public interface Extension<T extends EnchantingRecipe> {
      void setRecipe(IRecipeLayoutBuilder var1, IRecipeSlotBuilder var2, IRecipeSlotBuilder var3, EnchantingRecipe var4, IFocusGroup var5);
   }
}
