package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.common.block.tile.RuneTile;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Material;

public class RunicChalk extends ModItem {
   public RunicChalk() {
      super(ItemsRegistry.defaultItemProperties().m_41503_(15));
   }

   public InteractionResult m_6225_(UseOnContext context) {
      BlockPos pos = context.m_8083_();
      Level world = context.m_43725_();
      if (world.f_46443_) {
         return super.m_6225_(context);
      } else {
         if (world.m_8055_(pos.m_7494_()).m_60767_() == Material.f_76296_) {
            world.m_46597_(pos.m_7494_(), BlockRegistry.RUNE_BLOCK.m_49966_());
            if (world.m_7702_(pos.m_7494_()) instanceof RuneTile) {
               ((RuneTile)world.m_7702_(pos.m_7494_())).uuid = context.m_43723_().m_20148_();
            }

            context.m_43722_().m_41622_(1, context.m_43723_(), t -> {
            });
         }

         return InteractionResult.SUCCESS;
      }
   }
}
