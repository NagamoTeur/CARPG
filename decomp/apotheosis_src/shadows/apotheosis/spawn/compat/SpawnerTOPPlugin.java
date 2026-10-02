package shadows.apotheosis.spawn.compat;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import shadows.apotheosis.spawn.modifiers.SpawnerStats;
import shadows.apotheosis.spawn.spawner.ApothSpawnerBlock;
import shadows.apotheosis.spawn.spawner.ApothSpawnerTile;
import shadows.placebo.compat.TOPCompat;
import shadows.placebo.compat.TOPCompat.Provider;

public class SpawnerTOPPlugin implements Provider {
   public static void register() {
      TOPCompat.registerProvider(new SpawnerTOPPlugin());
   }

   public void addProbeInfo(ProbeMode mode, IProbeInfo info, Player player, Level level, BlockState state, IProbeHitData hitData) {
      if (level.m_7702_(hitData.getPos()) instanceof ApothSpawnerTile spw) {
         info.mcText(ApothSpawnerBlock.concat(SpawnerStats.MIN_DELAY.name(), spw.f_59788_.f_45447_));
         info.mcText(ApothSpawnerBlock.concat(SpawnerStats.MAX_DELAY.name(), spw.f_59788_.f_45448_));
         info.mcText(ApothSpawnerBlock.concat(SpawnerStats.SPAWN_COUNT.name(), spw.f_59788_.f_45449_));
         info.mcText(ApothSpawnerBlock.concat(SpawnerStats.MAX_NEARBY_ENTITIES.name(), spw.f_59788_.f_45451_));
         info.mcText(ApothSpawnerBlock.concat(SpawnerStats.REQ_PLAYER_RANGE.name(), spw.f_59788_.f_45452_));
         info.mcText(ApothSpawnerBlock.concat(SpawnerStats.SPAWN_RANGE.name(), spw.f_59788_.f_45453_));
         if (spw.ignoresPlayers) {
            info.mcText(SpawnerStats.IGNORE_PLAYERS.name().m_130940_(ChatFormatting.DARK_GREEN));
         }

         if (spw.ignoresConditions) {
            info.mcText(SpawnerStats.IGNORE_CONDITIONS.name().m_130940_(ChatFormatting.DARK_GREEN));
         }

         if (spw.redstoneControl) {
            info.mcText(SpawnerStats.REDSTONE_CONTROL.name().m_130940_(ChatFormatting.DARK_GREEN));
         }

         if (spw.ignoresLight) {
            info.mcText(SpawnerStats.IGNORE_LIGHT.name().m_130940_(ChatFormatting.DARK_GREEN));
         }

         if (spw.hasNoAI) {
            info.mcText(SpawnerStats.NO_AI.name().m_130940_(ChatFormatting.DARK_GREEN));
         }

         if (spw.silent) {
            info.mcText(SpawnerStats.SILENT.name().m_130940_(ChatFormatting.DARK_GREEN));
         }
      }
   }
}
