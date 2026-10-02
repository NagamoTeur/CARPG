package com.aizistral.enigmaticlegacy.blocks;

import com.aizistral.enigmaticlegacy.items.EndAnchor;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class BlockEndAnchor extends BaseEntityBlock {
   public static final int MIN_CHARGES = 0;
   public static final int MAX_CHARGES = 4;
   public static final IntegerProperty CHARGE = BlockStateProperties.f_61389_;
   private static final ImmutableList<Vec3i> RESPAWN_HORIZONTAL_OFFSETS = ImmutableList.of(
      new Vec3i(0, 0, -1),
      new Vec3i(-1, 0, 0),
      new Vec3i(0, 0, 1),
      new Vec3i(1, 0, 0),
      new Vec3i(-1, 0, -1),
      new Vec3i(1, 0, -1),
      new Vec3i(-1, 0, 1),
      new Vec3i(1, 0, 1)
   );
   private static final ImmutableList<Vec3i> RESPAWN_OFFSETS = new com.google.common.collect.ImmutableList.Builder()
      .addAll(RESPAWN_HORIZONTAL_OFFSETS)
      .addAll(RESPAWN_HORIZONTAL_OFFSETS.stream().map(Vec3i::m_7495_).iterator())
      .addAll(RESPAWN_HORIZONTAL_OFFSETS.stream().map(Vec3i::m_7494_).iterator())
      .add(new Vec3i(0, 1, 0))
      .build();

   public BlockEndAnchor() {
      super(
         Properties.m_60944_(Material.f_76278_, MaterialColor.f_76365_).m_60999_().m_60913_(50.0F, 1200.0F).m_60953_(state -> getScaledChargeLevel(state, 15))
      );
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(CHARGE, 0));
      DispenserBlock.m_52672_(Items.f_42584_, new OptionalDispenseItemBehavior() {
         public ItemStack m_7498_(BlockSource source, ItemStack stack) {
            Direction direction = (Direction)source.m_6414_().m_61143_(DispenserBlock.f_52659_);
            BlockPos facedPos = source.m_7961_().m_121945_(direction);
            Level level = source.m_7727_();
            BlockState facedState = level.m_8055_(facedPos);
            if (facedState.m_60734_() == BlockEndAnchor.this && BlockEndAnchor.canBeCharged(facedState)) {
               BlockEndAnchor.charge(level, facedPos, facedState);
               stack.m_41774_(1);
               this.m_123573_(true);
               return stack;
            } else {
               return super.m_7498_(source, stack);
            }
         }
      });
   }

   public InteractionResult m_6227_(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
      ItemStack stack = player.m_21120_(hand);
      if (hand == InteractionHand.MAIN_HAND && !isRespawnFuel(stack) && isRespawnFuel(player.m_21120_(InteractionHand.OFF_HAND))) {
         return InteractionResult.PASS;
      } else if (isRespawnFuel(stack) && canBeCharged(state)) {
         charge(level, pos, state);
         if (!player.m_150110_().f_35937_) {
            stack.m_41774_(1);
         }

         return InteractionResult.m_19078_(level.f_46443_);
      } else if ((Integer)state.m_61143_(CHARGE) == 0) {
         return InteractionResult.PASS;
      } else if (!canSetSpawn(level)) {
         if (!level.f_46443_) {
            this.explode(state, level, pos);
         }

         return InteractionResult.m_19078_(level.f_46443_);
      } else {
         if (!level.f_46443_) {
            ServerPlayer splayer = (ServerPlayer)player;
            if (splayer.m_8963_() != level.m_46472_() || !pos.equals(splayer.m_8961_())) {
               splayer.m_9158_(level.m_46472_(), pos, 0.0F, false, true);
               level.m_6263_(
                  null,
                  (double)pos.m_123341_() + 0.5,
                  (double)pos.m_123342_() + 0.5,
                  (double)pos.m_123343_() + 0.5,
                  SoundEvents.f_12326_,
                  SoundSource.BLOCKS,
                  1.0F,
                  1.0F
               );
               return InteractionResult.SUCCESS;
            }
         }

         return InteractionResult.CONSUME;
      }
   }

   private static boolean isRespawnFuel(ItemStack stack) {
      return stack.m_150930_(Items.f_42584_);
   }

   private static boolean canBeCharged(BlockState state) {
      return (Integer)state.m_61143_(CHARGE) < 4;
   }

   private static boolean isWaterThatWouldFlow(BlockPos pos, Level level) {
      FluidState state = level.m_6425_(pos);
      if (!state.m_205070_(FluidTags.f_13131_)) {
         return false;
      } else if (state.m_76170_()) {
         return true;
      } else {
         return (float)state.m_76186_() < 2.0F ? false : !level.m_6425_(pos.m_7495_()).m_205070_(FluidTags.f_13131_);
      }
   }

   private void explode(BlockState state, Level level, final BlockPos pos) {
      level.m_7471_(pos, false);
      boolean flag = Plane.HORIZONTAL.m_122557_().<BlockPos>map(pos::m_121945_).anyMatch(p_55854_ -> isWaterThatWouldFlow(p_55854_, level));
      final boolean flag1 = flag || level.m_6425_(pos.m_7494_()).m_205070_(FluidTags.f_13131_);
      ExplosionDamageCalculator explosiondamagecalculator = new ExplosionDamageCalculator() {
         public Optional<Float> m_6617_(Explosion p_55904_, BlockGetter p_55905_, BlockPos p_55906_, BlockState p_55907_, FluidState p_55908_) {
            return p_55906_.equals(pos) && flag1 ? Optional.of(Blocks.f_49990_.m_7325_()) : super.m_6617_(p_55904_, p_55905_, p_55906_, p_55907_, p_55908_);
         }
      };
      level.m_7703_(
         null,
         DamageSource.m_19334_(),
         explosiondamagecalculator,
         (double)pos.m_123341_() + 0.5,
         (double)pos.m_123342_() + 0.5,
         (double)pos.m_123343_() + 0.5,
         5.0F,
         true,
         BlockInteraction.DESTROY
      );
   }

   public static boolean canSetSpawn(Level level) {
      return EndAnchor.endExclusiveMode.getValue() ? level.m_46472_() == Level.f_46430_ : !EndAnchor.DIMENSION_BLACKLIST.contains(level.m_46472_().m_135782_());
   }

   public static void charge(Level level, BlockPos pos, BlockState state) {
      level.m_7731_(pos, (BlockState)state.m_61124_(CHARGE, (Integer)state.m_61143_(CHARGE) + 1), 3);
      level.m_6263_(
         null,
         (double)pos.m_123341_() + 0.5,
         (double)pos.m_123342_() + 0.5,
         (double)pos.m_123343_() + 0.5,
         SoundEvents.f_12376_,
         SoundSource.BLOCKS,
         1.0F,
         1.0F
      );
   }

   public void m_214162_(BlockState state, Level level, BlockPos pos, RandomSource random) {
      if ((Integer)state.m_61143_(CHARGE) != 0) {
         if (random.m_188503_(100) == 0) {
            level.m_6263_(
               (Player)null,
               (double)pos.m_123341_() + 0.5,
               (double)pos.m_123342_() + 0.5,
               (double)pos.m_123343_() + 0.5,
               SoundEvents.f_12375_,
               SoundSource.BLOCKS,
               1.0F,
               1.0F
            );
         }

         double d0 = (double)pos.m_123341_() + 0.1 + (random.m_188500_() - 0.1);
         double d1 = (double)pos.m_123342_() + 0.8;
         double d2 = (double)pos.m_123343_() + 0.1 + (random.m_188500_() - 0.1);
         level.m_7106_(ParticleTypes.f_123762_, d0, d1, d2, 0.0, 0.0, 0.0);
      }
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{CHARGE});
   }

   public boolean m_7278_(BlockState state) {
      return true;
   }

   public static int getScaledChargeLevel(BlockState state, int scale) {
      return Mth.m_14143_((float)((Integer)state.m_61143_(CHARGE) - 0) / 4.0F * (float)scale);
   }

   public int m_6782_(BlockState state, Level level, BlockPos pos) {
      return getScaledChargeLevel(state, 15);
   }

   public static Optional<Vec3> findStandUpPosition(EntityType<?> pEntityType, CollisionGetter pLevel, BlockPos pPos) {
      Optional<Vec3> optional = findStandUpPosition(pEntityType, pLevel, pPos, true);
      return optional.isPresent() ? optional : findStandUpPosition(pEntityType, pLevel, pPos, false);
   }

   private static Optional<Vec3> findStandUpPosition(EntityType<?> p_55844_, CollisionGetter p_55845_, BlockPos p_55846_, boolean p_55847_) {
      MutableBlockPos blockpos$mutableblockpos = new MutableBlockPos();
      UnmodifiableIterator var5 = RESPAWN_OFFSETS.iterator();

      while (var5.hasNext()) {
         Vec3i vec3i = (Vec3i)var5.next();
         blockpos$mutableblockpos.m_122190_(p_55846_).m_122193_(vec3i);
         Vec3 vec3 = DismountHelper.m_38441_(p_55844_, p_55845_, blockpos$mutableblockpos, p_55847_);
         if (vec3 != null) {
            return Optional.of(vec3);
         }
      }

      return Optional.empty();
   }

   public boolean m_7357_(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
      return false;
   }

   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new TileEndAnchor(pPos, pState);
   }

   public RenderShape m_7514_(BlockState pState) {
      return RenderShape.MODEL;
   }

   public List<ItemStack> m_7381_(BlockState state, net.minecraft.world.level.storage.loot.LootContext.Builder builder) {
      return List.of(new ItemStack(this));
   }
}
