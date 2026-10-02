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

public class SorrowSpireSpawnProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      double DungeonDecide = 0.0;
      double variation = 0.0;
      if (Mth.m_216271_(RandomSource.m_216327_(), 1, 2) == 1) {
         if (world instanceof ServerLevel _serverworld) {
            StructureTemplate template = _serverworld.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "sorrow_spire_base1"));
            if (template != null) {
               template.m_230328_(
                  _serverworld,
                  new BlockPos(x - 16.0, y - 16.0, z - 16.0),
                  new BlockPos(x - 16.0, y - 16.0, z - 16.0),
                  new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                  _serverworld.f_46441_,
                  3
               );
            }
         }
      } else if (world instanceof ServerLevel _serverworldx) {
         StructureTemplate template = _serverworldx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "sorrow_spire_base2"));
         if (template != null) {
            template.m_230328_(
               _serverworldx,
               new BlockPos(x - 16.0, y - 16.0, z - 16.0),
               new BlockPos(x - 16.0, y - 16.0, z - 16.0),
               new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
               _serverworldx.f_46441_,
               3
            );
         }
      }

      if (Mth.m_216271_(RandomSource.m_216327_(), 1, 2) == 1) {
         if (world instanceof ServerLevel _serverworldxx) {
            StructureTemplate template = _serverworldxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "sorrow_spire_top1"));
            if (template != null) {
               template.m_230328_(
                  _serverworldxx,
                  new BlockPos(x - 11.0, y + 6.0, z - 11.0),
                  new BlockPos(x - 11.0, y + 6.0, z - 11.0),
                  new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                  _serverworldxx.f_46441_,
                  3
               );
            }
         }
      } else if (world instanceof ServerLevel _serverworldxxx) {
         StructureTemplate template = _serverworldxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "sorrow_spire_top2"));
         if (template != null) {
            template.m_230328_(
               _serverworldxxx,
               new BlockPos(x - 11.0, y + 6.0, z - 11.0),
               new BlockPos(x - 11.0, y + 6.0, z - 11.0),
               new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
               _serverworldxxx.f_46441_,
               3
            );
         }
      }

      if (Mth.m_216271_(RandomSource.m_216327_(), 1, 2) == 1) {
         if (world instanceof ServerLevel _serverworldxxxx) {
            StructureTemplate template = _serverworldxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "sorrow_spire_spire1"));
            if (template != null) {
               template.m_230328_(
                  _serverworldxxxx,
                  new BlockPos(x - 6.0, y + 28.0, z - 6.0),
                  new BlockPos(x - 6.0, y + 28.0, z - 6.0),
                  new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                  _serverworldxxxx.f_46441_,
                  3
               );
            }
         }
      } else if (world instanceof ServerLevel _serverworldxxxxx) {
         StructureTemplate template = _serverworldxxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "sorrow_spire_spire2"));
         if (template != null) {
            template.m_230328_(
               _serverworldxxxxx,
               new BlockPos(x - 6.0, y + 28.0, z - 6.0),
               new BlockPos(x - 6.0, y + 28.0, z - 6.0),
               new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
               _serverworldxxxxx.f_46441_,
               3
            );
         }
      }
   }
}
