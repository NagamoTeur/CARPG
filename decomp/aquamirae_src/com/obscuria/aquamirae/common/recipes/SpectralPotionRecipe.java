package com.obscuria.aquamirae.common.recipes;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraePotions;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
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
public class SpectralPotionRecipe implements IBrewingRecipe {
   @SubscribeEvent
   public static void init(FMLCommonSetupEvent event) {
      event.enqueueWork(() -> BrewingRecipeRegistry.addRecipe(new SpectralPotionRecipe()));
   }

   public boolean isInput(ItemStack input) {
      Item inputItem = input.m_41720_();
      return (inputItem == Items.f_42589_ || inputItem == Items.f_42736_ || inputItem == Items.f_42739_) && PotionUtils.m_43579_(input) == Potions.f_43601_;
   }

   public boolean isIngredient(ItemStack ingredient) {
      return ingredient.m_41720_() == AquamiraeItems.ESCA.get();
   }

   @NotNull
   public ItemStack getOutput(@NotNull ItemStack input, @NotNull ItemStack ingredient) {
      return this.isInput(input) && this.isIngredient(ingredient)
         ? PotionUtils.m_43549_(new ItemStack(input.m_41720_()), (Potion)AquamiraePotions.SPECTRAL_POTION.get())
         : ItemStack.f_41583_;
   }
}
