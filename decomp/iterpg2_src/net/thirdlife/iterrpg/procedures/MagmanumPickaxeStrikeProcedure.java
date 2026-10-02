package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Explosion.BlockInteraction;

public class MagmanumPickaxeStrikeProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         if (world instanceof Level _level && !_level.m_5776_()) {
            _level.m_46511_(null, x + 0.5, y + 0.5, z + 0.5, 1.5F, BlockInteraction.BREAK);
         }

         if (world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123744_, x + 0.5, y + 0.5, z + 0.5, 20, 0.15, 0.15, 0.15, 0.12);
         }

         if (entity instanceof Player _player) {
            _player.m_36335_().m_41524_(itemstack.m_41720_(), 120);
         }

         if (itemstack.m_220157_(2, RandomSource.m_216327_(), null)) {
            itemstack.m_41774_(1);
            itemstack.m_41721_(0);
         }
      }
   }
}
