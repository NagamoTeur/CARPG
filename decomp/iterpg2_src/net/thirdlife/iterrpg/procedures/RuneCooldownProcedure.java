package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.thirdlife.iterrpg.init.IterRpgModItems;

public class RuneCooldownProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         double xpos = 0.0;
         double ypos = 0.0;
         double zpos = 0.0;
         boolean flag = false;
         if (entity instanceof Player _player) {
            _player.m_36335_().m_41524_((Item)IterRpgModItems.EARTH_RUNE.get(), 16);
         }

         if (entity instanceof Player _player) {
            _player.m_36335_().m_41524_((Item)IterRpgModItems.WATER_RUNE.get(), 16);
         }

         if (entity instanceof Player _player) {
            _player.m_36335_().m_41524_((Item)IterRpgModItems.AIR_RUNE.get(), 16);
         }

         if (entity instanceof Player _player) {
            _player.m_36335_().m_41524_((Item)IterRpgModItems.FIRE_RUNE.get(), 16);
         }

         if (entity instanceof Player _player) {
            _player.m_36335_().m_41524_((Item)IterRpgModItems.VOID_RUNE.get(), 16);
         }
      }
   }
}
