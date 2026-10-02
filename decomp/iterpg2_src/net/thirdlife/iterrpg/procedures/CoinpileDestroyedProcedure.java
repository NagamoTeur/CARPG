package net.thirdlife.iterrpg.procedures;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.thirdlife.iterrpg.init.IterRpgModItems;

public class CoinpileDestroyedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
      if (entity != null) {
         if (!(new Object() {
               public boolean checkGamemode(Entity _ent) {
                  if (_ent instanceof ServerPlayer _serverPlayer) {
                     return _serverPlayer.f_8941_.m_9290_() == GameType.CREATIVE;
                  } else {
                     return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                        ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                           && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.CREATIVE
                        : false;
                  }
               }
            })
            .checkGamemode(entity)) {
            int index0 = 0;

            while (true) {
               if (index0
                  >= 4 + (blockstate.m_60734_().m_49965_().m_61081_("stage") instanceof IntegerProperty _getip2 ? (Integer)blockstate.m_61143_(_getip2) : -1)) {
                  break;
               }

               if (world instanceof Level _level && !_level.m_5776_()) {
                  ItemEntity entityToSpawn = new ItemEntity(_level, x + 0.5, y + 0.25, z + 0.5, new ItemStack((ItemLike)IterRpgModItems.COIN.get()));
                  entityToSpawn.m_32010_(Mth.m_216271_(RandomSource.m_216327_(), 9, 11));
                  _level.m_7967_(entityToSpawn);
               }

               index0++;
            }
         }
      }
   }
}
