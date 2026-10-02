package com.hollingsworth.arsnouveau.api.ritual;

import java.util.Comparator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public class StructureComparator implements Comparator<StructureBlockInfo> {
   BlockPos targetPos;
   BlockPos offset;

   public StructureComparator(BlockPos targetPos, BlockPos offset) {
      this.targetPos = targetPos;
      this.offset = offset;
   }

   public int compare(StructureBlockInfo o1, StructureBlockInfo o2) {
      BlockPos pos1 = this.targetPos.m_7918_(o1.f_74675_.m_123341_(), o1.f_74675_.m_123342_(), o1.f_74675_.m_123343_()).m_121955_(this.offset);
      BlockPos pos2 = this.targetPos.m_7918_(o2.f_74675_.m_123341_(), o2.f_74675_.m_123342_(), o2.f_74675_.m_123343_()).m_121955_(this.offset);
      double aDistFromMid = this.targetPos.m_203198_((double)pos1.m_123341_(), (double)pos1.m_123342_(), (double)pos1.m_123343_());
      double bDistFromMid = this.targetPos.m_203198_((double)pos2.m_123341_(), (double)pos2.m_123342_(), (double)pos2.m_123343_());
      int c = Double.compare((double)o1.f_74675_.m_123342_(), (double)o2.f_74675_.m_123342_());
      if (c == 0) {
         c = Double.compare(aDistFromMid, bDistFromMid);
      }

      return c;
   }
}
