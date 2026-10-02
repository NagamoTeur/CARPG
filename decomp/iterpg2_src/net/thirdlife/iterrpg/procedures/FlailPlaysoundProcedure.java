package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class FlailPlaysoundProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world instanceof Level _level) {
         if (!_level.m_5776_()) {
            _level.m_5594_(
               null,
               new BlockPos(x, y, z),
               (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("iter_rpg:flail_strike")),
               SoundSource.PLAYERS,
               1.0F,
               (float)Mth.m_216263_(RandomSource.m_216327_(), 0.9, 1.1)
            );
         } else {
            _level.m_7785_(
               x,
               y,
               z,
               (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("iter_rpg:flail_strike")),
               SoundSource.PLAYERS,
               1.0F,
               (float)Mth.m_216263_(RandomSource.m_216327_(), 0.9, 1.1),
               false
            );
         }
      }
   }
}
