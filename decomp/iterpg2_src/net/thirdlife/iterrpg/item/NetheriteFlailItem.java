package net.thirdlife.iterrpg.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.thirdlife.iterrpg.procedures.FlailPlaysoundProcedure;

public class NetheriteFlailItem extends PickaxeItem {
   public NetheriteFlailItem() {
      super(new Tier() {
         public int m_6609_() {
            return 2437;
         }

         public float m_6624_() {
            return 2.0F;
         }

         public float m_6631_() {
            return 14.0F;
         }

         public int m_6604_() {
            return 1;
         }

         public int m_6601_() {
            return 15;
         }

         public Ingredient m_6282_() {
            return Ingredient.m_151265_();
         }
      }, 1, -3.5F, new Properties().m_41491_(CreativeModeTab.f_40757_).m_41486_());
   }

   public boolean m_7579_(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
      boolean retval = super.m_7579_(itemstack, entity, sourceentity);
      FlailPlaysoundProcedure.execute(entity.f_19853_, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      return retval;
   }
}
