package net.thirdlife.iterrpg.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.thirdlife.iterrpg.procedures.ElementalAttackProcedure;
import net.thirdlife.iterrpg.procedures.WaterSetRepairProcedure;

public class WaterHoeItem extends HoeItem {
   public WaterHoeItem() {
      super(new Tier() {
         public int m_6609_() {
            return 1024;
         }

         public float m_6624_() {
            return 7.0F;
         }

         public float m_6631_() {
            return 0.0F;
         }

         public int m_6604_() {
            return 3;
         }

         public int m_6601_() {
            return 16;
         }

         public Ingredient m_6282_() {
            return Ingredient.m_151265_();
         }
      }, 0, -2.0F, new Properties().m_41491_(CreativeModeTab.f_40756_));
   }

   public boolean m_7579_(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
      boolean retval = super.m_7579_(itemstack, entity, sourceentity);
      ElementalAttackProcedure.execute(entity.f_19853_, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity, sourceentity, itemstack);
      return retval;
   }

   public void m_6883_(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
      super.m_6883_(itemstack, world, entity, slot, selected);
      WaterSetRepairProcedure.execute(world, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity, itemstack);
   }
}
