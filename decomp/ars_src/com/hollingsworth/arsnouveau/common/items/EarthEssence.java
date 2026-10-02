package com.hollingsworth.arsnouveau.common.items;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

public class EarthEssence extends ModItem {
   public EarthEssence(Properties properties) {
      super(properties);
   }

   public EarthEssence() {
   }

   public InteractionResult m_6225_(UseOnContext pContext) {
      if (pContext.m_43723_().f_19853_.f_46443_) {
         return super.m_6225_(pContext);
      } else {
         if (pContext.m_43725_().m_8055_(pContext.m_8083_()).m_204336_(BlockTags.f_144274_)) {
            pContext.m_43725_().m_7731_(pContext.m_8083_(), Blocks.f_50440_.m_49966_(), 3);
            pContext.m_43722_().m_41774_(1);
         }

         return super.m_6225_(pContext);
      }
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip2, flagIn);
      tooltip2.add(Component.m_237115_("ars_nouveau.earth_essence.tooltip").m_130948_(Style.f_131099_.m_131140_(ChatFormatting.GOLD)));
   }
}
