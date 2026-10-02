package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class GoblinDungeonSpawnProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      double DungeonDecide = 0.0;
      double variation = 0.0;
      DungeonDecide = (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 4);
      if (DungeonDecide == 1.0) {
         if (world instanceof ServerLevel _serverworld) {
            StructureTemplate template = _serverworld.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "goblin_camp_small"));
            if (template != null) {
               template.m_230328_(
                  _serverworld,
                  new BlockPos(x - 4.0, y - 1.0, z - 4.0),
                  new BlockPos(x - 4.0, y - 1.0, z - 4.0),
                  new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                  _serverworld.f_46441_,
                  3
               );
            }
         }
      } else if (DungeonDecide == 2.0) {
         if (world instanceof ServerLevel _serverworldx) {
            StructureTemplate template = _serverworldx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "goblin_camp_medium"));
            if (template != null) {
               template.m_230328_(
                  _serverworldx,
                  new BlockPos(x - 6.0, y - 2.0, z - 6.0),
                  new BlockPos(x - 6.0, y - 2.0, z - 6.0),
                  new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                  _serverworldx.f_46441_,
                  3
               );
            }
         }
      } else if (DungeonDecide == 3.0) {
         if (world instanceof ServerLevel _serverworldxx) {
            StructureTemplate template = _serverworldxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "goblin_camp_big"));
            if (template != null) {
               template.m_230328_(
                  _serverworldxx,
                  new BlockPos(x - 8.0, y - 3.0, z - 8.0),
                  new BlockPos(x - 8.0, y - 3.0, z - 8.0),
                  new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                  _serverworldxx.f_46441_,
                  3
               );
            }
         }
      } else if (DungeonDecide == 4.0 && world instanceof ServerLevel _serverworldxxx) {
         StructureTemplate template = _serverworldxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "goblin_fortress"));
         if (template != null) {
            template.m_230328_(
               _serverworldxxx,
               new BlockPos(x - 10.0, y - 4.0, z - 10.0),
               new BlockPos(x - 10.0, y - 4.0, z - 10.0),
               new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
               _serverworldxxx.f_46441_,
               3
            );
         }
      }
   }
}
