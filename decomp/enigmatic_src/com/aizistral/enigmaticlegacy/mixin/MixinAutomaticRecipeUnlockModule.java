package com.aizistral.enigmaticlegacy.mixin;

import java.util.Collection;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(
   targets = {"vazkii.quark.content.tweaks.module.AutomaticRecipeUnlockModule"}
)
public class MixinAutomaticRecipeUnlockModule {
   @Redirect(
      method = {"onPlayerLoggedIn"},
      remap = false,
      at = @At(
         value = "INVOKE",
         remap = true,
         target = "Lnet/minecraft/world/entity/player/Player;awardRecipes(Ljava/util/Collection;)I"
      )
   )
   private int onAwardRecipes(Player player, Collection<Recipe<?>> collection) {
      return player.m_7281_(collection.stream().filter(recipe -> !"enigmaticlegacy".equals(recipe.m_6423_().m_135827_())).collect(Collectors.toList()));
   }

   @Redirect(
      method = {"removeRecipeAdvancements"},
      remap = false,
      at = @At(
         value = "INVOKE",
         remap = true,
         target = "Lnet/minecraft/resources/ResourceLocation;getPath()Ljava/lang/String;"
      )
   )
   private static String onGetPath(ResourceLocation location) {
      return "enigmaticlegacy".equals(location.m_135827_()) ? "OfF_YoU_fUcK_dAmNaBlE_pIeCe_Of_MoD" : location.m_135815_();
   }
}
