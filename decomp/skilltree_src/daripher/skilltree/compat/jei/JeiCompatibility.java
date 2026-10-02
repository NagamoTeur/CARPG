package daripher.skilltree.compat.jei;

import daripher.skilltree.SkillTreeMod;
import daripher.skilltree.item.gem.GemItem;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class JeiCompatibility implements IModPlugin {
   @NotNull
   public ResourceLocation getPluginUid() {
      return new ResourceLocation("skilltree", "jei_plugin");
   }

   public void registerRecipes(@NotNull IRecipeRegistration registration) {
      if (!SkillTreeMod.apotheosisEnabled()) {
         ForgeRegistries.ITEMS
            .getValues()
            .stream()
            .filter(GemItem.class::isInstance)
            .<ItemStack>map(ItemStack::new)
            .forEach(itemStack -> this.addGemInfo(registration, itemStack));
      }
   }

   public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registration) {
   }

   protected void addGemInfo(IRecipeRegistration registration, ItemStack itemStack) {
      registration.addIngredientInfo(itemStack, VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("skilltree.jei.gem_info")});
   }
}
