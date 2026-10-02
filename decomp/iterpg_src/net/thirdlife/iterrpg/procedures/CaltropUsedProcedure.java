package net.thirdlife.iterrpg.procedures;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.entity.CaltropThrownEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;

public class CaltropUsedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         boolean dospawn = false;
         double distance = 0.0;
         double ypos = 0.0;
         double yfinal = 0.0;
         if (entity instanceof Player _player) {
            _player.m_36335_().m_41524_(itemstack.m_41720_(), 10);
         }

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
            itemstack.m_41774_(1);
         }

         if (world instanceof ServerLevel _level) {
            Entity entityToSpawn = new CaltropThrownEntity((EntityType<CaltropThrownEntity>)IterRpgModEntities.CALTROP_THROWN.get(), _level);
            entityToSpawn.m_7678_(
               x, y + 1.0, z, (float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0), (float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0)
            );
            entityToSpawn.m_5618_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
            entityToSpawn.m_5616_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
            entityToSpawn.m_20334_(entity.m_20154_().f_82479_ * 0.5, entity.m_20154_().f_82480_ * 0.5, entity.m_20154_().f_82481_ * 0.5);
            if (entityToSpawn instanceof Mob _mobToSpawn) {
               _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
            }

            world.m_7967_(entityToSpawn);
         }
      }
   }
}
