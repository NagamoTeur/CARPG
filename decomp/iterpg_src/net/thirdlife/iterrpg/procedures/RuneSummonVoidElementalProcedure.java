package net.thirdlife.iterrpg.procedures;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.entity.VoidElementalEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class RuneSummonVoidElementalProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         boolean flag = false;
         double xpos = 0.0;
         double ypos = 0.0;
         double zpos = 0.0;
         double count = 0.0;
         if ((world instanceof Level _lvlx ? _lvlx.m_46472_() : Level.f_46428_) == Level.f_46430_
            || (world instanceof Level _lvl ? _lvl.m_46472_() : Level.f_46428_) == Level.f_46428_ && y <= -48.0) {
            flag = true;

            for (int index0 = 0; index0 < 6; index0++) {
               if (flag) {
                  xpos = (double)Mth.m_216271_(RandomSource.m_216327_(), -8, 8);
                  zpos = (double)Mth.m_216271_(RandomSource.m_216327_(), -8, 8);
                  ypos = -4.0;

                  for (int index1 = 0; index1 < 8; index1++) {
                     if (flag) {
                        if (world.m_8055_(new BlockPos(x + xpos, y + ypos, z + zpos)).m_60815_()
                           && world.m_46859_(new BlockPos(x + xpos, y + ypos + 1.0, z + zpos))
                           && world.m_46859_(new BlockPos(x + xpos, y + ypos + 2.0, z + zpos))) {
                           flag = false;
                           RuneCooldownLongProcedure.execute(entity);
                           if (world.m_5776_()) {
                              Minecraft.m_91087_().f_91063_.m_109113_(itemstack);
                           }

                           if (world instanceof ServerLevel _level) {
                              _level.m_8767_(
                                 (SimpleParticleType)IterRpgModParticleTypes.ELEMENTAL_VOID.get(), x + xpos, y + ypos + 1.0, z + zpos, 20, 0.8, 0.8, 0.8, 0.01
                              );
                           }

                           if (world instanceof ServerLevel) {
                              ServerLevel _level = (ServerLevel)world;
                              Entity entityToSpawn = new VoidElementalEntity((EntityType<VoidElementalEntity>)IterRpgModEntities.VOID_ELEMENTAL.get(), _level);
                              entityToSpawn.m_7678_(x + xpos, y + ypos + 1.0, z + zpos, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                              if (entityToSpawn instanceof Mob _mobToSpawn) {
                                 _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                              }

                              world.m_7967_(entityToSpawn);
                           }
                        }

                        ypos++;
                     }
                  }
               }
            }
         } else {
            RuneCooldownProcedure.execute(entity);
            if (entity instanceof Player _player && !_player.f_19853_.m_5776_()) {
               _player.m_5661_(Component.m_237113_(Component.m_237115_("iterpg.line.rune_wrong_enviroment").getString()), true);
            }
         }
      }
   }
}
