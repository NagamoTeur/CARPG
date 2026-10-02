package net.cisco.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class StrucuteblockfixUpdateTickProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world instanceof ServerLevel _serverworld) {
         StructureTemplate template = _serverworld.m_215082_().m_230359_(new ResourceLocation("cisco_mod", "owl_mage_tower"));
         if (template != null) {
            template.m_230328_(
               _serverworld,
               new BlockPos(x, y, z),
               new BlockPos(x, y, z),
               new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
               _serverworld.f_46441_,
               3
            );
         }
      }

      world.m_7731_(new BlockPos(x, y, z), Blocks.f_50016_.m_49966_(), 3);
   }
}
