package com.hollingsworth.arsnouveau.common.world.saved_data;

import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class RedstoneUtil {
   public static void getArsSignal(ServerLevel serverLevel, BlockPos pPos, Direction pFacing, CallbackInfoReturnable<Integer> cir) {
      BlockPos facing = pPos.m_121945_(pFacing);
      BlockPos requestingPos = pPos.m_121945_(pFacing.m_122424_());
      Map<BlockPos, RedstoneSavedData.Entry> map = RedstoneSavedData.from(serverLevel).SIGNAL_MAP;
      RedstoneSavedData.Entry entry = map.get(pPos);
      if (entry != null) {
         cir.setReturnValue(Math.max(entry.power, (Integer)cir.getReturnValue()));
      } else if (map.containsKey(facing)) {
         cir.setReturnValue(Math.max(map.get(facing).power, (Integer)cir.getReturnValue()));
      } else if (map.containsKey(requestingPos)) {
         cir.setReturnValue(Math.max(map.get(requestingPos).power, (Integer)cir.getReturnValue()));
      }
   }
}
