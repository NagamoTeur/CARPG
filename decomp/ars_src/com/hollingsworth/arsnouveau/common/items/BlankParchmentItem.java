package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.camera.ICameraMountable;
import com.hollingsworth.arsnouveau.api.item.IScribeable;
import com.hollingsworth.arsnouveau.common.block.tile.ScribesTile;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class BlankParchmentItem extends ModItem implements IScribeable {
   public BlankParchmentItem(Properties properties) {
      super(properties);
   }

   public BlankParchmentItem() {
   }

   public InteractionResult m_6225_(UseOnContext pContext) {
      if (pContext.m_43725_().f_46443_) {
         return super.m_6225_(pContext);
      } else if (pContext.m_43725_().m_7702_(pContext.m_8083_()) instanceof ICameraMountable) {
         ItemStack stack = new ItemStack((ItemLike)ItemsRegistry.SCRYER_SCROLL.get());
         ScryerScroll.ScryerScrollData data = new ScryerScroll.ScryerScrollData(stack);
         data.setPos(pContext.m_8083_(), stack);
         if (!pContext.m_43723_().m_36356_(stack)) {
            pContext.m_43725_()
               .m_7967_(
                  new ItemEntity(pContext.m_43725_(), pContext.m_43723_().m_20185_(), pContext.m_43723_().m_20186_(), pContext.m_43723_().m_20189_(), stack)
               );
         }

         pContext.m_43722_().m_41774_(1);
         return InteractionResult.SUCCESS;
      } else {
         return super.m_6225_(pContext);
      }
   }

   @Override
   public boolean onScribe(Level world, BlockPos pos, Player player, InteractionHand handIn, ItemStack thisStack) {
      ItemStack spellParchment = new ItemStack((ItemLike)ItemsRegistry.SPELL_PARCHMENT.get());
      if (spellParchment.m_41720_() instanceof IScribeable scribeable) {
         boolean success = scribeable.onScribe(world, pos, player, handIn, spellParchment);
         if (world.m_7702_(pos) instanceof ScribesTile scribesTile && success) {
            scribesTile.setStack(spellParchment);
         }

         return success;
      } else {
         return false;
      }
   }
}
