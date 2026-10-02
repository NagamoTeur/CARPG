package com.hollingsworth.arsnouveau.common.items.summon_charms;

import com.hollingsworth.arsnouveau.api.item.AbstractSummonCharm;
import com.hollingsworth.arsnouveau.common.block.tile.SummoningTile;
import com.hollingsworth.arsnouveau.common.block.tile.WixieCauldronTile;
import com.hollingsworth.arsnouveau.common.entity.EntityWixie;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CauldronBlock;

public class WixieCharm extends AbstractSummonCharm {
   @Override
   public InteractionResult m_6225_(UseOnContext context) {
      return super.m_6225_(context);
   }

   @Override
   public InteractionResult useOnBlock(UseOnContext context, Level world, BlockPos pos) {
      if (world.m_8055_(pos).m_60734_() instanceof CauldronBlock) {
         world.m_46597_(pos, BlockRegistry.WIXIE_CAULDRON.m_49966_());
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   @Override
   public InteractionResult useOnSummonTile(UseOnContext context, Level world, SummoningTile tile, BlockPos pos) {
      if (tile instanceof WixieCauldronTile cauldronTile) {
         if (!cauldronTile.hasWixie()) {
            EntityWixie wixie = new EntityWixie(world, pos);
            wixie.m_6034_((double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 1.0, (double)pos.m_123343_() + 0.5);
            world.m_7967_(wixie);
            cauldronTile.entityID = wixie.m_19879_();
            return InteractionResult.SUCCESS;
         }

         PortUtil.sendMessage(context.m_43723_(), Component.m_237115_("ars_nouveau.wixie.has_wixie"));
      }

      return InteractionResult.PASS;
   }
}
