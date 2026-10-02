package com.hollingsworth.arsnouveau.common.items.summon_charms;

import com.hollingsworth.arsnouveau.api.familiar.PersistentFamiliarData;
import com.hollingsworth.arsnouveau.common.block.tile.StorageLecternTile;
import com.hollingsworth.arsnouveau.common.entity.EntityBookwyrm;
import com.hollingsworth.arsnouveau.common.items.ModItem;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class BookwyrmCharm extends ModItem {
   public InteractionResult m_6225_(UseOnContext pContext) {
      Level world = pContext.m_43725_();
      BlockPos pos = pContext.m_8083_();
      if (world.f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         if (world.m_7702_(pos) instanceof StorageLecternTile tile) {
            EntityBookwyrm bookwyrm = tile.addBookwyrm();
            if (bookwyrm != null) {
               bookwyrm.readCharm(pContext.m_43722_());
               pContext.m_43722_().m_41774_(1);
               return InteractionResult.SUCCESS;
            }
         }

         return super.m_6225_(pContext);
      }
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip2, flagIn);
      if (stack.m_41782_()) {
         PersistentFamiliarData data = new PersistentFamiliarData(stack.m_41784_());
         if (data.name != null) {
            tooltip2.add(data.name);
         }
      }
   }
}
