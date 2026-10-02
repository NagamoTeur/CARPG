package com.cerbon.bosses_of_mass_destruction.block.custom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith.ObsidilithEntity;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.particle.ClientParticleBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class ObsidilithSummonBlock extends Block {
   public static final BooleanProperty eye = BlockStateProperties.f_61433_;
   protected final VoxelShape frameShape = m_49796_(0.0, 0.0, 0.0, 16.0, 13.0, 16.0);
   protected final VoxelShape eyeShape = m_49796_(4.0, 13.0, 4.0, 12.0, 16.0, 12.0);
   protected final VoxelShape frameWithEyeShape = Shapes.m_83110_(this.frameShape, this.eyeShape);

   public ObsidilithSummonBlock(Properties properties) {
      super(properties);
      this.m_49959_((BlockState)((BlockState)this.m_49965_().m_61090_()).m_61124_(eye, false));
   }

   public boolean m_7923_(@NotNull BlockState state) {
      return true;
   }

   @NotNull
   public VoxelShape m_5940_(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      return state.m_61143_(eye) ? this.frameWithEyeShape : this.frameShape;
   }

   @Nullable
   public BlockState m_5573_(@NotNull BlockPlaceContext context) {
      return (BlockState)this.m_49966_().m_61124_(eye, false);
   }

   public boolean m_7278_(@NotNull BlockState state) {
      return true;
   }

   public int m_6782_(BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
      return state.m_61143_(eye) ? 15 : 0;
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{eye});
   }

   public static void onEnderEyeUsed(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
      Level level = context.m_43725_();
      BlockPos blockPos = context.m_8083_();
      BlockState blockState = level.m_8055_(blockPos);
      if (blockState.m_60713_((Block)BMDBlocks.OBSIDILITH_SUMMON_BLOCK.get()) && !(Boolean)blockState.m_61143_(EndPortalFrameBlock.f_53043_)) {
         EventScheduler eventScheduler = BMDCapabilities.getLevelEventScheduler(level);
         if (level.f_46443_) {
            cir.setReturnValue(InteractionResult.SUCCESS);
            addSummonEntityEffects(eventScheduler, blockPos);
         } else {
            BlockState blockState2 = (BlockState)blockState.m_61124_(EndPortalFrameBlock.f_53043_, true);
            m_49897_(blockState, blockState2, level, blockPos);
            level.m_7731_(blockPos, blockState2, 2);
            context.m_43722_().m_41774_(1);
            level.m_46796_(1503, blockPos, 0);
            addSummonEntityEvent(eventScheduler, level, blockPos);
            cir.setReturnValue(InteractionResult.PASS);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   private static void addSummonEntityEffects(EventScheduler eventScheduler, BlockPos blockPos) {
      Vec3 centralPos = VecUtils.asVec3(blockPos.m_7494_()).m_82549_(VecUtils.unit.m_82490_(0.5));
      Vec3 particleVel = VecUtils.yAxis.m_82490_(-0.03);
      eventScheduler.addEvent(
         new TimedEvent(
            () -> ObsidilithSummonBlock.Particles.activateParticleFactory.build(centralPos.m_82549_(RandomUtils.randVec().m_82490_(2.0)), particleVel),
            0,
            80,
            () -> false
         )
      );
   }

   private static void addSummonEntityEvent(EventScheduler eventScheduler, Level level, BlockPos blockPos) {
      Vec3 pos = VecUtils.asVec3(blockPos).m_82549_(new Vec3(0.5, 0.0, 0.5));
      eventScheduler.addEvent(new TimedEvent(() -> {
         level.m_46597_(blockPos, Blocks.f_50016_.m_49966_());
         ObsidilithEntity obsidilithEntity = (ObsidilithEntity)((EntityType)BMDEntities.OBSIDILITH.get()).m_20615_(level);
         if (obsidilithEntity != null) {
            obsidilithEntity.m_217006_(pos.f_82479_, pos.f_82480_, pos.f_82481_);
            obsidilithEntity.m_20248_(pos.f_82479_, pos.f_82480_, pos.f_82481_);
            level.m_7967_(obsidilithEntity);
         }
      }, 100));
   }

   public static class Particles {
      public static final ClientParticleBuilder activateParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.PILLAR_RUNE.get())
         .scale(f -> (float)Math.sin((double)f.floatValue() * Math.PI) * 0.05F)
         .age(30);
   }
}
