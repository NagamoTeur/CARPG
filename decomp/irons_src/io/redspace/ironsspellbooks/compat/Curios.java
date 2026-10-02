package io.redspace.ironsspellbooks.compat;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.InterModComms;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotTypeMessage.Builder;

public class Curios {
   public static String SPELLBOOK_SLOT = "spellbook";
   public static String NECKLACE_SLOT = "necklace";
   public static String RING_SLOT = "ring";

   public static void registerCurioSlot(String identifier, int slots, boolean isHidden, @Nullable ResourceLocation icon) {
      Builder message = new Builder(identifier);
      message.size(slots);
      if (isHidden) {
         message.hide();
      }

      if (icon != null) {
         message.icon(icon);
      }

      InterModComms.sendTo("curios", "register_type", () -> message.build());
   }
}
