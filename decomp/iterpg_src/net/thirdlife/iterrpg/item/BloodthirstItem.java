package net.thirdlife.iterrpg.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.thirdlife.iterrpg.procedures.BloodthirstLeapProcedure;

public class BloodthirstItem extends SwordItem {
   public BloodthirstItem() {
      super(new Tier() {
         public int m_6609_() {
            return 616;
         }

         public float m_6624_() {
            return 4.0F;
         }

         public float m_6631_() {
            return 3.0F;
         }

         public int m_6604_() {
            return 1;
         }

         public int m_6601_() {
            return 20;
         }

         public Ingredient m_6282_() {
            return Ingredient.m_151265_();
         }
      }, 3, -2.4F, new Properties().m_41491_(CreativeModeTab.f_40757_).m_41486_());
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      list.add(Component.m_237115_("iterpg.desc.bloodthirst1"));
      list.add(Component.m_237115_("iterpg.desc.bloodthirst2"));
      list.add(Component.m_237115_("iterpg.desc.bloodthirst3"));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player entity, InteractionHand hand) {
      InteractionResultHolder<ItemStack> ar = super.m_7203_(world, entity, hand);
      BloodthirstLeapProcedure.execute(entity, (ItemStack)ar.m_19095_());
      return ar;
   }
}
