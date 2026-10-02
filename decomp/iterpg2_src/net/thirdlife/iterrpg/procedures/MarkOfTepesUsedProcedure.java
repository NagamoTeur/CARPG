package net.thirdlife.iterrpg.procedures;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.entity.DemonspineEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.network.IterRpgModVariables;

public class MarkOfTepesUsedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         double distance = 0.0;
         double ypos = 0.0;
         double yfinal = 0.0;
         double yspawn = 0.0;
         double zpos = 0.0;
         double xpos = 0.0;
         double attack = 0.0;
         boolean dospawn = false;
         boolean shouldtick = false;
         boolean shouldspawn = false;
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
               >= 16.0) {
            if (entity instanceof Player _player) {
               _player.m_36335_().m_41524_(itemstack.m_41720_(), 160);
            }

            double _setval = ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .Mana
               - 16.0;
            entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.Mana = _setval;
               capability.syncPlayerVariables(entity);
            });
            if (((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .MagicCooldown
               < 64.0) {
               _setval = 64.0;
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

            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wart_block.break")),
                     SoundSource.PLAYERS,
                     1.0F,
                     0.666F
                  );
               } else {
                  _level.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wart_block.break")),
                     SoundSource.PLAYERS,
                     1.0F,
                     0.666F,
                     false
                  );
               }
            }

            if (entity.m_6144_()) {
               for (int index0 = 0; index0 < 16; index0++) {
                  xpos = Mth.m_216263_(RandomSource.m_216327_(), -4.0, 4.0);
                  ypos = -4.0;
                  zpos = Mth.m_216263_(RandomSource.m_216327_(), -4.0, 4.0);
                  shouldspawn = false;

                  for (int index1 = 0; index1 < 8; index1++) {
                     if (world.m_46859_(new BlockPos(x + xpos, (double)Math.round(y + ypos), z + zpos))
                        && world.m_8055_(new BlockPos(x + xpos, (double)Math.round(y + ypos - 1.0), z + zpos)).m_60815_()) {
                        yspawn = ypos;
                        shouldspawn = true;
                     }

                     ypos++;
                  }

                  if (shouldspawn && world instanceof ServerLevel _serverLevelForEntitySpawn) {
                     Entity _entityForSpawning = new DemonspineEntity(
                        (EntityType<DemonspineEntity>)IterRpgModEntities.DEMONSPINE.get(), _serverLevelForEntitySpawn
                     );
                     _entityForSpawning.m_7678_(x + xpos, (double)Math.round(y + yspawn), z + zpos, world.m_213780_().m_188501_() * 360.0F, 0.0F);
                     _entityForSpawning.getPersistentData().m_128379_("friendly", true);
                     _entityForSpawning.getPersistentData().m_128359_("owner", entity.m_20149_());
                     _entityForSpawning.getPersistentData().m_128347_("damage", 2.0);
                     _entityForSpawning.m_146922_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
                     _entityForSpawning.m_146926_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
                     _entityForSpawning.m_5618_(_entityForSpawning.m_146908_());
                     _entityForSpawning.m_5616_(_entityForSpawning.m_146908_());
                     _entityForSpawning.f_19859_ = _entityForSpawning.m_146908_();
                     _entityForSpawning.f_19860_ = _entityForSpawning.m_146909_();
                     if (_entityForSpawning instanceof LivingEntity _entity) {
                        _entity.f_20884_ = _entity.m_146908_();
                        _entity.f_20886_ = _entity.m_146908_();
                     }

                     if (_entityForSpawning instanceof Mob _mobForSpawning) {
                        _mobForSpawning.m_6518_(_serverLevelForEntitySpawn, world.m_6436_(_entityForSpawning.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                     }

                     world.m_7967_(_entityForSpawning);
                  }
               }
            } else {
               distance = 1.2;

               for (int index2 = 0; index2 < 12; index2++) {
                  dospawn = false;
                  ypos = -4.0;

                  for (int index3 = 0; index3 < 8; index3++) {
                     if (world.m_46859_(
                           new BlockPos(x + entity.m_20154_().f_82479_ * distance, (double)Math.round(y + ypos), z + entity.m_20154_().f_82481_ * distance)
                        )
                        && world.m_8055_(
                              new BlockPos(
                                 x + entity.m_20154_().f_82479_ * distance, (double)Math.round(y + ypos - 1.0), z + entity.m_20154_().f_82481_ * distance
                              )
                           )
                           .m_60815_()) {
                        yfinal = ypos;
                        dospawn = true;
                     }

                     ypos++;
                  }

                  if (dospawn && world instanceof ServerLevel _serverLevelForEntitySpawn) {
                     Entity _entityForSpawningx = new DemonspineEntity(
                        (EntityType<DemonspineEntity>)IterRpgModEntities.DEMONSPINE.get(), _serverLevelForEntitySpawn
                     );
                     _entityForSpawningx.m_7678_(
                        x + entity.m_20154_().f_82479_ * distance + distance * Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2),
                        (double)Math.round(y + yfinal),
                        z + entity.m_20154_().f_82481_ * distance + distance * Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2),
                        world.m_213780_().m_188501_() * 360.0F,
                        0.0F
                     );
                     _entityForSpawningx.getPersistentData().m_128379_("friendly", true);
                     _entityForSpawningx.getPersistentData().m_128359_("owner", entity.m_20149_());
                     _entityForSpawningx.getPersistentData().m_128347_("damage", 2.0);
                     _entityForSpawningx.m_146922_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
                     _entityForSpawningx.m_146926_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
                     _entityForSpawningx.m_5618_(_entityForSpawningx.m_146908_());
                     _entityForSpawningx.m_5616_(_entityForSpawningx.m_146908_());
                     _entityForSpawningx.f_19859_ = _entityForSpawningx.m_146908_();
                     _entityForSpawningx.f_19860_ = _entityForSpawningx.m_146909_();
                     if (_entityForSpawningx instanceof LivingEntity _entity) {
                        _entity.f_20884_ = _entity.m_146908_();
                        _entity.f_20886_ = _entity.m_146908_();
                     }

                     if (_entityForSpawningx instanceof Mob _mobForSpawning) {
                        _mobForSpawning.m_6518_(
                           _serverLevelForEntitySpawn, world.m_6436_(_entityForSpawningx.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null
                        );
                     }

                     world.m_7967_(_entityForSpawningx);
                  }

                  distance += 0.8;
               }
            }
         }
      }
   }
}
