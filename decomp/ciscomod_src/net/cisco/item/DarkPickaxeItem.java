package net.cisco.item;

import java.util.List;
import net.cisco.init.CiscoModModItems;
import net.cisco.init.CiscoModModTabs;
import net.cisco.procedures.DarkPickaxeBlockDestroyedWithToolProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class DarkPickaxeItem extends PickaxeItem {
   public DarkPickaxeItem() {
      super(new Tier() {
         public int m_6609_() {
            return 5000;
         }

         public float m_6624_() {
            return 16.0F;
         }

         public float m_6631_() {
            return 6.0F;
         }

         public int m_6604_() {
            return 5;
         }

         public int m_6601_() {
            return 18;
         }

         public Ingredient m_6282_() {
            return Ingredient.m_43927_(new ItemStack[]{new ItemStack((ItemLike)CiscoModModItems.DARKSTEEL.get())});
         }
      }, 1, -2.5F, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41486_());
   }

   public boolean m_6813_(ItemStack itemstack, Level world, BlockState blockstate, BlockPos pos, LivingEntity entity) {
      boolean retval = super.m_6813_(itemstack, world, blockstate, pos, entity);
      DarkPickaxeBlockDestroyedWithToolProcedure.execute(entity);
      return retval;
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      list.add(Component.m_237113_("§5A pickaxe forged with the most powerful of ancient materials . Grants a big boost to mining speed on use."));
   }
}
