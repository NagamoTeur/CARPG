package com.obscuria.aquamirae.common.blocks;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

public class LuminescentBubbleBlock extends Block implements SimpleWaterloggedBlock {
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.f_61362_;

   public LuminescentBubbleBlock() {
      super(
         Properties.m_60944_(Material.f_76301_, MaterialColor.f_76362_)
            .m_60918_(SoundType.f_56753_)
            .m_60913_(0.1F, 0.5F)
            .m_60953_(s -> 14)
            .m_60910_()
            .m_60956_(0.8F)
            .m_60967_(0.8F)
            .m_60955_()
            .m_60982_((bs, br, bp) -> true)
            .m_60991_((bs, br, bp) -> true)
            .m_60924_((bs, br, bp) -> false)
            .m_60988_()
            .m_222979_(OffsetType.XYZ)
      );
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(WATERLOGGED, false));
   }

   public void m_6807_(@NotNull BlockState blockstate, @NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean moving) {
      super.m_6807_(blockstate, world, pos, oldState, moving);
      world.m_186460_(pos, this, 20);
   }

   public void m_213897_(@NotNull BlockState blockstate, @NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull RandomSource random) {
      super.m_213897_(blockstate, world, pos, random);
      world.m_186460_(pos, this, 20);
      Vec3 center = new Vec3((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
      List<Player> list = world.m_6443_(Player.class, new AABB(center, center).m_82400_(8.0), e -> true)
         .stream()
         .sorted(Comparator.comparingDouble(ent -> ent.m_20238_(center)))
         .toList();
      list.forEach(player -> {
         if (player.m_20069_()) {
            player.m_7292_(new MobEffectInstance(MobEffects.f_19593_, 80, 1, false, true));
         }
      });
   }

   public boolean m_7420_(BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos) {
      return state.m_60819_().m_76178_();
   }

   public int m_7753_(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos) {
      return 0;
   }

   @NotNull
   public VoxelShape m_5940_(BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      Vec3 offset = state.m_60824_(world, pos);
      return m_49796_(3.3, 1.0, 3.3, 12.7, 15.0, 12.7).m_83216_(offset.f_82479_, offset.f_82480_, offset.f_82481_);
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{WATERLOGGED});
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
      return ((Item)AquamiraeItems.LUMINESCENT_BUBBLE.get()).m_7968_();
   }

   public BlockPathTypes getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, Mob entity) {
      return BlockPathTypes.OPEN;
   }

   @NotNull
   public List<ItemStack> m_7381_(@NotNull BlockState state, @NotNull net.minecraft.world.level.storage.loot.LootContext.Builder builder) {
      return Collections.singletonList(((Item)AquamiraeItems.LUMINESCENT_BUBBLE.get()).m_7968_());
   }

   @OnlyIn(Dist.CLIENT)
   public void m_214162_(@NotNull BlockState blockstate, @NotNull Level world, @NotNull BlockPos pos, @NotNull RandomSource random) {
      super.m_214162_(blockstate, world, pos, random);

      for (int l = 0; l < 3; l++) {
         double x0 = (double)((float)pos.m_123341_() + random.m_188501_());
         double y0 = (double)((float)pos.m_123342_() + random.m_188501_());
         double z0 = (double)((float)pos.m_123343_() + random.m_188501_());
         double dx = ((double)random.m_188501_() - 0.5) * 0.16;
         double dy = ((double)random.m_188501_() - 0.5) * 0.16;
         double dz = ((double)random.m_188501_() - 0.5) * 0.16;
         world.m_7106_(ParticleTypes.f_123795_, x0, y0, z0, dx, dy, dz);
      }
   }

   @NotNull
   public InteractionResult m_6227_(
      @NotNull BlockState blockstate,
      @NotNull Level world,
      @NotNull BlockPos pos,
      @NotNull Player entity,
      @NotNull InteractionHand hand,
      @NotNull BlockHitResult hit
   ) {
      super.m_6227_(blockstate, world, pos, entity, hand, hit);
      world.m_46961_(pos, false);
      ItemStack stack = new ItemStack((ItemLike)AquamiraeItems.LUMINESCENT_BUBBLE.get(), 1);
      ItemHandlerHelper.giveItemToPlayer(entity, stack);
      return InteractionResult.SUCCESS;
   }
}
