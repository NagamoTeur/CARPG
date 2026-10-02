package com.obscuria.aquamirae.common.recipes;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraePotions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(
   bus = Bus.MOD
)
public class PotionOfTenacityRecipe implements IBrewingRecipe {
   @SubscribeEvent
   public static void init(FMLCommonSetupEvent event) {
      event.enqueueWork(() -> BrewingRecipeRegistry.addRecipe(new PotionOfTenacityRecipe()));
   }

   public boolean isInput(ItemStack input) {
      return input.m_41720_() == Items.f_42787_;
   }

   public boolean isIngredient(ItemStack ingredient) {
      return ingredient.m_41720_() == AquamiraeItems.WISTERIA_NIVEIS.get();
   }

   @NotNull
   public ItemStack getOutput(@NotNull ItemStack input, @NotNull ItemStack ingredient) {
      return this.isInput(input) && this.isIngredient(ingredient)
         ? PotionUtils.m_43549_(new ItemStack(Items.f_42589_), (Potion)AquamiraePotions.POTION_OF_TENACITY.get())
         : ItemStack.f_41583_;
   }
}
