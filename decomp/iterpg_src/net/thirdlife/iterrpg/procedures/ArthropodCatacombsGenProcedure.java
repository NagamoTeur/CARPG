package net.thirdlife.iterrpg.procedures;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Map.Entry;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class ArthropodCatacombsGenProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      double DungeonDecide = 0.0;
      double variation = 0.0;
      double xoffset = 0.0;
      double zoffset = 0.0;
      double iteration = 0.0;
      double xpos = 0.0;
      double ypos = 0.0;
      double zpos = 0.0;

      for (int index0 = 0; index0 < 64; index0++) {
         xpos = (double)Mth.m_216271_(RandomSource.m_216327_(), -16, 16);
         ypos = (double)Mth.m_216271_(RandomSource.m_216327_(), -6, 6);
         zpos = (double)Mth.m_216271_(RandomSource.m_216327_(), -16, 16);
         if (world.m_8055_(new BlockPos(x + xpos, y + ypos, z + zpos)).m_60734_() == Blocks.f_50069_) {
            BlockPos _bp = new BlockPos(x + xpos, y + ypos, z + zpos);
            BlockState _bs = Blocks.f_50226_.m_49966_();
            BlockState _bso = world.m_8055_(_bp);
            UnmodifiableIterator var27 = _bso.m_61148_().entrySet().iterator();

            while (var27.hasNext()) {
               Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var27.next();
               Property _property = _bs.m_60734_().m_49965_().m_61081_(entry.getKey().m_61708_());
               if (_property != null && _bs.m_61143_(_property) != null) {
                  try {
                     _bs = (BlockState)_bs.m_61124_(_property, entry.getValue());
                  } catch (Exception var31) {
                  }
               }
            }

            world.m_7731_(_bp, _bs, 3);
         }

         if (world.m_8055_(new BlockPos(x + xpos, y + ypos, z + zpos)).m_60734_() == Blocks.f_152550_) {
            BlockPos _bp = new BlockPos(x + xpos, y + ypos, z + zpos);
            BlockState _bs = Blocks.f_152596_.m_49966_();
            BlockState _bso = world.m_8055_(_bp);
            UnmodifiableIterator var98 = _bso.m_61148_().entrySet().iterator();

            while (var98.hasNext()) {
               Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var98.next();
               Property _property = _bs.m_60734_().m_49965_().m_61081_(entry.getKey().m_61708_());
               if (_property != null && _bs.m_61143_(_property) != null) {
                  try {
                     _bs = (BlockState)_bs.m_61124_(_property, entry.getValue());
                  } catch (Exception var32) {
                  }
               }
            }

            world.m_7731_(_bp, _bs, 3);
         }
      }

      if (world instanceof ServerLevel _serverworld) {
         StructureTemplate template = _serverworld.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "arthropod_catacombs_main_room"));
         if (template != null) {
            template.m_230328_(
               _serverworld,
               new BlockPos(x - 8.0, y, z - 8.0),
               new BlockPos(x - 8.0, y, z - 8.0),
               new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
               _serverworld.f_46441_,
               3
            );
         }
      }

      iteration = 0.0;

      for (int index1 = 0; index1 < 8; index1++) {
         variation = (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 15);
         if (iteration == 0.0) {
            xoffset = 11.0;
            zoffset = -6.0;
         } else if (iteration == 1.0) {
            xoffset = 11.0;
            zoffset = 11.0;
         } else if (iteration == 2.0) {
            xoffset = -6.0;
            zoffset = 11.0;
         } else if (iteration == 3.0) {
            xoffset = -23.0;
            zoffset = 11.0;
         } else if (iteration == 4.0) {
            xoffset = -23.0;
            zoffset = -6.0;
         } else if (iteration == 5.0) {
            xoffset = -23.0;
            zoffset = -23.0;
         } else if (iteration == 6.0) {
            xoffset = -6.0;
            zoffset = -23.0;
         } else if (iteration == 7.0) {
            xoffset = 11.0;
            zoffset = -23.0;
         }

         if (variation == 1.0) {
            if (world instanceof ServerLevel _serverworldx) {
               StructureTemplate template = _serverworldx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "arthropod_catacombs_small_room_1"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldx,
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldx.f_46441_,
                     3
                  );
               }
            }
         } else if (variation == 2.0) {
            if (world instanceof ServerLevel _serverworldxx) {
               StructureTemplate template = _serverworldxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "arthropod_catacombs_small_room_2"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxx,
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxx.f_46441_,
                     3
                  );
               }
            }
         } else if (variation == 3.0) {
            if (world instanceof ServerLevel _serverworldxxx) {
               StructureTemplate template = _serverworldxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "arthropod_catacombs_small_room_3"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxxx,
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxxx.f_46441_,
                     3
                  );
               }
            }
         } else if (variation == 4.0) {
            if (world instanceof ServerLevel _serverworldxxxx) {
               StructureTemplate template = _serverworldxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "arthropod_catacombs_small_room_4"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxxxx,
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxxxx.f_46441_,
                     3
                  );
               }
            }
         } else if (variation == 5.0) {
            if (world instanceof ServerLevel _serverworldxxxxx) {
               StructureTemplate template = _serverworldxxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "arthropod_catacombs_small_room_5"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxxxxx,
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxxxxx.f_46441_,
                     3
                  );
               }
            }
         } else if (variation == 6.0) {
            if (world instanceof ServerLevel _serverworldxxxxxx) {
               StructureTemplate template = _serverworldxxxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "arthropod_catacombs_small_room_6"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxxxxxx,
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxxxxxx.f_46441_,
                     3
                  );
               }
            }
         } else if (variation == 7.0) {
            if (world instanceof ServerLevel _serverworldxxxxxxx) {
               StructureTemplate template = _serverworldxxxxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "arthropod_catacombs_small_room_7"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxxxxxxx,
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxxxxxxx.f_46441_,
                     3
                  );
               }
            }
         } else if (variation == 8.0) {
            if (world instanceof ServerLevel _serverworldxxxxxxxx) {
               StructureTemplate template = _serverworldxxxxxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "arthropod_catacombs_small_room_8"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxxxxxxxx,
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxxxxxxxx.f_46441_,
                     3
                  );
               }
            }
         } else if (variation == 9.0) {
            if (world instanceof ServerLevel _serverworldxxxxxxxxx) {
               StructureTemplate template = _serverworldxxxxxxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "arthropod_catacombs_small_room_9"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxxxxxxxxx,
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxxxxxxxxx.f_46441_,
                     3
                  );
               }
            }
         } else if (variation == 10.0) {
            if (world instanceof ServerLevel _serverworldxxxxxxxxxx) {
               StructureTemplate template = _serverworldxxxxxxxxxx.m_215082_().m_230359_(new ResourceLocation("iter_rpg", "arthropod_catacombs_small_room_10"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxxxxxxxxxx,
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxxxxxxxxxx.f_46441_,
                     3
                  );
               }
            }
         } else if (variation == 11.0) {
            if (world instanceof ServerLevel _serverworldxxxxxxxxxxx) {
               StructureTemplate template = _serverworldxxxxxxxxxxx.m_215082_()
                  .m_230359_(new ResourceLocation("iter_rpg", "arthropod_catacombs_small_room_12"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxxxxxxxxxxx,
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxxxxxxxxxxx.f_46441_,
                     3
                  );
               }
            }
         } else if (variation == 12.0) {
            if (world instanceof ServerLevel _serverworldxxxxxxxxxxxx) {
               StructureTemplate template = _serverworldxxxxxxxxxxxx.m_215082_()
                  .m_230359_(new ResourceLocation("iter_rpg", "arthropod_catacombs_small_room_12"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxxxxxxxxxxxx,
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxxxxxxxxxxxx.f_46441_,
                     3
                  );
               }
            }
         } else if (variation == 13.0) {
            if (world instanceof ServerLevel _serverworldxxxxxxxxxxxxx) {
               StructureTemplate template = _serverworldxxxxxxxxxxxxx.m_215082_()
                  .m_230359_(new ResourceLocation("iter_rpg", "arthropod_catacombs_small_room_13"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxxxxxxxxxxxxx,
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxxxxxxxxxxxxx.f_46441_,
                     3
                  );
               }
            }
         } else if (variation == 14.0) {
            if (world instanceof ServerLevel _serverworldxxxxxxxxxxxxxx) {
               StructureTemplate template = _serverworldxxxxxxxxxxxxxx.m_215082_()
                  .m_230359_(new ResourceLocation("iter_rpg", "arthropod_catacombs_small_room_14"));
               if (template != null) {
                  template.m_230328_(
                     _serverworldxxxxxxxxxxxxxx,
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                     new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                     _serverworldxxxxxxxxxxxxxx.f_46441_,
                     3
                  );
               }
            }
         } else if (variation == 15.0 && world instanceof ServerLevel _serverworldxxxxxxxxxxxxxxx) {
            StructureTemplate template = _serverworldxxxxxxxxxxxxxxx.m_215082_()
               .m_230359_(new ResourceLocation("iter_rpg", "arthropod_catacombs_small_room_15"));
            if (template != null) {
               template.m_230328_(
                  _serverworldxxxxxxxxxxxxxxx,
                  new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                  new BlockPos(x + xoffset, y + 2.0, z + zoffset),
                  new StructurePlaceSettings().m_74379_(Rotation.NONE).m_74377_(Mirror.NONE).m_74392_(false),
                  _serverworldxxxxxxxxxxxxxxx.f_46441_,
                  3
               );
            }
         }

         iteration++;
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~-1 ~1 ~-8 ~2 ~6 ~-10 iter_rpg:hivestone"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~ ~3 ~-8 ~1 ~5 ~-10 minecraft:air"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~9 ~1 ~-1 ~11 ~6 ~2 iter_rpg:hivestone"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~9 ~3 ~ ~11 ~5 ~1 minecraft:air"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~2 ~1 ~9 ~-1 ~6 ~11 iter_rpg:hivestone"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~1 ~3 ~9 ~ ~5 ~11 minecraft:air"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~-8 ~1 ~2 ~-10 ~6 ~-1 iter_rpg:hivestone"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~-8 ~3 ~1 ~-10 ~5 ~ minecraft:air"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~7 ~1 ~16 ~11 ~6 ~19 iter_rpg:hivestone"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~7 ~3 ~17 ~11 ~5 ~18 minecraft:air"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~-10 ~1 ~16 ~-6 ~6 ~19 iter_rpg:hivestone"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~-10 ~3 ~17 ~-6 ~5 ~18 minecraft:air"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~-18 ~1 ~11 ~-15 ~6 ~7 iter_rpg:hivestone"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~-17 ~3 ~11 ~-16 ~5 ~7 minecraft:air"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~-18 ~1 ~-6 ~-15 ~6 ~-10 iter_rpg:hivestone"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~-17 ~3 ~-6 ~-16 ~5 ~-10 minecraft:air"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~16 ~1 ~11 ~19 ~6 ~7 iter_rpg:hivestone"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~17 ~3 ~11 ~18 ~5 ~6 minecraft:air"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~19 ~1 ~-6 ~16 ~6 ~-10 iter_rpg:hivestone"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~18 ~3 ~-6 ~17 ~5 ~-10 minecraft:air"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~11 ~1 ~-15 ~7 ~6 ~-18 iter_rpg:hivestone"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~11 ~3 ~-16 ~7 ~5 ~-17 minecraft:air"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~-6 ~1 ~-15 ~-10 ~6 ~-18 iter_rpg:hivestone"
            );
      }

      if (world instanceof ServerLevel _level) {
         _level.m_7654_()
            .m_129892_()
            .m_230957_(
               new CommandSourceStack(CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null)
                  .m_81324_(),
               "fill ~-6 ~3 ~-16 ~-10 ~5 ~-17 minecraft:air"
            );
      }
   }
}
