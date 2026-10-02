package dev.latvian.mods.kubejs.block.custom;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.block.BlockRightClickedEventJS;
import dev.latvian.mods.kubejs.block.KubeJSBlockProperties;
import dev.latvian.mods.kubejs.block.RandomTickCallbackJS;
import dev.latvian.mods.kubejs.block.callbacks.AfterEntityFallenOnBlockCallbackJS;
import dev.latvian.mods.kubejs.block.callbacks.BlockExplodedCallbackJS;
import dev.latvian.mods.kubejs.block.callbacks.BlockStateMirrorCallbackJS;
import dev.latvian.mods.kubejs.block.callbacks.BlockStateModifyCallbackJS;
import dev.latvian.mods.kubejs.block.callbacks.BlockStateModifyPlacementCallbackJS;
import dev.latvian.mods.kubejs.block.callbacks.BlockStateRotateCallbackJS;
import dev.latvian.mods.kubejs.block.callbacks.CanBeReplacedCallbackJS;
import dev.latvian.mods.kubejs.block.callbacks.EntityFallenOnBlockCallbackJS;
import dev.latvian.mods.kubejs.block.callbacks.EntitySteppedOnBlockCallbackJS;
import dev.latvian.mods.kubejs.block.entity.BlockEntityAttachment;
import dev.latvian.mods.kubejs.block.entity.BlockEntityJS;
import dev.latvian.mods.kubejs.core.BlockKJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BasicBlockJS extends Block implements BlockKJS, SimpleWaterloggedBlock {
   public final BlockBuilder blockBuilder;
   public final VoxelShape shape;

   public BasicBlockJS(BlockBuilder p) {
      super(p.createProperties());
      this.blockBuilder = p;
      this.shape = BlockBuilder.createShape(p.customShape);
      BlockState blockState = (BlockState)this.f_49792_.m_61090_();
      if (this.blockBuilder.defaultStateModification != null) {
         BlockStateModifyCallbackJS callbackJS = new BlockStateModifyCallbackJS(blockState);
         if (this.safeCallback(this.blockBuilder.defaultStateModification, callbackJS, "Error while creating default blockState for block " + p.id)) {
            this.m_49959_(callbackJS.getState());
         }
      } else if (this.blockBuilder.canBeWaterlogged()) {
         this.m_49959_((BlockState)blockState.m_61124_(BlockStateProperties.f_61362_, false));
      }
   }

   @Override
   public BlockBuilder kjs$getBlockBuilder() {
      return this.blockBuilder;
   }

   public MutableComponent m_49954_() {
      return this.blockBuilder.displayName != null && this.blockBuilder.formattedDisplayName
         ? Component.m_237113_("").m_7220_(this.blockBuilder.displayName)
         : super.m_49954_();
   }

   @Deprecated
   public VoxelShape m_5940_(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return this.shape;
   }

   protected void m_7926_(net.minecraft.world.level.block.state.StateDefinition.Builder<Block, BlockState> builder) {
      if (this.f_60439_ instanceof KubeJSBlockProperties kp) {
         for (Property<?> property : kp.blockBuilder.blockStateProperties) {
            builder.m_61104_(new Property[]{property});
         }

         kp.blockBuilder.blockStateProperties = Collections.unmodifiableSet(kp.blockBuilder.blockStateProperties);
      }
   }

   @Deprecated
   public FluidState m_5888_(BlockState state) {
      return state.m_61145_(BlockStateProperties.f_61362_).orElse(false) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(state);
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext context) {
      if (this.blockBuilder.placementStateModification != null) {
         BlockStateModifyPlacementCallbackJS callbackJS = new BlockStateModifyPlacementCallbackJS(context, this);
         if (this.safeCallback(
            this.blockBuilder.placementStateModification, callbackJS, "Error while modifying BlockState placement of " + this.blockBuilder.id
         )) {
            return callbackJS.getState();
         }
      }

      return !this.blockBuilder.canBeWaterlogged()
         ? this.m_49966_()
         : (BlockState)this.m_49966_().m_61124_(BlockStateProperties.f_61362_, context.m_43725_().m_6425_(context.m_8083_()).m_76152_() == Fluids.f_76193_);
   }

   public boolean m_6864_(BlockState blockState, BlockPlaceContext context) {
      if (this.blockBuilder.canBeReplacedFunction != null) {
         CanBeReplacedCallbackJS callbackJS = new CanBeReplacedCallbackJS(context, blockState);
         return this.blockBuilder.canBeReplacedFunction.test(callbackJS);
      } else {
         return super.m_6864_(blockState, context);
      }
   }

   @Deprecated
   public BlockState m_7417_(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos) {
      if (state.m_61145_(BlockStateProperties.f_61362_).orElse(false)) {
         world.m_186469_(pos, Fluids.f_76193_, Fluids.f_76193_.m_6718_(world));
      }

      return state;
   }

   public boolean m_7420_(BlockState state, BlockGetter level, BlockPos pos) {
      return this.blockBuilder.transparent || !state.m_61145_(BlockStateProperties.f_61362_).orElse(false);
   }

   @Deprecated
   public void m_213898_(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (this.blockBuilder.randomTickCallback != null) {
         RandomTickCallbackJS callback = new RandomTickCallbackJS(new BlockContainerJS(level, pos), random);
         this.safeCallback(this.blockBuilder.randomTickCallback, callback, "Error while random ticking custom block ");
      }
   }

   public boolean m_6724_(BlockState state) {
      return this.blockBuilder.randomTickCallback != null;
   }

   @Deprecated
   public VoxelShape m_5909_(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
      return this.blockBuilder.transparent ? Shapes.m_83040_() : super.m_5909_(state, level, pos, ctx);
   }

   @Deprecated
   public float m_7749_(BlockState state, BlockGetter level, BlockPos pos) {
      return this.blockBuilder.transparent ? 1.0F : super.m_7749_(state, level, pos);
   }

   @Deprecated
   public boolean m_6104_(BlockState state, BlockState state2, Direction direction) {
      return this.blockBuilder.transparent ? state2.m_60713_(this) || super.m_6104_(state, state2, direction) : super.m_6104_(state, state2, direction);
   }

   @Nullable
   private <T> boolean safeCallback(Consumer<T> consumer, T value, String errorMessage) {
      try {
         consumer.accept(value);
         return true;
      } catch (Throwable var5) {
         ScriptType.STARTUP.console.error(errorMessage, var5);
         return false;
      }
   }

   public boolean m_6044_(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
      return this.blockBuilder.canBeWaterlogged() ? super.m_6044_(blockGetter, blockPos, blockState, fluid) : false;
   }

   public boolean m_7361_(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
      return this.blockBuilder.canBeWaterlogged() ? super.m_7361_(levelAccessor, blockPos, blockState, fluidState) : false;
   }

   public ItemStack m_142598_(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
      return this.blockBuilder.canBeWaterlogged() ? super.m_142598_(levelAccessor, blockPos, blockState) : ItemStack.f_41583_;
   }

   public Optional<SoundEvent> m_142298_() {
      return this.blockBuilder.canBeWaterlogged() ? super.m_142298_() : Optional.empty();
   }

   public void m_141947_(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
      if (this.blockBuilder.stepOnCallback != null) {
         EntitySteppedOnBlockCallbackJS callbackJS = new EntitySteppedOnBlockCallbackJS(level, entity, blockPos, blockState);
         this.safeCallback(this.blockBuilder.stepOnCallback, callbackJS, "Error while an entity stepped on custom block ");
      } else {
         super.m_141947_(level, blockPos, blockState, entity);
      }
   }

   public void m_142072_(Level level, BlockState blockState, BlockPos blockPos, Entity entity, float f) {
      if (this.blockBuilder.fallOnCallback != null) {
         EntityFallenOnBlockCallbackJS callbackJS = new EntityFallenOnBlockCallbackJS(level, entity, blockPos, blockState, f);
         this.safeCallback(this.blockBuilder.fallOnCallback, callbackJS, "Error while an entity fell on custom block ");
      } else {
         super.m_142072_(level, blockState, blockPos, entity, f);
      }
   }

   public void m_5548_(BlockGetter blockGetter, Entity entity) {
      if (this.blockBuilder.afterFallenOnCallback != null) {
         AfterEntityFallenOnBlockCallbackJS callbackJS = new AfterEntityFallenOnBlockCallbackJS(blockGetter, entity);
         this.safeCallback(this.blockBuilder.afterFallenOnCallback, callbackJS, "Error while bouncing entity from custom block ");
         if (!callbackJS.hasChangedVelocity()) {
            super.m_5548_(blockGetter, entity);
         }
      } else {
         super.m_5548_(blockGetter, entity);
      }
   }

   public void m_7592_(Level level, BlockPos blockPos, Explosion explosion) {
      if (this.blockBuilder.explodedCallback != null) {
         BlockExplodedCallbackJS callbackJS = new BlockExplodedCallbackJS(level, blockPos, explosion);
         this.safeCallback(this.blockBuilder.explodedCallback, callbackJS, "Error while exploding custom block ");
      } else {
         super.m_7592_(level, blockPos, explosion);
      }
   }

   public BlockState m_6843_(BlockState blockState, Rotation rotation) {
      if (this.blockBuilder.rotateStateModification != null) {
         BlockStateRotateCallbackJS callbackJS = new BlockStateRotateCallbackJS(blockState, rotation);
         if (this.safeCallback(this.blockBuilder.rotateStateModification, callbackJS, "Error while rotating BlockState of ")) {
            return callbackJS.getState();
         }
      }

      return super.m_6843_(blockState, rotation);
   }

   public BlockState m_6943_(BlockState blockState, Mirror mirror) {
      if (this.blockBuilder.mirrorStateModification != null) {
         BlockStateMirrorCallbackJS callbackJS = new BlockStateMirrorCallbackJS(blockState, mirror);
         if (this.safeCallback(this.blockBuilder.mirrorStateModification, callbackJS, "Error while mirroring BlockState of ")) {
            return callbackJS.getState();
         }
      }

      return super.m_6943_(blockState, mirror);
   }

   public InteractionResult m_6227_(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
      if (this.blockBuilder.rightClick != null) {
         if (!level.m_5776_()) {
            this.blockBuilder.rightClick.accept(new BlockRightClickedEventJS(player, hand, pos, hit.m_82434_()));
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   public void m_6810_(BlockState state, Level level, BlockPos pos, BlockState newState, boolean bl) {
      if (!state.m_60713_(newState.m_60734_())) {
         if (level.m_7702_(pos) instanceof BlockEntityJS entity) {
            if (level instanceof ServerLevel) {
               for (BlockEntityAttachment attachment : entity.attachments) {
                  attachment.onRemove(newState);
               }
            }

            level.m_46717_(pos, this);
         }

         super.m_6810_(state, level, pos, newState, bl);
      }
   }

   public void m_6402_(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
      if (livingEntity != null && !level.m_5776_() && level.m_7702_(blockPos) instanceof BlockEntityJS e) {
         e.placerId = livingEntity.m_20148_();
      }
   }

   public static class Builder extends BlockBuilder {
      public Builder(ResourceLocation i) {
         super(i);
      }

      public Block createObject() {
         return (Block)(this.blockEntityInfo != null ? new BasicBlockJS.WithEntity(this) : new BasicBlockJS(this));
      }
   }

   public static class WithEntity extends BasicBlockJS implements EntityBlock {
      public WithEntity(BlockBuilder p) {
         super(p);
      }

      @Nullable
      public BlockEntity m_142194_(BlockPos blockPos, BlockState blockState) {
         return this.blockBuilder.blockEntityInfo.createBlockEntity(blockPos, blockState);
      }

      @Nullable
      public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
         return this.blockBuilder.blockEntityInfo.getTicker(level);
      }
   }
}
