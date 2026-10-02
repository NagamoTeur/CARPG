package daripher.skilltree.container;

import daripher.itemproduction.block.entity.Interactive;
import javax.annotation.Nullable;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class ContainerHelper {
   @Nullable
   public static Player getViewingPlayer(AbstractContainerMenu menu) {
      return ((Interactive)menu).getUser();
   }

   @Nullable
   public static Player getViewingPlayer(Container container) {
      return container instanceof Interactive interactive ? interactive.getUser() : null;
   }
}
