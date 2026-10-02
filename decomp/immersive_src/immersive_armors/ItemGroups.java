package immersive_armors;

import immersive_armors.cobalt.registration.Registration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

public class ItemGroups {
   public static final CreativeModeTab ARMOR = Registration.ObjectBuilders.ItemGroups.create(
      new ResourceLocation("immersive_armors", "immersive_armors_tab"),
      () -> Items.items.getOrDefault("divine_chestplate", () -> net.minecraft.world.item.Items.f_42468_).get().m_7968_()
   );
}
