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
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.entity.BlobEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.network.IterRpgModVariables;

public class HydraUsedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         boolean dospawn = false;
         boolean shouldtick = false;
         double distance = 0.0;
         double ypos = 0.0;
         double yfinal = 0.0;
         double zpos = 0.0;
         double xpos = 0.0;
         double decide = 0.0;
         if ((new Object() {
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
               .checkGamemode(entity)
            || ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .Mana
               >= 6.0) {
            if (entity instanceof Player _player) {
               _player.m_36335_().m_41524_(itemstack.m_41720_(), 32);
            }

            double _setval = ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .Mana
               - 8.0;
            entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.Mana = _setval;
               capability.syncPlayerVariables(entity);
            });
            if (((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .MagicCooldown
               < 32.0) {
               _setval = 32.0;
               entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                  capability.MagicCooldown = _setval;
                  capability.syncPlayerVariables(entity);
               });
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
                  .checkGamemode(entity)
               && itemstack.m_220157_(1, RandomSource.m_216327_(), null)) {
               itemstack.m_41774_(1);
               itemstack.m_41721_(0);
            }

            if (world instanceof ServerLevel _serverLevelForEntitySpawn) {
               Entity _entityForSpawning = new BlobEntity((EntityType<BlobEntity>)IterRpgModEntities.BLOB.get(), _serverLevelForEntitySpawn);
               _entityForSpawning.m_7678_(x, y + 0.9, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
               _entityForSpawning.m_20256_(new Vec3(entity.m_20154_().f_82479_ * 1.5, entity.m_20154_().f_82480_ * 1.5, entity.m_20154_().f_82481_ * 1.5));
               _entityForSpawning.getPersistentData().m_128379_("IsFriendly", true);
               _entityForSpawning.getPersistentData().m_128359_("owner", entity.m_20149_());
               _entityForSpawning.getPersistentData().m_128347_("deathtime", (double)Mth.m_216271_(RandomSource.m_216327_(), 72, 96));
               _entityForSpawning.getPersistentData().m_128347_("timer", -1.0);
               if (_entityForSpawning instanceof Mob _mobForSpawning) {
                  _mobForSpawning.m_6518_(_serverLevelForEntitySpawn, world.m_6436_(_entityForSpawning.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
               }

               world.m_7967_(_entityForSpawning);
            }
         }
      }
   }
}
