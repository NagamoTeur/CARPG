package com.obscuria.aquamirae.common.blocks;

import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class OxygeliumBlock extends Block implements SimpleWaterloggedBlock {
   public static final EnumProperty<OxygeliumBlock.Type> TYPE = EnumProperty.m_61587_("type", OxygeliumBlock.Type.class);
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.f_61362_;

   public OxygeliumBlock() {
      super(
         Properties.m_60944_(Material.f_76301_, MaterialColor.f_76421_)
            .m_60918_(SoundType.f_56753_)
            .m_60913_(0.8F, 1.0F)
            .m_60977_()
            .m_60999_()
            .m_60910_()
            .m_60956_(0.8F)
            .m_60967_(0.8F)
            .m_60924_((bs, br, bp) -> false)
            .m_60982_((bs, br, bp) -> isGlowing(bs))
            .m_60991_((bs, br, bp) -> isGlowing(bs))
            .m_60953_(s -> isGlowing(s) ? 12 : 0)
            .m_222994_()
      );
      this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(WATERLOGGED, false)).m_61124_(TYPE, OxygeliumBlock.Type.STEM));
   }

   private static boolean isGlowing(BlockState state) {
      return state.m_61143_(TYPE) == OxygeliumBlock.Type.BUD || state.m_61143_(TYPE) == OxygeliumBlock.Type.RARE_BUD;
   }

   public boolean m_7420_(BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos) {
      return state.m_60819_().m_76178_();
   }

   @NotNull
   public List<ItemStack> m_7381_(@NotNull BlockState state, @NotNull net.minecraft.world.level.storage.loot.LootContext.Builder builder) {
      return Collections.singletonList(((Item)AquamiraeItems.OXYGELIUM.get()).m_7968_());
   }

   public int m_7753_(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos) {
      return 0;
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{TYPE, WATERLOGGED});
   }

   public BlockState m_5573_(BlockPlaceContext context) {
      boolean flag = context.m_43725_().m_6425_(context.m_8083_()).m_76152_() == Fluids.f_76193_;
      return (BlockState)this.m_49966_().m_61124_(WATERLOGGED, flag);
   }

   @NotNull
   public FluidState m_5888_(BlockState state) {
      return state.m_61143_(WATERLOGGED) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(state);
   }

   @NotNull
   public BlockState m_7417_(
      BlockState state,
      @NotNull Direction facing,
      @NotNull BlockState facingState,
      @NotNull LevelAccessor world,
      @NotNull BlockPos currentPos,
      @NotNull BlockPos facingPos
   ) {
      if ((Boolean)state.m_61143_(WATERLOGGED)) {
         world.m_186469_(currentPos, Fluids.f_76193_, Fluids.f_76193_.m_6718_(world));
      }

      return super.m_7417_(state, facing, facingState, world, currentPos, facingPos);
   }

   public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
      return new ItemStack((ItemLike)AquamiraeItems.OXYGELIUM.get());
   }

   public BlockPathTypes getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, Mob entity) {
      return BlockPathTypes.OPEN;
   }

   public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
      return super.canHarvestBlock(state, world, pos, player)
         && player.m_150109_().m_36056_().m_41720_() instanceof TieredItem tieredItem
         && tieredItem.m_43314_().m_6604_() >= 1;
   }

   public void m_6861_(
      @NotNull BlockState blockstate, @NotNull Level world, @NotNull BlockPos pos, @NotNull Block neighborBlock, @NotNull BlockPos fromPos, boolean moving
   ) {
      super.m_6861_(blockstate, world, pos, neighborBlock, fromPos, moving);
      if (!world.m_8055_(pos.m_7495_()).m_60815_()) {
         if (!world.m_8055_(pos.m_7495_()).m_61138_(TYPE) || world.m_8055_(pos.m_7495_()).m_61143_(TYPE) != OxygeliumBlock.Type.STEM) {
            Block.m_49892_(world.m_8055_(pos), world, pos, null);
            world.m_46961_(pos, false);
         }
      }
   }

   public void m_213897_(@NotNull BlockState state, @NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull RandomSource random) {
      if (state.m_61143_(TYPE) == OxygeliumBlock.Type.EMPTY_BUD) {
         if (Math.random() < 0.1) {
            if (!world.m_5776_()) {
               world.m_5594_(null, pos, SoundEvents.f_11773_, SoundSource.BLOCKS, 5.0F, 1.0F);
            }

            if (Math.random() <= 0.98) {
               world.m_8767_(
                  ParticleTypes.f_123795_, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5, 10, 0.5, 0.5, 0.5, 0.2
               );
               world.m_7731_(
                  pos,
                  (BlockState)((BlockState)((Block)AquamiraeBlocks.OXYGELIUM.get()).m_49966_().m_61124_(TYPE, OxygeliumBlock.Type.BUD))
                     .m_61124_(WATERLOGGED, (Boolean)state.m_61143_(WATERLOGGED)),
                  3
               );
            } else {
               world.m_8767_(
                  ParticleTypes.f_123748_, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5, 5, 0.5, 0.5, 0.5, 0.2
               );
               world.m_7731_(
                  pos,
                  (BlockState)((BlockState)((Block)AquamiraeBlocks.OXYGELIUM.get()).m_49966_().m_61124_(TYPE, OxygeliumBlock.Type.RARE_BUD))
                     .m_61124_(WATERLOGGED, (Boolean)state.m_61143_(WATERLOGGED)),
                  3
               );
            }
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void m_214162_(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull RandomSource random) {
      super.m_214162_(state, world, pos, random);
      if (state.m_61143_(TYPE) == OxygeliumBlock.Type.BUD || state.m_61143_(TYPE) == OxygeliumBlock.Type.RARE_BUD) {
         int x = pos.m_123341_();
         int y = pos.m_123342_();
         int z = pos.m_123343_();
         if (world.m_8055_(pos.m_7494_()).m_60734_() == Blocks.f_49990_) {
            for (int l = 0; l < 2; l++) {
               double x0 = (double)x + 0.5 + ((double)random.m_188501_() - 0.5) * 2.0;
               double y0 = (double)y + 1.2 + ((double)random.m_188501_() - 0.5) * 2.0;
               double z0 = (double)z + 0.5 + ((double)random.m_188501_() - 0.5) * 2.0;
               world.m_7106_(ParticleTypes.f_123795_, x0, y0, z0, 0.0, 0.0, 0.0);
            }
         }
      }
   }

   public void m_6256_(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player entity) {
      super.m_6256_(state, world, pos, entity);
      if (state.m_61143_(TYPE) == OxygeliumBlock.Type.BUD) {
         this.useBud(state, world, pos, entity);
      } else if (state.m_61143_(TYPE) == OxygeliumBlock.Type.RARE_BUD) {
         this.useRareBud(state, world, pos, entity);
      }
   }

   public void m_7892_(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Entity entity) {
      super.m_7892_(state, world, pos, entity);
      if (entity instanceof LivingEntity living && state.m_61143_(TYPE) == OxygeliumBlock.Type.BUD) {
         this.useBud(state, world, pos, living);
         return;
      }

      if (entity instanceof LivingEntity living && state.m_61143_(TYPE) == OxygeliumBlock.Type.RARE_BUD) {
         this.useRareBud(state, world, pos, living);
      }
   }

   @NotNull
   public InteractionResult m_6227_(
      @NotNull BlockState state,
      @NotNull Level world,
      @NotNull BlockPos pos,
      @NotNull Player entity,
      @NotNull InteractionHand hand,
      @NotNull BlockHitResult hit
   ) {
      if (state.m_61143_(TYPE) == OxygeliumBlock.Type.BUD) {
         return this.useBud(state, world, pos, entity);
      } else {
         return state.m_61143_(TYPE) == OxygeliumBlock.Type.RARE_BUD
            ? this.useRareBud(state, world, pos, entity)
            : super.m_6227_(state, world, pos, entity, hand, hit);
      }
   }

   @NotNull
   private InteractionResult useBud(BlockState state, Level world, BlockPos pos, LivingEntity entity) {
      if (!world.m_5776_()) {
         world.m_5594_(null, pos, SoundEvents.f_11773_, SoundSource.BLOCKS, 5.0F, 1.0F);
      }

      world.m_7731_(
         pos,
         (BlockState)((BlockState)((Block)AquamiraeBlocks.OXYGELIUM.get()).m_49966_().m_61124_(TYPE, OxygeliumBlock.Type.EMPTY_BUD))
            .m_61124_(WATERLOGGED, (Boolean)state.m_61143_(WATERLOGGED)),
         3
      );
      if (world instanceof ServerLevel server) {
         server.m_8767_(
            ParticleTypes.f_123795_, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5, 12, 0.5, 0.5, 0.5, 0.01
         );
      }

      entity.m_20301_(0);
      entity.m_7292_(
         new MobEffectInstance(
            MobEffects.f_19608_, (entity.m_21023_(MobEffects.f_19608_) ? Objects.requireNonNull(entity.m_21124_(MobEffects.f_19608_)).m_19557_() : 0) + 200, 0
         )
      );
      if (!world.m_5776_() && entity.m_21023_(MobEffects.f_19608_) && Objects.requireNonNull(entity.m_21124_(MobEffects.f_19608_)).m_19557_() > 1200) {
         world.m_5594_(null, pos, SoundEvents.f_11689_, SoundSource.BLOCKS, 1.0F, 1.0F);
         entity.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 200, 1));
      }

      return InteractionResult.SUCCESS;
   }

   @NotNull
   private InteractionResult useRareBud(BlockState state, Level world, BlockPos pos, LivingEntity entity) {
      if (!world.m_5776_()) {
         world.m_5594_(null, pos, SoundEvents.f_11871_, SoundSource.BLOCKS, 0.6F, 1.0F);
         world.m_7967_(new ExperienceOrb(world, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5, 8));
      }

      world.m_7731_(
         pos,
         (BlockState)((BlockState)((Block)AquamiraeBlocks.OXYGELIUM.get()).m_49966_().m_61124_(TYPE, OxygeliumBlock.Type.EMPTY_BUD))
            .m_61124_(WATERLOGGED, (Boolean)state.m_61143_(WATERLOGGED)),
         3
      );
      return InteractionResult.SUCCESS;
   }

   public static enum Type implements StringRepresentable {
      STEM("stem"),
      EMPTY_BUD("empty_bud"),
      BUD("bud"),
      RARE_BUD("rare_bud");

      private final String NAME;

      private Type(String name) {
         this.NAME = name;
      }

      @NotNull
      public String m_7912_() {
         return this.NAME;
      }
   }
}
