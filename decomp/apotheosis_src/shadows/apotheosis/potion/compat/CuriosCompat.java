package shadows.apotheosis.potion.compat;

import net.minecraftforge.fml.InterModComms;
import top.theillusivec4.curios.api.SlotTypePreset;

public class CuriosCompat {
   public static void sendIMC() {
      InterModComms.sendTo("curios", "register_type", () -> SlotTypePreset.CHARM.getMessageBuilder().size(1).build());
   }
}
