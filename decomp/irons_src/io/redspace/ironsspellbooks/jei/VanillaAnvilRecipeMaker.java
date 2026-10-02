package io.redspace.ironsspellbooks.jei;

import io.redspace.ironsspellbooks.registries.ItemRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import mezz.jei.api.recipe.vanilla.IJeiAnvilRecipe;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.registries.RegistryObject;

public class VanillaAnvilRecipeMaker {
   public static List<IJeiAnvilRecipe> getAnvilRepairRecipes(IVanillaRecipeFactory vanillaRecipeFactory) {
      return Stream.concat(getArmorRepairRecipes(vanillaRecipeFactory), getItemRepairRecipes(vanillaRecipeFactory)).toList();
   }

   public static Stream<IJeiAnvilRecipe> getItemRepairRecipes(IVanillaRecipeFactory vanillaRecipeFactory) {
      List<TieredItem> repairableItems = getTieredItems();
      return repairableItems.stream()
         .mapMulti(
            (item, consumer) -> {
               ItemStack damagedThreeQuarters = new ItemStack(item);
               damagedThreeQuarters.m_41721_(damagedThreeQuarters.m_41776_() * 3 / 4);
               ItemStack damagedHalf = new ItemStack(item);
               damagedHalf.m_41721_(damagedHalf.m_41776_() / 2);
               IJeiAnvilRecipe repairWithSame = vanillaRecipeFactory.createAnvilRecipe(
                  List.of(damagedThreeQuarters), List.of(damagedThreeQuarters), List.of(damagedHalf)
               );
               consumer.accept(repairWithSame);
               List<ItemStack> repairMaterials = Arrays.stream(item.m_43314_().m_6282_().m_43908_()).toList();
               ItemStack damagedFully = new ItemStack(item);
               damagedFully.m_41721_(damagedFully.m_41776_());
               IJeiAnvilRecipe repairWithMaterial = vanillaRecipeFactory.createAnvilRecipe(
                  List.of(damagedFully), repairMaterials, List.of(damagedThreeQuarters)
               );
               consumer.accept(repairWithMaterial);
            }
         );
   }

   public static Stream<IJeiAnvilRecipe> getArmorRepairRecipes(IVanillaRecipeFactory vanillaRecipeFactory) {
      List<ArmorItem> repairableItems = getArmorItems();
      return repairableItems.stream()
         .mapMulti(
            (item, consumer) -> {
               ItemStack damagedThreeQuarters = new ItemStack(item);
               damagedThreeQuarters.m_41721_(damagedThreeQuarters.m_41776_() * 3 / 4);
               ItemStack damagedHalf = new ItemStack(item);
               damagedHalf.m_41721_(damagedHalf.m_41776_() / 2);
               IJeiAnvilRecipe repairWithSame = vanillaRecipeFactory.createAnvilRecipe(
                  List.of(damagedThreeQuarters), List.of(damagedThreeQuarters), List.of(damagedHalf)
               );
               consumer.accept(repairWithSame);
               List<ItemStack> repairMaterials = Arrays.stream(item.m_40401_().m_6230_().m_43908_()).toList();
               ItemStack damagedFully = new ItemStack(item);
               damagedFully.m_41721_(damagedFully.m_41776_());
               IJeiAnvilRecipe repairWithMaterial = vanillaRecipeFactory.createAnvilRecipe(
                  List.of(damagedFully), repairMaterials, List.of(damagedThreeQuarters)
               );
               consumer.accept(repairWithMaterial);
            }
         );
   }

   public static List<TieredItem> getTieredItems() {
      Collection<RegistryObject<Item>> registryItems = ItemRegistry.getIronsItems();
      List<TieredItem> items = new ArrayList<>();

      for (RegistryObject<Item> item : registryItems) {
         Object var5 = item.get();
         if (var5 instanceof TieredItem) {
            TieredItem tieredItem = (TieredItem)var5;
            if (tieredItem.m_41471_() != null) {
               items.add(tieredItem);
            }
         }
      }

      return items;
   }

   public static List<ArmorItem> getArmorItems() {
      Collection<RegistryObject<Item>> registryItems = ItemRegistry.getIronsItems();
      List<ArmorItem> items = new ArrayList<>();

      for (RegistryObject<Item> item : registryItems) {
         Object var5 = item.get();
         if (var5 instanceof ArmorItem) {
            ArmorItem tieredItem = (ArmorItem)var5;
            if (tieredItem.m_41471_() != null) {
               items.add(tieredItem);
            }
         }
      }

      return items;
   }
}
