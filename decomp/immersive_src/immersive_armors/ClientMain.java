package immersive_armors;

import immersive_armors.item.DyeableExtendedArmorItem;
import immersive_armors.mixin.MixinItemRenderer;
import immersive_armors.network.NetworkManagerImpl;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

public class ClientMain {
   public static void postLoad() {
      MixinItemRenderer itemRenderer = (MixinItemRenderer)Minecraft.m_91087_().m_91291_();

      for (Supplier<Item> item : Items.coloredItems.values()) {
         itemRenderer.getColors()
            .m_92689_(
               (stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableExtendedArmorItem)stack.m_41720_()).m_41121_(stack), new ItemLike[]{(ItemLike)item.get()}
            );
      }

      Main.networkManager = new NetworkManagerImpl();
      ItemsClient.setupPieces();
   }
}
