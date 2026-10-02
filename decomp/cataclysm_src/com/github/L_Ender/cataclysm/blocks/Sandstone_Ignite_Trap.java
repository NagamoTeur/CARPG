package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.blockentities.SandstoneIgniteTrap_Block_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class Sandstone_Ignite_Trap extends BaseEntityBlock {
   public static final BooleanProperty LIT = BlockStateProperties.f_61443_;

   public Sandstone_Ignite_Trap(Properties properties) {
      super(properties);
      this.m_49959_((BlockState)this.m_49966_().m_61124_(LIT, false));
   }

   public boolean m_6724_(BlockState state) {
      return (Boolean)state.m_61143_(LIT);
   }

   public void m_213898_(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
      if ((Boolean)state.m_61143_(LIT)) {
         worldIn.m_7731_(pos, (BlockState)state.m_61124_(LIT, false), 3);
      }
   }

   public void m_141947_(Level worldIn, BlockPos pos, BlockState state, Entity entityIn) {
      activate(worldIn.m_8055_(pos), worldIn, pos, entityIn);
      super.m_141947_(worldIn, pos, state, entityIn);
   }

   public RenderShape m_7514_(BlockState p_149645_1_) {
      return RenderShape.MODEL;
   }

   public static boolean shouldTrigger(Entity entity) {
      if (!(entity instanceof LivingEntity) || entity.m_6095_().m_204039_(ModTag.SANDSTONE_TRAP_NOT_DETECTED)) {
         return false;
      } else {
         return !(entity instanceof Player) ? !(entity instanceof ArmorStand) : !((Player)entity).m_7500_() && !entity.m_5833_();
      }
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{LIT});
   }

   private static void activate(BlockState state, Level world, BlockPos pos, Entity entity) {
      if (!(Boolean)state.m_61143_(LIT) && shouldTrigger(entity)) {
         world.m_7731_(pos, (BlockState)state.m_61124_(LIT, true), 3);
         world.m_7785_(
            (double)pos.m_123341_() + 0.5,
            (double)pos.m_123342_() + 0.5,
            (double)pos.m_123343_() + 0.5,
            (SoundEvent)ModSounds.FLAME_TRAP.get(),
            SoundSource.BLOCKS,
            1.0F + world.f_46441_.m_188501_(),
            world.f_46441_.m_188501_() * 0.7F + 0.3F,
            false
         );
      }
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new SandstoneIgniteTrap_Block_Entity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
      return m_152132_(p_152182_, (BlockEntityType)ModTileentites.SANDSTONE_IGNITE_TRAP.get(), SandstoneIgniteTrap_Block_Entity::commonTick);
   }
}
