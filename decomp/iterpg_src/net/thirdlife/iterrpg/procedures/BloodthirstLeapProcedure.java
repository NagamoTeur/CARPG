package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class BloodthirstLeapProcedure {
   public static void execute(Entity entity, ItemStack itemstack) {
      if (entity != null) {
         if (entity.m_20096_()) {
            if (entity instanceof Player _player) {
               _player.m_36335_().m_41524_(itemstack.m_41720_(), 64);
            }

            if (entity.m_6144_()) {
               entity.m_20256_(new Vec3(entity.m_20154_().f_82479_ * -1.5, entity.m_20154_().f_82480_ / -2.5 + 0.32, entity.m_20154_().f_82481_ * -1.5));
            } else {
               entity.m_20256_(new Vec3(entity.m_20154_().f_82479_ * 1.5, entity.m_20154_().f_82480_ / 2.5 + 0.32, entity.m_20154_().f_82481_ * 1.5));
            }
         }
      }
   }
}
