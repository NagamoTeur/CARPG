package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.lib.EntityTags;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.Nullable;

public class SourceBerryBush extends BushBlock implements BonemealableBlock {
   public static final IntegerProperty AGE = BlockStateProperties.f_61407_;
   private static final VoxelShape BUSHLING_SHAPE = Block.m_49796_(3.0, 0.0, 3.0, 13.0, 8.0, 13.0);
   private static final VoxelShape GROWING_SHAPE = Block.m_49796_(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

   public SourceBerryBush(Properties properties) {
      super(properties);
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(AGE, 0));
   }

   public ItemStack m_7397_(BlockGetter worldIn, BlockPos pos, BlockState state) {
      return new ItemStack(BlockRegistry.SOURCEBERRY_BUSH);
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      if ((Integer)state.m_61143_(AGE) == 0) {
         return BUSHLING_SHAPE;
      } else {
         return state.m_61143_(AGE) < 3 ? GROWING_SHAPE : super.m_5940_(state, worldIn, pos, context);
      }
   }

   public boolean m_6724_(BlockState state) {
      return (Integer)state.m_61143_(AGE) < 3;
   }

   public void m_213898_(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
      super.m_213898_(state, worldIn, pos, random);
      int i = (Integer)state.m_61143_(AGE);
      if (i < 3 && worldIn.m_45524_(pos.m_7494_(), 0) >= 9 && ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.m_188503_(5) == 0)) {
         worldIn.m_7731_(pos, (BlockState)state.m_61124_(AGE, i + 1), 2);
         ForgeHooks.onCropsGrowPost(worldIn, pos, state);
      }
   }

   public void m_7892_(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
      if (entityIn instanceof LivingEntity && !entityIn.m_6095_().m_204039_(EntityTags.BERRY_BLACKLIST)) {
         entityIn.m_7601_(state, new Vec3(0.8F, 0.75, 0.8F));
         if (!worldIn.f_46443_ && (Integer)state.m_61143_(AGE) > 0 && (entityIn.f_19790_ != entityIn.m_20185_() || entityIn.f_19792_ != entityIn.m_20189_())) {
            double d0 = Math.abs(entityIn.m_20185_() - entityIn.f_19790_);
            double d1 = Math.abs(entityIn.m_20189_() - entityIn.f_19792_);
            if (d0 >= 0.003F || d1 >= 0.003F) {
               entityIn.m_6469_(DamageSource.f_19325_, 1.0F);
            }
         }
      }
   }

   public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      int i = (Integer)state.m_61143_(AGE);
      boolean flag = i == 3;
      if (!flag && player.m_21120_(handIn).m_41720_() == Items.f_42499_) {
         return InteractionResult.PASS;
      } else if (i > 1) {
         int j = 1 + worldIn.f_46441_.m_188503_(2);
         m_49840_(worldIn, pos, new ItemStack(BlockRegistry.SOURCEBERRY_BUSH, j + (flag ? 1 : 0)));
         worldIn.m_5594_(null, pos, SoundEvents.f_12457_, SoundSource.BLOCKS, 1.0F, 0.8F + worldIn.f_46441_.m_188501_() * 0.4F);
         worldIn.m_7731_(pos, (BlockState)state.m_61124_(AGE, 1), 2);
         return InteractionResult.m_19078_(worldIn.f_46443_);
      } else {
         return super.m_6227_(state, worldIn, pos, player, handIn, hit);
      }
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{AGE});
   }

   public boolean m_7370_(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
      return (Integer)state.m_61143_(AGE) < 3;
   }

   public boolean m_214167_(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void m_214148_(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
      int i = Math.min(3, (Integer)state.m_61143_(AGE) + 1);
      worldIn.m_7731_(pos, (BlockState)state.m_61124_(AGE, i), 2);
   }

   @Nullable
   public BlockPathTypes getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
      return entity instanceof Starbuncle ? null : BlockPathTypes.DAMAGE_OTHER;
   }
}
