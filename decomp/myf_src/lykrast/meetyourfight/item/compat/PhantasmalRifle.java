package lykrast.meetyourfight.item.compat;

import java.util.List;
import javax.annotation.Nullable;
import lykrast.gunswithoutroses.entity.BulletEntity;
import lykrast.gunswithoutroses.item.GunItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class PhantasmalRifle extends GunItem {
   public PhantasmalRifle(Properties properties, int bonusDamage, double damageMultiplier, int fireDelay, double inaccuracy, int enchantability) {
      super(properties, bonusDamage, damageMultiplier, fireDelay, inaccuracy, enchantability);
   }

   protected void changeBullet(Level world, Player player, ItemStack gun, BulletEntity bullet, boolean bulletFree) {
      bullet.f_19794_ = true;
   }

   protected void addExtraStatsTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip) {
      tooltip.add(Component.m_237115_(this.m_5524_() + ".desc").m_130940_(ChatFormatting.GRAY));
   }
}
