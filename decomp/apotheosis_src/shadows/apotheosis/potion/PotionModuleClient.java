package shadows.apotheosis.potion;

import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.event.RegisterColorHandlersEvent.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import shadows.apotheosis.Apoth;

public class PotionModuleClient {
   @SubscribeEvent
   public static void colors(Item e) {
      e.register((stack, tint) -> PotionUtils.m_43575_(stack), new ItemLike[]{(ItemLike)Apoth.Items.POTION_CHARM.get()});
   }
}
