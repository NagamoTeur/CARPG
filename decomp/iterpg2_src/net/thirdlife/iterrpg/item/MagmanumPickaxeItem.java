package net.thirdlife.iterrpg.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.thirdlife.iterrpg.procedures.MagmanumPickaxeStrikeProcedure;

public class MagmanumPickaxeItem extends PickaxeItem {
   public MagmanumPickaxeItem() {
      super(new Tier() {
         public int m_6609_() {
            return 512;
         }

         public float m_6624_() {
            return 7.0F;
         }

         public float m_6631_() {
            return 2.0F;
         }

         public int m_6604_() {
            return 2;
         }

         public int m_6601_() {
            return 16;
         }

         public Ingredient m_6282_() {
            return Ingredient.m_151265_();
         }
      }, 1, -2.8F, new Properties().m_41491_(CreativeModeTab.f_40756_));
   }

   public InteractionResult m_6225_(UseOnContext context) {
      super.m_6225_(context);
      MagmanumPickaxeStrikeProcedure.execute(
         context.m_43725_(),
         (double)context.m_8083_().m_123341_(),
         (double)context.m_8083_().m_123342_(),
         (double)context.m_8083_().m_123343_(),
         context.m_43723_(),
         context.m_43722_()
      );
      return InteractionResult.SUCCESS;
   }
}
