package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class OverworldMonsterSpawnConditionProcedure {
   public static boolean execute(LevelAccessor world) {
      return (world instanceof Level _lvl ? _lvl.m_46472_() : Level.f_46428_) == Level.f_46428_;
   }
}
