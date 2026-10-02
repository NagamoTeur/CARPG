package net.thirdlife.iterrpg.procedures;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.entity.FireballProjectileEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.network.IterRpgModVariables;

public class SmolderingTomeUsedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         double distance = 0.0;
         double damage = 0.0;
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
               >= 2.0) {
            if (entity instanceof Player _player) {
               _player.m_36335_().m_41524_(itemstack.m_41720_(), 1);
            }

            for (int index0 = 0; index0 < Mth.m_216271_(RandomSource.m_216327_(), 2, 3); index0++) {
               Level projectileLevel = entity.f_19853_;
               if (!projectileLevel.m_5776_()) {
                  Projectile _entityToSpawn = (new Object() {
                        public Projectile getArrow(Level level, Entity shooter, float damage, int knockback, byte piercing) {
                           AbstractArrow entityToSpawn = new FireballProjectileEntity(
                              (EntityType<? extends FireballProjectileEntity>)IterRpgModEntities.FIREBALL_PROJECTILE.get(), level
                           );
                           entityToSpawn.m_5602_(shooter);
                           entityToSpawn.m_36781_((double)damage);
                           entityToSpawn.m_36735_(knockback);
                           entityToSpawn.m_20225_(true);
                           entityToSpawn.m_36767_(piercing);
                           return entityToSpawn;
                        }
                     })
                     .getArrow(projectileLevel, entity, 2.0F, 0, (byte)2);
                  _entityToSpawn.m_6034_(entity.m_20185_(), entity.m_20188_() - 0.1, entity.m_20189_());
                  _entityToSpawn.m_6686_(
                     entity.m_20154_().f_82479_,
                     entity.m_20154_().f_82480_,
                     entity.m_20154_().f_82481_,
                     (float)Mth.m_216263_(RandomSource.m_216327_(), 0.5, 0.9),
                     16.0F
                  );
                  projectileLevel.m_7967_(_entityToSpawn);
               }
            }

            double _setval = ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .Mana
               - 2.0;
            entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.Mana = _setval;
               capability.syncPlayerVariables(entity);
            });
            if (((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .MagicCooldown
               < 4.0) {
               _setval = 4.0;
               entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                  capability.MagicCooldown = _setval;
                  capability.syncPlayerVariables(entity);
               });
            }

            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use")),
                     SoundSource.PLAYERS,
                     0.5F,
                     (float)Mth.m_216263_(RandomSource.m_216327_(), 1.0, 2.0)
                  );
               } else {
                  _level.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.firecharge.use")),
                     SoundSource.PLAYERS,
                     0.5F,
                     (float)Mth.m_216263_(RandomSource.m_216327_(), 1.0, 2.0),
                     false
                  );
               }
            }
         }
      }
   }
}
