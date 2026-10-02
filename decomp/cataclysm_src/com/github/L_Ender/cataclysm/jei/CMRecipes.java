package com.github.L_Ender.cataclysm.jei;

import com.github.L_Ender.cataclysm.crafting.AltarOfAmethystRecipe;
import com.github.L_Ender.cataclysm.crafting.WeaponfusionRecipe;
import com.github.L_Ender.cataclysm.init.ModRecipeTypes;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

public class CMRecipes {
   private final RecipeManager recipeManager;

   public CMRecipes() {
      Minecraft minecraft = Minecraft.m_91087_();
      ClientLevel level = minecraft.f_91073_;
      if (level != null) {
         this.recipeManager = level.m_7465_();
      } else {
         throw new NullPointerException("minecraft world must not be null.");
      }
   }

   public List<WeaponfusionRecipe> getWeaponfusionRecipes() {
      return this.recipeManager.m_44013_((RecipeType)ModRecipeTypes.WEAPON_FUSION.get()).stream().toList();
   }

   public List<AltarOfAmethystRecipe> getAmethystBlessRecipes() {
      return this.recipeManager.m_44013_((RecipeType)ModRecipeTypes.AMETHYST_BLESS.get()).stream().toList();
   }
}
