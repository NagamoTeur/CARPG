package net.thirdlife.iterrpg.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.thirdlife.iterrpg.procedures.ScytheSplashDamageProcedure;

public class NetheriteScytheItem extends SwordItem {
   public NetheriteScytheItem() {
      super(new Tier() {
         public int m_6609_() {
            return 2234;
         }

         public float m_6624_() {
            return 4.0F;
         }

         public float m_6631_() {
            return 2.0F;
         }

         public int m_6604_() {
            return 0;
         }

         public int m_6601_() {
            return 15;
         }

         public Ingredient m_6282_() {
            return Ingredient.m_43927_(new ItemStack[]{new ItemStack(Items.f_42418_)});
         }
      }, 3, -2.6F, new Properties().m_41491_(CreativeModeTab.f_40757_).m_41486_());
   }

   public boolean m_7579_(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
      boolean retval = super.m_7579_(itemstack, entity, sourceentity);
      ScytheSplashDamageProcedure.execute(entity.f_19853_, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), sourceentity);
      return retval;
   }
}
