package net.thirdlife.iterrpg.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.thirdlife.iterrpg.entity.MarrowEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;

public class MarrowItem extends ArrowItem {
   public MarrowItem() {
      super(new Properties().m_41491_(CreativeModeTab.f_40757_).m_41487_(64));
   }

   public void m_7373_(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
      super.m_7373_(itemstack, world, list, flag);
      list.add(Component.m_237115_("iterpg.desc.marrow1"));
      list.add(Component.m_237115_("iterpg.desc.marrow2"));
   }

   public AbstractArrow m_6394_(Level level, ItemStack itemstack, LivingEntity sourceEntity) {
      return new MarrowEntity((EntityType<? extends MarrowEntity>)IterRpgModEntities.MARROW.get(), sourceEntity, level);
   }
}
