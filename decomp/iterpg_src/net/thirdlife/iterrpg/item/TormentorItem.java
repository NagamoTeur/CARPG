package net.thirdlife.iterrpg.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.thirdlife.iterrpg.procedures.TormentorChainProcedure;
import net.thirdlife.iterrpg.procedures.TormentorCooldownTickProcedure;

public class TormentorItem extends SwordItem {
   public TormentorItem() {
      super(new Tier() {
         public int m_6609_() {
            return 406;
         }

         public float m_6624_() {
            return 4.0F;
         }

         public float m_6631_() {
            return 2.0F;
         }

         public int m_6604_() {
            return 1;
         }

         public int m_6601_() {
            return 12;
         }

         public Ingredient m_6282_() {
            return Ingredient.m_151265_();
         }
      }, 3, -2.6F, new Properties().m_41491_(CreativeModeTab.f_40757_).m_41486_());
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      list.add(Component.m_237115_("item.iterpg.tormentor.desc1"));
      list.add(Component.m_237115_("item.iterpg.tormentor.desc2"));
   }

   public boolean m_7579_(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
      boolean retval = super.m_7579_(itemstack, entity, sourceentity);
      TormentorChainProcedure.execute(entity.f_19853_, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity, sourceentity);
      return retval;
   }

   public void m_6883_(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
      super.m_6883_(itemstack, world, entity, slot, selected);
      TormentorCooldownTickProcedure.execute(itemstack);
   }
}
