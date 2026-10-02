package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.api.spell.ILightable;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ArchfruitPod extends CocoaBlock implements ILightable {
   public Supplier<Block> surviveBlock;
   protected static final VoxelShape[] EAST_AABB = new VoxelShape[]{
      Block.m_49796_(11.0, 7.0, 6.0, 15.0, 11.0, 10.0), Block.m_49796_(9.0, 5.0, 5.0, 15.0, 11.0, 11.0), Block.m_49796_(7.0, 3.0, 4.0, 15.0, 11.0, 12.0)
   };
   protected static final VoxelShape[] NORTH_AABB = new VoxelShape[]{
      Block.m_49796_(6.0, 7.0, 1.0, 10.0, 11.0, 5.0), Block.m_49796_(5.0, 5.0, 1.0, 11.0, 11.0, 7.0), Block.m_49796_(4.0, 3.0, 1.0, 12.0, 11.0, 9.0)
   };
   protected static final VoxelShape[] SOUTH_AABB = new VoxelShape[]{
      Block.m_49796_(6.0, 7.0, 11.0, 10.0, 11.0, 15.0), Block.m_49796_(5.0, 5.0, 9.0, 11.0, 11.0, 15.0), Block.m_49796_(4.0, 3.0, 7.0, 12.0, 11.0, 15.0)
   };
   protected static final VoxelShape[] WEST_AABB = new VoxelShape[]{
      Block.m_49796_(1.0, 7.0, 6.0, 5.0, 11.0, 10.0), Block.m_49796_(1.0, 5.0, 5.0, 7.0, 11.0, 11.0), Block.m_49796_(1.0, 3.0, 4.0, 9.0, 11.0, 12.0)
   };

   public ArchfruitPod(Supplier<Block> surviveBlock) {
      this(
         Properties.m_60939_(Material.f_76300_)
            .m_60977_()
            .m_60913_(0.2F, 3.0F)
            .m_60918_(SoundType.f_56736_)
            .m_60955_()
            .m_60953_(b -> (Integer)b.m_61143_(SconceBlock.LIGHT_LEVEL))
      );
      this.surviveBlock = surviveBlock;
   }

   public ArchfruitPod(Properties properties) {
      super(properties);
      this.m_49959_(
         (BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_54117_, Direction.NORTH)).m_61124_(f_51736_, 0))
            .m_61124_(SconceBlock.LIGHT_LEVEL, 0)
      );
   }

   public boolean m_7898_(BlockState pState, LevelReader pLevel, BlockPos pPos) {
      BlockState blockstate = pLevel.m_8055_(pPos.m_121945_((Direction)pState.m_61143_(f_54117_)));
      return blockstate.m_60734_() == this.surviveBlock.get();
   }

   public VoxelShape m_5940_(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      int i = (Integer)pState.m_61143_(f_51736_);
      switch ((Direction)pState.m_61143_(f_54117_)) {
         case SOUTH:
            return SOUTH_AABB[i];
         case WEST:
            return WEST_AABB[i];
         case EAST:
            return EAST_AABB[i];
         default:
            return NORTH_AABB[i];
      }
   }

   protected void m_7926_(Builder<Block, BlockState> pBuilder) {
      pBuilder.m_61104_(new Property[]{f_54117_, f_51736_, SconceBlock.LIGHT_LEVEL});
   }

   @Override
   public void onLight(HitResult rayTraceResult, Level world, LivingEntity shooter, SpellStats stats, SpellContext spellContext) {
      if (rayTraceResult instanceof BlockHitResult blockHitResult) {
         BlockState state = world.m_8055_(blockHitResult.m_82425_());
         world.m_7731_(
            blockHitResult.m_82425_(),
            (BlockState)state.m_61124_(SconceBlock.LIGHT_LEVEL, Math.min(Math.max(0, 8 - stats.getBuffCount(AugmentDampen.INSTANCE)), 8)),
            3
         );
         world.m_7260_(
            blockHitResult.m_82425_(),
            state,
            (BlockState)state.m_61124_(SconceBlock.LIGHT_LEVEL, Math.min(Math.max(0, 8 - stats.getBuffCount(AugmentDampen.INSTANCE)), 8)),
            3
         );
      }
   }
}
