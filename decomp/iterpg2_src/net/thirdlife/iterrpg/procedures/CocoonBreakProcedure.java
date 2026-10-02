package net.thirdlife.iterrpg.procedures;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.entity.SpiderlingEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;

public class CocoonBreakProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if ((new Object() {
                  public boolean checkGamemode(Entity _ent) {
                     if (_ent instanceof ServerPlayer _serverPlayer) {
                        return _serverPlayer.f_8941_.m_9290_() == GameType.SURVIVAL;
                     } else {
                        return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                           ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                              && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.SURVIVAL
                           : false;
                     }
                  }
               })
               .checkGamemode(entity)
            && EnchantmentHelper.m_44843_(Enchantments.f_44985_, entity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_) == 0) {
            OreExpDropProcedure.execute(world, x, y, z, entity);

            for (int index0 = 0; index0 < Mth.m_216271_(RandomSource.m_216327_(), 2, 5); index0++) {
               if (world instanceof ServerLevel) {
                  ServerLevel _level = (ServerLevel)world;
                  Entity entityToSpawn = new SpiderlingEntity((EntityType<SpiderlingEntity>)IterRpgModEntities.SPIDERLING.get(), _level);
                  entityToSpawn.m_7678_(
                     x + Mth.m_216263_(RandomSource.m_216327_(), 0.3, 0.7),
                     y + Mth.m_216263_(RandomSource.m_216327_(), 0.2, 0.8),
                     z + Mth.m_216263_(RandomSource.m_216327_(), 0.3, 0.7),
                     world.m_213780_().m_188501_() * 360.0F,
                     0.0F
                  );
                  if (entityToSpawn instanceof Mob _mobToSpawn) {
                     _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                  }

                  world.m_7967_(entityToSpawn);
               }
            }
         }
      }
   }
}
