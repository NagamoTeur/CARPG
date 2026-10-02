package shadows.apotheosis.potion.compat;

import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.ench.compat.EnchantingCategory;
import shadows.apotheosis.ench.table.EnchantingRecipe;
import shadows.apotheosis.potion.PotionCharmItem;
import shadows.apotheosis.potion.PotionCharmRecipe;
import shadows.apotheosis.potion.PotionEnchantingRecipe;

@JeiPlugin
public class PotionJEIPlugin implements IModPlugin {
   ICraftingGridHelper gridHelper;

   public void registerRecipes(IRecipeRegistration reg) {
      if (Apotheosis.enablePotion) {
         this.gridHelper = reg.getJeiHelpers().getGuiHelper().createCraftingGridHelper();
      }
   }

   public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration reg) {
      if (Apotheosis.enablePotion) {
         reg.getCraftingCategory().addCategoryExtension(PotionCharmRecipe.class, x$0 -> new PotionJEIPlugin.PotionCharmRecipeWrapper(x$0));
         EnchantingCategory.registerExtension(PotionEnchantingRecipe.class, new PotionJEIPlugin.PotionCharmEnchantingWrapper());
      }
   }

   public void registerItemSubtypes(ISubtypeRegistration reg) {
      if (Apotheosis.enablePotion) {
         reg.registerSubtypeInterpreter((Item)Apoth.Items.POTION_CHARM.get(), new PotionJEIPlugin.PotionCharmSubtypes());
      }
   }

   public ResourceLocation getPluginUid() {
      return new ResourceLocation("apotheosis", "potion");
   }

   private class PotionCharmEnchantingWrapper implements EnchantingCategory.Extension<PotionEnchantingRecipe> {
      @Override
      public void setRecipe(IRecipeLayoutBuilder builder, IRecipeSlotBuilder input, IRecipeSlotBuilder output, EnchantingRecipe recipe, IFocusGroup focuses) {
         Potion potion = PotionUtils.m_43579_(
            focuses.getFocuses(VanillaTypes.ITEM_STACK)
               .findFirst()
               .map(IFocus::getTypedValue)
               .<ItemStack>map(ITypedIngredient::getIngredient)
               .orElse(ItemStack.f_41583_)
         );
         if (potion != Potions.f_43598_) {
            ItemStack out = new ItemStack((ItemLike)Apoth.Items.POTION_CHARM.get());
            PotionUtils.m_43549_(out, potion);
            ItemStack in = out.m_41777_();
            out.m_41784_().m_128379_("Unbreakable", true);
            input.addIngredient(VanillaTypes.ITEM_STACK, in);
            output.addIngredient(VanillaTypes.ITEM_STACK, out);
         } else {
            List<ItemStack> potionStacks = new ArrayList<>();
            List<ItemStack> unbreakable = new ArrayList<>();

            for (Potion p : ForgeRegistries.POTIONS) {
               if (p.m_43488_().size() == 1 && !((MobEffectInstance)p.m_43488_().get(0)).m_19544_().m_8093_()) {
                  ItemStack charm = new ItemStack((ItemLike)Apoth.Items.POTION_CHARM.get());
                  PotionUtils.m_43549_(charm, p);
                  potionStacks.add(charm);
                  ItemStack copy = charm.m_41777_();
                  copy.m_41784_().m_128379_("Unbreakable", true);
                  unbreakable.add(copy);
               }
            }

            input.addIngredients(VanillaTypes.ITEM_STACK, potionStacks);
            output.addIngredients(VanillaTypes.ITEM_STACK, unbreakable);
         }

         builder.createFocusLink(new IIngredientAcceptor[]{input, output});
      }
   }

   private class PotionCharmRecipeWrapper implements ICraftingCategoryExtension {
      private final PotionCharmRecipe recipe;

      PotionCharmRecipeWrapper(PotionCharmRecipe recipe) {
         this.recipe = recipe;
      }

      public ResourceLocation getRegistryName() {
         return this.recipe.m_6423_();
      }

      public int getWidth() {
         return 3;
      }

      public int getHeight() {
         return 3;
      }

      public void setRecipe(IRecipeLayoutBuilder builder, ICraftingGridHelper craftingGridHelper, IFocusGroup focuses) {
         Potion potion = PotionUtils.m_43579_(
            focuses.getFocuses(VanillaTypes.ITEM_STACK)
               .findFirst()
               .map(IFocus::getTypedValue)
               .<ItemStack>map(ITypedIngredient::getIngredient)
               .orElse(ItemStack.f_41583_)
         );
         List<List<ItemStack>> recipeInputs = this.recipe
            .m_7527_()
            .stream()
            .map(i -> Arrays.asList(i.m_43908_()))
            .collect(Collectors.toCollection(ArrayList::new));
         if (potion != Potions.f_43598_) {
            IntListIterator output = this.recipe.getPotionSlots().iterator();

            while (output.hasNext()) {
               int i = (Integer)output.next();
               recipeInputs.set(i, Arrays.asList(PotionUtils.m_43549_(new ItemStack(Items.f_42589_), potion)));
            }
         }

         ItemStack output = new ItemStack((ItemLike)Apoth.Items.POTION_CHARM.get());
         PotionUtils.m_43549_(output, potion);
         craftingGridHelper.createAndSetInputs(builder, VanillaTypes.ITEM_STACK, recipeInputs, this.getWidth(), this.getHeight());
         if (potion != Potions.f_43598_) {
            craftingGridHelper.createAndSetOutputs(builder, VanillaTypes.ITEM_STACK, Arrays.asList(output));
         } else {
            List<ItemStack> potionStacks = new ArrayList<>();

            for (Potion p : ForgeRegistries.POTIONS) {
               if (p.m_43488_().size() == 1 && !((MobEffectInstance)p.m_43488_().get(0)).m_19544_().m_8093_()) {
                  ItemStack charm = new ItemStack((ItemLike)Apoth.Items.POTION_CHARM.get());
                  PotionUtils.m_43549_(charm, p);
                  potionStacks.add(charm);
               }
            }

            craftingGridHelper.createAndSetOutputs(builder, VanillaTypes.ITEM_STACK, potionStacks);
         }
      }
   }

   private static class PotionCharmSubtypes implements IIngredientSubtypeInterpreter<ItemStack> {
      public String apply(ItemStack stack, UidContext context) {
         if (context != UidContext.Recipe) {
            if (!PotionCharmItem.hasPotion(stack)) {
               return "";
            } else {
               Potion p = PotionUtils.m_43579_(stack);
               MobEffectInstance contained = (MobEffectInstance)p.m_43488_().get(0);
               return ForgeRegistries.MOB_EFFECTS.getKey(contained.m_19544_()) + "@" + contained.m_19564_() + "@" + contained.m_19557_();
            }
         } else {
            return "";
         }
      }
   }
}
