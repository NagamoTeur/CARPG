package net.thirdlife.iterrpg.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.entity.PeeperEntity;
import net.thirdlife.iterrpg.entity.SpiderlingEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;

@EventBusSubscriber
public class PeeperSuicideProcedure {
   @SubscribeEvent
   public static void onEntityAttacked(LivingAttackEvent event) {
      Entity entity = event.getEntity();
      if (event != null && entity != null) {
         execute(event, entity.m_9236_(), entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), event.getSource().m_7639_());
      }
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
      execute(null, world, x, y, z, sourceentity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity sourceentity) {
      if (sourceentity != null) {
         if (sourceentity instanceof PeeperEntity) {
            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123759_, sourceentity.m_20185_(), sourceentity.m_20186_() + 1.0, sourceentity.m_20189_(), 8, 0.2, 0.2, 0.2, 0.025);
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123783_, sourceentity.m_20185_(), sourceentity.m_20186_() + 1.0, sourceentity.m_20189_(), 8, 0.2, 0.2, 0.2, 0.025);
            }

            for (int index0 = 0; index0 < Mth.m_216271_(RandomSource.m_216327_(), 1, 2); index0++) {
               if (world instanceof ServerLevel) {
                  ServerLevel _level = (ServerLevel)world;
                  Entity entityToSpawn = new Silverfish(EntityType.f_20523_, _level);
                  entityToSpawn.m_7678_(
                     sourceentity.m_20185_() + Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2),
                     sourceentity.m_20186_() + Mth.m_216263_(RandomSource.m_216327_(), 0.3, 0.6),
                     sourceentity.m_20189_() + Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2),
                     world.m_213780_().m_188501_() * 360.0F,
                     0.0F
                  );
                  if (entityToSpawn instanceof Mob _mobToSpawn) {
                     _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                  }

                  world.m_7967_(entityToSpawn);
               }
            }

            for (int index1 = 0; index1 < Mth.m_216271_(RandomSource.m_216327_(), 1, 2); index1++) {
               if (world instanceof ServerLevel) {
                  ServerLevel _level = (ServerLevel)world;
                  Entity entityToSpawn = new SpiderlingEntity((EntityType<SpiderlingEntity>)IterRpgModEntities.SPIDERLING.get(), _level);
                  entityToSpawn.m_7678_(
                     sourceentity.m_20185_() + Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2),
                     sourceentity.m_20186_() + Mth.m_216263_(RandomSource.m_216327_(), 0.3, 0.6),
                     sourceentity.m_20189_() + Mth.m_216263_(RandomSource.m_216327_(), -0.2, 0.2),
                     world.m_213780_().m_188501_() * 360.0F,
                     0.0F
                  );
                  if (entityToSpawn instanceof Mob _mobToSpawn) {
                     _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                  }

                  world.m_7967_(entityToSpawn);
               }
            }

            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.basalt.break")),
                     SoundSource.HOSTILE,
                     1.0F,
                     (float)Mth.m_216263_(RandomSource.m_216327_(), 1.0, 1.5)
                  );
               } else {
                  _level.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.basalt.break")),
                     SoundSource.HOSTILE,
                     1.0F,
                     (float)Mth.m_216263_(RandomSource.m_216327_(), 1.0, 1.5),
                     false
                  );
               }
            }

            if (!sourceentity.f_19853_.m_5776_()) {
               sourceentity.m_146870_();
            }
         }
      }
   }
}
