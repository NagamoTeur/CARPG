package net.thirdlife.iterrpg.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.entity.InsatiableEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.init.IterRpgModItems;

@EventBusSubscriber
public class InsatiableSummonProcedure {
   @SubscribeEvent
   public static void onEntityDeath(LivingDeathEvent event) {
      if (event != null && event.getEntity() != null) {
         execute(
            event,
            event.getEntity().f_19853_,
            event.getEntity().m_20185_(),
            event.getEntity().m_20186_(),
            event.getEntity().m_20189_(),
            event.getEntity(),
            event.getSource().m_7639_()
         );
      }
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
      execute(null, world, x, y, z, entity, sourceentity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
      if (entity != null && sourceentity != null) {
         boolean summon = false;
         boolean proceed = false;
         double chance = 0.0;
         double xrand = 0.0;
         double zrand = 0.0;
         if (entity instanceof Animal
            && sourceentity instanceof Player
            && (sourceentity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_).m_41720_() == IterRpgModItems.SACRIFICIAL_DAGGER.get()
            && (world instanceof Level _lvl ? _lvl.m_46472_() : Level.f_46428_) == Level.f_46428_
            && world.m_46861_(new BlockPos(x, y, z))
            && world.m_6443_(InsatiableEntity.class, AABB.m_165882_(new Vec3(x, y, z), 256.0, 256.0, 256.0), e -> true).isEmpty()) {
            proceed = false;
            if (world.m_6106_().m_6534_()) {
               chance = 1.0;
            } else if (world.m_6106_().m_6533_()) {
               chance = 3.0;
            } else {
               chance = 8.0;
            }

            if (Mth.m_216271_(RandomSource.m_216327_(), 1, (int)chance) == 1) {
               proceed = true;
               if (world instanceof Level _level) {
                  if (!_level.m_5776_()) {
                     _level.m_5594_(
                        null,
                        new BlockPos(x + Mth.m_216263_(RandomSource.m_216327_(), -4.0, 4.0), y, z + Mth.m_216263_(RandomSource.m_216327_(), -4.0, 4.0)),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.drowned.ambient")),
                        SoundSource.HOSTILE,
                        1.0F,
                        0.5F
                     );
                  } else {
                     _level.m_7785_(
                        x + Mth.m_216263_(RandomSource.m_216327_(), -4.0, 4.0),
                        y,
                        z + Mth.m_216263_(RandomSource.m_216327_(), -4.0, 4.0),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.drowned.ambient")),
                        SoundSource.HOSTILE,
                        1.0F,
                        0.5F,
                        false
                     );
                  }
               }

               if (world instanceof Level _levelx) {
                  if (!_levelx.m_5776_()) {
                     _levelx.m_5594_(
                        null,
                        new BlockPos(x + Mth.m_216263_(RandomSource.m_216327_(), -4.0, 4.0), y, z + Mth.m_216263_(RandomSource.m_216327_(), -4.0, 4.0)),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.drowned.ambient_water")),
                        SoundSource.HOSTILE,
                        1.0F,
                        0.8F
                     );
                  } else {
                     _levelx.m_7785_(
                        x + Mth.m_216263_(RandomSource.m_216327_(), -4.0, 4.0),
                        y,
                        z + Mth.m_216263_(RandomSource.m_216327_(), -4.0, 4.0),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.drowned.ambient_water")),
                        SoundSource.HOSTILE,
                        1.0F,
                        0.8F,
                        false
                     );
                  }
               }
            }

            if (proceed) {
               summon = false;

               for (int index0 = 0; index0 < 16; index0++) {
                  if (world.m_204166_(
                        new BlockPos(
                           x + Mth.m_216263_(RandomSource.m_216327_(), -4.0, 4.0),
                           y + Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0),
                           z + Mth.m_216263_(RandomSource.m_216327_(), -4.0, 4.0)
                        )
                     )
                     .m_203656_(TagKey.m_203882_(Registry.f_122885_, new ResourceLocation("iter_rpg:insatiable_lair")))) {
                     summon = true;
                  }
               }

               if (summon) {
                  for (int index1 = 0; index1 < 8; index1++) {
                     xrand = x + Mth.m_216263_(RandomSource.m_216327_(), -8.0, 8.0);
                     zrand = z + Mth.m_216263_(RandomSource.m_216327_(), -8.0, 8.0);
                     if (world.m_8055_(new BlockPos(xrand, (double)(world.m_6924_(Types.MOTION_BLOCKING_NO_LEAVES, (int)xrand, (int)zrand) - 1), zrand))
                        .m_60815_()) {
                        if (world instanceof ServerLevel _levelxx) {
                           Entity entityToSpawn = new InsatiableEntity((EntityType<InsatiableEntity>)IterRpgModEntities.INSATIABLE.get(), _levelxx);
                           entityToSpawn.m_7678_(
                              xrand,
                              (double)world.m_6924_(Types.MOTION_BLOCKING_NO_LEAVES, (int)xrand, (int)zrand),
                              zrand,
                              (float)Mth.m_216263_(RandomSource.m_216327_(), -100.0, 100.0),
                              (float)Mth.m_216263_(RandomSource.m_216327_(), -100.0, 100.0)
                           );
                           entityToSpawn.m_5618_((float)Mth.m_216263_(RandomSource.m_216327_(), -100.0, 100.0));
                           entityToSpawn.m_5616_((float)Mth.m_216263_(RandomSource.m_216327_(), -100.0, 100.0));
                           entityToSpawn.m_20334_(0.0, 0.0, 0.0);
                           if (entityToSpawn instanceof Mob _mobToSpawn) {
                              _mobToSpawn.m_6518_(_levelxx, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                           }

                           world.m_7967_(entityToSpawn);
                        }

                        for (int index2 = 0; index2 < 4; index2++) {
                           if (world instanceof ServerLevel _levelxx) {
                              LightningBolt entityToSpawn = (LightningBolt)EntityType.f_20465_.m_20615_(_levelxx);
                              entityToSpawn.m_20219_(
                                 Vec3.m_82539_(
                                    new BlockPos(
                                       xrand + Mth.m_216263_(RandomSource.m_216327_(), -4.0, 4.0),
                                       (double)world.m_6924_(Types.MOTION_BLOCKING_NO_LEAVES, (int)xrand, (int)zrand),
                                       zrand + Mth.m_216263_(RandomSource.m_216327_(), -4.0, 4.0)
                                    )
                                 )
                              );
                              entityToSpawn.m_20874_(true);
                              _levelxx.m_7967_(entityToSpawn);
                           }
                        }

                        summon = false;
                        break;
                     }
                  }
               }
            }
         }
      }
   }
}
