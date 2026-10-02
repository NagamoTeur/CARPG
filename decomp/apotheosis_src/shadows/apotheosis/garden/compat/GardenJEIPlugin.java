package shadows.apotheosis.garden.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.Apotheosis;

@JeiPlugin
public class GardenJEIPlugin implements IModPlugin {
   public void registerRecipes(IRecipeRegistration reg) {
      if (Apotheosis.enableGarden) {
         reg.addIngredientInfo(
            new ItemStack((ItemLike)Apoth.Items.ENDER_LEAD.get()), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("info.apotheosis.ender_lead")}
         );
      }
   }

   public ResourceLocation getPluginUid() {
      return new ResourceLocation("apotheosis", "garden");
   }
}
