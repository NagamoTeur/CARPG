package com.cerbon.bosses_of_mass_destruction.block.custom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.MathUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.RandomUtils;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities.VecUtils;
import com.cerbon.bosses_of_mass_destruction.entity.custom.lich.LichUtils;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.particle.ClientParticleBuilder;
import com.cerbon.bosses_of_mass_destruction.particle.ParticleFactories;
import com.cerbon.bosses_of_mass_destruction.util.BMDColors;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChiseledStoneAltarBlock extends Block {
   public static final BooleanProperty lit = BlockStateProperties.f_61443_;

   public ChiseledStoneAltarBlock(Properties properties) {
      super(properties);
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(lit, false));
   }

   @Nullable
   public BlockState m_5573_(@NotNull BlockPlaceContext context) {
      return (BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(lit, false);
   }

   public boolean m_7278_(@NotNull BlockState state) {
      return true;
   }

   public int m_6782_(BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
      return state.m_61143_(lit) ? 15 : 0;
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{lit});
   }

   public void m_214162_(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
      if ((Boolean)state.m_61143_(lit)) {
         if (random.m_188503_(3) == 0) {
            ChiseledStoneAltarBlock.Particles.blueFireParticleFactory
               .build(
                  VecUtils.asVec3(pos).m_82520_(0.5, 1.0, 0.5).m_82549_(VecUtils.planeProject(RandomUtils.randVec(), VecUtils.yAxis).m_82542_(0.5, 0.5, 0.5)),
                  VecUtils.yAxis.m_82542_(0.05, 0.05, 0.05)
               );
         }
      } else {
         ChiseledStoneAltarBlock.Particles.paleSparkleParticleFactory
            .build(
               VecUtils.asVec3(pos).m_82520_(0.5, 2.0, 0.5).m_82549_(RandomUtils.randVec().m_82542_(0.5, 0.5, 0.5)),
               VecUtils.yAxis.m_82542_(-0.05, -0.05, -0.05)
            );
      }
   }

   public static class Particles {
      public static final ClientParticleBuilder paleSparkleParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.DOWNSPARKLE.get())
         .color((Function<Float, Vec3>)(f -> MathUtils.lerpVec(f, BMDColors.WHITE, BMDColors.GREY)))
         .age(20, 30)
         .colorVariation(0.1)
         .scale(f -> 0.15F - f * 0.1F);
      public static final ClientParticleBuilder blueFireParticleFactory = ParticleFactories.soulFlame()
         .color(LichUtils.blueColorFade)
         .age(30, 40)
         .colorVariation(0.5)
         .scale(f -> 0.15F - f * 0.1F);
   }
}
