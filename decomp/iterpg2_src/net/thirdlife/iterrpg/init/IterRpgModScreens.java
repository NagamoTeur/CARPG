package net.thirdlife.iterrpg.init;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.thirdlife.iterrpg.client.gui.MobPlacerGUIScreen;
import net.thirdlife.iterrpg.client.gui.SpellbookGuiScreen;

@EventBusSubscriber(
   bus = Bus.MOD,
   value = {Dist.CLIENT}
)
public class IterRpgModScreens {
   @SubscribeEvent
   public static void clientLoad(FMLClientSetupEvent event) {
      event.enqueueWork(() -> {
         MenuScreens.m_96206_((MenuType)IterRpgModMenus.MOB_PLACER_GUI.get(), MobPlacerGUIScreen::new);
         MenuScreens.m_96206_((MenuType)IterRpgModMenus.SPELLBOOK_GUI.get(), SpellbookGuiScreen::new);
      });
   }
}
