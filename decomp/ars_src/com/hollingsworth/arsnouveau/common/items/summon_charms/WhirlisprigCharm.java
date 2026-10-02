package com.hollingsworth.arsnouveau.common.items.summon_charms;

import com.hollingsworth.arsnouveau.api.item.AbstractSummonCharm;
import com.hollingsworth.arsnouveau.common.block.tile.SummoningTile;
import com.hollingsworth.arsnouveau.common.block.tile.WhirlisprigTile;
import com.hollingsworth.arsnouveau.common.entity.Whirlisprig;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class WhirlisprigCharm extends AbstractSummonCharm {
   @Override
   public InteractionResult useOnBlock(UseOnContext context, Level world, BlockPos pos) {
      if (world.m_8055_(pos).m_204336_(BlockTags.f_13041_)) {
         world.m_46597_(pos, BlockRegistry.WHIRLISPRIG_FLOWER.m_49966_());
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   @Override
   public InteractionResult useOnSummonTile(UseOnContext context, Level world, SummoningTile tile, BlockPos pos) {
      if (tile instanceof WhirlisprigTile) {
         Whirlisprig whirlisprig = new Whirlisprig(world, true, pos);
         whirlisprig.m_6034_((double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 1.0, (double)pos.m_123343_() + 0.5);
         world.m_7967_(whirlisprig);
         whirlisprig.flowerPos = new BlockPos(pos);
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }
}
