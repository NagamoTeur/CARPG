package com.github.L_Ender.cataclysm.blocks;

import com.github.L_Ender.cataclysm.init.ModTag;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SandStoneTrapBlock extends Block {
   public static final BooleanProperty LIT = BlockStateProperties.f_61443_;

   public SandStoneTrapBlock(Properties properties) {
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
      super.m_141947_(worldIn, pos, state, entityIn);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_214162_(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
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
}
