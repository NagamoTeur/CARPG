package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.IFilter;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.util.CasterUtil;
import com.hollingsworth.arsnouveau.common.block.tile.RuneTile;
import com.hollingsworth.arsnouveau.common.items.RunicChalk;
import com.hollingsworth.arsnouveau.common.items.SpellParchment;
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RuneBlock extends TickableModBlock {
   public static VoxelShape shape = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 0.5, 16.0);
   public static final BooleanProperty POWERED = BlockStateProperties.f_61448_;

   public RuneBlock() {
      super(defaultProperties().m_60910_().m_60955_().m_60913_(0.0F, 0.0F));
   }

   public RuneBlock(Properties properties) {
      super(properties);
   }

   public void m_6402_(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
      super.m_6402_(worldIn, pos, state, placer, stack);
   }

   public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      ItemStack stack = player.m_21120_(handIn);
      if (worldIn.m_7702_(pos) instanceof RuneTile runeTile) {
         if (!worldIn.f_46443_ && stack.m_41720_() instanceof RunicChalk && runeTile.isTemporary) {
            runeTile.isTemporary = false;
            PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.rune.setperm"));
            return InteractionResult.SUCCESS;
         }

         if (!(stack.m_41720_() instanceof SpellParchment) || worldIn.f_46443_) {
            return InteractionResult.SUCCESS;
         }

         Spell spell = CasterUtil.getCaster(stack).getSpell();
         if (spell.isEmpty()) {
            return InteractionResult.SUCCESS;
         }

         if (!(spell.recipe.get(0) instanceof MethodTouch)) {
            PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.rune.touch"));
            return InteractionResult.SUCCESS;
         }

         runeTile.setSpell(spell);
         PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.spell_set"));
      }

      return super.m_6227_(state, worldIn, pos, player, handIn, hit);
   }

   public void m_213897_(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
      super.m_213897_(state, worldIn, pos, rand);
      if (worldIn.m_7702_(pos) instanceof RuneTile rune && rune.touchedEntity != null) {
         rune.castSpell(rune.touchedEntity);
         rune.touchedEntity = null;
      }
   }

   public void m_7892_(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
      super.m_7892_(state, worldIn, pos, entityIn);
      if (worldIn.m_7702_(pos) instanceof RuneTile rune) {
         if (rune.spell != null) {
            for (AbstractSpellPart part : rune.spell.recipe) {
               if (!(part instanceof IFilter filter)) {
                  break;
               }

               if (!filter.shouldResolveOnEntity(entityIn)) {
                  return;
               }
            }
         }

         rune.touchedEntity = entityIn;
         worldIn.m_186460_(pos, this, 1);
      }
   }

   public void m_6861_(BlockState state, Level world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
      super.m_6861_(state, world, pos, blockIn, fromPos, isMoving);
      if (!world.m_5776_() && world.m_7702_(pos) instanceof RuneTile runeTile) {
         runeTile.disabled = world.m_46753_(pos);
      }
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return shape;
   }

   public RenderShape m_7514_(BlockState p_149645_1_) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new RuneTile(pos, state);
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{POWERED});
   }
}
