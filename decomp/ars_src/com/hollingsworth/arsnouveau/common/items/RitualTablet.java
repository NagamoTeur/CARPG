package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.common.block.tile.RitualBrazierTile;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class RitualTablet extends ModItem {
   public AbstractRitual ritual;

   public RitualTablet(Properties properties) {
      super(properties);
   }

   public RitualTablet(AbstractRitual ritual) {
      super(ItemsRegistry.defaultItemProperties());
      this.ritual = ritual;
   }

   public InteractionResult m_6225_(UseOnContext context) {
      if (context.m_43725_().m_5776_() || !(context.m_43725_().m_7702_(context.m_8083_()) instanceof RitualBrazierTile tile)) {
         return InteractionResult.PASS;
      } else if (!tile.canTakeAnotherRitual()) {
         context.m_43723_().m_213846_(Component.m_237115_("ars_nouveau.ritual.no_start"));
         return InteractionResult.PASS;
      } else {
         tile.setRitual(this.ritual.getRegistryName());
         if (!context.m_43723_().m_7500_()) {
            context.m_43722_().m_41774_(1);
         }

         return InteractionResult.CONSUME;
      }
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip2, flagIn);
      tooltip2.add(Component.m_237115_("tooltip.ars_nouveau.tablet"));
      if (InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), Minecraft.m_91087_().f_91066_.f_92090_.getKey().m_84873_())) {
         tooltip2.add(Component.m_237115_(this.ritual.getDescriptionKey()));
      } else {
         tooltip2.add(
            Component.m_237110_("tooltip.ars_nouveau.hold_shift", new Object[]{Minecraft.m_91087_().f_91066_.f_92090_.getKey().m_84875_()})
               .m_130948_(Style.f_131099_.m_131140_(ChatFormatting.BLUE))
         );
      }
   }

   public Component m_7626_(ItemStack pStack) {
      return Component.m_237113_(this.ritual.getName());
   }
}
