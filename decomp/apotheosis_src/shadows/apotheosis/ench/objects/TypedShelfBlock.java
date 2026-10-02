package shadows.apotheosis.ench.objects;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import shadows.apotheosis.ench.api.IEnchantingBlock;

public class TypedShelfBlock extends Block implements IEnchantingBlock {
   protected final Supplier<? extends ParticleOptions> particle;

   public TypedShelfBlock(Properties props, Supplier<? extends ParticleOptions> particle) {
      super(props);
      this.particle = particle;
   }

   @Override
   public ParticleOptions getTableParticle(BlockState state) {
      return this.particle.get();
   }

   public static class SculkShelfBlock extends TypedShelfBlock {
      public SculkShelfBlock(Properties props, Supplier<? extends ParticleOptions> particle) {
         super(props, particle);
      }

      public void m_214162_(BlockState state, Level level, BlockPos pos, RandomSource rand) {
         if (rand.m_188503_(100) == 0) {
            level.m_7785_(
               (double)pos.m_123341_(),
               (double)pos.m_123342_(),
               (double)pos.m_123343_(),
               SoundEvents.f_215740_,
               SoundSource.BLOCKS,
               2.0F,
               0.6F + rand.m_188501_() * 0.4F,
               true
            );
         }
      }
   }
}
