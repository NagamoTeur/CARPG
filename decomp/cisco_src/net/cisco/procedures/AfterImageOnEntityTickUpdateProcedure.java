package net.cisco.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class AfterImageOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         boolean CanTeleport = false;
         double XTeleport = 0.0;
         double YTeleport = 0.0;
         double ZTeleport = 0.0;
         double TeleportAttempts = 0.0;
         double Verification = 0.0;

         for (int index0 = 0; index0 < 5 && !CanTeleport; index0++) {
            XTeleport = (double)Mth.m_216271_(RandomSource.m_216327_(), -5, 5);
            YTeleport = 1.0;
            ZTeleport = (double)Mth.m_216271_(RandomSource.m_216327_(), -5, 5);

            for (int index1 = 0; index1 < 15 && !CanTeleport; index1++) {
               if (!world.m_8055_(
                        new BlockPos(
                           (entity instanceof Mob _mobEntxxxxx ? _mobEntxxxxx.m_5448_() : null).m_20185_() + XTeleport,
                           (entity instanceof Mob _mobEntxxxx ? _mobEntxxxx.m_5448_() : null).m_20186_() + YTeleport,
                           (entity instanceof Mob _mobEntxxx ? _mobEntxxx.m_5448_() : null).m_20189_() + ZTeleport
                        )
                     )
                     .m_60815_()
                  && !world.m_8055_(
                        new BlockPos(
                           (entity instanceof Mob _mobEntxx ? _mobEntxx.m_5448_() : null).m_20185_() + XTeleport,
                           (entity instanceof Mob _mobEntx ? _mobEntx.m_5448_() : null).m_20186_() + YTeleport + 1.0,
                           (entity instanceof Mob _mobEnt ? _mobEnt.m_5448_() : null).m_20189_() + ZTeleport
                        )
                     )
                     .m_60815_()) {
                  CanTeleport = true;
               } else {
                  YTeleport++;
               }
            }
         }

         if (CanTeleport) {
            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.teleport")),
                     SoundSource.NEUTRAL,
                     2.0F,
                     1.0F
                  );
               } else {
                  _level.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.teleport")),
                     SoundSource.NEUTRAL,
                     2.0F,
                     1.0F,
                     false
                  );
               }
            }

            if (world instanceof ServerLevel _levelx) {
               _levelx.m_8767_(ParticleTypes.f_123771_, x, y, z, 60, 0.5, 1.8, 0.5, 1.0);
            }

            entity.m_6021_(
               (entity instanceof Mob _mobEntxx ? _mobEntxx.m_5448_() : null).m_20185_() + XTeleport,
               (entity instanceof Mob _mobEntx ? _mobEntx.m_5448_() : null).m_20186_() + YTeleport + 1.0,
               (entity instanceof Mob _mobEnt ? _mobEnt.m_5448_() : null).m_20189_() + ZTeleport
            );
            if (entity instanceof ServerPlayer _serverPlayer) {
               _serverPlayer.f_8906_
                  .m_9774_(
                     (entity instanceof Mob _mobEntxxxxx ? _mobEntxxxxx.m_5448_() : null).m_20185_() + XTeleport,
                     (entity instanceof Mob _mobEntxxxx ? _mobEntxxxx.m_5448_() : null).m_20186_() + YTeleport + 1.0,
                     (entity instanceof Mob _mobEntxxx ? _mobEntxxx.m_5448_() : null).m_20189_() + ZTeleport,
                     entity.m_146908_(),
                     entity.m_146909_()
                  );
            }
         }
      }
   }
}
