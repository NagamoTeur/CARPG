package daripher.skilltree.recipe;

import daripher.itemproduction.block.entity.Interactive;
import daripher.skilltree.skill.bonus.SkillBonusHandler;
import daripher.skilltree.skill.bonus.player.RecipeUnlockBonus;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Recipe;

public interface SkillRequiringRecipe {
   default boolean isUncraftable(Container container, Recipe<?> recipe) {
      return container instanceof Interactive interactive ? this.isUncraftable(interactive, recipe) : true;
   }

   default boolean isUncraftable(Interactive container, Recipe<?> recipe) {
      Player player = container.getUser();
      return player == null
         ? true
         : SkillBonusHandler.getSkillBonuses(player, RecipeUnlockBonus.class).stream().noneMatch(bonus -> bonus.getRecipeId().equals(recipe.m_6423_()));
   }
}
