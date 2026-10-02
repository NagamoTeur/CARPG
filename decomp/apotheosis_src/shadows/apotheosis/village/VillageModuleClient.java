package shadows.apotheosis.village;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.village.fletching.FletchingScreen;
import shadows.apotheosis.village.fletching.arrows.BroadheadArrowRenderer;
import shadows.apotheosis.village.fletching.arrows.ExplosiveArrowRenderer;
import shadows.apotheosis.village.fletching.arrows.MiningArrowRenderer;
import shadows.apotheosis.village.fletching.arrows.ObsidianArrowRenderer;

public class VillageModuleClient {
   public static void init() {
      MenuScreens.m_96206_((MenuType)Apoth.Menus.FLETCHING.get(), FletchingScreen::new);
      EntityRenderers.m_174036_((EntityType)Apoth.Entities.OBSIDIAN_ARROW.get(), ObsidianArrowRenderer::new);
      EntityRenderers.m_174036_((EntityType)Apoth.Entities.BROADHEAD_ARROW.get(), BroadheadArrowRenderer::new);
      EntityRenderers.m_174036_((EntityType)Apoth.Entities.EXPLOSIVE_ARROW.get(), ExplosiveArrowRenderer::new);
      EntityRenderers.m_174036_((EntityType)Apoth.Entities.MINING_ARROW.get(), MiningArrowRenderer::new);
   }
}
