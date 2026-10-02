package net.cisco.init;

import net.cisco.client.renderer.DescendedHeroArmorRenderer;
import net.cisco.client.renderer.SovereignAscendantArmorRenderer;
import net.cisco.item.DescendedHeroItem;
import net.cisco.item.SovereignAscendantItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent.AddLayers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@EventBusSubscriber(
   modid = "cisco_mod",
   bus = Bus.MOD
)
public class CiscoModModGeckoLibArmors {
   @OnlyIn(Dist.CLIENT)
   @SubscribeEvent
   public static void registerRenderers(AddLayers event) {
      GeoArmorRenderer.registerArmorRenderer(DescendedHeroItem.class, () -> new DescendedHeroArmorRenderer());
      GeoArmorRenderer.registerArmorRenderer(SovereignAscendantItem.class, () -> new SovereignAscendantArmorRenderer());
   }
}
