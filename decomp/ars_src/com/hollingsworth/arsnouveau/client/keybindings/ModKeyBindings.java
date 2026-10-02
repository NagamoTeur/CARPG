package com.hollingsworth.arsnouveau.client.keybindings;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
   modid = "ars_nouveau",
   value = {Dist.CLIENT},
   bus = Bus.MOD
)
public class ModKeyBindings {
   public static final String CATEGORY = "key.category.ars_nouveau.general";
   public static final KeyMapping OPEN_BOOK = new KeyMapping("key.ars_nouveau.open_book", 67, "key.category.ars_nouveau.general");
   public static final KeyMapping OPEN_RADIAL_HUD = new KeyMapping("key.ars_nouveau.selection_hud", 86, "key.category.ars_nouveau.general");
   public static final KeyMapping NEXT_SLOT = new KeyMapping("key.ars_nouveau.next_slot", 88, "key.category.ars_nouveau.general");
   public static final KeyMapping PREVIOUS_SLOT = new KeyMapping("key.ars_nouveau.previous_slot", 90, "key.category.ars_nouveau.general");
   public static final KeyMapping HEAD_CURIO_HOTKEY = new KeyMapping("key.ars_nouveau.head_curio_hotkey", 71, "key.category.ars_nouveau.general");
   public static final KeyMapping FAMILIAR_TOGGLE = new KeyMapping("key.ars_nouveau.familiar_toggle", -1, "key.category.ars_nouveau.general");
   public static final KeyMapping QC_1 = new KeyMapping("key.ars_nouveau.qc1", -1, "key.category.ars_nouveau.general");
   public static final KeyMapping QC_2 = new KeyMapping("key.ars_nouveau.qc2", -1, "key.category.ars_nouveau.general");
   public static final KeyMapping QC_3 = new KeyMapping("key.ars_nouveau.qc3", -1, "key.category.ars_nouveau.general");
   public static final KeyMapping QC_4 = new KeyMapping("key.ars_nouveau.qc4", -1, "key.category.ars_nouveau.general");
   public static final KeyMapping QC_5 = new KeyMapping("key.ars_nouveau.qc5", -1, "key.category.ars_nouveau.general");
   public static final KeyMapping QC_6 = new KeyMapping("key.ars_nouveau.qc6", -1, "key.category.ars_nouveau.general");
   public static final KeyMapping QC_7 = new KeyMapping("key.ars_nouveau.qc7", -1, "key.category.ars_nouveau.general");
   public static final KeyMapping QC_8 = new KeyMapping("key.ars_nouveau.qc8", -1, "key.category.ars_nouveau.general");
   public static final KeyMapping QC_9 = new KeyMapping("key.ars_nouveau.qc9", -1, "key.category.ars_nouveau.general");
   public static final KeyMapping QC_10 = new KeyMapping("key.ars_nouveau.qc10", -1, "key.category.ars_nouveau.general");

   public static int usedQuickSlot(int key) {
      for (ModKeyBindings.QuickSlot q : ModKeyBindings.QuickSlot.VALUES) {
         if (q.key().getKey().m_84873_() == key) {
            return q.slot;
         }
      }

      return -1;
   }

   @SubscribeEvent
   public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
      event.register(OPEN_BOOK);
      event.register(OPEN_RADIAL_HUD);
      event.register(NEXT_SLOT);
      event.register(PREVIOUS_SLOT);
      event.register(HEAD_CURIO_HOTKEY);
      event.register(QC_1);
      event.register(QC_2);
      event.register(QC_3);
      event.register(QC_4);
      event.register(QC_5);
      event.register(QC_6);
      event.register(QC_7);
      event.register(QC_8);
      event.register(QC_9);
      event.register(QC_10);
      event.register(FAMILIAR_TOGGLE);
   }

   public static record QuickSlot(int slot, KeyMapping key) {
      public static final ModKeyBindings.QuickSlot[] VALUES = new ModKeyBindings.QuickSlot[]{
         new ModKeyBindings.QuickSlot(0, ModKeyBindings.QC_1),
         new ModKeyBindings.QuickSlot(1, ModKeyBindings.QC_2),
         new ModKeyBindings.QuickSlot(2, ModKeyBindings.QC_3),
         new ModKeyBindings.QuickSlot(3, ModKeyBindings.QC_4),
         new ModKeyBindings.QuickSlot(4, ModKeyBindings.QC_5),
         new ModKeyBindings.QuickSlot(5, ModKeyBindings.QC_6),
         new ModKeyBindings.QuickSlot(6, ModKeyBindings.QC_7),
         new ModKeyBindings.QuickSlot(7, ModKeyBindings.QC_8),
         new ModKeyBindings.QuickSlot(8, ModKeyBindings.QC_9),
         new ModKeyBindings.QuickSlot(9, ModKeyBindings.QC_10)
      };
   }
}
