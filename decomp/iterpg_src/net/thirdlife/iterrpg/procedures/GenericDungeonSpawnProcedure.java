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

public class GenericDungeonSpawnProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      double DungeonDecide = 0.0;
      double variation = 0.0;
      double type = 0.0;
      type = (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 3);
      if (type == 2.0) {
         if (y <= 0.0) {
            DungeonDecide = (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 5);
         } else {
            DungeonDecide = (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 4);
         }

         if (DungeonDecide == 1.0) {
            if (world instanceof ServerLevel _serverworld) {
               StructureTemplate template = _serverworld.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "cave_lake"));
               if (template != null) {
                  template.m_230328_(
                     _serverworld,
                     new BlockPos(x - 6.0, y + 1.0, z - 7.0),
                     new BlockPos(x - 6.0, y + 1.0, z - 7.0),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworld.f_46441_,
                     3
                  );
               }
            }
         } else if (DungeonDecide == 2.0) {
            if (world instanceof ServerLevel _serverworldx) {
               StructureTemplate template = _serverworldx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "small_library_ruins"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldx,
                     new BlockPos(x - 4.0, y + 1.0, z - 5.0),
                     new BlockPos(x - 4.0, y + 1.0, z - 5.0),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldx.f_46441_,
                     3
                  );
               }
            }
         } else if (DungeonDecide == 3.0) {
            if (world instanceof ServerLevel _serverworldxx) {
               StructureTemplate template = _serverworldxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "simple_dungeon1"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxx,
                     new BlockPos(x - 5.0, y + 1.0, z - 1.0),
                     new BlockPos(x - 5.0, y + 1.0, z - 1.0),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxx.f_46441_,
                     3
                  );
               }
            }
         } else if (DungeonDecide == 4.0) {
            if (world instanceof ServerLevel _serverworldxxx) {
               StructureTemplate template = _serverworldxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "simple_dungeon2"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxxx,
                     new BlockPos(x - 5.0, y + 1.0, z - 1.0),
                     new BlockPos(x - 5.0, y + 1.0, z - 1.0),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxxx.f_46441_,
                     3
                  );
               }
            }
         } else if (world instanceof ServerLevel _serverworldxxxx) {
            StructureTemplate template = _serverworldxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "deepslate_cavity"));
            if (template != null) {
               template.m_230328_(
                  _serverworldxxxx,
                  new BlockPos(x - 5.0, y + 1.0, z - 5.0),
                  new BlockPos(x - 5.0, y + 1.0, z - 5.0),
                  new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                  _serverworldxxxx.f_46441_,
                  3
               );
            }
         }
      } else if (world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("ice_spikes"))
         || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("snowy_plains"))
         || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("snowy_taiga"))
         || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("snowy_taiga"))
         || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("snowy_taiga"))
         || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("cold_ocean"))
         || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("deep_cold_ocean"))
         || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("snowy_slopes"))) {
         DungeonDecide = (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 4);
         if (DungeonDecide == 1.0) {
            if (world instanceof ServerLevel _serverworldxxxxx) {
               StructureTemplate template = _serverworldxxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "ice_dungeon1"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxxxxx,
                     new BlockPos(x - 8.0, y + 1.0, z - 8.0),
                     new BlockPos(x - 8.0, y + 1.0, z - 8.0),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxxxxx.f_46441_,
                     3
                  );
               }
            }
         } else if (DungeonDecide == 2.0) {
            if (world instanceof ServerLevel _serverworldxxxxxx) {
               StructureTemplate template = _serverworldxxxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "ice_dungeon2"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxxxxxx,
                     new BlockPos(x - 8.0, y + 1.0, z - 8.0),
                     new BlockPos(x - 8.0, y + 1.0, z - 8.0),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxxxxxx.f_46441_,
                     3
                  );
               }
            }
         } else if (DungeonDecide == 3.0) {
            if (world instanceof ServerLevel _serverworldxxxxxxx) {
               StructureTemplate template = _serverworldxxxxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "ice_dungeon3"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxxxxxxx,
                     new BlockPos(x - 8.0, y + 1.0, z - 8.0),
                     new BlockPos(x - 8.0, y + 1.0, z - 8.0),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxxxxxxx.f_46441_,
                     3
                  );
               }
            }
         } else if (DungeonDecide == 4.0 && world instanceof ServerLevel _serverworldxxxxxxxx) {
            StructureTemplate template = _serverworldxxxxxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "ice_dungeon4"));
            if (template != null) {
               template.m_230328_(
                  _serverworldxxxxxxxx,
                  new BlockPos(x - 8.0, y + 1.0, z - 8.0),
                  new BlockPos(x - 8.0, y + 1.0, z - 8.0),
                  new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                  _serverworldxxxxxxxx.f_46441_,
                  3
               );
            }
         }
      } else if (world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("desert"))
         || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("desert"))
         || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("desert"))
         || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("badlands"))
         || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("badlands"))
         || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("eroded_badlands"))) {
         DungeonDecide = (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 3);
         if (DungeonDecide == 1.0) {
            if (world instanceof ServerLevel _serverworldxxxxxxxxx) {
               StructureTemplate template = _serverworldxxxxxxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "desert_dungeon1"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxxxxxxxxx,
                     new BlockPos(x - 8.0, y + 1.0, z - 8.0),
                     new BlockPos(x - 8.0, y + 1.0, z - 8.0),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxxxxxxxxx.f_46441_,
                     3
                  );
               }
            }
         } else if (DungeonDecide == 2.0) {
            if (world instanceof ServerLevel _serverworldxxxxxxxxxx) {
               StructureTemplate template = _serverworldxxxxxxxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "desert_dungeon2"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxxxxxxxxxx,
                     new BlockPos(x - 8.0, y + 1.0, z - 8.0),
                     new BlockPos(x - 8.0, y + 1.0, z - 8.0),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxxxxxxxxxx.f_46441_,
                     3
                  );
               }
            }
         } else if (DungeonDecide == 3.0 && world instanceof ServerLevel _serverworldxxxxxxxxxxx) {
            StructureTemplate template = _serverworldxxxxxxxxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "desert_dungeon3"));
            if (template != null) {
               template.m_230328_(
                  _serverworldxxxxxxxxxxx,
                  new BlockPos(x - 8.0, y + 1.0, z - 8.0),
                  new BlockPos(x - 8.0, y + 1.0, z - 8.0),
                  new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                  _serverworldxxxxxxxxxxx.f_46441_,
                  3
               );
            }
         }
      } else if (!world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("bamboo_jungle"))
         && !world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("bamboo_jungle"))
         && !world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("jungle"))
         && !world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("jungle"))
         && !world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("jungle"))
         && !world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("sparse_jungle"))
         && !world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("sparse_jungle"))) {
         DungeonDecide = (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 5);
         if (DungeonDecide == 1.0) {
            if (world instanceof ServerLevel _serverworldxxxxxxxxxxxx) {
               StructureTemplate template = _serverworldxxxxxxxxxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "generic_dungeon1"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxxxxxxxxxxxx,
                     new BlockPos(x - 23.0, y + 1.0, z - 20.0),
                     new BlockPos(x - 23.0, y + 1.0, z - 20.0),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxxxxxxxxxxxx.f_46441_,
                     3
                  );
               }
            }
         } else if (DungeonDecide == 2.0) {
            if (world instanceof ServerLevel _serverworldxxxxxxxxxxxxx) {
               StructureTemplate template = _serverworldxxxxxxxxxxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "generic_dungeon2"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxxxxxxxxxxxxx,
                     new BlockPos(x - 12.0, y + 1.0, z - 13.0),
                     new BlockPos(x - 12.0, y + 1.0, z - 13.0),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxxxxxxxxxxxxx.f_46441_,
                     3
                  );
               }
            }
         } else if (DungeonDecide == 3.0) {
            if (world instanceof ServerLevel _serverworldxxxxxxxxxxxxxx) {
               StructureTemplate template = _serverworldxxxxxxxxxxxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "generic_dungeon3"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxxxxxxxxxxxxxx,
                     new BlockPos(x - 15.0, y + 1.0, z - 17.0),
                     new BlockPos(x - 15.0, y + 1.0, z - 17.0),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxxxxxxxxxxxxxx.f_46441_,
                     3
                  );
               }
            }
         } else if (DungeonDecide == 4.0 && world instanceof ServerLevel _serverworldxxxxxxxxxxxxxxx) {
            StructureTemplate template = _serverworldxxxxxxxxxxxxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "generic_dungeon4"));
            if (template != null) {
               template.m_230328_(
                  _serverworldxxxxxxxxxxxxxxx,
                  new BlockPos(x - 12.0, y + 1.0, z - 12.0),
                  new BlockPos(x - 12.0, y + 1.0, z - 12.0),
                  new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                  _serverworldxxxxxxxxxxxxxxx.f_46441_,
                  3
               );
            }
         }
      } else if (world instanceof ServerLevel _serverworldxxxxxxxxxxxxxxxx) {
         StructureTemplate template = _serverworldxxxxxxxxxxxxxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "jungle_dungeon"));
         if (template != null) {
            template.m_230328_(
               _serverworldxxxxxxxxxxxxxxxx,
               new BlockPos(x - 8.0, y + 1.0, z - 8.0),
               new BlockPos(x - 8.0, y + 1.0, z - 8.0),
               new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
               _serverworldxxxxxxxxxxxxxxxx.f_46441_,
               3
            );
         }
      }

      world.m_7731_(new BlockPos(x, y, z), world.m_8055_(new BlockPos(x, y - 1.0, z)), 3);
   }
}
