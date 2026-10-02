package lykrast.meetyourfight.registry;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.SlotTypePreset;

@EventBusSubscriber(
   bus = Bus.MOD,
   modid = "meetyourfight"
)
public class CuriosIMC {
   @SubscribeEvent
   public static void sendIMC(InterModEnqueueEvent event) {
      InterModComms.sendTo("curios", "register_type", () -> SlotTypePreset.HEAD.getMessageBuilder().build());
      InterModComms.sendTo("curios", "register_type", () -> SlotTypePreset.NECKLACE.getMessageBuilder().build());
      InterModComms.sendTo("curios", "register_type", () -> SlotTypePreset.CHARM.getMessageBuilder().build());
      InterModComms.sendTo("curios", "register_type", () -> SlotTypePreset.HANDS.getMessageBuilder().build());
      InterModComms.sendTo("curios", "register_type", () -> SlotTypePreset.BODY.getMessageBuilder().build());
   }
}
