package net.thirdlife.iterrpg.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.thirdlife.iterrpg.procedures.RapierStrikeProcedure;

public class VeryGoodAndCheerfulRapierItem extends SwordItem {
   public VeryGoodAndCheerfulRapierItem() {
      super(new Tier() {
         public int m_6609_() {
            return 250;
         }

         public float m_6624_() {
            return 6.0F;
         }

         public float m_6631_() {
            return 2.0F;
         }

         public int m_6604_() {
            return 1;
         }

         public int m_6601_() {
            return 14;
         }

         public Ingredient m_6282_() {
            return Ingredient.m_43927_(new ItemStack[]{new ItemStack(Items.f_42416_), new ItemStack(Items.f_42749_)});
         }
      }, 3, -2.2F, new Properties().m_41491_(CreativeModeTab.f_40757_));
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      list.add(Component.m_237115_("iterpg.desc.rapier"));
   }

   public boolean onEntitySwing(ItemStack itemstack, LivingEntity entity) {
      boolean retval = super.onEntitySwing(itemstack, entity);
      RapierStrikeProcedure.execute(entity.f_19853_, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity, itemstack);
      return retval;
   }
}
