package shadows.apotheosis.adventure.compat;

import java.util.Arrays;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.adventure.affix.socket.gem.Gem;
import shadows.apotheosis.adventure.affix.socket.gem.GemManager;
import shadows.apotheosis.adventure.affix.socket.gem.cutting.GemCuttingBlock;
import shadows.apotheosis.adventure.affix.socket.gem.cutting.GemCuttingMenu;
import shadows.apotheosis.adventure.loot.LootRarity;

public class GemCuttingCategory implements IRecipeCategory<GemCuttingCategory.GemCuttingRecipe> {
   public static final ResourceLocation TEXTURES = new ResourceLocation("apotheosis", "textures/gui/gem_cutting_jei.png");
   private final IDrawable background;
   private final IDrawable icon;

   public GemCuttingCategory(IGuiHelper guiHelper) {
      this.background = guiHelper.drawableBuilder(TEXTURES, 0, 0, 148, 78).addPadding(0, 0, 0, 0).build();
      this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack((ItemLike)Apoth.Blocks.GEM_CUTTING_TABLE.get()));
   }

   public RecipeType<GemCuttingCategory.GemCuttingRecipe> getRecipeType() {
      return AdventureJEIPlugin.GEM_CUTTING;
   }

   public Component getTitle() {
      return GemCuttingBlock.NAME;
   }

   public IDrawable getBackground() {
      return this.background;
   }

   public IDrawable getIcon() {
      return this.icon;
   }

   public void setRecipe(IRecipeLayoutBuilder builder, GemCuttingCategory.GemCuttingRecipe recipe, IFocusGroup focuses) {
      builder.addSlot(RecipeIngredientRole.INPUT, 46, 14).addIngredient(VanillaTypes.ITEM_STACK, recipe.gem);
      builder.addSlot(RecipeIngredientRole.INPUT, 5, 14).addIngredient(VanillaTypes.ITEM_STACK, recipe.dust);
      builder.addSlot(RecipeIngredientRole.INPUT, 46, 57).addIngredient(VanillaTypes.ITEM_STACK, recipe.gem);
      builder.addSlot(RecipeIngredientRole.INPUT, 87, 14).addIngredients(VanillaTypes.ITEM_STACK, Arrays.asList(recipe.materials));
      builder.addSlot(RecipeIngredientRole.OUTPUT, 129, 14).addIngredient(VanillaTypes.ITEM_STACK, recipe.out);
   }

   public static class GemCuttingRecipe {
      protected final ItemStack out;
      protected final ItemStack gem;
      protected final ItemStack dust;
      protected final ItemStack[] materials;

      public GemCuttingRecipe(Gem gem, LootRarity rarity) {
         this.out = GemManager.createGemStack(gem, rarity.next());
         this.gem = GemManager.createGemStack(gem, rarity);
         this.dust = new ItemStack((ItemLike)Apoth.Items.GEM_DUST.get(), GemCuttingMenu.getDustCost(rarity));
         LootRarity min = LootRarity.COMMON;
         if (rarity == min) {
            this.materials = new ItemStack[2];
            this.materials[0] = new ItemStack(rarity.getMaterial().m_41720_(), 3);
            this.materials[1] = new ItemStack(rarity.next().getMaterial().m_41720_(), 1);
         } else if (rarity.next() == LootRarity.ANCIENT) {
            this.materials = new ItemStack[2];
            this.materials[0] = new ItemStack(rarity.prev().getMaterial().m_41720_(), 9);
            this.materials[1] = new ItemStack(rarity.getMaterial().m_41720_(), 3);
         } else {
            this.materials = new ItemStack[3];
            this.materials[0] = new ItemStack(rarity.prev().getMaterial().m_41720_(), 9);
            this.materials[1] = new ItemStack(rarity.getMaterial().m_41720_(), 3);
            this.materials[2] = new ItemStack(rarity.next().getMaterial().m_41720_(), 1);
         }
      }
   }
}
