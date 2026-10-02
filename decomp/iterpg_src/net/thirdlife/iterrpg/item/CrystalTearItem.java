package net.thirdlife.iterrpg.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.thirdlife.iterrpg.procedures.GrimKeyPassProcedure;

public class CrystalTearItem extends Item {
   public CrystalTearItem() {
      super(new Properties().m_41491_(CreativeModeTab.f_40759_).m_41487_(1).m_41486_().m_41497_(Rarity.UNCOMMON));
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      list.add(Component.m_237115_("iterpg.desc.crystal_tear"));
   }

   public InteractionResult m_6225_(UseOnContext context) {
      super.m_6225_(context);
      GrimKeyPassProcedure.execute(
         context.m_43725_(), (double)context.m_8083_().m_123341_(), (double)context.m_8083_().m_123342_(), (double)context.m_8083_().m_123343_()
      );
      return InteractionResult.SUCCESS;
   }
}
