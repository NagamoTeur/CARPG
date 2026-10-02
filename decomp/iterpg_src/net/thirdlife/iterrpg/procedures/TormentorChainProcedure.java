package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.entity.ChainHandleEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;

public class TormentorChainProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
      if (entity != null && sourceentity != null) {
         if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_).m_41784_().m_128459_("cooldown") < 1.0) {
            if (sourceentity instanceof Player _player) {
               _player.m_36335_().m_41524_((sourceentity instanceof LivingEntity _livEntx ? _livEntx.m_21205_() : ItemStack.f_41583_).m_41720_(), 256);
            }

            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.chain.break")),
                     SoundSource.PLAYERS,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.chain.break")),
                     SoundSource.PLAYERS,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }

            if (world instanceof Level _levelx) {
               if (!_levelx.m_5776_()) {
                  _levelx.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("iter_rpg:flail_strike")),
                     SoundSource.PLAYERS,
                     1.0F,
                     1.0F
                  );
               } else {
                  _levelx.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("iter_rpg:flail_strike")),
                     SoundSource.PLAYERS,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }

            if (world instanceof Level _levelxx) {
               if (!_levelxx.m_5776_()) {
                  _levelxx.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.use")),
                     SoundSource.PLAYERS,
                     0.6F,
                     3.0F
                  );
               } else {
                  _levelxx.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.use")),
                     SoundSource.PLAYERS,
                     0.6F,
                     3.0F,
                     false
                  );
               }
            }

            (sourceentity instanceof LivingEntity _livEntx ? _livEntx.m_21205_() : ItemStack.f_41583_).m_41784_().m_128347_("cooldown", 256.0);
            entity.getPersistentData().m_128347_("chainedTime", 128.0);
            if (world instanceof ServerLevel _levelxxx) {
               Entity entityToSpawn = new ChainHandleEntity((EntityType<ChainHandleEntity>)IterRpgModEntities.CHAIN_HANDLE.get(), _levelxxx);
               entityToSpawn.m_7678_(
                  x + 1.6, y, z, (float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0), (float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0)
               );
               entityToSpawn.m_5618_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
               entityToSpawn.m_5616_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
               entityToSpawn.m_20334_(0.0, -1.0, 0.0);
               if (entityToSpawn instanceof Mob _mobToSpawn) {
                  _mobToSpawn.m_6518_(_levelxxx, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
               }

               world.m_7967_(entityToSpawn);
            }

            if (world instanceof ServerLevel _levelxxx) {
               Entity entityToSpawn = new ChainHandleEntity((EntityType<ChainHandleEntity>)IterRpgModEntities.CHAIN_HANDLE.get(), _levelxxx);
               entityToSpawn.m_7678_(
                  x - 1.6, y, z, (float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0), (float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0)
               );
               entityToSpawn.m_5618_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
               entityToSpawn.m_5616_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
               entityToSpawn.m_20334_(0.0, -1.0, 0.0);
               if (entityToSpawn instanceof Mob _mobToSpawn) {
                  _mobToSpawn.m_6518_(_levelxxx, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
               }

               world.m_7967_(entityToSpawn);
            }

            if (world instanceof ServerLevel _levelxxx) {
               Entity entityToSpawn = new ChainHandleEntity((EntityType<ChainHandleEntity>)IterRpgModEntities.CHAIN_HANDLE.get(), _levelxxx);
               entityToSpawn.m_7678_(
                  x, y, z + 1.6, (float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0), (float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0)
               );
               entityToSpawn.m_5618_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
               entityToSpawn.m_5616_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
               entityToSpawn.m_20334_(0.0, -1.0, 0.0);
               if (entityToSpawn instanceof Mob _mobToSpawn) {
                  _mobToSpawn.m_6518_(_levelxxx, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
               }

               world.m_7967_(entityToSpawn);
            }

            if (world instanceof ServerLevel _levelxxx) {
               Entity entityToSpawn = new ChainHandleEntity((EntityType<ChainHandleEntity>)IterRpgModEntities.CHAIN_HANDLE.get(), _levelxxx);
               entityToSpawn.m_7678_(
                  x, y, z - 1.6, (float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0), (float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0)
               );
               entityToSpawn.m_5618_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
               entityToSpawn.m_5616_((float)Mth.m_216263_(RandomSource.m_216327_(), -360.0, 360.0));
               entityToSpawn.m_20334_(0.0, -1.0, 0.0);
               if (entityToSpawn instanceof Mob _mobToSpawn) {
                  _mobToSpawn.m_6518_(_levelxxx, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
               }

               world.m_7967_(entityToSpawn);
            }

            if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
               _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 140, 0, true, false));
            }

            entity.getPersistentData().m_128347_("brainX", entity.m_20185_());
            entity.getPersistentData().m_128347_("brainY", entity.m_20186_());
            entity.getPersistentData().m_128347_("brainZ", entity.m_20189_());
         }
      }
   }
}
