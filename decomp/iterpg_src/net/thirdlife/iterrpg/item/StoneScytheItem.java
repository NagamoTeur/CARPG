package net.thirdlife.iterrpg.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.thirdlife.iterrpg.procedures.ScytheSplashDamageProcedure;

public class StoneScytheItem extends SwordItem {
   public StoneScytheItem() {
      super(new Tier() {
         public int m_6609_() {
            return 131;
         }

         public float m_6624_() {
            return 4.0F;
         }

         public float m_6631_() {
            return -0.25F;
         }

         public int m_6604_() {
            return 0;
         }

         public int m_6601_() {
            return 5;
         }

         public Ingredient m_6282_() {
            return Ingredient.m_43927_(new ItemStack[]{new ItemStack(Blocks.f_50652_), new ItemStack(Blocks.f_152551_), new ItemStack(Blocks.f_50730_)});
         }
      }, 3, -2.6F, new Properties().m_41491_(CreativeModeTab.f_40757_));
   }

   public boolean m_7579_(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
      boolean retval = super.m_7579_(itemstack, entity, sourceentity);
      ScytheSplashDamageProcedure.execute(entity.f_19853_, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), sourceentity);
      return retval;
   }
}
