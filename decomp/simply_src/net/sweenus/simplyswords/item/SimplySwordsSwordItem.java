package net.sweenus.simplyswords.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.sweenus.simplyswords.SimplySwords;
import net.sweenus.simplyswords.util.HelperMethods;

public class SimplySwordsSwordItem extends SwordItem {
   String[] repairIngredient;

   public SimplySwordsSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, String... repairIngredient) {
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

   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (!attacker.f_19853_.m_5776_()) {
         HelperMethods.playHitSounds(attacker, target);
      }

      return super.m_7579_(stack, target, attacker);
   }
}
