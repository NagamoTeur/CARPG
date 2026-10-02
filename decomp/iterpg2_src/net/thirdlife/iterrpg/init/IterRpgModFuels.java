package net.thirdlife.iterrpg.init;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class IterRpgModFuels {
   @SubscribeEvent
   public static void furnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event) {
      ItemStack itemstack = event.getItemStack();
      if (itemstack.m_41720_() == ((Block)IterRpgModBlocks.SACRED_LOG.get()).m_5456_()) {
         event.setBurnTime(300);
      } else if (itemstack.m_41720_() == ((Block)IterRpgModBlocks.SACRED_WOOD.get()).m_5456_()) {
         event.setBurnTime(300);
      } else if (itemstack.m_41720_() == ((Block)IterRpgModBlocks.SACRED_PLANKS.get()).m_5456_()) {
         event.setBurnTime(300);
      } else if (itemstack.m_41720_() == ((Block)IterRpgModBlocks.SACRED_LEAVES.get()).m_5456_()) {
         event.setBurnTime(50);
      } else if (itemstack.m_41720_() == ((Block)IterRpgModBlocks.STRIPED_SACRED_LOG.get()).m_5456_()) {
         event.setBurnTime(300);
      } else if (itemstack.m_41720_() == IterRpgModItems.MAGMANUM_CHUNK.get()) {
         event.setBurnTime(12800);
      }
   }
}
