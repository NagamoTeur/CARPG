package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.api.potion.PotionData;
import com.hollingsworth.arsnouveau.common.block.tile.PotionJarTile;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class PotionJar extends ModBlock implements SimpleWaterloggedBlock, EntityBlock {
   VoxelShape shape = Stream.of(
         Block.m_49796_(2.0, 0.0, 2.0, 14.0, 2.0, 14.0),
         Block.m_49796_(3.0, 2.0, 3.0, 13.0, 9.0, 13.0),
         Block.m_49796_(5.0, 9.0, 5.0, 11.0, 14.0, 11.0),
         Block.m_49796_(6.0, 13.0, 6.0, 10.0, 16.0, 10.0)
      )
      .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
      .get();

   public PotionJar(Properties properties) {
      super(properties);
      this.m_49959_((BlockState)this.m_49966_().m_61124_(BlockStateProperties.f_61362_, false));
   }

   public boolean m_7278_(BlockState state) {
      return true;
   }

   public int m_6782_(BlockState blockState, Level worldIn, BlockPos pos) {
      PotionJarTile tile = (PotionJarTile)worldIn.m_7702_(pos);
      if (tile != null && tile.getAmount() > 0) {
         int step = (tile.getMaxFill() - 1) / 14;
         return (tile.getAmount() - 1) / step + 1;
      } else {
         return 0;
      }
   }

   public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      if (worldIn.f_46443_) {
         return super.m_6227_(state, worldIn, pos, player, handIn, hit);
      } else {
         PotionJarTile tile = (PotionJarTile)worldIn.m_7702_(pos);
         if (tile == null) {
            return super.m_6227_(state, worldIn, pos, player, handIn, hit);
         } else {
            ItemStack stack = player.m_21120_(handIn);
            Potion potion = PotionUtils.m_43579_(stack);
            if (stack.m_41720_() == Items.f_42589_ && potion != Potions.f_43598_) {
               if (tile.canAccept(new PotionData(stack), 100)) {
                  tile.add(new PotionData(stack), 100);
                  if (!player.m_7500_()) {
                     stack.m_41774_(1);
                     player.m_36356_(new ItemStack(Items.f_42590_));
                  }
               }

               return super.m_6227_(state, worldIn, pos, player, handIn, hit);
            } else {
               if (stack.m_41720_() == Items.f_42590_ && tile.getAmount() >= 100) {
                  ItemStack potionStack = new ItemStack(Items.f_42589_);
                  PotionUtils.m_43549_(potionStack, tile.getData().getPotion());
                  PotionUtils.m_43552_(potionStack, tile.getData().getCustomEffects());
                  if (player.m_36356_(potionStack)) {
                     player.m_21120_(handIn).m_41774_(1);
                     tile.remove(100);
                  }
               } else if (stack.m_41720_() == Items.f_42412_ && tile.getAmount() >= 10) {
                  ItemStack potionStack = new ItemStack(Items.f_42738_);
                  PotionUtils.m_43549_(potionStack, tile.getData().getPotion());
                  PotionUtils.m_43552_(potionStack, tile.getData().getCustomEffects());
                  if (player.m_36356_(potionStack)) {
                     player.m_21120_(handIn).m_41774_(1);
                     tile.remove(10);
                  }
               }

               return super.m_6227_(state, worldIn, pos, player, handIn, hit);
            }
         }
      }
   }

   public RenderShape m_7514_(BlockState p_149645_1_) {
      return RenderShape.MODEL;
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{SourceJar.fill});
      builder.m_61104_(new Property[]{BlockStateProperties.f_61362_});
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new PotionJarTile(pos, state);
   }

   public void m_5871_(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      super.m_5871_(stack, worldIn, tooltip, flagIn);
      if (stack.m_41783_() != null) {
         int fill = stack.m_41783_().m_128469_("BlockEntityTag").m_128451_("currentFill");
         tooltip.add(Component.m_237113_(fill * 100 / 10000 + "% full"));
         CompoundTag blockTag = stack.m_41783_().m_128469_("BlockEntityTag");
         if (blockTag.m_128441_("potionData")) {
            PotionData data = PotionData.fromTag(blockTag.m_128469_("potionData"));
            data.appendHoverText(tooltip);
         }
      }
   }

   public FluidState m_5888_(BlockState state) {
      return state.m_61143_(BlockStateProperties.f_61362_) ? Fluids.f_76193_.m_76068_(false) : Fluids.f_76191_.m_76145_();
   }

   @NotNull
   public BlockState m_5573_(BlockPlaceContext context) {
      FluidState fluidState = context.m_43725_().m_6425_(context.m_8083_());
      context.m_43725_().m_186460_(context.m_8083_(), BlockRegistry.POTION_JAR, 1);
      return (BlockState)this.m_49966_().m_61124_(BlockStateProperties.f_61362_, fluidState.m_76152_() == Fluids.f_76193_);
   }

   public void m_213897_(BlockState p_222945_, ServerLevel level, BlockPos pos, RandomSource p_222948_) {
      super.m_213897_(p_222945_, level, pos, p_222948_);
      if (level.m_7702_(pos) instanceof PotionJarTile jarTile) {
         jarTile.updateBlock();
      }
   }

   public BlockState m_7417_(BlockState stateIn, Direction side, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)stateIn.m_61143_(BlockStateProperties.f_61362_)) {
         worldIn.m_186469_(currentPos, Fluids.f_76193_, Fluids.f_76193_.m_6718_(worldIn));
      }

      return stateIn;
   }

   public VoxelShape m_5940_(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      return this.shape;
   }

   public boolean m_7357_(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
      return false;
   }
}
