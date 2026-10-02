package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.source.SourcelinkEventQueue;
import com.hollingsworth.arsnouveau.common.datagen.BlockTagProvider;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.SaplingGrowTreeEvent;
import net.minecraftforge.event.level.BlockEvent.CropGrowEvent.Post;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "ars_nouveau"
)
public class AgronomicSourcelinkTile extends SourcelinkTile {
   public AgronomicSourcelinkTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.AGRONOMIC_SOURCELINK_TILE, pos, state);
   }

   @Override
   public int getMaxSource() {
      return 1000;
   }

   @SubscribeEvent
   public static void cropGrow(Post event) {
      int mana = 20;
      if (event.getLevel().m_8055_(event.getPos()).m_204336_(BlockTagProvider.MAGIC_PLANTS)) {
         mana += 25;
      }

      if (event.getLevel() instanceof Level) {
         SourcelinkEventQueue.addManaEvent((Level)event.getLevel(), AgronomicSourcelinkTile.class, mana, event, event.getPos());
      }
   }

   @SubscribeEvent
   public static void treeGrow(SaplingGrowTreeEvent event) {
      int mana = 50;
      if (event.getLevel().m_8055_(event.getPos()).m_204336_(BlockTagProvider.MAGIC_SAPLINGS)) {
         mana += 50;
      }

      if (event.getLevel() instanceof Level) {
         SourcelinkEventQueue.addManaEvent((Level)event.getLevel(), AgronomicSourcelinkTile.class, mana, event, event.getPos());
      }
   }

   @Override
   public boolean usesEventQueue() {
      return true;
   }

   @Override
   public int getTransferRate() {
      return 1000;
   }
}
