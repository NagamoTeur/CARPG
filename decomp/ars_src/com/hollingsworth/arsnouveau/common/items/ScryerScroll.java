package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.camera.ICameraMountable;
import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ScryerScroll extends ModItem {
   public ScryerScroll(Properties properties) {
      super(properties);
   }

   public ScryerScroll() {
   }

   public InteractionResult m_6225_(UseOnContext pContext) {
      if (pContext.m_43725_().f_46443_) {
         return super.m_6225_(pContext);
      } else {
         if (pContext.m_43725_().m_7702_(pContext.m_8083_()) instanceof ICameraMountable) {
            ScryerScroll.ScryerScrollData data = new ScryerScroll.ScryerScrollData(pContext.m_43722_());
            data.setPos(pContext.m_8083_(), pContext.m_43722_());
            PortUtil.sendMessage(
               pContext.m_43723_(),
               Component.m_237110_(
                  "ars_nouveau.scryer_scroll.bound",
                  new Object[]{pContext.m_8083_().m_123341_() + ", " + pContext.m_8083_().m_123342_() + ", " + pContext.m_8083_().m_123343_()}
               )
            );
         }

         return super.m_6225_(pContext);
      }
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      ScryerScroll.ScryerScrollData data = new ScryerScroll.ScryerScrollData(stack);
      if (data.pos != null) {
         tooltip2.add(
            Component.m_237110_(
               "ars_nouveau.scryer_scroll.bound", new Object[]{data.pos.m_123341_() + ", " + data.pos.m_123342_() + ", " + data.pos.m_123343_()}
            )
         );
      } else {
         tooltip2.add(Component.m_237115_("ars_nouveau.scryer_scroll.craft"));
      }

      super.m_7373_(stack, worldIn, tooltip2, flagIn);
   }

   public static class ScryerScrollData {
      @javax.annotation.Nullable
      public BlockPos pos;

      public ScryerScrollData(CompoundTag tag) {
         this.pos = NBTUtil.hasBlockPos(tag, "pos") ? NBTUtil.getBlockPos(tag, "pos") : null;
      }

      public ScryerScrollData(ItemStack stack) {
         this(stack.m_41784_().m_128469_("scryer_scroll_data"));
      }

      public CompoundTag toTag() {
         CompoundTag tag = new CompoundTag();
         NBTUtil.storeBlockPos(tag, "pos", this.pos);
         return tag;
      }

      public void setPos(BlockPos pos, ItemStack stack) {
         this.pos = pos;
         stack.m_41784_().m_128365_("scryer_scroll_data", this.toTag());
      }
   }
}
