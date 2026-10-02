package com.github.L_Ender.cataclysm.inventory;

import com.github.L_Ender.cataclysm.crafting.WeaponfusionRecipe;
import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.github.L_Ender.cataclysm.init.ModMenu;
import com.github.L_Ender.cataclysm.init.ModRecipeTypes;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class WeaponfusionMenu extends ItemCombinerMenu {
   private final Level level;
   @Nullable
   private WeaponfusionRecipe selectedRecipe;
   private final List<WeaponfusionRecipe> recipes;

   public WeaponfusionMenu(int p_40245_, Inventory p_40246_) {
      this(p_40245_, p_40246_, ContainerLevelAccess.f_39287_);
   }

   public WeaponfusionMenu(int p_40248_, Inventory p_40249_, ContainerLevelAccess p_40250_) {
      super((MenuType)ModMenu.WEAPON_FUSION.get(), p_40248_, p_40249_, p_40250_);
      this.level = p_40249_.f_35978_.f_19853_;
      this.recipes = this.level.m_7465_().m_44013_((RecipeType)ModRecipeTypes.WEAPON_FUSION.get());
   }

   protected boolean m_8039_(BlockState p_266887_) {
      return p_266887_.m_60713_((Block)ModBlocks.MECHANICAL_FUSION_ANVIL.get());
   }

   protected boolean m_6560_(Player p_267240_, boolean p_266679_) {
      return this.selectedRecipe != null && this.selectedRecipe.m_5818_(this.f_39769_, this.level);
   }

   protected void m_142365_(Player p_267006_, ItemStack p_266731_) {
      p_266731_.m_41678_(p_267006_.f_19853_, p_267006_, p_266731_.m_41613_());
      this.f_39768_.m_8015_(p_267006_);
      this.shrinkStackInSlot(0);
      this.shrinkStackInSlot(1);
      this.f_39770_.m_39292_((p_267191_, p_267098_) -> p_267191_.m_46796_(1044, p_267098_, 0));
   }

   private List<ItemStack> getRelevantItems() {
      return List.of(this.f_39769_.m_8020_(0), this.f_39769_.m_8020_(1), this.f_39769_.m_8020_(2));
   }

   private void shrinkStackInSlot(int p_267273_) {
      ItemStack itemstack = this.f_39769_.m_8020_(p_267273_);
      itemstack.m_41774_(1);
      this.f_39769_.m_6836_(p_267273_, itemstack);
   }

   public void m_6640_() {
      List<WeaponfusionRecipe> list = this.level
         .m_7465_()
         .m_44056_((RecipeType)ModRecipeTypes.WEAPON_FUSION.get(), this.f_39769_, this.level)
         .stream()
         .filter(p_267116_ -> p_267116_ instanceof WeaponfusionRecipe)
         .map(p_266971_ -> (WeaponfusionRecipe)p_266971_)
         .toList();
      if (list.isEmpty()) {
         this.f_39768_.m_6836_(0, ItemStack.f_41583_);
      } else {
         WeaponfusionRecipe legacyupgraderecipe = list.get(0);
         ItemStack itemstack = legacyupgraderecipe.m_5874_(this.f_39769_);
         this.selectedRecipe = legacyupgraderecipe;
         this.f_39768_.m_6029_(legacyupgraderecipe);
         this.f_39768_.m_6836_(0, itemstack);
      }
   }

   public int getSlotToQuickMoveTo(ItemStack p_267241_) {
      return this.m_5861_(p_267241_) ? 1 : 0;
   }

   protected boolean m_5861_(ItemStack p_267176_) {
      return this.recipes.stream().anyMatch(p_267065_ -> p_267065_.isAdditionIngredient(p_267176_));
   }

   public boolean m_5882_(ItemStack p_266810_, Slot p_267252_) {
      return p_267252_.f_40218_ != this.f_39768_ && super.m_5882_(p_266810_, p_267252_);
   }
}
