package daripher.skilltree.mixin.minecraft;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({InventoryMenu.class})
public abstract class InventoryMenuMixin extends RecipeBookMenu<CraftingContainer> {
   @Shadow
   @Final
   private CraftingContainer f_39701_;
   @Shadow
   @Final
   private Player f_39703_;
   @Shadow
   @Final
   private ResultContainer f_39702_;

   public InventoryMenuMixin() {
      super(null, 0);
   }

   protected boolean m_38903_(@NotNull ItemStack itemStack, int fromSlot, int toSlot, boolean beginFromEnd) {
      if (itemStack == this.f_39702_.m_8020_(0)) {
         ForgeEventFactory.firePlayerCraftingEvent(this.f_39703_, itemStack, this.f_39701_);
      }

      return super.m_38903_(itemStack, fromSlot, toSlot, beginFromEnd);
   }
}
