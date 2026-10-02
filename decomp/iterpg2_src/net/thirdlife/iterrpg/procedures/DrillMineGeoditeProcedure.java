package net.thirdlife.iterrpg.procedures;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;

public class DrillMineGeoditeProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world.m_8055_(new BlockPos(x, y + 1.0, z)).m_204336_(BlockTags.create(new ResourceLocation("forge:geodites")))) {
         if ((new Object() {
            public double getValue(LevelAccessor world, BlockPos pos, String tag) {
               BlockEntity blockEntity = world.m_7702_(pos);
               return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
            }
         }).getValue(world, new BlockPos(x, y, z), "drillTime") >= GeodrillTimeConfigProcedure.execute() + (new Object() {
            public double getValue(LevelAccessor world, BlockPos pos, String tag) {
               BlockEntity blockEntity = world.m_7702_(pos);
               return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
            }
         }).getValue(world, new BlockPos(x, y, z), "TimeOffset")) {
            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.item.pickup")),
                     SoundSource.BLOCKS,
                     0.25F,
                     1.0F
                  );
               } else {
                  _level.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.item.pickup")),
                     SoundSource.BLOCKS,
                     0.25F,
                     1.0F,
                     false
                  );
               }
            }

            if (world instanceof ServerLevel _levelx) {
               _levelx.m_8767_(ParticleTypes.f_175830_, x + 0.5, y + 0.32, z + 0.5, 8, 0.16, 0.24, 0.16, 0.16);
            }

            if (!world.m_5776_()) {
               BlockPos _bp = new BlockPos(x, y, z);
               BlockEntity _blockEntity = world.m_7702_(_bp);
               BlockState _bs = world.m_8055_(_bp);
               if (_blockEntity != null) {
                  _blockEntity.getPersistentData().m_128347_("drillTime", 0.0);
               }

               if (world instanceof Level _levelx) {
                  _levelx.m_7260_(_bp, _bs, _bs, 3);
               }
            }

            if (world.m_8055_(new BlockPos(x, y + 1.0, z)).m_60734_() == IterRpgModBlocks.GEODITE.get()) {
               if (world.m_8055_(new BlockPos(x, y - 1.0, z)).m_204336_(BlockTags.create(new ResourceLocation("forge:has_inventory_drill")))) {
                  if (world instanceof ServerLevel _levelx) {
                     _levelx.m_7654_()
                        .m_129892_()
                        .m_230957_(
                           new CommandSourceStack(
                                 CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _levelx, 4, "", Component.m_237113_(""), _levelx.m_7654_(), null
                              )
                              .m_81324_(),
                           "loot insert ~ ~-1 ~ loot iter_rpg:gameplay/single_stone_geode"
                        );
                  }
               } else if (world instanceof ServerLevel _levelx) {
                  _levelx.m_7654_()
                     .m_129892_()
                     .m_230957_(
                        new CommandSourceStack(
                              CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _levelx, 4, "", Component.m_237113_(""), _levelx.m_7654_(), null
                           )
                           .m_81324_(),
                        "loot spawn ~0.5 ~-0.75 ~0.5 loot iter_rpg:gameplay/single_stone_geode"
                     );
               }
            } else if (world.m_8055_(new BlockPos(x, y + 1.0, z)).m_60734_() == IterRpgModBlocks.DEEPSLATE_GEODITE.get()) {
               if (world.m_8055_(new BlockPos(x, y - 1.0, z)).m_204336_(BlockTags.create(new ResourceLocation("forge:has_inventory_drill")))) {
                  if (world instanceof ServerLevel _levelx) {
                     _levelx.m_7654_()
                        .m_129892_()
                        .m_230957_(
                           new CommandSourceStack(
                                 CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _levelx, 4, "", Component.m_237113_(""), _levelx.m_7654_(), null
                              )
                              .m_81324_(),
                           "loot insert ~ ~-1 ~ loot iter_rpg:gameplay/single_deepslate_geode"
                        );
                  }
               } else if (world instanceof ServerLevel _levelx) {
                  _levelx.m_7654_()
                     .m_129892_()
                     .m_230957_(
                        new CommandSourceStack(
                              CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _levelx, 4, "", Component.m_237113_(""), _levelx.m_7654_(), null
                           )
                           .m_81324_(),
                        "loot spawn ~0.5 ~-0.75 ~0.5 loot iter_rpg:gameplay/single_deepslate_geode"
                     );
               }
            } else if (world.m_8055_(new BlockPos(x, y + 1.0, z)).m_60734_() == IterRpgModBlocks.NETHERRACK_GEODITE.get()) {
               if (world.m_8055_(new BlockPos(x, y - 1.0, z)).m_204336_(BlockTags.create(new ResourceLocation("forge:has_inventory_drill")))) {
                  if (world instanceof ServerLevel _levelx) {
                     _levelx.m_7654_()
                        .m_129892_()
                        .m_230957_(
                           new CommandSourceStack(
                                 CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _levelx, 4, "", Component.m_237113_(""), _levelx.m_7654_(), null
                              )
                              .m_81324_(),
                           "loot insert ~ ~-1 ~ loot iter_rpg:gameplay/single_netherrack_geode"
                        );
                  }
               } else if (world instanceof ServerLevel _levelx) {
                  _levelx.m_7654_()
                     .m_129892_()
                     .m_230957_(
                        new CommandSourceStack(
                              CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _levelx, 4, "", Component.m_237113_(""), _levelx.m_7654_(), null
                           )
                           .m_81324_(),
                        "loot spawn ~0.5 ~-0.75 ~0.5 loot iter_rpg:gameplay/single_netherrack_geode"
                     );
               }
            } else if (world.m_8055_(new BlockPos(x, y + 1.0, z)).m_60734_() == IterRpgModBlocks.BLACKSTONE_GEODITE.get()) {
               if (world.m_8055_(new BlockPos(x, y - 1.0, z)).m_204336_(BlockTags.create(new ResourceLocation("forge:has_inventory_drill")))) {
                  if (world instanceof ServerLevel _levelx) {
                     _levelx.m_7654_()
                        .m_129892_()
                        .m_230957_(
                           new CommandSourceStack(
                                 CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _levelx, 4, "", Component.m_237113_(""), _levelx.m_7654_(), null
                              )
                              .m_81324_(),
                           "loot insert ~ ~-1 ~ loot iter_rpg:gameplay/single_blackstone_geode"
                        );
                  }
               } else if (world instanceof ServerLevel _levelx) {
                  _levelx.m_7654_()
                     .m_129892_()
                     .m_230957_(
                        new CommandSourceStack(
                              CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _levelx, 4, "", Component.m_237113_(""), _levelx.m_7654_(), null
                           )
                           .m_81324_(),
                        "loot spawn ~0.5 ~-0.75 ~0.5 loot iter_rpg:gameplay/single_blackstone_geode"
                     );
               }
            } else if (world.m_8055_(new BlockPos(x, y + 1.0, z)).m_60734_() == IterRpgModBlocks.ENDSTONE_GEODITE.get()) {
               if (world.m_8055_(new BlockPos(x, y - 1.0, z)).m_204336_(BlockTags.create(new ResourceLocation("forge:has_inventory_drill")))) {
                  if (world instanceof ServerLevel _levelx) {
                     _levelx.m_7654_()
                        .m_129892_()
                        .m_230957_(
                           new CommandSourceStack(
                                 CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _levelx, 4, "", Component.m_237113_(""), _levelx.m_7654_(), null
                              )
                              .m_81324_(),
                           "loot insert ~ ~-1 ~ loot iter_rpg:gameplay/single_endstone_geode"
                        );
                  }
               } else if (world instanceof ServerLevel _levelx) {
                  _levelx.m_7654_()
                     .m_129892_()
                     .m_230957_(
                        new CommandSourceStack(
                              CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _levelx, 4, "", Component.m_237113_(""), _levelx.m_7654_(), null
                           )
                           .m_81324_(),
                        "loot spawn ~0.5 ~-0.75 ~0.5 loot iter_rpg:gameplay/single_endstone_geode"
                     );
               }
            }

            if (!world.m_5776_()) {
               BlockPos _bpx = new BlockPos(x, y, z);
               BlockEntity _blockEntityx = world.m_7702_(_bpx);
               BlockState _bsx = world.m_8055_(_bpx);
               if (_blockEntityx != null) {
                  _blockEntityx.getPersistentData().m_128347_("TimeOffset", (double)Mth.m_216271_(RandomSource.m_216327_(), -320, 320));
               }

               if (world instanceof Level _levelx) {
                  _levelx.m_7260_(_bpx, _bsx, _bsx, 3);
               }
            }
         } else {
            if (!world.m_5776_()) {
               BlockPos _bpxx = new BlockPos(x, y, z);
               BlockEntity _blockEntityxx = world.m_7702_(_bpxx);
               BlockState _bsxx = world.m_8055_(_bpxx);
               if (_blockEntityxx != null) {
                  _blockEntityxx.getPersistentData().m_128347_("drillTime", (new Object() {
                     public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                        BlockEntity blockEntity = world.m_7702_(pos);
                        return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                     }
                  }).getValue(world, new BlockPos(x, y, z), "drillTime") + 1.0);
               }

               if (world instanceof Level _levelx) {
                  _levelx.m_7260_(_bpxx, _bsxx, _bsxx, 3);
               }
            }

            if (world instanceof ServerLevel _levelx) {
               _levelx.m_8767_(ParticleTypes.f_123797_, x + 0.5, y + 0.32, z + 0.5, 1, 0.16, 0.24, 0.16, 0.025);
            }

            if (world instanceof ServerLevel _levelx) {
               _levelx.m_8767_(ParticleTypes.f_123783_, x + 0.5, y + 0.32, z + 0.5, 1, 0.16, 0.16, 0.16, 0.025);
            }
         }
      }
   }
}
