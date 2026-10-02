package net.cisco.item;

import java.util.List;
import net.cisco.init.CiscoModModItems;
import net.cisco.init.CiscoModModTabs;
import net.cisco.procedures.FellRagnarokLivingEntityIsHitWithToolProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class FellRagnarokItem extends AxeItem {
   public FellRagnarokItem() {
      super(new Tier() {
         public int m_6609_() {
            return 7000;
         }

         public float m_6624_() {
            return 4.0F;
         }

         public float m_6631_() {
            return 20.0F;
         }

         public int m_6604_() {
            return 1;
         }

         public int m_6601_() {
            return 2;
         }

         public Ingredient m_6282_() {
            return Ingredient.m_43927_(new ItemStack[]{new ItemStack((ItemLike)CiscoModModItems.DARKSTEEL.get())});
         }
      }, 1.0F, -2.5F, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41486_());
   }

   public boolean m_7579_(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
      boolean retval = super.m_7579_(itemstack, entity, sourceentity);
      FellRagnarokLivingEntityIsHitWithToolProcedure.execute(entity);
      return retval;
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      list.add(Component.m_237113_("§5The Fell King's weapon of choice."));
      list.add(Component.m_237113_("§8-"));
      list.add(Component.m_237113_("§6[Unique Effect]: Ragnarok: §fDeals bonus armor ignoring damage."));
      list.add(Component.m_237113_("§8-"));
      list.add(Component.m_237113_("§6[Unique Effect]: Fensalir: §fInflicts weakness to enemies on hit."));
   }
}
