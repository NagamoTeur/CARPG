package net.sweenus.simplyswords.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.sweenus.simplyswords.SimplySwords;
import net.sweenus.simplyswords.config.SimplySwordsConfig;

public class GobberEndSwordItem extends SwordItem {
   String[] repairIngredient;
   static boolean unbreakable = SimplySwordsConfig.getBooleanValue("compat_gobber_end_weapons_unbreakable");

   public GobberEndSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, String... repairIngredient) {
      super(toolMaterial, attackDamage, attackSpeed, new Properties().m_41491_(SimplySwords.SIMPLYSWORDS));
      this.repairIngredient = repairIngredient;
   }

   public boolean m_6832_(ItemStack stack, ItemStack ingredient) {
      List<Item> potentialIngredients = new ArrayList<>(List.of());
      Arrays.stream(this.repairIngredient)
         .toList()
         .forEach(repIngredient -> potentialIngredients.add((Item)Registry.f_122827_.m_7745_(new ResourceLocation(repIngredient))));
      return potentialIngredients.contains(ingredient.m_41720_());
   }

   public void m_7836_(ItemStack stack, Level world, Player player) {
      if (!world.f_46443_) {
         if (unbreakable) {
            stack.m_41784_().m_128379_("Unbreakable", true);
         }
      }
   }
}
