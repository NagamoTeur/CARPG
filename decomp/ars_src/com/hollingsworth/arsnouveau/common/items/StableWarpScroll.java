package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.event.EventQueue;
import com.hollingsworth.arsnouveau.common.event.timed.BuildPortalEvent;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class StableWarpScroll extends ModItem {
   public StableWarpScroll(Properties properties) {
      super(properties.m_41487_(1));
   }

   public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
      return ItemsRegistry.WARP_SCROLL.get().onEntityItemUpdate(stack, entity);
   }

   public InteractionResult m_6225_(UseOnContext context) {
      if (!context.m_43725_().f_46443_) {
         WarpScroll.WarpScrollData scrollData = WarpScroll.WarpScrollData.get(context.m_43722_());
         if (!scrollData.isValid()) {
            return InteractionResult.FAIL;
         }

         EventQueue.getServerInstance()
            .addEvent(new BuildPortalEvent(context.m_43725_(), context.m_8083_(), context.m_43723_().m_6350_().m_122427_(), scrollData));
         context.m_43725_().m_5594_(null, context.m_8083_(), SoundEvents.f_12049_, context.m_43723_().m_5720_(), 1.0F, 1.0F);
      }

      return super.m_6225_(context);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level pLevel, Player player, InteractionHand pUsedHand) {
      if (pUsedHand != InteractionHand.MAIN_HAND) {
         return InteractionResultHolder.m_19090_(player.m_21120_(pUsedHand));
      } else {
         ItemStack stack = player.m_21120_(InteractionHand.MAIN_HAND);
         WarpScroll.WarpScrollData data = WarpScroll.WarpScrollData.get(stack);
         if (!(pLevel instanceof ServerLevel serverLevel)) {
            return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
         } else {
            if (player.m_6144_() && !data.isValid()) {
               data.setData(player.m_20183_(), player.m_20193_().m_46472_().m_135782_().toString(), player.m_20155_());
               player.m_213846_(Component.m_237115_("ars_nouveau.warp_scroll.recorded"));
            } else if (player.m_6144_() && data.isValid()) {
               player.m_213846_(Component.m_237115_("ars_nouveau.warp_scroll.already_recorded"));
            }

            return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
         }
      }
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip2, flagIn);
      WarpScroll.WarpScrollData data = WarpScroll.WarpScrollData.get(stack);
      if (!data.isValid()) {
         tooltip2.add(Component.m_237115_("ars_nouveau.warp_scroll.no_location"));
      } else {
         BlockPos pos = data.getPos();
         tooltip2.add(Component.m_237110_("ars_nouveau.position", new Object[]{pos.m_123341_(), pos.m_123342_(), pos.m_123343_()}));
         String dimId = data.getDimension();
         if (dimId != null) {
            ResourceLocation resourceLocation = new ResourceLocation(dimId);
            tooltip2.add(Component.m_237115_(resourceLocation.m_135815_() + "." + resourceLocation.m_135827_() + ".name"));
         }
      }
   }

   public static class StableScrollData extends WarpScroll.WarpScrollData {
      public StableScrollData(ItemStack stack) {
         super(stack);
      }

      @Override
      public boolean canTeleportWithDim(String dim) {
         return true;
      }

      @Override
      public boolean canTeleportWithDim(Level level) {
         return true;
      }
   }
}
