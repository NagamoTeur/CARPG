package immersive_armors.forge;

import immersive_armors.client.OverlayRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent.Post;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
   modid = "immersive_armors",
   bus = Bus.FORGE,
   value = {Dist.CLIENT}
)
public class ForgeOverlayRenderer extends Gui {
   private static final ResourceLocation NamedGuiIdentifier = new ResourceLocation("minecraft:hotbar");

   public ForgeOverlayRenderer(Minecraft client, ItemRenderer itemRenderer) {
      super(client, itemRenderer);
   }

   @SubscribeEvent(
      priority = EventPriority.NORMAL
   )
   public static void renderOverlay(Post event) {
      if (event.getOverlay().id().equals(NamedGuiIdentifier)) {
         OverlayRenderer.renderOverlay();
      }
   }
}
