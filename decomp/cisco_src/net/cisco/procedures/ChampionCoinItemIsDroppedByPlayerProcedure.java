package net.cisco.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class ChampionCoinItemIsDroppedByPlayerProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world instanceof Level _level) {
         if (!_level.m_5776_()) {
            _level.m_5594_(
               null,
               new BlockPos(x, y, z),
               (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:coinflip")),
               SoundSource.PLAYERS,
               1.0F,
               1.0F
            );
         } else {
            _level.m_7785_(
               x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:coinflip")), SoundSource.PLAYERS, 1.0F, 1.0F, false
            );
         }
      }
   }
}
