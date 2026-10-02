package com.obscuria.aquamirae.common.blocks;

import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class WisteriaNiveisBlock extends DoublePlantBlock {
   public static final BooleanProperty LOOT = BooleanProperty.m_61465_("loot");

   public WisteriaNiveisBlock() {
      super(
         Properties.m_60939_(Material.f_76274_)
            .m_60918_(SoundType.f_56714_)
            .m_60913_(1.0F, 10.0F)
            .m_60910_()
            .m_60924_((bs, br, bp) -> false)
            .m_222979_(OffsetType.XYZ)
      );
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(LOOT, true));
   }

   protected void m_7926_(@NotNull Builder<Block, BlockState> builder) {
      super.m_7926_(builder);
      builder.m_61104_(new Property[]{LOOT});
   }

   public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
      return ((Item)AquamiraeItems.WISTERIA_NIVEIS.get()).m_7968_();
   }

   protected boolean m_6266_(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
      return state.m_204336_(BlockTags.f_144279_) || super.m_6266_(state, level, pos);
   }

   public boolean canBePlacedOn(Level level, BlockPos pos) {
      return this.m_6266_(level.m_8055_(pos), level, pos) && level.m_46859_(pos.m_6630_(1)) && level.m_46859_(pos.m_6630_(2));
   }

   public boolean canBePlacedOn(WorldGenLevel level, BlockPos pos) {
      return this.m_6266_(level.m_8055_(pos), level, pos) && level.m_46859_(pos.m_6630_(1)) && level.m_46859_(pos.m_6630_(2));
   }

   @NotNull
   public List<ItemStack> m_7381_(@NotNull BlockState state, @NotNull net.minecraft.world.level.storage.loot.LootContext.Builder builder) {
      return (List<ItemStack>)(state.m_61143_(f_52858_) == DoubleBlockHalf.LOWER
         ? (state.m_61143_(LOOT) ? super.m_7381_(state, builder) : List.of(((Item)AquamiraeItems.WISTERIA_NIVEIS.get()).m_7968_()))
         : new ArrayList<>());
   }

   public void m_6807_(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean moving) {
      if (state.m_61143_(f_52858_) == DoubleBlockHalf.LOWER && world.m_46859_(pos.m_7494_())) {
         world.m_7731_(
            pos.m_7494_(),
            (BlockState)((BlockState)((Block)AquamiraeBlocks.WISTERIA_NIVEIS.get()).m_49966_().m_61124_(f_52858_, DoubleBlockHalf.UPPER)).m_61124_(LOOT, false),
            3
         );
      }
   }
}
