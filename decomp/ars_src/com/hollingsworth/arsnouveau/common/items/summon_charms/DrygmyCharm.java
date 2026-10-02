package com.hollingsworth.arsnouveau.common.items.summon_charms;

import com.hollingsworth.arsnouveau.api.item.AbstractSummonCharm;
import com.hollingsworth.arsnouveau.common.block.tile.DrygmyTile;
import com.hollingsworth.arsnouveau.common.block.tile.SummoningTile;
import com.hollingsworth.arsnouveau.common.entity.EntityDrygmy;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class DrygmyCharm extends AbstractSummonCharm {
   @Override
   public InteractionResult useOnBlock(UseOnContext context, Level world, BlockPos pos) {
      if (world.m_8055_(pos).m_60734_() == Blocks.f_50079_) {
         world.m_46597_(pos, BlockRegistry.DRYGMY_BLOCK.m_49966_());
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   @Override
   public InteractionResult useOnSummonTile(UseOnContext context, Level world, SummoningTile tile, BlockPos pos) {
      if (tile instanceof DrygmyTile) {
         EntityDrygmy drygmy = new EntityDrygmy(world, true);
         drygmy.m_6034_((double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 1.0, (double)pos.m_123343_() + 0.5);
         world.m_7967_(drygmy);
         drygmy.homePos = new BlockPos(pos);
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }
}
