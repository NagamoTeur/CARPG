package com.hollingsworth.arsnouveau.api.item;

import com.hollingsworth.arsnouveau.common.block.tile.SummoningTile;
import com.hollingsworth.arsnouveau.common.items.ModItem;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public abstract class AbstractSummonCharm extends ModItem {
   public AbstractSummonCharm(Properties properties) {
      super(properties);
   }

   public AbstractSummonCharm() {
      this(ItemsRegistry.defaultItemProperties());
   }

   public InteractionResult m_6225_(UseOnContext context) {
      Level world = context.m_43725_();
      if (world.f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         BlockPos pos = context.m_8083_();
         InteractionResult result;
         if (world.m_7702_(pos) instanceof SummoningTile tile) {
            result = this.useOnSummonTile(context, world, tile, pos);
         } else {
            result = this.useOnBlock(context, world, pos);
         }

         if (result == InteractionResult.SUCCESS) {
            context.m_43722_().m_41774_(1);
         }

         return result;
      }
   }

   public abstract InteractionResult useOnBlock(UseOnContext var1, Level var2, BlockPos var3);

   public abstract InteractionResult useOnSummonTile(UseOnContext var1, Level var2, SummoningTile var3, BlockPos var4);
}
