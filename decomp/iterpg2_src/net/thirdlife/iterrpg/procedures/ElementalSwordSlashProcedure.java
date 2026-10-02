package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.entity.ElementalChargeEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;
import net.thirdlife.iterrpg.network.IterRpgModVariables;

public class ElementalSwordSlashProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         double pitch = 0.0;
         double yaw = 0.0;
         double pitch_off = 0.0;
         double yaw_off = 0.0;
         double xdec = 0.0;
         double ydec = 0.0;
         double zdec = 0.0;
         double repeat = 0.0;
         double dist = 0.0;
         double splashdmg = 0.0;
         double distabs = 0.0;
         double decide = 0.0;
         double true_pitch = 0.0;
         double iteration = 0.0;
         if (((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new IterRpgModVariables.PlayerVariables()))
               .MeleeAttackCooldown
            <= 0.0) {
            double _setval = 13.0;
            entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.MeleeAttackCooldown = _setval;
               capability.syncPlayerVariables(entity);
            });
            if (entity instanceof Player _player) {
               _player.m_36335_().m_41524_(itemstack.m_41720_(), 13);
            }

            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")),
                     SoundSource.PLAYERS,
                     0.25F,
                     2.0F
                  );
               } else {
                  _level.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.attack.sweep")),
                     SoundSource.PLAYERS,
                     0.25F,
                     2.0F,
                     false
                  );
               }
            }

            yaw = (double)entity.m_146908_();
            true_pitch = (double)entity.m_146909_();
            pitch_off = Mth.m_216263_(RandomSource.m_216327_(), -30.0, 30.0);
            yaw_off = Mth.m_216263_(RandomSource.m_216327_(), 75.0, 105.0);
            pitch = true_pitch - pitch_off;
            yaw -= yaw_off;
            repeat = 36.0;
            distabs = (Math.abs(entity.m_20154_().f_82479_) + Math.abs(entity.m_20154_().f_82481_) + 0.25) / 1.5;
            iteration = 0.0;

            for (int index0 = 0; index0 < (int)repeat; index0++) {
               dist = distabs * Mth.m_216263_(RandomSource.m_216327_(), 1.5, 2.0);
               Vec3 _center = new Vec3(
                  entity.m_20185_() - Math.sin(Math.toRadians(yaw)) * dist,
                  entity.m_20186_()
                     + 1.5
                     - (
                        Math.sin(Math.toRadians(true_pitch)) * dist * (0.25 - (iteration / repeat - 0.5) * (iteration / repeat - 0.5)) * 5.0
                           + Math.sin(Math.toRadians(pitch)) / 2.0
                     ),
                  entity.m_20189_() + Math.cos(Math.toRadians(yaw)) * dist
               );

               for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(0.75), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if ((!(entityiterator instanceof TamableAnimal _tamEnt) || !_tamEnt.m_21824_())
                     && !entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                     && entityiterator instanceof LivingEntity
                     && entity != entityiterator) {
                     entityiterator.m_6469_(DamageSource.f_19318_, 3.0F);
                  }
               }

               if (world instanceof ServerLevel _levelx) {
                  _levelx.m_8767_(
                     (SimpleParticleType)IterRpgModParticleTypes.ELEMENTAL_PARTICLE.get(),
                     entity.m_20185_() - Math.sin(Math.toRadians(yaw)) * dist,
                     entity.m_20186_()
                        + 1.5
                        - (
                           Math.sin(Math.toRadians(true_pitch)) * dist * (0.25 - (iteration / repeat - 0.5) * (iteration / repeat - 0.5)) * 5.0
                              + Math.sin(Math.toRadians(pitch)) / 2.0
                        ),
                     entity.m_20189_() + Math.cos(Math.toRadians(yaw)) * dist,
                     1,
                     0.01,
                     0.01,
                     0.01,
                     0.0032
                  );
               }

               pitch += 2.0 * (pitch_off / repeat);
               yaw += 2.0 * (yaw_off / repeat);
               iteration++;
            }

            if (world instanceof ServerLevel _serverLevelForEntitySpawn) {
               Entity _entityForSpawning = new ElementalChargeEntity(
                  (EntityType<ElementalChargeEntity>)IterRpgModEntities.ELEMENTAL_CHARGE.get(), _serverLevelForEntitySpawn
               );
               _entityForSpawning.m_7678_(x, y + 1.55, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
               _entityForSpawning.getPersistentData().m_128347_("maxAge", 128.0);
               _entityForSpawning.getPersistentData().m_128347_("damage", 5.0);
               _entityForSpawning.getPersistentData().m_128347_("velocity", 1.25);
               _entityForSpawning.getPersistentData().m_128379_("pierce", false);
               _entityForSpawning.getPersistentData().m_128347_("vx", entity.m_20154_().f_82479_);
               _entityForSpawning.getPersistentData().m_128347_("vy", entity.m_20154_().f_82480_ + 0.08);
               _entityForSpawning.getPersistentData().m_128347_("vz", entity.m_20154_().f_82481_);
               _entityForSpawning.getPersistentData().m_128359_("owner", entity.m_20149_());
               _entityForSpawning.getPersistentData().m_128379_("die", false);
               if (_entityForSpawning instanceof Mob _mobForSpawning) {
                  _mobForSpawning.m_6518_(_serverLevelForEntitySpawn, world.m_6436_(_entityForSpawning.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
               }

               world.m_7967_(_entityForSpawning);
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
               && !(new Object() {
                     public boolean checkGamemode(Entity _ent) {
                        if (_ent instanceof ServerPlayer _serverPlayer) {
                           return _serverPlayer.f_8941_.m_9290_() == GameType.SPECTATOR;
                        } else {
                           return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                              ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                                 && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.SPECTATOR
                              : false;
                        }
                     }
                  })
                  .checkGamemode(entity)
               && Mth.m_216271_(RandomSource.m_216327_(), 1, 3) == 2
               && itemstack.m_220157_(1, RandomSource.m_216327_(), null)) {
               itemstack.m_41774_(1);
               itemstack.m_41721_(0);
            }
         }
      }
   }
}
