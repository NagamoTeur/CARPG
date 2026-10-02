package com.hollingsworth.arsnouveau.common.items.summon_charms;

import com.hollingsworth.arsnouveau.api.item.AbstractSummonCharm;
import com.hollingsworth.arsnouveau.common.block.tile.SummoningTile;
import com.hollingsworth.arsnouveau.common.entity.BehaviorRegistry;
import com.hollingsworth.arsnouveau.common.entity.ChangeableBehavior;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class StarbuncleCharm extends AbstractSummonCharm {
   @Override
   public InteractionResult useOnBlock(UseOnContext context, Level world, BlockPos pos) {
      Starbuncle carbuncle = new Starbuncle(world, true);
      Starbuncle.StarbuncleData data = new Starbuncle.StarbuncleData(context.m_43722_().m_41784_());
      pos = pos.m_121945_(context.m_43719_());
      carbuncle.m_6034_((double)pos.m_123341_() + 0.5, (double)pos.m_123342_(), (double)pos.m_123343_() + 0.5);
      carbuncle.data = data;
      world.m_7967_(carbuncle);
      carbuncle.restoreFromTag();
      return InteractionResult.SUCCESS;
   }

   @Override
   public InteractionResult useOnSummonTile(UseOnContext context, Level world, SummoningTile tile, BlockPos pos) {
      return this.useOnBlock(context, world, pos);
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip2, flagIn);
      Starbuncle.StarbuncleData data = new Starbuncle.StarbuncleData(stack.m_41784_());
      if (data.name != null) {
         tooltip2.add(data.name);
      }

      if (data.adopter != null) {
         tooltip2.add(Component.m_237110_("ars_nouveau.adopter", new Object[]{data.adopter}).m_130948_(Style.f_131099_.m_131140_(ChatFormatting.GOLD)));
      }

      if (data.bio != null) {
         tooltip2.add(Component.m_237113_(data.bio).m_130948_(Style.f_131099_.m_131140_(ChatFormatting.DARK_PURPLE)));
      }

      if (data.behaviorTag != null && worldIn != null) {
         try {
            ChangeableBehavior behavior = BehaviorRegistry.create(new Starbuncle(worldIn, true), data.behaviorTag);
            if (behavior != null) {
               behavior.getTooltip(tooltip2);
            }
         } catch (Exception var7) {
         }
      }
   }
}
