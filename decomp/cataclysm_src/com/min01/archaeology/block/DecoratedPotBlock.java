package com.min01.archaeology.block;

import com.min01.archaeology.blockentity.DecoratedPotBlockEntity;
import com.min01.archaeology.init.ArchaelogyTags;
import com.min01.archaeology.init.ArchaeologyBlockEntityType;
import com.min01.archaeology.init.ArchaeologyParticleTypes;
import com.min01.archaeology.init.ArchaeologySounds;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DecoratedPotBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
   public static final ResourceLocation SHERDS_DYNAMIC_DROP_ID = new ResourceLocation("sherds");
   private static final VoxelShape BOUNDING_BOX = Block.m_49796_(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
   private static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.f_61374_;
   private static final BooleanProperty CRACKED = BooleanProperty.m_61465_("cracked");
   private static final BooleanProperty WATERLOGGED = BlockStateProperties.f_61362_;

   public DecoratedPotBlock(Properties properties) {
      super(properties);
      this.m_49959_(
         (BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(HORIZONTAL_FACING, Direction.NORTH))
               .m_61124_(WATERLOGGED, Boolean.FALSE))
            .m_61124_(CRACKED, Boolean.FALSE)
      );
   }

   public PushReaction m_5537_(BlockState state) {
      return PushReaction.DESTROY;
   }

   public BlockState m_7417_(
      BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPosition, BlockPos neighborPosition
   ) {
      if ((Boolean)state.m_61143_(WATERLOGGED)) {
         level.m_186469_(currentPosition, Fluids.f_76193_, Fluids.f_76193_.m_6718_(level));
      }

      return state;
   }

   public BlockState m_5573_(BlockPlaceContext context) {
      return (BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(HORIZONTAL_FACING, context.m_8125_()))
            .m_61124_(WATERLOGGED, context.m_43725_().m_6425_(context.m_8083_()).m_76152_() == Fluids.f_76193_))
         .m_61124_(CRACKED, Boolean.FALSE);
   }

   public InteractionResult m_6227_(BlockState state, Level level, BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult) {
      if (hand == InteractionHand.MAIN_HAND) {
         ItemStack stack = player.m_21120_(hand);
         InteractionResult result = this.handleWithItem(level, position, player, stack);
         return result == null ? this.handleWithoutItem(level, position, player) : result;
      } else {
         return InteractionResult.PASS;
      }
   }

   private InteractionResult handleWithoutItem(Level level, BlockPos position, Player player) {
      if (level.m_7702_(position) instanceof DecoratedPotBlockEntity potEntity) {
         level.m_5594_(null, position, (SoundEvent)ArchaeologySounds.DECORATED_POT_INSERT_FAIL.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
         potEntity.wobble(DecoratedPotBlockEntity.WobbleStyle.NEGATIVE);
         level.m_142346_(player, GameEvent.f_157792_, position);
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   @Nullable
   private InteractionResult handleWithItem(Level level, BlockPos position, Player player, ItemStack stack) {
      if (level.m_7702_(position) instanceof DecoratedPotBlockEntity potEntity) {
         if (level.m_5776_()) {
            return InteractionResult.CONSUME;
         } else {
            ItemStack potStack = potEntity.getTheItem();
            if (!stack.m_41619_() && (potStack.m_41619_() || ItemStack.m_150942_(stack, potStack) && potStack.m_41613_() < potStack.m_41741_())) {
               potEntity.wobble(DecoratedPotBlockEntity.WobbleStyle.POSITIVE);
               player.m_36246_(Stats.f_12982_.m_12902_(stack.m_41720_()));
               ItemStack newPotStack = player.m_7500_() ? new ItemStack(stack.m_41720_(), 1) : stack.m_41620_(1);
               float intensity;
               if (potEntity.m_7983_()) {
                  potEntity.setTheItem(newPotStack);
                  intensity = (float)newPotStack.m_41613_() / (float)newPotStack.m_41741_();
               } else {
                  potStack.m_41769_(1);
                  intensity = (float)potStack.m_41613_() / (float)potStack.m_41741_();
               }

               level.m_5594_(null, position, (SoundEvent)ArchaeologySounds.DECORATED_POT_INSERT.get(), SoundSource.BLOCKS, 1.0F, 0.7F + 0.5F * intensity);
               if (level instanceof ServerLevel serverLevel) {
                  serverLevel.m_8767_(
                     (SimpleParticleType)ArchaeologyParticleTypes.DUST_PLUME.get(),
                     (double)position.m_123341_() + 0.5,
                     (double)position.m_123342_() + 1.2,
                     (double)position.m_123343_() + 0.5,
                     7,
                     0.0,
                     0.0,
                     0.0,
                     0.0
                  );
               }

               potEntity.m_6596_();
               level.m_142346_(player, GameEvent.f_157792_, position);
               return InteractionResult.SUCCESS;
            } else {
               return null;
            }
         }
      } else {
         return InteractionResult.PASS;
      }
   }

   public void m_6402_(Level level, BlockPos position, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
      if (level.m_5776_()) {
         level.m_141902_(position, (BlockEntityType)ArchaeologyBlockEntityType.DECORATED_POT.get()).ifPresent(potEntity -> potEntity.setFromItem(stack));
      }
   }

   public boolean m_7357_(BlockState state, BlockGetter blockGetter, BlockPos position, PathComputationType type) {
      return false;
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter blockGetter, BlockPos position, CollisionContext context) {
      return BOUNDING_BOX;
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{HORIZONTAL_FACING, WATERLOGGED, CRACKED});
   }

   public BlockEntity m_142194_(BlockPos position, BlockState state) {
      return new DecoratedPotBlockEntity(position, state);
   }

   public void m_6810_(BlockState state, Level level, BlockPos position, BlockState newState, boolean isMoving) {
      if (!state.m_60713_(newState.m_60734_()) && level.m_7702_(position) instanceof DecoratedPotBlockEntity potEntity) {
         Containers.m_19002_(level, position, potEntity);
         level.m_46717_(position, state.m_60734_());
      }

      super.m_6810_(state, level, position, newState, isMoving);
   }

   public List<ItemStack> m_7381_(BlockState state, net.minecraft.world.level.storage.loot.LootContext.Builder builder) {
      if (builder.m_78982_(LootContextParams.f_81462_) instanceof DecoratedPotBlockEntity potEntity) {
         builder.m_78979_(SHERDS_DYNAMIC_DROP_ID, (context, stack) -> potEntity.getDecorations().sorted().map(Item::m_7968_).forEach(stack));
         List<ItemStack> drops = super.m_7381_(state, builder);
         if (!(Boolean)state.m_61143_(CRACKED) && builder.m_78982_(LootContextParams.f_81455_) instanceof Player player && this.shouldDropShards(player)) {
            boolean removed = drops.removeIf(stack -> stack.m_150930_(this.m_5456_()));
            if (removed) {
               drops.addAll(potEntity.getDecorations().sorted().map(Item::m_7968_).toList());
            }
         }

         return drops;
      } else {
         return super.m_7381_(state, builder);
      }
   }

   public void m_5707_(Level level, BlockPos position, BlockState state, Player player) {
      BlockState newState = state;
      if (this.shouldDropShards(player)) {
         newState = (BlockState)state.m_61124_(CRACKED, Boolean.TRUE);
         level.m_7731_(position, newState, 4);
      }

      super.m_5707_(level, position, newState, player);
   }

   private boolean shouldDropShards(Player player) {
      ItemStack stack = player.m_21205_();
      return stack.m_204117_(ArchaelogyTags.BREAKS_DECORATED_POTS) && stack.getEnchantmentLevel(Enchantments.f_44985_) <= 0;
   }

   public FluidState m_5888_(BlockState state) {
      return state.m_61143_(WATERLOGGED) ? Fluids.f_76193_.m_76068_(false) : Fluids.f_76191_.m_76145_();
   }

   public SoundType m_49962_(BlockState state) {
      return state.m_61143_(CRACKED) ? ArchaeologySounds.DECORATED_POT_CRACKED : ArchaeologySounds.DECORATED_POT;
   }

   public void m_5871_(ItemStack stack, @Nullable BlockGetter blockGetter, List<Component> tooltips, TooltipFlag flag) {
      super.m_5871_(stack, blockGetter, tooltips, flag);
      DecoratedPotBlockEntity.Decorations decorations = DecoratedPotBlockEntity.Decorations.load(BlockItem.m_186336_(stack));
      if (!decorations.equals(DecoratedPotBlockEntity.Decorations.EMPTY)) {
         tooltips.add(CommonComponents.f_237098_);
         Stream.of(decorations.front(), decorations.left(), decorations.right(), decorations.back())
            .forEach(item -> tooltips.add(new ItemStack(item, 1).m_41786_().m_6879_().m_130940_(ChatFormatting.GRAY)));
      }
   }

   public void m_5581_(Level level, BlockState state, BlockHitResult hitResult, Projectile projectile) {
      if (!level.m_5776_()) {
         BlockPos position = hitResult.m_82425_();
         if (projectile.m_142265_(level, position) && projectile.m_6095_().m_204039_(ArchaelogyTags.IMPACT_PROJECTILES)) {
            level.m_7731_(position, (BlockState)state.m_61124_(CRACKED, Boolean.TRUE), 4);
            level.m_46953_(position, true, projectile);
         }
      }
   }

   public ItemStack m_7397_(BlockGetter blockGetter, BlockPos position, BlockState state) {
      return blockGetter.m_7702_(position) instanceof DecoratedPotBlockEntity potEntity ? potEntity.getPotAsItem() : new ItemStack(this);
   }

   public boolean m_7278_(BlockState position) {
      return true;
   }

   public int m_6782_(BlockState state, Level level, BlockPos position) {
      return AbstractContainerMenu.m_38918_(level.m_7702_(position));
   }
}
