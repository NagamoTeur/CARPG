package io.redspace.ironsspellbooks.mixin;

import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({SmithingMenu.class})
public abstract class SmithingMenuMixin {
   private static final UpgradeRecipe fakeRecipe = new UpgradeRecipe(
      new ResourceLocation(""), Ingredient.m_151265_(), Ingredient.m_151265_(), ItemStack.f_41583_
   ) {
      public boolean m_5818_(Container pInv, Level pLevel) {
         return true;
      }
   };
   @Shadow
   private UpgradeRecipe f_40242_;

   @Inject(
      method = {"createResult"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void createResult(CallbackInfo ci) {
      SmithingMenu menu = (SmithingMenu)this;
      Slot baseSlot = menu.m_38853_(0);
      if (baseSlot.m_6657_() && menu.m_38853_(1).m_7993_().m_41720_().equals(ItemRegistry.SHRIVING_STONE.get())) {
         Slot resultSlot = menu.m_38853_(2);
         ItemStack resultStack = Utils.handleShriving(baseSlot.m_7993_());
         if (!resultStack.m_41619_()) {
            resultSlot.m_5852_(resultStack);
            this.f_40242_ = fakeRecipe;
            ci.cancel();
         }
      }
   }
}
