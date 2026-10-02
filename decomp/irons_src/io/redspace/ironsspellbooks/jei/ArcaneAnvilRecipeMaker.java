package io.redspace.ironsspellbooks.jei;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.item.UpgradeOrbItem;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public final class ArcaneAnvilRecipeMaker {
   private ArcaneAnvilRecipeMaker() {
   }

   public static List<ArcaneAnvilRecipe> getRecipes(IVanillaRecipeFactory vanillaRecipeFactory, IIngredientManager ingredientManager) {
      List<Item> visibleItems = getVisibleItems();
      return Stream.of(getScrollRecipes(visibleItems), getImbueRecipes(visibleItems), getUpgradeRecipes(visibleItems), getAffinityAttuneRecipes(visibleItems))
         .flatMap(x -> (Stream<? extends ArcaneAnvilRecipe>)x)
         .toList();
   }

   private static Stream<ArcaneAnvilRecipe> getScrollRecipes(List<Item> visibleItems) {
      return SpellRegistry.getEnabledSpells()
         .stream()
         .sorted(Comparator.comparing(AbstractSpell::getSpellId))
         .flatMap(spell -> IntStream.rangeClosed(spell.getMinLevel(), spell.getMaxLevel() - 1).mapToObj(i -> new ArcaneAnvilRecipe(spell, i)));
   }

   private static Stream<ArcaneAnvilRecipe> getImbueRecipes(List<Item> visibleItems) {
      return visibleItems.stream()
         .filter(item -> Utils.canImbue(new ItemStack(item)))
         .map(item -> new ArcaneAnvilRecipe(new ItemStack(item), (AbstractSpell)null));
   }

   private static Stream<ArcaneAnvilRecipe> getUpgradeRecipes(List<Item> visibleItems) {
      List<Item> upgradable = visibleItems.stream().filter(item -> Utils.canBeUpgraded(new ItemStack(item))).toList();
      return ForgeRegistries.ITEMS
         .getValues()
         .stream()
         .filter(item -> item instanceof UpgradeOrbItem)
         .flatMap(upgradeOrb -> upgradable.stream().map(item -> new ArcaneAnvilRecipe(new ItemStack(item), List.of(new ItemStack(upgradeOrb)))));
   }

   private static Stream<ArcaneAnvilRecipe> getAffinityAttuneRecipes(List<Item> visibleItems) {
      return SpellRegistry.getEnabledSpells().stream().sorted(Comparator.comparing(AbstractSpell::getSpellId)).map(ArcaneAnvilRecipe::new);
   }

   public static List<Item> getVisibleItems() {
      return ForgeRegistries.ITEMS.getValues().stream().filter(item -> item.m_41471_() != null).toList();
   }

   private static ItemStack getScrollStack(ItemStack stack, AbstractSpell spell, int spellLevel) {
      ItemStack scrollStack = stack.m_41777_();
      ISpellContainer.createScrollContainer(spell, spellLevel, scrollStack);
      return scrollStack;
   }
}
