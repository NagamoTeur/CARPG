package shadows.apotheosis.adventure.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.apotheosis.adventure.affix.salvaging.SalvagingRecipe;
import shadows.apotheosis.adventure.affix.socket.AddSocketsRecipe;
import shadows.apotheosis.adventure.affix.socket.SocketHelper;
import shadows.apotheosis.adventure.affix.socket.gem.Gem;
import shadows.apotheosis.adventure.affix.socket.gem.GemItem;
import shadows.apotheosis.adventure.affix.socket.gem.GemManager;
import shadows.apotheosis.adventure.loot.LootRarity;

@JeiPlugin
public class AdventureJEIPlugin implements IModPlugin {
   public static final RecipeType<UpgradeRecipe> APO_SMITHING = RecipeType.create("apotheosis", "smithing", AdventureModule.ApothUpgradeRecipe.class);
   public static final RecipeType<SalvagingRecipe> SALVAGING = RecipeType.create("apotheosis", "salvaging", SalvagingRecipe.class);
   public static final RecipeType<GemCuttingCategory.GemCuttingRecipe> GEM_CUTTING = RecipeType.create(
      "apotheosis", "gem_cutting", GemCuttingCategory.GemCuttingRecipe.class
   );
   private static final List<ItemStack> DUMMY_INPUTS = Arrays.asList(Items.f_42430_, Items.f_42390_, Items.f_42428_, Items.f_42469_, Items.f_42713_)
      .stream()
      .<ItemStack>map(ItemStack::new)
      .toList();

   public ResourceLocation getPluginUid() {
      return new ResourceLocation("apotheosis", "adventure_module");
   }

   public void registerRecipes(IRecipeRegistration reg) {
      if (Apotheosis.enableAdventure) {
         ItemStack gem = new ItemStack((ItemLike)Apoth.Items.GEM.get());
         Gem gemObj = (Gem)GemManager.INSTANCE.getRandomItem(new LegacyRandomSource(1854L));
         GemItem.setGem(gem, gemObj);
         GemItem.setLootRarity(gem, gemObj.getMaxRarity());
         reg.addIngredientInfo(gem, VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("info.apotheosis.socketing")});
         reg.addIngredientInfo(
            new ItemStack((ItemLike)Apoth.Items.GEM_DUST.get()), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("info.apotheosis.gem_crushing")}
         );
         reg.addIngredientInfo(
            new ItemStack((ItemLike)Apoth.Items.VIAL_OF_EXTRACTION.get()),
            VanillaTypes.ITEM_STACK,
            new Component[]{Component.m_237115_("info.apotheosis.gem_extraction")}
         );
         reg.addIngredientInfo(
            new ItemStack((ItemLike)Apoth.Items.VIAL_OF_EXPULSION.get()),
            VanillaTypes.ITEM_STACK,
            new Component[]{Component.m_237115_("info.apotheosis.gem_expulsion")}
         );
         reg.addIngredientInfo(
            new ItemStack((ItemLike)Apoth.Items.VIAL_OF_UNNAMING.get()),
            VanillaTypes.ITEM_STACK,
            new Component[]{Component.m_237115_("info.apotheosis.unnaming")}
         );
         ApothSmithingCategory.registerExtension(AddSocketsRecipe.class, new AdventureJEIPlugin.AddSocketsExtension());
         reg.addRecipes(
            APO_SMITHING,
            Minecraft.m_91087_()
               .f_91073_
               .m_7465_()
               .m_44013_(net.minecraft.world.item.crafting.RecipeType.f_44113_)
               .stream()
               .filter(rx -> rx instanceof AdventureModule.ApothUpgradeRecipe)
               .toList()
         );
         List<SalvagingRecipe> salvagingRecipes = new ArrayList<>(Minecraft.m_91087_().f_91073_.m_7465_().m_44013_(Apoth.RecipeTypes.SALVAGING));
         salvagingRecipes.sort(Comparator.comparingInt(recipe -> recipe.getOutputs().stream().mapToInt(SalvagingRecipe.OutputData::getMax).max().orElse(0)));
         reg.addRecipes(SALVAGING, salvagingRecipes);
         List<GemCuttingCategory.GemCuttingRecipe> gemCutRecipes = new ArrayList<>();

         for (Gem g : GemManager.INSTANCE.getValues()) {
            LootRarity r = LootRarity.COMMON;

            for (LootRarity max = LootRarity.ANCIENT; r != max; r = r.next()) {
               if (g.clamp(r) == r) {
                  gemCutRecipes.add(new GemCuttingCategory.GemCuttingRecipe(g, r));
               }
            }
         }

         reg.addRecipes(GEM_CUTTING, gemCutRecipes);
      }
   }

   public void registerCategories(IRecipeCategoryRegistration reg) {
      if (Apotheosis.enableAdventure) {
         reg.addRecipeCategories(new IRecipeCategory[]{new ApothSmithingCategory(reg.getJeiHelpers().getGuiHelper())});
         reg.addRecipeCategories(new IRecipeCategory[]{new SalvagingCategory(reg.getJeiHelpers().getGuiHelper())});
         reg.addRecipeCategories(new IRecipeCategory[]{new GemCuttingCategory(reg.getJeiHelpers().getGuiHelper())});
      }
   }

   public void registerRecipeCatalysts(IRecipeCatalystRegistration reg) {
      if (Apotheosis.enableAdventure) {
         reg.addRecipeCatalyst(new ItemStack(Blocks.f_50625_), new RecipeType[]{APO_SMITHING});
         reg.addRecipeCatalyst(new ItemStack((ItemLike)Apoth.Blocks.SALVAGING_TABLE.get()), new RecipeType[]{SALVAGING});
         reg.addRecipeCatalyst(new ItemStack((ItemLike)Apoth.Blocks.GEM_CUTTING_TABLE.get()), new RecipeType[]{GEM_CUTTING});
      }
   }

   public void registerItemSubtypes(ISubtypeRegistration reg) {
      if (Apotheosis.enableAdventure) {
         reg.registerSubtypeInterpreter((Item)Apoth.Items.GEM.get(), new AdventureJEIPlugin.GemSubtypes());
      }
   }

   static class AddSocketsExtension implements ApothSmithingCategory.Extension<AddSocketsRecipe> {
      private static final List<ItemStack> DUMMY_OUTPUTS = AdventureJEIPlugin.DUMMY_INPUTS.stream().<ItemStack>map(ItemStack::m_41777_).map(s -> {
         SocketHelper.setSockets(s, 1);
         return (ItemStack)s;
      }).toList();

      public void setRecipe(IRecipeLayoutBuilder builder, AddSocketsRecipe recipe, IFocusGroup focuses) {
         builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(VanillaTypes.ITEM_STACK, AdventureJEIPlugin.DUMMY_INPUTS);
         builder.addSlot(RecipeIngredientRole.INPUT, 50, 1).addIngredients(recipe.getInput());
         builder.addSlot(RecipeIngredientRole.OUTPUT, 108, 1).addItemStacks(DUMMY_OUTPUTS);
      }

      public void draw(AddSocketsRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
         Component text = Component.m_237110_("text.apotheosis.socket_limit", new Object[]{recipe.getMaxSockets()});
         Font font = Minecraft.m_91087_().f_91062_;
         font.m_92889_(stack, text, (float)(62 - font.m_92852_(text) / 2), 23.0F, 0);
      }
   }

   static class GemSubtypes implements IIngredientSubtypeInterpreter<ItemStack> {
      public String apply(ItemStack stack, UidContext context) {
         Gem gem = GemItem.getGem(stack);
         LootRarity rarity = GemItem.getLootRarity(stack);
         return gem == null ? ForgeRegistries.ITEMS.getKey(stack.m_41720_()).toString() : gem.getId() + "@" + rarity.id();
      }
   }
}
