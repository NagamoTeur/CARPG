package net.cisco.item;

import java.util.List;
import net.cisco.init.CiscoModModTabs;
import net.cisco.procedures.TalismanOfChallengeRightclickedProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class TalismanOfChallengeItem extends Item {
   public TalismanOfChallengeItem() {
      super(new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41487_(1).m_41486_().m_41497_(Rarity.EPIC));
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      list.add(Component.m_237113_("§dA powerful artifact used to summon and challenge cisco."));
   }

   public InteractionResult m_6225_(UseOnContext context) {
      super.m_6225_(context);
      TalismanOfChallengeRightclickedProcedure.execute(
         context.m_43725_(),
         (double)context.m_8083_().m_123341_(),
         (double)context.m_8083_().m_123342_(),
         (double)context.m_8083_().m_123343_(),
         context.m_43723_()
      );
      return InteractionResult.SUCCESS;
   }
}
