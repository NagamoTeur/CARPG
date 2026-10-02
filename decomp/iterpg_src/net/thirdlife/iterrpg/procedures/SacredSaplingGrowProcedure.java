package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class SacredSaplingGrowProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world.m_46859_(new BlockPos(x, y + 1.0, z))
         && world.m_46859_(new BlockPos(x, y + 2.0, z))
         && world.m_46859_(new BlockPos(x, y + 3.0, z))
         && world.m_46859_(new BlockPos(x, y + 4.0, z))
         && world.m_46859_(new BlockPos(x, y + 5.0, z))
         && world.m_46859_(new BlockPos(x, y + 6.0, z))
         && Math.random() >= 0.475
         && world instanceof ServerLevel _serverworld) {
         StructureTemplate template = _serverworld.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "sacred_tree_1"));
         if (template != null) {
            template.m_230328_(
               _serverworld,
               new BlockPos(x - 2.0, y, z - 2.0),
               new BlockPos(x - 2.0, y, z - 2.0),
               new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
               _serverworld.f_46441_,
               3
            );
         }
      }
   }
}
