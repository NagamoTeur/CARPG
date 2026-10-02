package com.cerbon.bosses_of_mass_destruction.block.custom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.block.BMDBlockEntities;
import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.entity.custom.lich.LichUtils;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.particle.ClientParticleBuilder;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class MobWardBlock extends BaseEntityBlock {
   public static final VoxelShape blockShape = m_49796_(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
   public static final VoxelShape thinBlockShape = m_49796_(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
   public static final EnumProperty<TripleBlockPart> tripleBlockPart = EnumProperty.m_61587_("triple_part", TripleBlockPart.class);

   public MobWardBlock(Properties properties) {
      super(properties);
      this.m_49959_((BlockState)((BlockState)this.m_49965_().m_61090_()).m_61124_(HorizontalDirectionalBlock.f_54117_, Direction.NORTH));
   }

   @Nullable
   public BlockEntity m_142194_(@NotNull BlockPos pos, @NotNull BlockState state) {
      return new MobWardBlockEntity(pos, state);
   }

   @NotNull
   public RenderShape m_7514_(@NotNull BlockState state) {
      return RenderShape.MODEL;
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
      return m_152132_(blockEntityType, (BlockEntityType)BMDBlockEntities.MOB_WARD.get(), ChunkCacheBlockEntity::tick);
   }

   public void m_5871_(@NotNull ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, @NotNull TooltipFlag flag) {
      tooltip.add(Component.m_237115_("item.bosses_of_mass_destruction.mob_ward.tooltip").m_130940_(ChatFormatting.DARK_GRAY));
   }

   @NotNull
   public BlockState m_7417_(
      BlockState state,
      @NotNull Direction direction,
      @NotNull BlockState newState,
      @NotNull LevelAccessor level,
      @NotNull BlockPos pos,
      @NotNull BlockPos posFrom
   ) {
      TripleBlockPart thisState = (TripleBlockPart)state.m_61143_(tripleBlockPart);
      BlockState superState = super.m_7417_(state, direction, newState, level, pos, posFrom);
      BlockState air = Blocks.f_50016_.m_49966_();
      boolean otherState = newState.m_60713_(this);
      BlockState facingState = otherState
         ? (BlockState)state.m_61124_(HorizontalDirectionalBlock.f_54117_, (Direction)newState.m_61143_(HorizontalDirectionalBlock.f_54117_))
         : air;

      return switch (thisState) {
         case BOTTOM -> {
            switch (direction) {
               case UP:
                  yield otherState && newState.m_61143_(tripleBlockPart) == TripleBlockPart.MIDDLE ? facingState : air;
               default:
                  yield superState;
            }
         }
         case MIDDLE -> {
            switch (direction) {
               case UP:
                  yield otherState && newState.m_61143_(tripleBlockPart) == TripleBlockPart.TOP ? facingState : air;
               case DOWN:
                  yield otherState && newState.m_61143_(tripleBlockPart) == TripleBlockPart.BOTTOM ? facingState : air;
               default:
                  yield superState;
            }
         }
         case TOP -> {
            switch (direction) {
               case DOWN:
                  yield otherState && newState.m_61143_(tripleBlockPart) == TripleBlockPart.MIDDLE ? facingState : air;
               default:
                  yield superState;
            }
         }
      };
   }

   public void m_5707_(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
      if (!level.f_46443_ && player.m_7500_()) {
         TripleBlockPart part = (TripleBlockPart)state.m_61143_(tripleBlockPart);
         if (part == TripleBlockPart.MIDDLE) {
            this.checkBreakPart(pos.m_7495_(), level, state, player, TripleBlockPart.BOTTOM);
            this.checkBreakPart(pos.m_7494_(), level, state, player, TripleBlockPart.TOP);
         } else if (part == TripleBlockPart.TOP) {
            this.checkBreakPart(pos.m_6625_(2), level, state, player, TripleBlockPart.BOTTOM);
            this.checkBreakPart(pos.m_7495_(), level, state, player, TripleBlockPart.MIDDLE);
         }
      }

      super.m_5707_(level, pos, state, player);
   }

   private void checkBreakPart(BlockPos pos, Level level, BlockState state, Player player, TripleBlockPart part) {
      BlockState blockState = level.m_8055_(pos);
      if (blockState.m_60734_() == state.m_60734_() && blockState.m_61143_(tripleBlockPart) == part) {
         level.m_7731_(pos, Blocks.f_50016_.m_49966_(), 35);
         level.m_5898_(player, 2001, pos, m_49956_(blockState));
      }
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext ctx) {
      BlockPos blockPos = ctx.m_8083_();
      return blockPos.m_123342_() < ctx.m_43725_().m_151558_() - 2
            && ctx.m_43725_().m_8055_(blockPos.m_7494_()).m_60629_(ctx)
            && ctx.m_43725_().m_8055_(blockPos.m_6630_(2)).m_60629_(ctx)
         ? (BlockState)((BlockState)((BlockState)this.m_49965_().m_61090_()).m_61124_(DoorBlock.f_52726_, ctx.m_8125_()))
            .m_61124_(tripleBlockPart, TripleBlockPart.BOTTOM)
         : null;
   }

   public void m_6402_(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
      level.m_7731_(pos.m_7494_(), (BlockState)state.m_61124_(tripleBlockPart, TripleBlockPart.MIDDLE), 3);
      level.m_7731_(pos.m_6630_(2), (BlockState)state.m_61124_(tripleBlockPart, TripleBlockPart.TOP), 3);
   }

   public boolean m_7898_(@NotNull BlockState state, LevelReader level, BlockPos pos) {
      BlockPos blockPos = pos.m_7495_();
      BlockState blockState = level.m_8055_(blockPos);
      return state.m_61143_(tripleBlockPart) == TripleBlockPart.BOTTOM ? blockState.m_60783_(level, blockPos, Direction.UP) : blockState.m_60713_(this);
   }

   @NotNull
   public BlockState m_6843_(BlockState state, Rotation rotation) {
      return (BlockState)state.m_61124_(HorizontalDirectionalBlock.f_54117_, rotation.m_55954_((Direction)state.m_61143_(HorizontalDirectionalBlock.f_54117_)));
   }

   @NotNull
   public BlockState m_6943_(@NotNull BlockState state, @NotNull Mirror mirror) {
      return mirror == Mirror.NONE ? state : state.m_60717_(mirror.m_54846_((Direction)state.m_61143_(DoorBlock.f_52726_)));
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{HorizontalDirectionalBlock.f_54117_, tripleBlockPart});
   }

   @NotNull
   public VoxelShape m_5940_(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      return state.m_61143_(tripleBlockPart) == TripleBlockPart.TOP ? blockShape : thinBlockShape;
   }

   public void m_214162_(BlockState state, @NotNull Level lvel, @NotNull BlockPos pos, @NotNull RandomSource random) {
      if (state.m_61143_(tripleBlockPart) == TripleBlockPart.TOP && random.m_188503_(3) == 0) {
         Vec3 vecPos = VecUtils.asVec3(pos).m_82549_(VecUtils.unit.m_82490_(0.5));
         double randomHeight = RandomUtils.randomDouble(0.25) + 0.25;
         double randomRadius = RandomUtils.randomDouble(0.1) + 0.3;
         double randomOffset = RandomUtils.randomDouble(Math.PI);
         MobWardBlock.Particles.blueFireParticleFactory
            .continuousPosition(simpleParticle -> this.calcParticlePos(vecPos, randomOffset, randomRadius, randomHeight, (double)simpleParticle.getAge() * 0.1))
            .build(this.calcParticlePos(vecPos, randomOffset, randomRadius, randomHeight, 0.0), Vec3.f_82478_);
      }
   }

   private Vec3 calcParticlePos(Vec3 vecPos, double randomOffset, double randomRadius, double randomHeight, double age) {
      return vecPos.m_82549_(new Vec3(Math.sin(age + randomOffset) * randomRadius, randomHeight + age * 0.3, Math.cos(age + randomOffset) * randomRadius));
   }

   public static void canSpawn(ServerLevel serverLevel, MutableBlockPos pos, CallbackInfoReturnable<Boolean> cir) {
      if ((Boolean)cir.getReturnValue()) {
         ChunkPos chunkPos = new ChunkPos(pos);
         BMDCapabilities.getChunkBlockCache(serverLevel)
            .ifPresent(
               capability -> {
                  for (int x = chunkPos.f_45578_ - 4; x <= chunkPos.f_45578_ + 4; x++) {
                     for (int z = chunkPos.f_45579_ - 4; z <= chunkPos.f_45579_ + 4; z++) {
                        List<BlockPos> blocks = capability.getBlocksFromChunk(new ChunkPos(x, z), (Block)BMDBlocks.MOB_WARD.get());
                        if (blocks == null) {
                           return;
                        }

                        for (BlockPos blockPos : blocks) {
                           if (Math.abs(blockPos.m_123341_() - pos.m_123341_()) < 64
                              && Math.abs(blockPos.m_123342_() - pos.m_123342_()) < 64
                              && Math.abs(blockPos.m_123343_() - pos.m_123343_()) < 64) {
                              cir.setReturnValue(false);
                              break;
                           }
                        }
                     }
                  }
               }
            );
      }
   }

   private static class Particles {
      private static final ClientParticleBuilder blueFireParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.SOUL_FLAME.get())
         .color(LichUtils.blueColorFade)
         .age(30, 40)
         .colorVariation(0.5)
         .scale(f -> 0.15F - f * 0.1F);
   }
}
