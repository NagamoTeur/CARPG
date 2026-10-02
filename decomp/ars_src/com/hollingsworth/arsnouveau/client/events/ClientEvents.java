package com.hollingsworth.arsnouveau.client.events;

import com.hollingsworth.arsnouveau.client.gui.PatchouliTooltipEvent;
import com.hollingsworth.arsnouveau.common.block.tile.GhostWeaveTile;
import com.hollingsworth.arsnouveau.common.block.tile.SkyBlockTile;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHighlightEvent.Block;
import net.minecraftforge.client.event.RenderTooltipEvent.Pre;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   value = {Dist.CLIENT},
   modid = "ars_nouveau"
)
public class ClientEvents {
   @SubscribeEvent
   public static void TooltipEvent(Pre e) {
      try {
         PatchouliTooltipEvent.onTooltip(e.getPoseStack(), e.getItemStack(), e.getX(), e.getY());
      } catch (Exception var2) {
         var2.printStackTrace();
      }
   }

   @SubscribeEvent
   public static void highlightBlockEvent(Block e) {
      Level level = Minecraft.m_91087_().f_91073_;
      if (level != null) {
         BlockEntity be = level.m_7702_(e.getTarget().m_82425_());
         if (be instanceof SkyBlockTile skyTile && !skyTile.showFacade()) {
            e.setCanceled(true);
         }

         if (be instanceof GhostWeaveTile ghostTile && ghostTile.isInvisible()) {
            e.setCanceled(true);
         }
      }
   }
}
