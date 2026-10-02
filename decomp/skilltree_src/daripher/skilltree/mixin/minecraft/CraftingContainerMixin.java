package daripher.skilltree.mixin.minecraft;

import daripher.itemproduction.block.entity.Interactive;
import javax.annotation.Nullable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({CraftingContainer.class})
public class CraftingContainerMixin implements Interactive {
   @Shadow
   @Final
   private AbstractContainerMenu f_39323_;

   @Nullable
   public Player getUser() {
      Interactive menu = (Interactive)this.f_39323_;
      return menu == null ? null : menu.getUser();
   }

   public void setUser(@Nullable Player player) {
   }
}
